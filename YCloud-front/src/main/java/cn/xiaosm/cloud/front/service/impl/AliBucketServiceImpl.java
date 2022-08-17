package cn.xiaosm.cloud.front.service.impl;

import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.mapper.BucketMapper;
import cn.xiaosm.cloud.front.service.BucketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author Young
 * @create 2022/4/26
 * @since 1.0.0
 */
@Service
public class AliBucketServiceImpl extends ServiceImpl<BucketMapper, Bucket> implements BucketService {

    @Override
    public Bucket getLocalBucket() {
        return null;
    }

    @Override
    public Bucket getBucket(String name) {
        return null;
    }

    @Override
    public Bucket getBucket(Integer bucketId) {
        return null;
    }

    @Override
    public Bucket getBucket(String name, Integer userId) {
        return null;
    }

    @Override
    public File transformBucketToFile(Bucket bucket) {
        return null;
    }

    @Override
    public boolean hasBucket(String name) {
        return false;
    }
}