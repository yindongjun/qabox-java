package yi.master.coretest.message.protocol.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * 测试结果返回
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/5 20:03
 */
public class ClientTestResponseObject {

    /**
     * 返回报文
     */
    private String responseMessage;
    /**
     * 请求头，只针对与http/https
     */
    private String headers;
    /**
     * 调用时间
     */
    private long useTime;
    /**
     * 状态码，如果为false代表是测试中断了
     */
    private String statusCode;
    /**
     * 一般是测试失败时的备注
     */
    private String mark = "";

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public long getUseTime() {
        return useTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void addMark (String mark) {
        if (this.mark == null) {
            this.mark = "";
        }
        if (StringUtils.isNotBlank(mark)) {
            this.mark += ("\n" + mark);
        }
    }
}

