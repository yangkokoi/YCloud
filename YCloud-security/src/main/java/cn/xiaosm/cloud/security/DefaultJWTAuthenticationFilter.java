/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: JWTAuthenticationFilter
 * Author:   Young
 * Date:     2020/6/16 21:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.service.DefaultTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Log4j2
@Component("defaultJWTAuthenticationFilter")
public class DefaultJWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private DefaultTokenService tokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头中的token
        String token = tokenService.getToken(request);
        if (StrUtil.isNotBlank(token) && tokenService.verifyToken(token)) {
            AuthUser authUser = tokenService.getAuthUser(request);
            log.info("一次授权的请求 => {}", request.getRequestURI());
            // 设置空权限
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser, token, authUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else if (StrUtil.isNotBlank((token = request.getParameter("token"))) && DefaultSecurityUtils.verifyUUIDToken(token)) {
            /**
             * 获取请求参数中的token
             *
             * 注意！！！
             * 请求参数中的token和请求头中的token权限是不一样的
             */
            // 设置预览权限
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(CacheUtils.get(token), token, DefaultSecurityUtils.getDefaultAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            log.info("{}未授权，请先登录 => {}", ServletUtil.getClientIP(request), request.getRequestURI());
        }
        chain.doFilter(request, response);
    }

}