$(function(){
   var cols= [[ //表头 
     {field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'}
     ,{field: 'oddNumber', title: '订单号', align:'center',width:'15%'}
      ,{field: 'batchNumber', title: '批号', align:'center',width:'11%'}
      ,{field: 'name', title: '耗材名称', align:'center',width:'11%'}
      ,{field: 'unit', title: '单位', align:'center',width:'6%'}
      ,{field: 'supply', title: '供应商', align:'center',width:'16%'}
      ,{field: 'surplusNumber', title: '剩余数量', align:'center',width:'10%'}
      ,{field: 'startTime', title: '有效开始时间', align:'center',width:'13%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.startTime));
		}}
      ,{field: 'endTime', title: '有效结束时间', align:'center',width:'13%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.endTime));
		}}
      ,{field: 'createDate', title: '入库时间', align:'center',width:'11%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		}}
      ,{fixed : 'right',title : '操作',align : 'center',width:'10%',toolbar : '#barDemo'
		}
    ]];
   dataAllIsOpenLimit("pointPage",cols,{"token" : getToken()},GetURLInfo() + 'queryPointList',GetURLInfo() +'updatePointPage','',true,'',10);
//搜索按钮点击事件 
$("#query").click(function(){
	doSearch();
});
//重置
$("#btn-reset").click(function(){
	$("#search_par input").val("");
	$("#search_par select").val("");
}); 
})////到这里
 //搜索
function doSearch(){
	var oddNumber = $("#oddNumber").val();
	layui.table.reload('pointPage', {page: {curr:1},where: {"oddNumber": oddNumber}});
}
function getToken() {
	return localStorage.getItem("token");
}
 

