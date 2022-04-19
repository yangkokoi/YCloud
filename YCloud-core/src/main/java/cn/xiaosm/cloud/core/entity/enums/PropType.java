/**
 * Copyright: 2019-2020
 * FileName: PropEnum
 * Author:   Young
 * Date:     2020/6/28 12:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/28
 * @since 1.0.0
 */
public enum PropType implements BaseEnum<String> {

    EMAIL("email")
    ;

    @EnumValue
    @JsonValue
    private final String value;

    PropType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}