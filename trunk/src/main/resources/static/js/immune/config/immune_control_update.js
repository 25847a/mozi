$(function(){
	// 根据普免加载免疫类型选项
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunTypeOne",
        datatype : "json",
        async:false,
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
 // 加载类型选项
    $.ajax({
        type : "POST",
        url :"/control/queryControlInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].controlName + "</option>";
                $("#controlId").append(str);
                $("#controlId").val($("#controlll").val());
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#control").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"/updateimmuneControl",
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
    $("#control").validate({
        //验证规则
        rules: {
        	immunetypeId:{
                required:true
            },
            frequency:{
                required:true
            },
            controlId:{
                required:true
            }
        },
        messages: {
        	controlName: {
        		immunetypeId: "请输入免疫类型",
            },
            frequency: {
        		immunetypeId: "请输入针次数",
            },
            controlId: {
        		immunetypeId: "请输入控制类别",
            }
        }
    });
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
                $("#immunetypeId").append(str);
                $("#immunetypeId").val($("#immune").val());
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
