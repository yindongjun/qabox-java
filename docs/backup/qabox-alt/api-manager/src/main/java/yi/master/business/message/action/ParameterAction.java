package yi.master.business.message.action;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Parameter;
import yi.master.business.message.service.InterfaceInfoService;
import yi.master.business.message.service.ParameterService;
import yi.master.constant.MessageKeys;
import yi.master.constant.ReturnCodeConsts;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;

/**
 * 接口自动化
 * 接口参数管理Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 *
 */

@Controller
@Scope("prototype")
public class ParameterAction extends BaseAction<Parameter> {

	private static final long serialVersionUID = 1L;
	
	private ParameterService parameterService;
	
	@Autowired
	public void setParameterService(ParameterService parameterService) {
		super.setBaseService(parameterService);
		this.parameterService = parameterService;
	}
	@Autowired
	private InterfaceInfoService interfaceInfoService;
	
	/**
	 * 通过入参json报文{paramJson}批量导入接口参数
	 */
	private String paramsJson;
	
	/**
	 * 参数对应的接口id
	 */
	private Integer interfaceId;
	
	/**
	 * 传入的报文类型
	 */
	private String messageType;
	
	/**
	 * 根据指定的interfaceId接口id来获取下面的所有参数
	 * @return
	 */
	public String getParams() {
		List<Parameter> ps = parameterService.findByInterfaceId(interfaceId);
		setData(ps);
		return SUCCESS;
	}
	
	/**
	 * 删除指定接口下的入参接口信息
	 * @return
	 */
	public String delInterfaceParams() {
		parameterService.delByInterfaceId(interfaceId);
		return SUCCESS;
	}
	
	/**
	 * 根据传入的接口入参报文批量处理导入参数
	 * @return
	 */
	public String batchImportParams() {
		InterfaceInfo info = interfaceInfoService.get(interfaceId);	
		
		if (info == null) {
			throw new YiException(AppErrorCode.INTERFACE_INFO_NOT_EXIST);
		}
		
		MessageParse parseUtil = MessageParse.getParseInstance(messageType);
		if (parseUtil == null) {
			throw new YiException(AppErrorCode.MESSAGE_PARSE_ERROR);
		}
		
		Set<Parameter> params = parseUtil.importMessageToParameter(paramsJson, info.getParameters());
		
		if (params == null) {
			throw new YiException(AppErrorCode.INTERFACE_ILLEGAL_TYPE.getCode(), "不是指定格式的合法报文!");
		}		

		for (Parameter p:params) {
			p.setInterfaceInfo(info);
			parameterService.edit(p);
		}

		return SUCCESS;
	}
	
	/**
	 * 添加或者修改之前，检查是否已存在
	 */
	@Override
	public String edit() {
		

		model.setPath(StringUtils.isEmpty(model.getPath()) ? MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH 
				: MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH + "." + model.getPath());
		if (parameterService.checkRepeatParameter(model.getParameterId(), model.getParameterIdentify(), model.getPath(), 
				model.getType(), model.getInterfaceInfo().getInterfaceId()) != null) {
			throw new YiException(AppErrorCode.NAME_EXIST);
		}

		return super.edit();
	}

	/***************************************GET-SET****************************************************/
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public void setParamsJson(String paramsJson) {
		this.paramsJson = paramsJson;
	}

	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}

}
