package yi.master.business.reportform.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.bean.ReturnJSONObject;
import yi.master.business.reportform.bean.index.IndexChartRenderData;
import yi.master.business.reportform.bean.report.SceneReportChartRenderData;
import yi.master.business.reportform.service.ReportFormService;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/12 18:01
 */
@Controller
@Scope("prototype")
public class ReportFormController extends ActionSupport {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReportFormService reportFormService;
    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(ReportFormController.class.getName());
    /**
     * ajax调用返回的map
     */
    private ReturnJSONObject jsonObject = new ReturnJSONObject();

    /**
     * 统计时间，~ 分隔
     */
    private String rangeDate;

    /**
     * 统计范围
     */
    private String includeScope;

    /**
     * 指定测试场景ID
     */
    private Integer messageSceneId;

    /**
     * 获取首页图表渲染数据
     * @author xuwangcheng
     * @date 2019/12/12 18:08
     * @param
     * @return {@link String}
     */
    public String getIndexChartRenderData () {
        IndexChartRenderData indexChartRenderData = new IndexChartRenderData();
        indexChartRenderData.setOverview(null);
        indexChartRenderData.setStat();
        jsonObject.setData(indexChartRenderData);
        return SUCCESS;
    }

    /**
     * 获取每日新增统计数据
     * @author xuwangcheng
     * @date 2019/12/16 18:06
     * @param
     * @return {@link String}
     */
    public String getDailyAddChartRenderData () {
        IndexChartRenderData indexChartRenderData = new IndexChartRenderData(rangeDate);
        indexChartRenderData.setStat();
        jsonObject.setData(indexChartRenderData);
        return SUCCESS;
    }

    /**
     * 获取测试报告统计数据
     * @author xuwangcheng
     * @date 2019/12/16 18:36
     * @param
     * @return {@link String}
     */
    public String getTestReportChartRenderData () {
        IndexChartRenderData indexChartRenderData = new IndexChartRenderData(rangeDate);
        indexChartRenderData.setOverview(CollUtil.newArrayList(StrUtil.split(includeScope, ",")));
        jsonObject.setData(indexChartRenderData);
        return SUCCESS;
    }

    /**
     * 获取场景测试响应时间趋势数据
     * @author xuwangcheng
     * @date 2019/12/17 17:55
     * @param
     * @return {@link String}
     */
    public String getSceneReportResponseTimeTrendData () {
        SceneReportChartRenderData renderData = new SceneReportChartRenderData(rangeDate);
        renderData.setTrendData(messageSceneId, CollUtil.newArrayList(StrUtil.split(includeScope, ",")));
        jsonObject.setData(renderData);
        return SUCCESS;
    }


    public ReturnJSONObject getJsonObject() {
        return jsonObject;
    }

    public void setRangeDate(String rangeDate) {
        this.rangeDate = rangeDate;
    }

    public void setIncludeScope(String includeScope) {
        this.includeScope = includeScope;
    }

    public void setMessageSceneId(Integer messageSceneId) {
        this.messageSceneId = messageSceneId;
    }
}
