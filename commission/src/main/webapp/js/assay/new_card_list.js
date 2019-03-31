var table;
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
        { data: 'providerNo' },                             
        { data: 'name' },
        { data: 'proteinValue' },
        { data: 'bloodRedProteinValue'},
        { data: 'syphilis',render:function(data, type, full, meta){
        	if(data==0){
        		return '阴性';
        	}else if(data==1){
        		return '阳性';
        	}else if(data==2){
        		return '弱阳性';
        	}
        	return '';
        }},
        { data: 'alt',render:function(data, type, full, meta){
        	if(data==0){
        		return '≤25u';
        	}else if(data==1){
        		return '>25u';
        	}else if(data==2){
        		return '≥57u';
        	}else if(data==3){
        		return '≥97u';
        	}
        	return '';
        }},
        { data: 'hbsag',render:function(data, type, full, meta){
        	if(data==0){
        		return '阴性';
        	}else if(data==1){
        		return '阳性';
        	}else if(data==2){
        		return '弱阳性';
        	}
        	return '';
        }},
        { data: 'hcv',render:function(data, type, full, meta){
        	if(data==0){
        		return '阴性';
        	}else if(data==1){
        		return '阳性';
        	}else if(data==2){
        		return '弱阳性';
        	}
        	return '';
        }},
        { data: 'hiv',render:function(data, type, full, meta){
        	if(data==0){
        		return '阴性';
        	}else if(data==1){
        		return '阳性';
        	}else if(data==2){
        		return '弱阳性';
        	}
        	return '';
        }},
        { data: 'blood',render:function(data, type, full, meta){
        	if(data==0){
        		return 'A';
        	}else if(data==1){
        		return 'B';
        	}else if(data==2){
        		return 'O';
        	}else if(data==3){
        		return 'AB';
        	}
        	return '';
        }},
        { data: 'result',render:function(data, type, full, meta){
        	if(data==0){
        		return '合格';
        	}else if(data==1){
        		return '不合格';
        	}else if(data==2){
        		return '复检';
        	}
        	return '';
        }},
        { data: 'updateDate',render:function(data, type, full, meta){
        	if(null!=data){
        		return dateFtt('yyyy-MM-dd',new Date(data));
        	}
        	return '';
        }}
     ]
	getData('table',getParam,'../admin/queryNewCardList',columns,function(){
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
		table.ajax.reload();
    });
    
})

function resetInfo(){
	 $("input").val("");
	 $("select").val("");
}
