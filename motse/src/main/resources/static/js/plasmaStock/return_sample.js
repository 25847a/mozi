var table;

$(function(){
	
	initDate("#startDate");
	initDate("#endDate");
	
	var simple = [ [ // 表头
		{
			field : 'createDate',
			title : '日期', 
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}, {
			field : 'name',
			title : '献浆员姓名', 
			align : 'center'
		}, {
			field : 'providerNo',
			title : '献浆员卡号', 
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号', 
			align : 'center'
		}, {
			field : 'fname',
			title : '留样人', 
			align : 'center'
		}, {
			field : 'fname',
			title : '留样人', 
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return '';
			}
		}
		] ];
	
	var startDate=$.trim($("#startDate").val());
	var endDate=$("#endDate").val();
	if(startDate.length>0){
		startDate+=" 00:00:00";
	}
	if(endDate.length>0){
		endDate+=" 23:59:59";
	}
	dataAll("simple",simple,{"startDate":startDate,"endDate":endDate},"/plasmaStock/queryReturnSimpleList",'','');
	
	//查询
	$("#query").click(function(){
		var startDate=$.trim($("#startDate").val());
		var endDate=$("#endDate").val();
		var startAllId=$("#startAllId").val();
		var endAllId=$("#endAllId").val();
		var startPlasmaNo=$("#startPlasmaNo").val();
		var endPlasmaNo=$("#endPlasmaNo").val();
		if(startDate.length>0){
			startDate+=" 00:00:00";
		}
		if(endDate.length>0){
			endDate+=" 23:59:59";
		}
		layui.table.reload('simple', {where: {"startDate": startDate,"endDate":endDate,"startAllId":startAllId,"endAllId":endAllId,"startPlasmaNo":startPlasmaNo,"endPlasmaNo":endPlasmaNo}});
	});
	
	/**
	 * 导出
	 */
	$("#export").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var name = $(this).attr("id");
			var val = $(this).val();
			param+=name+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length);
		}
		window.location.href="/plasmaStock/downReturnSimpleList?"+param;
	});
	
	//打印
	$("#print").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var name = $(this).attr("id");
			var val = $(this).val();
			param+=name+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length-1);
		}
		layer.open({
			type : 2,
			title:"打印血浆样本",
			maxmin : true,
			area : [ '100%', '100%' ],
			content : '/plasmaStock/printSample?'+param
		})
	});
})