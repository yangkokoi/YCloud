/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: OperatorServiceImpl
 * Author:   Young
 * Date:     2020/6/16 10:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.service.impl;

import cn.xiaosm.cloud.core.entity.Log;
import cn.xiaosm.cloud.core.entity.vo.LogVO;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import cn.xiaosm.cloud.core.mapper.LogMapper;
import cn.xiaosm.cloud.core.service.LogService;
import cn.xiaosm.cloud.core.util.WrapperUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogMapper logMapper;

    public boolean save(Log log) {
        log.setCreateTime(new Date());
        this.addEntityAsync(log);
        logger.debug("记录日志=>{}", log.getTitle());
        return true;
    }

    @Async
    public void addEntityAsync(Log log) {
        // 查询ip地址属于哪
        // http://ip-api.com/json/114.114.114.114?lang=zh-CN
        logMapper.insert(log);
    }

    @Override
    public Pager<Log> listOfPage(Pager<Log> pager, LogVO log) {
        QueryWrapper<Log> wrapper = new QueryWrapper();
        WrapperUtils.bindSearch(wrapper, pager, "title");
        wrapper.eq("type", log.getType())
            .orderByDesc("create_time");
        return this.listOfPage(pager, wrapper);
    }

    @Override
    public Pager<Log> listOfPage(Pager<Log> pager, QueryWrapper<Log> wrapper) {
        return this.page(pager, wrapper);
    }

}