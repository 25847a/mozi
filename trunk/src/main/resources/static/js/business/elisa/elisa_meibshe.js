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
        ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
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
        title: '酶标板标题设置_修改',
       maxmin: true,
       area: ['500px', '210px'],
       content: '../../../Popup/Bus/ELISA/elisashe_datelis.html',

     })
   });
   } else if(layEvent === 'del'){
     layer.confirm('真的删除行么', function(index){
       obj.del(); //删除对应行（tr）的DOM结构
       layer.close(index);
       //向服务端发送删除指令
     });
   }
 });
! function() {
  //页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
  $('#meibs').on('click', function() {
    layer.ready(function() {
     layer.open({
         type: 2,
          title: '酶标板标题设置_添加',
         maxmin: false,
        content: '../../../Popup/Bus/ELISA/elisashe_add.html',
                 area: ['500px', '210px'],
      })
    });
  })
}();

  })
