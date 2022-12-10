package yi.master.business.advanced.action;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.advanced.bean.InterfaceProbe;
import yi.master.business.advanced.enums.InterfaceProbeStatus;
import yi.master.business.advanced.service.InterfaceProbeService;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.reportform.AnalyzeUtil;
import yi.master.coretest.message.parse.URLMessageParse;
import yi.master.coretest.task.JobManager;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class InterfaceProbeAction extends BaseAction<InterfaceProbe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private InterfaceProbeService interfaceProbeService;
	@Autowired
	private JobManager jobManager;
	
	private String probeTimeRange;
	
	private Integer dateNum;
	
	/**
	 * 批量添加时传入的id集合
	 */
	private String sceneIds;
	
	@Autowired
	public void setInterfaceProbeService(
			InterfaceProbeService interfaceProbeService) {
		super.setBaseService(interfaceProbeService);
		this.interfaceProbeService = interfaceProbeService;
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

	/**
	 * 获取所有探测任务的总览视图数据
	 * @return
	 */
	public String getProbeResultSynopsisViewData() {
		//默认7天数据
		if (dateNum == null || dateNum < 1) {
			dateNum = 7;
		}

		setData(AnalyzeUtil.analyzeProbeResultSynopsisView(interfaceProbeService.listProbeBeforeResultInfo(dateNum)));
		return SUCCESS;
	}
	
	/**
	 * 获取指定探测任务的结果报表数据
	 * @return
	 */
	public String getProbeResultReportData () {
		model = interfaceProbeService.get(model.getProbeId());
		if (probeTimeRange != null) {
			String[] times = probeTimeRange.trim().split("~");
			model.setFirstCallTime(Timestamp.valueOf(times[0].trim()));
			model.setLastCallTime(Timestamp.valueOf(times[1].trim()));
		}		
		//分析数据生成报表所需内容
		setData(AnalyzeUtil.analyzeProbeResults(model));
		return SUCCESS;
		
	}
	
	/**
	 * 更新配置
	 * @return
	 */
	public String updateConfig() {
		if (!leadValidation()) {						
			return SUCCESS;
		}
		if (model.getProbeId() != null) {
			interfaceProbeService.updateConfig(model.getProbeId(), model.setProbeConfigJson());
		}
		return SUCCESS;
	}
	
	/**
	 * 开启任务
	 * @return
	 */
	public String startTask() {
		model = interfaceProbeService.get(model.getProbeId());
		
		if (model != null) {
			jobManager.addProbeTask(model);
		}
		return SUCCESS;
	}
	
	/**
	 * 停止任务
	 * @return
	 */
	public String stopTask() {
		model = interfaceProbeService.get(model.getProbeId());
		
		if (model != null) {
			jobManager.stopProbeTask(model);
		}

		return SUCCESS;
	}
	
	@Override
	public String edit() {
		
		if (!leadValidation()) {						
			return SUCCESS;
		}
		String configJson = model.getProbeConfigJson();
		if (model.getProbeId() == null) {
			//单个增加			
			if (StringUtils.isBlank(configJson)) {
				model.setProbeConfigJson();
			}
			model.setUser(FrameworkUtil.getLoginUser());
			model.setStatus(InterfaceProbeStatus.STOPPED.getStatus());
		} else {
			//修改
			//修改不会变更测试场景
			InterfaceProbe probe = interfaceProbeService.get(model.getProbeId());
			if (probe != null) {
				model.setCycleAnalysisData(probe.getCycleAnalysisData());
				if (StringUtils.isBlank(configJson)) {
					model.setProbeConfigJson(probe.getProbeConfigJson());
				}
			}
		}
		
		if (StringUtils.isNotBlank(configJson)) {
			model.setProbeConfigJson(JSONObject.fromObject(URLMessageParse.parseUrlToMap(configJson, new String[]{"probeId"})).toString());
		}
		
		interfaceProbeService.edit(model);
		return SUCCESS;
	}

	/**
	 * 批量添加探测任务
	 * @return
	 */
	public String batchAdd(){
		String[] ids = sceneIds.split(",");
		//自定义的配置参数
		String configJson = model.getProbeConfigJson();		
		if (StringUtils.isBlank(configJson)) {
			model.setProbeConfigJson();//没有自定义的配置使用默认
		} else {
			model.setProbeConfigJson(JSONObject.fromObject(URLMessageParse.parseUrlToMap(configJson, new String[]{"probeId"})).toString());
		}
		model.setUser(FrameworkUtil.getLoginUser());
		model.setStatus(InterfaceProbeStatus.STOPPED.getStatus());
		
		InterfaceProbe probe = null;
		for (String id:ids) {
			try {
				probe = (InterfaceProbe) model.clone();
				probe.setScene(new MessageScene(Integer.valueOf(id)));
				interfaceProbeService.edit(probe);
			} catch (Exception e) {
				LOGGER.error("The interfaceProbe clone fail！", e);
			}						
		}
		return SUCCESS;
	}
	
	@Override
	public String del() {
		if (!leadValidation()) {						
			return SUCCESS;
		}
		id = model.getProbeId();
		return super.del();
	}


	@Override
	public boolean leadValidation() {
		if (model.getProbeId() != null) {
			//验证是否处理停止状态
			InterfaceProbe probe = interfaceProbeService.get(model.getProbeId());
			if (probe != null && !InterfaceProbeStatus.STOPPED.getStatus().equals(probe.getStatus())) {
				throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "该任务处于运行状态,请先停止!");
			}
		}
		return super.leadValidation();
	}
	
	/********************************************************************/
	public void setProbeTimeRange(String probeTimeRange) {
		this.probeTimeRange = probeTimeRange;
	}
	
	public void setDateNum(Integer dateNum) {
		this.dateNum = dateNum;
	}
	
	public void setSceneIds(String sceneIds) {
		this.sceneIds = sceneIds;
	}
	
}
