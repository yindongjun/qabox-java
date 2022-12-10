package yi.master.business.testconfig.enums;

/**
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 13:59
 */
public enum GlobalVariableType {
    /**
     * HTTP协议调用参数模板
     */
    httpCallParameter,
    /**
     * socket协议调用参数模板
     */
    socketCallParameter,
    /**
     * webService调用参数模板
     */
    webServiceCallParameter,
    /**
     * 验证规则中的关联设定模板
     */
    relatedKeyWord,
    /**
     * 测试集运行时配置模板
     */
    setRuntimeSetting,
    /**
     * 常量
     */
    constant,
    /**
     * 当前日期时间
     */
    datetime,
    /**
     * 随机数,目前只能是整数
     */
    randomNum,
    /**
     * 当前时间戳
     */
    currentTimestamp,
    /**
     * 随机字母组成的字符串
     */
    randomString,
    /**
     * uuid
     */
    uuid,
    /**
     * 动态接口
     */
    dynamicInterface,
    /**
     * 动态组合接口
     */
    dynamicComplexInterface,
    /**
     * 文件
     */
    fileParameter,
    /**
     * 数据库取值
     */
    dbSql
}
