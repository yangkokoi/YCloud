package cn.xiaosm.cloud.front.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.xiaosm.cloud.common.util.ServletUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.core.entity.User;
import cn.xiaosm.cloud.front.entity.Chunk;
import cn.xiaosm.cloud.front.entity.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Young
 * @create 2022/12/9
 * @since 1.0.0
 */
class DownloadService {
    private static final Long chunkSize = 50 * 1024 * 1024l;
    private static File localPath = new File("C:\\Users\\Young\\Desktop\\local", "download");
    private static final ReentrantLock lock = new ReentrantLock();
    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
    private final ArrayList<Chunk> chunkList = new ArrayList<>();

    private String url;
    private Long userId;

    static {
        if (!localPath.exists()) localPath.mkdirs();
    }

    private final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    public DownloadService(String url, Long userId) {
        this.url = url;
        this.userId = userId;
    }

    public void test() {
        String[] urls = new String[]{
            "https://download-ssl.firefox.com.cn/releases-sha2/full/99.0/zh-CN/Firefox-full-latest-win64.exe"
            , "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202011%2F23%2F20201123135259_563a4.thumb.400_0.png&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1673071917&t=e4c7264966435d662b1b178b2e394ca9"
        };
        System.out.println();;
        // offlineDownload("https://download-ssl.firefox.com.cn/releases-sha2/full/99.0/zh-CN/Firefox-full-latest-win64.exe");
        // offlineDownload("");
    }

    /**
     * 通过设置 Range 头请求来判断服务器是否支持分段下载
     * @param url
     * @return
     * @throws IOException
     */
    private Range acceptSplit(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        // 下载十条数据测试一下
        connection.setRequestProperty("Range", "bytes=0-9");
        connection.setReadTimeout(3000);
        connection.connect();
        connection.disconnect();
        if (connection.getResponseCode() == 206 && connection.getContentLength() == 10) {
            return Range.build(connection.getHeaderField("Content-Range"));
        } else {
            return new Range(connection.getContentLengthLong());
        }
    }

    public synchronized boolean download() {
        Assert.isFalse(lock.isLocked(), "只能同时进行1个离线下载");
        lock.lock();
        // 检查是否支持分段下载
        Range range = null;
        try {
            range = acceptSplit(url);
        } catch (IOException e) {
            lock.unlock();
            return false;
        }
        if (range.isPart()) {
            int part = (int) (range.getTotal() / chunkSize);
            int chunkCount = (range.getTotal() % chunkSize == 0) ? part : part + 1;
            for (int i = 0; i < part; i++) {
                Range r = new Range(i * chunkSize, chunkSize, range.getTotal(), i);
                pool.submit(download(url, r, chunkCount));
            }
            if (chunkCount > part) {
                pool.submit(download(url, new Range(part * chunkSize, range.getTotal(), part), chunkCount));
            }
        }
        return true;
    }

    private Thread download(String url, Range range, Integer count) {
        return new Thread(() -> {
            try {
                long contentLength = 0;
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestProperty("Range", "bytes=" + range.getStart() + "-" + range.getEnd());
                connection.connect();
                contentLength = connection.getContentLengthLong();
                int code = connection.getResponseCode();
                if (code >= 400) {
                    logger.error("连接目标资源失败，响应码：{}", code);
                }
                connection.getHeaderFields().forEach((key, list) -> {
                    System.out.println(key + ": " + list.stream().toList());
                });
                // 获取文件名
                String disposition = connection.getHeaderField("Content-Disposition");
                System.out.println("文件名：" + disposition);
                System.out.println("响应体长度：" + contentLength + "/ " + contentLength / 1024 / 1024 + "MB");
                System.out.println("===================");
                // 转存文件
                File file = new File(localPath, IdUtil.simpleUUID() + ".data");
                file.createNewFile();
                RandomAccessFile writer = new RandomAccessFile(file, "rw");
                InputStream in = connection.getInputStream();
                int len = 0;
                byte[] buff = new byte[1024];
                // float rate = 0;
                // long now = System.currentTimeMillis();
                // int millis = 1000;
                while ((len = in.read(buff)) > 0) {
                    writer.write(buff, 0, len);
                    // rate += len;
                    // if (System.currentTimeMillis() - now >= millis) {
                    //     System.out.printf("当前进度：%.2f%%\n", ((rate / contentLength) * 100));
                    //     millis += 1000;
                    // }
                }
                // 判断文件长度是否相等
                if (writer.length() != contentLength) {
                    throw new IOException("文件传输异常");
                }
                in.close();
                connection.disconnect();
                writer.close();
                // 存储 chunk 信息
                Chunk chunk = new Chunk();
                chunk.setType(1);
                chunk.setOrder(range.getOrder());
                chunk.setTotal(count);
                // chunk.setUserId(SecurityUtils.getLoginUserId());
                chunkList.add(chunk);
                if (chunkList.size() == count) {
                    System.out.println("传输完成");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // 判断文件类型
        // String type = FileTypeUtil.getType(file);
        // String fileName = "";
        // if (StrUtil.isBlank(disposition)) {
        //     String uuid = IdUtil.simpleUUID();
        //     fileName = StrUtil.isBlank(type) ? uuid : uuid + "." + type;
        // } else {
        //     String regex = "filename=['\"](([\\w-]+).[a-z0-9]+)['\"]";
        //     Matcher m = Pattern.compile(regex).matcher(disposition);
        //     if (m.find()) {
        //         fileName = m.group(1);
        //     } else {
        //         logger.info("没有找到文件名，原内容：{}", disposition);
        //     }
        // }
        // if (!file.renameTo(new File(localPath, fileName))) {
        //     System.err.println("重命名失败");
        // }
    }
}