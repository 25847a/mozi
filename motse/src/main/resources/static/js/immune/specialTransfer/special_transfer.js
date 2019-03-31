$(function(){
	// 根据普免加载免疫类型选项
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunTypeOne",
        datatype : "json",
        success : function(result) {
           if(result.code==-1){
        	   var data = result.data;
        	   if(data.isDisable==1){
        		   settingList("/immuneSetting/queryAmmuneSetting",{type:1});
        	   }else{
        		   settingList("/immuneSetting/queryImmuneSettingList",null);
        	   }
           }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
	initDate("#workDate");
	layui.use(['table'], function(){
		//可转类浆员列表
	  var specialTransferList = [[{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ,{field: 'providerNo', title: '献浆卡号',width:'20%', align:'center'}
	      ,{field: 'name', title: '姓名', width:'20%',align:'center'}
	      ,{field: 'sex', title: '性别',width:'20%', align:'center',templet:function(data){
	    	  return data.sex == 0?"男":"女";
	      }}
	      ,{field: 'bloodType', title: '血型',width:'20%', align:'center',templet:function(data){
	    	  
	    	  return getBloodValue(data.bloodType);
	      }}
	      ,{field: 'ordinaryNo', title: '免疫编号', width:'20%',align:'center'}
	      ,{field: 'updateDate', title: '反馈日期', width:'20%',align:'center',templet:function(data){
	    	  return new Date(data.updateDate).Format("yyyy-MM-dd")
	      }}
	      ]]
	  data("newtestS",specialTransferList,"specialTransferList",'','',function(){
		  var tab = $("#newtestS").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  var tr = $(event.target).closest("tr")[0];
			  var id = $(tr).find("td").eq(0).find("div").html();
			  var updateDate = $("#updateDate").val();
			  headTitle(id);
		  });
	  });
	  
	  //已转类浆员列表
	  var conversionRecordList = [[{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'providerNo', title: '献浆卡号', width:'20%',align:'center'}
      ,{field: 'name', title: '姓名',width:'20%', align:'center'}
      ,{field: 'sex', title: '性别',width:'20%', align:'center',templet:function(data){
    	  return data.sex == 0?"男":"女";
      }}
      ,{field: 'bloodType', title: '血型',width:'20%', align:'center',templet:function(data){
    	  return getBloodValue(data.bloodType);
      }}
      ,{field: 'priname', title: '未转类前类型',width:'24%', align:'center'}
      ,{field: 'nowname', title: '转类后类型',width:'20%',align:'center'}
      ,{field: 'updateDate', title: '操作日期', width:'20%',align:'center',templet:function(data){
    	  return new Date(data.updateDate).Format("yyyy-MM-dd")
      }}
      ,{field: 'czName', title: '操作者',width:'20%', align:'center'}
      ]]
	  data("newtestright",conversionRecordList,"conversionRecordList",'','',function(){
		  
	  });
	  
	//根据id获取头部信息
	function headTitle(id){
		$.ajax({
			url:"/conversionRecord/querySpecialTransferHead",
			dataType:"json",
			type:"post",
			data:{"id":id},
			success:function(result){
				if(result.code == -1){
					var dr = result.data;
					$("#name").val(dr.name);
					$("#type").val(getPbTypeValue(dr.type));
					$("#providerNo").val(dr.providerNo);
					$("#sex").val(dr.sex == 0 ?"男":"女");
					$("#thisStatus").find("option").remove();
					$("#thisStatus").append("<option value='"+dr.immuneId+"'>"+dr.immuneName+"</option>");
					$("#immuneId").val(dr.ordinaryNo);
					$("#privilegeId").val(dr.ordinaryNo);
					$("#immuneName").val("");
					$("#workDate").val(new Date(dr.updateDate).Format("yyyy-MM-dd"));
					$("#idCard").attr("src",dr.imagez);
				}else{
					layer.alert("获取信息失败");
				}
			}
		});
	};
	
	
	//显示所有特免浆员
	/*$("#show").click(function(){
		 var providerNo = $("#proNo").val();
		layui.table.reload('newtestS',{where:{"providerNo":providerNo}});
		layui.table.reload('newtestright',{where:{"providerNo":providerNo}});
	});*/
	
	
	//转换
	$("#change").click(function(){
		var providerNo = $("#providerNo").val();
		var thisStatus = $("#thisStatus").val();
		var immuneName = $("#immuneName").val();
		if(providerNo == "" || providerNo == null){
			layer.alert("请先选择浆员");
		}else if(immuneName == thisStatus){
			layer.alert("请选择其它的类别进行转类");
		}else if(immuneName == "" || immuneName == null){
			layer.alert("请先选择转换类别");
		}else{
				layer.open({
					type: 2,
					title: '转换类别',
					maxmin: true,
					area: ['420px', '160px'],
					content:'/conversionRecord/toIsTransfer',
				})
		}
	});
	
	$("#refresh").click(function(){
		window.location.reload();
	});
	
	});
	
	//加载免疫类型选项
	function settingList(url,data){
		//获取所有可以转类的类别
		$.ajax({
			type : "POST",
			url:url,
			dataType:"json",
			data:data,
			success:function(result){
					for(var o in result.data){
						var str = '<option value='+result.data[o].id+'>'+result.data[o].immuneName+'</option>';
						$("#immuneName").append(str);
					}
				},
				 error : function() {
			        	parent.layer.alert("获取免疫类型失败",function(){
			   				parent.layer.closeAll();
			   			});
			        }
		});
	};
	//搜索按钮点击事件
	$("#show").click(function(){
		doSearch();
		currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	});
	//工作时间
	initDate("#updateDate");});
//搜索
function doSearch(){
	var providerNo = $("#proNo").val();
	var updateDate = $("#updateDate").val()
	layui.table.reload('newtestS', {where: {"providerNo":providerNo,"updateDate":updateDate}});
	layui.table.reload('newtestright', {where: {"providerNo":providerNo,"updateDate":updateDate}});
}

function getToken() {
	return localStorage.getItem("token");
}
