package cn.xiaosm.cloud.common.util;

import cn.hutool.json.JSONUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.entity.enums.RespStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * JSON数据包装工具
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
public class RespUtils {

    public static void sendToken(HttpServletResponse response, String token) {
        writeBody(response, new HashMap<String, Object>(){{
            put("code", 200);
            put("msg", "登录成功");
            put("token", token);
        }}, HttpServletResponse.SC_OK);
    }

    public static RespBody build(RespStatus status, String msg) {
        return new RespBody(status, msg);
    }

    public static RespBody build(RespStatus status, Object data) {
        return new RespBody(status, data);
    }

    public static RespBody build(RespStatus status, String msg, Object data) {
        return new RespBody(status, msg, data);
    }

    public static RespBody success() {
        return new RespBody(RespStatus.SUCCESS, "OK");
    }

    public static RespBody success(String msg) {
        return new RespBody(RespStatus.SUCCESS, msg);
    }

    public static RespBody success(Object data) {
        return new RespBody(RespStatus.SUCCESS, data);
    }

    public static RespBody success(String msg, Object data) {
        return new RespBody(RespStatus.SUCCESS, msg, data);
    }

    public static RespBody fail() {
        return new RespBody(RespStatus.FAIL, "FAIL");
    }

    public static RespBody fail(String msg) {
        return new RespBody(RespStatus.FAIL, msg);
    }

    public static RespBody fail(Object data) {
        return new RespBody(RespStatus.FAIL, data);
    }

    public static RespBody fail(String msg, Object data) {
        return new RespBody(RespStatus.FAIL, msg, data);
    }

    public static RespBody error(String msg) {
        return new RespBody(RespStatus.ERROR, msg);
    }

    public static RespBody error(Object data) {
        return new RespBody(RespStatus.ERROR, data);
    }

    public static RespBody error(String msg, Object data) {
        return new RespBody(RespStatus.ERROR, msg, data);
    }

    // 发送请求
    public static void success(HttpServletResponse response, String msg) {
        writeBody(response, success(msg), HttpServletResponse.SC_OK);
    }

    public static void sendError(HttpServletResponse response, String msg, int sc) {
        writeBody(response, build(RespStatus.SUCCESS, msg), sc);
    }

    public static void sendError(HttpServletResponse response, RespStatus status, String msg, int sc) {
        writeBody(response, new RespBody(status, msg), sc);
    }

    private static<T> void writeBody(HttpServletResponse response, T entity, int sc) {
        // response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(sc);
        try {
            response.getWriter().write(JSONUtil.toJsonStr(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}