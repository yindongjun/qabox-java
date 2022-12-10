package yi.master.business.message.enums;

/**
 * 场景验证-值获取方式
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 13:23
 */
public enum SceneValidateValueGetMethod {
    /**
     * 0 - 常量
     */
    CONSTANT("0"),
    /**
     * 1 - 入参节点
     */
    REQUEST_PARAMS_NODE("1"),
    /**
     * 查询SQL
     */
    QUERY_SQL("999999"),
    /**
     * 全局变量
     */
    GLOBAL_VARIABLE("3"),
    /**
     * 正则表达式
     */
    REGULAR_EXPRESSION("4"),
    /**
     * 响应时间
     */
    RESPONSE_TIME("5"),
    /**
     * 状态码
     */
    STATUS_CODE("6"),
    /**
     * 响应头
     */
    RESPONSE_HEADER("7");

    private String method;

    SceneValidateValueGetMethod (String method) {
        this.method = method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
