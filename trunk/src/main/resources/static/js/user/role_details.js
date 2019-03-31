/**
 * Created by wangjing on 2018-05-05.
 */
$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateRoleForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"updateRole",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
                        });
                    });
                },
                error : function() {
                    alert("上传失败，请检查网络后重试,上传文件太大");
                }
            });
        }
    });
    $("#updateRoleForm").validate({
        //验证规则
        rules: {
            name: {
                required: true,
                minlength: 2,
                maxlength: 8
            },
            depict: {
                required: true,
                minlength: 4,
                maxlength: 50,
            },
            isDisable: {
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
            isDisable: {
                required: "请选择角色是否可用",
            }
        }
    });

})


