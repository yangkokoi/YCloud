package cn.xiaosm.cloud.security.entity;

/**
 * @author Young
 * @create 2022/9/29
 * @since 1.0.0
 */
public enum TokenType {

    LOGIN( "用户登录")
    , SHARE( "访问分享资源" )
    , DEFAULT("默认用户")
    ;

    private final String name;

    TokenType(String name) {
        this.name = name;
    }


}