package yi.master.business.testconfig.enums;

/**
 * 测试运行模式
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 13:56
 */
public enum  TestRunType {
    /**
     * 0 - 并行
     */
    PARALLEL("0"),
    /**
     * 1 - 串行
     */
    SERIAL("1");

    private String type;

    TestRunType (String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
