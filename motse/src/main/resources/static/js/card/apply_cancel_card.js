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
      }}
      ,{field: 'idNo', title: '身份证号码', width:'30%',align:'center'}
      ,{field : 'imagez',
			title : '身份证正面', 
			type : "space"}
      ,{field : 'imagef',
			title : '身份证反面', 
			type : "space"}
    ]]
      
      //data("newtest",cols,GetURLInfo() + 'cardList');
      var date = $("#endTime").val();  
		dataAll("newtest",cols,{"createDate":date},"haveGrantCard",'','',function(){
			var tab = $("#newtest").next().find('.layui-table tbody tr');
		      tab.click(function(event) {
			    	var tr = $(event.target).closest("tr")[0];
			    	var id = $(tr).find("td").eq(0).find("div").html();
					$("#id").val(id);
					var providerNo = $(tr).find("td").eq(1).find("div").html();
					$("#providerNo_hide").val(providerNo);
					var imagez = $(tr).find("td").eq(6).find("div").html();
					$("#play").attr("src",imagez);
					
		      });
		});
		 
	});
	
	//查询
	$("#query").click(function(){
		var name=$("#name").val();
		var providerNo=$("#providerNo").val();
		layui.table.reload('newtest', {where: {"name": name,"providerNo":providerNo}});
	});
  
	//取消发卡
	$("#cancel").click(function(){
		var providerNo = $("#providerNo").val();
		if(providerNo.length<1){
			layer.alert("请选择浆员");
		}else{
			$.ajax({
				type : "post",
				url : '/card/sendCancelCard',
				data : {"providerNo":providerNo},
				async : false,
				success : function(res) {
					layer.alert(res.message);
				}
			})
		}
	});
});