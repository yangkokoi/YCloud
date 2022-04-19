package cn.xiaosm.cloud.front.entity.vo;

import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.enums.FileType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class UploadVO {

    private String bucketName;
    // 文件需要被保存的目录 ！！ 注意，只是文件夹目录，不是文件地址
    private String path = "";
    private LoginUser user;
    private List<MultipartFile> files;
    private FileType type = FileType.FILE;


    public UploadVO setUser(LoginUser user) {
        this.user = user;
        return this;
    }

    public UploadVO setPath(String path) {
        this.path = path.replaceAll("/+|\\\\+", "/");
        return this;
    }

    public String getFullPath() {
        return ("/" + this.getUser().getUuid() + "/" + this.getPath()).replaceAll("/+|\\\\+", "/");
    }
}