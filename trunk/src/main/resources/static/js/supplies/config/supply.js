

$(function(){
			var cols= [
				[ //表头
					{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
					{field: 'name', title: '供应商名称',align:'center'},
					{field: 'contacts', title: '联系人',align:'center'},
					{field: 'mobile', title: '联系电话',align:'center'},
					{field: 'address', title: '地址',align:'center'},
					{field: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
				]
			]
			dataAllIsOpenLimit("supply",cols,{"token" : getToken()},GetURLInfo() + 'querySuppliesSupplyList',GetURLInfo() +'/supplyUpdate',GetURLInfo() + "deleteSupply",true,'',10);
	//搜索按钮点击事件
	$("#query").click(function(){
		doSearch();
	});
	
});//到这里 

//搜索
function doSearch(){
	var name = $("#name").val();
	layui.table.reload('supply', {page: {curr:1},where: {"name": name}});
}
function getToken() {
	return localStorage.getItem("token");
}
//重置
$("#reset").click(function(){
	$("#search_par input").val("");
}); 
//新增
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#puituation').on('click', function() {
		addWithArea(GetURLInfo() +'supplyAdd',true,[ '500px', '350px' ]);
	})
}(); 
/**
 * 刷新
 * @returns
 */
function refresh(){
	location.reload();
}