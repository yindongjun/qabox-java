package yi.master.business.log.enums;

/**
 * 日志拦截状态枚举
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 10:41
 */
public enum  LogInterceptStatus {
    /**
     * 0 - 正常
     */
    SUCCESS("0"),
    /**
     * 1 - 没有权限
     */
    NO_PERMISSION("1"),
    /**
     * 没有登录
     */
    NO_LOGIN("2"),
    /**
     * 放行
     */
    PERMIT_THROUGH("3"),
    /**
     * token不正确
     */
    TOKEN_INCORRECT("4"),
    /**
     * 接口已禁用
     */
    INTERFACE_DISABLED("5"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR("6"),
    /**
     * mock错误
     */
    MOCK_ERROR("7"),
    /**
     * mock接口不存在
     */
    MOCK_NOT_EXIST("8");

    private String status;

    private LogInterceptStatus (String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
