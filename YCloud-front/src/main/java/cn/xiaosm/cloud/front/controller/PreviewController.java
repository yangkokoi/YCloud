package cn.xiaosm.cloud.front.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.config.security.service.TokenService;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.service.ShareService;
import cn.xiaosm.cloud.front.util.DownloadUtil;
import cn.xiaosm.cloud.security.annotation.AnonymousAccess;
import cn.xiaosm.cloud.security.entity.AuthUser;
import cn.xiaosm.cloud.security.entity.TokenType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 本类中的方法，均需要获取 unsafe_token 后才可以访问
 *
 * @author Young
 * @create 2022/9/9
 * @since 1.0.0
 */
@Log4j2
@RestController
@RequestMapping("resource")
public class PreviewController {

    @Autowired
    ResourceService resourceService;
    @Autowired
    ShareService shareService;
    @Autowired
    TokenService tokenService;

    /**
     * 文件下载请求
     * @param entry
     * @param request
     * @param response
     * @return
     */
    @AnonymousAccess
    @GetMapping("download")
    public Object download(
        @RequestParam("entry") String entry,
        HttpServletRequest request,
        HttpServletResponse response) {
        ResourceDTO resource = (ResourceDTO) CacheUtils.get(entry, true);
        if (resource == null) {
            log.info("资源已过期");
            return RespUtils.fail("资源已过期");
        }
        File file = new File(resource.getFileAbPath());
        if (!file.exists()) {
            CacheUtils.del(entry);
            log.info("资源已被删除");
            return RespUtils.fail("资源已被删除");
        }
        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        // 分段下载，响应头
        response.setHeader("Accept-Ranges", "bytes");
        // 设置文件名
        response.setHeader("Content-Disposition",
            "attachment;fileName=" + URLUtil.encode(resource.getName(), "UTF-8"));
        DownloadUtil.outputData(request, response, file);
        return null;
    }

    @GetMapping("preview/{id}")
    @PreAuthorize("hasAuthority('resource:preview')")
    public RespBody preview(
        @PathVariable("id") String id,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        TokenType type = ((AuthUser) SecurityUtils.getAuthentication().getPrincipal()).getTokenType();
        ResourceDTO resourceDTO = new ResourceDTO();
        switch (type) {
            case LOGIN -> {
                resourceDTO.setId(Long.valueOf(id));
                resourceDTO.setUserId(SecurityUtils.getLoginUserId());
                resourceDTO = resourceService.preview(resourceDTO);
            }
            case SHARE -> {
                Resource res = (Resource) CacheUtils.get("S" + id);
                if (res != null) {
                    BeanUtils.copyProperties(res, resourceDTO);
                    File file = resourceService.getLocalFile(res);
                    if (!file.exists()) {
                        return RespUtils.fail("资源不存在");
                    }
                    resourceDTO.setFileAbPath(file.getAbsolutePath());
                }
            }
        }
        return previewHandler(resourceDTO, request, response);
    }

    private final Long MAX_SIZE = (1 << 20) * 10l; // 10MB

    public RespBody previewHandler(ResourceDTO resourceDTO, HttpServletRequest request, HttpServletResponse response) {
        if (resourceDTO == null) {
            return RespUtils.fail("资源不存在");
        }
        File file;
        if (StrUtil.isBlank(resourceDTO.getFileAbPath())
            || !(file = new File(resourceDTO.getFileAbPath())).exists())
            return RespUtils.fail("文件不存在");
        if (resourceDTO.getSize() > MAX_SIZE) return RespUtils.fail("文件过大，暂不支持在线预览");
        response.reset();
        String contentType = getContentType(resourceDTO.getType());
        if (StrUtil.isBlank(contentType)) return RespUtils.fail("当前文件类型暂不支持预览");
        response.setContentType(contentType);
        // GET 请求直接在浏览器中能够展示
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            DownloadUtil.outputData(request, response, file);
        }
        // 如果非 GET 请求，且文件类型是文本时，直接把内容放在 json 中传回
        else if (ArrayUtil.contains(TEXT_TYPE, resourceDTO.getType())) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            return RespUtils.success("", IoUtil.read(FileUtil.getInputStream(file), Charset.defaultCharset()));
        }
        return null;
    }

    private static Map<String, String> RESOURCE_TYPE = new HashMap<String, String>(){{
       put("plain", "text/plain;charset=UTF-8");
       put("jpg", "image/jpeg");
       put("jpeg", "image/jpeg");
       put("gif", "image/gif");
       put("png", "image/png");
       put("pdf", "application/pdf");
       put("xml", "text/xml");
    }};

    private static String[] TEXT_TYPE = new String[]{
        "txt", "html", "css", "java", "ts", "js", "py", "c", "cpp", "md", "sql"
    };

    private String getContentType(String type) {
        if (ArrayUtil.contains(TEXT_TYPE, type)) {
            return RESOURCE_TYPE.get("plain");
        } else {
            return RESOURCE_TYPE.get(type);
        }
    }

}