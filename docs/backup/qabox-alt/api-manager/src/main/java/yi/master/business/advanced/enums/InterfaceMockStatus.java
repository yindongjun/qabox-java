package yi.master.business.advanced.enums;

/**
 * 接口mock状态枚举
 * @author xuwanghceng
 * @date 20190914
 */
public enum InterfaceMockStatus {
    /**
     * 0 - 启用
     */
    ENABLED("0"),
    /**
     * 1 - 禁用
     */
    DISABLED("1");

    private String status;

    private InterfaceMockStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
