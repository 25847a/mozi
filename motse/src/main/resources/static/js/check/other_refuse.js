$(function(){
	// 获取淘汰原因列表
	$.ajax({
		type : "POST",
		url : "/disease/queryDiseaseInfo",
		datatype : "json",
		success : function(data) {
			$("#eliminateReason").append('<option value="(ALT)转氨酶检验不合格">(ALT)转氨酶检验不合格</option>');
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#eliminateReason").append('<option value="'+item.name+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
	var refuse = [ [ // 表头
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
			width : '25%',
			align : 'center'
		}, {
			field : 'name',
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
				} else{
					text = "女";
				}
				return text;
			}
		}, {
			field : 'bloodType',
			title : '血型',
			width : '15%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.bloodType == 0) {
					text = "A";
				} else if(d.bloodType == 1){
					text = "B";
				}else if(d.bloodType == 2){
					text = "O";
				}else{
					text = "AB";
				}
				return text;
			}
		}, {
			field : 'idNo',
			title : '身份证',
			width : '15%',
			align : 'center'
		}, {
			field : 'addressx',
			title : '地址',
			width : '15%',
			align : 'center'
		}
		]];
	dataAll("refuse",refuse,null,"/refuseInfo/queryOtherRefuseInfoList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#refuse").next('.layui-table-view').find(
				'table.layui-table').eq(1);
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			$("#providerNo").val(providerNo);
			$("#id").val(id);
			loadData(providerNo);
		});
	});
	
	//查询
	$("#query").click(function(){
		var providerNo=$.trim($("#providerNo_search").val());
		layui.table.reload('refuse', {where: {"providerNo": providerNo}});
	});
	
	//确定
	$("#confirm").click(function(){
		var providerNo =$("#providerNo").val();
		if(providerNo.length<1){
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
			layer.alert('请选择淘汰原因');
			return false;
		}
		var releaseId=$("#releaseId").val();
		var id=$("#id").val();
		$.ajax({
			type : "POST",
			url : "/refuseInfo/upateOtherRefuseInfo",
			datatype : "json",
			data:{"opinion":opinion,"day":day,"eliminateReason":eliminateReason,"id":id,"isRefuse":1,"releaseId":releaseId,"providerNo":providerNo},
			success : function(data) {
				layer.alert(data.message,function(){
					location.reload();
				});
			}
		});
	});
	
	// 根据浆员卡号查询，然后拒绝
	$("#baseic_query").click(function(){
		var providerNo=$("#providerNo").val();
		$("#id").val("");
		if(providerNo.length<1){
			layer.alert('请输入浆员卡号');
		}else{
			loadData(providerNo);
		}
	});
	
	//刷新
	$("#refresh").click(function(){
		location.reload();
	});
})

function loadData(providerNo){
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
				var text = '-';
				if (bloodType == 0) {
					text = "A";
				} else if (bloodType == 1) {
					text = "B";
				} else if (bloodType == 2) {
					text = "O";
				} else {
					text = "AB";
				}
				$("#bloodType").val(text);
				$("#sex").val(sex==0 ?'男':'女');
				var type=data.data["type"];
				if(type==0){
					$("#type").val("普通");
				}else if(type==0){
					$("#type").val("普免");
				}else{
					$("#type").val("特免");
				}
				$("#idCard").attr("src",data.data["imagez"]);
			}
		},
		error:function(){
			
		}
	});
}