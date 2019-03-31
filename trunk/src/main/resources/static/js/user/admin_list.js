$(function(){
    // 加载新增角色选项
    $.ajax({
        type : "POST",
        url :"/sysRole/queryRoleList",
        datatype : "json",
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
    var cols=[
        [ //表头
            {type: 'checkbox', fixed: 'left'},
            {field: 'roleName',title: '角色',align: 'center'},
            {field: 'name',title: '真实姓名',align: 'center'},
            {field: 'userName',title: '用户名',align: 'center'},
            {field: 'mobile',title: '手机号码',align: 'center'},
            {field: 'isDisable',title: '状态',align: 'center',templet: function (d){ // 单元格格式化函数
                var text = '-';
                if (d.isDisable == 0) {
                    text = "启用";
                } else if (d.isDisable == 1) {
                    text = "禁用";
                }
                return text;
            }
            },
            {field: 'isLoginCount',title: '登陆次数',align: 'center'},
            {field: 'ip',title: '登陆IP',align: 'center'},
            {field: 'lockDate',title: '锁定时间',align: 'center'},
            {fixed: 'right',width:150,title: '操作',align: 'center',toolbar: '#barDemo'}
        ]
    ]
    data("usertable",cols,GetURLInfo() + 'queryAdminList',GetURLInfo() +'queryAdminDetails',GetURLInfo() + "deleteAdmin");
	//搜索按钮点击事件
		$("#searchBtn").click(function(){
			doSearch();
			 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
		});

    //批量删除
    $("#userDel").click(function() {
        var checkStatus = table.checkStatus('usertable');
        var ids = new Array();
        //没有选中任何一行
        if(checkStatus.data.length == 0) {
            layui.layer.msg('请选择要删除行');
        }else {
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids.push(checkStatus.data[i].id);
            }
            layui.layer.confirm('确定删除么', function (index) {
                $.post(GetURLInfo() + "deleteAdmin", {"ids": ids.join(",")}, function (result) {
                    parent.layer.alert(result.message);
                    if(result.code==-1) { //url跳转网页中传回的值。
                        location.reload();
                    }
                });

            });
        }
    });
})

//搜索
function doSearch(){
	var name = $("#searchName").val()
	var mobile = $("#searchMobile").val();
    var roleId = $("#roleId").val();
	layui.table.reload('usertable', {where: {"name": name, "mobile": mobile,"roleId":roleId}});
}

function getToken() {
	return localStorage.getItem("token");
}

 ! function() {
	 	/*新增*/
	 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	 	$('#userAdd').on('click', function() {
	 		add(GetURLInfo() +'adminAddPage',true);
	 		}
	 	)
	 }();
