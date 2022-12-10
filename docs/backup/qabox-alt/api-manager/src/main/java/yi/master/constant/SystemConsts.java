package yi.master.constant;




/**
 * 系统相关常量
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */
public interface SystemConsts {

	/**
	 * 当前版本号
	 */
	String VERSION = "1.1.4";


	/**
	 * 测试完成标志
	 */
	enum FinishedFlag {
		N,Y;
	}

	/**
	 * boolean定义
	 */
	enum DefaultBooleanIdentify  {
		/**
		 * true、1
		 */
		TRUE("true", "1"),
		/**
		 * false、0
		 */
		FALSE("false", "0");

		private String string;
		private String number;

		private DefaultBooleanIdentify (String string, String number) {
			this.string = string;
			this.number = number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public void setString(String string) {
			this.string = string;
		}

		public String getNumber() {
			return number;
		}

		public String getString() {
			return string;
		}
	}

	/**
	 * 默认全局的result
	 */
	enum GlobalResultName {
		/**
		 * 用户未登录
		 */
		usernotlogin,
		/**
		 * 操作接口不可用
		 */
		opisdisable,
		/**
		 * 没有权限
		 */
		usernotpower,
		/**
		 * mock接口不存在
		 */
		nonMockInterface,
		/**
		 * mock接口已被禁用
		 */
		mockInterfaceDisabled;

	}

	/**
	 * 一些默认的数据库对象ID
	 */
	enum DefaultObjectId {
		/**
		 * 默认admin角色的roleId
		 */
		ADMIN_ROLE(1),
		/**
		 * 默认default角色的roleId
		 */
		DEFAULT_ROLE(3),
		/**
		 * 默认ADMIN账户的用户ID
		 */
		ADMIN_USER(1),
		/**
		 * 特殊参数的id:数组中的数组参数对象
		 */
		PARAMETER_ARRAY_IN_ARRAY(2),
		/**
		 * 特殊参数的id:数组中的Map参数对象
		 */
		PARAMETER_MAP_IN_ARRAY(3),
		/**
		 * 特殊参数的id:Object对象 对应外层
		 */
		PARAMETER_OBJECT(1),
        /**
         * 特殊参数的ID：Array对象 对应外层
         */
        PARAMETER_OUTER_ARRAY(4),
		/**
		 * 默认项目ID
		 */
		DEFAULT_PROJECT_ID(1);

		private int id;

		private DefaultObjectId (int id) {
			this.id = id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}




	String DEFAULT_USER_PASSWORD = "111111";
	
	/**
	 * 请求带上此token代表为内部自调用接口，不需要验证权限
	 */
	String REQUEST_ALLOW_TOKEN = "ec189a1731d73dfe16d8f9df16d67187";


	String API_TOKEN_ATTRIBUTE_NAME = "token";

	/**
	 * sessionMap中登录用户key值
	 */
	String SESSION_ATTRIBUTE_LOGIN_USER = "user";

    /**
     * SessionMap中指定属性名
     */
	String SESSION_ATTRIBUTE_VERIFY_CODE = "verifyCode";

    /**
     * 定时任务相关标志词语
     */
	String QUARTZ_TIME_TASK_NAME_PREFIX_KEY = "timeScheduleJob";
	String QUARTZ_PROBE_TASK_NAME_PREFIX_KEY = "probeScheduleJob";	
	String QUARTZ_SCHEDULER_START_FLAG = "quartzStatus";	
	String QUARTZ_SCHEDULER_IS_START = "true"; 	
	String QUARTZ_SCHEDULER_IS_STOP = "false";
	
	///////////////////////////////////全局配置相关/////////////////////////////////////////////////////////////////////
	/**
	 * 通用设置
	 */
	String GLOBAL_SETTING_HOME = "home";
	String GLOBAL_SETTING_VERSION = "version";
	String GLOBAL_SETTING_LOG_SWITCH = "logSwitch";

	/**
	 * 接口自动化测试相关全局配置
	 */
	String GLOBAL_SETTING_MESSAGE_ENCODING = "messageEncoding";
	String GLOBAL_SETTING_MESSAGE_REPORT_TITLE = "messageReportTitle";
	String GLOBAL_SETTING_MESSAGE_CALL_HOME_URL = "messageCallHomeUrl";
	/**
	 * 邮件推送格式
	 */
	String GLOBAL_SETTING_MESSAGE_MAIL_STYLE = "messageMailStyle";
	/**
	 * 探测邮件格式
	 */
	String GLOBAL_SETTING_MESSAGE_MAIL_STYLE_PROBE_REPORT = "probe";
	/**
	 * 定时任务邮件格式
	 */
	String GLOBAL_SETTING_MESSAGE_MAIL_STYLE_TASK_REPORT = "time";

	/**
	 * web自动化脚本相关
	 */
	String GLOBAL_SETTING_WEB_SCRIPT_WORKPLACE = "webscriptWorkPlace";
	String GLOBAL_SETTING_WEB_SCRIPT_MODULE_PATH = "webscriptModulePath";

	/**
	 * 邮箱推送相关
	 */
	String GLOBAL_SETTING_IF_SEND_REPORT_MAIL = "sendReportMail";
	String GLOBAL_SETTING_MAIL_SERVER_HOST = "mailHost";
	String GLOBAL_SETTING_MAIL_SERVER_PORT = "mailPort";
	String GLOBAL_SETTING_MAIL_AUTH_USERNAME = "mailUsername";
	String GLOBAL_SETTING_MAIL_AUTH_PASSWORD = "mailPassword";
	String GLOBAL_SETTING_MAIL_RECEIVE_ADDRESS = "mailReceiveAddress";
	String GLOBAL_SETTING_MAIL_COPY_ADDRESS = "mailCopyAddress";
	String GLOBAL_SETTING_MAIL_SSL_FLAG = "mailSSL";
	String GLOBAL_SETTING_MAIL_SEND_ADDRESS = "mailSendAddress";
	String GLOBAL_SETTING_MAIL_PERSONAL_NAME = "mailPersonalName";
	
	/**
	 * 日志记录接口白名单
	 */
	String GLOBAL_SETTING_LOG_RECORD_WHITELIST = "logRecordWhitelist";
	/**
	 * 日志记录接口黑名单
	 */
	String GLOBAL_SETTING_LOG_RECORD_BLACKLIST = "logRecordBlacklist";
	
	/////////////////////////////////////文件存放路径/////////////////////////////////////////////////////////////

	/**
	 * 测试报告静态html存储文件夹
	 */
	String REPORT_VIEW_HTML_FOLDER = "reportHtml";
    /**
     * 上传或者下载 excel保存的文件夹
     */
    String EXCEL_FILE_FOLDER = "excel";

	/**
	 * 静态报告模板
	 */
	String REPORT_VIEW_HTML_FIXED_HTML_NEW = "offlineReportTemplateNew.xml";


	////////////////////////////////////内部请求地址//////////////////////////////////////////////////////////
	/**
	 * 测试集测试请求地址
	 */
	String AUTO_TASK_TEST_RMI_URL = "test-scenesTest";
	
	/**
	 * 接口探测测试请求地址
	 */
	String PROBE_TASK_TEST_RMI_URL = "test-probeTest";
	
	/**
	 * 生成静态报告请求地址
	 */
	String CREATE_STATIC_REPORT_HTML_RMI_URL = "report-generateStaticReportHtml";



    ///////////////////////////////////版本升级相关////////////////////////////////////////////////////////
	/**
	 * 检查版本升级的网址
	 */
	String CHECK_VERSION_UPGRADE_URL = "https://www.xuwangcheng.com/yi/api/checkVersion";
	//String CHECK_VERSION_UPGRADE_URL = "http://localhost:8080/api/checkVersion";

	/**
	 * 版本升级地址
	 */
	String VERSION_UPGRADE_URL = "https://gitee.com/xuwangcheng/masteryi-automated-testing/wikis/pages?sort_id=1608611&doc_id=196989";
}
