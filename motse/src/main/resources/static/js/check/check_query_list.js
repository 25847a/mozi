$(function(){
	initDate("#startTime");
	initDate("#endTime");
	initDate("#regStartTime");
	initDate("#regEndTime");
	
	$("#startTime,#endTime,#regStartTime,#regEndTime").val("");
	//未体检人员列表
	var cols = [ [ // 表头
		{
			field : 'providerNo',
			title : '献浆员卡号',
			width : '10%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : '9%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',
			width : '15%', 
			sort: true,
			align : 'center'
		}, {
			field : 'bloodType',
			title : '血型',
			width : '7%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '未知';
				if (d.bloodType == 0) {
					text = "A";
				} else if (d.bloodType == 1) {
					text = "B";
				} else if (d.bloodType == 2) {
					text = "O";
				} else if(d.bloodType == 3){
					text = "AB";
				}
				return text;
			}
		}, {
			field : 'tz',
			title : '体重',
			width : '7%',
			align : 'center'
		}, {
			field : 'tw',
			title : '体温',
			width : '7%',
			align : 'center'
		}, {
			field : 'mb',
			title : '脉搏',
			width : '7%',
			align : 'center'
		}, {
			field : 'xya',
			title : '血压MIN',
			width : '9%',
			align : 'center'
		}, {
			field : 'xyb',
			title : '血压MAX',
			width : '10%',
			align : 'center'
		}, {
			field : 'xb',
			title : '胸部',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.xb){
					if(d.xb==0){
						return '正常';
					}
					return '不正常';
				}
				return "-";
			}
		}, {
			field : 'fb',
			title : '腹部',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.fb){
					if(d.fb==0){
						return '正常';
					}else if(d.fb==1){
						return '有肿块';
					}else if(d.fb==2){
						return '压痛';
					}else{
						return '肝脾肿大';
					}
				}
				return "-";
			}
		}, {
			field : 'pf',
			title : '皮肤',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.pf){
					if(d.pf==0){
						return '正常';
					}else if(d.pf==1){
						return '黄染';
					}else if(d.pf==2){
						return '有创面感染';
					}else if(d.pf==3){
						return '有大面积皮肤病';
					}else{
						return '浅表淋巴结有明显肿大';
					}
				}
				return "-";
			}
		}, {
			field : 'wg',
			title : '五官',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.wg){
					if(d.wg==0){
						return '正常';
					}else if(d.wg==1){
						return '严重疾病';
					}else if(d.wg==2){
						return '巩膜黄染';
					}else{
						return '甲状腺肿大';
					}
				}
				return "-";
			}
		}, {
			field : 'sz',
			title : '四肢',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.sz){
					if(d.sz==0){
						return '正常';
					}else if(d.sz==1){
						return '严重残疾';
					}else if(d.sz==2){
						return '功能性障碍';
					}else if(d.sz==3){
						return '关节红肿';
					}else if(d.sz==4){
						return '静脉穿刺部分皮肤损伤';
					}else{
						return '有静脉注射药物痕迹';
					}
				}
				return "-";
			}
		}, {
			field : 'result',
			title : '检查结果',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.result){
					var text = '-';
					if (d.result == 0) {
						text = "合格";
					} else {
						text = "不合格";
					}
					return text;
				}
				return "-";
			}
		}, {
			field : 'createDate',
			title : '检查时间',
			align : 'center',
			width : '11%',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}, {
			field : 'checkType',
			title : '类型',
			align : 'center',
			width : '11%',
			templet : function(d) { // 单元格格式化函数
				if(d.checkType==0){
					return '正常体检';
				}else if(d.checkType==1){
					return '重检';
				}
				return '';
			}
		}, {
			field : 'opinion',
			title : '医生意见',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}
		
		] ];
	
	data("check",cols,GetURLInfo() + 'queryCheckQueryList','','',function(){
		var tab = $("#check").next().find('.layui-table tbody tr');
		$(tab).each(function(){
			var item=$(this);
			var state = item.find('td').last().find('div').html();
			if(null!=state && state.length>0){
				if(state=='1'){
					$(item).css({"color":"red"});
				}else if(state=='0'){
					$(item).css({"color":"green"});
				}
			}
		});
	});
	//查询
	$("#query").click(function(){
		var par=[];
		$("#search input,select").each(function(){
			var id = $(this).attr("id");
			par[id]=$(this).val();
		});
		layui.table.reload('check', {page: {curr:1},where: par});
	});
	
	//重置
	$("#rest").click(function(){
		$("#search input,select").val("");
	});
	
	//导出体检数据
	$("#export").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var id = $(this).attr("id");
			var val = $(this).val();
			param+=id+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length);
		}
		window.location.href="/check/downCheckList?"+param;
	});
	
	//打印
	$("#print").click(function(){
		var startTime=$("#startTime").val();
		if(startTime.length<1){
			layer.alert('请选择开始时间');
			return false;
		}
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印献血浆者体检记录表',
				maxmin : true,
				area: ['90%', '100%'],
				content : '/check/printCheck?startTime='+startTime
			})
		});
	});
})