$(function(){
	
	// 根据普免加载免疫类型选项
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunTypeOne",
        datatype : "json",
        success : function(result) {
           if(result.code==-1){
        	   var data = result.data;
        	   if(data.isDisable==1){
        		   settingList("/immuneSetting/queryAmmuneSetting",{type:1});
        	   }else{
        		   settingList( "/immuneSetting/queryImmuneSettingList",null);
        	   }
           }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
 // 获取免疫类型
  /*  $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunType",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].value + ">" + result.data[o].configName + "</option>";
                $("#packingImmuneId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });*/
    
	
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addDepotForm").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"addAssaySetting",
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
  //加载免疫类型选项
    function settingList(url,data){
    	//获取所有免疫类别设置类型
    	$.ajax({
    		url:url,
    		type:"post",
    		dataType:"json",
    		data:data,
    		success:function(result){
    			if(result.code == -1){
    				for(var i = 0; i < result.data.length; i++){
    					var str= "<option value='"+result.data[i].id+"'>"+result.data[i].immuneName+"</option>";
    					$("#immuneId").append(str);
    					 $("#packingImmuneId").append(str);
    				}
    			}
    		}
    	});
    }
})

