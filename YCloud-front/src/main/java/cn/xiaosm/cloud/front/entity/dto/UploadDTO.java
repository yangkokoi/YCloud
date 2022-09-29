package cn.xiaosm.cloud.front.entity.dto;

import cn.xiaosm.cloud.core.entity.LoginUser;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class UploadDTO {

    private String bucketName;
    // 文件需要被保存的目录 ！！ 注意，只是文件夹目录，不是文件地址
    private String path = "";
    private List<MultipartFile> files;
    private LoginUser user;


    public UploadDTO setUser(LoginUser user) {
        this.user = user;
        return this;
    }

    public String getPath() {
        return ("/" + this.path).replaceAll("/+|\\\\+", "/");
    }
}