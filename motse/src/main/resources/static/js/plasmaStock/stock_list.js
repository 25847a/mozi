$(function(){
	initDate("#startTime");
	initCheckAdmins("#checkAdmin");
	//登记号获得光标
	$("#qallId").focus();
	var notStock = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		},{type:'checkbox', fixed: 'left'}, {
			field : 'providerNo',
			title : '献浆员卡号', 
			width : '25%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名', 
			width : '20%',
			align : 'center'
		}, {
			field : 'isSendOff',
			title : '是否送样', 
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.plasmaType==0){
					return '已送样';
				}else if(d.isSendOff==0){
					return '未送样';
				}else if(d.isSendOff==1){
					return '已送样';
				}
				return '';
			}
		}, {
			field : 'allId',
			title : '登记号', 
			width : '27%',
			align : 'center'
		}, {
			field : 'updateDate',
			title : '采浆时间', 
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd hh:mm:ss',new Date(d.updateDate));
			}
		}
		] ];
	
	var stock = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		},
		{
			field : 'providerNo',
			title : '献浆员卡号',
			width : '20%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : '20%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',sort : true, 
			width : '20%',
			align : 'center'
		}, {
			field : 'boxId',
			title : '箱号',
			width : '20%',
			align : 'center'
		}, {
			field : 'plasmaNo',
			title : '血浆编号',
			width : '20%',
			align : 'center'
		}, {
			field : 'updateDate',
			title : '入库时间',
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd hh:mm:ss',new Date(d.updateDate));
			}
		}, {
			field : 'fname',
			title : '入库人',
			width : '20%',
			align : 'center'
		}, {
			field : 'status',
			title : 'status',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}
		] ];
	
	var date=$("#startTime").val();
	var providerNo=$("#providerNo").val();
	dataAll("notStock",notStock,{"date":date,"isStorage":0,"providerNo":providerNo},"/plasmaStock/queryPlasmaStockList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#notStock").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(2).find("div").html();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(4).find("div").html();
			var time=$("#startTime").val();
			//查询基本信息和现场图片
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
			//人脸识别浆员，并查询浆员是否需要做 大体检（照胸片）
			//faceValidate();
		});
	});
	
	dataAll("stock",stock,{"date":date,"isStorage":1,"providerNo":providerNo},"/plasmaStock/queryPlasmaStockList",'','',function(){
		// 表格 tr 单击事件
		var tab = $("#stock").next().find('.layui-table tbody tr');
		//报废血浆变红
		$(tab).find('tr').each(function(){
			var html=$(this).find('td').last().find('div').html();
			if(html==1){
				$(this).css({"background":"red","color":"white"});
			}
		});
		tab.click(function(event) {
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(4).find("div").html();
			var time=$("#startTime").val();
			//查询基本信息和现场图片
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
			//人脸识别浆员，并查询浆员是否需要做 大体检（照胸片）
			//faceValidate();
		});
	});
	
	//获取浆员卡号
	$("#providerNo").keydown(function(){
		var providerNo=$("#providerNo").val();
		console.log(providerNo);
	});
	
	//form 表单提交( 自动入库 )
	$("#addStock").submit(function(){
		var providerNo=$("#providerNo").val();
		var allId=$("#qallId").val();
    	if(providerNo.length<1 && allId.length<1){
    		return false;
    	}
		$.ajax({
            type : 'POST',
            url : "/plasmaStock/addPlasmaStock",
            data:{"providerNo":providerNo,"allId":allId},
            success : function(result) {
            	parent.layer.alert(result.message,function() {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.closeAll();
                    //登记号获得光标
                	$("#qallId").val("");
                	$("#qallId").focus();
                    $("#query").click();
                    //判断箱子是否已经装满
                    if(result.finsh=="1"){
                    	//TODO 箱子装满后需要打印装箱单
                    	$("#boxId").val(result.boxId);
                    	$("#detail").click();
                    }
                    if(result.code=="-1"){
                    	//打印条码
                        $.post("/common/stock",{"allId":allId},function(result){
                        	if(result.code!=-1){
                        		layer.alert(result.message,function(){
           						 	layer.closeAll();
           					 	});
                			}
                        });
                    }
                });
                //登记号获得光标
            	$("#qallId").focus();
            },
            error : function() {
                alert("入库失败");
            }
        });
		return false;
	});
	
	//确认( 手动入库，自动入库在账面 )
	$("#confirm").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert("请选择浆员");
			return false;
		}
		$.ajax({
			type : "POST",
			url : "/plasmaStock/addPlasmaStock",
			datatype : "json",
			data:{"id":id},
			async : false,
			success : function(result) {
				if(result.code==-1) {
					 layer.alert(result.message,function(){
						 layer.closeAll();
						 $("#print").click();
						 //判断箱子是否已经装满
	                     if(result.finsh=="1"){
	                    	//TODO 箱子装满后需要打印装箱单
	                    	$("#boxId").val(result.boxId);
	                    	$("#detail").click();
	                     }
	                     $("#query").click();
					 });
				 }else{
					 layer.alert(result.message);
				 }
				//登记号获得光标
            	$("#qallId").focus();
			}
		});
	});
	
	//打印浆员血浆条码
	$("#print").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		var allId= $("#allIds").val();
		$.post("/common/stock",{"allId":allId},function(res){
			if(res.code!=-1){
				layer.alert(res.message);
			}
		});
	});
	
	//打印装箱条码
	$("#printBox").click(function(){
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印装箱清单',
				maxmin : true,
				area : ["500px","200px"],
				content : '/plasmaStock/printBox'
			})
		});
	});
	
	//打印装箱清单
	$("#detail").click(function(){
		var boxId = $("#boxId").val();
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印装箱条码',
				maxmin : true,
				area : ["100%","100%"],
				content : '/plasmaStock/printBoxDetail?boxId='+boxId
			})
		});
	});
	
	//搜索/查询
	$("#query").click(function(){
		var providerNo=$.trim($("#providerNo").val());
		var date=$("#startTime").val();
		var allId=$("#qallId").val();
		if(!/^[0-9]*$/.test(allId)){
			layer.alert('请输入正确的登记号');
			return false;
		} 
		layui.table.reload('notStock', {where: {"isStorage": 0,"date":date,"providerNo":providerNo,"allId":allId}});
		layui.table.reload('stock', {where: {"isStorage": 1,"date":date,"providerNo":providerNo,"allId":allId}});
	});
	
	//血浆报废
	$("#scrap").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.post("/plasmaStock/updatePlasmaStockScrap",{"id":id},function(data){
			if(data.code==-1){
				layer.alert(data.message,function(){
					 layer.closeAll();
					 $("#query").click();
					 // 点击确定后，还原 人脸识别的默认值，默认值 为 0
					 clearFace();
				});
			}
		});
	});
	
	//取消入库
	$("#cancel").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.post("/plasmaStock/updatePlasmaStockStatus",{"id":id},function(data){
			if(data.code==-1){
				layer.alert(data.message,function(){
					 layer.closeAll();
					 $("#query").click();
				});
			}
		});
	});
	
	/**
	 * 血浆批量报废
	 */
	$("#all_scrap").click(function(){
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '血浆报废',
				maxmin : true,
				area: ['90%', '80%'],
				content : '/plasmaStock/batchScrap'
			})
		});
	});
	
	/**
	 * 血浆留样(固定浆员)
	 */
	$("#simple").click(function(){
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '血浆标本留样记录',
				maxmin : true,
				area: ['90%', '100%'],
				content : '/plasmaStock/returnSample'
			})
		});
	});
	
	//血浆装箱单
	$("#plasma_box").click(function(){
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '血浆装箱单',
				maxmin : true,
				area: ['90%', '100%'],
				content : '/plasmaStock/printPlasmaBox'
			})
		});
	});
	
	//修改血浆重量
	$("#update_plasmaWeight").click(function(){
		var id=$("#id").val();
		var allId=$("#allId").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '修改血浆重量',
				maxmin : true,
				area: ['500px', '400px'],
				content : '/plasmCollection/updatePlasmaWeightPage?allId='+allId
			})
		});
	});
	
	//刷新页面
	$("#refush").click(function(){
		location.reload();
	});
})

function sendOff(){
	var table = layui.table;
	var checkStats = table.checkStatus('notStock');
	var data = checkStats.data;
	var ids=new Array();
	if(data.length == 0){
		layer.alert("请选择要送样的标本");
		return  false;
	}
	for(var i = 0 ; i< data.length;i++){
		ids.push(data[i].id);
	}
	var sendPerson = $("#checkAdmin option:selected").val();
	var datas ={"ids":ids, "sendPerson":sendPerson};
	$.ajax({
		  url: "/plasmaStock/updateWithSendOff",
		  type: "POST",
		  data: datas,
		  traditional: true,//这里设置为true
		  success: function(result) {
			  layer.alert(result.message,function(){
				  layer.closeAll();
				  $("#query").click();
			  });
		  }
		});
	
}

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
					var names=item.attr("name");
					item.val(data.data[names]);
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
				}else if(type==1){
					$("#type").val("普免");
				}else{
					$("#type").val("特免");
				}
				$("#age").val(getAge(data.data["idNo"]));
				$("#idCard").attr("src",data.data["imagez"]);
			}
			$("#registriesNo").val(registriesNo);
			
			$.ajax({
				type : "POST",
				url : "/plasmaStock/queryPlasmaStockById",
				datatype : "json",
				data:{"id":id},
				success : function(data) {
					if(data.code==-1){
						$("#plasmAmount").val(data.data["plasmAmount"]);
						$("#allId").val(data.data["allId"]);
						$("#plasmaNo").val(data.data["plasmaNo"]);
						$("#boxId").val(data.data["boxId"]);
						$("#allIds").val(data.data["allId"]);
					}
				}
			});
		},
		error:function(){
			
		}
	});
}

function printSendInfo(){
	 var sendTime = $("#createTime").val();
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印送样记录',
				maxmin : true,
				area : [ '100%', '100%' ],
				content : '/specimenCollection/printSendInfo?sendDate='+sendTime,
			})
		});
}