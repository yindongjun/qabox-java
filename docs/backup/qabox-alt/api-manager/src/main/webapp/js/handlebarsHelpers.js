/*****************************Handlebars预定义helper***********************************/
/**null转换空字符串**/
Handlebars.registerHelper('inputValue', function(value, defaultValue){
    if (value == null || value.length == 0) {
        value = defaultValue;
    }

    return value;
});

/**结果标签展示**/
Handlebars.registerHelper('resultLabelView', function(status){
    var color = "";
    var flag = "";
    if (status == "0") {
        color = "success";
        flag = "SUCCESS";
    } else if (status == "1") {
        color = "danger";
        flag = "FAIL";
    } else {
        color = "default";
        flag = "STOP";
    }

    return '<span class="label label-' + color + ' radius">' + flag + '</span>';
});

/**
 * 比对实现
 */
Handlebars.registerHelper('if_eq', function(v1, v2, opts) {
    if(v1 == v2)
        return opts.fn(this);
    else
        return opts.inverse(this);
});


/**
 * 验证路径
 */
Handlebars.registerHelper('validate_path', function(v1) {
    if (v1 && !isJSON('{' + v1 + '}')) {
        return v1 + ".";
    }

    return "";
});

