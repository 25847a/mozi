var table;

//查询参数
function getParam(){
	var param={};
	 /** ************ 请求参数 ***************************** */
    var roleName=$("#roleNameKey").val();
    param.roleName=roleName;
    return param;
}

/*重置按钮*/
function resetInfo() {
	$("input").val("");
	$("select").val("");
}

$(function(){
	
	var columns = [
        { data:'roleName'},                             
        { data:null,render:function(data, type, full, meta){
     	   var html='<button type="button" class="btn btn-xs btn-reset" onclick="theEditor(\''+data.id+'\',\''+data.roleName+'\')">编辑</button>';
     	   html+='<button type="button" class="btn btn-xs btn-success" onclick="roleManage(\''+data.id+'\',\''+data.roleName+'\')">权限管理</button>';
     	   html+='<button type="button" class="btn btn-xs btn-danger" onclick="delRole(\''+data.id+'\')">删除</button>';
     	   return html;
        }}
     ]
	getData('roleGrid',getParam,'../admin/roleList',columns,function(){});
	
	$("#search").click(function(){
		table.ajax.reload();
	});
	
	/* 新增表单验证 */
	$("#roleForm").validate({
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
		    submitHandler:function(form){
		    	var data = $("#roleForm").serialize();
		    	$.post("../admin/saveOrUpdateRole",data, function(data) {
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
	
	//重新授权
	$("#SetForm").validate({
	    errorPlacement: function (error, element) { // 指定错误信息位置
		    },
		    submitHandler:function(form){
		    	var data={};
		    	data.roleId=$("#roleId").val();
		    	var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
	            	nodes=treeObj.getCheckedNodes(true),
	            	v="";
		    	data.menuId="";
	            for(var i=0;i<nodes.length;i++){
	            	data.menuId+=nodes[i].id+","; //获取选中节点的值
	            }
	            data.menuId=data.menuId.substring(0, data.menuId.length-1);
		    	$.post("../admin/addRoleMenu",data, function(data) {
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

/* 新增按钮 */
function add() {
	$("#roleTitle").html("新增角色");
	$("#id").val("");
	$("#roleName").val("");
	$('#modalRole').modal('show');
}
/* 编辑按钮 */
function theEditor(id,name) {
	$("#roleTitle").html("编辑角色");
	$("#id").val(id);
	$("#roleName").val(name);
	$('#modalRole').modal('show');
}

//确认删除
function delRole(id){
	layer.alert('',{icon:2,title:'删除确认',content:'您确定要删除这条记录吗？',closeBtn:1},function(index){
	$.post("../admin/delRole",{"id":id}, function(data) {
		if (data.error == "-1") {
			layer.msg('操作成功',function(){
				window.location.href=window.location.href;
			});
		} else {
			layer.msg(data.msg);
		}
	});
	});
}

//角色授权 /*权限管理*/
function roleManage(id,name){
	$("#roleSetName").val(name);
	$("#roleId").val(id);
	$('#modalSet').modal('show');
	$.ajax({
    	type : "POST",
		url : "../admin/queryUserRoleMenu",
        cache: false,  // 禁用缓存
        data: {"roleId":id},  // 传入组装的参数
        dataType: "json",
        success: function (dataSource) {
        	 $.fn.zTree.init($("#treeDemo"), setting, dataSource.data.listData);
        }
    });
}


/*初始化权限树形菜单*/
var setting = {
  check: {
    enable: true
  },
  data: {
    simpleData: {
      enable: true
    }
  }
};