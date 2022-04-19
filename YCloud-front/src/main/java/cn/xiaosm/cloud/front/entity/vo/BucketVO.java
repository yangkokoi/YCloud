package cn.xiaosm.cloud.front.entity.vo;

import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.enums.FileType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author Young
 * @create 2022/4/10
 * @since 1.0.0
 */
@Data
public class BucketVO extends Bucket {

    private String uuid;
    /* 当前访问路径 */
    @TableField(exist = false)
    private String currentPath;
    private String fileName;
    private FileType fileType = FileType.DIR;

    public Bucket setCurrentPath(String currentPath) {
        if (currentPath == null) return this;
        this.currentPath = currentPath.replaceAll("/+|\\\\+", "/");
        return this;
    }

    public String getFullPath() {
        return (currentPath + "/" + fileName).replaceAll("/+|\\\\+", "/");
    }

    public String getCurrentPath() {
        return currentPath == null ? "" : currentPath;
    }
}