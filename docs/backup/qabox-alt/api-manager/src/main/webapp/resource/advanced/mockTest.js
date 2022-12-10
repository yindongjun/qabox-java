var templateParams = {
    tableTheads: ["名称", "mock地址", "协议", "入参验证", "出参设定", "已请求次数", "当前状态", "创建时间", "创建用户", "备注", "操作"],
    btnTools: [{
        type: "primary",
        size: "M",
        id: "add-object",
        iconFont: "&#xe600;",
        name: "添加Mock接口"
    }, {
        type: "danger",
        size: "M",
        iconFont: "&#xe6bf;",
        name: "批量操作",
        children: [
            {
                name: '批量启用',
                id: 'batch-enable-mock'
            },
            {
                name: '批量禁用',
                id: 'batch-disable-mock'
            },
            {
                name: '批量删除',
                id: 'batch-del-mock'
            }
        ]
    }],
    formControls: [{
        edit: true,
        label: "ID",
        objText: "mockIdText",
        input: [{
            hidden: true,
            name: "mockId"
        }]
    },
        {
            required: true,
            label: "名称",
            input: [{
                name: "mockName"
            }]
        },
        {
            required: true,
            label: "协议",
            select: [{
                name: "protocolType",
                option: function () {
                    let arr = [];
                    $.each(MESSAGE_MOCK_TYPE, function (i, n) {
                        arr.push({
                            value: n,
                            text: i
                        });
                    });
                    return arr;
                }()
            }]
        },
        {
            label: "mock路径",
            input: [{
                name: "mockUrl",
                placeholder: "只在协议为HTTP/WebSocket时有效,请以斜杠/起始"
            }]
        },
        {
            label: "当前状态",
            select: [{
                name: "status",
                option: [{
                    value: "1",
                    text: "禁用"
                }, {
                        value: "0",
                        text: "启用"
                    }]
            }]
        },
        {
            name: "createTime",
            value: new Date().Format("yyyy-MM-dd hh:mm:ss")

        },
        {
            name: "user.userId"

        },
        {
            name:"projectInfo.projectId",
            value: top.currentProjectId
        },
        {
            name: "callCount",
            value: "0"
        },
        {
            name: "requestValidate"
        },
        {
            name: "responseMock"
        },
        {
            label: "备注",
            textarea: [{
                name: "mark"
            }]
        },

    ]
};

var columnsSetting = [
              {
			  	  	"data":null,
			  	  	"render":function(data, type, full, meta){                       
			          return checkboxHmtl(data.mockName, data.mockId, "selectMock");
			  }},
			  {"data":"mockId"},
			  ellipsisData("mockName"),
			  {
				  
				  "data":"mockUri",
				  "className":"ellipsis",
				  "render":function(data, type, full, meta){
				      if (full.status == '0') {
                          return '<a href="' + data + '" target="_blank" title="' + data + '">' + data + '</a>';
                      } else {
				          return '未启用';
                      }

				  }  
			  },
			  {
				   	"data":"protocolType",
					"render":function(data) {
						var option = {};

					    $.each(MESSAGE_MOCK_TYPE, function(i, n) {
                            option[i] = {
                                btnStyle:"success",
                                status:n
                            }
                        });

          		  	    return labelCreate(data, option);
			  }},
			  {
				  "data":null,
				  "render":function(){
					  return '<button class="btn btn-primary radius size-M setting-request-validate">设置</button>';
				  }
			  },
			  {
				  "data":null,
				  "render":function(){
					  return '<button class="btn btn-primary radius size-M setting-response-mock">设置</button>';
				  }
			  },
			  {
				"data":"callCount"  
			  },			  
			  {
				   	"data":"status",
					"render":function(data, type, full, meta) {
						// var option = {
            		  	// 		"0":{
            		  	// 			btnStyle:"success",
            		  	// 			status:"启用"
            		  	// 			},
        		  		// 		"1":{
        		  		// 			btnStyle:"danger",
        		  		// 			status:"禁用"
        		  		// 			}
            		  	// };
            		  	// return labelCreate(data, option);

                        var checked = '';
                        if(data == "0") {checked = 'checked';}
                        return '<div class="switch size-MINI" data-on-label="启用" data-off-label="禁用"><input type="checkbox" ' + checked + ' value="' + full.mockId + '"/></div>';
                    }},
			  ellipsisData("createTime"),
			  ellipsisData("user.realName"),
			  {
				    "data":"mark",
				    "className":"ellipsis",
				    "render":function(data, type, full, meta){
				    	if (data != "" && data != null) {
			  		    	return '<a href="javascript:;" onclick="showMark(\'' + full.mockName + '-\', \'mark\', this);"><span title="' + data + '">' + data + '</span></a>';
					    	}
					    return "";
				    }
			  },
			  {
				  	"data":null,
                    "render":function(data, type, full, meta){                    		                        	
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
}];

var currentSettingConfig = null;
var eventList = {
		".setting-request-validate":function(){ //打开入参验证配置界面
			var data = table.row( $(this).parents('tr') ).data();
			$.get(REQUEST_URL.INTERFACE_MOCK.GET, {id:data.mockId}, function(json){
				if (json.returnCode == 0) {
					currentSettingConfig = JSON.parse(json.data.requestValidate);
					layer_show(json.data.mockUri + "  Mock入参验证设定", templates["mock-request-validate-setting"](currentSettingConfig), 840, 450, 1, function(layero, index){
						layero.find("#method").val(currentSettingConfig.method && (currentSettingConfig.method.toUpperCase()));
						layero.find("#messageType").val(currentSettingConfig.messageType && (currentSettingConfig.messageType.toUpperCase()));
						layero.find("#mockId").val(data.mockId);
						layero.find("#layerIndex").val(index);
					}, null, null, {shadeClose:false});
				} else {
					layer.alert(json.msg, {icon:5});
				}
			});			
		},	
		".validate-setting-btn-add":function(){//添加该类型的验证参数
			var that = this;
			var title = "添加节点验证规则";
			var templateName = 'mock-request-validate-rule-config';
			var height = 635;
			if ($(this).parents('.formControls[group-name]').attr('mock-type') == 'mock') {
				title = "添加节点值生成规则";
				templateName = 'mock-response-rule-config';
				height = 400;
			}
			
			layer_show(title, templates[templateName]({})
						, 740, height, 1, function(layero, index){
					layero.find("#layerIndex").val(index);
					layero.find("#group-name").val($(that).parents('.formControls[group-name]').attr("group-name"));
			}, null, null, {shadeClose:false});
		},
		".edit-validate-parameter":function(){//编辑验证节点信息
			var type = $(this).parents('.formControls[group-name]').attr('group-name');
			var arrayIndex = $(this).attr('array-id');
			var ruleConfig = currentSettingConfig[type][Number(arrayIndex)];
			
			var title = "修改节点验证规则";
			var templateName = 'mock-request-validate-rule-config';
			var height = 635;
			if ($(this).parents('.formControls[group-name]').attr('mock-type') == 'mock') {
				title = "修改节点值生成规则";
				templateName = 'mock-response-rule-config';
				height = 380;
			}
			
			layer_show(title, templates[templateName](ruleConfig)
					, 740, height, 1, function(layero, index){
				layero.find("select").each(function(i, n){
					$(n).val(ruleConfig[$(n).attr('id')]);
				});
				layero.find("#array-id").val(arrayIndex);
				layero.find("#layerIndex").val(index);
				layero.find("#group-name").val(type);
			}, null, null, {shadeClose:false});
		},
		".validate-setting-btn-clear":function(){//一键清除当前类型的验证参数
			var that = this;
			layer.confirm('确认清除此类型当前所有条目?', {title:'警告', icon:0}, function(index){
				currentSettingConfig[$(that).parents('.formControls[group-name]').attr("group-name")] = [];
				$(that).siblings('span').remove();
				$(that).siblings('i').remove();
				layer.close(index);
			});
		},
		".validate-setting-btn-import":function(){//通过报文导入验证入参节点信息
			var $parent = $(this).parents('.formControls[group-name]');
			layer.prompt({
				formType : 2,
				value : '',
				maxlength:999999,
				title : '请输入入参报文',
				area : [ '500px', '300px' ]
			}, function(value, index, elem) {
				$.post(REQUEST_URL.INTERFACE_MOCK.PARSE_MESSAGE_TO_CONFIG, {message:value}, function(json){
					if (json.returnCode == 0) {
						$.each(JSON.parse(json.rules), function(i, n){
							$parent.append('<span array-id="' + currentSettingConfig['parameters'].length 
									+ '" class="label label-default radius edit-validate-parameter appoint">' 
									+ (n['path'] ? n['path'] + "." : "") + n['name']
									+ '</span><i class="Hui-iconfont del-validate-parameter appoint" style="margin-right:8px;">&#xe6a6;</i>');
							currentSettingConfig['parameters'].push(n);
						});						
						layer.close(index);
					} else {
						layer.msg(json.msg, {icon:5, time:1500});
					}
				});				
			});
		},		
		"#save-setting-validate-rule":function(){//保存更新验证或者生成节点规则
			var $layer = $(this).parents('form');
			var arrayId = $layer.find('#array-id').val();
			var groupName = $layer.find("#group-name").val();
			var ruleConfig = {};
			$layer.find('input:not(:hidden),select').each(function(i, n){
				ruleConfig[$(n).attr('id')] = $(n).val();
			});	
			var nameNew = ((ruleConfig['path'] && !isJSON('{' + ruleConfig['path'] + '}')) ? ruleConfig['path'] + "." : "") + ruleConfig['name'];
			if (arrayId == undefined || !strIsNotEmpty(arrayId)) {				
				$('div[group-name="' + groupName + '"]')
					.append('<span array-id="' + currentSettingConfig[groupName].length 
							+ '" class="label label-default radius edit-validate-parameter appoint">' 
							+ nameNew
							+ '</span><i class="Hui-iconfont del-validate-parameter appoint" style="margin-right:8px;">&#xe6a6;</i>');
				currentSettingConfig[groupName].push(ruleConfig);
			} else {
				currentSettingConfig[groupName][Number(arrayId)] = ruleConfig;
				$('div[group-name="' + groupName + '"]').find('span[array-id="' + arrayId + '"]').text(nameNew);
			}
									
			layer.close($layer.find("#layerIndex").val());
			layer.msg('保存成功!', {icon:1, time:1500});
		},
		".del-validate-parameter":function(){//删除验证或者生成节点信息
			var that = this;
			layer.confirm('确认删除此条目?', {title:'警告', icon:0}, function(index){
				delete currentSettingConfig[$(that).parents('.formControls[group-name]').attr("group-name")][Number($(that).prev('span').attr('array-id'))];
				$(that).prev('span').remove();
				$(that).remove();
				layer.close(index);
			});
		},
		"#save-setting-mock-validate":function(){//保存mock或者验证规则 通用
			var sendData = {mockId:$("#mockId").val()};
			
			$(this).parents('form').find('input:not(:hidden),select,textarea').each(function(i, n){
				currentSettingConfig[$(n).attr('id')] = $(n).val();
			});
			
			clearNullArr(currentSettingConfig["headers"]);
			clearNullArr(currentSettingConfig["querys"]);
			clearNullArr(currentSettingConfig["parameters"]);
			
			sendData[$(this).attr("setting-type")] = JSON.stringify(currentSettingConfig);
			$.post(REQUEST_URL.INTERFACE_MOCK.UPDATE_SETTING, sendData, function(json){
				if (json.returnCode == 0) {
					layer.msg('更新成功!', {icon:1, time:1500});
				} else {
					layer.alert(json.msg, {icon:5});
				}
			});		
		},		
		".setting-response-mock":function(){//打开出参模拟配置页面
			var data = table.row( $(this).parents('tr') ).data();
			$.get(REQUEST_URL.INTERFACE_MOCK.GET, {id:data.mockId}, function(json){
				if (json.returnCode == 0) {
					currentSettingConfig = JSON.parse(json.data.responseMock);
					layer_show(json.data.mockUri + "  Mock出参模拟设定", templates["mock-response-setting"](currentSettingConfig), 900, 675, 1, function(layero, index){
						layero.find("#format").val(currentSettingConfig.format);
						layero.find("#messageType").val(currentSettingConfig.messageType && (currentSettingConfig.messageType.toUpperCase()));
						layero.find("#mockId").val(data.mockId);
						layero.find("#layerIndex").val(index);
					}, null, null, {shadeClose:false});
				} else {
					layer.alert(json.msg, {icon:5});
				}
			});	
		},
		//从指定的返回示例报文中选择节点位置
		"#choose-response-message-parameter":function(){
			var exampleResponseMsg = $("#exampleResponseMsg").val();
			if (!strIsNotEmpty(exampleResponseMsg)) {
				layer.msg('没有可用的出参示例报文!', {icon:5, time:1500});
				return;
			}
			
			chooseParameterNodePath(REQUEST_URL.INTERFACE_MOCK.PARSE_MESSAGE_TO_NODES, {message:exampleResponseMsg}, {
				titleName:"选择出参模拟节点",
				isChoosePath:true, 
				notChooseTypes:["Array", "Map", "List", "Object"],
				choosenCallback:function (path) {
					var index = path.lastIndexOf(".");
					$("#name").val(path.substring(index + 1));
					$("#path").val(path.substring(0, index));
				}
			});
		},		
		"#add-object":function(){
            layer.confirm(
                '请选择新增方式:',
                {
                    title:'添加Mock接口',
                    btn:['从场景导入','手动添加'],
                    shadeClose:true,
                },function(index){
                    layer.close(index);
                    layer_show("选择需要Mock的场景"
                        , "chooseMessageScene.html?callbackFun=chooseScene&notMultiple=true&protocolType=Socket,HTTP,HTTPS,WebSocket", null, null, 2);
                },function(index){
                    layer.close(index);
                    publish.renderParams.editPage.modeFlag = 0;
                    layer_show("添加Mock接口", editHtml, editPageWidth, editPageHeight.add, 1);
                    publish.init();
                });
		},
		"#batch-op":function(){
			layer.confirm(
				'请选择你需要进行的批量操作:',
				{
					title:'批量操作',
					btn:['启用','禁用','删除'],
					shadeClose:true,
					btn3:function(index){
						layer.close(index);
						batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.DEL, "删除", null, "mockId");
					}
				},function(index){ 
					layer.close(index);
					batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.UPDATE_STATUS, "启用", null, "mockId", {status:"0"});
				},function(index){
					layer.close(index);
					batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.UPDATE_STATUS, "禁用", null, "mockId", {status:"1"});
				});
		},
        "#batch-enable-mock": function() {
            batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.UPDATE_STATUS, "启用", null, "mockId", {status:"0"});
        },
        "#batch-disable-mock": function() {
            batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.UPDATE_STATUS, "禁用", null, "mockId", {status:"1"});
        },
        "#batch-del-mock": function () {
            batchOp($(".selectMock:checked"), REQUEST_URL.INTERFACE_MOCK.DEL, "删除", null, "mockId");
        },
		".object-edit":function(){
			var data = table.row( $(this).parents('tr') ).data();
			publish.renderParams.editPage.modeFlag = 1;	
  			publish.renderParams.editPage.objId = data.mockId;
  			publish.renderParams.editPage.currentObj = data;
			layer_show("编辑Mock信息", editHtml, editPageWidth, editPageHeight.edit, 1);
			publish.init();	
		},
		".object-del":function(){
			var data = table.row( $(this).parents('tr') ).data();			
			opObj("确认要删除此mock接口吗？", REQUEST_URL.INTERFACE_MOCK.DEL, {id:data.mockId}, this, "删除成功!");
		},
		"#mock-validate-relation-setting":function(){//设定关联规则来获取入参节点内容
			var value = '{' + $("#path").val() + '}';
			var object = {};
			if (isJSON(value)) {
				object = JSON.parse(value);
			}
			layer_show("关联规则设定", templates["mock-validate-relation-keyword"](object), 700, 300, 1, null, function(index, layero){
				object = {};
				$(layero).find('input').each(function(){
					object[$(this).attr("id")] = $(this).val();
				});		
				var pathStr = JSON.stringify(object);
				$("#path").val(pathStr.substring(1, pathStr.length - 1));
				layer.close(index);
			}, null, {shadeClose:false});
		}	
};


var mySetting = {
		eventList:eventList,
		listPage:{
			listUrl:REQUEST_URL.INTERFACE_MOCK.LIST,
			tableObj:".table-sort",
			columnsSetting:columnsSetting,
			columnsJson:[0, 5, 6, 12],
            dtDrawCallback: function() {
                $('.switch')['bootstrapSwitch']();
                $('.switch input:checkbox').change(function(){
                    var flag = $(this).is(':checked');
                    var mockId = $(this).attr('value');
                    updateStatus(mockId, flag, this);
                });
            }
		},
		editPage:{
			editUrl:REQUEST_URL.INTERFACE_MOCK.EDIT,
			getUrl:REQUEST_URL.INTERFACE_MOCK.GET,
			rules:{
				mockUrl:{
					minlength:2,
					maxlength:2000,
					remote:{
						url:REQUEST_URL.INTERFACE_MOCK.CHECK_NAME,
						type:"post",
						dataType:"json",
						data: {                   
					        mockUrl: function() {
					            return $("#mockUrl").val();
					        },
					        mockId:function(){
					        	return $("#mockId").val();
					        },
                            protocolType: function() {
					            return $('#protocolType').val()
                            }
					}}
				},
				mockName:{
					required:true
				}								
			}
		},
		templateParams:templateParams	
};

$(function(){
	publish.renderParams = $.extend(true,publish.renderParams,mySetting);
	publish.init();
});


/**
 * 选择测试场景的回调
 * @param obj
 * @returns {boolean}
 */
function chooseScene (obj) {
    if (obj == null) {
        return false;
    }

    if (Object.prototype.toString.call(obj) === '[object Array]' ) {//可能是多选的数组
        layer.alert('暂时不能使用多选功能,请选择单个场景!', {title:"提示", icon:5});
        return false;
    }

    if (MESSAGE_MOCK_TYPE[obj.protocolType] == null) {
    	layer.alert('目前只支持HTTP/HTTPS/Socket/WebSocket类型的接口MOCK，请重新选择!', {title:"提示", icon:5});
    	return false;
	}

	loading(true, '正在添加...');
	$.get(REQUEST_URL.INTERFACE_MOCK.PARSE_SCENE_TO_MOCK_INFO, {messageSceneId: obj.messageSceneId, projectId: top.currentProjectId}, function(json){
		loading(false);
		if (json.returnCode == RETURN_CODE.SUCCESS) {
			layer.msg('添加成功!', {icon: 1, time: 1500})
			refreshTable();
		} else {
			layer.alert(json.msg, {icon:5});
		}
	});
}

/**
 * 更新状态
 * @param mockId
 * @param flag
 * @param obj
 */
function updateStatus (mockId, flag, obj) {
    let status = '1';
    if(flag == true){
        status = '0';
    }

    loading(true, '正在处理...');
    $.post(REQUEST_URL.INTERFACE_MOCK.UPDATE_STATUS, {mockId: mockId, status:status}, function(json) {
        loading(false);
        if (json.returnCode == RETURN_CODE.SUCCESS) {
            let uriObj = $(obj).parents('tr').children('td:eq(3)');
            if (!flag) {
                uriObj.text('未启用');
            } else {
                uriObj.html('<a href="' + json.data.mockUri + '" target="_blank" title="' + json.data.mockUri + '">' + json.data.mockUri + '</a>');
            }
            layer.msg('操作成功!', {icon: 1, time: 1500});
        } else {
            layer.alert(json.msg, {title: '提示', icon: 5});
            $(obj).click();
        }
    });

}