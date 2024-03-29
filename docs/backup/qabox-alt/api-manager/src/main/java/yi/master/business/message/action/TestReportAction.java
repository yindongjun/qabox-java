package yi.master.business.message.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.message.bean.TestReport;
import yi.master.business.message.bean.TestSet;
import yi.master.business.message.service.TestReportService;
import yi.master.business.message.service.TestSetService;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.notify.mail.NotifyMail;
import yi.master.util.notify.mail.ReportEmailCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口自动化<br>
 * 测试报告和测试结果Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.07.10
 *
 */


@Controller
@Scope("prototype")
public class TestReportAction extends BaseAction<TestReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TestReportService testReportService;
	@Autowired
	private TestSetService testSetService;
	
	@Autowired
	public void setTestReportService(TestReportService testReportService) {
		super.setBaseService(testReportService);
		this.testReportService = testReportService;
	}

	@Override
	public String[] prepareList() {
		List<String> conditions = new ArrayList<String>();
		if (projectId != null) {
			conditions.add("t.projectInfo.projectId=" + projectId);
		}
		this.filterCondition = conditions.toArray(new String[0]);
		return this.filterCondition;
	}

	@Override
	public String get() {
		model = testReportService.get(model.getReportId());
		model.countSceneNum();
		setData(model);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object processListData(Object o) {
		
		List<TestReport> reports = (List<TestReport>) o;
		
		for (TestReport r:reports) {
			r.countSceneNum();
			r.setSetName("全量测试");
			
			if (!"0".equals(r.getTestMode())) {
				TestSet set = testSetService.get(Integer.valueOf(r.getTestMode()));
				
				if (set != null) {
					r.setSetName(set.getSetName());
				} else {
					r.setSetName("测试集已删除");
				}
			}
		}		
		return reports;
	}
	
	/**
	 * 获取生成完整测试报告所需数据
	 * @return
	 */
	public String getReportDetail() {		
		TestReport report = testReportService.get(model.getReportId());
		if (StringUtils.isEmpty(report.getDetailsJson())) {			
			report.setDetailsJson(PracticalUtils.setReportDetails(report));
			testReportService.edit(report);						
		}
		setData(report.getDetailsJson());
		return SUCCESS;
	}
	
	/**
	 * 邮件推送测试报告
	 * @return
	 */
	public String sendMail(){
		TestReport report = testReportService.get(model.getReportId());
		if (report == null) {
			throw new YiException(AppErrorCode.REPORT_INFO_NOT_EXIST);
		}
		if (SystemConsts.FinishedFlag.N.name().equals(report.getFinishFlag()) || report.getTrs().size() < 1) {
			throw new YiException(AppErrorCode.REPORT_TEST_NO_DATA);
		}
		//发送测试报告
		TestSet set = testSetService.get(Integer.valueOf(report.getTestMode()));
		//如果对应的测试集存在则优先使用该测试集配置的收信人，如果对应的测试集已被删除则使用全局默认的收信人
		String receiveAddress_s = null, copyAddress_s = null;
		if (set != null && set.getConfig() != null) {
			receiveAddress_s = set.getConfig().getMailReceiveAddress();
			copyAddress_s = set.getConfig().getMailCopyAddress();
		}
		
		String result = NotifyMail.sendEmail(new ReportEmailCreator(report), receiveAddress_s, copyAddress_s);
		if (!SystemConsts.DefaultBooleanIdentify.TRUE.getString().equalsIgnoreCase(result)) {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), result);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 生成静态报告
	 * @return
	 */
	public String generateStaticReportHtml() {
		TestReport report = testReportService.get(model.getReportId());
		
		//判断测试是否已经完成 /判断是否有测试结果
		if ("N".equals(report.getFinishFlag()) || report.getTrs().size() < 1) {
			throw new YiException(AppErrorCode.REPORT_TEST_NO_DATA);
		}

		//最终生成文件： reportId_startTime.html
		File htmlFile = new File(FrameworkUtil.getProjectPath() + "/" + report.getReportHtmlPath());
		boolean successFlag = true;
		String msg = "";
		//判断是否已经生成
		if (!htmlFile.exists()) {
			if (StringUtils.isEmpty(report.getDetailsJson())) {			
				report.setDetailsJson(PracticalUtils.setReportDetails(report));
				testReportService.edit(report);						
			}			
			
			try {
				PracticalUtils.createReportNew(report.getDetailsJson(), new File( FrameworkUtil.getProjectPath() + "/" + report.getReportHtmlPath()));
			} catch (Exception e) {
				
				LOGGER.error("写静态报告文件出错!reportId=" + report.getReportId() + ",输出文件路径=" + FrameworkUtil.getProjectPath() 
						+ "/" + report.getReportHtmlPath(), e);
				successFlag = false;
				msg = "写静态报告文件出错!reportId=" + report.getReportId() + ",输出文件路径=" + FrameworkUtil.getProjectPath() 
						+ "/" + report.getReportHtmlPath() + "错误原因：<br>" + e.getMessage();
			}
		}
		
		if (successFlag) {
			setData(report.getReportHtmlPath());
		} else {
			throw new YiException(AppErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
		}
		
		return SUCCESS;
	}
}
