$(function(){
	/*列表数据显示借口*/
	
	layui.use(['laydate', 'laypage', 'table', ], function(){
	  var laydate = layui.laydate //日期
	  ,laypage = layui.laypage //分页
	  ,table = layui.table //表格
	  ,element = layui.element; //元素操作
	  //执行 table
	  
	  table.render({
	    elem: '#newtestS'
	    ,url: 'queryCostGrantList' //数据接口
	    ,page: true //开启分页
	    ,type:"post"
	    ,where:{
	    	providerNo:getProviderNo(),
	    	updater:getUpdater(),
	    	createDateCost:getCreateDateCost(),
	    	icNumber:getIcNumber(),
	    	idNo:getIdNo(),
	    	createDateCol:getCreateDateCol(),
	    	plasmaType:getPlasmaType()
	    },
		  response: { // //定义后端 json 格式，详细参见官方文档
		        statusName: 'code', //数据状态的字段名称，默认：code
		        statusCode: -1, //成功的状态码，默认：0
		        msgName: 'message', //状态信息的字段名称，默认：msg
		        count: "count", //数据总数的字段名称，默认：count
		        data: 'data' //数据列表的字段名称，默认：data
		   }
	    ,cols: [[ //表头
	/* {type: 'checkbox', fixed: 'left'}*/
	    	{field: 'id',title : 'id',
				display : 'none',
				minWidth : '0',
				width : '0',
				type : "space"}
	        ,{field: 'providerNo', title: '献浆员卡号', align:'center'}
	        ,{field: 'name', title: '姓名', align:'center'}
	         ,{field: 'sex', title: '性别', align:'center',templet:function(obj){
	       	  return obj.sex == 0 ? "男" : "女";
	         }}
	         ,{field: 'bloodType', title: '血型', sort: true,align:'center',templet:function(obj){
	       	  var param = "";
	       	  if(obj.bloodType == 0){
	       		  param = "A";
	       	  }else if(obj.bloodType == 1){
	       		  param = "B";
	       	  }else if(obj.bloodType == 2){
	       		  param = "O";
	       	  }else if(obj.bloodType == 3){
	       		  param = "AB";
	       	  }
	       	  return param;
	         }}
	         ,{field: 'type', title: '类型', align:'center',templet:function(obj){
	       	  var param = "";
	    	  if(obj.type == 0){
	    		  param = "普通";
	    	  }else if(obj.type == 1){
	    		  param = "普免";
	    	  }else if(obj.type == 2){
	    		  param = "特免";
	    	  }
	    	  return param;
	      }} 
	         ,{field: 'collDate', title: '采浆时间', align:'center',templet:function(obj){
	       	  return obj.collDate==null?"":new Date(obj.collDate).Format("yyyy-MM-dd");
	         }}
	         ,{field: 'money', title: '发放金额', align:'center',templet:function(obj){
	        	 if(null!=obj.fmoney){
	        		 return obj.money+obj.fmoney+obj.roadFee;
	        	 }
	        	 return obj.money;
	         }}
	         ,{field: 'createDate', title: '发放日期', align:'center',templet:function(obj){
	       	  return new Date(obj.createDate).Format("yyyy-MM-dd");
	         }}
	         ,{field: 'updater', title: '发放人', align:'center'}
	    ]],
	    done:function(obj){
	    	console.log(obj);
	    }
	 });
	  function getProviderNo(){
		  return localStorage.getItem("providerNo");
	  }
	  function getUpdater(){
		  return localStorage.getItem("updater");
	  }
	  function getCreateDateCost(){
		  return localStorage.getItem("createDateCost");
	  }
	  function getIcNumber(){
		  return localStorage.getItem("icNumber");
	  }
	  function getIdNo(){
		  return localStorage.getItem("idNo");
	  }
	  function getCreateDateCol(){
		  return localStorage.getItem("createDateCol");
	  }
	  function getPlasmaType(){
		  return localStorage.getItem("plasmaType");
	  }
	  function doSearch(){
		  var providerNo = $.trim($("#providerNo").val());
		  var updater = $.trim($("#updater").val());
		  var createDateCost = $.trim($("#createDateCost").val());
		  var icNumber = $.trim($("#icNumber").val());
		  var idNo = $.trim($("#idNo").val());
		  var createDateCol = $.trim($("#createDateCol").val());
		  var plasmaType = $.trim($("#plasmaType").val());
		  layui.table.reload('newtestS',{where:{
		    	"providerNo":providerNo,
		    	"updater":updater,
		    	"createDateCost":createDateCost,
		    	"icNumber":icNumber,
		    	"idNo":idNo,
		    	"createDateCol":createDateCol,
		    	"plasmaType":plasmaType
		    }});
	  }
	  $("#sub").click(function(){
		  doSearch();
		  currentPageAllAppoint = 1;
	  });
	});
	//重置
	$("#reset").click(function(){
		$("input[type=text]").val("");
		$("select").val("");
	});
	
	//导出Excel
	$("#exportToExcel").click(function(){
		var providerNo = $.trim($("#providerNo").val());
		  var updater = $.trim($("#updater").val());
		  var createDateCost = $.trim($("#createDateCost").val());
		  var icNumber = $.trim($("#icNumber").val());
		  var idNo = $.trim($("#idNo").val());
		  var createDateCol = $.trim($("#createDateCol").val());
		  var plasmaType = $.trim($("#plasmaType").val());
		  window.location.href="/costGrant/exportCostGrant?providerNo="+providerNo+"&updater="+updater+"&createDateCost="+createDateCost+"&icNumber="+icNumber+"&idNo="+idNo+"&createDateCol="+createDateCol+"&plasmaType="+plasmaType;
		//window.location.href="/check/downCheckList?"+param;
	});
	//格式化时间框
	//initDate("#createDateCol");
	layui.use("laydate",function(){
		var laydate = layui.laydate;
		laydate.render({
			elem:"#createDateCost",
			format:"yyyy-MM-dd",
			value:""
		});
		laydate.render({
			elem:"#createDateCol",
			format:"yyyy-MM-dd",
			value:""
		});
	});
	
});
