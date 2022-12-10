package yi.master.business.testconfig.dao;

import yi.master.business.base.dao.BaseDao;
import yi.master.business.testconfig.bean.PoolDataName;

import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:14
 */
public interface PoolDataNameDao extends BaseDao<PoolDataName> {

    /**
     *  获取指定资源池的数据变量名
     * @author xuwangcheng
     * @date 2021/1/3 14:50
     * @param poolId poolId
     * @return {@link List}
     */
    List<PoolDataName> listDataName (Integer poolId);

    /**
     *  根据名称查询池变量
     * @author xuwangcheng
     * @date 2021/1/3 15:28
     * @param name name
     * @param poolId poolId
     * @return {@link PoolDataName}
     */
    PoolDataName findByName (String name, Integer poolId);
}
