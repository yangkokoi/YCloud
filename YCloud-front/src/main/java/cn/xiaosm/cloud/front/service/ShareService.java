package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.core.service.BaseService;
import cn.xiaosm.cloud.front.entity.Share;
import cn.xiaosm.cloud.front.entity.dto.ShareDTO;

/**
 * @author Young
 * @create 2022/9/20
 * @since 1.0.0
 */
public interface ShareService extends BaseService<Share> {

    ShareDTO create(ShareDTO dto);

    Share checkPass(ShareDTO dto);

    ShareDTO info(ShareDTO share);
}