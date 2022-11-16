package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.security.SecurityAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * 项目安全框架，如果不需要使用可以删除以下注解或本类
 *
 * @author Young
 * @create 2021/3/24
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启spring方法级安全
public class SecurityConfig extends SecurityAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        // 放行以下路径
        web.ignoring().mvcMatchers("/test/*");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        super.configure(security);

        String[] staticPath = new String[]{
            "/*/*.html", "/*/*.css", "/*/*.js", "/*/*.map",
            "/*/*.woff", "/*/*.woff2", "/*/*.ttf",
            "/*/*.png", "/*/*.jpg", "/*/*.ico", "*.ico",
            "/*/*.gif", "/*/*.svg"
        };

        // 权限控制
        security.authorizeRequests()
            .antMatchers("/admin", "/admin/api/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/login").permitAll()
            // 放行静态资源
            .antMatchers(staticPath).permitAll()
            // 放行OPTIONS请求
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 后台请求需要认证
            .antMatchers("/admin/**").authenticated()
            // 所有API请求都需要认证
            .antMatchers("/api/**").authenticated()
            // .anyRequest().authenticated()
            .and()
            .headers().frameOptions().disable();
        // .and().apply(securityConfigurerAdapter())
    }

}