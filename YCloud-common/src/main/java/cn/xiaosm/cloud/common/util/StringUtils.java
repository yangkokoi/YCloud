package cn.xiaosm.cloud.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author Young
 * @create 2022/4/6
 * @since 1.0.0
 */
public class StringUtils {

    public static boolean isBlank (CharSequence str) {
        return StrUtil.isBlank(str);
    }

}