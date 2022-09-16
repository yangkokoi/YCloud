package cn.xiaosm.cloud.security.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.factory.YamlSourceFactory;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.security.entity.AuthUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
@Data
@Log4j2
@Component("defaultTokenService")
@PropertySource(value = "classpath:security.yml", factory = YamlSourceFactory.class)
public class DefaultTokenService {

    // 存放 Token 的头部名称
    @Value("${spring.security.token.header}")
    private String AUTH_HEADER;
    // Token 前缀
    @Value("${spring.security.token.prefix}")
    private String TOKEN_PREFIX;
    // 用于加密的密钥
    private String SECRET_KEY;
    // Token 的 claim 信息
    private final String JWT_CLAIM_UUID = "UUID";
    // 单位 分钟
    private final int MINUTE = 60 * 1000;
    // 设置 Token 到期时间 秒
    private int EXPIRES;
    // Token 验证器
    private JWTVerifier verifier = null;


    @Value("${spring.security.token.expires}")
    public void setEXPIRES(int EXPIRES) {
        this.EXPIRES = EXPIRES * MINUTE;
    }

    @Value("${spring.security.token.secret-key}")
    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
        this.verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
    }


    /**
     * 创建 Token
     * @param
     * @return
     */
    public String createToken(String uuid) {
        String token = JWT.create()
            .withAudience(uuid)
            .withClaim(JWT_CLAIM_UUID, uuid)
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES))
            .sign(Algorithm.HMAC256(SECRET_KEY));
        return token;
    }

    /**
     * 获取请求头中的 token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        // if (Objects.isNull(token)) token = ServletUtil.getParamMap(request).get(AUTH_HEADER);
        // if (Objects.isNull(token)) token = ServletUtil.getParamMap(request).get("_token");
        return Objects.isNull(token) ? "" : token.replace(TOKEN_PREFIX, "").trim();
    }

    /**
     * 校验Token是否过期
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            CacheUtils.del(this.getUUID(token));
            // e.printStackTrace();
            log.error("Token已过期，请重新登录 >>> {}", e.getMessage());
            throw new CanShowException("Token已过期，请重新登录");
        } catch (Exception e) {
            return false;
        }
    }

    public String getUUID(String token) {
        try {
            return JWT.decode(token).getClaim(JWT_CLAIM_UUID).asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }


    /**
     * 从内存中获取 UserDetails 信息
     * @return
     */
    public AuthUser getAuthUser() {
        return this.getAuthUser(ServletUtils.getRequest());
    }

    /**
     * 从内存中获取 UserDetails 信息
     * @param request
     * @return
     */
    public AuthUser getAuthUser(HttpServletRequest request) {
        String token = this.getToken(request);
        // 获取token中的uuid
        if (StrUtil.isBlank(token)) {
            // throw new NullPointerException("Token为空");
            return null;
        }
        return (AuthUser) CacheUtils.get(this.getUUID(token));
    }

}