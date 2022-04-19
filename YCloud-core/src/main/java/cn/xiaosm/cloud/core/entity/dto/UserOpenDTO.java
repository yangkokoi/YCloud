package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.core.entity.UserOpen;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import me.zhyd.oauth.model.AuthResponse;

/**
 * @author Young
 * @create 2021/4/16
 * @since 1.0.0
 */
@Data
public class UserOpenDTO extends UserOpen {

    private String openName;  // 第三方账号名称
    private String openAvatar; // 第三方账号头像
    @JsonIgnore
    private AuthResponse openInfo;

    public void setOpenInfo(AuthResponse openInfo) {
        this.openInfo = openInfo;
    }
}