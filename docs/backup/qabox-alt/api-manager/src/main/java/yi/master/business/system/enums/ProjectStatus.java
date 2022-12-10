package yi.master.business.system.enums;

/**
 * 项目状态
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  9:24
 */
public enum ProjectStatus {
    /**
     * 0 - 未开始
     */
    NOT_STARTED("0"),
    /**
     * 1 - 设计中
     */
    UNDER_DESIGN("1"),
    /**
     * 2 - 开发中
     */
    DEVELOPING("2"),
    /**
     * 3 - 测试中
     */
    TESTING("3"),
    /**
     * 4 - 已上线
     */
    ONLINE("4"),
    /**
     * 5 - 验收测试
     */
    UAT("5"),
    /**
     * 6 - 已完成
     */
    FINISHED("6"),
    /**
     * 7 - 禁用
     */
    DISABLED("7");

    private String status;

    ProjectStatus (String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
