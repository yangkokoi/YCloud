package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.common.exception.LoginException;
import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.security.DefaultSecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
public class SecurityUtils extends DefaultSecurityUtils {

    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new LoginException("登录信息过期");
        }
    }

    public static Long getLoginUserId() {
        return SecurityUtils.getLoginUser().getId();
    }

    public static Authentication getAuthentication() {
        try {
            return SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception e) {
            throw new LoginException("登录信息过期");
        }
    }

}