package yi.master.business.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import yi.master.business.base.action.BaseAction;
import yi.master.business.system.bean.GlobalSetting;
import yi.master.business.system.service.GlobalSettingService;
import yi.master.constant.SystemConsts;
import yi.master.business.reportform.AnalyzeUtil;
import yi.master.util.FrameworkUtil;
import yi.master.util.ParameterMap;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

/**
 * 全局设置Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class GlobalSettingAction extends BaseAction<GlobalSetting>{

	private static final long serialVersionUID = 1L;	
	
	private GlobalSettingService globalSettingService;
	
	/*@Autowired
	private TestRedisService testRedisService;*/
	
	@Autowired
	public void setGlobalSettingService(GlobalSettingService globalSettingService) {
		super.setBaseService(globalSettingService);
		this.globalSettingService = globalSettingService;
	}
	
	/**
	 * 将设置中字段值为null的转换成""
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object processListData(Object o) {
		List<GlobalSetting> settings = (List<GlobalSetting>) o;
		for (GlobalSetting g:settings) {
			if (g.getSettingValue() == null) {
				g.setSettingValue("");
			}
		}
		
		return o;
	}
	
	/**
	 * 测试统计
	 * @return
	 */
	public String getStatisticalQuantity () {
		setData(AnalyzeUtil.countStatistics());
		return SUCCESS;
	}
	
	/**
	 * 获取当前网站的所有设置属性
	 * @return
	 */
	public String getWebSettings() {
		Map<String,GlobalSetting> settingMap = CacheUtil.getGlobalSettingMap();
		Map<String, Object> map = new HashMap<>();
		for (GlobalSetting setting:settingMap.values()) {
			map.put(setting.getSettingName(), CacheUtil.getSettingValue(setting.getSettingName()));
		}
		setData(map);
		return SUCCESS;
	}

	/**
	 * 检查版本
	 * @return
	 */
	public String checkSystemVersion () {
		setData(new ParameterMap().put("newVersion", PracticalUtils.checkVersion())
				.put("versionUpgradeUrl", SystemConsts.VERSION_UPGRADE_URL)
				.put("version", SystemConsts.VERSION));
		return SUCCESS;
	}
	
	/**
	 * 编辑指定设置项
	 */
	@Override
	public String edit() {
		for (Map.Entry<String, Object> entry:FrameworkUtil.getParametersMap().entrySet()) {
			globalSettingService.updateSetting(entry.getKey(), ((String[])entry.getValue())[0]);
			CacheUtil.updateGlobalSettingValue(entry.getKey(), ((String[])entry.getValue())[0]);
		}
		return SUCCESS;
	}
	
	/*****************************************************************************************************/

}
