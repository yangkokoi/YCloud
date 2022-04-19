package cn.xiaosm.cloud.front.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * 允许编辑的文件
 *
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public enum BucketType {
    LOCAL( "LOCAL", "用户默认的本地仓库")
    , ALIYUN( "ALIYUN", "aliyun")
    ;

    @EnumValue
    @JsonValue
    private final String name;
    private final String lable;

    BucketType(String name, String lable) {
        this.name = name;
        this.lable = lable;
    }

    public BucketType value(String name) {
        BucketType type = BucketType.valueOf(name);
        return  Objects.isNull(type) ? type : null;
    }

}