package yi.master.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义异常
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/4 18:22
 */
public class YiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public YiException(AppErrorCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public YiException(AppErrorCode code, Object ...params) {
        this.code = code.getCode();
        this.msg = StrUtil.format(code.getMsg(), params);
    }

    public YiException(AppErrorCode code, Throwable e, Object ...params) {
        super(e);
        this.code = code.getCode();
        this.msg = StrUtil.format(code.getMsg(), params);
    }

    public YiException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public YiException(Integer code, String msg, Throwable e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }


    public YiException(String msg) {
        super(msg);
        this.code = AppErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public YiException(String format, String ... var) {
        super(StrUtil.format(format, var));
        this.code = AppErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = StrUtil.format(format, var);
    }

    public YiException(String msg, Throwable e) {
        super(msg, e);
        this.code = AppErrorCode.INTERNAL_SERVER_ERROR.getCode();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
