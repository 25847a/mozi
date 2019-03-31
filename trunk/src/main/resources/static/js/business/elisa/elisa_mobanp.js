$(function() {
	initCheckData2("projectName", "/elisaApi/queryAllProjects", 1);
	initCheckData2("templateNameT", "/elisaTemplate/queryTemplates", 2);
	initCheckData({ 'type' : "elisa_check_project" }, "projectId");
	initTable();
	initTypess();
	$("input[name='vehicle']").bind("click", function() {
		// 选择不同的类型则清空缓存表格ID
		tmpId = null;
	});

	// 加载所有检测配置
	$("#addFrom").validate(
			{
				rules : {
				// templateName : {
				// required : true,
				// rangelength : [2,20]
				// },
				// cutoffFormula :{
				// required : true
				// }
				},
				messages : {
				// templateName : {
				// required : "请输入模板名称",
				// rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串")
				// },
				// cutoffFormula :{
				// required : "Cutoff值计算公式不能为空"
				// }
				},
				submitHandler : function(from) {
					var templateName = $("#templateName").val();
					if (!validateNotNUll(templateName, "模板名称")) {
						return false;
					}
					var cutoffFormula = $("#cutoffFormula").val();
					if (!validateNotNUll(cutoffFormula, "Cutoff值计算公式")) {
						return false;
					}
					// 检测项目检验
					var projectName = $("#projectName").val();
					if (!validateNotNUll(projectName, "项目代号")) {
						return false;
					}
					// 计量方式检验
					var ovCount = 0;
					$(".optionsValue").each(
							function() {
								if (this.checked) {
									ovCount = 1
									if (this.value == 2) {
										$(".emmstd").each(
												function() {
													if (!validateNotNUll(
															this.value,
															"标准曲线STD值")) {
														return false;
													}

												});
									}
								}
							});
					if (ovCount != 1) {
						var index = parent.layer.alert("请选择一个计量方式", function() {
							parent.layer.close(index);
							return false;
						});

					}
					// 灰区设置检验
					ovCount = 0;
					$(".egaOptionsValue").each(
							function() {
								if (this.checked) {
									ovCount = 1;
									// 常规
									if (this.value == 0) {
										var conventionalValue = $(
												"#conventionalValue").val();
										validateFixedValue(conventionalValue,
												"灰区设置的常规值");
									} else if (this.value == 1) { // 比例
										var proportionValue = $(
												"#proportionValue").val();
										validateFixedValue(proportionValue,
												"灰区设置的比例值");
									} else { // 定值
										var gasMinValue = $("#gasMinValue")
												.val();
										validateFixedValue(gasMinValue,
												"灰区设置的定值最小值");
										var gasMaxValue = $("#gasMaxValue")
												.val();
										validateFixedValue(gasMaxValue,
												"灰区设置的定值最大值");
										if (gasMaxValue <= gasMinValue) {
											var index = parent.layer.alert(
													"灰区设置的定值最大值必须大于最小值",
													function() {
														parent.layer
																.close(index);
														return false;
													});
										}
									}
								}
							});
					if (ovCount != 1) {
						var index = parent.layer.alert("请选择一个计量方式", function() {
							parent.layer.close(index);
							return false;
						});

					}
					// 阴性阈值检测
					if ($("#useNegativeThreshold").get(0).checked) {
						$("#useNegativeThreshold").val("1");
						if (!validateNotNUll(
								$("#negativeThresholdValue").val(), "阴性阈值")) {
							return false;
						}
						// 如果选用固定值则固定值也需要验证 开始----
						if ($("#negativemaxValue").val() == 0) {
							var escnegativemaxValue = $("#escnegativemaxValue")
									.val();
							if (!validateFixedValue(escnegativemaxValue,
									"阴性阈值上限固定值"))
								return false;
						}
						if ($("#negativeMinValue").val() == 0) {
							var ecsnegativeMinValue = $("#ecsnegativeMinValue")
									.val();
							if (!validateFixedValue(ecsnegativeMinValue,
									"阴性阈值下限固定值"))
								return false;
						}

						// 固定值验证结束----
					}

					// 阳性阈值检测
					if ($("#usePositiveThreshold").get(0).checked) {
						$("#usePositiveThreshold").val("1");
						if (!validateNotNUll(
								$("#positiveThresholdValue").val(), "阳性阈值")) {
							return false;
						}

						// 如果选用固定值则固定值也需要验证 开始----
						if ($("#positiveMaxValue").val() == 0) {
							var ecspositiveMaxValue = $("#ecspositiveMaxValue")
									.val();
							if (!validateFixedValue(ecspositiveMaxValue,
									"阳性阈值上限固定值"))
								return false;
						}
						if ($("#positiveMinValue").val() == 0) {
							var ecspositiveMinValue = $("#ecspositiveMinValue")
									.val();
							if (!validateFixedValue(ecspositiveMinValue,
									"阳性阈值下限固定值"))
								return false;
						}
						// 固定值验证结束----
					}
					// 检测有效性 检查
					if ($("#detectionEffectiveness").get(0).checked) {
						$("#detectionEffectiveness").val("1");
						if ($("#negativeMeanOperator").val() != 1000) {
							var negativeMeanValue = $("#negativeMeanValue")
									.val();
							if (!validateFixedValue(negativeMeanValue, "阴性均值"))
								return false;
						}
						if ($("#negativeHoleOperator").val() != 1000) {
							var negativeHoleValue = $("#negativeHoleValue")
									.val();
							if (!validateFixedValue(negativeHoleValue, "阴性孔值"))
								return false;
						}
						if ($("#positive2MeanOperator").val() != 1000) {
							var positive2MeanValue = $("#positive2MeanValue")
									.val();
							if (!validateFixedValue(positive2MeanValue,
									"阳性II均值"))
								return false;
						}
						if ($("#positiveMeanOperator").val() != 1000) {
							var positiveMeanValue = $("#positiveMeanValue")
									.val();
							if (!validateFixedValue(positiveMeanValue, "阳性均值"))
								return false;
						}
						//				
						if ($("#positiveHoleOperator").val() != 1000) {
							var positiveHoleValue = $("#positiveHoleValue")
									.val();
							if (!validateFixedValue(positiveHoleValue, "阳性孔值"))
								return false;
						}
						//				
						if ($("#positive2HoleOperator").val() != 1000) {
							var positive2HoleValue = $("#positive2HoleValue")
									.val();
							if (!validateFixedValue(positive2HoleValue,
									"阳性II孔值"))
								return false;
						}
						if ($("#blankMeanOperator").val() != 1000) {
							var blankMeanValue = $("#blankMeanValue").val();
							if (!validateFixedValue(blankMeanValue, "空白均值"))
								return false;
						}
						if ($("#blankHoleOperator").val() != 1000) {
							var blankHoleValue = $("#blankHoleValue").val();
							if (!validateFixedValue(blankHoleValue, "空白孔值"))
								return false;
						}
					}
					// 将酶标板信息组合成集合放入一个隐藏表单
					initetvs();
					// 设置检测项目的apiId
					$("#apiId").val(projectName);
					// 设置阴性阈值和阳性阈值

					// 最后才提交
					$("#addFrom").ajaxSubmit(
							{
								type : 'POST',
								url : GetURLInfo() + "insertElisaTemplate",
								success : function(result) {
									var index = parent.layer.alert(
											result.message, function() {
												if (1 == result.code) {
													parent.layer.close(index);
												} else {
													parent.layer.closeAll();
													location.reload();
												}
											});
								},
								error : function() {
								}
							});
				}
			});

});
// 是否使用上限为Cutoff值
$("#isMaxWithCutOff").click(function() {
	if (!this.checked) {
		$("#showSpan1").show();
		$("#showSpan2").show();
	} else {
		$("#showSpan1").hide();
		$("#showSpan2").hide();
	}
})
// 控制固定值显示
function showText(obj, textId) {
	if (1000 == $(obj).val()) {
		$("#" + textId).hide();
	} else {
		$("#" + textId).show();
	}
}

// 检测固定阈值信息 包含非空, 数字验证
function validateNotNUll(value, msg) {
	if (value === "" || value == undefined) {
		var index = parent.layer.alert(msg + "不能为空", function() {
			parent.layer.close(index);
		});
		return false;
	}
	return true;
}
// 检测固定阈值信息 包含非空, 数字验证
function validateFixedValue(value, msg) {

	if (!validateNotNUll(value, msg)) {
		return false;
	}

	if (isNaN(value)) {
		var index = parent.layer.alert(msg + "只能为数字", function() {
			parent.layer.close(index);
		});
		return false;
	}
	if (Number(value) < 0) {
		var index = parent.layer.alert(msg + "不能小于0", function() {
			parent.layer.close(index);
		});
		return false;
	}
	return true;
}

// 获取项目集合信息
function initCheckData2(selectId, url, type) {
	$.ajax({
		type : "POST",
		data : {},
		url : url,
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#" + selectId).empty();
			$("#" + selectId).append("<option value=''></option>");
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
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});

}

function initTable() {
	$("#tdata").empty();
	for (var i = 0, j = 8; i < j; i++) {
		var trs = "<tr> <td >" + transToChars(i) + "</td>";
		for (var k = 1; k <= 12; k++) {
			trs += "<td id='td1"
					+ transToChars(i)
					+ k
					+ "'>"
					+ "<input readonly='readonly' ondblclick='dbchangeValues(this)' "
					+ "style='width:110px' onclick='changeValues(this)' "
					+ "id='t" + transToChars(i) + k + "' name='t"
					+ transToChars(i) + k + "'>" + "</input></td>";

		}
		trs += "</tr>"
		$("#tdata").append(trs);

	}
	;

}
function dbchangeValues(obj) {
	var projectName = $("#projectName").val();
	if (projectName == "") {
		var index = parent.layer.alert("请先选择一个项目代号!", function() {
			parent.layer.close(index);
		});
		tmpId = null;
		return;
	}

	// 获取选择的酶标板标本类型
	var vehicle = $("input[name='vehicle']:checked").val();
	var this1 = thisId.substring(1, 2);
	var this2 = thisId.substring(2);
	var namm = this1 + this2;
	$("#t" + namm).val(types[vehicle]);
	$("#td1" + namm).attr("class", "moad" + vehicle);
	initLable(vehicle);
	tmpId = null;
}
// 连续选择表格
var tmpId = null;
function changeValues(obj) {
	thisId = obj.id;
	if (null == tmpId) {
		tmpId = thisId;
		return;
	}
	if (tmpId == thisId) {
		return;
	}

	// 获取选择的酶标板标本类型
	var vehicle = $("input[name='vehicle']:checked").val();
	var projectName = $("#projectName").val();
	var pn = $("#projectName").find("option:selected").text();
	if (projectName == "") {
		var index = parent.layer.alert("请先选择一个项目代号!", function() {
			parent.layer.close(index);
		});
		tmpId = null;
		return;
	}
	// 计算tmpId和this.id相差多少个格子
	var tmp1 = tmpId.substring(1, 2);
	var tmp2 = tmpId.substring(2);
	var this1 = thisId.substring(1, 2);
	var this2 = thisId.substring(2);
	var arrangement = $("#arrangement").val();
	if (tmp1 > this1) {
		var tt1 = tmp1;
		var tt2 = tmp2;
		tmp1 = this1;
		tmp2 = this2;
		this1 = tt1;
		this2 = tt2;
	}
	if (arrangement == 1) { // 纵向
		// 如果跨列则需要单独处理
		if (tmp2 != this2) {
			
			// 循环赋值
			for (var i = 0, j = 12; i < j; i++) {
				for (k = 0, l = 8; k < l; k++) {
					if ((i + 1)>=tmp2 && (i + 1) <= this2) {
						if ((i + 1) == tmp2 && transToChars(k) >= tmp1) {
							var namm = transToChars(k) + (i + 1);
							$("#t" + namm).val(types[vehicle]);
							$("#td1" + namm).attr("class", "moad" + vehicle);
						}else  if((i + 1)==this2 && transToChars(k) <= this1){
							var namm = transToChars(k) + (i + 1);
							$("#t" + namm).val(types[vehicle]);
							$("#td1" + namm).attr("class", "moad" + vehicle);
						}else if((i + 1)>tmp2 && (i + 1) < this2  ){
							var namm = transToChars(k) + (i + 1);
							$("#t" + namm).val(types[vehicle]);
							$("#td1" + namm).attr("class", "moad" + vehicle);
						}
					}

				}
			}
		} else {
			for (var i = 0, j = 11; i < j; i++) {
					for (k = 0, l = 8; k <= l; k++) {
						if (transToChars(k) >= tmp1 && transToChars(k) <= this1) {
							if ((i + 1) >= tmp2 && (i + 1) <= this2) {
								var namm = transToChars(k) + (i + 1);
								$("#t" + namm).val(types[vehicle]);
								$("#td1" + namm).attr("class", "moad" + vehicle);
							}
					}
				}
			}
		}
	} else {

		// 如果跨行则需要单独处理
		if (tmp1 != this1) {
			
			// 循环赋值
			for (var i = 0, j = typess.length; i < j; i++) {
				if (transToChars(i) >= tmp1 && transToChars(i) <= this1) {
					for (k = 0, l = typess[i].length; k < l; k++) {
						if (transToChars(i) == tmp1 || transToChars(i) == this1) {
							if (transToChars(i) == tmp1) {
								if ((k + 1) >= tmp2) {
									var namm = transToChars(i) + (k + 1);
									$("#t" + namm).val(types[vehicle]);
									$("#td1" + namm).attr("class",
											"moad" + vehicle);
								}
							} else {
								if ((k + 1) <= this2) {
									var namm = transToChars(i) + (k + 1);
									$("#t" + namm).val(types[vehicle]);
									$("#td1" + namm).attr("class",
											"moad" + vehicle);
								}
							}
						} else {
							var namm = transToChars(i) + (k + 1);
							$("#t" + namm).val(types[vehicle]);
							$("#td1" + namm).attr("class", "moad" + vehicle);
						}

					}
				}
			}
		} else {
			for (var i = 0, j = typess.length; i < j; i++) {
				if (transToChars(i) >= tmp1 && transToChars(i) <= this1) {
					for (k = 0, l = typess[i].length; k <= l; k++) {
						if ((k + 1) >= tmp2 && (k + 1) <= this2) {
							var namm = transToChars(i) + (k + 1);
							$("#t" + namm).val(types[vehicle]);
							$("#td1" + namm).attr("class", "moad" + vehicle);
						}
					}
				}
			}
		}
	}
	initLable(vehicle);
	//
	// 做完一个选择后 需要将临时变量置为null
	tmpId = null;
}
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
var types = new Array("STD", "SMP", "PC", "PCⅡ", "NC", "QC", "BLK", "");
var typeArray = new Array();
// 酶标板项目设置
layui.use([ 'layer' ], function() {
	!function() {
		// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		$('#ealoshemb').on('click', function() {
			layer.ready(function() {
				layer.open({
					type : 2,
					title : '酶标板项目设置',
					maxmin : false,
					content : '/elisaApi/initPage',
					area : [ '700px', '420px' ],
				})
			});
		})
	}();
});

function init() {
	var templateName1 = $("#templateNameT").val();
	// sync, url, datatype, datas, func
	simpleAjax(
			"/elisaTemplate/queryElisaTemplateById",
			{
				"id" : templateName1
			},
			function(result) {
				var data = result.data;
				$("#id").val(data.id);
				$("#apiId").val(data.apiId);
				$("#projectName").val(data.apiId);
				$("#projectId").val(data.projectId);

				$("#cutoffFormula").val(data.cutoffFormula);
				$("#templateName").val(data.templateName);
				$(".optionsValue")
						.each(
								function() {
									if (this.value == data.elisaMeasurementMethod.optionsValue) {
										this.checked = true;
									}
								});
				$("#std1").val(data.elisaMeasurementMethod.std1);
				$("#std2").val(data.elisaMeasurementMethod.std2);
				$("#std3").val(data.elisaMeasurementMethod.std3);
				$("#std4").val(data.elisaMeasurementMethod.std4);
				$("#std5").val(data.elisaMeasurementMethod.std5);
				$(".egaOptionsValue").each(function() {
					if (this.value == data.elisaGrayAreaSettings.optionsValue) {
						this.checked = true;
					}
				});
				$("#proportionValue").val(
						data.elisaGrayAreaSettings.proportionValue);
				$("#conventionalValue").val(
						data.elisaGrayAreaSettings.conventionalValue);
				$("#gasMinValue").val(data.elisaGrayAreaSettings.minValue);
				$("#gasMaxValue").val(data.elisaGrayAreaSettings.maxValue);
				// if(1==data.elisaGrayAreaSettings.isMaxWithCutOff){
				$("#isMaxWithCutOff").val(
						data.elisaGrayAreaSettings.isMaxWithCutOff);
				// }
				$("#elisaReport_reportSignature").val(
						data.elisaReport.reportSignature);
				$("#elisaReport_reportName").val(data.elisaReport.reportName);
				$("#elisaReport_reportNO").val(data.elisaReport.reportNO);
				$("#elisaReport_inspectionUnit").val(
						data.elisaReport.inspectionUnit);
				$("#elisaReport_inspectionSpecimen").val(
						data.elisaReport.inspectionSpecimen);
				$("#elisaReport_detectionUnit").val(
						data.elisaReport.detectionUnit);
				$("#elisaReport_elisaEquipmentAndManufacturers").val(
						data.elisaReport.elisaEquipmentAndManufacturers);
				$("#elisaReport_cleaningEquipmentAndManufacturers").val(
						data.elisaReport.cleaningEquipmentAndManufacturers);
				$("#elisaReport_qualityControlProducts").val(
						data.elisaReport.qualityControlProducts);
				$("#elisaReport_useWavelength").val(
						data.elisaReport.useWavelength);
				$("#elisaReport_criticalResult").val(
						data.elisaReport.criticalResult);
				$("#elisaReport_qualityControlNO").val(
						data.elisaReport.qualityControlNO);
				$("#elisaReport_effectiveDate").val(
						data.elisaReport.effectiveDate);
				if (data.elisaCommonSetting.useNegativeThreshold == 1) {
					$("#useNegativeThreshold").prop("checked", true);
				} else {
					$("#useNegativeThreshold").prop("checked", false);
				}
				$("#negativeThresholdValue").val(
						data.elisaCommonSetting.negativeThresholdValue);
				// 使用阴性阈值上限
				$("#elisaCommonSetting_negativeMaxOperator").val(
						data.elisaCommonSetting.negativeMaxOperator);
				if (data.elisaCommonSetting.negativemaxValue != "1000") {
					$("#negativemaxValue").val("0");
					$("#ecsnegativemaxValue").show();
					$("#ecsnegativemaxValue").val(
							data.elisaCommonSetting.ecsnegativemaxValue);
				} else {
					$("#negativemaxValue").val("1000");
					$("#ecsnegativemaxValue").hide();
				}
				// 使用阴性阈值下限
				$("#elisaCommonSetting_negativeMinOperator").val(
						data.elisaCommonSetting.negativeMinOperator);
				if (data.elisaCommonSetting.negativeMinValue != "1000") {
					$("#negativeMinValue").val("0");
					$("#ecsnegativeMinValue").show();
					$("#ecsnegativeMinValue").val(
							data.elisaCommonSetting.ecsnegativeMinValue);
				} else {
					$("#negativeMinValue").val("1000");
					$("#ecsnegativeMinValue").hide();
				}

				if (data.elisaCommonSetting.usePositiveThreshold == 1) {
					$("#usePositiveThreshold").prop("checked", true);
				} else {
					$("#usePositiveThreshold").prop("checked", false);
				}
				$("#positiveThresholdValue").val(
						data.elisaCommonSetting.positiveThresholdValue);
				$("#elisaCommonSetting_positiveMaxOperator").val(
						data.elisaCommonSetting.positiveMaxOperator);
				if (data.elisaCommonSetting.positiveMaxValue != "1000") {
					$("#positiveMaxValue").val("0");
					$("#ecspositiveMaxValue").show();
					$("#ecspositiveMaxValue").val(
							data.elisaCommonSetting.ecspositiveMaxValue);
				} else {
					$("#positiveMaxValue").val("1000");
					$("#ecspositiveMaxValue").hide();
				}
				$("#elisaCommonSetting_positiveMinOperator").val(
						data.elisaCommonSetting.positiveMinOperator);
				if (data.elisaCommonSetting.positiveMinValue != "1000") {
					$("#positiveMinValue").val("0");
					$("#ecspositiveMinValue").show();
					$("#ecspositiveMinValue").val(
							data.elisaCommonSetting.ecspositiveMinValue);
				} else {
					$("#positiveMinValue").val("1000");
					$("#ecspositiveMinValue").hide();
				}
				// 检测有效性
				if (data.elisaCommonSetting.detectionEffectiveness == 1) {
					$("#detectionEffectiveness").prop("checked", true);
				} else {
					$("#detectionEffectiveness").prop("checked", false);
				}
				// 阴性均值
				if (data.elisaCommonSetting.negativeMeanOperator != "1000") {
					$("#negativeMeanOperator").val(
							data.elisaCommonSetting.negativeMeanOperator);
					$("#negativeMeanValue").show();
					$("#negativeMeanValue").val(
							data.elisaCommonSetting.negativeMeanValue);
				} else {
					$("#negativeMeanOperator").val("1000");
					$("#negativeMeanValue").hide();
				}
				// 阴性孔值：
				if (data.elisaCommonSetting.negativeHoleOperator != "1000") {
					$("#negativeHoleOperator").val(
							data.elisaCommonSetting.negativeHoleOperator);
					$("#negativeHoleValue").show();
					$("#negativeHoleValue").val(
							data.elisaCommonSetting.negativeHoleValue);
				} else {
					$("#negativeHoleOperator").val("1000");
					$("#negativeHoleValue").hide();
				}
				// 阳性II均值
				if (data.elisaCommonSetting.positive2MeanOperator != "1000") {
					$("#positive2MeanOperator").val(
							data.elisaCommonSetting.positive2MeanOperator);
					$("#positive2MeanValue").show();
					$("#positive2MeanValue").val(
							data.elisaCommonSetting.positive2MeanValue);
				} else {
					$("#positive2MeanOperator").val("1000");
					$("#positive2MeanValue").hide();
				}
				// 阳性均值
				if (data.elisaCommonSetting.positiveMeanOperator != "1000") {
					$("#positiveMeanOperator").val(
							data.elisaCommonSetting.positiveMeanOperator);
					$("#positiveMeanValue").show();
					$("#positiveMeanValue").val(
							data.elisaCommonSetting.positiveMeanValue);
				} else {
					$("#positiveMeanOperator").val("1000");
					$("#positiveMeanValue").hide();
				}
				// 阳性孔值
				if (data.elisaCommonSetting.positiveHoleOperator != "1000") {
					$("#positiveHoleOperator").val(
							data.elisaCommonSetting.positiveHoleOperator);
					$("#positiveHoleValue").show();
					$("#positiveHoleValue").val(
							data.elisaCommonSetting.positiveHoleValue);
				} else {
					$("#positiveHoleOperator").val("1000");
					$("#positiveHoleValue").hide();
				}
				// 阳性II孔值
				if (data.elisaCommonSetting.positive2HoleOperator != "1000") {
					$("#positive2HoleOperator").val(
							data.elisaCommonSetting.positive2HoleOperator);
					$("#positive2HoleValue").show();
					$("#positive2HoleValue").val(
							data.elisaCommonSetting.positive2HoleValue);
				} else {
					$("#positive2HoleOperator").val("1000");
					$("#positive2HoleValue").hide();
				}
				// 空白均值
				if (data.elisaCommonSetting.blankMeanOperator != "1000") {
					$("#blankMeanOperator").val(
							data.elisaCommonSetting.blankMeanOperator);
					$("#blankMeanValue").show();
					$("#blankMeanValue").val(
							data.elisaCommonSetting.blankMeanValue);
				} else {
					$("#blankMeanOperator").val("1000");
					$("#blankMeanValue").hide();
				}
				// 空白孔值
				if (data.elisaCommonSetting.blankHoleOperator != "1000") {
					$("#blankHoleOperator").val(
							data.elisaCommonSetting.blankHoleOperator);
					$("#blankHoleValue").show();
					$("#blankHoleValue").val(
							data.elisaCommonSetting.blankHoleValue);
				} else {
					$("#blankHoleOperator").val("1000");
					$("#blankHoleValue").hide();
				}
				// 填充酶标板孔
				var ll = 0;
				var tdata = data.elisaTemplateValues;
				if(data.arrangement ==1){
					for (var i = 1, j = 12; i <= j; i++) {
						for (var k = 0, l = 8; k < l; k++) {
							var namm = transToChars(k) + i;
							var td = $("#t" + namm).val(tdata[ll].title);
							$("#td1" + namm).attr("class", "moad" + tdata[ll].type);
							ll++;
						}
					}
				}else{
					for (var i = 0, j = typess.length; i < j; i++) {
						for (var k = 1, l = typess[i].length; k < l; k++) {
							var namm = transToChars(i) + k;
							var td = $("#t" + namm).val(tdata[ll].title);
							$("#td1" + namm).attr("class", "moad" + tdata[ll].type);
							ll++;
						}
					}
				}
				
				$("#isAuto").val(data.isAuto);
				$("#arrangement").val(data.arrangement);
				
			});
	initSupplies();
}

function initLable(typesValue) {
	var pn = $("#projectName").find("option:selected").text();
	var ll = 1;
	var lsls = 1;
	var arrangement = $("#arrangement").val();
	if (arrangement == 1) { // 纵向
		for (var i = 0, j = 12; i <= j; i++) {
			for (var k = 0, l = 8; k <= l; k++) {
				var namm = transToChars(k) + (i + 1);
				var td = $("#t" + namm).val();
				if (td == types[typesValue]) {
					$("#t" + namm).val(types[typesValue] + ll + "(" + pn + ")");
					if (typesValue == 1) {
						$("#t" + namm).val(
								types[typesValue] + ll + "(" + pn + ")  " + lsls);

						lsls++;
					}
					ll++;
				}
			}
		}
	}else{
		for (var i = 0, j = typess.length; i <= j; i++) {
			for (var k = 0, l = typess[i].length; k <= l; k++) {
				var namm = transToChars(i) + (k + 1);
				var td = $("#t" + namm).val();
				if (td == types[typesValue]) {
					$("#t" + namm).val(types[typesValue] + ll + "(" + pn + ")");
					if (typesValue == 1) {
						$("#t" + namm).val(
								types[typesValue] + ll + "(" + pn + ")  " + lsls);

						lsls++;
					}
					ll++;
				}
			}
		}
	}
	

}

function initetvs() {
	var etvs = "";
	var count = 1;
	var arrangement = $("#arrangement").val();
	if(arrangement ==1 ){
		for (var i = 1, j = 12; i <= j; i++) {
			for (var k = 0; k < 8; k++) {
				var tempY = transToChars(k);
				etvs += count + ":" + ($("#t" + tempY + i).val()) + ",";
				count++;
			}
		}
		if (etvs.length != 0) {
			etvs = etvs.substring(0, etvs.length - 1);
		}
	}else{
		for (var i = 0, j = typess.length; i < j; i++) {
			var tempY = transToChars(i);
			for (var k = 1; k <= 12; k++) {
				etvs += count + ":" + ($("#t" + tempY + k).val()) + ",";
				count++;
			}
		}
		if (etvs.length != 0) {
			etvs = etvs.substring(0, etvs.length - 1);
		}
	}
	
	$("#etvs").val(etvs);
}
$("#delById").click(function() {
	var id = $("#id").val();
	simpleAjax("/elisaTemplate/delElisaTemplateById", {
		"id" : id
	}, function(result) {
		if (result.code = -1) {
			var index = parent.layer.alert("删除成功", function() {
				parent.layer.close(index);
			});
		}
	});
});
$("#resetFrom").click(function() {
	document.getElementById("addFrom").reset();
	$("#cleanUp").click();
});
$("#cleanUp").click(function() {
	for (var i = 0, j = 8; i < j; i++) {
		for (var k = 0, l = 12; k < l; k++) {
			var namm = transToChars(i) + (k + 1);
			$("#t" + namm).val("");
			$("#td1" + namm).attr("class", "");
		}
	}
});
// 获取程序模式配置值
function initCheckData(datas, selectId) {
	$.ajax({
		type : "POST",
		data : datas,
		url : "/config/queryDictListByType",
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#" + selectId).empty();
			$("#" + selectId).append( "<option value=''></option>");
			for (var i = 0, j = data.length; i < j; i++) {
				$("#" + selectId).append(
						"<option value='" + data[i].value + "'>"
								+ data[i].lable + "</option>");
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

function initSupplies(){
	var proId = $("#projectId").val();
	var url = "/elisaTemplateSupplies/querySuppliesList";
	var datas = {"projectName":proId};
	simpleAjax(url, datas, function(result) {
		var info = result.data;
		$("#reagentId").empty();
		for (var i = 0, j = info.length; i < j; i++) {
			$("#reagentId").append(
					"<option value='" + info[i].id + "'>"
							+ info[i].name + "</option>");
		}
	});
	var url = "/elisaTemplateSupplies/queryQCList";
	var datas = {"checkProject":proId};
	simpleAjax(url, datas, function(result) {
		var info = result.data;
		$("#qcId").empty();
		for (var i = 0, j = info.length; i < j; i++) {
			$("#qcId").append(
					"<option value='" + info[i].id + "'>"
							+ info[i].name + "</option>");
		}
	});
}