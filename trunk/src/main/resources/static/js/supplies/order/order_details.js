$(function(){
/*列表数据显示借口*/
	var order= $("#orderformId").val();
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#details'
  ,height:'400px'
    ,url: GetURLInfo()+'/queryDatelisList' //数据接口
    ,where : {
    	"order" : order
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
    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
		{field: 'supplies', title: '耗材名称', sort: true,align:'center', fixed: 'left'},
		{field: 'type', title: '类型', sort: true,align:'center', fixed: 'left'},
		{field: 'unit', title: '单位', sort: true,align:'center', fixed: 'left'},
		{field: 'sopply', title: '供应商', sort: true,align:'center', fixed: 'left'},
		{field: 'depot', title: '仓库', sort: true,align:'center', fixed: 'left'},
		{field: 'num', title: '数量', sort: true,align:'center', fixed: 'left'},
		{field: 'money', title: '单价', sort: true,align:'center', fixed: 'left'},
		{field: 'sumMoney', title: '总价', sort: true,align:'center',fixed: 'left'}
    ]]
  });
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
  if(layEvent === 'dateadd'){
  }
    });
  table.render({
    elem: '#newtestS'
    ,height:'400px'
    ,page: true //开启分页
    ,cols: [[ //表头
     {field: 'id', title: '耗材编号', sort: true,align:'center'}
      ,{field: 'unit', title: '单位', align:'center'}
      ,{field: 'money', title: '单价', sort: true,align:'center'}
      ,{field: 'num', title: '数量', align:'center'}
      	 , { fixed: 'right',  title: '操作', width: 80,    align: 'center',   toolbar: '#barDemoding'   }
    ]]
  });
  table.on('tool(demo1)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var datas = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
     if(layEvent === 'del') {
      layer.confirm('真的删除行么', function(index) {
        obj.del(); //删除对应行（tr）的DOM结构
        layer.close(index);
        //向服务端发送删除指令
      });
    }
    });//table.on
  });

})
 
