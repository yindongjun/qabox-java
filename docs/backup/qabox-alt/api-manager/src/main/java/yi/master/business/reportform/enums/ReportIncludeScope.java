package yi.master.business.reportform.enums;

/**
 * 测试报告/测试场景统计范围
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/16 18:48
 */
public enum ReportIncludeScope {
    /**
     * 手工测试集测试
     */
    HAND_WORK_SET_TEST("1"),
    /**
     * 定时任务测试集测试
     */
    TIMED_TASK_SET("2"),
    /**
     * 第三方API调用
     */
    THIRD_PARTY_API_CALL_SET("3"),
    /**
     * 接口探测
     */
    INTERFACE_PROBE_TEST("4"),
    /**
     * 组合场景
     */
    COMPLEX_INTERFACE_TEST("5");

    private String scope;

    ReportIncludeScope (String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public static ReportIncludeScope getByScope (String scope) {
        for (ReportIncludeScope s:values()) {
            if (s.getScope().equals(scope)) {
                return s;
            }
        }

        return null;
    }

}
