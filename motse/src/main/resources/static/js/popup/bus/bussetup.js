$(function(){
	$("#venderId").attr("name","venderid");
	initDate("#termOfValidity");
	initDate("#endTime");
	initDate("#startDate");
	initCheckData({'type':"elisa_check_project"},"projectName");
	//加载 检测者、核对者、报告者
	$.ajax({
		type : "POST",
		data:{'isDelete':'0'},
		url : "/common/queryWhereAdminInfoList",
		datatype : "json",
		success : function(result) {
			data = result.data;
			dataCheckAdmins = data;
			$("#testAdminid,#checkAdminid,#reportAdminid").empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#testAdminid,#checkAdminid,#reportAdminid").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
	//检测方法设置
	initCheckSelect("testingMethodid", "check_method");
	
	var config = [ [ // 表头
		{
			field : 'id',
			title : '编号',
			width:'5%',
			align : 'center'
		}, {
			field : 'plable',
			title : '项目名称',
			width:'11%',
			align : 'center'
		}, {
			field : 'fname',
			title : '试剂名称',
			width:'11%',
			align : 'center'
		}, {
			field : 'batchNumber',
			title : '试剂批号',
			width:'11%',
			align : 'center'
		}, {
			field : 'yname',
			title : '厂家名称',
			width:'15%',
			align : 'left'
		}, {
			field : 'defaultValue',
			title : '缺省值',
			width:'10%',
			align : 'center'
		}, {
			field : 'termOfValidity',
			title : '有效期',
			width:'10%',
			align : 'center',templet:function(d){ return $.myTime.UnixToDate(d.termOfValidity)}
		}, {
			field : 'startDate',
			title : '起始日期',
			width:'10%',
			align : 'center',templet:function(d){ return $.myTime.UnixToDate(d.startDate)}
		}, {
			field : 'endTime',
			title : '截止日期',
			width:'10%',
			align : 'center',templet:function(d){ return $.myTime.UnixToDate(d.endTime)}
		}, {
			field : 'name',
			title : '检测者',
			width:'10%',
			align : 'center'
		}, {
			field : 'jname',
			title : '核对者',
			width:'10%',
			align : 'center'
		}, {
			field : 'hname',
			title : '报告者',
			width:'10%',
			align : 'center'
		}, {
			field : 'lable',
			title : '检测方法',
			width:'10%',
			align : 'center'
		}
		] ]; 
	
	var tcid=$("#tcid").val();
	var dd = true;
	dataAll("config",config,{"tcid":tcid},"/testConfigInfo/queryTestConfigInfoListByTcid",'','',function(){
		// 如果是异步请求数据方式，res即为你接口返回的信息。
		// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
		// 表格 tr 单击事件
		if(!dd){
			return ;
		}
		dd = true;
 	var tab = $("#config").next().find('.layui-table tbody tr'); 
 	tab.click(function(event) { 
		 	var tr = $(event.target).closest("tr")[0];
			var id = $(tr).find("td").eq(0).find("div").html();  
			$("#id").val(id);
			simpleAjax("/testConfigInfo/queryTestConfigInfoById", {"id":id }, function(result) {
				$("#addForm input,select").each(function(){
					var item=$(this);
					var name=item.attr("name");
					
					if(name!="templateName"){
						item.val(result.data[name]);
					}
					if(name=='reagentid'){
						loadSupplies(result.data[name]);
					}else if(name=='reagentBatch'){
						var ids=new Array();
						ids[0]=result.data["reagentid"];
						ids[1]=result.data["reagentBatch"];
						loadBatch(ids);
					}
				});
				
				$("#startDate").val(result.data["startDate"].split(" ")[0]);
				$("#endTime").val(result.data["endTime"].split(" ")[0]);
			});
			
		});
	})
	

});
//加载试剂名称
$("#venderId").change(function(){
	loadSupplies('');
});
//加载批号
$("#reagentid").change(function(){
	loadBatch(null);
});

// 加载默认的有效期信息
$("#reagentBatch").change(function(){
	simpleAjax("/outstock/queryInfoById", {"id":this.value}, function(result) {
		
		$("#startDate").val($.myTime.UnixToDate(result.data.startTime));
		$("#endTime").val($.myTime.UnixToDate(result.data.endTime));
		$("#termOfValidity").val($.myTime.UnixToDate(result.data.expiryTime));
	});
});

//添加
$("#add").click(function(){
	var param=[];
	var sub=false;
	$("#config_form input,select").each(function(){
		var item=$(this);
		var val = item.val();
		if(null!=val){
			var name=item.attr('name');
			var tip=item.attr('tip');
			if("tcMark" != name){
				param[name]=val;
				if(val.length<1){
					sub=true;
					layer.alert(name + "    	"+tip);
					return false;
				}
			}
			
		}
	});
	if(!sub){
		var data=$("#addForm").serialize();
		$.post("/testConfigInfo/addTestConfigInfo",data,function(res){
			if(res.code==-1){
				layer.alert(res.message,function(){
					window.location.href=window.location.href;
				});
			}else{
				layer.alert(res.message);
			}
		});
	}
});

//修改
$("#update").click(function(){
	var id=$("#id").val();
	if(id.length<1){
		layer.alert("请选择一条记录");
		return false;
	}
	var data=$("#addForm").serialize();
	
	$.post("/testConfigInfo/updateTestConfigInfo",data,function(res){
		if(res.code==-1){
			layer.alert(res.message,function(){
				window.location.href=window.location.href;
			});
		}else{
			layer.alert(res.message);
		}
	});
});

//删除
$("#delete").click(function(){
	var id=$("#id").val();
	if(id.length<1){
		layer.alert("请选择浆员");
		return false;
	}else{
		$.post("/testConfigInfo/deleteTestConfigInfo",{"id":id},function(res){
			if(res.code==-1){
				layer.alert(res.message,function(){
					window.location.href=window.location.href;
				});
			}else{
				layer.alert(res.message);
			}
		});
	}
});
//加载试剂名称 
function loadSupplies(val){
	var roomId=$("#venderId").val();
	$("#reagentid").empty();
	$("#reagentid").append('<option value="">请选择</option>');
	$("#reagentBatch").empty();
	$("#reagentBatch").append('<option value="">请选择</option>');
	$.post("/suppliesInfo/queryInfo",{"supplyId":roomId},function(res){
		if(res.code==-1){
			$.each(res.data,function(index,item){
				if(item.id==val){
					$("#reagentid").append('<option selected="selected" value="'+item.id+'">'+item.name+'</option>');
				}else{
					$("#reagentid").append('<option value="'+item.id+'">'+item.name+'</option>');
				}
			});
		}
	});
}
//加载批号
function loadBatch(ids){
	var reagentid=$("#reagentid").val();
	if(null==reagentid || reagentid.length<1){
		reagentid=ids[0];
	}
	$("#reagentBatch").empty();
	$("#reagentBatch").append('<option value="">请选择</option>');
	$.post("/suppliesStock/queryStockBatchNumber",{"suppliesId":reagentid},function(res){
		if(res.code==-1){
			$.each(res.data,function(index,item){
				if(null!=ids){
					if(item.suppliesId==ids[1]){
						$("#reagentBatch").append('<option selected="selected" value="'+item.id+'">'+item.batchNumber+'</option>');
					}else{
						$("#reagentBatch").append('<option value="'+item.id+'">'+item.batchNumber+'</option>');
					}
				}else{
					$("#reagentBatch").append('<option value="'+item.id+'">'+item.batchNumber+'</option>');
				}
			});
		}
	});
}

//获取程序模式配置值
function initCheckData(datas, selectId) {
	$.ajax({
		type : "POST",
		data:datas,
		url : "/config/queryDictListByType",
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#"+selectId).empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#"+selectId).append("<option value='"+data[i].value+"'>"+data[i].lable+"</option>"); 
			}
			$("#"+selectId).append("<option value=''>其它</option>"); 
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}