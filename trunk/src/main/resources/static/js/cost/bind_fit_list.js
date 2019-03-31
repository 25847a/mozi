$(function(){
/*列表数据显示借口*/
layui.use(['table'], function(){
    var cols= [[ //表头
     {field: 'id',title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"}
      ,{field: 'level', title: '邀请人等级', align:'center'}
      ,{field: 'proportion', title: '奖励比例', sort: true,align:'center'}
      ,{field: 'people', title: '升级人数', sort: true,align:'center'}
      ,{field: 'num', title: '献浆次数', align:'center'}
      ,{field: 'creater', title: '添加人', align:'center'}
      ,{field: 'createDate', title: '添加时间', align:'center',templet:function(res){
    	  return new Date(res.createDate).Format("yyyy-MM-dd");
      }}
      ,{field: 'updater', title: '修改人', align:'center'}
      ,{field: 'updateDate', title: '修改时间', align:'center',templet:function(res){
    	  return new Date(res.updateDate).Format("yyyy-MM-dd");
      }}
      ,{fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'}
    ]]
    data("newtestS",cols,GetURLInfo() + 'bindFitList',GetURLInfo()+"toUpdateBindFit",GetURLInfo()+"deleteBindFit");
    $("#query").click(function(){
    	doSearch();
		currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
    });
    
    function doSearch(){
    	var level = $("#level").val();
    	var creater = $("#creater").val();
		layui.table.reload('newtestS', {where: {"level": level, "creater": creater}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}
    //操作对应的js
	  /*table.on('tool(page)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	    var data = obj.data //获得当前行数据
	    ,layEvent = obj.event; //获得 lay-event 对应的值
	    console.log(data);
	 		if(layEvent === 'update') {
	     	layer.ready(function() {
				 layer.open({
				  type: 1,
				  title: '修改 ',
				  skin: 'layui-layer-demo', //样式类名
				  closeBtn: 0, //不显示关闭按钮
				  anim: 2,
				  area: ['370px', ''],
				  shadeClose: true, //开启遮罩关闭
				  content: '<div class="gray-bg audcost" id="bisudcost">	<div class="row">	<span>X</span></div><div class="row"><div class="col-xs-2"><img src="../.././../img/gacost.jpg" style="margin-left: 71%;width: 2.5em;"></div><div class="col-xs-10" style="line-height: 2.7em;text-align: left;">您确定要取消发放（陈某某）误工补贴240元吗?</div></div><div class="row" style=" width: 100%;"><div style="margin:   0 auto;margin-left: 36%;"><div class="form-group newformcol"><div class="pull-center"><button type="button" class="btn  btn-info" style="margin-right: 1em;padding: 0.2em 1.5em;">是</button>  <button type="button" class="btn  btn-info" id="cannel" style="padding: 0.2em 1.5em;" >否</button>	</div></div></div></div></div>'
				 });
			});
	     	}else if(layEvent === 'delete'){
	     		alert("dsf");
	     	}
	  });*/
	
	//删除绑定信息
	//deleteData(GetURLInfo+"deleteBindFit");
	
	
	});
});

//重置
$("#reset").click(function(){
	$("input[type=text]").val("");
});

/*新增绑定*/
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#activityadd').on('click', function() {
		layer.ready(function() {
			layer.open({
				type: 2,
				title: '邀请配置新增',
				maxmin: true,
				area: ['500px', '340px'],
				content: GetURLInfo()+'toAddBindFit',
			})
		});
	})
}();
