var messageSceneId;
var choosedCallBackFun;//成功选择之后的回调函数
var templateParams = {
		btnTools:[{
			type:"primary",
			size:"M",
			id:"batch-choose",
			iconFont:"&#xe667;",
			name:"批量选择"
		}],
		tableTheads:["接口", "协议","报文", "场景名", "测试数据", "验证规则", "出参示例", "创建时间","备注", "操作"]			
	};

var columnsSetting = [
                      {
                      	"data":null,
                      	"render":function(data, type, full, meta){                       
                              return checkboxHmtl(data.sceneName, data.messageSceneId, "selectScene");
                          }},
                      {"data":"messageSceneId"},
                      ellipsisData("interfaceName"),
                      {
				            "data":"protocolType",
				            "render":function(data) {
				            	return data == null ? '' : labelCreate(data.toUpperCase());
				            }
				     },
                      ellipsisData("messageName"),
                      ellipsisData("sceneName"),
                      {
                    	  "data":"testDataNum",
                          "render":function(data, type, full, meta){
                          	var context =
                          		[{
                        			type:"default",
                        			size:"M",
                        			markClass:"show-test-data",
                        			name:data
                        		}];
                              return btnTextTemplate(context);
                              }
            		    },  
            		    {
                      	  	"data":"rulesNum",
	                        "render":function(data, type, full, meta){
	                        	var context =
	                        		[{
	                      			type:"default",
	                      			size:"M",
	                      			markClass:"validate-method",
	                      			name:data
	                      		}];
	                            return btnTextTemplate(context);
	                            }
              		    },
              		   {
              			 "data":"responseExample",
              			 "render":function (data) {
              				var context =
                        		[{
                      			type:"primary",
                      			size:"M",
                      			markClass:"get-responseExample",
                      			name:"获取"
                      		}];
                            return btnTextTemplate(context);
              			 }
              		   },
              		   {
              		    	"data":"createTime",
              		    	"className":"ellipsis",
              		    	"render":function(data) {
              		    		if (strIsNotEmpty(data)) {
              		    			return '<span title="' + data + '">' + data + '</span>';
              		    		}
              		    		return "";
              		    	}
              		  },
                      {
            		    "data":"mark",
            		    "className":"ellipsis",
            		    "render":function(data, type, full, meta) { 
            		    	if (data != "" && data != null) {
                		    	return '<a href="javascript:;" onclick="showMark(\'' + full.sceneName + '\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
            		    	}
            		    	return "";
            		    }
                      },
                      {
                          "data":null,
                          "render":function(data, type, full, meta){
								return btnIconTemplate(
                        			[{
		                              	title:"场景测试",
		                  	    		markClass:"scene-test",
		                  	    		iconFont:"&#xe603;"
		                            },{
                                    	title:"选择",
                        	    		markClass:"choose-this-scene",
                        	    		iconFont:"&#xe6ab;"                           	
                                    }]);
                          }}
                  ];
var eventList = {
		"#batch-choose":function(){//批量选择
			let $checkbox = $(".selectScene:checked");
			if ($checkbox.length < 1) {
				return false;
			}
			let dataArr = [];
			let flag = true;
			$.each($checkbox, function(i, n){
			    let d = table.row( $(n).parents('tr') ).data();
                if (enabledProtocolTypes != null && enabledProtocolTypes.indexOf(d.protocolType) == -1) {
                    layer.alert('只能选择协议类型为' + enabledProtocolTypes.join('/') + '类型的接口场景，请重新选择!', {title:"提示", icon:5});
                    flag = false;
                    return false;
                }
				dataArr.push(d);
			});
			if (!flag) {
			    return;
            }
			choosedCallBackFun(dataArr);
			parent.layer.close(parent.layer.getFrameIndex(window.name));
		},
		".choose-this-scene":function() {//选择场景
			var data = table.row( $(this).parents('tr') ).data();
			if (enabledProtocolTypes != null && enabledProtocolTypes.indexOf(data.protocolType) == -1) {
                layer.alert('只能选择协议类型为' + enabledProtocolTypes.join('/') + '类型的接口场景，请重新选择!', {title:"提示", icon:5});
			    return;
            }
			choosedCallBackFun(data);
			parent.layer.close(parent.layer.getFrameIndex(window.name));
		},
		".scene-test":function() {//场景测试
			var data = table.row( $(this).parents('tr') ).data();
			messageSceneId = data.messageSceneId;
			layer_show(data.interfaceName + "-" + data.messageName + "-" + data.sceneName + "-测试", htmls["messageScene-test"], 1000, 520, 1, function() {
				renderSceneTestPage();				
			}, null, null, {shadeClose:false});
			
		},
		".validate-method":function() {//场景验证规则管理
			var data = table.row( $(this).parents('tr') ).data();
			messageSceneId = data.messageSceneId;
			
			layer_show (data.sceneName + "-验证规则管理", '../message/validateParameters.html?messageSceneId=' + messageSceneId, null, null, 2, null, null, function() {
				refreshTable();
			});						
		},
		".show-test-data":function() { //展示测试数据
			var data = table.row( $(this).parents('tr') ).data();	
			var title = data.interfaceName + "-" + data.messageName + "-" + data.sceneName + " " + "测试数据";
			var url = "../message/testData.html?messageSceneId=" + data.messageSceneId + "&sceneName=" + data.sceneName;
			
			layer_show (title, url, null, null, 2, null, null, function() {
				refreshTable();
			});
		},		
		".get-responseExample":function () {//获取返回示例报文
			var data = table.row( $(this).parents('tr') ).data();
			createViewWindow(data.responseExample, {
				title:data.sceneName + "-[出参示例]",
			});	
		}
};

var enabledProtocolTypes;
var mySetting = {
		eventList:eventList,
		templateCallBack:function(df) {
			choosedCallBackFun = parent[GetQueryString("callbackFun")];
			//是否禁止批量选择
			if (GetQueryString("notMultiple") == 'true') {
				$('#batch-choose').hide();
			}
			//指定查询的协议了类型
            let protocols = GetQueryString('protocolType');
			if (strIsNotEmpty(protocols)) {
			    enabledProtocolTypes = protocols.split(',');
            }
					
			df.resolve();
		},
		listPage:{
			listUrl:REQUEST_URL.MESSAGE_SCENE.LIST,
			tableObj:".table-sort",
			columnsSetting:columnsSetting,
			columnsJson:[0, 8, 10, 11],
			dblclickEdit:false,
			exportExcel:false,
			dtOtherSetting:{
				"stateSave": false
			}
		},
		templateParams:templateParams		
	};

$(function(){
	publish.renderParams = $.extend(true,publish.renderParams,mySetting);
	publish.init();
});

/**********************************************************************************************************************/
var customTestData = false;

/**
 * 自定义测试数据
 */
function changeTestData () {
    layer.prompt({
        formType: 2,
        value: $('#scene-test-request-message').val(),
        title: "自定义测试数据",
        offset: '30px',
        area: ['500px', '300px'] //自定义文本域宽高
    }, function(value, index, elem){
        if (value != $('#scene-test-request-message').val()) {
            $('#scene-test-request-message').val(value);
            customTestData = true;
        }
        layer.close(index);
    });
}
/**
 * 场景测试页面渲染
 */
function renderSceneTestPage(flag) {
	var index = layer.msg('加载中,请稍后...', {icon:16, time:60000, shade:0.35});
	$.get(REQUEST_URL.MESSAGE_SCENE.GET_TEST_OBJECT, {messageSceneId:messageSceneId}, function(data){
		if(data.returnCode == 0){
			var $F = $("#message-scene-test-view");
			
			var $selectSystem = $F.find("#select-system");
			
			$.each(data.data, function(systemId, object) {
				$selectSystem.append("<option value='" + systemId + "'>" + object.system.systemName + "[" 
					+ object.system.systemHost + ":" + object.system.systemPort + "]" + "</option>");			
			});
			
			$selectSystem.change(function(){
				var systemId = $(this).val();
				var object = data.data[systemId];
				if (object != null) {
					$F.find("#request-url").text(object.requestUrl);
					$F.find("#select-data").html('');
					$.each(object.requestData, function(i, n){
						$F.find("#select-data").append("<option data-id='" + n.dataId + "' value='" + i + "'>" + n.dataDiscr + "</option>");			
					});
					$F.find("#select-data").change();
				}
			});
			
			$F.find("#select-data").change(function(){
				var systemId = $selectSystem.val();	
				var that = this;
				if (!strIsNotEmpty($(that).val())) {
					$F.find("#scene-test-request-message").val('');
					return false;
				}
				$F.find("#scene-test-request-message").val(data.data[systemId]["requestData"][$(that).val()]["dataJson"]);
			});
			$selectSystem.change();
									
		} else {
			layer.alert(data.msg, {icon:5});
		}	
		layer.close(index);
	});
}

/**
 * 场景测试
 */
function sceneTest() {
	var $F = $("#message-scene-test-view");
	
	var requestUrl = $F.find("#request-url").text();
	var requestMessage = $F.find("#scene-test-request-message").val();
	
	if(!strIsNotEmpty(requestUrl) || !strIsNotEmpty(requestMessage)){
		layer.msg('请选择正确的接口地址和测试数据',{icon:2, time:1500});
		return;
	}
	
	var dataId = $F.find("#select-data > option:selected").attr("data-id");
	var index = layer.msg('正在进行测试...', {icon:16, time:9999999, shade:0.35});

	
	$.post(REQUEST_URL.AUTO_TEST.TEST_SCENE_URL, {customTestData: customTestData, systemId:$('#select-system').val(), messageSceneId:messageSceneId, dataId:dataId, requestUrl:requestUrl, requestMessage:requestMessage},function(data) {
		if (data.returnCode == 0) {			
			layer.close(index);
			renderResultViewPage(data.data, messageSceneId);
		}else{
			layer.close(index);
			layer.alert(data.msg, {icon:5});
		}
	});
}

