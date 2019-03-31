 var imageList;
　
$(function(){
	// 加载新增角色选项
	$.ajax({
		type : "POST",
		url :"/sysRole/queryRoleList",
		datatype : "json",
		success : function(result) {
            var check=$("#roleIds").val();
            for (var o in result.data){
                var str="";
                if(result.data[o].id==check){
                     str = "<option selected='selected' value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                }else{
                     str = "<option  value=" + result.data[o].id + ">" + result.data[o].name + " </option>";
                }

                $("#roleId").append(str);
            }
		},
		error : function() {
			alert("请稍后试试");
		}
	});

    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
        	var newpass = $("#newpass").val();
        	var passWord = $("#passWord").val();
        	if(newpass==passWord){
        		 $("#updateAdminForm").ajaxSubmit({
                     type : 'POST',
                     url : GetURLInfo() +"updateAdmin",
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
                         alert("上传失败，请检查网络后重试,上传文件太大");
                     }
                 });
        	}else{
        		parent.layer.alert("两次输入密码不同,请重新输入！");
        	}
           
        }
    });
    $("#updateAdminForm").validate({
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
            newpass:{
             //   required : true,
                minlength : 6,
                maxlength: 16,
                isPass : true
            },
            passWord:{
              //  required : true,
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
                minlength: "昵称长度不允许少于3位",
                maxlength: "昵称长度不允许多于12位"
            },
            mobile: {
                required: "请输入手机号码",
                minlength: "手机长度必须为11位",
                maxlength: "手机长度必须为11位",
                isphoneNum: "请填写正确的手机号码"
            },
            newpass:{
              //  required: "请输入密码",
                minlength: "密码长度不允许小于6位",
                maxlength: "密码长度不允许大于16位",
                isPass : "请输入正确的密码，必须是字母加数字"
            },
            passWord:{
            //    required: "请输入密码",
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

 //加载图片
 function addFile() {
     var preview = document.querySelector('img');
     var file  = document.querySelector('input[type=file]').files[0];
     var reader = new FileReader();
     reader.onloadend = function () {
         preview.src = reader.result;
     }
     if (file) {
         reader.readAsDataURL(file);
     } else {
         preview.src = "";
     }
 }


