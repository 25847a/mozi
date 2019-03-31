$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#order").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/delectOrder",
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
                	parent.layer.msg("删除失败");
                }
            });
        }
    });
    $("#order").validate({
        //验证规则 
        rules: {
            remarks:{
                required:true
            }
        },
        messages: {
            contacts: {
                required: "请输入备注",
            }
        }
    });
})
