$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateRoom",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
                        });
                    });
                },
                error : function() {
                    alert("修改失败");
                }
            });
        }
    });
    $("#updateDepotForm").validate({
    	//验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:12
            },
            address:{
                required:true,
                minlength:2,
                maxlength:30
            },
            isSelected:{
                required:true
            }
        },
        messages: {
        	name: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            },
            address: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于30位"
            }
        }
    });
    
    // 加载修改状态下拉选择框
    $("#isSelected").val($("#isSelectedId").val());
})
