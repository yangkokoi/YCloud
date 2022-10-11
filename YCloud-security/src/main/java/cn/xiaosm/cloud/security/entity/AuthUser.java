package cn.xiaosm.cloud.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Young
 * @create 2021/8/31
 * @since 1.0.0
 */
public interface AuthUser extends UserDetails {

    @JsonIgnore
    TokenType tokenType = null;
    String username = null;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password = null;
    Collection<? extends GrantedAuthority> authorities = null;

    default TokenType getTokenType() {
        return tokenType;
    }

}