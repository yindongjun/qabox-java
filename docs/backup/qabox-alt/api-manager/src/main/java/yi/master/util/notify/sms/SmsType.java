package yi.master.util.notify.sms;

/**
 * 通知短信类型
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/1/3  22:52
 */
public enum SmsType {
    /**
     * 1 - 验证码短信
     */
    AUTH_CODE(1),
    /**
     * 2 - 定时任务通知
     */
    TIME_TASK(2),
    /**
     * 3 - 接口探测通知
     */
    PROB_TASK(3);

    private Integer type;

    SmsType (Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
