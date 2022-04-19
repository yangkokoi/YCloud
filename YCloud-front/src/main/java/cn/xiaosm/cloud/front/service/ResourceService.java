package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.core.service.BaseService;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.UploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface ResourceService extends BaseService<Resource> {

    boolean rename(ResourceDTO resource);

    boolean delete(ResourceDTO resource);

    List<String> upload(UploadVO uploadVO);

    ResourceDTO download(ResourceDTO resource);

    boolean create(ResourceDTO resource);
}