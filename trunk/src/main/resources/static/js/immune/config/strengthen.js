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
	
	
	
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'immuneName', title: '免疫名称',align:'center'},
     {field: 'num', title: '针次数',align:'center'},
     {field: 'type', title: '所属类型',align:'center',templet:function(d){
      	  if(d.type==1){
      		  return '加强';
      	  }else{
      		  return '未知';
      	  }
  	  }},
  	  {field: 'intervalDay', title: '间隔天数',align:'center'},
      {field: 'minNominal', title: '最小标称值',align:'center'},
      {field: 'maxNominal', title: '最大标称值',align:'center'},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
     {field: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
  dataAllIsOpenLimit("strengthen",cols,{"token" : getToken()},GetURLInfo() + 'strongBasicRulesList',GetURLInfo() +'strongBasicRulesUpdate',GetURLInfo() + "deleteStrongBasicRules",true,'',10);
	
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
		currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
	}); 
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 	
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
})



	//搜索
	function doSearch(){
		var immuneId = $("#immuneId").val()
		layui.table.reload('strengthen', {where: {
			"immuneId": immuneId
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#immAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'strongBasicRulesAddPage',true,['400px', '340px']);
		 		}
		 	)
		 }();

		 /**
		  * 刷新按钮
		  * @returns
		  */
		 function refresh(){
		 	location.reload();
		 }		 