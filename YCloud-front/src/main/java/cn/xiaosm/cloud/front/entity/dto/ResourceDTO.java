package cn.xiaosm.cloud.front.entity.dto;

import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.enums.FileType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.File;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class ResourceDTO extends Resource {

    private String bucketName;
    // 文件绝对路径
    private String fileAbPath;
    /* 当前访问路径 */
    private FileType fileType = FileType.DIR;

    // @JsonDeserialize
    // public ResourceDTO setFileType(String fileType) {
    //     FileType type = FileType.valueOf(fileType);
    //     this.fileType = (fileType == null ? FileType.FILE : type);
    //     return this;
    // }

    @Override
    public String getPath() {
        return super.getPath() == null ? "" : super.getPath();
    }
    
}