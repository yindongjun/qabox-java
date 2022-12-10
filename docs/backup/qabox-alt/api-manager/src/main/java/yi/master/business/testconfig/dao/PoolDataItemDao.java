package yi.master.business.testconfig.dao;

import yi.master.business.base.dao.BaseDao;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.dto.ItemNameValueDto;

import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:14
 */
public interface PoolDataItemDao extends BaseDao<PoolDataItem> {
    /**
     *  查询类别数据
     * @author xuwangcheng
     * @date 2021/1/4 19:06
     * @param poolId poolId
     * @param itemId itemId
     * @return {@link List}
     */
    List<ItemNameValueDto> queryItemNameValue(Integer poolId, Integer itemId);

    /**
     *  根据名称查询
     * @author xuwangcheng
     * @date 2021/1/6 13:44
     * @param name name
     * @param poolId poolId
     * @return {@link PoolDataItem}
     */
    PoolDataItem findByName (String name, Integer poolId);

    /**
     *  查询指定池下面的类别
     * @author xuwangcheng
     * @date 2021/1/12 13:41
     * @param poolId poolId
     * @return {@link List}
     */
    List<PoolDataItem> listByPoolId (Integer poolId);
}
