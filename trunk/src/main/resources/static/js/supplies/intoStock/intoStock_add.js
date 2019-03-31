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
    ,url: '/orderForm/queryOrderFormPage' //数据接口
    ,page: true //开启分页
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
    	{field: 'orderformId', title: 'orderformId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
		{field: 'name', title: '耗材名称',align:'center',width:'24%'},
		{field: 'unit', title: '单位',align:'center',width:'14%'},
		{field: 'minStock', title: '下限',align:'center',width:'14%'},
		{field: 'maxStock', title: '上限',align:'center',width:'17%'},
		{field: 'num', title: '数量',align:'center',width:'16%'},
		{field: 'type', title: '类型',align:'center',width:'18%',templet: function (d){ 
            return getCategory(d.type);
        }}
    ]],
    //表格 tr 单击事件
    done:function(res,curr,count){
 	   var tab = $("#suppliesInfo").next().find('.layui-table tbody tr');
 	   tab.click(function(event){
 		  var tr =$(event.target).closest("tr")[0];
 		  $("#suppliesId").val($(tr).find("td").eq(0).find("div").html());
 	   	  $("#orderformId").val($(tr).find("td").eq(1).find("div").html());
 	   	  $("#name").val($(tr).find("td").eq(2).find("div").html());
 	   	  $("#unit").val($(tr).find("td").eq(3).find("div").html());
 	   	  $("#num").val($(tr).find("td").eq(6).find("div").html());
 	   	  $("#type").val($(tr).find("td").eq(7).find("div").html());
 	   	  $('#batchNumber').val("");
 	   });////
    }//
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
    {field: 'suppliesId',title: 'suppliesId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
    {field: 'orderformId', title: 'orderformId',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
    {field: 'name', title: '耗材名称', width:'24%',align:'center'},
	{field: 'num', title: '数量', width:'15%',align:'center'},
	{field: 'batchNumber', title: '批号', width:'15%',align:'center'},
	{field: 'type', title: '类型', align:'center',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
     {field: 'expiryTime', title: '到期时间', width:'24%', align:'center'},
     {field: 'startTime', title: '开始时间', width:'24%',align:'center'},
     {field: 'endTime', title: '结束时间', width:'24%',align:'center'},
     { fixed: 'right',  title: '操作', width:'16%',align: 'center',toolbar: '#barDemoding'}
    ]]
  });
	$("#kuadd").click(function(){ 
	   	var suppliesId = $('#suppliesId').val();
        var orderformId = $('#orderformId').val();
        var name = $('#name').val();
        var num = $('#num').val();
        var batchNumber = $('#batchNumber').val();
        if(batchNumber==""){
        	return layer.msg("请输入批号");
        }
        var type = $('#type').val();
        var expiryTime = $('#expiryTime').val();
        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
	   	  var currPageNo=0;
	   	  var 	oldData =  table.cache["newtestS"];
	   	  if(oldData==undefined){
	   		oldData=[];
	   	  }
	 	  var  data= {"suppliesId":suppliesId,"orderformId":orderformId,"name":name,"num":num,"batchNumber":batchNumber,"type":type,"expiryTime":expiryTime,"startTime":startTime,"endTime":endTime};
	 	 for(var i=0;i<oldData.length;i++){
	 		  if(data.orderformId==oldData[i].orderformId && data.suppliesId==oldData[i].suppliesId){
	 			 return layer.msg("请勿重复添加耗材");
	 		  }
	 		  var a = data.batchNumber;
	 		  var b = oldData[i].batchNumber;
	 		  if(data.batchNumber==oldData[i].batchNumber){
	 			 return layer.msg("请勿输入相同批号");
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
		var dataa =  table.cache["newtestS"];//集合
		var result = JSON.stringify(dataa);
		$.ajax({
			type : "POST",
			url :GetURLInfo()+"/insertStock", 
			data:{
				"list":result
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
 

  function Total(){
	     var numArr = []; // 定义一个空数组
	      var txt = $('#box').find(':text'); // 获取所有文本框
	      for (var i = 0; i < txt.length; i++) {
	          numArr.push(txt.eq(i).val()); // 将文本框的值添加到数组中
	      }
	      console.info(numArr);
	  }

initDate("#expiryTime");
initDate("#startTime");
initDate("#endTime");
