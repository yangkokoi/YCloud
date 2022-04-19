/**
 * Copyright: 2019-2020
 * FileName: RoleVO
 * Author:   Young
 * Date:     2020/6/21 18:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.vo;

import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.core.entity.Role;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/21
 * @since 1.0.0
 */
public class RoleVO extends Role {

    @TableField(exist = false)
    private Integer userId;
    // @TableField(exist = false)
    private Set<Integer> menuIds;

    public Integer getUserId() {
        return userId;
    }

    public RoleVO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Set<Integer> getMenuIds() {
        return menuIds;
    }

    public RoleVO setMenuIds(Set<Integer> menuIds) {
        this.menuIds = menuIds;
        return this;
    }

    @Override
    public Role setId(Integer id) {
        if (id == 1) throw new SQLOperateException("系统保留数据，请勿操作");
        return super.setId(id);
    }
}