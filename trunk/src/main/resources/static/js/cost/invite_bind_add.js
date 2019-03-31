$(function(){
	
	/*$("#sub").click(function(){
		var inviterDate = $("#inviterDate").val();
		var inviter = $("#inviter").val();
		var invited = $("#invited").val();
		$.ajax({
			url:,
			data:{"inviterDate":inviterDate,"inviter":inviter,"invited":invited},
			type:"post",
			dataType:"json",
			success:function(result){
				console.log(result);
			}
		});
	});*/
	var objParam = location.search.substring(location.search.indexOf("=")+1,location.search.length);
	$("#id").val(objParam);
	$.validator.setDefaults({
		submitHandler:function() {
			$("#addBindFit").ajaxSubmit({
				url:GetURLInfo()+"bindAdd",
				type:"post",
				dataType:"json",
				success:function(result){
					console.log(result);
					if(result.code == "-1"){
						parent.location.reload();
						var index = parent.layer.getFrameIndex(window.name);
				        parent.layer.close(index);
					}else if(result.code != "-1"){
						layer.alert(result.message);
					}
				}
			});
		}
	});
	
	$("#addBindFit").validate({
		rules:{
			inviterDate:{
				required:true
			},
			inviter:{
				required:true,
				isIDNo:true
			}
			/*invited:{
				required:true,
				isPhoneNum:true
			}*/
		},
		messages:{
			inviterDate:{
				required:"请选择邀请人类型"
			},
			inviter:{
				required:"请输入邀请人身份证号码",
				isIDNo:"请输入正确的身份证号码"
			}
			/*invired:{
				required:"请输入被邀请人手机号码",
				isPhoneNum:"请输入正确的手机号码"
			}*/
		}
	});
	/*jQuery.validator.addMethod("isPhoneNum", function(value, element) {  
	    var length = value.length;  
	    var regPhone = /^1([3578]\d|4[57])\d{8}$/;  
	    return this.optional(element) || ( length == 11 && regPhone.test( value ) );    
	}, "请正确填写您的手机号码");  */
	jQuery.validator.addMethod("isIDNo", function(value, element) {  
	    var length = value.length;  
	    var regPhone = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;  
	    return this.optional(element) || ( length == 18 && regPhone.test( value ) );    
	}, "请正确填写您的手机号码");  
	
	$("#cannel").click(function(){
		var index = parent.layer.getFrameIndex(window.name); 
		parent.layer.close(index);
	});
});