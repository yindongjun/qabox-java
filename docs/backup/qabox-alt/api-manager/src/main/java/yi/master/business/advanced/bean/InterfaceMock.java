package yi.master.business.advanced.bean;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.annotation.FieldRealSearch;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.user.bean.User;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.test.mock.MockServer;
import yi.master.util.cache.CacheUtil;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 接口mock
 * @author xuwangcheng
 * @version 2018.06.11
 *
 */
public class InterfaceMock implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer mockId;
	/**
	 * 名称，接口名或者释义
	 */
	private String mockName;
	/**
	 * 自定义路径
	 */
	private String mockUrl;

	/**
	 * 所属项目
	 */
	private ProjectInfo projectInfo;

	/**
	 * 请求验证规则，json串
	 */
	private String requestValidate;
	/**
	 * 返回模拟规则，json串
	 */
	private String responseMock;
	/**
	 * 调用次数
	 */
	private Integer callCount;
	/**
	 * 当前状态
	 */
	@FieldRealSearch(names = {"启用", "禁用"}, values = {"0", "1"})
	private String status;
	/**
	 * 备注
	 */
	private String mark;
	/**
	 * 创建用户
	 */
	private User user;

	private Timestamp createTime;
	
	@FieldNameMapper(fieldPath="mockUrl", ifSearch=false, ifOrder=true)
	private String mockUri;
	
	private String protocolType;
	
	public InterfaceMock(Integer mockId, String mockName, String mockUrl,
			String requestValidate, String responseMock, Integer callCount,
			String status, String mark, User user) {
		super();
		this.mockId = mockId;
		this.mockName = mockName;
		this.mockUrl = mockUrl;
		this.requestValidate = requestValidate;
		this.responseMock = responseMock;
		this.callCount = callCount;
		this.status = status;
		this.mark = mark;
		this.user = user;
	}

	public InterfaceMock() {
		super();
	}
	
	
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
		
	public String getProtocolType() {
		return protocolType;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public Integer getMockId() {
		return mockId;
	}

	public void setMockId(Integer mockId) {
		this.mockId = mockId;
	}

	public String getMockName() {
		return mockName;
	}

	public void setMockName(String mockName) {
		this.mockName = mockName;
	}

	public String getMockUrl() {
		return mockUrl;
	}
	
	public String getMockUri() {
		if (MessageKeys.ProtocolType.http.name().equalsIgnoreCase(this.protocolType)) {
			return CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_HOME)  + "/mock" + this.mockUrl;
		}

        MockServer mockServer = CacheUtil.getMockServers().get(this.mockId);
		if (mockServer != null) {
            return mockServer.getMockUrl() + (StringUtils.isNotBlank(this.mockUrl) ? this.mockUrl : "");
        }

		return "未启用";
	}
	
	public void setMockUri(String mockUri) {
		this.mockUri = mockUri;
	}

	public void setMockUrl(String mockUrl) {
		this.mockUrl = mockUrl;
	}

	public String getRequestValidate() {
		return requestValidate;
	}

	public void setRequestValidate(String requestValidate) {
		this.requestValidate = requestValidate;
	}

	@JSON(serialize=false)
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public String getResponseMock() {
		return responseMock;
	}

	public void setResponseMock(String responseMock) {
		this.responseMock = responseMock;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "InterfaceMock [mockId=" + mockId + ", mockName=" + mockName
				+ ", mockUrl=" + mockUrl + ", requestValidate="
				+ requestValidate + ", responseMock=" + responseMock
				+ ", callCount=" + callCount + ", status=" + status + ", mark="
				+ mark + ", createTime=" + createTime + ", mockUri=" + mockUri
				+ ", protocolType=" + protocolType + "]";
	}
}
