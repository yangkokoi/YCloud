package cn.xiaosm.cloud.core.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author Young
 * @create 2021/4/15
 * @since 1.0.0
 */
public enum UserOpenType implements IEnum<String> {
    QQ("QQ", "QQ快捷登录")
    ;

    // @EnumValue
    private final String type;
    private final String desc;

    UserOpenType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}