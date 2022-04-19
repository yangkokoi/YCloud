package cn.xiaosm.cloud.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.core.entity.User;
import cn.xiaosm.cloud.core.entity.UserLoginTrack;
import cn.xiaosm.cloud.core.entity.UserOpen;
import cn.xiaosm.cloud.core.entity.dto.UserDTO;
import cn.xiaosm.cloud.core.entity.enums.UserOpenType;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import cn.xiaosm.cloud.core.entity.vo.UserVO;
import cn.xiaosm.cloud.core.mapper.UserMapper;
import cn.xiaosm.cloud.core.mapper.UserOpenMapper;
import cn.xiaosm.cloud.core.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserOpenMapper userOpenMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public static final String DEFAULT_PASS = "123456";

    @Override
    @Transactional
    public boolean updateById(User user) {
        // if (this.isExist(user)) throw new SQLOperateException("用户名" + user.getUsername() + "已存在");
        user.setUsername(null);
        int i = userMapper.updateById(user);
        if (Objects.nonNull( ((UserVO) user).getRoleIds() )) {
            // 先清除所有的角色信息
            this.removeUserRoles(user.getId());
            // 在进行新的插入
            this.addUserRoles(user.getId(), ((UserVO) user).getRoleIds());
        }
        return i == 1;
    }

    @Override
    @Transactional
    public boolean save(User user) {
        if (this.isExist(user)) throw new SQLOperateException("用户名" + user.getUsername() + "已存在");
        // 设置默认密码
        this.defaultPass(user);
        userMapper.insert(user);
        this.addUserRoles(user.getId(), ((UserVO) user).getRoleIds());
        return true;
    }

    public boolean isExist(@NotNull User user) {
        if (StrUtil.isBlank(user.getUsername())) return false;
        return userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("username", user.getUsername())
        ) != null;
    }

    /**
     * 修改密码，但是不进行数据库操作
     *
     * @param user
     * @return
     */
    @Override
    public boolean modPassword(User user) {
        return this.modPassword(user, user.getPassword(), false);
    }

    /**
     * 修改密码，但是不进行数据库操作
     *
     * @param user
     * @param password
     * @return
     */
    @Override
    public boolean modPassword(User user, String password) {
        return this.modPassword(user, password, false);
    }

    /**
     * 修改密码，根据keep决定是否进行数据库操作
     * @param user
     * @param password
     * @param keep
     * @return
     */
    @Override
    public boolean modPassword(User user, String password, boolean keep) {
        User newU = new User();
        newU.setId(user.getId());
        this.encodePass(user, password);
        if (keep) {
            return userMapper.updateById(user) == 1;
        }
        return true;
    }

    /**
     * 设置用户密码，这里使用默认密码
     * @param user
     */
    @Override
    public void defaultPass(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASS));
    }

    /**
     * 设置用户密码
     * 会在user.password中覆盖原有的密码
     * @param user
     * @param password
     */
    public void encodePass(User user, String password) {
        if (StrUtil.isNotBlank(password))
            user.setPassword(bCryptPasswordEncoder.encode(password));
    }

    /**
     * 删除用户的所有角色信息
     * @param userId
     */
    public void removeUserRoles(Integer userId) {
        userMapper.deleteUserRole(userId);
    }

    /**
     * 添加用户的角色
     * @param userId
     * @param roleIds
     * @return
     */
    public int addUserRoles(Integer userId, Set<Integer> roleIds) {
        int i = 0;
        for (Integer roleId : roleIds) {
            try {
                i = userMapper.insertUserRole(userId, roleId);
            } catch (Exception e) {
                throw new SQLOperateException("保存失败###选择角色不存在");
            }
        }
        return i;
    }

    @Override
    public int removeById(Integer id) {
        return userMapper.updateUserStatusToDeleted(id);
    }

    @Override
    public int removeByIds(Set<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            if (id == 1) {
                throw new SQLOperateException("系统保留数据，请勿操作");
            }
            count += this.removeById(id);
        }
        return count;
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public UserDTO getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    /**
     * 通过快捷方式登录
     * @param openId
     * @param loginType
     * @return
     */
    @Override
    public UserDTO getByUsername(String openId, UserOpenType loginType) {
        return userMapper.selectByOpenId(openId, loginType);
    }

    /**
     * 登录方法
     *
     * 方法已废弃，真实的登录应使用 UserDetailsServiceImpl.loadUserByUsername
     *
     * @param user
     * @return
     */
    @Override
    @Deprecated
    public User login(User user) {
        User condition = new User();
        condition.setUsername(user.getUsername());
        condition.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(condition);
        User re = this.getOne(queryWrapper);
        if (Objects.isNull(re)) return null;
        return re;
    }

    public List findByCondition(QueryWrapper<User> queryWrapper) {
        List<User> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public Pager<User> listOfPage(Pager<User> pager, QueryWrapper<User> queryWrapper) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return userMapper.selectPage(pager, queryWrapper);
    }

    @Override
    public List<UserLoginTrack> listOfTrack(Integer userId, Integer size) {
        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
        // page.setOptimizeCountSql(false);
        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
        // 要点!! 分页返回的对象与传入的对象是同一个
        return userMapper.selectUserTrack(userId, size);
    }

    @Override
    public boolean addLoginTrack(Integer userId, String ip) {
        UserLoginTrack track = new UserLoginTrack();
        track.setUserId(userId);
        track.setLoginIp(ip);
        track.setLoginTime(new Date());
        return userMapper.insertUserTrack(track) == 1 ? true : false;
        // return userMapper.insertUserTrack(userId, ip, new Date()) == 1 ? true : false;
    }

    @Override
    public List<UserOpen> getUserOpenByUserId(Integer userId) {
        return userOpenMapper.selectList(new QueryWrapper<UserOpen>().eq("user_id", userId));
    }

    @Override
    public boolean addUserOpen(UserOpen userOpen) {
        // 查询当前的UserOpen信息是否已经被绑定
        if (!isUnbind(userOpen))
            throw new CanShowException(StrUtil.format("当前{}账号已被绑定", userOpen.getType()));
        // 根据平台类型查询当前用户是否有重复的信息
        UserOpen userDB = userOpenMapper.selectOne(
            new QueryWrapper<UserOpen>()
                .eq("user_id", userOpen.getUserId())
                .eq("type", userOpen.getType())
        );
        if (userDB != null) {
            userOpen.setId(userDB.getId()).clearTime();
            return userOpenMapper.updateById(userOpen) == 1;
        }
        return userOpenMapper.insert(userOpen) == 1;
    }

    /**
     * 查询当前openId是否没有被绑定
     *
     * @param userOpen
     * @return
     */
    public boolean isUnbind(UserOpen userOpen) {
        UserOpen open = userOpenMapper.selectOne(new QueryWrapper<UserOpen>()
            .eq("open_id", userOpen.getOpenId())
            .eq("type", userOpen.getType()));
        /**
         * 数据库不存在
         * 数据库已存在的用户Id与需要绑定的用户id相同
         */
        return open == null || open.getUserId() == userOpen.getUserId();
    }

    public boolean removeUserOpen(UserOpen userOpen) {
        return userOpenMapper.deleteById(userOpen) == 1;
    }

    /**
     * 删除用户的open信息
     * @param userOpen
     * @return
     */
    @Override
    public boolean revokeUserOpen(UserOpen userOpen) {
        return userOpenMapper.deleteById(userOpen) == 1;
    }

}