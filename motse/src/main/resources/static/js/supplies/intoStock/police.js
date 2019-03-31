var count = new Array()
$(function(){
	 // 加载仓库选项
    $.ajax({
        type : "POST",
        url :GetURLInfo()+"/querySuppliesConfig",
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
            	count[o]=result.data[o].lable;
            }
        },
        error : function() {
            alert("请稍后试试");
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
  //审批列表
/*列表数据显示借口*/
layui.use(['laydate', 'laypage', 'table', ], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页
  ,table = layui.table //表格
  ,element = layui.element; //元素操作
  //执行 table
  table.render({
    elem: '#newtest'
    ,url: GetURLInfo()+'/queryPoliceList' //数据接口
    ,where : {
		token : getToken()
	}
    ,page: true //开启分页
    ,limit: 10 //每页默认显示的数量
    ,response: { // //定义后端 json 格式，详细参见官方文档
        statusName: 'code', //数据状态的字段名称，默认：code
        statusCode: -1, //成功的状态码，默认：0
        msgName: 'message', //状态信息的字段名称，默认：msg
        count: "count", //数据总数的字段名称，默认：count
        data: 'data' //数据列表的字段名称，默认：data
   },
   cols: [[ //表头
      {field: 'name', title: '耗材名称', align:'center'}
      ,{field: 'type', title: '类型', sort: true,align:'center'}
      ,{field: 'unit', title: '单位', align:'center'}
      ,{field: 'supply', title: '供应商', align:'center'}
      ,{field: 'surplusNumber', sort: true, title: '库存数量', align:'center',templet: function (d){ 
    	  if(parseInt(d.surplusNumber)<=parseInt(count[3])){
    		  return '<span style="color: #eb2303;"> '+d.surplusNumber+' </span>';
    	  }else if(parseInt(d.surplusNumber)<=parseInt(count[4])){
    		  return '<span style="color: #70bf80;"> '+d.surplusNumber+' </span>';
    	  }else if(parseInt(d.surplusNumber)<=parseInt(count[5])){
    		  return '<span style="color: #0d0ae9;"> '+d.surplusNumber+' </span>';
    	  }
            return d.surplusNumber;
        }}
      ,{field: 'batchNumber', title: '批号', align:'center'}
      ,{field: 'expiryTime',sort: true, title: '有效期', align:'center',templet: function (d){
			 	var date =d.expiryTime- new Date().getTime();
			 	var day =Math.floor(date/86400000);
			 	if(parseInt(day)<=parseInt(count[0])){
			 		 return '<span style="color: #eb2303;"> '+$.myTime.UnixToDate(d.expiryTime)+' </span>';
			 	}else if(parseInt(day)<=parseInt(count[1])){
			 		 return '<span style="color: #70bf80;"> '+$.myTime.UnixToDate(d.expiryTime)+' </span>';
			 	}else if(parseInt(day)<=parseInt(count[2])){
			 		return '<span style="color: #0d0ae9;"> '+$.myTime.UnixToDate(d.expiryTime)+' </span>';
			 	}
			 	return dateFtt("yyyy-MM-dd",new Date(d.expiryTime));
		} }
    ]]
  });
  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    var data = obj.data //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    });
    
   
    	 
  });
    	/*到期时间*/ 
		! function() {
		//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		$('#kutiem').on('click', function() {
			layer.ready(function() {
				layer.open({
					type: 2,
					title: '到期时间设置',
					maxmin: true,
					area: ['450px', '220px'],
					content: GetURLInfo()+'/policeTime', 
				})
			});
		})
	}(); 
     	/*耗材数量设置*/ 
		! function() {
		//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
		$('#kutdmit').on('click', function() {
			layer.ready(function() {
				layer.open({
					type: 2,
					title: '耗材数量设置',
					maxmin: true,
					area: ['450px', '220px'],
					content: GetURLInfo()+'/policeNum', 
				})
			});
		})
	}(); 

	//搜索按钮点击事件
	$("#query").click(function(){
		doSearch();
	});
	//重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 
})//到这里

//搜索
function doSearch(){
	var name = $("#name").val();
	var supply = $("#supply").val();
	var expiryTime = $("#expiryTime").val();
	layui.table.reload('newtest', {page: {curr:1},where: {"name": name,"supply": supply,"expiryTime": expiryTime}});
}
function getToken() {
	return localStorage.getItem("token");
}
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}
/*日期时间*/ 
dayControl("expiryTime");
