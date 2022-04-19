package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.enums.FileType;
import cn.xiaosm.cloud.front.entity.vo.BucketVO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.BucketMapper;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.service.BucketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BucketServiceImpl extends ServiceImpl<BucketMapper, Bucket> implements BucketService {

    private final String LOCAL_BUCKET = UploadConfig.LOCAL_BUCKET;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BucketMapper bucketMapper;
    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public List<Resource> intoPath(Bucket bucket) {
        // 本地仓库处理方法
        if (StrUtil.equals(LOCAL_BUCKET, bucket.getName())) {
            // 获取当前访问的路劲
            File file = this.makePathFile(bucket);
            List<Resource> list = this.allFileAndDir(file);
            list.sort((el1, el2) -> {
                // 如果文件同类型，则按照文件首字母排序
                if (el1.isDir() == el2.isDir())
                    return el1.getName().compareTo(el2.getName());
                return el1.isDir() ? -1 : 1;
            });
            return list;
        } else {
            return cloudBucket(bucket);
        }
    }

    /**
     * 本例仓库处理方法
     * @param path
     * @return
     */
    public List<Resource> localBucket(String path) {
        return null;
    }

    public List<Resource> cloudBucket(Bucket bucket) {
        // 云仓库处理方法
        // 判断当前登录用户是否拥有此仓库
        Bucket bucketB = this.getMyBucket(bucket.getName());
        if (Objects.isNull(bucketB)) {
            logger.info("仓库[{}]不存在", bucket.getName());
            throw new ResourceException("仓库不存在");
        }
        return new ArrayList<>();
    }

    public File makePathFile(Bucket bucket) {
        return this.makePathFile(bucket, false);
    }

    /**
     * 拼接路径 格式为：{localPath}/{user.UUID}/{path}
     * @param bucket
     * @param makeChild 是否创建子目录
     * @return
     */
    public File makePathFile(Bucket bucket, boolean makeChild) {
        // 此处构建了用户在本地的私有仓库
        File file = new File(UploadConfig.LOCAL_PATH, SecurityUtils.getLoginUser().getUuid());
        // 如果私有仓库存不存在，则创建目录
        if (!file.exists()) file.mkdir();
        // 获取 path 路径
        String path = (bucket instanceof BucketVO) ? ((BucketVO) bucket).getCurrentPath() : bucket.getPath();
        file = new File(file, path);
        // 如果地址存在，直接返回
        if (file.exists()) return file;
        if (makeChild == true) file.mkdirs();
        else {
            logger.info("目录不存在：{}", path);
            throw new ResourceException("目录不存在");
        }
        return null;
    }

    public List<Resource> allFileAndDir(String path) {
        return allFileAndDir(new File(path));
    }

    public List<Resource> allFileAndDir(File path) {
        List<Resource> resources = new ArrayList<>();
        File file;
        for (String fileName : path.list()) {
            file = new File(path, fileName);
            Resource resource = new Resource()
                .setPath(file.getAbsolutePath());
            resource.setUpdateTime(file.lastModified());
            if (file.isFile()) {
                resource.setType(FileTypeUtil.getType(file))
                    .setName(fileName)
                    .setSize(file.length())
                    .setUuid(FileUtil.mainName(file));
            } else {
                resource.setName(file.getName())
                    .setType(FileType.DIR.name())
                    .setDir(true);
            }
            resources.add(resource);
        }
        return resources;
    }

    @Override
    public Bucket getMyBucket(String name) {
        return this.getMyBucket(name, SecurityUtils.getLoginUser().getId());
    }

    @Override
    public Bucket getMyBucket(String name, Integer userId) {
        return bucketMapper.selectByNameAndUserId(name, userId);
    }

    /**
     * 将 Bucket 转换转换成本地磁盘中映射的文件目录
     * @param bucket
     * @return
     */
    @Override
    public File transformBucketToFile(Bucket bucket) {
        File file = new File(UploadConfig.LOCAL_PATH, bucket.getPath());
        return file;
    }

    @Override
    public boolean hasBucket(String name) {
        return Objects.nonNull(this.getMyBucket(name));
    }
}