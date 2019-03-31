$(function(){
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#control").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/insertControl",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                            });
                    });
                },
                error : function() {
                	parent.layer.alert("新增失败",function(){
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
