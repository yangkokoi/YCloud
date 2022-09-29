package cn.xiaosm.cloud.core.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Young
 * @create 2021/4/16
 * @since 1.0.0
 */
@Configuration
public class JacksonConfig {

    /**
     * 解决 jackson 序列化报错问题
     * 新增对枚举的反序列化操作
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 序列化枚举值为数据库存储值
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        SimpleModule module = new SimpleModule();
        // 添加时间序列化
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        // 添加时间反序列化
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    // @Bean
    // public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
    //     return builder -> {
    //         builder.serializerByType(LocalDateTime.class, new LocalDateTimeJsonSerializer());
    //         builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
    //     };
    // }

    /**
     * 序列化
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(value.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        }
    }

    /**
     * 反序列化
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getValueAsLong()), ZoneOffset.of("+8"));
        }
    }
}


