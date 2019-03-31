
$(function() {
	$.validator.setDefaults({
        submitHandler: function() {
        	var id  = $("#id").val();
    		if(id== undefined || "" == id){
    			layer.alert("请先选择一个浆员");
    			return false;
    		}
            $("#updateForm").ajaxSubmit({
                type : 'POST',
                url : "/proteinContent/updateInfoById",
                success : function(result) {
                    checkCode(result,function(){
                            parent.layer.alert(result.message,function() {
                                parent.layer.closeAll();
                            	doSearch();
                            });
                    });
                },
                error : function() {
                    alert("新增失败");
                }
            });
        }
    });
	 $("#updateForm").validate({
	        //验证规则
	        rules: {
	        	value:{
	                required:true,
	                minlength:1,
	                maxlength:8
	            },
	            protein:{
	            	 required:true,
		                minlength:1,
		                maxlength:8
	            }
	        },
	        messages: {
	           
	        	value: {
	                required: "请输入折射仪刻度值",
	                minlength: "折射仪刻度值长度不允许少于1位",
	                maxlength: "折射仪刻度值长度不允许多于8位"
	            },
	            protein: {
	                required: "请输入蛋白值",
	                minlength: "蛋白值长度不允许少于1位",
	                maxlength: "蛋白值长度不允许多于8位"
	            }
	        }
	    });
	 
		var cols= [[ //表头
			/* {type: 'checkbox', fixed: 'left'}*/
				 {field: 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
				,{field: 'plasmaType', title: '浆员类型' ,align:'center',templet: function (d){ return d.plasmaType == 0?"非固定浆员":"固定浆员"}}
				,{field: 'sampleNo', title: '小样号',sort: true,align:'center',/* fixed: 'left'*/}
				,{field: 'allId', title: '全登记号',  sort: true,align:'center',/* fixed: 'left'*/}
				,{field: 'providerNo', title: '献浆员卡号',sort: true, align:'center'}
				,{field: 'name', title: '姓名', sort: true,align:'center'}
				,{field: 'sex', title: '性别',sort: true, align:'center',templet: function (d){ return getSexValue(d.sex);}} 
				,{field: 'updateDate', title: '登记日期',sort: true, align:'center', templet:function(d){ return $.myTime.UnixToDate(d.createDate);}}
				,{field: 'value', title: '折射仪刻度值',sort: true, align:'center'}
				,{field: 'protein', title: '蛋白值',sort: true, align:'center'}
				,{field: 'result', title: '检测结果',sort: true, align:'center' ,templet : function(d){return getCheckResultValue(d.result); }}
			    ]];
				var startTime = $("#startTime").val();
				dataAll("inhemogS",cols,{"startTime" : startTime, "token" : getToken()},GetURLInfo() + 'queryListByUpdateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
					 initTableOnClick();
				 });
	 
	 
})

	
function doSearch(){
	var startTime = $("#startTime").val();
	layui.table.reload('inhemogS', {where: {"startTime" : startTime, "token" : getToken()}});
	initTableOnClick();
}
 
/*//日期时间*/
initDate("#startTime");
//读取浆员卡号
function read(){
	var startTime=$("#startTime").val();//时间
	//请求身份证接口
	$.ajax({
		type : "POST",
		url :"/common/readProviderNoInfo",
		datatype : "json",
		success : function(result) {
			if (null!=result.data) {
				layui.table.reload('inhemogS', {where: {"providerNo": result.data.code,"startTime":startTime}});
				document.querySelector('tbody tr').style.background='#b6dcbe';
				$.post(GetURLInfo()+"queryProteinHeadInfo",{"providerNo":result.data.code,"startTime":startTime},function(res){
					if(res.code==-1 && null!=res.data){
						if(res.data.plasmaType ==1){
							initCheckData({"type":"fixedProtein"});
						}else{
							initCheckData({"type":"unfixedProtein"});
						}
						initData(res.data.id,res.data.registriesNo);
					}else{
						parent.layer.alert('无此浆员记录',function(){
			   				parent.layer.closeAll();
			   			});
					}
				});
			} else {
				parent.layer.alert('无此浆员信息',function(){
	   				parent.layer.closeAll();
	   			});
			}
		},
		error : function() {
			parent.layer.alert("进入错误，请重试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
}

function initTableOnClick(){
	// 表格 tr 单击事件
	var tab = $("#inhemogS").next().find('.layui-table tbody tr');
	
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var providerNo = $(tr).find("td").eq(0).find("div").html();
		var plasmaType = $(tr).find("td").eq(1).find("div").html();
		var registriesNo = $(tr).find("td").eq(2).find("div").html();
		if(undefined == providerNo) 
			return false;
		if(plasmaType ==1){
			initCheckData({"type":"fixedProtein"});
		}else{
			initCheckData({"type":"unfixedProtein"});
		}
			
		
		initData(providerNo,registriesNo);
	});
	
}
var min = 60;
var max = 85;
var idtep = 0;
function initData(id,registriesNo) {
	idtep = id;
	$.ajax({
		type : "POST",
		url : "/proteinContent/queryByID?id=" + id,
		datatype : "json",
		success : function(result) {
			data = result.data;
			$("#id").val(data.id);
			$("#name").val(data.name);
			$("#bloodType").val(getBloodValue(data.bloodType));
			$("#providerNo").val(data.providerNo);
			$("#sex").val(getSexValue(data.sex));
			$("#pbType").val(getPbTypeValue(data.pbType));
			$("#allId").val(data.allId);
			$("#sampleNo").val(data.sampleNo);
			$("#value").val(data.value);
			$("#protein").val(data.protein);
			$("#result").val(data.result);
			$("#bloodRedProteinValue").val(data.bloodRedProteinValue);
			$("#proteinValue").val(data.proteinValue);
			// 根据浆员类型得到合格范围值
			var type ="unfixedProtein";
			if(data.plasmaType == 1){
				type ="fixedProtein";
			}
			simpleAjax("/config/queryDictListByType", {"type":type}, function(result) {
				if("Min" == result.data.lable){
					min = result.data.value;
				}else if("Max" == result.data.lable){
					max = result.data.value;
				}
			});
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

function doSubmit(){
	var value = $("#value").val();
	var protein = $("#protein").val(); 
	if(value != "") {
		if(protein == ""){
			changeProtein();
		}
	}
	$("#result").val("2");
	var  id  = $("#id").val();
	if(id== undefined || "" == id){
		layer.alert("请先选择一个浆员");
		return false;
	}
	simpleAjax("/proteinContent/updateInfoById0", {"id":id,"result":2}, function(result) {
		layer.alert("操作成功");
		doSearch();
	});
}

function changeProtein(){
	var value = $("#value").val();
	var reg = /^\d+(?=\.{0,1}\d+$|$)/;
	if(!reg.test(value)) {
		layer.alert("刻度值为正数!");
		return false;
	}
	var  sss = value*10;
	$("#protein").val(value*10);
		if(max >=sss && sss>= min){
			$("#result").val('0');
		}else{
			$("#result").val('1');
		}
}

function initCheckData(datas) {
	$.ajax({
		type : "POST",
		data:datas,
		url : "/config/queryDictListByType",
		async : false,
		datatype : "json",
		success : function(result) {
			data = result.data;
			for(var i=0,j=data.length;i<j;i++){
				if(data[i].lable == 'Min'){
					min = data[i].value;
				}else if(data[i].lable == 'Max'){
					max = data[i].value;
				}
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}


$("#print").click(function(){
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '蛋白含量打印',
				maxmin : false,
				content : '/proteinContent/gotoPrint?chooseDate='+$("#startTime").val(),
				area : [ '100%', '100%' ],
			})
		});
});

$("#print2").click(function(){
	 layer.ready(function() {
			layer.open({
				type : 2,
				title : '蛋白含量打印',
				maxmin : false,
				content : '/proteinContent/gotoPrint?type=2&chooseDate='+$("#startTime").val(),
				area : [ '100%', '100%' ],
			})
		});
});