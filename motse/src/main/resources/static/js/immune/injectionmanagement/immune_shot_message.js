
$(function(){
	$.ajax({
		url:"/immuneSetting/getAmmuneSetting",
		dataType:"json",
		type:"post",
		success:function(result){
			for(var o in result.data){
				var str = "<option value='"+result.data[o].id+"'>"+result.data[o].immuneName+"</option>";
				$("#immuneId").append(str);
			}
		
		}
	});
	layui.use(['table'], function(){
		//免疫基本信息
	  var imuneMsg = [[{field: 'providerNo', title: '献浆卡号', align:'center'}
	      ,{field: 'name', title: '姓名', align:'center'}
	      ,{field : 'immuneId',title : 'immuneId',display : 'none',minWidth : '0',width : '0',type : "space"}
	      ,{field: 'immuneName', title: '免疫名称', align:'center'}
	      ,{field: 'jc', title: '基础针次数', align:'center',templet:function(data){
	    	  	if(data.jc==undefined){
	    	  		return 0;
	    	  	}else{
	    	  		return data.jc;
	    	  	}
	      }}
	      ,{field: 'jq', title: '加强针次数', align:'center',templet:function(data){
	    	  	if(data.jq==undefined){
	    	  		return 0;
	    	  	}else{
	    	  		return data.jq;
	    	  	}
	      }}
	      ]];
	  dataAllIsOpenLimit("newtestS",imuneMsg,{"token" : getToken()},'imuneMsg',
			  '','',true,function(){
		  var tab = $("#newtestS").next().find('.layui-table tbody tr');
		  tab.click(function(event) {
			  var tr = $(event.target).closest("tr")[0];
			  var providerNo = $(tr).find("td").eq(0).find("div").html();
			  var immuneId = $(tr).find("td").eq(2).find("div").html();
			  layui.table.reload('newtest',{where:{"providerNo":providerNo,"immuneId":immuneId}});
			  layui.table.reload('newtesta',{where:{"providerNo":providerNo,"immuneId":immuneId}});
		  });
	  },8);
	  //基础针注射信息
	  var baseInjectMsg = [[{
		  field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
	      ,{field: 'immuneName', title: '类型', align:'center',width:'20%'}
	      ,{field: 'num', title: '针次数', align:'center',width:'20%'}
	      ,{field: 'updateDate', title: '注射日期', align:'center',width:'20%',templet:function(data){
	    	  return dateFtt("yyyy-MM-dd",new Date(data.updateDate));
	      }}
	      ,{field: 'isShot', title: '是否注射', align:'center',width:'20%',templet:function(data){
	    	  return data.isShot == 0?"未注射":"已注射";
	      }}
	      ,{field: 'supplies', title: '使用疫苗', align:'center',width:'20%'}
	      ,{field: 'injection', title: '注射量', align:'center',width:'20%'}
	      ,{field: 'batchNumber', title: '使用批号', align:'center',width:'20%'}
	      ,{field: 'admin', title: '注射护士', align:'center',width:'20%'}
	      ,{field: 'supply', title: '生产厂家', align:'center',width:'20%'}
	      ,{field: 'status', title: '是否异常', align:'center',width:'20%',templet:function(data){
	    	  return data.status == 0 ?"无异常":"有异常";
	      }}
	      ,{field: 'remarks', title: '异常处理', align:'center',width:'20%'}
	     
	      ]];
	  dataAll("newtest",baseInjectMsg,null,"baseInjectMsg",'','',function(){});
	  //加强针注射信息
	  var strengthenInjectMsg = [[
		  {field: 'providerNo', title: '献浆卡号', align:'center',width:'20%'}
      ,{field: 'immuneName', title: '类型', align:'center',width:'20%'}
      ,{field: 'num', title: '针次数', align:'center',width:'20%'}
      ,{field: 'updateDate', title: '注射日期', align:'center',width:'20%',templet:function(data){
    	  return dateFtt("yyyy-MM-dd",new Date(data.updateDate));
      }}
      ,{field: 'isShot', title: '是否注射', align:'center',width:'20%',templet:function(data){
    	  return data.isShot == 0?"未注射":"已注射";
      }}
      ,{field: 'supplies', title: '使用疫苗', align:'center',width:'20%'}
      ,{field: 'injection', title: '注射量', align:'center',width:'20%'}
      ,{field: 'batchNumber', title: '使用批号', align:'center',width:'20%'}
      ,{field: 'admin', title: '注射护士', align:'center',width:'20%'}
      ,{field: 'supply', title: '生产厂家', align:'center',width:'20%'}
      ,{field: 'status', title: '是否异常', align:'center',width:'20%',templet:function(data){
    	  return data.status == 0 ?"无异常":"有异常";
      }}
      ,{field: 'remarks', title: '异常处理', align:'center',width:'20%'}
      ]];
	  dataAll("newtesta",strengthenInjectMsg,null,"strengthenInjectMsg",'','',function(){});
	});	
	
	//重置
	$("#reset").click(function(){
		$("#sertfd input").val("");
		$("#sertfd select").val("");
	})
	
	//搜索按钮点击事件
	$("#query").click(function(){
	 doSearch();
	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	});
	
});
//搜索
function doSearch(){
  	var providerNo = $("#providerNo").val();
  	var name = $("#name").val(); 
	var immuneId = $("#immuneId").val();
	layui.table.reload('newtestS',{where:{"providerNo":providerNo,"name":name,"immuneId":immuneId}});	
}
function getToken() {
	return localStorage.getItem("token");
}




$("#aaa").click(function(){
	location.reload();
})
