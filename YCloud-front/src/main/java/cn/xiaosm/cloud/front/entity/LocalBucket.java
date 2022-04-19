package cn.xiaosm.cloud.front.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Young
 * @create 2022/4/5
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName("storage")
public class LocalBucket extends Bucket {




}