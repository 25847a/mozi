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
    $("#type").val($("#typeId").val());
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#basics").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateBasicRules",
                success : function(result) {
                    checkCode(result,function(){
                        parent.layer.alert(result.message,function() {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.closeAll();
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
    $("#basics").validate({
        //验证规则
        rules: {
        	immuneId:{
                required:true
            },
            num:{
                required:true
            },
            intervalDay : {
                required : true,
            },
            minNominal:{
                required:true,
            },
            maxNominal:{
                required:true,
            }
        },
        messages: {
        	immuneId: {
                required: "请输入免疫名称",
            },
            num: {
                required: "请输入针次数",
            },
            intervalDay: {
                required: "请输入间隔天数",
            },
            minNominal:{
                required: "请输入最小标称值",
            },
            maxNominal:{
                required: "请输入最大标称值",
            }
        }
    });
  //加载免疫类型选项
    function settingList(url,data){
    	 $.ajax({
    	        type : "POST",
    	        url :url,
    	        datatype : "json",
    	        data:data,
    	        success : function(result) {
    	            for (var o in result.data){
    	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
    	                $("#immuneId").append(str);
    	               	 $("#immuneId").val($("#immune").val());
    	            }
    	        },
    	        error : function() {
    	        	parent.layer.alert("获取免疫类型失败",function(){
    	   				parent.layer.closeAll();
    	   			});
    	        }
    	    });
    }
})
