package yi.master.business.testconfig.dao.impl;

import org.springframework.stereotype.Repository;
import yi.master.business.base.dao.impl.BaseDaoImpl;
import yi.master.business.testconfig.bean.PoolDataValue;
import yi.master.business.testconfig.dao.PoolDataValueDao;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:20
 */
@Repository("poolDataValueDao")
public class PoolDataValueDaoImpl extends BaseDaoImpl<PoolDataValue> implements PoolDataValueDao {
    @Override
    public int updateNameValue(Integer nameId, Integer itemId, String value) {
        String hql = "update PoolDataValue p set p.value=:value where p.poolDataItem.itemId=:itemId and p.poolDataName.id=:nameId";
        return getSession().createQuery(hql)
                .setInteger("itemId", itemId)
                .setInteger("nameId", nameId)
                .setString("value", value)
                .executeUpdate();
    }

    @Override
    public PoolDataValue get(Integer nameId, Integer itemId) {
        String hql = "From PoolDataValue p where p.poolDataItem.itemId=:itemId and p.poolDataName.id=:nameId";
        return (PoolDataValue) getSession().createQuery(hql)
                .setInteger("itemId", itemId)
                .setInteger("nameId", nameId)
                .setCacheable(true).uniqueResult();
    }
}
