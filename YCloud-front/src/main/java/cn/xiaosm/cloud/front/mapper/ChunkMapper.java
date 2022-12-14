package cn.xiaosm.cloud.front.mapper;

import cn.xiaosm.cloud.front.entity.Chunk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Young
 * @create 2022/12/13
 * @since 1.0.0
 */
@Repository
public interface ChunkMapper extends BaseMapper<Chunk> {

    @Select("SELECT `order` FROM `chunk` WHERE `file_hash` = #{hash}")
    Integer[] listOrderByFileHash(String hash);

    @Select("SELECT 1 FROM `chunk` WHERE `hash` = #{hash} LIMIT 1")
    Integer existByHash(String hash);

    @Select("SELECT * FROM `chunk` WHERE `file_hash` = #{hash}")
    List<Chunk> listByFileHash(String hash);

    @Select("SELECT * FROM `chunk` WHERE `file_hash` = #{hash} LIMIT 1")
    Chunk selectByFileHash(String fileHash);
}