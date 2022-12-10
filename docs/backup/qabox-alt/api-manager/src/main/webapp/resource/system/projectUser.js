var projectId;
var mode = 0; //0-管理  1-添加

var templateParams = {
    tableTheads:["用户名", "姓名", "角色", "当前状态", "最近登录", "创建时间", "操作"],
    btnTools:[{
        type:"primary",
        size:"M",
        id:"manger-user",
        iconFont:"&#xe60c;",
        name:"管理成员"
    },{
        size:"M",
        id:"add-user",
        iconFont:"&#xe600;",
        name:"添加成员"
    },{
        size:"M",
        id:"batch-op",
        iMarkClass:"Hui-iconfont-del3",
        name:"批量操作"
    }]
};

var columnsSetting = [
    {"data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.username + '-' + data.realName, data.userId, "selectUser");
        }},
    {"data":"userId"},
    {"data":"username"},
    {"data":"realName"},
    {"data":"role.roleName"},
    {
        "data":"status",
        "render":function(data, type, full, meta ){
            var bstatus;
            var btnstyle;
            switch(data)
            {
                case "0":
                    bstatus = "正常";
                    btnstyle = "success";
                    break;
                case "1":
                    bstatus = "锁定";
                    btnstyle = "danger";
                    break;
            }
            return htmlContent = '<span class="label label-' + btnstyle + ' radius">' + bstatus + '</span>';
        }
    },
    {
        "data":"lastLoginTime",
        "className":"ellipsis",
        "render":CONSTANT.DATA_TABLES.COLUMNFUN.ELLIPSIS

    },
    {
        "data":"createTime",
        "className":"ellipsis",
        "render":CONSTANT.DATA_TABLES.COLUMNFUN.ELLIPSIS
    },
    {
        "data":null,
        "render":function(data, type, full, meta){
            var context = [];
            //管理-删除
            if (mode == 0) {
                context.push({
                    title:"删除",
                    markClass:"op-user",
                    iconFont:"&#xe6e2;"
                });
            }

            //添加
            if (mode == 1) {
                context.push({
                    title:"添加",
                    markClass:"op-user",
                    iconFont:"&#xe600;"
                });
            }
            return btnIconTemplate(context);
        }}
];


var eventList = {
    "#batch-op":function() { //批量操作 删除或者添加
        var checkboxList = $(".selectUser:checked");
        var opName = "删除";
        if (mode == 1) {
            opName = "添加";
        }
        batchDelObjs(checkboxList, REQUEST_URL.PROJECT_INFO.OP_USER + "?mode=" + mode + "&projectId=" + projectId, table, opName)
    },
    "#manger-user":function() { //管理项目 - 从项目中删除
        var that = this;
        mode = 0;
        refreshTable(REQUEST_URL.PROJECT_INFO.LIST_USERS + "?mode=" + mode + "&userProjectId=" + projectId, function(json) {
            $(that).addClass('btn-primary').siblings("#add-user").removeClass('btn-primary');
        }, null, true);
        $("#batch-op").children("i").removeClass("Hui-iconfont-add").addClass("Hui-iconfont-del3");
    },
    "#add-user":function() { //添加
        var that = this;
        mode = 1;
        refreshTable(REQUEST_URL.PROJECT_INFO.LIST_USERS + "?mode=" + mode + "&userProjectId=" + projectId, function(json) {
            $(that).addClass('btn-primary').siblings("#manger-user").removeClass('btn-primary');
        }, null, true);
        $("#batch-op").children("i").removeClass("Hui-iconfont-del3").addClass("Hui-iconfont-add");
    },
    ".op-user":function() {//单条删除或者添加
        var tip = '删除';

        if (mode == 1) {
            tip = "添加";
        }

        var data = table.row( $(this).parents('tr') ).data();
        var that = this;
        layer.confirm('确定要' + tip + '此用户吗?', {icon:0, title:'警告'}, function(index) {
            layer.close(index);
            $.get(REQUEST_URL.PROJECT_INFO.OP_USER + "?mode=" + mode + "&projectId=" + projectId + "&userId=" + data.userId, function(json) {
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
        publish.renderParams.listPage.listUrl = REQUEST_URL.PROJECT_INFO.LIST_USERS + "?mode=" + mode + "&userProjectId=" + projectId;
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

/****************************************************************/
