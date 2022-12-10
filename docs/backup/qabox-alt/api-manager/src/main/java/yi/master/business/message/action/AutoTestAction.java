package yi.master.business.message.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.advanced.bean.InterfaceProbe;
import yi.master.business.advanced.service.InterfaceProbeService;
import yi.master.business.base.bean.ReturnJSONObject;
import yi.master.business.message.bean.ComplexScene;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.message.bean.TestData;
import yi.master.business.message.bean.TestResult;
import yi.master.business.message.enums.TestDataStatus;
import yi.master.business.message.service.*;
import yi.master.business.testconfig.bean.TestConfig;
import yi.master.business.testconfig.service.BusinessSystemService;
import yi.master.business.testconfig.service.GlobalVariableService;
import yi.master.business.testconfig.service.TestConfigService;
import yi.master.business.user.bean.User;
import yi.master.business.user.service.UserService;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.coretest.message.parse.URLMessageParse;
import yi.master.coretest.message.test.MessageAutoTest;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static yi.master.constant.MessageKeys.InterfaceBusiType;
import static yi.master.constant.MessageKeys.QUARTZ_AUTO_TEST_REPORT_MARK;

/**
 * 接口自动化
 * 接口测试Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class AutoTestAction extends ActionSupport implements ModelDriven<TestConfig> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReturnJSONObject jsonObject = new ReturnJSONObject();
	
	@Autowired
	private MessageSceneService messageSceneService;
	@Autowired
	private TestDataService testDataService;
	@Autowired
	private TestResultService testResultService;
	@Autowired
	private TestConfigService testConfigService;
	@Autowired
	private UserService userService;
	@Autowired
	private TestSetService testSetService;
	@Autowired
	private MessageAutoTest autoTest;
	@Autowired
	private InterfaceProbeService interfaceProbeService;
	@Autowired
	private BusinessSystemService businessSystemService;
	@Autowired
	private ComplexSceneService complexSceneService;
	@Autowired
	private GlobalVariableService globalVariableService;
	
	private Integer messageSceneId;
	
	private Integer dataId;
	
	private String requestUrl;
	
	private String requestMessage;
	
	private Integer setId;
	
	private TestConfig config = new TestConfig();	
	
	private Boolean autoTestFlag;
	
	private Integer probeId;
	
	private Integer systemId;
	
	private Integer id;

	/**
	 * 单场景测试的时候是否为自定义的数据，如果是，就不会校验数据的有效性了
	 */
	private Boolean customTestData;
	
	
	/**
	 * 组合场景测试
	 * @return
	 */
	public String complexSceneTest(){
		ComplexScene complexScene = complexSceneService.get(id);
		
		User user = FrameworkUtil.getLoginUser();
		TestConfig config = testConfigService.configByUserId(user.getUserId());
		if (config == null) {
			config = testConfigService.configByUserId(0);
		}
		
		if (complexScene.getSceneNum() == 0) {
			throw new YiException(AppErrorCode.AUTO_TEST_NO_SCENE);
		}
		
		Object results = autoTest.singleTestComplexScene(autoTest.packageComplexRequestObject(complexScene, config, null, null), null, true, false);
		jsonObject.data(results);
		return SUCCESS;
	}
	
	/**
	 * 探测接口测试
	 * @return
	 */
	public String probeTest () {
		InterfaceProbe task = interfaceProbeService.get(probeId);
		Integer resultId = null;
		if (task != null) {
		    //解决异步线程执行时无法获取到当前用户配置的问题
		    FrameworkUtil.getSessionMap().put(SystemConsts.SESSION_ATTRIBUTE_LOGIN_USER, task.getUser());

			//执行测试
			TestConfig config = testConfigService.configByUserId(task.getUser().getUserId());
			if (config == null) {
				config = testConfigService.configByUserId(0);
			}
			Set<TestMessageScene> testObjects = autoTest.packageRequestObject(task.getScene(), config, task.getSystem(), null);
			
			if (testObjects.size() == 0) {
				throw new YiException(AppErrorCode.AUTO_TEST_BUSINESS_SYSTEM_NOT_EXIST, task.getSystem().getSystemName());
			}
			TestMessageScene testObject = new ArrayList<TestMessageScene>(testObjects).get(0);
			
			TestResult result = autoTest.singleTest(testObject, null);
			
			result.setQualityLevel(task.getConfig().judgeLevel(result));
			//保存测试结果
			result.setInterfaceProbe(task);
			resultId = testResultService.save(result);
		}

		jsonObject.data(resultId);
		return SUCCESS;
	}
	
	
	/**
	 * 单场景调试
	 * @return
	 */
	public String sceneTest() {
		TestData d = null;
		if (!customTestData) {
			d = testDataService.get(dataId);
			if (CacheUtil.checkLockedTestData(dataId)) {
				throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "该条测试数据正在被使用，请稍后再操作");
			}

			if (d == null || TestDataStatus.USED.getStatus().equals(d.getStatus())) {
				throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "测试数据不可用,请更换");
			}
		}

		User user = FrameworkUtil.getLoginUser();
		
		MessageScene scene = messageSceneService.get(messageSceneId);
		
		TestConfig config = testConfigService.configByUserId(user.getUserId());
		
		if (config == null) {
			config = testConfigService.configByUserId(0);
		}
		
		TestMessageScene testObject = new TestMessageScene(scene, requestUrl, PracticalUtils.replaceGlobalVariable(requestMessage, globalVariableService)
					, 0, false, config, PracticalUtils.jsonToMap(scene.getMessage().getCallParameter()));
		testObject.setBusinessSystem(businessSystemService.get(systemId));
		testObject.setRequestUrl(PracticalUtils.replacePathVariableParameter(testObject.getRequestUrl(), testObject.getRequestMessage()
                , MessageParse.getParseInstance(scene.getMessageType())));
		testObject.setBodyToQuery(SystemConsts.DefaultBooleanIdentify.TRUE.getNumber().equals(scene.getMessage().getBodyToQuery()) ? true : false);
		if (testObject.isBodyToQuery()) {
			if (!URLMessageParse.getInstance().messageFormatValidation(requestMessage)) {
				throw new YiException("请将报文设置为URL格式");
			}
		}
		if (!customTestData && InterfaceBusiType.SL.name().equalsIgnoreCase(scene.getMessage().getInterfaceInfo().getInterfaceType())
					&& TestDataStatus.AVAILABLE.getStatus().equals(d.getStatus())) {
			//改变预占数据			
			CacheUtil.addLockedTestData(dataId);
			testObject.setDataId(dataId);
		}
				
		TestResult result = autoTest.singleTest(testObject, null);

		jsonObject.data(result);
		return SUCCESS;
	}
	
	/**
	 * 批量场景测试
	 * @return
	 */
	public String scenesTest() {
		User user = FrameworkUtil.getLoginUser();
		if (user == null) {
			user = userService.get(config.getUserId());
		}
		String mark = "";
		if (autoTestFlag != null) {
			mark = QUARTZ_AUTO_TEST_REPORT_MARK;
		}
		
		int[] result = autoTest.batchTest(user, setId, mark, null);
		
		if (result == null) {
			throw new YiException(AppErrorCode.AUTO_TEST_NO_SCENE);
		}

		jsonObject.data(result);
		return SUCCESS;
	}
	
	/**
	 * 获取当前用户的自动化测试配置
	 * <br>如果没有就按照全局配置复制一个
	 * @return
	 */
	public String getConfig () {
		User user = FrameworkUtil.getLoginUser();
		TestConfig config = testConfigService.configByUserId(user.getUserId());

		jsonObject.data(config);
		return SUCCESS;
	}
	
	/**
	 * 更新测试配置
	 * @return
	 */
	public String updateConfig () {
	    if (config.getDataPool() != null && config.getDataPool().getPoolId() == null) {
	        config.setDataPool(null);
        }
		testConfigService.edit(config);

	    // 重新获取config
		jsonObject.data(testConfigService.get(config.getConfigId()));
		return SUCCESS;
	}
	
	/**
	 * 测试前检查测试场景数据是否足够
	 * @return
	 */
	public String checkHasData () {
		List<MessageScene> scenes = null;
		//全量
		if (setId == 0) {
			scenes = messageSceneService.findAll();
		//测试集	
		} else {
			scenes = messageSceneService.getBySetId(setId);
		}				
		
		if (scenes.size() == 0) {
			jsonObject.data(0);
			return SUCCESS;
		}
				
		int noDataCount = 0;
		
		for(MessageScene ms:scenes){
			if(!ms.hasEnoughData(null)){
				noDataCount++;
			}								
		}
		
		jsonObject.data(noDataCount);
		return SUCCESS;
	}
	
	/*********************************GET-SET**************************************************/
	public void setConfig(TestConfig config) {
		this.config = config;
	}
	
	public void setSetId(Integer setId) {
		this.setId = setId;
	}
	
	public void setMessageSceneId(Integer messageSceneId) {
		this.messageSceneId = messageSceneId;
	}
	
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public ReturnJSONObject getJsonObject() {
		return jsonObject;
	}

	@Override
	public TestConfig getModel() {
		return this.config;
	}
	
	public void setAutoTestFlag(Boolean autoTestFlag) {
		this.autoTestFlag = autoTestFlag;
	}
	
	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setCustomTestData(Boolean customTestData) {
		this.customTestData = customTestData;
	}
}
