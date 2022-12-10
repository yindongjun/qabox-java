var messageSceneId; //当前正在操作的sceneId
var configId; //当前正在操作的configId
var responseExample;


var selectGetValueMethodTip = {
		"0":["常量", "请输入用于比对该参数值的字符串,如18655036394", "常量"],
		"1":["入参节点", "请输入正确的入参节点路径,程序将会自动化来获取该路径下的值,区分大小写,请参考接口信息中的参数管理", "入参节点路径"],
		"2":["SQL语句", "请输入用于查询的SQL语句。在SQL语句中,你同样可以使用节点路径: \"#ROOT.DATA.PHONE_NO#\" 来表示入参节点数据", "查询SQL语句"],
        "2-1":["与通过SQL语句执行得到的值进行比对", "请输入执行SQL语句，在SQL语句中,你同样可以使用节点路径: \"#ROOT.DATA.PHONE_NO#\" 来表示入参节点数据", "与通过SQL语句执行得到的值进行比对"],
        "3":["全局变量", "在变量模板模块创建你要使用的全局变量", "全局变量"],
		"4":["正则表达式", "使用JAVA正则表达式模板来进行验证", "正则表达式"],
        "5":["时间，单位毫秒", "不需要输入值", "响应时间"],
        "6":["状态码", "不需要输入值", "状态码"],
        "7":["比对内容", "请输入响应头名称，例如：Content-Type, Set-Cookies等，不区分大小写", "响应头"]
	};


var customValidateCondition = {
    "nothing": "不验证",
    "gt": "大于",
    "eq": "等于",
    "lt": "小于",
    "contain": "包含",
    "notcontain": "不包含",
    "exist": "必须存在值",
    "reg": "匹配正则表达式"
};

/**
 * 0-左右边界取关键字验证<br>
 * 1-节点参数验证<br>
 * 2-自定义验证<br>
 */
var addValidateMethodFlag = 1; //添加的验证规则模式

var templateParams = {
		tableTheads:["验证方式", "节点路径/关联规则", "预期验证值类型", "比对条件", "预期值", "状态", "备注", "操作"],
		btnTools:[{
			type:"primary",
			size:"M",
			id:"add-object",
			iconFont:"&#xe600;",
			name:"添加验证规则"
		},{
			type:"danger",
			size:"M",
			id:"batch-del-object",
			iconFont:"&#xe6e2;",
			name:"批量删除"
		}],
		formControls:[
		{
			edit:true,
			label:"规则ID",  	
			objText:"validateIdText",
			input:[{	
				hidden:true,
				name:"validateId"
				}]
		},
		{
			label:"节点路径",  
			button:[{
				style:"primary",
				value:"选择",
				name:"choose-response-node-path",
				embellish:"&nbsp;"
			},{
				style:"default",
				value:"手动输入",
				name:"input-response-node-path"
			}],
            input:[{
                hidden:true,
                name:"parameterName"
            }]
		},
		{	
			required:true,
			label:"预期比对值类型",
			button:[{
				style:"primary",
				value:"选择",
				name:"select-get-value-method"
			}]
		},
        {
            label:"获取参数",
            input:[{
                name:"customParameter",
                placeholder: "请根据下方红字提示填写参数内容"
            }]
        },
        {
            label:"比对条件",
            select: [{
                name: "validateCondition",
                option:function() {
                    let options = [];
                    $.each(customValidateCondition, function(k, v) {
                        options.push({
                            value:k,
                            text:v
                        });
                    });

                    return options;
                }()
            }]
        },
		{
			label:"预期比对值",  
			input:[{	
				name:"validateValue"
				}]
		},
		{	
			required:true,
			label:"状态",  			
			select:[{	
				name:"status",
				option:[{
					value:"0",
					text:"可用"
				},{
					value:"1",
					text:"禁用"
				}]
				}]
		},
		{
			name:"messageScene.messageSceneId"
		},
		{
			name:"testConfig.configId"
		},
		{
			name:"validateMethodFlag",
		},
		{
			name:"getValueMethod"
		},
		{
			label:"备注",  
			textarea:[{	
				name:"mark",
				placeholder:""
			}]
		}
		]		
	};

var columnsSetting = [
                      {
                      	"data":null,
                      	"render":function(data, type, full, meta){                       
                              return checkboxHmtl(data.validateId, data.validateId, "selectValidate");
                          }},
                      {"data":"validateId"},
                      {
                    	  "data":"validateMethodFlag",
                    	  "render":function(data) {
                    		  var option = {
                          			"0":{
                          				btnStyle:"danger",
                  		  				status:"关联验证"
                          			},
                          			"1":{
                          				btnStyle:"primary",
                  		  				status:"节点验证"
                          			},
                          			"2":{
                          				btnStyle:"secondary",
                  		  				status:"自定义验证"
                          			}
                          		};
                          		
                          		return labelCreate(data, option);
                    	  }
                      },
                      {
                    	  "data":"parameterName",
                    	  "className":"ellipsis",
                    	  "render":function(data, type, row, meta) {
                    		  if (row.validateMethodFlag == "1") {
                    			  return '<span title="' + data + '">' + data + '</span>';
                    		  } else {
                    			  return "";
                    		  }
                    	  }
                      },                    
                      {
                    	"data":"getValueMethod",
                    	"render":function(data, type, row, meta) {
                    		let option = {
                    			"0":{
                    				btnStyle:"primary",
            		  				status:"常量"
                    			},
                    			"1":{
                    				btnStyle:"primary",
            		  				status:"入参节点"
                    			},
                    			"3":{
                    				btnStyle:"primary",
            		  				status:"全局变量"
                    			},
                    			"4":{
                    				btnStyle:"primary",
            		  				status:"正则表达式"
                    			},
                                "5":{
                                    btnStyle:"primary",
                                    status:"响应时间"
                                },
                                "6":{
                                    btnStyle:"primary",
                                    status:"状态码"
                                },
                                "7":{
                                    btnStyle:"primary",
                                    status:"响应头"
                                },
                                "default":{
                                    btnStyle:"primary",
                                    status:"SQL语句"
                                },
                    		};
                    		
                    		return labelCreate(data, option);
                    	}
                      },
                        {
                            "data":"validateCondition",
                            "render":function(data, type, row, meta) {
                                return customValidateCondition[data] || '';
                            }
                        },
                      {
                    	  "data":"validateValue",
                    	  "className":"ellipsis",
                    	  "render":function(data, type, full, meta) { 
                    		  if (data != "" && data != null) {
                    			  return '<a href="javascript:;" onclick="showMark(\'预期值\', \'validateValue\', this);"><span title="' + data + '">' + data + '</span></a>';
                    		  }
            		    	return "";
                    	  }
	                   },
                      {
                    	  "data":"status",
                    	  "render":function(data, type, row, meta) {
                    		  let checked = '';
                    		  if(data == "0") {checked = 'checked';}                  	
                    		  return '<div class="switch size-MINI" data-on-label="可用" data-off-label="禁用"><input type="checkbox" ' + checked + ' value="' + row.validateId + '"/></div>';                                                   							
                    	  }
                      }, 
                      {
              		    "data":"mark",
              		    "className":"ellipsis",
              		    "render":function(data, type, full, meta) { 
              		    	if (data != "" && data != null) {
                  		    	return '<a href="javascript:;" onclick="showMark(\'验证规则\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
              		    	}
              		    	return "";
              		    }
                      },
                      {
                          "data":null,
                          "render":function(data, type, full, meta){
                            let context = [{
                	    		title:"规则编辑",
                	    		markClass:"object-edit",
                	    		iconFont:"&#xe6df;"
                	    	},{
                	    		title:"规则删除",
                	    		markClass:"object-del",
                	    		iconFont:"&#xe6e2;"
                	    	}];                           
                          	return btnIconTemplate(context);
                          }}
                  ];

var eventList = {
		"#view-response-example":function(){//查看返回示例报文
			if (messageSceneId == null) {
				layer.msg('没有返回报文可供查看!', {time: 1600});
				return false;
			}
			if (responseExample != null) {
				createViewWindow(responseExample, {
					title:"返回报文示例", //标题
				});
				return false;
			}
			$.post(REQUEST_URL.MESSAGE_SCENE.GET, {id:messageSceneId}, function(json){
				if (json.returnCode == 0) {
					if (!strIsNotEmpty(json.data.responseExample)) {
						layer.msg('没有设置返回报文示例,请先设置!', {icon:2, time:1800});
						return false;
					}
					responseExample = json.data.responseExample;
					createViewWindow(responseExample, {
						title:"返回报文示例", //标题
					});
				} else {
					layer.alert(json.msg, {icon:5});
				}
			});
			
		},
		"#input-response-node-path":function(){//手动输入返回报文入参节点
			layer.prompt({title:"请输入需要验证的出参节点路径"}, function(value, index, elem){
				  if (strIsNotEmpty(value)) {
					 $("#parameterName").val(value);
					 $("#choose-response-node-path").siblings('span').remove();
					 $("#choose-response-node-path").before('<span>' + value + '&nbsp;</span>');
					 layer.close(index);
				  }
			});
		},
		"#choose-response-node-path":function(){//选择返回报文入参节点
            if (messageSceneId == null) {
                layer.msg('请手动输入节点路径！', {time: 1600});
                return false;
            }
			chooseParameterNodePath(REQUEST_URL.MESSAGE_SCENE.GET_RESPONSE_MSG_JSON_TREE, {messageSceneId:messageSceneId}, {
				titleName:"出参节点选择",
				isChoosePath:true, 
				notChooseTypes:["Array", "Map", "List", "Object"],
				choosenCallback:function (path) {
					$("#parameterName").val(path);
					$("#choose-response-node-path").siblings('span').remove();
					$("#choose-response-node-path").before('<span>' + path + '&nbsp;</span>');
				}
			});
		},
		"#add-object":function() {
			publish.renderParams.editPage.modeFlag = 0;
			layer.confirm(
					'请选择需要创建的验证方式',
					{
						title:'提示',
						btn:['关联验证','节点验证', "自定义验证"],
                        btn3:function(index) {
                            layer.close(index);
                            addValidateMethodFlag = 2;
                            currIndex = layer_show("增加自定义验证规则", editHtml, "700", "500", 1, function() {
                                addEditPageHtml();
                            });
                            publish.init();
                        }
					},function(index){ 
						layer.close(index);
						addValidateMethodFlag = 0;	
						showValidatRulePage();
					},function(index){
						layer.close(index);
						addValidateMethodFlag = 1;
						currIndex = layer_show("增加节点验证规则", editHtml, "700", "500", 1, function() {
							addEditPageHtml();
						});
						publish.init();
					});									
		},
		"#batch-del-object":function() {
			var checkboxList = $(".selectValidate:checked");
			batchDelObjs(checkboxList, REQUEST_URL.VALIDATE.DEL);
		},
		".object-edit":function() {
			var data = table.row( $(this).parents('tr') ).data();
			publish.renderParams.editPage.modeFlag = 1;
			addValidateMethodFlag = data.validateMethodFlag;
			if (data.validateMethodFlag == "1" || data.validateMethodFlag == "2") {
				publish.renderParams.editPage.objId = data.validateId;
				layer_show("编辑规则信息", editHtml, "700", "530",1, function() {
					addEditPageHtml(data);
				});
				publish.init();	
			} else {
				showValidatRulePage(data.validateId);
			}
		},
		".object-del":function() {
			var data = table.row( $(this).parents('tr') ).data();
			delObj("确定删除此验证规则？请慎重操作!", REQUEST_URL.VALIDATE.DEL, data.validateId, this);
		},
		"#select-get-value-method":function() {  //选择预期值取值方式
		    if (addValidateMethodFlag == 1) {
                layer.confirm('请选择预期比对数据类型',{btnAlign: 'l', offset: '30px',title:'提示',btn:['常量','入参节点','SQL语句', '全局变量', '正则表达式'],
                        btn3:function(index) {
                            queryDb(function(dbId, dbName) {
                                changeTigs(dbId, dbName);
                            });
                        },
                        btn4:function(index){//全局变量
                            changeTigs("3");
                            layer.close(index);
                        },
                        btn5:function(index){//正则表达式
                            changeTigs("4");
                            layer.close(index);
                        }}
                    ,function(index){//常量
                        changeTigs("0");
                        layer.close(index);
                    }
                    ,function(index){//入参节点
                        changeTigs("1");
                        layer.close(index);
                    });
            }

		    if (addValidateMethodFlag == 2) {
                layer.confirm('请选择自定义验证项',{btnAlign: 'l', offset: '30px',title:'提示',btn:['数据库变动','响应时间','状态码', '响应头'],
                        btn3:function(index) { //状态码
                            changeTigs("6");
                            layer.close(index);
                        },
                        btn4:function(index){//响应头
                            changeTigs("7");
                            layer.close(index);
                        }}
                    ,function(index){//数据库变动
                        queryDb(function(dbId, dbName) {
                            changeTigs(dbId, dbName);
                        });
                        layer.close(index);
                    }
                    ,function(index){//响应时间
                        changeTigs("5");
                        layer.close(index);
                    });
            }
		},
		'#choose-validate-template':function() {//选择关联验证模板
			$.post(REQUEST_URL.GLOBAL_VARIABLE.LIST_ALL, {variableType:"relatedKeyWord"}, function(json) {
				if (json.returnCode == 0) {
					showSelectBox(json.data, "variableId", "variableName", function(variableId, globalVariable, index) {
						$.each(JSON.parse(globalVariable["value"]), function(name, value) {
							if ($("#" + name)) {
								$("#" + name).val(value);
							}
						});
						$('#mark').val(globalVariable['mark']);
						layer.msg('已加载配置!', {icon:1, time:1500});
						layer.close(index);
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
			messageSceneId = GetQueryString("messageSceneId");
			configId = GetQueryString("configId");
			if (messageSceneId != null) {
                publish.renderParams.listPage.listUrl = REQUEST_URL.VALIDATE.LIST + "?messageSceneId=" + messageSceneId;
			} else {
                publish.renderParams.listPage.listUrl = REQUEST_URL.VALIDATE.GET_CONFIG_VALIDATE_RULES + "?configId=" + configId;
                $('#view-response-example').hide();
			}

			df.resolve();			   		 	
   	 	},
		editPage:{
			editUrl:REQUEST_URL.VALIDATE.EDIT,
			getUrl:REQUEST_URL.VALIDATE.GET,
			rules:{
				getValueMethod:{
					required:true
				}				
			},
			beforeInit:function(df){
				$("#messageScene\\.messageSceneId").val(messageSceneId);
				$("#testConfig\\.configId").val(configId);
       		 	df.resolve();
       	 	},
       	 	renderCallback:function(obj){
       	 		$("#choose-response-node-path").before('<span>' + obj.parameterName + '&nbsp;</span>');
       	 		changeTigs(obj.getValueMethod);
       	 	},
            beforeSubmitCallback:function(modeFlag, formObj) {
                if (addValidateMethodFlag == 2) {
                    formObj.find('#parameterName').val(formObj.find('#customParameter').val());
                }
            }
		},		
		listPage:{
			listUrl:REQUEST_URL.VALIDATE.LIST,
			tableObj:".table-sort",
			exportExcel:false,
			columnsSetting:columnsSetting,
			columnsJson:[0, 7, 8],
            dtDrawCallback: function() {
                $('.switch')['bootstrapSwitch']();
                $('.switch input:checkbox').change(function(){
                    let flag = $(this).is(':checked');
                    let validateId = $(this).attr('value');
                    updateStatus(validateId, flag, this);
                });
            },
			dtOtherSetting:{
				"serverSide": false
			}
		},
		templateParams:templateParams		
	};

$(function(){
	publish.renderParams = $.extend(true, publish.renderParams,mySetting);
	publish.init();
});

/*******************************************************************************************************/
/**
 * 绑定事件
 */
function bindChooseRequestNodePath(){
	if (messageSceneId == null) {
		return false;
	}
	//防止重复绑定事件，先解绑
    $("#validateValue").unbind('click');
	$("#validateValue").bind('click', function(){
		chooseParameterNodePath(REQUEST_URL.MESSAGE_SCENE.GET_REQUEST_MSG_JSON_TREE, {messageSceneId:messageSceneId}, {
			titleName:"入参节点选择",
			isChoosePath:true, 
			notChooseTypes:["Array", "Map", "List", "Object"],
			choosenCallback:function (path) {
				$("#validateValue").val(path);
			}
		});
	});
}

/**
 * 实时改变状态
 */
function updateStatus(validateId, flag, obj) {
	var status = '1';
	if(flag == true){
		status = '0';
	}
	$.post(REQUEST_URL.VALIDATE.RULE_UPDATE_STATUS, {validateId:validateId, status:status}, function(json) {
		if(json.returnCode != RETURN_CODE.SUCCESS){
			$(obj).click();
			layer.alert(json.msg, {icon:5});
		} else {
		    layer.msg('操作成功!', {icon: 1, time: 1500});
        }
	});
}

/**
 * 节点验证编辑时，根据取值方式的选择不同，改变页面提示
 * @param type 0 - 字符串 1 - 入参节点 其他为数据库的id
 * @param dbName 数据库名称，选择数据库时才有
 */
function changeTigs(type, dbName) {
	$("#select-get-value-method").siblings('strong').remove();
	$("#getValueMethod").val(type);
	
	if (/^9999/.test(type)) {
	    if (addValidateMethodFlag == 1) {
            type = "2";
        } else {
	        type = "2-1";
        }
		selectGetValueMethodTip[type][2] = "数据库  " + dbName;
	}
		
	if (type == "1") {
		bindChooseRequestNodePath();
	} else {
		$("#validateValue").unbind('click');
	}
	
	$("#validateValue").attr("placeholder", selectGetValueMethodTip[type][0]);
	$("#tipMsg").text(selectGetValueMethodTip[type][1]);
	$("#select-get-value-method").before('<strong>' + selectGetValueMethodTip[type][2] + '&nbsp;&nbsp;</strong>');
}

/**
 * 节点验证时,附加的页面渲染工作
 */
function addEditPageHtml(obj) {
    if (addValidateMethodFlag == 1) {
        $('#validateCondition').parents('.row').hide();
        $('#customParameter').parents('.row').hide();
    }
    if (addValidateMethodFlag == 2) {
        $('#parameterName').parents('.row').hide();
        if (obj != null) {
            $('#customParameter').val(obj.parameterName);
        }
    }

    $('#validateMethodFlag').val(addValidateMethodFlag);

	$("#form-edit").append('<div class="row cl"><div class="col-xs-8 col-sm-9 col-xs-offset-4' 
			+ ' col-sm-offset-3"><span id="tipMsg" style="color:red;"></span></div></div>');
}

/**
 * 展示不同的类型验证规则的编辑页面
 * @param type
 */
function showValidatRulePage(validateId) {
	//关联验证 根据publish.renderParams.editPage.modeFlag 0为增加  1为编辑
	if (addValidateMethodFlag == 0) {
		layer_show('关联验证', htmls["messageScene-validateKeyword"], '840', '570', 1, function() {
			if (publish.renderParams.editPage.modeFlag == 1) {
				$.get(REQUEST_URL.VALIDATE.GET, {id:validateId},function(data){
					if(data.returnCode == 0) {
						data = data.data;
						if (data.parameterName != "") {
							var relevanceObject = JSON.parse(data.parameterName);
							$("#ORDER").val(relevanceObject.ORDER);
							$("#LB").val(relevanceObject.LB);
							$("#RB").val(relevanceObject.RB);
							$("#OFFSET").val(relevanceObject.OFFSET);
							$("#LENGHT").val(relevanceObject.LENGHT);
							$("#objectSeqText").text(relevanceObject.ORDER);
						}
						$("#parameterName").val(data.parameterName);
						$("#validateValue").val(data.validateValue);
						$("#validateId").val(data.validateId);
						$("#getValueMethod").val(data.getValueMethod || "0");
						$("#messageScene\\.messageSceneId").val(messageSceneId);
                        $("#testConfig\\.configId").val(configId);
						$("#status").val(data.status);
						$("#mark").val(data.mark);
					} else {
						layer.alert(data.msg,{icon:5});
					}
				});
			}		
		});
	}
}


/**
 * 保存验证内容
 * 全文验证或者关联验证
 */
function saveValidateJson(){	
	var sendData = {};
	if ($("#parameterName").length > 0) {
		var parameterName = '{"LB":"' + ($("#LB").val()).replace(/\"/g, "\\\"") 
		+ '","RB":"' + ($("#RB").val()).replace(/\"/g, "\\\"") + '","ORDER":"' + $("#ORDER").val() 
		+ '","OFFSET":"' + ($("#OFFSET").val()).replace(/\"/g, "\\\"") 
		+ '","LENGHT":"' + ($("#LENGHT").val()).replace(/\"/g, "\\\"") + '"}';
		sendData.parameterName = parameterName;
	}	
	sendData.validateMethodFlag = addValidateMethodFlag;
	sendData.validateValue = $("#validateValue").val();
	sendData.validateId = $("#validateId").val();
	sendData.getValueMethod = $("#getValueMethod").val();
    sendData.mark = $('#mark').val();
	sendData["messageScene.messageSceneId"] = messageSceneId;
    sendData["testConfig.configId"] = configId;
	sendData["status"] = $("#status").val();
	
	$.post(REQUEST_URL.VALIDATE.EDIT, sendData, function(data){
		if(data.returnCode == 0){
			refreshTable();
			layer.closeAll('page');
			layer.msg('已保存!', {icon:1, time:1500});
		} else {
			layer.alert(data.msg,{icon:5});
		}
	});		
}


