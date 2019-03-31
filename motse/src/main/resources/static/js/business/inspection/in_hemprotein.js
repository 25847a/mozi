var startTime = $("#createTime").val();
$(function() {
	initCheckAdmins("#checkAdmin");
	initCheckCUSO();
	initListData({'startTime':startTime,"page":0,"limit":2000});
	initDate("#createTime");
});

　
/*function initListData(){
	
	var startTime = $("#createTime").val(); 
	$.ajax({
		type : "POST",
		data:{'startTime':startTime,"page":0,"limit":100},
		url : "/bloodRedProteinContent/queryListByUpdateDate",
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#tdata").empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#tdata").append("<tr onclick='initData("+data[i].id+")'>"+
								"<td align='center'>"+data[i].allId+"</td>"+
								"<td align='center'>"+data[i].providerNo+"</td>"+
								"<td align='center'>"+data[i].name+"</td>"+
								"<td align='center'>"+getSexValue(data[i].sex)+"</td>"+
								"<td align='center'>"+(data[i].ss>2?'是':'否')+"</td>"+
								"<td align='center'>"+(data[i].bluestone==1?'是':'否')+"</td>"+
								"<td align='center'>"+(data[i].bluestone==0?'是':'否')+"</td>"+
								"<td align='center'>"+getCUSOLable(data[i].resultId)+"</td>"+
								"<td align='center'>"+getCheckAdminName(data[i].checkedAdminId)+"</td>"+
								"<td align='center'>"+($.myTime.UnixToDate(data[i].updateDate))+"</td>"+
							"</tr>"); 
			};
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}*/


function initListData(dat){ 
var data;
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  ,layer = layui.layer;
	$.ajax({
		type : "POST",
		data:dat,//{'startTime':startTime,"page":0,"limit":20}
		url : "/bloodRedProteinContent/queryListByUpdateDate",
		datatype : "json",
		async:false,
		success : function(result) {
			data = result.data;
			$("#tdata").empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#tdata").append("<tr onclick='initData("+data[i].id+")'>"+
								"<td align='center'>"+data[i].allId+"</td>"+
								"<td align='center'>"+data[i].providerNo+"</td>"+
								"<td align='center'>"+data[i].name+"</td>"+
								"<td align='center'>"+getSexValue(data[i].sex)+"</td>"+
								"<td align='center'>"+(data[i].plasmaType == 1?'是':'否')+"</td>"+
								"<td align='center'>"+(data[i].bluestone==1?'是':'否')+"</td>"+
								"<td align='center'>"+(data[i].bluestone==0?'是':'否')+"</td>"+
								"<td align='center'>"+getCUSOLable(data[i].resultId)+"</td>"+
								"<td align='center'>"+getCheckAdminName(data[i].checkedAdminId)+"</td>"+
								"<td align='center'>"+($.myTime.UnixToDate(data[i].updateDate))+"</td>"+
							"</tr>"); 
			};
			laypage.render({
			    elem: 'biuuu_city_list'
			    ,count: data.length  
			    ,limit:20
			    ,layout: ['prev', 'page', 'next', 'refresh', 'skip' ,'count', 'limit']
			    ,jump: function(obj){ 
			      $("#tdata").empty(); 
					for(var i=obj.curr*obj.limit - obj.limit,j=(obj.curr*obj.limit>data.length?data.length:obj.curr*obj.limit);i<j;i++){
						$("#tdata").append("<tr onclick='initData("+data[i].id+")'>"+
										"<td align='center'>"+data[i].allId+"</td>"+
										"<td align='center'>"+data[i].providerNo+"</td>"+
										"<td align='center'>"+data[i].name+"</td>"+
										"<td align='center'>"+getSexValue(data[i].sex)+"</td>"+
										"<td align='center'>"+(data[i].plasmaType == 1?'是':'否')+"</td>"+
										"<td align='center'>"+(data[i].bluestone==1?'是':'否')+"</td>"+
										"<td align='center'>"+(data[i].bluestone==0?'是':'否')+"</td>"+
										"<td align='center'>"+getCUSOLable(data[i].resultId)+"</td>"+
										"<td align='center'>"+getCheckAdminName(data[i].checkedAdminId)+"</td>"+
										"<td align='center'>"+($.myTime.UnixToDate(data[i].updateDate))+"</td>"+
									"</tr>"); 
					};
			    }
			  });
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	  
	});
}
//读取浆员卡号
function read(){
	//请求身份证接口
	$.ajax({
		type : "POST",
		url :"/common/readProviderNoInfo",
		datatype : "json",
		success : function(result) {
			if (null!=result.data) {
				initListData({'startTime':startTime,"providerNo":result.data.code,"page":0,"limit":20});
				$.post(GetURLInfo()+"queryBloodRedHeadInfo",{"providerNo":result.data.code,"startTime":startTime},function(res){
					if(res.code==-1 && null!=res.data){
						initData(res.data.id);
					}else{
						parent.layer.alert('无此浆员记录',function(){
			   				parent.layer.closeAll();
			   			});
					}
				});
			} else {
				parent.layer.alert('无此浆员信息',function(){
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

// ////queryListByUpdateDate
var datacuso;
// 获取硫酸铜检验配置值
function initCheckCUSO() {

	$.ajax({
		type : "POST",
		data:{'type':'cuso_config'},
		url : "/config/queryDictListByType",
		datatype : "json",
		success : function(result) {
			data = result.data;
			datacuso=data;
			$("#resultId").empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#resultId").append("<option value='"+data[i].value+"'>"+data[i].lable+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

// 获取硫酸銅液面信息
function getCUSOLable(value){
	for(var i=0,j=datacuso.length;i<j;i++){
		if (datacuso[i].value == value) {
			return datacuso[i].lable;
		} 
	}
	
	return "";
}

//获取管理员名称信息
function getCheckAdminName(id){
	for(var i=0,j=dataCheckAdmins.length;i<j;i++){
		if (dataCheckAdmins[i].id == id) {
			return dataCheckAdmins[i].name;
		} 
	}
	
	return "";
}
var idtep = 0;

function initData(id) {
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/bloodRedProteinContent/queryByID?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#name").val(data.name);
			$("#bloodType").val(getBloodValue(data.bloodType));
			$("#isCollection").val(data.isCollection);
			$("#allId").val(data.allId);
			$("#sex").val(getSexValue(data.sex));
			if(data.sex==0){
				$("#resultId").val(1);
			}else{
				$("#resultId").val(2);
			}
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#icNumber").val(data.icNumber);
			$("#sampleNo").val(data.sampleNo);
			$("#providerNo").val(data.providerNo);
			if(data.imagespot != null)
				$("#play").attr('src',data.imgUrl);
			else{
				$("#play").attr('src', '../../../img/new_pa2.png');
			}
			if(null != data.checkedAdminId && '' != data.checkedAdminId)
			$("#checkAdmin").val(data.checkedAdminId);
			if(null != data.bluestone && '' != data.bluestone)
			$("#bluestone").val(data.bluestone);
			if(null != data.resultId && '' != data.resultId){
				$("#resultId").val(data.resultId);
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

$("#doUpdate").click(function(){
	var id=$("#id").val();
	if(id==""){
		parent.layer.alert("请先选择一条记录");
		return  false;
	}
	var bluestone=$("#bluestone").val();
	var resultId=$("#resultId").val();
	var checkAdmin=$("#checkAdmin").val();
	var isCollection = $("#isCollection").val();
	simpleAjax("/bloodRedProteinContent/updateInfoById", {"id":id,"bluestone":bluestone,"resultId":resultId,"checkedAdminId":checkAdmin,"isCollection":isCollection}, function(result) {
		parent.layer.alert("操作成功");
		initListData({'startTime':startTime,"page":0,"limit":20});
	});
	
	
	
});
$("#doRetract").click(function(){
	var id=$("#id").val();
	if(id==""){
		layer.alert("请先选择一条记录");
		return  false;
	}
	var bluestone=$("#bluestone").val();
	var resultId=$("#resultId").val();
	var checkAdmin=$("#checkAdmin").val();
	var isCollection = 0;
	simpleAjax("/bloodRedProteinContent/updateInfoById0", {"id":id,"bluestone":bluestone,"resultId":resultId,"checkedAdminId":checkAdmin,"isCollection":isCollection}, function(result) {
		parent.layer.alert("操作成功");
		initListData({'startTime':startTime,"page":0,"limit":20});
		$("#id").val("");
	});
	
	
});

function changeBluestone(){
	var sex = $("#sex").val();
	var result = $("#resultId").val();
	if("男"==sex){
		if(result==1){
			$("#bluestone").val(0);
		}else{
			$("#bluestone").val(1);
		}
	}else{
		if(result==2){
			$("#bluestone").val(0);
		}else{
			$("#bluestone").val(1);
		}
	}
	
}

 function changeResult(){
	 var sex = $("#sex").val();
	 var bluestone = $("#bluestone").val();
	 if("男"==sex){
			if(bluestone==0){
				$("#resultId").val('1');
			}else{
				$("#resultId").val('3');
			}
		}else{
			if(bluestone==0){
				$("#resultId").val('2');
			}else{
				$("#resultId").val('4');
			}
		}
	 
 }
 $("#print").click(function(){
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '血红蛋白含量打印',
				maxmin : false,
				content : '/bloodRedProteinContent/printReport?chooseDate='+$("#createTime").val(),
				area : [ '100%', '100%' ],
			})
		});
 });
