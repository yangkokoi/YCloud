package cn.xiaosm.cloud.core.controller;

import cn.xiaosm.cloud.core.annotation.YAdmin;
import cn.xiaosm.cloud.core.entity.LoginUser;
import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.enums.MenuType;
import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import cn.xiaosm.cloud.core.service.MenuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Young
 * @create 2022/11/17
 * @since 1.0.0
 */

@YAdmin
public class RouterController {

    @Autowired
    MenuService menuService;

    /**
     * 首页
     * @param request
     * @return
     */
    @RequestMapping
    public String index(HttpServletRequest request) {
        if (request.getSession().getAttribute("USER") == null) {
            return "login";
        }
        // 获取当前用户的菜单
        List<Menu> menus = ((LoginUser) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getMenusOriginalOfList();
        // 删除多余菜单
        menus = menus.stream().filter(el -> el.getType() != MenuType.BUTTON).collect(Collectors.toList());
        request.setAttribute("menus", menuService.buildTree(menus));
        return "index";
    }

    /**
     * 首页
     * @param request
     * @return
     */
    @RequestMapping("home")
    public String home(HttpServletRequest request) {
        return "home";
    }

    @RequestMapping("/system/user")
    public String user() {
        return "system/user";
    }

    @RequestMapping("/system/role")
    public String role(HttpServletRequest request) {
        return "system/role";
    }

    @RequestMapping("/system/menu")
    public String menu(HttpServletRequest request) {
        // 获取当前用户的菜单
        List<? extends Menu> menus = ((LoginUser) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getMenus();
        try {
            // 过滤非启用状态的菜单
            Iterator<? extends Menu> iterator = menus.iterator();
            while (iterator.hasNext()) {
                Menu next = iterator.next();
                if (!next.getStatus().equals(StatusEnum.ENABLED)) {
                    menus.remove(next);
                }
            }
            // .filter(el-> el.getStatus() == StatusEnum.ENABLED)
            request.setAttribute("menus", new ObjectMapper().writeValueAsString(menus));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "system/menu";
    }

    @RequestMapping("{path}")
    public String user(@PathVariable("path") String path) {
        return path;
    }

}