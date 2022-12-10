package yi.master.business.message.dao;

import java.util.List;

import yi.master.business.base.dao.BaseDao;
import yi.master.business.message.bean.TestData;

/**
 * 测试数据Dao接口类
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,20170502
 *
 */
public interface TestDataDao extends BaseDao<TestData>{
	
	/**
	 * 更新某个属性
	 * @param dataId
	 * @param dataName 属性名
	 * @param dataValue 要更新的值
	 */
	void updateDataValue(Integer dataId, String dataName, String dataValue);
	
	/**
	 * 通过数据标记来查找测试数据
	 * @param dataDiscr
	 * @param messageSceneId
	 * @return
	 */
	TestData findByDisrc(String dataDiscr, Integer messageSceneId);
	
	/**
	 * 根据场景获取一定数量的测试数据
	 * @param messageSceneId
	 * @param count
	 * @return
	 */
	List<TestData> getDatasByScene(Integer messageSceneId, int count);
}
