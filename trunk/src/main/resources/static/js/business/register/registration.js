//这个是点击路费默认是否可以填写的功能
$("input[name='roadFeeType']").click(function(){
    var radio = document.getElementsByName("roadFeeType");
    for (i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
        	if (radio[i].value == 0) {
                $("#roadFee").attr("readonly", "readonly");
            } else {
                $("#roadFee").removeAttr("readonly");
            }
        }
    }
})
//这个是点击车补默认是否可以填写的功能
$("input[name='roadFee']").keyup(function(){
    var tmptxt=$(this).val();
    $(this).val(tmptxt.replace(/\D|^/g,''));
}).bind("paste",function(){
    var tmptxt=$(this).val();
    $(this).val(tmptxt.replace(/\D|^/g,''));
}).css("ime-mode", "disabled");

//获取断档号选项
$.ajax({
    type : "POST",
    url :GetURLInfo()+"/queryBrokenNumber",
    datatype : "json",
    success : function(result) {
    	if(null!=result.data){
    		for (var o in result.data){
                var str = "<option value=" + result.data[o].registriesNo + ">" + result.data[o].registriesNo + "</option>";
                $("#breakNumber").append(str);
            }
    	}
    },
    error : function() {
    	parent.layer.alert("获取断档号失败",function(){
				parent.layer.closeAll();
			});
    }
});
//献浆员登记
$(function(){
    var registration = [[ //表头
    	//{type: 'checkbox', fixed: 'left'},
    	{field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center'}
      ,{field: 'providerNo',title: '献浆编号',width:'8%',align: 'center', fixed: 'left'}
      ,{field: 'name', title: '姓名',width:'8%', align:'center'}
      ,{field: 'idNo', title: '身份证', width:'16%',align:'center'}
      ,{field: 'registriesNo', title: '登记号',width:'8%',sort: true,align:'center'}
      ,{field: 'sex', title: '性别',align:'center',width:'6%',templet: function (d){ // 单元格格式化函数
				var text = '-';
              if (d.sex == 0) {
                  text = "男";
              } else{
                  text = "女";
              }
              return text;
          }} 
      ,{field: 'immuneName', title: '类型',align:'center',width:'6%',templet: function (d){ // 单元格格式化函数
    	  if(d.immuneName==undefined){
    		  return '普通';
    	  }else{
    		  return d.immuneName;
    	  }
      }
    	  }
      ,{field: 'inspection', title: '验证方式',align:'center',width:'9%',templet: function (d){ // 单元格格式化函数
  	  		return getTypeValue(d.inspection);
      	}
      }
      ,{field: 'plasmaType', title: '固定浆员',align:'center',width:'9%',templet: function (d){
    	  var text = '-';
    	  if(d.plasmaType==0){
    		  text="非固定";
    	  }else if(d.plasmaType==1){
    		  text="固定";
    	  }
  	  		return text;
  	   }}
      ,{field: 'createDate', title: '登记日期', align:'center',width:'12%',templet: function (d){
    	  var text = '-';
    	  if(d.createDate==undefined){
    		  text="";
    	  }else{
    		  text = dateFtt("yyyy-MM-dd",new Date(d.createDate));
    	  }
  	  		return text;
  	   }
    	  } 
      ,{field: 'isIdentity', title: '当前状态', align:'center',width:'9%',templet: function (d){
    	  var text = '-';
    	  	if (d.isIdentity == 0) {
    	  		text = "已验证";
    	  	} else{
    	  		text = "未验证";
    	  	}
    	  		return text;
    	  } 
      }
      ,{field: 'phone', title: '电话',width:'12%',align:'center'} 
      ,{field: 'addressx', title: '住址',width:'18%',align:'center'} 
      ,{fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'} // (临时注释不可删掉)隐藏删除按钮,如需求需要开放注释即可
    ]];
    dataAllAndWHWithPage("newtest",registration,{"token" : getToken()},GetURLInfo()+'queryRegistrationList','',GetURLInfo()+'delectRegistration','',true,function(){
    			//表格 tr 单击事件
    		   var tab = $("#newtest").next().find('.layui-table tbody tr');
    		   tab.click(function(event){
    		   	  var tr =$(event.target).closest("tr")[0];
    		   	  var code=$(tr).find("td").eq(1).find("div").html();
    		   	  $("#providerNo").val(code);
    		   	  var createDate = $("#startTime").val();
    		   	  if(createDate==''){
    		   		parent.layer.alert("请选择登记日期");
    		   		return;
    		   	  }
    		   	  var data = {"code":code,"createDate":createDate};
    		   	  $.post("/providerRegistries/queryPlasmaProviderNo",data,function(result){
    		   		  if(null!=result){
    		    	$("#name").val(result.data.name);
    		    		$("#id").val(result.data.id);
    					$("#bloodType").val(getBloodValue(result.data.bloodType));
    					$("#providerNo").val(result.data.providerNo);
    					$("#immuneName").val(result.data.immuneName==undefined?"普通":result.data.immuneName);
    					$("#effectiveValue").val(result.data.effectiveValue);
    					$("#icNumber").val(result.data.icNumber);
    					$("#groupName").val(result.data.num);
    					$("#sex").val(getSexValue(result.data.sex));
    					$("#phone").val(result.data.phone);
    					$("#year").val(result.data.year);
    					$("#month").val(result.data.month);
    					$("#immuneRegisterId").val(result.data.immuneRegisterId);
    					$("#collection").val(result.data.collectionDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(result.data.collectionDate)));
    					$("#valencyDate").val(result.data.valencyDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(result.data.valencyDate)));
    					$("#remarks").val(result.data.remarks);
    					$("#demo1").attr("src",result.data.imagez);//data.data.imagez
    					$("#imageFace_show").attr("src",result.data.imgUrl);
    					$("#registriesNo").val(result.data.registriesNo);
    					$("#roadFee").val(result.data.roadFee);
    					$("input[name=roadFeeType][value='"+result.data.roadFeeType+"']").attr("checked",true);
    		   		  }else{
    		   			parent.layer.alert("操作错误,请重试",function(){
    		   				parent.layer.closeAll();
    		   			});
    		   		  }
    		   	  });
    		   });
    });
	//搜索按钮点击事件
	$("#query").click(function(){
		doSearch();
	});
	//打印体格表
	$("#check_print").click(function(){
		var providerNo=$("#providerNo").val();
		if(providerNo.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		layer.open({
			type : 2,
			title:"打印体格表",
			maxmin : true,
			area : [ '100%', '100%' ],
			content : '/check/checkPrint?providerNo='+providerNo
		})
	});
	//修改手机号
	$("#updatephone").click(function(){
		var id=$("#id").val();
		if(id.length<1){
			layer.alert('请选择浆员');
			return false;
		}
		layer.open({
			type : 2,
			title:"修改手机号",
			maxmin : true,
			area : [ '33%', '25%' ],
			content : GetURLInfo()+'/updatephone?id='+id
		})
	});
	//添加老浆员路费
	$("#add_cost").click(function(){
		layer.open({
			type : 2,
			title:"以老带新添加老浆员路费",
			maxmin : true,
			area: ['650px', '400px'],
			content : '/providerRegistries/addCost'
		})
	});
	//手动通过
	$("#manual").click(function(){
		$("#result").val(0);
		$("#type").val(2);
	});

})////////////////
//搜索
function doSearch(){
	var registerDate = $("#startTime").val();
	if(registerDate==''){
		   parent.layer.alert("请选择登记日期");
		   return;
	}
	layui.table.reload('newtest', {page: {curr:1},where: {"registerDate": registerDate}});
}
function getToken() {
	return localStorage.getItem("token");
}
/*面部识别*/
! function() {
	//页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
	$('#face').on('click', function() {
		layer.ready(function() {
			identityValidate();
		});
	})
}();

//登记单击事件
function register(){
	var providerNo = $("#providerNo").val();
	if(providerNo==''){
		return	parent.layer.alert("请输入浆员卡号",function(){
			parent.layer.closeAll();
		});
	}
	if(providerNo.indexOf("R")!=-1){
		return	parent.layer.alert("无法登记临时浆员,请前往新浆员登记页面",function(){
			parent.layer.closeAll();
		});
	}
	var isIdentity = $("#result").val();
	var type = $("#type").val();
	submission("dengji","登记中.....");//锁住登记按钮,防止重复登记
	var data={"providerNo":providerNo,"isIdentity":isIdentity,"type":type};
	$.post("admin/verifyRegister",data,function(result){
		if(result.code==-1){//等于-1,
			insertRegistries(providerNo,isIdentity,type);
		}else if(result.code==4){//等于4,为拒绝信息
				layer.open({
					type: 2,
					title: '拒绝信息提示',
					maxmin: true,
					area: ['420px', '200px'],
					content: GetURLInfo()+'/immuneRegister?message='+result.message
				})
		}else{
			parent.layer.alert(result.message,function(){
				parent.layer.closeAll();
			});
			unlock("dengji","登记");//解锁按钮,恢复正常提交
		}
	});
}
/**
 * 登记流程方法
 * @returns
 */
function insertRegistries(providerNo,isIdentity,type){
	var registriesNo=$("#breakNumber").val();//断档号
	var  sceneImage= $("#imageFace").val();
	 var resutt={"providerNo":providerNo,"sceneImage":sceneImage,"isIdentity":isIdentity,"type":type,"roadFeeType":$("input[name='roadFeeType']:checked").val(),"roadFee":$("#roadFee").val(),"registriesNo":registriesNo};
		$.post("/providerRegistries/insertRegistries",resutt,function(result){
			parent.layer.alert(result.message,function(){
				parent.layer.closeAll();
				$("#dengji").removeAttr('disabled');
				$("#dengji").text("登记");
				if(result.code==-1){
					ticket(providerNo);
				}
			});
		})
}
//点击查询登记按钮
function calculation(){
	var providerNo = $.trim($("#providerNo").val());
	if(providerNo==""){
		return	parent.layer.alert("请输入浆员卡号",function(){
			parent.layer.closeAll();
		});
	}
	$.ajax({
		type : "POST",
		url :"/admin/readPlasmaProviderNo", 
		data:{
			"code":providerNo
		},
		datatype : "json",
		success : function(data) {
			if (null!=data.data) {
				$("#id").val(data.data.id);
				$("#name").val(data.data.name);
				$("#groupName").val(data.data.num);
				$("#bloodType").val(getBloodValue(data.data.bloodType));
				$("#providerNo").val(data.data.providerNo);
				$("#immuneName").val(data.data.immuneName==undefined?"普通":data.data.immuneName);
				$("#effectiveValue").val(data.data.effectiveValue);
				$("#icNumber").val(data.data.icNumber);
				$("#registriesNo").val(data.data.registriesNo);
				$("#sex").val(getSexValue(data.data.sex));
				$("#phone").val(data.data.phone);
				$("#year").val(data.data.year);
				$("#month").val(data.data.month);
				$("#immuneRegisterId").val(data.data.immuneRegisterId); 
				$("#collection").val(data.data.collectionDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(data.data.collectionDate)));
				$("#valencyDate").val(data.data.valencyDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(data.data.valencyDate)));
				$("#remarks").val(data.data.remarks);
				$("#demo1").attr("src",data.data.imagez);
				$("#demo3").attr("src",data.data.photo);
				$("#imageFace_show").attr("src","/img/new_pa1.png");
				$("#roadFee").val(data.data.roadFee);
			} else {
				parent.layer.alert(data.message,function(){
					parent.layer.closeAll();
				});
			}
		},
		error : function() {
			parent.layer.alert("操作错误,请重新操作",function(){
				parent.layer.closeAll();
			});
		}
	});
	
}
//单击打印小票、条码
function printing(){
	var providerNo = $("#providerNo").val();//浆员编号
	var name = $("#name").val();//浆员信息,进行浆员名字判断,可区别出操作员是否查询出浆员信息
	if(providerNo==""){
		 parent.layer.alert("请选择浆员进行打印",function(){
				parent.layer.closeAll();
			});
	};
	if(name==''){
		 parent.layer.alert("请查询浆员信息进行打印",function(){
				parent.layer.closeAll();
			});
	}else{
		var register = $("#startTime").val();
		var data={"providerNo":providerNo,"register":register};
		$.post("/common/ticket",data,function(result){
			if(result.code!=-1){
				parent.layer.alert('打印失败,请检查小票打印机是否在线',function(){
	   				parent.layer.closeAll();
	   			});
			}
		})
	}
}
//读取浆员卡号
function read(){
	//请求身份证接口
	$.ajax({
		type : "POST",
		url :GetURLInfo()+"/readPlasmaProviderNo", 
		datatype : "json",
		success : function(data) {
			if (data.code==-1) {
				$("#id").val(data.data.id);
				$("#name").val(data.data.name);
				$("#groupName").val(data.data.num);
				$("#bloodType").val(getBloodValue(data.data.bloodType));
				$("#providerNo").val(data.data.providerNo);
				$("#immuneName").val(data.data.immuneName==undefined?"普通":data.data.immuneName);
				$("#effectiveValue").val(data.data.effectiveValue);
				$("#icNumber").val(data.data.icNumber);
				$("#registriesNo").val(data.data.registriesNo);
				$("#sex").val(getSexValue(data.data.sex));
				$("#phone").val(data.data.phone);
				$("#year").val(data.data.year);
				$("#month").val(data.data.month);
				$("#immuneRegisterId").val(data.data.immuneRegisterId);
				$("#collection").val(data.data.collectionDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(data.data.collectionDate)));
				$("#valencyDate").val(data.data.valencyDate==undefined?"":dateFtt("yyyy-MM-dd",new Date(data.data.valencyDate)));
				$("#remarks").val(data.data.remarks);
				$("#demo1").attr("src",data.data.imagez);
				$("#demo3").attr("src",data.data.photo);
				$("#roadFee").val(data.data.roadFee);
			} else if(data.code==-4){
				parent.layer.alert(data.message,function(){
	   				parent.layer.closeAll();
	   			});
			}else if(data.code==-3){
				parent.layer.alert("读浆员卡失败,请调整浆员卡位置！",function(){
	   				parent.layer.closeAll();
	   			});
			}else if(data.code==1){
				parent.layer.alert('建立驱动连接失败,请确定驱动是否启动',function(){
	   				parent.layer.closeAll();
	   			});
			}
		},
		error : function() {
			parent.layer.alert("进入错误，请重试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
}
//打印小票的接口
function ticket(providerNo){
	var register = $("#startTime").val();
	var data={"providerNo":providerNo,"aboutDate":"","register":register};
	$.post("/common/ticket",data,function(result){
		if(result.code!=-1){
			$("#result").val(1);
			parent.layer.alert('打印失败,请检查小票打印机是否在线',function(){
   				parent.layer.closeAll();
   				layui.table.reload('newtest');
   			});
		}else{
			parent.layer.closeAll();
			location.reload();
		}
	});
}

//刷新按钮
function refresh(){
	location.reload();
}
//点击图片放大
$("#demo1").click(function(){
	loadIdCardImage($("#providerNo").val());
})
/*登记日期*/ 
initDate("#startTime");

/**
 * 验证完，自动提交数据
 * @returns
 */
function submit(){
	register();
}
