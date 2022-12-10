package yi.master.business.message.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.ComplexScene;
import yi.master.business.message.bean.ComplexSceneConfig;
import yi.master.business.message.service.ComplexSceneService;
import yi.master.business.message.service.MessageSceneService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class ComplexSceneAction extends BaseAction<ComplexScene> {
	private static final long serialVersionUID = 1L;
		
	private ComplexSceneService complexSceneService;
	
	@Autowired
	private MessageSceneService messageSceneService;
	
	@Autowired
	public void setComplexSceneService(ComplexSceneService complexSceneService) {
		super.setBaseService(complexSceneService);
		this.complexSceneService = complexSceneService;
	}
	
	public Integer setId;
	
	public Integer messageSceneId;
	
	public Integer sequenceNum;
	
	public String sequenceNums;
	
	public String config;

	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}


	/**
	 * 获取保存变量名列表,传入sequenceNum代表获取的为在此执行顺序之前的保存变量
	 * @return
	 */
	public String getSaveVariables () {
		model= complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();
		JSONArray variables = new JSONArray();
		
		for (Map.Entry<String, ComplexSceneConfig> entry:model.getComplexSceneConfigs().entrySet()) {
			if (Integer.parseInt(entry.getKey()) < sequenceNum) {				
				if (entry.getValue().getSaveVariables().size() != 0) {
					for (String name:entry.getValue().getSaveVariables().values()) {
						JSONObject variable = new JSONObject();
						variable.put("id", name);
						variables.add(variable);
					}
				}
			}
		}

		setData(variables);
		return SUCCESS;
	}
	
	/**
	 * 更新指定的测试场景配置信息
	 * @return
	 */
	public String updateSceneConfig () {
		model = complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();		
		
		if (model.getComplexSceneConfigs().get(String.valueOf(sequenceNum)) != null) {
			JSONObject configJson = JSONObject.fromObject(config);
			
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("useVariables", Map.class);
			classMap.put("saveVariables", Map.class);
			
			model.getComplexSceneConfigs().put(String.valueOf(sequenceNum),
					(ComplexSceneConfig) JSONObject.toBean(configJson, ComplexSceneConfig.class, classMap));
			
			complexSceneService.updateConfigInfo(model.getId(), JSONObject.fromObject(model.getComplexSceneConfigs()).toString());
		}
		return SUCCESS;
	}
	
	/**
	 * 执行顺序排序
	 * @return
	 */
	public String sortScenes () {
		String[] seqs = sequenceNums.split(",");
		model = complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();
		
		Map<String, ComplexSceneConfig> configs = new HashMap<String, ComplexSceneConfig>();
		Map<String, ComplexSceneConfig> oldConfigs = model.getComplexSceneConfigs();
		int count = 1;
		for (String seq:seqs) {
			if (oldConfigs.get(seq) != null) {
				configs.put(String.valueOf(count), oldConfigs.get(seq));
				count ++;
			}
		}
		complexSceneService.updateConfigInfo(model.getId(), JSONObject.fromObject(configs).toString());
		return SUCCESS;
	}
	
	/**
	 * 添加测试场景
	 * @return
	 */
	public String addScene () {
		model = complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();
		
		complexSceneService.updateConfigInfo(model.getId(), model.addScene(messageSceneId));
		return SUCCESS;
	}
	
	/**
	 * 删除测试场景
	 * @return
	 */
	public String delScene () {
		model = complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();
		
		complexSceneService.updateConfigInfo(model.getId(), model.delScene(sequenceNum));
		return SUCCESS;
	}
	
	/**
	 * 更新configInfo配置JSON串
	 * @return
	 */
	public String updateConfigInfo () {
		complexSceneService.updateConfigInfo(model.getId(), model.getConfigInfo());
		return SUCCESS;
	}
	
	/**
	 * 从指定组合场景中获取所有的测试场景
	 * @return
	 */
	public String listScenes () {
		model = complexSceneService.get(model.getId());
		model.setComplexSceneConfigs();				

		setData(model.setScenes(messageSceneService));
		return SUCCESS;
	}
	
	/**
	 * 获取指定测试拥有的组合场景列表
	 * @return
	 */
	public String listSetScenes () {
		setData(complexSceneService.listComplexScenesBySetId(setId));
		return SUCCESS;
	}
	
	/**
	 * 添加组合场景到测试集
	 * @return
	 */
	public String addToSet () {
		ComplexScene scene = complexSceneService.findAssignScene(model.getId(), setId);
		
		if (scene == null) {
			complexSceneService.addToSet(model.getId(), setId);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * 从测试集中删除组合场景
	 * @return
	 */
	public String delFromSet () {
		complexSceneService.delFromSet(model.getId(), setId);
		return SUCCESS;
	}
	
	/*******************************************************/
	public void setSetId(Integer setId) {
		this.setId = setId;
	}
	
	public void setMessageSceneId(Integer messageSceneId) {
		this.messageSceneId = messageSceneId;
	}
	
	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
	
	public void setSequenceNums(String sequenceNums) {
		this.sequenceNums = sequenceNums;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
}
