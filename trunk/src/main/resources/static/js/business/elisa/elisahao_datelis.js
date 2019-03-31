/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table','element'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#jiemei'
    ,height:'400px'
    ,url: '../../../../json/inde.json' //数据接口
    ,page: true //开启分页
    ,cols: [[ //表头
     {field: 'id', title: '模板编号',  sort: true,align:'center'/*, fixed: 'left'*/}
      ,{field: 'username', title: '模板名称',align:'center'}
      ,{field: 'sex', title: '配置日期',  sort: true,align:'center'}
      ,{field: 'city', title: '是否默认配置', align:'center'}
      ,{field: 'sign', title: '状态',align:'center'}
      ,{fixed: 'right', width: 100, align:'center', toolbar: '#barDemo'}
   ]]
 });

  })
