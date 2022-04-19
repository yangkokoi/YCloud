package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.core.service.BaseService;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.vo.BucketVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface BucketService extends BaseService<Bucket> {


    List<Resource> intoPath(Bucket bucket);

    Bucket getMyBucket(String name);

    Bucket getMyBucket(String name, Integer userId);

    File transformBucketToFile(Bucket bucket);

    boolean hasBucket(String name);
}