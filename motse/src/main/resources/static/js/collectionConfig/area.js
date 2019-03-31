$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'province', title: '省级',align:'center'},
     {field: 'county', title: '县级',align:'center'},
     {field: 'town', title: '乡镇级',align:'center'},
     {field: 'roadFee', title: '费用',align:'center'},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return $.myTime.UnixToDate(d.createDate);
   	  }},
     {field: 'updateDate', title: '修改时间', align:'center',templet:function(d){
    	  return $.myTime.UnixToDate(d.updateDate);
   	  }},
     {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryAreaList',GetURLInfo() +'areaDetails',GetURLInfo() + "deleteArea");

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
	
	
    // 加载区域县选项
    $.ajax({
        type : "POST",
        url :GetURLInfo() +'queryAreaByType',
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].county + ">" + result.data[o].county + "</option>";
                $("#county").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
})

	//搜索
	function doSearch(){
		var town = $("#keyword").val()
		var county = $("#county").val()
		layui.table.reload('rumsers', {where: {
			"town": town,
			"county": county
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'areaAdd',true,[ '500px', '300px' ]);
		 		}
		 	)
		 }();
