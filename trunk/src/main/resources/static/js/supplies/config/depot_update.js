$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateDepot",
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
    $("#updateDepotForm").validate({
        //验证规则
        rules: {
        	name:{
                required:true
            },
            contacts:{
                required:true
            },
            mobile : {
                required : true,
                minlength : 11,
                maxlength: 11,
                isphoneNum : true
            },
            address:{
                required:true,
                minlength:2,
                maxlength:16
            }
        },
        messages: {
        	name: {
                required: "请输入仓库名称",
            },
            contacts: {
                required: "请选择联系人",
            },
            mobile: {
                required: "请输入手机号码",
                minlength: "手机长度必须为11位",
                maxlength: "手机长度必须为11位",
                isphoneNum: "请填写正确的手机号码"
            },
            address:{
                required: "请输入地址",
                minlength: "地址长度不允许小于6位",
                maxlength: "地址长度不允许大于16位",
            }
        }
    });
    //自定义手机号验证
    jQuery.validator.addMethod("isphoneNum", function(value, element) {
        var length = value.length;
        var mobile = /^1[3|4|5|8|7]{1}[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

})
