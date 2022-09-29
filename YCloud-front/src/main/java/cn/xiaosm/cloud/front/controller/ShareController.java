package cn.xiaosm.cloud.front.controller;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.front.entity.Share;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;
import cn.xiaosm.cloud.front.entity.vo.ShareVO;
import cn.xiaosm.cloud.front.service.ShareService;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Api("share")
public class ShareController {

    @Autowired
    ShareService shareService;
    @Autowired
    TokenService tokenService;

    @RequestMapping("create")
    public RespBody create(@RequestBody @Validated ShareDTO shareDTO) {
        ShareDTO dto = shareService.create(shareDTO);
        ShareVO vo = new ShareVO(dto);
        return RespUtils.success(vo);
    }

    /**
     * 获取需要访问分享资源的 Token
     */
    @RequestMapping("pass")
    @AnonymousAccess
    public RespBody pass(@RequestBody ShareDTO dto, HttpServletRequest request, HttpServletResponse response) {
        if (StrUtil.isBlank(dto.getUuid())) return RespUtils.fail("当前分享的资源在地球找不到啦！");
        // 如果当前token还没有过期，直接返回
        String token = tokenService.getToken(request);
        if (dto.getUuid().equals(tokenService.getClaim(token, "shareId"))) {
            RespUtils.sendToken(response, token);
            return null;
        }
        Share share = shareService.checkPass(dto);
        if (null != share) {
            RespUtils.sendToken(response, tokenService.createShareToken(share.getUuid()));
            return null;
        }
        return RespUtils.fail("当前分享资源暂不可访问");
    }

    @RequestMapping("info")
    @PreAuthorize("hasRole('ROLE_share')")
    public RespBody preview(@RequestBody ShareDTO share) {
        if (StrUtil.isBlank(share.getUuid())) return RespUtils.fail("当前分享的资源在地球找不到啦！");
        ShareDTO dto = shareService.info(share);
        return RespUtils.success(new ShareVO(dto));
    }

    @RequestMapping("download")
    public void download() {

        return;
    }

}