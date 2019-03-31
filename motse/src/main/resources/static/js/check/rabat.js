$(function(){
	var url = location.href;
	url = url.substring(url.indexOf("?")+1,url.length);
	var providerNo = url.split("=")[1];
	
	$("#startTime").val(dateFtt('yyyy-MM-dd',new Date()));
	layui.use('laydate', function(){
		  var laydate = layui.laydate; 
		  var laydateS = layui.laydate; 
		  //单控件
		  laydate.render({
		    elem: '#startTime',
		    value:new Date()
		    ,format: 'yyyy-MM-dd'
		    ,ready: function(date){
		      console.log(date);
		    }
		    ,done: function(value, date, endDate){
		      console.log(value, date, endDate);
		    }
		 })
	});  
	
	//获取胸片检查单位列表
	$.ajax({
		type : "POST",
		url : "/rabatUnit/queryRabatUnitList",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				if(data.data.length<1){
					$("#rabatunitId").empty();
					$("#rabatunitId").append('<option value="" selected="selected">请选择</option>');
				}else{
					$("#rabatunitId").empty();
					$.each(data.data,function(index,item){
						if(item.isName==1){
							$("#rabatunitId").append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
						}else{
							$("#rabatunitId").append('<option value="'+item.id+'" >'+item.name+'</option>');
						}
					});
				}
			}
		},
		error:function(){
			
		}
	});
	
	//获取体检医生列表
	$.ajax({
		type : "POST",
		url : "/propagandist/queryPropagandistList",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				if(data.data.length > 0){
					$("#userId").empty();
					$.each(data.data,function(index,item){
						$("#userId").append('<option value="'+item.id+'" selected="selected">'+item.name+'</option>');
					});
				}
			}
		},
		error:function(){
			
		}
	});
	
	if(providerNo.length>0){
		$("#search").hide();
		$("#boxId").val(providerNo);
		loadInfo(providerNo);
	}
	
	//确定提交胸片结果
	$("#confirm").click(function(){
		var rabatunitId=$("#rabatunitId").val();
    	var userId=$("#userId").val();
    	if(rabatunitId.length<1){
    		layer.alert('请选择检查单位');
    		return false;
    	}
    	if(userId.length<1){
    		layer.alert('请选择检查医生');
    		return false;
    	}
		$("#rabatForm").ajaxSubmit({
	        type : 'POST',
	        url : GetURLInfo() +"insertRabatinfo",
	        success : function(result) {
	            layer.alert(result.message,function(){
	            	layer.closeAll();
	            	if(result.code==-1) { //url跳转网页中传回的值。
		                //window.location.href=window.location.href;
	            		var providerNo=$("#boxId").val();
	            		layui.table.reload('rabat', {where: {"providerNo": providerNo}});
	            		parent.location.reload();
		            }
	            });
	        },
	        error : function() {
	        	layer.alert("上传失败，请检查网络后重试,上传文件太大");
	        }
	    });
		return false;
	});
	
	//刷新
	$("#refresh").click(function(){
		$("#startTime").val(dateFtt('yyyy-MM-dd',new Date()));
		$("#remarks").val("");
		$("#demo1").attr("src","../../../img/new_pa1.png");
	});
 
	$("#chaxun").click(function(){
		var providerNo=$("#boxId").val();
		if(providerNo.length<1){
			layer.alert("请输入浆员卡号");
		}else{
			loadInfo(providerNo);
		}
	});
})
/**
 * 加载浆员信息
 * @param providerNo
 * @returns
 */
function loadInfo(providerNo){
	//获取浆员信息
	$.ajax({
		type : "POST",
		url : "/common/queryWhereBaseInfoOrDetailObj",
		datatype : "json",
		data:{"providerNo":providerNo},
		success : function(data) {
			if(data.code==-1){
				$("#basic input").each(function(){
					var item=$(this);
					var names=item.attr("name");
					if(names!='startTime'){
						item.val(data.data[names]);
					}
				});
				$("#age").val(getAge(data.data["idNo"]));
				$("#remarks").val("");
			}
		},
		error:function(){
			
		}
	});
	
	//已体检人员列表
	layui.use([ 'laypage', 'table' ], function() {
		var laydate = layui.laydate // 日期
		, laypage = layui.laypage // 分页
		, table = layui.table // 表格
		, element = layui.element; // 元素操作
		// 执行 table
		table.render({
			elem : '#rabat',
			url : GetURLInfo() + 'queryRabatinfoList', // 数据接口
			method : 'post',
			limits : [ 5, 10, 15, 20 ],
			limit : 5, // 每页默认显示的数量
			page : true, // 开启分页
			where : { // 查询条件
				providerNo : providerNo, // 
			},
			response : { // //定义后端 json 格式，详细参见官方文档
				statusName : 'code', // 数据状态的字段名称，默认：code
				statusCode : -1, // 成功的状态码，默认：0
				msgName : 'message', // 状态信息的字段名称，默认：msg
				count : "count", // 数据总数的字段名称，默认：count
				data : 'data' // 数据列表的字段名称，默认：data
			},
			cols : [ [ // 表头
			{
				field : 'id',
				title : 'id',
				display : 'none',
				minWidth : '0',
				width : '0',
				type : "space"
			}, {
				field : 'providerNo',
				title : '献浆员卡号',
				sort : true, 
				align : 'center'
			}, {
				field : 'name',
				title : '姓名',
				align : 'center'
			}, {
				field : 'sex',
				title : '性别',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					var text = '-';
					if (d.sex == 0) {
						text = "男";
					} else {
						text = "女";
					}
					return text;
				}
			}, {
				field : 'bloodType',
				title : '血型',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					var text = '未知';
					if (d.bloodType == 0) {
						text = "A";
					} else if (d.isDisable == 1) {
						text = "B";
					} else if (d.isDisable == 2) {
						text = "O";
					} else if(d.bloodType == 3){
						text = "AB";
					}
					return text;
				}
			}, {
				field : 'fname',
				title : '检查单位',
				align : 'center'
			}, {
				field : 'result',
				title : '检查结果',
				align : 'center',
				templet : function(d) { // 单元格格式化函数
					var text = '-';
					if (d.result == 0) {
						text = "合格";
					} else if(d.result == 1){
						text = "不合格";
					}
					return text;
				}
			}, {
				field : 'createDate',
				title : '检查时间',
				align : 'center'
			}
			
			] ],
			done : function(res, curr, count) {
				// 如果是异步请求数据方式，res即为你接口返回的信息。
				// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				// 表格 tr 单击事件
				var tab = $("#rabat").next('.layui-table-view').find(
						'table.layui-table');
				tab.click(function(event) {
					var tr = $(event.target).closest("tr")[0];
					var id = $(tr).find("td").eq(0).find("div").html();
					$.post(GetURLInfo()+"queryRabatinfoById",{"id":id},function(res){
						$("#startTime").val(res.data.createDate);
						$("#rabatunitId").val(res.data.rabatunitId);
						$("#result").val(res.data.result);
						$("#remarks").val(res.data.remarks);
						$("#demo1").attr("src",res.data.rabatImg);
					});
				});
			}
			
		});
	});
}

function addFile() {
    var preview = document.querySelector('img');
    var file  = document.querySelector('input[type=file]').files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        preview.src = reader.result;
    }
    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}