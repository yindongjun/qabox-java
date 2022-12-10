package yi.master.constant;

import yi.master.coretest.message.parse.*;
import yi.master.coretest.message.process.*;
import yi.master.coretest.message.protocol.*;

import java.util.regex.Pattern;

/**
 * 接口报文相关常量
 * @author xuwangcheng
 * @version 2017.12.12,1.0.0.6
 * 
 *
 */
public interface MessageKeys {

	/**
	 * 接口业务类型：CX-查询类接口、SL-办理类接口
	 */
	enum InterfaceBusiType {
		CX,SL;
	}

	/**
	 * 支持的报文格式
	 */
	enum MessageType {
		/**
		 * json类型
		 */
		JSON(JSONMessageParse.getInstance()),
		/**
		 * xml类型
		 */
		XML(XMLMessageParse.getInstance()),
		/**
		 * url类型
		 */
		URL(URLMessageParse.getInstance()),
		/**
		 * 固定类型
		 */
		FIXED(FixedMessageParse.getInstance()),
		/**
		 * 自定义类型
		 */
		OPT(OPTMessageParse.getInstance());

		private MessageParse parseUtil;

		public MessageParse getParseUtil() {
			return parseUtil;
		}

		public void setParseUtil(MessageParse parseUtil) {
			this.parseUtil = parseUtil;
		}

		private MessageType (MessageParse parseUtil) {
			this.parseUtil = parseUtil;
		}

		public static MessageParse getParseUtilByType (String type) {
			for (MessageType mt:values()) {
				if (mt.name().equalsIgnoreCase(type)) {
					return mt.getParseUtil();
				}
			}
			return OPT.getParseUtil();
		}
	}

	/**
	 * 排序类型
	 */
	enum QueryOrderType {
		DESC,ASC;
	}


	/**
	 * 报文参数节点类型
	 */
	enum MessageParameterType {
		MAP,
		ARRAY,
		NUMBER,
		STRING,
		OBJECT,
		LIST,
		ARRAY_MAP,
		ARRAY_ARRAY,
        OUTER_ARRAY,
		CDATA;

		/**
		 * 是否为对象类型：OBJECT MAP ARRAY_MAP
		 * @param type
		 * @return
		 */
		public static boolean isObjectType (String type) {
			if (type == null) {
				return false;
			}

			return Pattern.matches(OBJECT.name() + "|" + MAP.name() + "|" + ARRAY_MAP.name(), type.toUpperCase());
		}

		/**
		 * 是否为数组类型：ARRAY ARRAY_ARRAY LIST
		 * @param type
		 * @return
		 */
		public static boolean isArrayType (String type) {
			if (type == null) {
				return false;
			}

			return Pattern.matches(ARRAY.name() + "|" + ARRAY_ARRAY.name() + "|" + LIST.name() + "|" + OUTER_ARRAY.name(), type.toUpperCase());
		}

		/**
		 * 是否为字符串或者数字类型：STRING NUMBER CDATA
		 * @param type
		 * @return
		 */
		public static boolean isStringOrNumberType(String type) {
			if (type == null) {
				return false;
			}

			return Pattern.matches(STRING.name() + "|" + NUMBER.name() + "|" + CDATA.name(), type.toUpperCase());
		}
	}


	/**
	 * 接口协议类型
	 */
	enum ProtocolType {
		/**
		 * http测试
		 */
		http(HTTPTestClient.getInstance(), "HTTP"),
		/**
		 * webservice测试
		 */
		webservice(WebserviceTestClient.getInstance(), "WebService"),
		/**
		 * socket测试客
		 */
		socket(SocketTestClient.getInstance(), "Socket"),
		/**
		 * https测试
		 */
		https(HTTPTestClient.getInstance(), "HTTPS"),
		/**
		 * dubbo测试
		 */
		dubbo(DubboTestClient.getInstance(), "Dubbo"),
		/**
		 * webSocket测试
		 */
		websocket(WebSocketTestClient.getInstance(), "WebSocket");

        /**
         * 对应的协议客户端
         */
		private TestClient client;
        /**
         * 标识符号
         */
		private String identify;

		ProtocolType(TestClient client, String identify) {
			this.client = client;
			this.identify = identify;
		}

		public TestClient getClient() {
			return client;
		}

        public String getIdentify() {
            return identify;
        }

        public static TestClient getClientByType (String type) {
			for (ProtocolType p:values()) {
				if (p.name().equalsIgnoreCase(type)) {
					return p.getClient();
				}
			}

			return null;
		}
	}

	/**
	 * 执行结果中对应的状态<br>
	 */
	enum TestRunStatus {
		/**
		 * 0 - 正常
		 */
		SUCCESS("0"),
		/**
		 * 1 - 执行失败或者验证不通过
		 */
		FAIL("1"),
		/**
		 * 2 -异常结束
		 */
		STOP("2");

		private String code;

		TestRunStatus (String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}

	/**
	 * 报文处理类型
	 */
	enum MessageProcessType {
		/**
		 * 示例报文处理器
		 */
		ProcessDemo(DemoMessageProcess.getInstance());

		private MessageProcess processor;

		private MessageProcessType (MessageProcess processor) {
			this.processor = processor;
		}

		public void setProcessor(MessageProcess processor) {
			this.processor = processor;
		}

		public MessageProcess getProcessor() {
			return processor;
		}

		public static MessageProcess getProcessorByType(String type) {
			for (MessageProcessType m:values()) {
				if (m.name().equalsIgnoreCase(type)) {
					return m.getProcessor();
				}
			}

			return null;
		}
	}


	/**协议公共调用参数*/
	String PUBLIC_PARAMETER_CONNECT_TIMEOUT = "ConnectTimeOut";
	String PUBLIC_PARAMETER_READ_TIMEOUT = "ReadTimeOut";
	String PUBLIC_PARAMETER_USERNAME = "Username";
	String PUBLIC_PARAMETER_PASSWORD = "Password";
	String PUBLIC_PARAMETER_METHOD = "Method";

	/**Socket协议*/
	String SOCKET_SEND_MSG_TYPE = "sendType";


	/**HTTP协议调用参数*/
	String HTTP_PARAMETER_HEADER = "Headers";
	String HTTP_PARAMETER_QUERYS = "Querys";
	String HTTP_PARAMETER_AUTHORIZATION = "Authorization";
	String HTTP_PARAMETER_ENC_TYPE = "EncType";
	String HTTP_PARAMETER_REC_ENC_TYPE = "RecEncType";

	/**webservice协议调用参数*/
	String WEB_SERVICE_PARAMETER_NAMESPACE = "Namespace";


	/**默认path路径根节点*/
	String MESSAGE_PARAMETER_DEFAULT_ROOT_PATH = "TopRoot";
	String MESSAGE_PARAMETER_CUSTOM_PATH_NAME = "path";


	/**测试环境中默认路径中的替换变量*/
	String BUSINESS_SYSTEM_DEFAULTPATH_NAME_ATTRIBUTE = "\\$\\{name\\}";
	String BUSINESS_SYSTEM_DEFAULTPATH_PATH_ATTRIBUTE = "\\$\\{path\\}";


	/**
	 * 使用节点参数时需要再参数路径左右加上以下左右边界
	 */
	String CUSTOM_PARAMETER_BOUNDARY_SYMBOL_LEFT = "#";
	String CUSTOM_PARAMETER_BOUNDARY_SYMBOL_RIGHT = "#";

    /**
     * 组合场景内使用上下文变量的左右变量
     */
	String COMPLEX_SCENE_USE_VARIABLE_BOUNDARY_SYMBOL_LEFT = "${";
	String COMPLEX_SCENE_USE_VARIABLE_BOUNDARY_SYMBOL_RIGHT = "}";

	/**
	 * quartz定时任务执行的测试将会在对应的测试报告添加下面的备注
	 */
	String QUARTZ_AUTO_TEST_REPORT_MARK = "自动化定时任务";
    /**
     * 第三方通过API调用执行的测试报告备注
     */
	String API_CALL_TEST_REPORT_MARK = "外部API调用";

	/**
	 * 缺少测试数据时测试详情中的备注
	 */
	String NO_ENOUGH_TEST_DATA_RESULT_MARK = "缺少测试数据";
	
}
