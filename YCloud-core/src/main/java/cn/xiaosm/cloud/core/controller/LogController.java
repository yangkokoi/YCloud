// /**
//  * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
//  * FileName: LogController
//  * Author:   Young
//  * Date:     2020/6/18 13:42
//  * Description:
//  * History:
//  * <author>          <time>          <version>          <desc>
//  * Young         修改时间           版本号             描述
//  */
// package cn.xiaosm.cloud.core.controller;
//
// import cn.xiaosm.yadmin.core.annotation.LogRecord;
// import cn.xiaosm.yadmin.core.annotation.Yadmin;
// import cn.xiaosm.yadmin.core.entity.Log;
// import cn.xiaosm.yadmin.common.entity.ResponseBody;
// import cn.xiaosm.yadmin.core.entity.vo.LogVO;
// import cn.xiaosm.yadmin.core.entity.vo.Pager;
// import cn.xiaosm.yadmin.core.service.LogService;
// import cn.xiaosm.yadmin.common.util.ResponseUtils;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.Set;
//
// /**
//  * 〈一句话功能简述〉
//  * 〈〉
//  *
//  * @author Young
//  * @create 2020/6/18
//  * @since 1.0.0
//  */
// @Yadmin
// @RestController
// @RequestMapping("/log")
// public class LogController {
//
//     @Autowired
//     LogService logService;
//
//     @GetMapping
//     @PreAuthorize("hasAuthority('log:query') or hasRole('admin')")
//     public ResponseBody queryLogs(Pager<Log> pager, LogVO logVO) {
//         Page<Log> list = logService.listOfPage(pager, logVO);
//         return ResponseUtils.buildSuccess("获取了日志列表", list);
//     }
//
//     @DeleteMapping
//     @LogRecord("删除日志")
//     @PreAuthorize("hasAuthority('log:delete') or hasRole('admin')")
//     public ResponseBody deleteLogs(@RequestBody Set<Integer> ids) {
//         boolean b = logService.removeByIds(ids);
//         return b == true ? ResponseUtils.buildSuccess("删除日志信息成功")
//                 : ResponseUtils.buildFail("删除日志失败");
//     }
// }