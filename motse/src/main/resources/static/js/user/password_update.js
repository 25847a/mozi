$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
        	var newpass = $("#newpass").val().trim();
        	var passWord = $("#passWord").val().trim();
        	if(newpass==passWord){
        		$("#updatePasswordForm").ajaxSubmit({
                    type : 'POST',
                    url : GetURLInfo() +"updatePassword",
                    success : function(result) {
                        parent.layer.alert(result.message);
                        checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                            });
                        });
                    },
                    error : function() {
                    	parent.layer.alert("上传失败，请刷新页面重新再试",function(){
               				parent.layer.closeAll();
               			});
                    }
                });
        	}else{
        		parent.layer.alert("两次输入密码不同,请重新输入！");
        	}
            
        }
    });
    $("#updatePasswordForm").validate({
        //验证规则
        rules: {
        	newpass:{
                required : true,
                minlength : 6,
                maxlength: 16,
                isPass : true
            },
            passWord:{
                required : true,
                minlength : 6,
                maxlength: 16,
                isPass : true
            }
        },
        messages: {
        	newpass:{
                required: "请输入密码",
                minlength: "密码长度不允许小于6位",
                maxlength: "密码长度不允许大于16位",
                isPass : "请输入正确的密码，必须是字母加数字"
            },
            passWord:{
                required: "请输入密码",
                minlength: "密码长度不允许小于6位",
                maxlength: "密码长度不允许大于16位",
                isPass : "请输入正确的密码，必须是字母加数字"
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



