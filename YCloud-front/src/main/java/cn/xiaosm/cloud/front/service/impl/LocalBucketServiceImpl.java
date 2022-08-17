package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
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

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Service
public class LocalBucketServiceImpl extends ServiceImpl<BucketMapper, Bucket> implements BucketService {

    private final String LOCAL_BUCKET = UploadConfig.LOCAL_BUCKET;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BucketMapper bucketMapper;
    @Autowired
    ResourceMapper resourceMapper;

    /**
     * 本例仓库处理方法
     * @param path
     * @return
     */
    public List<Resource> localBucket(String path) {
        return null;
    }

    public List<Resource> cloudBucket(Bucket bucket) {
        // 判断当前登录用户是否拥有此仓库
        Bucket bucketB = this.getBucket(bucket.getName());
        if (Objects.isNull(bucketB)) {
            logger.info("仓库[{}]不存在", bucket.getName());
            throw new ResourceException("仓库不存在");
        }
        return new ArrayList<>();
    }

    @Override
    public Bucket getLocalBucket() {
        Bucket bucket = new Bucket();
        bucket.setPath("/" + SecurityUtils.getLoginUser().getUuid());
        return bucket;
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
                    .setSize(file.length()).setHash(FileUtil.mainName(file));
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
    public Bucket getBucket(String name) {
        Bucket bucket = this.getBucket(name, SecurityUtils.getLoginUser().getId());
        if (Objects.isNull(bucket)) throw new ResourceException(name + "-仓库不存在");
        return bucket;
    }

    @Override
    public Bucket getBucket(Integer bucketId) {
        if (null == bucketId) return null;
        return bucketMapper.selectById(bucketId);
    }

    @Override
    public Bucket getBucket(String name, Integer userId) {
        return bucketMapper.selectByNameAndUserId(name, userId);
    }

    /**
     * 将 Bucket 转换转换成本地磁盘中映射的文件目录
     * @param bucket
     * @return
     */
    @Override
    public File transformBucketToFile(Bucket bucket) {
        File file = FileUtil.file(UploadConfig.LOCAL_PATH, bucket.getPath());
        return file;
    }

    @Override
    public boolean hasBucket(String name) {
        return Objects.nonNull(this.getBucket(name));
    }
}