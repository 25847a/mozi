/**
 * Created by wangjing on 2018-05-05.
 */
$(function() {
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#roleaddForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertRole",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
                        });
                    });
                },
                error : function() {
                    alert("操作失败");
                }
            });
        }
    });

    $("#roleaddForm").validate({
        //验证规则
        rules: {
            name: {
                required: true,
                minlength: 2,
                maxlength: 8
            },
            depict: {
                required: true,
                minlength: 1,
                maxlength: 50,
            },
            available: {
                required: true
            }
        },
        messages: {
            name: {
                required: "请输入角色名称",
            },
            depict: {
                required: "请输入角色描述",
                minlength: "角色描述不允许小于4位",
                maxlength: "角色描述不允许大于50位",
            },
            available: {
                required: "请选择角色是否可用",
            }
        }
    });
})