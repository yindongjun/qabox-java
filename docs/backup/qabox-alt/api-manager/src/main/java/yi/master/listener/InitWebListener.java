package yi.master.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.advanced.service.InterfaceMockService;
import yi.master.business.log.LogRecordStorageTimeTask;
import yi.master.business.system.bean.GlobalSetting;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.system.service.GlobalSettingService;
import yi.master.business.system.service.OperationInterfaceService;
import yi.master.business.testconfig.bean.DataDB;
import yi.master.business.testconfig.service.DataDBService;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.test.mock.MockServer;
import yi.master.coretest.task.JobManager;
import yi.master.util.FrameworkUtil;
import yi.master.util.cache.CacheUtil;
import yi.master.constant.CustomSettingVariable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;


/**
 * 初始化Web操作-加载当前操作接口列表、加载网站全局设置
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.14
 */

public class InitWebListener implements ServletContextListener {
	
	private static final Logger LOGGER = Logger.getLogger(InitWebListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//处理未入库的信息
		while (CacheUtil.saveRecord()) {}
		
		//关闭Mock Socket Server
		for (MockServer server:CacheUtil.getMockServers().values()) {
			server.stop();
		}
		
		LOGGER.info("接口自动化测试平台已关闭!");
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		LOGGER.info("正在启动接口自动化测试平台...");
		
		ServletContext context = arg0.getServletContext();
		FrameworkUtil.setWebContext(context);
		//取得appliction上下文
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		FrameworkUtil.setCtx(ctx);
		
		//取得指定bean
		OperationInterfaceService opService =(OperationInterfaceService)ctx.getBean("operationInterfaceService");
		GlobalSettingService settingService = (GlobalSettingService) ctx.getBean("globalSettingService");
		DataDBService dbService = (DataDBService) ctx.getBean("dataDBService");
		
		//获取网站全局设置信息
		LOGGER.info("获取网站全局设置信息!");
		List<GlobalSetting> settings = settingService.findAll();
		Map<String,GlobalSetting> globalSettingMap = new HashMap<String,GlobalSetting>();
		
		for (GlobalSetting g:settings) {
			globalSettingMap.put(g.getSettingName(), g);
		}
		//放置到全局context中
		CacheUtil.setSettingMap(globalSettingMap);

		//获取系统版本号，如果与数据库中的版本号不一致则更新
		String version = CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_VERSION);
		VersionUpdateUtil.updateVersion(version);

        //获取项目根路径
        FrameworkUtil.setProjectPath(context.getRealPath(""));

		//获取自定义配置信息
        CustomSettingVariable.setSettingVariable(context);

		//获取当前系统的所有接口信息
		LOGGER.info("获取系统接口信息!");
		List<OperationInterface> ops = opService.findAll();
		CacheUtil.setSystemInterfaces(ops);

		//获取查询数据库信息
		LOGGER.info("获取测试数据源信息!");
		List<DataDB> dbs = dbService.findAll();
		Map<String,DataDB> dataDBMap = new HashMap<String,DataDB>();
		for (DataDB db:dbs) {
			dataDBMap.put(String.valueOf(db.getDbId()), db);
		}
		CacheUtil.setQueryDBMap(dataDBMap);
		
		//启动quartz定时任务
		JobManager jobManager = (JobManager) FrameworkUtil.getSpringBean("jobManager");
		jobManager.startTasks();
		context.setAttribute(SystemConsts.QUARTZ_SCHEDULER_START_FLAG, SystemConsts.QUARTZ_SCHEDULER_IS_START);

		//启动操作日志异步入库线程
		//日志信息异步入库
		new Timer().schedule(new LogRecordStorageTimeTask(), 5000, 60000);
		
		//启动所有的Mock服务
		LOGGER.info("启动所有当前可用的Mock服务!");
		List<InterfaceMock> mocks = ((InterfaceMockService) FrameworkUtil.getSpringBean(InterfaceMockService.class)).getEnableMockServer();
		for (InterfaceMock mock:mocks) {
			try {
				MockServer.getMockServerInstance(mock.getProtocolType(), mock.getMockId()).start();
			} catch (Exception e) {
				LOGGER.error("Mock Socket服务失败:mockName=" + mock.getMockName() + ",mockId=" + mock.getMockId(), e);
			}
		}
		
		LOGGER.info("接口自动化测试平台初始化完成!");
	}
	
}
