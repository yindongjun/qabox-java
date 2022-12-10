//全局常量和配置
//返回值
var RETURN_CODE = {
    SUCCESS: 0,
    NO_LOGIN: 7
}
//超级管理员用户
var SUPER_ADMIN_USER_ID = 1;

// 默认项目
var DEFAULT_PROJECT_ID = 1;

//请求路径
var REQUEST_URL = {
    WEB_SOCKET: {
      PUSH_MAIL_NUM: "/push/mail",
    },
    //报表统计相关
    REPORT_FORM: {
        GET_INDEX_CHART_RENDER_DATA: "reportForm-getIndexChartRenderData",
        GET_DAILY_ADD_CHART_RENDER_DATA: "reportForm-getDailyAddChartRenderData",
        GET_TEST_REPORT_CHART_RENDER_DATA: 'reportForm-getTestReportChartRenderData',
        GET_SCENE_REPORT_RESPONSE_TIME_TREND_DATA: 'reportForm-getSceneReportResponseTimeTrendData'
    },
    //登陆相关
    LOGIN: {
        LOGOUT: "user-logout",
        LOGIN: "user-toLogin",
        CREATE_VERIFY_CODE: "user-createVerifyCode",
        JUDGE_LOGIN: "user-judgeLogin",
        GET_LOGIN_USER_INFO: "user-getLoginUserInfo"
    },
    //自动化测试
    AUTO_TEST: {
        BATCH_AUTO_TEST_URL: "test-scenesTest", //批量测试   测试集或者全量
        GET_TEST_CONFIG_URL: "test-getConfig", //获取当前用户的测试配置
        UPDATE_TEST_CONFIG_URL: "test-updateConfig",
        CHECK_DATA_URL: "test-checkHasData",
        TEST_SCENE_URL: "test-sceneTest",
        TEST_COMPLEX_SCENE_URL: "test-complexSceneTest"
    },
    //接口参数
    INTERFACE_PARAMS: {
        SAVE: "param-save",
        DEL: "param-del",
        GET:"param-get",
        EDIT:"param-edit",
        LIST: "param-getParams",
        MSG_IMPORT: "param-batchImportParams",
        DEL_ALL_BY_INTERFACE_ID: "param-delInterfaceParams"
    },
    //接口
    INTERFACE: {
        DEL: "interface-del",
        GET:"interface-get",
        EDIT:"interface-edit",
        LIST: "interface-list",
        CHECK_NAME: "interface-checkName",
        IMPORT_FROM_EXCEL: "interface-importFromExcel",
        EXPORT_DOCUMENT_EXCEL: "interface-exportInterfaceDocument",
        GET_PARAMETERS_JSON_TREE: "interface-getParametersJsonTree",
        UPDATE_CHILDREN_BUSINESS_SYSTEMS: "interface-updateChildrenBusinessSystems",
        BATCH_UPDATE_PARAM: "interface-batchUpdateParam"
    },
    //报文
    MESSAGE: {
        DEL: "message-del",
        GET:"message-get",
        EDIT:"message-edit",
        LIST: "message-list",
        FORMAT: "message-format",
        VALIDATE_JSON: "message-validateJson",
        IMPORT_FROM_EXCEL: "message-importFromExcel",
        CREATE_MESSAGE_BY_NODES: "message-createMessage"
    },
    //场景
    MESSAGE_SCENE: {
        DEL: "scene-del",
        GET:"scene-get",
        EDIT:"scene-edit",
        LIST: "scene-list",
        GET_TEST_OBJECT: "scene-getTestObject",
        LIST_NO_DATA_SCENES: "scene-listNoDataScenes",
        IMPORT_FROM_EXCEL: "scene-importFromExcel",
        UPDATE_RESPONSE_EXAMPLE: "scene-updateResponseExample",
        GET_REQUEST_MSG_JSON_TREE: "scene-getRequestMsgJsonTree",
        GET_RESPONSE_MSG_JSON_TREE: "scene-getResponseMsgJsonTree"
    },
    //测试数据
    TEST_DATA: {
        GET_SETTING_DATA: "data-getSettingData",
        IMPORT_DATA_VALUES: "data-importDatas",
        DEL: "data-del",
        GET:"data-get",
        EDIT:"data-edit",
        LIST: "data-list",
        CHECK_NAME: "data-checkName",
        CREATE_NEW_DATA_MSG: "data-createDataMsg",
        CHANGE_STATUS: "data-changeStatus",
        UPDATE_PARAMS_DATA: "data-updateParamsData"
    },
    //数据池
    DATA_POOL: {
        DEL: "pool-del",
        GET: "pool-get",
        EDIT: "pool-edit",
        LIST: "pool-list",
        LIST_ALL: "pool-listAll",
        SHOW_JSON_DATA: "pool-showJsonData"
    },
    //数据池变量
    POOL_DATA_NAME: {
        DEL: "poolDataName-del",
        GET: "poolDataName-get",
        EDIT: "poolDataName-edit",
        LIST: "poolDataName-list",
        CHECK_NAME: "poolDataName-checkName",
        BATCH_ADD: "poolDataName-batchAdd"
    },
    // 数据池类别
    POOL_DATA_ITEM: {
        DEL: "poolDataItem-del",
        GET: "poolDataItem-get",
        EDIT: "poolDataItem-edit",
        LIST: "poolDataItem-list",
        LIST_ALL: "poolDataItem-listAll",
        BATCH_ADD: "poolDataItem-batchAdd",
        LIST_NAME_VALUE: "poolDataItem-listNameValue",
        SAVE_NAME_VALUE: "poolDataItem-saveNameValue",
        EXPORT_VALUE_TEMPLATE: "poolDataItem-exportValueTemplate",
        CHECK_NAME: "poolDataItem-checkName",
        IMPORT_ITEM_VALUE: "poolDataItem-importItemValue",
        UPDATE_VALUE_BY_REQUEST: "poolDataItem-updateValueByRequest"
    },
    //测试报告
    REPORT: {
        DEL: "report-del",
        GET:"report-get",
        LIST: "report-list",
        DOWNLOAD_STATIC_REPORT_HTML: "report-generateStaticReportHtml",
        GET_DETAILS: "report-getReportDetail",
        SEND_MAIL: "report-sendMail"
    },
    //测试结果
    REPORT_RESULT: {
        LIST: "result-list",
        GET: "result-get"
    },
    //测试集
    TEST_SET: {
        OP_SCENE: "set-opScene",
        LIST_MY_SETS: "set-getMySet",
        SET_SCENE_LIST: "set-listScenes",
        DEL: "set-del",
        GET:"set-get",
        EDIT:"set-edit",
        LIST: "set-list",
        CHECK_NAME: "set-checkName",
        SETTING_RUN_CONFIG: "set-settingConfig",
        GET_CATEGORY_NODES: "set-getCategoryNodes",
        MOVE_TO_FOLDER: "set-moveFolder"
    },
    //组合场景
    COMPLEX_SCENE: {
        DEL: "complexScene-del",
        GET:"complexScene-get",
        EDIT:"complexScene-edit",
        LIST: "complexScene-list",
        LIST_SET_SCENES: "complexScene-listSetScenes",
        ADD_TO_SET: "complexScene-addToSet",
        DEL_FROM_SET: "complexScene-delFromSet",
        LIST_SCENES: "complexScene-listScenes",
        UPDATE_CONFIG_INFO: "complexScene-updateConfigInfo",
        ADD_SCENE: "complexScene-addScene",
        DEL_SCENE: "complexScene-delScene",
        SORT_SCENES: "complexScene-sortScenes",
        UPDATE_SCENE_CONFIG: "complexScene-updateSceneConfig",
        LIST_SAVE_VARIABLES: "complexScene-getSaveVariables"
    },
    //验证规则
    VALIDATE: {
        FULL_EDIT: "validate-validateFullEdit",
        DEL: "validate-del",
        GET:"validate-get",
        EDIT:"validate-edit",
        LIST: "validate-getValidates",
        FULL_RULE_GET: "validate-getValidate",
        RULE_UPDATE_STATUS: "validate-updateValidateStatus",
        GET_CONFIG_VALIDATE_RULES: "validate-getConfigValidates",
    },
    //定时任务
    TASK: {
        CHECK_NAME: "task-checkName",
        DEL: "task-del",
        GET:"task-get",
        EDIT:"task-edit",
        LIST: "task-list",
        STOP_TASK: "task-stopRunningTask",
        ADD_RUNNABLE_TASK: "task-startRunableTask",
        START_QUARTZ: "task-startQuartz",
        STOP_QUARTZ: "task-stopQuartz",
        GET_QUARTZ_STATUS: "task-getQuartzStatus",
        UPDATE_CRON_EXPRESSION: "task-updateCronExpression"
    },
    //全局设置
    GLOBAL_SETTING: {
        EDIT: "global-edit",
        SETTING_LIST_ALL: "global-listAll",
        GET_WEB_SETTINGS: "global-getWebSettings",
        GET_STATISTICAL_QUANTITY: "global-getStatisticalQuantity",
        CHECK_SYSTEM_VERSION: "global-checkSystemVersion"
    },
    //内部接口
    OP_INTERFACE: {
        DEL: "op-del",
        GET:"op-get",
        EDIT:"op-edit",
        LIST: "op-listOp",
        GET_NODE_TREE: "op-getNodeTree",
        LIST_ALL: "op-listAll",
        LIST_BY_PAGE_NAME: "op-listByPageName"
    },
    //查询数据源
    QUERY_DB: {
        LINK_TEST: "db-testDB",
        DEL: "db-del",
        GET:"db-get",
        EDIT:"db-edit",
        LIST: "db-list",
        LIST_ALL: "db-listAll"
    },
    //站内信
    MAIL: {
        LIST: "mail-list",
        DEL: "mail-del",
        CHANGE_STATUS: "mail-changeStatus",
        GET_NO_READ_MAIL_NUM: "mail-getNoReadMailNum",

    },
    //角色
    ROLE: {
        DEL: "role-del",
        GET:"role-get",
        EDIT:"role-edit",
        LIST: "role-list",
        GET_NODES_INTERFACE: "role-getInterfaceNodes",
        GET_NODES_MENU: "role-getMenuNodes",
        UPDATE_POWER: "role-updateRolePower",
        UPDATE_MENU: "role-updateRoleMenu",
        LIST_ALL: "role-listAll",
        GET_USER_PERMISSION_LIST: "role-getUserPermissionList"
    },
    //用户
    USER: {
        LIST: "user-list",
        LOCK: "user-lock",
        GET: "user-get",
        EDIT: "user-edit",
        RESET_PASSWORD: "user-resetPwd",
        VERIFY_PASSWORD: "user-verifyPasswd",
        MODIFY_PASSWORD: "user-modifyPasswd",
    },
    //全局变量
    GLOBAL_VARIABLE: {
        DEL: "variable-del",
        GET:"variable-get",
        EDIT:"variable-edit",
        LIST_ALL: "variable-listAll",
        CHECK_NAME: "variable-checkName",
        UPDATE_VALUE: "variable-updateValue",
        CREATE_VARIABLE: "variable-createVariable"
    },
    //业务系统
    BUSINESS_SYSTEM: {
        DEL: "system-del",
        GET:"system-get",
        EDIT:"system-edit",
        LIST_ALL: "system-listAll",
        LIST: "system-list",
        INTERFACE_LIST: "system-listInterface",
        OP_INTERFACE: "system-opInterface"
    },
    //探测任务
    PROBE_TASK: {
        DEL: "probe-del",
        GET:"probe-get",
        EDIT:"probe-edit",
        LIST_ALL: "probe-listAll",
        LIST: "probe-list",
        UPDATE_CONFIG: "probe-updateConfig",
        START: "probe-startTask",
        STOP: "probe-stopTask",
        GET_SINGLE_REPORT_DATA: "probe-getProbeResultReportData",
        GET_PROBE_RESULT_SYNOPSIS_VIEW_DATA: "probe-getProbeResultSynopsisViewData",
        BATCH_ADD: "probe-batchAdd"
    },
    //文件上传和下载
    FILE: {
        UPLOAD_FILE: "file-upload",
        DOWNLOAD_FILE: "file-download"
    },
    //日志记录
    LOG_RECORD: {
        DEL: "log-del",
        GET:"log-get",
        LIST: "log-list",
    },
    //接口MOCK
    INTERFACE_MOCK: {
        DEL: "mock-del",
        GET:"mock-get",
        EDIT:"mock-edit",
        LIST: "mock-list",
        CHECK_NAME: "mock-checkName",
        UPDATE_STATUS: "mock-updateStatus",
        UPDATE_SETTING: "mock-updateSetting",
        PARSE_MESSAGE_TO_CONFIG: "mock-parseMessageToConfig",
        PARSE_MESSAGE_TO_NODES: "mock-parseMessageToNodes",
        PARSE_SCENE_TO_MOCK_INFO: "mock-parseSceneToMockInfo"
    },
    //性能测试
    PERFORMANCE_TEST: {
        DEL: "ptc-del",
        GET:"ptc-get",
        EDIT:"ptc-edit",
        LIST: "ptc-list",
        TASK_LIST: "ptc-listTest",
        TASK_STOP: "ptc-stopTest",
        TASK_DEL: "ptc-delTest",
        TASK_ACTION: "ptc-actionTest",
        TASK_VIEW: "ptc-viewTest",
        TASK_INIT: "ptc-initTest"
    },
    //性能测试结果
    PERFORMANCE_RESULT: {
        DEL: "ptr-del",
        GET:"ptr-get",
        LIST: "ptr-list",
        ANALYZE: "ptr-analyseView",
        SUMMARIZED: "ptr-summarizedView",
        DETAILS_LIST_ALL: "ptr-detailsList"
    },
    //Web自动化脚本模块
    WEB_SCRIPT_MODULE:{
        DEL: "webModule-del",
        GET:"webModule-get",
        EDIT:"webModule-edit",
        LIST: "webModule-list",
        CHECK_NAME: "webModule-checkName"
    },
    //Web自动化脚本任务
    WEB_SCRIPT_TASK: {
        LIST:"webTask-list",
        DEL:"webTask-del"
    },
    //web元素
    WEB_ELEMENT: {
        LIST_ALL: "element-listAll",
        DEL: "element-del",
        GET:"element-get",
        EDIT:"element-edit",
        LIST: "element-list",
        MOVE: "element-move",
        COPY: "element-copy"
    },
    //web用例
    WEB_CASE: {
        LIST_ALL: "webcase-listAll",
        DEL: "webcase-del",
        GET:"webcase-get",
        EDIT:"webcase-edit",
        LIST: "webcase-list",
        CHANGE_BROWSER_TYPE: "webcase-changeBrowserType",
        UPDATE_CONFIG_JSON: "webcase-updateConfig"
    },
    //web步骤
    WEB_STEP: {
        LIST_ALL: "webstep-listAll",
        DEL: "webstep-del",
        GET:"webstep-get",
        EDIT:"webstep-edit",
        LIST: "webstep-list",
        UPDATE_CONFIG: "webstep-updateConfig"
    },
    //web测试套件
    WEB_SUITE: {
        LIST_ALL: "websuite-listAll",
        DEL: "websuite-del",
        GET:"websuite-get",
        EDIT:"websuite-edit",
        LIST: "websuite-list",
        SUITE_CHANGE_BROWSER_TYPE: "websuite-changeBroswerType",
        UPDATE_CONFIG_JSON: "websuite-updateConfig",
        LIST_CASE: "websuite-listCase",
        OP_CASE: "websuite-opCase",
        CASE_UPDATE_SETTING: "websuite-updateCaseSetting"
    },
    //web自动化设置
    WEB_CONFIG: {
        GET: "webconfig-get",
        EDIT: "webconfig-edit"
    },
    //菜单
    MENU: {
        LIST_ALL: "menu-listAll",
        DEL: "menu-del",
        GET:"menu-get",
        EDIT:"menu-edit",
        LIST: "menu-list",
        GET_USER_MENUS: "menu-getUserMenus"
    },
    // 项目信息
    PROJECT_INFO: {
        DEL: "project-del",
        GET:"project-get",
        EDIT:"project-edit",
        LIST: "project-list",
        OP_USER: "project-addOrDelUser",
        OP_INTERFACE: "project-moveOrCopyOrDelInterface",
        LIST_USERS: "project-queryUsers",
        LIST_INTERFACES: "project-queryInterfaces",
        LIST_USER_PROJECTS: "project-listUserProjects"
    }
}

//表单注释
var EXPLANATION_MARK = {
    "parameterizedFilePath":{
        "title":"关于参数化文件的说明",
        "content":"上传的参数化文件需为<span class=\"label label-primary\"> txt </span>格式。<br>第一行为参数化节点路径(路径名称大小写敏感，请与参数标识符保持一致),从第二列开始为参数化数据,<span style='color:red;'>默认数据分隔符为英文下逗号,你可以自行设定不同的分隔符。</span>"
    },
    "mock-validate-path":{
        "title":"Mock入参验证节点路径的填写说明",
        "content":"根据入参报文的格式不同，该项需要填写不同的内容:<br><span class=\"label label-primary radius\"> JSON、XML: </span> 完整的入参节点路径,例如ROOT.INFO.USERNAME;<br><span class=\"label label-primary radius\"> URL: </span> 留空;<br><span class=\"label label-primary radius\"> 不限格式: </span> 例如Socket报文，可使用关联规则来获取。<br><span style=\"color:red;\">对于URL参数和请求头，此处可忽略。</span>"
    },
    "settingMailStyle":{
        "title":"自定义推送邮件的模板",
        "content":"你可以自定义设定定时任务和探测任务的推送的邮件模板，模板类型为Html，同时可以在模板中插入相关测试结果变量。"
    },
    "webStepConfig":{
        "title":"测试步骤配置",
        "html":true,
        "content":"explanation-mark-webstep-config",
        "width":915,
        "height":500
    },
    "webTestConfig":{
        "title":"Web自动化运行时配置说明",
        "content":"1、如果你的浏览器driver文件路径没有放置到环境变量下，请在此配置，<span class=\"label label-danger radius\"> 请填写全路径,注意填写的是自动化执行客户端所在机器上的driver路径 . </span><br>2、如何你的浏览器不是安装在默认路径或者安装的是绿色版，请指定各浏览器的执行bin文件的绝对路径（其他同上）。"
    },
    "webCustomVariableSetting":{
        "title":"Web自动化自定义变量说明",
        "content":"在测试用例、测试用例集、用例与用例集的关联对象中可以配置自定义的变量，变量以key-value形式保存（<span style=\"color:red;\">其中value可以使用全局变量</span>），自定义的变量可以在下属的测试步骤中使用：<br><span class=\"label label-danger radius\">优先级 </span>： <span class=\"label label-primary radius\">用例集自定义变量 </span> &gt; <span class=\"label label-primary radius\">用例与用例集关联对象自定义变量</span> &gt; <span class=\"label label-primary radius\">用例自定义变量</span>"
    },
    "webSuiteCaseSetting":{
        "title":"测试集用例自定义 配置说明",
        "content":"<span class=\"label label-danger radius\">执行顺序：</span>默认按照设定的数字大小来排序，数字越小的先执行，越大的后执行。<br><span class=\"label label-danger radius\">分组名：</span>在进行分布式执行时，会将组名相同的用例分配到同一个远程执行机执行。<br><span class=\"label label-danger radius\">跳过标记：</span>方便在不需要执行某个用例时排除而不是删除掉。"
    }
}

// 项目状态
var PROJECT_STATUS = {
    "0": "未开始",
    "1": "设计中",
    "2": "开发中",
    "3": "测试中",
    "4": "已上线",
    "5": "验收测试",
    "6": "已完成",
    "7": "禁用"
}


//接口自动化相关
//报文处理器参数设置
var MESSAGE_PROCESS = {
    "ProcessDemo":{
        "params1": "123",
        "params2": "222"
    }
}

//接口可Mock类型
var MESSAGE_MOCK_TYPE = {
    "HTTP":"HTTP",
    "Socket": "Socket",
    "WebSocket": "WebSocket",
    "HTTPS": "HTTPS"
}

//接口协议-调用参数配置信息
var MESSAGE_PROTOCOL = {
    "HTTP":{
        "Headers":{
        },
        "Querys":{
        },
        "Authorization":{
        },
        "Method":"POST",
        "ConnectTimeOut":"",
        "ReadTimeOut":"",
        "RecEncType":"UTF-8",
        "EncType":"UTF-8"
    },
    "HTTPS":{
        "Headers":{
        },
        "Querys":{
        },
        "Authorization":{
        },
        "Method":"POST",
        "ConnectTimeOut":"",
        "ReadTimeOut":"",
        "RecEncType":"UTF-8",
        "EncType":"UTF-8"
    },
    "Socket":{
        "ConnectTimeOut":"",
        "ReadTimeOut":"",
        "EncType":"UTF-8",
        "RecEncType":"UTF-8"
    },
    // "WebService":{
    //     "ConnectTimeOut":"",
    //     "Namespace":"",
    //     "Method":"",
    //     "Username":"",
    //     "Password":""
    // },
    "WebSocket":{
        "ConnectTimeOut":"",
        "ReadTimeOut":""
    },
    "Dubbo":{
        "ConnectTimeOut":"",
        "ReadTimeOut":""
    }
}




//WEB自动化相关
//测试步骤参数
var WEB_STEP_PARAMETER = {
    "dataType":{
        "string":{
            "dataType":true,
            "text":"字符串常量",
            "example":"xuwangcheng14@163.com",
            "mark":"普通的字符串，根据操作类型不同可能代表不同含义,请根据操作类型的说明来填写."
        },
        "keyboard":{
            "dataType":true,
            "text":"键盘组合按键",
            "example":"Keys.ENTER 模拟键盘Enter按键",
            "mark":"模拟键盘按键,支持组合按键,常用键如下:<br>回车键 Keys.ENTER<br>删除键 Keys.BACK_SPACE<br>空格键 Keys.SPACE<br>制表键 Keys.TAB<br>回退键 Keys.ESCAPE<br>刷新键 Keys.F5<br>更多按键说明请参考说明文档!"
        },
        "globalVariable":{
            "dataType":true,
            "text":"全局变量",
            "example":"${__current_timestamp}",
            "mark":"等同于字符串常量,但是需要在<span class='label label-primary radius'> 全局变量  </span>模块中提前定义."
        },
        "regexp":{
            "dataType":true,
            "text":"正则表达式",
            "example":"[0-9]{1,3}",
            "mark":"根据正则表达式来生成指定的字符串."
        },
        "attribute":{
            "dataType":true,
            "text":"元素属性/文本值",
            "example":"name",
            "mark":"获取选择的指定元素上的指定属性的值，其中text表示该元素的文本值."
        },
        "saveVariable":{
            "dataType":true,
            "text":"测试变量",
            "example":"order_id",
            "mark":"在本次测试中定义的、保存的上下文变量,如果有同名的变量，取值优先级如下:<br><span class='label label-success radius'>同个测试用例中不同步骤保存的变量</span> &gt; <span class='label label-success radius'>不同测试用例中步骤保存的值</span> &gt; <span class='label label-success radius'>测试集中定义的变量</span> &gt; <span class='label label-success radius'>测试用例中定义的变量</span>."
        },
        "dbSql":{
            "dataType":true,
            "text":"数据库取值",
            "example":"select order_id from order_info where order_type='0' and user_id=&lt;user_id&gt; and op_time&gt;${__current_date};",
            "mark":"可选择指定的数据源执行指定的SQL查询,如果返回多条记录,则只会取第一条内容,如果一条结果中包含多列也只会去第一列内容.<br>同时在SQL语句你可以使用<span class='label label-primary radius'> &lt;user_id&gt;  </span>格式来表示测试变量,同时可以使用全局变量."
        }

    },
    "opType":{
        "open":{
            "opType":true,
            "text":"打开",
            "mark":"打开一个正确的浏览器地址",
            "requiredData":"<span class='label label-danger radius'>可选</span> 如果填入该值，则会使用该处地址",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "check":{
            "opType":true,
            "text":"检查",
            "mark":"判断获取到的内容和预期值是否匹配",
            "requiredData":"<span class='label label-success radius'>需要</span> 该值可以为任何类型",
            "validateData":"<span class='label label-success radius'>需要</span> 该值可以为任何类型,如果该值类型为正则表达式,则会进行正则匹配来验证"
        },
        "action":{
            "opType":true,
            "text":"执行用例片段",
            "mark":"执行一个用例片段",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "script":{
            "opType":true,
            "text":"执行js脚本",
            "mark":"执行一段JS脚本",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "input":{
            "opType":true,
            "text":"输入",
            "mark":"向输入框输入文本内容",
            "requiredData":"<span class='label label-danger radius'>需要</span> 该值可为任何类型",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "save":{
            "opType":true,
            "text":"保存值",
            "mark":"获取内容并保存,可以获取窗口标题、当前浏览器地址、元素文本、元素属性等",
            "requiredData":"<span class='label label-danger radius'>可选</span> 当元素类型为TAG时,如果该值为空,默认取元素的文本内容;如果该值不为空,则该值为元素的属性名称",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "click":{
            "opType":true,
            "text":"点击",
            "mark":"点击元素",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "hover":{
            "opType":true,
            "text":"鼠标悬停",
            "mark":"将鼠标移动到某个元素上",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "alertCancel":{
            "opType":true,
            "text":"弹出框-取消",
            "mark":"对于系统级(非页面元素)的弹出框(包含警告框、确认框、对话框),进行取消操作",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "alertConfirm":{
            "opType":true,
            "text":"弹出框-确认",
            "mark":"对于系统级(非页面元素)的弹出框(包含确认框、对话框),进行确认操作",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "alertClose":{
            "opType":true,
            "text":"弹出框-关闭",
            "mark":"对于系统级(非页面元素)的弹出框(包含警告框、确认框、对话框),进行关闭操作",
            "requiredData":"<span class='label label-danger radius'>不需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "alertInput":{
            "opType":true,
            "text":"对话框-输入",
            "mark":"对于系统级(非页面元素)的对话框,进行输入操作，默认自动点击确认按钮",
            "requiredData":"<span class='label label-danger radius'>需要</span>",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "upload":{
            "opType":true,
            "text":"上传文件",
            "mark":"进行文件上传操作",
            "requiredData":"<span class='label label-danger radius'>需要</span> 该值为文件的绝对路径(文件应该位于执行机中而不是服务器上)",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        },
        "slide":{
            "opType":true,
            "text":"滑动",
            "mark":"拖动元素在 x，y 方向上移动移动一段距离",
            "requiredData":"<span class='label label-danger radius'>需要</span> 例如：x=500,y=100,代表在x/y轴上个拖动500px的距离",
            "validateData":"<span class='label label-danger radius'>不需要</span>"
        }
    }
}


function createWebSocket (path) {
    if (top.homeUrl == null || top.homeUrl == '') {
        return null;
    }
    let url = (top.homeUrl).replace('https', 'wss').replace('http', 'ws');
    let ws = null;
    try {
        if ('WebSocket' in window){
            ws = new WebSocket(url + path);
        }
        else if ('MozWebSocket' in window){
            ws = new MozWebSocket(url + path);
        }
        else{
            console.error("该浏览器不支持websocket");
            return null;
        }
    } catch (err) {
        console.error(err);
    }

    return ws;
}