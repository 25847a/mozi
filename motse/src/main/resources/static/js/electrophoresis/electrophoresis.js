$(function(){
	//初始化时间
	initDate("#time");
	
	
	var electrophoresis = [ [ // 表头
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
			sort : true, 
			width : '18%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : '10%',
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : '8%',
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
			width : '8%',
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
			field : 'albumin',
			title : '蛋白结果',
			width : '10%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.albumin == 0) {
					text = ">=50%";
				}else{
					text = "<50%";
				}
				return text;
			}
		}, {
			field : 'atlas',
			title : '图谱',
			width : '10%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.atlas == 0) {
					text = "正常";
				}else{
					text = "不正常";
				}
				return text;
			}
		}, {
			field : 'fname',
			title : '存档人',
			width : '8%',
			align : 'center'
		}, {
			field : 'createDate',
			title : '存档时间',
			width : '10%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}
		
		] ];
	data("electrophoresis",electrophoresis,GetURLInfo() + 'querySerumElectrophoresisList','','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#electrophoresis").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(4).find("div").html();
			//查询基本信息和现场图片
			loadData(providerNo,id);
			$("#id").val(id);
		});
	});
	/**
	 * 添加
	 */
	$("#add").click(function(){
		addWithArea(GetURLInfo()+"addElectrophoresis?providerNo=null",false,[ '600px', '300px' ]);
	});
	
	/**
	 * 修改
	 */
	$("#update").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		addWithArea(GetURLInfo()+"/updateElectrophoresis?id="+id,false,[ '600px', '300px' ]);
	});
	
	//删除
	$("#delete").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.post(GetURLInfo()+"/deleteSerumElectrophoresis",{"id":id},function(result){
			if(result.code==-1) {
				 layer.alert(result.message,function(){
					 layer.closeAll();
					 location.reload();
				 });
			 }else{
				 layer.alert(result.message);
			 }
		});
	});
	
	//查询
	$("#query").click(function(){
		var time = $("#time").val();
		var providerNo=$("#providerNo_query").val();
		layui.table.reload('electrophoresis', {where: {"providerNo": providerNo, "time": time}});
	});
	// 打印
	$("#report").click(function(){
		var time = $("#time").val();
		addWithArea("/serumElectrophoresis/initReport?sendDate="+time,false,[ '100%', '100%' ]);
	});
})

/**
 * 加载浆员详细信息和现场图片和登记号
 * @param providerNo
 * @param time
 * @returns
 */
function loadData(providerNo,id){
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
					var names=item.attr("name");
					item.val(data.data[names]);
				});
				var bloodType = data.data["bloodType"];
				var sex = data.data["sex"];
				var text = getBloodValue(bloodType);
				$("#bloodType").val(text);
				$("#sex").val(sex==0 ?'男':'女');
				$("#idCard").attr("src",data.data["imagez"]);
				$("#createDate").val(dateFtt('yyyy-MM-dd',new Date(data.data.createDate)));
			}
		},
		error:function(){
			
		}
	});
}
