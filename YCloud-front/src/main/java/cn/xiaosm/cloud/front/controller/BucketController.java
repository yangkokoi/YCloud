package cn.xiaosm.cloud.front.controller;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.vo.BucketVO;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.service.impl.LocalBucketServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Log4j2
@Api("bucket")
public class BucketController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    LocalBucketServiceImpl bucketService;


}