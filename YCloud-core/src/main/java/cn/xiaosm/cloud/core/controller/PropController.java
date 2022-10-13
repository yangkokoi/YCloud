// /**
//  * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
//  * FileName: PropertiesController
//  * Author:   Young
//  * Date:     2020/6/18 13:45
//  * Description:
//  * History:
//  * <author>          <time>          <version>          <desc>
//  * Young         修改时间           版本号             描述
//  */
// package cn.xiaosm.cloud.core.controller;
//
// import cn.xiaosm.yadmin.core.annotation.Yadmin;
// import cn.xiaosm.yadmin.core.entity.Prop;
// import cn.xiaosm.yadmin.common.entity.ResponseBody;
// import cn.xiaosm.yadmin.core.service.PropService;
// import cn.xiaosm.yadmin.common.util.ResponseUtils;
// import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.Date;
// import java.util.List;
// import java.util.Objects;
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
// @RequestMapping("/prop")
// public class PropController {
//
//     @Autowired
//     PropService propService;
//
//     @GetMapping
//     @PreAuthorize("hasAuthority('prop:query') or hasRole('admin')")
//     public ResponseBody queryProps(Prop prop) {
//         if (Objects.isNull(prop.getType())) {
//             return ResponseUtils.buildError("请求出错", null);
//         }
//         List<Prop> list = propService.list(new QueryWrapper<Prop>().eq("type", prop.getType()));
//         return ResponseUtils.buildSuccess("", list);
//     }
//
//     @PutMapping
//     @PreAuthorize("hasAuthority('prop:add') or hasRole('admin')")
//     public ResponseBody saveProp(@RequestBody List<Prop> props) {
//         props.stream().forEach(el -> el.setCreateTime(new Date()));
//         boolean b = propService.saveBatch(props);
//         return b == true ? ResponseUtils.buildSuccess("保存成功")
//                 : ResponseUtils.buildFail("保存失败");
//     }
//
//     @PostMapping
//     @PreAuthorize("hasAuthority('prop:modify') or hasRole('admin')")
//     public ResponseBody modifyProp(@RequestBody List<Prop> props) {
//         // boolean b = propService.modifyEntity(prop);
//         // boolean b = propService.updateBatchById(props);
//         boolean b = true;
//         for (Prop prop : props) {
//             b = propService.modifyEntity(prop);
//         }
//         return b ? ResponseUtils.buildSuccess("保存成功")
//                 : ResponseUtils.buildFail("修改失败");
//     }
//
//     @DeleteMapping
//     @PreAuthorize("hasAuthority('prop:delete') or hasRole('admin')")
//     public ResponseBody deleteProps(@RequestBody Set<Integer> ids) {
//         boolean b = propService.removeByIds(ids);
//         return b ? ResponseUtils.buildSuccess("删除成功")
//                 : ResponseUtils.buildFail("删除失败");
//     }
//
// }