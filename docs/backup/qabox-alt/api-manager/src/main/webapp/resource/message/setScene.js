var setId;
var setName;
var currentSetInfo;//当前测试集信息
var mode = 0; //0-管理  1-添加
var messageSceneId;

var templateParams = {
		tableTheads:["接口", "报文", "场景", "协议", "格式", "测试数据", "验证规则", "创建时间","操作"],
		btnTools:[{
			type:"primary",
			size:"M",
			id:"manger-scene",
			iconFont:"&#xe60c;",
			name:"管理场景"
		},{
			size:"M",
			id:"add-scene",
			iconFont:"&#xe600;",
			name:"添加场景"
		},{
			size:"M",
			id:"batch-op",
			iMarkClass:"Hui-iconfont-del3",
			name:"批量操作"
		}, {
            size:"M",
            id:"show-complex-set-scene",
            iconFont:"&#xe6f3;",
            name:"组合场景"
        }, {
			type:"success",
			size:"M",
			id:"setting-set-config",
			iconFont:"&#xe62e;",
			name:"运行时设置"
		},{
			type:"success",
			size:"M",
			id:"edit-set-info",
			iconFont:"&#xe60c;",
			name:"编辑测试集信息"
		},{
			type:"danger",
			size:"M",
			id:"del-this-set",
			iconFont:"&#xe6e2;",
			name:"删除当前测试集"
		}]		
};

var columnsSetting = [
					{
					  	"data":null,
					  	"render":function(data, type, full, meta){                       
					          return checkboxHmtl(data.sceneName, data.messageSceneId, "selectScene");
					 }},
					 {"data":"messageSceneId"},
					 ellipsisData("interfaceName"),
					 ellipsisData("messageName"),
					 ellipsisData("sceneName"),
					 {
				            "data":"protocolType",
				            "render":function(data) {
				            	return labelCreate(data.toUpperCase());
				            }
				     },
				     {
                   	   "data":"messageType",
                   	   "render":function(data) {
                   		   return labelCreate(data.toUpperCase());
                   	   }                       
                      },
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
                         "data":null,
                         "render":function(data, type, full, meta){
                        	 
                           var context = [{
                              	title:"场景测试",
                  	    		markClass:"scene-test",
                  	    		iconFont:"&#xe603;"
                              }];
                           
                           //管理-删除
                           if (mode == 0) {
                        	   context.push({
	   	               	    		title:"删除",
	   	               	    		markClass:"op-scene",
	   	               	    		iconFont:"&#xe6e2;"
                  	    		});
                           }
                           
                           //添加
                           if (mode == 1) {
                        	   context.push({
	   	               	    		title:"添加",
	   	               	    		markClass:"op-scene",
	   	               	    		iconFont:"&#xe600;"
                 	    		});
                           }
                           
                           
                         	return btnIconTemplate(context);
                       }}										     
];


var eventList = {
		"#choose-business-system":function() {//测试集配置选择可执行测试环境
			$.get(REQUEST_URL.BUSINESS_SYSTEM.LIST_ALL, function (json) {
				if (json.returnCode == 0) {
					layerMultipleChoose({
						title:"请选择当前需要测试的测试环境(可多选)",
						customData:{//自定义数据，Array数组对象
							enable:true,
							data:json.data,
							textItemName:["systemName", "protocolType"],
							valueItemName:"systemId"
						},
						choosedValues:$("#systems").val().split(','),//已被选择的数据合集		
						closeLayer:true,//是否在确认之后自动关闭窗口
						minChooseCount:1,
						confirmCallback:function (chooseValues, chooseObjects, index) {
							$("#choose-business-system").siblings('p').remove();
							$.each(chooseObjects, function (i, n) {								
								$("#choose-business-system").before('<p>' + n.systemName + "[" + n.systemHost + ":" + n.systemPort + ']</p>');
							});							
							$("#systems").val(chooseValues.join(','));
						} //选择之后的回调						
				});
				} else {
					layer.alert(json.msg, {icon:5});
				}
			});
		},
        "#choose-data-pool": function () { // 选择数据池
		    $.get(REQUEST_URL.DATA_POOL.LIST_ALL + '?projectId=' + top.currentProjectId, function(json) {
		        if (json.returnCode == 0) {
                    layerMultipleChoose({
                        title:"请选择数据池(只可选择一个)",
                        customData:{//自定义数据，Array数组对象
                            enable:true,
                            data:json.data,
                            textItemName:["name"],
                            valueItemName:"poolId"
                        },
                        choosedValues:$("#poolId").val().split(','),//已被选择的数据合集
                        closeLayer:true,//是否在确认之后自动关闭窗口
                        minChooseCount:1,
                        maxChooseCount:1,
                        confirmCallback:function (chooseValues, chooseObjects, index) {
                            $("#choose-data-pool").siblings('span').remove();
                            $.each(chooseObjects, function (i, n) {
                                $("#choose-data-pool").before('<span>' + n.name + '&nbsp;&nbsp;</span>');
                            });
                            if (chooseValues != $("#poolId").val()) {
                                $("#choose-pool-item-ids").siblings('p').remove();
                                $("#poolItemIds").val('');
                            }
                            $("#poolId").val(chooseValues.join(','));
                        } //选择之后的回调
                    });
                } else {
                    layer.alert(json.msg, {icon:5});
                }
            });
        },
        "#choose-pool-item-ids": function () { // 选择数据池类别
		    var poolId = $('#poolId').val()
            if (!strIsNotEmpty(poolId)) {
                layer.msg('请先选择一个数据池', {icon: 0, time: 1500})
                return false;
            }
            $.get(REQUEST_URL.POOL_DATA_ITEM.LIST_ALL, {poolId: poolId},function(json) {
                if (json.returnCode == 0) {
                    layerMultipleChoose({
                        title:"请选择数据池类别(只能选择一个)",
                        customData:{//自定义数据，Array数组对象
                            enable:true,
                            data:json.data,
                            textItemName:["name"],
                            valueItemName:"itemId"
                        },
                        choosedValues:$("#poolItemIds").val().split(','),//已被选择的数据合集
                        closeLayer:true,//是否在确认之后自动关闭窗口
                        minChooseCount:1,
                        maxChooseCount:1,
                        confirmCallback:function (chooseValues, chooseObjects, index) {
                            $("#choose-pool-item-ids").siblings('p').remove();
                            $.each(chooseObjects, function (i, n) {
                                $("#choose-pool-item-ids").before('<p>' + n.name + '</p>');
                            });
                            $("#poolItemIds").val(chooseValues.join(','));
                        } //选择之后的回调
                    });
                } else {
                    layer.alert(json.msg, {icon:5});
                }
            });
        },
        "#clear-business-system":function() { //测试集配置清除可执行测试环境
            $("#choose-business-system").siblings('p').remove();
            $("#systems").val('');
            layer.msg('已清除!', {icon: 1, time: 1500});
        },
        "#clear-data-pool": function () { // 清除已选择的数据池
            $("#choose-data-pool").siblings('span').remove();
            $("#poolId").val('');
            layer.msg('已清除!', {icon: 1, time: 1500});
        },
        "#clear-pool-item-ids":function() { // 清除以选择的数据池类别
            $("#choose-pool-item-ids").siblings('p').remove();
            $("#poolItemIds").val('');
            layer.msg('已清除!', {icon: 1, time: 1500});
        },
		"#batch-op":function() { //批量操作 删除或者添加
			var checkboxList = $(".selectScene:checked");
			var opName = "删除";
			if (mode == 1) {
				opName = "添加";
			}
			batchDelObjs(checkboxList, REQUEST_URL.TEST_SET.OP_SCENE + "?mode=" + mode + "&setId=" + setId, table, opName)
		},
		"#setting-set-config":function() {
			if (currentSetInfo == null) {
				return false;
			}
			
			if (currentSetInfo.config == null) {
				settingConfig(currentSetInfo.setId, "0", function (json) {
					currentSetInfo.config = json.data;
					viewRunSettingConfig();	
				});
			} else {					
				viewRunSettingConfig();	
			}
		},
		"#update-option":function() {
			updateTestOptions();
		},
		"#reset-option":function() {
			//resetOptions();
		},
		"#del-this-set":function () {//删除当前测试集
			parent.delSet('确认删除此测试集吗？<br><span class="c-red">删除之后将会跳转到测试集列表</span>', setId, function(json) {
				parent.$("#show-all-set").click();
			});
		},
		"#show-complex-set-scene":function () {//打开组合场景页面		
			/*$(this).attr("data-title", setName + " - 测试集 - 组合场景");
			$(this).attr("_href", "resource/message/complexScene.html?setId=" + setId);
			parent.Hui_admin_tab(this);*/
			parent.layer_show(setName + " - 测试集 - 组合场景", "complexScene.html?setId=" + setId, null, null, 2);
		},
		"#edit-set-info":function () { //编辑当前测试集信息
			parent.publish.renderParams.editPage.modeFlag = 1;
			parent.publish.renderParams.editPage.objId = setId;
			parent.layer_show("编辑测试集信息", parent.editHtml, parent.editPageWidth, parent.editPageHeight.edit, 1);
			parent.publish.init();	
		},
		"#manger-scene":function() { //管理测试集 - 从测试集中删除
			var that = this;
			mode = 0;				
			refreshTable(REQUEST_URL.TEST_SET.SET_SCENE_LIST + "?mode=" + mode + "&setId=" + setId, function(json) {
				$(that).addClass('btn-primary').siblings("#add-scene").removeClass('btn-primary');
			}, null, true);	
			$("#batch-op").children("i").removeClass("Hui-iconfont-add").addClass("Hui-iconfont-del3");
		},
		"#add-scene":function() { //添加测试场景
			var that = this;
			mode = 1;			
			refreshTable(REQUEST_URL.TEST_SET.SET_SCENE_LIST + "?mode=" + mode + "&setId=" + setId, function(json) {
				$(that).addClass('btn-primary').siblings("#manger-scene").removeClass('btn-primary');
			}, null, true);	
			$("#batch-op").children("i").removeClass("Hui-iconfont-del3").addClass("Hui-iconfont-add");
		},
		".validate-method":function() {//场景验证规则管理
			var data = table.row( $(this).parents('tr') ).data();
			messageSceneId = data.messageSceneId;
			
			layer_show (data.sceneName + "-验证规则管理", 'validateParameters.html?messageSceneId=' + messageSceneId, null, null, 2, null, function() {
				refreshTable();
			});			
		},
		".show-test-data":function() { //展示测试数据
			var data = table.row( $(this).parents('tr') ).data();	
			var title = data.interfaceName + "-" + data.messageName + "-" + data.sceneName + " " + "测试数据";
			var url = "testData.html?messageSceneId=" + data.messageSceneId + "&sceneName=" + data.sceneName + "&protocolType=" + data.protocolType;
			
			layer_show (title, url, null, null, 2, null, function() {
				refreshTable();
			});
		},
		".scene-test":function() {//测试
			var data = table.row( $(this).parents('tr') ).data();
			messageSceneId = data.messageSceneId;
			layer_show(data.interfaceName + "-" + data.messageName + "-" + data.sceneName + "-测试", htmls["messageScene-test"], 1000, 490, 1, function() {
				renderSceneTestPage();				
			});
			
		},
        //测试集公共数据
        "#set-public-data": function() {
		    let publicData = $("#publicData").val();
            if (!isJSON(publicData)) {
                publicData = '{}';
            }
            let publicDataObject = JSON.parse(publicData);
            customDataSettingView({
                title: '测试集公共变量',
                remark: '测试集公共变量的使用方式与全局变量相同，例如：${__手机号}，并且在测试集定义的公共变量优先级高于全局变量。',
                data: publicDataObject,
                saveCallback: function(d, index) {
                    if (!CompareJsonObj(d, publicDataObject)) {
                        $('#publicData').val(JSON.stringify(d));
						//layer.msg('你更新了测试集公共变量，记得点击保存按钮保存变更！', {time: 1700});
					}
                    layer.close(index);
                }
            });
        },
        //测试集公共头
        "#set-public-http-header": function() {
            let publicHeader = $("#publicHeader").val();
            if (!isJSON(publicHeader)) {
                publicHeader = '{}';
            }
            let publicHeaderObject = JSON.parse(publicHeader);
            customDataSettingView({
                title: '测试集公共请求头',
                remark: '测试集公共请求头只对HTTP/HTTPS协议类型的接口场景有效，优先级高于测试报文中配置的请求头。',
                data: publicHeaderObject,
                saveCallback: function(d, index) {
                    if (!CompareJsonObj(d, publicHeaderObject)) {
                        $("#publicHeader").val(JSON.stringify(d));
                        //layer.msg('你更新了测试集公共请求头，记得点击保存按钮保存变更！', {time: 1700});
                    }
                    layer.close(index);
                }
            });
        },
        //测试集公共验证规则
        "#set-public-validate-rule": function() {
            layer_show(currentSetInfo.setName + '-测试集公共验证规则', 'validateParameters.html?configId=' + currentSetInfo.config.configId, null, null, 2);
        },
		".op-scene":function() {//单条删除或者添加
			var tip = '删除';
			
			if (mode == 1) {
				tip = "添加";
			}
						
			var data = table.row( $(this).parents('tr') ).data();
			var that = this;
			layer.confirm('确定要' + tip + '此场景吗?', {icon:0, title:'警告'}, function(index) {
				layer.close(index);
				$.get(REQUEST_URL.TEST_SET.OP_SCENE + "?mode=" + mode + "&setId=" + setId + "&messageSceneId=" + data.messageSceneId, function(json) {
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
			setId = GetQueryString("setId");
			
			$.post(REQUEST_URL.TEST_SET.GET, {id:setId}, function(data) {
				if (data.returnCode == 0) {
					currentSetInfo = data.data;
				} else {
					layer.alert(data.msg, {icon:5});
				}
				
			});
			
			setName = GetQueryString("setName");
			//判断是从那个页面打开的 flag=true从目录树跳转的
			if (!GetQueryString("flag")) {
				$("#setting-set-config,#del-this-set,#show-complex-set-scene,#edit-set-info").hide();
			}
			publish.renderParams.listPage.listUrl = REQUEST_URL.TEST_SET.SET_SCENE_LIST + "?mode=" + mode + "&setId=" + setId;
			df.resolve();			   		 	
   	 	},
		listPage:{
			tableObj:".table-sort",
			columnsSetting:columnsSetting,
			columnsJson:[0, 9],
			dtOtherSetting:{
				serverSide:true,
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


function viewRunSettingConfig () { 
	layer_show(currentSetInfo.setName + "-运行时配置", templates["set-setting-config"](currentSetInfo.config), '800', '640', 1, function() {
        $("#runType").val(currentSetInfo.config.runType);
	});
}

function settingConfig(setId, mode, callback) {
	$.post(REQUEST_URL.TEST_SET.SETTING_RUN_CONFIG, {setId:setId, mode:mode}, function(json) {
		if (json.returnCode == 0) {			
			callback(json);
		} else {
			layer.alert(json.msg, {icon:5});
		}
	});
}

function resetOptions () {
	if (currentSetInfo.config != null) {
		$("#requestUrlFlag").val(currentSetInfo.config.requestUrlFlag);
		$("#connectTimeOut").val(currentSetInfo.config.connectTimeOut);
		$("#readTimeOut").val(currentSetInfo.config.readTimeOut);
		$("#checkDataFlag").val(currentSetInfo.config.checkDataFlag);
		$("#configId").val(currentSetInfo.config.configId);
		$("#runType").val(currentSetInfo.config.runType);
		$("#customRequestUrl").val(currentSetInfo.config.customRequestUrl);
		$("#retryCount").val(currentSetInfo.config.retryCount);
		$("#systems").val(currentSetInfo.config.systems);
        $("#publicData").val(currentSetInfo.config.publicData);
        $("#publicHeader").val(currentSetInfo.config.publicHeader);
        $("#poolId").val(currentSetInfo.config.dataPool.poolId);
        $("#poolItemIds").val(currentSetInfo.config.poolItemIds);
		appendSystem(currentSetInfo.config.businessSystems);
	}
}

//更新配置信息
function updateTestOptions(){
	var updateConfigData = $("#form-setting-config").serializeArray();
	$.post(REQUEST_URL.AUTO_TEST.UPDATE_TEST_CONFIG_URL, updateConfigData, function(data){
		if(data.returnCode == 0){
			currentSetInfo.config = data.data;
			layer.closeAll('page');
			layer.msg('更新成功',{icon:1, time:1500});
		} else {
			layer.alert("更新失败：" + data.msg, {icon:5});
		}
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
	var systemId = $F.find("#select-system").val();
	var index = layer.msg('正在进行测试...', {icon:16, time:9999999, shade:0.35});
	
	$.post(REQUEST_URL.AUTO_TEST.TEST_SCENE_URL, {customTestData: customTestData, messageSceneId:messageSceneId, dataId:dataId, requestUrl:requestUrl, requestMessage:requestMessage, systemId:systemId},function(data) {
		if (data.returnCode == 0) {			
			layer.close(index);
			renderResultViewPage(data.data, messageSceneId);
		}else{
			layer.close(index);
			layer.alert(data.msg, {icon:5});
		}
	});
}