var settingLayerIndex;//当前打开的变量配置的layer窗口
var settingMode;//0 - 编辑页面设置  1 - 单独设置、
var settingType;//当前正在编辑的变量类型
var settingValue;//当前正在编辑的变量配置内容
var variableId;//当前正在编辑的变量id


var variableTypeInfo = {
		httpCallParameter:{
			text:"HTTP调用参数",
			settingValue:{
			    "Headers":{
			    },
			    "Querys":{},
			    "Authorization":{
			    },
			    "Method":"POST",
			    "ConnectTimeOut":"",
			    "ReadTimeOut":"",
			    "RecEncType":"UTF-8",
			    "EncType":"UTF-8" 
			},
            layerWidth: "706",
			layerHeight:"700",
			keyIsNull:false,
			ifCreate:false
		},
		socketCallParameter:{
			text:"Socket调用参数",
			settingValue:{
				"ConnectTimeOut":"",
			    "ReadTimeOut":""
			},
			layerHeight:"280",
			keyIsNull:false,
			ifCreate:false
		},
		webServiceCallParameter:{
			text:"WebService调用参数",
			settingValue:{
			    "ConnectTimeOut":"",
			    "Namespace":"",
			    "Method":"",
			    "Username":"",
			    "Password":""
			},
			layerHeight:"430",
			keyIsNull:false,
			ifCreate:false
		},
		relatedKeyWord:{
			text:"验证关联规则",
			settingValue:{
				LB:"",
				RB:"",
				OFFSET:"",
				ORDER:"",
				LENGHT:"",
				validateValue:"",
				getValueMethod:""
			},
			layerHeight:"560",
			keyIsNull:false,
			ifCreate:false
		},
		setRuntimeSetting:{
			text:"测试集运行时配置",
			settingValue:{
				requestUrlFlag:"",
				connectTimeOut:"",
				readTimeOut:"",
				retryCount:"",
				runType:"",
				checkDataFlag:"",
				customRequestUrl:""
			},
			layerHeight:"580",
			keyIsNull:false,
			ifCreate:false
		},
		constant:{
			text:"常量",
			settingValue:"",
			layerHeight:"",
			keyIsNull:false,
			ifCreate:true
		},
		datetime:{
			text:"日期",
			settingValue:{
				datetimeFormat:"",
                dateTimeOffset: ""
			},
			layerHeight:"355",
            layerWidth:"710",
			keyIsNull:false,
			ifCreate:true
		},
		randomNum:{
			text:"随机数",
			settingValue:{
				randomMin:"",
				randomNumMax:""
			},
			layerHeight:"260",
			keyIsNull:false,
			ifCreate:true
		},
		currentTimestamp:{
			text:"时间戳",
			settingValue:{
				timeOffset: ""
			},
			layerHeight:"230",
			keyIsNull:false,
			ifCreate:true
		},
		randomString:{
			text:"随机字符串",
			settingValue:{
				randomStringMode:"",
				randomStringNum:""
			},
			layerHeight:"260",
			keyIsNull:false,
			ifCreate:true
		},
		uuid:{
			text:"UUID",
			settingValue:{
				uuidSeparator:""
			},
			layerHeight:"310",
			keyIsNull:false,
			ifCreate:true
		},
		dynamicInterface:{
			text:"动态接口",
			settingValue:{
				sceneId:"",
				systemId:"",
				protocolType:"",
				valueExpression:"",
				sceneName:"",
				systemName:""
			},
			layerHeight:"405",
			layerWidth: "733",
			keyIsNull:false,
			ifCreate:true
		},
        dynamicComplexInterface:{
            text:"动态组合接口",
            settingValue:{
                complexSceneId:"",
                valueName:"",
                complexSceneName:""
            },
            layerHeight:"320",
            layerWidth: "700",
            keyIsNull:false,
            ifCreate:true
        },
        fileParameter: {
            text:"文件参数",
            settingValue:"",
            layerHeight:"",
            keyIsNull:false,
            ifCreate:true
        },
        dbSql: {
            text:"数据库取值",
            settingValue:{
                dbId: "",
                sql: "",
                rowSeq: "", //行号，如果查询出来有多行数据的话，默认为1
                colSeq: "" //列号，如果查询出来有多个列内容，默认为1

            },
            layerHeight:"500",
            layerWidth: "",
            keyIsNull:false,
            ifCreate:true
        }
};

function variableTypeList () {
	var types = [{value:"all", text:"全部类型", selected:"selected"}];
	$.each(variableTypeInfo, function(i, n) {
		types.push({value:i, text:n.text});
	});
	return types;
}

var templateParams = {
		tableTheads:["名称","类型","key","value","创建时间","创建用户", "备注","操作"],
		btnTools:[{
			type:"primary",
			size:"M",
			id:"add-object",
			iconFont:"&#xe600;",
			name:"创建变量模板"
		},{
			type:"danger",
			size:"M",
			id:"batch-del-object",
			iconFont:"&#xe6e2;",
			name:"批量删除"
		},{
			select:true,
			id:"list-by-variable-type",
			option:variableTypeList()
		}],
		formControls:[
		{
			edit:true,
			required:false,
			label:"&nbsp;&nbsp;ID",  	
			objText:"variableIdText",
			input:[{	
				hidden:true,
				name:"variableId"
				}]
		},
		{
			edit:false,
			required:true,
			label:"名称",  			
			input:[{	
				name:"variableName",
				placeholder:"输入一个名称来简要说明"
				}]
		},
		{
			edit:false,
			required:true,
			label:"变量类型",  			
			select:[{	
				name:"variableType",
				option:[{value:"httpCallParameter", text:variableTypeInfo.httpCallParameter.text},
				        {value:"socketCallParameter", text:variableTypeInfo.socketCallParameter.text},
				        {value:"webServiceCallParameter", text:variableTypeInfo.webServiceCallParameter.text},
				        {value:"relatedKeyWord", text:variableTypeInfo.relatedKeyWord.text},
				        {value:"setRuntimeSetting", text:variableTypeInfo.setRuntimeSetting.text},
				        {value:"constant", text:variableTypeInfo.constant.text, selected:"selected"},
				        {value:"datetime", text:variableTypeInfo.datetime.text},
				        {value:"randomNum", text:variableTypeInfo.randomNum.text},
				        {value:"currentTimestamp", text:variableTypeInfo.currentTimestamp.text},
				        {value:"randomString", text:variableTypeInfo.randomString.text},
				        {value:"uuid", text:variableTypeInfo.uuid.text},
				        {value:"dynamicInterface", text:variableTypeInfo.dynamicInterface.text},
                        {value:"dynamicComplexInterface", text:variableTypeInfo.dynamicComplexInterface.text},
                        {value:"fileParameter", text:variableTypeInfo.fileParameter.text},
                        {value:"dbSql", text:variableTypeInfo.dbSql.text}]
				}]
		},
		{
			edit:false,
			required:true,
			label:"Key值",  			
			input:[{	
				name:"key",
				placeholder:"自定义key"
				}]
		},
		{
			edit:false,
			required:true,
			label:"唯一性范围",  
			reminder:"表明一些需要动态生成的变量在何在使用范围下不会再次生成而使用上一次生成的值",
			select:[{	
				name:"uniqueScope",
				option:[{value:"0", text:"忽略该配置"},
				        {value:"1", text:"同一个测试集"},
				        {value:"2", text:"同一个组合场景"},
				        {value:"3", text:"同一个测试场景"}]
				}]
		},
		{
			edit:false,
			required:true,
			label:"有效期",  
			reminder:"表明一些需要动态生成的变量在指定的时间范围内不会再次生成而使用上一次生成的值，单位为秒",
			input:[{	
				name:"validityPeriod",
				placeholder:"单位为秒,默认为0",
                value: "0"
				}]
		},
		{
			edit:false,
			required:true,
			label:"Value值",  			
			input:[{	
				name:"value"
				}],
			button:[{
				 style:"success",
				 value:"配置",
				 name:"setting-variable-value"
			},{
                style:"success",
                value:"上传文件",
                name:"upload-file"
            }]
		},
		{
			edit:true,
			required:false,
			label:"创建时间",  	
			objText:"createTimeText",
			input:[{	
				hidden:true,
				name:"createTime"
				}]
		},
		{
			edit:true,
			label:"创建用户",  	
			objText:"user.realNameText",
			input:[{	
				hidden:true,
				name:"user.userId"
				}]
		},
		{
			edit:false,
			label:"&nbsp;&nbsp;备注",
			textarea:[{
				name:"mark"	
			}]
		},
		{
			name: "lastCreateValue"
		},
		{
			name: "expiryDate"
		}	
		]		
	};


var columnsSetting = [
    {
    	"data":null,
    	"render":function(data, type, full, meta){
			  		return checkboxHmtl(data.variableName, data.variableId, "selectVariable");
		           	}},
	{"data":"variableId"},ellipsisData("variableName"),                                       
	{
		"data":"variableType",
		"render":function(data, type, full, meta ){
		   	var context = {
		   			httpCallParameter:{
		   				status:"HTTP调用参数",
		   				btnStyle:"primary"
		   			},
		   			socketCallParameter:{
		   				status:"Socket调用参数",
		   				btnStyle:"primary"
		   			},
		   			webServiceCallParameter:{
		   				status:"WebService调用参数",
		   				btnStyle:"primary"
		   			},
		   			relatedKeyWord:{
		   				status:"验证关联规则",
		   				btnStyle:"primary"
		   			},
		   			setRuntimeSetting:{
		   				status:"测试集运行时配置",
		   				btnStyle:"primary"
		   			},
		   			constant:{
		   				status:"常量",
		   				btnStyle:"success"
		   			},
		   			datetime:{
		   				status:"日期",
		   				btnStyle:"success"
		   			},
		   			randomNum:{
		   				status:"随机数",
		   				btnStyle:"success"
		   			},
		   			currentTimestamp:{
		   				status:"时间戳",
		   				btnStyle:"success"
		   			},
		   			randomString:{
		   				status:"随机字符串",
		   				btnStyle:"success"
		   			},
		   			uuid:{
		   				status:"UUID",
		   				btnStyle:"success"
		   			},
		   			dynamicInterface:{
		   				status:"动态接口",
		   				btnStyle:"danger"
		   			},
                    dynamicComplexInterface:{
                        status:"动态组合接口",
                        btnStyle:"danger"
                    },
                    fileParameter:{
                        status:"文件参数",
                        btnStyle:"success"
                    },
                    dbSql:{
                        status:"数据库取值",
                        btnStyle:"danger"
                    }
		   	};
		   	return labelCreate(data, context);
		}
	},
	{
		"data":"key",
		"className":"ellipsis",
		"render":function(data, type, full, meta) {
			if (data != "" && data != null && data != " ") {
				return "${__" + data + "}";
			}
			return "";
		}
	
	},
	{
	    "data":"value",
	    "className":"ellipsis",
	    "render":function(data, type, full, meta ) {
	    	if (data != null && data.trim().length > 0) {	    		
	    		if (full.variableType == "constant" || full.variableType == "fileParameter") {
	    			return '<a href="javascript:;" onclick="showMark(\'' + full.variableName + '\', \'value\', this, \'value值\');"><span title="' + data + '">' + data + '</span></a>';
	    		} else {
	    			return btnTextTemplate([{
              			type:"primary",
              			size:"M",
              			markClass:"setting-variable-value",
              			name:"配置"
              		}]);
	    		}    				    	
	    	}
	    	return "";
	    }
	},
	ellipsisData("createTime"),ellipsisData("user.realName"),
	{
	    "data":"mark",
	    "className":"ellipsis",
	    "render":function(data, type, full, meta ) {
	    	if (data != "" && data != null) {
		    	return '<a href="javascript:;" onclick="showMark(\'' + full.variableName + '\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
	    	}
	    	return "";
	    }
	},
	{
		"data":null,
	    "render":function(data, type, full, meta){	    		    	
	    	var context = [];
	    	if (variableTypeInfo[data.variableType]["ifCreate"]) {
	    		context.push({
	    			title:"生成变量",
		    		markClass:"variable-create",
		    		iconFont:"&#xe725;"
	    		});
	    	}
	    	return btnIconTemplate(context.concat([{
	    		title:"编辑",
	    		markClass:"object-edit",
	    		iconFont:"&#xe6df;"
	    	},{
	    		title:"删除",
	    		markClass:"object-del",
	    		iconFont:"&#xe6e2;"
	    	}]));	    	
	    }}];

var eventList = {
		"#list-by-variable-type":{
			'change':function() {
				table.ajax.url(REQUEST_URL.GLOBAL_VARIABLE.LIST_ALL + '?variableType=' + $(this).val()).load();
			}
		},
		"#add-object":function(){
			publish.renderParams.editPage.modeFlag = 0;					
			layer_show("添加全局变量", editHtml, editPageWidth, editPageHeight.add, 1);
			publish.init();
			
		},
		"#batch-del-object":function(){
			var checkboxList = $(".selectVariable:checked");
			batchDelObjs(checkboxList, REQUEST_URL.GLOBAL_VARIABLE.DEL);
		},
		".object-edit":function(){
			var data = table.row( $(this).parents('tr') ).data();
			publish.renderParams.editPage.modeFlag = 1;	
  			publish.renderParams.editPage.objId = data.variableId;
  			layer_show("编辑全局变量信息", editHtml, editPageWidth, editPageHeight.edit, 1);
			publish.init();	
		},
		".object-del":function(){
			var data = table.row( $(this).parents('tr') ).data();
			delObj("确认要删除此全局变量信息吗？", REQUEST_URL.GLOBAL_VARIABLE.DEL, data.variableId, this);
		},
		"#variableType":{
			'change':function() {
				changeFormByVariableType($(this).val());
			}
		},
		"#setting-variable-value,.setting-variable-value":function() {//设置某些需要配置的变量值
			var title;
			var data = table.row( $(this).parents('tr') ).data();
			
			if (data == null) {
				settingType = $("#variableType").val();
				settingValue = $("#value").val();
				settingMode = 0;
			} else {	
				settingType = data.variableType;
				settingValue = data.value;
				title = data.variableName;
				variableId = data.variableId;
				settingMode = 1;
			}
			showSettingPage(title);
		},
		".variable-create":function() {//生成变量
			var data = table.row( $(this).parents('tr') ).data();
			if (data.variableType == "constant" || data.variableType == "fileParameter") {
				layer.alert('<span class="c-success">常量值：</span><br>' + data.value, {icon:1, anim:5, offset: '120px', title:data.variableName});
				return;
			}
            $(".page-container").spinModal();
			$.post(REQUEST_URL.GLOBAL_VARIABLE.CREATE_VARIABLE, {variableId:data.variableId}, function(json) {
                $(".page-container").spinModal(false);
				if (json.returnCode == 0) {
					layer.alert('<div id="create-result"><span class="c-success">生成变量成功：</span><br>' + json.msg + '</div>'
							, {icon:1, anim:5, title:data.variableName, btn: ['强制刷新', '确定'], offset: '120px'}
							,function(index, layero){
                                $(".page-container").spinModal();
								$.post(REQUEST_URL.GLOBAL_VARIABLE.CREATE_VARIABLE, {variableId:data.variableId, forceCreate:true}, function(json){
                                    $(".page-container").spinModal(false);
								    if (json.returnCode == 0) {
										$(layero).find('#create-result').html('<span class="c-success">生成变量成功：</span><br>' + json.msg);
									} else {
										layer.close(index);
										layer.alert('<span class="c-danger">生成变量失败：</span><br>' + json.msg, {icon:5, anim:5, title:data.variableName, offset: '120px'});
									}
								});								
							}
							,function(index){
								layer.close(index);
							}	
					);
				} else {
					layer.alert('<span class="c-danger">生成变量失败：</span><br>' + json.msg, {icon:5, anim:5, title:data.variableName});
				}
			});
			
		},
		"#save-setting-variable-value":function() {//保存
			var value = $.extend({}, variableTypeInfo[settingType]["settingValue"]);
			$.each(value, function(settingName, settingValue) {
				if ($("#" + settingName)) {
					value[settingName] = $("#" + settingName).val();
				}
			});
			
			//HTTP调用参数单独处理
			//Content-Type:application/xml##User-agent:chrome
			if (settingType == "httpCallParameter") {
				value["Authorization"] = parseHttpParameterToJson(value["Authorization"], "\n");
				value["Headers"] = parseHttpParameterToJson(value["Headers"], "\n");
				value["Querys"] = parseHttpParameterToJson(value["Querys"], "\n");
			}
			
			value = JSON.stringify(value);
			if (settingMode == 0) {								
				$("#value").val(value);
				if (variableTypeInfo[settingType]["keyIsNull"]) {
					$("#key").val(" ");
				}
				layer.close(settingLayerIndex);
				return;
			}
					
			//发送请求更新该variable的value值
			if (settingValue != value) {
				$.post(REQUEST_URL.GLOBAL_VARIABLE.UPDATE_VALUE, {variableId:variableId, value:value}, function(json) {
					if (json.returnCode == 0) {								
						refreshTable();
						layer.msg('更新成功!', {icon:1, time:2000});
						layer.close(settingLayerIndex);
					} else {
						layer.alert(json.msg, {icon:5});
					}								
				});
			} else {
				layer.close(settingLayerIndex);
			}
			
		}
};


var mySetting = {
		eventList:eventList,
		editPage:{
			editUrl:REQUEST_URL.GLOBAL_VARIABLE.EDIT,
			getUrl:REQUEST_URL.GLOBAL_VARIABLE.GET,
			beforeInit:function(df){				
				$("#setting-variable-value").attr('type', 'hidden');
                $("#upload-file").attr('type', 'hidden');
				$("#key").val(uuid());

                let loadIndex;
                layui.use('upload', function(){
                    let upload = layui.upload;
                    //执行实例
                    let uploadInst = upload.render({
                        elem: '#upload-file' //绑定元素
                        ,url: REQUEST_URL.FILE.UPLOAD_FILE //上传接口
                        ,accept:"file"
                        ,data: {type: "1"}
                        ,size:"1024000"
                        ,drag:false
                        ,before:function(obj) {
                            loadIndex = layer.msg('正在上传文件中...', {icon:16, time:99999, shade:0.4});
                        }
                        ,done: function(res){
                            //上传完毕回调
                            if (res.returnCode == 0) {//上传成功
                                layer.close(loadIndex);
                                layer.msg('文件上传成功!', {icon: 1, time: 1500});
                                $('#upload-file').prev('span').remove();
                                $('#upload-file').before('<span>' + res.data.path + '&nbsp;&nbsp;</span>');
                                $('#value').val(res.data.path);
                            } else {
                                layer.close(loadIndex);
                                layer.alert(res.msg, {icon:5});
                            }
                        }
                    });
                });

				df.resolve();
			},
			renderCallback:function(obj){
				$("#variableType").trigger('change');
				$("#uniqueScope").trigger('change');
			},
			messages:{
				variableName:"请输入变量或者模板的名称",
				key:"请设定一个唯一的Key值",
                value: "请输入值或者点击配置按钮"
			},
			rules:{
				variableName:{
					required:true,
					minlength:2,
					maxlength:255
				},
				validityPeriod:{
					required:true,
					digits:true,
					minlength:1,
					maxlength:11
				},
				key:{
					required:true,					
					remote:{
						url:REQUEST_URL.GLOBAL_VARIABLE.CHECK_NAME,
						type:"post",
						dataType: "json",
						data: {                   
					        key: function() {
					            return $("#key").val();
					        },
					        variableId:function(){
					        	return $("#variableId").val();
					        },
					        variableType:function() {
					        	return $("#variableType").val();
					        }
					}}
				},
				value:{
					required:true
				}
			},
		},
		listPage:{
			listUrl:REQUEST_URL.GLOBAL_VARIABLE.LIST_ALL,
			tableObj:".table-sort",
			columnsSetting:columnsSetting,
			columnsJson:[0, 8, 9],
			dtOtherSetting:{
				serverSide:false
			}
		},
		templateParams:templateParams		
	};

$(function(){			
	publish.renderParams = $.extend(true,publish.renderParams,mySetting);
	publish.init();
});


/*****************************************************************************************************/
/**
 * 根据variableType改变form表单显示
 */
function changeFormByVariableType (variableType) {
	switch (variableType) {
		case "httpCallParameter":
		case "socketCallParameter":
		case "webServiceCallParameter":
		case "relatedKeyWord":
		case "setRuntimeSetting":
		case "dynamicInterface":
        case "dynamicComplexInterface":
			showOrHideInput('hidden', 'button', 'hidden', null);
			break;
        case "dbSql":
		case "datetime":
		case "randomNum":
		case "randomString":
        case "currentTimestamp":
		case "uuid":
			showOrHideInput('hidden', 'button', 'hidden', null);
			break;
		case "constant":
			showOrHideInput('text', 'hidden', 'hidden', null);
			break;
        case "fileParameter":
            showOrHideInput('hidden', 'hidden', 'button', null);
            break;
		default:
			break;
	}
}

/**
 * 根据配置的类型不同改变editPage页面上的一些控件的显示
 * @param key_type
 * @param value_type
 * @param settingButton_type
 * @returns
 */
function showOrHideInput(value_type, settingButton_type, uploadButton_type, value) {
	$("#value").attr('type', value_type);
	$("#setting-variable-value").attr('type', settingButton_type);
	$("#upload-file").attr('type', uploadButton_type);
	if ('button' == uploadButton_type && strIsNotEmpty($('#value').val())) {
        $('#upload-file').before('<span>' + $('#value').val() + '&nbsp;&nbsp;</span>');
    } else {
        $("#upload-file").prev('span').remove();
    }
    if (value != null) {
        $("#value").val(value);
	}

}

/**
 * 打开模板或者参数配置编辑页
 * @param title
 * @returns
 */
function showSettingPage(title) {
	if (title == null) {
		title = variableTypeInfo[settingType]["text"];
	}
	layer_show( title + "-配置", templates["global-variable-setting-value"](), variableTypeInfo[settingType]["layerWidth"] || '680', variableTypeInfo[settingType]["layerHeight"], 1
			, function(layero, index) {
				settingLayerIndex = index;	
				
				//该页面相关方法定义
                //选择查询数据库
                $(layero).find('#choose-db').click(function(){
                    queryDb(function(dbId, dbName){
                        $('#dbId').val(dbId);
                    });
                });

				//选择测试环境
				$(layero).find('#choose-system').click(function(){
					if (!strIsNotEmpty($(".dynamicInterface #protocolType").val())) {
						layer.msg("请先选择接口场景!", {icon:5, time:1800});
						return false;
					}						
					$.post(REQUEST_URL.BUSINESS_SYSTEM.LIST_ALL, {protocolType:$(".dynamicInterface #protocolType").val()}, function (json) {
						if (json.returnCode == 0) {
							if (json.data.length < 1) {
								layer.msg('无匹配的测试环境可供选择', {icon:0, time:1800});
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
								choosedValues:$(".dynamicInterface #systemId").val().split(","),//已被选择的数据合集		
								closeLayer:true,//是否在确认之后自动关闭窗口
								maxChooseCount:1,
								minChooseCount:1,
								//选择之后的回调	
								confirmCallback:function (chooseValues, chooseObjects, index) {
									$(".dynamicInterface #systemNameText").text(chooseObjects[0]['systemName']);
									$(".dynamicInterface #systemName").val(chooseObjects[0]['systemName']);
									$(".dynamicInterface #systemId").val(chooseValues[0]);
								} 					
						});				
						} else {
							layer.alert(json.msg, {icon:5});
						}
					});	
				});

				// 选择组合场景
                $(layero).find('#choose-complex-scene').click(function(){
                    layer_show("选择组合场景", "../message/chooseComplexScene.html?callbackFun=chooseComplexScene", null, null, 2);
                });

				//选择测试场景
				$(layero).find('#choose-scene').click(function(){
					layer_show("选择测试场景", "../advanced/chooseMessageScene.html?callbackFun=chooseTestScene&notMultiple=true", null, null, 2);	
				});			
				if (isJSON(settingValue)) {
					$.each(JSON.parse(settingValue), function(i, n) {
						if ($("#" + i)) {
							
							//HTTP调用参数单独处理
							//Content-Type:application/xml;User-agent:chrome
							if (i == "Authorization" || i == "Headers" || i == "Querys") {
								n = parseHttpParameterJsonToString(n, "\n");
							}
							
							$("#" + i).val(n);
							if (i == "ORDER") {
								$("#objectSeqText").text(n);
							}
							
							//动态接口 动态组合接口
							if (i == "systemName" || i == "sceneName" || i == "complexSceneName") {
								$("#" + i + "Text").text(n);
							}
						}
					});
				}
				$("div ." + settingType).removeClass('hide');						
	});
}

/**
 * 选择测试场景之后的回调
 * @param obj
 * @returns {Boolean}
 */
function chooseTestScene (sceneObj) {
	if (sceneObj == null) {
		return false;
	}	
	
	$('.dynamicInterface #protocolType').val(sceneObj.protocolType);
	$('.dynamicInterface #sceneId').val(sceneObj.messageSceneId);
	$('.dynamicInterface #sceneName').val(sceneObj.interfaceName + '-' + sceneObj.messageName + '-' + sceneObj.sceneName);
	$('.dynamicInterface #sceneNameText').text(sceneObj.interfaceName + '-' + sceneObj.messageName + '-' + sceneObj.sceneName);
}

/**
 * 选择组合场景之后的回调
 * @param complexScene
 */
function chooseComplexScene(complexScene){
    if (complexScene == null) {
        return false;
    }
    $('.dynamicComplexInterface #complexSceneId').val(complexScene.id);
    $('.dynamicComplexInterface #complexSceneName').val(complexScene.complexSceneName);
    $('.dynamicComplexInterface #complexSceneNameText').text(complexScene.complexSceneName);
}

/**
 * uuid生成
 * @returns
 */
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "";

    var uuid = s.join("");
    return uuid;
}