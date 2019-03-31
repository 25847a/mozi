layui.use(['laydate', 'laypage', 'table'], function () {});
$(function(){
	initDate("#startDate");
	initDate("#endDate");
	$("#startDate,#endDate").val('');
	
	//加载采浆量配置
	$.post('/config/queryDictListByType',{"type":"stock_config"},function(res){
		if(res.code==-1){
			$.each(res.data,function(index,item){
				if(item.lable=='code'){
					amount = item.value;
					$("#code").html(amount);
				}else if(item.lable=='parentCompany'){
					cost = item.value;
					$("#company").html(cost);
				}
			});
		}
	});
	
	//查询
	$("#chaxun").click(function(){
		var startDate =$("#startDate").val();
		var endDate =$("#endDate").val();
		if(startDate.length<1){
			layer.alert('请选择开始时间');
		}else if(endDate.length<1){
			layer.alert('请选择结束时间');
		}else{
			$.post('/plasmaStock/queryPlasmaBoxSummaryList',{"startDate":startDate,"endDate":endDate},function(res){
				if(res.code==-1){
					$("#boxNum").html(res.data.length);
					var tr='<tr>';
					tr+='<td style="width: 200px;">箱号 CasenNo.</td>';
					tr+='<td style="width: 200px;">采浆编号Control No.(from) (to)</td>';
					tr+='<td style="width: 200px;">重量(Subtotalg)</td>';
					tr+='<td class="tabdistr">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>';
					tr+='<td style="width: 200px;">箱号 CasenNo.</td>';
					tr+='<td style="width: 200px;">采浆编号Control No.(from) (to)</td>';
					tr+='<td style="width: 200px;">重量(Subtotalg)</td>';
					tr+='</tr>';
					var sumPlasma=0;
					for(var i=0;i<res.data.length;i++){
						var item = res.data[i];
						if(i%2==0){
							tr+='<tr>';
							tr+='<td>'+item.boxId+'</td>';
							tr+='<td>'+item.minPlasmaNo+'-<br/>'+item.maxPlasmaNo+'</td>';
							tr+='<td>'+item.plasmAmount+'</td>';
						}else{
							tr+='<td class="tabdistr liardg"></td>';
							tr+='<td>'+item.boxId+'</td>';
							tr+='<td>'+item.minPlasmaNo+'-'+item.maxPlasmaNo+'</td>';
							tr+='<td>'+item.plasmAmount+'</td>';
							tr+='</tr>';
						}
						sumPlasma=sumPlasma+item.plasmAmount;
					}
					if(tr.lastIndexOf('</tr>')==-1){
						tr+='<td class="tabdistr liardg"></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='</tr>';
					}
					$("#sumPlasma").html(sumPlasma);
					$("#tbody").html(tr);
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

getReportInfo(14);