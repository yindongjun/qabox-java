package yi.master.business.message.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.annotation.FieldRealSearch;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.user.bean.User;

import java.sql.Timestamp;

/**
 * 
 * 自动化定时测试任务
 * @author xuwangcheng
 * @version 1.0.0.0,20170724
 *
 */

public class AutoTask {
	
	/**
	 * 任务id
	 */
	private Integer taskId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务类型<br>
	 * 0 - 接口自动化     1 - web自动化
	 * 
	 */
	@FieldRealSearch(names = {"接口", "web"}, values = {"0", "1"})
	private String taskType;
	
	/**
	 * 关联的测试集Id会根据taskType查找
	 */
	private Integer relatedId;
	/**
	 * cron表达式<br>
	 * 参考quartz的定时器设定
	 */
	private String taskCronExpression;
	
	/**
	 * 该任务的运行次数,重启将会重新计数
	 */
	private Integer runCount;
	
	/**
	 * 最后一次完成时间
	 */
	private Timestamp lastFinishTime;
	
	/**
	 * 任务创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 当前状态<br>
	 * 0 - 可运行<br>
	 * 1 - 不可运行
	 */
	@FieldRealSearch(names = {"运行", "停止"}, values = {"0", "1"})
	private String status;
	
	@FieldRealSearch(names = {"否", "是"}, values = {"0", "1"})
	private String mailNotify;
	
	@FieldNameMapper(fieldPath="relatedId")
	private String setName = "";
	
	private User user;

	/**
	 * 所属项目
	 */
	private ProjectInfo projectInfo;
	
	@FieldNameMapper(fieldPath="user.realName")
	private String createUserName;
	
	public AutoTask() {
		super();
	}

	public AutoTask(String taskName, String taskType, Integer relatedId,
			String taskCronExpression, Integer runCount,
			Timestamp lastFinishTime, Timestamp createTime, String status) {
		super();
		this.taskName = taskName;
		this.taskType = taskType;
		this.relatedId = relatedId;
		this.taskCronExpression = taskCronExpression;
		this.runCount = runCount;
		this.lastFinishTime = lastFinishTime;
		this.createTime = createTime;
		this.status = status;
	}

	public void setMailNotify(String mailNotify) {
		this.mailNotify = mailNotify;
	}
	
	public String getMailNotify() {
		return mailNotify;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getCreateUserName() {
		return this.user.getRealName();
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}

	public String getTaskCronExpression() {
		return taskCronExpression;
	}

	public void setTaskCronExpression(String taskCronExpression) {
		this.taskCronExpression = taskCronExpression;
	}

	public Integer getRunCount() {
		return runCount;
	}

	public void setRunCount(Integer runCount) {
		this.runCount = runCount;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getLastFinishTime() {
		return lastFinishTime;
	}

	public void setLastFinishTime(Timestamp lastFinishTime) {
		this.lastFinishTime = lastFinishTime;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JSON(serialize=false)
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	@Override
	public String toString() {
		return "AutoTask [taskId=" + taskId + ", taskName=" + taskName
				+ ", taskType=" + taskType + ", relatedId=" + relatedId
				+ ", taskCronExpression=" + taskCronExpression + ", runCount="
				+ runCount + ", lastFinishTime=" + lastFinishTime
				+ ", createTime=" + createTime + ", status=" + status
				+ ", setName=" + setName + "]";
	}		
}
