package cn.xiaosm.cloud.core.config.security;

import cn.xiaosm.cloud.common.exception.LoginException;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
public class SecurityUtils {

    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new LoginException("登录信息过期");
        }
    }

    public static Integer getLoginUserId() {
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