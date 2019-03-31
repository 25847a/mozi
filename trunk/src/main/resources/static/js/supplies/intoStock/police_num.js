$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#police").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updatePolice",
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
    $("#police").validate({
        //验证规则
        rules: {
        	red:{
                required:true
            },
            green:{
                required:true
            },
            blue:{
                required:true
            }
        },
        messages: {
        	red: {
                required: "请输入数量",
            },
            green: {
                required: "请输入数量",
            },
            blue: {
                required: "请输入数量",
            }
        }
    });
})
