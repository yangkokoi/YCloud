package cn.xiaosm.cloud.front.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.core.annotation.LogRecord;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.dto.UploadDTO;
import cn.xiaosm.cloud.front.entity.vo.ResourceVO;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Api("resource")
public class ResourceController {

    @Autowired
    ResourceService resourceService;

    /**
     * 获取文件信息
     * @param resource
     * @return
     */
    @GetMapping
    public RespBody getResource(ResourceDTO resource) {
        if (StrUtil.isBlank(resource.getBucketName())) return RespUtils.success("仓库名称不可以为空");
        List<Resource> list = resourceService.list(resource);
        return RespUtils.success(list);
    }

    /**
     * 文件、目录创建
     * @param resource
     * @return
     */
    @PostMapping("create")
    public RespBody create(@RequestBody ResourceDTO resource) {
        if (StrUtil.isBlank(resource.getBucketName())) return RespUtils.fail("仓库名称不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("文件名不可以为空");
        return RespUtils.success( "文件【" + resource.getName() + "】创建成功", resourceService.create(resource));
    }

    /**
     * 文件重命名
     * @param resource
     * @return
     */
    @PostMapping("rename")
    public RespBody rename(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("资源id不可以为空");
        if (StrUtil.isBlank(resource.getName())) return RespUtils.fail("资源文件名不可以为空");
        return RespUtils.success(resourceService.rename(resource));
    }

    @PostMapping("copy")
    public RespBody copy(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("源资源id不可以为空");
        if (null == resource.getTargetId()) return RespUtils.fail("目标资源id不可以为空");
        return RespUtils.success(resourceService.copy(resource.getId(), resource.getTargetId()) ? "复制成功" : "复制失败");
    }

    @PostMapping("move")
    public RespBody move(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("源资源id不可以为空");
        if (null == resource.getTargetId()) return RespUtils.fail("目标资源id不可以为空");
        return RespUtils.success(resourceService.copy(resource.getId(), resource.getTargetId()) ? "保存成功" : "保存失败");
    }

    /**
     * 文件重命名
     * @param resource
     * @return
     */
    @PostMapping("saveContent")
    public RespBody saveContent(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("资源id不可以为空");
        if (resource.getContent() == null) {
            resource.setContent("");
        }
        return RespUtils.success(resourceService.saveContent(resource) ? "保存成功" : "保存失败");
    }

    /**
     * 资源删除
     * @param resource
     * @return
     */
    @RequestMapping("delete")
    public RespBody delete(@RequestBody ResourceVO resource) {
        ResourceDTO dto = new ResourceDTO();
        dto.setId(resource.getId());
        return resourceService.delete(dto) ? RespUtils.success() : RespUtils.fail();
    }

    @PostMapping("upload")
    @LogRecord("文件上传")
    public RespBody upload(UploadDTO uploadDTO) {
        if (CollectionUtil.isEmpty(uploadDTO.getFiles())) return RespUtils.fail("上传文件不可以为空");
        resourceService.upload(uploadDTO);
        return RespUtils.success("文件上传成功");
    }

    /**
     * 文件预下载处理
     * @param resource
     * @return
     */
    @PostMapping("pre_download")
    // @PreAuthorize("hasRole('ROLE_normal')")
    public RespBody makeDownload(
        @RequestBody ResourceDTO resource) {
        if (null == resource.getId()) return RespUtils.fail("文件ID不可以为空");
        resource = resourceService.download(resource);
        String uuid = IdUtil.simpleUUID();
        // 3 分钟后过期
        CacheUtils.set(uuid, resource, DateUnit.MINUTE.getMillis() * 3);
        return RespUtils.success("OK", uuid);
    }

}