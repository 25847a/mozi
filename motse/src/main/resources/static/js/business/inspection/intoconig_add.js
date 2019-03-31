var idtep = 0;
function initData(id) {
	
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/testConfig/queryTestConfigById?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#templateName").val(data.templateName);
			$("#id").val(data.id);
			$("#cmDate").val(data.cmDate);
			$("#isDefault").val(data.isDefault);
			$("#status").val(data.status);
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

$(function() {
	initDate("#cmDate");
	var id=$("#id").val();
	if(undefined!=id && ''!=id){
		initData(id);
	}
	// 加载所有检测配置
	$.validator.setDefaults({
		submitHandler : function() {
			$("#addForm").ajaxSubmit({
				type : 'POST',
				url : GetURLInfo() + "insertTestConfig",
				success : function(result) {
					parent.layer.alert(result.message);
					if (result.code == -1) { // url跳转网页中传回的值。
						location.reload();
					}
				},
				error : function() {
				}
			});
		}
	});
	$("#addForm").validate({
		rules : {
			templateName : {
				required : true,
				minlength : 2,
				maxlength : 16
			},
			cmDate : {
				required : true
			}
		},
		messages : {
			templateName : {
				required : "请输入模板名称",
				minlength : "模板名称长度不允许少于2位",
				maxlength : "模板名称长度不允许多于16位"
			},
			cmDate : {
				required : "配置日期不能为空"
			}
		}
	});
});