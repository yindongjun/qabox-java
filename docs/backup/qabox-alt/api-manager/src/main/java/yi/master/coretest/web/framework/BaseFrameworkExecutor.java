package yi.master.coretest.web.framework;

import yi.master.business.localize.shanxi.bean.WebScriptModule;
import yi.master.business.localize.shanxi.bean.WebScriptTask;
import yi.master.constant.WebScriptConsts;

/**
 * web自动化脚本执行类
 * @author xuwangcheng
 * @version 2018.04.23,1.0.0
 *
 */
public abstract class BaseFrameworkExecutor {
	
	/**
	 * 当前存在的没有结束的ui自动化测试任务
	 */
	private static Integer runningWebScriptId;
	private static Process runningWebScriptProcess;
	
	private static final WatirCucumberFrameworkExecutor wcfe = new WatirCucumberFrameworkExecutor();
	
	/**
	 * 开始进行本地或者远程的web自动化测试
	 * @param module
	 * @param guid
	 * @return
	 */
	public abstract WebScriptTask startTest(WebScriptModule module, String guid); 
	
	
	public static void setRunningWebScriptId(Integer runningWebScriptId) {
		BaseFrameworkExecutor.runningWebScriptId = runningWebScriptId;
	}
	
	public static Integer getRunningWebScriptId() {
		return runningWebScriptId;
	}
	
	public static void setRunningWebScriptProcess(Process runingWebScriptProcess) {
		BaseFrameworkExecutor.runningWebScriptProcess = runingWebScriptProcess;
	}
	
	public static Process getRunningWebScriptProcess() {
		return runningWebScriptProcess;
	}
	
	public static BaseFrameworkExecutor getExecutorInstance(String type) {
		switch (type.toLowerCase()) {
		case WebScriptConsts.FRAMEWORK_TYPE_WATIR_CUCUMBER:
			return wcfe;
		default:
			break;
		}
		
		return null;
	}
	
}
