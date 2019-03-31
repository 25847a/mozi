//页面动画调用
$(function() {
	$('body').particleground({
		dotColor: '#E8DFE8',
		lineColor: '#cae3ce'
	});
})
//获取数据连接信息
	$.ajax({
		type:"POST",
		url:"/log/queryDatabase",
		datatype:"json",
		success:function(data){
			if(data.code==-1){
				$("#txt_departmentname").val(data.data.IPString);
				$("#txt_parentdepartment").val(data.data.dataName);
				$("#txt_departmentlevel").val(data.data.dataWord);
				$("#txt_statu").val(data.data.database);
			}
		},
		error:function(){
			alert("请稍后重试");
		}
	});
/* 提示层*/ 
! function() {
	$('.secr').on('click', function() {
		layer.confirm('确定数据库连接？', {}, function() {
			var IPString = $("#txt_departmentname").val();
			var	dataName = $("#txt_parentdepartment").val();
			var dataWord = $("#txt_departmentlevel").val();
			var database = $("#txt_statu").val();
			$.ajax({
				type:"POST",
				url:"/log/testDatabase",
				data:{
					"IPString":IPString,
					"dataName":dataName,
					"dataWord":dataWord,
					"database":database
				},
				datatype:"json",
				success:function(data){
                    layer.msg(data.message, {
                        icon: 1
                    });
				},
				error:function(){
					alert("urdse").html("操作失败");
				}
			})
		}, function() {
			btn: ['确定', '取消 ']
		});
	})
}();

/*点击返回到登录页*/
function Fanir() {
    window.location.href = "login.html"
}

function lonzed() {
}