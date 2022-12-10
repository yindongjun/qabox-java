package yi.master.business.reportform.service;

import yi.master.business.message.bean.TestReport;

import java.util.List;
import java.util.Map;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/12 17:40
 */
public interface ReportFormService {
    /**
     * 查询指定时间内，每天新增的数量
     * @author xuwangcheng
     * @date 2019/12/12 17:54
     * @param tableName tableName 表名
     * @param beginTime beginTime
     * @param endTime endTime
     * @return {@link Map}
     */
    Map<String, Integer> queryCountByTime(String tableName, String beginTime, String endTime);

    /**
     * 查询指定时间内测试报告
     * @author xuwangcheng
     * @date 2019/12/16 9:02
     * @param beginTime beginTime
     * @param endTime endTime
     * @return {@link List}
     */
    List<TestReport> queryReportByTime(String beginTime, String endTime, List<String> includeScope);

    /**
     * 统计场景测试结果响应时间列表
     * @author xuwangcheng
     * @date 2019/12/17 16:47
     * @param messageSceneId messageSceneId
     * @param beginTime beginTime
     * @param endTime endTime
     * @param includeScope includeScope 包含范围
     * @return {@link List} List数组，list[0]为探测时间列表  list[1]为相对应的响应时间ms列表
     */
    List[] querySceneResultResponseTime (Integer messageSceneId, String beginTime, String endTime, List<String> includeScope);
}
