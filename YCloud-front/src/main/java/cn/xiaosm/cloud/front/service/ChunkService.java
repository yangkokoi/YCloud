package cn.xiaosm.cloud.front.service;

import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Chunk;
import cn.xiaosm.cloud.front.entity.dto.UploadDTO;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Young
 * @create 2022/12/13
 * @since 1.0.0
 */
public interface ChunkService {

    boolean save(UploadDTO dto, Bucket bucket, Long parentId);

    Chunk getByFileHash(String fileHash);

    boolean integrateFile(UploadDTO dto, Bucket bucket, Long parentId);

    int deleteByIds(Collection ids);

    Integer[] getUploaded(String hash);
}