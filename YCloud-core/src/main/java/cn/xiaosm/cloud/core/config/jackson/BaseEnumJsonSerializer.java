package cn.xiaosm.cloud.core.config.jackson;

import cn.xiaosm.cloud.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class BaseEnumJsonSerializer <T extends Enum<T> & BaseEnum> extends JsonDeserializer<T> {

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) {
        System.out.println(p);
        throw new RuntimeException("没有实现反序列化");
    }
}