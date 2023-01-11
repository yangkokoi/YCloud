/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: LoginUser
 * Author:   Young
 * Date:     2020/6/16 20:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class LoginUser extends User implements AuthUser {

    private TokenType tokenType = TokenType.LOGIN;
    @JsonIgnore
    private String loginId;
    @JsonIgnore
    private String password;
    private String roleIds;
    private List<Role> roles;
    private List<? extends Menu> menus; // 树结构的菜单列表
    private List<Menu> menusOriginal; // 源菜单列表
    private List<UserLoginTrack> userLoginTracks;
    private Collection<SimpleGrantedAuthority> authorities;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public List<Menu> getMenusOriginalOfList() {
        return this.menusOriginal;
    }

    public void setMenusOriginal(List<Menu> menusOriginal) {
        if (menusOriginal == null) menusOriginal = new ArrayList<>();
        this.menusOriginal = menusOriginal.stream()
            // 去重
            .distinct()
            // 排序
            .sorted(Comparator.comparingInt(Menu::getOrder)).collect(Collectors.toList());
    }

}