$(function(){
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate, //日期
  	  laypage = layui.laypage, //分页
      table = layui.table, //表格
      form = layui.form,
      element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#rumsers',
    url: GetURLInfo() + 'immuneConfig', //数据接口
    where: {
 		type : 'open_config'
 		},
    page: true, //开启分页
    response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
    cols: [[ //表头
        {field: 'id',title: 'id',display : 'none',minWidth : '0',width : '0',type : "space"},
        {field: 'configName', title: '配置名称',align:'center'},
        {field: 'lable', title: '配置参数', align:'center'},
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
      	 {field:'isDisable', title:'操作', templet: '#switchTpl', unresize: true,templet:function(d){
      		 var isDisable='未配置';
      		 if(d.isDisable==0){
      			text= '<input type="checkbox" name="config" value='+d.id+' lay-skin="switch" lay-text="开启|关闭" lay-filter="sexDemo" checked="checked"/>'
      		 }else if(d.isDisable==1){
      			text= '<input type="checkbox" name="config" value='+d.id+' lay-skin="switch" lay-text="开启|关闭" lay-filter="sexDemo" />'
      		 }
      		return text;
     	  }}
   		]
   	]
  });
    //监听操作
    form.on('switch(sexDemo)', function(obj){
    	var type=obj.elem.checked;
    	var id=this.value;
    	$.ajax({
    		type:'post',
    		url:GetURLInfo()+'/updateImmuneConfigType',
    		data:{"type":type,"id":id},
    		dataType:'json',
    		success:function(result){
    			parent.layer.alert(result.message,function(){
    				parent.layer.closeAll();
    			});
    		},
    		error:function(){
    			parent.layer.alert("操作错误,请重试",function(){
	   				parent.layer.closeAll();
	   			});
    		}
    	});
      
    });
	//layui-table-cell laytable-cell-1-id 
  });/// 
//重置
$("#btn-reset").click(function(){
	$("#search_par input").val("");
	$("#search_par select").val("");
});
//搜索按钮点击事件
$("#searchBtn").click(function(){
	doSearch();
	currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
});
})///到这里

//搜索
	function doSearch(){
		var configName = $("#configName").val()
		var isDisable = $("#isDisable").val()
		layui.table.reload('rumsers', {where: {"type":"open_config","configName": configName,"isDisable":isDisable}});
	}

	function getToken() {
		return localStorage.getItem("token");
	}

	 ! function() {
		 	/*新增*/
		 	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		 	$('#depotAdd').on('click', function() {
		 		addWithArea('config/configAdd',true,[ '500px', '380px' ]);
		 		}
		 	)
		 }();
