/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: AdminController
 * Author:   Young
 * Date:     2020/6/13 20:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.YAdmin;
import cn.xiaosm.cloud.core.config.security.service.UserDetailsServiceImpl;
import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.core.entity.dto.LoginUserDTO;
import cn.xiaosm.cloud.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@YAdmin
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManagerBuilder managerBuilder;

    /**
     * 登录页
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("USER") != null) {
            response.sendRedirect("/admin");
            return null;
        }
        return "login";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("USER") != null) {
            request.getSession().invalidate();
        }
        response.sendRedirect("/admin/login");
    }

    /**
     * 登录处理
     * @param user
     * @param request
     * @return 响应 JSON
     */
    @PostMapping("/api/login")
    @ResponseBody
    public RespBody login(LoginUserDTO user, HttpServletRequest request) {
        /* START
          该方法会去调用 UserDetailService 的方法
          由于我创建了此类的实现类，所以会去调用我自定义的登录逻辑，从后台获取 User 的信息
          密码的校验 security 会帮我们处理，如果密码错误，会抛出异常
         */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = managerBuilder
                .getObject().authenticate(authenticationToken);
        /* END */
        // 如果密码没有输入错误，将能够正确执行以下代码
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 暂时只允许 id 为1的登录
        if (!loginUser.getId().equals(1l)) {
            return RespUtils.fail("请求拒绝");
        }
        // 设置登录用户信息（用户的权限和菜单列表）
        userDetailsService.loadUserInfo(loginUser);
        // 记录登录足迹
        userService.addLoginTrack(loginUser.getId(), ServletUtil.getClientIP(request));
        request.getSession().setAttribute("USER", loginUser);
        return RespUtils.success();
    }

}