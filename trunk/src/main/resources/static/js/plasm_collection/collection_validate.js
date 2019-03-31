$(function(){
	
	//人工通过
	$("#people").click(function(){
		$("#result").val('2');
		$("#valdationType").val('2')
	});
	
	//点击身份证显示正反面
	$("#demo1").click(function(){
		var providerNo=$("#providerNo").val();
		loadIdCardImage(providerNo);
	});
	initDate("#startTime");
	
	var notValidate = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		},{
			field : 'providerNo',
			title : '献浆员卡号',
			align : 'center',
			width : '25%'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '15%'
		}, {
			field : 'allId',
			title : '登记号',sort : true, 
			align : 'center',
			width : '25%'
		}, {
			field : 'phone',
			title : '手机号码',
			align : 'center',
			width : '25%'
		}, {
			field : 'assayDate',
			title : '化验日期',
			align : 'center',
			width : '20%',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.assayDate){
					return dateFtt('yyyy-MM-dd',new Date(d.assayDate)); 
				}
				return '';
			}
		}, {
			field : 'plasmaType',
			title : '固定浆员',
			align : 'center',
			width : '20%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.plasmaType == 0) {
					text = "非固定";
				}else if(d.plasmaType == 1){
					text = "固定";
				}
				return text;
			}
		} 
		] ];
	var validate = [ [ // 表头
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
			align : 'center',
			width : '20%'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '15%'
		}, {
			field : 'allId',
			title : '登记号',sort : true, 
			align : 'center',
			width : '25%'
		}, {
			field : 'assayDate',
			title : '化验日期',
			align : 'center',
			width : '20%',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.assayDate){
					return dateFtt('yyyy-MM-dd',new Date(d.assayDate)); 
				}
				return '';
			}
		}, {
			field : 'plasmaType',
			title : '固定浆员',
			align : 'center',
			width : '20%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.plasmaType == 0) {
					text = "非固定";
				}else if(d.plasmaType == 1){
					text = "固定";
				}
				return text;
			}
		}
		] ];
	var date=$("#startTime").val();
	var providerNo=$("#registriesNo_search").val();
	//未喊号人员列表
	dataAll("notValidate",notValidate,{"assayDate":date+" 00:00:00","endDate":date+" 23:59:59","status":0,"providerNo":providerNo},"/plasmYell/queryplasmaYellList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#notValidate").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			if(providerNo.indexOf("f")>-1){
				parent.layer.alert("临时浆员请发卡再进行验证采浆",function(){
					parent.layer.closeAll();
				});
			}else{
				$("#providerNo").val(providerNo);
				var time=$("#startTime").val();
				//获取登记号
				var registriesNo=$(tr).find("td").eq(3).find("div").html();
				loadData(providerNo,time,id,registriesNo);
				$("#id").val(id);
				immunity(providerNo);//验证浆员的免疫流程
			}
			
		});
	});
	
	//已喊号人员列表
	dataAll("validate",validate,{"startDate":date+" 00:00:00","endDate":date+" 23:59:59","status":1,"providerNo":providerNo},"/plasmYell/queryplasmaYellList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#validate").next('.layui-table-view').find(
				'table.layui-table').eq(1);
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			$("#providerNo").val(providerNo);
			var time=$("#startTime").val();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(3).find("div").html();
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
		});
	});
	
	
	//人脸识别
	$("#newfacial").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		identityValidate();
	});
	
	//确定提交
	$("#confirm").click(function(){
		var number = $("#number").val();
		if(number.length==1){
			loadResult();
		}else{
			$.ajax({
				type:'post',
				url:GetURLInfo()+'verifyingImmunity',
				data:{
					"providerNo":$("#providerNo").val()
				},
				dataType:'json',
				async : false,
				success:function(result){
					if(result.code==1){
						parent.layer.alert("请操作完免疫流程",function(){
		       				parent.layer.closeAll();
		       			});
					}else{
						//确认键///////////////////////////////////
						loadResult();
					}
				},
				error:function(){
					parent.layer.alert("操作失败",function(){
	       				parent.layer.closeAll();
	       			});
				}
			});
		}
		
		
	});
	
	//取消喊号
	$("#cancel").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		} 
		$.post("/plasmYell/cancelPlasmYell",{"id":id},function(result){
			layer.alert(result.message,function(){
				clearFace();
				if(result.code==-1){
					layer.closeAll();
					var date = $("#startTime").val();
					layui.table.reload('notValidate', {where: {"date":date,"status":0}});
					layui.table.reload('validate', {where: {"date":date,"status":1}});
				}else{
					layer.closeAll();
				}
			});
		});
	});
	
	//刷新
	$("#refresh").click(function(){
		/*$("#basic input").val("");
		$("#image").attr("src",'../../../img/new_pa1.png');*/
		location.reload();
	});
	
	//查询
	$("#query").click(function(){
		var date = $("#startTime").val();
		var providerNo=$("#providerNo_search").val();
		var allId=$.trim($("#registriesNo_search").val());
		//化验天数
		var day = $("#day").val();
		var startDate=date+" 00:00:00";
		var assayDate=date+" 00:00:00";
		if(day.length>0){
			var sdate = new Date(date);
			sdate.setDate(sdate.getDate()-day);
			var ff = dateFtt('yyyy-MM-dd',sdate);
			assayDate = ff+' 00:00:00';
		}
		if(!/^[0-9]*$/.test(allId)){
			layer.alert('请输入正确的登记号');
			return false;
		} 
		layui.table.reload('notValidate', {where: {"startDate":startDate,"endDate":date+" 23:59:59","status":0,"providerNo":providerNo,"allId": allId,"assayDate":assayDate}});
		layui.table.reload('validate', {where: {"startDate":startDate,"endDate":date+" 23:59:59","status":1,"providerNo":providerNo,"allId": allId}});
	});
	
	//打印
	$("#print").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		} 
		//采浆验证打印
		var allId = $("#allId").val();
		var isSerum=$("#isSerum").val();
		$.post("/common/yell",{"allId":allId,"isSerum":isSerum},function(res){
			if(res.code!=-1){
				layer.alert(res.message);
			}
		});
	});
})
//确认键///////////////////////////////////
function immunity(providerNo){
	$.ajax({
		type:'post',
		url:GetURLInfo()+'verifyingImmunity',
		data:{
			"providerNo":providerNo
		},
		dataType:'json',
		success:function(result){
			if(result.code==1){
				layer.ready(function() {
					layer.open({
						type: 2,
						title: '免疫',
						closeBtn:0,
						maxmin: true,
						area: ['400px', '170px'],
						content: '/plasmYell/immuneDetails?message='+result.message
					})
				});
			}else{
				$("#number").val(0);
			}
		},
		error:function(){
		}
	});
}
function read(){
	var date=$("#startTime").val();//时间
	//读取请求身份证接口
	$.ajax({
		type : "POST",
		url :"/common/readProviderNoInfo",
		datatype : "json",
		success : function(result) {
			if (result.code==-1) {
				if(result.data.code.indexOf("f")>-1){
					parent.layer.alert("临时浆员请发卡再进行验证采浆",function(){
						parent.layer.closeAll();
					});
					return false;
				}else{
					$.ajax({
						type : "POST",
						url :GetURLInfo()+"/queryPlasmYellInfoDu",
						data:{"providerNo":result.data.code,"updateDate":date},
						datatype : "json",
						success : function(result) {
							if(null!=result.data){
								if(result.data.status==0){
									layui.table.reload('notValidate', {where: {"startDate":date+" 00:00:00","endDate":date+" 23:59:59","status":0,"providerNo":result.data.providerNo}});
									$('#domne tbody tr').css('background-color','#b6dcbe');
									$.post(GetURLInfo()+"queryValidateHeadInfo",{"providerNo":result.data.providerNo,"allId":result.data.allId},function(res){
										if(res.code==-1 && null!=res.data){
											//id,获取浆员卡号,获取登记号registriesNo
												$("#providerNo").val(res.data.providerNo);
												loadData(res.data.providerNo,date,res.data.id,res.data.allId);
												$("#id").val(res.data.id);
												immunity(res.data.providerNo);//验证浆员的免疫流程
										}else{
											parent.layer.alert('请刷新页面重新读取浆员卡',function(){
								   				parent.layer.closeAll();
								   			});
										}
									});
								}else{
									parent.layer.alert('该浆员今天已采浆验证',function(){
						   				parent.layer.closeAll();
						   			});
								}
							}else{
								parent.layer.alert('今天该流程没有此浆员记录',function(){
					   				parent.layer.closeAll();
					   			});
							}
						},
						error : function() {
							parent.layer.alert("进入错误，请重试",function(){
				   				parent.layer.closeAll();
				   			});
						}
					});
				}
			} else if(result.code==-3){
				parent.layer.alert('读浆员卡失败,请调整浆员卡位置！',function(){
	   				parent.layer.closeAll();
	   			});
			}else if(result.code==1){
				parent.layer.alert('建立驱动连接失败,请确定驱动是否启动',function(){
	   				parent.layer.closeAll();
	   			});
			}
		},
		error : function() {
			parent.layer.alert("进入错误，请重试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
}

function loadResult(){
	var id=$("#id").val();
	if(id.length<1){
		layer.alert('请选择浆员');
		return false;
	} 
	var result=$("#result").val();
	if(result.length<1){
		layer.alert('请先面部识别浆员');
		return false;
	}
	var type=$("#valdationType").val();
	var imageFace=$("#imageFace").val();
	var isIdentity=1;
	$.ajax({
		type : "POST",
		url : "/plasmYell/updatePlasmYell",
		data:{"id":id,"isIdentity":isIdentity,"type":type,"imageFace":imageFace},
		async : false,
		success : function(result) {
			layer.alert(result.message,function(){
				if(result.code==-1){
					clearFace();
					$("#isSerum").val(result.isSerum==true ? 1:2);
					layer.closeAll();
					$("#print").click();
					location.reload();
				}else{
					layer.closeAll();
				}
			});
		}
	});
}

/**
 * 加载浆员详细信息和现场图片和登记号
 * @param providerNo
 * @param time
 * @returns
 */
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
					if(typeof(names)!=='undefined' && names!='id'){
						item.val(data.data[names]);
					}
				});
				var bloodType = data.data["bloodType"];
				var sex = data.data["sex"];
				$("#bloodType").val(getBloodValue(bloodType));
				$("#sex").val(getSexValue(sex));
				var type=data.data["type"];
				$("#type").val(getPbTypeValue(type));//获取浆员类型
				$("#age").val(getAge(data.data["idNo"]));
				$("#demo1").attr("src",data.data["imagez"]);
			}
			$("#allId").val(registriesNo);
			$("#demo3").attr('src',data.data["photo"]);
			$.post("/immuneAssay/queryImmuneAssayLast",{"providerNo":providerNo},function(res){
				if(null!=res.data){
					if(null!=res.data.effectiveValue){
						$("#effectiveValue").val(res.data.effectiveValue);
						$("#effectiveDate").val(dateFtt('yyyy-MM-dd',new Date(res.data.createDate)));
					}
				}
			});
			
			$.post("/plasmaImage/queryPlasmaImageInfo",{"providerNo":providerNo,"allId":registriesNo},function(res){
				if(res.code==-1){
					if(null!=res.data){
						$("#imageFace_show").attr("src",res.data.imgUrl);
					}else{
						$("#imageFace_show").attr("src",'../../../img/new_pa1.png');
					}
				}
			});
		},
		error:function(){
			
		}
	});
}

function clearFace(){
	$("#isIdentity").val("0");
}

//验证成功后，直接提交
function submit(){
	$("#confirm").click();
}