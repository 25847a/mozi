$(function(){
	var checkDate = $("#checkDate").val();
	var comp = $("#comp").val();
	simpleAjax("/detectionProteins/printReport", {"chooseDate":checkDate}, function(result) {
		var data = result.data;
		var tableCon = "<TABLE border='0' style='display:table-header-group;font-size:1pt;' align='center'>"+
				"<THEAD>"+
				"	<TR>"+
				"		<th colspan='12' class='logdkr'>"+comp+"血红蛋白检测记录</th>"+
				"	</TR>"+
				"	<TR>"+
				"		<th colspan='9' class='blcirth'>&nbsp; </th>"+
				"	 <th colspan='3' align='center' class='blcirth'>  "+
	 	 	 	 	 "	   <div class=''  style='float:right'>"+
					"<div class='form-group newikrd'>"+
					"	<label class='control-label UserC'>化验日期：</label>"+
					"	<label class='control-label UserC inhenagoi'>"+checkDate+"</label>"+
					"</div>"+
                     " </div></th>"+
 	 	 	 	 	    "</TR>"+
					"<TR> "+
					"<th>小样号</th>"+
					"	<th>献血浆者卡号</th>"+
					"	<th>姓名</th>"+
						"<th>性别</th>"+
					"	<th>检测值</th>"+
					"	<th>结果判定</th>"+
					"	<th>小样号</th>"+
					"	<th>献血浆员卡号</th>"+
					"	<th>姓名</th>"+
					"	<th>性别</th>"+
					"	<th>检测值</th>"+
					"	<th>结果判定</th>"+
				"	</TR>"+
				"</THEAD>"+
			"	<TBODY style='text-align:center'>";
			for(var i=0,j=data.length;i<j;i++){
				var item = data[i];
				if(i%2==0){
					tableCon = tableCon+"<tr>"+
					"<td>"+(item.sampleNo==null?'':item.sampleNo)+"</td>"+
					"<td>"+item.providerNo+"</td>"+
					"<td>"+item.name+"</td>"+
					"<td>"+getSexValue(item.sex)+"</td>"+
					"<td>"+(item.value==null?'':item.value)+"</td>"+
					"<td>"+getCheckResultValue(item.result)+"</td>";
				}else{
					tableCon = tableCon+ "<td>"+(item.sampleNo==null?'':item.sampleNo)+"</td>"+
					"<td>"+item.providerNo+"</td>"+
					"<td>"+item.name+"</td>"+
					"<td>"+getSexValue(item.sex)+"</td>"+
					"<td>"+(item.value==null?'':item.value)+"</td>"+
					"<td>"+getCheckResultValue(item.result)+"</td>"+
					"</tr>";
				}
			}
			if(tableCon.substring(tableCon.length-5,tableCon.length)!="</tr>"){
				tableCon = tableCon+ "</tr>";
			}
			
			tableCon = tableCon+"<tr style='height: 3.5em;'>"+
			"<td colspan='2' align='center'>试剂厂家</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"<td colspan='2'>批号</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"</tr> "+
			"<tr style='height: 3.5em;'>"+
			"<td colspan='2'>化验者</td>"+
			"<td colspan='4'>&nbsp;</td>"+
			"<td colspan='2'>有效期</td>"+
			"<td colspan='4'>&nbsp; </td>"+
			"</tr>"+
			"</TBODY>"+
			"</TABLE>";
			$("#bustable").append(tableCon);
	});
 

})