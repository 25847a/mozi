$(function(){
	//初始化时间
	initDate("#startTime");
	
	//查询
	$("#chaxun").click(function(){
		var startTime = $("#startTime").val();
		if(startTime.length<1){
			layer.alert("请选择开始时间");
		}else{
			$("#date").html(startTime);
			$.post('/plasmCollection/queryPrintPlasmaCollectionRecordList',{"startDate":startTime+' 00:00:00',"endDate":startTime+' 23:59:59'},function(res){
				if(res.code==-1){
					$("#collection_table tbody").html('');
					var hege=0;
					var buhege=0;
					var param =[];
					for(var i=0;i<res.data.length;i++){
						var item = res.data[i];
						if(param.indexOf(item.templateId)==-1){
							param[i]=item.templateId;
						}
						var tr='<tr>';
						tr+='<td>'+item.providerNo+'</td>';
						tr+='<td>'+item.allId+'</td>';
						tr+='<td>'+item.name+'</td>';
						if(item.sex==0){
							tr+='<td>男</td>';
						}else{
							tr+='<td>女</td>';
						}
						tr+='<td>'+(null==item.immuneName ? '普通': item.immuneName)+'</td>';
						tr+='<td>'+getBloodValue(item.bloodType)+'</td>';
						if(item.isIdentity==0){
							tr+='<td>否</td>';
						}else if(item.consultResult==1){
							tr+='<td>是</td>';
						}else{
							tr+='<td></td>';
						}
						tr+='<td>'+item.tname+'</td>';
						tr+='<td>'+item.loopCount+'</td>';
						tr+='<td>'+item.runTime+'</td>';
						tr+='<td>'+item.wholeBlood+'</td>';
						tr+='<td>'+item.knAmount+'</td>';
						tr+='<td>'+item.nname+'</td>';
						tr+='<td>'+item.plasmAmount+'</td>';
						if(item.result==0){
							hege=hege+1;
							tr+='<td>√</td>';
						}else{
							buhege=buhege+1;
							tr+='<td>×</td>';
						}
						tr+='<td>'+item.fname+'</td>';
						tr+='<td>'+item.code+'</td>';
						tr+='<td>'+item.cname+'</td>';
						tr+='<td></td>';
						tr+='</tr>';
						$("#collection_table tbody").append(tr);
					}	
					getSupper(param);
					$("#sum").html(res.data.length+'人');
					$("#hege").html(hege+'人');
					$("#buhege").html(buhege+'人');
				}
				
			});
		}
	});
})

function printdiv(printpage) {
	var newstr = printpage.innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = newstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
//打印
function printCheck(){
	var div_print = document.getElementById("div_print");
	printdiv(div_print);
}
/**
 * 获取耗材信息
 * @param param
 * @returns
 */
function getSupper(param){
	$("#center").html('');
	var data = JSON.stringify(param);
	$.ajax({
		type : "POST",
		url : "/plasmCollection/queryDetailedCollectionInfo",
		data:{"data":data},
		datatype : "json",
		success : function(result) {
			if(null!=result.data){
				var dataSour = result.data;
				for(var o in dataSour){
					$("#center").append("<p>"+dataSour[o].number+":耗材:"+dataSour[o].name+" 批号:"+dataSour[o].batchNumber+
							" 耗材厂家："+dataSour[o].supply+" 有效期："+dateFtt("yyyy-MM-dd",new Date(dataSour[o].expiryTime))+"</p>");
				}
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
}
getReportInfo(12);