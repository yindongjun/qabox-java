package yi.master.business.testconfig.dao.impl;

import cn.hutool.core.util.StrUtil;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import yi.master.business.base.dao.impl.BaseDaoImpl;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.dao.PoolDataItemDao;
import yi.master.business.testconfig.dto.ItemNameValueDto;

import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:18
 */
@Repository("poolDataItemDao")
public class PoolDataItemDaoImpl extends BaseDaoImpl<PoolDataItem> implements PoolDataItemDao {
    @Override
    public List<ItemNameValueDto> queryItemNameValue(Integer poolId, Integer itemId) {
        String sql = "SELECT p.id as nameId,p.name, v.value,p.default_value as defaultValue FROM at_pool_data_name p LEFT JOIN at_pool_data_value v ON v.name_id = p.id and v.item_id=:itemId WHERE p.pool_id=:poolId order by p.id desc";

        return getSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(ItemNameValueDto.class))
                .setInteger("poolId", poolId)
                .setInteger("itemId", itemId)
                .list();
    }

    @Override
    public PoolDataItem findByName(String name, Integer poolId) {
        String hql = "From PoolDataItem p where p.name=:name and p.dataPool.poolId=:poolId";

        return (PoolDataItem) getSession().createQuery(hql)
                .setCacheable(true)
                .setString("name", StrUtil.trim(name))
                .setInteger("poolId", poolId)
                .uniqueResult();
    }

    @Override
    public List<PoolDataItem> listByPoolId(Integer poolId) {
        String hql = "From PoolDataItem p where p.dataPool.poolId=:poolId";
        return getSession().createQuery(hql).setCacheable(true)
                .setInteger("poolId", poolId).list();
    }
}
