$(function(){
/*列表数据显示借口*/
	var id= $("#id").val();
	  var cols= [[ //表头 
	    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
	    	{field: 'batchNumber', title: '批号', sort: true,align:'center', fixed: 'left'},
			{field: 'supplies', title: '耗材名称', sort: true,align:'center', fixed: 'left'},
			{field: 'type', title: '类型', sort: true,align:'center', fixed: 'left'},
			{field: 'unit', title: '单位', sort: true,align:'center', fixed: 'left'},
			{field: 'supply', title: '供应商', sort: true,align:'center', fixed: 'left'},
			{field: 'surplusNumber', title: '数量', sort: true,align:'center', fixed: 'left'},
			{field: 'money', title: '单价', sort: true,align:'center', fixed: 'left'},
			{field: 'sumMoney', title: '总价', sort: true,align:'center',fixed: 'left'},
			{fixed : 'right',title : '操作',align : 'center',toolbar : '#barDemo'}
	    ]];
dataAllAndWHWithPage("details",cols,{"id" : id}, GetURLInfo()+'/queryStockDatelis',GetURLInfo()+'/jumpUpdate','',[ '500px', '335px' ],true);
//
//uurl
})
 
