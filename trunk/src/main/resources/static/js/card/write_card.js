/*$(function(){
列表数据显示借口 
layui.use(['laydate', 'laypage', 'table'], function(){
  var laydate = layui.laydate //日期
  ,laypage = layui.laypage //分页 
  ,table = layui.table //表格 
  ,element = layui.element; //元素操作   
  table.render({
    elem: '#Advancedtest'  
    ,url: GetURLInfo()+'queryPunchCard' //数据接口
    ,page: true //开启分页
    ,where:{"providerNo":"","idNo":""},
    limits:[10, 15, 20],
    limit: 10, //每页默认显示的数量
    response: { // //定义后端 json 格式，详细参见官方文档
         statusName: 'code', //数据状态的字段名称，默认：code
         statusCode: -1, //成功的状态码，默认：0
         msgName: 'message', //状态信息的字段名称，默认：msg
         count: "count", //数据总数的字段名称，默认：count
         data: 'data' //数据列表的字段名称，默认：data
    },
    cols: [[ //表头
    	{field: 'createDate', title: '发证日期',  sort: true,width:120, align:'center'}
        ,{field: 'providerNo', title: '供血浆者卡号',width:130,align:'center'}
        ,{field: 'name', title: '姓名',  sort: true,width:100,align:'center'}
        ,{field: 'sex', title: '性别',width:70,align:'center'}
        ,{field: 'bloodType', title: '血型',width:70,align:'center'}
        ,{field: 'experience', title: '民族', width:70, align:'center'}
        ,{field: 'logins', title: '浆员状态', width:70,sort: true,align:'center'}
        ,{field: 'wealth', title: '出生日期',width:120,align:'center' }
        ,{field: 'suend', title: '身份证', width:180,align:'center'}
        ,{field: 'classify', title: '家庭住址', width:290,align:'center'}
        ,{field: 'score', title: '建卡日期', width:120,sort: true,align:'center'}
        ,{field: 'kadje', title: '发证人', width:100,align:'center'}
    ]]
  });
  $("#query").click(function(){
  	var providerNo = $("#providerNo").val();
  	var idNo = $("#idNo").val();
  	alert(idNo);
  	layui.table.reload('Advancedtest', {where: {"providerNo":providerNo,"idNo":idNo}});	  
	});
});
});*/

$(function(){
//列表数据显示借口 
	var cols= [[ //表头
	  {field: 'providerNo', title: '编号',width:200,align:'center'}
      ,{field: 'name', title: '姓名', width:200,align:'center'}
      ,{field: 'sex', title: '性别',width:100,align:'center',templet:function(obj){
    	  if(obj.sex==0){
    		  return '男';
    	  }else if(obj.sex==1){
    		  return '女';
    	  }
    	  return '';
      }}
      ,{field: 'bloodType', title: '血型',display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'nation', title: '民族', display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'idNo', title: '身份证号', width:230,align:'center'}
      ,{field: 'birthday', title: '出生日期',display : 'none',minWidth : '0',width : '0',type : "space",templet:function(obj){
    	  return new Date(obj.birthday).Format("yyyy-MM-dd");
      } }
      ,{field: 'addressx', title: '家庭住址', display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field: 'createDate', title: '建档日期', display : 'none',minWidth : '0',width : '0',type : "space",templet:function(obj){
    	  return new Date(obj.createDate).Format("yyyy-MM-dd");
      }}
      ,{field: 'imagez', title: '图片', display : 'none',minWidth : '0',width : '0',type : "space"}
      ,{field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
    ]]
	var providerNo = $("#providerNo").val();
	var idNo = $("#idNo").val();
	dataAll("Advancedtest",cols,{"providerNo":"***","idNo":""},"queryPunchCard",'','',function(){
		//获取表单里的行对象
		var tab = $("#Advancedtest").next().find('.layui-table tbody tr');
		var tr = $(event.target).closest("tr")[0];
		//给行对象添加点击事件
		tab.click(function(event) {
			var tr = $(event.target).closest("tr")[0];
			var proNo = $(tr).find("td").eq(0).find("div").html();
			//获取卡片上的相关配置信息(参数为配置表的type)
			$.ajax({
				url:"../upload/getCommissionMsg",
				type:"post",
				dataType:"json",
				data:{"providerNo":proNo},
				success:function(result){
					if(result.code == -1 && result.data!=null){
						//$("#id").val(result.data.id);
						var name = $(tr).find("td").eq(1).find("div").html();
						var sex = $(tr).find("td").eq(2).find("div").html();
						var bt = $(tr).find("td").eq(3).find("div").html();
						var nation = $(tr).find("td").eq(4).find("div").html();
						var idNo = $(tr).find("td").eq(5).find("div").html();
						var birth = $(tr).find("td").eq(6).find("div").html();
						var add = $(tr).find("td").eq(7).find("div").html();
						var createDate = $(tr).find("td").eq(8).find("div").html();
						//var img = $(tr).find("td").eq(9).find("div").html();
						
						$("#pro").val(proNo);
						
						//var company = result.data.company;
						var code = result.data.code; //浆站代码
						var issuer = result.data.licensor;//发证人
						var eleNoCard = result.data.elecLockNumber;//电子锁号
						//绑定到卡片板块上
						$.post("/common/queryWhereBaseInfoOrDetailObj",{"providerNo":proNo},function(res){
							$("#img").attr("src",res.data.imagespot);
						});
						//$("#comCard").html(company);
						$("#proNoCard").html(proNo);
						$("#nameCard").html(name);
						$("#sexCard").html(sex);
						var bloodType = "";
						if(bt == 0){
							bloodType="A";
						}else if(bt == 1){
							bloodType="B";
						}else if(bt == 2){
							bloodType="O";
						}else if(bt == 3){
							bloodType="AB";
						}
						$("#bloodTypeCard").html(bloodType);
						$("#nationCard").html(nation);
						$("#idNoCard").html(idNo);
						$("#birthCard").html(birth);
						$("#addCard").html(add);
						$("#createDateCard").html(createDate);
						$("#orgNoCard").html(code);
						$("#issuOrgCard").attr("src","/image/upload/certificationOrgan.jpg");
						//$("#issuOrgCard").html(issuOrg);
						$("#issuerCard").html(issuer);
						$("#eleNoCard").html(eleNoCard);
						$("#issuDateCard").html(new Date().Format("yyyy-MM-dd"));
						
						var r_id = $(tr).find("td").eq(10).find("div").html();
						$("#id").val(r_id);
					}else{
						layer.alert("请先对浆员进行审核");
					}
				}
			});
		});
	});
	
	//***************打印（一切正常后才可以打印）   ------获取从卫计委返回的信息
	$("#print").click(function(){
		var issuer = $("#issuerCard").html();
		if(issuer == undefined || issuer == "" || issuer==null){
			layer.alert("请先选择对应浆员");
		}else{
			var proNo=$("#pro").val();
			$.ajax({
				 url:"../common/printCard?providerNo="+proNo,
        		 type:"post",
        		 dataType:"json",
        		 success:function(result){
        			 if(result.code == -1){
        				 layer.alert("打印成功");
        			 }else{
        				 layer.alert("打印失败，请检查机器是否连接");
        			 }
        		 }
			});
		}
	});
	//***************打印（一切正常后才可以打印）
	
	//***************写卡（一切正常后才可以写卡）
	$("#write").click(function(){
		var issuer = $("#issuerCard").html();
		if(issuer == undefined || issuer == "" || issuer==null){
			layer.alert("请先选择对应浆员");
		}else{
			var proNo=$("#pro").val();
			//先读卡判断是否空卡
			$.ajax({
        		  url:"/common/readICNumber",
        		  type:"post",
        		  dataType:"json",
        		  success:function(result){
        			  //layer.alert("写卡成功");
        			  if(result.code == -3){
        				  layer.alert(result.message);
        			  }else if(result.code == -1){
        				  //if(result.data.code == "000000"){
        					 $.ajax({
        						 url:"/common/writeProviderNo?code="+proNo,
	        					 type:"post",
				        		 dataType:"json",
				        		 success:function(result){
				        			 if(result.code == -1){
				        				 //写卡成功后，更新浆员审核状态为已审核
				        				 var id = $("#id").val();
				        				 $.post('/upload/updateIsGrant',{"id":id},function(res){
				        					 if(res.code==-1){
				        						 layer.alert("写卡成功");
				        					 }else{
				        						 layer.alert("写卡失败");
				        					 }
				        				 });
				        				 
				        			 }else{
				        				 layer.alert("写卡失败，请检查机器设置");
				        			 }
				        		 }
        					 });
        				  //}
        			  }else{
        				  layer.alert(result.message);
        			  }
        		  }
        	  });
		}
	});
	//***************写卡（一切正常后才可以写卡）
	
	$("#query").click(function(){
	  	var providerNo = $("#providerNo").val();
	  	var idNo = $("#idNo").val();
	  	layui.table.reload('Advancedtest', {where: {"providerNo":providerNo,"idNo":idNo}});	  
	});
		
	
});