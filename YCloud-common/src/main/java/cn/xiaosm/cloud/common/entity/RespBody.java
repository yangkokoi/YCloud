/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: ResponseBody
 * Author:   Young
 * Date:     2020/6/13 15:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.common.entity;

import cn.xiaosm.cloud.common.entity.enums.RespStatus;

import java.util.Objects;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
public class RespBody {

    private int code;
    private String msg;
    private Object data;

    public RespBody(RespStatus status) {
        this.setStatus(status);
    }

    public RespBody(RespStatus status, String msg) {
        this.setStatus(status);
        this.setMsg(msg);
    }

    public RespBody(RespStatus status, Object data) {
        this.setStatus(status);
        this.setData(data);
    }

    public RespBody(RespStatus status, String msg, Object data) {
        this.setStatus(status);
        this.setMsg(msg);
        this.setData(data);
    }

    public void setStatus(RespStatus status) {
        this.code = status.getCode();
        this.msg = status.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        if (Objects.isNull(msg) || msg.contains("Exception")) {
            return;
        }
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}