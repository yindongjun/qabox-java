package yi.master.business.testconfig.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.testconfig.bean.PoolDataValue;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 16:56
 */
public interface PoolDataValueService extends BaseService<PoolDataValue> {

    /**
     *  更新类别数据值
     * @author xuwangcheng
     * @date 2021/1/6 10:24
     * @param itemId itemId
     * @param nameId nameId
     * @param value value
     * @return
     */
    void updateItemValue (Integer itemId, Integer nameId, String value);
}
