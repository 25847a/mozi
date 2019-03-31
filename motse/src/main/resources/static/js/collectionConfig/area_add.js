$(function(){
    // 加载新增机型名称选项
    $.ajax({
        type : "POST",
        url :GetURLInfo() +'/queryAreaByType',
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].county + ">" + result.data[o].county + "</option>";
                $("#county").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertArea",
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
    $("#addDepotForm").validate({
    	 //验证规则
        rules: {
        	province:{
                required:true,
                minlength:2,
                maxlength:12
            },
            county:{
                required:true,
                minlength:2,
                maxlength:12
            },
            town:{
                required:true,
                minlength:2,
                maxlength:12
            },
            roadFee:{
                required:true,
                digits:true 
            }
        },
        messages: {
        	province: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            },
            county: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            },
            town: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            },
            roadFee: {
                required: "请输入金额",
                digits:"请输入正整数的金额"
            }
        }
    });
    
})
