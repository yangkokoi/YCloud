package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.common.factory.YamlSourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Young
 * @create 2021/3/13
 * @since 1.0.0
 */
@Data
@Component
@PropertySource(value = "classpath:app-config.yml", factory = YamlSourceFactory.class)
@ConfigurationProperties(prefix = "web")
public class AppConfig {

    private Map<String, String> html = new HashMap<String, String>();

}