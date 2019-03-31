$(function(){
	//初始化时间
	initDate("#startTime"); 
	//人工通过
	$("#people").click(function(){
		$("#result").val('2');
	});
	//查询
	$("#query").click(function(){
		var param = $("#startTime").val();
		layui.table.reload('newtestS',{where:{"createDate":param}});
		layui.table.reload('newtestright',{where:{"createDate":param}});	
	});
	var startTime = $("#startTime").val();
/*列表数据显示借口*/ 
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页 
  ,table = layui.table //表格 
  ,element = layui.element; //元素操作  
  
//未发放
  
  //执行 table
  table.render({
    elem: '#newtestS' 
    ,url: 'queryNotGrantList' //数据接口
    ,page: true, //开启分页
    where:{"createDate":startTime},
    limits:[5, 10, 15,20],
    limit: 5, //每页默认显示的数量
    response: { // //定义后端 json 格式，详细参见官方文档
         statusName: 'code', //数据状态的字段名称，默认：code
         statusCode: -1, //成功的状态码，默认：0
         msgName: 'message', //状态信息的字段名称，默认：msg
         count: "count", //数据总数的字段名称，默认：count
         data: 'data' //数据列表的字段名称，默认：data
    },
    cols: [[ //表头
      {field:'id',title:'编号',display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'providerNo', title: '献浆员卡号',width:'20%', align:'center',/* fixed: 'left'*/}
      ,{field: 'regNo', title: '登记号', sort : true, width:'20%', align:'center'}
      ,{field: 'pName', title: '姓名',width:'20%', align:'center'}
      ,{field: 'sex', title: '性别',width:'20%',  align:'center',templet:function(obj){
    	  return (obj.sex == 1)?"女":"男";
      }}
      ,{field: 'icNumber', title: 'IC卡号',width:'20%', align:'center'}
      ,{field: 'remarks', title: '备注',width:'20%',  align:'center'}
    ]],
    done:function(){
		var tab = $("#newtestS").next().find('.layui-table tbody tr');
		 tab.click(function(event){
			 $("#grancost").attr("disabled",false);
			 $("#qgrancost").attr("disabled",true);
			 var tr = $(event.target).closest("tr")[0];
			 var id = $(tr).find("td").eq(0).find("div").html();
			 var allId = $(tr).find("td").eq(2).find("div").html();
			 var createDate = $("#startTime").val();//时间
			 var providerNo = $(tr).find("td").eq(1).find("div").html();//浆员卡号
			 showTitle(allId,id,createDate,providerNo);
			 
			 $("#providerNo").val(providerNo);
			 
			 $("#id").val(id);
			//免疫流程判断是否已经注射？但是先判断之前是否进行免疫
			 var da = {"providerNo":providerNo,"allId":allId};
			 $.post('/plasmYell/queryImmuneYell',da,function(result){
				 if(result.data.isImmune==1){
					 verifying(providerNo);
				 }
			 });
		 });
	}
 }); 
 
//点击图片放大
  $("#demo1").click(function(){
  	loadIdCardImage($("#proNo").val());
  });
//读取身份证接口
  $('#read').on('click', function() {
		var date=$("#startTime").val();//时间
		$.ajax({
			type : "POST",
			url :"/common/readProviderNoInfo",
			datatype : "json",
			success : function(result) {
				if (result.code==-1) {
					$.ajax({
						type : "POST",
						url :"queryNotGrantInfo",
						data:{"providerNo": result.data.code,"updateDate":date},
						datatype : "json",
						success : function(result) {
							if(null!=result.data){//有浆员
								if(result.data.isGrant==0){
									layui.table.reload('newtestS', {where: {"providerNo": result.data.providerNo,"createDate":date}});
									$('#domne tbody tr').css('background-color','#b6dcbe');
									$.post(GetURLInfo()+"queryCostHeadInfo",{"providerNo":result.data.providerNo,"allId":result.data.allId,"isGrant":0},function(res){
										if(res.code==-1 && null!=res.data){
											$("#grancost").attr("disabled",false);
											 $("#qgrancost").attr("disabled",true);
											 showTitle(res.data.allId,res.data.id,date,res.data.providerNo);
											 $("#id").val(res.data.id);
											 $("#providerNo").val(res.data.providerNo);
											//免疫流程判断是否已经注射？但是先判断之前是否进行免疫
											 var da = {"providerNo":res.data.providerNo,"allId":res.data.allId};
											 $.post('/plasmYell/queryImmuneYell',da,function(result){
												 if(result.data.isImmune==1){
													 verifying(providerNo);
												 }
											 });
										}else{
											parent.layer.alert('请刷新页面重新读取浆员卡',function(){
								   				parent.layer.closeAll();
								   			});
										}
									});
								}else{
									parent.layer.alert('该浆员今天已领取补助',function(){
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
	});
//显示头部相关信息
  function showTitle(allId,pro,createDate,providerNo){
		//获取现场照片
			$.ajax({
				type : "POST",
				url : "/plasmaImage/queryPlasmaImageByImgIdAndType",
				datatype : "json",
				data:{"id":pro,"type":4},
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
		 //获取头部内容
		 $.ajax({
			 url:"../costGrant/queryCostHead?id="+pro+"&createDate="+createDate+"&code="+providerNo,
			 type:"post",
			 async:false,
			 dataType:"json",
			 success:function(obj){
				 var res = obj.data;
				 //$("#id").val(res.id);
				 $("#isGrant").val(res.isGrant);
				 $("#pName").val(res.name);
				 if(res.bType == 0){
					 $("#bType").val("A");
				 }else if(res.bType = 1){
					 $("#bType").val("B");
				 }else if(res.bType = 2){
					 $("#bType").val("O");
				 }else if(res.bType = 3){
					 $("#bType").val("AB");
				 }
				 if(res.type == 0){
					 $("#pType").val("普通");
				 }else if(res.pType = 1){
					 $("#pType").val("普免");
				 }else if(res.pType = 2){
					 $("#pType").val("特免");
				 }
				 $("#regNo").val(res.allId);
				 $("#icNum").val(res.icNumber);
				 if(res.sex == 0){
					 $("#sex").val("男");
				 }else if(res.sex == 1){
					 $("#sex").val("女");
				 }
				 $("#demo3").attr('src',res.photo);
				 $("#proNo").val(res.providerNo);
				 $("#imNum").val(res.ordinaryNo);
				 $("#isNew").val(res.plasmaType == 0 ?"非固定":"固定")
				 $("#money").val(res.money);
				 $("#remark").val(res.remarks);
				 $("#colName").val(res.fname);
				 $("#year").val(res.year);
				 $("#month").val(res.month);
				 $("#colDate").val(null!=res.aboutDate ? dateFtt('yyyy-MM-dd',new Date(res.collectionDate)) : '');
				 if(null!=res.collectionDate){
					 $("#lastcolDate").val(dateFtt('yyyy-MM-dd',new Date(res.collectionDate)));
				 }
				 if(res.fmoney == ""){
					 $("#lastmoney").val("");
				 } else{
					 $("#lastmoney").val(res.fmoney);
				 }
				 if(res.daydiff == ""){
					 $("#daydiff").val();
				 }else{
					 $("#daydiff").val(res.daydiff);
				 }
				 $("#demo1").attr("src",res.imagez);
				 $("#roadFee").val(res.roadFee);
				 $("#activityMoney").val(res.activityMoney);
				 var cost = (res.pMoney == null?0:res.pMoney) + (res.roadFee==null?0:res.roadFee) + (res.activityMoney == null ? 0 :res.activityMoney);
				 $("#cost").val(cost);
				 costDetail(allId);
			 }
		 });
  }
  
  
//如果采浆费用变化的话   发放费用也要变化
 $("#money").change(function(){
	 var cost = parseFloat($("#money").val()) + parseFloat($("#roadFee").val()==null?0:$("#roadFee").val()) +parseFloat($("#activityMoney").val()==""?0:$("#activityMoney").val());
	 $("#cost").val(cost);
 });
 
//已发放
//执行 table
  table.render({
    elem: '#newtestright' 
    ,url: 'queryGrantList' //数据接口
    ,page: true, //开启分页
    where:{"createDate":startTime},
    limits:[5, 10, 15,20],
    limit: 5, //每页默认显示的数量
    response: { // //定义后端 json 格式，详细参见官方文档
         statusName: 'code', //数据状态的字段名称，默认：code
         statusCode: -1, //成功的状态码，默认：0
         msgName: 'message', //状态信息的字段名称，默认：msg
         count: "count", //数据总数的字段名称，默认：count
         data: 'data' //数据列表的字段名称，默认：data
    },
    cols: [[ //表头
      {field:'id',title:'编号',display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'providerNo', title: '献浆员卡号',width:'20%', align:'center',/* fixed: 'left'*/}
      ,{field: 'regNo', title: '登记号',width:'20%', align:'center'}
      ,{field: 'pName', title: '姓名',width:'20%', align:'center'}
      ,{field: 'sex', title: '性别',width:'20%',  align:'center',templet:function(obj){
    	  return (obj.sex == 1)?"女":"男";
      }}
      ,{field: 'provider', title: '发放人',width:'20%',  align:'center'}
      ,{field: 'ffDate', title: '发放日期',width:'20%',  align:'center',templet:function(obj){
    	  var text = "";
    	  text = new Date(obj.ffDate).Format("yyyy-MM-dd");
    	  return text;
      }}
    ]],
    done:function(){
		var tab = $("#newtestright").next().find('.layui-table tbody tr');
		 tab.click(function(event){
			 $("#grancost").attr("disabled",true);
			 $("#qgrancost").attr("disabled",false);
			 //$("#Phyfonta").attr("disabled",true);
			 var tr = $(event.target).closest("tr")[0];
			 var id = $(tr).find("td").eq(0).find("div").html();
			 var allId = $(tr).find("td").eq(2).find("div").html();
			 var createDate = $("#startTime").val();//时间
			 var providerNo = $(tr).find("td").eq(1).find("div").html();//浆员卡号
			 
			 showTitle(allId,id,createDate,providerNo);
			 $("#id").val(id);
		 });
	}
 }); 
  
  /*浆员费用明细table*/
  var objParam = location.search.substring(location.search.indexOf("=")+1,location.search.length);
  table.render({
    elem: '#newcost' 
    ,url: '../costGrant/getCostGrantDetailList?param='+objParam //数据接口
    ,page: false, //开启分页
    response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   }
    ,cols: [[ //表头
/* {type: 'checkbox', fixed: 'left'}*/
     {field: 'id', title: '编号', align:'center', fixed: 'left'}
     ,{field: 'providerNo', title: '献浆员卡号', align:'center'}
     ,{field: 'regNo', title: '登记号', sort : true,  width:130, align:'center'}
     ,{field: 'name', title: '姓名', align:'center'}
      ,{field: 'createDate', title: '发放日期', align:'center',function(obj){
    	  return new Date(obj.createDate).Format("yyyy-MM-dd");
      }}
      ,{field: 'updater', title: '发放人', align:'center'}
    ]]
  });
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
     		if(layEvent === 'detail') {
     			layer.ready(function() {
				layer.open({
					type: 2,
					title: '浆员费用明细',
					maxmin: true,
					area: ['700px', '400px'],
					content:'getCostGrantDetailMemu?id='+data.providerNo,
				})
			});
     		}
  });
}); 
//添加采浆费用，费用列表发生变化
$("#money").change(function(){
	var first = $("#costdetail tr").find("td:last").html();
	var m = $(this).val()==""?0:$(this).val();
	$("#ff").html(m);
	var t = $("#costdetail tr:last").find("td:last").html();
	var total = parseFloat(m) + parseFloat(t) - parseFloat(first);
	$("#costdetail tr:last").find("td:last").html(total);
});
//费用明细
function costDetail(allId){
	/*$.ajax({
		url:"../costGrant/grantCostDetailData",
		type:"post",
		dataType:"json",
		async:false,
		data:{"id":id},
		success:function(result){
			if(result.code == -1){
				$("#costdetail").find("tr:gt(0)").remove();
				var rd = result.data;
				console.log(rd);
				var html = "";
				var total;
				var ff = rd[0].colMoney;
				var hd = rd[0].roadMoney;
				if(ff == null || ff == ""){
					ff = 0;
				}
				if(hd == null || hd == ""){
					hd = 0;
				}
				if(rd.length == 1){
					//对应一个活动费用
					html += "<tr><td>采浆费用</td><td id='ff'>"+ff+"</td></tr>";
					html += "<tr><td>路费</td><td>"+hd+"</td></tr>";
					if(rd[0].name != null){
						html += "<tr><td>活动："+rd[0].name+"</td><td>"+rd[0].activityMoney+"</td></tr>";
						total = parseFloat(ff) + parseFloat(hd) + parseFloat(rd[0].activityMoney);
					}
					total = parseFloat(rd[0].colMoney==null?0:rd[0].colMoney) + parseFloat(rd[0].roadMoney==null?0:rd[0].roadMoney) +parseFloat(rd[0].activityMoney==null?0:rd[0].activityMoney);
				}else{
					//对应多个活动费用
					html += "<tr><td>采浆费用</td><td>"+ff+"</td></tr>";
					html += "<tr><td>路费</td><td>"+hd+"</td></tr>";
					total = parseFloat(ff) + parseFloat(hd);
					for(var i = 0; i<rd.length;i++){
						html += "<tr><td>活动："+rd[i].name+"</td><td>"+rd[i].activityMoney+"</td></tr>";
						total += parseFloat(rd[i].activityMoney);
					}
				}
				$("#costdetail").append(html).append("<tr><td>合计</td><td id='total'>"+total+"</td></tr>");
			}else{
				layer.alert("查询失败");
			}
		}
	});*/
	
	$("#costdetail tbody").eq(0).html('');
	var cost = $("#money").val();
	var tr='<tr>';
	tr+='<td>采浆费用</td>';
	tr+='<td>'+((null==cost || cost.length<1) ? 0:cost)+'</td>';
	tr+='</tr>';
	tr+='<td>路费</td>';
	tr+='<td>'+$("#cost").val()+'</td>';
	tr+='</tr>'; 
	$("#costdetail tbody").eq(0).append(tr);
	$.post('/plasmCollection/queryNextCollectionCost',{"allId":allId},function(res){
		if(res.code==-1){
			tr='';
			if(null!=res.data){
				$.each(res.data,function(index,item){
					tr+='<tr>';
					tr+='<td>'+item.name+'</td>';
					tr+='<td>'+((null==item.money || item.money.length<1) ? 0 : item.money)+'</td>';
					tr+='</tr>';
				});
			}
			$("#costdetail tbody").eq(0).append(tr);
			total();
		}
		
		//改变采浆费用后，更新总采浆费用
		$("#money").blur(function(){
			var tr= $("#costdetail tbody").eq(0).find('tr').eq(0);
			var money = $(this).val();
			$(tr).find('td').eq(1).html(money);
			total();
		});
	});
}

/**
 * 计算总采浆费用
 * @returns
 */
function total(){
	var sum=0;
	$("#costdetail tbody").eq(0).find('tr').each(function(index,item){
		var money = $(item).find('td').eq(1).html();
		sum+=parseInt($(item).find('td').eq(1).html());
	});
	$("#costdetail tbody").eq(1).find('td').eq(1).html(sum);
}

 //发放
layui.use('layer', function(){
$('#grancost').on('click', function() {
	var immune = $("#immune").val();
	if(immune!="" && parseInt(immune)==1){
		parent.layer.alert("请进行免疫登记注射,否则无法领取补助",function(){
			parent.layer.closeAll();
		});
		return false;
	}
	var name = $("#pName").val();
	var result = $("#result").val();
	if(name == null || name == "" ){
		layer.alert("请选择浆员发放误工补贴");
	}else if($("#result").val() == ""){
		layer.alert("请先进行验证");
	}else{
		$("#isIdentity").val(1);
		var image = $("#imageFace").val();
		var id = $("#id").val();
		var isGrant = $("#isGrant").val();
		if(isGrant == 1){
			layer.alert(name+"误工补贴已经发放完毕");
		}else{
			var total = $("#costdetail tbody").eq(1).find('td').eq(1).html();
			$("#qxff").html("您确定要发放（"+$("#pName").val()+"）误工补贴"+total+"元吗?");
			$("#bisudcost").show();
			var id = $("#id").val();
			var money = $("#money").val();
			var remarks = $("#remark").val();
			var providerNo=$("#providerNo").val();
			$("#sub").click(function(){
				$.ajax({
					url:'../costGrant/updateStatusTo1',
					type:"post",
					data:{"id":id,"money":money,"image":image,"remarks":remarks,"type":result,"providerNo":providerNo},
					dataType:"json",
					async : false,
					success:function(result){
						if(result.code == -1){
							location.reload();
						}else{
							layer.alert("费用发放失败");
						}
						
					}				
				});
		   });
		}
	}
});

//取消发放
$('#qgrancost').on('click', function() { 
	var name = $("#pName").val();
	var result = $("#result").val();
	/*if(name == null || name == "" ){
		layer.alert("请选择浆员取消发放误工补贴");
	}else if($("#result").val() == ""){
		layer.alert("请先进行验证");
	}else{
		$("#isIdentity").val(1);
		var image = $("#imageFace").val();
		var id = $("#id").val();
		var isGrant = $("#isGrant").val();
		if(isGrant == 0){
			layer.alert(name+"误工补贴已经取消发放");
		}else{
			$("#qxff").html("您确定要取消发放（"+$("#pName").val()+"）误工补贴吗?");
			$("#bisudcost").show();
			var id = $("#id").val();
			var money = $("#money").val();
			var remarks = $("#remark").val();
			$("#sub").click(function(){
				$.ajax({
					url:'../costGrant/updateStatusTo0',
					type:"post",
					data:{"id":id,"image":image,"remarks":remarks},
					dataType:"json",
					success:function(result){
						if(result.code == -1){
							$("#bisudcost").hide();
							layui.table.reload('newtestS');	
							var sdf = $("#startTime").val();
							layui.table.reload('newtestright',{where:{"param":sdf}});	
						}else{
							layer.alert("取消费用发放失败");
						}
					}				
				});
		   });
		}
	}*/
	
	$("#isIdentity").val(1);
	var image = $("#imageFace").val();
	var id = $("#id").val();
	var isGrant = $("#isGrant").val();
	if(isGrant == 0){
		layer.alert(name+"误工补贴已经取消发放");
	}else{
		$("#qxff").html("您确定要取消发放（"+$("#pName").val()+"）误工补贴吗?");
		$("#bisudcost").show();
		var id = $("#id").val();
		var money = $("#money").val();
		var remarks = $("#remark").val();
		var providerNo=$("#providerNo").val();
		$("#sub").click(function(){
			$.ajax({
				url:'../costGrant/updateStatusTo0',
				type:"post",
				data:{"id":id,"image":image,"remarks":remarks,"providerNo":providerNo},
				dataType:"json",
				async : false,
				success:function(result){
					if(result.code == -1){
						$("#bisudcost").hide();
						layui.table.reload('newtestS');	
						var sdf = $("#startTime").val();
						layui.table.reload('newtestright',{where:{"param":sdf}});	
					}else{
						layer.alert("取消费用发放失败");
					}
				}				
			});
	   });
	}
});

//打印小票
$("#printcost").click(function(){
	var id = $("#id").val();
	$.ajax({
		url:"../common/printGrantCost",
		type:"post",
		dataType:"json",
		data:{"id":id},
		success:function(result){
			if(result.code == -1){
				layer.alert("打印成功");
			}else{
				layer.alert(result.message);
			}
		}
	});
});

//页面刷新
$("#refresh").click(function(){
	window.location.reload();
});

})

//免疫验证是否需要注射
function verifying(providerNo){
	$.ajax({
		type:'post',
		url:GetURLInfo()+'costImmunity',
		data:{
			"providerNo":providerNo
		},
		dataType:'json',
		success:function(result){
			if(result.code==1){
				parent.layer.alert(result.message,function(){
					parent.layer.closeAll();
				});
				$("#immune").val(1);//给免疫验证隐藏框赋值
			}else{
				$("#immune").val(0);//给免疫验证隐藏框赋值
			}
		},
		error:function(){
			parent.layer.alert("操作失误",function(){
				parent.layer.closeAll();
			});
		}
	});
}

$('#cannel2').on('click', function() {  
	$("#bisudcost").hide();
})

$('#cannel2').on('click', function() {  
	$("#bisudcost").hide();
	})

/*面部识别*/
//
//
	$('#Phyfonta').click(function() {
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		identityValidate();
	});

})
