$(function(){
	 // 加载仓库选项
    $.ajax({
        type : "POST",
        url :"/depot/queryDepotInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#depotId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
        }
    });
	
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#suppliesInfo'
  ,height:'400px'
    ,url: '/suppliesInfo/querySuppliesInfoList' //数据接口
    ,page: true //开启分页
   
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center' },
		{field: 'name', title: '耗材名称',align:'center',width:'26%'},
		{field: 'category', title: '类型',width:'26%',align:'center',templet: function (d){
			return getCategory(d.category);
		}},
		{field: 'apply', title: '用于模块',align:'center',width:'22%',templet: function (d){
			return getTemplateValue(d.apply);
		} },
		{field: 'unit', title: '单位',align:'center',width:'16%'},
		{field: 'supply', title: '供应商',align:'center',width:'23%'},
		{field: 'money', title: '单价',align:'center' ,width:'16%'},
		{field: 'minStock', title: '最小库存',align:'center',width:'22%' },
		{field: 'maxStock', title: '最大库存',align:'center' ,width:'22%'}
    ]],
    //表格 tr 单击事件
    done:function(res,curr,count){
 	   var tab = $("#suppliesInfo").next().find('.layui-table tbody tr');
 	   tab.click(function(event){
 	   	  var tr =$(event.target).closest("tr")[0];
 	   	  $("#suppliesId").val($(tr).find("td").eq(0).find("div").html());
 	   	  $("#name").val($(tr).find("td").eq(1).find("div").html());
 	   	  $("#unit").val($(tr).find("td").eq(4).find("div").html());
 	   	  $("#money").val($(tr).find("td").eq(6).find("div").html());
 	   	  $("#minStock").val($(tr).find("td").eq(7).find("div").html());
 	   	  $("#maxStock").val($(tr).find("td").eq(8).find("div").html());
 	   	 $("#category").val($(tr).find("td").eq(2).find("div").html());
 	   
 	   });////
    }//
  });
/////第二个
  table.render({
    elem: '#newtestS'
    ,height:'400px'
    ,page: true //开启分页
    ,cols: [[ //表头
     {field: 'id',title: '耗材编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center' }
      ,{field: 'name', title: '耗材名称', align:'center'}
      ,{field: 'category', title: '类型', align:'center'}
      ,{field: 'unit', title: '单位', align:'center'}
      ,{field: 'money', title: '单价', sort: true,align:'center'}
      ,{field: 'num', title: '数量', align:'center'}
      	 , { fixed: 'right',  title: '操作', width: 80,    align: 'center',   toolbar: '#barDemoding'   }
    ]]
  });//////////////////////////////////////////////////////////////////////////////////////////////////
	$("#kuadd").click(function(){ 
	   	 var id = $('#suppliesId').val();
	   	 var name = $('#name').val();
        var unit = $('#unit').val();
        var money = $('#money').val();
        var num = $('#num').val();
        var category = $('#category').val();
	   	  var currPageNo=0;
	   	  var 	oldData =  table.cache["newtestS"];
	   	  if(oldData==undefined){
	   		oldData=[];
	   	  }
	   	  if(num==""){
	   		return layer.msg("数量不能为空", {icon: 2});
	   	//	return layer.msg('<span style="color:#FFF">数量不能为空</span>');
	   	  }
	   	  
	 	  var  data= {"id":id,"name":name,"category":category,"unit":unit,"money":money,"num":num};
	 	  for(var i=0;i<oldData.length;i++){
	 		  if(data.id==oldData[i].id){
	 			 return layer.msg("请勿重复添加耗材", {icon: 2});
	 		  }
	 	  }
	 	oldData.push(data); 
	 	    table.reload('newtestS', {
	 	        page : {
	 	           curr : currPageNo
	 	        },
	 	      data : oldData
	 	  });
	 	    
	 	var number=0;
		for(var i=0;i<oldData.length;i++){
			if(oldData[i].length!=0){
				var sum = parseFloat(oldData[i].num)*parseFloat(oldData[i].money);
				number = parseFloat(number)+parseFloat(sum);
			}
		}
		$("#total").val(number); 
		
	   	});
	$("#exactly").click(function(){
		var depotId = $("#depotId").val();
		if(depotId==''){
			return layer.msg("请选择仓库", {icon: 2});
		}
		var total = $("#total").val();//金额合计
		if(total==''){
			return layer.msg("请统计金额", {icon: 2});
		}
		var orderDate=$("#orderDate").val();
		var pay=$("#pay").val();
		if(pay==''){
			return layer.msg("请选择付款方式", {icon: 2});
		}
		var remarks = $("#remarks").val();
		var data =  table.cache["newtestS"];
		var result = JSON.stringify(data);
		$.ajax({
			type : "POST",
			url :GetURLInfo()+"/insertOrderform", 
			data:{
				"list":result,
				"depotId":depotId,
				"total":total,
				"orderDate":orderDate,
				"pay":pay,
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