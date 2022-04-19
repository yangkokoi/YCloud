/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OtherConfig
 * Author:   Young
 * Date:     2020/6/14 19:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * 〈其他配置〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@Configuration
public class BeanConfig {

    /**
     * 访问拒绝处理器
     * @return
     */
    @Bean("accessDeniedHandlerImplOne")
    AccessDeniedHandlerImpl accessDeniedHandlerImpl() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * 密码加密处理器
     * @return
     */
    @Bean("bCryptPasswordEncoder")
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}