$(function(){
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updatePropagandist",
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
    $("#updateDepotForm").validate({
    	//验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:12
            },
            idNo:{
                required:true,
                minlength:15,
                maxlength:18
            },
            type:{
                required:true
            },
            sex:{
                required:true
            },
            tel : {
                required : true,
                minlength : 11,
                maxlength: 11,
                isphoneNum : true
            },
            level:{
                required:true
            }
        },
        messages: {
        	name: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            },
            idNo: {
                required: "请输入名称",
                minlength: "长度不允许小于15位",
                maxlength: "长度不允许大于18位"
            },
            type: {
                required: "请选择类型"
            },
            sex: {
                required: "请选择性别"
            },
            tel: {
                required: "请输入手机号码",
                minlength: "手机长度必须为11位",
                maxlength: "手机长度必须为11位",
                isphoneNum: "请填写正确的手机号码"
            },
            level: {
                required: "请选择等级"
            }
        }
    });
  //自定义手机号验证
    jQuery.validator.addMethod("isphoneNum", function(value, element) {
        var length = value.length;
        var mobile = /^1[3|4|5|8|7]{1}[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");
    
    //加载宣传员等级
	queryPropagandistLevel();

    // 加载修改状态下拉选择框
    $("#sex").val($("#sexId").val());
    $("#type").val($("#typeId").val());
})
	// 加载宣传员等级
	function queryPropagandistLevel(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
	        url :GetURLInfo() + 'queryPropagandistLevel',
	        datatype : "json",
	        success : function(result) {
	            for (var o in result.data){
	                var str = "<option value=" + result.data[o].level + ">" + result.data[o].level + "</option>";
	                $("#level").append(str);
	                if(undefined!=level){
	                	$("#level").val($("#levelId").val());
	                }
	            }
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}	

