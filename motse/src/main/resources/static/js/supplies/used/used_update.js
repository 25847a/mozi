$(function(){
	/*日期时间*/
	dayControl("startDate");
	dayControl("endDate");
	//查询仓库的
	$.ajax({
		type : "POST",
		url : "/depot/queryDepotInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].name + "</option>";
				$("#depotId").append(str);
				$("#depotId").append($("#depot").val());
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
    $("#isDelete").val($("#isDel").val());//是否删除
    $("#isDisable").val($("#isDi").val());//是否可用
    $("#apply").val($("#app").val());//模块
    $("#isDelete").val($("#isDel").val());//是否删除
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateSupplies").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"updateTemplate",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                var index = parent.layer.getFrameIndex(window.name);
                                parent.layer.closeAll();
                                parent.location.reload();
                            });
                           
                    });
                },
                error : function() {
                	parent.layer.alert("修改失败",function(){
           				parent.layer.closeAll();
           			});
                }
            });
        }
    });
    $("#updateSupplies").validate({
        //验证规则
        rules: {
        	name:{
                required:true,
                minlength:2,
                maxlength:8
            },
            isDisable:{
                required:true
            },
            apply:{
                required:true,
            },
            depotId:{
                required:true
            },
            startDate : {
                required : true
            },
            endDate:{
                required : true
            },
            isDelete:{
                required:true
            }
            
        },
        messages: {
        	name: {
                required: "请输入模板名称",
                minlength: "模板名称长度不允许少于2位",
                maxlength: "模板名称长度不允许多于8位"
            },
            isDisable: {
                required: "请选择是否可用"
            },
            apply: {
                required: "请选择模块"
            },
            depotId: {
                required: "请输入仓库"
            },
            startDate: {
                required: "请输入开始时间"
            },
            endDate:{
                required: "请输入结束时间"
            },
            isDelete:{
                required:"请选择是否删除"
            }
        }
    });

})
