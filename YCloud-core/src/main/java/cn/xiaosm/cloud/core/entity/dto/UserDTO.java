/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserDTO
 * Author:   Young
 * Date:     2020/6/14 9:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.Role;
import cn.xiaosm.cloud.core.entity.User;
import cn.xiaosm.cloud.core.entity.UserLoginTrack;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
public class UserDTO extends User implements Serializable {

    @JsonIgnore
    private String password;
    private String roleIds;
    private List<Role> roles;
    private List<Menu> menus;
    private List<UserLoginTrack> userLoginTracks;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public UserDTO setRoleIds(String roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public UserDTO setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public UserDTO setMenus(List<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public List<UserLoginTrack> getUserLoginTracks() {
        return userLoginTracks;
    }

    public UserDTO setUserLoginTracks(List<UserLoginTrack> userLoginTracks) {
        this.userLoginTracks = userLoginTracks;
        return this;
    }
}