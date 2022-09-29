package cn.xiaosm.cloud.security;

import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
public class DefaultSecurityUtils {

    static TokenService tokenService;

    private static Collection authorities = new ArrayList<SimpleGrantedAuthority>() {{
        add(new SimpleGrantedAuthority("resource:preview"));
    }};

    public static Collection getDefaultAuthorities() {
        return authorities;
    }

    public static boolean verifyUUIDToken(String uuidToken) {
        return !Objects.isNull(CacheUtils.get(uuidToken));
    }

}