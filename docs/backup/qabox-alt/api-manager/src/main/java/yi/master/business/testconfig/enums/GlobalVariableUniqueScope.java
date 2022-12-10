package yi.master.business.testconfig.enums;

/**
 * 全局变量-有效范围
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 14:13
 */
public enum GlobalVariableUniqueScope {
    /**
     * 全局有效
     */
    GLOBAL("0"),
    /**
     * 同个测试集有效
     */
    SAME_SET("1"),
    /**
     * 同个组合场景有效
     */
    SAME_COMPLEX_SCENE("2"),
    /**
     * 同个测试场景有效
     */
    SAME_TEST_SCENE("3");

    private String scope;

    GlobalVariableUniqueScope (String scope) {
        this.scope = scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }


}
