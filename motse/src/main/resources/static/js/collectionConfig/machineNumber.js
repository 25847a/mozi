$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'name', title: '机器名称',align:'center'},
     {field: 'status', title: '机器状态', align:'center',templet:function(d){
      	  return getMachineNoValue(d.status);
  	  }},
     {field: 'plasmTypeId', title: '采浆机型', align:'center'},
     {field: 'type', title: '机号类型', align:'center',templet:function(d){
     	  return getMachineNoType(d.type);
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
   data("rumsers",cols,GetURLInfo() + 'queryMachineNumberList',GetURLInfo() +'machineNumberDetails',GetURLInfo() + "deleteMachineNumber");

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
	queryRoomByPlasmType();

})

	//搜索
	function doSearch(){
		var name = $("#keyword").val()
		var PlasmTypeId = $("#plasmTypeId").val()
		var roomId = $("#roomId").val()
		layui.table.reload('rumsers', {where: {
			"name": name,
			"PlasmTypeId":PlasmTypeId,
			"roomId":roomId
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'machineNumberAdd',true,[ '500px', '380px' ]);
		 		}
		 	)
		 }();
		 
	
	// 加载采室选项
	function queryRoomByPlasmType(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
	        url :'/room/queryRoomByPlasmType',
	        datatype : "json",
	        success : function(result) {
	            for (var o in result.data){
	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
	                $("#roomId").append(str);
	            }
	            queryPlasmTypeByMachine();
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}
	
	// 加载机型名称选项
	function queryPlasmTypeByMachine(){
		var roomId =$("#roomId").val();	//获取采室id
		$.ajax({
		    type : "POST",
		    cache: false,  // 禁用缓存
		    url :'/room/queryPlasmTypeByMachine',
		    data: {"roomId": roomId}, // 传入组装的参数
		    datatype : "json",
		    success : function(result) {
		    	$("#plasmTypeId").empty();//首先清空select现在有的内容
		    	$("#plasmTypeId").append("<option value=''>请选择</option>");
		    	for (var o in result.data){
		            var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
		            $("#plasmTypeId").append(str);
		        }
		    },
		});
	}
	
