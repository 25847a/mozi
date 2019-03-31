$(function(){
	/*日期时间*/ 
	initDate("#startTime");
	initCheckAdmins("#reportAdminid");
	initCheckData21("bloodRedProtein", "/config/queryDictListByType", {'type':'cuso_config'}, 1);
	initCheckData21("suppliesId", "/testConfig/queryTestConfigListByEffective", {}, 2);
	search();
});
function search(){
	initTable("newtest");
	initTable("newtestS");
}
function initTable(tableId){
	var cols;
	var startTime = $("#startTime").val();
	if("newtest"==tableId){
	cols =  [[
		 {field: 'id', title: 'ID',  sort: true,align:'center', display : 'none',minWidth : '0',width : '0',type : "space"/* fixed: 'left'*/}
	      ,{field: 'allId', title: '登记号', sort : true, width:'20%', align:'center'}
	      ,{field: 'sampleNo', title: '小样号', sort: true, width:'20%',align:'center'}
	      ,{field: 'providerNo', title: '浆员卡号',  width:'18%',align:'center'} 
	      ,{field: 'name', title: '姓名',  width:'18%',align:'center'}
	      ,{field: 'sex', title: '性别', width:'18%', align:'center',templet: function (d){ return getSexValue(d.sex);}} 
	      ,{field: 'prCreateDate', title: '登记日期',  width:'17%',align:'center', templet:function(d){ return $.myTime.UnixToDate(d.prCreateDate);}}
	      ,{field: 'rechecked', title: '是否复检',width:'18%', align:'center', templet:function(d){ return getBooleanValue(d.rechecked);}}
	]];
	}else{
		cols = [[
			//表头
			 {field: 'id', title: 'ID',  sort: true,align:'center', display : 'none',minWidth : '0',width : '0',type : "space"/* fixed: 'left'*/}
		      ,{field: 'allId', title: '登记号',  sort : true, width:'18%',align:'center'}
		      ,{field: 'sampleNo', title: '小样号',  width:'18%',sort: true,align:'center'}
		      ,{field: 'providerNo', title: '浆员卡号',  width:'15%',align:'center'} 
		      ,{field: 'name', title: '姓名',  width:'10%',align:'center'}
		      ,{field: 'sex', title: '性别', width:'10%', align:'center',templet: function (d){ return getSexValue(d.sex);}} 
		      ,{field: 'result', title: '化验结果',  width:'15%',align:'center',templet: function (d){ return getCheckResultValue(d.result);}}
		      ,{field: 'hbsag', title: 'HBsAg',  width:'12%',align:'center',templet: function (d){ return getCheckedValue(d.hbsag);}}
		      ,{field: 'hcv', title: 'HCV',  width:'10%',align:'center',templet: function (d){ return getCheckedValue(d.hcv);}}
		      ,{field: 'alt', title: 'ALT',  width:'10%',align:'center',templet: function (d){ return getAltValue(d.alt);} }
		      ,{field: 'hiv', title: 'HIV',  width:'10%',align:'center',templet: function (d){ return getCheckedValue(d.hiv);}}
		      ,{field: 'syphilis', title: '梅毒',  width:'10%',align:'center',templet: function (d){ return getCheckedValue(d.syphilis);}}
		      ,{field: 'wholeBlood', title: '全血比重',width:'15%', align:'center',templet: function (d){ return getCheckResultValue(d.wholeBlood);}}
		      ,{field: 'proteinValue',  width:'15%',title: '蛋白含量'}
		      ,{field: 'opinion', title: '浆员状态',  width:'15%',align:'center',templet: function (d){ return getOpinionValue(d.opinion);}}
		      ,{field: 'day', title: '暂拒天数',  width:'15%',align:'center'}
		      ,{field: 'remarks', title: '备注',  width:'10%',align:'center'}
		]];
	}
	dataAll(tableId,cols,{"isAssay": "newtest"==tableId?0:1,"type":1,"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListsByCreateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
		 initTableOnClick(tableId);
	});
}		


function initTableOnClick(tableId){
	// 表格 tr 单击事件
	var tab = $("#"+tableId).next().find('.layui-table tbody tr');
	
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var id = $(tr).find("td").eq(0).find("div").html();
		var providerNo = $(tr).find("td").eq(1).find("div").html();
		initData(id,providerNo);
		$("#id").val(id);
	});
	if($("#isSaveAll").is(':checked') && "newtest"==tableId){
		var tr1 =$(tab).find("tr").eq(0);
		var id1 = $(tr1).find("td").eq(0).find("div").html();
		var providerNo1 = $(tr1).find("td").eq(1).find("div").html();
		initData(id1,providerNo1);
		$("#id").val(id1);
	}
}
 // 初始化选择
var idtep = 0;
function initData(id,registriesNo,pcreateDate) {
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/newCard/selectById?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#protein").val(data.protein);
			$("#wholeBlood").val(data.wholeBlood);
			$("#bloodRedProtein").val(data.bloodRedProtein);
			$("#alt").val(data.alt);
			$("#syphilis").val(data.syphilis);
			$("#HBsAg").val(data.hbsag);
			$("#hcv").val(data.hcv);
			$("#hiv").val(data.hiv);
			$("#serumProtein").val(data.serumProtein);
			$("#bloodRedProteinValue").val(data.bloodRedProteinValue);
			$("#blood").val(data.blood);
			$("#remarks").val(data.remarks);
			$("#result").val(data.result);
			if(null != data.reportAdminid)
			$("#reportAdminid").val(data.reportAdminid);
			$("#name").val(data.name);
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#providerNo").val(data.providerNo);
			$("#sex").val(getSexValue(data.sex));
			$("#sampleNo").val(data.sampleNo);
			$("#allId").val(data.allId);
			$("#play").attr("src",data.imgUrl);
			$("#proteinValue").val(data.proteinValue);
			if($("#isSaveAll").is(':checked')){
				// saveInfo();
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
	
}

function saveInfo(){
	var id = $("#id").val();
	// 先检查浆员状态是否已经发布 
	simpleAjax("/newCard/selectById", {"id":id}, function(result) {
		if(result.data.isAssay == 1){
			var index = parent.layer.alert("该浆员已经发布结果,请不要重复发布结果!", function() {
				parent.layer.close(index);
			});
			return false;
		}
	});
	var tempResult = 0; // 0 合格 , 1不合格  用于结果发布
	var result = $("#result").val();
	var isSaveAll = $("#isSaveAll").is(':checked');
	if(2==result){
		var index = parent.layer.alert("该浆员还需要复检,请不要发布结果!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	//alert(isSaveAll);
	
	if(result == 0  &&false){
		var alt = $("#alt").val();
		if(alt==undefined || alt==''){
			var index = parent.layer.alert("还没有做转氨酶的化验!", function() {
				parent.layer.close(index);
			});
			return false;
		}else{
			if(alt != 0){
				tempResult = 1;
			}
		}
		var syphilis = $("#syphilis").val();
		if(syphilis==undefined || syphilis==''){
			var index = parent.layer.alert("还没有做梅毒的化验!", function() {
				parent.layer.close(index);
			});
			return false;
		}else{
			if(syphilis != 0){
				tempResult = 1;
			}
		}
		var HBsAg = $("#HBsAg").val();
		if(HBsAg==undefined || HBsAg==''){
			var index = parent.layer.alert("还没有做乙肝的化验!", function() {
				parent.layer.close(index);
			});
			return false;
		}else{
			if(HBsAg != 0){
				tempResult = 1;
			}
		}
		var hcv = $("#hcv").val();
		if(hcv==undefined || hcv==''){
			var index = parent.layer.alert("还没有做丙肝的化验!", function() {
				parent.layer.close(index);
			});
			return false;
		}else{
			if(hcv != 0){
				tempResult = 1;
			}
		}
		var hiv = $("#hiv").val();
		if(hiv==undefined || hiv==''){
			var index = parent.layer.alert("还没有做艾滋的化验!", function() {
				parent.layer.close(index);
			});
			return false;
		}else{
			if(hiv != 0){
				tempResult = 1;
			}
		}
	}
	
	var proteinValue = $("#proteinValue").val();
	var protein = $("#protein").val();
	if(protein != 0){
		tempResult = 1;
	}
	if(proteinValue==undefined || proteinValue==''){
		var index = parent.layer.alert("蛋白含量不能为空!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	if(isNaN(proteinValue)){
		var index = parent.layer.alert("蛋白含量只能为数字!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var sampleNo  = $("#sampleNo").val();
	if(sampleNo==undefined || sampleNo==''){
		var index = parent.layer.alert("还没有做固定浆员登记,请先登记!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var bloodRedProteinValue = $("#bloodRedProteinValue").val();
	var wholeBlood = $("#wholeBlood").val();
	if(wholeBlood==undefined || wholeBlood==''){
		var index = parent.layer.alert("还没有做全血比重检验!", function() {
			parent.layer.close(index);
		});
		return false;
	}else{
		if(wholeBlood != 0){
			tempResult = 1;
		}
	}
	var remarks = $("#remarks option:selected").text();
	var reportAdminid = $("#reportAdminid").val();
	var suppliesId = $("#suppliesId").val();
	
	var data = {"id":id,"proteinValue":proteinValue,"bloodRedProteinValue":bloodRedProteinValue,"isAssay":1,"remarks":remarks,"result":result,"reportAdminid":reportAdminid,"suppliesId":suppliesId};
	if(result == 1){
		layer.confirm('该浆员将发布为不合格浆员，是否确认。', function(index){
			if(remarks == '' )
				layer.confirm("请选择一个化验备注,虽然不选也没关系!", function() {
				});
			simpleAjax("/newCard/updateById", data, function(result) {
				layer.alert("操作成功");
				layer.closeAll();
				search();
				return false;
			});
		});
	}else{
		var isOver = true;
		if(tempResult ==1){
			layer.alert("有不合格的检验项目,不能发布合格结果.") ;
			return false;
		}
		// 还要做血清电泳检验判断
		var sedata = {"providerNo":$("#providerNo").val()};
		// 先查找超过14天还没发结果的蛋白电泳记录
		simpleAjax("/serumElectrophoresis/queryInfoByProviderNoBefore14Days", sedata, function(result) {
			if(false ==result.data){
				if(isOver){
					simpleAjax("/newCard/updateById", data, function(result2) {
						parent.layer.alert("操作成功");
						parent.layer.closeAll();
						search();
						isOver = false;
					});
				}
			}else{
				layer.confirm("该浆员需要做血清电泳化验,是否前往血清电泳化验?", function() {
					 $("#isSaveAll").prop("checked", false);
					addWithArea("/serumElectrophoresis/addElectrophoresis?providerNo="+$("#providerNo").val(),false,[ '600px', '300px' ]);
				});
			}
			
		});
		// 再查找一年内是否没有做蛋白电泳
		simpleAjax("/serumElectrophoresis/queryInfoByProviderNo", sedata, function(result) {
			// 提示要做蛋白电泳
			if(false !=result.data){
				layer.confirm("该浆员需要做血清电泳化验,是否前往血清电泳化验?", function() {
					 $("#isSaveAll").prop("checked", false);
					 layer.ready(function() {
							layer.open({
								type : 2,
								title : '血红蛋白检测打印',
								maxmin : false,
								content : '/serumElectrophoresis/addElectrophoresis?providerNo='+$("#providerNo").val(),
								area : [  '600px', '300px'  ],
								cancel: function(index, layero){ 
									if(isOver){
										simpleAjax("/newCard/updateById", data, function(result2) {
											parent.layer.alert("操作成功");
											parent.layer.closeAll();
											search();
											isOver = false;
										});
									}
										layer.close(index);
									}, 
								end : function(){
									if(isOver){
										simpleAjax("/newCard/updateById", data, function(result2) {
											parent.layer.alert("操作成功");
											parent.layer.closeAll();
											search();
											isOver = false;
										});
									}
								}
							})
						});
				});
			}else{
				if(isOver){
					simpleAjax("/newCard/updateById", data, function(result2) {
						parent.layer.alert("操作成功");
						parent.layer.closeAll();
						search();
						isOver = false;
					});
				}
			}
			
		});
	}
}

function initCheckData21(selectId, url, datas, type) {
	simpleAjax(url, datas, function(result) {
		data = result.data;
		$("#" + selectId).empty();
	
	if (type == 1) {
		for (var i = 0, j = data.length; i < j; i++) {
			$("#" + selectId).append( "<option value='" + data[i].value + "'>" + data[i].lable + "</option>");
		}
	} else if (type == 2) {
		for (var i = 0, j = data.length; i < j; i++) {
			$("#" + selectId).append(
					"<option value='" + data[i].id + "'>" + data[i].templateName + "</option>");
		}
	} else if (type == 3) {
		for (var i = 0, j = data.length; i < j; i++) {
			$("#" + selectId).append(
					"<option value='" + data[i].id + "'>"
							+ data[i].batchNumber + "</option>");
		}
	} else if (type == 4) {
		for (var i = 0, j = data.length; i < j; i++) {
			$("#" + selectId).append(
					"<option value='" + data[i].id + "'>"
							+ data[i].sequenceNumber + "</option>");
			}
		}

	});
}	


! function() {
	  $('#elisajie').on('click', function() {
	    layer.ready(function() {
	      perContent = layer.open({
	                  type:2,
	                title: '酶标板综合管理',
	                content: '/elisaInfo/initPage2',
	                  area: ['100%', '100%'],
	             });
	           layer.full(perContent);
	    });
	  })
	}();