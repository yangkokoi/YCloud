package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.ResourceVO;
import cn.xiaosm.cloud.front.entity.vo.UploadDTO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.service.ResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 文件名不可用字符
     */
    private final static String ILLEGAL_CHAR = "\\/:*\"<>|";

    @Autowired
    LocalBucketServiceImpl bucketService;
    @Autowired
    ResourceMapper resourceMapper;

    private Resource getByIdAndUser(Integer id) {
        return resourceMapper.selectByIdAndUser(id, SecurityUtils.getLoginUserId());
    }

    @Override
    public List<Resource> list(ResourceVO resource) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        List<Resource> resources;
        if (StrUtil.isBlank(resource.getPath()) || "/".equals(resource.getPath())) {
            // 获取当前仓库根目录下所有文件
            resources = resourceMapper.listRoot(0, bucket.getId());
        } else {
            resource.setPath("/" + resource.getPath());
            Integer parentId = getIdByPath(bucket.getId(), resource.getPath());
            resources = resourceMapper.listByParentId(parentId);
        }
        resources.sort((el1, el2) -> {
            // 如果文件同类型，则按照文件首字母排序
            if (el1.isDir() == el2.isDir()) return el1.getName().compareTo(el2.getName());
            // 文件夹在前，文件在后
            return el1.isDir() ? -1 : 1;
        });
        return resources;
    }

    private Integer getIdByPath(Integer bucketId, String fullPath) {
        if (fullPath.length() == 0 || "/".equals(fullPath)) return 0;
        if (!fullPath.startsWith("/")) fullPath = "/" + fullPath;
        // 截取最后一层文件名
        String name = fullPath.substring(fullPath.lastIndexOf("/") + 1);
        // String path = fullPath.substring(0, fullPath.lastIndexOf("/"));
        Integer parentId = resourceMapper.selectIdByBucketAndPath(bucketId, fullPath);
        if (null == parentId) throw new ResourceException(name + "-目录不存在");
        return parentId;
    }

    @Override
    public boolean create(ResourceDTO resource) {
        // 校验文件名
        if (!checkName(resource.getName())) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 获取父级菜单
        Integer parentId = getIdByPath(bucket.getId(), resource.getPath());
        if (null == parentId) throw new ResourceException(resource.getPath() + "目录不存在");
        // 校验名字是否重复
        Resource exist = resourceMapper.selectOne(
            new QueryWrapper<Resource>().eq("parent_id", parentId)
                .eq("name", resource.getName())
                .select("id")
        );
        // 当文件名重复时
        if (!(null == exist || null == exist.getId())) {
            throw new ResourceException("文件名重复");
        }
        // 获取到仓库在本地的存储路径
        File bucketPath = FileUtil.file(UploadConfig.LOCAL_PATH, bucket.getPath());
        File dest = new File(bucketPath, resource.getName());
        Resource db = new Resource(bucket);
        db.setPath(resource.getPath() + "/" + resource.getName());
        db.setName(resource.getName());
        db.setParentId(parentId);
        try {
            if (resource.isDir()) {
                db.setType("dir");
            } else {
                String uuid = IdUtil.simpleUUID();
                // 本地文件名格式：uuid.[fileType]
                String fileType = FileUtil.extName(resource.getName());
                String fileName;
                if (StrUtil.isBlank(fileType)) {
                    fileName = uuid;
                    db.setType("txt");
                } else {
                    fileName = uuid + "." + fileType;
                    db.setType(fileType);
                }
                db.setPath(resource.getPath() + "/" + fileName);
                db.setUuid(uuid);
            }
            // 数据库中创建数据后创建文件
            if (resourceMapper.insert(db) == 1) {
                if (!db.isDir() && !dest.createNewFile()) {
                    logger.info("文件创建失败");
                    return false;
                }
                logger.info("文件创建成功");
                return true;
            }
        } catch (Exception e) {
            dest.deleteOnExit();
            logger.error(e.toString(), "文件创建失败");
        }
        return false;
    }

    @Override
    public boolean rename(ResourceDTO resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        String fileName = resource.getName();
        // 文件名相同，跳过修改
        if (fileName.equals(db.getName())) return true;
        if (!checkName(fileName)) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        // 校验名字是否重复
        Resource exist = resourceMapper.selectOne(
            new QueryWrapper<Resource>().eq("parent_id", db.getParentId())
                .eq("name", resource.getName())
                .select("id")
        );
        // 如果文件存在
        if (!(null == exist || null == exist.getId()) && !exist.getId().equals(db.getId())) {
            throw new ResourceException("文件名重复");
        }
        Resource update = new Resource();
        update.setId(db.getId());
        update.setName(fileName);
        if (db.isDir()) {
            // 如果是目录还需要更新path值
            String path = db.getPath();
            path = path.replace(db.getName(), fileName);
            update.setPath(path);
        }
        return resourceMapper.updateById(update) == 1;
    }

    /**
     * 文件名校验
     *
     * @param fileName
     * @return
     */
    private boolean checkName(String fileName) {
        if (StrUtil.isBlank(fileName)) return false;
        for (int i = 0; i < fileName.length(); i++) {
            if (ILLEGAL_CHAR.indexOf(fileName.charAt(i)) != -1) return false;
        }
        return true;
    }

    @Override
    public boolean delete(ResourceDTO resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        File file = this.getLocalFile(db);
        if (!file.exists()) {
            // 文件不存在，直接删除数据库数据
            resourceMapper.deleteById(db.getId());
        } else if (file.delete()) {
            // 文件删除成功后删除数据库数据
            resourceMapper.deleteById(db.getId());
        } else throw new ResourceException("资源删除失败，请稍后再试");
        return true;
    }

    /**
     * 通过文件路劲获取 File 对象
     * 注：仅会获取本地的文件
     *
     * @param resource
     * @return
     */
    private File getLocalFile(Resource resource) {
        Bucket bucket = bucketService.getBucket(resource.getBucketId());
        // 获取到仓库在本地的存储路径
        File bucketPath = bucketService.transformBucketToFile(bucket);
        // 获取路径最后一层
        String path = resource.getPath();
        path = path.substring(path.lastIndexOf("/") + 1);
        // 根据仓库地址和文件相对地址创建文件对象
        return new File(bucketPath, path);
    }

    /**
     * 文件上传
     *
     * @param upload
     * @return
     */
    @Override
    public List<String> upload(UploadDTO upload) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(upload.getBucketName());
        Integer parentId = getIdByPath(bucket.getId(), upload.getPath());
        if (null == parentId) throw new ResourceException(upload.getPath() + "目录不存在");
        // 获取到仓库在本地的存储路径
        File bucketPath = FileUtil.file(UploadConfig.LOCAL_PATH, bucket.getPath());
        // 如果仓库目录不存在，则进行创建
        if (!bucketPath.exists()) bucketPath.mkdirs();
        List<String> pathList = new ArrayList<>();
        for (MultipartFile file : upload.getFiles()) {
            File dest = null;
            try {
                String uuid = IdUtil.simpleUUID();
                // 本地文件名格式：uuid.[fileType]
                String fileName = uuid + "." + FileUtil.extName(file.getOriginalFilename());
                // 转存文件至本地
                dest = new File(bucketPath, fileName);
                file.transferTo(dest);
                Resource resource = new Resource(bucket, dest);
                resource.setPath(upload.getPath() + "/" + fileName);
                resource.setUuid(uuid);
                resource.setName(file.getOriginalFilename());
                resource.setParentId(parentId);
                // 文件写入成功后在数据库中创建数据
                if (resourceMapper.insert(resource) == 1) pathList.add(resource.getName());
                logger.info("文件上传成功");
            } catch (Exception e) {
                dest.deleteOnExit();
                logger.error(e.toString(), "文件上传失败");
            }
        }
        return pathList;
    }

    @Override
    public ResourceDTO download(ResourceDTO condition) {
        Resource resource = this.getByIdAndUser(condition.getId());
        if (null == resource || resource.isDir()) return null;
        File file = this.getLocalFile(resource);
        if (!file.exists()) return null;
        condition.setName(resource.getName());
        condition.setFileAbPath(file.getAbsolutePath());
        return condition;
    }

    @Override
    public ResourceDTO preview(ResourceDTO resourceDTO) {
        // 获取资源信息
        Resource resource = resourceMapper.selectByUUIDAndUser(resourceDTO.getUuid(), resourceDTO.getUserId());
        // 如果文件过大，则不进行预览
        if (resource.getSize() > 10485760) return null;
        File file = this.getLocalFile(resource);
        resourceDTO.setName(resource.getName());
        resourceDTO.setType(resource.getType());
        resourceDTO.setFileAbPath(file.getAbsolutePath());
        return resourceDTO;
    }

    @Override
    public ResourceDTO offlineDownload(String url) {
        // File file = new File(bucketService.getLocalFile(), "download");
        File file = new File("C:\\Users\\Young\\Desktop\\local", "download");
        if (!file.exists()) file.mkdir();
        long length = 0;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            length = connection.getContentLengthLong();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // long len = Long.valueOf(res.header("Content-length"));
        // System.out.println("长度：" + len);

        return null;
    }

    public static void main(String[] args) {
        // new ResourceServiceImpl().offlineDownload("https://download-ssl.firefox.com.cn/releases-sha2/full/99.0/zh-CN/Firefox-full-latest-win64.exe");
        new ResourceServiceImpl().offlineDownload("https://download-ssl.firefox.com.cn/releases-sha2/full/99.0/zh-CN/Firefox-full-latest-win64.exe");
    }


    private String getPath() {
        return "";
    }

}