package cn.xiaosm.cloud.front.entity;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.xiaosm.cloud.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.Locale;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("resource")
public class Resource extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    // 文件保存的目录，不带文件名
    private String path;
    private String hash;
    private long size;
    private String type;
    private Integer userId;
    private Integer bucketId;
    private Integer parentId;
    @TableField(exist = false)
    private boolean dir = false;

    /**
     * 是否可编辑
     */
    private boolean edit = false;

    public Resource() { }

    public Resource(Bucket bucket, File file) {
        this.userId = bucket.getUserId();
        this.bucketId = bucket.getId();
        this.name = file.getName();
        this.type = FileTypeUtil.getType(file);
        this.dir = file.isDirectory();
        this.size = file.length();
        this.hash = FileUtil.mainName(file);
    }

    public Resource setName(String name) {
        this.name = name == null ? "unset-name." + this.type  : name;
        return this;
    }

    public Resource setType(String type) {
        this.type = type;
        this.dir = type.equalsIgnoreCase("DIR") ? true : false;
        return this;
    }

    public String getFullPath() {
        return (this.path + "/" + this.name).replaceAll("/+", "/");
    }

    public String getFullName() {
        return this.getName() + "." + this.type;
    }
}