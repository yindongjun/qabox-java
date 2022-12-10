package yi.master.business.message.enums;

/**
 * 验证条件
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/26 17:27
 */
public enum SceneValidateCondition {
    /**
     * 大于
     */
    gt,
    /**
     * 等于
     */
    eq,
    /**
     * 小于
     */
    lt,
    /**
     * 包含
     */
    contain,
    /**
     * 不包含
     */
    notcontain,
    /**
     * 存在值即可
     */
    exist,
    /**
     * 匹配正则
     */
    reg,
    /**
     * 不做任何验证
     */
    nothing
}
