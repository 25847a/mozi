$(function(){
	// 查询小组名称
	$.ajax({
		type : "POST",
		url : "/group/queryGroupInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].groupName + "</option>";
				$("#groupId").append(str);
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
	// 查询小组名称
	$.ajax({
		type : "POST",
		url : "/area/queryAreaInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].town + "</option>";
				$("#areaId").append(str);
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertgroupArea",
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

