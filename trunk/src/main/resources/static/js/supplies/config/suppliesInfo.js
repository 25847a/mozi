$(function(){
	 // 加载耗材类型选项
    $.ajax({
        type : "POST",
        url :"/type/querySuppliesTypeInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#typeId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
 // 加载耗材供应商选项
    $.ajax({
        type : "POST",
        url :"/suppliesSupply/querySuppliesSupplyInfo",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#supply").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取耗材供应商失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
			var cols= [[ //表头 
					{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
					{field: 'number', title: '耗材编号',width:'9%', align:'center'},
					{field: 'name', title: '耗材名称',width:'9%',align:'center'},
					{field: 'type', title: '类型',width:'8%',align:'center'},
					{field: 'unit', title: '单位',width:'8%',align:'center'},
					{field: 'supply', title: '供应商',width:'12.2%',align:'center'},
					{field: 'category', title: '类别',width:'8%',align:'center',templet: function (d){ 
				    	 return getCategory(d.category);
			        }},
					{field: 'money', title: '进货价格',width:'9%',align:'center',templet: function (d){ 
				    	 return d.money+'元';
			        }},
					{field: 'minStock', title: '最小库存',width:'9%',align:'center'},
					{field: 'maxStock', title: '最大库存',width:'9%',align:'center'},
					{field: 'apply', title: '用于模块',width:'9%',align:'center',templet: function (d){ 
				    	  return getTemplateValue(d.apply);
			        }},
					{field: 'remark', title: '备注',width:'8%',align:'center'},
					{fixed: 'right', title: '操作',width:'12%', align: 'center', toolbar: '#barDemo'}
				]
			];
			dataAllIsOpenLimit("rumsers",cols,{"token" : getToken()},'querySuppliesInfoList',GetURLInfo() +'/suppliesInfoUpdate',GetURLInfo() + "deleteSuppliesInfo",true,'',10);
	//搜索按钮点击事件
	$("#query").click(function(){
		 doSearch();
	});
	
});//到这里

//搜索
function doSearch(){
	var param=[];
	$("#search_par input,select").each(function(){
		var item=$(this);
		var name=item.attr("name");
		var val=item.val();
		param[name]=val;
	});
	//{page: {curr:1}当点击搜索的时候，应该回到第一页
	layui.table.reload('rumsers',{page: {curr:1},where: param});
}
function getToken() {
	return localStorage.getItem("token");
}
//重置
$("#reset").click(function(){
	$("#search_par input").val("");
	$("#search_par select").val("");
}); 	
//新增
! function() {
	$('#puituation').on('click', function() {
		addWithArea(GetURLInfo() +'suppliesInfoAdd',true,[ '500px', '350px' ]);
	})
}(); 
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}