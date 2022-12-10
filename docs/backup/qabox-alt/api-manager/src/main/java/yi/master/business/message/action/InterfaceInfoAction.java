package yi.master.business.message.action;

import cn.hutool.core.util.StrUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.base.dto.ParseMessageToNodesOutDTO;
import yi.master.business.message.bean.*;
import yi.master.business.message.service.*;
import yi.master.business.testconfig.bean.BusinessSystem;
import yi.master.business.testconfig.service.BusinessSystemService;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.excel.ExportInterfaceInfo;
import yi.master.util.excel.ImportInterfaceInfo;
import yi.master.util.excel.PoiExcelUtil;

import java.sql.Timestamp;
import java.util.*;

/**
 * 接口自动化
 * 接口信息Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class InterfaceInfoAction extends BaseAction<InterfaceInfo> {

	private static final long serialVersionUID = 1L;	
	
	private InterfaceInfoService interfaceInfoService;
	
	@Autowired
	private BusinessSystemService businessSystemService;
	
	@Autowired
	private MessageService messageService;
	@Autowired
	private TestDataService testDataService;
	@Autowired
	private MessageSceneService messageSceneService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private ComplexParameterService complexParameterService;
	
	private String path;	
	
	private String ids;
	
	private String oldIds;
	
	private Integer messageId;
	
	private String updateSystems;
	
	private Integer systemId;

	private Boolean batchUpdateMessage;
	
	@Autowired
	public void setInterfaceInfoService(InterfaceInfoService interfaceInfoService) {
		super.setBaseService(interfaceInfoService);
		this.interfaceInfoService = interfaceInfoService;
	}
	
	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (systemId != null) {
			conditions.add("exists (select 1 from InterfaceInfo o join o.systems s where s.systemId=" + systemId 
					+ " and o.interfaceId=t.interfaceId)");
		}
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}

		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}


	/**
	 *  批量新增更新参数
	 * @author xuwangcheng
	 * @date 2021/3/5 16:31
	 * @param
	 * @return {@link String}
	 */
	public String batchUpdateParam(){
	    if (StringUtils.isBlank(ids)) {
	        throw new YiException("接口ID不能为空");
        }
        Parameter updateParam = model.getUpdateParam();
	    if (updateParam == null || StrUtil.isBlank(updateParam.getParameterIdentify()) || StrUtil.isBlank(updateParam.getType())) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }
	    interfaceInfoService.batchInsertParam(updateParam, ids, batchUpdateMessage);

	    return SUCCESS;
    }

	/**
	 * 获取参数jsonTree数据
	 * @return
	 */
	public String getParametersJsonTree () {
		Object[] os = PracticalUtils.getParameterZtreeMap(interfaceInfoService.get(model.getInterfaceId()).getParameters());
		
		if (os == null) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "没有可用的参数,请检查!");
		}

		setData(new ParseMessageToNodesOutDTO(os[0], Integer.parseInt(os[1].toString()), os[2].toString()));
		return SUCCESS;
	}
	
	/**
	 * 批量导出接口文档
	 * @return
	 */
	public String exportInterfaceDocument () {
		if (ids == null) {
			ids = "";
		}
		
		String[] ids_s = ids.split(",");
		
		List<InterfaceInfo> infos = new ArrayList<InterfaceInfo>();
		for (String s:ids_s) {
			infos.add(interfaceInfoService.get(Integer.valueOf(s)));
		}
		
		if (infos.size() < 1) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "没有足够的数据可供导出,请刷新表格并重试!");
		}
		
		String path = null;
		
		try {
			path = ExportInterfaceInfo.exportDocuments(infos, PoiExcelUtil.XLSX);
		} catch (Exception e) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), "后台写文件出错:<br>" + e.getMessage() + "<br>请联系管理员!");
		}

		setData(path);
		return SUCCESS;
	}
	
	/**
	 * 从指定excel中导入数据
	 * @return
	 */
	public String importFromExcel () {
		Map<String, Object> result = null;
		try {
			result = ImportInterfaceInfo.importToDB(path, projectId);
		} catch (Exception e) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), "读取文件内容失败!");
		}

		setData(result);
		return SUCCESS;
	}

	/**
	 * 更新接口
	 * 根据传入的interfaceId判断修改还是新增
	 */
	@Override
	public String edit() {
		User user = FrameworkUtil.getLoginUser();
		//判断接口名是否重复
		checkObjectName();
		if (!checkNameFlag.equals(SystemConsts.DefaultBooleanIdentify.TRUE.getString())) {
			throw new YiException(AppErrorCode.NAME_EXIST);
		}
		if (model.getInterfaceId() == null) {
			//新增									
			model.setCreateTime(new Timestamp(System.currentTimeMillis()));
			model.setUser(user);				
		}
		
		model.setLastModifyUser(user.getRealName());	
		
		//设置测试环境
		Set<BusinessSystem> systems = new HashSet<BusinessSystem>();
		for (String id:ids.split(",")) {
			BusinessSystem system = businessSystemService.get(Integer.valueOf(id));
			if (system != null) {
				systems.add(system);
			}
		}
		model.setSystems(systems);
		
		interfaceInfoService.edit(model);
		/***********************************/	
		if (model.getInterfaceId() != null) {
			Map<String, List<Integer>> updateSystems = checkBusinessSystemUpdate();
			if (updateSystems != null) {
				jsonObject.setMsg("你刚刚更新了接口的测试环境关联:<br><strong>新增关联" + updateSystems.get("addSystems").size()
						+ "个,解除关联" + updateSystems.get("removeSystems").size() + "个。</strong><br>"
						+ "<span class=\"c-red\">是否需要同步更新该接口下属对象的测试环境信息？</span>");
				setData(updateSystems);
			}
		}		
		/*****************************************/
		return SUCCESS;
	}
	
		
	
	/**
	 * 判断接口名重复性
	 * 新增或者修改状态下均可用
	 */
	@Override
	public void checkObjectName() {
		InterfaceInfo info = interfaceInfoService.findInterfaceByName(model.getInterfaceName());
		checkNameFlag = (info != null && !info.getInterfaceId().equals(model.getInterfaceId())) ? "重复的接口名" : "true";
		
		if (model.getInterfaceId() == null) {
			checkNameFlag = (info == null) ? "true" : "重复的接口名";
		}
	}
	
	/**
	 * 根据接口所属的测试环境更新下属报文、场景、数据的相关信息<br>
	 * 更新规则：下属所有报文场景数据没有的就增加，多余的就删除
	 * @return
	 */
	public String updateChildrenBusinessSystems () {
		model = interfaceInfoService.get(model.getInterfaceId());
		
		if (model != null) {
			Collection<String> removeSystems = new HashSet<String>();
			Collection<String> addSystems = new HashSet<String>();
			if (StringUtils.isNotBlank(updateSystems)) {
				JSONObject updateSystemsObj = JSONObject.fromObject(updateSystems);
				removeSystems = JSONArray.toCollection(updateSystemsObj.getJSONArray("removeSystems"), String.class);
				addSystems = JSONArray.toCollection(updateSystemsObj.getJSONArray("addSystems"), String.class);
			} else {
				for (BusinessSystem sys:model.getSystems()) {
					addSystems.add(String.valueOf(sys.getSystemId()));					
				}
			}
			for (Message msg:model.getMessages()) {
				msg.setSystems(removeBusinessSystems(addBusinessSystems(msg.getSystems(), addSystems), removeSystems));
				messageService.edit(msg);
				
				for (MessageScene scene:msg.getScenes()) {
					scene.setSystems(removeBusinessSystems(addBusinessSystems(scene.getSystems(), addSystems), removeSystems));
					messageSceneService.edit(scene);
					
					for (TestData data:scene.getTestDatas()) {
						data.setSystems(removeBusinessSystems(addBusinessSystems(data.getSystems(), addSystems), removeSystems));;
						testDataService.edit(data);
					}
				}
				
			}
		}
		return SUCCESS;
	}
	
	@Override
	public String get() {
		if (id == null) {
			setData(messageService.get(messageId).getInterfaceInfo());
		} 
		
		if (id != null) {
			setData(interfaceInfoService.get(id));
		}
		return SUCCESS;
	}


    public void setBatchUpdateMessage(Boolean batchUpdateMessage) {
        this.batchUpdateMessage = batchUpdateMessage;
    }

    public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	
	public void setPath(String path) {
		this.path = path;
	}	
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public void setOldIds(String oldIds) {
		this.oldIds = oldIds;
	}
	
	public void setUpdateSystems(String updateSystems) {
		this.updateSystems = updateSystems;
	}
	
	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	
	public Map<String, List<Integer>> checkBusinessSystemUpdate () {
		
		Map<String, List<Integer>> returnObj = null;
		String[] idsNew = ids.split(",");
		String[] idsOld = oldIds.split(",");
		
		Arrays.sort(idsNew);
		Arrays.sort(idsOld);
		
		if (Arrays.equals(idsNew, idsOld)) {
			return returnObj;
		}
		
		returnObj = new HashMap<String, List<Integer>>();
		List<Integer> addIds = new ArrayList<Integer>();
		List<Integer> delIds = new ArrayList<Integer>();
		
		//判断有没有新增的
		label1:
		for (String idNew:idsNew) {
			for (String idOld:idsOld) {
				if (idNew.equals(idOld)) {
					continue label1;
				}				
			}
			if (PracticalUtils.isNumeric(idNew)) {
				addIds.add(Integer.valueOf(idNew));
			}			
		}
		
		//判断有没有删除的
		label1:
		for (String idOld:idsOld) {
			for (String idNew:idsNew) {
				if (idOld.equals(idNew)) {
					continue label1;
				}				
			}
			if (PracticalUtils.isNumeric(idOld)) {
				delIds.add(Integer.valueOf(idOld));
			}			
		}

		returnObj.put("addSystems", addIds);
		returnObj.put("removeSystems", delIds);
		return returnObj;
	
	}
	
	/**
     * 添加测试环境
     * @param addSystems
     */
    public String addBusinessSystems (String systems, Collection<String> addSystems) {
    	Set<String> systemsSet = new HashSet<String>();
    	if (StringUtils.isNotBlank(systems)) {
    		systemsSet = new HashSet<String>(Arrays.asList(systems.split(",")));
    	} 
    	  
    	label:
    	for (String s:addSystems) {
    		for (Iterator iter = systemsSet.iterator(); iter.hasNext();) {
    			if (s.equals((String)iter.next())) {
    				continue label;
    			}
        	}
    		systemsSet.add(s);
	     }
	
    	return StringUtils.join(systemsSet, ",");
    }
    
    /**
     * 删除测试环境
     * @param removeSystems
     */
    public String removeBusinessSystems (String systems, Collection<String> removeSystems) {
    	Set<String> systemsSet = new HashSet<String>();
    	if (StringUtils.isNotBlank(systems)) {
    		systemsSet = new HashSet<String>(Arrays.asList(systems.split(",")));
    	}   	
    	
    	label:
    	for (String s:removeSystems) {
    		for (Iterator iter = systemsSet.iterator(); iter.hasNext();) {
    			if (s.equals((String)iter.next())) {
    				iter.remove();
    				continue label;
    			}
        	}
	     }
	
    	return StringUtils.join(systemsSet, ",");
    }
}
