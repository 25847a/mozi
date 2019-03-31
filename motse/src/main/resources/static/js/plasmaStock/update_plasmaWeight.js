$(function(){
	layui.use(['laydate', 'laypage', 'table'], function () {});
	var url = location.search; //获取url中"?"符后的字串
	url = url.substring(1,url.length);
	var allId = url.split('=');
	$("#allId").val(allId[1]);
	//读取 电子秤重量
	$("#read_plasmAmount").click(function(){
		$.post('/common/readPlasmAmount',function(res){
			if(res.code==-1){
				$("#plasmAmount").val(res.data.scale);
			}else{
				layer.alert(res.message);
			}
		});
	});
	
	//确定修改浆量
	$("#confirm").click(function(){
		var plasmAmount = $("#plasmAmount").val();
		if(plasmAmount.length<1){
			layer.alert("请输入浆量");
		}else{
			var allIds = $("#allId").val();
			$.post('/plasmCollection/updatePlasmaWeight',{'allId':allIds,'plasmAmount':plasmAmount},function(res){
				layer.alert(res.message,function(){
					 var index = parent.layer.getFrameIndex(window.name);
                     parent.layer.closeAll();
				});
			});
		}
	});
})