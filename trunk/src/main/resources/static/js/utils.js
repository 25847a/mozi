var currentURLInfo = null;
$(function () {
	$("#refreshPage").click(function(){
        window.location.reload();
	})
    $("#cannel").bind('click', function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);//关闭弹出的子页面窗口
    });
})

function no(value){
    parent.layer.alert(value);
}
// 得到当前URL
function GetURLInfo() {
	if (currentURLInfo == null) {
		var protocol = window.location.protocol;
		var host = window.location.host;
		var pathname = window.location.pathname;
		pathname = pathname.substring(1);
		var webName = "";
		for (var i = 0; i < pathname.length; i++) {
			if (pathname.charAt(i) == '/') {
				break;
			}
			webName += pathname.charAt(i);
		}
		currentURLInfo = protocol + "//" + host + "/" + webName + "/";
	}
	return currentURLInfo;
}
/**
 * 新增
 * 
 * @param url
 *            请求地址
 * @param flag
 *            是否刷新
 */
function add(url, flag) {
	addWithArea(url, flag, [ '500px', '90%' ]);
}

function addWithArea(url, flag, area) {
	layer.ready(function() {
		layer.open({
			type : 2,
			title : '新增',
			maxmin : true,
			area : area,
			content : url,
			end : function() {
				if (flag) {
					location.reload();
				}
			}
		})
	});
}

/**
 * json封装
 * @param elem 列表显示id
 * @param url 请求路径
 * @param uurl 查询详情url
 * @param durl 删除url
 * @param func 列表加载完成后调用函数
 */
var laydate,laypage,table,element;
function dataOnlyUrl(elem,colos,url){
	dataAll(elem,colos,{"token" : getToken()},url,'','',function(){});
}
function data(elem,colos,url,uurl,durl,func){
	dataAll(elem,colos,{"token" : getToken()},url,uurl,durl,func);
}
//  增加参数是否弹层
function dataAllIsOpen(elem,colos,where,url,uurl,durl,isOpen,func){
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
        pageListData(elem,where,url,colos,func);
        table.on('tool(page)', function (obj) {
            var data = obj.data, //获得当前行数据
                layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent == 'detail') {
            	if(isOpen){
            		updateData(uurl,data);
            	}
            	else{
            		var index = parent.layer.confirm('确实要执行该操作?', {
            			  btn: ['确定','取消'] //按钮
            			}, function(){
            				ajaxUpdateData(uurl,data);
            			}, function(){
            				
            			});
            	}
            } else if (layEvent == 'del') {
            	if(isOpen){
            		deleteData(durl,data);
            	}else{
            		var index = parent.layer.confirm('确实要执行该操作?', {
            			  btn: ['确定','取消'] //按钮
            			}, function(){
            				ajaxUpdateData(durl,data);
            			}, function(){
            				
            			});
            	}
            }
        });
    });
}
//增加参数是否弹层(才健)
function dataAllIsOpenLimit(elem,colos,where,url,uurl,durl,isOpen,func,limit){
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
        pageListDataLimit(elem,where,url,colos,func,limit);
        table.on('tool(page)', function (obj) {
            var data = obj.data, //获得当前行数据
                layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent == 'detail') {
            	if(isOpen){
            		updateData(uurl,data);
            	}
            	else{
            		var index = parent.layer.confirm('确实要执行该操作?', {
            			  btn: ['确定','取消'] //按钮
            			}, function(){
            				ajaxUpdateData(uurl,data);
            			}, function(){
            				
            			});
            	}
            } else if (layEvent == 'del') {
            	if(isOpen){
            		deleteData(durl,data);
            	}else{
            		var index = parent.layer.confirm('确实要执行该操作?', {
            			  btn: ['确定','取消'] //按钮
            			}, function(){
            				ajaxUpdateData(durl,data);
            			}, function(){
            				
            			});
            	}
            }
        });
    });
}
// 最简单的列表函数,不触发操作弹层
function dataAll(elem,colos,where,url,uurl,durl,func){
	dataAllIsOpen(elem,colos,where,url,uurl,durl,true,func);
}
//列表函数,触发操作弹层,支持弹层宽高度调整
function dataAllAndWH(elem,colos,where,url,uurl,durl,area,func){
	dataAllAndWHWithPage(elem,colos,where,url,uurl,durl,area,true,func);
}
//列表函数,触发操作弹层,支持弹层宽高度调整,支持是否开启分页
function dataAllAndWHWithPage(elem,colos,where,url,uurl,durl,area,havPage,func){
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
        if(havPage){
        	pageListData(elem,where,url,colos,func);
        }else{
        	listData(elem,where,url,colos,havPage,func);
        }
        table.on('tool(page)', function (obj) {
            var data = obj.data, //获得当前行数据
                layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent == 'detail') {
                updateDataWhitArea(uurl,data,area);
            } else if (layEvent == 'del') {
                deleteData(durl,data);
            }
        });
    });
}
//没有分页的列表
function listDataLimit(elem,where,url,cols,havPage,func,limit){
	 table.render({
	        elem: '#'+elem,
	        method: "POST",
	        where:where,
	        url: url, //数据接口
	        page: havPage, //开启分页
	        limits: [5, 10, 15, 20],
	        limit: havPage?limit:100000000, //每页默认显示的数量
	        response: { // //定义后端 json 格式，详细参见官方文档
	            statusName: 'code', //数据状态的字段名称，默认：code
	            statusCode: -1, //成功的状态码，默认：0
	            msgName: 'message', //状态信息的字段名称，默认：msg
	            count: "count", //数据总数的字段名称，默认：count
	            data: 'data' //数据列表的字段名称，默认：data
	        },
	        cols: cols,
	        done : function(res, curr, count) {
	        	if(typeof(func)!=='undefined'){
	        		func();
	        	}
	        }
	    });
}
// 初始显示5条记录的分页
function listData(elem,where,url,cols,havPage,func){
	listDataLimit(elem,where,url,cols,havPage,func,5);
}
function pageListData(elem,where,url,cols,func){
	listData(elem,where,url,cols,true,func);
}
//裁剪
function pageListDataLimit(elem,where,url,cols,func,limit){
	listDataLimit(elem,where,url,cols,true,func,limit);
}
function updateData(url, data) {
	updateDataWhitArea(url, data,[ '500px', '335px' ]);
}
function ajaxUpdateData(url, data) {
	$.ajax({
		type : "post",
		url : url,
		data : {
			"id" : data.id
		},
		dataType : "json",
		success : function(result) {
			checkCode(result, function() {
				var index = parent.layer.alert(result.message, function() {
					parent.layer.close(index);
					location.reload();
				});
			});
		},
		error : function() {
			var index = parent.layer.alert("失败", function() {
				parent.layer.close(index);
			});
		}
	})
}

function updateDataWhitArea(url, data, area) {
	layer.ready(function() {
		layer.open({
			type : 2,
			title : "修改",
			maxmin : true,
			area : area,
			content : url + '?id=' + data.id,
			end : function() {
				location.reload();
			}
		})
	});
}


var isBase64 = /^\s*data:([a-z]+\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,([a-z0-9!$&',()*+;=\-._~:@\/?%\s]*?)\s*$/i;
function validDataUrl(s) {
    return isBase64.test(s);
}

function deleteData(url, data) {
	layer.confirm('是否删除该行?', function(index) {
		$.ajax({
			type : "post",
			url : url,
			data : {
				"ids" : data.id
			},
			dataType : "json",
			success : function(result) {
				checkCode(result, function() {
					parent.layer.alert(result.message, function() {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.closeAll();
						location.reload();
					});
				});
			},
			error : function() {
				alert("操作失败!");
			}
		})
	});
}
function checkCode(result, func) {
	if (result.code == -3) {
		window.location.href = "common/jurhundred";
		return;
	} else if(result.code == 1){
		parent.layer.alert(result.message,function() {
			func();
			parent.layer.closeAll();
		});
		
	}else{
		func();
	}
}

(function($) {
	$
			.extend({
				myTime : {
					/**
					 * 时间戳转换月份.日期
					 */
					UnixToMonthAndDate : function(unixTime, isFull, timeZone) {
						if (typeof (timeZone) == 'number') {
							unixTime = parseInt(unixTime) + parseInt(timeZone)
									* 60 * 60;
						}
						var time = new Date(unixTime);
						var ymdhis = "";
						ymdhis += (time.getUTCMonth() + 1) + ".";
						ymdhis += time.getUTCDate();
						
						return ymdhis;
					},
					/**
					 * 当前时间戳
					 * 
					 * @return <int> unix时间戳(秒)
					 */
					CurTime : function() {
						return Date.parse(new Date()) / 1000;
					},
					/**
					 * 日期 转换为 Unix时间戳
					 * 
					 * @param <string>
					 *            2014-01-01 20:20:20 日期格式
					 * @return <int> unix时间戳(秒)
					 */
					DateToUnix : function(string) {
						var f = string.split(' ', 2);
						var d = (f[0] ? f[0] : '').split('-', 3);
						var t = (f[1] ? f[1] : '').split(':', 3);
						return (new Date(parseInt(d[0], 10) || null, (parseInt(
								d[1], 10) || 1) - 1,
								parseInt(d[2], 10) || null, parseInt(t[0], 10)
										|| null, parseInt(t[1], 10) || null,
								parseInt(t[2], 10) || null)).getTime() / 1000;
					},
					/**
					 * 时间戳转换日期
					 * 
					 * @param <int>
					 *            unixTime 待时间戳(秒)
					 * @param <bool>
					 *            isFull 返回完整时间(Y-m-d 或者 Y-m-d H:i:s)
					 * @param <int>
					 *            timeZone 时区
					 */
					UnixToDate : function(unixTime, isFull, timeZone) {
						if (typeof (timeZone) == 'number') {
							unixTime = parseInt(unixTime) + parseInt(timeZone)
									* 60 * 60;
						}
						var time = new Date(unixTime);
						var ymdhis = "";
						ymdhis += time.getUTCFullYear() + "-";
						ymdhis += (time.getUTCMonth() + 1) + "-";
						ymdhis += time.getUTCDate();
						if (isFull === true) {
							ymdhis += " " + time.getUTCHours() + ":";
							ymdhis += time.getUTCMinutes() + ":";
							ymdhis += time.getUTCSeconds();
						}
						return ymdhis;
					}
					
				}
			});
})(jQuery);
//时间相加减少转换(啊健)
function daysJian(format,time){
	 var date =new Date(Date.parse(time.replace(/-/g,"/")));
	   date.setDate(date.getDate()-1);
	   return dateFtt(format,new Date(date.getTime()));
}
// 获取token
function getToken() {
	return localStorage.getItem("token");
}
// 页面重新刷新
function reload(){
	window.location.reload();
}
//获取血型对应的值
function getBloodValue(bloodType){
	if (bloodType == 0) {
		return "A";
	} else if (bloodType == 1) {
		return "B";
	} else if (bloodType == 2) {
		return "O";
	} else if (bloodType == 3) {
		return "AB";
	} else {
		return "未知";
	}
}
//获取血型对应的值
function getAltValue(va){
	if (va == 0) {
		return "<=25";
	} else if (va == 1) {
		return ">25";
	} else if (va == 2) {
		return ">=57";
	} else if (va == 3) {
		return ">=97";
	} else {
		return "未检";
	}
}
// 获取性别对应的值
function getSexValue(sex){
	if (sex == 0) {
		return "男";
	} else if (sex == 1) {
		return "女";
	}else{
		return "未知";
	}
}

//获取性别对应的值
function getTypeValue(type){
	if (type == 0) {
		return "人脸识别";
	} else if (type == 2) {
		return "人工通过";
	}else if(type==3){
		return "下乡登记";
	}else{
		return "未知";
	}
}

//获取逻辑对应的值
function getBooleanValue(va){
	if (va == 0) {
		return "否";
	}else if (va == 1) {
		return "是";
	}else{
		return "否";
	}
}

//获取逻辑对应的值
function getEnableValue(va){
	
	if (va == 0) {
		return "启用";
	}else{
		return "禁用";
	}
}

//获取拒绝信息的值
function getOpinionValue(va){
	if (va == 0) {
		return "暂时拒绝";
	}else if (va == 0){
		return "永久淘汰";
	}else{
		return  "";
	}
}

//获取阴阳值
function getCheckedValue(apply){
	if (apply == 0) {
		return "阴性";
	} else if (apply == 1) {
		return "阳性";
	}else if (apply == 2){
		return "弱阳性";
	} else{
		return "未检";
	}
}
//获取付款方式对应值
function getPaymentValue(paymentType){
	if (paymentType == 0) {
		return "转账";
	} else{
		return "未知";
	}
}
//获取订单状态对应值
function getQuarantineValue(status){
	if (status == 0) {
		return "待审批";
	} else if (status == 1) {
		return "待检验";
	} else if (status == 2) {
		return "已完成";
	} else if (status == 3) {
		return "订购删除";
	} else if(status == 4){
		return "已入库";
	}else{
		return "未知";
	}
}
//获取耗材用于的模块
function getTemplateValue(apply){
	if (apply == 0) {
		return "采浆";
	} else if (apply == 1) {
		return "化验";
	} else if (apply == 2) {
		return "免疫";
	}
}
//获取耗材分类(耗材、质控品)
function getCategory(category){
	if (category == 0) {
		return "耗材";
	} else {
		return "质控品";
	}
}
//获取采浆机号对应值
function getMachineNoValue(status){
	if (status == 0) {
		return "正常";
	} else if (status == 1) {
		return "损坏";
	} else if (status == 2) {
		return "报废";
	} else{
		return "未知";
	}
}
//获取机号类型对应值
function getMachineNoType(type){
	if (type == 0) {
		return "未使用";
	} else if (type == 1) {
		return "已使用";
	}else{
		return "未知";
	}
}

// 获取浆员免疫类型
function getPbTypeValue(pbType){
	var text = '-';
	if (pbType == 0) {
		text= "普通";
	} else if (pbType == 1) {
		text="普免";
	} else if (pbType == 2) {
		text="特免";
	}
	return text;
}
//获取浆员免疫类型
function getInjection(type){
	var text = '-';
	if(type==0){
		text='普免针次';
  	}else if(type==1){
  		text='特免针次';
  	}
	return text;
}
//获取浆员免疫类型
function getCheckResultValue(result){
	if (result == 0) {
		return "合格";
	} else if (result == 1) {
		return "不合格";
	} else {
		return "未检";
	}
}

//获取质控结果类型
function getQCValue(result){
	if (result == 0) {
		return "在控";
	} else if (result == 1) {
		return "告警";
	} else {
		return "失控";
	}
}
//锁住按钮,防止重复提交
function submission(id,text){
	$("#"+id).attr('disabled', 'disabled').text(text);
}
//解锁按钮,恢复正常提交
function unlock(id,text){
	$("#"+id).removeAttr('disabled').text(text);
}
/**
 * 时间格式化
 * @param fmt
 * @param date
 * @returns
 */
function dateFtt(fmt,date)   
{ // author: meizz
  var o = {   
    "M+" : date.getMonth()+1,                 // 月份
    "d+" : date.getDate(),                    // 日
    "h+" : date.getHours(),                   // 小时
    "m+" : date.getMinutes(),                 // 分
    "s+" : date.getSeconds(),                 // 秒
    "q+" : Math.floor((date.getMonth()+3)/3), // 季度
    "S"  : date.getMilliseconds()             // 毫秒
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} 

/*根据出生日期算出年龄*/  
function getAge(idNo){     
	var len = (idNo + "").length;
    if (len == 0) {
        return 0;
    } else {
        if ((len != 15) && (len != 18))//身份证号码只能为15位或18位其它不合法
        {
            return 0;
        }
    }
    
    var returnAge;  
    var birthYear = 0;  
    var birthMonth = 0;  
    var birthDay = 0;  
    if (len == 18)//处理18位的身份证号码从号码中得到生日和性别代码
    {
        birthYear = idNo.substr(6, 4);
        birthMonth = idNo.substr(10, 2);
        birthDay = idNo.substr(12, 2);
    }
    if (len == 15) {
        birthYear = idNo.substr(6, 2);
        birthMonth = idNo.substr(8, 2);
        birthDay = idNo.substr(10, 2);
    }
    d = new Date();  
    var nowYear = d.getFullYear();  
    var nowMonth = d.getMonth() + 1;  
    var nowDay = d.getDate();  
      
    if(nowYear == birthYear){  
        returnAge = 0;//同年 则为0岁  
    }  
    else{  
        var ageDiff = nowYear - birthYear ; //年之差  
        if(ageDiff > 0){  
            if(nowMonth == birthMonth) {  
                var dayDiff = nowDay - birthDay;//日之差  
                if(dayDiff < 0)  
                {  
                    returnAge = ageDiff - 1;  
                }  
                else  
                {  
                    returnAge = ageDiff ;  
                }  
            }  
            else  
            {  
                var monthDiff = nowMonth - birthMonth;//月之差  
                if(monthDiff < 0)  
                {  
                    returnAge = ageDiff - 1;  
                }  
                else  
                {  
                    returnAge = ageDiff ;  
                }  
            }  
        }  
        else  
        {  
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天  
        }  
    }  
    return returnAge;//返回周岁年龄  
} 

/**
 * 首页使用(啊健)
 */
function initDateGraphical(dateId,date,type){
	$(dateId).val(dateFtt(date,new Date()));
	layui.use('laydate', function(){
		  var laydate = layui.laydate; 
		  //年选择器
		  laydate.render({
		    elem: dateId
		    ,type: type
		  });
	}); 
}
/**
 * 初始化时间控件
 */
function initDate(dateId){
	initDateDone(dateId,null);
}


function initDateDone(dateId, donefun){
	$(dateId).val(dateFtt('yyyy-MM-dd',new Date()));
	layui.use('laydate', function(){
		  var laydate = layui.laydate; 
		  var laydateS = layui.laydate; 
		  //单控件
		  laydate.render({
		    elem: dateId
		    ,format: 'yyyy-MM-dd'
		    ,ready: function(date){
		      console.log(date);
		    }
		    ,done: function(value, date, endDate){
		   
		      console.log(value, date, endDate);
		    },change:function(value, date, endDate){
		    	 if(typeof(donefun)=="function")
				    	donefun();
		    }
		 })
	}); 
}
/**
 * 初始化时间控件
 */
function initDates(){
    initDate("#startTime")
}
//临时加载图片
function addFile(id,fileIndex,showIndex) {
    var preview = document.querySelector("#"+id);
    var file  = document.querySelector("#"+fileIndex).files[0];
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
function dayControl(id){
	layui.use('laydate', function(){
		var laydate = layui.laydate;
		var laydateS = layui.laydate;
		//单控件
		laydate.render({
			elem: '#'+id
			,format: 'yyyy-MM-dd'

			,ready: function(date){
				console.log(date);
			}
			,done: function(value, date, endDate){
				console.log(value, date, endDate);
			}
		});
	});
}

/**
 * 
 * @param providerNo
 * @returns
 */
function loadIdCardImage(providerNo){
	if(providerNo.length>0){
		// 获取淘汰原因列表
		$.ajax({
			type : "POST",
			url : "/common/queryWhereBaseInfoOrDetailObj",
			data:{"providerNo":providerNo},
			datatype : "json",
			success : function(data) {
				if(data.code==-1){
					layer.open({ 
		        		  type: 1,
		        		  title: "查看图片",
		        		  area : [ '350px', '400px' ],
		        		  content: '<img src="'+data.data.imagez+'" style="width: 350px;height: 300px;"/><img src="'+data.data.imagef+'" style="width: 350px;height: 300px;"/>'
		      		  })
				}
			},
			error:function(){
				
			}
		});
	}
}