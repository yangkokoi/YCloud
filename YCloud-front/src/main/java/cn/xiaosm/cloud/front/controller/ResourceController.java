package cn.xiaosm.cloud.front.controller;

import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SecureUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.core.annotation.LogRecord;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.UploadVO;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.util.DownloadUtil;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Api(path = "resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    @PostMapping("create/{bucketName}")
    public RespBody create(
        @PathVariable String bucketName,
        @RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(bucketName)) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        resource.setBucketName(bucketName);
        return RespUtils.success(resourceService.create(resource));
    }

    @PostMapping("rename/{bucketName}")
    public RespBody rename(
        @PathVariable String bucketName,
        @RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(bucketName)) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        resource.setBucketName(bucketName);
        return RespUtils.success(resourceService.rename(resource));
    }

    @RequestMapping("delete/{bucketName}")
    public RespBody delete(@PathVariable String bucketName, @RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(bucketName)) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        resource.setBucketName(bucketName);
        return resourceService.delete(resource) ? RespUtils.success() : RespUtils.fail();
    }

    @PostMapping("upload")
    @LogRecord("文件上传")
    public RespBody multiUpload(UploadVO uploadVO) {
        if (CollectionUtil.isEmpty(uploadVO.getFiles())) return RespUtils.fail("上传文件不可以为空");
        resourceService.upload(uploadVO);
        return RespUtils.success("文件上传成功");
    }

    @PostMapping("pre_download/{bucketName}")
    public RespBody makeDownload(
        @PathVariable("bucketName") String bucketName,
        @RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(bucketName)) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        resource.setBucketName(bucketName);
        resource = resourceService.download(resource);
        if (Objects.isNull(resource)) {
            return RespUtils.fail("文件不存在");
        }
        String uuid = IdUtil.simpleUUID();
        // 3 分钟后过期
        CacheUtils.set(uuid, resource, DateUnit.MINUTE.getMillis() * 3);
        return RespUtils.success("OK", uuid);
    }

    @AnonymousAccess
    @GetMapping("download")
    public Object download(
        @RequestParam("entry") String entry,
        HttpServletRequest request,
        HttpServletResponse response) {
        ResourceDTO resource = (ResourceDTO) CacheUtils.get(entry, true);
        if (resource == null)
            return RespUtils.fail("资源已过期");
        File file = new File(resource.getFileAbPath());
        if (!file.exists())
            return RespUtils.fail("资源已被删除");
        response.reset();
        // 设置文件名
        response.setHeader("Content-Disposition",
            "attachment;fileName=" + URLUtil.encode(file.getName(), "UTF-8"));
        DownloadUtil.outputData(request, response, file);
        return null;
    }


}