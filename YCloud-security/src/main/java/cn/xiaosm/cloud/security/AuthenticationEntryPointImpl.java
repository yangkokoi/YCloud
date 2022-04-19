/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: JwtAuthenticationEntryPoint
 * Author:   Young
 * Date:     2020/6/15 9:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.security;

import cn.xiaosm.cloud.common.entity.enums.RespStatus;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.security.service.DefaultTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉
 * 所有非匿名请求如果没有通过权限认证都将会进入到此处理类
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
@Log4j2
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Autowired
    DefaultTokenService tokenService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        log.debug("请先通过认证在进行操作");
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        if (tokenService.getToken(request).isEmpty()) {
            RespUtils.sendError(response, RespStatus.AUTHENTICATION_DENIED,
                RespStatus.AUTHENTICATION_DENIED.getMsg(),
                HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            RespUtils.sendError(response, RespStatus.AUTHENTICATION_DENIED,
                authException == null ? "Unauthorized" : "授权过期，请重新获取",
                HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

}