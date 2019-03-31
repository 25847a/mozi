/*tab导航点击*/
$(function(){
  $("#objectiveTab").on("click","li>a",function(){
    $(this).parent().addClass("active").siblings("li").removeClass("active");
  });
});
/*重置按钮*/
function resetInfo(){
  $("input").val("");
  $("select").val("");
}
// url ,参数 , 回调函数  同步方式
function simpleAjax(url, datas, func){
	ajaxPost(false, url, 'json', datas, func);
}

// 跳转编辑报表页眉页脚页面  url带权限限制, reportNo 为对应的报表序号
function gotoEditReportInfo(url,reportNo){
	parent.layer.ready(function() {
		parent.layer.open({
			type : 2,
			title : '报表表头配置',
			maxmin : false,
			content : url,
			area : [ '100%', '220px' ],
			end : function() {
				getReportInfo(reportNo);
			}
		})
	});
}

// 根据报表序号查询该页面上的页眉页脚信息
function getReportInfo(reportNo){
	var url = "/reportInfo/getInfoByReportNO";
	var datas = {"reportNO":reportNo};
	simpleAjax(url, datas, function(result) {
		var info = result.data;
		$("#headLeft").html(info.headLeft);
		$("#headCenter").html(info.headCenter);
		$("#headRight").html(info.headRight);
		$("#footLeft").html(info.footLeft);
		$("#footCenter").html(info.footCenter);
		$("#footRight").html(info.footRight);
	});
}

function ajaxPost(sync, url, datatype, datas, func){
	$.ajax({  
		async: sync,  
        type: 'POST',  
        url: url,  
        dataType: datatype,  
        data: datas,  
        beforSend: function () {  
        	
        },  
        error: function (data) {  
            //请求失败时被调用的函数   
         //   alert("传输失败:" + data);  
        },  
        success: function (data) {  
        	if(data.code != '-1'){
        		try{
        			var index = layer.alert(data.message, function(){
        				layer.close(index);
            		});
        			
        		}catch(e){
        			var index = parent.layer.alert(data.message, function(){
        				parent.layer.close(index);
            		});
        		}
        		
        		
        		if(undefined == data.canContinue || null == data.canContinue)
        		return false;
        	}
            func(data);  
        }  
    });  
}

function ajaxGet(sync, url, datatype, datas, func){
	$.ajax({  
        sync: sync,  
        type: 'GET',  
        url: url,  
        dataType: datatype,  
        data: datas,  
        beforSend: function () {  
        	
        },  
        error: function (data) {  
            //请求失败时被调用的函数   
            alert("传输失败:" + data);  
        },  
        success: function (data) {  
            func(data);  
        }  
    });  
}


function gotoEditReportInfo(url, reportNo){
	 parent.layer.ready(function() {
			parent.layer.open({
				type : 2,
				title : '报表表头配置',
				maxmin : false,
				content : url,
				area : [ '100%', '220px' ],
				end : function() {
					getReportInfo(reportNo);
				}
			})
		});
}

var dataCheckAdmins;
//获取检测人员列表
function initCheckAdmins(selectID) {

	$.ajax({
		type : "POST",
		data:{'isDelete':'0'},
		url : "/common/queryWhereAdminInfoList",
		datatype : "json",
		async : false,
		success : function(result) {
			data = result.data;
			dataCheckAdmins = data;
			var loginId = result.loginId;
			$(selectID).empty(); 
			for(var i=0,j=data.length;i<j;i++){
				if(loginId == data[i].id){
					$(selectID).append("<option selected  value='"+data[i].id+"'>"+data[i].name+"</option>");
				}else{
					$(selectID).append("<option value='"+data[i].id+"'>"+data[i].name+"</option>"); 
				}
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}


//获取
function initCheckSelect(selectId, type) {

	$.ajax({
		type : "POST",
		data:{'type':type},
		url : "/config/queryDictListByType",
		datatype : "json",
		success : function(result) {
			data = result.data;
			datacuso=data;
			$("#"+selectId).empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#"+selectId).append("<option value='"+data[i].value+"'>"+data[i].lable+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
}

function transToChars(s) {  
	var chars = ["A", "B", "C", "D", "E", "F", "G",  
        "H", "I", "J", "K", "L", "M", "N",  
        "O", "P", "Q", "R", "S", "T", "U",  
        "V", "W", "X", "Y", "Z"  
    ]; 
	return chars[s];
}


//获取性别对应的值 0std 1smp 2pc 3pc2 4nc 5qc 6blk 7null
function getElisaTypeName(type){
	if (type == 0) {
		return "标准品";
	} else if (type == 1) {
		return "样品";
	}else if (type == 2) {
		return "阳性对照";
	}else if (type == 3) {
		return "阳性对照2";
	}else if (type == 4) {
		return "阴性对照";
	}else if (type == 5) {
		return "质控品";
	}else if (type == 6) {
		return "空白孔";
	}else{
		return "空";
	}
}

function transformChars(s) {  
    var chars = ["A", "B", "C", "D", "E", "F", "G",  
        "H", "I", "J", "K", "L", "M", "N",  
        "O", "P", "Q", "R", "S", "T", "U",  
        "V", "W", "X", "Y", "Z"  
    ];  

    function getCharIndex(string) {  
        var strings = string.trim().split("");  
        var indexs = [];  
        var temp = [];  
        var result = 0;  
        for (var i = 0; i < strings.length; i++) {  
            indexs.push(chars.indexOf(strings[i]) + 1);  
        }  
        for (var i = 0; i < indexs.length; i++) {  
            if (i === indexs.length - 1) {  
                temp.push(indexs[i])  
            } else {  
                temp.push(indexs[i] * chars.length + (i === 0 ? 0 : Math.pow(chars.length, i+1) - 26 ))   
            }  
        }  
        for (var i = 0; i < temp.length; i++) {  
            result += temp[i]  
        }  
        return result;  
    }  
return getCharIndex(s)  
}  



//去尾法
Number.prototype.toFloor = function (num) {
return Math.floor(this * Math.pow(10, num)) / Math.pow(10, num);
};

//进一法
Number.prototype.toCeil = function (num) {
return Math.ceil(this * Math.pow(10, num)) / Math.pow(10, num);
};

//四舍五入法
Number.prototype.toRound = function (num) {
return Math.round(this * Math.pow(10, num)) / Math.pow(10, num);
};