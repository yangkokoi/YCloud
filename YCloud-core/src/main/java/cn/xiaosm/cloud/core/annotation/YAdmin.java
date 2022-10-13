/**
 * Copyright: 2019-2020
 * FileName: logRecord
 * Author:   Young
 * Date:     2020/6/21 9:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈一句话功能简述〉
 * 被修饰的类将会别识别为Yadmin的文件
 *
 * @author Young
 * @create 2020/6/21
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping
public @interface YAdmin {
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