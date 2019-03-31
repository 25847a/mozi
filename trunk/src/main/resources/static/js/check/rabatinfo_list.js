$(function(){
	$("#rest").click(function(){
		$("#search input,select").val('');
	});
	$("#refresh").click(function(){
		window.location.reload();
	});
	//初始化时间
	initDate("#startTime");
	initDate("#endTime");
	
	//加载体检单位
	$.ajax({
		type : "POST",
		url : "/rabatUnit/queryRabatUnitList",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#rabatunitId").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
	var rabatinfo = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.sex == 0) {
					text = "男";
				} else {
					text = "女";
				}
				return text;
			}
		}, {
			field : 'bloodType',
			title : '血型',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
			
				return getBloodValue(d.bloodType);
			}
		}, {
			field : 'isCheck',
			title : '是否体检',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.isCheck==0){
					return '未体检';
				}
				return '已体检';
			}
		}, {
			field : 'createDate',
			title : '体检时间',
			align : 'center'
		}, {
			field : 'fname',
			title : '体检单位',
			align : 'center'
		}, {
			field : 'fname',
			title : '体检结果',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.result==0){
					return "合格";
				}else if(d.result==1){
					return '不合格';
				}
				return '';
			}
		},{title:"操作",fixed: 'right', align:'center', toolbar: '#barDemoS'}
		] ];
	
	layui.use(['laydate', 'laypage', 'table'], function(){
		var laydate = layui.laydate //日期
		,laypage = layui.laypage //分页
		,table = layui.table //表格
		,element = layui.element; //元素操作
		//执行 table
		table.render({
			elem: '#rabatinfo',
	        method: "POST",
	        where:{"startTime":$("#startTime").val(),"endTime":$("#endTime").val()},
	        url: GetURLInfo() + 'queryRabatinfoList', //数据接口
	        page: true, //开启分页
	        limits: [5, 10, 15, 20],
	        limit: 5, //每页默认显示的数量
	        response: { // //定义后端 json 格式，详细参见官方文档
	            statusName: 'code', //数据状态的字段名称，默认：code
	            statusCode: -1, //成功的状态码，默认：0
	            msgName: 'message', //状态信息的字段名称，默认：msg
	            count: "count", //数据总数的字段名称，默认：count
	            data: 'data' //数据列表的字段名称，默认：data
	        },
	        cols: rabatinfo
		  });
		  table.on('tool(page)', function(obj){
			  var data = obj.data, //获得当前行数据
              layEvent = obj.event; //获得 lay-event 对应的值
	          if (layEvent == 'detail') {
	        	  $("#image_show img").attr("src",data.rabatImg);
	        	  layer.open({ 
	        		  type: 1,
	        		  title: "查看图片",
	        		  area : [ '500px', '335px' ],
	        		  content: $("#image_show")
	      		  })
	          }
		  });
	});
	
	//查询
	$("#query").click(function(){
		var par=[];
		$("#search input,select").each(function(){
			var id = $(this).attr("name");
			par[id]=$(this).val();
		});
		layui.table.reload('rabatinfo', {where: par});
	});
	
	$("#upload").click(function(){
		layer.open({
			type: 2,
			title: 'X光胸片检查录入',
			maxmin: true,
			area: ['800px', '550px'],
			content: "/rabatinfo/rabat?providerNo="
		})	
	});
})