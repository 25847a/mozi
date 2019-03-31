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
     {field: 'effectiveMin', title: '效价值最小范围值', align:'center'},
     {field: 'effectiveMax', title: '效价值最大范围值', align:'center'},
     {field: 'packingEffective', title: '包装后的效价值', align:'center'},
     {field: 'packingName', title: '包装后的免疫类型', align:'center'},
     {field: 'isDelete', title: '是否删除', align:'center',templet:function(d){
    	 var text = '-';
         if (d.isDelete == 0) {
             text = "否";
         } else if (d.isDelete == 1) {
             text = "是";
         }	
         return text;
  	  }},
     {field: 'createDate', title: '创建时间',align:'center',templet:function(d){
   	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));
   	  }},
     {fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
		]
	]
   data("rumsers",cols,GetURLInfo() + 'AssaySettingList',GetURLInfo() +'assaySettingUpdate',GetURLInfo() + "deleteAssaySetting");
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
//加载免疫类型选项
function settingList(url,data){
	//获取所有免疫类别设置类型
	$.ajax({
		url:url,
		type:"post",
		dataType:"json",
		data:data,
		success:function(result){
			if(result.code == -1){
				for(var i = 0; i < result.data.length; i++){
					var str= "<option value='"+result.data[i].id+"'>"+result.data[i].immuneName+"</option>";
					$("#immuneId").append(str);
				}
			}
		}
	});
};
	//搜索
	function doSearch(){
		var isDisable = $("#isDisable").val()
		var immuneId = $("#immuneId").val()
		layui.table.reload('rumsers', {where: {
			"isDisable": isDisable,
			"immuneId":immuneId,
			}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
	
	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea(GetURLInfo() +'assaySettingAdd',true,[ '500px', '380px' ]);
		 		}
		 	)
		 }();
