/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuService
 * Author:   Young
 * Date:     2020/6/16 10:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.common.util.cache.CacheUtils;
import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.dto.MenuDTO;
import cn.xiaosm.cloud.core.entity.enums.MenuType;
import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import cn.xiaosm.cloud.core.entity.vo.Pager;
import cn.xiaosm.cloud.core.mapper.MenuMapper;
import cn.xiaosm.cloud.core.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private static final Integer ROOT_ID = 1; // 根目录的ID

    @Autowired
    MenuMapper menuMapper;

    @Override
    public boolean save(Menu menu) {
        this.clearAttr(menu);
        try {
            return menuMapper.insert(menu) == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLOperateException(menu.getPermission() + "已存在，请勿重复添加");
        }
    }

    @Override
    @Transactional
    public int removeById(Integer id) {
        List<Menu> list = this.list(new QueryWrapper<Menu>().eq("parent_menu_id", id));
        if (!list.isEmpty()) {
            throw new SQLOperateException(
                    StrUtil.format("删除失败###{}此菜单下含有子菜单，本次删除操作已被取消")
            );
        }
        int i = menuMapper.deleteById(id);
        return i;
    }

    @Override
    public int removeByIds(Set<Integer> ids) {
        int count = 0;
        for (Integer id : ids) {
            count += this.removeById(id);
        }
        return count;
    }

    @Override
    public Pager<Menu> listOfPage(Pager<Menu> pager, QueryWrapper<Menu> queryWrapper) {
        Pager<Menu> menuPage = menuMapper.selectPage(pager, queryWrapper);
        return menuPage;
    }

    @Override
    public List<Menu> getByParentId(Integer parentId) {
        return this.getByParentId(parentId, false);
    }

    /**
     * 根据类型，清除不必要的内容
     * @param menu
     */
    // @SneakyThrows
    public void clearAttr(Menu menu) {
        if (menu.getType() == MenuType.LEVEL_1) {
            menu.setComponent(null);
        } else if (menu.getType() == MenuType.PAGE) {

        } else {
            menu.setComponent(null);
            menu.setPath(null);
        }
    }

    /**
     * 刷新菜单
     */
    @Override
    public void refreshMenus() {
        CacheUtils.set("MenuList", this.list());
    }

    /**
     * 根据父级菜单获取子菜单
     * @param parentMenu 父级菜单 id
     * @param recursive 是否递归获取子级的子级
     * @return
     */
    @Override
    public List<Menu> getByParentId(Integer parentMenu, boolean recursive) {
        List<Menu> menuList = this.getAll(true);
        if (recursive) {
            // 获取第一级子菜单
            List<Menu> reList = menuList.stream()
                .filter(el -> parentMenu.equals(el.getParentMenuId()))
                .collect(Collectors.toList());
            return null;
        } else {
            return menuList.stream()
                .filter(el -> parentMenu.equals(el.getParentMenuId()))
                .collect(Collectors.toList());
        }
    }

    /**
     * 根据父菜单获取子菜单，以树的形式()
     * @param parentId
     * @return
     */
    @Override
    public List<MenuDTO> getByParentIdOfTree(Integer parentId, boolean includeButton) {
        parentId = parentId == null || parentId < ROOT_ID ? ROOT_ID : parentId;
        return this.buildTree(this.getAll(includeButton), parentId);
    }

    /**
     * 根据角色id查询菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> getByRoleId(Integer roleId) {
        return roleId == 1 ?
            this.getAll(true) : menuMapper.selectAllByRoleId(roleId);
    }

    /**
     * 根据角色id查询菜单
     * (查询不包括 根目录)
     *
     * @param roleIds 1,2,3
     * @return
     */
    @Override
    public List<Menu> getByRoleIds(String roleIds) {
        if (StrUtil.isBlank(roleIds)) return new ArrayList<>();
        List<Menu> list = new ArrayList<>();
        for(String id : roleIds.split(",")) {
            list.addAll(menuMapper.selectAllByRoleId(Integer.valueOf(id)));
        }
        // 去除重复菜单
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    /**
     * 构建菜单树 （默认不包含父级菜单）
     * @param menuList
     * @return
     */
    @Override
    public List<MenuDTO> buildTree(List<Menu> menuList) {
        return this.buildTree(menuList, ROOT_ID, false);
    }

    /**
     * 构建菜单树 （默认不包含父级菜单）
     *
     * @param menuList
     * @param parentId
     * @return
     */
    @Override
    public List<MenuDTO> buildTree(List<Menu> menuList, Integer parentId) {
        return this.buildTree(menuList, parentId, false);
    }

    /**
     * 构建菜单树，这里是先将父级菜单及其子菜单过滤出来
     *
     * @param menuList
     * @param parentId
     * @param includeParent 是否包含父菜单
     * @return
     */
    @Override
    public List<MenuDTO> buildTree(List<Menu> menuList, Integer parentId, boolean includeParent) {
        // 转换为子类
        List<MenuDTO> menuList2 = menuList.stream()
            .map(menu -> {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtils.copyProperties(menu, menuDTO);
                return menuDTO;
            }).collect(Collectors.toList());
        List<MenuDTO> menuTree = menuList2.stream()
            .filter(el -> includeParent ? parentId.equals(el.getId()) : parentId.equals(el.getParentMenuId()))
            .collect(Collectors.toList());
        return this.buildTree(menuTree, menuList2);
    }

    /**
     * 构建菜单树
     * @param menuTree 初始菜单树，需要有顶级菜单（整理后的菜单）
     * @param menuList 链表菜单，通过遍历此菜单构建树（未整理的菜单）
     * @return
     */
    private List<MenuDTO> buildTree(List<MenuDTO> menuTree, List<MenuDTO> menuList) {
        if (menuList.isEmpty() || menuTree.isEmpty()) return menuTree;
        List<MenuDTO> children = new LinkedList<>();
        Iterator<MenuDTO> it;
        MenuDTO next;
        for (MenuDTO menu : menuTree) {
            // 如果是按钮，将不会继续找它的子级菜单
            if (menu.getType() == MenuType.BUTTON) continue;
            else {
                it = menuList.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    if (next.getParentMenuId() == menu.getId()) {
                        // next.setParent(menuDTO);
                        children.add(next);
                        it.remove();
                    }
                }
                menu.setChildren(children);
                children = new LinkedList<>();
                buildTree(menu.getChildren(), menuList);
            }
        }
        return menuTree;
    }

    /**
     * 获取所有的菜单
     * @param includePermission 是否包含权限
     * @return
     */
    @Override
    public List<Menu> getAll(boolean includePermission) {
        List<Menu> list = (List<Menu>) CacheUtils.get("MenuList");
        if (Objects.isNull(list) || list.isEmpty()) {
            list = menuMapper.selectList(null);
            CacheUtils.set("MenuList", list);
        }
        // 按照规则取出菜单
        if (!includePermission) {
            list = list.stream()
                .filter(el -> el.getType() != MenuType.BUTTON)
                .collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 获取所有的菜单
     * @param includePermission 是否包含权限
     * @return
     */
    @Override
    public List<Menu> getAllEnable(boolean includePermission) {
        return this.getAll(includePermission).stream()
            .filter(el -> el.getStatus() == StatusEnum.ENABLED)
            .collect(Collectors.toList());
    }
}