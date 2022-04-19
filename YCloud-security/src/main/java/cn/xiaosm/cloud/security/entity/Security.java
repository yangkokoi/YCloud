package cn.xiaosm.cloud.security.entity;

import lombok.Data;
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
public class Security {

    private Map<String, String> html = new HashMap<String, String>();

}