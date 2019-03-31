$(function(){
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
			layer.msg("请稍后试试");
		}
	});
    
	/*列表数据显示借口*/ 
	layui.use(['laydate', 'laypage', 'table'], function(){
	  var laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作
	  //执行 table
	  table.render({
	    elem: '#rumsers'
	    ,url: GetURLInfo() +'queryQuarantineList' //数据接口
	    ,page: true //开启分页
	    ,limit: 10 //每页默认显示的数量
	    ,response: { // //定义后端 json 格式，详细参见官方文档
	        statusName: 'code', //数据状态的字段名称，默认：code
	        statusCode: -1, //成功的状态码，默认：0
	        msgName: 'message', //状态信息的字段名称，默认：msg
	        count: "count", //数据总数的字段名称，默认：count
	        data: 'data' //数据列表的字段名称，默认：data
	   },
	    cols: [[ //表头
	        {type : 'checkbox',fixed : 'left'},
	        {field: 'id',title: '编号',width:'18%',display : 'none',align:'center'}, 
	        {field: 'oddNumber', title: '订购单号',width:'18%',align:'center'},
	        {field: 'paymentType', title: '付款方式',width:'15%', align:'center',templet:function(d){
	         	  return getPaymentValue(d.paymentType);
	     	  }},
	        {field: 'status', title: '是否检验',width:'15%', align:'center',templet:function(d){
	       	  return getQuarantineValue(d.status);
	   	  }},
	   	 {field: 'sumMoney', title: '总价',width:'15%', align:'center'},
	        {field: 'depot', title: '仓库',width:'15%', align:'center'},
	        {fixed: 'right', title: '操作',width:'20%', align: 'center', toolbar: '#barDemo'}
	   		]]
	  });
	  table.on('tool(barDemo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
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
	// 监听表格复选框选择
		table.on('checkbox(rumsers)', function(obj) {
			console.log(obj)
		});
		var $ = layui.jquery, active = {
			getCheckData : function() {
				var checkStats = table.checkStatus('rumsers'), data = checkStats.data;
				var result = JSON.stringify(data);
				examination(data, 1, "请选择待检验订单", result,
						function() {
							location.reload();
						});
			},
			cancel : function() {
				var checkStats = table.checkStatus('rumsers'), data = checkStats.data;
				var result = JSON.stringify(data);
				examination(data, 2, "请选择完成订单", result,
						function() {
							location.reload();
						});
			}
		};
		//审批
		$('.hcj').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		$('.hmy').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
	  });		/////////////////layui.use(['laydate', 'laypage', 'table', ]
	function examination(data, num, sun, result, func) {
		if (data[0].status != num) {
			layer.msg(sun);
		} else {
			var da = {"id":data[0].id,"status":data[0].status};
		//	var result = JSON.stringify(data);
			$.post(GetURLInfo()+"/updateBuyStatus",da,function(result){
				func();
			})
		}
	};
   //data("rumsers",cols,GetURLInfo() + 'queryQuarantineList',null,GetURLInfo() + "deleteDepot");
	//日期时间
	getDate();
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	});
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 
})////到这里
  
	//搜索
	function doSearch(){
		var updateDateStart = $("#updateDateStart").val();
		var updateDateEnd = $("#updateDateEnd").val();
		var depotId = $("#depotId").val();
		var oddNumber = $("#oddNumber").val();
		layui.table.reload('rumsers', {page: {curr:1},where: {
			"updateDateStart": updateDateStart,
			"updateDateEnd": updateDateEnd,
			"depotId": depotId,
			"oddNumber": oddNumber}});
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
	function getDate(){
		dayControl("updateDateStart");
		dayControl("updateDateEnd");
	}
