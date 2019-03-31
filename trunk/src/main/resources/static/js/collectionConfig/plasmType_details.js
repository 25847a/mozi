$(function(){
	// 加载新增机型名称选项
    $.ajax({
        type : "POST",
        url :'/room/queryRoomByPlasmType',
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#roomId").append(str);
                if(undefined!=roomId){
                	$("#roomId").val($("#room").val());
                }
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
    
    // 加载修改状态下拉选择框
    $("#isSelected").val($("#isSelectedId").val());

    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updatePlasmType",
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
            isSelected:{
                required:true
            },
            roomId:{
                required:true
            }
        },
        messages: {
        	name: {
                required: "请输入名称",
                minlength: "长度不允许小于3位",
                maxlength: "长度不允许大于12位"
            }
        }
    });

})
