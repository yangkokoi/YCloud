package cn.xiaosm.cloud.common.util.cache;

import cn.hutool.crypto.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Young
 * @create 2021/5/8
 * @since 1.0.0
 */
public class RedisCache implements CacheHandler{

    @Autowired
    private RedisTemplate<String, Object> template;

    @PostConstruct
    public void init() throws Exception{
        String key = MD5.create().toString();
        this.set(key, "0", 0);
        this.delete(key);
    }

    @Override
    public void set(String key, Object value, long exp) {
        if (exp == 0) template.opsForValue().set(key, value);
        else template.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return template.opsForValue().get(key);
    }

    @Override
    public Object get(String key, boolean update) {
        return template.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        template.delete(key);
    }
}