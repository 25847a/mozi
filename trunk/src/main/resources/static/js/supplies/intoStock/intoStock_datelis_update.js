$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateUnitForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateSurplusNumber",
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
    $("#updateUnitForm").validate({
        //验证规则 
        rules: {
            name:{
                required:true,
                minlength:0,
                maxlength:12
            }
        },
        messages: {
        	name: {
                required: "请输入昵称",
                minlength: "昵称长度不允许小于3位",
                maxlength: "昵称长度不允许大于12位"
            }
        }
    });

})
