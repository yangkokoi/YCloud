/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: UserRole
 * Author:   Young
 * Date:     2020/6/15 9:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/15
 * @since 1.0.0
 */
@TableName("user_role")
public class UserRole {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer roleId;

}