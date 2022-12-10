var poolId;

var templateParams = {
    tableTheads:["池名称", "池类别名称", "数据请求地址", "接口场景", "变量值", "自动更新","创建时间", "创建用户", "备注", "操作"],
    btnTools:[{
        type:"success",
        id:"add-object",
        iconFont:"&#xe600;",
        name:"添加池类别"
    }, {
        type:"primary",
        size:"M",
        iconFont:"&#xe667;",
        name:"批量操作",
        children: [
            {
                name: '批量新增',
                id: 'batch-add-object'
            },
            {
                name: '批量删除',
                id: 'batch-del-object'
            },
            {
                name: '批量获取数据',
                id: 'batch-request-data'
            }
        ]
    }, {
        type:"success",
        size:"M",
        id:"download-item-name",
        iconFont:"&#xe641;",
        name:"下载数据模板"
    }, {
        type:"warning",
        size:"M",
        id:"upload-item-value",
        iconFont:"&#xe642;",
        name:"上传池数据"
    }, {
        type:"primary",
        size:"M",
        id:"show-json-data",
        iconFont:"&#xe6cd;",
        name:"JSON格式数据示例"
    }],
    formControls:[
        {
            edit:true,
            required:false,
            label:"&nbsp;&nbsp;ID",
            objText:"itemIdText",
            input:[{
                hidden:true,
                name:"itemId"
            }]
        },
        {
            edit:false,
            required:true,
            label:"池类别名称",
            input:[{
                name:"name"
            }]
        },
        {
            edit:false,
            required:false,
            reminder: "指定该类数据变量通过接口获取的请求地址，该接口必须为GET请求，且必须返回JSON格式才能正确解析数据。<br>详细返回示例请点击工具栏按钮 \"JSON数据示例\"  查看",
            label:"数据获取地址",
            input:[{
                name:"requestUrl"
            }]
        },
        {
            edit:false,
            required:false,
            reminder: "选择一个接口测试场景，通过该场景去获取该类别的池数据，灵活性比只配置数据获取地址高很多",
            label:"接口场景",
            input:[{
                name:"messageScene.messageSceneId",
                hidden:true
            }],
            button:[{
                style:"success",
                value:"选择",
                name:"choose-message-scene"
            }]
        },
        {
            edit:false,
            required:false,
            label:"场景测试环境",
            input:[{
                name:"sceneSystem.systemId",
                hidden:true
            }],
            button:[{
                style:"success",
                value:"选择",
                name:"choose-scene-system"
            }]
        },
        {
            edit:false,
            required:false,
            reminder: "通过RequestUrl或MessageScene获取的JSON数据中，本类别数据所在返回的JSON_PATH路径，例如：ROOT.OUT_DATA.DATA。如不填写，则默认返回的数据全部为所需更新的JSON数据。",
            label:"数据获取JSON_PATH",
            input:[{
                name:"responseDataJsonPath"
            }]
        },
        {
            edit:false,
            required:false,
            reminder: "优先使用配置的接口场景获取数据，如果未配置接口场景才会使用配置的数据获取地址来请求，反之类似",
            label:"优先使用场景获取数据",
            select:[{
                name:"useMessageScene",
                option:[{value:"1", text:"是", selected:true}, {value:"0", text:"否"}]
            }]
        },
        {
            edit:false,
            required:false,
            reminder: "在使用前自动更新本类别数据，前提是必须配置了requestUrl或者messageScene",
            label:"自动更新数据",
            select:[{
                name:"beforeUseAutoUpdate",
                option:[{value:"1", text:"是"}, {value:"0", text:"否", selected:true}]
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
            name:"protocolType"
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
            return checkboxHmtl(data.name, data.itemId, "selectDataPoolItem");
        }},
    {"data":"itemId"},
    ellipsisData("dataPool.name"),
    ellipsisData("name"),
    ellipsisData("requestUrl"),
    {
        "data":"messageScene",
        "className":"ellipsis",
        "render":function (data) {
            if (data == null) {
                return "";
            }
            return '<span title="' + data.interfaceName + ',' + data.messageName + ',' + data.sceneName + '">' + data.interfaceName + '</span>';
        }
    },
    {
        "data":"valueCount",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-value-count",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    {
        "data":"beforeUseAutoUpdate",
        "render":function(data, type, full, meta ){
            var option = {
                "0":{
                    btnStyle:"danger",
                    status:"否"
                },
                "1":{
                    btnStyle:"success",
                    status:"是"
                }
            };
            return labelCreate(data, option);
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
                title:"通过接口获取数据",
                markClass:"send-request",
                iconFont:"&#xe603;"
            },{
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
        publish.renderParams.editPage.objId = data.itemId;
        layer_show("编辑池类别信息", editHtml, editPageWidth, editPageHeight.edit, 1);
        publish.init();
    },
    ".object-del": function () {
        var data = table.row( $(this).parents('tr') ).data();
        delObj("警告：确认要删除此池类别吗（会同时删除该类别下所有已设置的变量值）？", REQUEST_URL.POOL_DATA_ITEM.DEL, data.itemId, this);
    },
    "#add-object": function() {
        publish.renderParams.editPage.modeFlag = 0;
        layer_show("添加池类别信息", editHtml, editPageWidth, editPageHeight.add, 1);
        publish.init();
    },
    "#batch-del-object": function() {
        var checkboxList = $(".selectDataPoolItem:checked");
        batchDelObjs(checkboxList, REQUEST_URL.POOL_DATA_ITEM.DEL);
    },
    "#batch-add-object": function () {
        layer.prompt({
            formType : 2,
            value : '',
            maxlength:999999,
            title : '请输入要添加池种类名称，多个请换行输入',
            area : [ '500px', '300px' ]
        }, function (value, index, elem) {
            $.post(REQUEST_URL.POOL_DATA_ITEM.BATCH_ADD, {name: value, poolId: poolId}, function (json) {
                if (json.returnCode == 0) {
                    if (json.data.existNames.length > 0) {
                        layer.alert("成功添加类别共" + json.data.successCount + "个。</br>存在以下重复名称未添加：<br>" + json.data.existNames.join("<br>"), {icon: 0});
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
    },
    // 批量请求数据
    '#batch-request-data': function () {
        batchOp($(".selectDataPoolItem:checked"), REQUEST_URL.POOL_DATA_ITEM.UPDATE_VALUE_BY_REQUEST, "更新", null, "itemId");
    },
    "#download-item-name": function () {
        $.get(REQUEST_URL.POOL_DATA_ITEM.EXPORT_VALUE_TEMPLATE, {poolId: poolId}, function(json) {
            if (json.returnCode == 0) {
                window.open("../../" + json.data)
            } else {
                layer.alert(json.msg, {icon:5});
            }
        })
    },
    // 展示设置类别的变量值
    ".show-value-count": function () {
        var data = table.row( $(this).parents('tr') ).data();
        $.get(REQUEST_URL.POOL_DATA_ITEM.LIST_NAME_VALUE, {poolId: poolId, itemId: data.itemId}, function (json) {
            if (json.returnCode == 0) {
                layer_show("设置变量值-" + data.name, templates["pool-data-item-name-value"]({objects: json.data, itemId: data.itemId}), 630, 600, 1, function(layero, index) {
                }, null, function() {
                    //refreshTable();
                });
            } else {
                layer.alert(json.msg, {icon:5});
            }
        });
    },
    // 保存设置的变量值
    "#save-item-name-value": function () {
        $.get(REQUEST_URL.POOL_DATA_ITEM.SAVE_NAME_VALUE, $("#form-pool-item-name-value").serialize(), function(json) {
            if (json.returnCode == 0) {
                layer.close($("#form-pool-item-name-value").find("#layerIndex").val())
            } else {
                layer.alert(json.msg, {icon:5});
            }
        })
    },
    // 发送请求更新数据
    ".send-request": function () {
        var data = table.row( $(this).parents('tr') ).data();
        if ((data.requestUrl == null || data.requestUrl == '') && data.messageScene == null) {
            layer.msg("未配置请求地址或者接口场景", {icon: 0, time:1600})
            return false;
        }
        layer.confirm('确定通过请求获取更新该类别的池数据吗?', {title:'提示', icon:0}, function(index) {
            layer.close(index);
            var loadIndex = layer.msg('正在请求数据中...', {icon:16, time:99999, shade:0.4});
            $.get(REQUEST_URL.POOL_DATA_ITEM.UPDATE_VALUE_BY_REQUEST, {itemId: data.itemId}, function (json) {
                layer.close(loadIndex)
                if (json.returnCode == 0) {
                    layer.msg("更新成功！", {icon: 1, time:1500})
                } else {
                    layer.alert(json.msg, {icon:5});
                }
            });
        });
    },
    // 查看JSON格式数据示例
    "#show-json-data": function () {
        $.get(REQUEST_URL.DATA_POOL.SHOW_JSON_DATA, {poolId: poolId}, function (json) {
            if (json.returnCode == 0) {
                createViewWindow(json.data, {
                    title: "JSON数据示例",
                    width:840,//宽度
                    height:470,//高度
                    copyBtn:true//是否显示复制按钮
                });
            } else {
                layer.alert(json.msg, {icon:5});
            }
        });
    },
    // 选择接口场景
    "#choose-message-scene":function () {
        layer_show("选择接口场景", "../advanced/chooseMessageScene.html?callbackFun=chooseScene&notMultiple=true", null, null, 2);
    },
    //选择接口场景的测试环境
    "#choose-scene-system":function () {
        if (!strIsNotEmpty($("#protocolType").val())) {
            layer.msg("请先选择接口场景!", {icon:5, time:1800});
            return false;
        }
        $.post(REQUEST_URL.BUSINESS_SYSTEM.LIST_ALL, {protocolType:$("#protocolType").val()}, function (json) {
            if (json.returnCode == 0) {
                if (json.data.length < 1) {
                    layer.msg('无该接口场景可用的测试环境供选择', {icon:0, time:1800});
                    return false;
                }
                layerMultipleChoose({
                    title:"请选择测试环境(最多选择一个)",
                    customData:{//自定义数据，Array数组对象
                        enable:true,
                        data:json.data,
                        textItemName:["systemName", "protocolType"],
                        valueItemName:"systemId"
                    },
                    choosedValues:$("#sceneSystem\\.systemId").val().split(","),//已被选择的数据合集
                    closeLayer:true,//是否在确认之后自动关闭窗口
                    maxChooseCount:1,
                    minChooseCount:1,
                    confirmCallback:function (chooseValues, chooseObjects, index) {
                        $("#choose-scene-system").siblings('span').remove();
                        $.each(chooseObjects, function (i, n) {
                            $("#choose-scene-system").before("<span>" + n.systemName + "&nbsp;&nbsp;</span>");
                        });
                        $("#sceneSystem\\.systemId").val(chooseValues[0]);
                    } //选择之后的回调
                });
            } else {
                layer.alert(json.msg, {icon:5});
            }
        });
    }
};

var mySetting = {
    eventList:eventList,
    templateCallBack:function(df){
        poolId = GetQueryString("poolId");
        publish.renderParams.listPage.listUrl = REQUEST_URL.POOL_DATA_ITEM.LIST + "?poolId=" + poolId;
        createLayerUpload();
        df.resolve();
    },
    editPage:{
        editUrl:REQUEST_URL.POOL_DATA_ITEM.EDIT,
        getUrl:REQUEST_URL.POOL_DATA_ITEM.GET,
        beforeInit:function(df){
            $("#dataPool\\.poolId").val(poolId);

            df.resolve();
        },
        renderCallback:function (obj) {
            if (obj.messageScene != null) {
                $("#protocolType").val(obj.messageScene.protocolType);
                $("#choose-message-scene").before("<span>" + obj.messageScene.interfaceName + "-"
                    + obj.messageScene.messageName + "-" + obj.messageScene.sceneName + "&nbsp;&nbsp;</span>");
            }
            if (obj.sceneSystem != null) {
                $("#choose-scene-system").before("<span>" + obj.sceneSystem.systemName + "&nbsp;&nbsp;</span>");
            }
        },
        rules:{
            name:{
                required:true,
                minlength:2,
                maxlength:100,
                remote: {
                    url:REQUEST_URL.POOL_DATA_ITEM.CHECK_NAME,
                    type:"post",
                    dataType: "json",
                    data: {
                        name: function() {
                            return $("#name").val();
                        },
                        poolId:function(){
                            return poolId;
                        },
                        itemId:function () {
                            return $("#itemId").val()
                        }
                    }}
            }
        }
    },
    listPage:{
        listUrl:REQUEST_URL.POOL_DATA_ITEM.LIST,
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 11],
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


function createLayerUpload () {
    var loadIndex;
    layui.use('upload', function(){
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#upload-item-value' //绑定元素
            ,url: REQUEST_URL.FILE.UPLOAD_FILE //上传接口
            ,accept:"file"
            ,exts:"xlsx|xls"
            ,data: {type: "0"}
            ,size:"102400"
            ,drag:false
            ,before:function(obj) {
                loadIndex = layer.msg('正在上传文件中...', {icon:16, time:99999, shade:0.4});
            }
            ,done: function(res){
                layer.close(loadIndex);
                //上传完毕回调
                if (res.returnCode == 0) {//上传成功
                    loadIndex = layer.msg('正在导入数据...', {icon:16, time:99999, shade:0.4});
                    $.post(REQUEST_URL.POOL_DATA_ITEM.IMPORT_ITEM_VALUE, {path: res.data.path, poolId: poolId}, function (json) {
                        layer.close(loadIndex);
                        if (json.returnCode == 0) {
                            layer.msg('上传成功,请查看列表', {icon:1, time:1800});
                            refreshTable();
                        } else {
                            layer.alert(json.msg, {icon:5});
                        }
                    });
                } else {
                    layer.alert(res.msg, {icon:5});
                }
            }
        });
    });
}

/**
 * 选择测试场景之后的回调
 * @param obj
 * @returns {Boolean}
 */
function chooseScene (obj) {
    if (obj == null) {
        return false;
    }


    $("#choose-message-scene").siblings('span').remove();
    $("#messageScene\\.messageSceneId").val(obj.messageSceneId);
    $("#choose-message-scene").before("<span>" + obj.interfaceName + "-"
        + obj.messageName + "-" + obj.sceneName + "&nbsp;&nbsp;</span>");

    var protocolType = obj.protocolType;
    if (protocolType != $("#protocolType").val()) {
        $("#protocolType").val(protocolType);
        $("#choose-scene-system").siblings('span').remove();
        $("#sceneSystem\\.systemId").val('');
    }

}