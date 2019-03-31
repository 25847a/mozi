$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '名称',align:'center'},
     {field: 'path', title: '备份路径',align:'center'},
     {field: 'createrName', title: '备份人',align:'center'},
     {field: 'createDate', title: '备份日期',align:'center',templet:function(d){
    	 return dateFtt("yyyy-MM-dd hh:mm:ss",new Date(d.createDate));
   	  }},
   	 {fixed: 'right', title:'操作', toolbar: '#barDemo', width:110}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryBackUp',null,GetURLInfo() + "deleteDataBase");

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
	//备份
	$("#backup").click(function(){
		$.ajax({
	        type : "POST",
	        url :"/backup/copyDataBase",
	        datatype : "json",
	        success : function(result) {
	        	 checkCode(result,function(){
                     parent.layer.alert(result.message,function() {
                         var index = parent.layer.getFrameIndex(window.name);
                         parent.layer.closeAll();
                     });
                 });
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	})
})

	//搜索
	function doSearch(){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var name = $("#name").val();
		layui.table.reload('rumsers', {where: {
			"startTime": startTime,
			"endTime": endTime,
			"name":name
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
		 
	/*日期时间(开始日期、结束日期)*/
	dayControl("startTime");
	dayControl("endTime");
