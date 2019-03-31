$(function(){
/*列表数据显示借口*/
	var damageOrder= $("#damageOrder").val();
   var cols= [[ //表头 
    	{field: 'id', title: '编号', sort: true,align:'center', fixed: 'left'},
		{field: 'name', title: '耗材名称', sort: true,align:'center', fixed: 'left'},
		{field: 'type', title: '类型', sort: true,align:'center', fixed: 'left'},
		{field: 'unit', title: '单位', sort: true,align:'center', fixed: 'left'},
		{field: 'supply', title: '供应商', sort: true,align:'center', fixed: 'left'},
		{field: 'batchNumber', title: '批号', sort: true,align:'center', fixed: 'left'},
		{field: 'num', title: '数量', sort: true,align:'center', fixed: 'left'},
		{field: 'createDate', title: '创建时间', sort: true,align:'center', fixed: 'left',templet: function (d){
			return dateFtt("yyyy-MM-dd",new Date(d.createDate));
		} }
    ]]
dataAll('details',cols,{"damageOrder" : damageOrder}, GetURLInfo()+'/queryLossDatelis','','');
})
 
