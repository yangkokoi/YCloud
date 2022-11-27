package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.EditableType;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.dto.UploadDTO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.service.ResourceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    /**
     * 文件名不可用字符
     */
    private final static String ILLEGAL_CHAR = "\\/:*\"<>|";

    @Autowired
    LocalBucketServiceImpl bucketService;
    @Autowired
    ResourceMapper resourceMapper;

    /**
     * 通过 id 获取当前登录用户的资源
     * @param id
     * @return
     */
    @Override
    public Resource getByCurrentUser(Long id) {
        return resourceMapper.selectByIdAndUser(id, SecurityUtils.getLoginUserId());
    }

    @Override
    public List<Resource> getByCurrentUser(String ids) {
        return resourceMapper.selectByIdsAndUser(ids, SecurityUtils.getLoginUserId());
    }

    @Override
    public List<Resource> listByIds(String ids) {
        return resourceMapper.selectByIdsAndUser(ids, null);
    }

    @Override
    public List<Resource> list(ResourceDTO resource) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 后续的接口不需要再使用 userId 来查询，因为在上面的仓库查询中使用过 userId 筛选过了
        List<Resource> resources;
        if (resource.getParentId() != null && resource.getParentId() > 0) { // 如果有父级 id，则根据父级 id 查询
            QueryWrapper<Resource> wrapper = new QueryWrapper();
            wrapper.select("id")
                .eq("id", resource.getParentId())
                .eq("user_id", SecurityUtils.getLoginUserId());
            Resource db = resourceMapper.selectOne(wrapper);
            Assert.notNull(db, "父级目录不存在");
            resources = resourceMapper.listByParentId(resource.getParentId());
        } else if (StrUtil.isBlank(resource.getPath()) || "/".equals(resource.getPath())) { // 如果路径不为空，则根据路径查询
            // 获取当前仓库根目录下所有文件
            resources = resourceMapper.listRoot(0, bucket.getId());
        } else {
            Long parentId = getIdByPath(bucket.getId(), resource.getPath());
            resources = resourceMapper.listByParentId(parentId);
        }
        // 根据类型过滤
        if (StrUtil.isNotBlank(resource.getType())) {
            resources = resources.stream().filter(el -> resource.getType().equals(el.getType()))
                .collect(Collectors.toList());
        }
        resources.sort((el1, el2) -> {
            // 如果文件同类型，则按照文件首字母排序
            if (el1.isDir() == el2.isDir()) return el1.getName().compareToIgnoreCase(el2.getName());
            // 文件夹在前，文件在后
            return el1.isDir() ? -1 : 1;
        });
        return resources;
    }

    private Long getIdByPath(Integer bucketId, String fullPath) {
        if (fullPath.length() == 0 || "/".equals(fullPath)) return 0l;
        // 暂时先使用java循环来找进入文件夹叭
        String[] dirs = fullPath.split("/");
        Long parentId = 0l;
        for (String dir : dirs) {
            if ("".equals(dir)) continue;
            parentId = resourceMapper.selectIdByBucketAndNameAndDir(bucketId, parentId, dir);
            if (null == parentId) throw new ResourceException(dir + "-目录不存在");
        }
        return parentId;
    }

    @Override
    public String create(ResourceDTO resource) {
        // 校验文件名
        if (!checkName(resource.getName())) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(resource.getBucketName());
        // 获取父级菜单
        Long parentId = getIdByPath(bucket.getId(), resource.getPath());
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
        Resource db = new Resource(bucket);
        db.setName(resource.getName());
        db.setParentId(parentId);
        // 处理文件或目录
        File dest = null;
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
            // 获取到仓库在本地的存储路径
            File bucketPath = FileUtil.file(UploadConfig.LOCAL_PATH);
            dest = createOrTransformFile(bucketPath, fileName);
            db.setPath("/" + dest.getParentFile().getName() + "/" + fileName);
            db.setHash(uuid);
        }
        try {
            // 数据库中创建数据后创建文件
            if (resourceMapper.insert(db) == 1) {
                // 创建文件
                if (!db.isDir() && !dest.createNewFile()) {
                    log.info("文件创建失败");
                    throw new ResourceException("文件【" + resource.getName() + "】创建失败");
                }
                log.info("文件创建成功");
            }
        } catch (Exception e) {
            if (dest != null) {
                dest.deleteOnExit();
            }
            e.printStackTrace();
            log.error("文件创建失败");
            throw new ResourceException("文件【" + resource.getName() + "】创建失败");
        }
        return db.getHash();
    }

    @Override
    public boolean saveContent(ResourceDTO dto) {
        // 获取数据库中的数据
        QueryWrapper<Resource> wrapper = new QueryWrapper<>();
        wrapper.eq("id", dto.getId()).eq("user_id", SecurityUtils.getLoginUserId());
        Resource db = resourceMapper.selectOne(wrapper);
        // 如果不是属于自己的资源
        Assert.isTrue(null != db, () -> new ResourceException("资源不存在"));
        // 判断文件是否属于可编辑类型
        Assert.isTrue(EditableType.isEditable(db.getType()), () -> new ResourceException("文件不可编辑"));
        // 获取File文件
        File file = this.getLocalFile(db);
        Assert.isTrue(file.exists() && file.isFile(), () -> new ResourceException("资源不存在或已被删除"));
        FileUtil.writeBytes(dto.getContent().getBytes(), file);
        // 更新数据库信息
        Resource save = new Resource();
        BeanUtils.copyProperties(db, save);
        save.setId(dto.getId());
        save.setSize(file.length());
        save.setUpdateTime(LocalDateTime.now());
        resourceMapper.updateById(save);
        return true;
    }

    @Override
    public boolean rename(ResourceDTO resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        String fileName = resource.getName();
        // 文件名相同，跳过修改
        if (fileName.equals(db.getName())) return true;
        // 校验名字是否重复
        if (!checkNameAndUnique(fileName, db.getParentId())) throw new ResourceException("文件名不能包含：" + ILLEGAL_CHAR);
        Resource update = new Resource();
        update.setId(db.getId());
        update.setName(fileName);
        return resourceMapper.updateById(update) == 1;
    }

    /**
     * 复制文件
     * @param originId
     * @param targetId
     * @return
     */
    @Override
    @Transactional
    public boolean copy(Long originId, Long targetId) {
        // 复制原有的存储信息
        Resource origin = this.getByCurrentUser(originId);
        Assert.notNull(origin, "源资源不存在");
        // 获取 target
        Resource target;
        if (targetId.equals(0l)) {
            target = new Resource().setId(0l).setDir(true);
        } else {
            target = this.getByCurrentUser(targetId);
        }
        Assert.notNull(target, "目标资源不存在");
        Assert.isTrue(target.isDir(), "目标资源不是一个目录");
        // 校验操作
        this.checkMoveOrCopy(origin, target);
        Resource save = new Resource();
        // 复制被拷贝的数据
        BeanUtils.copyProperties(origin, save);
        save.setId(null);
        save.setParentId(target.getId());
        save.setCreateTime(LocalDateTime.now());
        if (origin.isDir()) {
            resourceMapper.insert(save);
            // 获取 origin 下的子文件
            List<Resource> children = resourceMapper.listByParentId(origin.getId());
            // 避免事物失效
            ((ResourceService) AopContext.currentProxy()).copy(children, save);
        } else {
            resourceMapper.insert(save);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean copy(List<Resource> resources, Resource parent) {
        Resource save;
        for (Resource child : resources) {
            // 保存每个子数据到新目录下
            save = new Resource();
            // 复制被拷贝的数据
            BeanUtils.copyProperties(child, save);
            save.setId(null);
            save.setParentId(parent.getId());
            save.setCreateTime(LocalDateTime.now());
            resourceMapper.insert(save);
            if (child.isDir()) {
                // 递归处理文件夹
                this.copy(resourceMapper.listByParentId(child.getId()), save);
            }
        }
        return true;
    }

    @Override
    public boolean move(Long originId, Long targetId) {
        Resource origin = this.getByCurrentUser(originId);
        Assert.notNull(origin, "源资源不存在");
        // 获取 target
        Resource target;
        if (targetId.equals(0l)) {
            target = new Resource().setId(0l).setDir(true);
        } else {
            target = this.getByCurrentUser(targetId);
        }
        Assert.notNull(target, "目标资源不存在");
        this.checkMoveOrCopy(origin, target);
        // 将源资源的 parentId 修改为目标 id
        Resource save = new Resource();
        BeanUtils.copyProperties(origin, save);
        save.setParentId(target.getId());
        return resourceMapper.updateById(save) == 1;
    }

    /**
     * 检查文件名是否合法
     * 检查目标文件夹下是否有重名文件
     * 检查 t 是否属于 o 的子文件
     * @param origin
     * @param target
     */
    public void checkMoveOrCopy(Resource origin, Resource target) {
        // 目标目录和被复制的文件的父级需要不相同，否则抛出异常
        Assert.isFalse(target.getId().equals(origin.getParentId()), "源文件夹不可与目标文件夹相同");
        // 校验文件名在目标目录下是否唯一
        try {
            this.checkNameAndUnique(origin.getName(), target.getId());
        } catch (CanShowException e) {
            throw new ResourceException("目标文件夹下有重名文件");
        }
        // 如果是根目录，校验 t 是否属于 o 的子文件
        if (origin.isDir() && !Long.valueOf(0l).equals(target.getId())) {
            // 判断目标文件夹是否是源文件夹的子文件夹
            Assert.isFalse(isChildren(origin, target), "目标文件夹是源文件夹的子文件夹");
        }
    }

    /**
     * 判断 t 是否属于 o 的子文件 或 两个资源是否相等
     * @param origin
     * @param target
     * @return
     */
    public boolean isChildren(Resource origin, Resource target) {
        if (null == target || null == target.getId()) return false;
        Long targetId = target.getId();
        if (Long.valueOf(0l).equals(targetId)) return false;
        // 如果源 id == 目标 id
        else if (origin.getId().equals(targetId)) return true;
        // 获取 target 的父级目录
        return isChildren(origin, resourceMapper.selectById(target.getParentId()));
    }

    /**
     * 资源删除，当资源 hash 唯一时，同时删除磁盘文件
     * 删除操作会同时删除当前资源下的所有子文件
     * @param resource
     * @return
     */
    @Override
    @Transactional
    public boolean delete(Resource resource) {
        // 获取数据库中的文件
        Resource db = resourceMapper.selectByIdAndUser(resource.getId(), SecurityUtils.getLoginUserId());
        if (null == db) throw new ResourceException("资源不存在");
        // 如果是目录，递归删除子文件
        if (db.isDir()) {
            QueryWrapper<Resource> wrapper = new QueryWrapper();
            wrapper.select("id", "type").eq("parent_id", db.getId());
            resourceMapper.selectList(wrapper).forEach(el -> {
                ((ResourceService) AopContext.currentProxy()).delete(el);
            });
        }
        resourceMapper.deleteById(db.getId());
        if (
            // 如果不为目录 && 当前资源没有引用，则删除本地资源
            db.isDir() == false && (resourceMapper.countByHash(db.getHash()) == 0)
        ) {
            File file = this.getLocalFile(db);
            if (file.exists() && file.delete()) {
                // 文件删除成功后删除数据库数据
                log.info("本地文件删除成功：{}", db.getName());
            }
        }
        return true;
    }

    /**
     * 通过文件路劲获取 File 对象
     * 注：仅会获取本地的文件
     * @param resource
     * @return
     */
    @Override
    public File getLocalFile(Resource resource) {
        Bucket bucket = bucketService.getBucket(resource.getBucketId());
        // 获取到仓库在本地的存储路径
        File bucketPath = bucketService.transformBucketToFile(bucket);
        // 根据仓库地址和文件相对地址创建文件对象
        return new File(bucketPath, resource.getPath());
    }

    public boolean save(MultipartFile file, Bucket bucket, @NonNull Long parentId, File bucketPath) throws IOException {
        String hash = DigestUtil.md5Hex(file.getInputStream());
        // 检查上传的文件名
        this.checkNameAndUnique(file.getOriginalFilename(), parentId);
        // 根据 hash 获取数据库数据
        Resource db = resourceMapper.selectByHash(hash);
        Resource resource;
        File dest = null;
        if (null != db) {
            // 如果 hash 冲突，且处于同一目录，则拒绝本次提交
            Assert.isFalse(parentId.equals(db.getParentId()), "当前目录下已有相同文件-" + db.getName());
            resource = new Resource();
            BeanUtils.copyProperties(db, resource);
            resource.setId(null);
        } else {
            String uuid = IdUtil.simpleUUID();
            String fileName = uuid + "." + FileUtil.extName(file.getOriginalFilename());
            // 创建本地文件
            dest = createOrTransformFile(bucketPath, fileName);
            resource = new Resource();
            // 文件对应的本地存储路径
            resource.setPath("/" + dest.getParentFile().getName() + "/" + fileName);
            resource.setType(FileTypeUtil.getType(file.getInputStream()));
            resource.setDir(false);
            resource.setSize(file.getSize());
        }
        resource.setHash(hash);
        resource.setName(file.getOriginalFilename());
        resource.setBucketId(bucket.getId());
        resource.setUserId(bucket.getUserId());
        resource.setParentId(parentId);
        if (resourceMapper.insert(resource) == 1) {
            // 转存至本地文件
            if (null != dest) file.transferTo(dest);
            return true;
        } else {
            if (null != dest) dest.deleteOnExit();
            return false;
        }
    }

    /**
     * 文件上传
     * @param upload
     * @return
     */
    @Override
    public List<String> upload(UploadDTO upload) {
        // 查询当前仓库
        Bucket bucket = bucketService.getBucket(upload.getBucketName());
        Long parentId = getIdByPath(bucket.getId(), upload.getPath());
        if (null == parentId) throw new ResourceException(upload.getPath() + "目录不存在");
        // 获取到仓库在本地的存储路径
        File bucketPath = FileUtil.file(UploadConfig.LOCAL_PATH);
        // 如果仓库目录不存在，则进行创建
        if (!bucketPath.exists()) bucketPath.mkdirs();
        List<String> pathList = new ArrayList<>();
        for (MultipartFile file : upload.getFiles()) {
            // 计算 hash
            try {
                // 文件写入成功后在数据库中创建数据
                this.save(file, bucket, parentId, bucketPath);
                log.info("文件上传成功");
            } catch (Exception e) {
                log.error("文件上传失败");
                e.printStackTrace();
                throw new ResourceException(e.getMessage());
            }
        }
        return pathList;
    }

    public File createOrTransformFile(File parent, String fileName) {
        // 本地文件名格式：yyyy-MM/uuid.[fileType]
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        File dest = new File(parent, month);
        if (!dest.exists()) dest.mkdir();
        dest = new File(dest, fileName);
        return dest;
    }

    /**
     * 文件名校验
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

    /**
     * 检查文件名是否合法
     * 检查文件名在目标文件夹下是否唯一
     * @param fileName
     * @param parentId
     * @return
     */
    private boolean checkNameAndUnique(String fileName, Long parentId) {
        if (!this.checkName(fileName)) return false;
        resourceMapper.selectList(
            new QueryWrapper<Resource>().eq("parent_id", parentId)
                .select("name")
        ).forEach(el -> {
            if (fileName.equals(el.getName())) throw new ResourceException("当前目录下已有同名文件");
        });
        return true;
    }

    @Override
    public ResourceDTO download(ResourceDTO condition) {
        Resource resource = this.getByCurrentUser(condition.getId());
        if (null == resource || resource.isDir()) throw new ResourceException("当前分享的资源在地球找不到啦！");
        File file = this.getLocalFile(resource);
        if (!file.exists()) throw new ResourceException("当前分享的资源在地球找不到啦！1001");;
        condition.setName(resource.getName());
        condition.setFileAbPath(file.getAbsolutePath());
        return condition;
    }

    @Override
    public ResourceDTO preview(ResourceDTO resourceDTO) {
        // 获取资源信息
        Resource resource = resourceMapper.selectByIdAndUser(resourceDTO.getId(), resourceDTO.getUserId());
        // 如果是目录，不进行预览
        // 如果文件过大，不进行预览
        if (resource.isDir() || (resource.getSize() != null && resource.getSize() > 10485760)) return null;
        File file = this.getLocalFile(resource);
        resourceDTO.setName(resource.getName());
        resourceDTO.setType(resource.getType());
        resourceDTO.setFileAbPath(file.getAbsolutePath());
        return resourceDTO;
    }

    @Override
    public ResourceDTO offlineDownload(String url) {
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

}