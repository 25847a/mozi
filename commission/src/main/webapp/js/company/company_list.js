var table;
//查询参数
function getParam(){
	var plasmaName=$("#plasmaNameKey").val();
	var param = {};
    param.plasmaName=plasmaName;
    return param;
}

$(function(){
	//查询
	$("#query").click(function(){
		table.ajax.reload();
	});
	var columns = [
	           { data: 'plasmaName' },
	           { data: 'createDate',render:function(data, type, full, meta){
	        	   return dateFtt("yyyy-MM-dd",new Date(data));
	           }},
	           { data:null,render:function(data, type, full, meta){
	        	   var html='<button type="button" class="btn btn-xs btn-warning" onclick="update(\''+data.id+'\')">修改</button>';
	        	   html+='<button type="button" class="btn btn-xs btn-danger" onclick="delHr(\''+data.id+'\')">删除</button>';
	        	   return html;
	           }}
     ];
	
	getData('companyGrid',getParam,'../admin/queryPlasmaCompanyList',columns,function(){
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
	    	$.post("../admin/savePlasmaCompany",data, function(data) {
	    		if (data.error == "-1") {
	    			layer.msg('操作成功',function(){
	    				window.location.href=window.location.href;
	    			});
	    		} else {
	    			layer.msg('操作失败');
	    		}
	    	});
	    }
	});
})
/**
 * 添加公司
 * @returns
 */
function add() {
	$("#add_cp_title").html("新增公司信息");
	$("#update_id").val("");
	$("#companyForm input[type='text']").val("");
	$("#companyForm input:radio").eq(0).attr("checked",true);
	$("#companyForm select").val("");
	$("#companyForm textarea").val("");
	//隐藏提示
	$("#companyForm input.error,#companyForm select.error").removeClass('error');
	$("#companyForm label.error").hide();
	$('#modalCompany').modal('show');
}

//修改公司信息
function update(id){
	$.ajax({
		type : "POST",
		url : "../admin/queryCCDetails",
		data : {
			"id" : id
		},
		datatype : "json",
		success : function(data) {
			if (null!=data) {
				$("#add_cp_title").html("修改公司信息");
				//隐藏提示
				$("#companyForm input.error,#companyForm select.error").removeClass('error');
				$("#companyForm label.error").hide();
				$("#companyName").val(data.data.plasmaName);
				$("#update_id").val(id);
				$('#modalCompany').modal('show');
			} else {
				layer.msg(data.msg);
			}
		},
		error : function() {
			layer.msg("请稍后重试");
		}
	});
}

//删除数据
function delHr(id){
//	$("#del_id").val(id);
	layer.alert('',{icon:2,title:'删除确认',content:'您确定要删除这条记录吗？',closeBtn:1},function(index){
	$.ajax({
		type : "POST",
		url : "../admin/delPlasmaCompany",
		data : {
			"id" : id
		},
		datatype : "json",
		success : function(data) {
			if (null!=data) {
				if(data.error!=-1){
					layer.msg(data.msg);
				}else{
					location.reload();
				}
			} else {
				layer.msg(data.msg);
			}
		},
		error : function() {
			layer.msg("请稍后重试");
		}
	});})
	$('#delHr').modal('show');
}

function resetInfo(){
	 $("input").val("");
	 $("select").val("");
}