$(function(){
	//加载查询条件下拉选择框
	queryRoomByPlasmType();
	
    // 加载修改状态下拉选择框
    $("#status").val($("#statusId").val());
    $("#type").val($("#typeId").val());
    
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateMachineNumber",
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
                status:{
                    required:true
                },
                plasmTypeId:{
                    required:true
                },
                type:{
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

	// 加载采室选项
	function queryRoomByPlasmType(){
		$.ajax({
	        type : "POST",
	        cache: false,  // 禁用缓存
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
	            queryPlasmTypeByMachine();
	        },
	        error : function() {
	            alert("请稍后试试");
	        }
	    });
	}
	
	// 加载机型名称选项
	function queryPlasmTypeByMachine(){
		var roomId =$("#roomId").val();	//获取采室id
		$.ajax({
		    type : "POST",
		    cache: false,  // 禁用缓存
		    url :'/room/queryPlasmTypeByMachine',
		    data: {"roomId": roomId}, // 传入组装的参数
		    datatype : "json",
		    success : function(result) {
		    	$("#plasmTypeId").empty();//首先清空select现在有的内容
		    	$("#plasmTypeId").append("<option value=''>请选择</option>");
		    	for (var o in result.data){
		            var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
		            $("#plasmTypeId").append(str);
		            if(undefined!=roomId){
	                	$("#plasmTypeId").val($("#plasmType").val());
	                }
		        }
		    },
		});
	}
