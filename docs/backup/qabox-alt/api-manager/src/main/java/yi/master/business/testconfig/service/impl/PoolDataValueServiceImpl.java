package yi.master.business.testconfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.bean.PoolDataValue;
import yi.master.business.testconfig.dao.PoolDataValueDao;
import yi.master.business.testconfig.service.PoolDataValueService;
import yi.master.util.FrameworkUtil;

import java.sql.Timestamp;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 17:51
 */
@Service("poolDataValueService")
public class PoolDataValueServiceImpl extends BaseServiceImpl<PoolDataValue> implements PoolDataValueService {

    private PoolDataValueDao poolDataValueDao;

    @Autowired
    public void setPoolDataValueDao(PoolDataValueDao poolDataValueDao) {
        super.setBaseDao(poolDataValueDao);
        this.poolDataValueDao = poolDataValueDao;
    }

    @Override
    public void updateItemValue(Integer itemId, Integer nameId, String value) {
        if (poolDataValueDao.get(nameId, itemId) == null) {
            PoolDataValue poolDataValue = new PoolDataValue();
            poolDataValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
            poolDataValue.setUser(FrameworkUtil.getLoginUser());
            poolDataValue.setValue(value);
            poolDataValue.setPoolDataItem(new PoolDataItem(itemId));
            poolDataValue.setPoolDataName(new PoolDataName(nameId));

            poolDataValueDao.save(poolDataValue);
        } else {
            poolDataValueDao.updateNameValue(nameId, itemId, value);
        }
    }
}
