package yi.master.coretest.task.probe;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import yi.master.business.advanced.bean.InterfaceProbe;
import yi.master.business.advanced.enums.InterfaceProbeNotifyType;
import yi.master.business.advanced.enums.InterfaceProbeStatus;
import yi.master.business.advanced.service.InterfaceProbeService;
import yi.master.business.message.bean.TestResult;
import yi.master.business.message.enums.CommonStatus;
import yi.master.business.message.service.TestResultService;
import yi.master.business.user.service.MailService;
import yi.master.constant.ReturnCodeConsts;
import yi.master.constant.SystemConsts;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;
import yi.master.util.notify.mail.NotifyMail;
import yi.master.util.notify.mail.ProbeEmailCreator;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 探测任务执行工作类
 * @author xuwangcheng
 * @version 1.0.0.0,2018.1.26
 *
 */
public class ProbeTaskJobAction implements Job {

	@Autowired
	private TestResultService testResultService;
	@Autowired
	private InterfaceProbeService interfaceProbeService;
	@Autowired
	private MailService mailService;
	
	private static final Logger LOGGER = Logger.getLogger(ProbeTaskJobAction.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		InterfaceProbe task = (InterfaceProbe) dataMap.get(context.getJobDetail().getKey().getName());
		
		//获取请求地址
		String testUrl = CacheUtil.getHomeUrl() + "/" + SystemConsts.PROBE_TASK_TEST_RMI_URL
				+ "?probeId=" + task.getProbeId() + "&token=" + SystemConsts.REQUEST_ALLOW_TOKEN;
		
		LOGGER.info("[接口探测任务]执行探测任务:url=" + testUrl);
		
		String returnJson = PracticalUtils.doGetHttpRequest(testUrl);
		
		LOGGER.info("[接口探测任务]请求返回内容：" + returnJson);
		Integer resultId = null;
		try {
			Map maps = new ObjectMapper().readValue(returnJson, Map.class);
			if (String.valueOf(ReturnCodeConsts.SUCCESS_CODE).equals(maps.get("returnCode").toString())) {
				resultId = Integer.valueOf(maps.get("data").toString());
			} else {
				task.setMark(maps.get("msg").toString());
			}
		} catch (Exception e) {
			LOGGER.error("[接口探测任务]探测任务执行出错:" + returnJson, e);
		}
		Timestamp lastCallTime = null;
		TestResult result = null;
		if (resultId == null) {
			//变更状态为"执行出错"
			task.setStatus(InterfaceProbeStatus.EXECUTE_ERROR.getStatus());
		} else {
			result = testResultService.get(resultId);
			task.setMark("");
            lastCallTime = result.getOpTime();
            task.setStatus(InterfaceProbeStatus.RUNNING.getStatus());
			//变更状态为“缺少数据”
			if (result.getQualityLevel() == 0) {
				task.setStatus(InterfaceProbeStatus.NO_DATA.getStatus());
			}
		}
		
		task.setLastCallTime(lastCallTime == null ? new Timestamp(System.currentTimeMillis()) : lastCallTime);
		//更新探测详情
		if (task.getFirstCallTime() == null) {
			task.setFirstCallTime(task.getLastCallTime());
		}
										
		task.setCallCount(task.getCallCount() + 1);
		interfaceProbeService.edit(task);
		
		//结果通知
		if (CommonStatus.ENABLED.getStatus().equals(CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_IF_SEND_REPORT_MAIL))
				&& !InterfaceProbeNotifyType.DISABLED_NOTIFY.getType().equals(task.getConfig().getNotifyType())
				&& result != null
				&& result.getQualityLevel() >= Integer.valueOf(task.getConfig().getNotifyLevel())) {
			String sendEmailFlag = SystemConsts.DefaultBooleanIdentify.TRUE.getString();
			//邮件通知
			if (InterfaceProbeNotifyType.MAIL_AND_MESSAGE.getType().equals(task.getConfig().getNotifyType())) {
				sendEmailFlag = NotifyMail.sendEmail(new ProbeEmailCreator(task, result), task.getConfig().getReceiveAddress()
						, task.getConfig().getCopyAddress());
			}

			//站内信通知
			StringBuilder mailInfo = new StringBuilder();
			mailInfo.append("接口探测任务<br><span class=\"label label-primary radius\">[任务Id]</span> = ")
					.append(task.getProbeId()).append("<br><span class=\"label label-primary radius\">[接口信息]</span> = ")
					.append(result.getMessageInfo()).append("<br><span class=\"label label-primary radius\">[探测时间]</span> = ")
					.append(result.getOpTime()).append("<br><span class=\"label label-primary radius\">[探测结果]</span> = ")
					.append(PracticalUtils.getProbeResultQualityLevelHtml(result.getQualityLevel())).append("<br>请在接口探测模块关注详情!");
			
			if (!SystemConsts.DefaultBooleanIdentify.TRUE.getString().equalsIgnoreCase(sendEmailFlag)) {
				mailInfo.append("<br><br><span class=\"label label-danger radius\">!! 由于以下原因,本次邮件通知失败,请检查!</span><br><br><code>")
						 .append(sendEmailFlag).append("</code>");
			}
			
			mailService.sendSystemMail("接口探测任务警告", mailInfo.toString(), task.getUser().getUserId());
		}
	}

}
