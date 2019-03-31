/**
 * 读取浆员详细信息
 */
function loadPlasmaInfo(type){
    var id =$("#id").val();//id
    $.ajax({
        url: GetURLInfo() + "queryPlasmaInfoDetail",
        type: "POST",
        dataType: "json",
        data:{"id":id},
        success: function (result) {
            if(result.code==-1){
                $("#providerNo").val(result.data.providerNo);
                $("#history").val(result.data.history);  
                $("#country").val(result.data.country);
                $("#village").val(result.data.village);
                $("#vgroup").val(result.data.vgroup);
                $("#name").val(result.data.name);
                $("#place").val(result.data.place);
                $("#sex").val(result.data.sex);
                $("#input_bloodType").val(getBloodValue(result.data.bloodType));
                $("#bloodType").val(result.data.bloodType);
                $("#plasmaState").val(result.data.plasmaState);
                $("#idNo").val(result.data.idNo);
                $("#validDate").val(dateFtt('yyyy-MM-dd',new Date(result.data.validDate)));
                $("#createDate").val(dateFtt('yyyy-MM-dd',new Date(result.data.createDate)));
                $("#addressx").val(result.data.addressx);
                $("#icNumber").val(result.data.icNumber);
                $("#age").val(result.data.age);
                $("#jdAge").val(result.data.jdAge);
                $("#nationId").val(result.data.nation);
                $("#nationName").val(result.data.nationName);
                $("#iproviderNo").val(result.data.iproviderNo);
                $("#birthday").val(dateFtt('yyyy-MM-dd',new Date(result.data.birthday)));
                $("#groupName").val(result.data.groupName);
                $("#inviteType").val(result.data.inviteType);
                $("#type").val(result.data.type);
                $("#phone").val(result.data.phone);
                $("#cid").val(result.data.cid);
                $("#mcreateDate").val(null!=result.data.mcreateDate ?dateFtt('yyyy-MM-dd',new Date(result.data.mcreateDate)):'');
                //$("#yycreateDate").val(null!=result.data.yycreateDate ? dateFtt('yyyy-MM-dd',new Date(result.data.yycreateDate)):'');
                $("#remarks").val(result.data.remarks);
                $("#demo1").attr("src",result.data.imagez);
                if(null!=result.data.imagespot){
                	$("#play").attr("src",result.data.imagespot);
                }
                if(null!=result.data.imagef){
                	$("#demo2").attr("src",result.data.imagef);
                }
                if(type==0){
                    //加载所需内容
                    loadinfo();
                }
                var mcreateDate = result.data.mcreateDate;
                if(null!=mcreateDate && mcreateDate.length>0){
                	 $.post("/config/queryDictByTypeAndLabel", {"type":"sys_config","label":'time'}, function(result) {
                 		var time = result.data.value;
                 		var mDate = new Date(mcreateDate);
                	 	mDate.setDate(date.getDate()+time);//设置天数 -1 天
                	 	$("#yycreateDate").val(dateFtt('yyyy-MM-dd',mDate));
                 	});
        		}
            }
        }
    });
}
