$(function(){
    //点击新增表单
    $.validator.setDefaults({
        submitHandler: function() {
            $("#addVersion").ajaxSubmit({
                type : 'POST',
                url : GetURLInfo() +"insertVersion",
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
    $("#addVersion").validate({
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
            type:{
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
            remark:{
                required: "请输入类型",
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
