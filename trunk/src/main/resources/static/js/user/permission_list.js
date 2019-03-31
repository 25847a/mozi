$(document).ready(function(){
    // 加载新增角色选项
    $.ajax({
        type : "POST",
        url :"/sysRole/queryRoleList",
        datatype : "json",
        async:false,
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#roleId").append(str);
            }
        },
        error : function() {
            alert("请稍后试试");
        }
    });
    loadpermission();
});
function loadpermission(){
    var id=$("#roleId").val();
    $.ajax({
        type : "POST",
        url :"/sysPermission/queryPermissionList",
        datatype : "json",
        data:{"id":id},
        success : function(result) {
            checkCode(result,function(){
                if(result.code==-1){
                    $.fn.zTree.init($("#treeDemo"), setting, result.data);
                }
            });
        },
        error : function() {
            alert("请稍后试试");
        }
    });
}
var setting = {
    view: {
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        jurad:jurad
    }
};
function jurad(){
    var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
        nodes=treeObj.getCheckedNodes(true),
        permissionId= new Array();
    for(var i=0;i<nodes.length;i++){
        permissionId.push(nodes[i].id)
    }
    var roleId=$("#roleId").val();
    $.ajax({
        type : "POST",
        url :"/sysRolePermission/insertRolePermission",
        datatype : "json",
        data:{"roleId":roleId,"permissionId":permissionId.join(",")},
        success : function(result) {
            checkCode(result,function(){
                if(result.code==-1){
                    parent.layer.alert(result.message,function() {
                    	parent.layer.closeAll();
                        loadpermission();
                    });
                }
            });
        },
        error : function() {
            alert("请稍后试试");
        }
    });
}