package yi.master.business.message.enums;

/**
 * 通用的状态表示枚举
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 11:21
 */
public enum  CommonStatus {
    /**
     * 0 - 可用
     */
    ENABLED("0"),
    /**
     * 1 - 禁用
     */
    DISABLED("1");

    private String status;

    CommonStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
