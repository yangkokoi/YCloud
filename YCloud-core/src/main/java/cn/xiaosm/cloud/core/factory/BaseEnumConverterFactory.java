package cn.xiaosm.cloud.core.factory;

/**
 * spring的类型转换器 适用于除了json数据的请求
 * 序列化时取数据
 *
 * @author Young
 * @create 2021/4/15
 * @since 1.0.0
 */
import cn.xiaosm.cloud.core.entity.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter converter = converterMap.get(targetType);
        if(converter == null) {
            converter = new IntegerStrToEnum<T>(targetType);
            converterMap.put(targetType, converter);
        }
        return converter;
    }

    class IntegerStrToEnum<T extends BaseEnum> implements Converter<String, T> {
        // private final Class<T> enumType;
        private Map<String, T> enumMap = new HashMap<>();

        public IntegerStrToEnum(Class<T> enumType) {
            // this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(String.valueOf(e.getValue()), e);
            }
        }

        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if(result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}
