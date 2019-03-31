$(function(){
	
	initDate("#startDate");
	initDate("#endDate");
	$("#startDate,#endDate").val("");
	
	var stock = [ [ // 表头
		{
			field : 'providerNo',
			title : '献浆员卡号',
			width : '15%',
			align : 'center'
		}, {
			field : 'bname',
			title : '姓名',
			width : '15%',
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : '15%',
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
			field : 'allId',
			title : '登记号',
			width : '15%',sort : true, 
			align : 'center'
		}, {
			field : 'bloodType',
			title : '血型',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.bloodType == 0) {
					text = "A";
				} else if (d.isDisable == 1) {
					text = "B";
				} else if (d.isDisable == 2) {
					text = "O";
				} else {
					text = "AB";
				}
				return text;
			}
		}, {
			field : 'ccreateDate',
			title : '采浆时间',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.updateDate));
			}
		}, {
			field : 'type',
			title : '浆员类型',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.type==0){
					return "普通";
				}else if(d.type==1){
					return "普免";
				}else if(d.type==2){
					return "特免";
				}
			}
		}, {
			field : 'status',
			title : '是否报废',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.status==0){
					return "正常";
				}
				return "报废";
			}
		}, {
			field : 'isStorage',
			title : '是否入库',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.isStorage==0){
					return "未入库";
				}
				return "已入库";
			}
		}, {
			field : 'boxId',
			title : '箱子编号',
			width : '15%',
			align : 'center'
		}, {
			field : 'plasmaNo',
			title : '血浆编号',
			width : '15%',
			align : 'center'
		}, {
			field : 'createDate',
			title : '入库时间',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}, {
			field : 'name',
			title : '入库人',
			width : '15%',
			align : 'center'
		}
		] ];
	data("stock",stock,GetURLInfo() + 'querySeniorStockList','','',function(){
		// 表格 tr 单击事件
		var tab = $("#stock").next().find('.layui-table tbody tr'); 
		$(tab).find('tr').each(function(){
			var html=$(this).find('td').eq('7').find('div').html();
			if(html!='正常'){
				$(this).css({"background":"red","color":"white"});
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
		layui.table.reload('stock', {where: par});
	});
	//重置
	$("#rest").click(function(){
		$("#search input,select").val("");
	});
	//导出
	$("#export").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var id = $(this).attr("name");
			var val = $(this).val();
			param+=id+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length);
		}
		window.location.href="/plasmaStock/downPlasmaStockList?"+param;
	});
})