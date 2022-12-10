var projectId;
var mode = 0; //0-管理  1-复制

var templateParams = {
    tableTheads:["名称", "中文名", "类型", "协议", "状态", "报文数量", "所属项目", "备注", "操作"],
    btnTools:[{
        type:"primary",
        size:"M",
        id:"manger-interface",
        iconFont:"&#xe60c;",
        name:"管理接口"
    },{
        size:"M",
        id:"add-interface",
        iconFont:"&#xe687;",
        name:"复制接口"
    },{
        size:"M",
        id:"batch-op",
        iMarkClass:"Hui-iconfont-del3",
        name:"批量操作"
    },{
        size:"M",
        type:"success",
        id:"copy-params",
        iconFont:"&#xe61d;",
        name:"配置复制参数"
    }]
};

var columnsSetting = [
    {
        "data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.interfaceName+'-'+data.interfaceCnName,data.interfaceId,"selectInterface");
        }},
    {"data":"interfaceId"},
    {
        "className":"ellipsis",
        "data":"interfaceName",
        "render":CONSTANT.DATA_TABLES.COLUMNFUN.ELLIPSIS
    },
    {
        "className":"ellipsis",
        "data":"interfaceCnName",
        "render":CONSTANT.DATA_TABLES.COLUMNFUN.ELLIPSIS
    },
    {
        "data":"interfaceType",
        "render":function(data, type, full, meta){
            var option = {
                "SL":{
                    btnStyle:"warning",
                    status:"受理类"
                },
                "CX":{
                    btnStyle:"success",
                    status:"查询类"
                }
            };
            return labelCreate(data, option);
        }},
    {
        "data":"interfaceProtocol",
        "render":function(data) {
            return labelCreate(data.toUpperCase());
        }
    },
    {
        "data":"status",
        "render":function(data, type, full, meta ){
            return labelCreate(data);
        }},
    {
        "data":"messagesNum",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"primary",
                    size:"M",
                    markClass:"show-interface-messages",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    ellipsisData("projectInfo.projectName"),
    {
        "data":"mark",
        "className":"ellipsis",
        "render":function(data, type, full, meta) {
            if (data != "" && data != null) {
                return '<a href="javascript:;" onclick="showMark(\'' + full.interfaceName + '\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
            }
            return "";
        }
    },
    {
        "data":null,
        "render":function(data, type, full, meta){
            var context = [];
            //管理-删除
            if (mode == 0) {
                context.push({
                    title:"删除",
                    markClass:"op-interface",
                    iconFont:"&#xe6e2;"
                });
            }
            //复制
            if (mode == 1) {
                context.push({
                    title:"复制",
                    markClass:"op-interface",
                    iconFont:"&#xe687;"
                });
            }
            return btnIconTemplate(context);
        }}
];

var copyParams = {
    "接口探测": "0",
    "性能测试": "1",
    "定时任务": "2",
    "测试报告": "3"
};

var selectCopyParams = [];

var eventList = {
    "#copy-params": function() { // 复制参数配置
        layerMultipleChoose({
            title:"请选择复制接口时关联复制的内容(可多选)",
            layerWidth:510,
            layerHeight:187,
            simpleData: copyParams,
            choosedValues:selectCopyParams,//已被选择的数据合集
            closeLayer:true,//是否在确认之后自动关闭窗口
            maxChooseCount:99,
            confirmCallback:function (chooseValues, chooseObjects, index) {
                selectCopyParams = chooseValues;
            } //选择之后的回调
        });
    },
    "#batch-op":function() { //批量操作 删除或者添加
        var checkboxList = $(".selectInterface:checked");
        var opName = "删除";
        if (mode == 1) {
            opName = "复制";
        }
        batchDelObjs(checkboxList, REQUEST_URL.PROJECT_INFO.OP_INTERFACE + "?mode=" + mode + "&projectId=" + projectId + "&copyParams=" + selectCopyParams.join(","), table, opName)

    },
    "#manger-interface":function() { //管理项目 - 从项目中删除
        var that = this;
        mode = 0;
        refreshTable(REQUEST_URL.PROJECT_INFO.LIST_INTERFACES + "?mode=" + mode + "&userProjectId=" + projectId, function(json) {
            $(that).addClass('btn-primary').siblings("#add-interface").removeClass('btn-primary');
        }, null, true);
        $("#batch-op").children("i").removeClass("Hui-iconfont-quanbudingdan").addClass("Hui-iconfont-del3");
    },
    "#add-interface":function() { //添加
        var that = this;
        mode = 1;
        refreshTable(REQUEST_URL.PROJECT_INFO.LIST_INTERFACES + "?mode=" + mode + "&userProjectId=" + projectId, function(json) {
            $(that).addClass('btn-primary').siblings("#manger-interface").removeClass('btn-primary');
        }, null, true);
        $("#batch-op").children("i").removeClass("Hui-iconfont-del3").addClass("Hui-iconfont-quanbudingdan");
    },
    ".op-interface":function() {//单条删除或者添加
        var tip = '删除';

        if (mode == 1) {
            tip = "复制";
        }

        var data = table.row( $(this).parents('tr') ).data();
        var that = this;
        layer.confirm('确定要' + tip + '此接口吗?', {icon:0, title:'提示'}, function(index) {
            layer.close(index);
            $.get(REQUEST_URL.PROJECT_INFO.OP_INTERFACE + "?mode=" + mode + "&projectId=" + projectId + "&interfaceId=" + data.interfaceId + "&copyParams=" + selectCopyParams.join(",")
                , function(json)
                    {
                        if (json.returnCode == 0) {
                            table.row($(that).parents('tr')).remove().draw();
                            layer.msg(tip + '成功!', {icon:1, time:1500});
                        } else {
                            layer.alert(json.msg, {icon:5});
                        }
                     });
        });
    }
};


var mySetting = {
    eventList:eventList,
    templateCallBack:function(df){
        projectId = GetQueryString("projectId");
        publish.renderParams.listPage.listUrl = REQUEST_URL.PROJECT_INFO.LIST_INTERFACES + "?mode=" + mode + "&userProjectId=" + projectId;
        df.resolve();
    },
    listPage:{
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 8],
        dtOtherSetting:{
            bStateSave: false
        },
        exportExcel:false
    },
    templateParams:templateParams
};


$(function(){
    publish.renderParams = $.extend(true,publish.renderParams,mySetting);
    publish.init();
});
