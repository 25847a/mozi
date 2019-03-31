$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#order").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateOrder",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
                            parent.location.reload();
                        });
                    });
                },
                error : function() {
                	parent.layer.msg("修改失败");
                }
            });
        }
    });
    $("#order").validate({
        //验证规则 
        rules: {
        	sumMoney:{
                required:true
            },
            remarks:{
                required:true
            }
        },
        messages: {
        	name: {
                required: "请输入 金额总价",
            },
            contacts: {
                required: "请输入备注",
            }
        }
    });
})
