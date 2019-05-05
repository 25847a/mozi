//请求数据    

    var tableDate = new Array();
    var pageIndex=1;
    var pageSize=10;
    var imeiNum='';
  var tableList= new Vue({
        el: "#app",
        data() {
            return {
                item: [],
                total:''
            }
        },
        methods: {
            goback: function () {
            	var params = new URLSearchParams();
            	console.log("pageNum:"+pageIndex+"<<<<<pageSize"+pageSize);
           	 params.append('pageNum',pageIndex);
           	 params.append('pageSize',pageSize);
           	 params.append('imeiNum',imeiNum);
                axios.post(GetURLInfo()+"user/queryAdduserList",params).then(this.getnew);
            },
            getnew(res) {
            	if(res.data.code==-1){
            		tableDate=[];
            		for(var i=0;i<res.data.data.length;i++){
            			tableDate.push(res.data.data[i]);
                            }
            		this.item = tableDate;
            		this.total=res.data.total;
            	}
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
            	pageIndex=val;
            	this.goback();
                console.log(`当前页: ${val}`);
            }
        },
        mounted() {
            this.goback();
        }
    });
    //在线离线弹窗
   	$(".table-hover tbody").on("click", "#line button", function () {
        var equipmentId = $(this).parent('#line').parent('tr').find('td').eq(0).text();
        $.ajax({
            type : "POST",
            url :GetURLInfo()+"user/queryaddUserInfo",
            data:{"equipmentId":equipmentId},
            datatype : "json",
            success : function(result) {
            	if(null!=result.data){
            		var data =result.data;
            		document.getElementById("closeImei").innerHTML=data.imei;
            		document.getElementById("closeName").innerHTML=data.name;
            		document.getElementById("closeBluetooth").innerHTML=data.bluetoothmac;
            		$("#closeState").val(data.bluetooth_status);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
    }); 
   	//在线离线帮客户连接衣服。
   	function closeconfirm(){
   		console.log("在线离线帮客户连接衣服。");
   	}
   	//更改重点关爱对象
   	var love;
   	$(".table-hover tbody").on("click", "#love #ai", function () {
   	//	$(this).find("i").css("color","red");
        var imei = $(this).parent('#love').parent('tr').find('td').eq(1).text();
       $.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/updateLoveInfo",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		if(result.data.love==0){
            			love=0;
            			
            			//不重点关爱
            		}else if(result.data.love==1){
            			love=1;
            			//重点关爱
            		}
            		
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
       if(love==1){
    	   $(this).find("i").css("color","red");
       }else{
    	   $(this).find("i").css("color","gray");
       }
    });
   	function query(){
   		imeiNum = $("#imei").val();
   		tableList.goback();
   	}
   	//点击预警设置
	$(".table-hover tbody").on("click", "#love #early", function () {
		var equipmentId = $(this).parent('#love').parent('tr').find('td').eq(0).text();
	       $.ajax({
	            type : "POST",
	            async: false,	
	            url :GetURLInfo()+"user/queryPushInfo",
	            data:{"equipmentId":equipmentId},
	            datatype : "json",
	            success : function(result) {
	            	if(result.code==-1){
	            		document.getElementById("earlyImei").innerHTML=result.imei;
	            		document.getElementById("earlyName").innerHTML=result.name;
	            		$("#userId").val(result.userId);
	            		$("#alias").val(result.alias);
	            		$("#heartLowThd").val(result.heartLowThd);
	            		$("#heartHigThd").val(result.heartHigThd);
	            		$("#hbpstart").val(result.hbpstart);
	            		$("#hbpend").val(result.hbpend);
	            		$("#lbpstart").val(result.lbpstart);
	            		$("#lbpend").val(result.lbpend);//
	            		if(result.allNotifyOn==0){
	            			 $('#allNotifyOn').removeAttr('checked');
	            			 myBoolean=false;
	            			 $("[name='my-checkbox']").bootstrapSwitch('state',false);
	            		}else{
	            			
	            			  $('#allNotifyOn').attr('checked','checked');
	            			  myBoolean=true;
	            			 $("[name='my-checkbox']").bootstrapSwitch('state',true);
	            		}
	            		
	            	}
	            },
	            error : function() {
	            	 $.MsgBox.Alert("消息","操作失败");
	            }
	        });
	    }); 
	/**
	 * 预警确定键
	 * @returns
	 */
	function earlyQuery(){
		var data={};
		$("#divid input").each(function () {
			data[this.name]=this.value;
		});
		data['allNotifyOn']=$('#allNotifyOn').bootstrapSwitch('state')==true?1:0;
		data['userId']=$("#userId").val();
		data['alias']=$("#alias").val();
		$.ajax({
            type : "POST",
            url :GetURLInfo()+"user/updatePushInfo",
            data:data,
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		$.MsgBox.Alert("消息",result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 点击人工智能学习
	 */
	$(".table-hover tbody").on("click", "#love #study", function () {
		var imei = $(this).parent('#love').parent('tr').find('td').eq(1).text();
	       $.ajax({
	            type : "POST",
	            async: false,	
	            url :GetURLInfo()+"healthdao/queryHealthDaoInfo",
	            data:{"imei":imei},
	            datatype : "json",
	            success : function(result) {
	            	if(result.code==-1){
	            		document.getElementById("studyImei").innerHTML=result.imei;
	            		document.getElementById("studyName").innerHTML=result.name;
	            		document.getElementById("studyTime").innerHTML=result.createtime;
	            		$("#Heartrate").val(result.Heartrate);
	            		$("#microcirculation").val(result.microcirculation);
	            		$("#Bloodoxygen").val(result.Bloodoxygen);
	            		$("#HRV").val(result.HRV);
	            		$("#respirationrate").val(result.respirationrate);
	            		$("#sbp_ave").val(result.sbp_ave);
	            		$("#dbp_ave").val(result.dbp_ave);
	            		$("#daoId").val(result.id);
	            	}
	            },
	            error : function() {
	            	 $.MsgBox.Alert("消息","操作失败");
	            }
	        });
	    });
	//人工智能学习确定键
	function studyQuery(){
		var data={};
		$("#daodivd input").each(function () {
			data[this.name]=this.value;
		});
		data['phone']="mozistar"+$("#daoId").val();
		data['imei']=document.getElementById("studyImei").innerHTML;
		$.ajax({
            type : "POST",
            url :GetURLInfo()+"healthdao/updateHealthDaoInfo",
            data:data,
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 点击详情
	 */
	$(".table-hover tbody").on("click", "#love #details", function () {
		var equipmentId = $(this).parent('#love').parent('tr').find('td').eq(0).text();
	       $.ajax({
	            type : "POST",
	            async: false,	
	            url :GetURLInfo()+"user/queryUserEquipmentInfo",
	            data:{"equipmentId":equipmentId},
	            datatype : "json",
	            success : function(result) {
	            	if(result.code==-1){
	            		var data = result.data;
	            		document.getElementById("detailsName").innerHTML=data.name;
	            		$("#pic").attr("src",data.avatar);//data.data.imagez
	            		document.getElementById("detailsImei").innerHTML=data.imei;
	            		$("#detailsGender").val(data.gender);
	            		document.getElementById("detailsPhone").innerHTML=data.phone;
	            		document.getElementById("detailsLove").innerHTML=data.love==0?'否':'是';
	            		document.getElementById("detailsBluetoothmac").innerHTML=data.bluetoothmac;
	            		
	            		$("#detailsBorn").datepicker('setValue',data.born);
	            		$("#detailsLiveTime").datepicker('setValue',data.liveTime);
	            		
	            		document.getElementById("detailsHeight").innerHTML=data.height;
	            		document.getElementById("detailsWeight").innerHTML=data.weight;
	            		document.getElementById("detailsBed").innerHTML=data.bed;
	            	//	$("#detailsNurseName").val(data.nurseName);
	            		document.getElementById("detailsNurseName").innerHTML=data.nurseName;
	            		document.getElementById("detailsPhone1").innerHTML=data.phone1;
	            		document.getElementById("detailsPhone2").innerHTML=data.phone2;
	            		document.getElementById("detailsAddress").innerHTML=data.address;
	            		$("#illness").val(data.illness);
	            		console.log(result.list.length);
	            		if(result.list.length>0){
	            			document.getElementById("detailsTypeof").innerHTML = "";
	            			var divA = document.getElementById("detailsTypeof");
	            			for(var o in result.list){
	            				var str = "<div>" + (parseInt(o)+1) +"."+result.list[o].name+"&emsp;"+result.list[o].phone+ "</div>";
	            				divA.innerHTML = divA.innerText+str;
	            			}
	            		}
	            		
	            		
	            		
	            	}
	            },
	            error : function() {
	            	 $.MsgBox.Alert("消息","操作失败");
	            }
	        });
	    });
	/**
	 * 输入手机号码添加观察者
	 * @returns
	 */
	function follow(){
		var account=$("#textId").val();
		var imei=document.getElementById("detailsImei").innerHTML;
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"userEq/addFollowInfo",
            data:{"account":account,"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		alert(result.message);
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 个人详情确定键
	 * @returns
	 */
	function detailsDetermine(){
		var gender = $("#detailsGender").val();
		var imei=document.getElementById("detailsImei").innerHTML;
		var born = $("#detailsBorn").val();
		var liveTime = $("#detailsLiveTime").val();
		if(liveTime=="" || born==""){
			alert("出生日期或者入住时间不能为空");
			return;
		}
		var illness = $("#illness").val();
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/updateUserInfo",
            data:{"imei":imei,"gender":gender,"born":born,"liveTime":liveTime,"illness":illness},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		alert(result.message);
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 清空添加按钮
	 * @returns
	 */
	function addUser(){
		$("#addImei").val("");	
		$("#addTable tbody tr td").html("");
	}
	/**
	 * 添加用户查询
	 * @returns
	 */
	function addQuery(){
		var imei = $("#addImei").val();
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/queryImeiUserInfo",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		var data = result.data;
            		document.getElementById("addImeiA").innerHTML=data.imei;
            		document.getElementById("addName").innerHTML=data.name;
            		document.getElementById("addGender").innerHTML=data.gender;
            		document.getElementById("addBorn").innerHTML=data.born;
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 添加确定键
	 * @returns
	 */
	function AddUserDetermine(){
		var imei = document.getElementById("addImeiA").innerHTML;
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/AddUserDetermine",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		alert(result.message);
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	};
	/**
	 * 移除查询
	 * @returns
	 */
	function deleteQuery(){
		var imei = $("#deleteImei").val();
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/queryImeiUserInfo",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		var data = result.data;
            		document.getElementById("deleteImeiA").innerHTML=data.imei;
            		document.getElementById("deleteName").innerHTML=data.name;
            		document.getElementById("deleteGender").innerHTML=data.gender;
            		document.getElementById("deleteBorn").innerHTML=data.born;
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	}
	/**
	 * 清空移除按钮
	 * @returns
	 */
	function deleteUser(){
		$("#deleteImei").val("");	
		$("#deleteTable tbody tr td").html("");
	};
	/**
	 * 移除确定键按钮
	 * @returns
	 */
	function deleteUserDetermine(){
		var imei = document.getElementById("deleteImeiA").innerHTML;
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/deleteUserDetermine",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		alert(result.message);
            		location.reload();
            	}else{
            		alert(result.message);
            	}
            },
            error : function() {
            	 $.MsgBox.Alert("消息","操作失败");
            }
        });
	}
	
/*
function status(){
	 $( ".table-hover tr" ).click( function() {//给每行绑定了一个点击事件
	        var td = $( this ).find( "td" );//this指向了当前点击的行，通过find我们获得了该行所有的td对象
	        //题中说到某个td，为了演示所以我们假设是要获得第3个td的数据
	        console.log(td);
	    } );
	$("#aaa").html(1525555555);
};*/



/*//<!-- 人工智能 --> 
var one = true;
    $("#study").click(function () {

        if (one) {
            $("#tick").css("color", "#3277c1")
            $("#study").html("重新学习")
            one = false;
        } else {
            $("#tick").css("color", "#c2c2c2")
            $("#study").html("开始学习")
            one = true;
        }
    });
 //   <!-- 详细弹窗验证手机号 -->
    $(this).keydown(function (e) {
        var key = window.event ? e.keyCode : e.which;
        if (key.toString() == "13") {
            return false;
        }
    });

    $("#textId").keyup(function () {
        $(this).val($(this).val().replace(/[^0-9]/g, ''));
    }).bind("paste", function () {
        $(this).val($(this).val().replace(/[^0-9]/g, ''));
    })
    $("#invite-number").click(function () {
        // console.log($("#textId").val())
        var phone = $("#textId").val()
        if (phone == '') {
            alert("手机号码不能为空！");
        } else if (phone && /^1[3|4|5|8]\d{9}$/.test(phone)) {
            //对的            
            $('#invite').modal('hide');
            $('#textId').val('');
            checkPhoneIsExist();
        } else {
            //不对
            alert("请输入正确的手机号")
        }
    });
    //验证手机号是否存在
    function checkPhoneIsExist() {
        var phone = jQuery("#textId").val();
        var flag = true;
        jQuery.ajax(
            {
                url: "url" + (new Date()).getTime(),
                data: { phone: phone },
                dataType: "json",
                type: "GET",
                async: false,
                success: function (data) {
                    var status = data.status;
                    if (status == "0") {
                    }
                }
            });
    };
//<!-- 详细弹窗上传头像 -->    
    $(function () {
        $("#pic").click(function () {
            $("#upload").click(); //隐藏了input:file样式后，点击头像就可以本地上传
            $("#upload").on("change", function () {
                var objUrl = getObjectURL(this.files[0]); //获取图片的路径，该路径不是图片在本地的路径
                if (objUrl) {
                    $("#pic").attr("src", objUrl); //将图片路径存入src中，显示出图片
                    upimg();
                }
            });
        });
    }); 
    //建立一?可存取到?file的url   
  //建立一?可存取到?file的url
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
    //上传头像到服务器
    function upimg() {
        console.log(344)
        var pic = $('#upload')[0].files[0];
        var file = new FormData();
        file.append('image', pic);
        $.ajax({
            url: "",
            type: "post",
            data: file,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(data);
                var res = data;
                //$("").append("<img src='/" + res + "'>")
            }
        });
    }

    $("#detailed-btn").click(function () {
        $('#detailed').modal('hide');

    });*/


   