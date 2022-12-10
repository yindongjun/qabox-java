var templateParams = {
    tableTheads:["项目名称", "状态", "成员", "接口", "详情","创建时间", "创建用户", "备注", "操作"],
    btnTools:[{
        type:"success",
        id:"add-object",
        iconFont:"&#xe600;",
        name:"添加项目"
    }, {
        type:"danger",
        size:"M",
        markClass:"batch-del-object",
        iconFont:"&#xe6e2;",
        name:"批量删除"
    }],
    formControls:[
        {
            edit:true,
            required:false,
            label:"&nbsp;&nbsp;ID",
            objText:"projectIdText",
            input:[{
                hidden:true,
                name:"projectId"
            }]
        },
        {
            edit:false,
            required:true,
            label:"项目名称",
            input:[{
                name:"projectName"
            }]
        },
        {
            edit:false,
            required:true,
            label:"状态",
            select:[{
                name:"status",
                option:function() {
                  let options = [];
                  $.each(PROJECT_STATUS, function(k){
                      options.push({
                          value: k,
                          text: PROJECT_STATUS[k]
                      })
                  });
                  return options;
                }()
            }]
        },
        {
            edit:false,
            label:"&nbsp;&nbsp;详情",
            textarea:[{
                name:"details"
            }]
        },
        {
            name:"createTime",
            value:new Date().Format("yyyy-MM-dd hh:mm:ss")
        },
        {
            name:"createUser.userId"
        },
        {
            edit:false,
            label:"&nbsp;&nbsp;备注",
            textarea:[{
                name:"mark"
            }]
        }
    ]

};

var columnsSetting = [
    {
        "data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.projectName, data.projectId, "selectProject");
        }},
    {"data":"projectId"},{"data":"projectName"},
    {
        "data":"status",
        "render":function(data){
            return labelCreate(data, {
                "0":{
                    btnStyle:"default",
                    status:"未开始"
                },
                "1":{
                    btnStyle:"primary",
                    status:"设计中"
                },
                "2":{
                    btnStyle:"primary",
                    status:"开发中"
                },
                "3":{
                    btnStyle:"primary",
                    status:"测试中"
                },
                "4":{
                    btnStyle:"secondary",
                    status:"已上线"
                },
                "5":{
                    btnStyle:"secondary",
                    status:"验收测试"
                },
                "6":{
                    btnStyle:"success",
                    status:"已完成"
                },
                "7":{
                    btnStyle:"danger",
                    status:"禁用"
                }
            });
        }

    },
    {
        "data":"userNum",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-users",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    {
        "data":"interfaceNum",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-interfaces",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    {
        "data":"details",
        "className":"ellipsis",
        "render":function(data, type, full, meta ){
            if (data != "" && data != null) {
                return '<a href="javascript:;" onclick="showMark(\'' + full.projectName + '-\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
            }
            return "";
        }
    },
    ellipsisData("createTime"),ellipsisData("createUser.realName"),
    {
        "data":"mark",
        "className":"ellipsis",
        "render":function(data, type, full, meta ){
            if (data != "" && data != null) {
                return '<a href="javascript:;" onclick="showMark(\'' + full.projectName + '-\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
            }
            return "";
        }
    }
    ,{
        "data":null,
        "render":function (data) {
            var context = [{
                title:"编辑",
                markClass:"object-edit",
                iconFont:"&#xe6df;"
            },{
                title:"删除",
                markClass:"object-del",
                iconFont:"&#xe6e2;"
            }];
            return btnIconTemplate(context);
        }
    }
];

var eventList = {
    ".object-edit": function () {
        var data = table.row( $(this).parents('tr') ).data();
        publish.renderParams.editPage.modeFlag = 1;
        publish.renderParams.editPage.objId = data.projectId;
        layer_show("编辑项目信息", editHtml, editPageWidth, editPageHeight.edit, 1);
        publish.init();
    },
    ".object-del": function () {
        var data = table.row( $(this).parents('tr') ).data();
        delObj("警告：确认要删除此项目信息吗？", REQUEST_URL.PROJECT_INFO.DEL, data.projectId, this);
    },
    "#add-object": function() {
        console.log(editPageHeight)
        publish.renderParams.editPage.modeFlag = 0;
        layer_show("添加项目信息", editHtml, editPageWidth, editPageHeight.add, 1);
        publish.init();
    },
    ".batch-del-object": function() {
        var checkboxList = $(".selectProject:checked");
        batchDelObjs(checkboxList, REQUEST_URL.PROJECT_INFO.DEL);
    },
    ".show-users": function () {
        var data = table.row($(this).parents('tr')).data();
        currIndex = layer_show(data.projectName + "-成员管理", "projectUser.html?projectId=" + data.projectId, null, null, 2, null, function() {
            refreshTable();
        });
    },
    ".show-interfaces": function () {
        var data = table.row($(this).parents('tr')).data();
        currIndex = layer_show(data.projectName + "-接口管理", "projectInterface.html?projectId=" + data.projectId, null, null, 2, null, function() {
            refreshTable();
        });
    }
};

var mySetting = {
    eventList:eventList,
    editPage:{
        editUrl:REQUEST_URL.PROJECT_INFO.EDIT,
        getUrl:REQUEST_URL.PROJECT_INFO.GET,
        rules:{
            projectName:{
                required:true,
                minlength:2,
                maxlength:100
            },
            status:{
                required:true
            }
        }
    },
    listPage:{
        listUrl:REQUEST_URL.PROJECT_INFO.LIST,
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 6, 9, 10]
    },
    templateParams:templateParams
};

$(function(){
    publish.renderParams = $.extend(true, publish.renderParams, mySetting);
    publish.init();
});