package cn.xiaosm.cloud.core.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Objects;

/**
 * mybatis-plus查询对象快速包装类
 *
 * @author Young
 * @create 2020/12/11
 * @since 1.0.0
 */
public class WrapperUtils {

    /**
     * 生成用于搜索功能通用的 wrapper 对象
     * @param wrapper
     * @param pager
     * @return
     */
    public static QueryWrapper bindSearch(QueryWrapper wrapper, Pager pager) {
        return bindSearch(wrapper, pager, null);
    }

    /**
     * 生成用于搜索功能通用的 wrapper 对象
     * @param wrapper
     * @param pager
     * @return
     */
    public static QueryWrapper bindSearch(QueryWrapper<?> wrapper, Pager pager, String... columns) {
        if (Objects.nonNull(pager.getStartTime())) {
            wrapper.ge("create_time", pager.getStartTime());
        }
        if (Objects.nonNull(pager.getEndTime())) {
            wrapper.le("create_time", pager.getEndTime());
        }
        if (Objects.nonNull(pager.getStatus())) {
            wrapper.eq("status", pager.getStatus());
        }
        if (StrUtil.isNotBlank(pager.getWord()) && ArrayUtil.isNotEmpty(columns)) {
            for (int i = 0; i < columns.length; i++) {
                if (i > 0) wrapper.or();
                wrapper.like(columns[i], pager.getWord());
            }
        }
        return wrapper;
    }

}