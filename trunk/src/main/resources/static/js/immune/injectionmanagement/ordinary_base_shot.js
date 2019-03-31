$(function(){
	layui.use(['table'], function(){
		//未注射人员
	  var notShotOrdBaseList = [[{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ,{field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
	      ,{field: 'name', title: '姓名', align:'center',width:'20%'}
	      ,{field: 'immuneName', title: '免疫名称', align:'center',width:'20%'}
	      
	      ,{field: 'sex', title: '性别', align:'center',width:'14%',templet:function(data){
	    	  return data.sex == 0?"男":"女";
	      }}
	      ,{field: 'type', title: '类型', align:'center',width:'14%',templet:function(data){
	    	   return getPbTypeValue(data.type);
	    	 
	      }}
	      ,{field: 'num', title: '针次数', align:'center',width:'16%'}
	      
	      ]]
	  dataAll("newtest",notShotOrdBaseList,{"token" : getToken()},"notShotOrdBaseList",'','',function(){
		  var tab = $("#newtest").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  $("#reload input").val("");
			  $("#reload select").val("");
			  var tr = $(event.target).closest("tr")[0];
			  var id = $(tr).find("td").eq(0).find("div").html();
			  var updateDate = $("#startTime").val();
			  headTitle(id,updateDate);
		  });
	  });
	  
	//已注射人员
	  var shotOrdBaseList = [[{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	  		,{field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
	  		,{field: 'name', title: '姓名', align:'center',width:'20%'}
	  		,{field: 'immuneName', title: '免疫名称', align:'center',width:'20%'}
	  		,{field: 'sex', title: '性别', align:'center',width:'14%',templet:function(data){
	    	  return data.sex == 0?"男":"女";
	      }}
	      ,{field: 'type', title: '类型', align:'center',width:'14%',templet:function(data){
	    	   return getPbTypeValue(data.type);
	    	 
	      }}
	      ,{field: 'num', title: '针次数', align:'center',width:'16%'}
	      ]]
	  dataAll("newtestS",shotOrdBaseList,{"token" : getToken()},"shotOrdBaseList",'','',function(){
		  var tab = $("#newtestS").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  $("#reload input").val("");
			  $("#reload select").val("");
			  var tr = $(event.target).closest("tr")[0];
			  var id = $(tr).find("td").eq(0).find("div").html();
			  var updateDate = $("#startTime").val();
			  headTitle(id,updateDate);
			 
		  });
		  
	  });
	});
	
	//根据id获取头部信息
	function headTitle(id,updateDate){
		$.ajax({
			url:"/ordinaryInjection/ordinaryBaseHead",
			dataType:"json",
			type:"post",
			data:{"id":id,"updateDate":updateDate},
			success:function(result){
				if(result.code == -1){
					var rd = result.data;
					$("#id").val(rd.id);
					$("#isShot").val(rd.isShot);
					$("#name").val(rd.name);
					$("#sex").val(rd.sex==0?"男":"女");
					$("#bloodType").val(getBloodValue(rd.bloodType));
					$("#type").val(getPbTypeValue(rd.type));
					$("#immuneName").val(rd.immuneName);
					$("#immuneId").val(rd.immuneId);
					$("#icNum").val(rd.icNumber);
					$("#providerNo").val(rd.providerNo);
					if(!rd.providerNo==""){
						$("#play").attr("src",rd.photo);
					}
			//		$("#regId").val(rd.registriesNo);
					$("#shotTime").val(new Date(rd.updateDate).Format("yyyy-MM-dd"));
					$("#shotNum").val(rd.injection);
					$("#nurse").val(rd.zsName);
					$("#num").val(rd.num);
					$("#immuneNum").val(rd.immuneRegisterId);
					if(rd.injection != null ){
						$("#shotNum").val(rd.injection);
						$("#errorSeason").val(rd.status);
						$("#errorSolve").val(rd.remarks);
					}else{
						$("#errorSeason").val(0);
					}
				//	$("#immune").empty();//防止缓存记录存在
				//	if(number==2){
					/*	var str ="<option value='"+rd.vaccineId+"'>"+rd.supplies+"</option>";
						$("#immune").append(str);
						getimmune($("#immune").val());//这里应该获取疫苗ID才行*/
						//获取疫苗类型相关信息
					//	getImmuneType($("#immuneId").val());
				//	}else{
						//获取疫苗类型相关信息
						getImmuneType($("#immuneId").val());
				//	}
				}else{
					layer.alert("获取内容出错");
				}
			}
		});
	};
	
	//注射
	$("#shot").click(function(){
		var id = $("#id").val();
		var isShot= $("#isShot").val();
		var status = $("#errorSeason").val();
		var remarks = $("#errorSolve").val();
		var injection = $("#shotNum").val();
		var vaccineId = $("#vaccineId").val();
		var num = parseInt($("#num").val());
		var immune = $("#immune").val();
		if(isShot==1){
			layer.alert("浆员已注射,不可点击注射按钮");
			return false;
		}
		if(id == "" || id == null){
			layer.alert("请选择浆员进行注射");
		}else{
			//当有异常时，异常处理必填
			if((status == "") && (remarks == "" || remarks ==undefined)){
				layer.alert("请填写异常信息");
				return false;
			}
			if((immune == "") && (immune == "" || immune ==undefined)){
				layer.alert("请选择疫苗");
				return false;
			}
			$.ajax({
				url:"/ordinaryInjection/ordBaseToShot",
				type:"post",
				dataType:"json",
				data:{"id":id,"status":status,"remarks":remarks,"injection":injection,"vaccineId":vaccineId,"num":num},
				success:function(result){
					if(result.code == -1){
						parent.layer.alert("注射成功",function(){
							parent.layer.closeAll();
							location.reload();
						});
					}else{
						parent.layer.alert("注射失败",function(){
							parent.layer.closeAll();
							location.reload();
						});
					}
				}
			});
		}
	});
	
	//取消注射
	$("#cancelShot").click(function(){
		var id = $("#id").val();
		var isShot= $("#isShot").val();
		var providerNo = $("#providerNo").val();
		var immuneId = $("#immuneId").val();
		var num = parseInt($("#num").val());
		if(isShot==0){
			layer.alert("浆员未已注射,不可点击取消注射按钮");
			return false;
		}
		if(id == "" || id == null){
			layer.alert("请选择浆员进行取消注射");
		}else{
				$.ajax({
					type:"post",
					url:GetURLInfo()+"/ordBaseToNotShot",
					dataType:"json",
					data:{"id":id,"providerNo":providerNo,"immuneId":immuneId,"num":num},
					success:function(result){
						if(result.code == -1){
							parent.layer.alert("取消注射成功",function(){
								parent.layer.closeAll();
								location.reload();
							});
						}else{
							parent.layer.alert("取消注射失败",function(){
								parent.layer.closeAll();
								location.reload();
							});
						}
					}
				});
		}
	});
	
	//选择疫苗
/*	$("#immune").change(function(){
		var immune = $("#immune").val();
		getimmune(immune);
	});
	//选择疫苗的接口
	function getimmune(immune){
		$.ajax({
			url:"/ordinaryInjection/queryVaccineInfo",
			type:"post",
			dataType:"json",
			data:{"immune":immune},
			success:function(result){
				var data = result.data;
				if(result.code==-1){
					$("#immuneBatch").val(data.batchNumber);//批号
					$("#size").val(data.standard);//规格
					$("#shotNum").val(data.injection);//注射量
					$("#validateDate").val(data.validMonth);//有效期
					$("#factoryName").val(data.supply);//生产厂家
					$("#vaccineId").val(data.vaccineId);//隐藏疫苗ID
				}
			}
		});
	}*/
	//获取疫苗类型相关信息
	function getImmuneType(id){
		$.ajax({
			url:"/ordinaryInjection/getBaseImmuneTypes",
			type:"post",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if(null==result.data){
					layer.alert("请先将该疫苗出库");
					return;
				}
				var data = result.data;
				$("#immune").val(data.name);
				$("#immuneBatch").val(data.batchNumber);//批号
				$("#size").val(data.standard);//规格
				$("#shotNum").val(data.injection);//注射量
				$("#validateDate").val(dateFtt("yyyy-MM-dd",new Date(data.validMonth)));//有效期
				$("#factoryName").val(data.supply);//生产厂家
				$("#vaccineId").val(data.vaccineId);//隐藏疫苗ID
			/*	var st = "<option value=''>请选择</option>";
				$("#immune").append(st);
				for(var o in result.data){
					var str = "<option value='"+result.data[o].vaccineId+"'>"+result.data[o].name+"</option>";
					$("#immune").append(str);
				}*/
			}
		});
	}
	
	//刷新
	$("#refresh").click(function(){
		window.location.reload();
	});
	
	//搜索按钮点击事件
	 $("#query").click(function(){
	 	doSearch();
	 	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	 });
	 //工作时间
	 initDate("#startTime");
});
 
//搜索
function doSearch(){
	var date = $("#startTime").val();
	layui.table.reload('newtestS',{where:{"updateDate":date}});
	layui.table.reload('newtest',{where:{"updateDate":date}});
}
function getToken() {
	return localStorage.getItem("token");
}
