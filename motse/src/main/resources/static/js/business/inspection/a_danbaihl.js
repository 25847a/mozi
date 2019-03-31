 $(function(){
	 getReportInfo(8);
	var checkDate = $("#checkDate").val();
	var comp = $("#comp").val();
	var type = $("#type").val(); 
	simpleAjax("/proteinContent/printReport", {"chooseDate":checkDate}, function(result) {
		var data = result.data;
		var tableCon ="<TABLE border='0' style='display:table-header-group;font-size:1pt;' align='center'>"+
		"<THEAD>"+
		"	<TR>"+
		"		<th colspan='12' class='logdkr'>"+(type!=2?comp+"血浆蛋白原始检测记录":"血浆(清)蛋白原始检测记录")+"</th>"+
		"	</TR>"+
			"	<TR>"+
			"	<th  class='blcirth'  >"+(type==2?"化验方法:":"")+"</th><th class='blcirth' >"+(type==2?"折射仪法":"")+"</th><th class='blcirth' colspan='3'></th><th class='blcirth' >"+(type==2?"折射仪编号:":"")+"</th> <th class='blcirth'> </th>	<th colspan='3' class='blcirth'>&nbsp; </th>"+
			"	 <th colspan='3' align='center' class='blcirth'>  "+
		 	 	 	 "	   <div class=''  style='float:right'>"+
				"<div class='form-group newikrd'>"+
				"	<label class='control-label UserC'>检测日期：</label>"+
				"	<label class='control-label UserC inhenagoi'>"+checkDate+"</label>"+
				"</div>"+
	             " </div></th>"+
		 	 	 	    "</TR>"+
				"<TR> "+
			"<th>小样号</th>"+
			"	<th>献浆员卡号</th>"+
			"	<th>姓名</th>"+
				"<th>折射仪刻度值</th>"+
			"	<th>蛋白值（g/L）</th>"+
			"	<th>结果判定</th>"+
			"	<th>小样号</th>"+
			"	<th>献浆员卡号</th>"+
			"	<th>姓名</th>"+
			"<th>折射仪刻度值</th>"+
			"	<th>蛋白值（g/L）</th>"+
			"	<th>结果判定</th>"+
		"	</TR>"+
		"</THEAD>"+
	"	<TBODY style='text-align:center'>";
		for(var i=0,j=data.length;i<j;i++){
			var item = data[i];
			if(i%2==0){
				tableCon =tableCon+"		<tr>"+
				"				<td>"+(item.sampleNo==null?'':item.sampleNo)+"</td>"+
				"				<td>"+item.providerNo+"</td>"+
				"				<td>"+item.name+"</td>"+
				"				<td>"+(item.value==null?'':item.value)+"</td>"+
				"				<td>"+(item.protein==null?'':item.protein)+"</td>"+
				"				<td>"+getCheckResultValue(item.result)+"</td>";
			}else{
				tableCon = tableCon+ "<td>"+(item.sampleNo==null?'':item.sampleNo)+"</td>"+
				"<td>"+item.providerNo+"</td>"+
				"<td>"+item.name+"</td>"+
				"<td>"+(item.value==null?'':item.value)+"</td>"+
				"<td>"+(item.protein==null?'':item.protein)+"</td>"+
				"<td>"+getCheckResultValue(item.result)+"</td>"+
				"</tr>";
			}
		}
		if(tableCon.substring(tableCon.length-5,tableCon.length)!="</tr>"){
			tableCon = tableCon+ "</tr>";
		}
		if(type!=2){

			var bzItem = result.bzItem;
			var lable = "";
			var name = "";
			var batchNumber = "";
			var expiryTime = "";
			if(bzItem!= null ){
				lable = bzItem.lable;
				name = bzItem.name;
				batchNumber = bzItem.batchNumber;
				expiryTime = bzItem.expiryTime;
			}
			tableCon = tableCon+"<tr style='height: 3.5em;'>"+
			"<td colspan='2' align='center'>标本信息</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"<td colspan='2'>检测方法</td>"+
			"<td colspan='4'>"+lable+" </td>"+
			"</tr> "+"<tr style='height: 3.5em;'>"+
			"<td rowspan = '3' align='center'>试剂</td><td  align='center'>厂家</td>"+
			"<td colspan='4'>"+name+" </td>"+
			"<td colspan='2'>检测者</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"</tr> "+"<tr style='height: 3.5em;'>"+
			"<td  align='center'>批号</td>"+
			"<td colspan='4'>"+batchNumber+"</td>"+
			"<td colspan='2'>复核者</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"</tr> "+
			"<tr style='height: 3.5em;'>"+
			"<td >有效期</td>"+
			"<td colspan='4'>"+expiryTime+"</td>"+
			"<td colspan='2'></td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"</tr>";
		}
		
		
		tableCon = tableCon+ "</TBODY>"+
		"</TABLE>";
		$("#bustable").append(tableCon);
		
		
		
	});


})