package cn.xiaosm.cloud.security.entity;

import cn.xiaosm.cloud.security.DefaultSecurityUtils;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Young
 * @create 2022/9/29
 * @since 1.0.0
 */
@Data
public class ShareUser implements AuthUser {

    private TokenType tokenType = TokenType.SHARE;
    private String shareId;
    private Collection<SimpleGrantedAuthority> authorities;

    public ShareUser(String shareId) {
        this.shareId = shareId;
        authorities = new ArrayList<String>(){{
            add("ROLE_share");
        }}.stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        authorities.addAll(DefaultSecurityUtils.getDefaultAuthorities());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}