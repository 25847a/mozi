$(function(){
	/*//日期时间*/
	initDate("#startTime");
	initDate("#endTime");
	initTableData();
	initTemplates();
	initCoordinate();
	var cols= [[ //表头
		/* {type: 'checkbox', fixed: 'left'}*/
			 {field : 'id',title : 'id',display : 'none',minWidth : '0',width : '0',type : "space"}
			,{field: 'sampleNo', title: '全小样号',width : '10%',sort: true,align:'center',/* fixed: 'left'*/}
			,{field: 'allId', title: '全登记号',width : '10%',  sort: true,align:'center',/* fixed: 'left'*/}
			,{field: 'providerNo', title: '献浆员卡号', width : '10%',align:'center'}
			,{field: 'blood', title: '血型', sort: true,align:'center',width : '6%',templet : function(d){return getBloodValue(d.blood); }}
			,{field: 'name', title: '姓名', sort: true,width : '6%',align:'center'}
			,{field: 'result', title: '化验结果', width : '10%',align:'center', templet:function(d){ return getCheckResultValue(d.result);}}
			,{field: 'proteinValue', title: '蛋白含量', width : '10%',align:'center'}
			,{field: 'HBsAg', title: 'HBsAg', width : '8%',align:'center',templet: function (d){ return getCheckedValue(d.HBsAg);}} 
			,{field: 'hcv', title: 'HCV', width : '8%',align:'center',templet : function(d){return getCheckedValue(d.hcv); }}
			,{field: 'alt', title: 'ALT',width : '8%', align:'center' ,templet : function(d){return getCheckedValue(d.alt); }}
			,{field: 'hiv', title: 'HIV', width : '8%',align:'center' ,templet : function(d){return getCheckedValue(d.hiv); }}
			,{field: 'syphilis', title: '梅毒',width : '8%', align:'center' ,templet : function(d){return getCheckedValue(d.syphilis); }}
			,{field: 'wholeBlood', title: '全血比重', width : '10%',align:'center' ,templet : function(d){return getCheckResultValue(d.wholeBlood); }}
			,{field: 'plasmaType', title: '固定浆员', width : '10%',align:'center' ,templet : function(d){return getBooleanValue(d.plasmaType); }}
		    ]];
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			startTime = startTime+" 00:00:00";
			endTime = endTime+ " 23:59:59";
			var isAssay = $("#isAssay").val();
			var result = $("#result").val();
			var type = $("#type").val();
			dataAll("toconig",cols,{"startTime" : startTime, "endTime" :endTime,"isAssay":isAssay, "type":type, "result" :result, "token" : getToken()},'/newCard/queryListsByCreateDate',GetURLInfo() +'',GetURLInfo() + "",function(){
				 initTableOnClick();
			 });
	
	
	
	
});

function doSearch(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	startTime = startTime+" 00:00:00";
	endTime = endTime+ " 23:59:59";
	var isAssay = $("#isAssay").val();
	var result = $("#result").val();
	var type = $("#type").val();
	layui.table.reload('toconig', {where: {"startTime" : startTime, "endTime" :endTime,"isAssay":isAssay, "type":type, "result" :result, "token" : getToken()}});
}

function initTableData(){
	$("#tdata").empty(); 
	for(var i=0,j=8;i<j;i++){
		$("#tdata").append("<tr height='30px'>"+
						"<td align='center' width='20px'>"+transToChars(i)+"</td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"1'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"2'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"3'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"4'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"5'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"6'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"7'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"8'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"9'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"10'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"11'></td>"+
						"<td align='center' width='80px' id='"+transToChars(i)+"12'></td>"+
					"</tr>"); 
	};
}

function initTableOnClick(){
	// 表格 tr 单击事件
	var tab = $("#toconig").next('.layui-table-view') .find('table.layui-table');
	
	tab.click(function(event) {
		var tr = $(event.target).closest("tr")[0];
		var td = $(event.target).closest("td")[0];
		var id = $(tr).find("td").eq(0).find("div").html();
		var sampleNo = $(tr).find("td").eq(1).find("div").html();
		var providerNo = $(tr).find("td").eq(3).find("div").html();
		var name = $(tr).find("td").eq(5).find("div").html();
		var blood = $(tr).find("td").eq(4).find("div").html();
		if(undefined == providerNo) 
			return false;
		initData(id,sampleNo+" "+providerNo+" "+name+"　"+blood);
	});
	
}

function initData(id,specimen) {
	$("#id").val(id);
	$("#specimen").val(specimen);
	
}

$("#elisaAuto").click(function(){
	
	var url = "/elisaInfo/doAutoElisa";
	var datas = {};
	simpleAjax(url, datas, function(result) {
		var info = result.data;
		var size = result.size;
		var str = "";
		if(result.code == -1){
			str= "操作成功!共处理了"+size+"块微孔板.";
			var index = parent.layer.alert(str, function() {
				parent.layer.close(index);
			});
		}
	});
});

$("#bioAuto").click(function(){
	 layer.ready(function() {
	      perContent = layer.open({
	                  type:2,
	                title: '全自动生化仪',
	                content: '/testResult/initBioPage',
	                  area: ['100%', '100%'],
	             });
	           layer.full(perContent);
	    });
	
});


! function() {
  //页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
  $('#elisajilv').on('click', function() {
    layer.ready(function() {
      perContent = layer.open({
                  type:2,
                title: '保存记录查询',
                content: '/elisaInfo/gotoCM',
                  area: ['100%', '100%'],
             });
           layer.full(perContent);
    });
  })

}();
! function() {
  $('#elisajie').on('click', function() {
    layer.ready(function() {
      perContent = layer.open({
                  type:2,
                title: '酶标板综合管理',
                content: '/elisaInfo/initPage2',
                  area: ['100%', '100%'],
             });
           layer.full(perContent);
    });
  })
}();

function changeTD(){
	var specimen = $("#specimen").val();
	$("#"+($("#vertical").val()+ $("#landscape").val())).html(specimen.replace(/ /g,"<p>"));
}

// 初始化表格坐标
function initCoordinate(){
	$("#landscape").empty(); 
	$("#vertical").empty(); 
	for(var i=0,j=8;i<j;i++){
		$("#vertical").append("<option value='"+transToChars(i)+"'>"+transToChars(i)+"</option>"); 
	}
	for(var i=1,j=13;i<j;i++){
		$("#landscape").append("<option value='"+i+"'>"+i+"</option>"); 
	}
}

// 初始化模板选择框
function initTemplates(){
	$.ajax({
		type : "POST",
		data:{'delFlag':'0'},
		url : "/elisaTemplate/queryTemplates",
		datatype : "json",
		success : function(result) {
			data = result.data;
			datacuso=data;
			$("#template").empty(); 
			for(var i=0,j=data.length;i<j;i++){
				$("#template").append("<option value='"+data[i].id+"'>"+data[i].templateName+"</option>"); 
			}
		},
		error : function() {
			alert("请稍后试试");
		}
	});
	
}


! function() {
  //页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
  $('#mianshe').on('click', function() {
    layer.ready(function() {
     layer.open({
         type: 2,
          title: '酶标板标题设置',
         maxmin: false,
        content: '/elisaInfo/gotoCM',
                 area: ['90%', '90%'],
      })
    });
  })
}();
