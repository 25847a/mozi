$(function(){
/*列表数据显示借口*/
	var templateId= $("#templateId").val();
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#details'
  ,height:'400px'
    ,url: GetURLInfo()+'/queryTemplateDatelis' //数据接口
    ,where : {
    	"templateId" : templateId
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
		{field: 'name', title: '耗材名称', sort: true,align:'center', fixed: 'left'},
		{field: 'type', title: '类型', sort: true,align:'center', fixed: 'left'},
		{field: 'unit', title: '单位', sort: true,align:'center', fixed: 'left'},
		{field: 'supply', title: '供应商', sort: true,align:'center', fixed: 'left'},
		{field: 'batchNumber', title: '批号', sort: true,align:'center', fixed: 'left'},
		{field: 'num', title: '数量', sort: true,align:'center', fixed: 'left'},
		{field: 'createDate', title: '创建时间', sort: true,align:'center', fixed: 'left',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		} }
    ]]
  });
  });

})
 
