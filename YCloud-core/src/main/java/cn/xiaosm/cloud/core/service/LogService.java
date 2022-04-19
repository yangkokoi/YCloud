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

import cn.xiaosm.cloud.core.entity.Log;
import cn.xiaosm.cloud.core.entity.vo.LogVO;
import cn.xiaosm.cloud.core.entity.vo.Pager;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public interface LogService extends BaseService<Log> {


    Pager<Log> listOfPage(Pager<Log> page, LogVO log);
}