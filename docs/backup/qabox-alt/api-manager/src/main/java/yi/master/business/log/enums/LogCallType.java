package yi.master.business.log.enums;

/**
 * 日志-接口调用类型
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 10:49
 */
public enum LogCallType {
    /**
     * 0 - 用户调用
     */
    USER_CALLED("0"),
    /**
     * 第三方调用
     */
    THIRD_PARTY_CALLED("1"),
    /**
     * 自调
     */
    SELF_CALLED("2"),
    /**
     * mock
     */
    MOCK("3");

    private String type;

    private LogCallType(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
