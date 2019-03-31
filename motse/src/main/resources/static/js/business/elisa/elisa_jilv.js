$(function(){
	initDate("#startDate");
	initDate("#endDate");
	search();
	//initTable("toconig");
});

function search(){
	/*列表数据显示借口*/
	layui.use(['laydate', 'laypage', 'table','element'], function(){
		var laydate = layui.laydate //日期
		,laypage = layui.laypage //分页
		,table = layui.table //表格
		,element = layui.element; //元素操作
		var startDate = $("#startDate").val();
		startDate = startDate+" 00:00:00";
		var endDate = $("#endDate").val();
		endDate = endDate+ " 23:59:59";
		var allSequenceNumber = $("#allSequenceNumber").val();
		//执行 table
		table.render({
			elem: '#toconig'
				,where :{"startDate":startDate,"endDate":endDate,"allSequenceNumber":allSequenceNumber}
				,height:'400px'
					,url: "/elisaInfo/selectElisaInfoByCondition" //数据接口
					,page: true //开启分页
					,response: { // //定义后端 json 格式，详细参见官方文档
			            statusName: 'code', //数据状态的字段名称，默认：code
			            statusCode: -1, //成功的状态码，默认：0
			            msgName: 'message', //状态信息的字段名称，默认：msg
			            count: "count", //数据总数的字段名称，默认：count
			            data: 'data' //数据列表的字段名称，默认：data
			        }
					,cols: [[ //表头
						{field: 'allSequenceNumber', title: '酶标板号',  sort: true,align:'center'/*, fixed: 'left'*/}
						,{field: 'templateName', title: '酶标板名称',align:'center'}
						,{field: 'createDate', title: '化验日期',  sort: true,align:'center', templet:function(d){ return $.myTime.UnixToDate(d.createDate);}}
						,{field: 'ss', title: '标本数', align:'center'}
						,{ width: 150, align:'center', toolbar: '#barDemo'}
						]]
		});
		table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			var data = obj.data //获得当前行数据
			,layEvent = obj.event; //获得 lay-event 对应的值
			if(layEvent === 'xiangq'){
				layer.ready(function() {
					layer.open({
						type: 2,
						title: '查看详情',
						maxmin: false,
						content: '/elisaInfo/gotoEVinfo?id='+data.id,
						area: ['90%', '655px'],
					})
				});
			}else if(layEvent === 'del'){
				layer.confirm('真的删除行么', function(index){
					
					//向服务端发送删除指令
					simpleAjax("/elisaInfo/delById", {"id":data.id}, function(result) {
						var index = parent.layer.alert("删除成功!", function() {
							obj.del(); //删除对应行（tr）的DOM结构
							parent.layer.close(index);
						});
					});
					
				});
			}
		});
	})
}
