var poolId;

var templateParams = {
    tableTheads:["池名称", "变量名", "key", "默认值","创建时间", "创建用户", "备注", "操作"],
    btnTools:[{
        type:"success",
        id:"add-object",
        iconFont:"&#xe600;",
        name:"添加池变量"
    }, {
        type:"primary",
        size:"M",
        iconFont:"&#xe667;",
        name:"批量操作",
        children: [
            {
                name: '批量删除',
                id: 'batch-del-object'
            },
            {
                name: '批量新增',
                id: 'batch-add-object'
            }
        ]
    }],
    formControls:[
        {
            edit:true,
            required:false,
            label:"&nbsp;&nbsp;ID",
            objText:"idText",
            input:[{
                hidden:true,
                name:"id"
            }]
        },
        {
            edit:false,
            required:true,
            label:"池变量名称",
            input:[{
                name:"name"
            }]
        },
        {
            edit:false,
            reminder: "指定池类别中未设置值的变量将会使用该变量的默认值",
            required:false,
            label:"默认值",
            input:[{
                name:"defaultValue"
            }]
        },
        {
            name:"dataPool.poolId"
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
        }
    ]

};

var columnsSetting = [
    {
        "data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.name, data.id, "selectDataPoolName");
        }},
    {"data":"id"},
    ellipsisData("dataPool.name"),
    ellipsisData("name"),
    ellipsisData("useKey"),
    ellipsisData("defaultValue"),
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
        publish.renderParams.editPage.objId = data.id;
        layer_show("编辑池变量信息", editHtml, editPageWidth, editPageHeight.edit, 1);
        publish.init();
    },
    ".object-del": function () {
        var data = table.row( $(this).parents('tr') ).data();
        delObj("警告：确认要删除此池变量吗（会同时删除数据池下不同数据类别中的该变量值）？", REQUEST_URL.POOL_DATA_NAME.DEL, data.id, this);
    },
    "#add-object": function() {
        publish.renderParams.editPage.modeFlag = 0;
        layer_show("添加池变量信息", editHtml, editPageWidth, editPageHeight.add, 1);
        publish.init();
    },
    "#batch-del-object": function() {
        var checkboxList = $(".selectDataPoolName:checked");
        batchDelObjs(checkboxList, REQUEST_URL.POOL_DATA_NAME.DEL);
    },
    "#batch-add-object": function () {
        layer.prompt({
            formType : 2,
            value : '',
            maxlength:999999,
            title : '请输入要添加池变量名称，多个请换行输入',
            area : [ '500px', '300px' ]
        }, function (value, index, elem) {
            $.post(REQUEST_URL.POOL_DATA_NAME.BATCH_ADD, {name: value, poolId: poolId}, function (json) {
                if (json.returnCode == 0) {
                    if (json.data.existNames.length > 0) {
                        layer.alert("成功添加变量名共" + json.data.successCount + "个。</br>存在以下重复名称未添加：<br>" + json.data.existNames.join("<br>"), {icon: 0});
                    } else {
                        layer.close(index);
                        layer.msg("批量新增成功，共" + json.data.successCount + "个。", {icon: 1, time:1600});
                    }
                    if (json.data.successCount > 0) {
                        refreshTable();
                    }
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
        poolId = GetQueryString("poolId");
        publish.renderParams.listPage.listUrl = REQUEST_URL.POOL_DATA_NAME.LIST + "?poolId=" + poolId;
        df.resolve();
    },
    editPage:{
        editUrl:REQUEST_URL.POOL_DATA_NAME.EDIT,
        getUrl:REQUEST_URL.POOL_DATA_NAME.GET,
        beforeInit:function(df){
            $("#dataPool\\.poolId").val(poolId);

            df.resolve();
        },
        rules:{
            name:{
                required:true,
                minlength:2,
                maxlength:100,
                remote:{
                    url:REQUEST_URL.POOL_DATA_NAME.CHECK_NAME,
                    type:"post",
                    dataType: "json",
                    data: {
                        name: function() {
                            return $("#name").val();
                        },
                        poolId:function(){
                            return poolId;
                        },
                        id:function () {
                            return $("#id").val()
                        }
                    }}
            }
        }
    },
    listPage:{
        listUrl:REQUEST_URL.POOL_DATA_NAME.LIST,
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 8],
        dtOtherSetting:{
            //serverSide:false
        }
    },
    templateParams:templateParams
};

$(function(){
    publish.renderParams = $.extend(true, publish.renderParams, mySetting);
    publish.init();
});