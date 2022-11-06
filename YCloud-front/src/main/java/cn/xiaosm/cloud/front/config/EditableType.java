package cn.xiaosm.cloud.front.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author Young
 * @create 2022/11/6
 * @since 1.0.0
 */
public class EditableType {

    public static String[] types = new String[]{
        "txt", "md",
        "json", "xml", "html", "css", "js", "ts",
        "jsp", "java", "kt", "c", "go", "py"
    };

    public static boolean isEditable(String type) {
        return ArrayUtil.contains(types, type);
    }

}