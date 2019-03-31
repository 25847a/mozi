/*列表数据显示借口*/ 
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页 
  ,table = layui.table //表格 
  ,element = layui.element; //元素操作   
  table.render({
    elem: '#Advancedtest'  
    ,url: '../../../json/inde.json' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
		/*  {type: 'checkbox', fixed: 'left'} */
     {field: 'id', title: 'ID',  sort: true, fixed: 'left',align:'center'}
      ,{field: 'username', title: '2',align:'center'}
      ,{field: 'sex', title: '3',  sort: true,align:'center'}
      ,{field: 'city', title: '4',align:'center'} 
      ,{field: 'sign', title: '5',align:'center'}
      ,{field: 'experience', title: '6',  sort: true,align:'center'}
      ,{field: 'score', title: '7', sort: true,align:'center'}
      ,{field: 'classify', title: '8',align:'center' }
      ,{field: 'wealth', title: '9', sort: true,align:'center'}
    ]]
  });
}); 
 
 /*也统计*/
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#BusAdvan').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '血源部月统计',
				maxmin: true,
				area: ['750px', '520px'],
				content: '../../popup/bus/bus_adancedquery.html',

			})
		});
	})
}();