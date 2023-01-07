package cn.xiaosm.cloud.front.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.common.util.SpringContextUtils;
import cn.xiaosm.cloud.core.config.security.SecurityUtils;
import cn.xiaosm.cloud.front.config.UploadConfig;
import cn.xiaosm.cloud.front.entity.Bucket;
import cn.xiaosm.cloud.front.entity.Chunk;
import cn.xiaosm.cloud.front.entity.Resource;
import cn.xiaosm.cloud.front.entity.dto.UploadDTO;
import cn.xiaosm.cloud.front.exception.ResourceException;
import cn.xiaosm.cloud.front.mapper.ChunkMapper;
import cn.xiaosm.cloud.front.service.ChunkService;
import cn.xiaosm.cloud.front.service.ResourceService;
import cn.xiaosm.cloud.front.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author Young
 * @create 2022/12/13
 * @since 1.0.0
 */
@Slf4j
@Service
public class ChunkServiceImpl implements ChunkService {

    @Autowired
    ChunkMapper chunkMapper;

    @Override
    @Transactional
    public boolean save(UploadDTO dto, Bucket bucket, Long parentId) {
        // 检查上传的文件名
        // this.checkNameAndUnique(file.getOriginalFilename(), parentId, bucket.getId()); // 与下面的判断重复
        if (!dto.getCurrentChunkSize().equals(dto.getFile().getSize())) {
            log.error("文件大小不一致");
            return false;
        }
        String hash = "";
        try {
            hash = DigestUtil.md5Hex(dto.getFile().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 当前分块没有上传
        if (chunkMapper.existByHash(hash) == null) {
            Chunk chunk = new Chunk();
            chunk.setSize(dto.getCurrentChunkSize());
            chunk.setOrder(dto.getChunkNumber());
            chunk.setTotal(dto.getTotalChunks());
            chunk.setFileHash(dto.getIdentifier());
            chunk.setType(2);
            chunk.setHash(hash);
            String fileName = hash + ".data";
            // 数据库存储失败
            if (chunkMapper.insert(chunk) != 1) {
                return false;
            }
            File dest = new File(UploadConfig.CHUNK_PATH, fileName);
            try {
                // 缓存文件存在 && 大小相等直接返回成功
                if (dest.exists() && dest.length() == dto.getCurrentChunkSize()) {
                    return true;
                }
                dto.getFile().transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        // 分块转存完成后，进行整合
        synchronized (SecurityUtils.getLoginUserId()) {
            this.integrateFile(dto, bucket, parentId);
        }
        return true;
    }

    /**
     * 合并分块
     * @param dto
     * @param bucket
     * @param parentId
     * @return
     */
    @Override
    public boolean integrateFile(UploadDTO dto, Bucket bucket, Long parentId) {
        List<Chunk> chunks = chunkMapper.listByFileHash(dto.getIdentifier());
        // 检查所有分块是否都上传完成
        if (chunks.size() != dto.getTotalChunks()) {
            // 没有上传完，或者上传完，数据已经删除了
            return false;
        }

        // 合并之前，检查当前用户的仓库中是否文件存在
        Resource db = SpringContextUtils.getBean(ResourceServiceImpl.class)
            .getAndCheckHashInPath(dto.getIdentifier(), dto.getFilename(), parentId, bucket.getId());
        if (db != null) {
            log.error("当前目录下已有相同文件-{}", db.getName());
            return false;
        }

        chunks.sort(Comparator.comparingInt(Chunk::getOrder));
        String filename = dto.getIdentifier() + "." + FileUtil.extName(dto.getFilename());
        File dest = ResourceServiceImpl.transformFile(bucket.getPathFile(), filename);
        try (RandomAccessFile raf = new RandomAccessFile(dest, "rw");) {
            for (Chunk chunk : chunks) {
                File temp = new File(UploadConfig.CHUNK_PATH, chunk.getHash() + ".data");
                if (!temp.exists()) throw new CanShowException("文件 chunk 不存在", 400);
                integrateFile(raf, new FileInputStream(temp));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CanShowException("文件整合出现问题", 400);
        }
        // 写完以后保存至数据库
        Resource resource = new Resource();
        resource.setPath("/" + dest.getParentFile().getName() + "/" + filename);
        resource.setHash(dto.getIdentifier());
        resource.setName(dto.getFilename());
        resource.setBucketId(bucket.getId());
        resource.setUserId(bucket.getUserId());
        resource.setParentId(parentId);
        resource.setDir(false);
        resource.setSize(dest.length());
        resource.setType(FileUtil.getType(dest));
        SpringContextUtils.getBean(ResourceService.class).save(resource);
        // 删除所有chunk，注：故意这么写的，为了不走事物，即时生效 // 先不删除，后期考虑怎么处理
        // this.deleteByIds(chunks.stream().map(Chunk::getId).toList());
        log.info("文件整合完成");
        return true;
    }

    @Override
    public Chunk getByFileHash(String fileHash) {
        return chunkMapper.selectByFileHash(fileHash);
    }

    @Override
    public int deleteByIds(Collection ids) {
        return chunkMapper.deleteBatchIds(ids);
    }

    private void integrateFile(RandomAccessFile raf, InputStream in) throws IOException {
        byte[] buff = new byte[4096];
        int len = 0;
        while ((len = in.read(buff)) != -1) {
            raf.write(buff, 0, len);
        }
        if (in != null) {
            in.close();
        }
    }

    @Override
    public Integer[] getUploaded(String hash) {
        return chunkMapper.listOrderByFileHash(hash);
    }

}