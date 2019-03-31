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
        { data: 'tz' },
        { data: 'tw' },
        { data: 'mb' },
        { data: null,render:function(data, type, full, meta){
        	return data.xya+'/'+data.xyb;
        }},
        { data: 'xb',render:function(data, type, full, meta){
        	if(data==0){
        		return '正常';
        	}
        	return '异常';
        }},
        { data: 'fb',render:function(data, type, full, meta){
        	if(data==0){
        		return '正常';
        	}else if(data==1){
        		return '有肿块';
        	}else if(data==2){
        		return '压痛';
        	}else if(data==3){
        		return '肝脾肿大';
        	}
        	return '';
        }},
        { data: 'pf',render:function(data, type, full, meta){
        	if(data==0){
        		return '正常';
        	}else if(data==1){
        		return '黄染';
        	}else if(data==2){
        		return '有创面感染';
        	}else if(data==3){
        		return '有大面积皮肤病';
        	}else if(data==4){
        		return '浅表淋巴结有明显肿大';
        	}
        	return '';
        }},
        { data: 'wg',render:function(data, type, full, meta){
        	if(data==0){
        		return '正常';
        	}else if(data==1){
        		return '严重疾病';
        	}else if(data==2){
        		return '巩膜黄染';
        	}else if(data==3){
        		return '甲状腺肿大';
        	}
        	return '';
        }},
        { data: 'sz',render:function(data, type, full, meta){
        	if(data==0){
        		return '正常';
        	}else if(data==1){
        		return '严重残疾';
        	}else if(data==2){
        		return '功能性障碍';
        	}else if(data==3){
        		return '关节红肿';
        	}else if(data==4){
        		return '静脉穿刺部分皮肤损伤';
        	}else if(data==5){
        		return '有静脉注射药物痕迹';
        	}
        	return '';
        }},
        { data: 'heart' },
        { data: 'liver' },
        { data: 'spleen' },
        { data: 'lung' },
        { data: 'result',render:function(data, type, full, meta){
        	if(data==0){
        		return '合格';
        	}else if(data==1){
        		return '不合格';
        	}
        	return '';
        }},
        { data: 'reason' },
        { data: 'updateDate',render:function(data, type, full, meta){
        	if(null!=data){
        		return dateFtt('yyyy-MM-dd',new Date(data));
        	}
        	return '';
        }}
     ]
	getData('table',getParam,'../admin/queryCheckList',columns,function(){
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
