
/*列表数据显示借口*/ 
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页 
  ,table = layui.table //表格 
  ,element = layui.element; //元素操作  
  //执行 table
  table.render({
    elem: '#rumser' 
    ,url: '../../../json/inde.json' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
/* {type: 'checkbox', fixed: 'left'}*/
     {field: 'id', title: 'ID',  width: 80, sort: true,align:'center', fixed: 'left'}
      ,{field: 'username', title: '2',align:'center'}
      ,{field: 'sex', title: '3',  sort: true,align:'center'}
      ,{field: 'city', title: '4', align:'center'} 
      ,{field: 'sign', title: '5',align:'center'}
      ,{field: 'experience', title: '6',sort: true,align:'center'}
      ,{field: 'score', title: '7', sort: true,align:'center'}
      ,{field: 'classify', title: '8',align:'center'}
      ,{field: 'wealth', title: '9', sort: true,align:'center'}
       
    ]]
  });
 
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
 
  }); 
}); 
/*血清/血浆电泳信息添加*/

	! function() {
		//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		$('#insertian').on('click', function() {
			layer.ready(function() {
				layer.open({
					type: 2,
					title: '血清/血浆电泳信息添加',
					maxmin: true,
					area: ['650px', '320px'],
					content: '../../Popup/Bus/Bus_serum.html',
/*	btn: ['确定', '取消'],*/
				})
			});
		})
	}(); 