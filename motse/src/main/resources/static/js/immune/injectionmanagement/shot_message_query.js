$(function(){
	// 根据普免加载免疫类型选项
	$.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmunTypeOne",
        datatype : "json",
        success : function(result) {
           if(result.code==-1){
        	   var data = result.data;
        	   if(data.isDisable==1){
        		   settingList("/immuneSetting/queryAmmuneSetting",{type:1});
        	   }else{
        		   settingList( "/immuneSetting/queryImmuneSettingList",null);
        	   }
           }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
	layui.use(['table'], function(){
		var clos = [[
		   {type: 'checkbox', fixed: 'left'}
		  ,{field: 'updateDate', title: '注射时间', align:'center',templet:function(data){
	    	  return dateFtt("yyyy-MM-dd",new Date(data.updateDate));
	       }}
	      ,{field: 'providerNo', title: '献浆卡号', align:'center'}
	      ,{field: 'name', title: '姓名', align:'center'}
	      ,{field: 'supplies', title: '疫苗名称', align:'center'}
	      ,{field: 'standard', title: '规格', align:'center'}
	      ,{field: 'batchNumber', title: '批号', align:'center'}
	      ,{field: 'supply', title: '生产厂家', align:'center'}
	      ,{field: 'injection', title: '注射量', align:'center'}
	      ,{field: 'validMonth', title: '有效期', align:'center',templet:function(data){
	    	  return dateFtt("yyyy-MM-dd",new Date(data.updateDate));
	       }}
	      ,{field: 'num', title: '注射次数', align:'center'}
	      ,{field: 'isShot', title: '注射', align:'center',templet:function(data){
	    	  	if(data.isShot==0){
	    	  		return '否';
	    	  	}else{
	    	  		return '是';
	    	  	}
	      }}
	      ,{field: 'remarks', title: '不良反应及处理', align:'center'}
	      ,{field: 'type', title: '备注', align:'center',templet:function(data){
	    	  return getInjection(data.type);
	      }}
	      ]]
		var isShot = $("#isShot").val();
	  dataAllIsOpenLimit("newtestS",clos,{"isShot" :isShot},"queryShotMsg",'','',true,function(){},10);
		
	});
	//搜索按钮点击事件
		 $("#query").click(function(){
		 	doSearch();
		 	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
		 });
	  //重置
	  $("#reset").click(function(){
		  $("#search_par input").val("");
		  $("#search_par select").val("");
	  });
	//注射开始时间
	initDate("#startDate");
	//注射结束时间
	initDate("#endDate");
});
//乙肝打印
$("#pudaochu").click(function(){
	var startDate = $("#startDate").val(); 
  	var endDate = $("#endDate").val(); 
  	var providerNo = $("#providerNo").val();
  	var name = $("#name").val();
  	var num = $("#num").val();
  	var isShot = $("#isShot").val(); 
  	var immuneId = $("#immuneId").val();
	layer.open({
		type : 2,
		title:"打印供血浆者乙肝免疫注射记录",
		maxmin : true,
		area : [ '100%', '100%' ],
		content : "/ordinaryInjection/downloadPu?startDate="+startDate+"&endDate="+endDate+"&providerNo="+providerNo+"&name="+name+"&num="+num+
		"&isShot="+isShot+"&immuneId="+immuneId+"&type="+0
	})
});
//特免打印
$("#tedaochu").click(function(){
	var startDate = $("#startDate").val(); 
  	var endDate = $("#endDate").val(); 
  	var providerNo = $("#providerNo").val();
  	var name = $("#name").val();
  	var num = $("#num").val();
  	var isShot = $("#isShot").val(); 
  	var immuneId = $("#immuneId").val();
	layer.open({
		type : 2,
		title:"打印供血浆者乙肝免疫注射记录",
		maxmin : true,
		area : [ '100%', '100%' ],
		content : "/ordinaryInjection/downloadTe?startDate="+startDate+"&endDate="+endDate+"&providerNo="+providerNo+"&name="+name+"&num="+num+
		"&isShot="+isShot+"&immuneId="+immuneId+"&type="+1
	})
});

//加载免疫类型选项
function settingList(url,data){
	// 加载免疫类型选项
    $.ajax({
        type : "POST",
        url :url,
        datatype : "json",
        data:data,
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
                $("#immuneId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
}
//搜索
function doSearch(){
	var startDate = $("#startDate").val(); 
  	var endDate = $("#endDate").val(); 
  	var providerNo = $("#providerNo").val();
  	var name = $("#name").val();
  	var num = $("#num").val();
  	var isShot = $("#isShot").val(); 
  	var immuneId = $("#immuneId").val();
	layui.table.reload('newtestS',{where:{"startDate":startDate,"providerNo":providerNo,"endDate":endDate,"providerNo":providerNo,"name":name,"num":num,"isShot":isShot,"immuneId":immuneId}});	
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