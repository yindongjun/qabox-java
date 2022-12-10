package yi.master.business.message.enums;

/**
 * 自动化测试类型
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 11:07
 */
public enum TestType {
    /**
     * 0 - 接口
     */
    INTERFACE("0"),
    /**
     * 1 - web
     */
    WEB_UI("1"),
    /**
     * 2 - app
     */
    APP_UI("2");

    private String type;

    private TestType (String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
