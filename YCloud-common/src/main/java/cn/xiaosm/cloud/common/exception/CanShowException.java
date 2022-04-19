/**
 * Copyright: 2019-2020
 * FileName: CanShowException
 * Author:   Young
 * Date:     2020/6/27 12:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.common.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/27
 * @since 1.0.0
 */
public class CanShowException extends RuntimeException {

    public CanShowException(String message) {
        super(message);
    }

    public CanShowException(String title, String message) {
        super(StrUtil.join("###", title ,message));
    }
}