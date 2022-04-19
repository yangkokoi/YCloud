package cn.xiaosm.cloud.front.entity.enums;

import cn.xiaosm.cloud.core.entity.enums.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 允许编辑的文件
 *
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public enum FileType implements BaseEnum<String> {
    DIR( "DIR", "directory")
    , FILE( "FILE", "default file")
    ;

    @EnumValue
    @JsonValue
    private final String value;
    private final String detail;

    FileType(String value, String detail) {
        this.value = value;
        this.detail = detail;
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * 枚举反序列话调用该方法
     *
     * @param name
     * @return
     */
    @JsonCreator
    public static FileType des(String name) {
        for (FileType value : FileType.values()) {
            if (name.equals(value.value)) return value;
        }
        return FileType.FILE;
    }

}