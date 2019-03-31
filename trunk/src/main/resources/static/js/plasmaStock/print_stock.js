$(function(){

	var boxId = location.search.substring(location.search.indexOf("=")+1,location.search.length);
	
	var stock = [ [ // 表头
		{
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
			field : 'allId',
			title : '登记号',
			align : 'center'
		}, {
			field : 'bloodType',
			title : '血型',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				var text = '-';
				if (d.bloodType == 0) {
					text = "A";
				} else if (d.isDisable == 1) {
					text = "B";
				} else if (d.isDisable == 2) {
					text = "O";
				} else {
					text = "AB";
				}
				return text;
			}
		}, {
			field : 'createDate',
			title : '采浆时间',
			align : 'center'
		}
		] ];
	
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
        table.render({
            elem: '#stock',
            method: "POST",
            where:{"boxId":boxId},
            url: "/plasmaStock/queryPrintPlasmaStockList", //数据接口
            page: false, //开启分页
            limits: [5, 10, 15, 20],
            limit: 5, //每页默认显示的数量
            response: { // //定义后端 json 格式，详细参见官方文档
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: -1, //成功的状态码，默认：0
                msgName: 'message', //状态信息的字段名称，默认：msg
                count: "count", //数据总数的字段名称，默认：count
                data: 'data' //数据列表的字段名称，默认：data
            },
            cols: stock,
            done : function(res, curr, count) {
            	doPrint() 
            }
        });
    });
})

function doPrint() {
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
        eprnstr="<!--endprint-->"; //结束打印标识字符串
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
        window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
        window.print(); //调用浏览器的打印功能打印指定区域
        window.document.body.innerHTML=bdhtml; // 最后还原页面
    }