package yi.master.business.testconfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.dao.PoolDataNameDao;
import yi.master.business.testconfig.service.PoolDataNameService;

import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 17:49
 */
@Service("poolDataNameService")
public class PoolDataNameServiceImpl extends BaseServiceImpl<PoolDataName> implements PoolDataNameService {

    private PoolDataNameDao poolDataNameDao;

    @Autowired
    public void setPoolDataNameDao(PoolDataNameDao poolDataNameDao) {
        super.setBaseDao(poolDataNameDao);
        this.poolDataNameDao = poolDataNameDao;
    }

    @Override
    public List<PoolDataName> listDataName(Integer poolId) {
        return poolDataNameDao.listDataName(poolId);
    }

    @Override
    public PoolDataName findByName(String name, Integer poolId) {
        return poolDataNameDao.findByName(name, poolId);
    }
}
