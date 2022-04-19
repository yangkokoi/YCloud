/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: Prop
 * Author:   Young
 * Date:     2020/6/18 16:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity;

import cn.xiaosm.cloud.core.entity.enums.PropType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("properties")
public class Prop {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String propKey;
    private String propValue;
    private PropType type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date createTime;

}