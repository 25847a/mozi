$(function(){
	getReportInfo(1);
	showReportInfo();
});


function showReportInfo(){
	var data = {'sendDate':$("#sendDate").val()};
	var type = $("#type").val();
	$("#printDate").html((new Date()).toLocaleString());
	var url = "/specimenCollection/getSendInfoBySendDate";
	if(type == 'small'){// 采小血的URL会不一样
		url = "/smallBlood/getSendInfoBySendDate";
	}else if(type=="all"){
		$("#titleName").html("血液标本交接记录");
		url = "/fixedPulpRegister/getSendInfoBySendDate";
	}
	simpleAjax(url, data, function(result) {
		var da= result.data;
		var infoStr = "<table class='layui-table'>"+
		 "<thead>"+
		 "<tr>"+
		 "<th  width='200' >日期(n)</th>"+
		 "<th  width='100' >时间</th>"+
		 "<th>献血浆者姓名</th>"+
		 "<th>性别</th>"+
		 "<th>献血浆者卡号</th>"+
		 "<th>登记号</th>"+
		 "<th>全血比重(g/L)</th>"+
		 "<th>标本类型</th>"+
		 (type=="all"?"<th>标本接收人</th>":"<th>标本采集人</th>")+
		  "<th>备注</th>"+ 
		" </tr>"+
		 "</thead>"+
		" <tbody>";
		var qx=0,xj=0;
		 for(var i=0;i<da.length;i++){
			 var info = da[i];
			 infoStr+=" <tr>"+
			 " <td width='200'>"+$.myTime.UnixToDate(info.updateDate)+"</td>"+
			 " <td width='100'>"+dateFtt('hh:mm',new Date(info.updateDate))+"</td>"+
			 " <td>"+info.name+"</td>"+
			  "<td>"+getSexValue(info.sex)+"</td>"+
			  "<td>"+info.providerNo+"</td>"+
			  "<td>"+info.allId+"</td>"+
			  "<td>"+(info.lable==null?"":info.lable)+"</td>"+
			  "<td>"+(info.plasmaType==1?"血浆":"全血")+"</td>"+
			  "<td>"+(info.adName==null?"":info.adName)+"</td>"+
			  "<td> </td>"+
			 " </tr>";
			 if(info.plasmaType==1){
				 xj++;
			 }else{
				 qx++;
			 }
		 }

			 $("#bustable").append( infoStr+"</tbody>"+  "</table> ");
			 $("#count").html(da.length);
			 $("#qxcount").html(qx);
			 $("#xjcount").html(xj);
			 if(type == ''){
				 $("#qx").hide();
				 $("#xj").hide();
			 }
	});
}

