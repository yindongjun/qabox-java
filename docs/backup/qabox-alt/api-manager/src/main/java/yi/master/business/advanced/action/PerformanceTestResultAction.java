package yi.master.business.advanced.action;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.advanced.bean.PerformanceTestResult;
import yi.master.business.advanced.service.PerformanceTestResultService;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.TestResult;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.excel.ExportPerformanceTestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 性能测试结果查询相关接口
 * @author xuwangcheng
 * @date 2019/8/30 16:01
 */
@Controller
@Scope("prototype")
public class PerformanceTestResultAction extends BaseAction<PerformanceTestResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(PerformanceTestResultAction.class);
	
	private PerformanceTestResultService performanceTestResultService;
	
	private Integer ptId;
	
	private String ids;
	
	private String rangeTime;
	
	@Autowired
	public void setPerformanceTestResultService(
			PerformanceTestResultService performanceTestResultService) {
		super.setBaseService(performanceTestResultService);
		this.performanceTestResultService = performanceTestResultService;
	}
	
	@Override
	public String[] prepareList() {
		
		List<String> conditions = new ArrayList<String>();
		if (ptId != null) {
			conditions.add("performanceTestConfig.ptId=" + ptId);
		}		
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}
	
	/**
	 * 视图数据
	 * @return
	 */
	public String analyseView() {
		model = performanceTestResultService.get(model.getPtResultId());
		if (model != null && SystemConsts.FinishedFlag.Y.name().equals(model.getFinishFlag())) {
			Date maxTime = null;
			Date minTime = null;
			if (StringUtils.isNotBlank(rangeTime)) {
				String[] times = rangeTime.split("~");
				minTime = PracticalUtils.convertDate("yyyy-MM-dd HH:mm:ss", times[0].trim());
				maxTime = PracticalUtils.convertDate("yyyy-MM-dd HH:mm:ss", times[1].trim());
			}
			
			JSONObject obj = new JSONObject();
			
			obj = JSONObject.fromObject(model.getAnalyzeResult().reAnalyseByTime(maxTime, minTime), PracticalUtils.jsonConfig);
			obj.put("ptName", model.getPerformanceTestConfig().getPtName());
			obj.put("finished", true);
			obj.put("interfaceName", model.getInterfaceName());
			obj.put("systemName", model.getSystemName());
			obj.put("startTime", PracticalUtils.formatDate("yyyy-MM-dd HH:mm:ss", model.getStartTime()));
			obj.put("currentStatus", "测试已完成");
			setData(obj);
		} else {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "无此结果或者测试未完成!");
		}					
		return SUCCESS;
	}
	
	/**
	 * 汇总多条结果到excel供下载
	 * @return
	 */
	public String summarizedView() {
		if (StringUtils.isBlank(ids)) {
			throw new YiException(AppErrorCode.MISS_PARAM);
		}
		
		List<PerformanceTestResult> results = new ArrayList<PerformanceTestResult>();
		String[] resultIds = ids.split(",");
		for (String id:resultIds) {
			PerformanceTestResult result = performanceTestResultService.get(Integer.valueOf(id));
			if (result != null) {
				results.add(result);
			}
		}
		
		try {
			setData(ExportPerformanceTestResult.exportDocuments(results));
		} catch (Exception e) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), "创建excel文件出错!");
		}
		return SUCCESS;
	}
	
	/**
	 * 删除的同时删除文件
	 */
	@Override
	public String del() {
		model = performanceTestResultService.get(id);
		if (model != null) {
			FileUtils.deleteQuietly(new File(model.getDetailsResultFilePath()));
			if (StringUtils.isNotBlank(model.getParameterizedFilePath())) {
				FileUtils.deleteQuietly(new File(FrameworkUtil.getProjectPath() + File.separator + model.getParameterizedFilePath()));
			}			
		}				
		return super.del();
	}

	/**
	 * 详细结果查看-从序列化文件中反序列化
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String detailsList() {
		model = performanceTestResultService.get(model.getPtResultId());
		List<TestResult> results = new ArrayList<TestResult>();

		if (model != null && (new File(model.getDetailsResultFilePath())).exists()) {
			FileInputStream fn = null;
			ObjectInputStream ois = null;
			try {
				 fn = new FileInputStream(model.getDetailsResultFilePath());
				 ois = new ObjectInputStream(fn);	
			     results = (List<TestResult>) ois.readObject();
			} catch (Exception e) {
				LOGGER.error("反序列化失败：" + model.getDetailsResultFilePath(), e);
				throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR, "测试结果文件反序列化失败！");
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
						LOGGER.warn("IOException", e);
					}
				}
				if (fn != null) {
					try {
						fn.close();
					} catch (IOException e) {
						LOGGER.warn("IOException", e);
					}
				}
			}			
		} else {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "测试结果文件已被删除,请确认!");
		}
		
		setData(results);
		return SUCCESS;
	}
	
	public void setPtId(Integer ptId) {
		this.ptId = ptId;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setRangeTime(String rangeTime) {
		this.rangeTime = rangeTime;
	}
}
