/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: Role
 * Author:   Young
 * Date:     2020/6/14 10:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import cn.hutool.core.util.ObjectUtil;
import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/14
 * @since 1.0.0
 */
@Data
@TableName(value = "role")
public class Role extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String nameZh;
    @TableField(value = "`desc`")
    private String desc;
    private StatusEnum status;

    public Role setId(Integer id) {
        this.id = id;
        return this;
    }

    public Role setNamePrefix() {
        if (ObjectUtil.isNotNull(this.name) &&
                !this.name.startsWith("ROLE_")) this.name = "ROLE_" + this.name;
        return this;
    }
}