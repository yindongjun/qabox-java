package yi.master.business.message.enums;

/**
 * 场景验证方式
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 13:27
 */
public enum  SceneValidateType {
    /**
     * 0 - 关联验证
     */
    ASSOCIATED_VALIDATION("0"),
    /**
     * 1 - 节点验证
     */
    NODE_VALIDATION("1"),
    /**
     * 2 - 自定义验证
     */
    CUSTOM_VALIDATION("2");

    private String type;

    SceneValidateType (String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
