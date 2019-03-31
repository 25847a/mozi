$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '单位名称',align:'center'},
     {field: 'isName', title: '是否默认', align:'center',templet:function(d){
    	 var text='-';
    	 if (d.isName == 0) {
             text = "否";
         } else if (d.isName == 1) {
             text = "是";
         }	
    	 return text;
     }},
     {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
  
  dataAllAndWHWithPage("rumsers",cols,null,GetURLInfo() + 'queryRabatUnitList',GetURLInfo() +'queryRabatUnitById',GetURLInfo() + "deleteRabatUnit",[ '500px', '335px' ],false,function(){});
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
		var configName = $("#configName").val()
		var type = $("#type").val()
		var isDisable = $("#isDisable").val()
		layui.table.reload('rumsers', {where: {
			"configName": configName,
			"type":type,
			"isDisable":isDisable
			}});
	}

	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'addRabatUnit',true,[ '500px', '380px' ]);
		 		}
		 	)
		 }();
		 
