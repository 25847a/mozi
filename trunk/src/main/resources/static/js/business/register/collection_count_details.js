$(function(){
/*列表数据显示借口*/
	var providerNo= $("#providerNo").val();
	  var cols= [[ //表头 
	    	{field: 'providerNo', title: '献浆卡号',align:'center', fixed: 'left'},
			{field: 'name', title: '姓名',align:'center', fixed: 'left'},
			{field: 'plasmAmount', title: '采浆量',align:'center', fixed: 'left'},
			{field: 'updateDate', title: '采浆时间',align:'center', fixed: 'left',templet: function (d){
		    	  var text = '-';
		    	  if(d.updateDate==undefined){
		    		  text="";
		    	  }else{
		    		  text = dateFtt("yyyy-MM-dd",new Date(d.updateDate));
		    	  }
		  	  		return text;
		  	   }},
			{field: 'money', title: '发放费用',align:'center', fixed: 'left'}	
	    ]];
dataAllAndWHWithPage("details",cols,{"providerNo" : providerNo}, GetURLInfo()+'/queryCollectionCountDetails','','',[ '500px', '335px' ],true);
//
//uurl
})
 
