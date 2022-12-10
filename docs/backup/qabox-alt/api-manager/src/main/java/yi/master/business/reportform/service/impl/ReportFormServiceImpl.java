package yi.master.business.reportform.service.impl;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.message.bean.TestReport;
import yi.master.business.reportform.dao.ReportFormDao;
import yi.master.business.reportform.service.ReportFormService;
import yi.master.util.PracticalUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/12 17:41
 */
@Service("reportFormService")
public class ReportFormServiceImpl implements ReportFormService {

    @Autowired
    private ReportFormDao reportFormDao;

    @Override
    public Map<String, Integer> queryCountByTime(String tableName, String beginTime, String endTime) {
        List<Object[]> os = reportFormDao.queryCountByTime(tableName, beginTime, endTime);
        Map<String, Integer> map = new HashMap<>(20);
        for (Object[] o:os) {
            map.put(o[0].toString(), Integer.valueOf(o[1].toString()));
        }

        return map;
    }

    @Override
    public List<TestReport> queryReportByTime(String beginTime, String endTime, List<String> includeScope) {
        return reportFormDao.queryReportByTime(beginTime, endTime, includeScope);
    }

    @Override
    public List[] querySceneResultResponseTime(Integer messageSceneId, String beginTime, String endTime, List<String> includeScope) {
        List<List> lists = reportFormDao.querySceneResultResponseTime(messageSceneId, beginTime, endTime, includeScope);
        if (CollUtil.isEmpty(lists)) {
            return new ArrayList[2];
        }

        ArrayList<String> datetime = new ArrayList<String>();
        ArrayList<String> responseTime = new ArrayList<String>();

        for (List list:lists) {
            datetime.add(PracticalUtils.formatDate(PracticalUtils.FULL_DATE_PATTERN, (Timestamp)list.get(0)));
            responseTime.add(list.get(1).toString());
        }

        return new ArrayList[]{datetime, responseTime};
    }
}
