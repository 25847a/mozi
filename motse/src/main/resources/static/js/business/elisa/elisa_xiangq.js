$(function(){
	initTypess();
	initTable();
	selectElisaInfoById();
	//initTable("toconig");
});
var typeArray = new Array();
//初始化一个数组用来保存不能被跳过的孔
var blkArray = new Array();
//初始化一个二维数组 保存现有的表格
var typess = new Array();
function initTypess() {
	for (var i = 0, j = 8; i < j; i++) {
		typess[i] = new Array();
		for (var k = 0; k < 13; k++) {
			typess[i][k] = "";
			// 初始化 所有的lable和input
		}
	}
}

function initTable() {
	$("#tdata").empty();
	for (var i = 0, j = 8; i < j; i++) {
		var idname = transToChars(i);
		var trs = "<tr> <td rowspan='2'>" + transToChars(i) + "</td>";
		for (var k = 1; k <= 12; k++) {
			trs += "<td id='td" + idname + k + "'><input type='hidden' id='hid"
					+ idname + k + "' name='h" + idname + k
					+ "'/><input type='hidden' id='h"
					+ idname + k + "' name='h" + idname + k
					+ "'/><lable style='width:110px' id='l" + idname + k
					+ "' name='l" + idname + k + "' ></lable></td>";
		}
		trs += "</tr><tr>";
		for (var k = 1; k <= 12; k++) {
			trs += "<td id='td1"
					+ idname
					+ k
					+ "'><input  style='width:110px' readonly='readonly' id='t"
					+ idname + k + "' name='t" + idname + k + "'></input></td>";
		}
		trs += "</tr>";
		$("#tdata").append(trs);
	} ;

}
function selectElisaInfoById() {
	var id = $("#id").val();
	var elisaInfoJson = { "id" : id };
	simpleAjax("/elisaInfo/selectElisaInfoById", elisaInfoJson, function(result) {
		showDataTable(result.data);
	});
}
//读取酶标板检测数据 elisaInfo读板信息, isALT是否alt检测
function showDataTable(elisaInfo) {
	var isALT = false;
	// 酶标仪读孔信息
	var datass = elisaInfo.elisaValues;
	var tci = elisaInfo.testConfigInfo;
	if(tci.projectName==1){
		isALT = true;
	}
	var isShowSampleNo = $("#showSampleNo").is(':checked');
	// 是否查询
	var isSearch = $("#default1").attr("checked");
	var elisaTemplate = elisaInfo.elisaTemplate;
	
	var ii = 0;
	if(elisaTemplate.arrangement == 1){ //纵向
		for (var i = 1, j = 12; i <= j; i++) {
			for (var k = 0, l = 8; k <= l; k++) {
				if (ii >= 96) {
					break;
				}
				var tdIndex = transToChars(k) + i;
				getValueByIndex(tdIndex, ii,  datass, isALT, isSearch);
				ii++;
			}
		}
	}else{ // 横向
		
		for (var i = 0, j = 8; i <= j; i++) {
			for (var k = 1, l = 12; k <= l; k++) {
				if (ii >= 96) {
					break;
				}
				var tdIndex = transToChars(k) + i;
				getValueByIndex(tdIndex, ii,  datass, isALT, isSearch);
				ii++;
			}
		}
	}
}



function getTypeName(type){
	if (type == 0) {
		return "标准品";
	} else if (type == 5) {
		return "质检";
	} else {
		return "";
	}
}


function getValueByIndex(tdIndex, index,  datass, isALT, isSearch){
	
	elisaValue = datass[index];
	
	var namm = tdIndex;
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

	// 如果非特殊孔位并且小样号为空则跳过
	if (("-1" == sampleNo|| "" == sampleNo) && type == 1 || $("#t" + namm).prop("disabled")) {
		if(isSearch){
			$("#t" + namm).val("");
			$("#l" + namm).html("");
		}
		return "";
	}
	// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名 A 100001 李瀚昂 -? 0.008
	if (type == 1) {
		$("#hid"+namm).val(elisaValue.id);
		if(isALT){
			$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ odValue +" "+ value);
		}else{
			$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ (judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?") + " "+ odValue);
		}
	} else {
		
			if(isALT){
				$("#t" + namm) .val(getTypeName(type) +" "+odValue +" "+ value);
			}else{
				$("#t" + namm).val((judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?") + " " + odValue);
			}
		}
	
	
	if(elisaValue.reportStatus==1){
		$("#td" + namm).attr("class", "moad8" );
		$("#td1" + namm).attr("class", "moad8" );
	}else{
		$("#td" + namm).attr("class", "moad" + elisaValue.type);
		$("#td1" + namm).attr("class", "moad" + elisaValue.type);
	}
	// 设置样式 是否发布 是否阳性
	if(isALT){
		if(value>=25){
			$("#td" + namm).attr("class", "moad9");
			$("#td1" + namm).attr("class", "moad9");
		}
	}else{
		if(elisaValue.judgementResult ==2 ){
			$("#td" + namm).attr("class", "moad9");
			$("#td1" + namm).attr("class", "moad9");
		}else if(elisaValue.judgementResult ==1 && type == 1){
			$("#td" + namm).attr("class", "moad2");
			$("#td1" + namm).attr("class", "moad2");
		}
	}
		if(sampleNo=="-1"){
			$("#l" + namm).text(getElisaTypeName(type));
		}else{
			$("#l" + namm).text(sampleNo);
		}
}
