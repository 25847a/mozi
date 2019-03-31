var time = new Date().getTime();
// 复选框 ID 集合
//var ids=new Array();

$(function(){
	// 重置
	$("#btn-reset").click(function(){
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 
	
    // 加载用户选项
    $.ajax({
        type : "POST",
        url :GetURLInfo() +'queryWhereAdminInfoList',
        datatype : "json",
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#creater").append(str);
            }
        },
        error : function() {
            parent.layer.alert("操作失败",function(){
            	parent.layer.closeAll();
            })
        }
    });
	
    /*列表数据显示借口*/ 
	layui.use(['laydate', 'laypage', 'table', ], function(){
	  var laydate = layui.laydate, //日期
	  laypage = layui.laypage, //分页 
	  table = layui.table, //表格 
	  element = layui.element; //元素操作  
	  //执行 table
	  table.render({
	    elem: '#newtest' ,
	    url: GetURLInfo() +'querySeniorRegistries', //数据接口
	    method:'post',
	    where: {
	    	token : getToken()
	 	},
	 	limits:[5, 10, 15,20],
	    limit: 10, //每页默认显示的数量
		page: true, //开启分页
	    response: { // //定义后端 json 格式，详细参见官方文档
	        statusName: 'code', //数据状态的字段名称，默认：code
	        statusCode: -1, //成功的状态码，默认：0
	        msgName: 'message', //状态信息的字段名称，默认：msg
	        count: "count", //数据总数的字段名称，默认：count
	        data: 'data' //数据列表的字段名称，默认：data
	   },
	    cols: [[ //表头
	      {field: 'icNumber',title: 'IC卡号',width:"10%",align: 'center'},
	      {field: 'providerNo',title: '献浆编号',width:"10%",align: 'center'},
	      {field: 'name', title: '姓名',width:"10%",align:'center'},
	      {field: 'idNo', title: '身份证号', width:"10%",align:'center'},
	      {field: 'registriesNo', title: '登记号', width:"8%", sort: true,align:'center'}, 
	      {field: 'sex', title: '性别',width:"6%", align:'center',templet: function (d){ // 单元格格式化函数
	          return getSexValue(d.sex);
	      }},
	      {field: 'plasmaType',title: '免疫类型',width:"10%",align: 'center',templet: function (d){ // 单元格格式化函数
	          return d.plasmaType==null?'普通':d.plasmaType;
	      }},
	      {field: 'registriesType',title: '验证类型',width:"10%",align: 'center',templet: function (d){ // 单元格格式化函数
	    	  return getTypeValue(d.registriesType);
	      }},      
	      {field: 'registerDate', title: '登记日期',width:"10%",align:'center',templet:function(d){
	    	  return dateFtt("yyyy-MM-dd",new Date(d.registerDate));}  
	      },
	      {field: 'createDate', title: '建档日期',width:"10%",align:'center',templet:function(d){
	    	  return dateFtt("yyyy-MM-dd",new Date(d.createDate));}
	      },
	      {field: 'phone', title: '联系方式',width:"10%",align:'center'},
	      {field: 'addressx', title: '现住地址',width:"10%",align:'center'},
	      {field: 'imgUrl',title: 'imgUrl',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'},
	      {field: 'remarks', title: '备注',width:"10%",align:'center'},
	    ]], 
		 //表格 tr 单击事件
		   done:function(res,curr,count){
			   var tab = $("#newtest").next().find('.layui-table tbody tr');
			   tab.click(function(event){
			   	  var tr =$(event.target).closest("tr")[0];
			   	var plays= $(tr).find("td").eq(12).find("div").html();
			   	$("#plays").attr("src",plays);
			   });
		   }
	  });
	
	}); 
	
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	});
	
	
	})
	
	//搜索
	function doSearch(){
		var param=[];
		$("#search_par input,select").each(function(){
			var itme = $(this);
			var name = itme.attr("name");
			var itr = itme.val();
			param[name]=itr;
		});
		layui.table.reload('newtest', {page: {curr:1},where: param});
	}
	function getToken() {
		return localStorage.getItem("token");
	}
	
	// 导出
	$("#daochu").click(function(){
		var param="";
		$("#search_par input,select").each(function(){
			var name= $(this).attr("name");
			var itr = $(this).val();
			param+=name+"="+itr+"&";
		});
		window.open(GetURLInfo() +"downRegistries?"+param);
	}) 
/*登记日期*/ 
dayControl("registerStart");
dayControl("registerEnd");
/*建档时间*/ 
dayControl("createStart");
dayControl("createEnd");
//刷新按钮
function refresh(){
	location.reload();
}