$(function(){
 
/*耗材列表*/
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#stockPage'
    ,url: GetURLInfo()+'/queryoutstockList' //数据接口
    ,where: {
 		token : getToken()
 		},
    page: true //开启分页
    ,response : { // //定义后端 json 格式，详细参见官方文档
		statusName : 'code', // 数据状态的字段名称，默认：code
		statusCode : -1, // 成功的状态码，默认：0
		msgName : 'message', // 状态信息的字段名称，默认：msg
		count : "count", // 数据总数的字段名称，默认：count
		data : 'data' // 数据列表的字段名称，默认：data
	},
    cols: [[ //表头  
    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
		{field: 'name', title: '耗材名称',align:'center' },
		{field: 'category', title: '类别',align:'center',templet: function (d){
			if(d.category==0){
				return '耗材';
			}else{
				return '质控品';
			}
		} },
		{field: 'type', title: '类型',align:'center'},
		{field: 'unit', title: '单位',align:'center'},
		{field: 'supply', title: '供应商',align:'center'},
		{field: 'batchNumber', title: '批号',align:'center'},
		{field: 'outNumber', title: '数量', sort: true,align:'center'},
		{field: 'expiryTime', title: '有效期',align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.expiryTime));
		} },
		{field: 'createDate', title: '出库时间',align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		} }
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
	          content: GetURLInfo()+'/outStockDatelis?batchNumber='+data.batchNumber,
	        });
	      });
	    }
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
})////到这里
 //搜索
function doSearch(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var batchNumber = $("#batchNumber").val();
	var expiryTime = $("#expiryTime").val();
	layui.table.reload('stockPage', {page: {curr:1},where: {"startTime": startTime,"endTime": endTime,"batchNumber": batchNumber,"expiryTime": expiryTime}});
}
function getToken() {
	return localStorage.getItem("token");
}
/*耗材添加*/
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#haocaiadd').on('click', function() {
		layer.ready(function() {
			layer.open({

				type: 2,
				title: '耗材添加',
				maxmin: true,
				area: ['90%', '90%'],
        maxmin: false,            //最大化按钮
				content: GetURLInfo()+'/outStockAdd',
			})
		});
	})
}();
/*日期时间*/
initDate("#startTime");
initDate("#endTime");
initDate("#expiryTime");
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}
