/**
 * Copyright: 2019-2020
 * FileName: LogVO
 * Author:   Young
 * Date:     2020/6/27 8:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.vo;

import cn.hutool.core.util.StrUtil;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/27
 * @since 1.0.0
 */
public class LogVO {

    private String type = "INFO";

    public String getType() {
        return type;
    }

    public LogVO setType(String type) {
        this.type = StrUtil.isBlank(type.trim()) ? "INFO" : type;
        return this;
    }
}