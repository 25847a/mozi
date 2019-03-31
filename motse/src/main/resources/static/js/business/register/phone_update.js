$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#phone").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updatePhoneNumber",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
                            parent.window.reload();
                        });
                    });
                },
                error : function() {
                	parent.layer.alert("修改失败",function(){
           				parent.layer.closeAll();
           			});
                }
            });
        }
    });
    $("#phone").validate({
        //验证规则 
        rules: {
            phone:{
                required:true,
            }
        },
        messages: {
        	phone: {
                required: "请输入手机号码",
            }
        }
    });

})
