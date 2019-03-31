$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '仓库名称',align:'center'},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
     {field: 'updateDate', title: '修改时间', align:'center',templet:function(d){
    	  return dateFtt("yyyy-MM-dd",new Date(d.updateDate));
   	  }},
     {field: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
  dataAllIsOpenLimit("rumsers",cols,{"token" : getToken()},GetURLInfo() + 'queryUnitList',GetURLInfo() +'unitUpdate',GetURLInfo() + "deleteUnit",true,'',10);

	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	}); 
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 	
	
})

	//搜索
	function doSearch(){
		var name = $("#keyword").val()
		layui.table.reload('rumsers', {page: {curr:1},where: {
			"name": name
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#unittAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'unitAddPage',true,[ '500px', '200px' ]);
		 		}
		 	)
		 }();
		 /**
		  * 刷新
		  * @returns
		  */
		 function refresh(){
		 	location.reload();
		 }