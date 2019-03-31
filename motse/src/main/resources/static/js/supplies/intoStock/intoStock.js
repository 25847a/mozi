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
    ,url: GetURLInfo()+'/queryStockInfoList' //数据接口
    ,where: {
 		token : getToken(),
 		},
    page: true //开启分页
    ,limit: 10 //每页默认显示的数量
    ,response : { // //定义后端 json 格式，详细参见官方文档
		statusName : 'code', // 数据状态的字段名称，默认：code
		statusCode : -1, // 成功的状态码，默认：0
		msgName : 'message', // 状态信息的字段名称，默认：msg
		count : "count", // 数据总数的字段名称，默认：count
		data : 'data' // 数据列表的字段名称，默认：data
	},
    cols: [[ //表头  
     {field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'}
      ,{field: 'oddNumber', title: '订单号', align:'center'}
      ,{field: 'paymentType', title: '付款方式', align:'center',templet: function (d){
    	  var text = '-';
    	  if(d.paymentType==0){
    		  text="转账";
    	  }else{
    		  text="其他";
    	  }
  	  		return text;
  	   }}
      ,{field: 'status', title: '状态', align:'center',templet: function (d){
    	  var text = '-';
    	  if(d.status==4){
    		  text="已入库";
    	  }else if(d.status==5){
    		  text="待完成入库";
    	  }
  	  		return text;
  	  		
		}}
      ,{field: 'sumMoney', title: '总价', align:'center'
    	  ,templet: function (d){
    	  		return d.sumMoney+"元";
  		}}
      ,{field: 'createDate', title: '创建时间', align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		}}
      ,{field : 'right',title : '操作',align : 'center',toolbar : '#barDemo'
		}
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
	          content: GetURLInfo()+'/intoStockDatelis?id='+data.id,
	        });
	      });
	    }
    });
    	 
  });
  			
 

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
	var oddNumber = $("#oddNumber").val();
	layui.table.reload('stockPage', {page: {curr:1},where: {"oddNumber": oddNumber}});
}
function getToken() {
	return localStorage.getItem("token");
}
 
/*耗材添加*/
//
//
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
				content: GetURLInfo()+'/intoStockAdd',
			})
		});
	})
}();
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}
 

