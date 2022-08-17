package cn.xiaosm.cloud.front.entity;

import cn.xiaosm.cloud.core.entity.BaseEntity;
import cn.xiaosm.cloud.front.entity.enums.BucketType;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文件存储仓库
 *
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("bucket")
public class Bucket extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /* 仓库名称 */
    private String name;

    /* 仓库中文名称 */
    private String zhName;

    /* 仓库路径 | 在数据库中存储的是根路径 */
    private String path = "";

    /* 仓库归属权 */
    private Integer userId;

    @TableField(exist = false)
    private BucketType type;

    public Bucket setPath(String path) {
        if (path == null) return this;
        this.path = path.replaceAll("/+|\\\\+", "/");
        return this;
    }

}