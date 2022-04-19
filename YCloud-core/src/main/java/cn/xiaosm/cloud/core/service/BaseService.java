/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: BaseService
 * Author:   Young
 * Date:     2020/6/13 15:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.service;

import cn.hutool.core.collection.ListUtil;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * 〈一句话功能简述〉
 * 〈通用 cn.xiaosm.step.ocr.cn.xiaosm.cloud.core.service〉
 *
 * @author Young
 * @create 2020/6/13
 * @since 1.0.0
 */
public interface BaseService<DOMAIN> extends IService<DOMAIN> {

    default Pager<DOMAIN> listOfPage(Pager<DOMAIN> pager, QueryWrapper<DOMAIN> wrapper) {
        return pager;
    };

}