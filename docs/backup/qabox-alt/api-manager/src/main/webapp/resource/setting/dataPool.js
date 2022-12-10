var templateParams = {
    tableTheads:["池名称", "池变量", "数据类别","创建时间", "创建用户", "备注", "操作"],
    btnTools:[{
        type:"success",
        id:"add-object",
        iconFont:"&#xe600;",
        name:"添加数据池"
    }, {
        type:"danger",
        size:"M",
        markClass:"batch-del-object",
        iconFont:"&#xe6e2;",
        name:"批量删除"
    }, {
        type:"primary",
        size:"M",
        id:"show-use-sample",
        iconFont:"&#xe6cd;",
        name:"如何使用?"
    }],
    formControls:[
        {
            edit:true,
            required:false,
            label:"&nbsp;&nbsp;ID",
            objText:"poolIdText",
            input:[{
                hidden:true,
                name:"poolId"
            }]
        },
        {
            edit:false,
            required:true,
            label:"池名称",
            input:[{
                name:"name"
            }]
        },
        {
            name:"createTime",
            value:new Date().Format("yyyy-MM-dd hh:mm:ss")
        },
        {
            name:"user.userId"
        },
        {
            edit:false,
            label:"&nbsp;&nbsp;备注",
            textarea:[{
                name:"mark"
            }]
        },
        {
            name:"projectInfo.projectId",
            value: top.currentProjectId
        }
    ]

};

var columnsSetting = [
    {
        "data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.name, data.poolId, "selectDataPool");
        }},
    {"data":"poolId"},{"data":"name"},
    {
        "data":"nameCount",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-name-count",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    {
        "data":"itemCount",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-item-count",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    ellipsisData("createTime"),ellipsisData("user.realName"),
    {
        "data":"mark",
        "className":"ellipsis",
        "render":function(data, type, full, meta ){
            if (data != "" && data != null) {
                return '<a href="javascript:;" onclick="showMark(\'' + full.name + '-\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
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
        publish.renderParams.editPage.objId = data.poolId;
        layer_show("编辑数据池信息", editHtml, editPageWidth, editPageHeight.edit, 1);
        publish.init();
    },
    ".object-del": function () {
        var data = table.row( $(this).parents('tr') ).data();
        delObj("警告：确认要删除此数据池吗（可能会导致相关测试集无法正常测试）？", REQUEST_URL.DATA_POOL.DEL, data.poolId, this);
    },
    "#add-object": function() {
        publish.renderParams.editPage.modeFlag = 0;
        layer_show("添加数据池信息", editHtml, editPageWidth, editPageHeight.add, 1);
        publish.init();
    },
    ".batch-del-object": function() {
        var checkboxList = $(".selectDataPool:checked");
        batchDelObjs(checkboxList, REQUEST_URL.DATA_POOL.DEL);
    },
    ".show-name-count": function () {
        var data = table.row($(this).parents('tr')).data();
        currIndex = layer_show(data.name + "-池变量管理", "poolDataName.html?poolId=" + data.poolId, null, null, 2, null, function() {
            refreshTable();
        });
    },
    ".show-item-count": function () {
        var data = table.row($(this).parents('tr')).data();
        currIndex = layer_show(data.name + "-池类别管理", "poolDataItem.html?poolId=" + data.poolId, null, null, 2, null, function() {
            refreshTable();
        });
    },
    "#show-use-sample": function () {
        layer_show("如何使用数据池？", htmls['showDataPoolUseSample'], 795, 550, 1);
    }
};

var mySetting = {
    eventList:eventList,
    editPage:{
        editUrl:REQUEST_URL.DATA_POOL.EDIT,
        getUrl:REQUEST_URL.DATA_POOL.GET,
        rules:{
            name:{
                required:true,
                minlength:2,
                maxlength:100
            }
        }
    },
    listPage:{
        listUrl:REQUEST_URL.DATA_POOL.LIST,
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 8]
    },
    templateParams:templateParams
};

$(function(){
    publish.renderParams = $.extend(true, publish.renderParams, mySetting);
    publish.init();
});