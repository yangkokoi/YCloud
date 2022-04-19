/**
 * Copyright: 2019-2020
 * FileName: QQAuth
 * Author:   Young
 * Date:     2020/12/3 19:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.oauth;

import cn.xiaosm.cloud.common.factory.YamlSourceFactory;
import cn.xiaosm.cloud.core.entity.enums.UserOpenType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/12/3
 * @since 1.0.0
 */
@Data
@Component
@PropertySource(value = "classpath:security.yml", factory = YamlSourceFactory.class)
@ConfigurationProperties(prefix = "oauth.qq")
public class QQAuth {

    private String appid;
    private String appkey;
    private String callback;
    private UserOpenType type = UserOpenType.QQ;

}