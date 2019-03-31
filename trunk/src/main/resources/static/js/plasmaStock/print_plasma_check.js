$(function(){
	//获取公司名字
	$.ajax({
		type : "POST",
		data:{'type':"stock_config"},
		url : "/config/queryDictListByType",
		datatype : "json",
		success : function(result) {
			if(result.code==-1 && null!=result.data){
				for(var i=0;i<result.data.length;i++){
					var item = result.data[i];
					if(item.lable=='company'){
						$("#company").html(item.value);
					}else if(item.lable=='parentCompany'){
						$("#parentCompany").html(item.value);
					}
				}
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
			$("#boxId_No").html("血浆箱号：<label>"+boxId+'</label>');
			var sum=0;
			var allId="";
			var endTime='';
			$.post('/plasmaStock/queryPlasmaCheckList',{"boxId":boxId},function(res){
				if(res.code==-1){
					for(var i=0;i<res.data.length;i++){
						var item = res.data[i];
						if(i==0){
							$("#boxType").html('血浆类型：<label>'+(null==item.immuneName ? '普通' : item.immuneName)+'</label>');
						}
						var tr='<tr>';
						var sampleNo=item.sampleNo+'';
						var no = sampleNo.substring(sampleNo.length-2,sampleNo.length);
						tr+='<td>'+no+'</td>';
						tr+='<td>'+item.name+'</td>';
						tr+='<td>'+item.plasmaNo+'</td>';
						tr+='<td>'+item.providerNo+'</td>';
						tr+='<td>'+item.plasmAmount+'</td>';
						tr+='<td>'+dateFtt('yyyy-MM-dd',new Date(item.updateDate));+'</td>';
						if(item.bloodRedProtein==1){
							tr+='<td>&gt;=120</td>';
						}else if(item.bloodRedProtein==2){
							tr+='<td>&gt;=110</td>';
						}else if(item.bloodRedProtein==3){
							tr+='<td>&lt;120</td>';
						}else if(item.bloodRedProtein==4){
							tr+='<td>&lt;110</td>';
						}else{
							tr+='<td></td>';
						}
						tr+='<td>'+item.serumProtein+'</td>';
						if(item.alt==null){
							tr+='<td>-</td>';
						}else{
							if(item.alt==0){
								tr+='<td>阴性</td>';
							}else if(item.alt==1){
								tr+='<td>阳性</td>';
							}else if(item.alt==2){
								tr+='<td>弱阳性</td>';
							}
						}
						if(item.hbsag==null){
							tr+='<td>-</td>';
						}else{
							if(item.hbsag==0){
								tr+='<td>阴性</td>';
							}else if(item.hbsag==1){
								tr+='<td>阳性</td>';
							}else if(item.hbsag==2){
								tr+='<td>弱阳性</td>';
							}
						}
						
						if(item.hcv==null){
							tr+='<td>-</td>';
						}else{
							if(item.hcv==0){
								tr+='<td>阴性</td>';
							}else if(item.hcv==1){
								tr+='<td>阳性</td>';
							}else if(item.hcv==2){
								tr+='<td>弱阳性</td>';
							}
						}
						
						if(item.syphilis==null){
							tr+='<td>-</td>';
						}else{
							if(item.syphilis==0){
								tr+='<td>阴性</td>';
							}else if(item.syphilis==1){
								tr+='<td>阳性</td>';
							}else if(item.syphilis==2){
								tr+='<td>弱阳性</td>';
							}
						}
						
						if(item.hiv==null){
							tr+='<td>-</td>';
						}else{
							if(item.hiv==0){
								tr+='<td>阴性</td>';
							}else if(item.hiv==1){
								tr+='<td>阳性</td>';
							}else if(item.hiv==2){
								tr+='<td>弱阳性</td>';
							}
						}
						
						
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='<td></td>';
						tr+='</tr>';
						
						sum = sum+item.plasmAmount;
						$("#tbody").append(tr);
						endTime = dateFtt('yyyy-MM-dd',new Date(item.updateDate));
						allId = item.allId;
					}	
					$("#sum").html('总计：Total：<label>'+sum+'</label>克(g)');
					
					//获取试剂信息
					$.post("/plasmaStock/queryBoxPlasmaInfoReagents",{"allId":allId,"time":endTime},function(result){
						if(result.code==-1 && null!=result.data){
							
							var cang='';//厂名
							var pihao='';//批号
							var you=''; //有效期
							var arr = [];
							for(var i=0;i<result.data.length;i++){
								var item=result.data[i];
								arr[item.lable]=item;
							}
							
							//加入血型
							var item = result.bloodItem;
							item["termOfValidity"] = item.expiryTime;
							arr["blood"] = item;
							//加入全血比重
							var item1 = result["ptItem"];
							item1["termOfValidity"] = item1.expiryTime;
							arr["whole"] = item1;
							//加入蛋白含量
							var item2 = result["wbItem"];
							item2["termOfValidity"] = item2.expiryTime;
							arr["protein"] = item2;
							var name=new Array('name','batchNumber','termOfValidity');
							for(var i=0;i<name.length;i++){
								var n = name[i];
								
								tr='<tr>';
								tr+='<td>'+(typeof(arr['blood'])!=='undefined' ? arr['blood'][n]:'')+'</td>';
								tr+='<td>'+(arr['HBsAG'][n]==null ?'' : arr['HBsAG'][n])+'</td>';
								tr+='<td >'+(arr['HCV'][n]==null ? '':arr['HCV'][n])+'</td>';
								tr+='<td >'+(arr['HIV'][n]==null ?'' : arr['HIV'][n])+'</td>';
								tr+='<td>'+(arr['syphilis'][n]==null ?'':arr['syphilis'][n])+'</td>';
								tr+='<td >'+(arr['ALT'][n]==null ? '' :arr['ALT'][n])+'</td>';
								tr+='<td >'+(typeof(arr['whole'])!=='undefined' ? arr['whole'][n]:'')+'</td>';
								tr+='<td>'+(typeof(arr['protein'])!=='undefined' ? arr['protein'][n]:'')+'</td>';
								tr+='<td> </td>';
								tr+='</tr>';
								$("#check_plasma").append(tr);
							}
						}
					});
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

getReportInfo(13);