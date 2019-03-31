 $(function(){
	 // 初始化表格
	 for(var i=0 ;i<29;i++){
		 $("#datesTR").append("<td id='dates"+i+"'></td>");
		 $("#nameTr").append("<td style='height: 6em;padding: 4px 11px;' id='name"+i+"'></td>");
		 $("#markTR").append("<td style='height: 6em;'></td>");
		 $("#resultTR").append("<td id='result"+i+"' > </td>");
	 }
	 showTable();
	 
 });
 
 
 function showTable(){
		var page = $("#page").val();
		
		var url = "/elisaReport/selectQCReportForTable";
		var tciid = $("#reagentId").val();
		if(tciid=='' || tciid ==undefined){
			var index = parent.layer.alert("没有检验配置信息", function() {
				parent.layer.close(index);
			});
			return false;
		}
		var qcId = $("#qcId").val();
		if(qcId==''|| qcId ==undefined){
			var index = parent.layer.alert("没有质控品信息", function() {
				parent.layer.close(index);
			});
			return false;
		}
		var page = $("#page").val();
		var datas= {"tciid":tciid,"qcId":qcId,"page":page};
		if(page==1){
			window.location.href = "/elisaReport/printQCReportForTable?tciid="+tciid+"&qcId="+qcId+"&page="+page+"&limit=20&type="+$("#type").val();
		}
		simpleAjax(url, datas, function(result) {
			var data = result;
			var list = data.data;
			var firstItem = data.firstItem;
			var checkMethod = data.tci.testingMethodid;
			var type = $("#type").val();
			var isTreu = checkMethod ==2;
			$("#fStandardDeviation").html(isTreu?firstItem.altFrameStandardDeviation.toRound(2):firstItem.frameStandardDeviation.toRound(2));
			$("#fCV").html(isTreu?firstItem.altFrameCV.toRound(2):firstItem.frameCV.toRound(2) +"%");
			$("#fTargetValue").html(isTreu?firstItem.altFrameTargetValue.toRound(2):firstItem.frameTargetValue.toRound(2));
			var lastItem = data.lastItem;
			$("#selfStandardDeviation").html(isTreu?firstItem.altStandardDeviation.toRound(2):lastItem.standardDeviation.toRound(2));
			$("#selfCV").html(isTreu?firstItem.altCV.toRound(2):lastItem.cv.toRound(2) +"%");
			$("#selfTargetValue").html(isTreu?firstItem.altTargetValue.toRound(2):lastItem.targetValue.toRound(2));
			$("#detectionUnit").html(lastItem.detectionUnit);
			$("#checkProjectName").html(lastItem.checkProjectName);
			$("#checkDates").html( $.myTime.UnixToDate(firstItem.createDate)  +" - " + $.myTime.UnixToDate(lastItem.createDate));
			$("#batchNumber").html(lastItem.batchNumber);
			$("#ssName").html(lastItem.ssName);
			$("#termOfValidity").html(lastItem.termOfValidity);
			$("#elisaEquipmentAndManufacturers").html(lastItem.elisaEquipmentAndManufacturers);
			$("#checkMethod").html(lastItem.checkMethod);
			$("#useWavelength").html(lastItem.useWavelength);
			$("#qcBatchNumber").html(lastItem.qcBatchNumber);
			$("#qcTermOfValidity").html($.myTime.UnixToDate(lastItem.qcExpiryTime));
			$("#qcSsName").html(lastItem.qcName);
			// 先处理分页事项
			// 是否首页
			if(data.isFirst){
				$("#isFirst").hide();
			}else{
				$("#isFirst").show();
			}
			// 是否显示上一页
			if(data.hasPrev){
				$("#hasPrev").show();
			}else{
				$("#hasPrev").hide();
			}
			// 是否显示下一页
			if(data.hasNext){
				$("#hasNext").show();
			}else{
				$("#hasNext").hide();
			}
			// 是否末页
			if(data.isLast){
				$("#isLast").hide();
			}else{
				$("#isLast").show();
			}
			$("#page").val(data.pageIndex);
			$("#pages").val(data.pages);
			Xkr = isTreu?firstItem.altFrameTargetValue.toRound(3):firstItem.frameTargetValue.toRound(3);
			Skr = isTreu?firstItem.altFrameStandardDeviation.toRound(3):firstItem.frameStandardDeviation.toRound(3);
			// 处理表格
		
			for(var i=0,j=list.length;i<j;i++){
				var item = list[i];
				dates[i]= isTreu?item.altTargetValue.toRound(2):item.targetValue.toRound(2);
				xData[i]= isTreu?item.altValue.toRound(3):item.sDivideCO.toRound(3);
				$("#dates"+i).html( $.myTime.UnixToMonthAndDate(item.createDate));
				$("#name"+i).html(item.name);
				$("#result"+i).html(getQCValue(item.criticalResult));
			}
		
			drawImg();
		});
 }
 var Xkr=1.237;
 var Skr=0.05;
 var xData = [];
 var dates = [' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '];
 function drawImg(){
	 var myChart4 = echarts.init(document.getElementById("line_container4"));
	
	option4= {
	   tooltip: {
	        trigger: 'axis'
	    }, 
	    grid: {
	        left: '6.6%',
	        right: '5%',
	        bottom: '9.1%'
	        /*containLabel: true*/
	    }, 
	    xAxis: {
	        type: 'category',
	        boundaryGap: true,　splitLine:{
	　　　　show:true
	　　},
	        data: dates,
	 
	        axisLabel: {  
	        	   interval:0,  
	        	   rotate:0,
	        	   show: true,
	                textStyle: {
	                    color: '#000'
	                }
	        	}  
	    },
	    yAxis: {  
	    	boundaryGap: false,
	          　splitLine:{
	　　　　show:true
	　　}, 
	　max:Xkr+Skr*3,
	　min:Xkr-Skr*3,
	splitNumber : 6, 
	        type: 'value', 
	        axisLabel: {                   
	                 formatter: function (value, index) {           
	                  return value.toFixed(3);      
	                   }, 
	                   show: true,
		                textStyle: {
		                    color: '#000'
		                }
	        }
	    },
	    interval:Skr,
	    series: [
	        {
	            name:'',
	            type:'line',
	            stack: null,
	            color:['#000'],
	            data:xData
	        }
	          
	    ]
	   }
	 myChart4.setOption(option4);
 }
 $("#isFirst").click(function(){
		$("#page").val(0);
		showTable();
	});
	$("#hasPrev").click(function(){
		$("#page").val(Number($("#page").val())-1);
		showTable();
	});
	$("#hasNext").click(function(){
		$("#page").val(Number($("#page").val())+1);
		showTable();
		
	});
	$("#isLast").click(function(){
		$("#page").val($("#pages").val());
		showTable();
	});
 