/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: PropertiesController
 * Author:   Young
 * Date:     2020/6/18 13:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.YAdmin;
import cn.xiaosm.cloud.core.entity.Prop;
import cn.xiaosm.cloud.core.service.PropService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/18
 * @since 1.0.0
 */
@YAdmin
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    PropService propService;

    @GetMapping
    @PreAuthorize("hasAuthority('prop:query') or hasRole('admin')")
    public String queryProps(Prop prop) {
        List<Prop> list = propService.list(new QueryWrapper<Prop>().eq("type", prop.getType()));
        return "config";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('prop:add') or hasRole('admin')")
    public RespBody saveProp(@RequestBody List<Prop> props) {
        props.stream().forEach(el -> el.setCreateTime(new Date()));
        boolean b = propService.saveBatch(props);
        return b == true ? RespUtils.success("保存成功")
                : RespUtils.fail("保存失败");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('prop:modify') or hasRole('admin')")
    public RespBody modifyProp(@RequestBody List<Prop> props) {
        // boolean b = propService.modifyEntity(prop);
        // boolean b = propService.updateBatchById(props);
        boolean b = true;
        for (Prop prop : props) {
            b = propService.updateById(prop);
        }
        return b ? RespUtils.success("保存成功")
                : RespUtils.fail("修改失败");
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('prop:delete') or hasRole('admin')")
    public RespBody deleteProps(@RequestBody Set<Integer> ids) {
        boolean b = propService.removeByIds(ids);
        return b ? RespUtils.success("删除成功")
                : RespUtils.fail("删除失败");
    }

}