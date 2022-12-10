package yi.master.business.advanced.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.advanced.bean.PerformanceTestConfig;
import yi.master.business.advanced.service.PerformanceTestConfigService;
import yi.master.business.base.action.BaseAction;
import yi.master.business.user.bean.User;
import yi.master.coretest.message.test.performance.PerformanceTestObject;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;
import yi.master.util.jsonlib.JsonDateValueProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 性能测试配置相关接口
 * @author xuwangcheng
 * @date 2019/8/30 16:00
 */
@Controller
@Scope("prototype")
public class PerformanceTestConfigAction extends BaseAction<PerformanceTestConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PerformanceTestConfigService performanceTestConfigService;
	
	private Integer objectId;
	
	@Autowired
	public void setPerformanceTestConfigService(
			PerformanceTestConfigService performanceTestConfigService) {
		super.setBaseService(performanceTestConfigService);
		this.performanceTestConfigService = performanceTestConfigService;
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

	@Override
	public String edit() {
		if (model.getPtId() == null || model.getUser() == null) {
			User user = FrameworkUtil.getLoginUser();
			model.setUser(user);
		}
		
		return super.edit();
	}
	
	/**
	 * 开启性能测试任务-初始化
	 * @return
	 */
	public String initTest() {
		model = performanceTestConfigService.get(model.getPtId());
		PerformanceTestObject pto = new PerformanceTestObject(model, FrameworkUtil.getLoginUser());
		boolean flag = pto.init();
		setData(pto);
		if (!flag) {
			throw new YiException(AppErrorCode.OPERATION_FAIL.getCode(), "初始化测试任务失败,请查看错误信息!");
		}
		return SUCCESS;
	}
	
	/**
	 * 开启性能测试任务-执行测试
	 * @return
	 */
	public String actionTest() {
		User user  = FrameworkUtil.getLoginUser();
		PerformanceTestObject pto = CacheUtil.getPtObjectsByUserId(user.getUserId()).get(objectId);
		
		if (pto == null) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "性能测试任务不存在或者已结束,请查看测试结果列表!");
		}
		
		if (!pto.isRunning()) {
			boolean flag = pto.action();
			if (!flag) {
				throw new YiException(AppErrorCode.OPERATION_FAIL.getCode(), "启动测试任务失败,请查看错误信息!");
			}			
		}
		setData(pto);
		return SUCCESS;
	}
	
	/**
	 * 查看实时的测试情况
	 * @return
	 */
	public String viewTest() {
		User user = FrameworkUtil.getLoginUser();
		Map<Integer, PerformanceTestObject>  ptos = CacheUtil.getPtObjectsByUserId(user.getUserId());
		JSONObject obj = new JSONObject();
		obj.put("running", false);
		PerformanceTestObject pto = ptos.get(objectId);		
		if (pto != null) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			obj = JSONObject.fromObject(pto.getAnalyzeResult(), jsonConfig);
			obj.put("objectId", pto.getObjectId());
			obj.put("ptName", pto.getConfig().getPtName());
			obj.put("running", pto.isRunning());
			obj.put("interfaceName", pto.getResult().getInterfaceName());
			obj.put("systemName", pto.getResult().getSystemName());
			obj.put("startTime", PracticalUtils.formatDate("yyyy-MM-dd HH:mm:ss", pto.getResult().getStartTime()));
			obj.put("currentStatus", pto.getCurrentStatus());
			obj.put("infoMsg", pto.getInfoMsg());
			obj.put("errorMsg", pto.getErrorMsg());
			obj.put("resultMark", pto.getResultMarkMsg());
		}
		setData(obj);
		return SUCCESS;
	}
	
	/**
	 * 立即停止所有线程并开始保存数据
	 * @return
	 */
	public String stopTest() {
		User user = FrameworkUtil.getLoginUser();
		PerformanceTestObject pto = CacheUtil.getPtObjectsByUserId(user.getUserId()).get(objectId);
		if (pto != null && pto.isRunning()) {
			pto.setGlobalStoped(true);			
		}
		jsonObject.setMsg("停止成功,请等待。");
		return SUCCESS;
	}
	
	/**
	 * 删除测试任务：先停止，同时不会保存测试结果数据到数据库或者文件中
	 * @return
	 */
	public String delTest() {
		User user = FrameworkUtil.getLoginUser();
		PerformanceTestObject pto = CacheUtil.getPtObjectsByUserId(user.getUserId()).get(objectId);
		if (pto != null) {
			if (pto.isRunning()) {
				pto.setEnableSave(false);
				pto.setGlobalStoped(true);
			} else {				
				CacheUtil.getPtObjectsByUserId(user.getUserId()).remove(objectId);
			}
			
		}
		jsonObject.setMsg("删除任务成功,请等待!");
		return SUCCESS;
	}
	
	/**
	 * 性能测试任务列表
	 * @return
	 */
	public String listTest() {
		User user = FrameworkUtil.getLoginUser();
		Map<Integer, PerformanceTestObject>  ptos = CacheUtil.getPtObjectsByUserId(user.getUserId());
		
		JSONArray ptosArray = new JSONArray();
		if (ptos != null) {
			for (Integer key:ptos.keySet()) {
				if (projectId != null || !projectId.equals(ptos.get(key).getProjectId())) {
					continue;
				}
				ptosArray.add(ptos.get(key).createSummaryView());
			}
		}
		
		setData(ptosArray);
		return SUCCESS;
	}
	
	/************************************************************************************************************/
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
}
