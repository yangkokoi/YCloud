/**
 * Copyright: 2019-2020
 * FileName: MailVO
 * Author:   Young
 * Date:     2020/6/29 8:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.core.entity.vo;

import lombok.Data;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/29
 * @since 1.0.0
 */
@Data
public class MailVO {

    private String to;
    private String subject;
    private String content;
    private Boolean isHtml;
    private String template;

}