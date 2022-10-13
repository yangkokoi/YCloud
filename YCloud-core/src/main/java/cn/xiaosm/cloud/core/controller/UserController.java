/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserController
 * Author:   Young
 * Date:     2020/6/13 14:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.LogRecord;
import cn.xiaosm.cloud.core.annotation.YAdmin;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.core.entity.User;
import cn.xiaosm.cloud.core.entity.UserOpen;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import cn.xiaosm.cloud.core.entity.vo.UserVO;
import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.core.service.UserService;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.util.WrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
@YAdmin(value = "user")
@RestController("adminUserController")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 根据 Token 获取当前登录用户的信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public RespBody userInfo(HttpServletRequest request) {
        return RespUtils.success(tokenService.getLoginUser(request));
    }

    /**
     * 修改个人信息
     * @param userVO
     * @param request
     * @return
     */
    @PostMapping("/info")
    public RespBody userInfo(@RequestBody UserVO userVO, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (userVO.getId() != loginUser.getId()) {
            return RespUtils.fail("请求被拒绝");
        }
        // 用户更新
        if (userService.updateById(userVO)) {
            // 更新内存的对象
            BeanUtil.copyProperties(userVO, loginUser, CopyOptions.create(User.class, true));
            CacheUtils.set(loginUser.getUuid(), loginUser);
            return RespUtils.success("个人资料修改成功");
        } else {
            return RespUtils.fail("修改失败");
        }
    }

    @PostMapping("/password")
    public RespBody password(@RequestBody UserVO userVO, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (userVO.getId() != loginUser.getId()) {
            return RespUtils.fail("请求被拒绝");
        }
        if (StrUtil.isNotBlank(userVO.getPassword())) {
            if (!bCryptPasswordEncoder.matches(userVO.getPassword(), loginUser.getPassword())) {
                return RespUtils.fail("原密码错误");
            }
            boolean b = userService.modPassword(userVO, userVO.getNewPwd(), true);
            // 更新内存的对象
            BeanUtil.copyProperties(userVO, loginUser, CopyOptions.create(User.class, true));
            CacheUtils.set(loginUser.getUuid(), loginUser);
            return b ? RespUtils.success("密码修改成功")
                : RespUtils.fail("修改失败");
        } else {
            return RespUtils.fail("原密码不可以为空");
        }
    }

    /**
     * 获取当前登录用户足迹
     * @param size
     * @return
     */
    @GetMapping("/info/track")
    public RespBody userTrack(@RequestParam(value = "size", defaultValue = "5") Integer size, HttpServletRequest request) {
        return RespUtils.success(userService.listOfTrack(tokenService.getLoginUser(request).getId(), size));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user:query') or hasRole('admin')")
    public RespBody queryUser(Pager<User> pager) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        WrapperUtils.bindSearch(wrapper, pager, "username", "nickname");
        wrapper.orderByDesc("create_time");
        return RespUtils.success("获取了用户列表", userService.page(pager, wrapper));
    }

    @PutMapping
    @LogRecord("添加用户")
    @PreAuthorize("(hasAuthority('user:add') and hasAuthority('role:query')) or hasRole('admin')")
    public RespBody saveUser(@RequestBody UserVO userVO) {
        boolean b = userService.save(userVO);
        return b == true ? RespUtils.fail("新增用户信息成功")
                : RespUtils.fail("保存失败");
    }

    @PostMapping
    @LogRecord("修改用户")
    @PreAuthorize("hasAuthority('user:modify') or hasRole('admin')")
    public RespBody modifyUser(@RequestBody UserVO userVO) {
        if (userVO.getId() == 1) throw new SQLOperateException("系统保留数据，请勿操作");
        if (userVO.getIsReset()) {
            userService.defaultPass(userVO);
            boolean b = userService.updateById(userVO);
            return b ? RespUtils.success("密码重置成功###新密码为123456")
                : RespUtils.fail("密码重置失败");
        }
        boolean b = userService.updateById(userVO);
        return b ? RespUtils.success("修改用户信息成功")
                : RespUtils.fail("修改失败");
    }

    @DeleteMapping
    @LogRecord("删除用户")
    @PreAuthorize("hasAuthority('user:delete') or hasRole('admin')")
    public RespBody deleteUsers(@RequestBody Set<Integer> ids) {
        if (ids.stream().filter(el -> el == 1).count() == 1) {
            throw new SQLOperateException("系统保留数据，请勿操作");
        }
        int b = userService.removeByIds(ids);
        return RespUtils.success("删除用户成功，本次删除" + b + "条用户");
    }

    @GetMapping("/open")
    public RespBody getUserOpen(HttpServletRequest request) {
        List<UserOpen> opens = userService.getUserOpenByUserId(tokenService.getLoginUser(request).getId());
        return RespUtils.success(opens);
    }

    @DeleteMapping("/open")
    public RespBody getUserOpen(@RequestBody UserOpen userOpen) {
        return userService.revokeUserOpen(userOpen) ?
            RespUtils.success("解绑成功") : RespUtils.fail("解绑失败");
    }
}