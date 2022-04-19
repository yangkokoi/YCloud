/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: RoleMapper
 * Author:   Young
 * Date:     2020/6/14 10:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.Role;
import cn.xiaosm.cloud.core.entity.dto.RoleDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户 id 查询角色信息
     * @param userId
     * @return
     */
    @Select("SELECT r.* FROM role r " +
            " LEFT JOIN user_role ur ON ur.role_id = r.id" +
            " LEFT JOIN `user` u ON ur.user_id = u.id" +
            " WHERE u.id = #{userId}")
    List<Role> selectByUserId(Integer userId);

    /**
     * 通过用户id查询角色id和名称
     * 在 UserMapper 中调用
     * @param userId
     * @return
     */
    @Select("SELECT r.id, r.name FROM `role` r " +
            " LEFT JOIN `user_role` ur ON ur.role_id = r.id" +
            " LEFT JOIN `user` u ON ur.user_id = u.id" +
            " WHERE u.id = #{userId}")
    List<Role> selectIdAndNameByUserId(Integer userId);

    @Select("SELECT * FROM `role` WHERE `role`.user_id = #{userId}")
    RoleDTO findIncludeMenuByUserId(Integer userId);

    /**
     * 清除角色权限
     *
     * @param roleId
     * @return
     */
    @Delete("DELETE FROM `role_menu` WHERE `role_id` = #{roleId}")
    int clearRoleMenu(Integer roleId);

    @Insert("INSERT INTO `role_menu`(`role_id`, `menu_id`) VALUES (#{roleId}, #{menuId})")
    int insertRoleMenu(Integer roleId, Integer menuId);
}