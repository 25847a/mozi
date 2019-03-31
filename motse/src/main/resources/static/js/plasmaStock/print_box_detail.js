layui.use(['layer'], function () {});
$(function(){
	
	//获取公司名字
	$.ajax({
		type : "POST",
		data:{'type':"stock_config","label":"company"},
		url : "/config/queryDictByTypeAndLabel",
		datatype : "json",
		success : function(result) {
			if(result.code==-1){
				$("#company").html(result.data.value);
			}
		},
		error : function() {
			layer.alert("请稍后试试");
		}
	});
	
	$("#chaxun").click(function(){
		var boxId=$("#boxId").val();
		if(boxId.length<1){
			layer.alert("请输入箱号");
		}else{
			var count=0;
			var sum=0;
			$("#boxId_val").html(boxId);
			$.post("/plasmaStock/queryBoxPlasmaInfo",{"boxId":boxId},function(res){
				if(res.code==-1 && null!=res.data){
					var allId=""; // 登记号
					var endTime=""; //结束时间
					$("#div_print").show();
					$("tbody").html("");
					$.each(res.data,function(index,item){
						count++;
						sum+=item.plasmAmount;
						if(index==0){
							if(null!=item.immuneName){
								$("#type").html(item.immuneName);
							}else{
								$("#type").html('普通');
							}
							$("#time").html(item.time);
						}
						var tr='<tr>';
						tr+='<td style="width:122px">'+item.plasmaNo+'</td>';
						tr+='<td>'+item.providerNo+'</td>';
						tr+='<td>'+item.name+'</td>';
						tr+='<td>'+(item.sex==0 ? '男':'女')+'</td>';
						tr+='<td>'+dateFtt('yyyy-MM-dd',new Date(item.updateDate))+'</td>';
						var text='';
						if (item.bloodType == 0) {
							text = "A";
						} else if (item.bloodType == 1) {
							text = "B";
						} else if (item.bloodType == 2) {
							text = "O";
						} else {
							text = "AB";
						}
						tr+='<td>'+text+'</td>';
						tr+='<td>'+item.plasmAmount+'</td>';
						tr+='<td>'+(item.wholeBlood==0 ? '合格' :'不合格')+'</td>';
						tr+='<td>'+(item.protein==0 ? '合格' :'不合格')+'</td>';
						tr+='<td>≤25</td>';
						tr+='<td>阴性</td>';
						tr+='<td>阴性</td>';
						tr+='<td>阴性</td>';
						tr+='<td>阴性</td>';
						tr+='<td>'+(null!=item.immuneName ? item.immuneName : '普通')+'</td>';
						tr+='<td>合格</td>';
						tr+='</tr>';
						$("tbody").append(tr);
						allId = item.allId;
						endTime = dateFtt('yyyy-MM-dd',new Date(item.updateDate));
					});
					var tr='<tr>';
					tr+='<td rowspan="4">试剂</td>';
					tr+='<td> </td>';
					tr+='<td colspan="2">血型</td>';
					tr+='<td>HBsAg</td>';
					tr+='<td colspan="2">抗-HCV</td>';
					tr+='<td colspan="2">抗-HIV</td>';
					tr+='<td>梅毒</td>';
					tr+='<td colspan="2">ALT</td>';
					tr+='<td colspan="2">全血比重</td>';
					tr+='<td>蛋白含量</td>';
					tr+='<td> </td>';
					tr+='</tr>';
					$("tbody").append(tr);
					//获取试剂信息
					$.post("/plasmaStock/queryBoxPlasmaInfoReagents",{"allId":allId,"time":endTime},function(res){
						if(res.code==-1 && null!=res.data){
							
							var cang='';//厂名
							var pihao='';//批号
							var you=''; //有效期
							var arr = [];
							for(var i=0;i<res.data.length;i++){
								var item=res.data[i];
								arr[item.lable]=item;
							}
							//加入血型
							var item = res.bloodItem;
							item["termOfValidity"] = item.expiryTime;
							arr["blood"] = item;
							//加入全血比重
							var item1 = res["ptItem"];
							item1["termOfValidity"] = item1.expiryTime;
							arr["whole"] = item1;
							//加入蛋白含量
							var item2 = res["wbItem"];
							item2["termOfValidity"] = item2.expiryTime;
							arr["protein"] = item2;
							
							var name=new Array('name','batchNumber','termOfValidity');
							for(var i=0;i<name.length;i++){
								var n = name[i];
								
								tr='<tr>';
								if(i==0){
									tr+='<td>厂名</td>';
								}else if(i==1){
									tr+='<td>批号</td>';
								}else{
									tr+='<td>有效期</td>';
								}
								tr+='<td colspan="2">'+(typeof(arr['blood'])!=='undefined' ? arr['blood'][n]:'')+'</td>';
								tr+='<td>'+(arr['HBsAG'][n]==null ?'' : arr['HBsAG'][n])+'</td>';
								tr+='<td colspan="2">'+(arr['HCV'][n]==null ? '':arr['HCV'][n])+'</td>';
								tr+='<td colspan="2">'+(arr['HIV'][n]==null ?'' : arr['HIV'][n])+'</td>';
								tr+='<td>'+(arr['syphilis'][n]==null ?'':arr['syphilis'][n])+'</td>';
								tr+='<td colspan="2">'+(arr['ALT'][n]==null ? '' :arr['ALT'][n])+'</td>';
								tr+='<td colspan="2">'+(typeof(arr['whole'])!=='undefined' ? arr['whole'][n]:'')+'</td>';
								tr+='<td>'+(typeof(arr['protein'])!=='undefined' ? arr['protein'][n]:'')+'</td>';
								tr+='<td> </td>';
								tr+='</tr>';
								$("tbody").append(tr);
							}
							// 工作人员
							var check=new Array('testName','checkName','reportName');
							
							for(var i=0;i<name.length;i++){
								var n = check[i];
								
								tr='<tr>';
								if(i==0){
									tr+='<td rowspan="4">工作人员</td>';
									tr+='<td>检测者</td>';
								}else if(i==1){
									tr+='<td>复核者</td>';
								}else{
									tr+='<td>报告者</td>';
								}
								tr+='<td colspan="2">'+(typeof(arr['blood'])!=='undefined' ? arr['blood'][n]:'')+'</td>';
								tr+='<td>'+arr['HBsAG'][n]+'</td>';
								tr+='<td colspan="2">'+arr['HCV'][n]+'</td>';
								tr+='<td colspan="2">'+arr['HIV'][n]+'</td>';
								tr+='<td>'+arr['syphilis'][n]+'</td>';
								tr+='<td colspan="2">'+arr['ALT'][n]+'</td>';
								tr+='<td colspan="2">'+(typeof(arr['whole'])!=='undefined' ? arr['whole'][n]:'')+'</td>';
								tr+='<td>'+(typeof(arr['protein'])!=='undefined' ? arr['protein'][n]:'')+'</td>';
								tr+='<td> </td>';
								tr+='</tr>';
								$("tbody").append(tr);
							}
							
						}
					});
					
					$("#count").html(count);
					$("#sum").html(sum+"     g");
				}else{
					layer.alert("请输入正确的箱号");
				}
			});
		}
	});
	
	var url = location.search;
	if(url.indexOf('?')!=-1){
		url = url.substring(1,url.length);
		var boxId = url.split('=')[1];
		$("#boxId").val(boxId);
		$("#chaxun").click();
	}
})

function printdiv(){
	var printpage = document.getElementById("div_print");
	$("#search").hide();
	var newstr = printpage.innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = newstr;
	window.print();
	document.body.innerHTML = oldstr;
	$("#search").show();
}