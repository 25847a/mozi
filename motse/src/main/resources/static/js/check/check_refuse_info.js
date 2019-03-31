$(function(){
	// 获取淘汰原因列表
	$.ajax({
		type : "POST",
		url : "/disease/queryDiseaseInfo",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#eliminateReason").append('<option value="'+item.name+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
	//初始化时间
	initDate("#startTime");
	var notRefuse = [ [ // 表头
		{
			field : 'pid',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			width : '25%',
			align : 'center'
		}, {
			field : 'pname',
			title : '姓名',
			width : '15%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',
			width : '25%',
			sort: true,
			align : 'center'
		}, {
			field : 'result',
			title : '体检结果',
			align : 'center',
			width : '20%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.result == 0) {
					text = "合格";
				} else{
					text = "不合格";
				}
				return text;
			}
		}, {
			field : 'tz',
			title : '体重',
			width : '15%',
			align : 'center'
		}, {
			field : 'tw',
			title : '体温',
			width : '15%',
			align : 'center'
		}, {
			field : 'mb',
			title : '脉搏',
			width : '15%',
			align : 'center'
		}, {
			field : 'xya',
			title : '血压MIN',
			width : '20%',
			align : 'center'
		}, {
			field : 'xyb',
			title : '血压MAX',
			width : '20%',
			align : 'center'
		}, {
			field : 'xb',
			title : '胸部',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.xb==0){
					return '正常';
				}
				return '不正常';
			}
		}, {
			field : 'fb',
			title : '腹部',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'pf',
			title : '皮肤',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'wg',
			title : '五官',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'sz',
			title : '四肢',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}
		]];
	
	var refuse = [ [ // 表头
		{
			field : 'pid',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			width : '20%',
			align : 'center'
		}, {
			field : 'pname',
			title : '姓名',
			width : '15%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',
			width : '20%',
			sort: true,
			align : 'center'
		}, {
			field : 'result',
			title : '体检结果',
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.result == 0) {
					text = "合格";
				} else{
					text = "不合格";
				}
				return text;
			}
		}, {
			field : 'tz',
			title : '体重',
			width : '15%',
			align : 'center'
		}, {
			field : 'tw',
			title : '体温',
			width : '15%',
			align : 'center'
		}, {
			field : 'mb',
			title : '脉搏',
			width : '15%',
			align : 'center'
		}, {
			field : 'xya',
			title : '血压MIN',
			width : '20%',
			align : 'center'
		}, {
			field : 'xyb',
			title : '血压MAX',
			width : '20%',
			align : 'center'
		}, {
			field : 'xb',
			title : '胸部',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.xb==0){
					return '正常';
				}
				return '不正常';
			}
		}, {
			field : 'fb',
			title : '腹部',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'pf',
			title : '皮肤',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'wg',
			title : '五官',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'sz',
			title : '四肢',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
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
		}, {
			field : 'opinion',
			title : '医生意见',
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.opinion==0){
					return '暂时拒绝';
				}else{
					return '永久淘汰';
				}
			}
		}, {
			field : 'day',
			title : '拒绝天数',
			width : '20%',
			align : 'center'
		}, {
			field : 'eliminateReason',
			title : '拒绝原因',
			width : '20%',
			align : 'center'
		}
		]];
	var date = $("#startTime").val();
	//体检未发布人员列表
	dataAll("notRefuse",notRefuse,{"date":date,"isRefuse":0},"/refuseInfo/queryCheckRefuseInfoList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#notRefuse").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			var time=$("#startTime").val();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(4).find("div").html();
			
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
		});
	});
	
	//体检已发布人员列表
	dataAll("refuse",refuse,{"date":date,"isRefuse":1},"/refuseInfo/queryCheckRefuseInfoList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#refuse").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			var time=$("#startTime").val();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(4).find("div").html();
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
		});
	});
	
	//刷新
	$("#refresh").click(function(){
		/*$("#idCard").attr("src","../../../img/new_pa2.png");
		$("#basic input,select").each(function(index,item){
			var r = $(item).hasClass("not");
			if(!$(item).hasClass("not")){
				$(item).val("");
			}
			$("#eliminateReason").val("");
		});*/
		location.reload();
	});
	
	//确定
	$("#confirm").click(function(){
		var id =$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		var opinion=$.trim($("#opinion").val());
		var day=$("#day").val();
		var eliminateReason=$("#eliminateReason").val();
		if(opinion.length<1){
			layer.alert('请选择医生意见');
			return false;
		}else if(opinion==0 && day.length<1){
			layer.alert('请选择拒绝天数');
			return false;
		}
		if(eliminateReason.length<1){
			layer.alert('请选择拒绝原因');
			return false;
		}
		var releaseId=$("#releaseId").val();
		$.ajax({
			type : "POST",
			url : "/refuseInfo/upateCheckRefuseInfo",
			datatype : "json",
			data:{"opinion":opinion,"day":day,"eliminateReason":eliminateReason,"id":id,"isRefuse":1,"releaseId":releaseId},
			success : function(data) {
				layer.alert(data.message,function(){
					location.reload();
				});
			}
		});
	});
	
	//查询
	$("#query").click(function(){
		var date = $("#startTime").val();
		var providerNo=$.trim($("#providerNo_search").val());
		var allId=$.trim($("#registriesNo_search").val());
		if(!/^[0-9]*$/.test(allId)){
			layer.alert('请输入正确的登记号');
			return false;
		}
		layui.table.reload('notRefuse', {where: {"providerNo": providerNo, "allId": allId,"date": date,isRefuse:0}});
		layui.table.reload('refuse', {where: {"providerNo": providerNo, "allId": allId,"date":date,isRefuse:1}});
	});
	
	//取消发布
	$("#cancel").click(function(){
		var id =$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.ajax({
			type : "POST",
			url : "/refuseInfo/upateCheckRefuseInfo",
			datatype : "json",
			data:{"id":id,"isRefuse":0},
			success : function(data) {
				layer.alert(data.message,function(){
					location.reload();
				});
			}
		});
	});
})

function loadData(providerNo,time,id,registriesNo){
	//获取浆员详细信息
	$.ajax({
		type : "POST",
		url : "/common/queryWhereBaseInfoOrDetailObj",
		datatype : "json",
		data:{"providerNo":providerNo},
		success : function(data) {
			if(data.code==-1){
				$("#basic input").each(function(){
					var item=$(this);
					if(!item.hasClass('not')){
						var names=item.attr("name");
						item.val(data.data[names]);
					}
				});
				var bloodType = data.data["bloodType"];
				var sex = data.data["sex"];
				var text = '未知';
				if (bloodType == 0) {
					text = "A";
				} else if (bloodType == 1) {
					text = "B";
				} else if (bloodType == 2) {
					text = "O";
				} else if(bloodType == 3){
					text = "AB";
				}
				$("#bloodType").val(text);
				$("#sex").val(sex=='0' ?'男':'女');
				var type=data.data["type"];
				if(type==0){
					$("#type").val("普通");
				}else if(type==1){
					$("#type").val("普免");
				}else if(type==2){
					$("#type").val("特免");
				}
				$("#age").val(getAge(data.data["idNo"]));
				$("#idCard").attr("src",data.data["imagez"]);
				
				$.post(GetURLInfo()+"queryAssayRefuseInfoById",{"id":id},function(res){
					$("#opinion").val(res.data["opinion"]);
					$("#day").val(res.data["day"]);
					$("#eliminateReason").val(res.data["eliminateReason"]);
				});
			}
		},
		error:function(){
			
		}
	});
}