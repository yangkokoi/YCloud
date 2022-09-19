package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.entity.enums.RespStatus;
import cn.xiaosm.cloud.common.util.RespUtils;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
class OnErrorController extends AbstractErrorController {

    public OnErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 默认路径/error，可以通过server.error.path配置
     */
    @RequestMapping(("${server.error.path:/error}"))
    public RespBody notFoundError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(200);
        request.getRequestDispatcher("/").forward(request, response);
        return RespUtils.build(getStatus(request), "");
    }

}