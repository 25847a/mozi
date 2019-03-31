
$(function(){
	// 根据普免加载免疫类型选项
	initCheckData21(null,'/immuneSetting/queryImmunTypeOne',null,0);
	//获取用户信息
	initCheckData21('#creater','/immuneAssay/getImmuneAssayAdmin',null,2);
	
	layui.use(['table'], function(){
		//未化验人员
	  var specialNotAssay = [[
		  {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
		  ,{field: 'number', title: '序号', align:'center'}
		  ,{field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
	      ,{field: 'name', title: '姓名', align:'center',width:'20%'}
	      ,{field: 'type', title: '类型', align:'center',width:'20%',templet:function(d){
        	  return getPbTypeValue(d.type);
      	  }}
	      ,{field: 'immuneName', title: '免疫类型', align:'center',width:'20%'}
	      ,{field: 'bloodType', title: '血型', align:'center',width:'20%',templet:function(d){
        	 return getBloodValue(d.bloodType);
      	  }}
	      ,{field: 'sex', title: '性别', align:'center',width:'20%',templet:function(d){
        	 return getSexValue(d.sex);
      	  }}
	      ,{field: 'sampleNo', title: '小样号', align:'center',width:'20%'}
	      ,{field: 'allId', title: '登记号',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ]]
	  dataAll("newtestS",specialNotAssay,{"token" : getToken()},GetURLInfo()+"specialNotAssay",'','',function(){
		  var tab = $("#newtestS").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  var tr = $(event.target).closest("tr")[0];
			  var providerNo = $(tr).find("td").eq(2).find("div").html();
			  var allId = $(tr).find("td").eq(9).find("div").html();
			  $("#id").val( $(tr).find("td").eq(1).find("div").html());
			  getInfo(providerNo,allId);
		  });
	  });
	//已化验人员
	  var specialHaveAssay = [[
		  {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
		  ,{field: 'number', title: '序号', align:'center'}
	     ,{field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
	     ,{field: 'name', title: '姓名', align:'center',width:'20%'}
	      ,{field: 'oldimmuneName', title: '原免疫类型', align:'center',width:'20%'}
	     ,{field: 'immuneName', title: '效价后免疫类型', align:'center',width:'20%'}
	      ,{field: 'bloodType', title: '血型', align:'center',width:'20%',templet:function(d){
        	 return getBloodValue(d.bloodType);
      	  }}
	      ,{field: 'sex', title: '性别', align:'center',width:'20%',templet:function(d){
        	 return getSexValue(d.sex);
      	  }}
	      ,{field: 'sampleNo', title: '小样号', align:'center',width:'20%'}
	      ,{field: 'effectiveValue', title: '效价值', align:'center',width:'20%'}
	      ,{field: 'result', title: '化验结果', align:'center',width:'20%',templet:function(d){
        	 if(d.result==0){
        		 return '合格';
        	 }else{
        		 return '不合格';
        	 }
      	  }},
      	{field : 'allId',title : 'allId',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ]]
	  dataAll("newtest",specialHaveAssay,{"token" : getToken()},GetURLInfo()+"specialHaveAssay",'','',function(){
		  var tab = $("#newtest").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  var tr = $(event.target).closest("tr")[0];
			  var providerNo = $(tr).find("td").eq(2).find("div").html();
			  var allId = $(tr).find("td").eq(11).find("div").html();
			  $("#id").val( $(tr).find("td").eq(1).find("div").html());
			  getInfo(providerNo,allId);
		  });
	  });
	  //发布化验
	$("#confirm").click(function(){
		var providerNo = $("#providerNo").val();
		if(providerNo == "" || providerNo == null){
			layer.alert("请先选择浆员");
			return false;
		}
		var number = $("#number").val();//序号
		if(number == "" || number == null){
			layer.alert("请先扫描条形码");
			return false;
		}
		var id = $("#id").val();//主键ID
		var effectiveValue = $("#effectiveValue").val();//效价值
		if(effectiveValue == "" || effectiveValue == null){
			layer.alert("请输入效价值");
			return false;
		}
		var creater = $("#creater").val();//化验人
		var remarks = $("#remarks").val();//备注
		var result = $("#result").val();//免疫状态
		var oldImmuneId = $("#oldImmuneId").val();//免疫类型
		if(id == "" || id == null){
			layer.alert("请先选择浆员进行发布");
		}else{
			$.ajax({
				url:GetURLInfo()+"publishAssay",
				type:"post",
				dataType:"json",
				async:false,
				data:{"id":id,"effectiveValue":effectiveValue,"creater":creater,"remarks":remarks,"result":result,"oldImmuneId":oldImmuneId},
				success:function(result){
					if(result.code == -1){
						parent.layer.alert(result.message,function(){
							parent.layer.closeAll();
							location.reload();
							
						});
					}else{
						parent.layer.alert(result.message,function(){
							parent.layer.closeAll();
							location.reload();
						});
					}
				}
			});
		}
	});
	//取消化验
	$("#cancel").click(function(){
		var id = $("#id").val();
		var allId = $("#allId").val();
		var number = $("#number").val();
		var providerNo = $("#providerNo").val();
		var oldImmuneId = $("#oldImmuneId").val();
		var creater = $("#creater").val();
		if(id == "" || id == null){
			layer.alert("请先选择浆员进行取消发布");
			return false;
		}else{
			$.ajax({
				url:GetURLInfo()+"/cancelAssay",
				type:"post",
				dataType:"json",
				data:{"id":id,"allId":allId,"number":number,"providerNo":providerNo,"creater":creater,"oldImmuneId":oldImmuneId},
				success:function(result){
					if(result.code == -1){
						parent.layer.alert(result.message,function(){
							parent.layer.closeAll();
							location.reload();
						});
					}else{
						parent.layer.alert(result.message,function(){
							parent.layer.closeAll();
							location.reload();
						});
					}
				}
			});
		}
	});
	
	
	  
	});
	
	//刷新
	$("#refresh").click(function(){
		window.location.reload();
	});
	//工作时间
	initDate("#workDate");
	
	//搜索按钮点击事件
	 $("#query").click(function(){
	 	doSearch();
	 	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	 })
});///////////////////////////////////////////////////////////////

//自动判断效价值是否合格
function cop(){
	var str = document.getElementById("effectiveValue").value;
	if(Number(str)>=0 && Number(str)<=5.9){
		$("#result").val(1);
	}else if(Number(str)>=6 && Number(str)<=100){
		$("#result").val(0);
	}
}
//扫描器自动更新浆员信息
$("#queryAllId").submit(function(){
	var allId = $("#all").val();
	if(allId=='' || null==allId){
		layer.alert('请输入全登记号');
		return false;
	}
	$.ajax({
		type:'post',
		url:GetURLInfo()+'updateAssayNumber',
		data:{
			"allId":allId
		},
		dataType:'json',
		async:false,
		success:function(result){
			if(result.code==-1){
				var data = result.data;
				$("#id").val(data.id);
				$("#number").val(data.number);
				$("#providerNo").val(data.providerNo);
				$("#updater").val(data.updater);
				$("#name").val(data.name);//姓名
				$("#type").val(getPbTypeValue(data.type));//类型
				$("#bloodType").val(getBloodValue(data.bloodType));//血型
				$("#sex").val(data.sex==0?"男":"女");//性别
				$("#sampleNo").val(data.sampleNo);//小样号
				$("#immuneRegisterId").val(data.immuneRegisterId);//免疫编号
				$("#oldImmuneId").val(data.immuneId);//
				$("#idCard").attr("src",data.imgUrl);//图片
				
				//隐藏框
				$("#allId").val(data.allId);//全登记号
				layui.table.reload('newtestS');
			}else{
				parent.layer.alert(result.message,function(){
					parent.layer.closeAll();
				});
			}
		},
		error:function(){
			parent.layer.alert(result.message,function(){
				parent.layer.closeAll();
			});
		}
	});
	return false;
});

//搜索
function doSearch(){
	var workDate = $("#workDate").val();
	layui.table.reload('newtestS', {where: {"createDate": workDate}});
	layui.table.reload('newtest', {where: {"createDate": workDate}});
}
function getToken() {
	return localStorage.getItem("token");
}

function getInfo(providerNo,allId){
	$.ajax({
		  type:'post',
		  url:GetURLInfo()+'queryHeadInfo',
		  data:{
			  "providerNo":providerNo,
			  "allId":allId
		  },
		  dataType:'json',
		  success:function(result){
			  if(result.code==-1){
				  var data = result.data;
				  $("#name").val(data.name);
				  $("#type").val(getPbTypeValue(data.type));
				  $("#providerNo").val(data.providerNo);
				  $("#bloodType").val(getBloodValue(data.bloodType));
				  $("#sex").val(getSexValue(data.sex));
				  $("#sampleNo").val(data.sampleNo);
				  $("#immuneRegisterId").val(data.immuneRegisterId);
				  $("#idCard").attr("src",data.imgUrl);
				  //已登记的
				  $("#effectiveValue").val(data.effectiveValue);
				  $("#oldImmuneId").val(data.oldImmuneId);
				  $("#result").val(data.result);
				  $("#creater").val(data.creater);
				//隐藏框
				  	$("#allId").val(data.allId);
					$("#id").val(data.id);
					$("#number").val(data.number);
				//全登记号扫描框
					$("#all").val(data.allId);
			  }else{
				  parent.layer.alert(result.message,function(){
						parent.layer.closeAll();
					});
			  }
		  },
		  error:function(){
			  parent.layer.alert('操作失败',function(){
					parent.alert.closeAll();
				});
		  }
	  })
}

function initCheckData21(selectId,url,datas,type){
	simpleAjax(url,datas,function(result){
		if(type==0){
			if(result.code==-1){
	        	   var data = result.data;
	        	   if(data.isDisable==1){
	        		// 加载免疫类型选项
	        			initCheckData21('#oldImmuneId','/immuneSetting/queryAmmuneSetting',{type:1},1);
	        	   }else{
	        		   initCheckData21('#oldImmuneId','/immuneSetting/queryImmuneSettingList',null,1);
	        	   }
	           }
		}
		if(type==1){
			// 加载免疫类型选项
			 for (var o in result.data){
	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
	                $(selectId).append(str);
	            }
		}
		if(type==2){
			// 加载用户信息
			for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $(selectId).append(str);
            }
		}
	})
	
}
