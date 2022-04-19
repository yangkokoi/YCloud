package cn.xiaosm.cloud.front.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.vo.BucketVO;
import cn.xiaosm.cloud.front.service.BucketService;
import cn.xiaosm.cloud.front.service.ResourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Log4j2
@Api(path = "bucket")
public class BucketController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    BucketService bucketService;
    private final String LOCAL_PREFIX = "/bucket/local";

    @GetMapping("{bucketName}/**")
    public RespBody intoDir(@PathVariable("bucketName") String bucketName,
                            HttpServletRequest request) {
        if (StrUtil.isBlank(bucketName)) return RespUtils.success("仓库名称不可以为空");
        BucketVO bucket = new BucketVO();
        bucket.setCurrentPath(getUri(request));
        bucket.setName(bucketName);
        List<Resource> list = bucketService.intoPath(bucket);
        return RespUtils.success(list);
    }

    public String getUri(HttpServletRequest request) {
        // 从地址获取路径
        // 获取 URI 地址上的路径
        String uri = request.getRequestURI();
        uri = uri.substring(uri.indexOf(LOCAL_PREFIX) + LOCAL_PREFIX.length());
        return URLUtil.decode(uri);
    }

}