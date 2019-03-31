$(function() {

	// 查询仓库的
	$.ajax({
		type : "POST",
		url : "/depot/queryDepotInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].name + "</option>";
				$("#depotId").append(str);
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	/* 列表数据显示借口 */ 
	layui.use([ 'laydate', 'laypage', 'table'],function() {
						var laydate = layui.laydate, // 日期
						laypage = layui.laypage, // 分页
						table = layui.table, // 表格
						element = layui.element; // 元素操作
						table.render({
							elem : '#order',
							url : '/order/queryOrderList', // 数据接口
							method : 'post',
							where : {
								token : getToken()
							},
							page : true,// 开启分页
							 limit: 10, //每页默认显示的数量
							response : { // //定义后端 json 格式，详细参见官方文档
								statusName : 'code', // 数据状态的字段名称，默认：code
								statusCode : -1, // 成功的状态码，默认：0
								msgName : 'message', // 状态信息的字段名称，默认：msg
								count : "count", // 数据总数的字段名称，默认：count
								data : 'data' // 数据列表的字段名称，默认：data
							},
							cols : [ [ // 表头
							{
								type : 'checkbox',
								fixed: 'left'
							}, {
								field : 'id',
								title : '编号',
								width:'25%',
								align : 'center',
							}, {
								field : 'oddNumber',
								title : '订单号',
								width:'20%',
								align : 'center',
							}, {
								field : 'paymentType',
								title : '付款方式',
								width:'15%',
								align : 'center',
							}, {
								field : 'status',
								title : '状态',
								width:'15%',
								align : 'center',
								templet : function(d) { // 单元格格式化函数
									var text = '-';
									if (d.status == 0) {
										text = "待审批";
									} else if (d.status == 1) {
										text = "待检验";
									} else if (d.status == 2) {
										text = "已完成";
									} else if (d.status == 3) {
										text = "订购失败";
									}
									return text;
								}
							}, {
								field : 'sumMoney',
								title : '总价',
								width:'15%',
								align : 'center',
							}, {
								field : 'depot',
								title : '仓库',
								width:'15%',
								align : 'center',
							}, {
								fixed : 'right',
								title : '操作',
								width:'17%',
								align : 'center',
								toolbar : '#barDemo'
							} ] ]
						});// table

						table.on('tool(demo)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
							var data = obj.data // 获得当前行数据
							, layEvent = obj.event; // 获得
							// 对应的值
							if (layEvent === 'detail') {
								layer.ready(function() {
									layer.open({
										type : 2,
										title : '详情页面',
										maxmin : true,
										area : [ '80%', '65%' ],
										content : '/orderForm/queryDatelis?id='
												+ data.id,
									})
								});
							}

						});

						var $ = layui.jquery, active = {
							getCheckData : function() {
								var checkStats = table.checkStatus('order'), data = checkStats.data;
								var result = JSON.stringify(data);
								examination(data, 0, "请选择待审批订单", result,
										function() {
											location.reload();
										});
							},
							cancel : function() {
								var checkStats = table.checkStatus('order'), data = checkStats.data;
								var result = JSON.stringify(data);
								examination(data, 1, "请选择待审批订单", result,
										function() {
											location.reload();
										});
							},
							delect : function() {
								var checkStats = table.checkStatus('order'), data = checkStats.data;
								var result = JSON.stringify(data);
								layer.confirm('确定订单作废么', function(index) {
									$.ajax({
										type : "POST",
										url : "/orderForm/UselessStatus",
										data : result,
										dataType : "json",
										contentType : "application/json",
										success : function(data) {
											location.reload();
										},
										error : function() {
											alert("进入错误");
										}
									});
								});
							}

						};
						$('.hcj').on('click', function() {
							var type = $(this).data('type');
							active[type] ? active[type].call(this) : '';
						});
						$('.hmy').on('click', function() {
							var type = $(this).data('type');
							active[type] ? active[type].call(this) : '';
						});
						$('.lgk').on('click', function() {
							var type = $(this).data('type');
							active[type] ? active[type].call(this) : '';
						});
					});// layui.use
	function examination(data, num, sun, result, func) {
		if (data[0].status != num) {
			return layer.msg(sun);
		} else {
			var da = {
				"id" : data[0].id,
				"status" : data[0].status
			};
			$.post("/orderForm/updateStatus", da, function(result) {
				func();
			})
		}
	}
	;
	// 搜索按钮点击事件
	$("#query").click(function() {
		doSearch();
	});

})// 到这里 $(function()
// 搜索
function doSearch() {
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var oddNumber = $("#oddNumber").val();
	var depotId = $("#depotId").val();
	var status = $("#status").val();
	layui.table.reload('order', {page: {curr:1},
		where : {
			"startTime" : startTime,
			"endTime" : endTime,
			"oddNumber" : oddNumber,
			"depotId" : depotId,
			"status" : status
		}
	});
}
function getToken() {
	return localStorage.getItem("token");
}
/* 日期时间(开始日期、结束日期) */
dayControl("startTime");
dayControl("endTime");
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}