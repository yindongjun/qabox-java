package yi.master.business.message.enums;

/**
 * 组合场景测试客户端类型
 * @author xuwangcheng14@163.com
 * @date 2019/9/14 11:13
 */
public enum ComplexSceneTestClientType {
    /**
     * 0 - 采用独立的httpclient客户端
     */
    NEW_CLIENT("0"),
    /**
     * 1 - 使用共享客户端来测试
     */
    SHARE_CLIENT("1");

    private String clientType;

    ComplexSceneTestClientType (String clientType) {
        this.clientType = clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientType() {
        return clientType;
    }
}
