$(function(){
	//加载采浆量配置
	$.post('/config/queryDictListByType',{"type":"stock_config"},function(res){
		if(res.code==-1){
			$.each(res.data,function(index,item){
				if(item.lable=='company'){
					amount = item.value;
					$("#plasmaName").html(amount);
				}else if(item.lable=='parentCompany'){
					cost = item.value;
					$("#parentCompany").html(cost);
				}
			});
		}
	});
	
	//查询
	$("#chaxun").click(function(){
		var boxId = $("#boxId").val();
		if(boxId.length<1){
			layer.alert('请输入箱号');
		}else{
			var sum=0;
			$.post('/plasmaStock/queryPlasmaBoxList',{"boxId":boxId},function(res){
				if(res.code==-1){
					$("#boxNo").html(boxId);
					for(var i=0;i<res.data.length;i++){
						var item = res.data[i];
						var tr='<tr>';
						tr+='<td>'+item.name+'</td>';
						if(item.sex==0){
							tr+='<td>男</td>';
						}else{
							tr+='<td>女</td>';
						}
						tr+='<td>'+item.providerNo+'</td>';
						tr+='<td>'+getBloodValue(item.bloodType)+'</td>';
						tr+='<td>'+dateFtt('yyyy-MM-dd',new Date(item.updateDate)) +'</td>';
						tr+='<td>'+item.plasmAmount+'</td>';
						tr+='<td>'+item.plasmaNo+'</td>';
						tr+='<td>'+(null!=item.immuneName ? item.immuneName : '普通')+'</td>';
						tr+='</tr>';
						$("#box_table tbody").append(tr);
						sum+=item.plasmAmount;
					}
				}
				$("#sumAmount").html(sum+' g');
			});
		}
	});
})
getReportInfo(10);

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