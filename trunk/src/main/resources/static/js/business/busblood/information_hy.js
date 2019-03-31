$(function() {
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
        {field : 'id',title : 'id', display : 'none',minWidth : '0',width : '0',type : "space"}
        ,{field: 'providerNo', title: '献浆员卡号',align:'center' }
        ,{field: 'name', title: '姓名',align:'center'}
        ,{field: 'idNo', title: '身份证',align:'center'}
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
        ,{field: 'zname', title: '建档人',align:'center'}
    ]];
    dataAllAndWH("infortest",cols,null,GetURLInfo() + 'queryProviderBaseInfoList',GetURLInfo() +'queryNewRegistrationDetails',null,['750px','400px'],function(){
        var tab = $("#infortest").next().find('.layui-table tbody tr');
        tab.click(function(event) {
            var tr = $(event.target).closest("tr")[0];
            var id = $(tr).find("td").eq(0).find("div").html();
            $("#id").val(id);
            var providerNo = $(tr).find("td").eq(1).find("div").html();
            $("#providerNo").val(providerNo);
            loadPlasmaInfo();
        })
    });
//条件筛选
    $("#search").click(function(){
        var name=$("#seaName").val();
        var idNo=$("#seaIdNo").val();
        var providerNo=$("#seaProviderNo").val();
        layui.table.reload('infortest',{page: {curr:1}, where: {"name":name,"idNo":idNo,"providerNo":providerNo}});
    })
    //面部验证
    $("#newfacial").click(function () {
        var img=$("#demo1")[0].src;
        if(img==null||img.indexOf("/img/new_pa1.png")>0){
            layui.use('layer', function() {
                layer.alert("请上传身份证件");
            })
        }else {
            identityValidate();
        }
    })
    //修改浆员信息
    $("#updateBaseInfo").click(function (){
    	var providerNo = $("#providerNo").val();
    	if(providerNo.indexOf("f")!=-1){
    		layer.alert("临时浆员不能修改信息");
    		return false;
    	}
        var form=document.forms[0];
        var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
        var base64Codes=$("#play").attr('src');
        //js判断非空
        var bol = validateParam(base64Codes,formData);
        if(bol) {
            if(validDataUrl(base64Codes)){
                formData.append("file", convertBase64UrlToBlob(base64Codes));
            }
            //ajax 提交form
            $.ajax({
                url: GetURLInfo() + "/updateProviderBaseInfo",
                type: "POST",
                data: formData,
                dataType: "json",
                processData: false,         // 告诉jQuery不要去处理发送的数据
                contentType: false,        // 告诉jQuery不要去设置Content-Type请求头
                success: function (result) {
                    if (result.code == -1) {
                        parent.layer.alert(result.message,function(){
                            parent.layer.closeAll();
                        });
                    }else{
                        parent.layer.alert(result.message);
                    }
                }
            });
        }
    })
    
    //提交卫计委审核
    $("#submit_wei").click(function(){
    	var providerNo=$("#providerNo").val();
    	if(providerNo.length<1){
    		parent.layer.alert('请选择浆员');
    	}else{
    		$.ajax({
				url:"/card/submitPlasmaUpdate",
				dataType:"json",
				type:"post",
				async: false,
				data:{'providerNo':providerNo},
				success:function(result){
					parent.layer.alert(result.message);
				}
    		})
    	}
    });
    
    //卫计委审核通过后，更新浆员信息
    $("#update_plalsma").click(function(){
    	var providerNo=$("#providerNo").val();
    	if(providerNo.length<1){
    		parent.layer.alert('请选择浆员');
    	}else{
    		$.ajax({
				url:"/providerBaseinfo/updatePlasmaInfo",
				dataType:"json",
				type:"post",
				async: false,
				data:{'providerNo':providerNo},
				success:function(result){
					parent.layer.alert(result.message);
				}
    		})
    	}
    });
});

//验证
function validateParam(base64){
    var name=$("#name").val();
    var idNo=$("#idNo").val();
    var nation=$("#nationId").val();
    var sex=$("#sex").val();
    var birthday=$("#birthday").val();
    var bloodType=$("#bloodType").val();
    var place=$("#place").val();
    var validDate=$("#validDate").val();
   var type=$("#type").val();
   var plasmaState=$("#plasmaState").val();
   var createDate=$("#createDate").val();
    var inviteType=$("#inviteType").val();
    var inviteId=$("#inviteId").val();
    var id=$("#id").val();
    var demo1=$("#demo1").attr("src");
    var play=$("#play").attr("src");
    if(id==''){
        parent.layer.alert("请选择修改的浆员");
        return false;
    }if(name==''){
        parent.layer.alert("姓名不允许为空");
        return false;
    }else if(idNo==''){
        parent.layer.alert("身份证号码不允许为空");
        return false;
    }else if(nation==''){
        parent.layer.alert("民族不允许为空");
        return false;
    }else if(sex==''){
        parent.layer.alert("性别不允许为空");
        return false;
    }else if(birthday==''){
        parent.layer.alert("出生日期不允许为空");
        return false;
    }else if(bloodType==''){
        parent.layer.alert("血型不允许为空");
        return false;
    }else if(place==''){
        parent.layer.alert("籍贯不允许为空");
        return false;
    }else if(validDate==''){
        parent.layer.alert("身份证到期时间不允许为空");
        return false;
    }else if(type==''){
        parent.layer.alert("浆员类型不允许为空");
        return false;
    }else if(plasmaState==''){
        parent.layer.alert("浆员状态不允许为空");
        return false;
    }else if(createDate==''){
        parent.layer.alert("建档不允许为空");
        return false;
    }else if(validDataUrl(play)&&base64==''){//现场照片
        parent.layer.alert("请先拍照");
        return false;
    }else if(validDataUrl(demo1)&&file==''){//validDataUrl(demo1)=true 修改了 身份证件照 才判断图片不允许为空
        parent.layer.alert("请上传证件照");
        return false;
    }else if(file1==''){//validDataUrl(demo1)=true 修改了 身份证件照 才判断图片不允许为空
        parent.layer.alert("请上传反面证件照");
        return false;
    }else if(inviteType!=''&&inviteId==''){
        parent.layer.alert("邀请人号不允许为空");
        return false;
    }else if(inviteId!=''&&inviteType==''){
        parent.layer.alert("请选择邀请人类型");
        return false;
    }else{
        return true;
    }
}
