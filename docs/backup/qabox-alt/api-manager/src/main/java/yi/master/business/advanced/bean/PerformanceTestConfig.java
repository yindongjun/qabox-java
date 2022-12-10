package yi.master.business.advanced.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.CustomConditionSetting;
import yi.master.annotation.FieldNameMapper;
import yi.master.business.advanced.enums.PtKeepAlive;
import yi.master.business.advanced.enums.PtParameterGetType;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.testconfig.bean.BusinessSystem;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 接口性能测试配置
 * @author xuwangcheng
 * @version 20180626
 *
 */
public class PerformanceTestConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 默认分隔符
	 */
	public static final String DEFAULT_FORMAT_CHARACTER = ",";
	/**
	 * 100
	 */
	public static final Integer MAX_THREAD_COUNT = 100;
	/**
	 * 28800
	 */
	public static final Integer MAX_TEST_TIME = 28800;
	

	private Integer ptId;
	/**
	 * 名称：默认为 场景名_测试环境名_线程数
	 */
	private String ptName;
	/**
	 * 关联测试场景，相关默认数据、测试配置等均使用此场景关联内容
	 */
	private MessageScene messageScene;
	/**
	 * 使用测试环境(测试环境)
	 */
	private BusinessSystem businessSystem;
	/**
	 * 是否保持长连接测试<br>
	 * 0 - 短连接<br>
	 * 1 - 保持长连接<br>
	 * 默认为 保持长连接
	 */
	private String keepAlive = PtKeepAlive.KEEP_ALIVE.getCode();
	/**
	 * 测试线程数<br>
	 * 范围1 - 99（防止线程太大出现不可控情况,后期改进测试核心代码再解除限制）<br>
	 * 默认为1
	 */
	private Integer threadCount = 1;
	/**
	 * 参数化文件路径：文件格式需要为txt<br>
	 * 第一列为要参数化的参数节点完整路径
	 */
	private String parameterizedFilePath;
	/**
	 * 参数是否可复用<br>
	 * 1 - 可复用<br>
	 * 0 - 不可复用<br>
	 * 默认为可复用
	 */
	private String parameterReuse = SystemConsts.DefaultBooleanIdentify.TRUE.getNumber();
	/**
	 * 参数取值规则<br>
	 * 0 - 按顺序取值<br>
	 * 1 - 随机取值<br>
	 * 默认为按顺序取值
	 */
	private String parameterPickType = PtParameterGetType.SEQUENCE.getType();
	/**
	 * 参数化文件格式化分隔符,默认为英文下的逗号
	 */
	private String formatCharacter = DEFAULT_FORMAT_CHARACTER;
	/**
	 * 最大请求次数，多线程测试将会平分这个值
	 */
	private Integer maxCount;
	/**
	 * 最大持续时间<br>单位秒<br>
	 * 从第一个线程开始启动计算时间，达到最大时间之后打上停止标记并开始停止各线程
	 */
	private Integer maxTime;
	/**
	 * 规则创建用户
	 */
	private User user;
	/**
	 * 创建时间
	 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis());

	/**
	 * 所属项目
	 */
	private ProjectInfo projectInfo;
	/**
	 * 备注
	 */
	private String mark;
	
	@FieldNameMapper(fieldPath="size(results)",ifSearch=false)
	@CustomConditionSetting(conditionType="")
	private Integer resultNum;
	
	private Set<PerformanceTestResult> results = new HashSet<PerformanceTestResult>();
	
	
	public PerformanceTestConfig(Integer ptId, String ptName,
			MessageScene messageScene, BusinessSystem businessSystem,
			String keepAlive, Integer threadCount,
			String parameterizedFilePath, String parameterReuse,
			String parameterPickType, Integer maxCount, Integer maxTime,
			User user, Timestamp createTime, String mark) {
		super();
		this.ptId = ptId;
		this.ptName = ptName;
		this.messageScene = messageScene;
		this.businessSystem = businessSystem;
		this.keepAlive = keepAlive;
		this.threadCount = threadCount;
		this.parameterizedFilePath = parameterizedFilePath;
		this.parameterReuse = parameterReuse;
		this.parameterPickType = parameterPickType;
		this.maxCount = maxCount;
		this.maxTime = maxTime;
		this.user = user;
		this.createTime = createTime;
		this.mark = mark;
	}


	public PerformanceTestConfig() {
		super();
		
	}


	public void setFormatCharacter(String formatCharacter) {
		this.formatCharacter = formatCharacter;
	}
	
	public String getFormatCharacter() {
		return formatCharacter;
	}
	
	public void setResults(Set<PerformanceTestResult> results) {
		this.results = results;
	}
	
	@JSON(serialize=false)
	public Set<PerformanceTestResult> getResults() {
		return results;
	}
	
	public void setResultNum(Integer resultNum) {
		this.resultNum = resultNum;
	}
	
	public Integer getResultNum() {
		return this.results.size();
	}
	
	public Integer getPtId() {
		return ptId;
	}


	public void setPtId(Integer ptId) {
		this.ptId = ptId;
	}


	public String getPtName() {
		return ptName;
	}


	public void setPtName(String ptName) {
		this.ptName = ptName;
	}


	public MessageScene getMessageScene() {
		return messageScene;
	}


	public void setMessageScene(MessageScene messageScene) {
		this.messageScene = messageScene;
	}


	public BusinessSystem getBusinessSystem() {
		return businessSystem;
	}


	public void setBusinessSystem(BusinessSystem businessSystem) {
		this.businessSystem = businessSystem;
	}


	public String getKeepAlive() {
		return keepAlive;
	}


	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}


	public Integer getThreadCount() {
		return threadCount;
	}


	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}


	public String getParameterizedFilePath() {
		return parameterizedFilePath;
	}


	public void setParameterizedFilePath(String parameterizedFilePath) {
		this.parameterizedFilePath = parameterizedFilePath;
	}


	public String getParameterReuse() {
		return parameterReuse;
	}


	public void setParameterReuse(String parameterReuse) {
		this.parameterReuse = parameterReuse;
	}


	public String getParameterPickType() {
		return parameterPickType;
	}


	public void setParameterPickType(String parameterPickType) {
		this.parameterPickType = parameterPickType;
	}


	public Integer getMaxCount() {
		return maxCount;
	}


	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}


	public Integer getMaxTime() {
		return maxTime;
	}


	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public String getMark() {
		return mark;
	}


	public void setMark(String mark) {
		this.mark = mark;
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
		return "PerformanceTestConfig [ptId=" + ptId + ", ptName=" + ptName
				+ ", messageScene=" + messageScene + ", businessSystem="
				+ businessSystem + ", keepAlive=" + keepAlive
				+ ", threadCount=" + threadCount + ", parameterizedFilePath="
				+ parameterizedFilePath + ", parameterReuse=" + parameterReuse
				+ ", parameterPickType=" + parameterPickType + ", maxCount="
				+ maxCount + ", maxTime=" + maxTime + ", user=" + user
				+ ", createTime=" + createTime + ", mark=" + mark + "]";
	}

}
