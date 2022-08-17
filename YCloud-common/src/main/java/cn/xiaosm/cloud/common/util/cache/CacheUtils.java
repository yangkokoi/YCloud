package cn.xiaosm.cloud.common.util.cache;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.xiaosm.cloud.common.util.SpringContextUtils;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public class CacheUtils {

    static {
        // 判断是否使用了redis缓存
        // try {
        //     boolean b = ClassLoaderUtil.isPresent("org.springframework.data.redis.core.RedisTemplate");
        //     if (b) {
        //         handler = (CacheHandler) SpringContextUtils.createClass("cn.xiaosm.cloud.common.util.cache.RedisCache");
        //     } else {
        //         // 未使用则用java来进行存储对象
        //         handler = new JavaCache();
        //     }
        // } catch (Exception e) {
        //     System.err.println(e.getMessage() + "\nRedis连接失败，将使用其他方式缓存数据");
        //     handler = new JavaCache();
        // }
        handler = new JavaCache();
    }

    // 缓存处理器
    private static CacheHandler handler;


    public static void set(String key, Object value) {
        set(key, value, 0);
    }

    /**
     * 保存对象
     * @param key
     * @param value
     * @param exp 到期时间 单位 毫秒
     */
    public static void set(String key, Object value, long exp) {
        handler.set(key, value, exp);
    }

    /**
     * 获取对象
     * @param key
     * @return
     */
    public static Object get(String key) {
        return handler.get(key);
    }

    public static Object get(String key, boolean update) {
        return handler.get(key, update);
    }

    /**
     * 删除对象
     * @param key
     */
    public static void del(String key) {
        handler.delete(key);
    }

}