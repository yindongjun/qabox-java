package yi.master.business.testconfig.dao;

import yi.master.business.base.dao.BaseDao;
import yi.master.business.testconfig.bean.PoolDataValue;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:14
 */
public interface PoolDataValueDao extends BaseDao<PoolDataValue> {

    /**
     *  更新值
     * @author xuwangcheng
     * @date 2021/1/6 9:06
     * @param nameId nameId
     * @param itemId itemId
     * @param value value
     * @return {@link int}
     */
    int updateNameValue (Integer nameId, Integer itemId, String value);

    /**
     *  获取指定的value
     * @author xuwangcheng
     * @date 2021/1/6 10:06
     * @param nameId nameId
     * @param itemId itemId
     * @return {@link PoolDataValue}
     */
    PoolDataValue get (Integer nameId, Integer itemId);
}
