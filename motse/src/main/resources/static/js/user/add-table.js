//限制选择未来时间
futureDate("addBorn");
futureDate("addLiveTime");
futureDate("detailsBorn");
//回车键查询
$(document).keydown(function(e) {  
  if (e.keyCode == 13) { 
	  query();
      }  
 });  
		/**
		 * 获取床号列表
		 */
		$.ajax({
		    type : "POST",
		    url :GetURLInfo()+"bedNumber/queryBedNumberInfo",
		    datatype : "json",
		    success : function(result) {
		    	if(null!=result.data){
		    		for (var o in result.data){
		                var str = "<option value=" + result.data[o].id + ">" + result.data[o].bed + "</option>";
		          	      $("#addBed").append(str);
		                $("#detailsBed").append(str);
		            }
		    	}
		    },
		    error : function() {
		    }
		});
		/**
		 * 获取护士列表
		 */
		$.ajax({
		    type : "POST",
		    url :GetURLInfo()+"/nurse/queryNurseInfo",
		    datatype : "json",
		    success : function(result) {
		    	if(null!=result.data){
		    		for (var o in result.data){
		                var str = "<option value=" + result.data[o].id + ">" + result.data[o].nurseName + "</option>";
		                $("#addNurse").append(str);
		                $("#detailsNurseName").append(str);
		            }
		    	}
		    },
		    error : function() {
		    }
		});
    var tableDate = new Array();
    var pageIndex=1;
    var pageSize=10;
    var imeiNum='';
    var userName='';
  var tableList= new Vue({
        el: "#app",
        data() {
            return {
            	 options: [{
                     value: '男',
                     label: '男'
                     }, {
                     value: '女',
                     label: '女'
                     }],
                     value: '',
            	item: [],
                total:''
            }
        },
        methods: {
            goback: function () {
            	var params = new URLSearchParams();
            ///	console.log("pageNum:"+pageIndex+"<<<<<pageSize"+pageSize);
           	 params.append('pageNum',pageIndex);
           	 params.append('pageSize',pageSize);
           	 params.append('imeiNum',imeiNum);
           	params.append('userName',userName);
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
            	pageSize=val;
            	this.goback();
               console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
            	pageIndex=val;
            	this.goback();
               console.log(`当前页: ${val}`);
            },
            	promptBox(){
            		this.$confirm('此操作删除后无法恢复, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(() => {
                    	var imei = document.getElementById("deleteImeiA").innerHTML;
            	    	$.ajax({
            	            type : "POST",
            	            url :GetURLInfo()+"user/deleteUserDetermine",
            	            data:{"imei":imei},
            	            datatype : "json",
            	            success : function(result) {
            	            	if(result.code==-1){
            	            		tips(result.message);
            	            		location.reload();
            	            	}else{
            	            		tips(result.message);
            	            	}
            	            },
            	            error : function() {
            	            	tips("操作失败");
            	            }
            	        });
             //           this.slist.splice(index, 1);
                    }).catch(() => {
                    	tips("已取消删除")
                    });
            	}
        },
        mounted() {
            this.goback();
        }
        
    });
    //在线离线弹窗
   	$(".table-hover tbody").on("click", "#line button", function () {
   	//	$("tr[id=clothesNumber2]").remove();
        var equipmentId = $(this).parent('#line').parent('tr').find('td').eq(1).text();
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
            		$("#closeBluetooth1").val(data.closeBluetooth1);
            		$("#closeBluetooth2").val(data.closeBluetooth2);
            		$("#bluetoothmac").val(data.bluetoothmac);
            		if(data.bluetoothmac==data.closeBluetooth1){
            			$("#clothes1").text("已绑定");
            			$("#clothes2").text("点击绑定");
            		}else if(data.bluetoothmac==data.closeBluetooth2){
            			$("#clothes1").text("点击绑定");
            			$("#clothes2").text("已绑定");
            		}else{
            			$("#clothes1").text("点击绑定");
            			$("#clothes2").text("点击绑定");
            		}
            		$("#disconnect1").text("断开衣服");
            		$("#disconnect2").text("断开衣服");
            		$('#closeBluetooth1').attr("disabled",true);
            		$('#closeBluetooth2').attr("disabled",true);
            //		document.getElementById("closeBluetooth").innerHTML=data.bluetoothmac;
            		$("#closeState").text(data.bluetooth_type==0?"离线":"在线");//data.bluetooth_type
            		$("#equipmentId").val(data.id);
            	}
            },
            error : function() {
            	tips("操作失败");
            }
        });
    });
   	//在线离线帮客户连接第一件衣服;
   	function clothesNumber1(){
   		$("#clothes1").text("绑定中..");
		var mac =$("#closeBluetooth1").val();
		if(mac=='' || mac ==null){
			tips("无衣服名称,请输入衣服名称连接");
			return;
			if($("#closeBluetooth2").val()==$("#bluetoothmac").val()){
				$("#clothes2").text("已绑定");
			}else{
				$("#clothes2").text("点击绑定");
			}
		}
		var id=$("#equipmentId").val();
		var bluetoothName=mac;
		var bluetoothmac=mac;
		$.ajax({
            type : "POST",
         //   async: false,
            url :GetURLInfo()+"equipment/updateBluetooth",
            data:{"id":id,"bluetoothName":bluetoothName,"bluetoothmac":bluetoothmac},
            datatype : "json",
            success : function(result) {
            	if(result.code==601){
            		$("#clothes1").text("点击绑定");
            	}else if(result.code==200){
            		$("#clothes1").text("已绑定");
            		$("#closeState").text("在线");
            	}else if(result.code==350){
            		$("#clothes1").text("点击绑定");
            	}
            	tips(result.message);
            		
            },
            error : function() {
            	tips("操作失败");
            }
        });
   	};
  //在线离线帮客户断开第一件衣服;
   	function disconnectClothes1(){
   		$("#disconnect1").text("断开中..");
		var id=$("#equipmentId").val();
		var bluetoothName="000000000000";
		var bluetoothmac="000000000000";	
		var mac =$("#closeBluetooth1").val();
		if(mac=='' || mac ==null){
			tips("无衣服名称,无需断开衣服");
			$("#disconnect1").text("断开衣服");
			return;
		}
		$.ajax({
            type : "POST",
         //   async: false,
            url :GetURLInfo()+"equipment/updateBluetooth",
            data:{"id":id,"bluetoothName":bluetoothName,"bluetoothmac":bluetoothmac},
            datatype : "json",
            success : function(result) {
            	if(result.code==601){
            		$("#disconnect1").text("断开失败");
            	}else if(result.code==200){
            		$("#disconnect1").text("断开成功");
            		$("#clothes1").text("点击绑定");
            		$("#closeState").text("离线");
            	}else if(result.code==350){
            		$("#disconnect1").text("断开失败");
            	}
            	tips(result.message);
            		
            },
            error : function() {
            	tips("操作失败");
            }
        });
   	};
   	//在线离线帮客户连接第二件衣服。
   	function clothesNumber2(){
   		$("#clothes2").text("绑定中..");
		var mac =$("#closeBluetooth2").val();
		if(mac=='' || mac ==null){
			tips("无衣服名称,请输入衣服名称连接");
			if($("#closeBluetooth2").val()==$("#bluetoothmac").val()){
				$("#clothes2").text("已绑定");
			}else{
				$("#clothes2").text("点击绑定");
			}
			return;
		}
		var id=$("#equipmentId").val();
		var bluetoothName=mac;
		var bluetoothmac=mac;
		$.ajax({
            type : "POST",
         //   async: false,
            url :GetURLInfo()+"equipment/updateBluetooth",
            data:{"id":id,"bluetoothName":bluetoothName,"bluetoothmac":bluetoothmac},
            datatype : "json",
            success : function(result) {
            	if(result.code==601){
            		$("#clothes2").text("点击绑定");
            	}else if(result.code==200){
            		$("#clothes2").text("已绑定");
            		$("#closeState").text("在线");
            	}
            	tips(result.message);
            		
            },
            error : function() {
            	tips("操作失败");
            }
        });
   	};
  //在线离线帮客户断开第二件衣服;
   	function disconnectClothes2(){
   		$("#disconnect2").text("断开中..");
		var id=$("#equipmentId").val();
		var bluetoothName="000000000000";
		var bluetoothmac="000000000000";	
		var mac =$("#closeBluetooth2").val();
		if(mac=='' || mac ==null){
			tips("无衣服名称,无需断开衣服");
			$("#disconnect2").text("断开衣服");
			return;
		}
		$.ajax({
            type : "POST",
         //   async: false,
            url :GetURLInfo()+"equipment/updateBluetooth",
            data:{"id":id,"bluetoothName":bluetoothName,"bluetoothmac":bluetoothmac},
            datatype : "json",
            success : function(result) {
            	if(result.code==601){
            		$("#disconnect2").text("断开失败");
            	}else if(result.code==200){
            		$("#disconnect2").text("断开成功");
            		$("#clothes2").text("点击绑定");
            		$("#closeState").text("离线");
            	}
            	tips(result.message);
            		
            },
            error : function() {
            	tips("操作失败");
            }
        });
   	};
   	//在线离线编辑1
   	function edit1(){
   		$('#closeBluetooth1').attr("disabled",false);
   	};
  //在线离线编辑2
   	function edit2(){
   		$('#closeBluetooth2').attr("disabled",false);
   	};
   	//在线离线确定键
   	function closeconfirm(){location.reload();}
   	//学习按钮
   	function startLearning(){
   		$("#learning").text('正在学习中....'); // 只支持修改文本
   		var imei =document.getElementById("studyImei").innerHTML;
   		$.ajax({
            type : "POST",
          //  async: false,	
            url :GetURLInfo()+"equipment/startLearning",
            data:{"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		var data = result.data;
            		$("#Heartrate").val(data.Heartrate);
            		$("#microcirculation").val(data.microcirculation);
            		$("#Bloodoxygen").val(data.Bloodoxygen);
            		$("#HRV").val(data.HRV);
            		$("#respirationrate").val(data.respirationrate);
            		$("#sbp_ave").val(data.sbp_ave);
            		$("#dbp_ave").val(data.dbp_ave);
            		$("#tick").css("color", "#3277c1");
                	
            	}else{
            		tips(result.message);
            	}
            	$("#learning").text('重新学习'); // 只支持修改文本
            },
            error : function() {
            	tips("操作失败");
            }
        });
   	}
   	//更改重点关爱对象
   	var love;
   	$(".table-hover tbody").on("click", "#love #ai", function () {
        var imei = $(this).parent('#love').parent('tr').find('td').eq(2).text();
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
            	tips("操作失败");
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
   		userName = $("#userName").val();
   		tableList.goback();
   	}
   	//点击预警设置
	$(".table-hover tbody").on("click", "#love #early", function () {
		var equipmentId = $(this).parent('#love').parent('tr').find('td').eq(1).text();
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
	            	tips("操作失败");
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
            		tips(result.message);
            		location.reload();
            	}else{
            		tips(result.message);
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
		var imei = $(this).parent('#love').parent('tr').find('td').eq(2).text();
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
	            		$("#learning").text('开始学习'); // 只支持修改文本
	            	}
	            },
	            error : function() {
	            	tips("操作失败");
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
            		tips(result.message);
            	}
            },
            error : function() {
            	tips("操作失败");
            }
        });
	};
	/**
	 * 点击详情
	 */
	$(".table-hover tbody").on("click", "#love #details", function () {
		var equipmentId = $(this).parent('#love').parent('tr').find('td').eq(1).text();
	       $.ajax({
	            type : "POST",
	            url :GetURLInfo()+"user/queryUserEquipmentInfo",
	            data:{"equipmentId":equipmentId},
	            datatype : "json",
	            success : function(result) {
	            	if(result.code==-1){
	            		var data = result.data;
	            		$("#detailsName").val(data.name);
	            		if(imageGudge(data.avatar)){
	            			$("#pic").attr("src",data.avatar);
	            		}
	            		document.getElementById("detailsImei").innerHTML=data.imei;
	            	//	 $("#detailsGender option[value='"+data.gender+"']").attr('selected',true);
	            		$("#detailsGender").val(data.gender);
	            		$("#detailsPhone").val(data.phone);
	            		document.getElementById("detailsLove").innerHTML=data.love==0?'否':'是';
	            		document.getElementById("detailsBluetoothmac").innerHTML=data.bluetoothmac;
	            		$("#detailsBorn").datepicker('setValue',data.born);
	            		$("#detailsLiveTime").datepicker('setValue',data.liveTime);
	            		$("#detailsHeight").val(data.height);
	            		$("#detailsWeight").val(data.weight);
	            		$("#detailsBed").val(data.bedId);
	            		$("#detailsNurseName").val(data.nurseId);
	            		var phone1= data.phone1.split(",");
	            		var phone2= data.phone2.split(",");
	            		$("#phoneName1").val(phone1[0]);
	            		$("#detailsPhone1").val(phone1[1]);
	            		$("#phoneName2").val(phone2[0]);
	            		$("#detailsPhone2").val(phone2[1]);
	            		$("#detailsAddress").val(data.address);
	            		$("#illness").val(data.illness);
	            		if(result.list.length>0){
	            			document.getElementById("detailsTypeof").innerHTML = "";
	            			var divA = document.getElementById("detailsTypeof");
	            			for(var o in result.list){
	            				var str = "<div>" + (parseInt(o)+1) +"."+result.list[o].name+"&emsp;"+result.list[o].phone+ "</div>";
	            				divA.innerHTML = divA.innerText+str;
	            			}
	            		}else{
	            			document.getElementById("detailsTypeof").innerHTML = "";
	            		}
	            	}
	            },
	            error : function() {
	            	tips("操作失败");
	            }
	        });
	    });
	/**
	 * 输入手机号码添加观察者
	 * @returns
	 */
	function follow(){
		var account=$("#textId").val();
		if(account==""){
			tips("请输入已注册健康管家的手机号码");
			return;
			
		}
		var imei=document.getElementById("detailsImei").innerHTML;
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"userEq/addFollowInfo",
            data:{"account":account,"imei":imei},
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		if(result.data.length>0){
            			document.getElementById("detailsTypeof").innerHTML = "";
            			var divA = document.getElementById("detailsTypeof");
            			for(var o in result.data){
            				var str = "<div>" + (parseInt(o)+1) +"."+result.data[o].name+"&emsp;"+result.data[o].phone+ "</div>";
            				divA.innerHTML = divA.innerText+str;
            			}
            		}else{
            			document.getElementById("detailsTypeof").innerHTML = "";
            		}
            		$("#invite-number").attr("data-dismiss","modal");
            	}else{
            		$("#invite-number").removeAttr("data-dismiss","modal");
            		tips(result.message);
            	}
            		
            },
            error : function() {
            	tips("操作失败");
            }
        });
	};
	/**
	 * 个人详情确定键
	 * @returns
	 */
	function detailsDetermine(){
        var data={};
        $("#detailsdivd input").each(function () {
			data[this.name]=this.value;
		});
        $("#detailsdivd select").each(function () {
			data[this.name]=this.value;
		});
        data["imei"]=document.getElementById("detailsImei").innerHTML;
		if($("#detailsLiveTime").val()=="" || $("#detailsBorn").val()==""){  
			tips("出生日期或者入住时间不能为空");
			return;
		}
		if(new Date($("#detailsLiveTime").val().replace("-","/"))<new Date($("#detailsBorn").val().replace("-","/"))){  
			tips("入住日期不能早于出生日期");
			return;
		}
		if(!isPoneAvailable($("#detailsPhone").val())){
			tips("请输入正确的手机号码");
			return;
		}
		if(isPoneAvailable(data.phone1)){
			if(data.phoneName1=="" || data.phone1==""){
				data["phone1"]="";
			}else{
				data["phone1"]=data.phoneName1+","+data.phone1
			}
		}else{
			tips("请输入紧急联系人1正确的手机号码");
			return;
		}
		if(isPoneAvailable(data.phone2)){
			if(data.phoneName2=="" || data.phone2==""){
				data["phone1"]="";
			}else{
				data["phone2"]=data.phoneName2+","+data.phone2
			}
		}else{
			tips("请输入紧急联系人2正确的手机号码");
			return;
		}
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/updateUserInfo",
            data:data,
            datatype : "json",
            success : function(result) {
            	tips(result.message);
            	location.reload();
            },
            error : function() {
            	tips("操作失败");
            }
        });
	};
	
	//<!-- 详细弹窗上传头像 -->    
        $("#pic").click(function () {
            $("#upload").click(); //隐藏了input:file样式后，点击头像就可以本地上传
            $("#upload").off("change").on("change", function () {
            	if(this.files[0].name.substring(this.files[0].name.lastIndexOf(".")+1,this.files[0].name.length)=="jpg"){
            		  var objUrl = window.URL.createObjectURL(this.files[0]);
            	         console.log(this.files[0].name);
            	         console.log(this.files[0].name.substring(this.files[0].name.lastIndexOf(".")+1,this.files[0].name.length));
            	                    $("#pic").attr("src", objUrl); //将图片路径存入src中，显示出图片
            	                    upimg();
            	}else{
            		tips("请上传jsp格式的图片");
            	}
         
            });
        });
  //上传头像到服务器
    function upimg() {
    	var reader = new FileReader();
    	reader.readAsDataURL($('#upload')[0].files[0]);
    	reader.onload = function (e) { 
    		var	avatar=reader.result.substring(23,reader.result.length);
    		var imei=document.getElementById("detailsImei").innerHTML;
            $.ajax({
                url: GetURLInfo()+"user/uploadUserPicture",
                type: "post",
                data: {"imei":imei,"avatar":avatar},
                datatype : "json",
                success: function (data) {
                	jqueryAlert({
        				'content' : data.message,
        				'closeTime' : 2000});
                },
                error : function() {
                	tips("操作失败");
                }
            });
    	}
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
		if(imei==""){
			tips("请输入IMEI号进行查询");return;
		}
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
            		document.getElementById("addNames").innerHTML=data.name;
            		document.getElementById("addGenderB").innerHTML=data.gender;
            		document.getElementById("addBorns").innerHTML=data.born;
            	}else{
            		tips(result.message);
            	}
            },
            error : function() {
            	tips("操作失败");
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
            		tips(result.message);
            		location.reload();
            	}else{
            		tips(result.message);
            	}
            	
            },
            error : function() {
            	tips("操作失败");
            }
        });
	};
	/**
	 * 移除查询
	 * @returns
	 */
	function deleteQuery(){
		var imei = $("#deleteImei").val();
		if(imei==""){
			tips("请输入IMEI号进行查询");return;
		};
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
            		tips(result.message);
            	}
            },
            error : function() {
            	tips("操作失败");
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
		tableList.promptBox();
	}
	/**
	 * 创建用户弹窗确定键
	 * @returns
	 */
	function addDetermine(){
		var data={};
		var result = true;
		$("#adddivd input").each(function () {
			if(this.id!="addIllness" & this.id!="addNurse" & this.id!="addBed"){
				if(this.value==""){
					tips($("#"+this.id).attr('placeholder'));
					result = false;
	                return false;
				}
			}
			data[this.name]=this.value;
			if (!result){
				return;
			}
		});
		if (!result){
			return;
		}
		$("#adddivd select").each(function () {
			if(this.value==""){	
				if($("#addGender").val()==""){
					tips("请选择性别");
					result = false;
				}
			
                return false;
			}
			data[this.name]=this.value;
			if (!result){
				return;
			}
		});
		if (!result){
			return;
		}
		if(!isPoneAvailable($("#addPhone").val())){
			tips("请输入正确的手机号码");
			return;
		}
		$.ajax({
            type : "POST",
            async: false,	
            url :GetURLInfo()+"user/addUserInfo",
            data:data,
            datatype : "json",
            success : function(result) {
            	if(result.code==-1){
            		location.reload();
            		$("#invite-determine").attr("data-dismiss","modal");
            	}else{
            		$("#invite-determine").removeAttr("data-dismiss","modal");
            		tips(result.message);
            	}
            },
            error : function() {
            	tips("操作失败");
            }
        });
	};
	

        


 /*//   <!-- 详细弹窗验证手机号 -->
    $(this).keydown(function (e) {
        var key = window.event ? e.keyCode : e.which;
        if (key.toString() == "13") {
            return false;
        }
    });*/
    /*//
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
    };*/


   