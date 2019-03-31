$(document).ready(function(){ 
	$("#content").html("你确定要将"+parent.$("#name").val()+"转类为"+parent.$("#immuneName option:selected").text()+"吗？");
});
$(function(){
	$("#quey").click(function(){
		var providerNo = parent.$("#providerNo").val();//浆员卡号
		var thisStatus = parent.$("#thisStatus").val();//当前状态
		var immuneName = parent.$("#immuneName").val();//转为类别
		
		$.ajax({
			url:"/conversionRecord/transferType",
			type:"post",
			dataType:"json",
			data:{"providerNo":providerNo,"thisStatus":thisStatus,"transferType":immuneName},
			success:function(result){
				parent.layer.closeAll();
				if(result.code == -1){
					parent.layui.table.reload('newtestS',{where:{"providerNo":""}});
					parent.layui.table.reload('newtestright',{where:{"providerNo":""}});
					parent.layer.alert("浆员转换类型成功");
				}else{
					parent.layer.alert(result.message);
				}
			}
		});
	});
});