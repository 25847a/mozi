$(function(){
	
	//$("#sub").click(function(){
	$.validator.setDefaults({
		submitHandler: function() {
			$("#addBindFit").ajaxSubmit({
				url:GetURLInfo()+"addBindFit",
				type:"post",
				dataType:"json",
				success:function(result){
					if(result.code == "-1"){
						parent.location.reload();
						var index = parent.layer.getFrameIndex(window.name);
				        parent.layer.close(index);
					}
				}
			});
		}
	});
	//});
	
	$("#addBindFit").validate({
		rules:{
			level:{
				required:true,
				isNum:true
			},
			proportion:{
				required:true,
				isFit:true
			},
			people:{
				required:true,
				isNum:true
			},
			num:{
				required:true,
				isNum:true
			}
		},
		messages:{
			level:{
				required:"请输入等级",
				isNum:"必须为数字"
			},
			proportion:{
				required:"请输入奖励比例",
				isFit:"必须为1-2位小数的且小于1的正实数"
			},
			people:{
				required:"请输入新浆员人次",
				isNum:"必须为数字"
			},
			num:{
				required:"请输入献浆次数",
				isNum:"必须为数字"
			}
		}
	});
	
	//自定义表单验证方法
	jQuery.validator.addMethod("isNum", function(value, element) {
        var num = /^[0-9]*$/;
        return this.optional(element) || (num.test(value));
    }, "必须为数字");
	
	jQuery.validator.addMethod("isFit", function(value, element) {
        var num = /^0+(.[0-9]{1,2})?$/;
        return this.optional(element) || (num.test(value));
    }, "1-2位小数的正实数");
	
	$("#cannel").click(function(){
		var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
	});
});