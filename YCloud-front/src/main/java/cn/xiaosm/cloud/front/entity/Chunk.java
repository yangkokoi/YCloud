package cn.xiaosm.cloud.front.entity;

import cn.xiaosm.cloud.core.config.jackson.JacksonConfig;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Young
 * @create 2022/12/9
 * @since 1.0.0
 */
@Data
public class Chunk {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String hash;
    private String fileHash;
    @TableField(value = "`order`")
    private Integer order;
    private Integer total;
    /**
     * 分块大小
     */
    private Long size;
    private Long userId;
    // 1下载，2上传
    private Integer type;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = JacksonConfig.LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createTime;

}