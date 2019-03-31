$(function(){
	/*日期时间*/
	dayControl("validMonth");
	dayControl("endDate");
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
    // 加载耗材名称选项
    $.ajax({
        type : "POST",
        url :"/outstock/queryVaccineList",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].suppliesId + ">" + result.data[o].name + "</option>";
                $("#suppliesId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材名称失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    
    $("#suppliesId").change(function(){
    	roead({"suppliesId":$("#suppliesId").val()},function(data){
    		if(data.code==-1){
			$("#batchNumber").empty();
			 for (var o in data.data){
	                var str = "<option value=" + data.data[o].batchNumber + ">" + data.data[o].batchNumber + "</option>";
	                $("#batchNumber").append(str);
	                $("#startDate").val(dateFtt("yyyy-MM-dd",new Date(data.data[0].createDate)));
	            }
		}
    	});
    });
    $("#batchNumber").change(function(){
    	roead({"batchNumber":$("#batchNumber").val()},function(data){
    		if(data.code==-1){
				 $("#startDate").val(dateFtt("yyyy-MM-dd",new Date(data.data[0].createDate)));
			}
    	});
    });
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#vaccine").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/insertVaccine",
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
    $("#vaccine").validate({
        //验证规则
        rules: {
        	immuneId:{
                required:true
            },
            injectionType:{
                required:true
            },
            suppliesId : {
                required : true,
            },
            injection:{
                required:true,
            },
            validMonth:{
                required:true,
            }
        },
        messages: {
        	immuneId: {
                required: "请输入免疫名称",
            },
            injectionType: {
                required: "请输入注射方式",
            },
            suppliesId: {
                required: "请输入耗材名称",
            },
            injection:{
                required: "请输入注射量",
            },
            validMonth:{
                required: "请输入有效期",
            }
        }
    });
    function roead(dat,func){
    	$.ajax({
    		type : "POST",
    		url : "/outstock/queryVaccineBatchNumber",
    		datatype : "json",
    		data:dat,//{"suppliesId":$("#suppliesId").val()}
    		success : function(data) {
    			func(data);
    		},
    		error:function(){
    			parent.layer.alert("操作失败",function(){
    				parent.layer.closeAll();
    			})
    		}
    	});
    }
  //加载免疫类型选项
    function settingList(url,data){
    	// 加载免疫类型选项
        $.ajax({
            type : "POST",
            url :url,
            datatype : "json",
            data:data,
            success : function(result) {
                for (var o in result.data){
                    var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
                    $("#immuneId").append(str);
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

