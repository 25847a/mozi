$(function(){
/*列表数据显示借口*/ 
layui.use(['laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页 
  ,table = layui.table //表格 
  ,element = layui.element; //元素操作  
  
  //执行 table
 
  table.render({
    elem: '#newtestS' 
    ,url: 'queryActity' //数据接口
    ,page: true //开启分页
    ,limits:[5, 10, 15,20]
    ,limit: 5  //每页默认显示的数量
    ,where:{name:getName()}
    ,response: { // //定义后端 json 格式，详细参见官方文档
         statusName: 'code', //数据状态的字段名称，默认：code
         statusCode: -1, //成功的状态码，默认：0
         msgName: 'message', //状态信息的字段名称，默认：msg
         count: "count", //数据总数的字段名称，默认：count
         data: 'data' //数据列表的字段名称，默认：data
    }
    ,cols: [[ //表头
     {field: 'id',title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"}
      ,{field: 'name', title: '活动名称', align:'center'}
      ,{field: 'startDate', title: '活动开始时间', sort: true,align:'center',templet:function(obj){
    	  return obj.startDate == null ?"":new Date(obj.startDate).Format("yyyy-MM-dd");
      }}
      ,{field: 'endDate', title: '活动结束时间', align:'center',templet:function(obj){
    	  return obj.endDate == null ?"":new Date(obj.endDate).Format("yyyy-MM-dd");
      }} 
      ,{field: 'count', title: '针次条件', align:'center'}
      ,{field: 'money', title: '活动金额', align:'center'}
      , {fixed: 'right',title: '操作', width: 165,align: 'center',toolbar: '#barDemo'}
    ]],
    done:function(){
    	
    }
 }); 
  $("#query").click(function(){
		var name = $("#name").val();
		layui.table.reload('newtestS',{where:{"name":name}});
	});
  function getName(){
	  return localStorage.getItem("name");
  }
 
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	var id=obj.data.id;
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值 
     		if(layEvent === 'detail') {
     		layer.ready(function() {
			layer.open({
				type: 2,
				title: '修改活动',
				maxmin: true,
				area: ['500px', '440px'],
				content: 'toUpdateActivity?id='+id, 
			})
		});
     		} else if(layEvent === 'del'){
      layer.confirm('真的删除行么', function(index){
    	  	$.ajax({
    	  		url:"deleteActity?id="+id,
    	  		type:"post",
    	  		dataType:"json",
    	  		success:function(result){
    	  			if(result.code == "-1"){
    	  				obj.del();
        	  			var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.location.reload();
    	  			}else{
    	  				alert(result.message);
    	  			}
    	  		}
    	  	});
	        obj.del(); //删除对应行（tr）的DOM结构
	        layer.close(index);
	        location.reload();
	        //向服务端发送删除指令
      });
    } 
 
  }); 
}); 

//重置
$("#reset").click(function(){
	$("input[type=text]").val("");
});
 
/*新增*/
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#activityadd').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '新增活动',
				maxmin: true,
				area: ['500px', '440px'],
				content:'toAddActivity', 
			});
			
		});
	})
}();


})

 




 