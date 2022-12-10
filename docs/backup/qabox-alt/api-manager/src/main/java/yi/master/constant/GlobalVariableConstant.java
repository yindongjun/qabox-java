package yi.master.constant;
/**
 * 
 * 全局变量参数名称<br>
 * 主要是保存的json串的key值
 * @author xuwangcheng
 * @version 20171201,1.0.0.0
 *
 */
public interface GlobalVariableConstant {
	
	/**UUID*/
	String UUID_SEPARATOR_ATTRIBUTE_NAME = "uuidSeparator";
	
	/**随机数*/
	String RANDOM_MIN_NUM_ATTRIBUTE_NAME = "randomMin";
	String RANDOM_MAX_NUM_ATTRIBUTE_NAME = "randomNumMax";
	
	/**格式化日期*/
	String DATETIME_FORMAT_ATTRIBUTE_NAME = "datetimeFormat";
	String DATETIME_OFFSET_ATTRIBUTE_NAME = "dateTimeOffset";
	
	/**随机字符串*/
	String RANDOM_STRING_MODE_ATTRIBUTE_NAME = "randomStringMode";
	String RANDOM_STRING_NUM_ATTRIBUTE_NAME = "randomStringNum";
	
	/**测试运行时配置*/
	String SET_RUNTIME_SETTING_REQUEST_URL_FLAG = "requestUrlFlag";
	String SET_RUNTIME_SETTING_CONNECT_TIMEOUT = "connectTimeOut";
	String SET_RUNTIME_SETTING_READ_TIMEOUT = "readTimeOut";
	String SET_RUNTIME_SETTING_RETRY_COUNT = "retryCount";
	String SET_RUNTIME_SETTING_RUN_TYPE = "runType";
	String SET_RUNTIME_SETTING_CHECK_DATA_FLAG = "checkDataFlag";
	String SET_RUNTIME_SETTING_CUSTOMR_EQUEST_URL = "customRequestUrl";
	
	/**验证模板*/
	String RELATED_KEYWORD_VALIDATE_VALUE = "validateValue";
	String RELATED_KEYWORD_VALUE_GET_METHOD = "getValueMethod";
	
	/**动态接口*/
	String DYNAMIC_INTERFACE_SYSTEM_ID = "systemId";
	String DYNAMIC_INTERFACE_SCENE_ID = "sceneId";
	String DYNAMIC_INTERFACE_VALUE_EXPRESSION = "valueExpression";

	/**动态组合接口*/
	String DYNAMIC_COMPLEX_INTERFACE_SCENE_ID = "complexSceneId";
	String DYNAMIC_COMPLEX_INTERFACE_VALUE_NAME = "valueName";
	
	/**数据库取值*/
	String DB_SQL_DB_ID = "dbId";
	String DB_SQL_SQL_STR = "sql";
	String DB_SQL_ROW_SEQ = "rowSeq";
	String DB_SQL_COL_SEQ = "colSeq";

	/**时间戳*/
	String TIMESTAMP_OFFSET_MS = "timeOffset";

	/**
	 * 使用方式，左边界
	 */
	String USE_VARIABLE_LEFT_BOUNDARY = "${__";

	/**
	 * 使用方式，右边界
	 */
	String USE_VARIABLE_RIGHT_BOUNDARY = "}";
	
	
}
