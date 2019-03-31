$(function(){
    // 加载新增角色选项
    $.ajax({
        type : "POST",
        url :"/sysRole/queryRoleList",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#roleId").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addAdminForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertAdmin",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                            });
                    });
                },
                error : function() {
                    alert("新增失败");
                }
            });
        }
    });
    $("#addAdminForm").validate({
        //验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:8
            },
            roleId:{
                required:true
            },
            sex:{
                required:true,
            },
            userName:{
                required:true,
                minlength:2,
                maxlength:15
            },
            mobile : {
                required : true,
                minlength : 11,
                maxlength: 11,
                isphoneNum : true
            },
            passWord:{
                required : true,
                minlength : 6,
                maxlength: 16,
                isPass : true
            },
            isDisable:{
                required:true
            }
        },
        messages: {
            roleId: {
                required: "请选择角色",
            },
            name: {
                required: "请输入姓名",
                minlength: "姓名长度不允许少于2位",
                maxlength: "姓名长度不允许多于8位"
            },
            sex: {
                required: "请选择性别"
            },
            userName: {
                required: "请输入昵称",
                minlength: "昵称长度不允许小于3位",
                maxlength: "昵称长度不允许大于12位"
            },
            mobile: {
                required: "请输入手机号码",
                minlength: "手机长度必须为11位",
                maxlength: "手机长度必须为11位",
                isphoneNum: "请填写正确的手机号码"
            },
            passWord:{
                required: "请输入密码",
                minlength: "密码长度不允许小于6位",
                maxlength: "密码长度不允许大于16位",
                isPass : "请输入正确的密码，必须是字母加数字"
            },
            isDisable:{
                required:"请选择状态"
            }
        }
    });
    //自定义手机号验证
    jQuery.validator.addMethod("isphoneNum", function(value, element) {
        var length = value.length;
        var mobile = /^1[3|4|5|8|7]{1}[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

  //自定义手机号验证
    jQuery.validator.addMethod("isPass", function(value, element) {
        var length = value.length;
        var pass = /^(?!([a-zA-Z]+|\d+)$)[a-zA-Z\d]{6,20}$/;
        return this.optional(element) || (pass.test(value));
    }, "请输入正确的密码，必须是字母加数字");
})