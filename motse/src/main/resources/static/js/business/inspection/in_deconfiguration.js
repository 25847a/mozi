$(function(){
	/*日期时间*/ 
	initDate("#startTime");
	initTable("newtest");
	initTable("newtestS");
	document.onkeydown=keyDownSearch;  
 
}); 
// 判断键盘事件
function keyDownSearch(e) {    
    // 兼容FF和IE和Opera    
    var theEvent = e || window.event;    
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
    // 回车键
    if (code == 13) {    
    	// 判断登记号栏是否有焦点
    	var isFocus=$("#allId").is(":focus");  
    	if(true==isFocus){  
    	   var allId =  $("#allId").val();
    	   if(allId != ''){
    		   initData(allId,1);
    		   $("#allId").select();
    	   }
    	}else{  
    		$("#allId").focus();
    	}  
        return false;    
    }    
    return true;    
}  
function initTable(tableId){
	var cols;
	var startTime = $("#startTime").val();
	if("newtest"==tableId){
		cols= [[ //表头
			/* {type: 'checkbox', fixed: 'left'}*/
			{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
			,{field: 'providerNo', title: '献浆员卡号',sort: true, width:'20%',align:'center'}
			,{field: 'name', title: '姓名', sort: true,width:'15%',align:'center'}
			,{field: 'type', title: '类型',sort: true,width:'15%',align:'center',/* fixed: 'left'*/templet:function(d){ return getPbTypeValue(d.pbType);}}
			,{field: 'bloodType', title: '血型',width:'13%', align:'center',templet: function (d){ return getBloodValue(d.bloodType);}}
			,{field: 'sex', title: '性别',width:'13%', align:'center',templet: function (d){ return getSexValue(d.sex);}} 
			,{field: 'allId', title: '全登记号', sort: true, width:'25%',sort: true,align:'center'/* fixed: 'left'*/}
			]];
		
	}else{
		cols= [[ //表头
			/* {type: 'checkbox', fixed: 'left'}*/
			{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
			,{field: 'sampleNo', title: '小样号', width:'15%',align:'center'}
			,{field: 'providerNo', title: '献浆员卡号', width:'19%',align:'center'}
			,{field: 'name', title: '姓名', sort: true,width:'14%',align:'center'}
			,{field: 'type', title: '类型',sort: true,width:'15%',align:'center',/* fixed: 'left'*/templet:function(d){ return getPbTypeValue(d.pbType);}}
			,{field: 'bloodType', title: '血型', width:'12%',align:'center',templet: function (d){ return getBloodValue(d.bloodType);}}
			,{field: 'sex', title: '性别', width:'12%',align:'center',templet: function (d){ return getSexValue(d.sex);}} 
			,{field: 'plasmaNo', title: '血浆编号',sort: true,width:'15%', align:'center'}
			,{field: 'allId', title: '全登记号',  sort: true,width:'15%',align:'center'/* fixed: 'left'*/}
			]];
		
	}
	dataAll(tableId,cols,{"isAssay":"newtest"==tableId?0:1,"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListByCreateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
		initTableOnClick(tableId);
	});
}


function doSearch(){
	var startTime = $("#startTime").val();
	layui.table.reload('newtest', {where: {"isAssay":0,"startTime" : startTime, "token" : getToken()}});
	layui.table.reload('newtestS', {where: {"isAssay":1,"startTime" : startTime, "token" : getToken()}});
}

function initTableOnClick(tableId){
	// 表格 tr 单击事件
	var tab = $("#"+tableId).next('.layui-table-view') .find('table.layui-table');
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var providerNo = $(tr).find("td").eq(0).find("div").html();
		var registriesNo = $(tr).find("td").eq(1).find("div").html();
		if(undefined == providerNo) 
			return false;
		initData(providerNo);
	});
	
}

var idtep = 0;
function initData(id,type) {
	idtep = id;
	var url  = "/fixedPulpRegister/queryByID";
	var  datas =  {"id":id};
	if(type ==1 ){
		url = "/fixedPulpRegister/queryByAllId";
		datas = {"allId":id};
	}
	simpleAjax(url, datas, function(result) {
		data = result.data;
		if(data == null ){
			parent.layer.alert("没有该登记号信息.", function(index) {
				parent.layer.close(index);
				$("#allId").focus();
			});
			return false;
		}
		$("#allId").focus();
		$("#allId").select();
		$("#id").val(data.id);
		$("#name").val(data.name);
		$("#isAssay").val(data.isAssay);
		$("#bloodType").val(getBloodValue(data.bloodType));
		$("#providerNo").val(data.providerNo);
		if(data.pcreateDate == null || data.pcreateDate == "" ){
		}else{
			$("#pcreateDate").val($.myTime.UnixToDate(data.pcreateDate));
		}
		$("#sex").val(getSexValue(data.sex));
		$("#pbType").val(getPbTypeValue(data.pbType));
		$("#allId").val(data.allId);
		$("#sampleNo").val(data.sampleNo);
		$("#plasmaNo").val(data.plasmaNo);
		if(data.imagez != null)
		$("#play").attr('src',data.imagez);
		if(data.imgUrl != null)
		$("#play1").attr('src',data.imgUrl);
	});
	
	if($("#isAssay").val()==0){
		simpleAjax("/systemSeq/querySystemSeqInfo", {"type":1}, function(result) {
			data = result.data;
			$("#sampleNo").val(data.value);
		});
		
		
	}
	
	initCheckData("selectSampleNo","/specimenCollection/selectAllSampleNoByDateStr?dateStr="+$("#startTime").val(),1);
	if(type ==1){ //自动登记
		doAssay(1);
	}
}

function changeSMNO(){
	$("#sampleNo").val($("#selectSampleNo").val());
	
}

// 拒收一个样本
function refusePerson(){
	addWithArea("/fixedPulpRegister/initRefusePage",false,[ '400px', '200px' ]);
	
}

function initCheckData(selectId, url, datas, type) {
	simpleAjax(url, datas, function(result) {
		data = result.data;
		$("#" + selectId).empty();
		if (type == 1) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append( "<option value='" + data[i].sampleNo + "'>" + data[i].sampleNo + "</option>");
			}
		} 
	});
}

function doAssay(data1){
	if($("#id").val()==''){
		parent.layer.alert("请先选择一条记录.", function(index) {
			parent.layer.close(index);
		});
		return false;
	}
	var isAssay = $("#isAssay").val();
	var url = "/fixedPulpRegister/updateWithAssay";
	if(data1==0){
		if(isAssay == 0){
			parent.layer.alert("是未登记状态,不需要取消.");
			return false;
		}
		url ="/fixedPulpRegister/updateWithAssay0";
	}else{
		if(isAssay == 1){
			parent.layer.alert("是已登记状态,不需要再次登记.");
			return false;
		}
	}
	simpleAjax(url, {"id":$("#id").val(),"isAssay":data1}, function(result) {
		parent.layer.alert("操作成功");
		if(data1 == 1){
			$("#printType").val(0);
			printSM();
		}
		doSearch();
		$("#id").val("");
	});
}

function printSM(){
	var printType = $("#printType").val();
	if(0==printType){
		var id=$("#id").val();
		if(id.length>0){
			$.post("/common/printPulpRegisterCollection",{"id":id, "isSpecimen" : 2},function(res){
				if(res.code!=-1){
					layer.alert(res.message);
				}
			});
		}else{
			layer.alert("请选择浆员");
		}
	}else if(1==printType){
		 var sendTime = $("#startTime").val();
		 layer.ready(function() {
				layer.open({
					type : 2,
					title : '打印送样记录',
					maxmin : true,
					area : [ '100%', '100%' ],
					content : '/specimenCollection/printSendInfo?type=all&sendDate='+sendTime,
				})
			});
	}else{
		 var sendTime = $("#startTime").val();
		 layer.ready(function() {
				layer.open({
					type : 2,
					title : '打印拒收样本记录',
					maxmin : true,
					area : [ '100%', '100%' ],
					content : '/fixedPulpRegister/initRefusePrintPage?printDate='+sendTime,
				})
			});
	}
}