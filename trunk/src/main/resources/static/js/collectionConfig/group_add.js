$(function(){
	
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addGroupForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertGroup",
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
    $("#addGroupForm").validate({
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
