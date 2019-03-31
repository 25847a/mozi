// 复选框 ID 集合
var ids=new Array();

$(function(){
    /*列表数据显示借口*/ 
	layui.use(['laydate', 'laypage', 'table', ], function(){
	  var laydate = layui.laydate, //日期
	  laypage = layui.laypage, //分页 
	  table = layui.table, //表格 
	  element = layui.element; //元素操作  
	  //执行 table
	  table.render({
	    elem: '#newtest' ,
	    url: GetURLInfo() +'queryAboutPeople', //数据接口
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
	      {field: 'providerNo',title: '献浆编号',width:"10%",align: 'center',sort: true},
	      {field: 'name', title: '姓名',width:"10%",align:'center'},
	      {field: 'sex', title: '性别',width:"6%", align:'center',templet: function (d){ // 单元格格式化函数
	          return getSexValue(d.sex);
	      }},
	      {field: 'immuneName', title: '类型',width:"10%",align:'center'},
	      {field: 'birthday', title: '出生日期',width:"10%",align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.birthday==undefined){
	    		  text="";
	    	  }else{
	    		  text = dateFtt("yyyy-MM-dd",new Date(d.birthday));
	    	  }
	  	  		return text;
	  	   }},
	      {field: 'collectionDate', title: '最后采浆日期', width:"12%",align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.maxDate==undefined){
	    		  text="";
	    	  }else{
	    		  text = dateFtt("yyyy-MM-dd",new Date(d.maxDate));
	    	  }
	  	  		return text;
	  	   }},
	      {field: 'aboutDate', title: '预约日期', width:"12%", sort: true,align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.aboutDate==undefined){
	    		  text="";
	    	  }else{
	    		  text = dateFtt("yyyy-MM-dd",new Date(d.aboutDate));
	    	  }
	  	  		return text;
	  	   }}, 
	      {field: 'examineStatus',title: '当前状态',width:"10%",align: 'center',templet: function (d){ // 单元格格式化函数
	          if(d.examineStatus==0){
	        	  return '未审核';
	          }else{
	        	  return '已审核';
	          }
	      }},
	      {field: 'phone',title: '电话',width:"10%",align: 'center'},      
	      {field: 'addressx', title: '地址',width:"30%",align:'center'},
	      {field: 'num', title: '组号',width:"10%",align:'center'},
	      {field: 'remarks', title: '备注',width:"10%",align:'center'},
	    ]],
	   
	 //表格 tr 单击事件
	   done:function(res,curr,count){
		   var tab = $("#newtest").next().find('.layui-table tbody tr');
		   tab.click(function(event){
		   	  var tr =$(event.target).closest("tr")[0];
		   	 $("#providerNo").val($(tr).find("td").eq(0).find("div").html());
		   	 $("#name").val($(tr).find("td").eq(1).find("div").html());
		   	 $("#sex").val($(tr).find("td").eq(2).find("div").html());
		   	 $("#immuneName").val($(tr).find("td").eq(3).find("div").html());
		   	 $("#birthday").val($(tr).find("td").eq(4).find("div").html());
		   	 $("#maxDate").val($(tr).find("td").eq(5).find("div").html());
		   	 $("#aboutDate").val($(tr).find("td").eq(6).find("div").html());
		   	 $("#status").val($(tr).find("td").eq(7).find("div").html());
		   	 $("#phone").val($(tr).find("td").eq(8).find("div").html());
		   	 $("#addressx").val($(tr).find("td").eq(9).find("div").html());
		   	 $("#remarks").val($(tr).find("td").eq(11).find("div").html());
		   });
	   }
	  });
	  
	}); 
	//搜索按钮点击事件
	$("#searchBtn").click(function(){
		doSearch();
	});
	// 重置
	$("#btn-reset").click(function(){
		document.getElementById("search_par").reset();
		document.getElementById("search").reset();
	}); 
	})
	//搜索
	function doSearch(){
		var aboutDate = $("#aboutDate").val()//预约人数
		var future = $("#future").val()//预约人数
		layui.table.reload('newtest', {page: {curr:1},where: {"aboutDate": aboutDate,"future": future}});
	}
	function getToken() {
		return localStorage.getItem("token");
	}
	// 导出
	$("#daochu").click(function(){
		var providerNo=$("#providerNo").val();
		var name=$("#name").val();
		var aboutDate=$("#aboutDate").val();
		var sex=$("#sex").val();
		var immuneName=$("#immuneName").val();
		var birthday=$("#birthday").val();
		var maxDate=$("#maxDate").val();
		var status=$("#status").val();
		var groupName=$("#groupName").val();
		var phone=$("#phone").val();
		var addressx=$("#addressx").val();
		var remarks=$("#remarks").val();
		var token="providerNo="+providerNo+"&name="+name+
			"&sex="+sex+"&immuneName="+immuneName+
			"&birthday="+birthday+"&maxDate="+maxDate+
			"&aboutDate="+aboutDate+"&status="+status+
			"&phone="+phone+"&addressx="+addressx+
			"&groupName="+groupName+"&remarks="+remarks;
		if(ids.length>0){
			window.open(GetURLInfo() +"downAbout?ids="+ids.join(","));
		}else{
			window.open(GetURLInfo() +"downAbout?"+token);
		}
	}) 
function refresh(){
		location.reload();
	}
/*预约日期*/ 
initDate("#aboutDate");