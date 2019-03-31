$(function() {
	layui.use([ 'table' ], function() {
		var cols = [ [ // 表头
		// {type: 'checkbox', fixed: 'left'},
		{field : 'id',title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"},
		{field : 'name',title : '被邀请人',align : 'center'},
		{field : 'providerNo',title : '浆员卡号',align : 'center'},
		{field : 'phone',title : '电话号码',align : 'center'},
		{field : 'addressx',title : '地址',align : 'center'},
		
		{field : 'fname',title : '邀请人',align : 'center'},
		{field : 'num',title : '浆员卡号',align : 'center'},
		{field : 'fphone',title : '电话号码',align : 'center'},
		{field : 'faddressx',title : '地址',align : 'center'},
		
		{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'}
		] ]
		data("newtestS", cols, GetURLInfo() + 'queryBindMsgList');
		// pageListData("newtestS",{"inviter":inviter,"startDate":startDate,"endDate":endDate,"invited":invited},cols,GetURLInfo()
		// + 'queryBindMsgList');
		// 搜索按钮点击事件
		$("#searchBtn").click(function() {
			doSearch();
			currentPageAllAppoint = 1; // 当点击搜索的时候，应该回到第一页
		});

		table.on('tool(demo)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
												// lay-filter="对应的值"
			var data = obj.data // 获得当前行数据
			, layEvent = obj.event; // 获得 lay-event 对应的值
			if (layEvent === 'detail') {
				if(data.inviteDate == null){
					layer.alert("没有绑定信息，不需要解绑");
				}else{
					layer.ready(function() {
						$("#bisudcost").show();
						$("#layui-layer-shade1").show();
						// 否
						$("#cannel2").click(function() {
							$("#bisudcost").hide();
							location.reload();
						})
						// 是
						$("#butbind").click(function() {
							var id = data.id;
							$.ajax({
								url : GetURLInfo() + "unbind",
								type : "post",
								dataType : "json",
								data : {
									"id" : id
								},
								success : function(result) {
									console.log(result);
									location.reload();
								}
							});
							
						})
					});
				}
				}else if(layEvent === 'add'){
					if(data.inviteDate != null && data.inviterName != null){
						layer.alert("信息已经绑定，不能重复绑定");
					}else{
						add(GetURLInfo() + 'toBindAdd?id='+data.id, true);
					}
				}
		})
	})
	/*
	 * initDate("#startDate"); initDate("#endDate");
	 */
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var laydateS = layui.laydate;
		// 单控件
		laydate.render({
			elem : "#startDate",
			format : 'yyyy-MM-dd',
			value : "",
			ready : function(date) {
				// console.log(date);
			},
			done : function(value, date, endDate) {
				// console.log(value, date, endDate);
			}
		})
	});
	layui.use('laydate', function() {
		var laydate = layui.laydate;
		var laydateS = layui.laydate;
		// 单控件
		laydate.render({
			elem : "#endDate",
			format : 'yyyy-MM-dd',
			value : "",
			ready : function(date) {
				// console.log(date);
			},
			done : function(value, date, endDate) {
				// console.log(value, date, endDate);
			}
		})
	});

	function doSearch() {
		var inviter = $("#inviter").val()
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val()
		var invited = $("#invited").val();
		layui.table.reload('newtestS', {
			where : {
				"inviter" : inviter,
				"startDate" : startDate,
				"endDate" : endDate,
				"invited" : invited
			}
		});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
});

//重置
$("#reset").click(function(){
	$("input[type=text]").val("");
});

!function() {
	// 新增
	// 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#bindAdd').on('click', function() {
		add(GetURLInfo() + 'toBindAdd', true);
	});
}();

// 列表数据显示借口
/*
 * layui.use(['laydate', 'laypage', 'table', ], function(){ var laydate =
 * layui.laydate //日期 ,laypage = layui.laypage //分页 ,table = layui.table //表格
 * ,element = layui.element; //元素操作
 * 
 * //执行 table
 * 
 * table.render({ elem: '#newtestS' ,url: 'queryBindMsgList' //数据接口 ,page: true
 * //开启分页 ,limits:[5, 10, 15,20], limit: 5, //每页默认显示的数量 response: { // //定义后端
 * json 格式，详细参见官方文档 statusName: 'code', //数据状态的字段名称，默认：code statusCode: -1,
 * //成功的状态码，默认：0 msgName: 'message', //状态信息的字段名称，默认：msg count: "count",
 * //数据总数的字段名称，默认：count data: 'data' //数据列表的字段名称，默认：data } ,cols: [[ //表头
 * {field: 'id', title: '编号', width:90, sort: true,align:'center', fixed:
 * 'left'} ,{field: 'inviter', title: '邀请人', align:'center'} ,{field:
 * 'inviterIDNo', title: '邀请人身份证号', align:'center'} ,{field: 'lev', title:
 * '邀请人等级', align:'center'} ,{field: 'invited', title: '被邀请人',align:'center'}
 * ,{field: 'invitedIDNo', title: '被邀请人身份证号',align:'center'} ,{field:
 * 'inviteDate', title: '邀请时间', align:'center'} ,{fixed: 'right',title: '操作',
 * width: 165,align: 'center',toolbar: '#barDemo'}
 *  ]] }); table.on('tool(demo)', function(obj){
 * //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值" var data = obj.data
 * //获得当前行数据 ,layEvent = obj.event; //获得 lay-event 对应的值 if(layEvent ===
 * 'detail') { layer.ready(function() { $("#bisudcost").show();
 * $("#layui-layer-shade1").show(); //否 $("#cannel2").click(function(){
 * $("#bisudcost").hide(); location.reload(); }) // 是
 * $("#butbind").click(function(){ alert('ID：'+ data.id + ' 的查看操作')
 * location.reload(); })
 * 
 * }); }
 * 
 * }); }); //新增绑定 ! function() { //页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
 * $('#bindAdd').on('click', function() { layer.ready(function() { layer.open({
 * type: 2, title: '新增绑定', maxmin: true, area: ['500px', '300px'], content:
 * '../../Popup/Bus/bind_add.html', }) }); }) }(); //日期时间 layui.use('laydate',
 * function(){ var laydate = layui.laydate; var laydateS = layui.laydate; //单控件
 * laydate.render({ elem: '#startTime' ,format: 'yyyy-MM-dd' ,value:
 * '2017-09-10' ,ready: function(date){ console.log(date); } ,done:
 * function(value, date, endDate){ console.log(value, date, endDate); } })
 * laydate.render({ elem: '#startTimeimd' ,format: 'yyyy-MM-dd' ,value:
 * '2017-09-10' ,ready: function(date){ console.log(date); } ,done:
 * function(value, date, endDate){ console.log(value, date, endDate); } }) });
 */
