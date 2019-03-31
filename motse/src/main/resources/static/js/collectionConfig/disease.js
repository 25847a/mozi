$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '淘汰原因',align:'center'},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
     {field: 'updateDate', title: '修改时间', align:'center',templet:function(d){
    	  return dateFtt("yyyy-MM-dd",new Date(d.updateDate));
   	  }},
     {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryDiseaseList',GetURLInfo() +'diseaseUpdate',GetURLInfo() + "deleteDisease");

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
		var name = $("#name").val()
		layui.table.reload('rumsers', {where: {
			"name": name
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#diseaseAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'diseaseAdd',true,[ '500px', '220px' ]);
		 		}
		 	)
		 }();
