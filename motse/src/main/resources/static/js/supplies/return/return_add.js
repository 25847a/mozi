$(function(){
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#suppliesInfo'
  ,height:'400px'
    ,url: GetURLInfo()+'/queryAddReturn' //数据接口
    ,page: true //开启分页
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},//入库表ID
    	{field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
		{field: 'oddNumber', title: '订单号',align:'center',width:'44%'},
		{field: 'name', title: '耗材名称',align:'center',width:'26%'},
		{field: 'sum', title: '剩余数量',align:'center',width:'26%'},
		{field: 'batchNumber', title: '批号',align:'center',width:'28%'},
		{field: 'expiryTime', title: '有效期',align:'center',width:'28%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.expiryTime));
		} }
    ]],
    //表格 tr 单击事件
    done:function(res,curr,count){
 	   var tab = $("#suppliesInfo").next().find('.layui-table tbody tr');
 	   tab.click(function(event){
 	   	  var tr =$(event.target).closest("tr")[0];
 	   	 $("#id").val($(tr).find("td").eq(0).find("div").html());
 	   	 $("#suppliesId").val($(tr).find("td").eq(1).find("div").html());
 	   	  $("#oddNumber").val($(tr).find("td").eq(2).find("div").html());
 	   	  $("#name").val($(tr).find("td").eq(3).find("div").html());
 	   	  $("#batchNumber").val($(tr).find("td").eq(5).find("div").html());
 	   	  $("#expiryTime").val($(tr).find("td").eq(6).find("div").html());
 	   });////
    }//
  });
///////////////
  table.render({
    elem: '#addReturn'
    ,height:'400px'
    ,page: true //开启分页
    ,cols: [[ //表头
    	{field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
    	{field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'}
      ,{field: 'name', title: '耗材名称', align:'center',width:'26%'}
      ,{field: 'batchNumber', title: '批号',width:'28%',align:'center'}
      ,{field: 'expiryTime', title: '有效期', align:'center',width:'28%'}
      ,{field: 'num', title: '数量', align:'center'}
      	 , { fixed: 'right',  title: '操作', width: 80,align: 'center',toolbar: '#barDemoding'   }
    ]]
  });
	$("#add").click(function(){ 
		var id = $('#id').val();//入库表ID
		 var suppliesId = $('#suppliesId').val();
        var name = $('#name').val();
        var batchNumber = $('#batchNumber').val();
        var expiryTime = $('#expiryTime').val();
        var num = $('#num').val();
        if(num==''){
        	return layer.msg("请输入退还数量");
        }
	   	  var currPageNo=0;
	   	  var 	oldData =  table.cache["addReturn"];
	   	  if(oldData==undefined){
	   		oldData=[];
	   	  }
	 	  var  data= {"id":id,"suppliesId":suppliesId,"name":name,"batchNumber":batchNumber,"expiryTime":expiryTime,"num":num};
	 	 for(var i=0;i<oldData.length;i++){
	 		  if(data.suppliesId==oldData[i].suppliesId && data.batchNumber==oldData[i].batchNumber){
	 			 return layer.msg("请勿重复添加耗材");
	 		  }
	 	  }
	 	  oldData.push(data); 
	 	    table.reload('addReturn', {
	 	        page : {
	 	           curr : currPageNo
	 	        },
	 	      data : oldData
	 	  });
	   	});
	$("#exactly").click(function(){
		var reason = $("#reason").val();//退还原因
		var data =  table.cache["addReturn"];
		var result = JSON.stringify(data);
		$.ajax({
			type : "POST",
			url :GetURLInfo()+"/insertReturn", 
			data:{
				"list":result,
				"reason":reason
			},
			datatype : "json",
			success : function(data) {
				parent.layer.alert(data.message, function() {
					parent.layer.closeAll();
					 parent.location.reload();
				});
			},
			error : function() {
				parent.layer.alert(data.message, function() {
					parent.layer.closeAll();
					 parent.location.reload();
				});
			}
		});
		
		
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
initDate("#orderDate");