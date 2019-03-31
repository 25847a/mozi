$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#control").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateControl",
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
    $("#control").validate({
        //验证规则
        rules: {
        	controlName:{
                required:true
            }
        },
        messages: {
        	controlName: {
                required: "请输入控制名称",
            }
        }
    });
})
