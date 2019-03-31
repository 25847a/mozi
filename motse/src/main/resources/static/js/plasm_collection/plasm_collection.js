var amount=0;//采浆量
var cost=0;//采浆费用
var immune=0;//特免费用
var bloodGrade=0;//血管等级
var minAmount=0;
$(function(){
	
	//采程显示和隐藏
	$("#show_caicheng").click(function(){
		if($("#caicheng").css('display')=='none'){
			$("#caicheng").css('display',"block"),
			$(this).html('关闭采程');
		}else{
			$("#caicheng").css('display',"none"),
			$(this).html('显示采程');
		}
	});
	
	initDateByHHmm("collectionStart");
	initDateByHHmm("collectionEnd");
	
	//加载采浆量配置
	$.post('/config/queryDictListByType',{"type":"plasma"},function(res){
		if(res.code==-1){
			$.each(res.data,function(index,item){
				if(item.lable=='amount'){
					amount = item.value;
					$("#plasmAmount").val(amount);
				}else if(item.lable=='cost'){
					cost = item.value;
				}else if(item.lable=='immune'){
					immune = item.value;
				}else if(item.lable=='minAmount'){
					minAmount=item.value;
				}
				
				$("#plasmAmount").blur(function(){
					var val = $(this).val();
					if(val < minAmount){
						$("#isAmple").val('1');
					}else{
						$("#isAmple").val('0');
					}
				});
			});
		}
	});
	
	//点击身份证显示正反面
	$("#demo1").click(function(){
		var providerNo=$("#providerNo").val();
		loadIdCardImage(providerNo);
	});
	//初始化时间
	initDate("#startTime");
	
	//加载采浆情况设置
	$.ajax({
		type : "POST",
		url : "/situation/querySituationConfigList",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#situation").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
	//加载耗材模板
	$.ajax({
		type : "POST",
		url : "/template/queryPulpingTemplate",
		datatype : "json",
		data:{"apply":0},
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#suppliesId").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
	//加载采浆护士列表
	$.ajax({
		type : "POST",
		url : "/plasmCollection/queryCollectionAdminList",
		datatype : "json",
		data:{"apply":0},
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#userId").append('<option value="'+item.id+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	
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
	
	//加载机型数据
	$("#roomId").change(function(){
		loadRoom('');
	});
	$("#typeId").change(function(){
		loadTypeId(null);
	});
	 
	
	var notColl = [ [ // 表头
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
			width : '25%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',
			width : '30%',sort : true, 
			align : 'center'
		}, {
			field : 'plasmaType',
			title : '固定浆员',
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.plasmaType==1){
					return '固定';
				}else{
					return '非固定';
				}
			}
		}] ];

	var coll = [ [ // 表头
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
			width : '25%',
			align : 'center'
		}, {
			field : 'allId',
			title : '登记号',sort : true, 
			width : '30%',
			align : 'center'
		}, {
			field : 'plasmaType',
			title : '固定浆员',
			width : '20%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.plasmaType==1){
					return '固定';
				}else{
					return '非固定';
				}
			}
		}
		] ];
	var date=$("#startTime").val();
	dataAll("notColl",notColl,{"startDate":date+" 00:00:00","endDate":date+" 23:59:59","isCollection":0},"/plasmCollection/queryPlasmCollectionList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#notColl").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(3).find("div").html();
			var time=$("#startTime").val();
			//查询基本信息和现场图片
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
			//更新状态为未采集
			$("#result").val('');
			//设置采浆费用
			$("#money").val(cost);
			//查询浆员采浆费用
			$("#cose_detail tbody").eq(0).html('');
			costDetail(registriesNo);
			
		});
	});
	
	dataAll("coll",coll,{"startDate":date+" 00:00:00","endDate":date+" 23:59:59","isCollection":1},"/plasmCollection/queryPlasmCollectionList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#coll").next().find('.layui-table tbody tr');
		tab.click(function(event) {
			
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(3).find("div").html();
			var time=$("#startTime").val();
			//查询基本信息和现场图片
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
			//更新状态为未采集
			$("#isCollection").val('1');
			costDetail(registriesNo);
		});
	});
	
	//确定
	$("#confirm").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert("请选择浆员");
			return false;
		}
		var result = $("#result").val();
		//先判断有没有人脸识别成功 
		/*if(result.length<1){
			layer.alert('请先识别浆员');
			return false;
		}*/
		var sub=true;
		$("#addColl .sub_coll input,select").each(function(index,it){
			var item=$(this);
			if(index==1){
				return true;
			}
			var tip=item.attr("tip");
			var val=item.val();
			var id=item.attr("id");
			if(id!="remarks" && id!="id" && val.length<1){
				layer.alert(tip);
				sub=false;
				return false;
			}
		});
		if(sub){
			var grade = $("#bloodGrade").val();
			if(grade!=bloodGrade){
				$("#sub_bloodGrade").val(grade);
			}
			var plasmAmount=$("#plasmAmount").val();
			if(plasmAmount>amount){
				layer.alert('采浆量最高不能超过 '+amount);
				return false;
			}
			var remarks=$("#remarks").val();
			if(plasmAmount < minAmount && remarks.length <1){
				layer.alert('浆量不足'+minAmount+'，请输入备注');
				return false;
			}
			$("#valdationType").val(result);
			$("#isIdentity").val(1);
			
			//开采时间
			var collectionStart=$("#collectionStart").val();
			if(collectionStart.length<1){
				layer.alert("请输入开采时间");
				return false;
			}
			var collectionEnd=$("#collectionEnd").val();
			if(collectionEnd.length<1){
				layer.alert("请输入结束时间");
				return false;
			}
			
			var  params = [];  
			$(".collection").each(function(index,item){
				var it = $(this).find("input");
		        params.push({"num":(index+1),"speed":$(it[0]).val(),"amount":$(it[1]).val(),"returnSpeed":$(it[2]).val()});  
			});
			var json = JSON.stringify(params);//采程详情
			$("#detail").val(json);
			
			var ij = 0;
			$("#plasma_process .collection").each(function(index,it){
				var item = $(this);
				var inputs = $(item).find("input");
				var in1 = $(inputs[0]).val();
				var in2 = $(inputs[1]).val();
				var in3 = $(inputs[2]).val();
				if(in1.length>0){
					if(in2.length<1 || in3.length<1){
						layer.alert("请填充完数据");
						sub = false;
						return false;
					}else{
						if(index!=0){
							ij++;
						}
					}
					if(index!=0 && ij!=index){
						layer.alert("请按顺序填充数据");
						sub = false;
						return false;
					}
				}else{
					ij--;
				}
			});
			if(!sub){
				return false;
			}
			var data=$("#addColl").serialize();
			
			$.ajax({
				type : "post",
				url : '/plasmCollection/updatePlasmCollection',
				data : data,
				async : false,
				success : function(result) {
					if(result.code==-1) {
						 layer.alert(result.message);
						 $("#demo1,#imageFace_show").attr('src','../../../img/new_pa1.png');
						 $("#sub_coll input,select").val("");
						 $("#plasma_process input,select").val("");
						 $(".col-xs-12").eq(0).find("input,select").val("");
						 $("#query").click();
						 //免疫验证是都需要注射
						 verifying($("#providerNo").val());
					 }else{
						 layer.alert(result.message);
					 }
				}
			}); 
		}
	});
	
	//查询
	$("#query").click(function(){
		var providerNo=$.trim($("#providerNo_search").val());
		var allId=$.trim($("#registriesNo_search").val());
		if(!/^[0-9]*$/.test(allId)){
			layer.alert('请输入正确的登记号');
			return false;
		} 
		var date=$("#startTime").val();
		layui.table.reload('notColl', {where: {"providerNo": providerNo, "allId": allId,"startDate":date+" 00:00:00","endDate":date+" 23:59:59",isNew:1}});
		layui.table.reload('coll', {where: {"providerNo": providerNo, "allId": allId,"startDate":date+" 00:00:00","endDate":date+" 23:59:59",isNew:1}});
	});
	//刷新
	$("#refresh").click(function(){
		/*var username=$("#userName").val();
		var userId=$("#userId").val();
		$(".sub_coll input").val("");
		$(".sub_coll select").find('option:eq(0)').attr('selected','selected');
		$("#name").val("");
		$("#sex").val("");
		$("#type").val("");
		$("#bloodType").val("");
		$("#idCard").attr("src","../../../img/new_pa2.png");
		$("#roomId").change();
		$("#userName").val(username);
		$("#userId").val(userId);*/
		location.reload();
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
})///////////////

/**
 * 计算总采浆费用
 * @returns
 */
function total(){
	var sum=0;
	$("#cose_detail tbody").eq(0).find('tr').each(function(index,item){
		var money = $(item).find('td').eq(1).html();
		sum+=parseInt($(item).find('td').eq(1).html());
	});
	$("#cose_detail tbody").eq(1).find('td').eq(1).html(sum);
}
function costDetail(allId){
		$.post('/plasmCollection/queryNextCollectionCost',{"allId":allId},function(res){
			if(res.code==-1){
				var tr='<tr>';
				tr+='<td>采浆费用</td>';
				tr+='<td>'+$("#money").val()+'</td>';
				tr+='</tr>';
				tr+='<td>路费</td>';
				tr+='<td>'+res.roadFee+'</td>';
				tr+='</tr>'; 
				if(null!=res.data){
					$.each(res.data,function(index,item){
						tr+='<tr>';
						tr+='<td>'+item.name+'</td>';
						tr+='<td>'+item.money+'</td>';
						tr+='</tr>';
					});
				}
				$("#cose_detail tbody").eq(0).html(tr);
				total();
			}
			
			//改变采浆费用后，更新总采浆费用
			$("#money").blur(function(){
				var tr= $("#cose_detail tbody").eq(0).find('tr').eq(0);
				var money = $(this).val();
				$(tr).find('td').eq(1).html(money);
				total();
			});
		});
	}
//读取浆员卡号/请求身份证接口
function read(){
	var date=$("#startTime").val();//时间
	$.ajax({
		type : "POST",
		url :"/common/readProviderNoInfo",
		datatype : "json",
		success : function(result) {
			if (result.code==-1) {
				$.ajax({
					type : "POST",
					url :"queryDetailedCollectionDu",
					data:{"providerNo": result.data.code,"updateDate":date},
					datatype : "json",
					success : function(result) {
						if(null!=result.data){
							if(result.data.isCollection==0){
								layui.table.reload('notColl', {where: {"startDate":date+" 00:00:00","endDate":date+" 23:59:59","isCollection":0,"providerNo":result.data.providerNo}});
								$('#domne tbody tr').css('background-color','#b6dcbe');
								$.post(GetURLInfo()+"queryCollectionHeadInfo",{"providerNo":result.data.providerNo,"isCollection":0,"allId":result.data.allId},function(res){
									if(res.code==-1 && null!=res.data){
										loadData(res.data.providerNo,date,res.data.id,res.data.allId);
										$("#id").val(res.data.id);
										$("#result").val('');//更新状态为未采集
										$("#money").val(cost);//设置采浆费用
										$("#cose_detail tbody").eq(0).html('');//查询浆员采浆费用
										costDetail(res.data.allId);
									}else{
										parent.layer.alert('请刷新页面重新读取浆员卡',function(){
							   				parent.layer.closeAll();
							   			});
									}
								});
							}else{
								parent.layer.alert('该浆员今天已采浆',function(){
					   				parent.layer.closeAll();
					   			});
							}
						}else{
							parent.layer.alert('今天该流程没有此浆员记录',function(){
				   				parent.layer.closeAll();
				   			});
						}
					}
				});
				
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


//免疫验证是否需要注射
function verifying(providerNo){
	$.ajax({
		type:'post',
		url:GetURLInfo()+'/collectionAfterImmune',
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
						content: '/plasmCollection/immuneCollection?message='+result.message
					})
				});
			}
		},
		error:function(){
			parent.layer.alert("操作失败",function(){
				parent.layer.closeAll();
			});
		}
	});
}

function loadRoom(id){
	var roomId=$("#roomId").val();
	//加载浆站采室
	$.ajax({
		type : "POST",
		url : "/room/queryPlasmTypeByMachine",
		datatype : "json",
		data:{"roomId":roomId},
		async:false, 
		success : function(data) {
			if(data.code==-1){
				$("#typeId").empty();
				$("#typeId").append('<option value="">请选择</option>');
				$("#machineId").empty();
				$("#machineId").append('<option value="">请选择</option>');
				$.each(data.data,function(index,item){
					if(item.id==id){
						$("#typeId").append('<option selected="selected" value="'+item.id+'">'+item.name+'</option>');
					}else{
						$("#typeId").append('<option value="'+item.id+'">'+item.name+'</option>');
					}
				});
			}
		},
		error:function(){
			
		}
	});
}

function loadTypeId(ids){
	//加载机号数据
	var plasmTypeId=$("#typeId").val();
	if(plasmTypeId.length<1){
		plasmTypeId=ids[0];
	}
	//加载浆站采室
	$.ajax({
		type : "POST",
		url : "/room/queryMachineNumberById",
		datatype : "json",
		async:false, 
		data:{"plasmTypeId":plasmTypeId},
		success : function(data) {
			if(data.code==-1){
				$("#machineId").empty();
				$("#machineId").append('<option value="">请选择</option>');
				$.each(data.data,function(index,item){
					if(null!=ids){
						if(item.id==ids[1]){
							$("#machineId").append('<option selected="selected" value="'+item.id+'">'+item.name+'</option>');
						}else{
							$("#machineId").append('<option value="'+item.id+'">'+item.name+'</option>');
						}
					}else{
						$("#machineId").append('<option value="'+item.id+'">'+item.name+'</option>');
					}
				});
			}
		},
		error:function(){
			
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
				$("#name").val(data.data["name"]);
				$("#year").val(data.data["year"]);
				$("#month").val(data.data["month"]);
				$("#icNumber").val(data.data["icNumber"]);
				$("#providerNo").val(data.data["providerNo"]);
				$("#demo1").attr("src",data.data["imagez"]);
				bloodGrade=data.data["bloodGrade"];
				$("#bloodGrade").val(bloodGrade);
			}
			$("#registriesNo").val(registriesNo);
		},
		error:function(){
			
		}
	});
	
	//获取采浆详情
	$.ajax({
		type : "POST",
		url : "/plasmCollection/queryPlasmCollectionById",
		datatype : "json",
		data:{"id":id},
		async:false, 
		success : function(data) {
			$(".sub_coll input,select").each(function(){
				var item=$(this);
				var name=item.attr('name');
				var val = data.data[name];
				if(!(null==val || val.length<1)){
					if(name=='typeId'){
						loadRoom(val);
					}else if(name=='machineId'){
						var ids=new Array();
						ids[0]=data.data["typeId"];
						ids[1]=val;
						loadTypeId(ids);
					}else{
						item.val(data.data[name]);
					}
				}
			});
			$("#collectionStart").val(data.data.collectionStart);
			$("#collectionEnd").val(data.data.collectionEnd);
			$("#print").val('0');
			$("#imageFace_show").attr("src",data.data.imgUrl);
		}
	});
	
	//获取现场照片
	$.ajax({
		type : "POST",
		url : "/plasmaImage/queryPlasmaImageInfo",
		datatype : "json",
		data:{"providerNo":providerNo,"time":time},
		success : function(data) {
			if(data.code==-1){
				if(null!=data.data){
					$("#imageFace_show").attr("src",data.data.imgUrl);
				}else{
					$("#imageFace_show").attr("src",'../../../img/new_pa1.png');
				}
			}
		},
		error:function(){
			
		}
	});
	
	$.post('/plasmCollectionDetail/queryPlasmCollectionDetailListByCollectionId',{"id":id},function(res){
		if(res.code==-1){
			$(".collection").each(function(index,item){
				var it = $(this).find("input");
				var num=index+1;
		        for(var i=0;i<res.data.length;i++){
					var is=res.data[i];
					if(num==is.num){
						$(it[0]).val(is.speed);
						$(it[1]).val(is.amount);
						$(it[2]).val(is.returnSpeed);
					}
				}
			});
		}
	});
}

function initDateByHHmm(id){
	var time = dateFtt('hh:mm',new Date());
	layui.use('laydate', function(){
		var laydate = layui.laydate;
		var laydateS = layui.laydate;
		//单控件
		laydate.render({
			elem: '#'+id
			,format: 'HH:mm'
			,istime: true
			,type: 'time'
			,value:time
			,ready: function(date){
				console.log(date);
			}
			,done: function(value, date, endDate){
				console.log(value, date, endDate);
			}
		});
	});
}