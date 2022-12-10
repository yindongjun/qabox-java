package yi.master.business.message.enums;

/**
 * 定时任务状态
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 11:10
 */
public enum AutoTaskStatus {
    /**
     * 0 - 运行中
     */
    RUNNING("0"),
    /**
     * 1 - 已停止
     */
    STOPPED("1");

    private String status;

    private AutoTaskStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
