$(function(){
	/*日期时间*/
	dayControl("validMonth");
	dayControl("endDate");
	$("#injectionType").val($("#injection").val());
	// 根据普免加载免疫类型选项
	initCheckData21(null,'/immuneSetting/queryImmunTypeOne',null,0);
	// 加载耗材名称选项
	initCheckData21('#suppliesId','/outstock/queryVaccineList',null,2);
	// 加载批号选项
	initCheckData21('#batchNumber','/outstock/queryVaccineBatch',null,3);
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#vaccine").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateVaccine",
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
})
function initCheckData21(selectId,url,datas,type){
	simpleAjax(url,datas,function(result){
		if(type==0){
			if(result.code==-1){
	        	   var data = result.data;
	        	   if(data.isDisable==1){
	        		// 加载免疫类型选项
	        			initCheckData21('#immuneId','/immuneSetting/queryAmmuneSetting',{type:1},1);
	        	   }else{
	        		   initCheckData21('#immuneId','/immuneSetting/queryImmuneSettingList',null,1);
	        	   }
	           }
		}
		if(type==1){
			// 加载免疫类型选项
			 for (var o in result.data){
	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
	                $(selectId).append(str);
	               	 $(selectId).val($("#immune").val());
	            }
		}
		if(type==2){
			// 加载耗材名称选项
			for (var o in result.data){
                var str = "<option value=" + result.data[o].suppliesId + ">" + result.data[o].name + "</option>";
                $(selectId).append(str);
                $(selectId).val($("#supplies").val());
            }
		}
		if(type==3){
			// 加载批号选项
			for (var o in result.data){
                var str = "<option value=" + result.data[o].batchNumber + ">" + result.data[o].batchNumber + "</option>";
                $(selectId).append(str);
                $(selectId).val($("#batch").val());
            }
		}
	})
	
}