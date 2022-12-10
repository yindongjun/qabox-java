package yi.master.business.message.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.base.dto.ParseMessageToNodesOutDTO;
import yi.master.business.message.bean.*;
import yi.master.business.message.service.*;
import yi.master.business.testconfig.bean.BusinessSystem;
import yi.master.business.testconfig.service.GlobalVariableService;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.PracticalUtils;
import yi.master.util.excel.ImportMessageScene;

import java.util.*;

/**
 * 报文场景Action
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,2017.3.6
 */

@Controller
@Scope("prototype")
public class MessageSceneAction extends BaseAction<MessageScene>{
	private static final long serialVersionUID = 1L;
	
	private Integer messageId;

	private MessageSceneService messageSceneService;
	@Autowired
	private TestSetService testSetService;
	@Autowired
	private TestDataService testDataService;
	@Autowired
	private GlobalVariableService globalVariableService;
	@Autowired
	private SceneValidateRuleService sceneValidateRuleService;
	@Autowired
	private MessageService messageService;
	
	private String path;
	private Integer variableId;
	private Integer setId;
	
	@SuppressWarnings("unused")
	private String mode;

	@Autowired
	public void setMessageSceneService(MessageSceneService messageSceneService) {
		super.setBaseService(messageSceneService);
		this.messageSceneService = messageSceneService;
	}
	
	
	/**
	 * 获取指定报文的入参节点树
	 * @return
	 */
	public String getRequestMsgJsonTree() {
		Message msg = messageSceneService.get(model.getMessageSceneId()).getMessage();
		
		MessageParse parseUtil = MessageParse.getParseInstance(msg.getMessageType());
		String paramsJson = parseUtil.depacketizeMessageToString(msg.getComplexParameter(), null);
		Set<Parameter> params = parseUtil.importMessageToParameter(paramsJson, new HashSet<Parameter>());
		
		if (params == null) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "报文格式不正确，请检查出入参报文!");
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
	
	/**
	 * 获取指定报文的出参节点树
	 * @return
	 */
	public String getResponseMsgJsonTree() {
		String responseMsg = messageSceneService.get(model.getMessageSceneId()).getResponseExample();
		
		if (StringUtils.isBlank(responseMsg)) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "该测试场景没有返回示例报文,请先设置!");
		}	
		
		MessageParse parseUtil = MessageParse.getParseInstance(messageSceneService.get(model.getMessageSceneId()).getMessage().getMessageType());
			
		Set<Parameter> params = MessageParse.judgeMessageType(responseMsg).importMessageToParameter(responseMsg, new HashSet<Parameter>());
		if (params == null) {
			throw new YiException(AppErrorCode.MESSAGE_VALIDATE_ERROR.getCode(), "尚不支持此类型的报文格式，请检查出报文格式!");
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
	
	
	
	/**
	 * 更新返回报文示例
	 * @return
	 */
	public String updateResponseExample () {
		messageSceneService.updateResponseExample(model.getMessageSceneId(), model.getResponseExample());
		return SUCCESS;
	}
	
	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (messageId != null) {
			conditions.add("t.message.messageId=" + messageId);
		}
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}	
	
	/**
	 * 从上传的Excel导入场景信息到数据库
	 * @return
	 */
	public String importFromExcel () {
		Message message = messageService.get(messageId);
		if (message == null) {
			throw new YiException(AppErrorCode.MESSAGE_INFO_NOT_EXITS);
		}
		
		Map<String, Object> result = ImportMessageScene.importToDB(path, message, projectId);

		setData(result);
		return SUCCESS;
	}
	
	@Override
	public String edit() {
        //新增
		if (model.getMessageSceneId() == null) {
			messageSceneService.save(model, true, variableId);
			//编辑
		} else {
			messageSceneService.edit(model);
		}
		return SUCCESS;
	}
	
	/**
	 * 变更验证规则
	 * @return
	 */
	public String changeValidateRule() {		
		messageSceneService.updateValidateFlag(model.getMessageSceneId(), model.getValidateRuleFlag());
		return SUCCESS;
	}
	
	/**
	 * 获取测试中需要用到的url和所有可用测试数据
	 * @return
	 */
	public String getTestObject () {
		model = messageSceneService.get(model.getMessageSceneId());		
		Message msg = model.getMessage();
		InterfaceInfo info = msg.getInterfaceInfo();
		//检查是否缺少关联测试环境信息
		boolean normal = true;
		String s = "";
		if (info.getSystems().size() == 0) {
			normal = false;
			s += "接口信息[" + info.getInterfaceName() + "]缺少关联的测试环境<br>";
		}
		
		if (StringUtils.isBlank(msg.getSystems())) {
			normal = false;
			s += "报文信息[" + msg.getMessageName() + "]缺少关联的测试环境<br>";
		}
		
		if (StringUtils.isBlank(model.getSystems())) {
			normal = false;
			s += "测试场景[" + model.getSceneName() + "]缺少关联的测试环境";
		}
		
		if (!normal) {
			throw new YiException(AppErrorCode.MISS_PARAM.getCode(), s);
		}
		
		Map<String, Object> testObject = new HashMap<String, Object>();
		
		MessageParse parseUtil = MessageParse.getParseInstance(msg.getMessageType());
		for (BusinessSystem system:msg.checkSystems(model.getSystems())) {
			Map<String, Object> object = new HashMap<String, Object>();	
			
			String requestUrl = "";
			if (StringUtils.isNotBlank(model.getRequestUrl())) {
				requestUrl = model.getRequestUrl();
			}
			if (StringUtils.isBlank(requestUrl) && StringUtils.isNotBlank(msg.getRequestUrl())) {
				requestUrl = msg.getRequestUrl();
			}
			if (StringUtils.isBlank(requestUrl)) {
				requestUrl = info.getRequestUrlReal();
			}
			
			object.put("requestUrl", system.getReuqestUrl(requestUrl, system.getDefaultPath(), info.getInterfaceName()));
			List<TestData> datas = model.getEnabledTestDatas(5, String.valueOf(system.getSystemId()));
			for (TestData data:datas) {
				if (data.getDataJson() == null) {
					data.setDataJson(parseUtil.depacketizeMessageToString(msg.getComplexParameter(), data.getParamsData()));
				}				
			}
			object.put("requestData", datas);
			object.put("system", system);
			testObject.put(String.valueOf(system.getSystemId()), object);
		}

		setData(testObject);
		return SUCCESS;
	}
		
	/**
	 * 获取指定测试集中没有测试数据的测试场景列表
	 * @return
	 */
	public String listNoDataScenes() {
		List<MessageScene> noDataScenes = new ArrayList<MessageScene>();		
		List<MessageScene> scenes = null;
		
		//全量
		if (setId == 0) {
			scenes = messageSceneService.findAll();
		//测试集	
		} else {
			scenes = messageSceneService.getBySetId(setId);
		}
		
		for(MessageScene ms:scenes){
			if(!ms.hasEnoughData(null)){
				noDataScenes.add(ms);
			}								
		}	
		setData(noDataScenes);
		return SUCCESS;
	}
	
	/***************************************GET-SET************************************************/
	
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
