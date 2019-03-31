$(function(){
	initTable("jiemei");
	initCheckData({'type':"elisa_check_project"},"projectName");
	initCheckSelect("testingMethodid", "check_method");
	initDate("#startTime");
	initDate("#endTime");
	initDate("#expiryTime");
});

function initTable(tableId){
	var cols;
	var data= {};
	cols= [[ //表头
		 {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
		 ,{field: 'ssName', title: '供应商',sort: true,  width:'20%',align:'center'}
	      ,{field: 'siName', title: '耗材名称', width:'20%',align:'center'}
	      ,{field: 'batchNumber', title: '耗材批号',  width:'15%',align:'center'}
	      ,{field: 'outNumber', title: '出库数量',   align:'center',templet:function(d){ return d.outNumber/-1}}
	      ,{field: 'startTime', title: '开始时间', width:'12%',align:'center' ,templet:function(d){ return $.myTime.UnixToDate(d.startTime)}}
	      ,{field: 'endTime', title: '结束日期', width:'12%',align:'center' ,templet:function(d){ return $.myTime.UnixToDate(d.endTime)}}
	      ,{field: 'expiryTime', title: '到期日期', width:'12%',align:'center' ,templet:function(d){ return $.myTime.UnixToDate(d.expiryTime)}}
	   ]];
	dataAll(tableId,cols,data, '/elisaTemplateSupplies/querySuppliesQC',GetURLInfo() +'',GetURLInfo() + "",function(){
			 initTableOnClick(tableId);
	});
}

function initTableOnClick(tableId){
	// 表格 tr 单击事件
	var tab = $("#"+tableId).next('.layui-table-view') .find('table.layui-table');
	
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var id = $(tr).find("td").eq(0).find("div").html();
		if(undefined == id) 
			return false;
		initData(id);
	});
} 

function initData(id){
	simpleAjax("/elisaTemplateSupplies/queryStockById",{"id":id},function(result){
		var data  = result.data;
		$("#ssName").val(data.ssName);
		$("#siName").val(data.siName);
		$("#standard").val(data.standard);
		$("#endTime").val($.myTime.UnixToDate(data.endTime));
		$("#expiryTime").val($.myTime.UnixToDate(data.expiryTime));
		$("#startTime").val($.myTime.UnixToDate(data.startTime));
		$("#batchNumber").val(data.batchNumber);
		$("#stockId").val(data.id);
	});
	
}

function saveQc(){
	var stockId  = $("#stockId").val();
	if(stockId==null || stockId=='' ){
		var index = parent.layer.alert("请先选择一个质控品!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var checkMethod  = $("#testingMethodid").val();
	var checkProject  = $("#projectName").val();
	var data  = {"stockId":stockId,"checkMethod":checkMethod,"checkProject":checkProject};
	simpleAjax("/elisaTemplateSupplies/saveElisaQC",data,function(result){
		var index = parent.layer.alert("操作成功!", function() {
			parent.layer.close(index);
		});
	});
}

//获取程序模式配置值
function initCheckData(datas, selectId) {
	$.ajax({
		type : "POST",
		data:datas,
		url : "/config/queryDictListByType",
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#"+selectId).empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#"+selectId).append("<option value='"+data[i].value+"'>"+data[i].lable+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}
