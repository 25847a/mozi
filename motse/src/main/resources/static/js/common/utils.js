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
/**
 * 判断图片是否存在
 */
function imageGudge(imageUrl){
	var result= true;
	 $.ajax({
	        url: ""+imageUrl,
	    //    async:false,
	        type: 'GET',
	        success: function(response) {
	        	console.log("图片地址获取正常:"+imageUrl);
	        	result=true;
	        },
	        error:function () {
	        	console.log("获取图片地址404:默认使用项目自带图片");
	        	result=false;
	        }
	    });
	 return result;
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

/**
 * 提示框
 * @param message
 * @returns
 */
function tips(message){
	jqueryAlert({
		'content' : message,
		'closeTime' : 2000
	})
};
/**
 * 提示框提示用语
 * @param message
 * @returns

function tipsMessage(data,message){
	if(data==""){
		tips(message);
		return;
	}
}; */
/**
 * 限制未来时间的选择
 */
function futureDate(dateId){
    var nowTemp = new Date();
    var nowDay = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0).valueOf();
    var nowMoth = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), 1, 0, 0, 0, 0).valueOf();
    var nowYear = new Date(nowTemp.getFullYear(), 0, 1, 0, 0, 0, 0).valueOf();
    var $myStart2 = $('#'+dateId);

    var checkin = $myStart2.datepicker({
        onRender: function (date, viewMode) {
            // 默认 days 视图，与当前日期比较
            var viewDate = nowDay;

            switch (viewMode) {
                // moths 视图，与当前月份比较
                case 1:
                    viewDate = nowMoth;
                    break;
                // years 视图，与当前年份比较
                case 2:
                    viewDate = nowYear;
                    break;
            }
            return date.valueOf() > viewDate ? 'am-disabled' : '';
        }
    })
};
/**
 * 手机号码正则表达
 * @param $poneInput
 * @returns
 */
function isPoneAvailable(poneInput) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(poneInput)) {
        return false;
    } else {
        return true;
    }
}


/**
 *  去尾法 进一法 四舍五入法
 */
/**
 * 格式化数字
 * @param num 要格式化的数字
 * @param len 保留小数位数默认2
 * @param type 格式化方式0:四舍五入 1:进一  2:舍去 默认0
 * @return string
 */
function format_number(num,len,type) {
    len = len > 0 && len <= 20 ? len : 2;
    var result = parseFloat(num);
    num=isNaN(result)?0:result;
    var numpow=Math.pow(10,len);
    var numcheng=accMul(num,numpow);
    if(type==1){//ceil进一 
    	result = Math.ceil(numcheng);
    }else if(type==2){//floor舍去
    	result = Math.floor(numcheng);
    }else{//round四舍五入 
    	result = Math.round(numcheng);
    }
    result =accDiv(result,numpow);
    var s_x = result.toString();
    var pos_decimal = s_x.indexOf('.');
    if (pos_decimal < 0) {pos_decimal = s_x.length;s_x += '.';}
    while (s_x.length <= pos_decimal + len) {s_x += '0';}
    return s_x;
}
//乘法函数
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {m += s1.split(".")[1].length;}catch (e) {}
    try {m += s2.split(".")[1].length;}catch (e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
//除法函数
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {t1 = arg1.toString().split(".")[1].length;}catch (e) {}
    try {t2 = arg2.toString().split(".")[1].length;}catch (e) {}
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
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
