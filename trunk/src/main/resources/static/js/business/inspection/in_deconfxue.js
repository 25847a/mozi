$(function(){
	/* 日期时间 */ 
	initDate("#createTime");
	initTable("newtest");
	initTable("newtestS");
	//打印
	$("#print").click(function(){
		printSM();
	});
	//确定 
	$("#confirm").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
//		var result = $("#result").val();    // 判断是否识别成功
//		//先判断有没有人脸识别成功 
//		if(result.length<1){
//			layer.alert('请先识别浆员');
//			return false;
//		}
		$.post("/smallBlood/updateWithCollection",{"id":id},function(res){
			if(res.code==-1){
				layer.alert(res.message,function(){
					 layer.closeAll();
					 initList();
				 });
			}else{
				layer.alert(res.message);
			}
		});
	})
	
	//取消采样
	$("#cancal").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		$.post("/smallBlood/cacalSmallBlood",{"id":id},function(res){
			if(res.code==-1){
				layer.alert(res.message,function(){
					 layer.closeAll();
					 initList();
				 });
			}else{
				layer.alert(res.message);
			}
		});
	});
});


function printSendInfo(){

	 var sendTime = $("#createTime").val();
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '打印送样记录',
				maxmin : true,
				area : [ '100%', '100%' ],
				content : '/specimenCollection/printSendInfo?type=small&sendDate='+sendTime,
			})
		});
	 
}

function printSM(){
	var id=$("#id").val();
	if(id.length>0){
		$.post("/common/printSpecimenCollection",{"id":id, "isSpecimen" : 1},function(res){
			
			if(res.code!=-1){
				layer.alert(res.message);
			}
		});
	}else{
		layer.alert("请选择浆员");
	}
}
//读取浆员卡号
function read(){
	var startTime=$("#createTime").val();//时间
	//请求身份证接口
	$.ajax({
		type : "POST",
		url :"/common/readProviderNoInfo",
		datatype : "json",
		success : function(result) {
			if (result.code==-1) {
				$.ajax({
					type : "POST",
					url :"/smallBlood/querySmallBloodDu",
					data:{"providerNo":result.data.code,"updateDate":startTime},
					datatype : "json",
					success : function(result) {
						if(null!=result.data){
							if(result.data.isCollection==0){
								layui.table.reload('newtest', { where : { "isCollection" : 0, "startTime" : startTime,"providerNo":result.data.providerNo}});
								$('#domne tbody tr').css('background-color','#b6dcbe');
								// 每次点击，还原 人脸识别的默认值，默认值 为 0
								$.post("/smallBlood/querySmallBloodHeadInfo",{"providerNo":result.data.providerNo,"allId":result.data.allId},function(res){
									if(res.code==-1 && null!=res.data){
										initData(res.data.id,res.data.scount);
										$("#id").val(res.data.id);
									}else{
										parent.layer.alert('请刷新页面重新读取浆员卡',function(){
							   				parent.layer.closeAll();
							   			});
									}
								});
							}else{
								parent.layer.alert('该浆员今天已采集小血',function(){
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

function initList() {
	var startTime = $("#createTime").val()
	layui.table.reload('newtest', { where : { "isCollection" : 0, "startTime" : startTime }});
	layui.table.reload('newtestS', {where : {"isCollection" : 1,"startTime" : startTime}});
}

function initTable(tableId){
	var cols;
	var startTime = $("#createTime").val();
	if("newtest"==tableId){
	cols =  [[ // 表头
		/* {type: 'checkbox', fixed: 'left'} */
		{
			field : 'id', title : 'id', display : 'none', minWidth : '0', width : '0', type : "space"
		}, {
			field : 'scount', title : 'scount', display : 'none', minWidth : '0', width : '0', type : "space"
		},
		{
			field : 'providerNo', title : '献浆员卡号', sort : true, width:'20%', align : 'center'
		}, {
			field : 'name', title : '姓名', width:'15%', align : 'center'
		}, {
			field : 'sex', title : '性别', width:'15%', align : 'center', templet : function(d) { // 单元格格式化函数
				return getSexValue(d.sex); }
		}, {
			field : 'allId', title : '登记号', width:'15%', sort : true, align : 'center'
		}, {
			field : 'pbType', title : '浆员类型', width:'20%', align : 'center', templet : function(d) { // 单元格格式化函数
				return getPbTypeValue(d.pbType); }
		}, {
			field : 'bloodType', title : '血型', align : 'center', width:'15%', templet : function(d) { // 单元格格式化函数
				return getBloodValue(d.bloodType);
			}
		},  {
			field : 'updateDate', title : '化验日期', width:'20%', align : 'center', templet:function(d){ return $.myTime.UnixToDate(d.updateDate);}
		} ] ];
	}else{
		cols = [ [ // 表头
			/* {type: 'checkbox', fixed: 'left'} */
			{
				field : 'id',
				title : 'id',
				display : 'none',
				minWidth : '0',
				width : '0',
				type : "space"
			}, {
				field : 'scount',
				title : 'scount',
				display : 'none',
				minWidth : '0',
				width : '0',
				type : "space"
			},  {
				field : 'providerNo', title : '献浆员卡号', sort : true, width:'20%', align : 'center'
			}
			,  {
				field : 'name', title : '姓名', width:'10%', align : 'center'
			}, {
				field : 'sex', title : '性别', width:'10%', align : 'center', templet : function(d) { // 单元格格式化函数
					return getSexValue(d.sex);
				}
			}, {
				field : 'allId', title : '登记号', width:'15%', sort : true, align : 'center'
			}, {
				field : 'pbType', title : '浆员类型', width:'15%', align : 'center', templet : function(d) { // 单元格格式化函数
					return getPbTypeValue(d.pbType);
				}
			}, {
				field : 'bloodType', title : '血型', width:'10%', align : 'center', templet : function(d) { // 单元格格式化函数
					return getBloodValue(d.bloodType);
				}
			}, {
				field : 'updateDate', title : '化验日期', width:'25%', align : 'center', templet:function(d){ return $.myTime.UnixToDate(d.updateDate);}
			} ] ];
	}
	dataAll(tableId,cols,{"isCollection": "newtest"==tableId?0:1,"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListByCreateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
		 initTableOnClick(tableId);
	});
}

function sendOff(){
	var table = layui.table;
	var checkStats = table.checkStatus('newtestS');
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
		  url: "/smallBlood/updateWithSendOff",
		  type: "POST",
		  data: datas,
		  traditional: true,//这里设置为true
		  success: function(result) {
			  layer.alert(result.message,function(){
				  layer.closeAll();
				  window.reload();
			  });
		  }
		});
	
}
/* 静脉验证 */
!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#Physeg').on('click', function() {
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '手掌静脉验证',
				maxmin : true,
				area : [ '550px', '429px' ],
				content : '../../Popup/Bus/Bus_New-Veri.html',

			})
		});
	})
}();

/* 面部识别 */
//
//
!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		$('#indecfxue').on('click', function() {
			var id=$("#id").val();
			if(id.length<1){
				layer.alert('请选择浆员');
				return false;
			}
			identityValidate();
		})
}();


var idtep = 0;
function initData(id,scount) {
	if(idtep == id){
		return;
	}
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/smallBlood/queryByID?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#isCollection").val(data.isCollection);
			$("#name").val(data.name);
			getBloodValue(data.bloodType);
			$("#bloodType").val(getBloodValue(data.bloodType));
			$("#providerNo").val(data.providerNo);
			$("#scount").val(scount);
			$("#sex").val(getSexValue(data.sex));
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#icNumber").val(data.icNumber);
			$("#sampleNo").val(data.sampleNo);
			$("#demo3").attr('src',data.photo);
			if(data.imagez != null)
				$("#demo1").attr('src',data.imagez);
			else{
				$("#demo1").attr('src', '../../../img/new_pa1.png');
			}
			if(data.imgUrl != null)
				$("#imageFace_show").attr('src',data.imgUrl);
			else{
				$("#imageFace_show").attr('src', '../../../img/new_pa2.png');
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

function initTableOnClick(tableId){
	　var re = /^[0-9]+.?[0-9]*$/; 
	// 表格 tr 单击事件
	var tab = $("#"+tableId).next().find('.layui-table tbody tr');
	tab.click(function(event) {
		
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var id = $(tr).find("td").eq(0).find("div").html();
		var providerNo = $(tr).find("td").eq(1).find("div").html();
		if (re.test(id)) {
			initData(id,providerNo);
			$("#id").val(id);
		}
	});
	
}





