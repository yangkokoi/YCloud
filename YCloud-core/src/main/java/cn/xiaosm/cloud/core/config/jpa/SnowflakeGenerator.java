package cn.xiaosm.cloud.core.config.jpa;

import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author Young
 * @create 2022/11/7
 * @since 1.0.0
 */
public class SnowflakeGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return IdUtil.getSnowflake(1, 1);
    }
}