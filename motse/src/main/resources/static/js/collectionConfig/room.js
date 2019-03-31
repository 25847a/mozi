$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '采室名称',align:'center'},
     {field: 'address', title: '采室地址', align:'center'},
     {field: 'isSelected', title: '是否默认', align:'center',templet:function(d){
    	 var text = '-';
         if (d.isSelected == 0) {
             text = "未默认";
         } else if (d.isSelected == 1) {
             text = "已默认";
         }	
         return text;
  	  }},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return $.myTime.UnixToDate(d.createDate);
   	  }},
     {field: 'updateDate', title: '修改时间', align:'center',templet:function(d){
    	  return $.myTime.UnixToDate(d.updateDate);
   	  }},
     {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryRoomList',GetURLInfo() +'roomDetails',GetURLInfo() + "deleteRoom");

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
		var name = $("#keyword").val()
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
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'roomAdd',true,[ '500px', '300px' ]);
		 		}
		 	)
		 }();
