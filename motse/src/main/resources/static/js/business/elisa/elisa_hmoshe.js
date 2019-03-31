$(function(){
	initCheckData({'type':"elisa_program_mode"},"programModel");

	initCheckData({'type':"elisa_inboard_mode"},"inboardMode");
	initCheckData({'type':"elisa_oscillation_speed"},"oscillationSpeed");
	initCheckData2("projectName", "/elisaApi/queryAllProjects");
	
//	$('#projectName').editableSelect({
//		effects: 'slide'
//	});
	
	// 加载所有检测配置
	$.validator.setDefaults({
		submitHandler : function() {
			$("#hmoshe").ajaxSubmit({
				type : 'POST',
				url : GetURLInfo() + "saveProject",
				success : function(result) {
					parent.layer.alert(result.message, function(){
						parent.layer.closeAll();
						location.reload();
					});
					
				},
				error : function() {
				}
			});
		}
	});
	$("#hmoshe").validate({
		rules : {
			projectNameT : {
				required : true,
				rangelength : [2,20]
			},
			extensionOfTime : {
				required : true,
				range:[1,60],
				digits:true
			},
			readCount : {
				required : true,
				range:[1,60],
				digits:true
			},
			intervals : {
				required : true,
				range:[1,60],
				digits:true
			}
		},
		messages : {
			projectNameT : {
				required : "请输入项目名称",
				rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串")
			},
			extensionOfTime : {
				required : "请输入延长时间",
				 range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
				 digits: "只能输入数字"
			},
			readCount : {
				required : "请输入读板次数",
				 range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
				 digits: "只能输入数字"
			},
			intervals : {
				required : "请输入间隔时间",
				 range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
				 digits: "只能输入数字"
			}
		}
	});
});

// 删除一个记录
function doDel(){
	if($("#id").val()==""){
		parent.layer.alert("请先选择一个项目", {time: 2000});
		return false;
		
	}
	$("#delFlag").val(1);
	$("#hmoshe").submit();
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
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

//获取项目信息记录
function initCheckData2( selectId, url) {
	$.ajax({
		type : "POST",
		data:{},
		url : url,
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#"+selectId).empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#"+selectId).append("<option value='"+data[i].id+"'>"+data[i].projectName+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
}

function changeProject(){
	var id= $("#projectName").val();
	$.ajax({
		type : "POST",
		data:{"id":id},
		url :  "/elisaApi/queryProject",
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#delFlag").val(data.delFlag);
			$("#projectNameT").val(data.projectName);
			if(data.isBlank==1){
				$("#isBlank").prop("checked",true);
			}
			if(data.isOscillation==1){
				$("#isOscillation").prop("checked",true);
			}
			if(data.isDuWaveDetection==1){
				$("#isDuWaveDetection").prop("checked",true);
			}
			if(data.isSelfTest==1){
				$("#isSelfTest").prop("checked",true);
			}
			if(data.canUseBlankHole==1){
				$("#canUseBlankHole").prop("checked",true);
			}
			$("#programModel").val(data.programModel);
			$("#inboardMode").val(data.inboardMode);
			$("#oscillationSpeed").val(data.oscillationSpeed);
			$("#oscillationTime").val(data.oscillationTime);
			$("#mainFilter").val(data.mainFilter);
			$("#subFilter").val(data.subFilter);
			$("#extensionOfTime").val(data.extensionOfTime);
			$("#readCount").val(data.readCount);
			$("#intervals").val(data.intervals);
			
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}
