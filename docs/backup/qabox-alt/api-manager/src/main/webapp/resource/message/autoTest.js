var configData;
var modeFlag = 0;//0为全量测试

var isTesting = false;//当前是否有测试任务?
var reportId;
var count;

var eventList = {
	'#choose-test-set':function() {
		$.get(REQUEST_URL.TEST_SET.LIST_MY_SETS + "?projectId=" + top.currentProjectId, function(json) {
			if (json.returnCode == 0) {
				layer_show("所有测试集", templates["show-test-set"](json.data), null, null, 1);
			} else {
				layer.alert(json.msg, {icon:5});
			}
		});
	},
	'#prepare-total-test':function() {
		batchTest(0);
	},
	"#update-option":function() {
		updateTestOptions();
	},
	"#reset-option":function() {
		resetOptions();
	}	
};


var mySetting = {
		eventList:eventList,
		userDefaultRender:false,    
   	 	userDefaultTemplate:false,
   	 	customCallBack:function(params){
   	 		$.Huitab("#tab-system .tabBar span","#tab-system .tabCon","current","click","0");
   	 		$.get(REQUEST_URL.AUTO_TEST.GET_TEST_CONFIG_URL, function(json) {
   	 			if (json.returnCode == 0) {
   	 				configData = json.data;
   	 				resetOptions();
   	 			} else {
   	 				layer.alert("获取当前用户自动化测试配置失败：" + json.msg, {icon:5});
   	 			}
   	 		});
   	 		
   	 	}	
};

$(function(){			
	publish.renderParams = $.extend(true,publish.renderParams,mySetting);
	publish.init();
});


/*************************************************************************************************************/
function resetOptions () {
	if (configData != null) {
		$("#requestUrlFlag").val(configData.requestUrlFlag);
		$("#connectTimeOut").val(configData.connectTimeOut);
		$("#readTimeOut").val(configData.readTimeOut);
		$("#checkDataFlag").val(configData.checkDataFlag);
		$("#configId").val(configData.configId);
		$("#runType").val(configData.runType);
		$("#userId").val(configData.userId);
		$("#retryCount").val(configData.retryCount);
	}
}

//更新配置信息
function updateTestOptions(){
	var updateConfigData=$("#form-article-add").serializeArray();
	$.post(REQUEST_URL.AUTO_TEST.UPDATE_TEST_CONFIG_URL, updateConfigData, function(data){
		if(data.returnCode == 0){
			configData=data.data;
			layer.msg('更新成功',{icon:1, time:1500});
		} else {
			layer.alert("更新失败：" + data.msg, {icon:5});
		}
	});	
}

function batchTest(setId) {	
	if (isTesting) {
		layer.alert('当前已存在正在进行的测试任务,请等待测试完成!', {icon:0, title:'提示'});
		return false;
	}
	
	layer.closeAll("page");
	
	var sendTest = function() {
		$("#testTips").html('');
		$("#total-count").text('0');
		$("#current-complete-count").text('0');
		$("#current-success-count").text('0');
		$("#current-fail-count").text('0');
		$("#current-stop-count").text('0');
		$(".progress .progress-bar .sr-only").attr("style", "width:0%");
		
		isTesting = true;
		$("#testTips").append('<p>正在发送测试请求...</p>');
		$("#testTips").append('<p>正在准备测试数据...<img src="../../libs/layer/2.1/skin/default/loading-2.gif" alt="loading" /></p>');
		$.get(REQUEST_URL.AUTO_TEST.BATCH_AUTO_TEST_URL + "?setId=" + setId, function(json) {
			if (json.returnCode == 0) {
                reportId = json.data[0];
                count = json.data[1];

				$("img").remove();
				$("#testTips").append('<p>开始执行测试...<img src="../../libs/layer/2.1/skin/default/loading-2.gif" alt="loading" /></p>');
				$("#total-count").text(count);

				var intervalID = setInterval(function() {
					getProcessInfo(intervalID);
				}, 1000);
			} else {
				$("img").remove();
				$("#testTips").append('<p>准备测试过程中出现错误,请检查接口、报文、场景的完整性!</p>');
				isTesting = false;
				layer.alert(json.msg,{icon:5});
			}
		});	
	}
	$("#testTips").html('');
	if (configData.checkDataFlag == "0") {
		$("#testTips").append('<p>正在检查测试数据...<img src="../../libs/layer/2.1/skin/default/loading-2.gif" alt="loading" /></p>');
		$.get(REQUEST_URL.AUTO_TEST.CHECK_DATA_URL, {setId:setId}, function(json) {
			$("img").remove();
			if (json.returnCode == 0) {
				if (json.data == 0) {
					sendTest();
				} else {
					layer.confirm('当前还有<span class="c-red"><strong>' + json.data + '</strong></span>个测试场景未有足够的测试数据进行测试<br>是否需要手动添加这些测试数据?', {title:'提示', icon:'0', btn:['手动添加', '直接测试']}
					, function(index) {
						layer.close(index);
						$(this).attr("data-title", "手动添加测试数据");
						$(this).attr("_href", "resource/message/messageScene.html?setId=" + setId + "&addDataFlag=0");
						Hui_admin_tab(this);
					}
					, function(index) {
						layer.close(index);
						sendTest();
					});
				}
			} else {
				layer.alert(json.msg, {icon:5});
				$("#testTips").append('<p>在检查测试数据的过程中发生了错误:' + json.msg + '</p>');
			}
		});
	} else {
		sendTest();
	}
	
	
}

function getProcessInfo(intervalID) {
	$.get(REQUEST_URL.REPORT.GET + "?reportId=" + reportId, function(json) {
		if (json.returnCode == 0) {
			$("#current-complete-count").text(json.data.sceneNum);
			$("#current-success-count").text(json.data.successNum);
			$("#current-fail-count").text(json.data.failNum);
			$("#current-stop-count").text(json.data.stopNum);
			var per = ((json.data.sceneNum /  count).toFixed(2)) * 100;
			if ((600 * per) > $(".progress .progress-bar .sr-only").width()) {
				$(".progress .progress-bar .sr-only").attr("style", "width:" + per + "%");
			}			
			
			if (json.data.sceneNum == count) {
				isTesting = false;
				clearInterval(intervalID);
				$("img").remove();
				$("#testTips").append('<p>测试完成,请至测试报告模块查看测试详细测试报告!</p>');
			}
		} else {
			layer.alert(data.msg,{icon:5});
		}
	});
}