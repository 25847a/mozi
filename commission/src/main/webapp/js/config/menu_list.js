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

//获取菜单栏目
function getMenu(){
	 $.ajax({
    	type : "POST",
		url : "../admin/menuAllList",
        cache: false,  // 禁用缓存
        data: null,  // 传入组装的参数
        dataType: "json",
        success: function (dataSource) {
          $.each(dataSource.data.listData, function(i,val){ 
	       	   $("#menuid").append("<option value='"+val.id+"'>"+val.name+"</option>");
	      });
        }
    });
}

$(function(){
	getMenu();
	var columns = [
        { data:'menuName'}, 
        { data:'url'},  
        { data:null,render:function(data, type, full, meta){
     	   var html='<button type="button" class="btn btn-xs btn-reset" onclick="theEditor(\''+data.id+'\',\''+data.menuName+'\',\''+data.url+'\',\''+data.pid+'\',\''+data.style+'\')">编辑</button>';
     	   html+='<button type="button" class="btn btn-xs btn-danger" onclick="delMenu(\''+data.id+'\')">删除</button>';
     	   return html;
        }}
     ]
	getData('roleGrid',getParam,'../admin/menuList',columns,function(){});
	
	/* 新增表单验证 */
	$("#sub").click(function(){
		var menuid =$("#menuid").val();
		var menuName=$("#menuName").val();
		var url=$("#url").val();
		$("#roleForm label.error").remove();
		var bol=true;
		if(menuName==''){
			$("#menuName-error").remove();
			$('<label id="menuName-error" class="error" for="menuName">请输入菜单名称</label>').insertAfter($("#menuName"));
			bol= false;
		}
		if(menuid!='0'){//非一级栏目则需要填写请求地址
			if(url==''){
				$("#url-error").remove();
				$('<label id="url-error" class="error" for="url">请输入请求地址</label>').insertAfter($("#url"));
				bol= false;
			}
		}
		/*else{
			var style=$("#style").val();
			if(style==''){
				$("#style-error").remove();
				$('<label id="style-error" class="error" for="style">请输入菜单样式</label>').insertAfter($("#style"));
				bol= false;
			}
		}*/
		if(bol==false){
			return false;
		}
		var data = $("#roleForm").serialize();
    	$.post("../admin/saveOrUpdateMenu",data, function(data) {
    		var dataSource=JSON.parse(data);
    		if (dataSource.error == "-1") {
    			layer.msg('操作成功',function(){
    				window.location.href=window.location.href;
    			});
    		} else {
    			layer.msg('操作失败');
    		}
    	});
	})
})

/* 新增按钮 */
function add() {
	$("#roleForm input").val("");
	$("#menuTitle").html("新增菜单");
	$("#id").val("");
	$('#modalRole').modal('show');
}
/* 编辑按钮 */
function theEditor(id,name,url,pid,style) {
	$("#menuTitle").html("编辑菜单");
	$("#id").val(id);
	$("#menuName").val(name);
	$("#url").val(url);
	$("#style").val(style);
	$("#menuid").val(pid);
	$('#modalRole').modal('show');
}

//确认删除
function delMenu(id){
	layer.alert('',{icon:2,title:'删除确认',content:'您确定要删除这条记录吗？',closeBtn:1},function(index){
	$.post(site + "fadl/delMenu",{"id":id}, function(data) {
		var dataSource=JSON.parse(data);
		if (dataSource.error == "-1") {
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