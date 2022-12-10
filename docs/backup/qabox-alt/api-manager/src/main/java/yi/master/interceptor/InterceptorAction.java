package yi.master.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.bean.ReturnJSONObject;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.PracticalUtils;

/**
 * 根据跳转请求action返回前台指定的returnCode和msg
 * 该action主要将一些通用的返回集合起来供全局调用
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class InterceptorAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger(InterceptorAction.class.getName());
	
	/**
	 * ajax调用返回给前台的map
	 */
	private ReturnJSONObject jsonObject = new ReturnJSONObject();

	
	/**
	 * 全局异常处理
	 * @return
	 */
	public String error() {
		Exception ex = (Exception)ActionContext.getContext().getValueStack().findValue("exception");
		String exDetails = PracticalUtils.getExceptionAllinformation(ex);

		if (ex instanceof YiException) {
			YiException yiEx = (YiException) ex;
			jsonObject.setMsg(yiEx.getMsg());
			jsonObject.setReturnCode(yiEx.getCode());
			return SUCCESS;
		}

		logger.error("系统内部错误:\n" + exDetails);
		jsonObject.setReturnCode(AppErrorCode.INTERNAL_SERVER_ERROR.getCode());
		jsonObject.setMsg(AppErrorCode.INTERNAL_SERVER_ERROR.getMsg());

		return SUCCESS;
	}

	public ReturnJSONObject getJsonObject() {
		return jsonObject;
	}
}
