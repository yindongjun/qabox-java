package yi.master.business.advanced.enums;

/**
 * 性能测试-参数取值方式
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 10:28
 */
public enum PtParameterGetType {
    /**
     * 0 - 按照顺序
     */
    SEQUENCE("0"),
    /**
     * 1 - 随机
     */
    RANDOM("1");

    private String type;

    private PtParameterGetType (String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
