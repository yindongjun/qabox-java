//设置jQuery Ajax全局的参数
$.ajaxSetup({
    error: function (jqXHR, textStatus, errorThrown) {
        layer.closeAll('dialog');
        switch (jqXHR.status) {
            case(403):
                let needrestart = jqXHR.getResponseHeader("needrestart");
                if (needrestart != null && needrestart == "true") {
                    top.layer.alert("系统升级完成,请联系管理重启服务器!", {offset: '60px', title: "升级完成提示!", icon: 0, closeBtn: 0}, function(index, layero){
                        return;
                    });
                    return;
                }

                break;
            default:
                break;
        }
    }
});