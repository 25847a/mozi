$(document).ready(function(){
	initDate("#endTime");
})
$(function(){
	
	layui.use(['table'], function(){
      var cols = [[{field : 'id',
			title : 'id', /*
			minWidth : '0',
			width : '0',*/
			type : "space"}
      ,{field: 'providerNo',width:'20%', title: '浆员卡号', align:'center'}
      ,{field: 'name', title: '姓名', width:'20%',align:'center'}
      ,{field: 'sex', title: '性别', align:'center',width:'20%',templet:function(obj){
    	  return obj.sex == 0 ? "男" : "女";
      }}
      ,{field: 'bloodType', title: '血型', align:'center',width:'20%',templet:function(obj){
    	  var bt = "";
    	  if(obj.bloodType == 0){
    		  bt = "A";
    	  }else if(obj.bloodType == 1){
    		  bt = "B";
    	  }else if(obj.bloodType == 2){
    		  bt = "O";
    	  }else if(obj.bloodType == 3){
    		  bt = "AB";
    	  }
    	  return bt;
      }},
      {field : 'status',title : '状态',align:'center',width:'20%',templet:function(obj){
    	  if(obj.providerNo.indexOf("f")!=-1){
    		  return '未提交';
    	  }else if(obj.status==0 && obj.isGrantCard==1){
    		  return '已提交，未审核'
    	  }else if(obj.status==1){
    		  return '已审核';
    	  }else if(obj.status==0 && obj.isGrantCard==0){
    		  return '未审核';
    	  }
    	  return '';
      }}
      ,{field: 'createDate', title: '发证日期',width:'20.7%', align:'center',templet:function(obj){
    	  return new Date(obj.createDate).Format("yyyy-MM-dd");
      }}
    ]]
      
      //data("newtest",cols,GetURLInfo() + 'cardList');
      var date = $("#endTime").val();  
		dataAll("newtest",cols,{"createDate":date},"cardList",'','',function(){
			var tab = $("#newtest").next().find('.layui-table tbody tr');
		      tab.click(function(event) {
			    	var tr = $(event.target).closest("tr")[0];
					var id = $(tr).find("td").eq(0).find("div").html();
					$("#id").val(id);
					var providerNo = $(tr).find("td").eq(1).find("div").html();
					$("#providerNo").val(providerNo);
					var name = $(tr).find("td").eq(2).find("div").html();
					$("#name").val(name);
					var sex = $(tr).find("td").eq(3).find("div").html();
					$("#sex").val(sex);
					var bloodType = $(tr).find("td").eq(4).find("div").html();
					$("#bloodType").val(bloodType);
					var createDate = $(tr).find("td").eq(5).find("div").html();
					$("#createDate").val(createDate);
					loadImage(providerNo);
					//获取浆员发卡记录次数
					$.ajax({
						url:"../cardRecord/countCardRecord",
						type:"post",
						dataType:"json",
						data:{"id":id},
						success:function(result){
							$("#num").val(result.data);
						}
					});
		      });
		});
//      table.on('tool(demo)',function(obj){ 
//    	  //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
//    	    var data = obj.data //获得当前行数据
//    	    ,layEvent = obj.event; //获得 lay-event 对应的值
//    	    alert(data);
//    	  });
      
		//提交审核
		$("#audit").click(function(){
			var providerNo = $("#providerNo").val();
			var bloodType = $("#bloodType").val();
			if(providerNo == ""){
				layer.alert("请选择浆员进行提交审核");
			}else{
				//先更改所有相关表的浆员编号
				$.ajax({
					url:"../card/changeProviderNo",
					dataType:"json",
					type:"post",
					async: false,
					data:{"providerNo":providerNo,"bloodType":bloodType},
					success:function(result){
						if(result.code == -1){
							/*if(result.data.newProviderNo != null){
								layer.alert(result.message);
								$("#providerNo").val(result.data.newProviderNo);
								layui.table.reload('newtest', {where: {"createDate": date}});
							}else{
								layer.alert(result.message);
							}*/
							layer.alert(result.message);
							var date = $("#endTime").val();
							layui.table.reload('newtest', {where: {"createDate": date}});
						}else{
							layer.alert(result.message);
						}
					}
				});
			}
		});
	});
      
      //读卡
      $("#readCard").click(function(){
    	  var pNo = $("#providerNo").val();
			$.ajax({
      		  url:"../common/readICNumber",
      		  type:"post",
      		  dataType:"json",
      		  success:function(result){
      			  if(result.code == -1){
      				  $("#icNum").val(result.data.id);
      			  }else{
      				layer.alert(result.message);
      			  }
      		  }
      	  });
      });
	
	$("#query").click(function(){
		var date = $("#endTime").val();
		layui.table.reload('newtest', {where: {"createDate": date}});
		layui.table.reload('newtestS', {where: {"createDate": date}});
	});
  
	//取消发卡
	$("#cancel").click(function(){
		/*var id = $("#id").val();
		if(id == "" || id == null){
			layer.alert("请选择浆员进行取消发卡");
		}else{
			$.ajax({
				url:"../card/cancelCard",
				type:"post",
				dataType:"json",
				data:{"id":id},
				success:function(result){
					if(result.code == -1){
						var date = $("#endTime").val();
						layui.table.reload('newtest', {where: {"createDate": date}});
						layui.table.reload('newtestS', {where: {"createDate": date}});
						layer.alert("取消发卡成功");
					}else{
						layer.alert("取消发卡失败");
					}
				}
			});
		}*/
		layer.open({
			type: 2,
		         title: '取消发卡',
			maxmin: true,
			area: ['90%', '80%'],
			content: '../card/cancelCard',
		});
	});
	
	//申请取消发卡
	$("#cancel_card").click(function(){
		layer.open({
			type: 2,
		    title: '申请取消发卡',
			maxmin: true,
			area: ['90%', '80%'],
			content: '../card/applyCancelCard',
		});
	});
});


/*发卡*/
! function() {
	$('#grantCard').on('click', function() {
		var icNum = $("#icNum").val();
		var id = $("#id").val();
		var providerNo = $("#providerNo").val();
		var bloodType = $("#bloodType").val();
		if(id == "" || id == null){
			layer.alert("请选择未发卡人员");
		}else if(icNum == ""){
			layer.alert("请先读取卡片");
		}else{
			$.ajax({
				url:GetURLInfo()+"grantCard",
				type:"post",
				dataType:"json",
				data:{"id":id,"icNum":icNum,"providerNo":providerNo},
				success:function(result){
					layer.alert(result.message,function(){
						if(result.code == -1){
							//获取浆员发卡记录次数
							$.ajax({
								url:"../cardRecord/countCardRecord",
								type:"post",
								dataType:"json",
								data:{"id":id},
								success:function(result){
									$("#num").val(result.data);
								}
							});
							location.reload();
						}
						layer.closeAll();
					});
				}
			});
		}
		//layer.alert('张三发卡成功', {icon: 6});
	})
}(); 
//刷新
$("#refresh").click(function(){
	window.location.reload();
});
/*写入*/
//
//
 
! function() {
		$('#writeCard').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
			         title: '写卡',
				maxmin: true,
				area: ['90%', '80%'],
				content: '../card/toWriteCard',
			})
		});
		});
	}();

/*面部识别*/
//
//
! function() {
	
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#puverfor').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '面部验证',
				maxmin: true,
				area: ['550px', '370px'],
				content: '../../Popup/Bus/Bus_Facialrecognition.html',
			})
		});
	})
}(); 
 
//已发卡浆员信息
	layui.use(['table', ], function() {
		var cols = [[{field: 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ,{field: 'providerNo', title: '浆员卡号',width:'20%', align:'center'}
	      ,{field: 'icNumber', title: 'IC卡号',width:'25%', align:'center'}
	      ,{field: 'name', title: '姓名',width:'20%', align:'center'}
	      ,{field: 'sex', title: '性别',width:'10%', align:'center',templet:function(obj){
	    	  return obj.sex == 0 ? "男" : "女";
	      }}
	      ,{field: 'bloodType', title: '血型',width:'10%', align:'center',templet:function(obj){
	    	  var bt = "";
	    	  if(obj.bloodType == 0){
	    		  bt = "A";
	    	  }else if(obj.bloodType == 1){
	    		  bt = "B";
	    	  }else if(obj.bloodType == 2){
	    		  bt = "O";
	    	  }else if(obj.bloodType == 3){
	    		  bt = "AB";
	    	  }
	    	  return bt;
	      }}
	      ,{field : 'icNumber',title : '是否写卡',width:'20%', align:'center',templet:function(obj){
	    	  if(obj.isGrantCard==2){
	    		  return '已写卡';
	    	  }
	    	  return '未写卡';
	      }}
	      ,{field : 'imagez',title : '身份证正面', type : "space"}
	      ,{field : 'imagef',title : '身份证反面', type : "space"}
	      ]]
	      //data("newtestS",cols,GetURLInfo() + 'cardList');
		var date = $("#endTime").val();  
		dataAll("newtestS",cols,{"createDate":date},"haveGrantCard",'','',function(){
			var tab = $("#newtestS").next().find('.layui-table tbody tr');
		      tab.click(function(event) {
			    	var tr = $(event.target).closest("tr")[0];
					var id = $(tr).find("td").eq(0).find("div").html();
					$("#id").val(id);
					var providerNo = $(tr).find("td").eq(1).find("div").html();
					$("#providerNo").val(providerNo);
					var icNum = $(tr).find("td").eq(2).find("div").html();
					$("#icNum").val(icNum);
					var name = $(tr).find("td").eq(3).find("div").html();
					$("#name").val(name);
					var sex = $(tr).find("td").eq(4).find("div").html();
					$("#sex").val(sex);
					var bloodType = $(tr).find("td").eq(5).find("div").html();
					$("#bloodType").val(bloodType);
					//var createDate = $(tr).find("td").eq(5).find("div").html();
					$("#createDate").val(new Date($("#endTime").val()).Format("yyyy-MM-dd"));
					loadImage(providerNo);
					//获取浆员发卡记录次数
					$.ajax({
						url:"../cardRecord/countCardRecord",
						type:"post",
						dataType:"json",
						data:{"id":id},
						success:function(result){
							$("#num").val(result.data);
						}
					});
		      });
		
		});
	});

	function loadImage(providerNo){
		$.ajax({
			type : "POST",
			url : "/common/queryWhereBaseInfoOrDetailObj",
			datatype : "json",
			data:{"providerNo":providerNo},
			success : function(data) {
				if(data.code==-1){
					$("#play").attr("src",data.data["imagez"]);
					$("#demo1").attr("src",data.data["imagef"]);
				}
			}
		});
	}

