/**
 * Copyright: 2019-2020，小树苗(www.xiaosm.cn)
 * FileName: RoleService
 * Author:   Young
 * Date:     2020/6/18 15:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Young         修改时间           版本号             描述
 */
package cn.xiaosm.cloud.front.mapper;

import cn.xiaosm.cloud.front.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈〉
 *
 * @author Young
 * @create 2020/6/18
 * @since 1.0.0
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 根据 hash 值获取文件名
     * @param uuid
     * @return
     */
    @Select("SELECT `name` FROM `resource` WHERE `uuid` = #{uuid}")
    String selectNameByUUID(String uuid);

    /**
     * 根据 hash 值获取文件
     * @param uuid
     * @return
     */
    @Select("SELECT * FROM `resource` WHERE `uuid` = #{uuid}")
    Resource selectByUUID(String uuid);


    @Select("SELECT * FROM `resource` WHERE `bucket_id` = #{bucketId} AND `parent_id` = #{parentId}")
    List<Resource> listRoot(int parentId, int bucketId);

    @Select("SELECT `id` FROM `resource` WHERE `bucket_id` = #{bucketId} AND `path` = #{path}")
    Integer selectIdByBucketAndPath(int bucketId, String path);

    @Select("SELECT * FROM `resource` WHERE `parent_id` = #{parentId}")
    List<Resource> listByParentId(int parentId);

    @Select("SELECT * FROM `resource` WHERE `id` = #{id} AND `user_id` = #{userId}")
    Resource selectByIdAndUser(int id, int userId);

    @Select("SELECT * FROM `resource` WHERE `uuid` = #{uuid} AND `user_id` = #{userId}")
    Resource selectByUUIDAndUser(String uuid, int userId);

    @Select("SELECT * FROM `resource` WHERE `id` = #{id} AND `bucket_id` = #{bucketId}")
    Resource selectByIdAndBucket(int id, int bucketId);

}