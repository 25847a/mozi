//献浆员登记
$(function(){
	// 加载免疫类型选项
    $.ajax({
        type : "POST",
        url :"/immuneSetting/queryImmuneSettingList",
        datatype : "json",
        data:{
        	type:"2"
        },
        success : function(result) {
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].immuneName + "</option>";
                $("#immuneId").append(str);
            }
        },
        error : function() {
        	parent.layer.alert("获取免疫类型失败",function(){
   				parent.layer.closeAll();
   			});
        }
    });
    var cols=[[ //表头
    	/* {type: 'checkbox', fixed: 'left'}*/
         {field: 'ordinaryNo', title: '免疫编号', align:'center'},
          {field: 'providerNo', title: '浆员编号', align:'center'},
          {field: 'sex', title: '性别', align:'center',templet: function (d){
    			return getSexValue(d.sex);
    			}},
          {field: 'bloodType', title: '血型', sort: true,align:'center',templet:function(d){
        	  return getBloodValue(d.bloodType);
        	  }},
          {field: 'immuneName', title: '免疫类型', align:'center'},
          {field: 'pcount', title: '特免基础针', align:'center',templet:function(d){
        	  if(d.pcount==null){
        		  return 0;
        	  }else{
        		  return d.pcount;
        	  }
    	  }},
          {field: 'tcount', title: '特免加强针', align:'center',templet:function(d){
        	  if(d.tcount==null){
        		  return 0;
        	  }else{
        		  return d.tcount;
        	  }
    	  }},
          {field: 'updateDate', title: '登记时间', align:'center',templet: function (d){
        	  return dateFtt("yyyy-MM-dd",new Date(d.updateDate));
  		} },
  		{field: 'admin', title: '登记人', align:'center'}
    ]]
dataAllIsOpenLimit("teregisterInfo",cols,{"token" : getToken()},GetURLInfo() + 'queryTuregisterInfo','','',true,'',13);
	
  //搜索按钮点击事件
   $("#query").click(function(){
    	doSearch();
    	 currentPageAllAppoint = 1; //当点击搜索的时候，应该回到第一页
    });
    //重置
    $("#btn-reset").click(function(){
    	$("#search_par input").val("");
    	$("#search_par select").val("");
    });
  });




//搜索
function doSearch(){
	var ordinaryNo = $("#ordinaryNo").val();
	var immuneId = $("#immuneId").val();
	var providerNo = $("#providerNo").val();
	var pcount = $("#pcount").val();
	var tcount = $("#tcount").val();
	var name = $("#name").val();
	layui.table.reload('teregisterInfo', {where: {"ordinaryNo": ordinaryNo,"immuneId":immuneId,"providerNo":providerNo,"pcount":pcount,"tcount":tcount,"name":name}});
}
function getToken() {
	return localStorage.getItem("token");
}
/**
 * 刷新按钮
 * @returns
 */
function refresh(){
	location.reload();
}

