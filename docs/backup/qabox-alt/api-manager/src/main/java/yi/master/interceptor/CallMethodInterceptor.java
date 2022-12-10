package yi.master.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.log.enums.LogCallType;
import yi.master.business.log.enums.LogInterceptStatus;
import yi.master.business.log.service.LogRecordService;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.system.service.OperationInterfaceService;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.listener.VersionUpdateUtil;
import yi.master.util.FrameworkUtil;
import yi.master.util.MD5Util;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 每个用户调用任何一个操作接口都必须经过本拦截器
 * 结合角色权限表和操作接口表来进行权限控制
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */
@SuppressWarnings("serial")
@Controller
public class CallMethodInterceptor extends AbstractInterceptor {
	
	private static Logger logger = Logger.getLogger(CallMethodInterceptor.class.getName());
	@Autowired
	private OperationInterfaceService operationInterfaceService;
	
	@Autowired
	private LogRecordService recordService;
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		ActionContext actionContext = arg0.getInvocationContext();
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		//判断当前系统是否需要重启
		if (VersionUpdateUtil.getIsNeedRestart()) {
			response.setHeader("needrestart", "true");
			response.setStatus(HttpStatus.SC_FORBIDDEN);
			throw new YiException(AppErrorCode.SYSTEM_IS_NEED_RESTART);
		}


		User user = null;
		OperationInterface opInterface = null;
		String callUrl = null; 
		String interceptStatus = LogInterceptStatus.SUCCESS.getStatus();
		String callType = LogCallType.USER_CALLED.getType();
		String userHost = null; 
		String browserAgent = null;
		int validateTime = 0;
		int executeTime = 0;
		String requestParams = null;
		String responseParams = null;
		String mark = null;	
		

		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		CacheUtil.setHomeUrl(request);
		browserAgent = request.getHeader("User-Agent");
		userHost = PracticalUtils.getIpAddr(request);
		
		long beginTime = System.currentTimeMillis();
		//请求接口路径
		callUrl = arg0.getProxy().getActionName();
		Map paramMap = arg0.getInvocationContext().getParameters();	
		try {
			requestParams = JSONObject.fromObject(paramMap).toString();
		} catch (Exception e) {

		}

		Object obj = arg0.getAction();
		BaseAction action = null;
		if (obj instanceof BaseAction) {
			action = (BaseAction) obj;
		}
		
		//内部调用带上指定的token直接通过
		String[] tokens = (String[]) paramMap.get(SystemConsts.API_TOKEN_ATTRIBUTE_NAME);
		if (tokens != null && SystemConsts.REQUEST_ALLOW_TOKEN.equals(tokens[0])) {		
			callType = LogCallType.SELF_CALLED.getType();
			String result = null;
			
			try {
				long time1 = System.currentTimeMillis();
				result = arg0.invoke();
				long time2 = System.currentTimeMillis();
				
				validateTime = Integer.valueOf(String.valueOf(time1 - beginTime));
				executeTime = Integer.valueOf(String.valueOf(time2 - time1));
			} catch (Exception e) {
				
				validateTime = Integer.valueOf(String.valueOf(System.currentTimeMillis() - beginTime));
				
				logger.error("系统异常,请求失败!", e);
				
				mark = PracticalUtils.getExceptionAllinformation(e);
				interceptStatus = LogInterceptStatus.SYSTEM_ERROR.getStatus();
				
				recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
						validateTime, executeTime, requestParams, responseParams, mark);
				throw e;
			}
			
			interceptStatus = LogInterceptStatus.SUCCESS.getStatus();
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);
			return result;
		}	
		
		String[] userHosts =  (String[]) paramMap.get("host");
		if (userHosts != null) {
			userHost =  userHosts[0];
		}
		
		String[] browserAgents = (String[]) paramMap.get("agent");
		if (browserAgents != null) {
			browserAgent = browserAgents[0];
		}
				
		String timeTag = MD5Util.code(String.valueOf(beginTime) + new Random().nextInt(10000));
		try {
			//当前所有接口信息
			List<OperationInterface> ops = CacheUtil.getSystemInterfaces();
			
			logger.info("[" + timeTag + "]" + "调用接口:" + callUrl + "\n入参：" +  requestParams
					+ "\n进行权限校验...");
			
			//判断该接口是否为通用接口(不存在于系统接口列表中即认为是通用接口,不需要任何验证就可以调用)
			int isCommon = 0;
			//在系统接口列表中查找本次调用的接口			
			for (OperationInterface op:ops) {
				if(op.getCallName().equals(callUrl)) {
					isCommon = 1;
					opInterface = op;
					break;
				}
			}
			
			if (isCommon == 0) {
				long endTime = System.currentTimeMillis();
				String result = arg0.invoke();					
				long endTime2 = System.currentTimeMillis();
				
				executeTime = Integer.valueOf(String.valueOf(endTime2 - endTime));
				validateTime = Integer.valueOf(String.valueOf(endTime - beginTime));
							
				logger.info("[" + timeTag + "]" + "接口" + callUrl + "未在接口列表定义,"
						+ "为通用接口,请求放行!\n验证耗时：" +  validateTime 
						+ "ms.执行耗时：" + executeTime + "ms.");		
				
				if (action != null) {
					//responseParams = JSONObject.fromObject(action.getJsonObject()).toString();
					logger.info("[" + timeTag + "]接口" + callUrl + "出参\n" + responseParams);
				}
				
				interceptStatus = LogInterceptStatus.PERMIT_THROUGH.getStatus();
				
				recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
						validateTime, executeTime, requestParams, responseParams, mark);
				return result;
			}
			
			
			//判断用户是否登录
			//获取当前登录用户
			user = FrameworkUtil.getLoginUser();
			
			if (user == null) {
				logger.info("[" + timeTag + "]" + "用户未登录,调用接口" + callUrl + "失败!\n验证耗时：" +  (System.currentTimeMillis() - beginTime) + "ms.");
				interceptStatus = LogInterceptStatus.NO_LOGIN.getStatus();
				
				recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
						validateTime, executeTime, requestParams, responseParams, mark);

				throw new YiException(AppErrorCode.NO_LOGIN);
			}
			
			String userTag = "[" + "用户名:" + user.getUsername() + ",ID=" + user.getUserId() + "]";
			//判断该接口是否正常可调用
			if (!"0".equals(opInterface.getStatus())) {
				validateTime = Integer.valueOf(String.valueOf(System.currentTimeMillis() - beginTime));
				logger.info("[" + timeTag + "]" + userTag + "当前接口" + callUrl + "已被禁用!\n验证耗时：" +  validateTime + "ms.");
				interceptStatus = LogInterceptStatus.INTERFACE_DISABLED.getStatus();
				
				recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
						validateTime, executeTime, requestParams, responseParams, mark);

				throw new YiException(AppErrorCode.OP_DISABLE);
			}
			
			//判断当前用户是否拥有调用权限
			//获取登录用户所属角色的权限信息
			List<OperationInterface> ops1 = operationInterfaceService.listByRoleId(user.getRole().getRoleId());
			
			int isPrivilege = 0;
			//超级管理员用户ID=1可以调用一切接口
			if (SystemConsts.DefaultObjectId.ADMIN_USER.getId() != user.getUserId()) {
				isPrivilege = 1;
				
				for (OperationInterface op:ops1) {
					if(op.getCallName().equals(callUrl)) {
						isPrivilege = 0;
						break;
					}
				}
			}

			if (isPrivilege == 1) {
				validateTime = Integer.valueOf(String.valueOf(System.currentTimeMillis() - beginTime));
				logger.info("[" + timeTag + "]" + userTag + "用户没有调用接口" + callUrl + "的权限,调用失败!\n验证耗时：" +  validateTime + "ms.");
				
				interceptStatus = LogInterceptStatus.NO_PERMISSION.getStatus();
				
				recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
						validateTime, executeTime, requestParams, responseParams, mark);

				throw new YiException(AppErrorCode.NO_PERMISSION);
			}
			
			long endTime = System.currentTimeMillis();
			String result = arg0.invoke();
			long endTime2 = System.currentTimeMillis();	
			
			executeTime = Integer.valueOf(String.valueOf(endTime2 - endTime));
			validateTime = Integer.valueOf(String.valueOf(endTime - beginTime));
			
			logger.info("[" + timeTag + "]" + userTag + "当前接口" + callUrl + "权限验证通过!\n验"
					+ "证耗时：" +  validateTime 
					+ "ms.执行耗时：" + executeTime + "ms.");	
						
			if (action != null) {
				//responseParams = JSONObject.fromObject(action.getJsonObject()).toString();
				logger.info("[" + timeTag + "]接口" + callUrl + "出参\n" + responseParams);
			}	
			
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);
			return result;
			
		} catch (Exception e) {
			validateTime = Integer.valueOf(String.valueOf(System.currentTimeMillis() - beginTime));
			logger.info("[" + timeTag + "]验证耗时：" +  validateTime + "ms.");

			if (!(e instanceof YiException)) {
				interceptStatus = LogInterceptStatus.SYSTEM_ERROR.getStatus();
				mark = PracticalUtils.getExceptionAllinformation(e);
			}
			
			recordService.saveRecord(user, opInterface, callUrl, interceptStatus, callType, userHost, browserAgent,
					validateTime, executeTime, requestParams, responseParams, mark);
			throw e;			
		}
			
	}
}
