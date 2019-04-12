var currentURLInfo = null;
$(function () {
	$("#refreshPage").click(function(){
        window.location.reload();
	})
    $("#cannel").bind('click', function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);//关闭弹出的子页面窗口
    });
	
	/* 导航栏图标旋转 */
	    $(".panel-heading").click(function (e) {
	        /*切换折叠指示图标*/
	        $(this).find("span").toggleClass("glyphicon-chevron-right");
	        $(this).find("span").toggleClass("glyphicon-chevron-down");
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
	//	var pathname = window.location.pathname;
	//	pathname = pathname.substring(1);
	//	var webName = "";
	//	for (var i = 0; i < pathname.length; i++) {
	//		if (pathname.charAt(i) == '/') {
	//			break;
	//		}
	//		webName += pathname.charAt(i);
	//	}
		currentURLInfo = protocol + "//" + host + "/";// + webName + "/";
	}
	return currentURLInfo;
}

/* 获取时间 */
function getDateDay(d){
	window.onload = function () {
        var iframe = document.getElementById("iframe");
        iframe.style.height = window.innerHeight + 'px';
        var row = document.getElementById("row-height");
        row.style.height = window.innerHeight + 'px';
        setInterval(function () {
            var date = new Date();
            var year = date.getFullYear(); //获取当前年份   
            var mon = date.getMonth() + 1; //获取当前月份   
            var da = date.getDate(); //获取当前日   
            var day = date.getDay(); //获取当前星期几   
            var h = date.getHours(); //获取小时   
            var m = date.getMinutes(); //获取分钟 
            var d = document.getElementById('Date');
            d.innerHTML = h + ':' + m + ' ' + year + '-' + mon + '-' + da + ' ' + '星期' + day;
        }, 1000)
    };
}








var isBase64 = /^\s*data:([a-z]+\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,([a-z0-9!$&',()*+;=\-._~:@\/?%\s]*?)\s*$/i;
function validDataUrl(s) {
    return isBase64.test(s);
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
