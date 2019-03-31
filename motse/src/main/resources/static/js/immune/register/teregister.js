$(function(){
	// 加载免疫类型选项
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmuneSettingList",
        datatype : "json",
        data:{
        	type:"2"
        },
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
                $("#immuneId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
	$("#immuneId").change(function(){
		loadresult($("#immuneId").val());
	})
    
   var cols=[[ //表头
    	{field: 'providerNo', title: '浆员卡号',align:'center',width:'14%'},
    	{field: 'name', title: '姓名',align:'center',width:'15%'},
    	{field: 'sex', title: '性别',align:'center',width:'14%',templet:function(d){
        	  return getSexValue(d.sex);
      	  }},
    	{field: 'type', title: '类型',align:'center',width:'14%',templet:function(d){
        	  return getPbTypeValue(d.type);
      	  }},
    	{field: 'bloodType', title: '血型',align:'center',width:'14%',templet:function(d){
        	 return getBloodValue(d.bloodType);
      	  }},
    	{field: 'birthday', title: '生日',align:'center',width:'16%',templet:function(d){
    		return dateFtt("yyyy-MM-dd",new Date(d.birthday));
      	  }},
      	  //
      	{field: 'addressx', title: '住址',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
      	{field: 'icNumber', title: 'IC卡号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
      	{field: 'ordinaryNo', title: '普免编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
      	{field: 'registriesNo',display : 'none',minWidth : '0',width : '0',type : "space",title: '登记号',align:'center'},
    	{field: 'immuneId', title: '免疫ID',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
    	{field: 'id', title: '普免登记ID',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
    	{field: 'imgUrl', title: '图片',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
    	{field: 'status', title: '状态',align:'center',width:'14%',templet:function(d){
    		if(d.status==0){
    			return '未审核';
    		}else{
    			return '已审核';
    		}
    	  }}
    ]];
	 dataAllAndWHWithPage("teregisterNo",cols,{"token" : getToken()},GetURLInfo()+'queryImmuneRegisterSpecial','','','',true,function(){
			//表格 tr 单击事件
		   var tab = $("#teregisterNo").next().find('.layui-table tbody tr');
		   tab.click(function(event){
		   	  var tr =$(event.target).closest("tr")[0];
		   	  $("#providerNo").val($(tr).find("td").eq(0).find("div").html());
		   	  $("#name").val($(tr).find("td").eq(1).find("div").html());
		   	  $("#sex").val($(tr).find("td").eq(2).find("div").html());
		   	  $("#birthday").val($(tr).find("td").eq(5).find("div").html());
		   	  $("#addressx").val($(tr).find("td").eq(6).find("div").html());
		   	  $("#type").val($(tr).find("td").eq(3).find("div").html());
		   	$("#icNumber").val($(tr).find("td").eq(7).find("div").html());
		   	$("#registriesNo").val($(tr).find("td").eq(9).find("div").html());
		   	$("#immuneId").val($(tr).find("td").eq(10).find("div").html());
		   	$("#immuneId").attr("disabled",false);
		   	$("#typeCode").val($(tr).find("td").eq(8).find("div").html());
		   	$("#id").val($(tr).find("td").eq(11).find("div").html());
		   	$("#play").attr("src",$(tr).find("td").eq(12).find("div").html());
		   	$("#register").val(1);
		   	  var createDate = $("#startTime").val();
		   });
});
  
	 
	 var colsYes=[[ //表头
	    	{field: 'providerNo', title: '浆员卡号',align:'center',width:'14%'},
	    	{field: 'name', title: '姓名',align:'center',width:'15%'},
	    	{field: 'sex', title: '性别',align:'center',width:'14%',templet:function(d){
	        	  return getSexValue(d.sex);
	      	  }},
	    	{field: 'type', title: '类型',align:'center',width:'14%',templet:function(d){
	        	  return getPbTypeValue(d.type);
	      	  }},
	    	{field: 'bloodType', title: '血型',align:'center',width:'14%',templet:function(d){
	        	 return getBloodValue(d.bloodType);
	      	  }},
	    	{field: 'birthday', title: '生日',align:'center',width:'16%',templet:function(d){
	    		return dateFtt("yyyy-MM-dd",new Date(d.birthday));
	      	  }},
	      	  //
	      	{field: 'addressx', title: '住址',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	      	{field: 'icNumber', title: 'IC卡号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	      	{field: 'ordinaryNo', title: '普免编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	      	{field: 'registriesNo',display : 'none',minWidth : '0',width : '0',type : "space",title: '登记号',align:'center'},
	    	{field: 'immuneId', title: '免疫ID',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	    	{field: 'id', title: '普免登记ID',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	    	{field: 'imgUrl', title: '图片',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
	    	{field: 'status', title: '状态',align:'center',width:'14%',templet:function(d){
	    		if(d.status==0){
	    			return '未审核';
	    		}else{
	    			return '已审核';
	    		}
	    	  }}
	    ]];
	 dataAllAndWHWithPage("teregisterYes",colsYes,{"token" : getToken()},GetURLInfo()+'queryTeregisterAdopt','','','',true,function(){
			//表格 tr 单击事件
		   var tab = $("#teregisterYes").next().find('.layui-table tbody tr');
		   tab.click(function(event){
		  	  var tr =$(event.target).closest("tr")[0];
		   	  $("#providerNo").val($(tr).find("td").eq(0).find("div").html());
		   	  $("#name").val($(tr).find("td").eq(1).find("div").html());
		   	  $("#sex").val($(tr).find("td").eq(2).find("div").html());
		   	  $("#birthday").val($(tr).find("td").eq(5).find("div").html());
		   	  $("#addressx").val($(tr).find("td").eq(6).find("div").html());
		   	  $("#type").val($(tr).find("td").eq(3).find("div").html());
		   	$("#icNumber").val($(tr).find("td").eq(7).find("div").html());
		   	$("#registriesNo").val($(tr).find("td").eq(9).find("div").html());
		   	$("#immuneId").val($(tr).find("td").eq(10).find("div").html());
		   	$("#immuneId").attr("disabled",true);
		   	$("#typeCode").val($(tr).find("td").eq(8).find("div").html());
		   	$("#id").val($(tr).find("td").eq(11).find("div").html());
			$("#play").attr("src",$(tr).find("td").eq(12).find("div").html());
			$("#register").val(undefined);
		   	  var createDate = $("#startTime").val();
		   });
});
    
	//搜索按钮点击事件
	 $("#query").click(function(){
		 $("#num").val(undefined);
		 var startTime = $("#startTime").val();
		 if(startTime==''){
			 parent.layer.alert("请输入查询时间",function(){
				 parent.layer.closeAll();
			 });
		 }
	 	doSearch();
	 	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	 }); 
	 //显示所有浆员
	 $("#display").click(function(){
		 $("#num").val(1);
		 $("#startTime").val(undefined);
		 doSearch();
		 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	 });
})



//搜索
function doSearch(){
	var startTime = $("#startTime").val();
	var num = $("#num").val();
	layui.table.reload('teregisterNo', {where: {"startTime": startTime,"num":num}});
	layui.table.reload('teregisterYes', {where: {"startTime": startTime,"num":num}});
}
function getToken() {
	return localStorage.getItem("token");
}

function loadresult(id){
	$.ajax({
		type : "POST",
		url : "/immuneSetting/queryImmuneSettingIn",
		datatype : "json",
		data:{"id":id},
		success : function(data) {
			if(data.code==-1){
				$("#typeCode").val(data.message);
			}
		},
		error:function(){
			
		}
	});
}

/*日期时间(开始日期、结束日期)*/
initDate("#startTime");
	 /*登记*/
function skufansp(){
	var immuneId = $("#immuneId").val();
	var ordinaryNo = $("#typeCode").val();
	var id = $("#id").val();
	var register = $("#register").val();
	var providerNo = $("#providerNo").val();
	if(immuneId.length<1){
		parent.layer.alert("请选择免疫类型",function(){
				parent.layer.closeAll();
			});
		return false;
	}
	if(register==''){
		parent.layer.alert("已登记,请勿重复登记",function(){
			parent.layer.closeAll();
		});
		return false;
	}
	submission("dengji","登记中.....");//锁住登记按钮,防止重复登记
	 $.ajax({
	        type : "POST",
	        url :GetURLInfo()+"/updateteregister",
	        data:{
	        	"immuneId":immuneId,
	        	"ordinaryNo":ordinaryNo,
	        	"id":id,
	        	"providerNo":providerNo
	        },
	        datatype : "json",
	        success : function(result) {
	        	if(result.code==-1){
	        		parent.layer.alert(result.message,function(){
	        			parent.layer.closeAll();
	        			layui.table.reload('teregisterNo');
	        			layui.table.reload('teregisterYes');
	        			parent.$("number").val(0);
	        		});
	        	}
	        	unlock("dengji","登记");//解锁按钮,恢复正常提交
	        },
	        error : function() {
	        	parent.layer.alert("操作失败",function(){
	   				parent.layer.closeAll();
	   			});
	        }
	    });
}
/*取消登记*/
function skufanspas(){
	  var providerNo = $("#providerNo").val();
	  var immuneId = $("#immuneId").val();
		if(immuneId==''){
			parent.layer.alert("请选择已登记浆员进行取消登记",function(){
				parent.layer.closeAll();
			});
		return false;
		};
		submission("cancel","取消中.....");//锁住登记按钮,防止重复登记
		$.ajax({
			type:'post',
			url:GetURLInfo()+'/canceltergisterRegister',
			data:{"providerNo":providerNo,
					"immuneId":immuneId},
			dataType:'json',
			success:function(result){
				if(result.code==-1){
					layer.alert('成功取消登记');
					layui.table.reload('teregisterNo');
      			layui.table.reload('teregisterYes');
				}else{
					layer.alert(result.message)
				}
				unlock("cancel","取消登记");//解锁按钮,恢复正常提交
			},
			error:function(){
				layer.alert("操作失败,请重试")
			}
		});
      
}
  /**
   * 刷新页面
   * @returns
   */
  $("#Refresh").click(function(){
  	location.reload();
  });