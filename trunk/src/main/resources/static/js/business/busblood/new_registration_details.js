/*列表数据显示借口*/
// 查询小组名称
	$.ajax({
		type : "POST",
		url : "/group/queryGroupInfo",
		datatype : "json",
		success : function(result) {
			for ( var o in result.data) {
				var str = "<option value=" + result.data[o].id + ">"
						+ result.data[o].groupName + "</option>";
				$("#groupId").append(str);
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
var cols= [[ //表头
    {field: 'regCreateDate', title: '登记日期',align:'center',templet:function(d){
        return $.myTime.UnixToDate(d.regCreateDate);}
    },
    {field: 'isCheck',title: '是否体检',align: 'center',templet: function (d){ // 单元格格式化函数
        var text = '-';
        if (d.isCheck ==1) {
            text = "已体检";
        } else{
            text = "未体检";
        }
        return text;
    }},
    {field: 'result',title: '体检合格',align: 'center',templet: function (d){ // 单元格格式化函数
        var text = '-';
        if (d.result == 0) {
            text = "合格";
        } else if (d.result == 1) {
            text = "不合格";
        }
        return text;
    }},
    {field: 'isCollection',title: '是否采集',align: 'center',templet: function (d){ // 单元格格式化函数
        var text = '-';
        if (d.isCollection == 1) {
            text = "已采集";
        } else{
            text = "未采集";
        }
        return text;
    }},
    {field: 'colUpdateDate', title: '采浆日期',align:'center',templet:function(d){
    	if(null==d.colUpdateDate){
    		return '';
    	}
        return $.myTime.UnixToDate(d.colUpdateDate);}
    }
]];
loadPlasmaInfo(0);
function loadinfo(){
    var providerNo = $("#providerNo").val();
    dataAll("busdetatable", cols, {"providerNo": providerNo}, GetURLInfo() + "queryPlasmaInfoDetailList", '', '');
}
