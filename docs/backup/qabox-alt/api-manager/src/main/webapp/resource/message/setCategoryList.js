var templateParams = {
		btnTools:[{
			type:"primary",
			size:"M",
			id:"add-object",
			iconFont:"&#xe600;",
			name:"添加目录或测试集"
		},{
			type:"success",
			size:"M",
			id:"show-all-set",
			iconFont:"&#xe667;",
			name:"测试集列表"
		}],
		formControls:[
		{
			edit:true,
			label:"ID",  	
			objText:"setIdText",
			input:[{	
				hidden:true,
				name:"setId"
				}]
		},            
		{
			required:true,
			label:"名称",  
			input:[{	
				name:"setName"
				}]
		},
		{
			required:true,
			label:"类型",  	
			select:[{	
				name:"parented",
				option:[{
					value:"0",
					text:"目录"
				},{
					value:"1",
					text:"测试集"
				}]
				}]
		},
		{
			required:true,
			label:"父目录",  	
			select:[{	
				name:"parentId",
				option:[{
					value:"0",
					text:"最顶级"
				}]
				}]
		},
		{	
			required:true,
			label:"状态",  			
			select:[{	
				name:"status",
				option:[{
					value:"0",
					text:"可用",
					selected:"selected"
				},{
					value:"1",
					text:"禁用"
				}]
				}]
		},
		{
			edit:true,
			name:"user.userId"
							
		},
		{
			label:"备注",  
			textarea:[{	
				name:"mark"
				}]
		},
		{
			name:"projectInfo.projectId",
			value: top.currentProjectId
		},
		{
			 name:"createTime",
			 value:new Date().Format("yyyy-MM-dd hh:mm:ss")
			
		 }]		
	};
var currentSetId;
var eventList = {
	"#add-object":function() {
		publish.renderParams.editPage.modeFlag = 0;		
		var callback = publish.renderParams.editPage.renderCallback;
		layer_show("增加目录或测试集", editHtml, editPageWidth, editPageHeight.add, 1, function() {
			callback();
		});
		publish.init();			
	},
	"#show-all-set":function() {
		$("#current-set-name").html('<span class="c-gray en">&gt;</span> 测试集列表');
		$("#set-list-iframe").attr("src", "testSet.html");
	    $("#set-list-iframe").click();
	    currentSetId = null;
	    $('li').attr("style", "");
	}
};

var mySetting = {
		eventList:eventList,
		renderType:"edit",
		templateParams:templateParams,
		editPage:{
       	 	editUrl:REQUEST_URL.TEST_SET.EDIT,
			getUrl:REQUEST_URL.TEST_SET.GET,
			rules:{
				setName:{
					required:true,
					minlength:2,
					maxlength:100
				}				
			},
       	 	renderCallback:function(obj){
       	 		$.get(REQUEST_URL.TEST_SET.GET_CATEGORY_NODES + "?projectId=" + top.currentProjectId, function(json) {
       	 			if (currentNodeType != null) {
       	 				$('#parented').val(currentNodeType);
					}
       	 			if (json.returnCode == 0) {
       	 				$.each(json.data, function(i, node) {
       	 					createOption(node, $("#parentId"), "");
							if (currentFolderId != null) {
                                $('#parentId').val(currentFolderId);
							}
       	 				});
	       	 			if (obj != null) {
	       	 				$("#parented").parents(".row").hide();
	           	 			$("#parentId").val(obj.parentId);
	           	 		}
       	 			} else {
       	 				layer.alert(json.msg, {icon:5});
       	 			}
       	 		});
       	 		
       	 		
       	 	},
       	 ajaxCallbackFun:function (data) {
       		 if (data.returnCode == 0) {
       			 if (data.data.parented == 1) { //如果是测试集代表编辑的是当前测试集信息
       				 //更新导航条测试集名称
       				$("#current-set-name").html('<span class="c-gray en">&gt;</span> 当前测试集 - ' + data.data.setName);
       			 }
       			 createNodeTree();//更新目录树结构
       			 layer.closeAll('page');
       		 } else {
       			 layer.alert(data.msg, {icon:5});
       		 }
       	 }
		},
		templateCallBack:function(df) {
            editPageHeight.add += 40;
			createNodeTree();
   	 		df.resolve();
   	 	}	
	};


$(function(){			
	publish.renderParams = $.extend(true, publish.renderParams, mySetting);
	publish.init();
});
var currentFolderId ;
var currentNodeType;
/**
 * 父目录选择下拉框
 * @param node
 * @param selectObj
 * @param sign
 * @returns {Boolean}
 */
function createOption(node, selectObj, sign) {
	//不能将当前节点移动到自己节点下和自己的子节点下
	if (node.id == $("#setId").val()) {		
		return false;
	}
	if (!node.parented) {
		return false;
	}
	selectObj.append('<option value="' + node.id + '">' + sign + "╟&nbsp;" + node.name + '</option>');
	if (node.children != null && node.children.length > 0) {
		$.each(node.children, function(i, n) {
			createOption(n, selectObj, sign + "&nbsp;&nbsp;");
		});
	}
}

var contextMenuSetting = { //菜单样式
    menuStyle: {
        border: '2px solid #000'
    }, //菜单项样式
    itemStyle: {
        fontFamily: 'verdana',
        //backgroundColor: 'green',
        //color: 'white',
        border: 'none',
        padding: '1px'
    }, //菜单项鼠标放在上面样式
    itemHoverStyle: {
        //color: 'blue',
        //backgroundColor: 'red',
        border: 'none'
    }, //事件
    bindings: {
        'item_1': function(t) { //添加子目录
			var id = $(t).attr('id');
            currentFolderId = id;
            currentNodeType = 0;
            publish.renderParams.editPage.modeFlag = 0;
            var callback = publish.renderParams.editPage.renderCallback;
            layer_show("增加目录或测试集", editHtml, editPageWidth, editPageHeight.add, 1, function() {
                callback();
            });
            publish.init();
        },
        'item_2': function(t) { //添加测试集
            var id = $(t).attr('id');
            currentFolderId = id;
            currentNodeType = 1;
            publish.renderParams.editPage.modeFlag = 0;
            var callback = publish.renderParams.editPage.renderCallback;
            layer_show("增加目录或测试集", editHtml, editPageWidth, editPageHeight.add, 1, function() {
                callback();
            });
            publish.init();
        },
        'item_3': function(t) { //编辑
            var id = $(t).attr('id');
            publish.renderParams.editPage.modeFlag = 1;
            publish.renderParams.editPage.objId = id;
            layer_show("编辑目录信息", editHtml, editPageWidth, editPageHeight.edit, 1);
            publish.init();
        },
        'item_4': function(t) { //删除
            var id = $(t).attr('id');
            delSet('确认删除此目录吗？<br><span class="c-red">删除之后该目录下所有的测试集和子目录将会移动到被删除目录的上层目录下</span>', id);
        }
    }
};


/**
 * 生成左侧目录树
 */
function createNodeTree () {
	$.get(REQUEST_URL.TEST_SET.GET_CATEGORY_NODES + "?projectId=" + top.currentProjectId, function(json) {
			$(".page-container").spinModal(false); 	 			
			if (json.returnCode == 0) {
				$("#setTree").html('');
	 			layui.use('tree', function(){
	 			  layui.tree({
	 			  elem: '#setTree' //传入元素选择器
	 			  ,nodes: json.data
	 			  ,callback:function (nodes) {
	 				 currentSetId != null && $("#" + currentSetId) 
	 				 	&&　 $("#" + currentSetId).parent("li").attr("style", "background-color:#C6C6C6");
	 				 $("#setTree .layui-tree-branch").parent('a').each(function(){
                         	var objEvt = $._data($(this)[0], "events");
                         	if (objEvt && !objEvt["contextmenu"]) {
                                $(this).contextMenu('treeMenu', contextMenuSetting);
                            }
					 });
	 			  }
	 			  ,click: function(elem, node){//node即为当前点击的节点数据	   	 				
		   	 		    if (!node.parented) {
		   	 		    	$("#set-list-iframe").attr("src", "setScene.html?setId=" + node.id + "&setName=" + node.name + "&flag=true");
		   	 		    	$("#set-list-iframe").click();
		   	 		    	$('li').attr("style", "");		   	 		    	
		   	 		    	$(elem).attr("style", "background-color:#C6C6C6");
		   	 		    	currentSetId = node.id;
		   	 		    	$("#current-set-name").html('<span class="c-gray en">&gt;</span> 当前测试集 - ' + node.name)
		   	 		    }
	 			  }  
	 			});
	 			});
			} else {
				layer.alert(json.msg, {icon:5});
			}
		});
}

/**
 * 目录操作
 * @param id
 */
function opSetFolder(id) {
	layer.confirm('请选择你需要的操作:<br>点击其他位置关闭对话框', {
		title:'提示',
		btn:['删除目录','编辑信息', '返回'],
		anim:5,
		shadeClose:true,
		btn3:function(index) {
			layer.close(index);
		}
	}, function(index) {
		delSet('确认删除此目录吗？<br><span class="c-red">删除之后该目录下所有的测试集和子目录将会移动到被删除目录的上层目录下</span>', id);
		layer.close(index);
	}, function(index) {
		publish.renderParams.editPage.modeFlag = 1;
		publish.renderParams.editPage.objId = id;
		layer_show("编辑目录信息", editHtml, editPageWidth, editPageHeight.edit, 1);
		publish.init();	
		layer.close(index);
	});
}

/**
 * 删除目录或测试集
 * @returns
 */
function delSet (tip, id, callback) {
	layer.confirm(tip, {title:'警告', anim:5}, function(index) {	
				$(".page-container").spinModal(); 
				$.post(REQUEST_URL.TEST_SET.DEL, {id:id}, function(json) {
					if (json.returnCode == 0) {
						createNodeTree();
						callback && callback(json);
						layer.msg('删除成功!', {icon:1, time:1800});
					} else {
						layer.alert(json.msg, {icon: 5});
					}
				});
				layer.close(index);					
	});
}