var table;

//查询参数
function getParam(){
	var param={};
	 /** ************ 请求参数 ***************************** */
    var userNameKey=$("#userNameKey").val();
    var roleId=$("#roleName").val();
    param.roleId=roleId;
    param.userName=userNameKey;
    return param;
}

$(function(){
	//搜索
	$("#search").click(function(){
		table.ajax.reload();
	});
	
	//遍历出所有角色
	$.ajax({
    	type : "POST",
		url : "../admin/roleAllList",
        cache: false,  // 禁用缓存
        data: null,  // 传入组装的参数
        dataType: "json",
        success: function (dataSource) {
        	if(dataSource.error==-1 && null!=dataSource.data.listData){
        		$.each(dataSource.data.listData, function(i,val){ 
    	       	   $("#roleName").append("<option value='"+val.id+"'>"+val.roleName+"</option>");
    	       	   $("#roleId").append("<option value='"+val.id+"'>"+val.roleName+"</option>");
    	       	});
        	}
        }
    });
	
	$.ajax({
     	type : "POST",
 		url : "../admin/queryPlasmaCompanyAllList",
         cache: false,  // 禁用缓存
         data: null,  // 传入组装的参数
         dataType: "json",
         success: function (dataSource) {
        	 if(dataSource.error=="-1"){
        		 var data = dataSource.data;
        		 for (var i = 0; i < data.length; i++) {
	            	 $("#companyName").append("<option value='"+data[i].id+"'>"+data[i].plasmaName+"</option>");
				 }
        	 } 
         }
	 });	
	
	//根据公司选择浆站
	$("#companyName").change(function(){
		$("#plasmaId").empty().append('<option value="">请选择</option>');
		var id = $(this).val();
		if(id==''){
			return;
		}
		$.ajax({
	     	type : "POST",
	 		url : "../admin/queryPlasmaSiteList",
	         cache: false,  // 禁用缓存
	         async :false ,
	         data: {"companyId":id},  // 传入组装的参数
	         dataType: "json",
	         success: function (dataSource) {
	        	 if(dataSource.error=="-1"){
	        		 var data = dataSource.data.listData;
	        		 for (var i = 0; i < data.length; i++) {
		            	 $("#plasmaId").append("<option value='"+data[i].id+"'>"+data[i].plasmaName+"</option>");
					 }
	        	 } 
	         }
		 });	
	});
	
	var columns = [
        { data:'userName'}, 
        { data:'phone'},  
        { data:"createDate",render:function(data, type, full, meta){
     	   if(""==data){
     		   return "";
     	   }
     	   return dateFtt("yyyy-MM-dd",new Date(data));
        }},
        {data:"isDisable",render:function(data, type, full, meta){
     	   if("0"==data){
     		   return "未禁用";
     	   }else{
     		   return "禁用";
     	   }
        }},
        { data:'roleName',render:function(data, type, full, meta){
     	   if(""==data){
     		   return "无";
     	   }else{
     		   return data;
     	   }
        }}, 
        { data:null,render:function(data, type, full, meta){
     	   var html='<button type="button" class="btn btn-xs btn-reset" onclick="update('+data.id+')">编辑</button>';
     	   html+='<button type="button" class="btn btn-xs btn-danger" onclick="delUser(\''+data.id+'\')">删除</button>';
     	   return html;
        }}
     ]
	getData('accountGrid',getParam,'../admin/queryUserList',columns,function(){});
	
	$("#sub").click(function(){
		var id =$("#update_id").val();
		var userName=$("#userName").val();
		var phone=$("#phone").val();
		var companyName=$("#companyName").val();
		var plasmaId=$("#plasmaId").val();
		var roleId=$("#roleId").val();
		var bol=true;
		$("#accountNewForm label.error").remove();
		if(roleId==''){
			$("#roleId-error").remove();
			$('<label id="roleId-error" class="error" for="roleId">请选择用户角色</label>').insertAfter($("#roleId"));
			bol= false;
		}
		if(userName==''){
			$("#userName-error").remove();
			$('<label id="userName-error" class="error" for="userName">请输入用户名</label>').insertAfter($("#userName"));
			bol= false;
		}
		if(id==''){
			//为新增，密码不允许为空
			var passWord=$("#passWord").val();
			if(passWord==''){
				$("#passWord-error").remove();
				$('<label id="passWord-error" class="error" for="passWord">请输入密码</label>').insertAfter($("#passWord"));
				bol= false;
			}
		}
		if(phone==''){
			$("#phone-error").remove();
			$('<label id="phone-error" class="error" for="phone">请输入手机号码</label>').insertAfter($("#phone"));
			bol= false;
		}
		if(bol==false){
			return false;
		}
		var data = $("#accountNewForm").serialize();
    	$.post("../admin/saveOrUpdateUser",data, function(data) {
    		if (data.error == -1) {
    			layer.msg('操作成功',function(){
    				window.location.href=window.location.href;
    			});
    		} else {
    			layer.msg(data.msg);
    		}
    	});
	})
})


/* 新增用户 */
function add() {
	$("#modal-title").html("新增用户信息");
	$("#update_id").val("");
	$("#accountNewForm input").val("");
	//隐藏提示
	$("#accountNewForm input.error,#accountNewForm select.error").removeClass('error');
	$("#accountNewForm label.error").hide();
	$('#modalAccount').modal('show');
}

/*编辑用户*/
function update(id){
	$.ajax({
		type : "POST",
		url :  "../admin/queryByIdUser",
		data : {
			"id" : id
		},
		datatype : "json",
		success : function(dataSource) {
			if (dataSource.error==-1) {
				$("#modal-title").html("修改用户信息");
				//隐藏提示
				$("#accountNewForm input.error,#accountNewForm select.error").removeClass('error');
				$("#accountNewForm label.error").hide();
				$("#userName").val(dataSource.data.userName);
				$("#phone").val(dataSource.data.phone);
				if(dataSource.data.roleId!=''){
					$("#accountName").val(dataSource.data.roleId);
				}
				if(null==dataSource.data.roleId){
					$("#roleId").val("");
				}else{
					var doc = document.getElementById("roleId");
					if(!jsSelectIsExitItem(doc,dataSource.data.roleId)){
						$("#roleId").val("");
					}
				}
				$("#companyName").val(dataSource.data.companyId);
				$("#companyName").change();
				$("#update_id").val(id);
				$("#plasmaId").val(dataSource.data.plasmaId);
				$('#modalAccount').modal('show');
			} else {
				layer.msg(data.msg);
			}
		},
		error : function() {
			layer.msg("请稍后重试");
		}
	});
}

/*删除按钮*/
function delUser(id){
	layer.alert('',{
		icon:2,title:'删除确认',content:'您确定要删除这条记录吗？',closeBtn:1},function(index){
			 var param = {};
			 param.id=id;
			 $.post("../admin/deleteUser",param, function(data) {
					if (data.error == "-1") {
						layer.msg('操作成功',function(){
							window.location.href=window.location.href;
						});
					} else {
						layer.msg('操作失败');
					}
				});
		});
}

function resetInfo(){
	 $("input").val("");
	 $("select").val("");
}

/**
 * 判断 select 值是否存在
 * @param objSelect
 * @param objItemValue
 * @returns
 */
function jsSelectIsExitItem(objSelect, objItemValue) {
	var isExit = false;
	for (var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == objItemValue) {
			isExit = true;
			break;
		}
	}
	return isExit;
}