package yi.master.business.testconfig.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.testconfig.bean.GlobalVariable;
import yi.master.business.testconfig.enums.GlobalVariableUniqueScope;
import yi.master.business.testconfig.service.GlobalVariableService;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,20171129
 *
 */
@Controller("globalVariableAction")
@Scope("prototype")
public class GlobalVariableAction extends BaseAction<GlobalVariable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GlobalVariableService globalVariableService;
	
	private Boolean forceCreate;
	
	@Autowired
	public void setGlobalVariableService(
			GlobalVariableService globalVariableService) {
		super.setBaseService(globalVariableService);
		this.globalVariableService = globalVariableService;
	}


	@Override
	public String edit() {
		
		
		User user = FrameworkUtil.getLoginUser();
		if (model.getVariableId() == null) {
			//新增
			model.setCreateTime(new Timestamp(System.currentTimeMillis()));
			model.setUser(new User(user.getUserId()));
			model.setExpiryDate(new Timestamp(System.currentTimeMillis()));
			model.setUniqueScope(GlobalVariableUniqueScope.GLOBAL.getScope());
			if (model.getValidityPeriod() == null) {
				model.setValidityPeriod(0);
			}
		}
		
		//验证key的唯一性
		if (GlobalVariable.ifHasKey(model.getVariableType())) {
			checkObjectName();
			if (StringUtils.isBlank(model.getKey()) || !SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(checkNameFlag)) {
				throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), "无效或者" + checkNameFlag + ",请重试!");
			}
		}
		
		globalVariableService.edit(model);
		return SUCCESS;
	}

	@Override
	public String listAll () {
		List<GlobalVariable> variables = new ArrayList<GlobalVariable>();
		
		if (model.getVariableType() == null || "all".equalsIgnoreCase(model.getVariableType())) {
			variables = globalVariableService.findAll();
		} else {
			variables = globalVariableService.findByVariableType(model.getVariableType());
		}

		setData(variables);
		return SUCCESS;
	}
	
	/**
	 * 更新指定全局变量的value值，不包括constant和timestamp类型的
	 * @return
	 */
	public String updateValue() {
		globalVariableService.updateValue(model.getVariableId(), model.getValue());
		return SUCCESS;
	}
	

	/**
	 * 根据variableType和value生成返回变量
	 * @return
	 */
	public String createVariable() {
		model = globalVariableService.get(model.getVariableId());
		if (model == null) {;
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "该全局变量不存在！");
		}
		
		Object str = model.createSettingValue(forceCreate == null ? false : forceCreate);

		if (str == null) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), model.getCreateErrorInfo());
		}
		
		jsonObject.setMsg(str.toString());
		return SUCCESS;
	}
	
	/**
	 * 在同种类型中判断key值是否重复<br>
	 * 新增或者修改状态下均可用
	 */
	@Override
	public void checkObjectName() {
		GlobalVariable info = globalVariableService.findByKey(model.getKey());
		checkNameFlag = (info != null && !info.getVariableId().equals(model.getVariableId())) ? "重复的key" : "true";
		
		if (model.getVariableId() == null) {
			checkNameFlag = (info == null) ? "true" : "重复的key";
		}
	}

	public void setForceCreate(Boolean forceCreate) {
		this.forceCreate = forceCreate;
	}
}
