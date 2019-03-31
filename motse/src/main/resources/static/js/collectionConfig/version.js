$(function(){
	$.ajax({
        type : "POST",
        cache: false,  // 禁用缓存
        url :GetURLInfo() + 'queryVersionList',
        datatype : "json",
        success : function(result) {
        	 for (var o in result.data){
                 var str = "<option value=" + result.data[o].id + ">" + result.data[o].versionNumber + "</option>";
                 $("#versionNumber").append(str);
             }
        	 versionOne(null);//获取当前版本(这样写的方式是异步执行完嵌套请求,防止数据返回错乱)
        },
        error : function() {
            alert("请稍后试试");
        }
    });
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
    });
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		var id=$("#versionNumber").val();
		 versionOne({"id":id});
	});
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 	
})
/**
 * 获取当前版本
 * @returns
 */
function versionOne(data){
	$.ajax({
        type : "POST",
        cache: false,  // 禁用缓存
        url :GetURLInfo() + 'queryVersionOne',
        data:data,
        datatype : "json",
        success : function(result) {
        	if(null!=result.data){
        		$("#versionDescription").val(result.data.versionDescription);
        	}
        },
        error : function() {
            alert("请稍后试试");
        }
    });
}

	function getToken() {
		return localStorage.getItem("token");
	}
	 ! function() {
		 	/* 新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#versionAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'versionAdd',true,[ '380px', '440px' ]);
		 		}
		 	)
		 }();
		