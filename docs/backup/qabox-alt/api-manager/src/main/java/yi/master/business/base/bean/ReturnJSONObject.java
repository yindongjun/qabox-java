package yi.master.business.base.bean;


import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;

import java.io.Serializable;

/**
 * 通用json返回对象
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/4 19:06
 */
public class ReturnJSONObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int SUCCESS_CODE = 0;

    public ReturnJSONObject () {
        this.returnCode = SUCCESS_CODE;
        this.msg = "ok";
    }

    public ReturnJSONObject (int returnCode, String msg) {
        this.returnCode = returnCode;
        this.msg = msg;
    }

    public ReturnJSONObject (YiException ye) {
        this.returnCode = ye.getCode();
        this.msg = ye.getMsg();
    }

    public ReturnJSONObject (AppErrorCode appErrorCode) {
        this.returnCode = appErrorCode.getCode();
        this.msg = appErrorCode.getMsg();
    }


    private String msg;
    private Integer returnCode;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ReturnJSONObject data(Object data) {
        this.data = data;
        return this;
    }

}
