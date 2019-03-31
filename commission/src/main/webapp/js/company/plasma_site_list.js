//hr 人员列表 对象
var table;
//查询参数
function getParam(){
	var companyId=$("#companyNameOne").val();
	var plasmaName = $("#roleNameKey").val();
	var param = {};
    param.companyId=companyId;
    param.plasmaName=plasmaName;
    return param;
}

function resetInfo(){
  $("input").val("");
  $("select").val("");
}
/* 初始化表格 */
$(document).ready(function() {
	// 获取浆站列表
    var columns= [
           { data:'plasmaName'},
           {data:'code'},
           { data:'certificationOrgan'}, 
           { data:'licensor'},  
           { data:'elecLockNumber'},  
           { data:'name'},
           { data:"createDate",render:function(data, type, full, meta){
        	   if(""==data){
        		   return "";
        	   }
        	   return dateFtt("yyyy-MM-dd hh:mm:ss",new Date(data));
           }},
           { data:null,render:function(data, type, full, meta){
        	   var html='<button type="button" class="btn btn-xs btn-reset" onclick="theEditor(\''+data.id+'\')">编辑</button>';
        	 	 html+='<button type="button" class="btn btn-xs btn-danger" onclick="delHr(\''+data.id+'\')">删除</button>';
	        	 return html;																	
	           }}
           
        ]
    getData('stationGrid',getParam,'../admin/queryPlasmaSiteList',columns,function(){
		//表格加载完成事件
	});
	getPlasma();
	// 搜索
	$("#query").click(function(){
		table.ajax.reload();
	});
});

//加载下拉选查询条件的公司名称
function getPlasma(){
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
	            	 $("#companyNameOne").append("<option value='"+data[i].id+"'>"+data[i].plasmaName+"</option>");
	            	 $("#companyName").append("<option value='"+data[i].id+"'>"+data[i].plasmaName+"</option>");
				 }
        	 } 
         }
	 });	
}

/* 新增公司按钮 */
function add() {
	$("#add_cp_title").html("新增浆站");
	$("#edit_id").val("");
	$("#companyForm input[type='text']").val("");
	$("#companyForm input:radio").eq(0).attr("checked",true);
	$("#companyForm select").val("");
	$("#companyForm textarea").val("");
	// 隐藏提示
	$("#companyForm input.error,#companyForm select.error").removeClass('error');
	$("#companyForm label.error").hide();
	$('#modalStation').modal('show');
}

// 编辑按钮
function theEditor(id,population,plasmaName,provinceId,type){
	$.post('../admin/queryPlasmaSiteById',{"id":id},function(data){
		$("#code").val(data.data.code);
		$("#certificationOrgan").val(data.data.certificationOrgan);
		$("#companyName").val(data.data.companyId);
		$("#licensor").val(data.data.licensor);
		$("#elecLockNumber").val(data.data.elecLockNumber);
		$("#edit_id").val(data.data.id);
		$("#plasmaName").val(data.data.plasmaName);
	});
	$("#add_cp_title").html("修改浆站");
	$('#modalStation').modal('show');
	
	
	$("#areaName").val(provinceId);
}

// 删除按钮
function delHr(id){
	layer.alert('',{icon:2,title:'删除确认',content:'您确定要删除这条记录吗？',closeBtn:1},function(index){
	$.ajax({
		type : "POST",
		url : "../admin/deletePlasmaSiteById",
		data : {
			"id" : id
		},
		datatype : "json",
		success : function(data) {
			if (null!=data) {
				// alert("删除成功")
				location.reload();
			} else {
				layer.msg(data.msg);
			}
		},
		error : function() {
			layer.msg("请稍后重试");
		}
	});
	})
	$('#delHr').modal('show');
}

//新增修改页面
$(function(){
	  /* 新增公司表单验证 */
		$("#companyForm").validate({
		    errorPlacement: function (error, element) { // 指定错误信息位置
		      if (element.is(':radio') || element.is(':checkbox') ) { // 如果是radio或checkbox
		        var eid = element.attr('name'); // 获取元素的name属性
		        error.appendTo(element.parent().parent().parent()); // 将错误信息添加当前元素的父结点后面
		      } else if(element.is("input[name='path']")){
		        var eid = element.attr('name');
		        error.appendTo(element.parent());
		      }else {
		        error.insertAfter(element);
		      }
		    },
		    rules:{
		      phone:{
		        required:true,
		        isMobile:true
		      }
		    },
		    messages:{
		    },
		    submitHandler:function(form){
		    	var data = $("#companyForm").serialize();
		    	$.post("../admin/savePlasmaSite",data, function(dataSource) {
		    		if (dataSource.error == "-1") {
		    			layer.msg('操作成功',function(){
		    				window.location.href=window.location.href;
		    			});
		    		} else {
		    			layer.msg('操作失败');
		    		}
		    	});
		    }
		});
	  
	});
