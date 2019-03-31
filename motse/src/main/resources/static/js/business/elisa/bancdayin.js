$(function(){
	selectElisaInfoById();
})

function selectElisaInfoById() {
	var id = $("#id").val();
	var elisaInfoJson = { "id" : id };
	simpleAjax("/elisaInfo/selectElisaInfoById", elisaInfoJson, function(result) {
		showDataTable(result.data);
	});
}
// 读取酶标板检测数据 elisaInfo读板信息, isALT是否alt检测
function showDataTable(elisaInfo) {
	drawTable();
	// 酶标仪读孔信息
	var datass = elisaInfo.elisaValues;
	var tci = elisaInfo.testConfigInfo;
	var ecSetting = elisaInfo.elisaTemplate.elisaCommonSetting;
	var elisaTemplate = elisaInfo.elisaTemplate;
	// 获得显示类型
	var eadbutS = $("#eadbutS").val();
	// 是否显示小样号
	var isShowSampleNo = $("#showSampleNo").val();
	var ii = 0;
	if(elisaTemplate.arrangement == 1){ //纵向
		for (var i = 1, j = 12; i <= j; i++) {
			for (var k = 0, l = 8; k < l; k++) {
				if (ii >= 96) {
					break;
				}
				getValueByIndex("td"+transToChars(k)+i, ii, elisaInfo, datass, tci, ecSetting, eadbutS, isShowSampleNo);
				ii++;
			}
		}
	}else{
		for (var i = 0, j = 8; i < j; i++) {
			for (var k = 1, l = 12; k <= l; k++) {
				if (ii >= 96) {
					break;
				}
				getValueByIndex("td"+transToChars(i)+k, ii, elisaInfo, datass, tci, ecSetting, eadbutS, isShowSampleNo);
				ii++;
			}
			
		}
	}
	simpleAjax("/config/queryDictByTypeAndValue", {"type":"elisa_check_project","value":$("#projectName").val()}, function(result) {
		$("#checkProject").html(result.data.lable);
	});
	simpleAjax("/config/queryDictByTypeAndValue", {"type":"check_method","value":$("#testingMethodid").val()}, function(result) {
		$("#checkMethod").html(result.data.lable);
	});
}


function drawTable(){
	$("#bustable").html("");
	var isClass = " class = '' ";
	var tabless = "<table class='layui-table'  style='table-layout:fixed;word-wrap: break-word;' ><thead><tr><th   style='width:3em;' >@</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th><th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th></tr></thead><tbody>";
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

function getValueByIndex(tdIndex, index, elisaInfo, datass, tci, ecSetting, eadbutS, isShowSampleNo){
	elisaValue = datass[index];
	// 取值 -- 浆员名字
	var personName = elisaValue.personName;
	personName = personName=="-1"?"":personName;
	// 取值 -- 血型
	var bloodType = elisaValue.bloodType;
	bloodType = bloodType=="-1"?"未知":bloodType;
	bloodType = bloodType=="null"?"未知":bloodType;
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
	var isClass = "baidin";
	if(judgementResult ==0 ||( ("-1" == sampleNo||"" == sampleNo) && type == 1)){
		isClass ="";
	}
	$("#"+tdIndex).attr("class", isClass);
	var valueText = "";
	// 如果非特殊孔位并且小样号为空则跳过
	if (("-1" == sampleNo|| "" == sampleNo ||null ==sampleNo || "null"==sampleNo) && type == 1 ) {
		$("#"+tdIndex).html("");
		return "";
	}

	if(type!=1){
		valueText += getElisaTypeName(type)+"<p>"+odValue+"</p>";
	}else{	
		
//				if(isShowSampleNo==1){
//					sampleNo = "";
//				}
		if(eadbutS ==4) // 详细信息
			valueText += "<p>"+getBloodValue(bloodType)+"型,"+personName+"</p>"+sampleNo+"<p>"+odValue+","+(judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?")+"</p>"
		else if(eadbutS ==3) // 评定结果
			valueText += (judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?");
		else if(eadbutS ==2) // 测值
			valueText += value;
		else if(eadbutS ==1) // 原始OD值
			valueText += originalODValue;
		else if(eadbutS ==0) // OD值
			valueText += odValue;
	}
	$("#"+tdIndex).html(valueText);
	return valueText;
}


