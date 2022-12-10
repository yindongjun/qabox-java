package yi.master.business.system.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.system.service.OperationInterfaceService;
import yi.master.util.cache.CacheUtil;

import java.util.ArrayList;

/**
 * 系统操作接口Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class OperationInterfaceAction extends BaseAction<OperationInterface> {

	private static final long serialVersionUID = 1L;
		
	/**
	 * 操作接口类型
	 */
	private Integer opType;
	
	private OperationInterfaceService operationInterfaceService;
	
	@Autowired
	public void setOperationInterfaceService(OperationInterfaceService operationInterfaceService) {
		super.setBaseService(operationInterfaceService);
		this.operationInterfaceService = operationInterfaceService;
	}
	
	@Override
	public String edit() {
		model.setOi(new OperationInterface(model.getParentOpId2()));
		operationInterfaceService.edit(model);
		jsonObject.setData(model);
		CacheUtil.updateSystemInterfaces();
		return SUCCESS;
	}

	@Override
	public String del() {
		operationInterfaceService.delete(id);
		CacheUtil.updateSystemInterfaces();
		return SUCCESS;
	}

	/**
	 * 根据传入opType查找不同类型的操作接口信息
	 * @return
	 */
	public String listOp() {
		setData(operationInterfaceService.findAll());
		return SUCCESS;
	}

	/**
	 *  获取指定页面的权限列表
	 * @author xuwangcheng
	 * @date 2019/11/24 19:05
	 * @param
	 * @return {@link String}
	 */
	public String listByPageName () {
		setData(new ArrayList<>());
		if (StringUtils.isNotBlank(model.getPageName())) {
			setData(operationInterfaceService.listByPageName(model.getPageName()));
		}
		return SUCCESS;
	}

	/**************************************************************************/
	
	public void setOpType(Integer opType) {
		this.opType = opType;
	}
	
	
}
