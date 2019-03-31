$(function(){
    // 加载新增角色选项
    $.ajax({
        type : "POST",
        url :"/sysPermission/queryMenuList",
        datatype : "json",
        success : function(result) {
            $("#parentId").append("<option value='0'>请选择</option>");
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#parentId").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addMenuForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertMenu",
                success : function(result) {
                    checkCode(result,function(){
                        if(result.code==-1){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                            });
                        }else{
                            parent.layer.alert(result.message);
                        }
                    });
                },
                error : function() {
                    alert("上传失败，请检查网络后重试,上传文件太大");
                }
            });
        }
    });
    $("#addMenuForm").validate({
        //验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:20
            },
           /* url:{
                required:true
            },
            permission:{
                required:true
            }*/
        },
        messages: {
            name: {
                required: "请输入资源名称",
                minlength: "资源名称长度不允许少于2位",
                maxlength: "资源名称长度不允许多于8位"
            },
           /* url:{
                required:"路径不允许为空"
            },
            permission:{
                required:"权限字符串不允许为空"
            }*/
        }
    });
})