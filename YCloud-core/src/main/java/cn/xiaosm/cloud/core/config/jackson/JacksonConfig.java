package cn.xiaosm.cloud.core.config.jackson;

import cn.xiaosm.cloud.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

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
     * error to avoid cn.xiaosm.step.ocr.exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 序列化枚举值为数据库存储值
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        SimpleModule module = new SimpleModule();
        // 添加时间序列化
        module.addSerializer(LocalDateTime.class, new LocalDateTimeJsonSerializer());
        mapper.registerModule(module);
        return mapper;
    }

}


