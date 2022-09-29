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
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.ShareUser;
import cn.xiaosm.cloud.security.entity.TokenType;
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
 * 所有请求都会经过这个过滤器
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
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = tokenService.getToken(request);
        if (StrUtil.isNotBlank(token) && tokenService.verifyToken(token)) {
            log.info("一次授权的请求 => {}", request.getRequestURI());
            // 获取 token 类型
            TokenType tokenType = tokenService.getType(token);
            if (tokenType.equals(TokenType.LOGIN)) {
                AuthUser authUser = tokenService.getAuthUser(request);
                if (null == authUser) {
                    chain.doFilter(request, response);
                    return;
                }
                // 设置当前用户的权限
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser, token, authUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else if (tokenType.equals(TokenType.SHARE)) {
                String shareId = tokenService.getClaim(token, "shareId");
                ShareUser shareUser = new ShareUser(shareId);
                // 设置当前用户的查看当前分享资源的权限
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(shareUser, token, shareUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } else if (StrUtil.isNotBlank((token = request.getParameter("token"))) && DefaultSecurityUtils.verifyUUIDToken(token)) {
            /**
             * 获取请求参数中的token
             *
             * 此权限暂时只拥有 文件预览
             * 注意！！！
             * 请求参数中的token和请求头中的token权限是不一样的
             */
            // 设置预览权限
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(CacheUtils.get(token), token, DefaultSecurityUtils.getDefaultAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}