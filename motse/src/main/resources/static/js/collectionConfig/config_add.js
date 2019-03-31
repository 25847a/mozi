$(function(){
	//加载查询条件下拉选择框
	queryConfigType();
	
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertConfig",
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
            configName:{
                required:true,
            },
            type:{
                required:true
            },
            lable:{
                required:true,
            },
            value:{
                required:true,
            },
            isDisable:{
                required:true
            }
        },
        messages: {
        	configName: {
                required: "请输入配置名称",
            },
            type: {
                required: "请输入类型",
            },
            lable: {
                required: "请输入标签名",
            },
            value: {
                required: "请输入标签值",
            },
            isDisable: {
                required: "请选择是否禁用",
            }
        }
    });
})

	// 加载宣传员等级
	function queryConfigType(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
	        url :GetURLInfo() + 'queryConfigType',
	        datatype : "json",
	        success : function(result) {
	            for (var o in result.data){
	                var str = "<option value=" + result.data[o].type + ">" + result.data[o].type + "</option>";
	                $("#type").append(str);
	            }
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}	