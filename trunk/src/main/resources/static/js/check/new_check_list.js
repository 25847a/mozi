$(function(){
	
	//体检结果为合格，淘汰原因为空 
	$("#check_result").change(function(){
		var res = $(this).val();
		if(res==0){
			$("#isRoadFee").val("");
			$("#reason").val("");
			$("#cost_div").hide();
			$("#finalResult").val("");
		}else{
			$("#finalResult").val(1);
			$("#cost_div").show();
		}
	});
	
	//点击身份证显示正反面
	$("#demo1").click(function(){
		var providerNo=$("#providerNo").val();
		loadIdCardImage(providerNo);
	});
	
	// 获取淘汰原因列表
	$.ajax({
		type : "POST",
		url : "/disease/queryDiseaseInfo",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#reason").append('<option value="'+item.name+'">'+item.name+'</option>');
				});
			}
		},
		error:function(){
			
		}
	});
	initDate("#startTime");
	
	var check = [ [ // 表头
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
			width : '25%',
			sort: true,
			align : 'center'
		}, {
			field : 'fname',
			title : '体检医生',
			width : '25%',
			align : 'center'
		}, {
			field : 'result',
			title : '体检结果',
			width : '25%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.result==0){
					return '合格';
				}else{
					return '不合格';
				}
			}
		}, {
			field : 'reason',
			width : '25%',
			title : '淘汰原因',
			align : 'center'
		} 
		
		] ];
	
	var notCheck = [ [ // 表头
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
			width : '25%',
			sort: true,
			align : 'center'
		}, {
			field : 'bloodType',
			width : '25%',
			title : '血型',
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
		} ] ];
	//体检人员列表
	var date=$("#startTime").val();
	dataAll("notCheck",notCheck,{"date":date,"isNew":0,"isCheck":0},"/check/queryNewCheckList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#notCheck").next('.layui-table-view').find(
				'table.layui-table').eq(1);
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			clearFace();
			$("#rabat").val("");
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			$("#providerNo").val(providerNo);
			var time=$("#startTime").val();
			//获取登记号
			var registriesNo=$(tr).find("td").eq(3).find("div").html();
			$("#allId").val(registriesNo);
			//查询上次体检信息
			/*$.post("/check/queryCheckById",{"providerNo":providerNo,"isCheck":"0"},function(res){
				if(res.code==-1 && null!=res.data){
					$("#xya").val(res.data.xya);
					$("#xyb").val(res.data.xyb);
					$("#tz").val(res.data.tz);
					$("#tw").val(res.data.tw);
					$("#mb").val(res.data.mb);
				}
			});*/
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
			chest(providerNo);
		});
	});
	//已体检人员列表
	dataAll("check",check,{"date":date,"isNew":0,"isCheck":1},"/check/queryNewCheckList",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		var tab = $("#check").next().find('.layui-table tbody tr');
		$.each($(tab).find("tr"),function(index,item){
			var td = $(item).find("td").last().find("div").html();
			if(null!=td && td.length>0){
				if(td==0){
					$(item).css({"background":"#4f4fd2","color":"#FFF"});
				}else if(td==1){
					$(item).css({"background":"red","color":"#FFF"});
				}
			} 
		})
		tab.click(function(event) {
			// 每次点击，还原 人脸识别的默认值，默认值 为 0
			clearFace();
			$("#rabat").val("");
			var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();
			//获取浆员卡号
			var providerNo=$(tr).find("td").eq(1).find("div").html();
			$("#providerNo").val(providerNo);
			//获取登记号
			var registriesNo=$(tr).find("td").eq(3).find("div").html();
			var time=$("#startTime").val();
			$("#allId").val(registriesNo);
			$.post("/check/queryCheckById",{"id":id,"isCheck":"1"},function(res){
				$("#addCheck input,select").each(function(){
					var name=$(this).attr("name");
					if(name!="userId" && name!='userName'){
						$(this).val(res.data[name]);
					}
				})
			});
			//查询基本信息和现场图片
			loadData(providerNo,time,id,registriesNo);
			$("#id").val(id);
		});
	});
	
})

$(function(){
	//搜索
	$("#query").click(function(){
		var providerNo=$.trim($("#providerNo_search").val());
		var allId=$.trim($("#registriesNo_search").val());
		if(!/^[0-9]*$/.test(allId)){
			layer.alert('请输入正确的登记号');
			return false;
		}
		var date=$("#startTime").val();
		layui.table.reload('notCheck', {page: {curr:1},where: {"providerNo": providerNo, "allId": allId,"date":date,isNew:0}});
		layui.table.reload('check', {page: {curr:1},where: {"providerNo": providerNo, "allId": allId,"date":date,isNew:0}});
	});
	
	//刷新
	$("#refresh").click(function(){
		/*$("#addCheck input,select").each(function(){
			var name=$(this).attr("name");
			if(name!="userId" && name!='userName'){
				$(this).val("");
			}
		})
		$("#basic input,select").each(function(){
			var name=$(this).attr("name");
			$(this).val("");
		})
		$("#addCheck select").val(0);
		$("#idCard").attr("src","../../../img/new_pa1.png");
		$("#imageFace_show").attr("src","../../../img/new_pa2.png");*/
		location.reload();
	});
	
	//面部识别
	$("#face").click(function(){
		faceValidate();
	});
	
	//提交体检结果
	$("#confirm").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		//先判断有没有人脸识别成功 
		/*var result = $("#result").val();
		if(result.length<1){
			layer.alert('请先识别浆员');
			return false;
		}*/
		// 获取浆员编号
		var providerNo=$("#providerNo").val();
		/*var res = chest(providerNo);
		if(!res){
			return false;
		}*/
		var res=true;
		var check_result = $("#check_result").val();
		var reason = $("#reason").val();
		if(check_result==1){
			if(reason=='请选择' || reason.length<1){
				layer.alert("请选择淘汰原因");
				return false;
			}
		}
		$("#addCheck input,select").each(function(){
			var item=$(this);
			if(!item.hasClass("not")){
				var val = item.val();
				var name=item.attr("name");
				var tip = item.attr("data-tip");
				if(null!=val){
					if(name=="tw" || name=="tz"){
						var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
						if(!reg.test(val)){
							res=false;
							layer.alert(tip);
							return false;
						}
					}else if(val.length<1){
						res=false;
						layer.alert(tip);
						return false;
					}
				}
			}
		});
		if(res){
			var check_result = $("#check_result").val();
			if(check_result==1){
				var isRoadFee=$("#isRoadFee").val();
				if(isRoadFee.length<1){
					layer.alert("请选择是否发放路费");
					return false;
				}
			}
			var data=$("#addCheck").serialize();
			$.ajax({
				type : "post",
				url : GetURLInfo() + "updateChcekInfo",
				data : data,
				async : false,
				success : function(result) {
					if(result.code==-1) {
						 layer.alert(result.message,function(){
							 layer.closeAll();
							 $("#query").click();
							 // 点击确定后，还原 人脸识别的默认值，默认值 为 0
							 location.reload();
						 });
					 }else{
						 layer.alert(result.message);
					 }
				}
			}); 
		}
	});
	
	//面部识别
	$("#newfacial").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		var providerNo=$("#providerNo").val();
		chest(providerNo);
	});
	
	//取消体检
	$("#cacal").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.post("/check/cacalCheckInfo",{"id":id},function(result){
			 if(result.code==-1) {
				 layer.alert(result.message,function(){
					 layer.closeAll();
					 $("#query").click();
				 });
			 }else{
				 layer.alert(result.message);
			 }
		});
	});
})

/**
 * 是否需要做胸片
 * @returns
 */
function chest(providerNo){
	var res=true;
	//查询该浆员是否做过胸片
	$.ajax({
		type : "POST",
		url : "/rabatinfo/queryProviderLastTime",
		datatype : "json",
		async:false,
		data:{"providerNo":providerNo},
		success : function(data) {
			if(data.code==-1){
				if(null==data.data  || data.data.time > 0){
					/*layer.confirm('该浆员需要做大体检，请确定是否大体检', {
						  btn: ['是','否'] //按钮
						}, function(){
							layer.closeAll();
							//需要上传胸片
							layer.open({
								type: 2,
								title: 'X光胸片检查录入',
								maxmin: true,
								area: ['800px', '550px'],
								content: "/rabatinfo/rabat?providerNo="+providerNo
							})	
							res= false;
						}, function(){
							layer.closeAll();
						});*/
					layer.alert('该浆员需要做大体检，请上传胸片后再体检');
				}
			}
		}
	});
	return res;
}

/**
 * 文本框只能输入数字和小数点
 * @param obj
 * @returns
 */
function clearNoNum(obj){ 
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的  
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数  
    if(obj.value.indexOf(".")< 0 && obj.value !=""){//以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额 
        obj.value= parseFloat(obj.value); 
    } 
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
				$("#demo1").attr("src",data.data["imagez"]);
			}
			$("#registriesNo").val(registriesNo);
		},
		error:function(){
			
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
					$("#imageFace_show").attr("src",'../../../img/new_pa2.png');
				}
			}
		},
		error:function(){
			
		}
	});
	
	var allId = $("#allId").val();
	//获取胸片结果
	$.ajax({
		type : "POST",
		url : "/rabatinfo/queryRabationInfoByAllId",
		datatype : "json",
		data:{"allId":allId},
		success : function(data) {
			if(data.code==-1 && data.data.isCheck==1){
				$("#rabat").val(data.data.result);
				if(data.data.result==1){
					$("#finalResult").val(1);
					$("#cost_div").show();
				}else{
					$("#cost_div").hide();
				}
			}
		},
		error:function(){
			
		}
	});
}

/**
 * 男女体重判断
 * @param obj
 * @returns
 */
function tzBlur(obj){
	var val = obj.value;
	if(val.length>0){
		var sex=$("#sex").val();
		if(sex=='男'){
			if(val<50){
				layer.alert("体重输入不正确");
				$("#tz").addClass("tzcss");
			}else{
				$("#tz").removeClass("tzcss");
			}
		}else{
			if(val<45){
				layer.alert("体重输入不正确");
				$("#tz").addClass("tzcss");
			}else{
				$("#tz").removeClass("tzcss");
			}
		}
	}
}

/**
 * 血压判断
 * @param obj
 * @returns
 */
function xyBlur(obj,type){
	var val = obj.value;
	var min=0;
	var max=0;
	if(type=='a'){
		min=12;
		max=18.7;
	}else{
		min=8;
		max=12;
	}
	if(val.length>0){
		if(!(val>=min && val<=max)){
			$(obj).addClass("tzcss");
		}else{
			$(obj).removeClass("tzcss");
		}
	}
}

/**
 * 脉搏判断
 * @param obj
 * @returns
 */
function mbBlur(obj){
	var val = obj.value;
	if(val.length>0){
		if(!(val>=60 && val<=100)){
			layer.alert("脉搏输入不正确");
			$(obj).addClass("tzcss");
		}else{
			$(obj).removeClass("tzcss");
		}
	}
}

/**
 * 体温判断
 * @param obj
 * @returns
 */
function twBlur(obj){
	var val = obj.value;
	if(val.length>0){
		if(!(val>=36.5 && val<=37.5)){
			layer.alert("体温输入不正确");
			$(obj).addClass("tzcss");
		}else{
			$(obj).removeClass("tzcss");
		}
	}
}

/**
 * 血压判断
 * @param obj
 * @returns
 */
function xyBlur(obj,type){
	var val = obj.value;
	var min=0;
	var max=0;
	if(type=='a'){
		min=12;
		max=18.7;
	}else{
		min=8;
		max=12;
	}
	if(val.length>0){
		if(!(val>=min && val<=max)){
			layer.alert("血压输入不正确");
			$(obj).addClass("tzcss");
		}else{
			$(obj).removeClass("tzcss");
		}
	}
}


function clearFace(){
	// 每次点击，还原 人脸识别的默认值，默认值 为 0
	$("#isIdentity").val("0");
}