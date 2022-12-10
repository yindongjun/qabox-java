package yi.master.business.reportform.dao.impl;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import yi.master.business.message.bean.TestReport;
import yi.master.business.reportform.dao.ReportFormDao;
import yi.master.business.reportform.enums.ReportIncludeScope;
import yi.master.constant.MessageKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/12 17:39
 */
@Repository("reportFormDao")
public class ReportFormDaoImpl implements ReportFormDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public List<Object[]> queryCountByTime(String tableName, String beginTime, String endTime) {
        StringBuilder hql = new StringBuilder();
        hql.append("select m.ct,count(1) from (");
        hql.append("select date_format(t.create_time, '%Y-%m-%d') as ct,t.* from ");
        hql.append(tableName);
        hql.append(" t where t.create_time >= :beginTime and t.create_time <= :endTime");
        hql.append(") m group by m.ct order by m.ct");

        return getSession().createSQLQuery(hql.toString()).setString("beginTime", beginTime)
                .setString("endTime", endTime).list();
    }

    @Override
    public List<TestReport> queryReportByTime(String beginTime, String endTime, List<String> includeScope) {
        if (CollUtil.isEmpty(includeScope)) {
            return CollUtil.newArrayList();
        }

        StringBuilder hql = new StringBuilder();
        hql.append("from TestReport t where t.finishTime >= :beginTime and t.finishTime <= :endTime and t.finishFlag = 'Y' ");
        if (CollUtil.isNotEmpty(includeScope)) {
            hql.append("and (");
            for (int i = 0;i < includeScope.size();i++) {
                if (i > 0) {
                    hql.append(" or ");
                }
                if (ReportIncludeScope.HAND_WORK_SET_TEST.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                    hql.append("t.mark is null or t.mark = ''");
                }
                if (ReportIncludeScope.TIMED_TASK_SET.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                    hql.append("t.mark = '" + MessageKeys.QUARTZ_AUTO_TEST_REPORT_MARK + "'");
                }
                if (ReportIncludeScope.THIRD_PARTY_API_CALL_SET.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                    hql.append("t.mark = '" + MessageKeys.API_CALL_TEST_REPORT_MARK + "'");
                }
            }
            hql.append(")");
        }

        hql.append(" order by t.finishTime");

        return getSession().createQuery(hql.toString()).setString("beginTime", beginTime)
                .setString("endTime", endTime).setCacheable(true).list();
    }

    @Override
    public List<List> querySceneResultResponseTime(Integer messageSceneId, String beginTime, String endTime, List<String> includeScope) {
        if (messageSceneId == null || CollUtil.isEmpty(includeScope)) {
            return new ArrayList<List>();
        }

        StringBuilder hql = new StringBuilder();
        hql.append("select new list(t.opTime,t.useTime) from TestResult t where t.messageScene.messageSceneId=:messageSceneId and ");
        hql.append("t.opTime >= :beginTime and t.opTime <= :endTime and t.runStatus!='2'");
        hql.append(" and (");
        for (int i = 0;i < includeScope.size();i++) {
            if (i > 0) {
                hql.append(" or ");
            }
            if (ReportIncludeScope.HAND_WORK_SET_TEST.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                hql.append("t.testReport.mark is null or t.testReport.mark = ''");
            }
            if (ReportIncludeScope.TIMED_TASK_SET.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                hql.append("t.testReport.mark = '" + MessageKeys.QUARTZ_AUTO_TEST_REPORT_MARK + "'");
            }
            if (ReportIncludeScope.THIRD_PARTY_API_CALL_SET.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                hql.append("t.testReport.mark = '" + MessageKeys.API_CALL_TEST_REPORT_MARK + "'");
            }
            if (ReportIncludeScope.INTERFACE_PROBE_TEST.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                hql.append("t.interfaceProbe is not null");
            }
            if (ReportIncludeScope.COMPLEX_INTERFACE_TEST.equals(ReportIncludeScope.getByScope(includeScope.get(i)))) {
                hql.append("t.complexResult is not null");
            }
        }
        hql.append(") order by t.opTime");

        return getSession().createQuery(hql.toString()).setInteger("messageSceneId", messageSceneId)
                .setString("beginTime", beginTime).setString("endTime", endTime).setCacheable(true).list();
    }
}
