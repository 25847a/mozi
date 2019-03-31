$(function(){
	initTable("jiemei");
});


function initTable(tableId){
	
	var cols;
	var type = $("#type").val();
	if(type == 1){
		$("#xiuaddd").removeAttr("disabled");
	}else{
		$("#xiuaddd").attr("disabled","disabled");
	}
	var status = $("#status").val();
	var data= {'type':type,'status':status};
	cols= [[ //表头
		 {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ,{field: 'name', title: '名称',  align:'center'}
	      ,{field: 'batchNumber', title: '批号', align:'center'}
	      ,{field: 'expiryTime', title: '有效期',align:'center', templet:function(d){ return $.myTime.UnixToDate(d.expiryTime);}}
	      ,{field: 'supply', title: '厂家名称',align:'center'}
	      ,{field: 'startTime', title: '开始日期',align:'center', templet:function(d){ return $.myTime.UnixToDate(d.startTime);}}
	      ,{field: 'endTime', title: '截止日期',align:'center' , templet:function(d){ return $.myTime.UnixToDate(d.endTime);}}
	      ,{field: 'status', title: '是否禁用',align:'center', templet : function(d) { // 单元格格式化函数
				return getEnableValue(d.status);
			}}
	      ,{fixed: 'right',title: '操作', width: 250, align:'center', toolbar: '#barDemo'}
	   ]];
	dataAllIsOpen(tableId,cols,data,'/suppliesStock/queryTestList',GetURLInfo() +"/updateStockEnableById",GetURLInfo() + "/updateStockUnEnableById",false,function(){
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
function initData(obj1){
	$("#id").val(obj1);
}

! function() {
    //页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
    $('#xiuaddd').on('click', function() {
      layer.ready(function() {
       layer.open({
           type: 2,
            title: '质控品添加',
           maxmin: false,
           area: ['100%', '100%'],
        content: '/elisaTemplateSupplies/gotoAdd',
        })
      });
    })
  }();
