package cn.xiaosm.cloud.front.repository;

import cn.xiaosm.cloud.front.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Young
 * @create 2022/11/7
 * @since 1.0.0
 */
@Repository
public interface ResourceDao extends JpaRepository<Resource, Long> {

}