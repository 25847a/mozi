$(function() {
	initCheckAdmins("#opAdmin");
	initDate("#startDate");
	initDate("#endDate");
	
	var rumser = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			width : '10%',
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : '5%',
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			width : '5%',
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
			field : 'type',
			title : '类型',
			width : '6%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(d.type==0){
					return "普通";
				}else if(d.type==1){
					return "普免";
				}else if(d.type==2){
					return "特免";
				}
				return "-";
			}
		}, {
			field : 'allId',
			title : '全登记号',
			align : 'center',
			width : '10%',
		}, {
			field : 'sampleNo',
			title : '小样号',
			align : 'center',
			width : '8%',
		}, {
			field : 'bloodType',
			title : '血型',
			align : 'center',
			width : '5%',
			templet : function(d){return getBloodValue(d.blood); }
		},  {
			field : 'result',
			title : '化验结果',
			align : 'center',
			width : '10%',
			 templet:function(d){ return getCheckResultValue(d.result);}
		}, {
			field : 'proteinValue',
			title : '蛋白含量',
			width : '10%',
			align : 'center'
		}, {
			field : 'hbsag',
			title : 'hbsag',
			width : '6%',
			align : 'center',templet: function (d){ return getCheckedValue(d.hbsag);}
		}, {
			field : 'hvc',
			title : 'HCV',
			width : '5%',
			align : 'center',templet : function(d){return getCheckedValue(d.hcv); }
		}, {
			field : 'alt',
			title : 'ALT',
			width : '5%',
			align : 'center',templet : function(d){return getAltValue(d.alt); }
		}, {
			field : 'hiv',
			title : 'HIV',
			width : '5%',
			align : 'center',templet : function(d){return getCheckedValue(d.hiv); }
		}, {
			field : 'syphilis',
			title : '梅毒',
			width : '5%',
			align : 'center',templet : function(d){return getCheckedValue(d.syphilis); }
			
		}, {
			field : 'wholeBlood',
			title : '全血比重',
			width : '10%',
			align : 'center',templet : function(d){return getCheckResultValue(d.wholeBlood); }
		}, {
			field : 'createDate',
			title : '化验日期',
			width : '10%',
			align : 'center',templet:function(d){ return $.myTime.UnixToDate(d.createDate)}
		}, {
			field : 'hname',
			title : '化验人',
			width : '8%',
			align : 'center'
		}, {
			field : 'opinion',
			title : '是否拒绝',
			width : '8%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.opinion){
					if(d.opinion==0){
						return "暂时拒绝";
					}else{
						return "永久淘汰";
					}
				}else{
					return "";
				}
			}
		}, {
			field : 'eliminateReason',
			title : '拒绝原因',
			width : '15%',
			align : 'center'
		}, {
			field : 'day',
			title : '拒绝天数',
			width : '10%',
			align : 'center'
		}
		] ];
	
	dataOnlyUrl("rumser",rumser,"/queryInfo/queryListsByCondition");
}) 

function getSearchInfo(){
	var allTime = $("#allTime").val();
	var checked =$("#allTime").prop('checked');
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	if(checked){ // 如果是全部时间  则将日期时间清空
		startDate ="";
		endDate ="";
	}
	var idNo = $("#idNo").val();
	var providerNo = $("#providerNo").val();
	var opAdmin = $("#opAdmin").val();
	var name = $("#name").val();
	var icNumber = $("#icNumber").val();
	var proteinValue = $("#proteinValue").val();
	var result = $("#result").val();
	var syphilis = $("#syphilis").val();
	var alt = $("#alt").val();
	var isAssay = $("#isAssay").val();
	var hcv = $("#hcv").val();
	var hbsag = $("#hbsag").val();
	var wholeBlood = $("#wholeBlood").val();
	
	var datas = {"allTime":allTime,
			"startTime":startDate,
			"endTime":endDate,
			"idNo":idNo,
			"providerNo":providerNo,
			"opAdmin":opAdmin,
			"name":name,
			"icNumber":icNumber,
			"proteinValue":proteinValue,
			"result":result,
			"syphilis":syphilis,
			"alt":alt,
			"isAssay":isAssay,
			"hcv":hcv,
			"hbsag":hbsag,
			"wholeBlood":wholeBlood,
			"page" :1,
			"limit":10
			
	}
	return datas;
}
function search(){
	layui.table.reload('rumser', {where: getSearchInfo()});
}

//导出数据到公司
$("#exportCompany").click(function(){
	layer.open({
		type : 2,
		title : '导出化验记录',
		maxmin : true,
		area : ["90%","90%"],
		content : '/plasmaStock/exportPlasmaToCompany'
	})
});
// 打印查询记录
function printSearchInfo(){
	layer.ready(function() {
	      perContent = layer.open({
	                  type:2,
	                title: '化验结果打印',
	                content: '/queryInfo/initReportPage?sendDate='+$("#startDate").val()+"&plasmaType="+$("#plasmaType").val(),
	                  area: ['100%', '100%'],
	             });
	           layer.full(perContent);
	    });
}

//打印当天的汇总记录
function printDayOfReport(){
	layer.ready(function() {
	      perContent = layer.open({
	                  type:2,
	                title: '汇总报表打印',
	                content: '/queryInfo/initeveryDayReportPage?sendDate='+$("#startDate").val(),
	                  area: ['100%', '100%'],
	             });
	           layer.full(perContent);
	    });
}

