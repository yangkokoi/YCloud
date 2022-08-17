package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.ResourceVO;
import cn.xiaosm.cloud.front.entity.vo.UploadVO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
            resource.setPath(("/" + resource.getPath()).replaceAll("/+|\\\\+", "/"));
            Integer parentId = getIdByPath(bucket.getId(), resource.getPath());
            resources = resourceMapper.listByParentId(parentId);
        }
        resources.sort((el1, el2) -> {
            // 如果文件同类型，则按照文件首字母排序
            if (el1.isDir() == el2.isDir())
                return el1.getName().compareTo(el2.getName());
            // 文件夹在前，文件在后
            return el1.isDir() ? -1 : 1;
        });
        return resources;
    }

    private Integer getIdByPath(Integer bucketId, String fullPath) {
        if (fullPath.length() == 0 || "/".equals(fullPath)) return 0;
        // 截取最后一层文件名
        String name = fullPath.substring(fullPath.lastIndexOf("/") + 1);
        // String path = fullPath.substring(0, fullPath.lastIndexOf("/"));
        Integer parentId = resourceMapper.selectIdByBucketAndPath(bucketId, fullPath);
        if (null == parentId) throw new ResourceException(name + "-目录不存在");
        return parentId;
    }

    @Override
    public boolean create(ResourceVO resource) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 查询当前目录在是否存在
        long parentId = resourceMapper.selectIdByBucketAndPath(bucket.getId(), resource.getPath());
        if (parentId == 0) throw new ResourceException(resource.getPath() + "目录不存在");
        if (resource.isDir()) {

        } else {

        }
        // 在用户的指定仓库的路径下下新建文件夹
        // 此处构建了用户在本地的私有仓库
        File file = new File(UploadConfig.LOCAL_PATH, SecurityUtils.getLoginUser().getUuid());
        file = new File(file, resource.getPath());
        if (!file.exists() || !file.isDirectory()) throw new ResourceException("父级目录不存在");
        file = new File(file, resource.getName());
        if (file.exists()) throw new ResourceException("资源已存在，请勿重复创建");
        try {
            file = new File(file.getParentFile(), resource.getName());
            file.createNewFile();
            resource.setBucketId(bucketService.getBucket("local").getId());
            resource.setType(FileTypeUtil.getType(file));
            resource.setUserId(SecurityUtils.getLoginUser().getId());
            resource.setSize(0);
            resourceMapper.insert(resource);
        } catch (IOException e) {
            throw new ResourceException("创建资源出现问题");
        }
        return false;
    }

    private static String ILLEGAL_CHAR = "\\/:*\"<>|";
    @Override
    public boolean rename(ResourceDTO resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        String fileName = resource.getName();
        for (int i = 0; i < fileName.length(); i++) {
            if (ILLEGAL_CHAR.indexOf(fileName.charAt(i)) != -1) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
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
        // 此处构建了用户在本地的私有仓库
        return new File(bucketPath, resource.getPath());
    }

    /**
     * 文件上传
     * @param uploadVO
     * @return
     */
    @Override
    public List<String> upload(UploadVO uploadVO) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(uploadVO.getBucketName());
        Integer parentId = getIdByPath(bucket.getId(), uploadVO.getPath());
        if (null == parentId) throw new ResourceException(uploadVO.getPath() + "目录不存在");
        // 获取到仓库在本地的存储路径
        File bucketPath = FileUtil.file(UploadConfig.LOCAL_PATH, bucket.getPath());
        // 如果仓库目录不存在，则进行创建
        if (!bucketPath.exists()) bucketPath.mkdirs();
        List<String> pathList = new ArrayList<>();
        for (MultipartFile file : uploadVO.getFiles()) {
            File dest = null;
            try {
                String uuid = IdUtil.simpleUUID();
                // 本地文件名格式：uuid.[fileType]
                String fileName = uuid + "." + FileUtil.extName(file.getOriginalFilename());
                // 转存文件至本地
                dest = new File(bucketPath, fileName);
                file.transferTo(dest);
                Resource resource = new Resource(bucket, dest);
                resource.setPath(uploadVO.getPath() + "/" + fileName);
                resource.setHash(uuid);
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
        // BeanUtils.copyProperties(resource, condition);
        condition.setName(resource.getName());
        condition.setFileAbPath(file.getAbsolutePath());
        return condition;
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