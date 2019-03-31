var ids = new Array();
$(function(){
	
	var stock = [ [ // 表头
		{type:'checkbox', fixed: 'left'}, 
		{
			field : 'id',
			title : '箱号',
			align : 'center'
		},{
			field : 'sNumber',
			title : '数量',
			align : 'center'
		}, {
			field : 'fstatus',
			title : '状态',
			align : 'center'
		}, {
			field : 'immuneName',
			title : '类型',
			align : 'center'
		}, {
			field : 'createDate',
			title : '创建时间',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.createDate));
			}
		}, {
			field : 'updateDate',
			title : '送浆时间',
			align : 'center',
			templet : function(d) { // 单元格格式化函数
				return dateFtt('yyyy-MM-dd',new Date(d.updateDate));
			}
		}
		] ];
	
	// 获取淘汰原因列表
	$.ajax({
		type : "POST",
		url : "/immuneSetting/queryImmuneSettingList",
		datatype : "json",
		success : function(data) {
			if(data.code==-1){
				$.each(data.data,function(index,item){
					$("#type").append('<option value="'+item.id+'">'+item.immuneName+'</option>');
				});
				var type=$("#type").val();
				var status=$("#status").val();
				/*dataAllAndWHWithPage("stock",stock,{"type":type,"status":0},GetURLInfo()+'queryOutPlasmaStock','','','',true,function(){
					
					});*/
				loadTable("stock",stock,{"type":type,"status":0},GetURLInfo()+'queryOutPlasmaStock');
			}
		},
		error:function(){
			
		}
	}); 
	
	//查询
	$("#query").click(function(){var type=$("#type").val();
		var type=$("#type").val();
		var status=$("#status").val();//入库状态为未出库
		var start=$("#start").val();
		var end=$("#end").val();
		var sNumber=$("#sNumber").val();
		layui.table.reload('stock', {where: {"type":type,"status":status,"start":start,"end":end,"sNumber":sNumber}});
	});
	
	/**
	 * 批量送浆
	 */
	$("#all").click(function(){
		/*var type=$("#type").val();
		var start=$("#start").val();
		var end=$("#end").val();
		var sNumber=$("#sNumber").val();
		var status=$("#status").val();//入库状态为未出库
		if(status.length<1 || status!=0){
			layer.alert('请选择状态为未出库');
			return false;
		}*/
		
		if(ids.length<1){
			layer.alert('请选择箱号');
			return false;
		}
		
		var html='<div class="gray-bg "><div class="wrapper wrapper-content animated fadeInRight weralid"><div class="row newregis indegirtion"><div class="row newregistr" id="search"><div class="col-xs-12 colxsgray"><div class="form-group newikrd"><label class="control-label UserC inhenagoi"><la style="line-height: 2.8rem;">批号</la>：</label>';
		html+='<div class="input-group inhemog"><input class="form-control newfrin" name="batchNumber" id="batchNumber"></div></div></div></div></div></div></div>';
		layer.open({
			  type: 1,
			  skin: 'layui-layer-rim', //加上边框
			  area: ['450px', '240px'], //宽高
			  btn: ['确定'],
			  content: html,
			  yes:function(){
				  var batchNumber = $("#batchNumber").val();
				  if(batchNumber.length<1){
					  layer.alert('请输入批号');
				  }else{
					  $.post('/plasmaStock/updatePulpingList',{"batchNumber":batchNumber,"id":ids.join(",")},function(res){
							layer.alert(res.message,function(){
								layer.closeAll();
								location.reload();
							});
						});
				  }
			  }
		});
	});
	
	//取消送浆
	$("#cacal").click(function(){
		var checkStats = table.checkStatus('stock'), data = checkStats.data;
		if(data.length<1){
			layer.alert('请选择箱号');
			return false;
		}
		var ids = '';
		for(var i=0;i<data.length;i++){
			ids+=data[i].id+',';
		}
		ids = ids.substring(0,ids.length-1);
		$.post('/plasmaStock/cacalPulpingList',{"id":ids},function(res){
			layer.alert(res.message,function(){
				layer.closeAll();
				location.reload();
			});
		});
	});
	
	//导出
	$("#export").click(function(){
		var type=$("#type").val();
		var status=$("#status").val();//入库状态为未出库
		var start=$("#start").val();
		var end=$("#end").val();
		var sNumber=$("#sNumber").val();
		window.location.href='/plasmaStock/downOutPlasmaStockList?type='+type+"&status="+status+"&start="+start+"&end="+end+"&sNumber="+sNumber;
	});
	
	//导出数据到公司
	$("#exportCompany").click(function(){
		layer.open({
			type : 2,
			title : '导出数据到公司',
			maxmin : true,
			area : ["90%","90%"],
			content : '/plasmaStock/exportPlasmaToCompany'
		})
	});
	
	//血浆检测装运表
	$("#plasma_check").click(function(){
		layer.open({
			type : 2,
			title : '打印血浆检测装运表',
			maxmin : true,
			area : ["90%","90%"],
			content : '/plasmaStock/plasmaCheck'
		})
	});
	
	//血浆装箱汇总表
	$("#plasma_summary").click(function(){
		layer.open({
			type : 2,
			title : '打印血浆装箱汇总表',
			maxmin : true,
			area : ["90%","90%"],
			content : '/plasmaStock/plasmaSummary'
		})
	});
})

function loadTable(elem,colos,where,url){
	layui.use(['laydate', 'laypage', 'table'], function () {
        laydate = layui.laydate, //日期
            laypage = layui.laypage, //分页
            table = layui.table, //表格
            element = layui.element; //元素操作
        table.render({
            elem: '#'+elem,
            method: "POST",
            where:where,
            url: url, //数据接口
            page: true, //开启分页
            limits: [5, 10, 15, 20],
            limit: 5, //每页默认显示的数量
            response: { // //定义后端 json 格式，详细参见官方文档
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: -1, //成功的状态码，默认：0
                msgName: 'message', //状态信息的字段名称，默认：msg
                count: "count", //数据总数的字段名称，默认：count
                data: 'data' //数据列表的字段名称，默认：data
            },
            cols: colos,
            done : function(res, curr, count) {
            	var tab = $("#stock").next().find('.layui-table tbody tr');
				
				//以复选框事件为例
				table.on('checkbox(demo)', function(obj){
					var type = obj.type;
					var check = obj.checked;
					var id = obj.data.id;
					var flag = false;
					//是否选中,如果选中，判断集合中有没有
					if(type=='all'){
						$(tab).each(function(index,item){
							var id = $(item).find("td").eq(1).find("div").html();
							if(index==5){
								return false;
							}
							var flag = true;
							for(var i=0;i<ids.length;i++){
								if(ids[i]==id){
									flag = false;
									if(!check){
										ids.splice(i,1);
									}
									break;
								}
							}
							if(check){
								if(flag){
									ids.push(id);
								}
							}
						});
					}else{
						if(check){
							ids.push(id);
						}else{
							for(var i=0;i<ids.length;i++){
								if(ids[i]==id){
									ids.splice(i,1);
									break;
								}
							}
						}
					}
				});
					for(var i=0;i<res.data.length;i++){
						var fas = false;
						var id = res.data[i].id;
						for(var j=0;j<ids.length;j++){
							if(ids[j]==id){
								fas = true;
								break;
							}
						}
						if(fas){
							res.data[i]["LAY_CHECKED"]='true';
						    $('.layui-table-fixed-l tr[data-index=' + i + '] input[type="checkbox"]').prop('checked', true);
                            $('.layui-table-fixed-l tr[data-index=' + i + '] input[type="checkbox"]').next("div").addClass('layui-form-checked');
						}
					}
					var checkStatus = table.checkStatus('stock');
					   if(checkStatus.isAll){
					    $('.layui-table-header th[data-field="0"] input[type="checkbox"]').prop('checked', true);
					    $('.layui-table-header th[data-field="0"] input[type="checkbox"]').next().addClass('layui-form-checked');
					   }
            }
        });
    });
}
