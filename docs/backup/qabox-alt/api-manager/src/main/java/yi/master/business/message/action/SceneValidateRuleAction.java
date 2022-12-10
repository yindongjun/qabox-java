package yi.master.business.message.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.SceneValidateRule;
import yi.master.business.message.service.SceneValidateRuleService;

import java.util.List;

/**
 * 报文场景验证结果Action
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,2017.3.6
 */

@Controller
@Scope("prototype")
public class SceneValidateRuleAction extends BaseAction<SceneValidateRule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer messageSceneId;
	
	private SceneValidateRuleService sceneValidateRuleService;

	private Integer configId;
	
	@Autowired
	public void setSceneValidateRuleService(
			SceneValidateRuleService sceneValidateRuleService) {
		super.setBaseService(sceneValidateRuleService);
		this.sceneValidateRuleService = sceneValidateRuleService;
	}


	@Override
	public String edit() {
		if (model.getTestConfig().getConfigId() == null) {
			model.setTestConfig(null);
		}
		if (model.getMessageScene().getMessageSceneId() == null) {
			model.setMessageScene(null);
		}
		return super.edit();
	}

	/**
	 * 全文验证规则更新
	 * @return
	 */
	public String validateFullEdit() {
		sceneValidateRuleService.updateValidate(model.getValidateId(), model.getValidateValue(), model.getParameterName());
		return SUCCESS;
	}
	
	/**
	 * 获取所有的节点验证规则
	 * @return
	 */
	public String getValidates() {
		List<SceneValidateRule> rules = sceneValidateRuleService.getParameterValidate(messageSceneId);

		setData(rules);
		return SUCCESS;
	}
	
	/**
	 * 更新节点验证规则的可用状态
	 * @return
	 */
	public String updateValidateStatus() {
		sceneValidateRuleService.updateStatus(model.getValidateId(), model.getStatus());
		return SUCCESS;
	}

	/**
	 *  获取测试配置下的公共验证规则
	 * @author xuwangcheng
	 * @date 2019/11/28 21:50
	 * @param
	 * @return {@link String}
	 */
	public String getConfigValidates () {
		List<SceneValidateRule> rules = sceneValidateRuleService.getConfigRules(configId);
		setData(rules);
		return SUCCESS;
	}
	
	/***********************************GET-SET**********************************************************/
	public void setMessageSceneId(Integer messageSceneId) {
		this.messageSceneId = messageSceneId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
}
