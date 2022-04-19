package cn.xiaosm.cloud.front.exception;

import cn.xiaosm.cloud.common.exception.CanShowException;

/**
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */
public class ResourceException extends CanShowException {

    public ResourceException(String message) {
        super(message);
    }
}