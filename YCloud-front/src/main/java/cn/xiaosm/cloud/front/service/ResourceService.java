package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.core.service.BaseService;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.ResourceVO;
import cn.xiaosm.cloud.front.entity.vo.UploadDTO;

import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface ResourceService extends BaseService<Resource> {

    List<Resource> list(ResourceVO resource);

    boolean rename(ResourceDTO resource);

    boolean delete(ResourceDTO resource);

    List<String> upload(UploadDTO uploadDTO);

    ResourceDTO download(ResourceDTO resource);

    boolean create(ResourceDTO resource);

    ResourceDTO preview(ResourceDTO resourceDTO);

    ResourceDTO offlineDownload(String url);
}