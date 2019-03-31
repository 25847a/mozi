$(function(){
	layui.use(['laydate', 'laypage', 'table']);
	// 根据浆员卡号查询
	$("#chaxun").click(function(){
		var providerNo=$("#providerNo").val();
		if(providerNo.length<1){
			layer.alert('请输入浆员卡号');
		}else{
			//获取浆员详细信息
			$.ajax({
				type : "POST",
				url : "/common/queryWhereBaseInfoOrDetailObj",
				datatype : "json",
				data:{"providerNo":providerNo},
				success : function(data) {
					if(data.code==-1){
						$("#name").val(data.data.name);
						$("#idNo").val(data.data.idNo);
						$("#demo1").attr('src',data.data.imagez);
						$("#id").val(data.data.id);
					}
				}
			});
		}
	});
	
	//确定
	$("#confirm").click(function(){
		var providerNo=$("#providerNo").val();
		if(providerNo.length<1){
			layer.alert('请输入浆员卡号');
			return false;
		}
		var id=$("#id").val();
		if(id.length<1){
			layer.alert("请输入正确浆员卡号");
			return false;
		}
		var roadFee=$("#roadFee").val();
		if(roadFee.length<1){
			layer.alert("请输入备注");
			return false;
		}
		var remarks=$("#remarks").val();
		if(remarks.length<1){
			layer.alert("请输入备注");
			return false;
		}
		$.post('/providerRegistries/addOldPlasmaCost',{"providerNo":providerNo,"remarks":remarks,"roadFee":roadFee},function(res){
			if(res.code==-1){
				layer.alert(res.message,function(){
					parent.layer.closeAll();
				});
			}else{
				layer.alert(res.message);
			}
		});
	});
	
	//取消
	$("#cacal").click(function(){
		layer.closeAll();
	});
})