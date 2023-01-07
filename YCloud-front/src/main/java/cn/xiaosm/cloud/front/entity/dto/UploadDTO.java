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
    /**
     * 文件需要被保存的目录 ！！ 注意，只是文件夹目录，不是文件地址
     */
    private String path = "";
    private MultipartFile file;

    /**
     * 分块序号
     */
    private Integer chunkNumber;

    /**
     * 分块个数
     */
    private Integer totalChunks;

    /**
     * 分块大小
     */
    private Long chunkSize;

    /**
     * 当前分块大小
     */
    private Long currentChunkSize;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 文件完整 hash
     */
    private String identifier;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 是否是上传前的检查请求
     */
    private boolean uploadBefore = false;

    public String getPath() {
        return ("/" + this.path).replaceAll("/+|\\\\+", "/");
    }
}