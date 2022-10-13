package cn.xiaosm.cloud.front.entity.vo;

import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.Share;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
@Data
public class ShareVO {

    private String id;
    private String password;
    private LocalDateTime deadline;
    private List<ResourceVO> resourceList;

    public ShareVO() {

    }

    public ShareVO(Share share) {
        this.id = share.getId();
        this.password = share.getPassword();
        this.deadline = share.getDeadline();
        if (share instanceof ShareDTO dto) {
            if (null == dto.getResourceList()) return;
            resourceList = new ArrayList<>();
            for (Resource resource : dto.getResourceList()) {
                resourceList.add(new ResourceVO(resource));
            }
        }
    }
}