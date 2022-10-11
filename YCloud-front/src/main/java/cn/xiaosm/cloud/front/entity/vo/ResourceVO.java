package cn.xiaosm.cloud.front.entity.vo;

import cn.xiaosm.cloud.core.entity.BaseEntity;
import cn.xiaosm.cloud.front.entity.Resource;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Young
 * @create 2022/4/9
 * @since 1.0.0
 */
@Data
public class ResourceVO extends BaseEntity {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String bucketName;
    /* 以下为源属性 */
    private Integer id;
    // 文件名
    private String name;
    // 文件uuid
    private String uuid;
    // 文件大小，单位字节
    private Long size;
    // 文件类型
    private String type;
    private boolean edit;

    public ResourceVO() {}

    public ResourceVO(Resource resource) {
        this.setId(resource.getId());
        this.setName(resource.getName());
        this.setType(resource.getType());
        this.setEdit(resource.isEdit());
        this.setSize(resource.getSize());
        this.setUuid(resource.getUuid());
        this.setUpdateTime(resource.getUpdateTime());
        this.setCreateTime(resource.getCreateTime());
    }

}