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
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
@Component("defaultSessionAuthenticationFilter")
public class DefaultSessionAuthenticationFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        AuthUser user = null;
        if (request.getRequestURI().startsWith("/admin") && (user = (AuthUser) request.getSession().getAttribute("USER")) != null) {
            // 获取请求头中的token
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, user.getId(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }

}