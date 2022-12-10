package yi.master.business.advanced.enums;

/**
 * 接口探测消息通知类型
 * @author xuwangcheng14@163.com
 * @date 2019/9/15 10:55
 */
public enum InterfaceProbeNotifyType {
    /**
     * 0 - 不通知
     */
    DISABLED_NOTIFY("0"),
    /**
     * 1 - 站内信
     */
    MESSAGE("1"),
    /**
     * 2 - 邮件和站内信
     */
    MAIL_AND_MESSAGE("2");

    private String type;

    InterfaceProbeNotifyType (String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
