$(function(){
	//点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"addRabatUnit",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                            });
                    });
                },
                error : function() {
                    alert("新增失败");
                }
            });
        }
    });
    $("#addDepotForm").validate({
        //验证规则
        rules: {
        	province:{
                required:true,
                minlength:2,
                maxlength:12
            },
            county:{
                required:true,
                minlength:2,
                maxlength:12
            },
            town:{
                required:true,
                minlength:2,
                maxlength:12
            }
        },
        messages: {
        	name: {
                required: "请输入检查单位"
            },
            isName: {
                required: "请选择是否默认"
            }
        }
    });
})