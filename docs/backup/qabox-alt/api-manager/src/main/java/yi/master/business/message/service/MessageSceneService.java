package yi.master.business.message.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Message;
import yi.master.business.message.bean.MessageScene;

import java.util.List;

/**
 * 报文场景service接口
 * @author xuwangcheng
 * @version 1.0.0,2017.3.6
 */
public interface MessageSceneService extends BaseService<MessageScene>{
	
	/**
	 * 更新场景的验证规则
	 * @param messageSceneId  场景id
	 * @param validateRuleFlag 规则标志 0 1 2
	 */
	void updateValidateFlag(Integer messageSceneId,String validateRuleFlag);
	
	/**
	 * 查找对应测试集下的测试场景<br>
	 * 并且测试场景对应的测试报文和接口状态都为可用
	 * @param setId
	 * @return
	 */
	List<MessageScene> getBySetId(Integer setId);
	
	/**
	 * 查询场景对应的接口信息
	 * @param messageSceneId
	 * @return
	 */
	InterfaceInfo getInterfaceOfScene(Integer messageSceneId);
	
	/**
	 * 查询场景对应的报文信息
	 * @param messageSceneId
	 * @return
	 */
	Message getMessageOfScene(Integer messageSceneId);

	/**
	 * 更新返回示例报文
	 * @param messageSceneId
	 * @param response
	 */
	void updateResponseExample (Integer messageSceneId, String response);

	/**
	 *  保存测试场景
	 * @author xuwangcheng
	 * @date 2019/11/15 17:05
	 * @param messageScene messageScene
	 * @param createDefaultData createDefaultData 是否创建默认的数据
	 * @param variableId variableId 是否根据模板创建默认的验证
	 * @return {@link Integer} id
	 */
	Integer save(MessageScene messageScene, Boolean createDefaultData, Integer variableId);

	/**
	 *  复制场景
	 * @author xuwangcheng
	 * @date 2020/8/3 15:42
	 * @param scenes scenes
	 * @param copyParams copyParams
	 *
	 */
	void copyScene (List<MessageScene> scenes, List<String> copyParams);
}
