package yi.master.business.message.enums;

/**
 * 组合场景测试成功的标记
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 11:17
 */
public enum  ComplexSceneSuccessFlag {
    /**
     * 0 -  要求所有场景必须测试成功
     */
    ALL_SUCCESS_FLAG("0"),
    /**
     * 2 - 单独统计结果(即在测试报告中，所有测试详情结果不会出现组合场景中)
     */
    SEPARATE_STATISTICS_RESULT("2");

    private String flag;

    ComplexSceneSuccessFlag (String flag) {
        this.flag = flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
