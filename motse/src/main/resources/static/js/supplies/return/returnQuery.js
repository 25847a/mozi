$(function(){
  //耗材列表
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#returnPage'
    ,url: GetURLInfo()+'/queryReturnList' //数据接口
    ,where : {
		token : getToken()
	}
    ,page: true //开启分页
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{type: 'checkbox', fixed: 'left'},
     {field: 'id', title: '编号', sort: true,align:'center', fixed: 'left'}
      ,{field: 'batchNumber', title: '批号',width:'15%', align:'center'}
      ,{field: 'returnOrder', title: '退还订单',width:'15%', sort: true,align:'center'}
      ,{field: 'reason', title: '退还原因',width:'18%', align:'center'}
      ,{field: 'status', title: '状态', width:'14%',align:'center',templet: function (d){
    	  var text = '-';
  	  	if (d.status == 0) {
  	  		text = "未审核";
  	  	} else if(d.status==1){
  	  		text = "已审核";
  	  	}
  	  		return text;
		} }
      ,{field: 'createDate', title: '退还时间',width:'17%', align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		} }
      , { fixed: 'right',  title: '操作',width:'18%',align: 'center',   toolbar: '#barDemohao'   }
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
	          content: GetURLInfo()+'/returnDatelis?returnOrder='+data.returnOrder,
	        });
	      });
	    }
	  });
  //监听表格复选框选择
  table.on('checkbox(returnPage)', function(obj) {
	console.log(obj)
  });
  var $ = layui.jquery, active = {
		getCheckData : function() {
			var checkStats = table.checkStatus('returnPage'), data = checkStats.data;
			var result = JSON.stringify(data);
			examination(data, 0, "请选择待审批订单", result,
					function() {
						location.reload();
					});
		},
		cancel : function() {
			var checkStats = table.checkStatus('returnPage'), data = checkStats.data;
			var result = JSON.stringify(data);
			examination(data, 1, "请选择待审批订单", result,
					function(result) {
						location.reload();
					});
		},
	};
	  $('.hcj').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		$('.hmy').on('click', function() {
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
		});
		function examination(data, num, sun, result, func) {
			if (data[0].status != num) {
				layer.msg(sun);
			} else {
			//	var result = JSON.stringify(data);
				var dd={"returnOrder":data[0].returnOrder,"status":data[0].status};
				$.post(GetURLInfo()+"/updateReturnStatus",dd,function(result){
					func(result);
				})
			}
		};
		
//搜索按钮点击事件
$("#query").click(function(){
	doSearch();
});
//重置
$("#btn-reset").click(function(){
	$("#search_par input").val("");
	$("#search_par select").val("");
}); 
})//列表
})//function()到这里

//搜索
function doSearch(){
	var createDate = $("#startTime").val();
	var updateDate = $("#endTime").val();
	var returnOrder = $("#returnOrder").val();
	var status = $("#status").val();
	layui.table.reload('returnPage', {page: {curr:1},where: {
		"createDate": createDate,"updateDate": updateDate,
		"returnOrder": returnOrder,"status": status}});
}
function getToken() {
	return localStorage.getItem("token");
}
/*日期时间(开始日期、结束日期)*/
dayControl("startTime");
dayControl("endTime"); 
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}