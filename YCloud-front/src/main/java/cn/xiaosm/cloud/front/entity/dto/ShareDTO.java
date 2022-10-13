package cn.xiaosm.cloud.front.entity.dto;

import cn.xiaosm.cloud.core.config.jackson.JacksonConfig;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.Share;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
@Data
public class ShareDTO extends Share {

    @NotNull(message = "资源id不可以为空")
    private String resourceIds;

    @Pattern(regexp = "(^[a-z0-9]{4}$)|(\\s{0})", message = "密码应为小写字母或数字组合的4位字符")
    private String password;

    @Future(message = "到期时间不能小于此刻")
    private LocalDateTime deadline;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Resource> resourceList;

    private Integer resourceId;

    /**
     * 访问文件路径
     */
    private String path;
}