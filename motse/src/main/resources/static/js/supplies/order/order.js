$(function(){
  //耗材列表
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate, //日期
  	  laypage = layui.laypage, //分页
      table = layui.table, //表格
      element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#orderlist',
    url: '/orderForm/queryOrder', //数据接口
    where : {
		token : getToken()
	},
    page: true, //开启分页
    limit: 10, //每页默认显示的数量
    response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头 
    {type: 'checkbox',fixed: 'left'},
    {field: 'id', title: 'ID', align:'center'},
    {field: 'oddNumber', title: '订购单号', width:'20%',align:'center'},
    {field: 'paymentType', title: '付款方式', width:'15%',align:'center',templet: function (d){ 
			var text = '-';
            if (d.paymentType == 0) {
                text = "转账";
            } 
            return text;
        }},
      {field: 'status', title: '状态', width:'15%', align:'center',templet: function (d){ 
    	  return getQuarantineValue(d.status);
        }},
      {field: 'sumMoney', title: '总价', width:'15%', align:'center'},
      {field: 'remarks', title: '备注', width:'15%', align:'center'},
      {fixed: 'right',  title: '操作', width:'18%',align: 'center',toolbar: '#barDemohao'}
    ]]
  });
    table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail') {
      layer.ready(function() {
        layer.open({
          type: 2,
          title: '详情页面',
          maxmin: false,
          area: ['80%', '80%'],
          content: '/orderForm/queryDatelis?id='+data.id,
        });
      });
    }
    });
    table.on('checkbox(	)', function(obj) {
		console.log(obj)
	});
     var $ = layui.jquery, active = {
			delect : function() {
				var checkStats = table.checkStatus('orderlist'), data = checkStats.data;
				if(data.length==0){
					return layer.msg("请选择订单", {icon: 2});
				}
				if(data.length==0 | data.length>=2){
					return layer.msg("请选择一个订单", {icon: 2});
				}
				layer.ready(function() {
					layer.open({
						type: 2,
						title: '订单删除',
						maxmin: true,
						area: ['500px', '190px'],
						maxmin: false,            //最大化按钮
						content: '/orderForm/orderDelect?id='+data[0].id
					})
				});
			},
			update : function() {
				var checkStats = table.checkStatus('orderlist'), data = checkStats.data;
				if(data.length==0 && data.length<2){
					return layer.msg("请选择一个订单", {icon: 2});
				}
				if(data[0].status!=0){
					return layer.msg("只能修改待审批订单", {icon: 2});
				}
				var result = JSON.stringify(data);
				layer.ready(function() {
					layer.open({
						type: 2,
						title: '订单修改',
						maxmin: true,
						area: ['500px', '250px'],
						maxmin: false,            //最大化按钮
						content: '/orderForm/orderDatelis?id='+data[0].id
					})
				});
				
			}

		};
	$('.lgk').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
	$('.hcj').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
  });/// 
//搜索按钮点击事件
$("#query").click(function(){
	doSearch();
});
//重置
$("#btn-reset").click(function(){
	$("#search_par input").val("");
	$("#search_par select").val("");
}); 
})///到这里


//搜索
function doSearch(){
	var createDate = $("#startTime").val();
	var updateDate = $("#endTime").val();
	var oddNumber = $("#oddNumber").val();
	var status = $("#status").val();
	layui.table.reload('orderlist', {page: {curr:1},where: {"createDate": createDate,"updateDate": updateDate,"oddNumber": oddNumber,"status": status}});
}
function getToken() {
	return localStorage.getItem("token");
}
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}
/*日期时间*/ 
dayControl("startTime");
dayControl("endTime");
/*耗材添加*/
! function() {
	$('#haocaiadd').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '耗材添加',
				maxmin: true,
				area: ['90%', '90%'],
				maxmin: false,//最大化按钮
				content: GetURLInfo()+'/orderAdd',
			})
		});
	})
}();