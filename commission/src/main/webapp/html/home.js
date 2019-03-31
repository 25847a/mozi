//各浆站采浆情况
var myChart = echarts.init(document.getElementById("statics"));
var option1 = {
  tooltip : {
    trigger: 'axis',
    axisPointer : {
      type : 'shadow'
    }
  },
  legend: {
    data: ['同比增长率','实际采浆','计划采浆'],
    top:'top'
  },
  xAxis:[
    {
      type : 'category',
      data : ['浆站1','浆站2','浆站3','浆站4','浆站5','浆站6','浆站7','浆站8','浆站9','浆站10','浆站11','浆站12'],
      splitLine: {
        show: true
      },
      axisTick: {
        show:false
      }
    }
  ],
  yAxis:[
    {
      type: 'value',
      scale: true,
      name: '各桨站采浆情况',
      max: 100,
      min: 0,
      splitNumber: 10,
      splitLine: {
        show: true
      },
      axisTick: {
        show:false
      }
    },
    {
      type: 'value',
      scale: true,
      max: 100,
      min: 0,
      splitNumber: 10,
      axisLabel: {
        formatter: '{value} %'
      },
      boundaryGap: [0.1, 0.2],
      axisTick: {
        show:false
      }
    }
  ],
  series: [
    {
      name:'同比增长率',
      type:'line',
      yAxisIndex:1,
      label: {
        normal: {
          show: true,
          position: 'top'
        }
      },
      itemStyle:{
        normal:{
          color:'#E8C6AB'
        }
      },
      data:[32, 33, 30, 43, 59,67,78,89,75,63,81,92]
    },
    {
      name:'实际采浆',
      type:'bar',
      barWidth:20,
      stack: 'one',
      data:[40, 50, 20, 30, 40,26,34,23, 23,34,44,39],
      //柱状颜色设置
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#5A9EF1'
            ];
            return colorList[params.dataIndex]
          }
        }
      }
    },
    {
      name:'计划采浆',
      type:'bar',
      stack: 'one',
      data:[30,40, 30, 20, 40,36,33,45,27,37,43,47],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#DB7478'
            ];
            return colorList[params.dataIndex]
          }
        }
      }
    }
  ]
};
myChart.setOption(option1);

//各区域采浆量
var myChart2 = echarts.init(document.getElementById("pine_chart"));
var option2 = {
  title: {
    text: '各区域采浆量',
    left: 'left',
    textStyle: {
      fontWeight: 'normal',
      color: '#000',
      fontSize:'12'
    }
  },
  tooltip: {
    trigger: 'item',
    formatter: "{a} <br/>{b}: {c} ({d}%)"
  },
  legend: {
    orient: 'vertical',
    x: 'center',
    y: 'bottom',
    icon: 'circle',
    data:['华中0采浆','华中1采浆','华中2采浆','华中3采浆']
  },
  color:['#5BB0F0', '#2EC8CA','#D97A80','#FFB880'],
  series: [
    {
      name:'访问来源',
      type:'pie',
      radius: ['35%', '50%'],
      avoidLabelOverlap: false,
      label: {
        normal: {
          show: false,
          position: 'center'
        },
        emphasis: {
          show: true,
          textStyle: {
            fontSize: '14',
            fontWeight: 'bold'
          }
        }
      },
      labelLine: {
        normal: {
          show: false
        }
      },
      data:[
        {value:335, name:'华中0采浆'},
        {value:310, name:'华中1采浆'},
        {value:234, name:'华中2采浆'},
        {value:135, name:'华中3采浆'},
      ]
    }
  ]
};
myChart2.setOption(option2);

var myChartRegion = echarts.init(document.getElementById("region"));
var optionRegion= {
  tooltip : {
    trigger: 'axis',
    axisPointer : {
      type : 'shadow'
    }
  },
  xAxis:[
    {
      type : 'category',
      data : ['华中','桂中','贵西','东南'],
      axisLine: {//是否显示坐标轴轴线。
        show: false
      },
      axisTick: {
        show:false
      },
      splitLine: {
        show: false
      }
    }
  ],
  yAxis:[
    {
      type: 'value',
      scale: true,
      max: 80,
      min: 0,
      boundaryGap: [0.2, 0.2],
      axisLine: {//是否显示坐标轴轴线。
        show: false
      },
      axisTick: {
        show:false
      },
      splitLine: {
        show: false
      }
    }
  ],
  series: [
    {
      name:'实际采浆',
      type:'bar',
      barWidth:30,
      data:[40, 50, 60, 70],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#589AF0'
            ];
            return colorList[params.dataIndex]
          }
        }
      }
    }
  ]
};
myChartRegion.setOption(optionRegion);

var myChart3 = echarts.init(document.getElementById("line_chart"));
var option3= {
  tooltip : {
    trigger: 'axis',
    axisPointer : {
      type : 'shadow'
    }
  },
  xAxis:[
    {
      type : 'category',
      data : ['1月','2月','3月','4月','5月','6月'],
      axisLine: {//是否显示坐标轴轴线。
        show: false
      },
      axisTick: {
        show:false
      },
      splitLine: {
        show: false
      }
    }
  ],
  yAxis:[
    {
      type: 'value',
      scale: true,
      max: 80,
      min: 0,
      boundaryGap: [0.2, 0.2],
      axisLine: {//是否显示坐标轴轴线。
        show: false
      },
      axisTick: {
        show:false
      },
      splitLine: {
        show: false
      }
    },
    {
      type: 'value',
      scale: true,
      max: 100,
      min: 0,
      axisLabel: {
        formatter: '{value} %'
      },
      boundaryGap: [0.1, 0.2],
      axisLine: {//是否显示坐标轴轴线。
        show: false
      },
      axisTick: {
        show:false
      },
      splitLine: {
        show: false
      },
      axisLabel : {
        formatter: function(){
          return "";
        }
      }
    }
  ],

  series: [
    {
      name:'同比增长率',
      type:'line',
      yAxisIndex:1,
      data:[32, 33, 30, 63, 56,64],
      itemStyle:{
        normal:{
          color:'#37C5C5'
        }
      }
    },
    {
      name:'实际采浆',
      type:'bar',
      barWidth:8,
      data:[40, 50, 60, 70, 80,26],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#BAA5E6'
            ];
            return colorList[params.dataIndex]
          }
        }
      }
    },
    {
      name:'计划采浆',
      type:'bar',
      barWidth:8,
      data:[30,40, 50, 20, 40,36],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#57A6F3'
            ];
            return colorList[params.dataIndex]
          }
        }
      }
    }
  ]
};
myChart3.setOption(option3);

/*天气*/
function findWeather() {
  var cityUrl = 'http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js';
  $.getScript(cityUrl, function(script, textStatus, jqXHR) {
    var citytq = remote_ip_info.city ;// 获取城市
    var url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php?code=js&city=" + citytq + "&day=0&dfc=3";
    $.ajax({
      url : url,
      dataType : "script",
      scriptCharset : "gbk",
      success : function(data) {
        var _w = window.SWther.w[citytq][0];
        var _f= _w.f1+"_0.png";
        if(new Date().getHours() > 17){
          _f= _w.f2+"_1.png";
        }
        var img = "<img src='http://i2.sinaimg.cn/dy/main/weather/weatherplugin/wthIco/20_20/" +_f
          + "' />";
        var tq = "<div class='citytq'>"+citytq +"</div>"
              + "<div class='situation'>"
              +   img
              +   "<span>"+ _w.s1 + _w.t1 + "℃～" + _w.t2 + "℃</span>"
              + "</div>"
              + "<div class='wind'>"+ _w.d1 + _w.p1 + "级</div>";

        $('#weather').html(tq);
      }
    });
  });
}

findWeather();


/*报废率*/
var myChart4 = echarts.init(document.getElementById("line_container1"));
var option4 = {
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data:['警示报废率','平均报废率'],
    icon: 'line'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['桨站1','桨站2','桨站3','桨站4','桨站5','桨站6','桨站7','桨站8','桨站9','桨站10','桨站11',,'桨站12'],
    splitLine: {
      show: true
    },
    axisTick: {
      show:false
    }
  },
  yAxis: {
    type: 'value',
    splitLine: {
      show: true
    },
    axisTick: {
      show:false
    }
  },
  series: [
    {
      name:'警示报废率',
      type:'line',
      itemStyle:{
        normal:{
          color:'#37C5C5'
        }
      },
      data:[120, 132, 101, 134, 90, 230, 210,140,120,134,140,192,122]
    },
    {
      name:'平均报废率',
      type:'line',
      itemStyle:{
        normal:{
          color:'#E8C6AB'
        }
      },
      data:[220, 182, 191, 234, 290, 330, 310,230,340,210,233,244,110]
    }

  ]
};
myChart4.setOption(option4);


/*
tab点击*/
$(".tab_box a").click(function(){
  $(this).addClass("on").siblings().removeClass("on");
});

/*新浆员发展情况*/
var myChart5 = echarts.init(document.getElementById("line_container2"));
var option5 = {
  legend: {
    data: ['同比增长率', '新浆员数']
  },
  tooltip: {},
  xAxis: {
    data: ["地名1","地名2","地名3","地名4","地名5","地名6","地名7","地名8","地名9","地名10","地名11","地名12"],
    silent: false,
    axisLine: {onZero: true},
    splitLine: {show: true},
    splitArea: {show: false},
    axisTick: {
      show:false
    }
  },
  yAxis: [
    {
      type: 'value',
      scale: true,
      max: 400,
      min: -200,
      splitArea: {
        show: false
      },
      splitLine: {
        show: true
      },
      axisTick: {
        show:false
      },
      boundaryGap: [0.1, 0.2]
    },
    {
      type: 'value',
      scale: true,
      max: 40,
      min: -20,
      axisLabel: {
        formatter: '{value} %'
      },
      boundaryGap: [0.1, 0.2]
    }

  ],
  series: [
    {
      name: '同比增长率',
      type: 'line',
      yAxisIndex:1,
      itemStyle:{
        normal:{
          color:'#5BB2EF'
        }
      },
      data: [-19, -12, -14, 34, 9, -13, 19,17,14,15,14,19]
    },
    {
      name: '新浆员数',
      type: 'bar',
      barWidth:20,
      data:[-120, 132, 101, 134, 90, 230, 210,140,120,134,140,192],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#DF737A'
            ];
            return colorList[params.dataIndex]
          }
        }
      }

    }

  ]
};
myChart5.setOption(option5);



/*不同权限用户界面*/
/*检疫期报废率*/
var myScrapRate = echarts.init(document.getElementById("scrap_rate"));
var optionScrapRate = {
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data:['警示报废率','平均报废率'],
    icon: 'line'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: ['1月','2月','3月','4月','5月','6月'],
    splitLine: {
      show: false
    },
    axisTick: {
      show:false
    }
  },
  yAxis: {
    type: 'value',
    max: 6,
    min: 0,
    splitLine: {
      show: true
    },
    axisTick: {
      show:false
    }
  },
  series: [
    {
      name:'警示报废率',
      type:'line',
      itemStyle:{
        normal:{
          color:'#37C5C5'
        }
      },
      data:[2, 3, 4, 3.4, 4.9, 2.8]
    },
    {
      name:'平均报废率',
      type:'line',
      itemStyle:{
        normal:{
          color:'#E8C6AB'
        }
      },
      data:[3, 3, 4.2, 4.7, 5, 5.3]
    }

  ]
};
myScrapRate.setOption(optionScrapRate);

/*新老浆员发展情况*/
var myDevelopmentSituation = echarts.init(document.getElementById("development_situation"));
var developmentSituation = {
  legend: {
    data: ['同比增长率', '新浆员数']
  },
  tooltip: {},
  xAxis: {
    data: ["2017/1","2017/2","2017/3","2017/4","2017/5","2017/6","2017/7","2017/8","2017/9","2017/10","2017/11","2017/12"],
    silent: false,
    axisLine: {onZero: true},
    splitLine: {show: true},
    splitArea: {show: false},
    axisTick: {
      show:false
    }
  },
  yAxis: [
    {
      type: 'value',
      scale: true,
      max: 500,
      min: 0,
      splitArea: {
        show: false
      },
      splitLine: {
        show: true
      },
      axisTick: {
        show:false
      },
      boundaryGap: [0.1, 0.2]
    },
    {
      type: 'value',
      scale: true,
      max: 100,
      min: 0,
      axisLabel: {
        formatter: '{value} %'
      },
      boundaryGap: [0.1, 0.2]
    }

  ],
  series: [
    {
      name: '同比增长率',
      type: 'line',
      yAxisIndex:1,
      itemStyle:{
        normal:{
          color:'#E8C6AB'
        }
      },
      data: [29, 32, 51, 74, 49, 23, 61,44,27,37,54,89]
    },
    {
      name: '新浆员数',
      type: 'bar',
      barWidth:20,
      data:[190, 132, 141, 34, 90, 103, 190,147,134,175,140,192],
      itemStyle: {
        normal: {
          color: function(params) {
            // build a color map as your need.
            var colorList = [
              '#55ABF4'
            ];
            return colorList[params.dataIndex]
          }
        }
      }

    }

  ]
};
myDevelopmentSituation.setOption(developmentSituation);






