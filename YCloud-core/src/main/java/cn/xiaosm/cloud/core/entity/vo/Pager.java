/**
 * Copyright: 2019-2020
 * FileName: Pager
 * Author:   Young
 * Date:     2020/12/11 16:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.vo;

import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 此类继承至 Mybatis-Plus 的分页对象
 * 用于自定义分页逻辑
 * 用户前端支持分页的条件查询的包装类
 *
 * @author Young
 * @create 2020/12/11
 * @since 1.0.0
 */
@Data
public class Pager<T> extends Page<T> {

    /**
     * 模糊查询关键词
     */
    private String word;

    /**
     * 查询状态
     */
    private StatusEnum status;

    /**
     * 起始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 因为后台接收的日期只包含 年月日
     * 所以设置结束日期为当天的 23:59:59
     *
     * @param endTime
     * @return
     */
    public Pager<T> setEndTime(Date endTime) {
        this.endTime = endTime;
        endTime.setTime(endTime.getTime() + 86400000 - 1);
        return this;
    }

    /**
     * -1在前端表示查询所有
     *
     * @param status
     * @return
     */
    public Pager<T> setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }
}