$(function(){
	// 初始化类型
	initCheckData2("templateName", "/elisaTemplate/queryTemplates", 2);
	getReportInfo();

})
// 根据选择的模板自动生成试剂和质控品的下拉框
function changeValue(){
	var templateName = $("#templateName").val(); 
	initCheckData21("qcId", "/suppliesStock/queryTestPage", { "type" : 1, "id": templateName }, 3);
	initCheckData21("reagentId", "/testConfigInfo/queryByEtId", { "etId" : templateName }, 4);
}

function gotoEditReportInfo(){
	parent.layer.ready(function() {
		parent.layer.open({
			type : 2,
			title : '报表表头配置',
			maxmin : false,
			content : '/reportInfo/initElisaPage',
			area : [ '100%', '220px' ],
			end : function() {
				getReportInfo();
			}
		})
	});
}
function getReportInfo(){
	var url = "/reportInfo/getInfoByReportNO";
	var datas = {"reportNO":0};
	simpleAjax(url, datas, function(result) {
		var info = result.data;
		$("#headLeft").html(info.headLeft);
		$("#headCenter").html(info.headCenter);
		$("#headRight").html(info.headRight);
		$("#footLeft").html(info.footLeft);
		$("#footCenter").html(info.footCenter);
		$("#footRight").html(info.footRight);
	});
}
function showTable(){
	var page = $("#page").val();
	
	var url = "/elisaReport/selectQCReportForTable";
	var tciid = $("#reagentId").val();
	if(tciid=='' || tciid ==undefined){
		var index = parent.layer.alert("没有检验配置信息", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var qcId = $("#qcId").val();
	if(qcId==''|| qcId ==undefined){
		var index = parent.layer.alert("没有质控品信息", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var page = $("#page").val();
	var datas= {"tciid":tciid,"qcId":qcId,"page":page};
	var type = $("#type").val();
	if(page>=1){
		window.location.href = "/elisaReport/printQCReportForImg?tciid="+tciid+"&qcId="+qcId+"&page="+page+"&limit=29&type="+type;
	}
	simpleAjax(url, datas, function(result) {
		var data = result;
		var list = data.data;
		var checkMethod = data.tci.testingMethodid;
		// 先处理分页事项
		// 是否首页
		if(data.isFirst){
			$("#isFirst").hide();
		}else{
			$("#isFirst").show();
		}
		// 是否显示上一页
		if(data.hasPrev){
			$("#hasPrev").show();
		}else{
			$("#hasPrev").hide();
		}
		// 是否显示下一页
		if(data.hasNext){
			$("#hasNext").show();
		}else{
			$("#hasNext").hide();
		}
		// 是否末页
		if(data.isLast){
			$("#isLast").hide();
		}else{
			$("#isLast").show();
		}
		$("#bustable").html("");
		$("#page").val(data.pageIndex);
		$("#pages").val(data.pages);
		// 处理表格
		var typename ="S/CO";
		var isTreu = checkMethod ==2;
		if(isTreu){
			typename = "卡门值"
		}
		var table = "<table class='layui-table'><thead><tr><th  width='200' >次数(n)</th><th>日期</th><th>"+typename+"</th><th>X</th><th>S</th><th>SDI上限</th><th style='width:13%‘'>SDI下限</th><th>N2S</th><th>N3S</th><th>质控结果</th><th>操作人</th><th>备注</th></tr></thead> <tbody>";
		var trs="";
		for(var i=0,j=list.length;i<j;i++){
			var item = list[i];
			var tr = " <tr>"+
			" <td width='200'>"+(i+1)+"</td>"+
			" <td>"+$.myTime.UnixToDate(item.createDate)+"</td>"+
			 "<td>"+(isTreu?item.altValue.toRound(2):item.sDivideCO.toRound(2))+"</td>"+
			 "<td>"+(isTreu?item.altTargetValue==null?"":item.altTargetValue.toRound(2):(item.targetValue==null?"":item.targetValue.toRound(2)))+"</td>"+
			 "<td>"+(isTreu?item.altStandardDeviation==null?"":item.altStandardDeviation.toRound(2):(item.standardDeviation==null?"":item.standardDeviation.toRound(2)))+"</td>"+
			 "<td>"+(isTreu?item.altSIMAX==null?"":item.altSIMAX.toRound(2):(item.siMAX==null?"":item.siMAX.toRound(2)))+"</td>"+
			 "<td>"+(isTreu?item.altSIMIN==null?"":item.altSIMIN.toRound(2):(item.siMIN==null?"":item.siMIN.toRound(2)))+"</td>"+
			 "<td>"+(item.n2s==null?"":item.n2s)+"</td>"+
			 "<td>"+(item.n3s==null?"":item.n3s)+"</td>"+
			 "<td>"+(item.criticalResult==null?"":getQCValue(item.criticalResult))+"</td>"+
			 "<td>"+item.name+"</td>"+
			 "<td></td>"+
			" </tr>";
			trs = trs+tr;
		}
		table =table+trs+"</tbody> </table>";
		$("#bustable").append(table);
		// 处理表头
		// 处理表尾
		if(data.lastItem!=null){
			$("#elisaEquipmentAndManufacturers").text(data.lastItem.elisaEquipmentAndManufacturers);
			$("#checkProjectName").text(data.lastItem.checkProjectName);
			$("#checkMethod1").text(data.lastItem.checkMethod);
			$("#checkDate").text($.myTime.UnixToDate(data.firstItem.createDate)+"   -   "+$.myTime.UnixToDate(data.lastItem.createDate))
			$("#useWavelength").text(data.lastItem.useWavelength);
			var cvValue = isTreu?data.lastItem.altCV: data.lastItem.cv;
			if(cvValue != null) {
				cvValue = cvValue.toRound(2);
			}
			$("#selfCV").text(cvValue+"  %");
			var standardDeviation = isTreu?data.lastItem.altStandardDeviation:data.lastItem.standardDeviation;
			if(standardDeviation != null) {
				standardDeviation = standardDeviation.toRound(2);
			}
			$("#selfStandardDeviation").text(standardDeviation);
			var selfTargetValue = isTreu?data.lastItem.altTargetValue:data.lastItem.targetValue;
			if(selfTargetValue != null) {
				selfTargetValue = selfTargetValue.toRound(2);
			}
			$("#selfTargetValue").text(selfTargetValue);
			$("#batchNumber").text(data.lastItem.batchNumber);
			$("#ssName").text(data.lastItem.ssName);
			$("#termOfValidity").text($.myTime.UnixToDate(data.lastItem.termOfValidity));
			$("#qcBatchNumber").text(data.lastItem.qcBatchNumber);
			$("#qcSsName").text(data.lastItem.qcName);
			$("#qcTermOfValidity").text($.myTime.UnixToDate(data.lastItem.qcExpiryTime));
			
		}
	});
}

//获取项目集合信息
function initCheckData2( selectId, url, type) {
	$.ajax({
		type : "POST",
		data:{},
		url : url,
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#"+selectId).empty(); 
			$("#"+selectId).append("<option value=''></option>"); 
			if(type==1){
				for(var i=0,j=data.length;i<j;i++){
					$("#"+selectId).append("<option value='"+data[i].id+"'>"+data[i].projectName+"</option>"); 
				}
			}else if(type==2){
				for(var i=0,j=data.length;i<j;i++){
					$("#"+selectId).append("<option value='"+data[i].id+"'>"+data[i].templateName+"</option>"); 
				}
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
}


function initCheckData21(selectId, url, datas, type) {
	simpleAjax(url, datas, function(result) {
		data = result.data;
		$("#" + selectId).empty();
		if (type == 2) {
			$("#" + selectId).append("<option value=''></option>");
		}
		if (type == 1) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ data[i].projectName + "</option>");
			}
		} else if (type == 2) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ data[i].templateName + "</option>");
			}
		} else if (type == 3) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ data[i].batchNumber + "</option>");
			}
		} else if (type == 4) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ (data[i].tcMark+"("+data[i].batchNumber+")") + "</option>");
			}
		}

	});
	
	
	
}
$("#isFirst").click(function(){
	$("#page").val(0);
	showTable();
});
$("#hasPrev").click(function(){
	$("#page").val(Number($("#page").val())-1);
	showTable();
});
$("#hasNext").click(function(){
	$("#page").val(Number($("#page").val())+1);
	showTable();
	
});
$("#isLast").click(function(){
	$("#page").val($("#pages").val());
	showTable();
});