$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'providerNo', title: '邀请人编号',width:'10%',align:'center'},
     {field: 'name', title: '姓名',width:'10%',align:'center'},
     {field: 'type', title: '类型',width:'10%', align:'center',templet:function(d){
    	 var text = '-';
         if (d.type == 0) {
             text = "宣传员";
         } else if (d.type == 1) {
             text = "体检医生";
         }	
         return text;
  	  }},
     {field: 'idNo', title: '身份证', width:'20%',align:'center'},
     {field: 'sex', title: '性别',width:'10%', align:'center',templet:function(d){
    	 return getSexValue(d.sex);
     }},
     {field: 'tel', title: '电话',width:'10%', align:'center'},
     {field: 'money', title: '可用金额',width:'10%', align:'center'},
     {field: 'freezeMoney', title: '冻结金额',width:'10%', align:'center'},
     {field: 'remark', title: '备注',width:'20%', align:'center'},
     {field: 'level', title: '等级',width:'8%', align:'center'},
     {field: 'createDate', title: '创建时间',width:'10%', align:'center',templet:function(d){
   	  return $.myTime.UnixToDate(d.createDate);
   	  }},
     {field: 'updateDate', title: '修改时间',width:'10%', align:'center',templet:function(d){
    	  return $.myTime.UnixToDate(d.updateDate);
   	  }},
     {fixed: 'right', title: '操作',  width:'20%'      ,align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'queryPropagandist',GetURLInfo() +'propagandistDetails',GetURLInfo() + "deletePropagandist");

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
	//加载宣传员等级
	queryPropagandistLevel();
})

	//搜索
	function doSearch(){
		var name = $("#keyword").val()
		var idNo = $("#idNo").val()
		var sex = $("#sex").val()
		var tel = $("#tel").val()
		var type = $("#type").val()
		var level = $("#level").val()
		layui.table.reload('rumsers', {where: {
			"name": name,
			"idNo": idNo,
			"sex": sex,
			"tel": tel,
			"type": type,
			"level": level
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'propagandistAdd',true,[ '500px', '470px' ]);
		 		}
		 	)
		 }();
		 
	// 加载宣传员等级
	function queryPropagandistLevel(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
	        url :GetURLInfo() + 'queryPropagandistLevel',
	        datatype : "json",
	        success : function(result) {
	            for (var o in result.data){
	                var str = "<option value=" + result.data[o].level + ">" + result.data[o].level + "</option>";
	                $("#level").append(str);
	            }
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}	
