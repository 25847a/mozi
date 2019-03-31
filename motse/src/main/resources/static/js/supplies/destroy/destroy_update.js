$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#order").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateDestroy",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
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
    $("#order").validate({
        //验证规则
        rules: {
            remarks:{
                required:true
            }
        },
        messages: {
            contacts: {
                required: "请输入销毁原因",
            }
        }
    });
})
