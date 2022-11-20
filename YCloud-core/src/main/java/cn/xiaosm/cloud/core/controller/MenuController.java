/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuController
 * Author:   Young
 * Date:     2020/6/18 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.common.entity.RespBody;
import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.common.util.RespUtils;
import cn.xiaosm.cloud.core.annotation.LogRecord;
import cn.xiaosm.cloud.core.annotation.YAdmin;
import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.dto.MenuQueryDTO;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import cn.xiaosm.cloud.core.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/18
 * @since 1.0.0
 */
@YAdmin("/api/menu")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('menu:query') or hasRole('admin')")
    public RespBody queryMenus(Pager<Menu> page, MenuQueryDTO menu) {
        if ("tree".equals(menu.getShowType())) {
            // 默认：父级菜单为 0 且不包含按钮
            return RespUtils.success("成功获取菜单列表",
                    menuService.getByParentIdOfTree(menu.getParentMenu(), menu.isIncludeButton()));
        } else {
            QueryWrapper<Menu> wrapper = new QueryWrapper<>();
            if (menu.getParentMenu() == null || menu.getParentMenu().equals(0)) {
                wrapper.eq("id", 1);
            } else {
                wrapper.eq("parent_menu_id", menu.getParentMenu());
            }
            wrapper.orderByAsc("`order`");
            if (Objects.nonNull(menu.getStatus())) wrapper.eq("status", menu.getStatus());
            return RespUtils.success("获取了菜单列表",
                    menuService.page(page, wrapper));
        }

    }

    @PutMapping
    @LogRecord("菜单添加")
    @PreAuthorize("hasAuthority('menu:add') or hasRole('admin')")
    public RespBody saveMenu(@RequestBody Menu menu) {
        boolean b = menuService.save(menu);
        // 重新获取最新的菜单表到内存
        if (b) menuService.refreshMenus();
        return b == true ? RespUtils.success("新增菜单信息成功")
                : RespUtils.fail("新增失败");
    }

    @PostMapping
    @LogRecord("菜单修改")
    @PreAuthorize("hasAuthority('menu:modify') or hasRole('admin')")
    public RespBody modifyMenu(@RequestBody Menu menu) {
        if (menu.getId() <= 5) {
            throw new SQLOperateException("系统保留数据，请勿操作");
        }
        boolean b = menuService.updateById(menu);
        if (b) menuService.refreshMenus();
        return b == true ? RespUtils.success("修改菜单信息成功")
                : RespUtils.fail("修改失败");
    }

    @DeleteMapping
    @LogRecord("菜单删除")
    @PreAuthorize("hasAuthority('menu:delete') or hasRole('admin')")
    public RespBody deleteMenus(@RequestBody Set<Integer> ids) {
        if (ids.stream().filter(el -> el <= 36).count() >= 1) {
            throw new SQLOperateException("系统保留数据，请勿操作");
        }
        int b = menuService.removeByIds(ids);
        if (b > 0) menuService.refreshMenus();
        return RespUtils.success("删除菜单信息成功，本次删除" + b + "条菜单");
    }

}