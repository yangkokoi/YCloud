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
    private String content;

    @Override
    public String getPath() {
        return ("/" + (super.getPath() == null ? "" : super.getPath())).replaceAll("/+|\\+", "/");
    }
    
}