$(function(){
  var  cols= [[ //表头
     {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
     {field: 'typeCode', title: '类别编码',align:'center'},
     {field: 'immuneName', title: '免疫名称',align:'center'},
     {field: 'basicNum', title: '基础针数',align:'center'},
     {field: 'strengthenNum', title: '加强针数',align:'center'},
     {field: 'invalidDate', title: '失效期',align:'center'},
     {field: 'type', title: '所属类型',align:'center',templet:function(d){
      	  if(d.type==0){
      		  return '普通';
      	  }else if(d.type==1){
      		  return '普免';
      	  }else if(d.type==2){
      		  return '特免';
      	  }else{
      		  return '未知';
      	  }
  	  }},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
     {field: 'updateDate', title: '修改时间', align:'center',templet:function(d){
    	  return dateFtt("yyyy-MM-dd",new Date(d.updateDate));
   	  }},
     {field: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
  dataAllIsOpenLimit("immunetable",cols,{"token" : getToken()},GetURLInfo() + 'immuneSettingList',GetURLInfo() +'immuneUpdate',GetURLInfo() + "deleteImmuneSetting",true,'',10);
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
	
})

	//搜索
	function doSearch(){
		var immuneName = $("#immuneName").val()
		layui.table.reload('immunetable', {where: {
			"immuneName": immuneName
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#immAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'immuneAddPage',true,['400px', '280px']);
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