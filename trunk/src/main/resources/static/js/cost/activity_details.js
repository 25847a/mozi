$(function(){
	
	
	$("#sub").click(function(){
		$("#updateActivityForm").ajaxSubmit({
			url:"updateActivity",
			type:"post",
			dataType:"json",
			success:function(result) {
				console.log(result.code);
				parent.layer.alert(result.message);
                if(result.code==-1) { //url跳转网页中传回的值。
                    /*var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);*/
                	//window.location.href=document.refferrer;
                }
	        },
	        error:function() {
	            layer.alert("修改失败");
	        }
		});
		var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        parent.location.reload();
		//window.location.href=document.refferrer;
	});
	
	$("#updateActivityForm").validate({
        //验证规则
        rules: {
            name:{
                required:true,
                minlength:2,
                maxlength:15
            },
            isNew:{
                required:true
            },
            type:{
                required:true
            },
            moneyType : {
                required : true
            },
            startDate:{
                required : function(){
                	//如果活动类型为限定次数则不需要做非空验证
                	if($("#type").val() == 0){
                		return false;
                	}else{
                		return true;
                	}
                }
            },
            endDate:{
                required : function(){
                	//如果活动类型为限定次数则不需要做非空验证
                	if($("#type").val() == 0){
                		return false;
                	}else{
                		return true;
                	}
                }
            },
            count:{
                required:true
            },
            money:{
                required : true,
            }
        },
        messages: {
        	name:{
                required:"请输入活动名称",
                minlength:"活动名称长度不允许少于2位",
                maxlength:"活动名称长度不允许大于15位"
            },
            isNew:{
            	required:"请选择浆员类型"
            },
            type:{
            	required:"请输入活动类型"
            },
            moneyType : {
            	required:"请输入固定金额"
            },
            startDate:{
            	required:"请输入开始时间"
            },
            endDate:{
            	required:"请输入结束时间"
            },
            count:{
            	required:"请输入针次数"
            },
            money:{
            	required:"请输入奖励金额"
            }
        }
    });
	
	
	//格式化时间框
	//initDate("#startDate");
	//initDate("#endDate");
	$("#cannel").bind('click',function(){
		var index = parent.layer.getFrameIndex(window.name); 
		  parent.layer.close(index);  
	}); 
});