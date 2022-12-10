package yi.master.business.message.enums;

/**
 * 测试数据类型
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 13:31
 */
public enum TestDataStatus {
    /**
     * 0 - 可使用
     */
    AVAILABLE("0"),
    /**
     * 1 - 已使用
     */
    USED("1"),
    /**
     * 2 - 可重复使用
     */
    REUSABLE("2");

    private String status;

    TestDataStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static TestDataStatus getByStatus (String status) {
        for (TestDataStatus tds:values()) {
            if (tds.getStatus().equalsIgnoreCase(status)){
                return tds;
            }
        }
        return null;
    }
}
