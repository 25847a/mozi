$(function(){
	layui.use(['laydate', 'laypage', 'table'], function () {
		$("#confirm").click(function(){
			var providerNo=$("#providerNo").val();
			var albumin=$("#albumin").val();
			var atlas=$("#atlas").val();
			var remarks=$("#remarks").val();
			var id=$("#id").val();
			if(providerNo.length<1){
				layer.alert('请输入浆员卡号');
				return false;
			}else if(albumin.length<1){
				layer.alert('请选择白蛋白');
				return false;
			}else if(atlas.length<1){
				layer.alert('请选择图谱');
				return false;
			}else{
				$.post("/serumElectrophoresis/updateSerumElectrophoresis",{"id":id,"albumin":albumin,"atlas":atlas,"remarks":remarks},function(result){
					if(result.code==-1) {
						 layer.alert(result.message,function(){
							 layer.closeAll();
							 parent.location.reload();
						 });
					 }else{
						 layer.alert(result.message);
					 }
				});
			}
		});
		
		//取消
		$("#cannel").click(function(){
			var index=parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
	});
})