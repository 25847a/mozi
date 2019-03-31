$(function(){
	//查询仓库的
	$.ajax({
		type : "POST",
		url : "/depot/queryDepotInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].name + "</option>";
				$("#depotId").append(str);
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
 //耗材列表
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
	  var laydate = layui.laydate, //日期
	  laypage = layui.laypage, //分页 
	  table = layui.table, //表格 
	  element = layui.element; //元素操作  
	  //执行 table
	  table.render({
	    elem: '#resuly',
	    url: GetURLInfo()+'queryTemplateList' , //数据接口
	    method:'post',
	 	where: {
	 		token : getToken()
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
		{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'},
		{field: 'name', title: '耗材模板',align:'center'},
		{field: 'apply', title: '用于模块',align:'center',templet: function (d){
			return getTemplateValue(d.apply);
		} },
		{field: 'depot', title: '仓库',align:'center'},
		{field: 'startDate', title: '有效期开始',align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.startDate));
		} 
			},
		{field: 'endDate', title: '有效期结束',align:'center',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.endDate));
		} 
			},
		{field: 'isDelete', title: '是否删除',align:'center',templet: function (d){
			if(d.isDelete==1){
				return '是';
			}else{
				return '否';
			}
		} },
		{fixed : 'right',title : '操作',align : 'center',toolbar : '#barDemohao'} 
    ]]
  });
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
      layer.ready(function(){
        layer.open({
          type: 2,
          title: '模板详情',
          maxmin: false,
        	area: ['80%', '80%'],
          content: '/detailed/templateDatelis?id='+data.id,
        });
      });
    } else if(layEvent == 'update') {
    	 layer.ready(function(){
    	        layer.open({
    	          type: 2,
    	          title: '修改模板',
    	          maxmin: false,
    	        	area: ['34%', '45%'],
    	          content: '/template/templateUpdate?id='+data.id,
    	        });
    	      });
    }else if(layEvent=='del'){
    	layer.confirm('真的删除行么', function(index) {
      	  $.ajax({
    			type : "post",
    			url : GetURLInfo()+"deleteTemplate",
    			data : {
    				"id" : data.id
    			},
    			dataType : "json",
    			success : function(result) {
    				checkCode(result, function() {
    					parent.layer.alert(result.message, function() {
    						var index = parent.layer.getFrameIndex(window.name);
    						parent.layer.closeAll();
    						location.reload();
    					});
    				});
    			},
    			error : function() {
    				parent.layer.alert("请稍后试试",function(){
    	   				parent.layer.closeAll();
    	   			});
    			}
    		})
        });
    }
    });
 });
//搜索按钮点击事件
$("#query").click(function() {
	doSearch();
});
$("#reod").click(function(){
	$("#queryapply").val("");
	$("#isDelete").val("");
})
}) ////到这里


//搜索
function doSearch(){
	var isDelete = $("#isDelete").val();
	var apply = $("#queryapply").val();
	layui.table.reload('resuly', {page: {curr:1},where: {"apply": apply,"isDelete":isDelete}});
}
function getToken() {
	return localStorage.getItem("token");
}
/*模板添加*/
//
//
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#haocaiadd').on('click', function() {
		var apply = $("#apply").val();
		var depotId = $("#depotId").val();
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '模板添加',
				maxmin: true,
				area: ['80%', '80%'],
				content: '/template/templateAdd?apply='+apply+'&depotId='+depotId,
			})
		});
	})
}();
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}




