	///tab点击
 $(".tab_box a").click(function() {
	 	$(this).addClass("on").siblings().removeClass("on");
	 });
$(function(){
	 clickevent("#newtest","createDate","determine","#result","day");
	 initDate("#createDate");		//建档人数时间 
	 clickevent("#registries","daclrdata","registries_set","#registries_row","day");
	 initDate("#daclrdata");		//登记人数时间
	 clickevent("#collectionC","mondadata","collection_set","#collection_row","day");
	 initDate("#mondadata");		//采浆人数时间
	 clickevent("#plasmAmountC","sxlordara","plasmAmount_set","#plasmAmount_row","day");
	 initDate("#sxlordara");		//采浆重量时间
	 //今日建档人数,今日登记人数,今日采浆人数,今日采浆重量
	 initCheckData21('/providerRegistries/queryTodayPeopleInfo','',1);
	 //建档人数统计(扇形图)
	 initCheckData21('/providerRegistries/queryRecordPeople','',2);
	 //登记人数统计(扇形图)
	 initCheckData21('/providerRegistries/queryRegisterPeople','',3);
	 //采浆人数统计(扇形图)
	 initCheckData21('/providerRegistries/queryCollectionPeopleCount','',4);
	 //采浆量统计(扇形图)
	 initCheckData21('/providerRegistries/queryPlasmAmountPeopleCount','',5);
	//建档人数(柱状图)
	 initCheckData21('/providerRegistries/queryHistogramPeople',{"service":$("#result").val(),"toDate":$("#createDate").val()+" 00:00:00","toDateEnd":$("#createDate").val()+" 23:59:59","oldDate":daysJian("yyyy-MM-dd",$("#createDate").val())+" 00:00:00","oldDateEnd":daysJian("yyyy-MM-dd",$("#createDate").val())+" 23:59:59"},6);
	//登记人数(折线图)
	 initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":$("#registries_row").val(),"toDate":$("#daclrdata").val()+" 00:00:00","toDateEnd":$("#daclrdata").val()+" 23:59:59","oldDate":daysJian("yyyy-MM-dd",$("#daclrdata").val())+" 00:00:00","oldDateEnd":daysJian("yyyy-MM-dd",$("#daclrdata").val())+" 23:59:59"},9);
	//采浆人数(柱状图)
	 initCheckData21('/providerRegistries/queryCollectionPeople',{"service":$("#collection_row").val(),"toDate":$("#mondadata").val()+" 00:00:00","toDateEnd":$("#mondadata").val()+" 23:59:59","oldDate":daysJian("yyyy-MM-dd",$("#mondadata").val())+" 00:00:00","oldDateEnd":daysJian("yyyy-MM-dd",$("#mondadata").val())+" 23:59:59"},12);
	//采浆重量(折线图)
	 initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":$("#plasmAmount_row").val(),"toDate":$("#sxlordara").val()+" 00:00:00","toDateEnd":$("#sxlordara").val()+" 23:59:59","oldDate":daysJian("yyyy-MM-dd",$("#sxlordara").val())+" 00:00:00","oldDateEnd":daysJian("yyyy-MM-dd",$("#sxlordara").val())+" 23:59:59"},15);
 });/////////////////////////////////////////////////////////////////
 ////ajax公共类
function initCheckData21(url,datas,type,func){
	simpleAjax(url,datas,function(result){
			var houraArray = new Array();
			var olddayArray = new Array();
			var todayArray = new Array();
			function inintext(){
				for(var o in  result.data){
					houraArray.push(result.data[o].ohour);
					olddayArray.push(result.data[o].oldday==null?0:result.data[o].oldday);
					todayArray.push(result.data[o].today==null?0:result.data[o].today);
				}
			}
		//今日建档人数,今日登记人数,今日采浆人数,今日采浆重量
		 if(type==1){
			 if(result.code==-1){
					var data = result.data;
					$("#record").text(data.record==null?0:data.record);
					$("#register").text(data.register==null?0:data.register);
					$("#collection").text(data.collection==null?0:data.collection);
					$("#plasmAmount").text(data.plasmAmount==null?0:data.plasmAmount);
				}
		 };
		 //建档人数统计(扇形图)
		 if(type==2){
			 if(result.code==-1){
					var data = result.data;
					recordPeople(data.toYear,data.oldYear);
				}
		 };
		 //登记人数统计(扇形图)
		 if(type==3){
			 if(result.code==-1){
					var data = result.data;
					registerPeople(data.toYear,data.oldYear);
				}
		 };
		 //采浆人数统计(扇形图)
		 if(type==4){
			 if(result.code==-1){
					var data = result.data;
					collectionPeople(data.toYear,data.oldYear);
				}
		 };
		 //采浆量统计(扇形图)
		 if(type==5){
			 if(result.code==-1){
					var data = result.data;
					PlasmAmountPeople(data.toYear,data.oldYear);
				}
		 };
		 //建档人数(柱状图)(天份)(月份)(年份)
		 if(type==6){
			 if(result.code==-1){
				 	inintext();
					createdPeople(houraArray,olddayArray,todayArray);
				}
		 };
		//建档人数(柱状图)(月份)
		 if(type==7){
			 if(result.code==-1){
				 	inintext();
					loadDrugs(houraArray,olddayArray,todayArray);
				}
		 };
		//建档人数(柱状图)(年份)
		 if(type==8){
			 if(result.code==-1){
				 	inintext();
					loadDrugxiaos(houraArray,olddayArray,todayArray);
				}
		 };
		 //登记人数(折线图)(天份)
		 if(type==9){
			 if(result.code==-1){
				 	inintext();
					registriesPage(houraArray,olddayArray,todayArray);
				}
		 };
		//登记人数(折线图)(月份)
		 if(type==10){
			 if(result.code==-1){
				 	inintext();
					loadDrugsa(houraArray,olddayArray,todayArray);
				}
		 };
		//登记人数(折线图)(年份)
		 if(type==11){
			 if(result.code==-1){
				 	inintext();
					loadDrugxiaod(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆人数(柱状图)(天份)
		 if(type==12){
			 if(result.code==-1){
				 	inintext();
					collectionPage(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆人数(柱状图)(月份)
		 if(type==13){
			 if(result.code==-1){
				 	 inintext();
					 loadDrugsade(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆人数(柱状图)(年份)
		 if(type==14){
			 if(result.code==-1){
				 	inintext();	
					loadDrugxiaowe(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆重量(折线图)(天份)
		 if(type==15){
			 if(result.code==-1){
				 	inintext();
					plasmAmountPage(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆重量(折线图)(月份)
		 if(type==16){
			 if(result.code==-1){
				 	inintext();
					loadDrugju(houraArray,olddayArray,todayArray);
				}
		 };
		//采浆重量(折线图)(年份)
		 if(type==17){
			 if(result.code==-1){
				 	inintext();
					loadDrugxiki(houraArray,olddayArray,todayArray);
				}
		 };
	 });
	}
//建档人数统计(扇形图)
 function recordPeople(toYear,oldYear){
	 var myChart5 = echarts.init(document.getElementById("line_container2"));
	 var option5 = {
	 	tooltip: {
	 		trigger: 'item',
	 		formatter: "{a} <br/>{b} : {c} ({d}%)"
	 	},
	 	legend: {
	 		bottom: 'bottom',
	 		data: ['本年', '去年']
	 	},
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	series: [{
	 		name: '人数统计',
	 		type: 'pie',
	 		radius: '55%',//扇形图外形大小
	 		center: ['50%', '60%'],
	 		data: [{
	 				value: toYear==null?0:toYear,
	 				name: '本年',
	 				itemStyle: {
	 					normal: {
	 						color: '#9ce9ff'
	 					}
	 				}
	 			},
	 			{
	 				value: oldYear==null?0:oldYear,
	 				name: '去年',
	 				itemStyle: {
	 					normal: {
	 						color: '#36a8f0'
	 					}
	 				},
	 			},
	 		],
	 		label: {
	 			normal: {
	 				textStyle: {
	 					color: '#666666'
	 				}
	 			}
	 		},
	 		labelLine: {
	 			normal: {
	 				lineStyle: {
	 					color: '#666666'
	 				},
	 				smooth: 0.2,
	 				length: 10,
	 				length2: 20
	 			}
	 		},
	 		itemStyle: {
	 			emphasis: {
	 				shadowBlur: 10,
	 				shadowOffsetX: 0,
	 				shadowColor: 'rgba(0, 0, 0, 0.5)'
	 			}
	 		}
	 	}]
	 };
	 myChart5.setOption(option5); 
 }	
//登记人数统计(扇形图)
function registerPeople(toYear,oldYear){
	 var myChart7 = echarts.init(document.getElementById("line_container4"));
	 var option7 = {
	 	tooltip: {
	 		trigger: 'item',
	 		formatter: "{a} <br/>{b} : {c} ({d}%)"
	 	},
	 	legend: {
	 		bottom: 'bottom',
	 		data: ['本年', '去年']
	 	},
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	series: [{
	 		name: '人数统计',
	 		type: 'pie',
	 		radius: '55%',
	 		center: ['50%', '60%'],
	 		data: [{
	 				value: toYear==null?0:toYear,
	 				name: '本年',
	 				itemStyle: {
	 					normal: {
	 						color: '#67fef5'
	 					}
	 				}
	 			},
	 			{
	 				value: oldYear==null?0:oldYear,
	 				name: '去年',
	 				itemStyle: {
	 					normal: {
	 						color: '#0ed0c4'
	 					}
	 				},
	 			},
	 		],
	 		label: {
	 			normal: {
	 				textStyle: {
	 					color: '#666666'
	 				}
	 			}
	 		},
	 		labelLine: {
	 			normal: {
	 				lineStyle: {
	 					color: '#666666'
	 				},
	 				smooth: 0.2,
	 				length: 10,
	 				length2: 20
	 			}
	 		},
	 		itemStyle: {
	 			emphasis: {
	 				shadowBlur: 10,
	 				shadowOffsetX: 0,
	 				shadowColor: 'rgba(0, 0, 0, 0.5)'
	 			}
	 		}
	 	}]
	 };

	 myChart7.setOption(option7);
 }
//采浆人数统计(扇形图)
function collectionPeople(toYear,oldYear){
	 var myChart9 = echarts.init(document.getElementById("line_container6"));
	 var option9 = {
	 	tooltip: {
	 		trigger: 'item',
	 		formatter: "{a} <br/>{b} : {c} ({d}%)"
	 	},
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		bottom: 'bottom',
	 		data: ['本年', '去年']
	 	},

	 	series: [{
	 		name: '人数统计',
	 		type: 'pie',
	 		radius: '55%',
	 		center: ['50%', '60%'],
	 		data: [{
	 				value: toYear==null?0:toYear,
	 				name: '本年',
	 				itemStyle: {
	 					normal: {
	 						color: '#ffe00d'
	 					}
	 				}
	 			},
	 			{
	 				value: oldYear==null?0:oldYear,
	 				name: '去年',
	 				itemStyle: {
	 					normal: {
	 						color: '#2fc98f'
	 					}
	 				},
	 			},
	 		],
	 		label: {
	 			normal: {
	 				textStyle: {
	 					color: '#666666'
	 				}
	 			}
	 		},
	 		labelLine: {
	 			normal: {
	 				lineStyle: {
	 					color: '#666666'
	 				},
	 				smooth: 0.2,
	 				length: 10,
	 				length2: 20
	 			}
	 		},
	 		itemStyle: {
	 			emphasis: {
	 				shadowBlur: 10,
	 				shadowOffsetX: 0,
	 				shadowColor: 'rgba(0, 0, 0, 0.5)'
	 			}
	 		}
	 	}]
	 };

	 myChart9.setOption(option9);
}
//采浆量统计(扇形图)
function PlasmAmountPeople(toYear,oldYear){
	 var myChart11 = echarts.init(document.getElementById("line_container11"));
	 var option11 = {
	 	tooltip: {
	 		trigger: 'item',
	 		formatter: "{a} <br/>{b} : {c} ({d}%)"
	 	},
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		bottom: 'bottom',
	 		data: ['本年', '去年']
	 	},
	 	series: [{
	 		name: '人数统计',
	 		type: 'pie',
	 		radius: '55%',
	 		center: ['50%', '60%'],
	 		data: [{
	 				value: toYear==null?0:toYear,
	 				name: '本年',
	 				itemStyle: {
	 					normal: {
	 						color: '#f5736e'
	 					}
	 				}
	 			},
	 			{
	 				value: oldYear==null?0:oldYear,
	 				name: '去年',
	 				itemStyle: {
	 					normal: {
	 						color: '#ffe4aa'
	 					}
	 				},
	 			},
	 		],
	 		label: {
	 			normal: {
	 				textStyle: {
	 					color: '#666666'
	 				}
	 			}
	 		},
	 		labelLine: {
	 			normal: {
	 				lineStyle: {
	 					color: '#666666'
	 				},
	 				smooth: 0.2,
	 				length: 10,
	 				length2: 20
	 			}
	 		},
	 		itemStyle: {
	 			emphasis: {
	 				shadowBlur: 10,
	 				shadowOffsetX: 0,
	 				shadowColor: 'rgba(0, 0, 0, 0.5)'
	 			}
	 		}
	 	}]
	 };

	 myChart11.setOption(option11);
}
//建档人数柱状图(天份)单击事件
function loadToday(){
	clickevent("#newtest","createDate","determine","#result","day");
	initDate("#createDate");
	var toDate = $("#createDate").val()+" 00:00:00";//今日开始时间
	var toDateEnd = $("#createDate").val()+" 23:59:59";//今日结束时间
	var oldDate = daysJian("yyyy-MM-dd",$("#createDate").val())+" 00:00:00";//昨日开始时间
	var oldDateEnd = daysJian("yyyy-MM-dd",$("#createDate").val())+" 23:59:59";//昨日开始时间
	initCheckData21('/providerRegistries/queryHistogramPeople',{"service":$("#result").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},6);
}
//建档人数柱状图(月份)单击事件
function thismonth(){
	clickevent("#newtest","createDate","determine","#result","month");
	initDateGraphical("#createDate",'yyyy-MM','month');
	var toDate = $("#createDate").val()+"-01 00:00:00";//今月开始时间
	var toDateEnd = $("#createDate").val()+"-31 23:59:59";//今月结束时间
	var oldDate = daysJian("yyyy-MM",$("#createDate").val())+"-01 00:00:00";//昨月开始时间
	var oldDateEnd = daysJian("yyyy-MM",$("#createDate").val())+"-31 23:59:59";//昨月开始时间
	initCheckData21('/providerRegistries/queryHistogramPeople',{"service":$("#result").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},7);
}
//建档人数柱状图(年份)单击事件
function thisyear(){
	clickevent("#newtest","createDate","determine","#result","year");
	initDateGraphical("#createDate",'yyyy','year');
	var toDate = $("#createDate").val()+"-01-01 00:00:00";//今年开始时间
	var toDateEnd = $("#createDate").val()+"-12-31 23:59:59";//今年结束时间
	var oldDate = daysJian("yyyy",$("#createDate").val())+"-01-01 00:00:00";//昨年开始时间
	var oldDateEnd = daysJian("yyyy",$("#createDate").val())+"-12-31 23:59:59";//昨年开始时间
	initCheckData21('/providerRegistries/queryHistogramPeople',{"service":$("#result").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},8);
}
//建档人数柱状图(天份)
function createdPeople(houra,oldday,today){
	var myChart4 = echarts.init(document.getElementById("line_container1"));
	 var option4 = {
	 	tooltip: {
	 		trigger: 'axis',
	 		axisPointer: {
	 			type: 'cross',
	 			crossStyle: {
	 				color: '#999'
	 			}
	 		}
	 	},
	 	dataZoom: [{
	 		id: 'dataZoomX',
	 		show: true,
	 		backgroundColor: "rgba(47,69,84,0)",
	 		type: 'slider',
	 		xAxisIndex: 0,
	 		end: 0,
	 		startValue: 8,
	 		endValue: 100,
	 		orient: "horizontal",
	 		zoomLock: false,
	 		zoomOnMouseWheel: true,
	 		moveOnMouseMove: true,
	 	}, ],

	 	toolbox: {
	 		show: true,
	 		feature: {
	 		myTool: {
	 				show: true,
	 				title: '数值显示',
	 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 				onclick: function() {
	 					/*loadDrlo(houra,oldday,today);*/
	 					//建档人数柱状图(天份)显示数值
	 					var myChart4 = echarts.init(document.getElementById("line_container1"));
	 					 var option4= {
	 					 	tooltip: {
	 					 		trigger: 'axis',
	 					 		axisPointer: {
	 					 			type: 'cross',
	 					 			crossStyle: {
	 					 				color: '#999'
	 					 			}
	 					 		}
	 					 	},
	 					 	dataZoom: [{
	 					 		id: 'dataZoomX',
	 					 		show: true,
	 					 		backgroundColor: "rgba(47,69,84,0)",
	 					 		type: 'slider',
	 					 		xAxisIndex: 0,
	 					 		end: 0,
	 					 		startValue: 8,
	 					 		endValue: 100,
	 					 		orient: "horizontal",
	 					 		zoomLock: false,
	 					 		zoomOnMouseWheel: true,
	 					 		moveOnMouseMove: true,
	 					 	}, ],

	 					 	toolbox: {
	 					 		show: true,
	 					 		feature: {
	 					 			myTool: {
	 					 				show: true,
	 					 				title: '数值隐藏',
	 					 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 					 				onclick: function() {
	 					 					createdPeople(houra,oldday,today)
	 					 				}
	 					 			},
	 					 			saveAsImage: {
	 					 				show: true,
	 					 				title: '下载'
	 					 			}
	 					 		}
	 					 	},
	 					 	legend: {
	 					 		data: ['今日数量', '昨日数量']
	 					 	},
	 					 	xAxis: [{
	 					 		type: 'category',
	 					 		data:houra,
	 					 		axisPointer: {
	 					 			type: 'shadow'
	 					 		}
	 					 	}],
	 					 	yAxis: [{
	 					 		type: 'value',
	 					 		name: '数量（人）',
	 					 		min: 0,
	 					 		interval: 0,
	 					 		axisLabel: {
	 					 			formatter: '{value} (人)'
	 					 		}
	 					 	}, ],
	 					 	series: [{
	 					 			name: '今日数量',
	 					 			type: 'bar', 
	 					 		data: today,	
	 					 		itemStyle: {
	 					 				normal: {
	 					 					show: true,
	 					 					color: '#9ce9ff'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},
	 					 		{
	 					 			name: '昨日数量',
	 					 			type: 'bar',
	 					 			data: oldday,
	 					 			itemStyle: {
	 					 				normal: {
	 					 					show: true,
	 					 					color: '#36a8f0'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},

	 					 	]
	 					 };
	 					 myChart4.setOption(option4);
	 				}
	 			},
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		data: ['今日数量', '昨日数量']
	 	},
	 	xAxis: [{
	 		type: 'category',
	 		data:houra,
	 		axisPointer: {
	 			type: 'shadow'
	 		}
	 	}],
	 	yAxis: [{
	 		type: 'value',
	 		name: '数量（人）',
	 		min: 0,
	 		interval: 0,
	 		axisLabel: {
	 			formatter: '{value} (人)'
	 		}
	 	}, ],
	 	series: [{
	 			name: '今日数量',
	 			type: 'bar', 
	 		data: today,
	 		itemStyle: {
	 				normal: {
	 					color: '#9ce9ff'
	 				}
	 			},
	 			label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
	 		},
	 		{
	 			name: '昨日数量',
	 			type: 'bar',
	 			data: oldday,
	 			itemStyle: {
	 				normal: {
	 					color: '#36a8f0'
	 				}
	 			},
	 			label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
	 		},

	 	]
	 };
	 myChart4.setOption(option4);
}
   
//建档人数柱状图(月份)
 function loadDrugs(ohour,oldday,today) {
 	var myChart4 = echarts.init(document.getElementById("line_container1"));
 	var option4 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 				myTool: {
 					show: true,
 					title: '数值显示',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						/*loadDrugsYear(ohour,oldday,today);*/
 						var myChart4 = echarts.init(document.getElementById("line_container1"));
 					 	var option4 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 				myTool: {
 					 					show: true,
 					 					title: '数值隐藏',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugs(ohour,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['本月数量', '上月数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: ohour,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（人）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '本月数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,	itemStyle: {
 					 					normal: {
 					 						color: '#9ce9ff'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '上月数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#36a8f0'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart4.setOption(option4);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['本月数量', '上月数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: ohour,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（人）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '本月数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,	itemStyle: {
 					normal: {
 						color: '#9ce9ff'
 					}
 				},
 			},
 			{
 				name: '上月数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#36a8f0'
 					}
 				},
 			},

 		]
 	};
 	myChart4.setOption(option4);
 } 
 //建档人数柱状图(年份)
 function loadDrugxiaos(ohour,oldday,today) {
 	var myChart4 = echarts.init(document.getElementById("line_container1"));
 	var option4 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 				myTool: {
 					show: true,
 					title: '数值显示',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart4 = echarts.init(document.getElementById("line_container1"));
 					 	var option4 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 				myTool: {
 					 					show: true,
 					 					title: '数值显示',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugxiaos(ohour,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['今年数量', '去年数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: ohour,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（人）',
 					 			min: 0, 
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '今年数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 			data:today,	
 					 			itemStyle: {
 					 					normal: {
 					 						color: '#9ce9ff'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '去年数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data:  oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#36a8f0'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart4.setOption(option4);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['今年数量', '去年数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: ohour,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（人）',
 			min: 0, 
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '今年数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 			data:today,	itemStyle: {
 					normal: {
 						color: '#9ce9ff'
 					}
 				},
 			},
 			{
 				name: '去年数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data:  oldday,
 				itemStyle: {
 					normal: {
 						color: '#36a8f0'
 					}
 				},
 			},

 		]
 	};
 	myChart4.setOption(option4);
 }
 //建档人数柱状图(确定单击时间)
$(document).on("click", "#determine", function () {
	 var result= $("#result").val();
	 var toDate = $("#createDate").val();//今天的时间
	 if(result=='day'){
		 var oldDate = daysJian("yyyy-MM-dd",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryHistogramPeople',{"service":result,"toDate":toDate+" 00:00:00","toDateEnd":toDate+" 23:59:59","oldDate":oldDate+" 00:00:00","oldDateEnd":oldDate+" 23:59:59"},6);
	 }else if(result=='month'){
		 var oldDate = daysJian("yyyy-MM",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryHistogramPeople',{"service":result,"toDate":toDate+"-01 00:00:00","toDateEnd":toDate+"-31 23:59:59","oldDate":oldDate+"-01 00:00:00","oldDateEnd":oldDate+"-31 23:59:59"},7);
	 }else if(result=='year'){
		 var oldDate = daysJian("yyyy",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryHistogramPeople',{"service":result,"toDate":toDate+"-01-01 00:00:00","toDateEnd":toDate+"-12-31 23:59:59","oldDate":oldDate+"-01-01 00:00:00","oldDateEnd":oldDate+"-12-31 23:59:59"},8);
	 }
	  });
//登记人数折线图(天份)单击事件
function registriesDay(){
	clickevent("#registries","daclrdata","registries_set","#registries_row","day");
	 initDate("#daclrdata");
	 var toDate = $("#daclrdata").val()+" 00:00:00";//今日开始时间
	 var toDateEnd = $("#daclrdata").val()+" 23:59:59";//今日结束时间
	 var oldDate = daysJian("yyyy-MM-dd",$("#daclrdata").val())+" 00:00:00";//昨日开始时间
	 var oldDateEnd = daysJian("yyyy-MM-dd",$("#daclrdata").val())+" 23:59:59";//昨日开始时间
	 initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":$("#registries_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},9);
}
//登记人数折线图(月份)单击事件
function registriesMonth(){
	clickevent("#registries","daclrdata","registries_set","#registries_row","month");
	initDateGraphical("#daclrdata",'yyyy-MM','month');
	var toDate = $("#daclrdata").val()+"-01 00:00:00";//今月开始时间
	var toDateEnd = $("#daclrdata").val()+"-31 23:59:59";//今月结束时间
	var oldDate = daysJian("yyyy-MM",$("#daclrdata").val())+"-01 00:00:00";//昨月开始时间
	var oldDateEnd = daysJian("yyyy-MM",$("#daclrdata").val())+"-31 23:59:59";//昨月开始时间
	initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":$("#registries_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},10);
}
//登记人数折线图(年份)单击事件
function registriesYear(){
	clickevent("#registries","daclrdata","registries_set","#registries_row","year");
	initDateGraphical("#daclrdata",'yyyy','year');
	var toDate = $("#daclrdata").val()+"-01-01 00:00:00";//今年开始时间
	var toDateEnd = $("#daclrdata").val()+"-12-31 23:59:59";//今年结束时间
	var oldDate = daysJian("yyyy",$("#daclrdata").val())+"-01-01 00:00:00";//昨年开始时间
	var oldDateEnd = daysJian("yyyy",$("#daclrdata").val())+"-12-31 23:59:59";//昨年开始时间
	initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":$("#registries_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},11);
}
//登记人数折线图(确定单击时间)
$(document).on("click", "#registries_set", function () {
	 var result= $("#registries_row").val();
	 var toDate = $("#daclrdata").val();//今天的时间
	 if(result=='day'){
		 var oldDate = daysJian("yyyy-MM-dd",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":result,"toDate":toDate+" 00:00:00","toDateEnd":toDate+" 23:59:59","oldDate":oldDate+" 00:00:00","oldDateEnd":oldDate+" 23:59:59"},9);
	 }else if(result=='month'){
		 var oldDate = daysJian("yyyy-MM",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":result,"toDate":toDate+"-01 00:00:00","toDateEnd":toDate+"-31 23:59:59","oldDate":oldDate+"-01 00:00:00","oldDateEnd":oldDate+"-31 23:59:59"},10);
	 }else if(result=='year'){
		 var oldDate = daysJian("yyyy",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryRegistriesPeople',{"service":result,"toDate":toDate+"-01-01 00:00:00","toDateEnd":toDate+"-12-31 23:59:59","oldDate":oldDate+"-01-01 00:00:00","oldDateEnd":oldDate+"-12-31 23:59:59"},11);
	 }
	  });
//登记人数折线图(天份)
 function registriesPage(houra,oldday,today){
	 var myChart6 = echarts.init(document.getElementById("line_container3"));
	 var option6 = {
	 	tooltip: {
	 		trigger: 'axis',
	 		axisPointer: {
	 			type: 'cross',
	 			crossStyle: {
	 				color: '#999'
	 			}
	 		}
	 	},
	 	dataZoom: [{
	 		id: 'dataZoomX',
	 		show: true,
	 		backgroundColor: "rgba(47,69,84,0)",
	 		type: 'slider',
	 		xAxisIndex: 0,
	 		end: 0,
	 		startValue: 8,
	 		endValue: 100,
	 		orient: "horizontal",
	 		zoomLock: false,
	 		zoomOnMouseWheel: true,
	 		moveOnMouseMove: true,
	 	}, ],

	 	toolbox: {
	 		show: true,
	 		feature: {
	 			myTool: {
	 				show: true,
	 				title: '数值显示',
	 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 				onclick: function() {
	 					var myChart6 = echarts.init(document.getElementById("line_container3"));
	 					 var option6 = {
	 					 	tooltip: {
	 					 		trigger: 'axis',
	 					 		axisPointer: {
	 					 			type: 'cross',
	 					 			crossStyle: {
	 					 				color: '#999'
	 					 			}
	 					 		}
	 					 	},
	 					 	dataZoom: [{
	 					 		id: 'dataZoomX',
	 					 		show: true,
	 					 		backgroundColor: "rgba(47,69,84,0)",
	 					 		type: 'slider',
	 					 		xAxisIndex: 0,
	 					 		end: 0,
	 					 		startValue: 8,
	 					 		endValue: 100,
	 					 		orient: "horizontal",
	 					 		zoomLock: false,
	 					 		zoomOnMouseWheel: true,
	 					 		moveOnMouseMove: true,
	 					 	}, ],

	 					 	toolbox: {
	 					 		show: true,
	 					 		feature: {
	 					 			myTool: {
	 					 				show: true,
	 					 				title: '数值显示',
	 					 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 					 				onclick: function() {
	 					 					registriesPage(houra,oldday,today);
	 					 				}
	 					 			},
	 					 			saveAsImage: {
	 					 				show: true,
	 					 				title: '下载'
	 					 			}
	 					 		}
	 					 	},
	 					 	legend: {
	 					 		data: ['今日数量', '昨日数量']
	 					 	},
	 					 	xAxis: [{
	 					 		type: 'category',
	 					 		data: houra,/////时间
	 					 		axisPointer: {
	 					 			type: 'shadow'
	 					 		}
	 					 	}],
	 					 	yAxis: [{
	 					 		type: 'value',
	 					 		name: '数量（人）',
	 					 		min: 0,
	 					 		interval: 0,
	 					 		axisLabel: {
	 					 			formatter: '{value} (人)'
	 					 		}
	 					 	}, ],
	 					 	series: [{
	 					 			name: '今日数量',
	 					 			type: 'line',
	 						data: today,itemStyle: {	///今天
	 					 				normal: {
	 					 					color: '#fdd269'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},
	 					 		{
	 					 			name: '昨日数量',
	 					 			type: 'line',
	 					 			data: oldday,////昨天
	 					 			itemStyle: {
	 					 				normal: {
	 					 					color: '#0ed0c4'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},

	 					 	]
	 					 };
	 					 myChart6.setOption(option6);
	 				}
	 			},
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		data: ['今日数量', '昨日数量']
	 	},
	 	xAxis: [{
	 		type: 'category',
	 		data: houra,/////时间
	 		axisPointer: {
	 			type: 'shadow'
	 		}
	 	}],
	 	yAxis: [{
	 		type: 'value',
	 		name: '数量（人）',
	 		min: 0,
	 		interval: 0,
	 		axisLabel: {
	 			formatter: '{value} (人)'
	 		}
	 	}, ],
	 	series: [{
	 			name: '今日数量',
	 			type: 'line',
		data: today,itemStyle: {	///今天
	 				normal: {
	 					color: '#fdd269'
	 				}
	 			},
	 		},
	 		{
	 			name: '昨日数量',
	 			type: 'line',
	 			data: oldday,////昨天
	 			itemStyle: {
	 				normal: {
	 					color: '#0ed0c4'
	 				}
	 			},
	 		},

	 	]
	 };
	 myChart6.setOption(option6);
}
//登记人数折线图(月份)
 function loadDrugsa(houra,oldday,today) {
 	var myChart6 = echarts.init(document.getElementById("line_container3"));
 	var option6 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 			myTool: {
 					show: true,
 					title: '数值隐藏',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart6 = echarts.init(document.getElementById("line_container3"));
 					 	var option6 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 			myTool: {
 					 					show: true,
 					 					title: '数值隐藏',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugsa(houra,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['本月数量', '上月数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（人）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '本月数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,itemStyle: {
 					 					normal: {
 					 						color: '#fdd269'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '上月数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#0ed0c4'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart6.setOption(option6);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['本月数量', '上月数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（人）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '本月数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,itemStyle: {
 					normal: {
 						color: '#fdd269'
 					}
 				},
 			},
 			{
 				name: '上月数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#0ed0c4'
 					}
 				},
 			},

 		]
 	};
 	myChart6.setOption(option6);
 }
//登记人数折线图(年份)
 function loadDrugxiaod(houra,oldday,today) {
 	var myChart6 = echarts.init(document.getElementById("line_container3"));
 	var option6 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 		myTool: {
 					show: true,
 					title: '数值显示',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart6 = echarts.init(document.getElementById("line_container3"));
 					 	var option6 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 		myTool: {
 					 					show: true,
 					 					title: '数值显示',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugxiaod(houra,oldday,today);	 
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['今年数量', '去年数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（人）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '今年数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,	
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#fdd269'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '去年数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#0ed0c4'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart6.setOption(option6); 
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['今年数量', '去年数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（人）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '今年数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,	
 				itemStyle: {
 					normal: {
 						color: '#fdd269'
 					}
 				},
 			},
 			{
 				name: '去年数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#0ed0c4'
 					}
 				},
 			},

 		]
 	};
 	myChart6.setOption(option6);
 }

//采浆人数柱状图(天份)单击事件
function collectionDay(){
	 clickevent("#collectionC","mondadata","collection_set","#collection_row","day");
	 initDate("#mondadata");
	 var toDate = $("#mondadata").val()+" 00:00:00";//今日开始时间
	 var toDateEnd = $("#mondadata").val()+" 23:59:59";//今日结束时间
	 var oldDate = daysJian("yyyy-MM-dd",$("#mondadata").val())+" 00:00:00";//昨日开始时间
	 var oldDateEnd = daysJian("yyyy-MM-dd",$("#mondadata").val())+" 23:59:59";//昨日开始时间
	initCheckData21('/providerRegistries/queryCollectionPeople',{"service":$("#collection_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},12);
}
//采浆人数柱状图(月份)单击事件
function collectionMonth(){
	 clickevent("#collectionC","mondadata","collection_set","#collection_row","month");
	 initDateGraphical("#mondadata",'yyyy-MM','month');
	 var toDate = $("#mondadata").val()+"-01 00:00:00";//今月开始时间
	 var toDateEnd = $("#mondadata").val()+"-31 23:59:59";//今月结束时间
	 var oldDate = daysJian("yyyy-MM",$("#mondadata").val())+"-01 00:00:00";//昨月开始时间
	 var oldDateEnd = daysJian("yyyy-MM",$("#mondadata").val())+"-31 23:59:59";//昨月开始时间
	initCheckData21('/providerRegistries/queryCollectionPeople',{"service":$("#collection_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},13);
}
//采浆人数柱状图(年份)单击事件
function collectionYear(){
	 clickevent("#collectionC","mondadata","collection_set","#collection_row","year");
	 initDateGraphical("#mondadata",'yyyy','year');
	 var toDate = $("#mondadata").val()+"-01-01 00:00:00";//今年开始时间
	 var toDateEnd = $("#mondadata").val()+"-12-31 23:59:59";//今年结束时间
	 var oldDate = daysJian("yyyy",$("#mondadata").val())+"-01-01 00:00:00";//昨年开始时间
	 var oldDateEnd = daysJian("yyyy",$("#mondadata").val())+"-12-31 23:59:59";//昨年开始时间
	initCheckData21('/providerRegistries/queryCollectionPeople',{"service":$("#collection_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},14);
}
//采浆人数柱状图(确定单击时间)
$(document).on("click", "#collection_set", function () {
	 var result= $("#collection_row").val();
	 var toDate = $("#mondadata").val();//今天的时间
	 if(result=='day'){
		 var oldDate = daysJian("yyyy-MM-dd",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryCollectionPeople',{"service":result,"toDate":toDate+" 00:00:00","toDateEnd":toDate+" 23:59:59","oldDate":oldDate+" 00:00:00","oldDateEnd":oldDate+" 23:59:59"},12);
	 }else if(result=='month'){
		 var oldDate = daysJian("yyyy-MM",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryCollectionPeople',{"service":result,"toDate":toDate+"-01 00:00:00","toDateEnd":toDate+"-31 23:59:59","oldDate":oldDate+"-01 00:00:00","oldDateEnd":oldDate+"-31 23:59:59"},13);
	 }else if(result=='year'){
		 var oldDate = daysJian("yyyy",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryCollectionPeople',{"service":result,"toDate":toDate+"-01-01 00:00:00","toDateEnd":toDate+"-12-31 23:59:59","oldDate":oldDate+"-01-01 00:00:00","oldDateEnd":oldDate+"-12-31 23:59:59"},14);
	 }
	  });
//采浆人数柱状图(天份)
 function collectionPage(houra,oldday,today){
	 var myChart8 = echarts.init(document.getElementById("line_container5"));
	 var option8 = {
	 	tooltip: {
	 		trigger: 'axis',
	 		axisPointer: {
	 			type: 'cross',
	 			crossStyle: {
	 				color: '#999'
	 			}
	 		}
	 	},
	 	dataZoom: [{
	 		id: 'dataZoomX',
	 		show: true,
	 		backgroundColor: "rgba(47,69,84,0)",
	 		type: 'slider',
	 		xAxisIndex: 0,
	 		end: 0,
	 		startValue: 8,
	 		endValue: 100,
	 		orient: "horizontal",
	 		zoomLock: false,
	 		zoomOnMouseWheel: true,
	 		moveOnMouseMove: true,
	 	}, ],

	 	toolbox: {
	 		show: true,
	 		feature: {
	 			myTool: {
	 				show: true,
	 				title: '数值显示',
	 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 				onclick: function() {
	 					 var myChart8 = echarts.init(document.getElementById("line_container5"));
	 					 var option8 = {
	 					 	tooltip: {
	 					 		trigger: 'axis',
	 					 		axisPointer: {
	 					 			type: 'cross',
	 					 			crossStyle: {
	 					 				color: '#999'
	 					 			}
	 					 		}
	 					 	},
	 					 	dataZoom: [{
	 					 		id: 'dataZoomX',
	 					 		show: true,
	 					 		backgroundColor: "rgba(47,69,84,0)",
	 					 		type: 'slider',
	 					 		xAxisIndex: 0,
	 					 		end: 0,
	 					 		startValue: 8,
	 					 		endValue: 100,
	 					 		orient: "horizontal",
	 					 		zoomLock: false,
	 					 		zoomOnMouseWheel: true,
	 					 		moveOnMouseMove: true,
	 					 	}, ],

	 					 	toolbox: {
	 					 		show: true,
	 					 		feature: {
	 					 			myTool: {
	 					 				show: true,
	 					 				title: '数值显示',
	 					 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 					 				onclick: function() {
	 					 					collectionPage(houra,oldday,today);
	 					 				}
	 					 			},
	 					 			saveAsImage: {
	 					 				show: true,
	 					 				title: '下载'
	 					 			}
	 					 		}
	 					 	},
	 					 	legend: {
	 					 		data: ['今日数量', '昨日数量']
	 					 	},
	 					 	xAxis: [{
	 					 		type: 'category',
	 					 		data: houra,
	 					 		axisPointer: {
	 					 			type: 'shadow'
	 					 		}
	 					 	}],
	 					 	yAxis: [{
	 					 		type: 'value',
	 					 		name: '数量（人）',
	 					 		min: 0,
	 					 		interval: 0,
	 					 		axisLabel: {
	 					 			formatter: '{value} (人)'
	 					 		}
	 					 	}, ],
	 					 	series: [{
	 					 			name: '今日数量',
	 					 			type: 'bar',
	 						data: today,
	 						itemStyle: {
	 					 				normal: {
	 					 					color: '#ffe00d'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},
	 					 		{
	 					 			name: '昨日数量',
	 					 			type: 'bar',
	 					 			data: oldday,
	 					 			itemStyle: {
	 					 				normal: {
	 					 					color: '#2fc98f'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},

	 					 	]
	 					 };
	 					 myChart8.setOption(option8);
	 				}
	 			},
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		data: ['今日数量', '昨日数量']
	 	},
	 	xAxis: [{
	 		type: 'category',
	 		data: houra,
	 		axisPointer: {
	 			type: 'shadow'
	 		}
	 	}],
	 	yAxis: [{
	 		type: 'value',
	 		name: '数量（人）',
	 		min: 0,
	 		interval: 0,
	 		axisLabel: {
	 			formatter: '{value} (人)'
	 		}
	 	}, ],
	 	series: [{
	 			name: '今日数量',
	 			type: 'bar',
		data: today,itemStyle: {
	 				normal: {
	 					color: '#ffe00d'
	 				}
	 			},
	 		},
	 		{
	 			name: '昨日数量',
	 			type: 'bar',
	 			data: oldday,
	 			itemStyle: {
	 				normal: {
	 					color: '#2fc98f'
	 				}
	 			},
	 		},

	 	]
	 };
	 myChart8.setOption(option8);
}
//采浆人数柱状图(月份)
 function loadDrugsade(houra,oldday,today) {
 	var myChart8 = echarts.init(document.getElementById("line_container5"));
 	var option8 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 			myTool: {
 					show: true,
 					title: '数值隐藏',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart8 = echarts.init(document.getElementById("line_container5"));
 					 	var option8 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 			myTool: {
 					 					show: true,
 					 					title: '数值隐藏',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugsade(houra,oldday,today);	
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['本月数量', '上月数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（g）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '本月数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,itemStyle: {
 					 					normal: {
 					 						color: '#ffe00d'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '上月数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#2fc98f'
 					 					}
 					 				},
 					 			},
 					 		]
 					 	};
 					 	myChart8.setOption(option8);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['本月数量', '上月数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（g）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '本月数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,itemStyle: {
 					normal: {
 						color: '#ffe00d'
 					}
 				},
 			},
 			{
 				name: '上月数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#2fc98f'
 					}
 				},
 			},
 		]
 	};
 	myChart8.setOption(option8);
 }
//采浆人数柱状图(年份)
 function loadDrugxiaowe(houra,oldday,today) {
 	var myChart8 = echarts.init(document.getElementById("line_container5"));
 	var option8 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 			myTool: {
 					show: true,
 					title: '数值显示',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart8 = echarts.init(document.getElementById("line_container5"));
 					 	var option8 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 			myTool: {
 					 					show: true,
 					 					title: '数值显示',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugxiaowe(houra,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['今年数量', '去年数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（人）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (人)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '今年数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,itemStyle: {
 					 					normal: {
 					 						color: '#ffe00d'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '去年数量',
 					 				type: 'bar',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#2fc98f'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart8.setOption(option8);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['今年数量', '去年数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（人）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (人)'
 			}
 		}, ],
 		series: [{
 				name: '今年数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,itemStyle: {
 					normal: {
 						color: '#ffe00d'
 					}
 				},
 			},
 			{
 				name: '去年数量',
 				type: 'bar',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#2fc98f'
 					}
 				},
 			},

 		]
 	};
 	myChart8.setOption(option8);
 }

//采浆重量折线图(天份)单击事件
function plasmAmountDay(){
	clickevent("#plasmAmountC","sxlordara","plasmAmount_set","#plasmAmount_row","day");
	 initDate("#sxlordara");
	 var toDate = $("#sxlordara").val()+" 00:00:00";//今日开始时间
	 var toDateEnd = $("#sxlordara").val()+" 23:59:59";//今日结束时间
	 var oldDate = daysJian("yyyy-MM-dd",$("#sxlordara").val())+" 00:00:00";//昨日开始时间
	 var oldDateEnd = daysJian("yyyy-MM-dd",$("#sxlordara").val())+" 23:59:59";//昨日开始时间
	initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":$("#plasmAmount_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},15);
}
//采浆重量折线图(月份)单击事件
function plasmAmountMonth(){
	clickevent("#plasmAmountC","sxlordara","plasmAmount_set","#plasmAmount_row","month");
	initDateGraphical("#sxlordara",'yyyy-MM','month');
	 var toDate = $("#sxlordara").val()+"-01 00:00:00";//今月开始时间
	 var toDateEnd = $("#sxlordara").val()+"-31 23:59:59";//今月结束时间
	 var oldDate = daysJian("yyyy-MM",$("#sxlordara").val())+"-01 00:00:00";//昨月开始时间
	 var oldDateEnd = daysJian("yyyy-MM",$("#sxlordara").val())+"-31 23:59:59";//昨月开始时间
	initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":$("#plasmAmount_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},16);
}
//采浆重量折线图(年份)单击事件
function plasmAmountYear(){
	clickevent("#plasmAmountC","sxlordara","plasmAmount_set","#plasmAmount_row","year");
	initDateGraphical("#sxlordara",'yyyy','year');
	var toDate = $("#sxlordara").val()+"-01-01 00:00:00";//今年开始时间
	 var toDateEnd = $("#sxlordara").val()+"-12-31 23:59:59";//今年结束时间
	 var oldDate = daysJian("yyyy",$("#sxlordara").val())+"-01-01 00:00:00";//昨年开始时间
	 var oldDateEnd = daysJian("yyyy",$("#sxlordara").val())+"-12-31 23:59:59";//昨年开始时间
	initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":$("#plasmAmount_row").val(),"toDate":toDate,"toDateEnd":toDateEnd,"oldDate":oldDate,"oldDateEnd":oldDateEnd},17);
}
//采浆重量折线图(确定单击时间)
$(document).on("click", "#plasmAmount_set", function () {
	 var result= $("#plasmAmount_row").val();
	 var toDate = $("#sxlordara").val();//今天的时间
	 if(result=='day'){
		 var oldDate = daysJian("yyyy-MM-dd",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":result,"toDate":toDate+" 00:00:00","toDateEnd":toDate+" 23:59:59","oldDate":oldDate+" 00:00:00","oldDateEnd":oldDate+" 23:59:59"},15);
	 }else if(result=='month'){
		 var oldDate = daysJian("yyyy-MM",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":result,"toDate":toDate+"-01 00:00:00","toDateEnd":toDate+"-31 23:59:59","oldDate":oldDate+"-01 00:00:00","oldDateEnd":oldDate+"-31 23:59:59"},16);
	 }else if(result=='year'){
		 var oldDate = daysJian("yyyy",toDate);//昨天的时间
		 initCheckData21('/providerRegistries/queryPlasmAmountPeople',{"service":result,"toDate":toDate+"-01-01 00:00:00","toDateEnd":toDate+"-12-31 23:59:59","oldDate":oldDate+"-01-01 00:00:00","oldDateEnd":oldDate+"-12-31 23:59:59"},17);
	 }
	  });
//采浆重量折线图(天份)
function plasmAmountPage(houra,oldday,today){
	 var myChart10 = echarts.init(document.getElementById("line_container10"));
	 var option10 = {
	 	tooltip: {
	 		trigger: 'axis',
	 		axisPointer: {
	 			type: 'cross',
	 			crossStyle: {
	 				color: '#999'
	 			}
	 		}
	 	},
	 	dataZoom: [{
	 		id: 'dataZoomX',
	 		show: true,
	 		backgroundColor: "rgba(47,69,84,0)",
	 		type: 'slider',
	 		xAxisIndex: 0,
	 		end: 0,
	 		startValue: 8,
	 		endValue: 100,
	 		orient: "horizontal",
	 		zoomLock: false,
	 		zoomOnMouseWheel: true,
	 		moveOnMouseMove: true,
	 	}, ],

	 	toolbox: {
	 		show: true,
	 		feature: {
	 		 myTool: {
	 				show: true,
	 				title: '数值显示',
	 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 				onclick: function() {
	 					var myChart10 = echarts.init(document.getElementById("line_container10"));
	 					 var option10 = {
	 					 	tooltip: {
	 					 		trigger: 'axis',
	 					 		axisPointer: {
	 					 			type: 'cross',
	 					 			crossStyle: {
	 					 				color: '#999'
	 					 			}
	 					 		}
	 					 	},
	 					 	dataZoom: [{
	 					 		id: 'dataZoomX',
	 					 		show: true,
	 					 		backgroundColor: "rgba(47,69,84,0)",
	 					 		type: 'slider',
	 					 		xAxisIndex: 0,
	 					 		end: 0,
	 					 		startValue: 8,
	 					 		endValue: 100,
	 					 		orient: "horizontal",
	 					 		zoomLock: false,
	 					 		zoomOnMouseWheel: true,
	 					 		moveOnMouseMove: true,
	 					 	}, ],

	 					 	toolbox: {
	 					 		show: true,
	 					 		feature: {
	 					 		 myTool: {
	 					 				show: true,
	 					 				title: '数值显示',
	 					 				icon: 'image://http://echarts.baidu.com/images/favicon.png',
	 					 				onclick: function() {
	 					 					plasmAmountPage(houra,oldday,today);
	 					 				}
	 					 			}, 
	 					 			saveAsImage: {
	 					 				show: true,
	 					 				title: '下载'
	 					 			}
	 					 		}
	 					 	},
	 					 	legend: {
	 					 		data: ['今日数量', '昨日数量']
	 					 	},
	 					 	xAxis: [{
	 					 		type: 'category',
	 					 		data: houra,
	 					 		axisPointer: {
	 					 			type: 'shadow'
	 					 		}
	 					 	}],
	 					 	yAxis: [{
	 					 		type: 'value',
	 					 		name: '数量（kg）',
	 					 		min: 0,
	 					 		interval: 0,
	 					 		axisLabel: {
	 					 			formatter: '{value} (kg)'
	 					 		}
	 					 	}, ],
	 					 	series: [{
	 					 			name: '今日数量',
	 					 			type: 'line',

	 					 			data: today,	
	 					 			itemStyle: {
	 					 				normal: {
	 					 					color: '#a855ee'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},
	 					 		{
	 					 			name: '昨日数量',
	 					 			type: 'line',
	 					 			data: oldday,
	 					 			itemStyle: {
	 					 				normal: {
	 					 					color: '#e81818'
	 					 				}
	 					 			},
	 					 			label: {
	 				 					normal: {
	 				 						show: true,
	 				 						position: 'top'
	 				 					}
	 				 				},
	 					 		},

	 					 	]
	 					 };
	 					 myChart10.setOption(option10);	
	 				}
	 			}, 
	 			saveAsImage: {
	 				show: true,
	 				title: '下载'
	 			}
	 		}
	 	},
	 	legend: {
	 		data: ['今日数量', '昨日数量']
	 	},
	 	xAxis: [{
	 		type: 'category',
	 		data: houra,
	 		axisPointer: {
	 			type: 'shadow'
	 		}
	 	}],
	 	yAxis: [{
	 		type: 'value',
	 		name: '数量（kg）',
	 		min: 0,
	 		interval: 0,
	 		axisLabel: {
	 			formatter: '{value} (kg)'
	 		}
	 	}, ],
	 	series: [{
	 			name: '今日数量',
	 			type: 'line',

	 			data: today,	
	 			itemStyle: {
	 				normal: {
	 					color: '#a855ee'
	 				}
	 			},
	 			label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
	 		},
	 		{
	 			name: '昨日数量',
	 			type: 'line',
	 			data: oldday,
	 			itemStyle: {
	 				normal: {
	 					color: '#e81818'
	 				}
	 			},
	 			label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
	 		},

	 	]
	 };
	 myChart10.setOption(option10);	
}
//采浆重量折线图(月份)
 function loadDrugju(houra,oldday,today) {
 	var myChart10 = echarts.init(document.getElementById("line_container10"));
 	var option10 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 			myTool: {
 					show: true,
 					title: '数值隐藏',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart10 = echarts.init(document.getElementById("line_container10"));
 					 	var option10 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 			myTool: {
 					 					show: true,
 					 					title: '数值隐藏',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugju(houra,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['本月数量', '上月数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data:houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（kg）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (kg)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '本月数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#a855ee'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '上月数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#e81818'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart10.setOption(option10);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['本月数量', '上月数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data:houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（kg）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (kg)'
 			}
 		}, ],
 		series: [{
 				name: '本月数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,
 				itemStyle: {
 					normal: {
 						color: '#a855ee'
 					}
 				},
 			},
 			{
 				name: '上月数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#e81818'
 					}
 				},
 			},

 		]
 	};
 	myChart10.setOption(option10);
 }
//采浆重量折线图(年份)
 function loadDrugxiki(houra,oldday,today) {
 	var myChart10 = echarts.init(document.getElementById("line_container10"));
 	var option10 = {
 		tooltip: {
 			trigger: 'axis',
 			axisPointer: {
 				type: 'cross',
 				crossStyle: {
 					color: '#999'
 				}
 			}
 		},
 		dataZoom: [{
 			id: 'dataZoomX',
 			show: true,
 			backgroundColor: "rgba(47,69,84,0)",
 			type: 'slider',
 			xAxisIndex: 0,
 			end: 0,
 			startValue: 8,
 			endValue: 100,
 			orient: "horizontal",
 			zoomLock: false,
 			zoomOnMouseWheel: true,
 			moveOnMouseMove: true,
 		}, ],

 		toolbox: {
 			show: true,
 			feature: {
 				myTool: {
 					show: true,
 					title: '数值显示',
 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					onclick: function() {
 						var myChart10 = echarts.init(document.getElementById("line_container10"));
 					 	var option10 = {
 					 		tooltip: {
 					 			trigger: 'axis',
 					 			axisPointer: {
 					 				type: 'cross',
 					 				crossStyle: {
 					 					color: '#999'
 					 				}
 					 			}
 					 		},
 					 		dataZoom: [{
 					 			id: 'dataZoomX',
 					 			show: true,
 					 			backgroundColor: "rgba(47,69,84,0)",
 					 			type: 'slider',
 					 			xAxisIndex: 0,
 					 			end: 0,
 					 			startValue: 8,
 					 			endValue: 100,
 					 			orient: "horizontal",
 					 			zoomLock: false,
 					 			zoomOnMouseWheel: true,
 					 			moveOnMouseMove: true,
 					 		}, ],

 					 		toolbox: {
 					 			show: true,
 					 			feature: {
 					 				myTool: {
 					 					show: true,
 					 					title: '数值显示',
 					 					icon: 'image://http://echarts.baidu.com/images/favicon.png',
 					 					onclick: function() {
 					 						loadDrugxiki(houra,oldday,today);
 					 					}
 					 				},
 					 				saveAsImage: {
 					 					show: true,
 					 					title: '下载'
 					 				}
 					 			}
 					 		},
 					 		legend: {
 					 			data: ['今年数量', '去年数量']
 					 		},
 					 		xAxis: [{
 					 			type: 'category',
 					 			data: houra,
 					 			axisPointer: {
 					 				type: 'shadow'
 					 			}
 					 		}],
 					 		yAxis: [{
 					 			type: 'value',
 					 			name: '数量（kg）',
 					 			min: 0,
 					 			interval: 0,
 					 			axisLabel: {
 					 				formatter: '{value} (kg)'
 					 			}
 					 		}, ],
 					 		series: [{
 					 				name: '今年数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: today,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#a855ee'
 					 					}
 					 				},
 					 			},
 					 			{
 					 				name: '去年数量',
 					 				type: 'line',
 					 				label: {
 					 					normal: {
 					 						show: true,
 					 						position: 'top'
 					 					}
 					 				},
 					 				data: oldday,
 					 				itemStyle: {
 					 					normal: {
 					 						color: '#e81818'
 					 					}
 					 				},
 					 			},

 					 		]
 					 	};
 					 	myChart10.setOption(option10);
 					}
 				},
 				saveAsImage: {
 					show: true,
 					title: '下载'
 				}
 			}
 		},
 		legend: {
 			data: ['今年数量', '去年数量']
 		},
 		xAxis: [{
 			type: 'category',
 			data: houra,
 			axisPointer: {
 				type: 'shadow'
 			}
 		}],
 		yAxis: [{
 			type: 'value',
 			name: '数量（kg）',
 			min: 0,
 			interval: 0,
 			axisLabel: {
 				formatter: '{value} (kg)'
 			}
 		}, ],
 		series: [{
 				name: '今年数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: today,
 				itemStyle: {
 					normal: {
 						color: '#a855ee'
 					}
 				},
 			},
 			{
 				name: '去年数量',
 				type: 'line',
 				label: {
 					normal: {
 						show: false,
 						position: 'top'
 					}
 				},
 				data: oldday,
 				itemStyle: {
 					normal: {
 						color: '#e81818'
 					}
 				},
 			},

 		]
 	};
 	myChart10.setOption(option10);
 }

 /**
  * 公共方法
  * @param divId
  * @param dateId
  * @param determine
  */
function clickevent(divId,dateId,determine,result,date){
	$(result).val(date);
 	$(divId).empty();
 	$(divId).append('<input id='+dateId+' name='+dateId+' class="form-control newfrin" />'+
 	'<button type="button" id='+determine+' class="btn btn-info">确定</button>');
 }