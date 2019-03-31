$(function() {
	// 加载所有检测配置
	$.validator
			.setDefaults({
				submitHandler : function() {
					if (Number($("#unfixedProteinMin").val()) >= Number($("#unfixedProteinMax").val()) ) {
						 parent.layer.alert("范围值最大值必须大于最小值");
						return false;

					}
					if (Number($("#fixedProteinMin").val()) >= Number($("#fixedProteinMax").val()) ) {
						 parent.layer.alert("范围值最大值必须大于最小值");
						return false;
					}
					$("#addForm").ajaxSubmit(
							{
								type : 'POST',
								url : GetURLInfo()
										+ "updateTestConfigWithProtein",
								success : function(result) {
									parent.layer.alert(result.message);
									if (result.code == -1) { // url跳转网页中传回的值。
										var index = parent.layer
												.getFrameIndex(window.name);
										parent.layer.close(index);
									}
								},
								error : function() {
									alert("11");
								}
							});
				}
			});
	$("#addForm").validate({
		rules : {
			unfixedProteinMin : {
				required : true,
				isNumber : true,
				isFloatGtZero : true
			},
			unfixedProteinMax : {
				required : true,
				isNumber : true,
				isFloatGtZero : true
			},
			fixedProteinMin : {
				required : true,
				isNumber : true,
				isFloatGtZero : true
			},
			fixedProteinMax : {
				required : true,
				isNumber : true,
				isFloatGtZero : true
			},
			isSample : {
				required : true
			}
		},
		messages : {
			unfixedProteinMin : {
				required : "请输入非固定浆员合格范围最小值",
				isNumber : "请输入数字",
				isFloatGtZero : "请输入大于0的数字"
			},
			unfixedProteinMax : {
				required : "请输入非固定浆员合格范围最大值",
				isNumber : "请输入数字",
				isFloatGtZero : "请输入大于0的数字"
			},
			fixedProteinMin : {
				required : "请输入固定浆员合格范围最小值",
				isNumber : "请输入数字",
				isFloatGtZero : "请输入大于0的数字"
			},
			fixedProteinMax : {
				required : "请输入固定浆员合格范围最大值",
				isNumber : "请输入数字",
				isFloatGtZero : "请输入大于0的数字"
			},
			isSample : {
				required : "请选择是否抽取小样"
			}
		}
	});

});
