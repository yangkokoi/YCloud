package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.User;
import cn.xiaosm.cloud.core.entity.UserLoginTrack;
import cn.xiaosm.cloud.core.entity.dto.UserDTO;
import cn.xiaosm.cloud.core.entity.enums.UserOpenType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户登录查询的 sql
     * 返回数据包括用户基本信息、所对应的角色信息和最近1条登录记录
     * @param user
     * @return UserDTO
     */
    @Select("SELECT u.* " +
            " FROM `user` u " +
            " WHERE u.username = #{username} AND u.password = #{password}")
    @Results(id = "userRoleMap", value = {
            // 配置id 避免无法注入进id属性
            @Result(id = true, property = "id", column = "id"),
            // 查询角色信息
            @Result(property = "roles", column = "id",
                    many = @Many(select = "cn.xiaosm.cloud.core.mapper.RoleMapper.selectIdAndNameByUserId")),
            // 查询用户登录足迹
            @Result(property = "userLoginTracks", column = "id",
                    many = @Many(select = "cn.xiaosm.cloud.core.mapper.UserMapper.selectUserTrackByUserId"))
    })
    UserDTO selectByUsernameAndPassword(User user);

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    @Select("SELECT u.* FROM `user` u " +
            " WHERE u.username = #{username}")
    @ResultMap(value = "userRoleMap")
    UserDTO selectByUsername(String username);

    /**
     * qq快捷登录
     * @param openId
     * @param type
     * @return
     */
    @Select("SELECT u.* FROM `user` u LEFT JOIN `user_open` o ON u.id = o.user_id" +
            " WHERE o.type = #{type} AND o.open_id = #{openId}")
    @ResultMap(value = "userRoleMap")
    UserDTO selectByOpenId(@Param("openId") String openId, @Param("type") UserOpenType type);

    /**
     * 查询用户最近 1 条登录记录
     * @param userId 用户id
     * @return
     */
    @Select("SELECT * FROM user_login_track WHERE user_id = #{userId} ORDER BY login_time DESC LIMIT 1")
    UserLoginTrack selectUserTrackByUserId(@Param("userId") Integer userId);

    /**
     * 将用户状态改为已删除
     * @param userId
     * @return
     */
    @Update("UPDATE `user` SET `status` = 2 WHERE `id` = #{userId}")
    int updateUserStatusToDeleted(Integer userId);

    /**
     * 删除所有的用户角色信息
     * @param userId
     * @return
     */
    @Delete("DELETE FROM `user_role` WHERE `user_id` = #{userId}")
    int deleteUserRole(Integer userId);

    /**
     * 插入用户角色信息
     * @param userId
     * @param roleId
     * @return
     */
    @Insert("INSERT INTO `user_role`(`user_id`, `role_id`) VALUES (#{userId}, #{roleId})")
    int insertUserRole(Integer userId, Integer roleId);

    /**
     * 查询用户登陆足迹
     * @param userId
     * @param size
     * @return
     */
    @Select("SELECT * FROM `user_login_track` WHERE `user_id` = #{userId} ORDER BY `login_time` DESC LIMIT 0,#{size}")
    List<UserLoginTrack> selectUserTrack(Integer userId, Integer size);

    /**
     * 记录用户登录记录
     * @param track
     * @return
     */
    @Insert("INSERT INTO `user_login_track`(`user_id`, `login_ip`, `login_time`) VALUES (#{userId}, #{loginIp}, #{loginTime})")
    int insertUserTrack(UserLoginTrack track);

}