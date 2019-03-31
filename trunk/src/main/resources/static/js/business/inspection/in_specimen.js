$(function(){
	/* 日期时间 */ 
	initDate("#createTime");
	initTable("newtest");
	initTable("newtestS");
	initCheckAdmins("#checkAdmin");
	//打印
	$("#print").click(function(){
		printSM();
	});
});

function printSM(callback){
	var id=$("#id").val();
	if(id.length>0){
		simpleAjax("/common/printSpecimenCollection",{"id":id, "isSpecimen" : 1},function(res){
			if(res.code!=-1){
				layer.alert(res.message);
				if(typeof callback =="function"){
					callback();
				}
			}
		});
	}else{
		layer.alert("请选择浆员");
	}
}

function initList() {
	var startTime = $("#createTime").val()
	layui.table.reload('newtest', {where: {"isCollection": 0, "startTime": startTime}});
	layui.table.reload('newtestS', {where: {"isCollection": 1, "startTime": startTime}});
}

function initTable(tableId){
	var cols;
	var startTime = $("#createTime").val();
	if("newtest"==tableId){
	cols = [ [ // 表头
		/* {type: 'checkbox', fixed: 'left'} */
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			sort : true,
			width:'17%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width:'13%',
			
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width:'10%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return getSexValue(d.sex);
			}
		}, {
			field : 'allId',
			title : '登记号',
			sort: true,width : '15%',
			align : 'center'
		}, {
			field : 'pbType',
			title : '类型',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return getPbTypeValue(d.pbType);
			}
		}, {
			field : 'bloodType',
			title : '血型',
			width:'10%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return getBloodValue(d.bloodType);
			}
		}, {
			field : 'plasmaType',
			title : '固定浆员',
			width:'15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.plasmaType ==1) {
					text = "是";
				} else {
					text = "否"; } return text; }
		}, { field : 'icNumber',width : '15%', title : 'IC卡号', align : 'center' } ] ];

	}else{
		cols = [ [ // 表头
			/* {type: 'checkbox', fixed: 'left'} */
			{
				field : 'id',
				title : 'id',
				display : 'none',
				minWidth : '0',
				width : '0',
				type : "space"
			},
			 {type:'checkbox', fixed: 'left' },
			 {
				field : 'providerNo',
				title : '献浆员卡号',
				width:'15%',
				sort : true,
				align : 'center'
			}, {
				field : 'allId',
				title : '登记号',
				width:'20%',
				sort: true,
				align : 'center'
			}, {
				field : 'isSendOff',
				title : '是否送样',
				width:'15%',
				sort : true,
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					return getBooleanValue(d.isSendOff);
				}
			},{
				field : 'name',
				title : '姓名',
				width:'10%',
				sort : true,
				align : 'center'
			}, {
				field : 'sex',
				title : '性别',
				width:'10%',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					return getSexValue(d.sex);
				}
			}, {
				field : 'pbType',
				title : '类型',
				width:'10%',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					return getPbTypeValue(d.pbType);
				}
			}, {
				field : 'bloodType',
				title : '血型',
				width:'10%',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					return getBloodValue(d.bloodType);
				}
			},{
				field : 'sampleNo',
				title : '小样号',
				width:'20%',
				align : 'center'
			}, {
				field : 'plasmaType',
				title : '固定浆员',
				width:'20%',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					var text = '-';
					if (d.plasmaType ==1) {
						text = "是";
					} else {
						text = "否";
					}
					return text;
				}
			}] ];
	}
	dataAll(tableId,cols,{"isCollection": "newtest"==tableId?0:1,"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListByCreateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
		 initTableOnClick(tableId);
	},50);
}

/* 静脉验证 */
!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#inspecim').on('click', function() {
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '手掌静脉验证',
				maxmin : true,
				area : [ '550px', '429px' ],
				content : '../../Popup/Bus/Bus_New-Veri.html',
			})
		});
	})
}();
/* 面部识别 */
//
//
!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#imspced').on('click', function() {
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		identityValidate();
	})
}();
// 弹出详情

function sendOff(){
	var table = layui.table;
	var checkStats = table.checkStatus('newtestS');
	var data = checkStats.data;
	var ids=new Array();
	if(data.length == 0){
		layer.alert("请选择要送样的标本");
		return  false;
	}
	for(var i = 0 ; i< data.length;i++){
		ids.push(data[i].id);
	}
	var sendPerson = $("#checkAdmin option:selected").val();
	var datas ={"ids":ids, "sendPerson":sendPerson};
	$.ajax({
		  url: "/specimenCollection/updateWithSendOff",
		  type: "POST",
		  data: datas,
		  traditional: true,//这里设置为true
		  success: function(result) {
			  layer.alert(result.message,function(){
				  layer.closeAll();
				  initTable("newtestS");
			  });
		  }
		});
	
}
var idtep = 0;
function initData(id,registriesNo) {
	if(idtep == id){
		return;
	}
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/specimenCollection/querySpecimenById?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#isCollection").val(data.isCollection);
			$("#name").val(data.name);
			getBloodValue(data.bloodType);
			$("#bloodType").val(getBloodValue(data.bloodType));
			$("#providerNo").val(data.providerNo);
			$("#sex").val(getSexValue(data.sex));
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#registriesNo").val(data.allId);
			$("#icNumber").val(data.icNumber);
			$("#sampleNo").val(data.sampleNo);
			$("#demo3").attr('src',data.photo);
			if(data.imagez != null)
				$("#demo1").attr('src',data.imagez);
			else{
				$("#demo1").attr('src', '../../../img/new_pa1.png');
			}
			if(data.imgUrl != null)
				$("#imageFace_show").attr('src',data.imgUrl);
			else{
				$("#imageFace_show").attr('src', '../../../img/new_pa2.png');
			}
			if($("#isCollection").val()==0){
				
				simpleAjax("/systemSeq/querySystemSeqInfo", {"type":1}, function(result) {
					data = result.data;
					$("#sampleNo").val(data.value);
				});
			}
			initCheckData("selectSampleNo","/specimenCollection/selectAllSampleNoByDateStr?dateStr="+$("#createTime").val(),1);
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
	
//	if($("#isCollection").val()==0){
//		$.ajax({
//			type : "POST",
//			url : "/systemSeq/querySystemSeqInfo?type=" + 1,
//			datatype : "json",
//			success : function(result) {
//				data = result.data;
//				$("#sampleNo").val(data.value);
//				
//			}
//		});
//	}
	
	
}

/**
 * 更新采样记录
 * @param isCollection
 * @returns
 */
function updateWithCollection(isCollection){
	var id = $("#id").val();
	if(undefined==id || ''==id){
		layer.alert('请先选择一条记录再操作!');
		return false;
	}
	var isCollectionO = $("#isCollection").val();
	if(undefined==isCollectionO || ''==isCollectionO){
		layer.alert('请先选择一条记录再操作!');
		return false;
	}
	if(isCollectionO ==isCollection){
		return false;
	}
//	var result = $("#result").val();
//	//先判断有没有人脸识别成功 
//	if(result.length<1){
//		layer.alert('请先识别浆员');
//		return false;
//	}
	var data ={"id":id, "isCollection":isCollection};
	simpleAjax("/specimenCollection/updateWithCollection", data, function(result) {
		if(data.code == '-1'){
			layer.alert('操作成功!');
			if(isCollection==1)
				printSM(function(){
					window.reload();
				});
			return false;
		}else{
			window.reload();
		}
		
	});
	
	
}


function printSendInfo(){

	 var sendTime = $("#createTime").val();
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印送样记录',
				maxmin : true,
				area : [ '100%', '100%' ],
				content : '/specimenCollection/printSendInfo?sendDate='+sendTime,
			})
		});
	 
}

function initTableOnClick(tableId){
	　var re = /^[0-9]+.?[0-9]*$/; 
	// 表格 tr 单击事件
	var tab = $("#"+tableId).next().find('.layui-table tbody tr'); 
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var providerNo = $(tr).find("td").eq(0).find("div").html();
		var registriesNo = $(tr).find("td").eq(4).find("div").html();
		if(undefined == providerNo) 
			return false;
		if (re.test(providerNo)) {
			initData(providerNo,registriesNo);
		}
	});
	
}

function changeSMNO(){
	$("#sampleNo").val($("#selectSampleNo").val());
	
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

