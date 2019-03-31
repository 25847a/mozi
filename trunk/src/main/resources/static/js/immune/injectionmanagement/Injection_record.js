
$(function(){
	getReportInfo(7);
function printdiv(printpage) {
	var newstr = printpage.innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = newstr; window.print();
	document.body.innerHTML = oldstr; 
	return false; 
	} 
window.onload = function() {
	var bt = document.getElementById("bt"); 
	var div_print = document.getElementById("div_print");
	bt.onclick = function() {
		printdiv(div_print);
		} }
	layui.use(['laypage', 'layer'], function(){
		  var laypage = layui.laypage
		  ,layer = layui.layer;
	var startDate = $("#startDate").val(); 
  	var endDate = $("#endDate").val(); 
  	var providerNo = $("#providerNo").val();
  	var name = $("#name").val();
  	var num = $("#num").val();
  	var isShot = $("#isShot").val(); 
  	var immuneId = $("#immuneId").val();
  	var type = $("#type").val();
	data={"startDate":startDate,"providerNo":providerNo,"endDate":endDate,"providerNo":providerNo,"name":name,"num":num,"isShot":isShot,"immuneId":immuneId,"type":type};
	$.ajax({
		type : "POST",
		data:data,
		url : "/ordinaryInjection/downloadImmune",
		datatype : "json",
		success:function(result){
			data = result.data;
			$("#tdata").empty();
			if(null!=data){
				for(var i=0;i<data.length;i++){
					$("#tada").append("<tr>" +
							"<td>"+dateFtt("yyyy-MM-dd",new Date(data[i].updateDate))+"</td>" +
							"<td>"+data[i].name+"</td>" +
							"<td>"+data[i].providerNo+"</td>" +
							"<td>"+(data[i].supplies==null?"":data[i].supplies)+"</td>" +
							"<td>"+(data[i].standard==null?"":data[i].standard)+"</td>" +
							"<td>"+(data[i].batchNumber==null?"":data[i].batchNumber)+"</td>" +
							"<td>"+(data[i].supply==null?"":data[i].supply)+"</td>" +
							"<td>"+(data[i].injection==null?"":data[i].injection)+"</td>" +
							"<td>"+(dateFtt("yyyy-MM-dd",new Date(data[i].validMonth))==null?"":dateFtt("yyyy-MM-dd",new Date(data[i].validMonth)))+"</td>" +
							"<td>"+(data[i].num==null?"":data[i].num)+"</td>" +
							"<td>"+(data[i].remarks==null?"":data[i].remarks)+"</td>" +
							"<td>"+data[i].admin+"</td>" +
							"</tr>");
				}
			}else{
				parent.layer.alert("无数据可打印",function(){
					parent.layer.closeAll();
				});
			}
			
		},
		error:function(){
			
		}
	});
});
	$("#recording").html($("#record").val());
});