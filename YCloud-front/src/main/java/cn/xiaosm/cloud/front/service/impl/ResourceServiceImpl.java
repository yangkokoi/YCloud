package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.enums.FileType;
import cn.xiaosm.cloud.front.entity.vo.BucketVO;
import cn.xiaosm.cloud.front.entity.vo.UploadVO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.service.BucketService;
import cn.xiaosm.cloud.front.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    BucketService bucketService;
    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public boolean rename(ResourceDTO resource) {

        return true;
    }

    @Override
    public boolean create(ResourceDTO resource) {
        // 本地仓库处理方法
        if (StrUtil.equals(UploadConfig.LOCAL_BUCKET, resource.getBucketName())) {
            // 在用户的指定仓库的路径下下新建文件夹
            // 此处构建了用户在本地的私有仓库
            File file = new File(UploadConfig.LOCAL_PATH, SecurityUtils.getLoginUser().getUuid());
            file = new File(file, resource.getPath());
            if (!file.exists() || !file.isDirectory()) throw new ResourceException("父级目录不存在");
            file = new File(file, resource.getName());
            if (file.exists()) throw new ResourceException("资源已存在，请勿重复创建");
            try {
                if (resource.getFileType() == FileType.DIR) file.mkdir();
                else {
                    resource.setUuid(IdUtil.simpleUUID());
                    file = new File(file.getParentFile(), resource.getName());
                    file.createNewFile();
                    resource.setBucketId(bucketService.getMyBucket("local").getId());
                    resource.setType(FileTypeUtil.getType(file));
                    resource.setUserId(SecurityUtils.getLoginUser().getId());
                    resource.setSize(0);
                    resourceMapper.insert(resource);
                }
            } catch (IOException e) {
                throw new ResourceException("创建资源出现问题");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(ResourceDTO resource) {
        if (StrUtil.equals(UploadConfig.LOCAL_BUCKET, resource.getBucketName())) {
            File file = this.getFileByPath(resource.getPath());
            file = new File(file, resource.getName());
            if (!file.exists()) throw new ResourceException("资源不存在");
            if (FileUtil.del(file))
                resourceMapper.deleteByUuidAndUserId(resource.getUuid(), SecurityUtils.getLoginUser().getId());
            return true;
        }
        return false;
    }

    public File getFileByPath(String path) {
        // 此处构建了用户在本地的私有仓库
        File file = new File(UploadConfig.LOCAL_PATH, SecurityUtils.getLoginUser().getUuid());
        return new File(file, path);
    }

    public File upload(MultipartFile file, File parent) {
        if (file.isEmpty()) return null;
        // String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // String fileName = UUID.randomUUID().toString() + fileType;
        try {
            File dest = new File(parent, file.getOriginalFilename());
            // 如果文件存在，则进行名字追加
            if (dest.exists()) dest = new File(parent, FileNameUtil.mainName(dest)
                    + " - 副本." + FileNameUtil.extName(dest));
            file.transferTo(dest);
            logger.info("文件上传成功");
            return dest;
        } catch (IOException e) {
            logger.error(e.toString(), "文件上传失败");
            return null;
        }
    }

    /**
     * 文件上传
     * @param uploadVO
     * @return
     */
    @Override
    public List<String> upload(UploadVO uploadVO) {
        List<String> pathList = new ArrayList<>();
        // 加载存储路劲
        uploadVO.setUser(SecurityUtils.getLoginUser());
        // 查询当前仓库
        Bucket bucket = bucketService.getMyBucket(uploadVO.getBucketName(), uploadVO.getUser().getId());
        if (Objects.isNull(bucket)) throw new ResourceException(uploadVO.getBucketName() + "仓库不存在");
        // 获取到存储路径的地址
        File parent = FileUtil.file(UploadConfig.LOCAL_PATH, uploadVO.getFullPath());
        // 如果目标目录不存在，则进行创建
        if (Objects.isNull(parent)) parent.mkdirs();
        for (MultipartFile file : uploadVO.getFiles()) {
            File dest = upload(file, parent);
            Resource resource = new Resource(bucket, dest, uploadVO.getPath());
            resource.setUuid(IdUtil.simpleUUID());
            resource.setName(file.getOriginalFilename());
            try {
                // 文件写入成功后在数据库中创建数据
                if (resourceMapper.insert(resource) == 1)
                    pathList.add(file.getName());
            } catch (Exception e) {
                dest.deleteOnExit();
            }
        }
        return pathList;
    }


    @Override
    public ResourceDTO download(ResourceDTO resource) {
        if (StrUtil.equals(UploadConfig.LOCAL_BUCKET, resource.getBucketName())) {
            File file = this.getFileByPath(resource.getPath());
            file = new File(file, resource.getName());
            if (!file.exists()) throw new ResourceException("资源不存在");
            resource.setFileAbPath(file.getAbsolutePath());
        }
        return resource;
    }

    private String getPath() {
        return "";
    }

}