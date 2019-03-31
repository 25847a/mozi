$(function(){
	initDate("#cmDate");
});
/*列表数据显示借口*/
layui.use([ 'laydate', 'laypage', 'table', 'element' ],
		function() {
			var laydate = layui.laydate // 日期
			, laypage = layui.laypage // 分页
			, table = layui.table // 表格
			, element = layui.element; // 元素操作
			// 执行 table
			table.render({
				elem : '#toconig',
				url : GetURLInfo() + 'queryTestConfigList', // 数据接口
				page : false, // 开启分页
				method : 'POST',
				response : { // //定义后端 json 格式，详细参见官方文档
					statusName : 'code', // 数据状态的字段名称，默认：code
					statusCode : -1, // 成功的状态码，默认：0
					msgName : 'message', // 状态信息的字段名称，默认：msg
					count : "count", // 数据总数的字段名称，默认：count
					data : 'data' // 数据列表的字段名称，默认：data
				},
				cols : [[
				{field : 'id', title : '模板编号', sort : true, align : 'center', fixed : 'left'
				}, {field : 'templateName',title : '模板名称',align : 'center'
				}, {field : 'cmDate',title : '配置日期',sort : true,align : 'center', templet:function(d){ return $.myTime.UnixToDate(d.cmDate);}}
				, {field : 'isDefault',title : '是否默认配置',align : 'center',templet : function(d) { var text = '-';if (d.isDefault == 0) {text = "否";} else if (d.isDefault == 1) {text = "是";}return text;}
				}, {field : 'status',title : '状态',align : 'center',templet : function(d) { var text = '-';if (d.status == 0) {text = "禁用";} else if (d.status == 1) {text = "启用";}return text;}
				}, {fixed : 'right',title : '操作',width : 380,align : 'center',toolbar : '#barDemo'
				}]]
			});
			table.on('tool(demo)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
				// lay-filter="对应的值"
				var data = obj.data // 获得当前行数据
				, layEvent = obj.event; // 获得 lay-event 对应的值
				if (layEvent === 'shezhi') {// 参数设置
					layer.ready(function() {
						layer.open({
							type : 2,
							title : '检验项目设置-默认配置',
							maxmin : true,
							area : [ '550px', '383px' ],
							content : GetURLInfo() + 'busintoconig?id=' + data.id
						})
					});

				} else if (layEvent === 'del') {// 删除
					layer.confirm('真的删除该行么', function(index) {
						obj.del(); // 删除对应行（tr）的DOM结构
						$.ajax({
							type : "POST",
							url : GetURLInfo() + "deleteTestConfig?id=" + data.id,
							datatype : "json",
							success : function(result) {
								alert("操作成功");
								layer.close(index);
							}
						});
						// 向服务端发送删除指令
					});
				} else if (layEvent === 'peizhi') {// 配置
					layer.ready(function() {
						layer.open({
							type : 2,
							title : '参数配置',
							maxmin : true,
							area : [ '80%', '88%' ],
							content : GetURLInfo() + 'bussetup?id=' + data.id
						})
					});
				} else if (layEvent === 'moren') {// 默认
					$.ajax({
						type : "POST",
						url : GetURLInfo() + "updateTestConfigWithDefault?id="
								+ data.id,
						datatype : "json",
						success : function(result) {
							checkCode(result,function(){
								location.reload();
							});
						}
					});

				} else if (layEvent === 'detail') {// 修改
					layer.ready(function() {
						$("#id").val( data.id);
						$("#submitButton").click();
					});
				}
			});

			// 表格 tr 单击事件
			var tab = $("#toconig").next('.layui-table-view').find(
					'table.layui-table');
			tab.click(function(event) {
				var tr = $(event.target).closest("tr")[0];
				var td = $(event.target).closest("td")[0];
				var providerNo = $(tr).find("td").eq(0).find("div").html();
				if (5 != $(td).attr("data-field")) {
					initData(providerNo);
				}
			});
		});
			

var idtep = 0;
function initData(id) {
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/testConfig/queryTestConfigById?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#templateName").val(data.templateName);
			$("#id").val(data.id);
			$("#cmDate").val(data.cmDate);
			$("#isDefault").val(data.isDefault);
			$("#status").val(data.status);
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}



! function() { 
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#submitButton').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '检验配置添加',
				maxmin: true,
				area: ['450px', '260px'],
				content: GetURLInfo() +'/intoconigAdd?id='+$("#id").val(),
			})
		});
	})
}();
