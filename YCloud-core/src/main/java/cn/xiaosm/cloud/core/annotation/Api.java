package cn.xiaosm.cloud.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Young
 * @create 2022/3/23
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
public @interface Api {
    @AliasFor(
        value = "path",
        annotation = RequestMapping.class
    )
    String[] value() default {};

    @AliasFor(
        value = "value",
        annotation = RestController.class
    )
    String name() default "";
}