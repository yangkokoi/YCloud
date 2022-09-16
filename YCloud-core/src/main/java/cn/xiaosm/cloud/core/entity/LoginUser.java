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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public class LoginUser extends User implements AuthUser {

    @JsonIgnore
    private String loginId;
    @JsonIgnore
    private String password;
    private String roleIds;
    private List<Role> roles;
    private List<? extends Menu> menus; // 树结构的菜单列表
    private Map<Integer, Menu> menusOriginal; // 源菜单列表
    private List<UserLoginTrack> userLoginTracks;
    private Collection<? extends GrantedAuthority> authorities;


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

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LoginUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public LoginUser setRoleIds(String roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public LoginUser setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<? extends Menu> getMenus() {
        return menus;
    }

    public LoginUser setMenus(List<? extends Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Map<Integer, Menu> getMenusOriginal() {
        return menusOriginal;
    }
    @JsonIgnore
    public List<Menu> getMenusOriginalOfList() {
        List<Menu> menus = new ArrayList<>();
        menusOriginal.forEach((id, menu) -> menus.add(menu));
        return menus;
    }

    public void setMenusOriginal(List<Menu> menusOriginal) {
        if (this.menusOriginal == null) this.menusOriginal = new HashMap<>();
        for (Menu menu : menusOriginal) {
            this.menusOriginal.put(menu.getId(), menu);
        }
    }

    public List<UserLoginTrack> getUserLoginTracks() {
        return userLoginTracks;
    }

    public LoginUser setUserLoginTracks(List<UserLoginTrack> userLoginTracks) {
        this.userLoginTracks = userLoginTracks;
        return this;
    }

    public LoginUser setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }
}