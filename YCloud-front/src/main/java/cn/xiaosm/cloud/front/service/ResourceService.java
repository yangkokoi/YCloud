package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.core.service.BaseService;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.ResourceDTO;
import cn.xiaosm.cloud.front.entity.vo.ResourceVO;
import cn.xiaosm.cloud.front.entity.dto.UploadDTO;

import java.io.File;
import java.util.List;

/**
 * @author Young
 * @create 2022/3/24
 * @since 1.0.0
 */
public interface ResourceService extends BaseService<Resource> {

    Resource getByCurrentUser(Integer id);

    List<Resource> getByCurrentUser(String ids);

    List<Resource> listByIds(String ids);

    List<Resource> list(ResourceDTO resource);

    boolean rename(cn.xiaosm.cloud.front.entity.dto.ResourceDTO resource);

    boolean delete(cn.xiaosm.cloud.front.entity.dto.ResourceDTO resource);

    File getLocalFile(Resource resource);

    List<String> upload(UploadDTO uploadDTO);

    ResourceDTO download(cn.xiaosm.cloud.front.entity.dto.ResourceDTO resource);

    String create(cn.xiaosm.cloud.front.entity.dto.ResourceDTO resource);

    ResourceDTO preview(cn.xiaosm.cloud.front.entity.dto.ResourceDTO resourceDTO);

    ResourceDTO offlineDownload(String url);
}