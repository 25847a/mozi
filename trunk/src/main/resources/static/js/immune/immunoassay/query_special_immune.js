$(function(){
	//获取所有免疫类别设置类型
	$.ajax({
		url:"/immuneSetting/getAmmuneSetting",
		type:"post",
		dataType:"json",
		success:function(result){
			if(result.code == -1){
				for(var i = 0; i < result.data.length; i++){
					var str= "<option value='"+result.data[i].id+"'>"+result.data[i].immuneName+"</option>";
					$("#immuneId").append(str);
				}
			}
		}
	});
	layui.use(['table'], function(){
		//未化验人员
	  var querySpecialImmune = [[
	      {field: 'providerNo', title: '献浆卡号', align:'center'}
	      ,{field: 'name', title: '姓名', align:'center'}
	      ,{field: 'sex', title: '性别', align:'center',templet:function(data){
	    	  return data.sex == 0?"男":"女";
	      }}
	      ,{field: 'immuneRegisterId', title: '免疫编号', align:'center'}
	      ,{field: 'immuneName', title: '类型', align:'center'}
	      ,{field: 'updateDate', title: '检测日期', align:'center',templet:function(data){
	    	  return new Date(data.updateDate).Format("yyyy-MM-dd");
	      }}
	      ,{field: 'effectiveValue', title: '效价', align:'center'}
	      ,{field: 'result', title: '检测结果', align:'center',templet:function(data){
	    	  var text='-';
	    	  if(data.result == 0){
	    		  text="合格";
	    	  }else if(data.result == 1){
	    		  text="不合格";
	    	  }
	    	  return text;
	      }}
	      ,{field: 'admin', title: '检测人', align:'center'}
	      ]]
	  dataAllIsOpenLimit("newtestS",querySpecialImmune,{"token" : getToken()},"querySpecialImmune",'','',true,function(){
		  var tab = $("#newtestS").next().find('.layui-table tbody tr');
	  },10);
	  //导出excel
	  $("#export").click(function(){
		  var startDate = $("#startDate").val();
		  var endDate = $("#endDate").val();
		  var name = $("#name").val();
		  var minValue = $("#minValue").val();
		  var maxValue = $("#maxValue").val();
		  var result = $("#result").val();
		  var inspector = $("#inspector").val();
		  var providerNo = $("#providerNo").val();
		  var type = $("#type").val();
		  window.location.href="/immuneAssay/exportSpecialImmune?startDate="+startDate+"&endDate="+endDate+"&name="+name+"&minValue="+minValue+"&maxValue="+maxValue+"&result="+result+"&inspector="+inspector+"&providerNo="+providerNo+"&type="+type;
	  });
	  
	  });
	
	//重置
	$("#reset").click(function(){
		$("#read input").val("");
		$("#read select").val("");
	});
	//搜索按钮点击事件
	 $("#query").click(function(){
	 	doSearch();
	 	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	 })
	//开始时间
	 initDate("#startDate");
	 //结束时间
	 initDate("#endDate");
});///////////////
 


//搜索
function doSearch(){
	var endDate = $("#endDate").val();
	var startDate = $("#startDate").val();
	var minValue = $("#minValue").val();
	var maxValue = $("#maxValue").val();
	var result = $("#result").val();
	var name = $("#name").val();
	var immuneId = $("#immuneId").val();
	var providerNo = $("#providerNo").val();
	layui.table.reload('newtestS', {where: {"startDate": startDate,"endDate": endDate,"minValue": minValue,"maxValue": maxValue,"result": result,"name": name,"immuneId": immuneId,"providerNo": providerNo}});
}

function getToken() {
	return localStorage.getItem("token");
}
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}