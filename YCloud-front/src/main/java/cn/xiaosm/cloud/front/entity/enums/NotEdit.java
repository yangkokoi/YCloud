package cn.xiaosm.cloud.front.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 允许编辑的文件
 *
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public enum NotEdit {
    IMG( "img")
    , PNG( "png")
    , HTML("")
    ;

    @EnumValue
    @JsonValue
    private final String label;

    NotEdit(String label) {
        this.label = label;
    }

}