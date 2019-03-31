var table;
//复选框 ID 集合
var ids=new Array();
//查询参数
function getParam(){
	var name=$("#name").val();
	var providerNo=$("#providerNo").val();
	var param = {};
	param.name=name;
    param.providerNo=providerNo;
    param.type=0;
    return param;
}
$(function(){
	
    var columns = [
    	{ data:null,render:function(data, type, full, meta){
 		   return '<input type="checkbox" value="'+data.id+'" id="id_'+data.id+'" class="data_checkbox">';
 	   }},
        { data: 'providerNo' },                             
        { data: 'name' },
        { data: 'sex',render:function(data, type, full, meta){
      	   if(data=='0'){
      		   return "男";
      	   }else{
      		   return "女";
      	   }
         }},
        { data: 'idNo' },
        { data: 'bloodType',render:function(data, type, full, meta){
        	if(data=='0'){
       		   return "A";
       	   }else if(data=='1'){
       		   return "B";
       	   }else if(data=='2'){
       		   return "O";
       	   }else if(data=='3'){
       		   return "AB";
       	   }
           return '';
        }},
        { data: 'birthday',render:function(data, type, full, meta){
        	return dateFtt("yyyy-MM-dd",new Date(data));
        }},
        { data: 'addressx' },
        { data: 'status',render:function(data, type, full, meta){
        	if(data=='0'){
        		return '否';
        	}
        	return '是';
        }},
        { data: 'createDate',render:function(data, type, full, meta){
        	return dateFtt("yyyy-MM-dd",new Date(data));
        }},
        { data: 'updateDate',render:function(data, type, full, meta){
        	if(null==data){
        		return '';
        	}
        	return dateFtt("yyyy-MM-dd",new Date(data));
        }},
        { data: null,render:function(data, type, full, meta){
        	var html='<button type="button" class="btn  btn-info" onclick="openModal(\''+data.id+'\',\'2\')">查看</button>';
        	return html;
        }}
     ]
	getData('table',getParam,'../admin/queryCancelCardList',columns,function(){
		//表格加载完成事件
    	$(".data_checkbox").each(function(){
    		var item=$(this);
    		var id=item.val();
    		for(var i=0;i<ids.length;i++){
    			if(ids[i]==id){
    				item.attr("checked",true);
    				break;
    			}
    		}
    		
    		item.click(function(){
    			$("#all").prop('checked',false);
    			var id=item.val();
    			if(item.is(':checked')){
    				ids.push(id);
    			}else{
    				for(var i=0;i<ids.length;i++){
    					if(ids[i]==id){
    						ids.splice(i,1);
    						break;
    					}
    				}
    			}
    		});
    	});
	});
    
    //查询
    $("#query").click(function(){
    	ids=new Array();
		table.ajax.reload();
    });
    
    //全选
    $("#all").click(function(){
    	var isCheck=$(this).prop('checked');
    	$("#table tbody tr input").each(function(){
    		var item=$(this)
    		item.prop("checked",isCheck);
    		var id = item.val();
    		if(isCheck){
    			var is=true;
    			for(var i=0;i<ids.length;i++){
					if(ids[i]==id){
						is=false;
						break;
					}
				}
    			if(is){
    				ids.push(id);
    			}
    		}else{
    			for(var i=0;i<ids.length;i++){
					if(ids[i]==id){
						ids.splice(i,1);
						break;
					}
				}
    		}
    	});
    });
    
  //审核
    $("#cancel").click(function(){
    	var id = ids.join(",");
    	if(id.length<1){
    		alert('请选择浆员');
    	}else{
    		if(confirm("按照国家相关规定，是否同意修改浆员信息")){
    			$.post('../admin/examinePlasmaUpdate',{"id":id},function(res){
            		if(res.error==-1){
            			alert('审核成功');
            			location.reload();
            		}else{
            			alert('审核失败');
            		}
            	});
    		}
    	}
    });
})

//打开模态框
function openModal(param,type){
	var fatherBody = $(window.top.document.body);
	var id = 'pages';
	var dialog = $('#' + id);
	if (dialog.length == 0) {
		dialog = $('<div class="modal fade" role="dialog" id="' + id + '"/>');
		dialog.appendTo(fatherBody);
	}
	dialog.load("../admin/queryDetail?id="+param+"&type="+type, function() {
		dialog.modal({
		  backdrop: false
		});
	});
}

//关闭模态框
function closeModal(){
	var fatherBody = $(window.top.document.body);
	fatherBody.find("#pages").on('hidden.bs.modal', function (e) {
		fatherBody.find("#backdropId").remove();
	});
}

function resetInfo(){
	 $("input").val("");
	 $("select").val("");
}