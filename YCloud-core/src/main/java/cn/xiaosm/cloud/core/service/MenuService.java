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
package cn.xiaosm.cloud.core.service;

import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.dto.MenuDTO;

import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/16
 * @since 1.0.0
 */
public interface MenuService extends BaseService<Menu> {

    int removeById(Integer id);

    int removeByIds(Set<Integer> ids);

    List<Menu> getByParentId(Integer parentId);

    void refreshMenus();

    List<Menu> getByParentId(Integer parentId, boolean getChild);

    List<MenuDTO> getByParentIdOfTree(Integer parentId, boolean includeButton);

    List<Menu> getByRoleId(Integer roleId);

    List<Menu> getByRoleIds(String roleId);

    List<MenuDTO> buildTree(List<Menu> menuList);

    List<MenuDTO> buildTree(List<Menu> menuList, Integer parentId);

    List<MenuDTO> buildTree(List<Menu> menuList, Integer parentId, boolean includeParent);

    List<Menu> getAll(boolean includeButton);

    List<Menu> getAllEnable(boolean includePermission);
}