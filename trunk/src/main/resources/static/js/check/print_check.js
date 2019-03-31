$(function(){
	var url = location.search; //获取url中"?"符后的字串
	if(url.length>0){
		url = url.substring(1,url.length);
		var time = url.split('=');
		$("#date").html(time[1]);
		$.post('/check/queryCheckRecordList',{"startDate":time[1]+" 00:00:00","endDate":time[1]+" 23:59:59"},function(res){
			if(res.code==-1){
				var hege=0;
				var buhege=0;
				for(var i=0;i<res.data.length;i++){
					var item = res.data[i];
					var tr='<tr>';
					tr+='<td>'+item.providerNo+'</td>';
					tr+='<td>'+item.allId+'</td>';
					tr+='<td>'+item.fname+'</td>';
					if(item.sex==0){
						tr+='<td>男</td>';
					}else{
						tr+='<td>女</td>';
					}
					tr+='<td>'+(null==item.immuneName ? '普通': item.immuneName)+'</td>';
					tr+='<td>'+getBloodValue(item.bloodType)+'</td>';
					if(item.consultResult==0){
						tr+='<td>合格</td>';
					}else if(item.consultResult==1){
						tr+='<td>不合格</td>';
					}else{
						tr+='<td></td>';
					}
					tr+='<td>'+item.tz+'</td>';
					tr+='<td>'+item.tw+'</td>';
					tr+='<td>'+item.mb+'</td>';
					tr+='<td>'+item.xya+'/'+item.xyb+'</td>';
					if(item.xb==0){
						tr+='<td>正常</td>';
					}else{
						tr+='<td>异常</td>';
					}
					if(item.fb==0){
						tr+='<td>正常</td>';
					}else{
						tr+='<td>异常</td>';
					}
					if(item.pf==0){
						tr+='<td>正常</td>';
					}else{
						tr+='<td>异常</td>';
					}
					if(item.wg==0){
						tr+='<td>正常</td>';
					}else{
						tr+='<td>异常</td>';
					}
					if(item.sz==0){
						tr+='<td>正常</td>';
					}else{
						tr+='<td>异常</td>';
					}
					tr+='<td>'+item.name+'</td>';
					if(item.result==0){
						hege=hege+1;
						tr+='<td>正常</td>';
					}else{
						buhege=buhege+1;
						tr+='<td>异常</td>';
					}
					tr+='</tr>';
					$("#check_table TBODY").append(tr);
				}	
				$("#sum").html(res.data.length+'人');
				$("#hege").html(hege+'人');
				$("#buhege").html(buhege+'人');
			}
		});
	}
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

getReportInfo(9);