/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: ResponseCode
 * Author:   Young
 * Date:     2020/6/13 15:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.common.entity.enums;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
public enum RespStatus {

    FAIL(500, "失败"),
    SUCCESS(200, "成功"),
    ERROR(400, "请求出现错误"),
    AUTHENTICATION_DENIED(401, "denied access!"),
    AUTHORITIES_DENIED(403, "权限不足"),
    DATA_EMPTY(404, "查询数据空"),
    OAUTH_UNBIND(4000, "快捷登录失败");

    // @EnumValue
    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    RespStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RespStatus valueOf(int code) {
        for (RespStatus val : RespStatus.values()) {
            if (val.code == code) {
                return val;
            }
        }
        return RespStatus.valueOf("SUCCESS");
    }
}