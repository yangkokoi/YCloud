package cn.xiaosm.cloud.security;

import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
public class DefaultSecurityUtils {

    private static ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>() {{
        add(new SimpleGrantedAuthority("resource:preview"));
    }};

    public static ArrayList<SimpleGrantedAuthority> getDefaultAuthorities() {
        return authorities;
    }

    public static boolean verifyUUIDToken(String uuidToken) {
        return !Objects.isNull(CacheUtils.get(uuidToken));
    }

}