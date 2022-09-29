package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.Share;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;
import cn.xiaosm.cloud.front.exception.ShareException;
import cn.xiaosm.cloud.front.mapper.ShareMapper;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.service.ShareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        dto.setUuid(share.getUuid());
        dto.setPassword(share.getPassword());
        return dto;
    }

    private Share getByUUIDAndDeadline(ShareDTO dto) {
        // 获取当前分享
        Share db = shareMapper.selectById(dto.getUuid());
        Assert.isTrue(null != db, () -> new ShareException("当前分享的资源在地球找不到啦！"));
        // 不是永久资源，且资源已过期 now - deadline == -1 表示没过期
        if (db.getDeadline() != null && LocalDateTime.now().compareTo(db.getDeadline()) != -1) {
            throw new ShareException("当前分享链接已过期！");
        }
        return db;
    }

    @Override
    public Share checkPass(ShareDTO dto) {
        Share db = this.getByUUIDAndDeadline(dto);
        // 分享有密码，但是未提供密码，或密码不正确
        if (StrUtil.isNotBlank(db.getPassword()) && !db.getPassword().equals(dto.getPassword())) {
            throw new ShareException("密码错误");
        }
        return db;
    }

    @Override
    public ShareDTO info(ShareDTO share) {
        Share db = this.getByUUIDAndDeadline(share);
        // 根据 ids 获取资源信息
        List<Resource> resourceList = resourceService.listByIds(db.getResourceIds());
        ShareDTO dto = new ShareDTO();
        BeanUtil.copyProperties(db, dto);
        dto.setResourceList(resourceList);
        return dto;
    }

}