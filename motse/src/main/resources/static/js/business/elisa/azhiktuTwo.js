$(function(){ 
 var Xkr=2.680;
 var Skr=0.230;
 var myChart4 = echarts.init(document.getElementById("line_container4"));

option4= {
   tooltip: {
        trigger: 'axis'
    }, 
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    }, 
    xAxis: {
        type: 'category',
        boundaryGap: true,　splitLine:{
　　　　show:true
　　},
        data: ['2.12','1.12','3.12','4.1','5.1','6.1','7.1','8.0','9.0','10.0','11.0','12.0','13.0','14.0','15.0','16.0','17.0','18.0','19.0','20.0','21.0','22.0','23.0',' ',' ',' ',' ','',' ',' ']
    },
    yAxis: {  
    	boundaryGap: false,
          　splitLine:{
　　　　show:true
　　}, 
　　max:Xkr+Skr*3,
　　min:Xkr-Skr*3,
splitNumber : 6, 
        type: 'value', 
        axisLabel: {                   
                 formatter: function (value, index) {
                  return value.toFixed(3);      
                  }, 
        }
    },
  
   interval:Skr,
    series: [
        {
            name:'邮件营销',
            type:'line',
            stack: null,
             color:['#000'],
            data:[2.01, 2.2, 2.20,2.25, 2.31,3.10, 2.338, 3.25, 2.34, 3.15, 2.26, 2.34,3.22, 3.29,2.08,2.388],
          
        }
          
    ]
   }
 myChart4.setOption(option4);
 

})