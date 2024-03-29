package yi.master.business.message.action;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Message;
import yi.master.business.message.bean.Parameter;
import yi.master.business.message.service.*;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.excel.ImportMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接口报文Action
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.17
 */

@Controller
@Scope("prototype")
public class MessageAction extends BaseAction<Message>{

	private static final long serialVersionUID = 1L;		
	
	/**报文对应的接口id*/
	private Integer interfaceId;
	
	private String path;
	
	private String nodes;

	/**
	 * 是否在创建报文时创建一个默认的场景，只在新增时有效
	 */
	private Boolean createDefaultScene;
	
	private MessageService messageService;

	@Autowired
	private MessageSceneService messageSceneService;
	
	@Autowired
	public void setMessageService(MessageService messageService) {
		super.setBaseService(messageService);
		this.messageService = messageService;
	}
	
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	@Autowired
	private ComplexParameterService complexParameterService;
	
	
	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (this.interfaceId != null) {
			conditions.add("interfaceInfo.interfaceId=" + interfaceId);
		}
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}

	
	/**
	 * 从指定Excel中导入报文信息
	 * @return
	 */
	public String importFromExcel () {
		InterfaceInfo info = interfaceInfoService.get(interfaceId);
		
		if (info == null) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), "接口信息不存在");
		}
		
		Map<String, Object> result = ImportMessage.importToDB(path, info, projectId);
		
		setData(result);
		return SUCCESS;
	}

	/**
	 * 格式化报文的入参
	 * @return
	 */
	public String format() {
		MessageParse parseUtil = MessageParse.getParseInstance(model.getMessageType());		
		
		String returnJson = parseUtil.messageFormatBeautify(model.getParameterJson());

		if (returnJson == null) {
			throw new YiException(AppErrorCode.INTERFACE_ILLEGAL_TYPE);
		}	

		setData(returnJson);
		return SUCCESS;
	}
	
	
	/**
	 * 验证报文入参的正确性
	 * 判断依据是：报文中的所有节点是否都存在于对应接口的参数列表中
	 * @return
	 */
	public String validateJson() {
		
		MessageParse parseUtil = MessageParse.getParseInstance(model.getMessageType());
		
		if (interfaceId == null) {
			interfaceId = (messageService.get(model.getMessageId())).getInterfaceInfo().getInterfaceId();
		}
		
		List<Parameter> interfaceParams = parameterService.findByInterfaceId(interfaceId);
		
		String resultFlag = parseUtil.checkParameterValidity(interfaceParams, model.getParameterJson());

		if (!SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(resultFlag)) {
			throw new YiException(AppErrorCode.MESSAGE_VALIDATE_ERROR.getCode(), resultFlag);
		}

		jsonObject.setMsg(resultFlag);
		return SUCCESS;		
	}
	
	/**
	 * 增加和修改的方法
	 */
	@Override
	public String edit() {

		MessageParse parseUtil = MessageParse.getParseInstance(model.getMessageType());
		
		if (model.getInterfaceInfo().getInterfaceId() == null) {
			model.setInterfaceInfo((messageService.get(model.getMessageId())).getInterfaceInfo());
		}

		Set<Parameter> params = (interfaceInfoService.get(model.getInterfaceInfo().getInterfaceId())).getParameters();
		String validateFlag = parseUtil.checkParameterValidity(new ArrayList<Parameter>(params), model.getParameterJson());

		if (!SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(validateFlag)) {
			throw new YiException(AppErrorCode.MESSAGE_VALIDATE_ERROR.getCode(), validateFlag);
		}

		User user = FrameworkUtil.getLoginUser();
        model.setLastModifyUser(user.getRealName());
        model.setParameterJson(parseUtil.messageFormatBeautify(model.getParameterJson()));
		if (model.getMessageId() == null) {
			//增加			
			model.setCreateTime(new Timestamp(System.currentTimeMillis()));
			model.setUser(user);
            model.setComplexParameter(parseUtil.parseMessageToObject(model.getParameterJson(), new ArrayList<Parameter>(params)));

            //新增
            messageService.save(model, createDefaultScene);
		} else {
			//删除之前的复杂参数
			Message msg = messageService.get(model.getMessageId());
			if (msg != null && msg.getComplexParameter() != null) {
				Integer delId = msg.getComplexParameter().getId();
				msg.setComplexParameter(null);
				complexParameterService.delete(delId);
			}
            model.setComplexParameter(parseUtil.parseMessageToObject(model.getParameterJson(), new ArrayList<Parameter>(params)));
			//更新
            messageService.edit(model);
		}

		return SUCCESS;
	}
	
	
	
	@Override
	public String get() {
		Message msg = messageService.get(id);		

		MessageParse parseUtil = MessageParse.getParseInstance(msg.getMessageType());

		msg.setParameterJson(parseUtil.messageFormatBeautify(parseUtil.depacketizeMessageToString(msg.getComplexParameter(), null)));

		setData(msg);
		return SUCCESS;
	}
	
	/**
	 * 根据指定的参数来创建指定格式的报文
	 * @return
	 */
	public String createMessage() {
		MessageParse parseUtil = MessageParse.getParseInstance(model.getMessageType());
		setData(parseUtil.createMessageByNodes(JSONObject.fromObject(nodes)));
		return SUCCESS;
	}
	
	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

    public void setCreateDefaultScene(Boolean createDefaultScene) {
        this.createDefaultScene = createDefaultScene;
    }
}
