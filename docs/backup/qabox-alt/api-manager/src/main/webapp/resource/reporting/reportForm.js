/**
 * echarts对象和查询参数
 */
var kindObj = {
    sceneReport: {
        echartObj:null,
        params: {
            messageSceneId: null,
            rangDate: GLOBAL_UTILS.dateUtils.getDay(-30) + " ~ " + GLOBAL_UTILS.dateUtils.getDay(0),
            includeScope: [1,2,3,4,5]
        }
    },
    testReport: {
        echartObj:null,
        params: {
            rangDate: GLOBAL_UTILS.dateUtils.getDay(-30) + " ~ " + GLOBAL_UTILS.dateUtils.getDay(0),
            includeScope: [1,2,3]
        }
    },
    dailyAdd: {
        echartObj:null,
        params: {
            rangDate: GLOBAL_UTILS.dateUtils.getDay(-30) + " ~ " + GLOBAL_UTILS.dateUtils.getDay(0)
        }
    }
}

var eventList = {
    '#btn-tools > a': function () { //切换视图
        if ($(this).hasClass('btn-default')) {
            $(this).removeClass('btn-default').addClass('btn-primary').siblings('a').removeClass('btn-primary').addClass('btn-default');
            $('#' + $(this).attr('view-data')).show().siblings('.mt-20').hide();
        }
    },
    '#choose-result-scene':function() {//选择一个接口场景
        layer_show("选择一个接口场景", "../advanced/chooseMessageScene.html?callbackFun=chooseScene&notMultiple=true", null, null, 2);
    },
    '#scene-report-view a':function() {//查询场景报告视图数据查询
        createSceneReportChartView(false);
    },
    '#test-report-view a':function() {//测试报告视图数据查询
        createTestReportChartView(false);
    },
    '#daily-add-view a':function() {//每日新增视图数据查询
        createDailyAddChartView(false);
    }
};

var mySetting = {
    eventList:eventList,
    userDefaultRender:false,
    userDefaultTemplate:false,
    customCallBack:function(params){
        createDataPick('sceneReport');
        createDataPick('testReport');
        createDataPick('dailyAdd');

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        })
        $('input[type="checkbox"]').on('change', function() {
            let type = $(this).attr('data-kind');
            let val = $(this).attr('data-val');
            if ($(this).is(":checked")) {
                kindObj[type]['params']['includeScope'].push(parseInt(val));
            } else {
                GLOBAL_UTILS.arrayUtils.removeValue(kindObj[type]['params']['includeScope'], parseInt(val));
            }
        });
    }

};

$(function(){
    publish.renderParams = $.extend(true, publish.renderParams, mySetting);
    publish.init();
});


/**
 * 选择场景的回调
 * @param obj
 * @returns {boolean}
 */
function chooseScene (obj) {
    if (obj == null) {
       return false;
    }
    if (Object.prototype.toString.call(obj) === '[object Array]') {
        layer.msg('暂时不支持多场景比对,请期待后续版本!', {icon: 5, time: 1600});
        return false;
    }

    $('#choose-result-scene').val(obj.interfaceName + '-' + obj.messageName + '-' + obj.sceneName);
    kindObj.sceneReport.params.messageSceneId = obj.messageSceneId;
}


/**
 * 创建日期选择器
 * @param id
 */
function createDataPick (id) {
    //初始化日期选择器
    laydate.render({
        elem: '#' + id, //指定元素
        type: 'date',
        range: '~',
        value:kindObj[id]['params']['rangDate'],
        done:function(value, date, endDate){
            kindObj[id]['params']['rangDate'] = value;
        }
    });
}


/**
 * 创建场景报告视图echarts
 * @param ifFirst
 * @returns {boolean}
 */
function createSceneReportChartView (ifFirst) {
    if (kindObj.sceneReport.params.messageSceneId == null) {
        layer.msg('请先选择一个接口场景', {time: 1600});
        return false;
    }

    $.post(REQUEST_URL.REPORT_FORM.GET_SCENE_REPORT_RESPONSE_TIME_TREND_DATA
        , {messageSceneId: kindObj.sceneReport.params.messageSceneId, rangDate: kindObj.sceneReport.params.rangDate
            , includeScope: kindObj.sceneReport.params.includeScope.join(",")}, function(json) {
            if (json.returnCode == RETURN_CODE.SUCCESS) {
                if (kindObj.sceneReport.echartObj == null) {
                    kindObj.sceneReport.echartObj = echarts.init(document.getElementById('scene-report-charts'), 'shine');
                    window.addEventListener("resize",function(){
                        kindObj.sceneReport.echartObj.resize();
                    });
                }
                kindObj.sceneReport.echartObj.setOption(createSceneReportChartOption(json.data.trendData));
                if (!ifFirst) {
                    if (json.data.trendData.opTime.length == 0) {
                        layer.msg('暂无数据!', {time: 1600});
                    } else {
                        layer.msg('加载成功!', {icon: 1, time: 1500});
                    }
                }
            } else {
                layer.alert(json.msg, {icon: 5});
            }
        });
}


/**
 * 创建场景报告echarts配置信息
 * @param data
 * @returns {{yAxis: {axisLabel: {formatter: string}, axisPointer: {snap: boolean}, type: string}, xAxis: {data: Array, type: string, boundaryGap: boolean}, grid: {left: string, bottom: string, right: string, containLabel: boolean}, series: {data: Array, name: string, type: string, smooth: boolean}[], tooltip: {axisPointer: {type: string}, trigger: string}, toolbox: {feature: {saveAsImage: {show: boolean}, dataView: {show: boolean, readOnly: boolean}}}, title: {x: string, text: string}}}
 */
function createSceneReportChartOption (data) {
    let option = {
        title: {
            text: '响应时间趋势',
            x:'left'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        grid: {
            left: '2%',
            right: '6%',
            bottom: '4%',
            containLabel: true
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                saveAsImage: {show: true}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            data: []
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} ms'
            },
            axisPointer: {
                snap: true
            }
        },
        series: [
            {
                name:'响应时间(毫秒)',
                type:'line',
                smooth: true,
                data: []
            }
        ]
    };

    if (data.opTime.length > 0) {
        option.series[0].data = data.responseTime;
        option.xAxis.data = data.opTime;
    }

    return option;
}

/**
 * 创建测试报告视图echarts
 * @param ifFirst
 */
function createTestReportChartView (ifFirst) {
    $.post(REQUEST_URL.REPORT_FORM.GET_TEST_REPORT_CHART_RENDER_DATA
        , {rangeDate: kindObj.testReport.params.rangDate, includeScope: kindObj.testReport.params.includeScope.join(",")}
        , function(json) {
            if (json.returnCode == RETURN_CODE.SUCCESS) {
                if (kindObj.testReport.echartObj == null) {
                    kindObj.testReport.echartObj = echarts.init(document.getElementById('test-report-charts'), 'shine');
                    kindObj.testReport.echartObj.on('click', function(params){
                        window.open("../message/reportView.html?reportId=" + params.data[4]);
                    });
                    window.addEventListener("resize",function(){
                        kindObj.testReport.echartObj.resize();
                    });
                }
                kindObj.testReport.echartObj.setOption(createTestReportChartOption(json.data.overview));
                if (!ifFirst) {
                    if ($.isEmptyObject(json.data.overview)) {
                        layer.msg('暂无数据!', {time: 1600});
                    } else {
                        layer.msg('加载成功!', {icon: 1, time: 1500});
                    }
                }
            } else {
                layer.alert(json.msg, {icon: 5});
            }
        });
}

/**
 * 创建测试报告echarts视图配置
 * @param data
 * @returns {{backgroundColor, yAxis: {axisLabel: {formatter: string}, min: number, max: number, name: string, splitLine: {lineStyle: {type: string}}, type: string}, xAxis: {name: string, splitLine: {lineStyle: {type: string}}, type: string}, grid: {left: string, bottom: string, right: string, containLabel: boolean}, legend: {top: number, data: Array, right: number}, series: {data: Array, symbolSize: number, type: string}[], toolbox: {feature: {saveAsImage: {show: boolean}, dataView: {show: boolean, readOnly: boolean}}}, tooltip: {formatter: (function(*): string), axisPointer: {type: string}, trigger: string}}}
 */
function createTestReportChartOption (data) {
    let option = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#F5FAFE'
        }]),
        // title: {
        //     text: '测试报告通过率趋势',
        //     subtext: '最近一个月',
        //     textAlign: 'left'
        //
        // },
        grid: {
            left: '2%',
            right: '6%',
            bottom: '4%',
            containLabel: true
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                saveAsImage: {show: true}
            }
        },
        tooltip: {
            trigger:'item',
            formatter:function(params){
                return params.data[0] + ' ' + params.data[2] + ' <br>通过率: <strong>' + params.data[1] + '%</strong>';
            },
            axisPointer: {
                type: 'cross'
            }
        },
        // dataZoom: [{
        //     type: 'inside'
        // }, {
        //     type: 'slider'
        // }],
        legend: {
            right: 60,
            top:4,
            data: []
        },
        xAxis: {
            name: '测试时间',
            type:"category",
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            },
        },
        yAxis: {
            name: '通过率',
            type:"value",
            min: 0.00,
            max: 100.00,
            axisLabel: {
                formatter: '{value}%'
            },
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        series: [
            {
                data: [],
                type: 'scatter',
                symbolSize: 14
            }
        ]
    };

    if (!$.isEmptyObject(data)) {
        option.series = [];
    }
    $.each(data, function(setId, info) {
        option.legend.data.push(info[0][2]);
        option.series.push({
            name: info[0][2],
            data: info,
            type: 'scatter',
            symbolSize: 14
        });

    });

    return option;
}


/**
 * 创建每日新增数据echarts视图
 * @param ifFirst
 */
function createDailyAddChartView (ifFirst) {
    $.post(REQUEST_URL.REPORT_FORM.GET_DAILY_ADD_CHART_RENDER_DATA, {rangeDate: kindObj.dailyAdd.params.rangDate}, function(json) {
        if (json.returnCode == RETURN_CODE.SUCCESS) {
            if (kindObj.dailyAdd.echartObj == null) {
                kindObj.dailyAdd.echartObj = echarts.init(document.getElementById('daily-add-charts'), 'shine');
                window.addEventListener("resize",function(){
                    kindObj.dailyAdd.echartObj.resize();
                });
            }
            kindObj.dailyAdd.echartObj.setOption(createDailyAddChartOption(json.data.stat));

            if (!ifFirst) {
                if (json.data.stat.time == null || json.data.stat.time.length == 0) {
                    layer.msg('暂无数据!', {time: 1600});
                } else {
                    layer.msg('加载成功!', {icon: 1, time: 1500});
                }
            }
        } else {
            layer.alert(json.msg, {icon: 5});
        }
    });
}

/**
 * 创建每日新增数据echarts配置
 * @param data
 * @returns {{backgroundColor, yAxis: {name: string, type: string}, xAxis: {data: Array, name: string, type: string, boundaryGap: boolean}, legend: {data: string[], top: string}, grid: {left: string, bottom: string, right: string, containLabel: boolean}, series: *[], tooltip: {trigger: string}, toolbox: {feature: {saveAsImage: {show: boolean}, dataView: {show: boolean, readOnly: boolean}}}}}
 */
function createDailyAddChartOption (data) {
    let option = {
        backgroundColor: new echarts.graphic.RadialGradient(0.3, 0.3, 0.8, [{
            offset: 0,
            color: '#f7f8fa'
        }, {
            offset: 1,
            color: '#F5FAFE'
        }]),
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['新增接口','新增报文','新增场景','新增报告'],
            top: '6%'
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                saveAsImage: {show: true}
            }
        },
        grid: {
            left: '2%',
            right: '6%',
            bottom: '4%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            name: '时间',
            boundaryGap: false,
            data: []
        },
        yAxis: {
            name: '新增数',
            type: 'value'
        },
        series: [
            {
                name:'新增接口',
                type:'line',
                data:[]
            },
            {
                name:'新增报文',
                type:'line',
                data:[]
            },
            {
                name:'新增场景',
                type:'line',
                data:[]
            },
            {
                name:'新增报告',
                type:'line',
                data:[]
            }
        ]
    };

    option.xAxis.data = data.time;
    if (data.data.length > 0) {
        for (let i = 0;i < data.data.length;i++) {
            option.series[i]['data'] = data.data[i];
        }
    }

    return option;
}

