$(function(){ 
	prints();
	getReportInfo(11);
})

function printWindow(){
	var div_print = document.getElementById("div_print");
	printdiv(div_print);
}

function printdiv(printpage) {
	var newstr = printpage.innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = newstr;
	window.print();
	document.body.innerHTML = oldstr;
}

function prints(){
	var url = location.search;
	url = url.substring(1,url.length);
	var limit=20;
	$("#tbody").empty();
	var count=0;
		$.ajax({ 
	       type: "get", 
	       url: '/plasmaStock/queryReturnSimpleList?'+url+"&page="+1+"&limit="+1000000, 
	       cache:false, 
	       async:false, 
	       success: function(result){ 
		   		if(result.data.length<limit){
					flag=true;
				}
				$("#people").html(result.count+' 份');
				var tr='';
				$.each(result.data,function(index,item){
					tr+='<tr>';
					tr+='<td>'+dateFtt('yyyy-MM-dd',new Date(item.createDate))+'</td>';
					tr+='<td>'+item.name+'</td>';
					tr+='<td>'+item.providerNo+'</td>';
					tr+='<td>'+item.allId+'</td>';
					tr+='<td>'+item.fname+'</td>';
					tr+='<td></td>';
					tr+='</tr>';
				});
				$("#tbody").html(tr);
	       } 
		});
}