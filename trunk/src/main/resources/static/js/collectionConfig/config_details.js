$(function(){
	//加载查询条件下拉选择框
	queryConfigType();
	
    // 加载修改状态下拉选择框
    $("#isDisable").val($("#isDisableId").val());
    
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateConfig",
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
// 加载类型选择
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
	                if(undefined!=type){
	                	$("#type").val($("#typeId").val());
	                }
	            }
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}	