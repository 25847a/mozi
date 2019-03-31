$(function(){
	// 查询小组名称
	$.ajax({
		type : "POST",
		url : "/group/queryGroupInfo",
		datatype : "json",
		success : function(result) {
			var param=[];
				for(var i=0;i<result.data.length;i++){
					param[i]=result.data[i].num.toString();
				}
			var autoComplete = new AutoComplete("group","auto",param);
			document.getElementById("group").onkeyup=function(event){
				autoComplete.start(event);
			}
		},
		error : function() {
			parent.layer.alert("请稍后试试",function(){
   				parent.layer.closeAll();
   			});
		}
	});
    $.ajax({
        url : "/nation/queryNationByPlasmType",
        type : "POST",
        dataType:"json",
        success:function(result){
            for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#nationId").append(str);
            }
        }
    });
    var cols= [[ //表头
        {field: 'id',title: '编号',display : 'none',minWidth : '0',width : '0',type : "space",align:'center', fixed: 'left'}
        , {field: 'providerNo', title: '献浆员卡号',align:'center' }
        ,{field: 'registriesNo', title: '登记号', sort: true,align:'center'}
        ,{field: 'name', title: '姓名', align:'center'}
        ,{field: 'idNo', title: '身份证', align:'center'}
        ,{field: 'sex',title: '性别',align: 'center',templet: function (d){ // 单元格格式化函数
            var text = '-';
            if (d.sex == 0) {
                text = "男";
            } else if (d.sex == 1) {
                text = "女";
            }
            return text;
        }
        }
        ,{field: 'zname', title: '登记人',align:'center'}
        , {fixed: 'right' , title: '操作', align: 'center', toolbar: '#barDemoS'}
    ]];
    dataAllAndWH("newBaseinfo",cols,{"startTime":$("#startTime").val()},GetURLInfo() + 'queryRegistriesList',GetURLInfo() +'queryNewRegistrationDetails',GetURLInfo() +'deleteBaseInfo',['750px','400px'],function(){
        var tab = $("#newBaseinfo").next().find('.layui-table tbody tr');
        tab.click(function(event) {
            var tr = $(event.target).closest("tr")[0];
            var providerNo = $(tr).find("td").eq(1).find("div").html();
            $("#providerNo").val(providerNo);
            queryPlasmaDetail(providerNo);
        })
    });
    //面部验证
    $("#newfacial").click(function () {
    	$("#result").val(null);//如果打开了人脸识别 必须要做人脸识别
        var img=$("#demo1")[0].src;
        if(img==null||img.indexOf("/img/new_pa1.png")>0){
            layui.use('layer', function() {
                layer.alert("请上传身份证件");
            })
        }else {
            identityValidate();
        }
    })
    //条件筛选
    $("#search").click(function(){
        var startTime=$("#startTime").val();
        layui.table.reload('newBaseinfo', {where: {"startTime":startTime}});
    })
    //添加登记信息
    $("#saveBaseInfo").click(function (){
        var form=document.forms[0];
        var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
        var base64Codes=$("#play").attr('src');
        var demo1=$("#demo1").attr('src');
        var demo2=$("#demo2").attr('src');
        var demo3=$("#demo3").attr('src');//身份证图像
        //js判断非空
      var bol = validateParam(base64Codes,demo1,demo2,demo3);
       if(bol) {
          var reg = /^\s*data:([a-z]+\/[a-z0-9-+.]+(;[a-z-]+=[a-z0-9-]+)?)?(;base64)?,([a-z0-9!$&',()*+;=\-._~:@\/?%\s]*?)\s*$/i;
           if(reg.test(demo1)){
               formData.append("file", convertBase64UrlToBlob(demo1));
           }
           if(reg.test(demo2)){
               formData.append("file", convertBase64UrlToBlob(demo2));
           }
           if(reg.test(demo3)){
               formData.append("file", convertBase64UrlToBlob(demo3));
           }
           formData.append("file", convertBase64UrlToBlob(base64Codes));
           //ajax 提交form
           $.ajax({
               url: GetURLInfo() + "/insertBaseInfo",
               type: "POST",
               data: formData,
               dataType: "json",
               async:false,
               processData: false,         // 告诉jQuery不要去处理发送的数据
               contentType: false,        // 告诉jQuery不要去设置Content-Type请求头
               success: function (result) {
                   if (result.code == -1) {
                       parent.layer.alert(result.message,function(){
                           parent.layer.closeAll();
                           $("#providerNo").val(result.data.providerNo);
                           //$("#print").click();
                           window.location.reload();
                       });
                   }else{
                       parent.layer.alert(result.message);
                   }
               }
           });
       }
    })
   
    
    //打印
    $("#print").click(function(){
    	var providerNo = $("#providerNo").val();//浆员编号
    	if(providerNo==""){
    		parent.layer.alert("请选择浆员进行打印");
    		return false;
    	}
    	var register = $("#startTime").val();//这是查询日期,需要查询日期算出预约时间(啊健添加)
    	var data={"providerNo":providerNo,"register":register};
    	$.post("/common/ticket",data,function(result){
    		if(result.code!=-1){
    			parent.layer.alert(result.message);
    		}
    	})
    });
    
  //打印体格表（正面）
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
	
	//打印体格表（反面）
	$("#check_print_f").click(function(){
		layer.open({
			type : 2,
			title:"打印体格表",
			maxmin : true,
			area : [ '100%', '100%' ],
			content : '/check/checkPrintF?providerNo='+providerNo
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
});

//验证
function validateParam(base64,demo1,demo2,demo3){
    var name=$("#name").val();
    var age=$("#age").val();
    var idNo=$("#idNo").val();
    var nation=$("#nation").val();
    var sex=$("#sex").val();
    var birthday=$("#birthday").val();
    var place=$("#place").val();
    var validDate=$("#validDate").val();
    var file =$("#file").val();
  /*  var demo1=$("#demo1").attr("src");*/
    var file1 =$("#file1").val();
    /*var demo2 =$("#demo2").attr("src");*/
    var inviteType=$("#inviteType").val();
    var inviteId=$("#inviteId").val();
    var file2=$("#file2").val();
    var groupId = $("#group").val();
    var isMarry = $("#isMarry").val();
    var vocation = $("#vocation").val();
    var phone = $("#phone").val();
    if(!(/^1[34578]\d{9}$/.test(phone)) && !(/([0-9]{4}-)?[0-9]{7}/.test(phone))){ 
    	parent.layer.alert("请重新输入11位手机号码或者(区号-电话号码)");  
        return false; 
    } 
    if(name==''||age==''||idNo==''||nation==''||sex==''||birthday==''||place==''||validDate==''){
        parent.layer.alert("请先读取身份信息");
        return false;
    }else if((base64==''||base64=='/img/new_pa2.png')&&file2==''){
        parent.layer.alert("请先拍照");
        return false;
    }else if(file==''&&(demo1==''||"/img/new_pa1.png"==demo1)){
        parent.layer.alert("请上传证件照");
        return false;
    }else if(file1==''&&(demo2==''||'/img/new_pa1.png'==demo2)){
        parent.layer.alert("请上传反面证件照");
        return false;
    }else if(demo3==''){
    	parent.layer.alert("身份证图像提取失败，请稍后重试");
        return false;
    }else if(inviteType!=''&&inviteId==''){
        parent.layer.alert("邀请人号不允许为空");
        return false;
    }else if(inviteId!=''&&inviteType==''){
        parent.layer.alert("请选择邀请人类型");
        return false;
    }else if(groupId==''){
    	parent.layer.alert("请配置组号信息或者输入组号");
        return false;
    }else if(isMarry==''){
    	parent.layer.alert("请选择婚否");
        return false;
    }else if(vocation==''){
    	parent.layer.alert("请选择职业");
    	return false;
    }else{
    	return true;
    }
}
//加载初始化日历控件
initDates();
//读取身份证信息  正面
function func(data){
    $("#name").val(data.name);
    $("#age").val(getAge(data.idCardNo));
    $("#idNo").val(data.idCardNo);
    var val='';
    $("#nationId option").each(function () {
        var txt = $(this).text(); //获取单个text
        if(data.nation==txt){
            val = $(this).val(); //获取单个value
        }
    })
    if(val!=''){
        $("#nationId").val(val);
        $("#nation").val(val);
    }else{
        layer.alert("请先添加《"+data.nation+"》民族");
    }
    $("#sexTemp").val(data.sex);
    if(data.sex=='男'){
        $("#sex").val(0);
    }else{
        $("#sex").val(1);
    }
    var birthday = data.born;
    birthday=birthday.substring(0,4)+"-"+birthday.substring(4,6)+"-"+birthday.substring(6,birthday.length);
    $("#birthday").val(birthday);
    
    $("#roadFee").val(data.roadFee);
    $("#place").val(data.native);
    $("#addressx").val(data.address);
    var userLifeEnd = data.userLifeEnd;
    userLifeEnd=userLifeEnd.substring(0,4)+"-"+userLifeEnd.substring(4,6)+"-"+userLifeEnd.substring(6,userLifeEnd.length);
    $("#validDate").val(userLifeEnd);
    //验证浆员是否合法
    $.ajax({
        url: GetURLInfo() + "/queryPlasmaLegal",
        type: "POST",
        data: {"birthday":data.born,"address":data.address,"idNo":$("#idNo").val(),"validDate":data.userLifeEnd},
        success: function (result) {
            if (result.code!= -1) {
                parent.layer.alert(result.message);
            }
        }
    });
}
function funcf(data){//反面
    var userLifeEnd = data.userLifeEnd;
    userLifeEnd=userLifeEnd.substring(0,4)+"-"+userLifeEnd.substring(4,6)+"-"+userLifeEnd.substring(6,userLifeEnd.length);
    $("#validDate").val(userLifeEnd);
    //验证浆员是否合法
    $.ajax({
        url: GetURLInfo() + "/queryPlasmaLegal",
        type: "POST",
        data: {"birthday":data.born,"address":data.address,"idNo":data.idCardNo,"validDate":data.userLifeEnd},
        success: function (result) {
            if (result.code!= -1) {
                parent.layer.alert(result.message);
            }
        }
    });
}

//查询浆员详情
function queryPlasmaDetail(providerNo){
    $.ajax({
        url: GetURLInfo() + "/queryProviderBaseInfoDetails",
        type: "POST",
        data: {"providerNo":providerNo,"startTime":$("#startTime").val(),"inviteType":$("#inviteType").val()},
        success: function (result) {
            if (result.code== -1 && null!=result.data) {
                $("#name").val(result.data.name);
                $("#age").val(getAge(result.data.idNo));
                $("#idNo").val(result.data.idNo);
                $("#nationId").val(result.data.nation);
                $("#nation").val(result.data.nation);
                $("#sex").val(result.data.sex)
                if(result.data.sex==0){
                    $("#sexTemp").val("男");
                }else{
                    $("#sexTemp").val("女");
                }
                $("#birthday").val(dateFtt('yyyy-MM-dd',new Date(result.data.birthday)));
               
                $("#place").val(result.data.place);
                $("#validDate").val(dateFtt('yyyy-MM-dd',new Date(result.data.validDate)));
                $("#addressx").val(result.data.addressx);
                $("#group").val(result.data.num);
                $("#isMarry").val(result.data.vocation);
                $("#vocation").val(result.data.isMarry);
                $("#history").val(result.data.history);
                $("#phone").val(result.data.phone);
                $("#remarks").val(result.data.remarks);
                $("#inviteType").val(result.data.inviteType);
                $("#inviteId").val(result.data.iproviderNo);
                $("#demo1").attr("src",result.data.imagez);
                $("#demo2").attr("src",result.data.imagef);
                $("#play").attr("src",result.data.imgUrl);
                $("#saveBaseInfo").attr('onclick','no("此人不能登记")');
    			$("#roadFee").val(result.data.roadFee);
    			$("input[name=roadFeeType][value='"+result.data.roadFeeType+"']").attr("checked",true);
            }
        }
    });
}
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
$("input[name='roadFee']").keyup(function(){
    var tmptxt=$(this).val();
    $(this).val(tmptxt.replace(/\D|^/g,''));
}).bind("paste",function(){
    var tmptxt=$(this).val();
    $(this).val(tmptxt.replace(/\D|^/g,''));
}).css("ime-mode", "disabled");


