package yi.master.business.user.enums;

/**
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 14:17
 */
public enum  UserStatus {
    /**
     * 0 - 正常
     */
    NORMAL("0"),
    /**
     * 锁定
     */
    LOCKED("1");

    private String status;

    UserStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
