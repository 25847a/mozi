//献浆员登记
$(function(){
	var apply = $("#apply").val();
	var depotId = $("#depotId").val();
/*列表数据显示借口*/ 
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate, //日期
  laypage = layui.laypage, //分页 
  table = layui.table, //表格 
  element = layui.element; //元素操作  
  //执行 table
  table.render({
    elem: '#usedPage',
    url: GetURLInfo()+'/queryTemplateSuppliesInfo', //数据接口
    method:'post',
 	where: {
 		"apply":apply,
 		"depotId":depotId
 		},
    page: true,//开启分页 
    response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
    	{field: 'suppliesId',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center' },
		{field: 'name', title: '耗材名称',align:'center',width:'20%'},
		{field: 'unit', title: '单位',align:'center',width:'15%'},
		{field: 'surplusNumber', title: '数量',align:'center',width:'15%'},
		{field: 'batchNumber', title: '批号',align:'center',width:'20%'},
		{field: 'expiryTime', title: '有效期',align:'center',width:'25%',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.expiryTime));
		}},
		{field: 'apply', title: '用于模块',align:'center',width:'20%',templet: function (d){
			return getTemplateValue(d.apply);
		} }
    ]],
  //表格 tr 单击事件
    done:function(res,curr,count){
 	   var tab = $("#usedPage").next().find('.layui-table tbody tr');
 	   tab.click(function(event){
 	   	  var tr =$(event.target).closest("tr")[0];
 	   	  $("#suppliesId").val($(tr).find("td").eq(0).find("div").html());
 	   	  $("#name").val($(tr).find("td").eq(1).find("div").html());
 	   	  $("#unit").val($(tr).find("td").eq(2).find("div").html());
 	   	  $("#batchNumber").val($(tr).find("td").eq(4).find("div").html());
 	   	  $("#applyName").val($(tr).find("td").eq(6).find("div").html());
 	   });////
    }//
   
  });
/////第二个
  table.render({
    elem: '#newtestS'
    ,height:'400px'
    ,page: true //开启分页
    ,cols: [[ //表头
     {field: 'suppliesId',title: '耗材编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center' }
      ,{field: 'name', title: '耗材名称', align:'center'}
      ,{field: 'unit', title: '单位', align:'center'}
      ,{field: 'batchNumber', title: '批号',align:'center'}
      ,{field: 'num', title: '数量', align:'center'}
      	 , { fixed: 'right',  title: '操作', width: 80,    align: 'center',   toolbar: '#barDemoding'   }
    ]]
  });//////////////////////////////////////////////////////////////////////////////////////////////////
  //添加按钮
  $("#kuadd").click(function(){ 
	 var suppliesId = $('#suppliesId').val();
	 var name = $('#name').val();
     var unit = $('#unit').val();
     var batchNumber = $('#batchNumber').val();
     var num = $('#num').val();
	   	  var currPageNo=0;
	   	  var 	oldData =  table.cache["newtestS"];//第二个
	   	  if(oldData==undefined){
	   		oldData=[];
	   	  }
	   	  if(num==""){
	   		return layer.msg("数量不能为空");
	   	  }
	 	  var  data= {"suppliesId":suppliesId,"name":name,"unit":unit,"batchNumber":batchNumber,"num":num};
	 	  for(var i=0;i<oldData.length;i++){
	 		  if(data.suppliesId==oldData[i].id){
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
  //确认按钮
  $("#exactly").click(function(){
	  	var templateName = $("#templateName").val();
	  	if(templateName==''){
			return layer.msg("请输入模板名称");
		}
		var apply = $("#apply").val();//模块
		var depotId = $("#depotId").val();//仓库ID
		var startTime = $("#startTime").val();//开始时间
		var endTime = $("#endTime").val();//结束时间
		var data =  table.cache["newtestS"];
		var result = JSON.stringify(data);
		$.ajax({
			type : "POST",
			url :GetURLInfo()+"/insertTemplate", 
			data:{
				"list":result,
				"templateName":templateName,
				"apply":apply,
				"depotId":depotId,
				"startTime":startTime,
				"endTime":endTime
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
  
//监听表格复选框选择
/*	table.on('checkbox(usedPage)', function(obj) {
		console.log(obj)
	});
	var $ = layui.jquery, active = {
			getCheckData : function() {
				var checkStats = table.checkStatus('usedPage'), data = checkStats.data;
				if(data.length==0){
					return layer.msg("请选择耗材信息");
				}
				var result = JSON.stringify(data);
				var name = $("#name").val();
				if(name==''){
					return layer.msg("请输入模板名称");
				}
				var num = $("#num").val();
				var apply = $("#apply").val();
				var depotId = $("#depotId").val();
				var startTime = $("#startTime").val();
				var endTime = $("#endTime").val();
					$.ajax({
						type : "POST",
						url :GetURLInfo()+"/insertTemplate",
						data : {
							"list":result,
							"name":name,
							"num":num,
							"apply":apply,
							"depotId":depotId,
							"startTime":startTime,
							"endTime":endTime
						},
						dataType : "json",
						success : function(data) {
							parent.layer.alert(data.message,function() {
                                parent.layer.closeAll();
                                parent.location.reload();
                            });
						},
						error : function() {
							parent.layer.alert("请稍后试试",function(){
				   				parent.layer.closeAll();
				   			});
						}
					});
			}
		};
	$('.hcj').on('click', function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
  */
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
}); ////////////layui.use(['laydate', 'laypage', 'table', ], function(){

})//////$(function()

initDate("#startTime");
initDate("#endTime");

