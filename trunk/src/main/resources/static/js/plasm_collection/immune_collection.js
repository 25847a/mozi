$(function(){
	$.ajax({
		type:'post',
		url:'/plasmYell/queryImmuneRefuse',
		dataType:'json',
		sync:false,
		success:function(result){
			if(result.code==-1){
				var currentBtn  = document.getElementById("cannel");
				if(result.data.isDisable!=1){
					 currentBtn.style = "padding: 3px 27px;";
				}
			}
		},
		error:function(){
			parent.layer.alert("操作失败,请重试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
    //点击修改表单
	$("#quey").click(function(){
		$.ajax({
			type:'post',
			url:'/plasmYell/operationImmunity',
			data:{
				"providerNo":parent.$("#providerNo").val()
			},
			dataType:'json',
			success:function(result){
				if(result.code==-1){
					parent.layer.alert("操作成功,请进行免疫登记",function(){
	       				parent.layer.closeAll();
	       			});
				}else{
					parent.layer.alert(result.message,function(){
	       				parent.layer.closeAll();
	       			});
				}
			},
			error:function(){
				parent.layer.alert("修改失败",function(){
       				parent.layer.closeAll();
       			});
			}
		})
		
	});
	$("#cannel").click(function(){
		var providerNo =parent.$("#providerNo").val();
		var allId =parent.$("#registriesNo").val();
		$.ajax({
			type:'post',
			url:'/plasmYell/updateImmuneRefuse',
			data:{
				"providerNo":providerNo,
				"allId":allId
				},
			dataType:'json',
			success:function(result){
				if(result.code==-1){
				}
			},
			error:function(){
				parent.layer.alert("操作失败,请重试",function(){
	   				parent.layer.closeAll();
	   			});
			}
		});
	});
})