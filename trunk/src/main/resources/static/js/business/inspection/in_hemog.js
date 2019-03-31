$(function(){
	
	$.validator.setDefaults({
		
        submitHandler: function() {
        	var id  = $("#id").val();
    		if(id== undefined || "" == id){
    			layer.alert("请先选择一个浆员");
    			return false;
    		}
            $("#updateForm").ajaxSubmit({
                type : 'POST',
                url : "/detectionProteins/updateInfoById",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                parent.layer.closeAll();
                            	doSearch();
                            });
                    });
                },
                error : function() {
                    alert("新增失败");
                }
            });
        }
    });
	 $("#updateForm").validate({
	        //验证规则
	        rules: {
	        	value:{
	                required:true,
	                minlength:1,
	                maxlength:8
	            }
	        },
	        messages: {
	           
	        	value: {
	                required: "请输入检测值",
	                minlength: "检测值长度不允许少于1位",
	                maxlength: "检测值长度不允许多于8位"
	            }
	        }
	    });
	
	var cols= [[ //表头
/* {type: 'checkbox', fixed: 'left'}*/
	 {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
	,{field: 'sampleNo', title: '小样号',sort: true,align:'center',/* fixed: 'left'*/}
	,{field: 'allId', title: '全登记号',  sort: true,align:'center',/* fixed: 'left'*/}
	,{field: 'providerNo', title: '献浆员卡号', align:'center'}
	,{field: 'name', title: '姓名', sort: true,align:'center'}
	,{field: 'sex', title: '性别', align:'center',templet: function (d){ return getSexValue(d.sex);}} 
	,{field: 'updateDate', title: '检测日期', align:'center', templet:function(d){ return $.myTime.UnixToDate(d.updateDate);}}
	,{field: 'value', title: '检测值', align:'center'}
	,{field: 'result', title: '检测结果', align:'center' ,templet : function(d){return getCheckResultValue(d.result); }}
    ]];
	var startTime = $("#startTime").val();
	dataAll("inhemogS",cols,{"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListByUpdateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
		 
		 initTableOnClick();
	 });

});

function doSearch(){
	var startTime = $("#startTime").val();
	layui.table.reload('inhemogS', {where: {"startTime" : startTime, "token" : getToken()}});
	initTableOnClick();
}
 
/*//日期时间*/
initDate("#startTime");
　
function initTableOnClick(){
	// 表格 tr 单击事件
	var tab = $("#inhemogS").next().find('.layui-table tbody tr');
	
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var providerNo = $(tr).find("td").eq(0).find("div").html();
		var registriesNo = $(tr).find("td").eq(1).find("div").html();
		if(undefined == providerNo) 
			return false;
		initData(providerNo,registriesNo);
	});
	
}

var idtep = 0;
function initData(id,registriesNo) {
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/detectionProteins/queryByID?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#name").val(data.name);
			$("#bloodType").val(getBloodValue(data.bloodType));
			$("#providerNo").val(data.providerNo);
			$("#sex").val(getSexValue(data.sex));
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#allId").val(data.allId);
			$("#sampleNo").val(data.sampleNo);
			$("#value").val(data.value);
			$("#result").val(data.result);
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}
function doSubmit(){
	$("#result").val("2");
	var  id  = $("#id").val();
	if(id== undefined || "" == id){
		layer.alert("请先选择一个浆员");
		return false;
	}
	simpleAjax("/detectionProteins/updateInfoById0", {"id":id,"result":2}, function(result) {
		layer.alert("操作成功");
		doSearch();
		$("#id").val("");
	});
}

$("#print").click(function(){
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '血红蛋白检测打印',
				maxmin : false,
				content : '/detectionProteins/gotoPrint?chooseDate='+$("#startTime").val(),
				area : [ '100%', '100%' ],
			})
		});
});