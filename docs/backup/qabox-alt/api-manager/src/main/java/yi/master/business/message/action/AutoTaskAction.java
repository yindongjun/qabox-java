package yi.master.business.message.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.AutoTask;
import yi.master.business.message.bean.TestSet;
import yi.master.business.message.service.AutoTaskService;
import yi.master.business.message.service.TestSetService;
import yi.master.constant.SystemConsts;
import yi.master.coretest.task.JobManager;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class AutoTaskAction extends BaseAction<AutoTask> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AutoTaskService autoTaskService;
	@Autowired
	private TestSetService testSetService;
	@Autowired
	private JobManager jobManager;
	
	@Autowired
	public void setAutoTaskService(AutoTaskService autoTaskService) {
		super.setBaseService(autoTaskService);
		this.autoTaskService = autoTaskService;
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
		
		checkObjectName();
		if (!checkNameFlag.equals(SystemConsts.DefaultBooleanIdentify.TRUE.getString())) {
			throw new YiException(AppErrorCode.NAME_EXIST);
		}
		if (model.getTaskId() == null) {
			model.setUser(FrameworkUtil.getLoginUser());
		}
		return super.edit();
	}




	/**
	 * 判断标任务名重复性
	 * 新增或者修改状态下均可用
	 */
	@Override
	public void checkObjectName() {
		AutoTask task = autoTaskService.findByName(model.getTaskName());
		
		checkNameFlag = (task != null && !task.getTaskId().equals(model.getTaskId())) ? "重复的任务名称" : "true";
		
		if (model.getTaskId() == null) {
			checkNameFlag = (task == null) ? "true" : "重复的任务名称";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object processListData(Object o) {
		
		List<AutoTask> tasks = (List<AutoTask>) o;
		
		for (AutoTask task:tasks) {
			setTestSetName(task);
		}
		return tasks;
	}	
		
	@Override
	public String get() {
		model = autoTaskService.get(id);
		
		setTestSetName(model);
		setData(model);
		return SUCCESS;
	}

	/**
	 * 启动quartz
	 * @return
	 */
	public String startQuartz() {
		String quartzFlag = (String) FrameworkUtil.getApplicationMap().get(SystemConsts.QUARTZ_SCHEDULER_START_FLAG);
		
		if (SystemConsts.QUARTZ_SCHEDULER_IS_START.equalsIgnoreCase(quartzFlag)) {
			throw new YiException(AppErrorCode.QUARTZ_SCHEDULER_IS_START);
		}
		
		jobManager.startTasks();
		FrameworkUtil.getApplicationMap().put(SystemConsts.QUARTZ_SCHEDULER_START_FLAG, SystemConsts.QUARTZ_SCHEDULER_IS_START);
		setData(SystemConsts.QUARTZ_SCHEDULER_IS_START);
		return SUCCESS;
	}
	
	/**
	 * 停止quartz
	 * @return
	 */
	public String stopQuartz() {
		String quartzFlag = (String) FrameworkUtil.getApplicationMap().get(SystemConsts.QUARTZ_SCHEDULER_START_FLAG);
		
		if (!SystemConsts.QUARTZ_SCHEDULER_IS_START.equalsIgnoreCase(quartzFlag)) {
			throw new YiException(AppErrorCode.QUARTZ_HAS_BEEN_STOP);
		}
		
		jobManager.stopTasks();
		FrameworkUtil.getApplicationMap().put(SystemConsts.QUARTZ_SCHEDULER_START_FLAG, SystemConsts.QUARTZ_SCHEDULER_IS_STOP);
		setData(SystemConsts.QUARTZ_SCHEDULER_IS_STOP);
		return SUCCESS;
	}
	
	/**
	 * 添加可运行的任务到quartz
	 * @return
	 */
	public String startRunableTask() {
		String quartzFlag = (String) FrameworkUtil.getApplicationMap().get(SystemConsts.QUARTZ_SCHEDULER_START_FLAG);
			
		if (!SystemConsts.QUARTZ_SCHEDULER_IS_START.equalsIgnoreCase(quartzFlag)) {
			throw new YiException(AppErrorCode.QUARTZ_HAS_BEEN_STOP.getCode(), "请先恢复定时任务调度器!");
		}
		model = autoTaskService.get(model.getTaskId());
		
		if (!PracticalUtils.isNormalString(model.getTaskCronExpression())) {
			throw new YiException(AppErrorCode.QUARTZ_NEED_CRON_EXPRESSION);
		}

		try {
			jobManager.addTimeTask(model);
		} catch (Exception e) {
			LOGGER.error("定时任务规则设置有误!", e);
			throw new YiException(AppErrorCode.QUARTZ_CRON_EXPRESSION_SETTING_ERROR, e.getMessage());
		}		
		return SUCCESS;
	}
	
	/**
	 * 停止运行中的任务
	 * @return
	 */
	public String stopRunningTask() {
		String quartzFlag = (String) FrameworkUtil.getApplicationMap().get(SystemConsts.QUARTZ_SCHEDULER_START_FLAG);
		
		if (!SystemConsts.QUARTZ_SCHEDULER_IS_START.equalsIgnoreCase(quartzFlag)) {
			throw new YiException(AppErrorCode.QUARTZ_HAS_BEEN_STOP.getCode(), "定时任务调度器不在运行状态!");
		}
		model = autoTaskService.get(model.getTaskId());
		jobManager.stopTimeTask(model);

		return SUCCESS;
	}
	
	public String getQuartzStatus() {
		setData(FrameworkUtil.getApplicationMap().get(SystemConsts.QUARTZ_SCHEDULER_START_FLAG));
		return SUCCESS;
	}
	
	public String updateCronExpression() {
		autoTaskService.updateExpression(model.getTaskId(), model.getTaskCronExpression());
		return SUCCESS;
	}
	
	
	private void setTestSetName(AutoTask task) {
		if ("0".equals(task.getRelatedId())) {
			task.setSetName("全量测试");
		} else {
			switch (task.getTaskType()) {
			case "0":
				TestSet set = testSetService.get(task.getRelatedId());
				task.setSetName(set == null ? "测试集已删除" : set.getSetName());
				break;
			case "1":
				task.setSetName("");
				break;
			default:
				break;
			}
		}
	}
	
}
