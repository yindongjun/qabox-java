package yi.master.business.advanced.dao;

import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.base.dao.BaseDao;

import java.util.List;

public interface InterfaceMockDao extends BaseDao<InterfaceMock>{
	/**
	 * 根据mockUrl查找指定的mock信息
	 * @param mockUrl
     * @param protocolType
	 * @return
	 */
	InterfaceMock findByMockUrl(String mockUrl, String protocolType);
	/**
	 * 更新状态
	 * @param mockId
	 * @param status
	 */
	void updateStatus(Integer mockId, String status);
	/**
	 * 更新验证或者mock配置信息json串
	 * @param mockId
	 * @param settingType
	 * @param configJson
	 */
	void updateSetting(Integer mockId, String settingType, String configJson);
	
	/**
	 * 获取所有启用状态的Socket Mock服务
	 * @return
	 */
	List<InterfaceMock> getEnableMockServer();

	/**
	 * 更新调用次数：包含成功和失败的
	 * @author xuwangcheng
	 * @date 2019/11/22 16:11
	 * @param mockId mockId
	 * @return
	 */
	void updateCallCount(Integer mockId);
}
