package yi.master.business.advanced.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.advanced.bean.config.mock.MockRequestValidateConfig;
import yi.master.business.advanced.bean.config.mock.MockResponseConfig;
import yi.master.business.advanced.bean.config.mock.MockValidateRuleConfig;
import yi.master.business.advanced.enums.MockConfigSettingType;
import yi.master.business.advanced.service.InterfaceMockService;
import yi.master.business.base.action.BaseAction;
import yi.master.business.base.dto.ParseMessageToNodesOutDTO;
import yi.master.business.message.bean.Parameter;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.coretest.message.test.mock.MockServer;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 接口mock相关接口
 * @author xuwangcheng
 * @version 20180612
 *
 */
@Controller
@Scope("prototype")
public class InterfaceMockAction extends BaseAction<InterfaceMock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(InterfaceMockAction.class);
	
	private InterfaceMockService interfaceMockService;
	
	private String message;

	private Integer messageSceneId;
	
	@Autowired
	public void setInterfaceMockService(
			InterfaceMockService interfaceMockService) {
		super.setBaseService(interfaceMockService);
		this.interfaceMockService = interfaceMockService;
	}



	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}

	public String updateStatus() {
		interfaceMockService.updateStatus(model.getMockId(), model.getStatus());
        MockServer.handleMockServer(model.getMockId());
        setData(interfaceMockService.get(model.getMockId()));
		return SUCCESS;
	}
	
	@Override
	public String edit(){
		if (model.getMockId() == null) {
			model.setUser(FrameworkUtil.getLoginUser());
			model.setRequestValidate(JSONObject.fromObject(new MockRequestValidateConfig()).toString());
			model.setResponseMock(JSONObject.fromObject(new MockResponseConfig()).toString());
			model.setMockId(interfaceMockService.save(model));
		} else {
			interfaceMockService.edit(model);
		}

        MockServer.handleMockServer(model.getMockId());
		setData(model);
		return SUCCESS;
	}


	/**
	 *  解析接口场景为Mock规则
	 * @author xuwangcheng
	 * @date 2019/11/25 20:56
	 * @param
	 * @return {@link String}
	 */
	public String parseSceneToMockInfo () {
		interfaceMockService.parseSceneToMock(messageSceneId, projectId);
		return SUCCESS;
	}

	/**
	 * 更新配置内容
	 * @return
	 */
	public String updateSetting (){
		String settingType = "";
		String settingValue = "";
		if (StringUtils.isNotBlank(model.getResponseMock())) {
			settingType = MockConfigSettingType.responseMock.name();
			settingValue = model.getResponseMock();
		}
		if (StringUtils.isNotBlank(model.getRequestValidate())) {
			settingType = MockConfigSettingType.requestValidate.name();
			settingValue = model.getRequestValidate();
		}
		interfaceMockService.updateSetting(model.getMockId(), settingType, settingValue);
		MockServer thisSocket = CacheUtil.getMockServers().get(model.getMockId());
		if (thisSocket != null) {
			thisSocket.updateConfig(settingType, settingValue);
		}

		return SUCCESS;
	}
	
	
	/**
	 * 解析入参报文成默认的验证规则
	 * @return
	 */
	public String parseMessageToConfig() {
		List<MockValidateRuleConfig> rules = MockValidateRuleConfig.parseValidateRuleList(message, null);
		setData(JSONArray.fromObject(rules).toString());
		return SUCCESS;
	}
	
	/**
	 * 解析指定的示例报文成适合ztree的tree node
	 * @return
	 */
	public String parseMessageToNodes() {
		MessageParse parseUtil = MessageParse.getParseInstance(MessageParse.judgeType(message));
		Set<Parameter> params = parseUtil.importMessageToParameter(message, new HashSet<Parameter>());
		if (params == null) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "尚不支持此类型的报文格式，请检查报文格式!");
		}
		//自定义parmeterId
		int count = 1;
		for (Parameter p:params) {
			p.setParameterId(count++);
		}
		
		Object[] os = PracticalUtils.getParameterZtreeMap(params);
		
		if (os == null) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "没有可用的参数,请检查!");
		}
		setData(new ParseMessageToNodesOutDTO(os[0], Integer.parseInt(os[1].toString()), os[2].toString()));

		return SUCCESS;
	}
	
	@Override
	public void checkObjectName() {
	    //为空时不验证
	    if (StringUtils.isBlank(model.getMockUrl())) {
            checkNameFlag = "true";
	        return;
        }
		InterfaceMock mock = interfaceMockService.findByMockUrl(model.getMockUrl(), model.getProtocolType());
		checkNameFlag = (mock != null && !mock.getMockId().equals(model.getMockId())) ? "请求路径重复" : "true";
		
		if (model.getMockId() == null) {
			checkNameFlag = (mock == null) ? "true" : "请求路径重复";
		}
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessageSceneId(Integer messageSceneId) {
		this.messageSceneId = messageSceneId;
	}
}
