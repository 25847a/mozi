/**
 * Created by wangjing on 2018-05-05.
 */
$(function(){
    // 加载新增角色选项
    $.ajax({
        type : "POST",
        url :"/sysPermission/queryMenuList",
        datatype : "json",
        success : function(result) {
            $("#parentId").append("<option value='0'>请选择</option>");
            var pid=$("#pid").val();
            for (var o in result.data){
                if(pid==result.data[o].id){
                    var str = "<option selected='selected'  value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                }else{
                    var str = "<option  value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                }

                $("#parentId").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });

    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateMenuForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"updateMenu",
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
    $("#updateMenuForm").validate({
        //验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:20
            },
            url:{
                required:true
            },
            permission:{
                required:true
            }
        },
        messages: {
            name: {
                required: "请输入资源名称",
                minlength: "资源名称长度不允许少于2位",
                maxlength: "资源名称长度不允许多于8位"
            },
            url:{
                required:"路径不允许为空"
            },
            permission:{
                required:"权限字符串不允许为空"
            }
        }
    });

})

