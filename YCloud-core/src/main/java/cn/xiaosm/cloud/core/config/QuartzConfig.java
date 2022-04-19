/**
 * Copyright: 2019-2020
 * FileName:    SchedulerConfig
 * Author:      Young
 * Date:        2020/5/19
 * Description:
 * History:
 */
package cn.xiaosm.cloud.core.config;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author
 * @create
 * @version 1.0.0
 */
@Configuration
public class QuartzConfig {

    @Bean("SchedulerFactory")
    public SchedulerFactory createSchedulerFactory() {
        return new StdSchedulerFactory();
    }

}