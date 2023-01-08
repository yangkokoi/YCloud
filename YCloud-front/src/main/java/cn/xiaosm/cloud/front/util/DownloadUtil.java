package cn.xiaosm.cloud.front.util;

import cn.hutool.core.util.HexUtil;
import cn.xiaosm.cloud.front.entity.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.Date;
import java.util.Optional;

/**
 * @author Young
 * @create 2022/4/11
 * @since 1.0.0
 */
public class DownloadUtil {

    private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    public static void outputData(HttpServletRequest request, HttpServletResponse response, @NotNull File file) {
        response.setHeader("Etag", makeEtag(file));
        response.setHeader("Last-Modified", new Date(file.lastModified()).toString());
        // 创建 Range 分段
        Range range = Optional
            .ofNullable(Range.build(request.getHeader("Range"), file))
            .orElse(new Range(file.length()));
        // Content-Range，格式为：bytes [要下载的开始位置]-[结束位置]/[文件总大小]
        response.setHeader("Content-Range", "bytes "
            + range.getStart() + "-" + range.getEnd() + "/" + range.getTotal());
        response.setContentLengthLong(range.getContentLength());
        if (range.isPart())
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        // 数据发送
        RandomAccessFile randomAccessFile = null;
        BufferedOutputStream out = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            // file = null;
            // 从 start 位置开始读取数据
            randomAccessFile.seek(range.getStart());
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int len = 0;
            // 已发送数据长度
            long sending = 0;
            // 要下载的长度
            long dataLen = range.getContentLength();
            // 如果需要下载的长度小于一次 buff 传输的大小，则直接进行下面的一次传输完成
            if (dataLen >= buff.length) {
                // 如果下一次的传输的长度大于了本次需要传输的长度，则进行的一次传输完成
                while ((sending + len < dataLen) && (len = randomAccessFile.read(buff)) != -1) {
                    out.write(buff, 0, len);
                    sending += len;
                }
            }
            // 一次传输完剩下的数据
            if (sending < dataLen) {
                len = randomAccessFile.read(buff, 0, (int) (dataLen - sending));
                out.write(buff, 0, len);
            }
            out.flush();
            response.flushBuffer();
        } catch (IOException e) {
            logger.error("文件下载失败");
        } finally {
            close(randomAccessFile, out);
        }
    }

    public static void offlineDownload(String url, Long userId) {
        new DownloadService(url, userId).download();
    }

    private static String makeEtag(File file) {
        StringBuffer sb = new StringBuffer();
        sb.append(HexUtil.encodeHexStr( String.valueOf(file.lastModified()) ));
        sb.append("-");
        sb.append(HexUtil.encodeHexStr( String.valueOf(file.length()) ));
        return sb.toString();
    }

    private static void close(Closeable... args) {
        try {
            for (Closeable arg : args) {
                if (arg != null) arg.close();;
            }
        } catch (IOException e) {

        }
    }
}