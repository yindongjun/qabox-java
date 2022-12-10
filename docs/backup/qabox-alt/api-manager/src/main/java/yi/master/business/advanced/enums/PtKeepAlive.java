package yi.master.business.advanced.enums;

/**
 * 性能测试-长连接短连接
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 10:23
 */
public enum PtKeepAlive {
    /**
     * 1 - 长连接
     */
    KEEP_ALIVE("1"),
    /**
     * 0 - 短连接
     */
    SHORT_CONNECTION("0");
    private String code;

    private PtKeepAlive (String code) {
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
