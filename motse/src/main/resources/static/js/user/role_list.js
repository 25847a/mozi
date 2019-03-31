$(function() {
var cols=[
    [ //表头
        {field: 'name', title: '角色名称', align: 'center'},
        {field: 'depict', title: '角色描述', align: 'center'},
        {
            field: 'isDisable', title: '是否禁用', align: 'center', templet: function (d) { // 单元格格式化函数
            var text = '-';
            if (d.isDisable == 0) {
                text = "启用";
            } else if (d.isDisable == 1) {
                text = "禁用";
            }
            return text;
        }
        },
        {field: 'createDate', title: '创建时间', align: 'center'},
        {fixed: 'right',title: '操作', width: 165, align: 'center', toolbar: '#button'}
    ]
];
    data("roleatable",cols,GetURLInfo() + 'queryRoleListPage',GetURLInfo() +'queryRoleDetails',GetURLInfo() + "deleteRole");
    //搜索按钮点击事件
    $("#searchBtn").click(function(){
        doSearch();
        currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
    });
});
//搜索
function doSearch(){
    var name = $("#name").val()
    layui.table.reload('roleatable', {where: {"name": name}});
}

function getToken() {
    return localStorage.getItem("token");
}
! function() {
	/*新增*/
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#roleAdd').on('click', function() {
        add(GetURLInfo() +'roleAddPage',true);
	})
}();

 