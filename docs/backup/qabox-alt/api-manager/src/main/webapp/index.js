//方便子页面调用
// 加载系统菜单
var loadMenuFun;
// 加载项目列表
var loadProjectFun;

//菜单信息
var menuJson;

//////////////////////////////////可全局用到的信息
//当前登陆用户的权限信息列表
var currentUserPermissionList;
//首页地址
var homeUrl;
//用户id
var userId;
//第三方token登陆
var token;
//退出时返回的页面
var backUrl;

// 当前项目ID
var currentProjectId = getCookie("projectId") == null ? DEFAULT_PROJECT_ID : getCookie("projectId");

//全部的handlebars模版，子页面调用
var templates;
//全部的html模板，子页面调用
var htmls;
//////////////////////////////////
$(document).ready(function() {
    //打开welcome页面
    $('iframe').attr("src", "welcome.html");

    /***********通过外部token登录的方式打开首页****************/
    token = GetQueryString("token");
    backUrl = GetQueryString("backUrl");
    backUrl = (backUrl == null ? "login.html" : "http://" + backUrl);
    if (token != null && token != "") {
        $(".redirect-to-page").hide();
    }
    /******************************************************/

    $(".openIframeNew").on("click",function(){
        Hui_admin_tab(this);
    });


    /*******************初始化子iFrame中的模板和远程子页面html代码***************************/
    //模板id
    templates = initHandlebarsTemplate();


    //页面名
    htmls = loadChildrenHtml(["interfaceParameter-viewTree",
        "messageScene-test",
        "messageScene-validateFullJson",
        "messageScene-validateKeyword",
        "interface-batchAddParameter",
        "role-power",
        "showDataPoolUseSample",
        "viewWindow"]);
    /*****************************************************/
});


/*************预先编译子iframe中的模板***************/
function initHandlebarsTemplate () {
    var loadingIndex = layer.msg('正在初始化系统...', {icon:16, shade:0.45, time:999999999});
    var templates = {};
    $("#templates-page").load("./resource/template/customTemplate.htm", function() {
        $("#templates-page > script").each(function(i, n){
            templates[$(n).attr('id')] = Handlebars.compile($(n).html());
        });
        layer.close(loadingIndex);
    });
    return templates;
}

/*****加载子页面代码到内存*******/
function loadChildrenHtml (options) {
    if (options == null || typeof options != 'object') {
        return false;
    }
    var htmls = {};
    $.each(options, function (i, n) {
        $("#children-page").append('<div id="' + n + '"></div>');
        $("#" + n).load("./resource/template/" + n + ".htm", function() {
            htmls[n] = $("#" + n).html();
            $("#" + n).html('');
        });
    });
    return htmls;
}

/********************加载菜单***************************/
loadMenuFun = function() {
    GLOBAL_UTILS.ajaxUtils.ajaxGet(REQUEST_URL.MENU.GET_USER_MENUS, function(data) {
        menuJson = data.data;
        var systemSwitchDom = $('#system-type-name').siblings('ul');
        var defaultTypeKey = null;
        $.each(menuJson, function(systemKey, content){
            defaultTypeKey == null && (defaultTypeKey = systemKey);
            systemSwitchDom.append('<li><a system-type="' + systemKey + '" href="javascript:void(0)" class="switch-system"><i class="Hui-iconfont ' + content.icon + '"></i>' + content.name + '</a></li>');
        });

        $("#menu-page").load("./resource/template/menuTemplate.htm", function(){
            templates['menu-template'] = Handlebars.compile($("#menu-page > script").html());
            switchMenu(getCookie('menuType') == null ? defaultTypeKey : getCookie('menuType'), true, defaultTypeKey);
            $(".switch-system").click(function(){
                var name = switchMenu($(this).attr('system-type'));
                name && (layer.msg('已切换至系统：' + name, {time:2000}));
            });
        })
    }, function(data){
        return false;
    });
}

/**
 * 加载项目列表
 */
loadProjectFun = function() {
    GLOBAL_UTILS.ajaxUtils.ajaxGet(REQUEST_URL.PROJECT_INFO.LIST_USER_PROJECTS, function(data) {
        let projectList = data.data;
        let systemSwitchDom = $('#project-name').siblings('ul');
        let thisName = null;
        $.each(projectList, function(index, project){
            if (project.projectId == currentProjectId) {
                thisName = project.projectName;
            }
            systemSwitchDom.append('<li><a project-id="' + project.projectId + '" href="javascript:void(0)" class="switch-project">' + project.projectName + '</a></li>');
        });
        if (!thisName) {
            currentProjectId =  projectList[0].projectId;
            thisName = projectList[0].projectName;
        }
        $('#project-name').html(thisName + '<i class="Hui-iconfont">&#xe6d5;</i>');

        $(".switch-project").click(function(){
            let projectId = $(this).attr('project-id');
            let projectName = $(this).text();
            if (projectId) {
                layer.confirm('确定切换到项目 【' + projectName + '】 吗？（点击确定将会刷新当前页面）', {icon: 0, title: '警告'}, function(index){
                    setCookie("projectId", projectId);
                    location.reload()
                    layer.close(index);
                });
                //$('#project-name').html(projectName + '<i class="Hui-iconfont">&#xe6d5;</i>');
                //currentProjectId = projectId;
                //(layer.msg('已切换至项目：' + projectName, {time:2000}));
            }
        });
    })
}

/**
 * 根据配置切换菜单显示
 * @param menuType
 * @param init
 * @param defaultTypeKey
 * @returns {*}
 */
function switchMenu(menuType, init, defaultTypeKey) {
    if (menuType == null) {
        menuType = "interface";
    }
    if (menuType == getCookie('menuType') && !init) {
        return null;
    }
    if (menuJson[menuType] == null) {
        menuType = defaultTypeKey;
    }
    $("#menu").html(templates['menu-template'](menuJson[menuType]['menu']));
    //重新初始化菜单效果
    $.Huifold(".menu_dropdown dl dt",".menu_dropdown dl dd","fast",3,"click");
    //第一个菜单为打开状态
    $("#menu dt:eq(0)").click();
    //如果切换的是其他的系统菜单，则关闭全部的Tab选项卡
    //$("#min_title_list li i").trigger("click");
    setCookie("menuType", menuType);
    $("#system-type-name").html(menuJson[menuType]['name'] + '<i class="Hui-iconfont">&#xe6d5;</i>');
    return menuJson[menuType]['name'];
}



/***********修改密码*********/
function to_changePasswd() {
    layer.prompt({
        formType: 1,
        value: '',
        title: '验证旧密码'
    }, function(value, index, elem){
        layer.close(index);
        $.post(REQUEST_URL.USER.VERIFY_PASSWORD, {password: value}, function (data) {
            if (data.returnCode == 0) {
                layer.prompt({
                    formType: 1,
                    value: '',
                    title: '请输入新密码'
                }, function(value1, index1, elem){
                    layer.close(index1);
                    layer.confirm('确定要将密码修改为"' + value1 + '"吗？', {icon:0, title:'警告'}, function(index2) {
                        layer.close(index2);
                        $.get(REQUEST_URL.USER.MODIFY_PASSWORD,{password:value1}, function(data) {
                            if (data.returnCode == 0) {
                                layer.msg('密码修改成功,设置自动登录的需要重新输入密码!', {icon:1, time:1500});
                            } else {
                                layer.alert(data.msg, {icon: 5});
                            }
                        });

                    });
                });
            }else{
                layer.alert(data.msg, {icon:5});
            }
        });
    });
}

/***********手动点击logout会清除登录信息cookie*********/
function to_logout(){
    $.get(REQUEST_URL.LOGIN.LOGOUT,function(data){
        if(data.returnCode==0){
            clearCookie("username");
            clearCookie("password");
            window.location.href = backUrl;
        }
    });
}

/***********切换用户*********/
function to_changeUser(){
    var loginHtml ='<form class="form form-horizontal" action="index.html" method="post" style="width:485px;">'+
        '<div class="row cl">'+
        '<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>'+
        '<div class="formControls col-xs-8">'+
        '<input id="username"  type="text" placeholder="账户" class="input-text size-L">'+
        '</div>'+
        '</div>'+
        '<div class="row cl">'+
        '<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>'+
        '<div class="formControls col-xs-8">'+
        '<input id="password"  type="password" placeholder="密码" class="input-text size-L">'+
        '</div>'+
        '</div>'+
        '<div class="row cl">' +
        '<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe613;</i></label>' +
        '<div class="formControls col-xs-8"> ' +
        '<input id="verifyCode"  type="text" placeholder="验证码" class="input-text size-L" style="width:200px;"> ' +
        '<img src="" style="width:100px;height:40px;cursor:pointer;"/>' +
        '</div>' +
        '</div>' +
        '<div class="row cl">'+
        '<div class="formControls col-xs-8 col-xs-offset-3">'+
        '<input id="loginBtn" type="button" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">&nbsp;'+
        '<input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">'+
        '<p id="loginTip" style="color: red;"></p>'+
        '</div>'+
        '</div>'+
        '</form>';

    var index = layer.open({
        title:'切换用户',
        type: 1,
        area: ['569px', '317px'],
        content: loginHtml,
        success:function(layero, index){
            $("#loginBtn").click(userLogin);
            //监听键盘回车事件
            $(layero).keyup(function (event) {
                var keyCode = event.which;
                if(keyCode == 13){
                    $("#loginBtn").click();
                }
            });

            $("#verifyCode").siblings('img').click(function(){
                var that = this;
                $.get(REQUEST_URL.LOGIN.CREATE_VERIFY_CODE, function(data){
                    if (data.returnCode == 0) {
                        $(that).attr('src', 'data:image/png;base64,' + data.data);
                    }
                });
            });

            $("#username").trigger("focus").trigger("select");
            $("#verifyCode").siblings('img').click();
        }
    });
}

/**切换用户时的登录页面*/
function userLogin(){
    var username = $("#username").val();
    var password = $("#password").val();
    var verifyCode = $("#verifyCode").val();
    var tipMsg = $("#loginTip");
    if(username != "" && username != null && password != "" && password != null && verifyCode != "" && verifyCode != null){
        $.post(REQUEST_URL.LOGIN.LOGIN,{
            username:username,
            password:password,
            verifyCode: verifyCode
        },function(data){
            if(data.returnCode==0){
                location.reload();
            }else if (data.returnCode==2){
                $("#verifyCode").siblings('img').click();
                layer.alert(data.msg, {icon: 4});
            }else {
                $("#verifyCode").siblings('img').click();
                tipMsg.text(data.msg);
            }
        });
    }else{
        tipMsg.text("请填写完整再提交登录");
    }
}

/**
 * 获取地址栏参数
 * @param name
 * @returns
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURIComponent(r[2]);
    return null;
}

/**
 * 判断是否为json格式字符串
 * @param str
 */
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    console.log('It is not a string!')
}