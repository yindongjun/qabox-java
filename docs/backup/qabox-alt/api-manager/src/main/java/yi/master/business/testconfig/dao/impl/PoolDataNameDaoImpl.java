package yi.master.business.testconfig.dao.impl;

import org.springframework.stereotype.Repository;
import yi.master.business.base.dao.impl.BaseDaoImpl;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.dao.PoolDataNameDao;

import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:19
 */
@Repository("poolDataNameDao")
public class PoolDataNameDaoImpl extends BaseDaoImpl<PoolDataName> implements PoolDataNameDao {
    @Override
    public List<PoolDataName> listDataName(Integer poolId) {
        String hql = "From PoolDataName t where t.dataPool.poolId=:poolId";
        return getSession().createQuery(hql)
                .setInteger("poolId", poolId)
                .setCacheable(true)
                .list();
    }

    @Override
    public PoolDataName findByName(String name, Integer poolId) {
        String hql = "From PoolDataName t where t.name=:name and t.dataPool.poolId=:poolId";
        return (PoolDataName) getSession().createQuery(hql)
                .setString("name", name).setInteger("poolId", poolId)
                .setCacheable(true).uniqueResult();
    }
}
