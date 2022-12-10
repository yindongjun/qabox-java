package yi.master.business.message.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.message.bean.SceneValidateRule;

import java.util.List;

public interface SceneValidateRuleService extends BaseService<SceneValidateRule>{
	
	/**
	 * 获取指定messageScene的全文验证规则或者关键字验证规则
	 * @param messageSceneId
	 * @return
	 */
	SceneValidateRule getValidate(Integer messageSceneId, String type);
	
	/**
	 * 更新验证规则，只更新验证值validateValue和parameterName
	 * @param validateId
	 * @param validateValue
	 * @param parameterName
	 */
	void updateValidate(Integer validateId, String validateValue, String parameterName);
	
	/**
	 * 获取指定测试场景的入参验证测试规则
	 * @param messageSceneId
	 * @return
	 */
	List<SceneValidateRule> getParameterValidate(Integer messageSceneId);
	
	/**
	 * 更新验证规则状态
	 * <br>1为不可用  0为可用
	 * @param validateId
	 * @param status
	 */
	void updateStatus(Integer validateId, String status);

	/**
	 *  获取配置下的公共验证规则
	 * @author xuwangcheng
	 * @date 2019/11/28 21:46
	 * @param configId configId
	 * @return {@link List}
	 */
	List<SceneValidateRule> getConfigRules (Integer configId);
}
