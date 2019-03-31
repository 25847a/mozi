$(function(){
	//初始化时间
	initDate("#birthdayStartDate");
	initDate("#birthdayEndDate");
	initDate("#startDate");
	initDate("#endDate");
	$("#query_date input").val("");
	
	// 加载免疫类型选项
	initCheckData21(null,'/immuneSetting/queryImmunTypeOne',null,0);
	//加载浆站民族
	initCheckData21('#nation','/nation/queryNationByPlasmType',null,2);
	var plasma = [ [ // 表头
		{
			field : 'id',
			title : 'id',
			display : 'none',
			minWidth : '0',
			width : '0',
			type : "space"
		}, {
			field : 'providerNo',
			title : '献浆员卡号',
			align : 'center',
			width : '10%'
		}, {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : '8%'
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			width : '6%',
			templet : function(d) { // 单元格格式化函数
				return getSexValue(d.sex);
			}
		}, {
			field : 'bloodType',
			title : '血型',
			width : '6%',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return getBloodValue(d.bloodType);
			}
		}, {
			field : 'plasmaState',
			title : '浆员状态',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				if(d.plasmaState==0){
					return "正常";
				}else if(d.plasmaState==1){
					return "暂时拒绝";
				}else{
					return "永久拒绝";
				}
			}
		},{
            field : 'refuseDate',
            title : '拒绝时间',
            align : 'center',
            width : '8%',
            templet : function(d) { // 单元格格式化函数
            	if(null!=d.refuseDate){
            		return dateFtt('yyyy-MM-dd',new Date(d.refuseDate));
            	}
                return '';
            }
        },{
            field : 'eliminateReason',
            title : '拒绝原因',
            align : 'center',
            width : '20%',
        }, {
			field : 'immuneName',
			title : '浆员类型',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				if(d.immuneName=='' | d.immuneName==null){
					return "普通";
				}else{
					return d.immuneName;
				}
			}
		},{
			field : 'birthday',
			title : '出生日期',
			align : 'center',
			width : '10%',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.birthday){
            		return dateFtt('yyyy-MM-dd',new Date(d.birthday));
            	}
                return '';
			}
		},{
			field : 'idNo',
			title : '身份证号码',
			align : 'center',
			width : '15%'
		},{
			field : 'addressx',
			title : '身份证地址',
			align : 'center',
			width : '20%'
		},{
			field : 'nname',
			title : '民族',
			align : 'center',
			width : '6%'
		},{
			field : 'date',
			title : '最后采浆时间',
			align : 'center',
			width : '15%',
			templet : function(d) { // 单元格格式化函数
				if(null!=d.date){
					return dateFtt('yyyy-MM-dd',new Date(d.date));
				}
				return '';
			}
		},{
			field : 'cou',
			title : '采浆次数',
			align : 'center',
			width : '15%'
		}
		] ];
	
	dataAll("plasma",plasma,{},"/providerBaseinfo/queryPlasmaDetailList",'','',function(){
		var tab = $("#plasma").next().find('.layui-table tbody tr');
		$(tab).each(function(){
			var item=$(this);
			var state = item.find('td').eq(5).find('div').html();
			if(state=='永久拒绝'){
				$(item).css({"color":"red"});
			}else if(state=='暂时拒绝'){
				$(item).css({"color":"green"});
			}
		});
	});
	
	//查询
	$("#query").click(function(){
		var param=[];
		$("#search input,select").each(function(){
			var item=$(this);
			var name=item.attr("id");
			var val = item.val();
			param[name]=val;
		});
		layui.table.reload('plasma', {page: {curr:1},where: param});
	});
	
	//导出
	$("#export").click(function(){
		var param="";
		$("#search input,select").each(function(){
			var id = $(this).attr("id");
			var val = $(this).val();
			param+=id+"="+val+"&"
		});
		if(param.length>0){
			param = param.substring(0,param.length);
		}
		window.location.href="/providerBaseinfo/exportBaseInfoList?"+param;
	});
	//重置
	$("#btn-reset").click(function(){
		$("#search input").val("");
		$("#search select").val("");
	}); 
})


function initCheckData21(selectId,url,datas,type){
		simpleAjax(url,datas,function(result){
			if(type==0){
				if(result.code==-1){
		        	   var data = result.data;
		        	   if(data.isDisable==1){
		        		// 加载免疫类型选项
		        			initCheckData21('#type','/immuneSetting/queryAmmuneSetting',{type:1},1);
		        	   }else{
		        		   initCheckData21('#type','/immuneSetting/queryImmuneSettingList',null,1);
		        	   }
		           }
			}
			if(type==1){
				// 加载免疫类型选项
				 for (var o in result.data){
		                var str = "<option value=" + result.data[o].immuneName + ">" + result.data[o].immuneName + "</option>";
		                $(selectId).append(str);
		            }
			}
			if(type==2){
				//加载浆站民族
				for (var o in result.data){
	                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
	                $(selectId).append(str);
	            }
			}
		})
		
	}