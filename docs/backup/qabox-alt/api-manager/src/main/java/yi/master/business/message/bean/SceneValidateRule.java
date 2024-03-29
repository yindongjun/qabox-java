package yi.master.business.message.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.annotation.FieldRealSearch;
import yi.master.business.testconfig.bean.TestConfig;

import java.io.Serializable;

/**
 * 出参验证规则
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,20170502
 *
 */
public class SceneValidateRule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer validateId;
	private MessageScene messageScene;
	private TestConfig testConfig;
	
	/**
	 * 根据validate_method_flag的值有不同的含义：<br>
	 * validate_method_flag=0时，其为取值的json串，例如：{"LB":"ss","RB":"vv","ORDER":1},分别表示左边界、右边界、取值顺序<br>
	 * validate_method_flag=1时,该值表示节点路径<br>		
	 */
	private String parameterName;
	
	/**
	 * 验证值
	 */
	private String validateValue;
	
	/**
	 * 获取validate_value的方式<br>
	 */
	@FieldRealSearch(names = {"常量", "入参节点值", "SQL语句", "全局变量", "正则表达式"}, values = {"0", "1", "999999", "3", "4"})
	private String getValueMethod;
	
	/**
	 * 0-左右边界取关键字验证<br>
	 * 1-节点参数验证<br>
	 */
	@FieldRealSearch(names = {"关联验证", "节点验证", "自定义验证"}, values = {"0", "1", "2"})
	private String validateMethodFlag;

    /**
     * 比对条件
     */
	private String validateCondition;
	
	/**
	 * 当validate_method_flag为1时有效：<br>
	 * 复杂参数标记<br>
	 * 0-复杂节点，1-简单节点
	 */
	@FieldNameMapper(ifSearch=false)
	private String complexFlag;
	
	/**
	 * 当validate_method_flag为1时有效：<br>
	 * 0-需要强制验证,1-不需要验证
	 */
	@FieldRealSearch(names = {"正常", "禁用"}, values = {"0", "1"})
	private String status;
	private String mark;
	

	public SceneValidateRule(Integer validateId, MessageScene messageScene,
			String parameterName, String validateValue, String getValueMethod,
			String validateMethodFlag, String complexFlag, String status,
			String mark) {
		super();
		this.validateId = validateId;
		this.messageScene = messageScene;
		this.parameterName = parameterName;
		this.validateValue = validateValue;
		this.getValueMethod = getValueMethod;
		this.validateMethodFlag = validateMethodFlag;
		this.complexFlag = complexFlag;
		this.status = status;
		this.mark = mark;
	}




	public SceneValidateRule() {
		super();
		
	}


    public void setValidateCondition(String validateCondition) {
        this.validateCondition = validateCondition;
    }

    public String getValidateCondition() {
        return validateCondition;
    }

    public String getGetValueMethod() {
		return getValueMethod;
	}
	public void setGetValueMethod(String getValueMethod) {
		this.getValueMethod = getValueMethod;
	}
	public Integer getValidateId() {
		return validateId;
	}
	public void setValidateId(Integer validateId) {
		this.validateId = validateId;
	}
	
	@JSON(serialize=false)
	public MessageScene getMessageScene() {
		return messageScene;
	}
	public void setMessageScene(MessageScene messageScene) {
		this.messageScene = messageScene;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getValidateValue() {
		return validateValue;
	}
	public void setValidateValue(String validateValue) {
		this.validateValue = validateValue;
	}
	
	public String getValidateMethodFlag() {
		return validateMethodFlag;
	}

	public void setValidateMethodFlag(String validateMethodFlag) {
		this.validateMethodFlag = validateMethodFlag;
	}

	public String getComplexFlag() {
		return complexFlag;
	}
	public void setComplexFlag(String complexFlag) {
		this.complexFlag = complexFlag;
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

	public void setTestConfig(TestConfig testConfig) {
		this.testConfig = testConfig;
	}

	@JSON(serialize=false)
	public TestConfig getTestConfig() {
		return testConfig;
	}

	@Override
	public String toString() {
		return "SceneValidateRule [validateId=" + validateId
				+ ", parameterName=" + parameterName + ", validateValue="
				+ validateValue + ", getValueMethod=" + getValueMethod
				+ ", validateMethodFlag=" + validateMethodFlag
				+ ", complexFlag=" + complexFlag + ", status=" + status
				+ ", mark=" + mark + "]";
	}
	
	
		
}
