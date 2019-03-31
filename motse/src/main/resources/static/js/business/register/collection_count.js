$(function(){
	// 加载免疫类型选项
	initCheckData21(null,'/immuneSetting/queryImmunTypeOne',null,0);
	//获取邀请人
	initCheckData21('#inviteId','/propagandist/queryPropagandistInfo',null,2);
	//获取组号
	initCheckData21('#groupId','/group/queryGroupInfo',null,3);
    /*列表数据显示借口*/ 
	layui.use(['laydate', 'laypage', 'table', ], function(){
	  var laydate = layui.laydate, //日期
	  laypage = layui.laypage, //分页 
	  table = layui.table, //表格 
	  element = layui.element; //元素操作  
	  //执行 table
	  table.render({
	    elem: '#newtest' ,
	    url: GetURLInfo() +'queryCollectionCountPeople', //数据接口
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
	      {field: 'name', title: '浆员姓名',width:"10%",align:'center'},
	      {field: 'sex', title: '性别',width:"6%", align:'center',templet: function (d){ // 单元格格式化函数
	          return getSexValue(d.sex);
	      }},
	      {field: 'immuneName', title: '类型',width:"8%",align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.immuneName==undefined){
	    		  text="普通";
	    	  }else{
	    		  text = d.immuneName;
	    	  }
	  	  		return text;
	  	   }},
	      {field: 'num', title: '组号',width:"10%",align:'center'},
	      {field: 'adist', title: '邀请人',width:"10%",align:'center'},
	      {field: 'count', title: '采浆次数',width:"10%",align:'center'},
	      {field: 'money', title: '合计发放费用',width:"12%",align:'center'},
	      {field: 'amount', title: '血浆量',width:"10%",align:'center'},
	      {field: 'maxDate', title: '最后采浆日期', width:"12%",align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.maxDate==undefined){
	    		  text="";
	    	  }else{
	    		  text = dateFtt("yyyy-MM-dd",new Date(d.maxDate));
	    	  }
	  	  		return text;
	  	   }},
	      {field: 'addressx', title: '地址',width:"30%",align:'center'},
	      {field: 'birthday', title: '出生日期',width:"10%",align:'center',templet: function (d){
	    	  var text = '-';
	    	  if(d.birthday==undefined){
	    		  text="";
	    	  }else{
	    		  text = dateFtt("yyyy-MM-dd",new Date(d.birthday));
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
	      {field: 'phone',title: '电话',width:"14%",align: 'center'},
	      {fixed: 'right', title: '操作', align: 'center',width:"10%", toolbar: '#barDemo'}
	    ]]
	  });
	  table.on('tool(demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		  var data = obj.data //获得当前行数据
		    ,layEvent = obj.event; //获得 lay-event 对应的值
		    if(layEvent === 'detail') {
		      layer.ready(function() {
		        layer.open({
		          type: 2,
		          title: '详情页面',
		          maxmin: false,
		          area: ['80%', '80%'],
		          content: GetURLInfo()+'/collectionCountDetails?providerNo='+data.providerNo,
		        });
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
		$("#search_par input").val("");
		$("#search_par select").val("");
	}); 
	})//////////////////
	//搜索
	function doSearch(){
		var providerNo = $("#providerNo").val();
		var name = $("#name").val();
		var immuneId = $("#immuneId").val();
		var inviteId = $("#inviteId").val();
		var groupId = $("#groupId").val();
		var addressx = $("#addressx").val();
		var phone = $("#phone").val();
		layui.table.reload('newtest', {page: {curr:1},where: {"providerNo": providerNo,"name": name,"immuneId": immuneId,"inviteId": inviteId,"groupId": groupId,"addressx": addressx,"phone": phone}});
	}
	function getToken() {
		return localStorage.getItem("token");
	}
	//刷新按钮
	function refresh(){
		location.reload();
	}
	
	function initCheckData21(selectId,url,datas,type){
		simpleAjax(url,datas,function(result){
			if(type==0){
				if(result.code==-1){
		        	   var data = result.data;
		        	   if(data.isDisable==1){
		        		// 加载免疫类型选项
		        			initCheckData21('#immuneId','/immuneSetting/queryAmmuneSetting',{type:1},1);
		        	   }else{
		        		   initCheckData21('#immuneId','/immuneSetting/queryImmuneSettingList',null,1);
		        	   }
		           }
			}
			if(type==1){
				// 加载免疫类型选项
				 for (var o in result.data){
		                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
		                $(selectId).append(str);
		            }
			}
			if(type==2){
				//获取邀请人
				for (var o in result.data){
	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
	                $(selectId).append(str);
	            }
			}
			if(type==3){
				//获取组号
				for(var o in result.data){
					var str = "<option value="+result.data[o].id+">"+result.data[o].num+"</option>";
					$(selectId).append(str);
				}
			}
		})
		
	}