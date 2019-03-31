$(function(){
  var  cols= [[ //表头 
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'configName', title: '配置名称',align:'center',width:'12%'},
     {field: 'type', title: '类型', align:'center',width:'10%'},
     {field: 'lable', title: '标签名', align:'center',width:'10%'},
     {field: 'value', title: '标签值', align:'center',width:'10%'},
     {field: 'isDisable', title: '是否禁用', align:'center',width:'10%',templet:function(d){
    	 var text = '-';
         if (d.isDisable == 0) {
             text = "否";
         } else if (d.isDisable == 1) {
             text = "是";
         }	
         return text;
  	  }},
     {field: 'remarks', title: '备注',width:'38%',align:'center'},
     {fixed: 'right', title: '操作', width:'10%',align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryConfigList',GetURLInfo() +'configDetails',GetURLInfo() + "deleteConfig");
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
	
	//加载查询条件下拉选择框
	queryConfigType();

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

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'configAdd',true,[ '500px', '400px' ]);
		 		}
		 	)
		 }();
		 
	
	// 加载宣传员等级
	function queryConfigType(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
	        url :GetURLInfo() + 'queryConfigType',
	        datatype : "json",
	        success : function(result) {
	            for (var o in result.data){
	                var str = "<option value=" + result.data[o].type + ">" + result.data[o].type + "</option>";
	                $("#type").append(str);
	            }
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}	
