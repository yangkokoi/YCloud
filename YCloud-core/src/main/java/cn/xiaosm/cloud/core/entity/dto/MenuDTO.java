/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuDTO
 * Author:   Young
 * Date:     2020/6/15 15:49
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.core.entity.Menu;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
public class MenuDTO extends Menu {

    private boolean hasChildren = true;
    @JsonIgnore
    private Menu parent; // 父菜单 未使用
    private List<MenuDTO> children = new ArrayList<>(); // 子菜单

    public MenuDTO() {}

    public MenuDTO(Integer id, String name) {
        super(id, name);
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public Menu getParent() {
        return parent;
    }

    public MenuDTO setParent(Menu parent) {
        this.parent = parent;
        return this;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public MenuDTO setChildren(List<MenuDTO> children) {
        this.children = children == null ? new ArrayList<>() : children;
        // 设置是否包含子菜单
        this.hasChildren = !children.isEmpty();
        return this;
    }
}