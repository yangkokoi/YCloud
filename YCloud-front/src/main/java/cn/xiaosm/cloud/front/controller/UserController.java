package cn.xiaosm.cloud.front.controller;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.Api;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Young
 * @create 2022/10/13
 * @since 1.0.0
 */
@Api("user")
public class UserController {

    @Autowired
    TokenService tokenService;

    /**
     * 根据 Token 获取当前登录用户的信息
     * @param request
     * @return
     */
    @GetMapping("info")
    public RespBody userInfo(HttpServletRequest request) {
        return RespUtils.success(tokenService.getLoginUser(request));
    }


}