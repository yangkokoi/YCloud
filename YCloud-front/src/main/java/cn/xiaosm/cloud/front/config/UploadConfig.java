package cn.xiaosm.cloud.front.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.Application;
import cn.xiaosm.cloud.common.factory.YamlSourceFactory;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */

@Data
@Component
@PropertySource(value = "classpath:app-config.yml", factory = YamlSourceFactory.class)
// @ConfigurationProperties(prefix = "ycloud.upload")
public class UploadConfig {

    Logger logger = LoggerFactory.getLogger(UploadConfig.class);

    /**
     * 默认仓库（bucket）的名称
     */
    public static final String LOCAL_BUCKET = "local";
    /**
     * 项目文件在机器上的绝对路径
     */
    public static final String PROJECT_PATH = new ApplicationHome(Application.class).getSource().getParent();
    /**
     * 文件存储目录
     */
    public static String LOCAL_PATH;
    /**
     * 分块存储目录
     */
    public static String CHUNK_PATH;
    /**
     * 最大上传大小，单位 MB
     */
    public static Long MAX_UPLOAD_SIZE;

    @Value("${cloud.local-path}")
    public void setROOT(String path) {
        path = path.replaceAll("\\\\", "/");
        File file = new File(PROJECT_PATH, path);
        if (!file.exists()) file.mkdirs();
        LOCAL_PATH = file.getAbsolutePath();
        logger.info("当前存储路径：{}", LOCAL_PATH);

        file = new File(PROJECT_PATH, "/chunk");
        if (!file.exists()) file.mkdirs();
        CHUNK_PATH = file.getAbsolutePath();
        logger.info("当前分块路径：{}", CHUNK_PATH);
    }

    @Value("${cloud.max-upload-size}")
    public void setMaxUploadSize(String arg) {
        long mb = DataUnit.MEGABYTES.ordinal();
        if (StrUtil.isBlank(arg)) {
            MAX_UPLOAD_SIZE = mb * 100;
            return;
        }
        arg = arg.replace("MB", "");
        try {
            MAX_UPLOAD_SIZE = Long.valueOf(arg) * mb;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("MAX_UPLOAD_SIZE 格式化错误");
        }
    }

}