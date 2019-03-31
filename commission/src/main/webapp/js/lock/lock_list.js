var table;
//查询参数
function getParam(){
	var plasmaId1=$("#plasmaId1").val();
	var param = {};
    param.plasmaId1=plasmaId1;
    return param;
}

$(function(){
	//查询
	$("#query").click(function(){
		table.ajax.reload();
	});
	
    $.ajax({
        type : "POST",
        url : "../admin/queryPlasmaSiteNoPageList",
        datatype : "json",
        success : function(data) {
            if (data.error=='-1') {
                $.each(data.data, function (i, item) {
                    jQuery("#plasmaId").append("<option value="+ item.id+">"+ item.plasmaName+"</option>");
                    jQuery("#plasmaId1").append("<option value="+ item.id+">"+ item.plasmaName+"</option>");
                });
            } else {
                layer.msg(data.msg);
            }
        },
        error : function() {
            layer.msg("请稍后重试");
        }
    });

	var columns = [
	           { data: 'hid' },
	           { data: 'plasmaName' },
	           { data: 'createDate',render:function(data, type, full, meta){
	        	   return dateFtt("yyyy-MM-dd",new Date(data));
	           }}
     ];
	
	getData('companyGrid',getParam,'../admin/queryPasswordLockList',columns,function(){
		//表格加载完成事件
	});
	
	//新增、修改表单验证
	$("#companyForm").validate({
	    errorPlacement: function (error, element) { // 指定错误信息位置
	       if(element.is("input[name='path']")){
	        var eid = element.attr('name');
	        error.appendTo(element.parent());
	      }else {
	        error.insertAfter(element);
	      }
	    },
	    submitHandler:function(form){
	    	var data = $("#companyForm").serialize();
	    	$.post("../admin/insertPasswrodLock",data, function(data) {
	    		if (data.error == "-1") {
	    			layer.msg('操作成功',function(){
	    				window.location.href=window.location.href;
	    			});
	    		} else {
	    			layer.msg(data.msg);
	    		}
	    	});
	    }
	});
})
/**
 * 登记加密锁信息
 * @returns
 */
function add() {
	$("#add_cp_title").html("登记加密锁信息");
	$("#companyForm input:radio").eq(0).attr("checked",true);
	//隐藏提示
	$("#type").val(0);
	$("#companyForm input.error,#companyForm select.error").removeClass('error');
	$("#companyForm label.error").hide();
	$('#modalCompany').modal('show');
}

//重置加密锁信息
function update(){
	$("#add_cp_title").html("重置加密锁信息");
	//隐藏提示
	$("#type").val(1);
	$("#companyForm input.error,#companyForm select.error").removeClass('error');
	$("#companyForm label.error").hide();
	$('#modalCompany').modal('show');
}

