/**
 * Copyright: 2019-2020
 * FileName: StatusEnum
 * Author:   Young
 * Date:     2020/6/21 15:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 状态枚举
 * 〈〉
 *
 * @author Young
 * @create 2020/6/21
 * @since 1.0.0
 */
public enum StatusEnum implements BaseEnum<Integer> {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    DELETED(2, "删除");

    @EnumValue
    @JsonValue
    private final int value;
    private final String label;

    StatusEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

}