/**
 * 常用工具封装，以后有任何工具方法请集中写在此工具对象中,在任何页面中都可以通过GLOBAL_UTILS对象调用其中的方法
 * @author xuwanghcneg
 * @modify 2019.4
 */
var GLOBAL_UTILS = {
    //时间日期工具
    dateUtils:{
        /**
         * 时间格式化处理-日期转字符串
         * @param date 日期类型变量
         * @param fmt 格式 yyyy-MM-dd HH:mm:ss
         * @returns {*}
         */
        dateFormat: function(date, fmt){
            var o = {
                "M+": date.getMonth() + 1,                 //月份
                "d+": date.getDate(),                    //日
                "H+": date.getHours(),                   //小时
                "m+": date.getMinutes(),                 //分
                "s+": date.getSeconds(),                 //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        },
        /*
         * 方法作用：【取传入日期是星期几】
         * 使用方法：dateUtil.nowFewWeeks(new Date());
         * @param date{date} 传入日期类型
         * @returns {星期四，...}
         */
        nowFewWeeks:function(date){
            if(date instanceof Date){
                var dayNames = new Array("星期天","星期一","星期二","星期三","星期四","星期五","星期六");
                return dayNames[date.getDay()];
            } else{
                throw "Param error,date type!";
            }
        },
        /*
         * 方法作用：【字符串转换成日期】
         * 使用方法：dateUtil.strTurnDate("2010-01-01");
         * @param str {String}字符串格式的日期，传入格式：yyyy-mm-dd(2015-01-31)
         * @return {Date}由字符串转换成的日期
         */
        strTurnDate:function(str){
            var   re   =   /^(\d{4})\S(\d{1,2})\S(\d{1,2})$/;
            var   dt;
            if (re.test(str)){
                dt = new Date(RegExp.$1,RegExp.$2 - 1,RegExp.$3);
            }
            return dt;
        },
        /*
         * 方法作用：【计算2个日期之间的天数】
         * 传入格式：yyyy-mm-dd(2015-01-31)
         * 使用方法：dateUtil.dayMinus(startDate,endDate);
         * @startDate {Date}起始日期
         * @endDate {Date}结束日期
         * @return endDate - startDate的天数差
         */
        dayMinus:function(startDate, endDate){
            if(startDate instanceof Date && endDate instanceof Date){
                var days = Math.floor((endDate-startDate)/(1000 * 60 * 60 * 24));
                return days;
            }else{
                throw "Param error,need date type!";
            }
        },
        /**
         * 获取N天前的日期
         * @param day 从今天起的第几天  0代表今天  -1代表一天前 1代表一天后
         * @returns {string}
         */
        getDay:function (day){
            var today = new Date();
            var targetday_milliseconds=today.getTime() + 1000 * 60 * 60 * 24 * day;
            today.setTime(targetday_milliseconds); //注意，这行是关键代码
            var tYear = today.getFullYear();
            var tMonth = today.getMonth();
            var tDate = today.getDate();
            tMonth = GLOBAL_UTILS.dateUtils.__doHandleMonth(tMonth + 1);
            tDate = GLOBAL_UTILS.dateUtils.__doHandleMonth(tDate);
            return tYear + "-" + tMonth + "-" + tDate;
        },
        __doHandleMonth: function(month){
            var m = month;
            if(month.toString().length == 1){
                m = "0" + month;
            }
            return m;
        }
    },
    //字符串工具
    stringUtils:{
        /*
         * 判断字符串是否不为空
         * @param str 传入的字符串
         * @returns {}
         */
        isNotEmpty:function(str){
            if(str != null && str.length > 0 && str != ''){
                return true;
            }else{
                return false;
            }
        },
        /**
         * 判断字符串是否不为空 为null undefined 或者 只有空字符/制表符等均为空
         * @param str
         * @returns {Boolean}
         */
        isNotBlank:function(str){
            if (str == null || str == undefined || str == '' || String(str).replace(/(^\s*)|(\s*$)/g, "").length == 0) {
                return false;
            }
            return true;
        },
        /**
         * 判断字符串是否为空 为null undefined 或者 只有空字符/制表符等均为空
         * @param str
         * @returns {Boolean}
         */
        isBlank:function(str){
            if (str == null || str == undefined || str == '' || String(str).replace(/(^\s*)|(\s*$)/g, "").length == 0) {
                return true;
            }
            return false;
        },
        /*
         * 判断字符串是否为空
         * @param str 传入的字符串
         * @returns {}
         */
        isEmpty:function(str){
            if(str != null && str.length > 0 && str != ''){
                return false;
            }else{
                return true;
            }
        },
        /*
         * 判断两个字符串是否相同
         * @param str1
         * @param str2
         * @returns {Boolean}
         */
        isEquals:function(str1,str2){
            if(str1 == str2){
                return true;
            }else{
                return false;
            }
        },
        /*
         * 忽略大小写判断字符串是否相同
         * @param str1
         * @param str2
         * @returns {Boolean}
         */
        isEqualsIgnorecase:function(str1,str2){
            if(str1.toUpperCase() == str2.toUpperCase()){
                return true;
            }else{
                return false;
            }
        },
        /**
         * 判断是否是中文
         * @param str
         * @returns {Boolean}
         */
        isChine:function(str){
            var reg = /^([u4E00-u9FA5]|[uFE30-uFFA0])*$/;
            if(reg.test(str)){
                return false;
            }
            return true;
        },
        /**
         * 字符串转boolean
         * @param str
         * @returns {boolean}
         */
        toBoolean:function(str){
            if (str == 'false') {
                return false;
            }
            return Boolean(str);
        },
        /**
         * 获取字符串真实长度，中文字符占两位
         * @param str
         * @return {number}
         */
        getLength: function (str) {
            var realLength = 0, len = str.length, charCode = -1;
            for (var i = 0; i < len; i++) {
                charCode = str.charCodeAt(i);
                if (charCode >= 0 && charCode <= 128) realLength += 1;
                else realLength += 2;
            }
            return realLength;
        }
    },
    //数字工具
    numberUtils:{
        /**
         * 判断是否为数字
         * @param val
         * @returns {boolean}
         */
        isNum:function(val){
            if(val === "" || val == null){
                return false;
            }
            if(!isNaN(val)){
                return true;
            }else{
                return false;
            }
        },
        /**
         * float类型数字的减法操作，防止精度溢出
         * @param float1
         * @param float2
         * @returns {number}
         */
        floatSub:function(float1, float2) {
            return (parseFloat(float1) * 1000 - parseFloat(float2) * 1000) / 1000;
        },
        /**
         * 将浮点数四舍五入，取小数点后2位
         * @param x
         * @return {number}
         */
        toDecimal:function (x) {
            let f = parseFloat(x);
            if (isNaN(f)) {
                return x;
            }
            f = Math.round(x*100)/100;
            return f;
        },
        /**
         * 强制保留2位小数，如：2，会在2后面补上00.即2.00
         * @param x
         * @return {*}
         */
        toDecimal2: function (x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return false;
            }
            var f = Math.round(x*100)/100;
            var s = f.toString();
            var rs = s.indexOf('.');
            if (rs < 0) {
                rs = s.length;
                s += '.';
            }
            while (s.length <= rs + 2) {
                s += '0';
            }
            return s;
        }
    },
    //事件工具
    eventUtils:{
        /**
         * 取消事件的相关默认操作
         * @param event
         * @returns {boolean}
         */
        cancelHandler:function(event){
            var event=event||window.event;//兼容IE

            //取消事件相关的默认行为
            if(event.preventDefault)    //标准技术
                event.preventDefault();
            if(event.returnValue)    //兼容IE9之前的IE
                event.returnValue=false;
            return false;    //用于处理使用对象属性注册的处理程序
        }
    },
    //json工具
    jsonUtils:{
        /**
         * 转换json字符串为key=value模式
         * @param jsonStr
         */
        formatJsonStr: function(jsonStr){
            if (GLOBAL_UTILS.stringUtils.isNotBlank(jsonStr) && GLOBAL_UTILS.jsonUtils.isJsonStr(jsonStr)) {
                let str = [];
                $.each(JSON.parse(jsonStr), function(i, n){
                    str.push(i + '=' + n);
                });

                return str.join(';');
            }
            return '';
        },
        /**
         * 判断是否为json格式字符串
         * @param str
         */
        isJsonStr:function(str){
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
            return false;
        },
        /**
         * 获取json对象的长度
         * @param jsonObject
         * @returns {number}
         */
        len:function(jsonObject){
            if (typeof jsonObject != 'object') return 1;
            var jsonLength = 0;
            var fun = function(obj) {
                for (var item in obj) {
                    if (typeof item != 'object') {
                        jsonLength ++;
                    } else {
                        fun(item);
                    }
                }
            }
            fun(jsonObject);
            return jsonLength;
        },
        /**
         * 比较两个json对象是否相同
         * @param objA
         * @param objB
         * @returns {*}
         * @constructor
         */
        CompareJsonObj:function(objA, objB){
            if(!GLOBAL_UTILS.jsonUtils.isObj(objA) || !GLOBAL_UTILS.jsonUtils.isObj(objB)) return false; //判断类型是否正确
            if(GLOBAL_UTILS.jsonUtils.getLength(objA) != GLOBAL_UTILS.jsonUtils.getLength(objB)) return false; //判断长度是否一致
            return GLOBAL_UTILS.jsonUtils.__CompareObj(objA, objB, true); //默认为true
        },
        /**
         * 判断是否为json对象
         * @param object
         * @returns {*|boolean}
         */
        isObj:function(object){
            return object && typeof(object) == 'object' && Object.prototype.toString.call(object).toLowerCase() == "[object object]";
        },
        /**
         * 获取json对象对一层属性的个数
         * @param object
         * @returns {number}
         */
        getLength:function(object){
            var count = 0;
            for(var i in object) count++;
            return count;
        },
        __CompareObj:function(objA, objB, flag){
            if(GLOBAL_UTILS.jsonUtils.getLength(objA) != GLOBAL_UTILS.jsonUtils.getLength(objB)) return false;
            for(var key in objA) {
                if(!flag) //flag为false，则跳出整个循环
                    break;
                if(!objB.hasOwnProperty(key)) {//是否有自身属性，而不是继承的属性
                    flag = false;
                    break;
                }
                if(!GLOBAL_UTILS.arrayUtils.isArray(objA[key])) { //子级不是数组时,比较属性值
                    if (GLOBAL_UTILS.jsonUtils.isObj(objA[key])) {
                        if (GLOBAL_UTILS.jsonUtils.isObj(objB[key])) {
                            if(!flag) //这里跳出循环是为了不让递归继续
                                break;
                            flag = GLOBAL_UTILS.jsonUtils.__CompareObj(objA[key], objB[key], flag);
                        } else {
                            flag = false;
                            break;
                        }
                    } else {
                        if(String(objB[key]) != String(objA[key])) { //排除数字比较的类型差异
                            flag = false;
                            break;
                        }
                    }
                } else {
                    if(!GLOBAL_UTILS.arrayUtils.isArray(objB[key])) {
                        flag = false;
                        break;
                    }
                    var oA = objA[key],
                        oB = objB[key];
                    if(oA.length != oB.length) {
                        flag = false;
                        break;
                    }
                    for(var k in oA) {
                        if(!flag) //这里跳出循环是为了不让递归继续
                            break;
                        flag = GLOBAL_UTILS.jsonUtils.__CompareObj(oA[k], oB[k], flag);
                    }
                }
            }
            return flag;
        }
    },
    //数组工具
    arrayUtils:{
        /**
         * 判断是否为数组
         * @param arr
         * @returns {boolean}
         */
        isArray:function(arr){
            return Object.prototype.toString.call(arr)=='[object Array]';
        },
        /**
         * 数组indexOf方法
         * @param arr
         * @param val
         * @returns {number}
         */
        indexOf:function(arr, val){
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == val) return i;
            }
            return -1;
        },
        /**
         * 删除数组中指定属性
         * @param arr
         * @param val
         */
        removeValue:function(arr, val){
            let index = arr.indexOf(val);
            if (index > -1) {
                arr.splice(index, 1);
            }
        },
        /**
         * 删除数组中空值
         * @param arr
         * @returns
         */
        clearNullArr:function(arr){
            if (arr == null) return false;
            for(var i=0,len=arr.length;i<len;i++){
                if(!arr[i]||arr[i]==''||arr[i] === undefined){
                    arr.splice(i,1);
                    len--;
                    i--;
                }
            }
            return arr;
        }
    },
    // //set集合工具
    // setUtils: {
    //     /**
    //      * 判断一个set是否为另外一个set的父集合
    //      * @param set 父
    //      * @param subset 子集
    //      * @returns {boolean}
    //      */
    //     isSuperset: function(set, subset){
    //         for (var elem of subset) {
    //             if (!set.has(elem)) {
    //                 return false;
    //             }
    //         }
    //         return true;
    //     },
    //     /**
    //      * 两个set合集
    //      * @param setA
    //      * @param setB
    //      * @returns {Set<any>}
    //      */
    //     union: function(setA, setB){
    //         var _union = new Set(setA);
    //         for (var elem of setB) {
    //             _union.add(elem);
    //         }
    //         return _union;
    //     },
    //     /**
    //      * 两个set的交集
    //      * @param setA
    //      * @param setB
    //      * @returns {Set<any>}
    //      */
    //     intersection: function(setA, setB){
    //         var _intersection = new Set();
    //         for (var elem of setB) {
    //             if (setA.has(elem)) {
    //                 _intersection.add(elem);
    //             }
    //         }
    //         return _intersection;
    //     },
    //     /**
    //      * 两个set的不同元素集合
    //      * @param setA
    //      * @param setB
    //      * @returns {Set<any>}
    //      */
    //     difference:function(setA, setB){
    //         var _difference = new Set(setA);
    //         for (var elem of setB) {
    //             _difference.delete(elem);
    //         }
    //         return _difference;
    //     }
    // },
    //ajax工具，是对jquery的ajax方法进一步封装
    ajaxUtils: {
        /**
         * 调用ajax的get方法
         * @param url url地址
         * @param successFn 调用成功后的回调，是指返回code=0的情况下
         * @param isAsync 是否需要异步发送，默认为true
         * @param data 发送数据
         * @param loadingTips 是否需要开启loading，传入字符串为loading的提示文件
         */
        ajaxGet: function(url, successFn, failFn, isAsync, data, loadingTips){
            var async = isAsync == false ? false : true;
            if (GLOBAL_UTILS.stringUtils.isNotBlank(loadingTips)) {
                GLOBAL_UTILS.layuiUtils.layerLoading(true, loadingTips);
            }
            $.ajax({
                async: async,
                url: url,
                type: "GET",
                cache: false,
                //contentType: "application/json",
                data:data,
                datatype: "json",
                success: function (data) {
                    GLOBAL_UTILS.layuiUtils.layerLoading(false);

                    if (data.returnCode == RETURN_CODE.NO_LOGIN) {
                        top.layer.alert(data.msg || '你还未登录，点击确认跳转到登陆页！', {title: '提示', icon: 5}, function(index){
                            if (GLOBAL_UTILS.stringUtils.isNotBlank(top.backUrl)) {
                                top.window.location.href = 'http://' + top.backUrl;
                            } else {
                                top.window.location.href = 'login.html';
                            }
                            layer.close(index);
                        });
                        return;
                    }

                    if (data.returnCode == RETURN_CODE.SUCCESS) {
                        typeof successFn === 'function' && successFn(data);
                    } else {
                        let flag;
                        typeof failFn === 'function' && (flag = failFn(data));
                        if (flag == null || flag) {
                            top.layer.alert(data.msg || '请求出错, 请稍后再试!', {title:'提示', icon: 5});
                        }
                    }
                }
            });
        },
        /**
         * 调用ajax的post方法
         * @param url
         * @param data 发送数据
         * @param successFn 调用成功后的回调，是指返回code=0的情况下
         * @param isAsync  是否需要异步发送，默认为true
         * @param loadingTips 是否需要开启loading，传入字符串为loading的提示文件
         */
        ajaxPost:function(url, data, successFn, failFn, isAsync, loadingTips){
            var async = isAsync == false ? false : true;
            if (GLOBAL_UTILS.stringUtils.isNotBlank(loadingTips)) {
                GLOBAL_UTILS.layuiUtils.layerLoading(true, loadingTips);
            }
            $.ajax({
                async: async,
                url: url,
                data: JSON.stringify(data),
                type: "POST",
                cache: false,
                //contentType: "application/json",
                dataType: "json",
                success: function (data) {
                    GLOBAL_UTILS.layuiUtils.layerLoading(false);

                    if (data.code == RETURN_CODE.NO_LOGIN) {
                        top.layer.alert(data.msg || '你还未登录，点击确认跳转到登陆页！', {title: '提示', icon: 5}, function(index){
                            if (GLOBAL_UTILS.stringUtils.isNotBlank(top.backUrl)) {
                                top.window.location.href = 'http://' + top.backUrl;
                            } else {
                                top.window.location.href = 'login.html';
                            }
                            layer.close(index);
                        });
                        return;
                    }

                    if (data.code == RETURN_CODE.SUCCESS) {
                        typeof successFn === 'function' && successFn(data);
                    } else {
                        let flag;
                        typeof failFn === 'function' && (flag = failFn(data));
                        if (flag == null || flag) {
                            top.layer.alert(data.msg || '请求出错, 请稍后再试!', {title:'提示', icon: 5});
                        }
                    }
                }
            });
        },
        /**
         * 模拟表单提交同步方式下载文件  能够弹出保存文件对话框
         * @param url 下载地址
         * @param parameter 其他参数
         */
        downloadFileByForm:function(url, parameter) {
            var form = $("<form></form>").attr("action", url).attr("method", "post");
            if (parameter != null) {
                $.each(parameter, function(k, v){
                    form.append($("<input></input>").attr("type", "hidden").attr("name", k).attr("value", v));
                })
            }
            form.appendTo('body').submit().remove();
        }
    },
    //layui工具
    layuiUtils:{
        /**
         * loading加载效果
         * @param flag true开启  false关闭
         * @param msg  提示语，默认为'正在获取信息...'
         * @param shade 遮罩层的阴影度 默认为0.4
         * @returns
         */
        layerLoading:function (flag, msg, shade) {
            if (flag) {
                shade == null && (shade = 0.4);
                window.globalLoadIndex = top.layer.msg(msg || '正在获取信息...', {icon:16, time:99999999, shade:shade});
            } else {
                window.globalLoadIndex != null && (top.layer.close(window.globalLoadIndex));
            }
        },
        /**
         * layer关闭自身frame，
         */
        closeSlefFrame:function () {
            let index = parent.layer.getFrameIndex(window.name);
            if (index != null) top.layer.close(index);
        },
        /**
         * 自动调整layer自身高度以自适应屏幕高度
         * @param layer 指定的layer对象
         * @param index 当前layer窗口的index
         * @param currHeight 当前默认高度
         */
        autoLayerHeight: function(layer, index, currHeight){
            let maxHeight = parent.$(document).height();
            if (maxHeight < currHeight) {
                currHeight = maxHeight * 0.9;
                layer.style(index, {
                    height: currHeight
                });
            }
        },
        /**
         * layer.open时自动计算宽高
         * @param layer
         * @param options
         */
        layerOpenWithAutoArea: function(layer, options) {
            let maxHeight = top.$(document).height();
            let maxWidth = top.$(document).width();
            if (options.area == null) {
                options.area = ['90%', '90%'];
            } else {
                if (maxHeight < parseInt(options.area[1])) {
                    options.area[1] = "90%";
                }
                if (maxWidth < parseInt(options.area[0])) {
                    options.area[0] = "90%";
                }
            }
            let index = layer.open(options);
            return index;
        },
        /**
         * layui通用表单验证，是对layer.form的verify的扩展
         * @param layuiForm 对应页面的form对象
         */
        formVerify:function(layuiForm){
            layuiForm.verify({
                //字符长度验证
                len: function(value, item){
                    let setting = $(item).attr('verify-data');
                    if (value != null && GLOBAL_UTILS.stringUtils.isNotBlank(setting)) {
                        setting = JSON.parse(setting);
                        if (GLOBAL_UTILS.stringUtils.getLength(value) < parseInt(setting.min) ) {
                            return "至少输入" + setting.min + "个字符";
                        }
                        if (GLOBAL_UTILS.stringUtils.getLength(value) > parseInt(setting.max)) {
                            return "超过最大字符数 " + setting.max;
                        }
                    }
                },
                //数字大小验证
                num:function(value, item){
                    if (!GLOBAL_UTILS.numberUtils.isNum(value)) {
                        return "必须输入数字";
                    }
                    let setting = $(item).attr('verify-data');
                    if (GLOBAL_UTILS.stringUtils.isNotBlank(setting)) {
                        setting = JSON.parse(setting);
                        if (parseInt(value) < parseInt(setting.min) ) {
                            return "请输入不小于" + setting.min + "的值";
                        }
                        if (parseInt(value) > parseInt(setting.max)) {
                            return "请输入不大于" + setting.max + "的值";
                        }
                    }
                },
                //正则验证
                regular:function(value, item){
                    let re = $(item).attr('verify-data');
                    let msg = $(item).attr('verify-msg');
                    if (GLOBAL_UTILS.stringUtils.isNotBlank(re)) {
                        let reObj = new RegExp(re,'gi');
                        if (!reObj.test(value)) {
                            return msg || "输入值不符合规范";
                        }
                    }
                }
            });
        },
        /**
         * 自定义单个组件的表单验证：对于不能通过form组件的submit事件来触发表单验证的，可以使用此方法来单个执行验证
         * @param domObj组件 jquery对象
         * @return true或者false
         */
        customItemVerify:function(domObj){
            let value = domObj.val();
            let verifyType = domObj.attr('lay-verify');
            if (GLOBAL_UTILS.stringUtils.isBlank(verifyType)) {
                return true;
            }
            let setting = domObj.attr('verify-data');
            verifyType = verifyType.split('|');
            let result = true;
            $.each(verifyType, function(i, n){
                let fun = GLOBAL_UTILS.validateUtils[n];
                if (typeof fun == 'function') {
                    result = fun(value, setting);
                    if (result != true) {
                        return false;
                    }
                }
            });
            if (result == true) {
                domObj.removeClass('layui-form-danger');
                return true;
            }
            domObj.addClass('layui-form-danger');
            domObj.focus();
            top.layer.msg(result, {icon: 5, shift: 6, time: 1400});
            return false;
        }
    },
    //验证工具，一般验证正确会返回true,否则返回提示语
    validateUtils:{
        required: function(v){
            if (GLOBAL_UTILS.stringUtils.isNotBlank(v)) {
                return true;
            }
            return '必填项不能为空';
        },
        len:function(v, setting){
            if (GLOBAL_UTILS.stringUtils.isNotBlank(setting)) {
                setting = JSON.parse(setting);
                if (GLOBAL_UTILS.stringUtils.getLength(v) < parseInt(setting.min) ) {
                    return "至少输入" + setting.min + "个字符";
                }
                if (GLOBAL_UTILS.stringUtils.getLength(v) > parseInt(setting.max)) {
                    return "超过最大字符数" + setting.max;
                }
                return true;
            }
            return true;
        },
        num:function(v, setting){
            if (!GLOBAL_UTILS.numberUtils.isNum(v)) {
                return "必须输入数字";
            }
            if (GLOBAL_UTILS.stringUtils.isNotBlank(setting)) {
                setting = JSON.parse(setting);
                if (parseInt(v) < parseInt(setting.min) ) {
                    return "请输入不小于" + setting.min + "的值";
                }
                if (parseInt(v) > parseInt(setting.max)) {
                    return "请输入不大于" + setting.max + "的值";
                }
                return true;
            }
            return true;
        },
        regular: function(v, setting){
            if (GLOBAL_UTILS.stringUtils.isNotBlank(setting)) {
                let reObj = new RegExp(setting,'gi');
                if (!reObj.test(v)) {
                    return "输入值不符合规范";
                }
                return true;
            }
            return true;
        },
        phone: function(v){
            if (/^1\d{10}$/.test(v)) {
                return true;
            }
            return "请输入正确的手机号";
        },
        email: function(v){
            if (/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(v)) {
                return true;
            }
            return "邮箱格式不正确";
        },
        identity: function(v){
            if (/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(v)) {
                return true;
            }
            return "请输入正确的身份证号";
        }
    },
    //项目工具
    yiUtils:{
        /**
         * 在加载完数据后，执行此方法，可以让iframe的高度适应内容大小
         * @param docObj 对应页面的body jquery对象
         */
        resizeIframe:function(jquery) {
            $("iframe", document).css("height", jquery('body').height() + 50 + "px");
        }
    }
};