var templateParams = {
    tableTheads:["名称", "包含场景", "创建时间", "独立客户端测试","备注", "操作"],
    btnTools:[]
};

var columnsSetting = [
    {
        "data":null,
        "render":function(data, type, full, meta){
            return checkboxHmtl(data.complexSceneName, data.id, "selectComplexScene");
        }},
    {"data":"id"},
    ellipsisData("complexSceneName"),
    {
        "data":"sceneNum",
        "render":function(data, type, full, meta){
            var context =
                [{
                    type:"default",
                    size:"M",
                    markClass:"show-scenes",
                    name:data
                }];
            return btnTextTemplate(context);
        }
    },
    ellipsisData("createTime"),
    {
        "data":"newClient",
        "render":function(data){
            var option = {
                "0":{
                    btnStyle:"success",
                    status:"是"
                },
                "1":{
                    btnStyle:"default",
                    status:"否"
                },
                "default":{
                    btnStyle:"default",
                    status:"否"
                }

            };
            return labelCreate(data, option);
        }
    },
    {
        "data":"mark",
        "className":"ellipsis",
        "render":function(data, type, full, meta) {
            if (data != "" && data != null) {
                return '<a href="javascript:;" onclick="showMark(\'' + full.complexSceneName + '\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
            }
            return "";
        }
    },
    {
        "data":null,
        "render":function(data, type, full, meta){
            return btnIconTemplate(
                [{
                    title:"组合场景测试",
                    markClass:"object-test",
                    iconFont:"&#xe603;"
                },{
                    title:"选择",
                    markClass:"choose-this-scene",
                    iconFont:"&#xe6ab;"
                }]);
        }
    }
];
var eventList = {
    ".object-test":function(){//组合场景测试
        var data = table.row( $(this).parents('tr') ).data();
        layer.confirm('确认测试该组合场景吗？<br>组合场景的测试时间长度和包含的场景数有关,请耐心等待测试完成!', {title:'提示', icon:3}, function(index){
            var loadIndex = layer.msg('正在测试,请耐心等待...', {icon:16, time:9999999, shade:0.4});
            $.post(REQUEST_URL.AUTO_TEST.TEST_COMPLEX_SCENE_URL, {id:data.id}, function(json){
                layer.close(loadIndex);
                if (json.returnCode == 0 && json.data != null) {
                    var results = json.data;
                    if (json.data.length == undefined) {
                        results = json.data.complexSceneResults;
                    }
                    layer_show(data.complexSceneName + '-测试结果  点击查看对应详情', templates["complex-scene-results-view"]({results:results}), 600, 300, 1, function(layero, index){
                        layero.find('.result-view').bind('click', function(){
                            renderResultViewPage(results[$(this).attr("data-id")]);
                        });
                    }, function(index, layero) {
                        layero.find('.result-view').unbind('click');
                    }, null)
                } else {
                    layer.alert(strIsNotEmpty(json.msg) ? json.msg : '测试出错!', {icon:5});
                }
            });
            layer.close(index);
        });
    },
    ".show-scenes":function() { //管理组合中的测试场景
        var data = table.row( $(this).parents('tr') ).data();
        layer_show(data.complexSceneName + "-组合场景设定", "messageScene.html?complexSceneId=" + data.id, null, null, 2, null, null, function() {
            refreshTable();
        })
    },
    ".choose-this-scene": function() {
        var data = table.row( $(this).parents('tr') ).data();
        choosedCallBackFun(data);
        parent.layer.close(parent.layer.getFrameIndex(window.name));
    }
};

var choosedCallBackFun;
var mySetting = {
    eventList:eventList,
    templateCallBack:function(df){
        choosedCallBackFun = parent[GetQueryString("callbackFun")];
        df.resolve();
    },
    listPage:{
        listUrl:REQUEST_URL.COMPLEX_SCENE.LIST,
        tableObj:".table-sort",
        columnsSetting:columnsSetting,
        columnsJson:[0, 3, 6, 7],
        dtOtherSetting:{
            "bStateSave": false
        }
    },
    templateParams:templateParams
};

$(function(){
    publish.renderParams = $.extend(true,publish.renderParams,mySetting);
    publish.init();
});

/**********************************************************************************************************************/




