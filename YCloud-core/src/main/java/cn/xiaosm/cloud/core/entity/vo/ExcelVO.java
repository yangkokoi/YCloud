/**
 * Copyright: 2019-2020
 * FileName: ExcelVO
 * Author:   Young
 * Date:     2020/12/1 20:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/12/1
 * @since 1.0.0
 */
@Data
public class ExcelVO {

    private String name;
    private List<Column> columns;
    private String data;

    @Data
    public static class Column {
        private String key;
        private String value;
    }
}