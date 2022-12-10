package yi.master.util.cache;

import org.apache.commons.lang.StringUtils;
import yi.master.business.log.bean.LogRecord;
import yi.master.business.log.service.LogRecordService;
import yi.master.business.system.bean.GlobalSetting;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.system.service.OperationInterfaceService;
import yi.master.business.testconfig.bean.DataDB;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.test.mock.MockServer;
import yi.master.coretest.message.test.performance.PerformanceTestObject;
import yi.master.util.FrameworkUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * web相关的一些配置数据以及全局数据的缓存设置<br>
 * 目前暂时只能缓存在内存中
 * @author xuwangcheng
 * @version 2017.11.17,1.0.0
 *
 */
public class CacheUtil {
	
	/**
	 * 全局设置
	 */
	private static Map<String,GlobalSetting> settingMap;

	/**
	 * 被预占的测试数据
	 */
	private static Set<Integer> lockedTestDatas = new HashSet<Integer>();
	
	/**
	 * 数据源
	 */
	private static Map<String, DataDB> queryDBMap;
	
	/**
	 * 放入缓存中的操作日志信息<br>
	 * linkedList是线程不安全的，多线程下可能导致异常情况，参考<a href="http://yizhilong28.iteye.com/blog/717158">LinkedList多线程不安全的解决办法</a>
	 */
	private static Queue<LogRecord> records = new ConcurrentLinkedQueue<LogRecord>();
	
	/**
	 * 性能测试对象
	 */
	private static Map<Integer, Map<Integer, PerformanceTestObject>> ptObjects = new HashMap<Integer, Map<Integer, PerformanceTestObject>>();

    /**
     * mock相关服务，key值为对应的mock对象的mockId
     */
	private static Map<Integer, MockServer> mockServers = new HashMap<>();

	/**
	 * 系统接口
	 */
	private static List<OperationInterface> systemInterfaces = new ArrayList<>();

    /**
     * 配置的首页地址
     */
    private static String homeUrl;
	/**
	 * 自动获取的首页地址
	 */
	private static String autoGetHomeUrl;
	
	public static void setQueryDBMap(Map<String, DataDB> queryDBMap) {
		CacheUtil.queryDBMap = queryDBMap;
	}

	public static void addLockedTestData(Integer dataId) {
		CacheUtil.lockedTestDatas.add(dataId);
	}
	
	public static void removeLockedTestData(Integer dataId) {
		CacheUtil.lockedTestDatas.remove(dataId);
	}

    /**
     * 检查该测试数据是不是已经被预占了
     * @param dataId
     * @return
     */
	public static boolean checkLockedTestData(Integer dataId) {
		return CacheUtil.lockedTestDatas.contains(dataId);
	}

	/**
	 *  获取所有系统接口
	 * @author xuwangcheng
	 * @date 2019/11/24 17:20
	 * @param
	 * @return {@link List}
	 */
	public static List<OperationInterface> getSystemInterfaces () {
		return systemInterfaces;
	}

	public static void setSystemInterfaces(List<OperationInterface> systemInterfaces) {
		CacheUtil.systemInterfaces = systemInterfaces;
	}

	/**
	 *  重新获取系统接口
	 * @author xuwangcheng
	 * @date 2019/11/24 17:22
	 * @param
	 * @return
	 */
	public static void updateSystemInterfaces () {
		OperationInterfaceService opService =(OperationInterfaceService)FrameworkUtil.getSpringBean("operationInterfaceService");
		CacheUtil.systemInterfaces = opService.findAll();
	}

	/**
	 * 获取查询数据库信息列表
	 * @return
	 */
	public static Map<String, DataDB> getQueryDBMap() {
		return queryDBMap;
	}
	
	/**
	 * 根据dbId获取指定的查询数据库信息
	 * @param dbId
	 * @return
	 */
	public static DataDB getQueryDBById (String dbId) {
		return queryDBMap.get(dbId);
	}
	/**
	 * 有新增、删除、修改时更新此MAP
	 */
	public static void updateQueryDBMap(DataDB db, Integer id) {
		if (db == null) {
			//删除
			queryDBMap.remove(String.valueOf(id));			
			return;
		}
		
		//更新或者新增
		queryDBMap.put(String.valueOf(db.getDbId()), db);
	}
	
	/**
	 * 获取全局设置项
	 * @return
	 */
	public static Map<String,GlobalSetting> getGlobalSettingMap () {
		return settingMap;
	}
	
	public static void setSettingMap(Map<String, GlobalSetting> settingMap) {
		CacheUtil.settingMap = settingMap;
	}
	
	/**
	 * 获取指定名称的全局设置项
	 * @param settingName
	 * @return
	 */
	public static String getSettingValue (String settingName) {
		GlobalSetting setting = settingMap.get(settingName);
		if (setting != null) {
			return  StringUtils.isEmpty(setting.getSettingValue()) ? setting.getDefaultValue() : setting.getSettingValue();
		}
		return "";
	}
	
	/**
	 * 更新内存中的指定全局设置项的值
	 * @param settingName
	 * @param settingValue
	 */
	public static void updateGlobalSettingValue(String settingName, String settingValue) {
		for (GlobalSetting setting:settingMap.values()) {
			if (setting.getSettingName().equals(settingName)) {
				setting.setSettingValue(settingValue);
			}
		}
	}

	/**
	 * 保存日志记录
	 * @author xuwangcheng
	 * @date 2019/8/30 15:42
	 * @param
	 * @return {@link boolean}
	 */
	public static boolean saveRecord() {
		LogRecord record = 	CacheUtil.records.poll();
		if (record != null) {
			LogRecordService service = (LogRecordService) FrameworkUtil.getSpringBean("logRecordService");
			service.save(record);
			return true;
		}
		return false;
	}

	/**
	 * 新增一个日志对象
	 * @author xuwangcheng
	 * @date 2019/8/30 15:43
	 * @param record record
	 */
	public static void addRecord(LogRecord record) {
		if (record != null) {
			CacheUtil.records.offer(record);
		}		
	}

	/**
	 * 查询当前缓存的日志条数
	 * @author xuwangcheng
	 * @date 2019/8/30 15:43
	 * @param
	 * @return {@link int}
	 */
	public static int getRecordCount() {
		return CacheUtil.records.size();
	}

	/**
	 * 设置性能测试对象
	 * @author xuwangcheng
	 * @date 2019/8/30 15:43
	 * @param ptObjects ptObjects
	 */
	public static void setPtObjects(
			Map<Integer, Map<Integer, PerformanceTestObject>> ptObjects) {
		CacheUtil.ptObjects = ptObjects;
	}

	/**
	 * 获取性能测试对象集合
	 * @author xuwangcheng
	 * @date 2019/8/30 15:52
	 * @param
	 * @return {@link Map}
	 */
	public static Map<Integer, Map<Integer, PerformanceTestObject>> getPtObjects() {
		return ptObjects;
	}

	/**
	 * 获取当前登陆用户的性能测试任务列表
	 * @author xuwangcheng
	 * @date 2019/8/30 15:53
	 * @param userId userId
	 * @return {@link Map}
	 */
	public static Map<Integer, PerformanceTestObject> getPtObjectsByUserId (Integer userId) {
		Map<Integer, PerformanceTestObject> ptos = ptObjects.get(userId);
		if (ptos == null) {
			ptos = new HashMap<Integer, PerformanceTestObject>(5);
			ptObjects.put(userId, ptos);
		}
		return ptos;
	}

	/**
	 * 新增性能测试任务
	 * @author xuwangcheng
	 * @date 2019/8/30 15:53
	 * @param userId userId
	 * @param ptObject ptObject
	 */
	public static void addPtObject (Integer userId, PerformanceTestObject ptObject) {
		Map<Integer, PerformanceTestObject> pts = getPtObjectsByUserId(userId);
		pts.put(ptObject.getObjectId(), ptObject);
	}

    /**
     *  设置Mock服务集合
     * @author xuwangcheng
     * @date 2019/11/22 9:49
     * @param mockServers mockServers
     * @return
     */
	public static void setMockServers (Map<Integer, MockServer> mockServers) {
	     CacheUtil.mockServers = mockServers;
    }

    /**
     *  获取Mock服务集合
     * @author xuwangcheng
     * @date 2019/11/22 9:49
     * @param
     * @return {@link Map}
     */
    public static Map<Integer, MockServer> getMockServers () {
	    return mockServers;
    }

    /**
     * 自动获取首页地址，只有一次
     * @param request
     */
    public static void setHomeUrl (HttpServletRequest request) {
        if (StringUtils.isNotBlank(autoGetHomeUrl)) {
            return;
        }
		autoGetHomeUrl = request.getRequestURL().toString().replace(request.getServletPath(), "");
    }

    /**
     *  获取请求地址
     * @author xuwangcheng
     * @date 2020/1/2 14:43
     * @param
     * @return {@link String}
     */
    public static String getHomeUrl() {
    	if ("0".equals(getSettingValue(SystemConsts.GLOBAL_SETTING_MESSAGE_CALL_HOME_URL))) {
    		// 使用自动获取的地址
			if (StringUtils.isNotBlank(autoGetHomeUrl)) {
				return autoGetHomeUrl;
			}
		}
		// 使用配置的地址
		return getSettingValue(SystemConsts.GLOBAL_SETTING_HOME);
    }
}
