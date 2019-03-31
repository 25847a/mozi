$(function() {
	
	initTypess();
	initCheckData2("templateName", "/elisaTemplate/queryTemplates", 2);
	initDate("#startTime");
	initDate("#endTime");
	initDateDone("#createDate",function(){$("#templateName").val("");});
	initCheckAdmins("#creater");
	initTable1("jiemei");
	initTable();
	
});

function selectElisaInfoById(obj) {
	var id = $("#sequenceNumber").val();
	if(obj){
		id=$("#eiId").val();
	}
	if(id==null || id=='' ){
		var index = parent.layer.alert("没有读板信息!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	var elisaInfoJson = { "id" : id };
	simpleAjax("/elisaInfo/selectElisaInfoById", elisaInfoJson, function(result) {
		showDataTable(result.data);
	});
}
// 
// isDuWaveDetection

function saveElisaInfo(opType,type) {
	
	if($("#qcResult").val()==2){
		var index = parent.layer.alert("本次实验的质控结果为失控,不能发布!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	// 操作人
	var opAdmin = $("#creater").val();
	// 模板类型
	var etId = $("#templateName").val();
	// 试剂
	var reagentId = $("#reagentId").val();
	// 质控品
	var qcId = $("#qcId").val();
	// 酶标仪检测信息ID
	var eiId = $("#eiId").val();
	// 记录检测的酶标仪孔位集合
	var checked = new Array();
	// 获得BC空白孔的OD值
	bcValue = 0;
	// 判断是否有阳性孔需要发布
	var isAdd =false;
	var evIds = "";
	if(opType){ //全部发布
		for (var i = 0, j = 8; i < j; i++) {
			for (var k = 1; k <= 12; k++) { // 循环找到需要发布的
				var idname = transToChars(i)+k;
				var hid =$("#hid"+idname).val();
				if(hid!=undefined && hid !=""){
					var value = $("#t"+idname).val();
					if(value.indexOf("+")==-1){
						evIds =evIds+ hid+",";
					}else{
						isAdd = true;
					}
				}
			}
		}
	}else{
		var tdx = $("#tdx").val();
		var tdy = $("#tdy").val();
		var idname = tdx+tdy;
		var hid =$("#hid"+idname).val();
		if(hid!=undefined && hid !=""){
			var value = $("#t"+idname).val();
			if(value.indexOf("+")==-1){
				evIds = hid+",";
			}else{
				evIds = hid+",";
				isAdd = true;
			}
		}
	}
	if(evIds.length>1){
		evIds = evIds.substring(0,evIds.length-1);
	}
	if(evIds==""){
		var index = parent.layer.alert("请不要发布空白孔或者特殊孔!", function() {
			parent.layer.close(index);
		});
		return false;
	}
	if(isAdd){
		var index = parent.layer.confirm(opType?"有阳性记录,是否跳过该孔继续发布所有阴性孔?":"是否发布该阳性孔?", {
			btn: ['确定','取消'] //按钮
		},function() {
			simpleAjax("/elisaInfo/releaseElisaValue", {"infoId":eiId,"evids":evIds,"type":type}, function(result) {
				var index = parent.layer.alert(type=='0'?"发布成功!":"取消发布成功!", function() {
					selectElisaInfoById(true);
					parent.layer.close(index);
				});
			});
		},function(){
			parent.layer.close(index);
			return false;
		});
	}else{
		simpleAjax("/elisaInfo/releaseElisaValue", {"infoId":eiId,"evids":evIds,"type":type}, function(result) {
			var index = parent.layer.alert(type=='0'?"发布成功!":"取消发布成功!", function() {
				selectElisaInfoById(true);
				parent.layer.close(index);
			});
		});
	}
	
}

// td的坐标 isAll是否全部发布 不获取特殊孔位信息
function getElisaInfoForSMP(eiId, tdx, tdy, isAll) {
	
	return evIds;
	
}
function getIndex(x, y) {
	return (transformChars(x) - 1) * 12 + y;
}

function initTable() {
	$("#tdata").empty();
	for (var i = 0, j = 8; i < j; i++) {
		var idname = transToChars(i);
		var trs = "<tr> <td rowspan='2'>" + transToChars(i) + "</td>";
		for (var k = 1; k <= 12; k++) {
			trs += "<td id='td" + idname + k + "'><input type='hidden' id='hid"
					+ idname + k + "' name='h" + idname + k
					+ "'/><input type='hidden' id='h"
					+ idname + k + "' name='h" + idname + k
					+ "'/><lable style='width:110px' id='l" + idname + k
					+ "' name='l" + idname + k + "' ></lable></td>";
		}
		trs += "</tr><tr>";
		for (var k = 1; k <= 12; k++) {
			trs += "<td id='td1"
					+ idname
					+ k
					+ "'><input  style='width:110px' onclick='changeValues(this)' id='t"
					+ idname + k + "' name='t" + idname + k + "'></input></td>";
		}
		trs += "</tr>";
		$("#tdata").append(trs);
	}
	;

}
// 设置跳过该孔
function jumpOver() {
	var x = $("#tdx").val();
	var y = $("#tdy").val();
	var namm = x + y;
	if ($("#t" + namm).prop("disabled")) {
		var index = parent.layer.alert("已经跳过该孔", function() {
			parent.layer.close(index);
		});
		return false;
	}
	if (blkArray.indexOf(namm) != -1) {
		var index = parent.layer.alert("仅能跳过样品孔", function() {
			parent.layer.close(index);
		});
		return false;
	}
	blkArray[blkArray.length] = namm;
	$("#t" + namm).val("");
	$("#l" + namm).prop("disabled", true);
	$("#t" + namm).prop("disabled", true);
	$("#td" + namm).prop("disabled", true);
	$("#td1" + namm).prop("disabled", true);

}
// 将表格点击的表格填进select 中
function changeValues(obj) {
	var x = obj.id.substring(1, 2);
	var y = obj.id.substring(2);
	$("#tdx").val(x);
	$("#tdy").val(y);
}

var template;
// 改变酶标板表格内容
function changeTable() {
	var templateName = $("#templateName").val();
	var createDate = $("#createDate").val();
	if (templateName == "") {
		initTypess();
		initTable1("jiemei");
		initTable();
		initCheckData21("sequenceNumber", "/elisaInfo/selectSNByParams", { "etId" : templateName, "createDate" : createDate }, 4);
		return;
	}
	initCheckData21("qcId", "/suppliesStock/queryTestPage", { "type" : 1, "id": templateName }, 3);
	initCheckData21("reagentId", "/suppliesStock/queryTestPage", {"type" : 0,"id" : templateName }, 3);
	initCheckData21("sequenceNumber", "/elisaInfo/selectSNByParams", { "etId" : templateName, "createDate" : createDate }, 4);
	// sync, url, datatype, datas, func
	simpleAjax("/elisaTemplate/queryElisaTemplateById", {"id" : templateName },
			function(result) {
				var data = result.data;
				template = data;
				// 填充酶标板孔
				var ll = 0;
				var tdata = data.elisaTemplateValues;
				var ddd = 0;
				if(template.arrangement == 1){
					$("#eadbut").val(1);
					for (var i = 1, j = 12; i <= j; i++) {
						for (var k = 0, l = 8; k < l; k++) {
							var namm = transToChars(k) + i;
							if (tdata[ll].type != 1) {
								specialCoordinate[specialCoordinate.length] = tdata[ll].type
										+ ":" + (k + 1) + "-" + i;
							}
							var title = tdata[ll].title.substring(0,
									tdata[ll].title.indexOf(")") + 1);
							var value = tdata[ll].title.substring(tdata[ll].title
									.indexOf(")") + 1, tdata[ll].title.length);
							if (title.indexOf("SMP") == -1) {
								blkArray[ddd] = namm;
								ddd++;
							}
							$("#l" + namm).html(title);
							$("#t" + namm).val(value);
							$("#td" + namm).attr("class", "moad" + tdata[ll].type);
							$("#td1" + namm).attr("class", "moad" + tdata[ll].type);
							ll++;
						}
					}
				}else{
					$("#eadbut").val(0);
					for (var i = 0, j = typess.length; i < j; i++) {
						for (var k = 1, l = typess[i].length; k < l; k++) {
							var namm = transToChars(i) + k;
							if (tdata[ll].type != 1) {
								specialCoordinate[specialCoordinate.length] = tdata[ll].type
										+ ":" + (i + 1) + "-" + k;
							}
							var title = tdata[ll].title.substring(0,
									tdata[ll].title.indexOf(")") + 1);
							var value = tdata[ll].title.substring(tdata[ll].title
									.indexOf(")") + 1, tdata[ll].title.length);
							if (title.indexOf("SMP") == -1) {
								blkArray[ddd] = namm;
								ddd++;
							}
							$("#l" + namm).html(title);
							$("#t" + namm).val(value);
							$("#td" + namm).attr("class", "moad" + tdata[ll].type);
							$("#td1" + namm).attr("class", "moad" + tdata[ll].type);
							ll++;
						}
					}
				}
				

			});

	// 更新其它控件的选择值
	simpleAjax("/elisaInfo/selectSNByParams", {
		"createDate" : createDate
	}, function(result) {
		var data = result.data;
		var items={};
		for(var i=0 , j=data.length;i<j;i++){
			var info = data[i];
			items[info.id] =  {name: ""+info.allSequenceNumber+"["+info.projectName+"]", icon: "copy"};
		}
		/*批量插入*/
		$.contextMenu({
	        selector: '.alogd', 
	        callback: function(key, options) {
	        	doInsertData(key);
				
	        	},
	        items: items
	    });
	})

}
// 标记表格分类
var types = new Array("STD", "SMP", "PC", "PCⅡ", "NC", "QC", "BLK", "");
// BLK平均值
var bcValue = 0;
// 记录模板中特殊孔位信息
var specialCoordinate = new Array();
var typeArray = new Array();
// 初始化一个数组用来保存不能被跳过的孔
var blkArray = new Array();
// 初始化一个二维数组 保存现有的表格
var typess = new Array();
function initTypess() {
	for (var i = 0, j = 8; i < j; i++) {
		typess[i] = new Array();
		for (var k = 0; k < 13; k++) {
			typess[i][k] = "";
			// 初始化 所有的lable和input
		}
	}
}

/* 列表数据显示接口 批量插入*/
$("#batchInsert").click( function() {
			// 先把所有的样品孔还原成初始化一样
			for (var i = 1, j = 12; i <= j; i++) {
				for (var k = 0, l = 8; k < l; k++) {
					var namm = transToChars(k) + i;
					var tdd = $("#l" + namm).text();
					if (tdd.indexOf("SMP") != -1) {
						$("#t" + namm).prop('disabled',false);
						$("#t" + namm).val("");
						$("#h" + namm).val("");
					}
				}
			}
			var id = $("#id").val();
			// 插入方向
			var eadbut = $("#eadbut").val();
			// 判断是否选择了模板
			var templateName = $("#templateName").val();
			if (templateName == '') {
				var index = parent.layer.alert("请先选择一个模板", function() {
					parent.layer.close(index);
				});
				return false;
			}
			// 记录是否开启加入小样号
			var isInsert = false;
			// 抓取表格中的所有记录
			var tab = $("#jiemei").next('.layui-table-view').find(
					'table.layui-table');
			if ($(tab).find("tr").length == 1) {
				var index = parent.layer.alert("没有需要检测的浆员信息", function() {
					parent.layer.close(index);
				});
				return false;
			}
			// 循环表格
			var ll = 0;
			var index = 0;
			var sampleNoArray = new Array();
			// 浆员卡号
			var providerNoArray = new Array();
			// 血型
			var bloodTypeArray = new Array();
			// 姓名
			var nameArray = new Array();
			// 循环找出所有的需要检验的小样号
			$($(tab).find("tr")).each(function(f) { // 遍历 tr
				var isd = $(this).find("td").eq(0).find("div").html();
				var providerNo = $(this).find("td").eq(1).find("div").html();
				var bloodType = $(this).find("td").eq(2).find("div").html();
				var name = $(this).find("td").eq(3).find("div").html();
				var sampleNo = $(this).find("td").eq(4).find("div").html();
				if (undefined == sampleNo || '' == sampleNo) {
				} else {
					if ('' == id || isd == id || isInsert) {
						isInsert = true;
					}
					if (isInsert) {
						sampleNoArray[index] = sampleNo;
						providerNoArray[index] = providerNo;
						bloodTypeArray[index] = bloodType;
						nameArray[index] = name;
						index++;
					}
				}

			});

			// 重置index为0
			index = 0;
			if (eadbut == 0) {
				for (var i = 0, j = typess.length; i < j; i++) {
					for (var k = 1, l = typess[i].length; k < l; k++) {
						var namm = transToChars(i) + k;
						if ($("#t" + namm).prop("disabled"))
							continue;
						var tdd = $("#l" + namm).text();
						if (tdd != '' && undefined != tdd && tdd.indexOf("SMP") != -1) {
							$("#t" + namm).val("");
							$("#t" + namm).prop("disabled", false);
							if (index < sampleNoArray.length) {
								$("#t" + namm).val(sampleNoArray[index]);
								$("#t" + namm).prop("disabled", true);
								$("#h" + namm).val(
										bloodTypeArray[index] + " "
												+ providerNoArray[index] + " "
												+ nameArray[index]);
								index++;
							}
						}
					}
				}
			} else {
				for (var i = 1, j = 12; i <= j; i++) {
					for (var k = 0, l = typess.length; k < l; k++) {
						var namm = transToChars(k) + i;
						if ($("#t" + namm).prop("disabled"))
							continue;
						var tdd = $("#l" + namm).text();
						if (tdd != '' && undefined != tdd && tdd.indexOf("SMP") != -1) {
							$("#t" + namm).val("");
							$("#h" + namm).val("");
							if (index < sampleNoArray.length) {
								$("#t" + namm).val(sampleNoArray[index]);
								$("#h" + namm).val(
										bloodTypeArray[index] + " "
												+ providerNoArray[index] + " "
												+ nameArray[index]);
								index++;
							}
						}
					}
				}
			}
		});

function initTable1(tableId) {
	var cols;
	var allTime = $("#allTime").is(':checked');
	var startTime = $("#startTime").val();
	startTime = startTime+" 00:00:00";
	var endTime = $("#endTime").val();
	endTime = endTime+" 23:59:59";
	if (allTime) {
		startTime = null;
		endTime = null;
	}
	var sampleType = $("#sampleType").val();
	var startSampleNo = $("#startSampleNo").val();
	var endSampleNo = $("#endSampleNo").val();
	cols = [ [ {
		field : 'id',
		title : 'id',
		display : 'none',
		minWidth : '0',
		width : '0',
		type : "space"
	}, {
		field : 'providerNo',
		title : '浆员卡号',
		sort : true,
		align : 'center'
	}, {
		field : 'bloodType',
		title : '血型',
		align : 'center',
		templet : function(d) { // 单元格格式化函数
			return getBloodValue(d.bloodType);
		}
	}, {
		field : 'name',
		title : '姓名',
		sort : true,
		align : 'center'
	}, {
		field : 'sampleNo',
		title : '小样号',
		align : 'center'
	} ] ];
	var data = {
		"sampleType" : sampleType,
		"startTime" : startTime,
		"endTime" : endTime,
		"startSampleNo" : startSampleNo,
		"endSampleNo" : endSampleNo,
		isAssay : null,
		"token" : getToken()
	};
	dataAllAndWHWithPage(tableId, cols, data, '/newCard/queryListsByCondition',
			GetURLInfo() + '', GetURLInfo() + "", [ '500px', '335px' ], false,
			function() {
				initTableOnClick(tableId);
			});
}

function initTableOnClick(tableId) {
	// 表格 tr 单击事件
	var tab = $("#" + tableId).next('.layui-table-view').find(
			'table.layui-table');

	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var id = $(tr).find("td").eq(0).find("div").html();
		var sampleNo = $(tr).find("td").eq(4).find("div").html();
		if (undefined == sampleNo || '' == sampleNo) {
			return false;
		}
		if (undefined == id)
			return false;
		initData(id);
	});
}

function initData(obj1) {
	$("#id").val(obj1);
}

/*!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#qcButton').on('click', function() {

	})
}();*/
!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#haocp').on('click', function() {
		layer.ready(function() {
			layer.open({
				type : 2,
				title : '耗材配置',
				maxmin : false,
				content : '/elisaTemplateSupplies/initPage',
				area : [ '92%', '85%' ],
			})
		});
	})
}();

!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#mobanp').on('click', function() {
		layer.ready(function() {
			perContent = layer.open({
				type : 2,
				title : '模板配置',
				content : '/elisaTemplate/initPage',
				area : [ '100%', '100%' ],
			});
			layer.full(perContent);
		});
	})
}();

/*!function() {
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#printElisa').on('click', function() {
		
	})
}();*/
$(function() {
	document.getElementsByTagName("eadbut").disabled = true;
	$(".buse").attr("disabled", "disabled");

	$(":radio").click(function() {
		if (this.checked) {
			if ($(this).attr("id") == "default1") {
				$("#chaja").show();
				$("#eclaid").hide();
				document.getElementsByTagName("eadbut").disabled = true;
				$(".buse").attr("disabled", "disabled");
				document.getElementsByTagName("eadbut").disabled = true;
				$(".buseS").removeAttr("disabled");
			}
			if ($(this).attr("id") == "default2") {
				$("#chaja").hide(); 
				$("#eclaid").show();
				document.getElementsByTagName("eadbut").disabled = false;
				$(".buse").removeAttr("disabled");
				document.getElementsByTagName("eadbutS").disabled = true;
				$(".buseS").attr("disabled", "disabled");
				initTypess();
				initTable1("jiemei");
				initTable();
				$("#templateName").val("");
			}

		}
	});
});

function initCheckData21(selectId, url, datas, type) {
	simpleAjax(url, datas, function(result) {
		data = result.data;
		$("#" + selectId).empty();
		if (type == 2) {
			$("#" + selectId).append("<option value=''></option>");
		}
		if (type == 1) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ data[i].projectName + "</option>");
			}
		} else if (type == 2) {
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].id + "'>"
								+ data[i].templateName + "</option>");
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
// 初始化选择控件
function initCheckData2(selectId, url, type) {
	initCheckData21(selectId, url, {}, type);
}

// 保存酶标仪保存记录
var elisaValues = new Array();
// 阴阳性比对方式 true 阴<阳, false 阴>阳
var negative = true;

// 读酶标仪
function doElisa() {
	// 获取cutoff 计算公式
	var cutoffFormula = template.cutoffFormula;
	// 获取灰区配置信息
	var gary = template.elisaGrayAreaSettings;
	// 获得灰区阈值
	var garyValue = 0;

	// 获取空白孔平均值
	var blkValue = new Array();
	var templateName = $("#templateName").val();
	if (templateName == '' || templateName == null) {
		var index = parent.layer.alert("请先选择一个模板", function() {
			parent.layer.close(index);
		});
		return false;
	}
	// 检查试剂是否已经选择
	var reagentId = $("#reagentId").val();
	if (reagentId == '' || reagentId == null) {
		var index = parent.layer.alert("请先选择一个试剂", function() {
			parent.layer.close(index);
		});
		return false;
	}
	// 检查质控品是否已经选择
	var qcId = $("#qcId").val();
	if (qcId == '' || qcId == null) {
		var index = parent.layer.alert("请先选择一个质控品", function() {
			parent.layer.close(index);
		});
		return false;
	}

	var creater = $("#creater").val();
	// 表格信息
	var tds = "";
	// 根据纵向和横向来处理表格
	if(template.arrangement==0){ //横向
		for (var i = 0; i < 8; i++) {
			for (var j = 1; j < 13; j++) {
				var namm = transToChars(i) + (j);
				// 判断是否特殊孔位
				if ("" == $("#h" + namm).val() || undefined == $("#h" + namm).val()) {
					var tdText=$("#t" + namm).val();
					if(undefined != tdText && tdText != '' ){
						tds = tds+tdText + "_-1_-1_-1_-1,";
					}else{
						tds = tds + "-1_-1_-1_-1_-1,";
					}
				} else {
					// 血型 浆员卡号 姓名 全登记号不需要
					var tdsss = ($("#h" + namm).val()).split(" ");
					
					// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名 全登记号不需要现在为-1
					if (tdsss.length == 3) {
						tds = tds + $("#t" + namm).val() + "_-1_" + tdsss[1] + "_"
								+ tdsss[0] + "_" + tdsss[2] + ",";
					}
				}

			}
		}
	}else{ // 纵向
		for (var i = 1; i < 13; i++) {
			for (var j = 0; j < 8; j++) {
				var namm = transToChars(j) + (i);
				// 判断是否特殊孔位
				if ("" == $("#h" + namm).val() || undefined == $("#h" + namm).val()) {
					var tdText=$("#t" + namm).val();
					if(undefined != tdText && tdText != '' ){
						tds = tds+tdText + "_-1_-1_-1_-1,";
					}else{
						tds = tds + "-1_-1_-1_-1_-1,";
					}
				} else {
					// 血型 浆员卡号 姓名 全登记号不需要
					var tdsss = ($("#h" + namm).val()).split(" ");
					
					// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名 全登记号不需要现在为-1
					if (tdsss.length == 3) {
						tds = tds + $("#t" + namm).val() + "_-1_" + tdsss[1] + "_"
								+ tdsss[0] + "_" + tdsss[2] + ",";
					}
				}

			}
		}
	}
	
	tds = tds.substring(0, tds.lastIndexOf(","));
	
	var datas = {
		"templateId" : templateName,
		"qcId" : qcId,
		"reagentId" : reagentId,
		"creater" : creater,
		"tds" : tds
	}
	// sync, url, datatype, datas, func
	simpleAjax("/elisaInfo/doElisa", datas, function(result) {
		if(undefined != result.canContinue && null != result.canContinue){
			var index = parent.layer.alert(result.emsg, function() {
				parent.layer.close(index);
			});
		}
		var data = result.data;
		$("#default1").click();
		changeTable();
		$("#default1").click();
		showDataTable(data);
	
	});
}
// 读取酶标板检测数据 elisaInfo读板信息, isALT是否alt检测
function showDataTable(elisaInfo) {
	var isALT = false;
	// 酶标仪读孔信息
	var datass = elisaInfo.elisaValues;
	var tci = elisaInfo.testConfigInfo;
	if(tci.projectName==1){
		isALT = true;
	}
	$("#qcResult").val(elisaInfo.qcResult);
	if(elisaInfo.qcResult ==2){
		parent.layer.alert("本次实验的质控结果为失控 !");
	}
	$("#eiId").val(elisaInfo.id);
	// 获得显示类型
	var eadbutS = $("#eadbutS").val();
	// 是否显示小样号
	var isShowSampleNo = $("#showSampleNo").is(':checked');
	// 是否查询
	var isSearch = $("#default1").attr("checked");
	var arrangement = 0;
	if(elisaInfo.elisaTemplate.arrangement != null){
		arrangement = elisaInfo.elisaTemplate.arrangement;
	}
	var ii = 0;
	if(arrangement == 1){ // 纵向
		for (var i = 1, j = 12; i <= j; i++) {
			for (var k = 0, l = 8; k < l; k++) {
				if (ii >= 96) {
					break;
				}
				elisaValue = datass[(i-1)*8+k];
				ii++;
				var namm = transToChars(k) + i;
				// 取值 -- 浆员名字
				var personName = elisaValue.personName;
				personName = personName=="-1"?"":personName;
				// 取值 -- 血型
				var bloodType = elisaValue.bloodType;
				bloodType = bloodType=="-1"?"":bloodType;
				bloodType = bloodType=="null"?"未知":bloodType;
				bloodType = getBloodValue(bloodType)+"型";
				// 取值 -- 小样号
				var sampleNo = elisaValue.sampleNo;
				// 取值 -- 浆员ID
				var pbId = elisaValue.pbId;
				pbId = pbId=="-1"?"":pbId;
				// 取值 -- 灰区信息
				var judgementResult = elisaValue.judgementResult;
				// 取值 -- od值
				var odValue = elisaValue.odValue;
				// 取值 -- 原始od值
				var originalODValue = elisaValue.originalODValue;
				// 取值 -- 测值
				var value = elisaValue.value;
				// 取值 -- 孔位类型
				var type = elisaValue.type;
				// 如果非特殊孔位并且小样号为空则跳过
				if (("-1" == sampleNo|| "" == sampleNo|| " " == sampleNo) && type == 1 || ("-1" == sampleNo && "" != sampleNo && $("#t" + namm).prop("disabled"))) {
					if(isSearch){
						$("#t" + namm).val("");
						$("#l" + namm).html("");
						$("#td" + namm).prop("class", "moad1");
						$("#td1" + namm).prop("class", "moad1");
					}
					continue;
				}
				// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名 A 100001 李瀚昂 -? 0.008
				if (type == 1) {
					if(sampleNo ==null){
						$("#l" + namm).html("");
						$("#t" + namm) .val("");
						continue;
					}
					$("#hid"+namm).val(elisaValue.id);
					if (eadbutS == 0) {// OD值
						$("#t" + namm).val(odValue);
					} else if (eadbutS == 1) { // 原始OD值
						$("#t" + namm).val(originalODValue);
					} else if (eadbutS == 2) { // 测值
						$("#t" + namm).val(value);
					} else if (eadbutS == 3) { // 评定结果
						$("#t" + namm).val( judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?");
					} else if (eadbutS == 4) { // 详细信息
						if(isALT){
							$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ odValue +" "+ value);
						}else{
							$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ (judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?") + " "+ odValue);
						}
					}
				} else {
					if (eadbutS == 0) {// OD值
						$("#t" + namm).val(odValue);
					} else if (eadbutS == 1) { // 原始OD值
						$("#t" + namm).val(originalODValue);
					} else if (eadbutS == 2) { // 测值
						$("#t" + namm).val(value);
					} else if (eadbutS == 3) { // 评定结果
						$("#t" + namm).val( judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?");
					} else if (eadbutS == 4) { // 详细信息
						if(isALT){
							$("#t" + namm) .val(getTypeName(type) +" "+odValue +" "+ value);
						}else{
							$("#t" + namm).val((judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?") + " " + odValue);
						}
					}
				}
				$("#td" + namm).prop("class", "moad1");
				$("#td1" + namm).prop("class", "moad1");
				if(elisaValue.reportStatus==1){
					$("#td" + namm).prop("class", "moad8" );
					$("#td1" + namm).prop("class", "moad8" );
				}else{
					$("#td" + namm).prop("class", "moad" + elisaValue.type);
					$("#td1" + namm).prop("class", "moad" + elisaValue.type);
				}
				// 设置样式 是否发布 是否阳性
				if(isALT){
					if(value>=25){
						$("#td" + namm).prop("class", "moad9");
						$("#td1" + namm).prop("class", "moad9");
					}
				}else{
					if(elisaValue.judgementResult ==2 ){
						$("#td" + namm).prop("class", "moad9");
						$("#td1" + namm).prop("class", "moad9");
					}else if(""!=sampleNo&&elisaValue.judgementResult ==1 && type == 1){
						$("#td" + namm).prop("class", "moad2");
						$("#td1" + namm).prop("class", "moad2");
					}
				}
				
				if (isShowSampleNo && type == 1) {
					$("#l" + namm).text(sampleNo);
				}
			}
		}
	}else{ // 横向
		for (var i = 0, j = 8; i <= j; i++) {
			for (var k = 1, l = 12; k <= l; k++) {
				if (ii >= 96) {
					break;
				}
				elisaValue = datass[ii];
				ii++;
				var namm = transToChars(i) + k;
				// 取值 -- 浆员名字
				var personName = elisaValue.personName;
				personName = personName=="-1"?"":personName;
				// 取值 -- 血型
				var bloodType = elisaValue.bloodType;
				bloodType = bloodType=="-1"?"":bloodType;
				bloodType = bloodType=="null"?"未知":bloodType;
				bloodType = getBloodValue(bloodType)+"型";
				// 取值 -- 小样号
				var sampleNo = elisaValue.sampleNo;
				// 取值 -- 浆员ID
				var pbId = elisaValue.pbId;
				pbId = pbId=="-1"?"":pbId;
				// 取值 -- 灰区信息
				var judgementResult = elisaValue.judgementResult;
				// 取值 -- od值
				var odValue = elisaValue.odValue;
				// 取值 -- 原始od值
				var originalODValue = elisaValue.originalODValue;
				// 取值 -- 测值
				var value = elisaValue.value;
				// 取值 -- 孔位类型
				var type = elisaValue.type;
				// 如果非特殊孔位并且小样号为空则跳过
				if (("-1" == sampleNo|| "" == sampleNo) && type == 1 || ("-1" == sampleNo && "" != sampleNo && $("#t" + namm).prop("disabled"))) {
					if(isSearch){
						$("#t" + namm).val("");
						$("#l" + namm).html("");
						$("#td" + namm).prop("class", "moad1");
						$("#td1" + namm).prop("class", "moad1");
					}
					continue;
				}
				// 保存页面上表格中的 小样号_全登记号_浆员信息ID_血型_浆员姓名 A 100001 李瀚昂 -? 0.008
				if (type == 1) {
					$("#hid"+namm).val(elisaValue.id);
					if (eadbutS == 0) {// OD值
						$("#t" + namm).val(odValue);
					} else if (eadbutS == 1) { // 原始OD值
						$("#t" + namm).val(originalODValue);
					} else if (eadbutS == 2) { // 测值
						$("#t" + namm).val(value);
					} else if (eadbutS == 3) { // 评定结果
						$("#t" + namm).val( judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?");
					} else if (eadbutS == 4) { // 详细信息
						if(isALT){
							$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ odValue +" "+ value);
						}else{
							$("#t" + namm) .val(""+ bloodType+ " "+ pbId+ " "+ personName+ " "+ (judgementResult == 0 ? "-": judgementResult == 1 ? "+": "-?") + " "+ odValue);
						}
					}
				} else {
					if (eadbutS == 0) {// OD值
						$("#t" + namm).val(odValue);
					} else if (eadbutS == 1) { // 原始OD值
						$("#t" + namm).val(originalODValue);
					} else if (eadbutS == 2) { // 测值
						$("#t" + namm).val(value);
					} else if (eadbutS == 3) { // 评定结果
						$("#t" + namm).val( judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?");
					} else if (eadbutS == 4) { // 详细信息
						if(isALT){
							$("#t" + namm) .val(getTypeName(type) +" "+odValue +" "+ value);
						}else{
							$("#t" + namm).val((judgementResult == 0 ? "-" : judgementResult == 1 ? "+" : "-?") + " " + odValue);
						}
					}
				}
				$("#td" + namm).prop("class", "moad1");
				$("#td1" + namm).prop("class", "moad1");
				if(elisaValue.reportStatus==1){
					$("#td" + namm).prop("class", "moad8" );
					$("#td1" + namm).prop("class", "moad8" );
				}else{
					$("#td" + namm).prop("class", "moad" + elisaValue.type);
					$("#td1" + namm).prop("class", "moad" + elisaValue.type);
				}
				// 设置样式 是否发布 是否阳性
				if(isALT){
					if(value>=25){
						$("#td" + namm).prop("class", "moad9");
						$("#td1" + namm).prop("class", "moad9");
					}
				}else{
					if(elisaValue.judgementResult ==2 ){
						$("#td" + namm).prop("class", "moad9");
						$("#td1" + namm).prop("class", "moad9");
					}else if(""!=sampleNo&&elisaValue.judgementResult ==1 && type == 1){
						$("#td" + namm).prop("class", "moad2");
						$("#td1" + namm).prop("class", "moad2");
					}
				}
				
				if (isShowSampleNo && type == 1) {
					$("#l" + namm).text(sampleNo);
				}
			}
		}
	}
}

function doInsertData(eiId){
	var datas = {"id":eiId};
	simpleAjax("/elisaInfo/selectElisaInfoById", datas, function(result) {
		var data = result.data;
		var evs = data.elisaValues;
		var index =0;
		for(var i =0;i<8;i++){
			for(var j = 1;j<=12;j++){
				var namm = transToChars(i) + j;
				if("-1" != evs[index].sampleNo && "" != evs[index].sampleNo){
					$("#t" + namm).val(evs[index].sampleNo);
					$("#t" + namm).prop("disabled",true);
				}else{
					$("#t" + namm).val("");
					$("#t" + namm).prop("disabled",false);
				}
					//$("#h" + namm).val( bloodTypeArray[index] + " " + providerNoArray[index] + " " + nameArray[index]);
					$("#h" + namm).val( evs[index].bloodType + " " + evs[index].pbId + " " + evs[index].personName);
					index++;
				}
			}
	});
}
function initAllSENO(){
	simpleAjax("/elisaInfo/getAllSequenceNumber", datas, function(result) {
		var data = result.data;
	});
}
function getTypeName(type){
	if (type == 0) {
		return "标准品";
	} else if (type == 5) {
		return "质检";
	} else {
		return "";
	}
}

function del(){
	var id = $("#sequenceNumber").val();
	if(id==undefined || id==""){
		id=$("#eiId").val();
	}
	if(id==undefined || id==""){
		var index = parent.layer.alert("没有酶标板读板信息", function() {
			parent.layer.close(index);
		});
		return false;
	} 
	var datas = {"id":id};
	simpleAjax("/elisaInfo/delById", datas, function(result) {
		var index = parent.layer.alert("删除成功", function() {
			parent.layer.close(index);
			changeTable();
		});
	});
	
}
// 酶标板打印
function doPrintElisa(type){
	var id = $("#sequenceNumber").val();
	if(id==undefined || id==""){
		id=$("#eiId").val();
	}
	if(id==undefined || id==""){
		var index = parent.layer.alert("没有酶标板读板信息", function() {
			parent.layer.close(index);
		});
		return false;
	} 
	var eadbutS  = $("#eadbutS").val();
	var showSampleNo  = $("#showSampleNo").val();
	var url = "/elisaReport/printElisa";
	layer.ready(function() {
		perContent = layer.open({
			type : 2,
			title : '酶标板打印',
			content : url+'?id='+id+"&eadbutS="+eadbutS+"&showSampleNo="+showSampleNo+"&type="+type,
			area : [ '100%', '100%' ],
		});
		layer.full(perContent);
	});
}
function printQCReport(type){
	layer.ready(function() {
		layer.open({
			type : 2,
			title : '酶标仪基础参数配置',
			maxmin : false,
			content : '/elisaReport/printQCReportForTable?type='+type,
			area : [ '100%', '100%' ],
		})
	});
}
/*板次打印*/
$(function(){
	$.contextMenu({
        selector: '.jqDnR', 
        callback: function(key, options) {
			if(key=='edit'){
				doPrintElisa(1);
			}
			if (key == 'publish') {
				doPrintElisa(2);
			}
        },
        items: {
            "edit": {name: "打印1", icon: "copy"},
            "publish": {name: "打印2", icon: "copy"}
        }
    });
	/*质控报表
	$.contextMenu({
        selector: '.pcboar', 
        callback: function(key, options) {
			if(key=='edit'){
				printQCReport(1);
			}
			if (key == 'publish') {
				printQCReport(2);
			}
        	},
        items: {
            "edit": {name: "打印1", icon: "copy"},
            "publish": {name: "打印2", icon: "copy"}
        }
    });*/
})

