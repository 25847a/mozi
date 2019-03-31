var table;

$(function(){
	$("#query").click(function(){
		loadInfo();
	});
})

/**
 * 加载浆员信息
 * @param providerNo
 * @returns
 */
function loadInfo(){
	var providerNo=$("#providerNo").val();
	var name=$("#name").val();
	if(providerNo.length<1 && name.length<1){
		layer.alert('请输入查询条件');
		return;
	}
	//已体检人员列表
	layui.use(['laydate', 'laypage', 'table'], function(){
		  var laydate = layui.laydate, //日期
	  	  laypage = layui.laypage, //分页
	      table = layui.table, //表格
	      element = layui.element; //元素操作
		// 执行 table
		table.render({
			elem : '#newtest',
			url : GetURLInfo() + 'queryScrapPlasmaList', // 数据接口
			method : 'post',
			limits : [ 5, 10, 15, 20 ],
			limit : 5, // 每页默认显示的数量
			page : true, // 开启分页
			where : { // 查询条件
				"providerNo" : providerNo,"name":name
			},
			response : { // //定义后端 json 格式，详细参见官方文档
				statusName : 'code', // 数据状态的字段名称，默认：code
				statusCode : -1, // 成功的状态码，默认：0
				msgName : 'message', // 状态信息的字段名称，默认：msg
				count : "count", // 数据总数的字段名称，默认：count
				data : 'data' // 数据列表的字段名称，默认：data
			},
			cols : [ [ // 表头
			{
				 type: 'checkbox',fixed: 'left'
			}, {
				field : 'providerNo',
				title : '献浆员卡号',
				align : 'center'
			}, {
				field : 'name',
				title : '姓名',
				align : 'center'
			}, {
				field : 'plasmAmount',
				title : '采浆量',
				align : 'center'
			}, {
				field : 'updateDate',
				title : '采浆时间',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					if(null!=d.updateDate){
						return dateFtt('yyyy-MM-dd',new Date(d.updateDate)); 
					}
					return '';
				}
			}
			] ]
		});
		//批量报废
		$('#scrap').unbind('click').click(function() {
			var checkStats = table.checkStatus('newtest');
			var data = checkStats.data;
			if(data.length < 1){
				layer.alert('请选择需要报废的血浆');
			}else{
				var ids=new Array();
				for(var i=0;i<data.length;i++){
					ids.push(data[i].id);
				}
				$.ajax({
				  url: "/plasmaStock/updatePlasmaScrapList",
				  type: "POST",
				  data: {
				    "id": ids
				  },
				  traditional: true,//这里设置为true
				  success: function(result) {
					  layer.alert(result.message,function(){
						  layer.closeAll();
						  loadInfo();
					  });
				  }
				});
			}
			
			return false;
		});
	});
}
