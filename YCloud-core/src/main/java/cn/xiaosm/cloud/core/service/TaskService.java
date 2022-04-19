/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorService
 * Author:   Young
 * Date:     2020/6/16 10:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.service;

import cn.xiaosm.cloud.core.entity.Task;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public interface TaskService extends BaseService<Task> {

    Page<Task> listOfPage(Page<Task> page, Task task);

    int removeByIds(Set<Integer> ids);

    int removeById(Integer id);

    int startAllTask();

    int shutAllTask();
}