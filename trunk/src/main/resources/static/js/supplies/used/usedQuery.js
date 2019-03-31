//献浆员登记
$(function(){
/*列表数据显示借口*/ 
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate, //日期
  laypage = layui.laypage, //分页 
  table = layui.table, //表格 
  element = layui.element; //元素操作  
  //执行 table
  table.render({
    elem: '#queryUsed',
    url: GetURLInfo()+'/queryUsedList', //数据接口
    method:'post',
 	where: {
 		token : getToken()
 		},
    page: true,//开启分页
    response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	/* {type: 'checkbox', fixed: 'left'}*/
    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
         {field: 'oddNumber', title: '订单号', align:'center'},
          {field: 'supplies', title: '耗材名称', align:'center'},
          {field: 'type', title: '类别', align:'center',templet: function (d){
    			if(d.type==0){
    				return '耗材';
    			}else{
    				return '质控品';
    			}
    		} },
          {field: 'sum', title: '出入库数量', sort: true,align:'center'},
          {field: 'batchNumber', title: '批号', align:'center'},
          {field: 'apply', title: '用于模块',align:'center',templet: function (d){
  			return getTemplateValue(d.apply);
  		} },
          {field: 'createDate', title: '使用时间', align:'center',templet: function (d){
        	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
  		} }
    ]]
  });
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
   /*   layer.msg('查看详情');*/
  		layer.ready(function() {
			layer.open({
				type: 2,
				title: '献浆员信息统计',
				maxmin: true,
				area: ['750px', '400px'],
				content: '../../Popup/Bus/Bus-details.html', 
			})
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
})///到这里
//搜索
function doSearch(){
	var batchNumber = $("#batchNumber").val();
	var createDate = $("#createDate").val();
	var supplies = $("#supplies").val();
	layui.table.reload('queryUsed', {page: {curr:1},where: {"batchNumber": batchNumber,"createDate":createDate,"supplies":supplies}});
}
function getToken() {
	return localStorage.getItem("token");
}
/*日期时间*/ 
dayControl("createDate");
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}
 

