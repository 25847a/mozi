var table;

$(function(){
	
	var stock = [ [ // 表头
		{
			field : 'id',
			title : '箱号',
			align : 'center'
		}, {
			field : 'sNumber',
			title : '箱子数量',
			align : 'center'
		}, {
			field : 'immuneName',
			title : '类型',
			align : 'center'
		}, {
			field : 'name',
			title : '装箱人',
			align : 'center'
		}, {
			field : 'fname',
			title : '送浆人',
			align : 'center'
		}, {
			field : 'updateDate',
			title : '送浆时间',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.updateDate));
			}
		}
		] ];
	
	var startDate=$.trim($("#start").val());
	var endDate=$("#end").val();
	
	dataAll("stock",stock,{"startDate":startDate,"endDate":endDate,"status":1},"/plasmaStock/queryOutPlasmaStock",'','');
	
	//查询
	$("#query").click(function(){
		var start=$.trim($("#start").val());
		var end=$("#end").val();
		
		if(start.length<1){
			layer.alert("请输入开始箱号");
		}else if(end.length<1){
			layer.alert("请输入结束箱号");
		}else{
			var boxIdStart=start.substring(0,6);
			var boxIdEnd=end.substring(0,6);
			if(boxIdStart!=boxIdEnd){
				layer.alert('请输入相同的类型的箱号');
			}else{
				start= start.substring(6,start.length);
				end = end.substring(6,end.length);
				layui.table.reload('stock', {where: {"startDate": (boxIdStart+start),"endDate":(boxIdStart+end),"status":1}});
			}
		}
	});
	
	//导出数据到公司
	$("#exportCompany").click(function(){
		/*var start=$.trim($("#start").val());
		var end=$("#end").val();
		
		if(start.length<1){
			layer.alert('请输入开始时间');
		}else if(end.length<1){
			layer.alert('请输入结束时间');
		}else{
			$.get('/plasmaStock/exportPlasmaInfo?startDate='+startDate+"&endDate="+endDate,function(res){
				layer.alert(res.message);
			});
		}*/
		
		var start=$("#start").val();
		var end = $("#end").val();
		if(start.length<1){
			layer.alert("请输入开始箱号");
		}else if(end.length<1){
			layer.alert("请输入结束箱号");
		}else{
			var boxIdStart=start.substring(0,6);
			var boxIdEnd=end.substring(0,6);
			if(boxIdStart!=boxIdEnd){
				layer.alert('请输入相同的类型的箱号');
			}else{
				start= start.substring(6,start.length);
				end = end.substring(6,end.length);
				$.get('/plasmaStock/exportPlasmaInfo?startDate='+(boxIdStart+start)+"&endDate="+(boxIdStart+end),function(res){
					layer.alert(res.message);
				});
			}
		}
	});
	
})