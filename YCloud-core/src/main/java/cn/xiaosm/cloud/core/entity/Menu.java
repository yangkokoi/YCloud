/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: Menu
 * Author:   Young
 * Date:     2020/6/14 14:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.core.entity.enums.MenuType;
import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@TableName("menu")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class Menu extends BaseEntity implements Cloneable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name; // 菜单名称
    private MenuType type; // 菜单类型
    private Integer parentMenuId; // 上级菜单
    private String icon; // 菜单图标
    @TableField(value = "`order`")
    private int order; // 排序
    private String path; // 组件路径
    private String component; // 组件名称
    private String permission; // 权限许可
    private StatusEnum status; // 状态

    public Menu() { }

    public Menu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        // 只匹配 id
        return Objects.equals(this.id, menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permission);
    }

}