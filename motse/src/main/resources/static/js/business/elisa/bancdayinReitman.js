$(function(){
	selectElisaInfoById();
	var dateee = $("#stockExpiryTime").html();
	dateee = dateee.substring(0,dateee.indexOf(" "));
	$("#stockExpiryTime").html(dateee);
	
})
 function selectElisaInfoById() {
	var id = $("#id").val();
	var elisaInfoJson = { "id" : id };
	simpleAjax("/elisaInfo/selectElisaInfoById", elisaInfoJson, function(result) {
		showDataTable(result.data);
	});
	
}
var yangx = 0, yinx = 0, huiqu = 0 ,count = 0, stdIndex = 0;
// 读取酶标板检测数据 elisaInfo读板信息, isALT是否alt检测
function showDataTable(elisaInfo) {
	stdIndex = 0;
	drawTable();
	// 获取具体哪种打印方式
	var printType = $("#printType").val();
	// 酶标仪读孔信息
	var datass = elisaInfo.elisaValues;
	var tci = elisaInfo.testConfigInfo;
	var ecSetting = elisaInfo.elisaTemplate.elisaCommonSetting;
	// 获得显示类型
	var eadbutS = $("#eadbutS").val();
	var elisaTemplate = elisaInfo.elisaTemplate;
	// 是否显示小样号
	var isShowSampleNo = $("#showSampleNo").val();
	var ii = 0;
	yangx = 0, yinx = 0, huiqu = 0 ,count = 0;
	var std = [$("#std1").val(),$("#std2").val(),$("#std3").val(),$("#std4").val(),$("#std5").val()];
	var stdIndex = 0;
	if(elisaTemplate.arrangement == 1){ //纵向
		for (var i = 1, j = 12; i <= j; i++) {
			for (var k = 0, l = 8; k < l; k++) {
				if (ii >= 96) {
					break;
				}
				
				getValueByIndex("td"+transToChars(k)+i,ii,datass,std, printType, eadbutS);
				ii++;
			}
		}
	}else{ // 横向
		for (var i = 0, j = 8; i < j; i++) {
			for (var k = 1, l = 12; k <= l; k++) {
				if (ii >= 96) {
					break;
				}
				getValueByIndex("td"+transToChars(i)+k,ii,datass,std, printType, eadbutS);
				ii++;
			}
		}
		
	}
	
	getXDataReal();
	getYData();
	$("#ybs").html("样本数= "+count);
	$("#yxs1").html("阳性数（+）："+yangx+" ("+((yangx/count).toRound(2)*100)+"%)");
	$("#yxs2").html("阴性数（- ）："+yinx+" ("+((yinx/count).toRound(2)*100)+"%)");
	$("#hqs").html("灰区： "+huiqu+" ("+((huiqu/count).toRound(2)*100)+"%)");
	doEchart();
	simpleAjax("/config/queryDictByTypeAndValue", {"type":"elisa_check_project","value":$("#projectName").val()}, function(result) {
		$("#checkProject").html(result.data.lable);
	});
	simpleAjax("/config/queryDictByTypeAndValue", {"type":"check_method","value":$("#testingMethodid").val()}, function(result) {
		$("#checkMethod").html(result.data.lable);
	});
}
function getValueByIndex(tdIndex,index, datass, std, printType, eadbutS){
	var  elisaValue = datass[index];
	// 取值 -- 浆员名字
	var personName = elisaValue.personName;
	personName = personName=="-1"?"":personName;
	// 取值 -- 血型
	var bloodType = elisaValue.bloodType;
	bloodType = bloodType=="-1"?"未知":bloodType;
	bloodType = bloodType=="null"?"未知":bloodType;
	bloodType = getBloodValue(bloodType)+"型";
	// 取值 -- 小样号
	var sampleNo = elisaValue.sampleNo;
	// 取值 -- 浆员ID
	var pbId = elisaValue.pbId;
	pbId = pbId=="-1"?"":pbId;
	// 取值 -- 灰区信息
	var judgementResult = elisaValue.judgementResult;
	// 取值 -- od值
	var odValue = elisaValue.odValue;
	// 取值 -- 原始od值
	var originalODValue = elisaValue.originalODValue;
	// 取值 -- 测值
	var value = elisaValue.value;
	// 取值 -- 孔位类型
	var type = elisaValue.type;
	if(type ==0 ){
		value = std[stdIndex];
		stdIndex++;
	}
	var isClass = "baidin";
	if(judgementResult ==0 ||( "-1" == sampleNo && type == 1)){
		isClass ="";
	}
	$("#"+tdIndex).attr("class", isClass);
	var valueText = "";
	// 如果非特殊孔位并且小样号为空则跳过
	if (("-1" == sampleNo|| "" == sampleNo||null ==sampleNo || "null"==sampleNo) && type == 1 ) {
		$("#"+tdIndex).html("");
		return "";
	}
	sampleNo="<p>"+sampleNo+"</p>";
	if(type!=1){
		if(type==0){
			yarrayReal[yarrayReal.length] = elisaValue.value;
			xarrayReal[xarrayReal.length] = odValue;
		}
		valueText+=getElisaTypeName(type)+"<p>"+odValue+"</p>"+value;
	}else{	
			count ++; 
			if(judgementResult == 0){
				yinx++;
			}else  if(judgementResult == 1){
				yangx++;
			}else{
				huiqu++;
			}
//		if(isShowSampleNo==1){
//			sampleNo = "";
//		}
		if(eadbutS ==4) // 详细信息
			valueText+="<p>"+sampleNo+"</p><p>"+odValue+"&nbsp;"+personName+"</p>"+value;
		else if(eadbutS ==3) // 评定结果
			valueText+=(judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?");
		else if(eadbutS ==2) // 测值
			valueText+=value;
		else if(eadbutS ==1) // 原始OD值
			valueText+=originalODValue;
		else if(eadbutS ==0) // OD值
			valueText+=odValue;
		if(printType == 2 && eadbutS !=3){
			valueText+="<p></p>"+(judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?");
		}
	}
	$("#"+tdIndex).html(valueText);
	return valueText;
}
function drawTable(){
	$("#bustable").html("");
	var isClass = " class = '' ";
	var tabless = "<table class='layui-table' cellpadding='0' cellspacing='0' style='table-layout:fixed;    word-wrap: break-word;'><thead><tr><th style=' width:3em;'>@</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th></tr></thead><tbody>";
	for (var i = 0, j = 8; i < j; i++) {
		var tr = "<tr><td width='200'>"+transToChars(i)+"</td>";
		for (var k = 1, l = 12; k <= l; k++) {
			var td = "<td "+isClass+" id = 'td"+(transToChars(i)+k)+"'> </td>";
			tr= tr+td;
		}
		tr = tr+"</tr>";
		tabless = tabless+tr;
		
	}
	tabless =tabless +"</tbody></table>";
	$("#bustable").html(tabless);
}

var xarrayReal = new Array();
var yarrayReal = new Array();
var minNum =0.3;
var maxNum =0.8;
function getXDataReal(){
	minNum = xarrayReal[0].toFloor(1);
	maxNum = xarrayReal[4].toCeil(1);
}
var yarray = new Array();
function getYData(){
	yarray[0] = $("#std1").val();
	yarray[1] = $("#std2").val();
	yarray[2] = $("#std3").val();
	yarray[3] = $("#std4").val();
	yarray[4] = $("#std5").val();
    return yarray;
}
function doEchart(){  
	var myChart4 = echarts.init(document.getElementById("line_container4"));
	 var option4 = { 
	    xAxis: {
	       min: 0, 
		   max: 150,
		   splitNumber:5
	    },
	    yAxis: {
	    	min: minNum, 
			max: maxNum,
			splitLine: {
                show: true,
               lineStyle:{
             type:'dashed'
                },
			},
	    },
	    series: [{
	        data: [[yarrayReal[0],xarrayReal[0]], [yarrayReal[1],xarrayReal[1]], [yarrayReal[2],xarrayReal[2]], [yarrayReal[3],xarrayReal[3]], [yarrayReal[4],xarrayReal[4]]],  
	        type: 'scatter',
	 	   smooth:false, 
	 	   
	        
	    },
	    {
	       data: [[yarray[0],xarrayReal[0]], [yarray[1],xarrayReal[1]], [yarray[2],xarrayReal[2]], [yarray[3],xarrayReal[3]], [yarray[4],xarrayReal[4]]],
	      smooth: true,
	        type: 'line'
	    }]
	 }; 
	 myChart4.setOption(option4);
}


Array.prototype.distinct = function (){
	 var arr = this,
	  i,
	  j,
	  len = arr.length;
	 for(i = 0; i < len; i++){
	  for(j = i + 1; j < len; j++){
	   if(arr[i] == arr[j]){
	    arr.splice(j,1);
	    len--;
	    j--;
	   }
	  }
	 }
	 return arr;
	};
 