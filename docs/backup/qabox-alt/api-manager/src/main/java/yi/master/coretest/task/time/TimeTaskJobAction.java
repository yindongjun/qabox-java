package yi.master.coretest.task.time;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import yi.master.business.message.bean.AutoTask;
import yi.master.constant.ReturnCodeConsts;
import yi.master.constant.SystemConsts;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.util.List;
import java.util.Map;

/**
 * 定时任务工作执行类
 * @author xuwangcheng
 * @version 1.0.0.0,2018.1.26
 *
 */
public class TimeTaskJobAction implements Job {

	private static final Logger LOGGER = Logger.getLogger(TimeTaskJobAction.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(JobExecutionContext context) {
        String[] result = new String[2];
        String returnJson = "";
		try {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            AutoTask task = (AutoTask)dataMap.get(context.getJobDetail().getKey().getName());
            //获取请求地址
            String testUrl = CacheUtil.getHomeUrl() + "/" + SystemConsts.AUTO_TASK_TEST_RMI_URL
                    + "?setId=" + task.getRelatedId() + "&autoTestFlag=true" + "&token=" + SystemConsts.REQUEST_ALLOW_TOKEN + "&userId=" + task.getUser().getUserId();
            LOGGER.info("[自动化定时任务]执行自动化测试任务:url=" + testUrl);

            returnJson = PracticalUtils.doGetHttpRequest(testUrl);

            LOGGER.info("[自动化定时任务]请求返回内容：" + returnJson);
			Map maps = new ObjectMapper().readValue(returnJson, Map.class);
			if (String.valueOf(ReturnCodeConsts.SUCCESS_CODE).equals(maps.get("returnCode").toString())) {
				result[0] = String.valueOf(((List<Integer>)maps.get("data")).get(0));
			} else {
				result[1] = returnJson;
			}
		} catch (Exception e) {
			LOGGER.error("[自动化定时任务]自动化测试出错:" + returnJson, e);
			result[1] = returnJson;
		}
				
		context.setResult(result);				
	}

}
