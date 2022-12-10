package yi.master.business.advanced.service;

import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.base.service.BaseService;

import java.util.List;

public interface InterfaceMockService extends BaseService<InterfaceMock> {
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
	 * 获取所有启用状态的 Mock服务
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

    /**
     * 接口场景转换为Mock规则
     * @author xuwangcheng
     * @date 2019/11/25 18:34
     * @param sceneId sceneId
     * @return {@link boolean}
     */
    boolean parseSceneToMock (Integer sceneId, Integer projectId);
}
