/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: MenuVO
 * Author:   Young
 * Date:     2020/6/19 16:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.dto;

import cn.xiaosm.cloud.common.exception.SQLOperateException;
import cn.xiaosm.cloud.core.entity.Menu;
import cn.xiaosm.cloud.core.entity.enums.StatusEnum;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/19
 * @since 1.0.0
 */
@Data
public class MenuQueryDTO {

    private String showType; // 数据展示格式默认为 链表
    private boolean includeButton; // 是否包含按钮
    private Integer parentMenu;
    private StatusEnum status;

    public MenuQueryDTO setParentMenu(Integer parentMenu) {
        this.parentMenu = parentMenu == null || parentMenu <= 0 ? 1 : parentMenu;
        return this;
    }

    public void setShowType(String showType) {
        this.showType = StringUtils.isBlank(showType) ? "list" : showType;
    }


    public String getShowType() {
        return showType;
    }

    public boolean isIncludeButton() {
        return includeButton;
    }

    public MenuQueryDTO setIncludeButton(boolean includeButton) {
        this.includeButton = includeButton;
        return this;
    }
}