$(function(){
	
	//重置
	$("#rest").click(function(){
		$("#search input,select").each(function(){
			$(this).val('');
		});
	});
	//加载采浆情况设置
	$.ajax({
		type : "POST",
		url : "/situation/querySituationConfigList",
		datatype : "json",
		success : function(result) {
			if(result.code==-1){
				$.each(result.data,function(index,item){
					$("#situation").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			parent.layer.alert(result.message,function(){
				parent.layer.closeAll();
			});
		}
	});
	//初始化时间
	initDate("#startTime");
	//初始化时间
	initDate("#endTime");
	$("#startTime,#endTime").val("");
	
	var rumser = [ [ // 表头
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
			width : '15%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : '10%',
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			width : '7%',
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
			title : '登记号',sort : true, 
			width : '20%',
			align : 'center'
		}, {
			field : 'bloodType',
			title : '血型',
			align : 'center',
			width : '7%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.bloodType == 0) {
					text = "A";
				} else if (d.bloodType == 1) {
					text = "B";
				} else if (d.bloodType == 2) {
					text = "O";
				} else {
					text = "AB";
				}
				return text;
			}
		}, {
			field : 'createDate',
			title : '采浆日期',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}, {
			field : 'result',
			title : '采浆结果',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.result == 0) {
					text = "合格";
				}else{
					text = "不合格";
				}
				return text;
			}
		}, {
			field : 'plasmAmount',
			title : '采浆量',
			width : '10%',
			align : 'center'
		}, {
			field : 'fname',
			title : '采浆人',
			width : '10%',
			align : 'center'
		}, {
			field : 'loopCount',
			title : '循环次数',
			width : '10%',
			align : 'center'
		}, {
			field : 'runTime',
			title : '运行时间',
			width : '10%',
			align : 'center'
		}, {
			field : 'wholeBlood',
			title : '处理全血量',
			width : '10%',
			align : 'center'
		}, {
			field : 'knAmount',
			title : '抗凝剂用量',
			width : '10%',
			align : 'center'
		}, {
			field : 'rname',
			title : '采浆室',
			width : '10%',
			align : 'center'
		}, {
			field : 'yname',
			title : '机型',
			width : '10%',
			align : 'center'
		}, {
			field : 'uname',
			title : '机号',
			width : '10%',
			align : 'center'
		}, {
			field : 'sname',
			title : '采浆情况',
			width : '20%',
			align : 'center'
		}
		] ];
	
	data("rumser",rumser,"/plasmCollection/querySeniorQueryPlasmCollectionList",'','');
	
	//加载浆站采室
	$.ajax({
		type : "POST",
		url : "/room/queryRoomByPlasmType",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#roomId").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	//加载机型
	$("#roomId").change(function(){
		var roomId=$(this).val();
		//加载浆站采室
		$.ajax({
			type : "POST",
			url : "/room/queryPlasmTypeByMachine",
			datatype : "json",
			data:{"roomId":roomId},
			success : function(data) {
				if(data.code==-1){
					$("#typeId").empty();
					$("#typeId").append('<option value="">请选择</option>');
					$("#machineId").empty();
					$("#machineId").append('<option value="">请选择</option>');
					$.each(data.data,function(index,item){
						$("#typeId").append('<option value="'+item.id+'">'+item.name+'</option>');
					});
				}
			},
			error:function(){
				
			}
		});
	});
	
	$("#typeId").change(function(){
		//加载机号数据
		var plasmTypeId=$(this).val();
		//加载浆站采室
		$.ajax({
			type : "POST",
			url : "/room/queryMachineNumberById",
			datatype : "json",
			data:{"plasmTypeId":plasmTypeId},
			success : function(data) {
				if(data.code==-1){
					$("#machineId").empty();
					$("#machineId").append('<option value="">请选择</option>');
					$.each(data.data,function(index,item){
						$("#machineId").append('<option value="'+item.id+'">'+item.name+'</option>');
					});
				}
			},
			error:function(){
				
			}
		});
	});
	
	//查询
	$("#query").click(function(){
		var param=[];
		$("#search input,select").each(function(){
			var item=$(this);
			var name=item.attr("name");
			var val=item.val();
			param[name]=val;
		});
		layui.table.reload('rumser', {page: {curr:1},where: param});
	});
	
	//导出
	$("#export").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var name = $(this).attr("name");
			var val = $(this).val();
			param+=name+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length);
		}
		window.location.href="/plasmCollection/exportCollList?"+param;
	});
	
	//打印
	$("#print").click(function(){
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '献血浆者采浆记录表',
				maxmin : true,
				area: ['90%', '100%'],
				content : '/plasmCollection/printPlasmaCollection'
			})
		});
	});
})