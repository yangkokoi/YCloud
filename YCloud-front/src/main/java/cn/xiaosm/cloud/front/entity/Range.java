package cn.xiaosm.cloud.front.entity;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.front.exception.ResourceException;
import lombok.Data;

import java.io.File;

@Data
public class Range {

    /**
     * 起始下标
     */
    private long start;
    /**
     * 结束下标
     */
    private long end;
    /**
     * 总大小
     */
    private long total;
    /**
     * 当前分段大小
     */
    private long contentLength;
    /**
     * 是否分段
     */
    private boolean part = true;
    /**
     * 分段顺序
     */
    private int order = -1;

    private Range() { }

    public Range(long total) {
        this.start = 0;
        this.end = total - 1;
        this.total = total;
        this.contentLength = total;
    }

    public Range(long start, long total, int order) {
        this.start = start;
        this.end = total - 1;
        this.total = total;
        this.contentLength = total - start;
        this.order = order;
    }

    public Range(long start, long size, long total, int order) {
        this.start = start;
        this.end = start + size - 1;
        this.total = total;
        this.contentLength = size;
        this.order = order;
    }

    public static Range build(String range) {
        if (StrUtil.isBlank(range)) throw new IllegalArgumentException("Range值不可以为空");
        if (range.startsWith("bytes ")) return buildByResponse(range);
        else return null;
    }

    /**
     * 根据响应头构建 Range
     * Content-Range: bytes 1-10/55755272
     * @param arg
     * @return
     */
    public static Range buildByResponse(String arg) {
        if (StrUtil.isBlank(arg)) throw new IllegalArgumentException("Range值不可以为空");
        if (!arg.startsWith("bytes ")) throw new IllegalArgumentException("Range不合法");
        String[] value = arg.substring(6).split("/");
        Assert.isTrue(value.length == 2, "Range不合法");
        String[] se = value[0].split("-");
        Assert.isTrue(se.length == 2, "Range不合法");
        Range range = new Range();
        range.setStart(Long.valueOf(se[0]));
        range.setEnd(Long.valueOf(se[1]));
        range.setTotal(Long.valueOf(value[1]));
        range.setContentLength(range.end - range.start + 1);
        return range;
    }

    /**
     * 根据请求头构建 Range
     * Range: bytes=1-10
     * @param arg
     * @param file
     * @return
     * @throws ResourceException
     */
    public static Range build(String arg, File file) throws ResourceException {
        if (StrUtil.isBlank(arg)) return new Range(file.length());
        if (!arg.startsWith("bytes=")) return new Range(file.length());
        try {
            // 如果 arg 数据不存在，则默认使用 0-file.length() 的范围
            arg = arg.substring(arg.indexOf("=") + 1).trim();
            String[] arr = arg.split("-");
            Assert.isTrue(arr.length > 0, "Range不合法");
            // 如果 arg 存在，包装 arg 范围
            Range range = new Range();
            if (arr.length == 1) {
                if (arg.startsWith("-")) {  // 没有 start  -xx
                    range.setEnd(Long.valueOf(arr[0]));
                } else {                    // 没有 end  xx-
                    // 不是分段
                    range.setStart(Long.valueOf(arr[0]));
                    range.setEnd(file.length() - 1);
                    range.setPart(false);
                }
            } else {
                range.setStart(Long.valueOf(arr[0]));
                range.setEnd(Long.valueOf(arr[1]));
            }
            Assert.isTrue(range.start < range.end, "请求资源超出限制");
            range.setTotal(file.length());
            // 由于 Range 的表示方式，如 length = 1000; 分两次下载
            // 则两次分段分别是是 [0, 499] [500, 999],其表示方式遵循数学的闭区间
            // 所以在计算长度的时候需要 +1
            range.setContentLength(range.end - range.start + 1);
            return range;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Range{" + "start=" + start + ", end=" + end + ", total=" + total + ", contentLength=" + contentLength + ", part=" + part + '}';
    }
}