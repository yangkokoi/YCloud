package cn.xiaosm.cloud.core.controller.oauth;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.entity.UserOpen;
import cn.xiaosm.cloud.core.service.UserService;
import me.zhyd.oauth.model.AuthCallback;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Young
 * @create 2021/4/15
 * @since 1.0.0
 */
public interface AuthLoginHandler {

    String BIND_PREFIX = "id=";
    UserService userService = null;

    /**
     * 跳转到快捷登录的平台网址
     * @param response
     * @throws IOException
     */
    void renderAuth(boolean bind, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 平台的回调地址
     * @param callback 包装了平台用于登录的key
     * @param request
     * @return
     */
    String login(AuthCallback callback, HttpServletRequest request);

    /**
     * 解除绑定的操作
     *
     * @param user
     * @return
     */
    @RequestMapping("/revoke")
    default RespBody revokeAuth(UserOpen user) {
        if (user == null ||
            (user.getUserId() == null && user.getType() == null)) {
            return userService.revokeUserOpen(user) ?
                RespUtils.success("解绑成功")
                : RespUtils.fail("解绑失败");
        }
        return RespUtils.error("参数不完整");
    };

}