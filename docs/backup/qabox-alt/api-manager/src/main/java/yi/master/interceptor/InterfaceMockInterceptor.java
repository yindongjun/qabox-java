package yi.master.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import yi.master.business.advanced.action.MockAction;
import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.advanced.enums.InterfaceMockStatus;
import yi.master.business.advanced.service.InterfaceMockService;
import yi.master.business.log.enums.LogCallType;
import yi.master.business.log.enums.LogInterceptStatus;
import yi.master.business.log.service.LogRecordService;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.user.bean.User;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import javax.servlet.http.HttpServletRequest;


/**
 * Http/https接口Mock拦截器
 * @author xuwangcheng
 * @version 1.0.0.0,2018.4.1
 */
public class InterfaceMockInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(InterfaceMockInterceptor.class.getName());
	
	@Autowired
	private InterfaceMockService interfaceMockService;
	
	@Autowired
	private LogRecordService recordService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		User user = null;
		OperationInterface opInterface = null;
		String callUrl = null; 
		String interceptStatus = LogInterceptStatus.SUCCESS.getStatus();
		String callType = LogCallType.MOCK.getType();
		String userHost = null; 
		String browserAgent = null;
		int validateTime = 0;
		int executeTime = 0;
		String requestParams = null;
		String responseParams = null;
		String mark = null;	
		
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		
		browserAgent = request.getHeader("User-Agent");
		userHost = PracticalUtils.getIpAddr(request);
		
		String homeUrl = CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_HOME);
		callUrl = request.getRequestURI().replace(homeUrl.substring(homeUrl.lastIndexOf("/")), "").substring(5);		
		
		InterfaceMock mock = interfaceMockService.findByMockUrl(callUrl, MessageKeys.ProtocolType.http.name().toUpperCase());
		if (mock == null) {
			interceptStatus = LogInterceptStatus.MOCK_NOT_EXIST.getStatus();
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);

			throw new YiException(AppErrorCode.MOCK_INTERFACE_NOT_EXIST);
		}
		
		if (InterfaceMockStatus.DISABLED.getStatus().equals(mock.getStatus())) {
			interceptStatus = LogInterceptStatus.INTERFACE_DISABLED.getStatus();
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);

			throw new YiException(AppErrorCode.MOCK_INTERFACE_DISABLED);
		}
		String result = null;
		try {
			result = invocation.invoke();
			MockAction action = (MockAction) invocation.getAction();
			responseParams = action.getResponseMsg();
			requestParams = action.getRequestMsg();
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);
		} catch (Exception e) {
			logger.error(request.getRequestURI() + " 接口mock出错!", e);
			interceptStatus =  LogInterceptStatus.MOCK_ERROR.getStatus();
			mark = PracticalUtils.getExceptionAllinformation(e);
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);
			throw new YiException(AppErrorCode.MOCK_ERROR);
		}

		return result;
	}
	
}
