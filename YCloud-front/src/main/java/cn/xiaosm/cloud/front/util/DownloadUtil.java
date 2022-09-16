package cn.xiaosm.cloud.front.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.front.exception.ResourceException;
import lombok.Data;
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
        // 创建 Range 分段
        Range range = Optional
            .ofNullable(Range.buildRange(request.getHeader("Range"), file))
            .orElse(new Range(file.length()));
        response.setHeader("Etag", makeEtag(file));
        response.setHeader("Last-Modified", new Date(file.lastModified()).toString());
        // Content-Range，格式为：[要下载的开始位置]-[结束位置]/[文件总大小]
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
            // 已发送数据长度, 初始设置为 buff 的长度
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

@Data
class Range {

    private long start;
    private long end;
    private long total;
    private long contentLength;
    private boolean isPart = false;

    private Range() { }

    public Range(long total) {
        this.start = 0;
        this.end = total;
        this.total = total;
        this.contentLength = total;
    }

    public Range(long start, long end, long total) {
        this.start = start;
        this.end = end;
        this.total = total;
    }

    public static Range buildRange(String range, File file) throws ResourceException {
        try {
            // 如果 range 数据不存在，则默认使用 0-file.length() 的范围
            if (StrUtil.isBlank(range)) return null;
            if (!range.startsWith("bytes=")) return null;
            range = range.substring(range.indexOf("=") + 1).trim();
            String[] arr = range.split("-");
            if (arr.length == 0) return null;
            // 如果 range 存在，包装 range 范围
            Range obj = new Range();
            if (arr.length == 1) {
                if (range.startsWith("-")) obj.end = Long.valueOf(arr[0]);
                else {
                    obj.start = Long.valueOf(arr[0]);
                    obj.end = file.length() - 1;
                }
            } else {
                obj.start = Long.valueOf(arr[0]);
                obj.end = Long.valueOf(arr[1]);
            }
            if (obj.start > obj.end) throw new ResourceException("请求资源超出限制");
            obj.total = file.length();
            // 开启分段
            obj.isPart = true;
            // 由于 Range 的表示方式，如 length = 1000; 分两次下载
            // 则两次分段分别是是 [0, 499] [500, 999],其表示方式遵循数学的闭区间
            // 所以在计算长度的时候需要 +1
            obj.contentLength = obj.end - obj.start + 1;
            return obj;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}