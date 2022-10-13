package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.Share;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.exception.ShareException;
import cn.xiaosm.cloud.front.mapper.ResourceMapper;
import cn.xiaosm.cloud.front.mapper.ShareMapper;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.service.ShareService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
@Service
public class ShareServiceImpl extends ServiceImpl<ShareMapper, Share> implements ShareService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ResourceService resourceService;
    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ShareMapper shareMapper;

    @Override
    public ShareDTO create(ShareDTO dto) {
        // 根据 id 查询当前登录用户的资源
        List<Resource> resourceList = resourceService.getByCurrentUser(dto.getResourceIds());
        Assert.notEmpty(resourceList, () -> new CanShowException("资源不存在"));
        Share share = new Share();
        share.setDeadline(dto.getDeadline());
        share.setUserId(SecurityUtils.getLoginUserId());
        share.setResourceIds(dto.getResourceIds());
        if (StrUtil.isBlank(dto.getPassword())) {
            // 随机生成四位密码
            share.setPassword(RandomUtil.randomString(4));
        } else {
            // 使用自定义密码
            share.setPassword(dto.getPassword());
        }
        int i = shareMapper.insert(share);
        Assert.isTrue(i == 1,() -> new ShareException("资源分享失败"));
        dto.setId(share.getId());
        dto.setPassword(share.getPassword());
        return dto;
    }

    private Share getByIdAndDeadline(ShareDTO dto) {
        // 获取当前分享
        Share db = shareMapper.selectById(dto.getId());
        Assert.isTrue(null != db, () -> new ShareException("当前分享的资源在地球找不到啦！"));
        // 不是永久资源，且资源已过期 now - deadline == -1 表示没过期
        if (db.getDeadline() != null && LocalDateTime.now().compareTo(db.getDeadline()) != -1) {
            throw new ShareException("当前分享链接已过期！");
        }
        return db;
    }

    @Override
    public Share checkPass(ShareDTO dto) {
        Share db = this.getByIdAndDeadline(dto);
        // 分享有密码，但是未提供密码，或密码不正确
        if (StrUtil.isNotBlank(db.getPassword()) && !db.getPassword().equals(dto.getPassword())) {
            throw new ShareException("密码错误");
        }
        return db;
    }

    @Override
    public ShareDTO info(ShareDTO shareDTO) {
        Share db = this.getByIdAndDeadline(shareDTO);
        // 根据 ids 获取资源信息
        // 获取当前分享下所有文件和一级目录
        List<Resource> resourceList = resourceService.listByIds(db.getResourceIds());
        if (StrUtil.isBlank(shareDTO.getPath()) || "/".equals(shareDTO.getPath())) {

        } else {
            Integer parentId = this.getResourceIdByPath(shareDTO.getPath(), resourceList);
            resourceList = resourceMapper.listByParentId(parentId);
        }
        ShareDTO dto = new ShareDTO();
        BeanUtil.copyProperties(db, dto);
        dto.setResourceList(resourceList);
        return dto;
    }

    @Override
    public ResourceDTO download(ShareDTO shareDTO) {
        Share db = this.getByIdAndDeadline(shareDTO);
        Resource resource;
        if (StrUtil.isBlank(shareDTO.getPath())) {
            // 如果不包含此资源
            Assert.isFalse(ArrayUtil.contains(db.getResourceIds().split(","), shareDTO.getId()),
                () -> new ShareException("当前分享的资源在地球找不到啦！"));
            resource = resourceMapper.selectById(shareDTO.getResourceId());
        } else {
            List<Resource> resourceList = resourceService.listByIds(db.getResourceIds());
            Integer parentId = this.getResourceIdByPath(shareDTO.getPath(), resourceList);
            resource = resourceMapper.selectByParentAndIdNotDir(parentId, shareDTO.getResourceId());
        }
        if (null == resource) {
            throw new ShareException("当前分享的资源在地球找不到啦！");
        }
        File file = resourceService.getLocalFile(resource);
        if (!file.exists()) throw new ShareException("当前分享的源文件已被删除！");;
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setName(resource.getName());
        resourceDTO.setFileAbPath(file.getAbsolutePath());
        return resourceDTO;
    }

    @Override
    public ShareDTO shortUrl(ShareDTO shareDTO) {
        Share share = shareMapper.selectById(shareDTO.getId());
        if (StrUtil.isBlank(share.getShortCode())) {

        }
        shareDTO.setShortCode(share.getShortCode());
        return shareDTO;
    }

    /**
     * 根据全路径获取资源的 id
     * @param fullPath
     * @param resourceList 根路径下所有文件
     * @return
     */
    private Integer getResourceIdByPath(String fullPath, List<Resource> resourceList) {
        // 暂时先使用java循环来找进入文件夹叭
        if (fullPath.startsWith("/")) fullPath = fullPath.substring(1);
        String[] path = fullPath.split("/");
        // 获取到基准目录的 id
        Integer parentId = null;
        Integer bucketId = null;
        for (Resource resource : resourceList) {
            if (path[0].equals(resource.getName())) {
                parentId = resource.getId();
                bucketId = resource.getBucketId();
                break;
            }
        }
        if (null == parentId) {
            throw new ResourceException(path[0] + "-资源不存在");
        }
        for (int i = 1; i < path.length; i++) {
            if ("".equals(path[i])) continue;
            parentId = resourceMapper.selectIdByBucketAndNameAndDir(bucketId, parentId, path[i]);
            if (null == parentId) throw new ResourceException(path[i] + "-资源不存在");
        }
        return parentId;
    }

    /**
     * 获取分享里的当个资源信息
     * @return
     */
    public ResourceDTO preview(String shareUUID) {
        // 获取资源信息
        // Resource resource = resourceMapper.selectByUUIDFromShare(resourceDTO.getUuid(), resourceDTO.getUserId());
        // 如果文件过大，则不进行预览
        return null;
    }

    public boolean hasResource(Integer resourceId) {

        return true;
    }

}