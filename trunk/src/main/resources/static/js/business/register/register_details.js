$(function(){
    //点击修改表单
	$("#quey").click(function(){
		var providerNo = parent.$("#providerNo").val();
		var isIdentity = parent.$("#result").val();
		var type = parent.$("#type").val();
		parent.insertRegistries(providerNo,isIdentity,type);
		parent.layer.closeAll();
	});
})
