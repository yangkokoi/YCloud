/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserLoginTrack
 * Author:   Young
 * Date:     2020/6/14 10:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@TableName(value = "user_login_track")
public class UserLoginTrack implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String loginIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public UserLoginTrack setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public UserLoginTrack setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public UserLoginTrack setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public UserLoginTrack setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        return this;
    }
}