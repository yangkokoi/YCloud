/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorMapper
 * Author:   Young
 * Date:     2020/6/16 10:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.mapper;

import cn.xiaosm.cloud.core.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Update("UPDATE `task` SET `status` = '2' WHERE `id` = #{id}")
    int updateStatusById(Integer id);

}