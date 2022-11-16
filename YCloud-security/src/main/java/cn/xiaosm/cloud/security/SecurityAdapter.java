/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: SecurityConfig
 * Author:   Young
 * Date:     2020/6/15 9:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.security;

import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import cn.xiaosm.cloud.security.handler.LoginFailHandler;
import cn.xiaosm.cloud.security.handler.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("accessDeniedHandlerImplOne")
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailHandler loginFailHandler;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CorsFilter corsFilter;
    @Autowired
    private DefaultJWTAuthenticationFilter defaultJWTAuthenticationFilter;
    @Autowired
    private DefaultSessionAuthenticationFilter defaultSessionAuthenticationFilter;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 如果某个地址无需拦截，在此进行放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers("/login");
        // web.ignoring().antMatchers("/static/*");
        // web.ignoring().antMatchers("/oauth/*");
        super.configure(web);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 配置自定义service
        builder.userDetailsService(userDetailsService)
                // 配置加密方式
                .passwordEncoder(bCryptPasswordEncoder);
        super.configure(builder);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<PathPattern> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                anonymousUrls.addAll(infoEntry.getKey().getPathPatternsCondition().getPatterns());
            }
        }

        // 禁用 CSRF 因为我不需要 session 鸭
        security.csrf().disable();
        //         // 不创建会话，因为此项目基于 Java Web Token 进行登录
        //         .sessionManagement()
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 添加跨域请求（允许）过滤器
        security.addFilter(corsFilter);
        // 添加 session 过滤器，session 过滤器通过以后，直接掠过 JWT 过滤器
        security.addFilterBefore(defaultSessionAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加 JWT 过滤器
        security.addFilterBefore(defaultJWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // security.addFilter(defaultJWTAuthenticationFilter);
        // 登录处理
        security.formLogin()
                // 登录成功调用
                .successHandler(loginSuccessHandler)
                // 登录失败调用
                .failureHandler(loginFailHandler)
                // JWT认证
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        // 退出登录处理
        security.logout()
                .logoutUrl("/api/logout")
                .clearAuthentication(false)
                .logoutSuccessHandler(logoutSuccessHandler);

        security.authorizeRequests()
            // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
            .antMatchers(anonymousUrls.stream().map(el -> el.getPatternString()).toArray(String[]::new)).permitAll();
    }

}