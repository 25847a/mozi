$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDiseaseForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateDisease",
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
    $("#updateDiseaseForm").validate({
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
                required: "请输入淘汰原因",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            }
        }
    });

})
