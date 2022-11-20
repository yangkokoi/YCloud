/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuMapper
 * Author:   Young
 * Date:     2020/6/14 14:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT * FROM menu WHERE parent_menu = 0")
    @Results(id = "menuMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "children", column = "id",
                    many = @Many(select = "MenuMapper.findAllByParentId"))
    })
    List<Menu> findAll();

    /**
     * 根据父级 id 查询菜单
     * @param parentId 父级 id，0表示顶级菜单
     * @return
     */
    @Select("SELECT * FROM menu WHERE parent_menu_id = ${parentId}")
    List<Menu> selectAllByParentId(Integer parentId);

    /**
     * 通过角色 id 查询所有菜单
     * 以树形结构存储，影响数据库效率，弃用
     * @param roleId
     * @return
     */
    @Select(value = "")
    @Results(id = "menuMapByRoleId", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "children", column = "{roleId = r.id, parentId = id}",
                many = @Many(select = "MenuMapper.selectAllByRoleIdAndParentId",
                        fetchType = FetchType.LAZY))
    })
    @Deprecated
    List<Menu> selectAllByRoleIdOfTree(String roleId);

    @Select("SELECT * FROM `role_menu` rm LEFT JOIN `menu` m ON rm.menu_id = m.id LEFT JOIN `role` r ON rm.role_id = r.id where r.id = ${roleId}")
    List<Menu> selectAllByRoleId(Integer roleId);

    /**
     * 通过角色 id 和父级菜单 id 查询所有菜单
     * @param roleId
     * @param parentId
     * @return
     */
    @Select("")
    @ResultMap("menuMapByRoleId")
    List<Menu> selectAllByRoleIdAndParentId(String roleId, Integer parentId);

}