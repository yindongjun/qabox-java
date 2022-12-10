package yi.master.exception;

/**
 * 错误码
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/4 18:20
 */
public enum AppErrorCode {

    //通用
    INTERNAL_SERVER_ERROR(500, "系统内部错误"),
    NO_LOGIN(7, "你还没有登录或者登录已失效,请重新登录"),
    NO_PERMISSION(8, "你没有权限进行此操作"),
    OP_DISABLE(4, "服务不可用"),
    OP_NOT_FOUND(5, "服务不存在"),
    NO_FILE_UPLOAD(6, "上传文件不存在"),
    NO_RESULT(3, "查询无结果"),
    MISS_PARAM(2, "请求参数不正确"),
    ILLEGAL_HANDLE(9, "禁止操作"),
    NAME_EXIST(10, "重复的名称"),
    PARAMETER_VALIDATE_FAIL(11, "参数验证失败"),
    OPERATION_FAIL(9999, "操作失败,请稍后再试"),
    API_TOKEN_VALIDATE_FAIL(9998, "token不正确"),
    MOCK_INTERFACE_DISABLED(9997, "mock接口被禁止调用"),
    MOCK_INTERFACE_NOT_EXIST(9996, "未定义的mock接口"),
    MOCK_ERROR(9995, "接口mock出错,请联系接口自动化测试平台"),
    SYSTEM_IS_UPDATING(9994, "系统正在系统,请稍后刷新访问..."),
    SYSTEM_IS_NEED_RESTART(9993, "系统刚刚升级完成,请联系管理员重启服务器..."),
    EXPORT_FILE_ERROR(9992, "导出文件出错,请重试"),
    TEMPLATE_FILE_ERROR(9991, "模板格式不正确,请检查"),

    //自动化测试
    AUTO_TEST_COMPLEX_SCENE_LACK_BUSINESS_SYSTEM(100001, "测试场景没有配置独立的环境,请打开组合场景中的配置管理添加"),
    AUTO_TEST_NO_SCENE(100002, "无可测试的场景"),
    AUTO_TEST_BUSINESS_SYSTEM_NOT_EXIST(100003, "测试环境[{}]不存在或者被禁用,请检查"),


    //用户相关
    USER_RE_LOGIN(200001, "你已登陆此账号"),
    USER_ACCOUNT_LOCK(200002, "该账号已被锁定,请联系管理员"),
    USER_VERIFY_CODE_ERROR(200003, "验证码不正确"),
    USER_ERROR_ACCOUNT(200004, "账号或者密码不正确"),
    USER_PASSWORD_VALIDATE_ERROR(200005, "密码验证不正确"),

    //站内信
    MAIL_MISS_RECEIVER(300001, "需要选定一个收件用户才能发送"),

    //查询数据源
    DB_CONNECT_FAIL(310001, "尝试连接数据库失败,请检查配置"),


    //接口相关
    INTERFACE_ILLEGAL_TYPE(320001, "格式不正确"),
    INTERFACE_INFO_NOT_EXIST(320002, "不存在的接口信息"),
    INTERFACE_PARAMS_HAS_BEEN_CHANGE(320003, "接口参数有改动,请检查并重新设置报文的入参信息"),

    //报文相关
    MESSAGE_VALIDATE_ERROR(330001, "入参验证失败"),
    MESSAGE_INFO_NOT_EXITS(330002, "报文信息不存在"),
    MESSAGE_PARSE_ERROR(330003, "不支持的报文格式，无法解析"),

    //定时任务
    QUARTZ_SCHEDULER_IS_START(340001, "定时任务调度器已经是启动状态了"),
    QUARTZ_HAS_BEEN_STOP(340002, "定时任务调度器已经被暂停了"),
    QUARTZ_NEED_CRON_EXPRESSION(340003, "请先设置定时任务规则/cron表达式"),
    QUARTZ_CRON_EXPRESSION_SETTING_ERROR(340004, "启动定时任务出错,详情：{}"),

    //测试报告
    REPORT_INFO_NOT_EXIST(350001, "测试报告信息不存在"),
    REPORT_TEST_NO_DATA(350002, "该项测试还未完成或者没有任何测试结果"),


    //高级测试相关
    MOCK_PROTOCOL_NOT_SUPPORT(360001, "不支持该协议接口的Mock"),
    MOCK_URL_EXIST(360002, "已存在Url路径为{}的Mock信息,请重新选择或者修改接口场景中的请求路径！"),

    //场景相关
    SCENE_INFO_NOT_EXIST(370001, "测试场景不存在"),

    //项目相关
    PROJECT_CAN_NOT_DELETE(380001, "该项目下仍有关联信息，请手动删除这些信息或者将该项目设置为禁用状态！")
    ;

    private Integer code;
    private String msg;

    private AppErrorCode (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
