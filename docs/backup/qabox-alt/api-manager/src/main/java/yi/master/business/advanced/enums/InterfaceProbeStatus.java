package yi.master.business.advanced.enums;

/**
 * 接口探测状态枚举
 * @author xuwangcheng
 * @date 20190714
 */
public enum InterfaceProbeStatus {
    /**
     * 0 - 已停止
     */
    STOPPED("0"),
    /**
     * 1 - 正在运行
     */
    RUNNING("1"),
    /**
     * 2 - 缺少数据
     */
    NO_DATA("2"),
    /**
     * 3 - 执行出错
     */
    EXECUTE_ERROR("3");

    private String status;

    InterfaceProbeStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
