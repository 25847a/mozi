$(function(){
	// 获取免疫类型
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunType",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].value + ">" + result.data[o].configName + "</option>";
                $("#type").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#immuneAdd").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/insertImmuneSetting",
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
    $("#immuneAdd").validate({
        //验证规则
        rules: {
        	immuneName:{
                required:true
            },
            basicNum:{
                required:true
            },
            strengthenNum : {
                required : true,
            },
            invalidDate:{
                required:true,
            },
            type:{
                required:true,
            }
        },
        messages: {
        	immuneName: {
                required: "请输入免疫名称",
            },
            basicNum: {
                required: "请输入基础针数",
            },
            strengthenNum: {
                required: "请输入加强针数",
            },
            invalidDate:{
                required: "请输入失效期",
            },
            type:{
                required: "请选择类型",
            }
        }
    });
})
