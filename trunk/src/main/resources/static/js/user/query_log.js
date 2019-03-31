$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'method', title: '功能',align:'center'},
     {field: 'describe', title: '描述',align:'center'},
     {field: 'ip', title: 'IP地址',align:'center'},
     {field: 'name', title: '操作用户',align:'center'},
     {field: 'createDate', title: '操作日期',align:'center',templet:function(d){
    	 return dateFtt("yyyy-MM-dd hh:mm:ss",new Date(d.createDate));
   	  }},
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryLog',null,null);

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
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		layui.table.reload('rumsers', {where: {
			"startTime": startTime,
			"endTime": endTime
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
		 
	/*日期时间(开始日期、结束日期)*/
	dayControl("startTime");
	dayControl("endTime");
