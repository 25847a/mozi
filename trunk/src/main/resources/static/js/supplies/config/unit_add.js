$(function(){
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addUnitForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertUnit",
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
    $("#addUnitForm").validate({
        //验证规则 
        rules: {
            name:{
                required:true,
            }
        },
        messages: {
        	name: {
                required: "请输入昵称",
            }
        }
    });

})
