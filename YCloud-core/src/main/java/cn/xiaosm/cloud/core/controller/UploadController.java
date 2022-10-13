// /**
//  * Copyright: 2019-2020
//  * FileName: UploadController
//  * Author:   Young
//  * Date:     2020/6/29 16:26
//  * Description:
//  * History:
//  * <author>          <time>          <version>          <desc>
//  * Young         修改时间           版本号             描述
//  */
// package cn.xiaosm.cloud.core.controller;
//
// import cn.hutool.core.io.FileUtil;
// import cn.hutool.core.util.HashUtil;
// import cn.hutool.core.util.StrUtil;
// import cn.xiaosm.yadmin.core.annotation.LogRecord;
// import cn.xiaosm.yadmin.core.annotation.Yadmin;
// import cn.xiaosm.yadmin.common.entity.ResponseBody;
// import cn.xiaosm.yadmin.common.util.ResponseUtils;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.util.ResourceUtils;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;
//
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.util.List;
//
// /**
//  * 〈一句话功能简述〉
//  * 〈〉
//  *
//  * @author Young
//  * @create 2020/6/29
//  * @since 1.0.0
//  */
// @Yadmin
// @RestController
// @RequestMapping("/upload")
// public class UploadController {
//
//     // 文件存储目录
//     private final static String UPLOAD_PATH = "static/upload/";
//     private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//     @RequestMapping
//     @LogRecord("文件上传")
//     public ResponseBody multiUpload(
//             @RequestParam("files") List<MultipartFile> files,
//             @RequestParam(value = "type", defaultValue = "file") String type) {
//         // 如果是头像上传
//         if ("avatar".equals(type)) {
//             String fileName = this.upload(files.get(0));
//             return StrUtil.isBlank(fileName) ? ResponseUtils.buildFail("头像上传出现错误")
//                     : ResponseUtils.buildSuccess("头像上传成功", "/upload/" + fileName);
//         } else {
//             for (MultipartFile file : files) {
//                 this.upload(file);
//             }
//         }
//         return ResponseUtils.buildSuccess("文件上传成功");
//     }
//
//     public String upload(MultipartFile file) {
//         if (file.isEmpty()) {
//             logger.info("文件为空");
//             return null;
//         }
//         // String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//         // String fileName = UUID.randomUUID().toString() + fileType;
//         // 获取文件类型
//         String fileType = FileUtil.extName(file.getOriginalFilename());
//         try {
//             // 使用 hash code 作为文件名
//             String fileName = HashUtil.cityHash32(file.getBytes()) + fileType;
//             File dest = new File(this.getPath() + fileName);
//             file.transferTo(dest);
//             logger.info("文件上传成功");
//             return fileName;
//         } catch (IOException e) {
//             logger.error(e.toString(), "文件上传失败");
//             return null;
//         }
//     }
//
//     public String getPath() {
//         // 获取项目所在的根目录
//         File path = null;
//         try {
//             path = new File(ResourceUtils.getURL("classpath:").getPath());
//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//         }
//         // 获取上传目录
//         File upload = new File(path.getAbsolutePath(), UPLOAD_PATH);
//         if(!upload.exists()) { upload.mkdirs(); }
//         return upload.getAbsolutePath() + "/";
//     }
//
// }