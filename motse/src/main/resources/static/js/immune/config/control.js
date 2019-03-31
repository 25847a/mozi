$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'controlName', title: '控制名称',align:'center'},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
   	{field: 'updateDate', title: '修改时间',align:'center',templet:function(d){
     	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
     	  }},
     {field: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
   data("control",cols,GetURLInfo() + 'controlList',GetURLInfo() +'controlUpdate',GetURLInfo() + "deleteControl");

	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
		currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	}); 
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 	
	
})

	//搜索
	function doSearch(){
		var controlName = $("#controlName").val()
		layui.table.reload('control', {where: {
			"controlName": controlName
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#immAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'controlAddPage',true,['400px', '280px']);
		 		}
		 	)
		 }();
