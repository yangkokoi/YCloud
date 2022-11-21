package cn.xiaosm.cloud.front.config;

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

    public static String ROOT;
    public static final String LOCAL_BUCKET = "local";
    public static final String PROJECT_PATH = new ApplicationHome(Application.class).getSource().getParent();
    // public static String PROJECT_PATH = System.getProperty("user.home") + "\\desktop\\";
    // 文件存储目录
    public static String LOCAL_PATH;

    @Value("${ycloud.local.root}")
    public void setROOT(String ROOT) {
        UploadConfig.ROOT = ROOT.replaceAll("\\\\", "/");
        File file = new File(UploadConfig.PROJECT_PATH, UploadConfig.ROOT);
        if (!file.exists()) file.mkdirs();
        LOCAL_PATH = file.getAbsolutePath();
        logger.info("当前存储路劲：{}", LOCAL_PATH);
    }
}