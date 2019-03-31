$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateGroup",
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
    $("#updateDepotForm").validate({
    	//验证规则
        rules: {
        	num:{
                required:true,
            },
            groupName:{
                required:true
            }
        },
        messages: {
        	num: {
                required: "请输入小组组号",
            },
            groupName: {
                required: "请输入小组名称",
            }
        }
    });
})
