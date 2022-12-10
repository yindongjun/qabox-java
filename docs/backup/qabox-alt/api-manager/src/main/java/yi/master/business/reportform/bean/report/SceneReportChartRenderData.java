package yi.master.business.reportform.bean.report;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import yi.master.business.reportform.service.ReportFormService;
import yi.master.util.FrameworkUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 场景报告图表渲染数据
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/17 16:31
 */
public class SceneReportChartRenderData {
    /**
     * 统计时间起始
     */
    private String beginDate = DateUtil.formatDate(DateUtil.offsetDay(new Date(), -30)) + " 00:00:00";;

    /**
     * 统计时间结束
     */
    private String endDate = DateUtil.formatDate(new Date()) + " 23:59:59";
    /**
     * 响应时间统计
     */
    private ResponseTimeTrendData trendData = new ResponseTimeTrendData();


    public SceneReportChartRenderData (String beginDate, String endDate) {
        if (StringUtils.isNotBlank(beginDate)) {
            this.beginDate = beginDate;
        }
        if (StringUtils.isNotBlank(endDate)) {
            this.endDate = endDate;
        }
    }

    public SceneReportChartRenderData () {
    }

    public SceneReportChartRenderData (String rangeDate) {
        if (StringUtils.isNotBlank(rangeDate)) {
            String[] ss = rangeDate.split("~");
            this.beginDate = ss[0].trim();
            if (ss.length == 2) {
                this.endDate = ss[1].trim();
            }
        }
    }

    /**
     * 设置场景响应数据的趋势数据
     * @author xuwangcheng
     * @date 2019/12/18 9:07
     * @param messageSceneId messageSceneId 指定的场景ID
     * @param includeScope includeScope 统计范围
     * @return
     */
    public void setTrendData (Integer messageSceneId, List<String> includeScope) {
        ReportFormService reportFormService = (ReportFormService) FrameworkUtil.getSpringBean(ReportFormService.class);
        List[] list = reportFormService.querySceneResultResponseTime(messageSceneId, beginDate, endDate, includeScope);

        if (CollUtil.isNotEmpty(list[0])) {
            this.trendData.opTime = list[0];
        }
        if (CollUtil.isNotEmpty(list[1])) {
            this.trendData.responseTime = list[1];
        }
    }


    public void setTrendData(ResponseTimeTrendData trendData) {
        this.trendData = trendData;
    }

    public ResponseTimeTrendData getTrendData() {
        return trendData;
    }

    /**
     * 响应时间数据，配合前端echarts折线图
     */
    public class ResponseTimeTrendData {
        private List<String> responseTime = new ArrayList<String>();
        private List<String> opTime = new ArrayList<String>();

        public void setOpTime(List<String> opTime) {
            this.opTime = opTime;
        }

        public List<String> getOpTime() {
            return opTime;
        }

        public void setResponseTime(List<String> responseTime) {
            this.responseTime = responseTime;
        }

        public List<String> getResponseTime() {
            return responseTime;
        }
    }
}
