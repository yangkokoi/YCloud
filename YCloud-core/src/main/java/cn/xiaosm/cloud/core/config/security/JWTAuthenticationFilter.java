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
package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.security.entity.AuthUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Log4j2
// @Component("JWTAuthenticationFilter")
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("tokenService")
    private TokenService tokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenService.getToken(request);
        AuthUser authUser = tokenService.getAuthUser(request);
        /*
         * 查找用户是否存在 且 Token还未过期
         */
        if (tokenService.verifyToken(token) && Objects.nonNull(authUser)) {
            log.info("一次授权的请求 => {}", request.getRequestURI());
            /*
              创建 UsernamePasswordAuthenticationToken 对象，设置到 SecurityContextHolder 中
              如果没有此段代码，security将不不会允许即使有 token 的请求
              主要用于方法权限认证
            */
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authUser, token, authUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            /* ******  很重要的代码  ****** */
        } else {
            // log.info("{}未授权，请先登录 => {}", ServletUtil.getClientIP(request), request.getRequestURI());
        }
        chain.doFilter(request, response);
    }

}