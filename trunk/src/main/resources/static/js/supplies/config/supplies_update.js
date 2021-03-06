$(function(){
	 // 加载耗材类型选项
    $.ajax({
        type : "POST",
        url :"/type/querySuppliesTypeInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#typeId").append(str);
                if(undefined!=typeId){
                	 $("#typeId").val($("#type").val());
                }
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    // 加载耗材单位选项 
    $.ajax({
        type : "POST",
        url :"/unit/queryUnitInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#unitId").append(str);
                if(undefined!=typeId){
               	 $("#unitId").val($("#unit").val());
               }
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材单位失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    // 加载耗材供应商选项
    $.ajax({
        type : "POST",
        url :"/suppliesSupply/querySuppliesSupplyInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#supplyId").append(str);
                if(undefined!=typeId){
               	 $("#supplyId").val($("#supply").val());
               }
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材供应商失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    $("#apply").val($("#applyy").val());
    //点击修改表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#updateSupplies").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"updateSuppliesInfo",
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
    $("#updateSupplies").validate({
        //验证规则
        rules: {
        	number:{
                required:true,
                minlength:2,
                maxlength:8
            },
            name:{
                required:true
            },
            typeId:{
                required:true,
            },
            unitId:{
                required:true
            },
            supplyId : {
                required : true
            },
            apply:{
                required : true
            },
            money:{
                required:true
            },
            maxStock:{
                required:true
            },
            minStock:{
                required:true
            }
            
        },
        messages: {
        	number: {
                required: "请输入耗材编号",
                minlength: "耗材编号长度不允许少于2位",
                maxlength: "耗材编号长度不允许多于8位"
            },
            name: {
                required: "请输入姓名"
            },
            typeId: {
                required: "请选择类别"
            },
            unitId: {
                required: "请输入单位"
            },
            supplyId: {
                required: "请输入供应商"
            },
            apply:{
                required: "请输入应用模块"
            },
            money:{
                required:"请输入进货价"
            },
            maxStock:{
                required:"请输入库存上限"
            },
            minStock:{
                required:"请输入库存下限"
            }
        }
    });

})
