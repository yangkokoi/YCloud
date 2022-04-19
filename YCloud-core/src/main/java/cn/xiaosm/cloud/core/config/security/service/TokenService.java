package cn.xiaosm.cloud.core.config.security.service;

import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.service.DefaultTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
@Log4j2
@Component("tokenService")
public class TokenService extends DefaultTokenService {

    /**
     * 创建 Token
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser) {
        String token = super.createToken(loginUser.getUuid());
        // 先通过旧的UUID删除内存中的登录信息，如果存在
        CacheUtils.del(loginUser.getUuid());
        // 保存登录信息到内存
        CacheUtils.set(loginUser.getUuid(), loginUser, super.getEXPIRES());
        return token;
    }

    public LoginUser getLoginUser() {
        return this.getLoginUser(ServletUtils.getRequest());
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        AuthUser authUser = super.getAuthUser(request);
        if (authUser instanceof LoginUser) return (LoginUser) authUser;
        return null;
    }

}