$(function(){
	//初始化时间
	initDate("#startTime");
	initDate("#endTime");
	$("#startTime,#endTime").val("");
	var baseInfo = [ [ // 表头
		{type: 'checkbox', fixed: 'left'},
		{	field : 'providerNo',
			title : '献浆员卡号',
			sort : true, 
			align : 'center',
			width : '15%'
		}, {
			field : 'icNumber',
			title : 'IC卡号',
			align : 'center',
			width : '15%'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '10%'
		}, {
			field : 'birthday',
			title : '出生日期',
			align : 'center',
			width : '15%',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.birthday));
			}
		}, {
			field : 'sex',	
			title : '性别',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.sex == 0) {
					text = "男";
				} else {
					text = "女";
				}
				return text;
			}
		}, {
			field : 'bloodType',
			title : '血型',
			align : 'center',
			width : '15%',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.bloodType == 0) {
					text = "A";
				} else if (d.isDisable == 1) {
					text = "B";
				} else if (d.isDisable == 2) {
					text = "O";
				} else {
					text = "AB";
				}
				return text;
			}
		} , {
			field : 'idNo',
			title : '身份证号码',
			align : 'center',
			width : '15%'
		} , {
			field : 'addressx',
			title : '地址',
			align : 'center',
			width : '15%'
		} , {
			field : 'phone',
			title : '电话号码',
			align : 'center',
			width : '15%'
		}, {
			field : 'status',
			title : '是否审核',
			align : 'center',
			width : '15%',
			templet : function(d) { // 单元格格式化函数
				if(d.examineStatus==0){
					return "未审核";
				}
				return "已审核";
			}
		}, {
			field : 'createDate',
			title : '建档日期',
			align : 'center',
			width : '15%',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}
		] ];
	dataAll("baseInfo",baseInfo,null,"/providerBaseinfo/queryPlasmaExamineList",'','',function(){
	});
	
	//查询
	$("#query").click(function(){
		var name=$("#name").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var idNo=$("#idNo").val();
		var status=$("#status").val();
		layui.table.reload('baseInfo', {where: {"name": name, "startTime": startTime,"endTime":endTime,"idNo":idNo,"status":status}});
	});
	
	//审核
	$("#examine").click(function(){
		var checkStatus = table.checkStatus('baseInfo');
        var ids = new Array();
        //没有选中任何一行
        if(checkStatus.data.length == 0) {
            layer.alert('请选择浆员');
        }else {
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids.push(checkStatus.data[i].id);
            }
            $.post(GetURLInfo() + "updateBaseInfoExamine", {"ids": ids.join(","),"examineStatus":1}, function (result) {
                if(result.code==-1) { //url跳转网页中传回的值。
                	layer.alert(result.message,function(){
                		layer.closeAll();
                		location.reload();
                	});
                }
            });
        }
	});
	
	//取消审核
	$("#calcal_examine").click(function(){
		var checkStatus = table.checkStatus('baseInfo');
        var ids = new Array();
        //没有选中任何一行
        if(checkStatus.data.length == 0) {
            layer.alert('请选择浆员');
        }else {
            for (var i = 0; i < checkStatus.data.length; i++) {
                ids.push(checkStatus.data[i].id);
            }
            $.post(GetURLInfo() + "updateBaseInfoExamine", {"ids": ids.join(","),"status":0}, function (result) {
                if(result.code==-1) { //url跳转网页中传回的值。
                	layer.alert(result.message,function(){
                		layer.closeAll();
                		location.reload();
                	});
                }
            });
        }
	});
	
	//导出
	$("#export").click(function(){
		var param="";
		var name=$("#name").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var idNo=$("#idNo").val();
		var status=$("#status").val();
		param+="name="+name+"&startTime="+startTime+"&endTime="+endTime+"&idNo="+idNo+"&status="+status;
		window.location.href="/providerBaseinfo/exportExamineList?"+param;
	});
	//重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 
})