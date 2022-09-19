/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: InterceptorConfig
 * Author:   Young
 * Date:     2020/3/8 11:32
 * Description: 拦截器配置文件
 * History:
 */
package cn.xiaosm.cloud.core.config;

import cn.xiaosm.cloud.core.factory.BaseEnumConverterFactory;
import cn.xiaosm.cloud.core.interceptor.LogInterceptor;
import cn.xiaosm.cloud.core.interceptor.MainInterceptor;
import cn.xiaosm.cloud.core.annotation.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 〈拦截器配置文件〉
 *
 * @author Young
 * @create 2020/3/8
 * @since 1.0.0
 */
@Log4j2
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 视图控制器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("forward:/index.html");
        // registry.addViewController("/login")
        //         .setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * 静态资源处理器
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:/static/upload/");
    }

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         - /**： 匹配所有路径 /**
         - /api/*：只匹配 /api下的内容，不匹配/api/v1/*
         - /api/v1/**：匹配 /api/v1/ 下的所有路径
         */
        List<String> excludePath = new LinkedList<>();
        excludePath.addAll(Arrays.asList(
            "/**/*.html",
            "/**/*.css", "/**/*.js", "/**/*.map",
            "/**/*.woff", "/**/*.ttf",
            "/**/*.png", "/**/*.jpg", "/**/*.ico",
            "/**/*.gif", "/**/*.svg"
        ));
        registry.addInterceptor(createMainInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(excludePath);
        registry.addInterceptor(createLogInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(excludePath)
            .excludePathPatterns("/login", "logout");
    }

    /**
     * 转换器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new BaseEnumConverterFactory());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
            .addPathPrefix("/api", c -> c.isAnnotationPresent(Api.class))
            // .addPathPrefix("/api", c ->  true);
        ;
    }

    @Bean
    public MainInterceptor createMainInterceptor() {
        log.info("加载Main拦截器");
        return new MainInterceptor();
    }

    @Bean
    public LogInterceptor createLogInterceptor() {
        log.info("加载日志记录拦截器");
        return new LogInterceptor();
    }

}