$(function(){
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table' ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#suppliesInfo'
  ,height:'400px'
    ,url: GetURLInfo()+'/queryAddOutStockList' //数据接口
    ,page: true //开启分页
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},//入库表ID    0
    	{field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},//1
    	{field: 'orderId',title: 'orderId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},//2
    	{field: 'name', title: '耗材名称',align:'center', width : '25%'},//3
    	{field: 'supply', title: '供应商',align:'center', width : '25%'},//8
    	{field: 'batchNumber', title: '批号',align:'center', width : '20%'},//6
		{field: 'surplusNumber', title: '剩余数量',align:'center', width : '20%'},//5
		{field: 'unit', title: '单位',align:'center',  width : '15%'},//4
		{field: 'expiryTime', title: '有效期',align:'center',  width : '20%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.expiryTime));
		} }//7
    ]],
    //表格 tr 单击事件
    done:function(res,curr,count){
 	   var tab = $("#suppliesInfo").next().find('.layui-table tbody tr');
 	   tab.click(function(event){
 	   	  var tr =$(event.target).closest("tr")[0];
 	   	 $("#id").val($(tr).find("td").eq(0).find("div").html());
 	   	  $("#suppliesId").val($(tr).find("td").eq(1).find("div").html());
 	   	  $("#orderformId").val($(tr).find("td").eq(2).find("div").html());
 	   	  $("#name").val($(tr).find("td").eq(3).find("div").html());
 	   	  $("#batchNumber").val($(tr).find("td").eq(5).find("div").html());
 	   	  $("#surplusNumber").val($(tr).find("td").eq(6).find("div").html());
 	   	  $("#expiryTime").val($(tr).find("td").eq(8).find("div").html());
 	   	  $("#supply").val($(tr).find("td").eq(4).find("div").html());
 	   
 	   });////
    }//
  });
//////////
  table.render({
    elem: '#newtestS'
    ,height:'400px'
    ,page: true //开启分页
    ,cols: [[ //表头
    {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},//入库表ID
     {field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
 	 {field: 'orderId',title: 'orderId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
     {field: 'name', title: '耗材名称', width : '25%', align:'center'},
     {field: 'outNumber', title: '出货数量',  width : '25%',align:'center'},
     {field: 'batchNumber', title: '批号',  width : '25%',align:'center'},
     {field: 'expiryTime', title: '到期时间',  width : '25%',align:'center'},
     { fixed: 'right',  title: '操作',  width : '25%',   align: 'center',   toolbar: '#barDemoding'}
    ]]
  });
	$("#kuadd").click(function(){ 
		var id = $('#id').val();//入库表ID
	   	 var suppliesId = $('#suppliesId').val();
	   	 var orderId =$('#orderformId').val();
	   	 var name = $('#name').val();
	   	 var outNumber = $('#outNumber').val();
	   	if(outNumber==''){
	   		return layer.msg("请输入出库数量");
	   	}
	   	var surplusNumber = $('#surplusNumber').val();
	   	if(parseInt(outNumber)>parseInt(surplusNumber)){
	   		return layer.msg("出库数量不能大于剩余数量");
	   	}
        var batchNumber = $('#batchNumber').val();
        var expiryTime = $('#expiryTime').val();
	   	  var currPageNo=0;
	   	  var 	oldData =  table.cache["newtestS"];
	   	  if(oldData==undefined){
	   		oldData=[];
	   	  }
	 	  var  data= {"id":id,"suppliesId":suppliesId,"orderId":orderId,"name":name,"outNumber":outNumber,"batchNumber":batchNumber,"expiryTime":expiryTime};
	 	 for(var i=0;i<oldData.length;i++){
	 		  if(data.suppliesId==oldData[i].suppliesId && data.orderId==oldData[i].orderId){
	 			 return layer.msg("请勿重复添加耗材");
	 		  }
	 	  }
	 	  oldData.push(data); 
	 	    table.reload('newtestS', {
	 	        page : {
	 	           curr : currPageNo
	 	        },
	 	      data : oldData
	 	  });
	   	});
	$("#exactly").click(function(){
		var remarks = $("#remarks").val();
		var dataa =  table.cache["newtestS"];//集合
		var result = JSON.stringify(dataa);
		$.ajax({
			type : "POST",
			url :GetURLInfo()+"/insertOutstock", 
			data:{
				"list":result,
				"remarks":remarks
			},
			datatype : "json",
			success : function(data) {
				parent.layer.alert(data.message, function() {
					parent.layer.closeAll();
					 parent.location.reload();
				});
			},
			error : function() {
				parent.layer.alert("操作失败", function() {
					parent.layer.closeAll();
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
