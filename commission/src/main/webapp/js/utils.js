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

//获取hr列表
function getData(id,getParam,url,columns,fun){
	// 先清除表格数据,在初始化datatable
	if(typeof(table)!='undefined'){
		table.fnClearTable();// 清空数据.fnClearTable();//清空数据
		table.fnDestroy(); // 还原初始化了的datatable
	}
	// 提示信息
    var lang = {
    		"sLengthMenu" : "每页显示 _MENU_ 条记录",
    		"sZeroRecords" : "抱歉， 没有找到",
    		"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
    		"sInfoEmpty" : "没有数据",
    		"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页",
            "sJump": "跳转"
        }
    };

    // 初始化表格
    table = $("#"+id).dataTable({
    	language:lang,  //提示信息
        serverSide: true,  //启用服务器端分页
        "scrollX": true,//水平滚动条
        "bPaginate" : true, // 翻页功能
        "bProcessing" : true, //加载数据等待效果
		"bLengthChange" : false, // 改变每页显示数据数量
		"bFilter" : false, // 过滤功能
		"bSort" : false, // 排序功能
		"bInfo" : false,// 页脚信息
		"bAutoWidth" : true,// 自动宽度
		"iDisplayLength" : 10,
		"sPaginationType" : "full_numbers",
        ajax: function (data, callback, settings) {
            // 封装请求参数
            // 每页显示条数
        	var param={};
        	if(getParam!=null && typeof(getParam)!=='undefined'){
        		param = getParam();
        	}
            param.pageSize = 10;// 页面显示记录条数，在页面显示每页显示多少项的时候
            param.start = data.start;// 开始的记录序号
            // 当前页码
            param.pageNum = (data.start / data.length)+1;
            /** ************ 请求参数 ***************************** */
            /** ************ 请求参数 ***************************** */
            // ajax请求数据
            $.ajax({
            	type : "POST",
        		url : url,
                cache: false,  // 禁用缓存
                data: param,  // 传入组装的参数
                dataType: "json",
                success: function (dataSource) {
                	// 封装返回数据
                    var returnData = {};
                    // 这里直接写死
                    returnData.draw = data.draw;
                    // 这里是记录总数
                    returnData.recordsTotal = dataSource.data.totalNum;
                    returnData.recordsFiltered = dataSource.data.totalNum;// 后台不实现过滤功能，每次查询均视作全部结果
                    // 返回的数据
                    returnData.data = dataSource.data.listData;// 返回的数据列表
                    // 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    // 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
        // 列表表头字段
        columns: columns
    }).api().on('draw.dt', function () {
    	if(null!=fun && typeof(fun)!=='undefined'){
    		fun();
    	}
    });
}