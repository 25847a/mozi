var last =new Vue({
	el: "#last",
	data() {
		return {
			xAxis: [],
			yAxis: [],
			myChart: null,
			pieChart: null,
			pieDatArray: []
		}
	},
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.lastChart = echarts.init(document.getElementById('breathechart'));
	},
	methods: {
		initlast(respirationrate) {
			let option = {				
				title: {
					text: '',
					left: 'center',
					top: 20,
					textStyle: {
						
					}
				},	
				legend: {
					  x: 'center',
					   data:['＜12次/分钟:'+respirationrate.respirationrate3+'人','12-24次/分钟:'+respirationrate.respirationrate2+'人','＞24次/分钟:'+respirationrate.respirationrate1+'人']
					                },
				tooltip:{
					trigger:'item',
					formatter:"{a} <br/>{b} : {c} ({d}%)"
				},
				series: [
					{
						name: '墨子星',
						type: 'pie',
						radius: '55%',
						center: ['50%', '50%'],
						data: [
							/*	{ value: respirationrate.respirationrate3, name: '＜12次/分钟', itemStyle: { color: '#ffb980' } },
							{ value: respirationrate.respirationrate2, name: '12-24次/分钟', itemStyle: { color: '#2ec7c9' } },
							{ value: respirationrate.respirationrate1, name: '＞24次/分钟', itemStyle: { color: '#d87a80' } }*/
							{ value: respirationrate.respirationrate3, name: '＜12次/分钟:'+respirationrate.respirationrate3+'人', itemStyle: { color: '#ffb980' } },
							{ value: respirationrate.respirationrate2, name: '12-24次/分钟:'+respirationrate.respirationrate2+'人', itemStyle: { color: '#2ec7c9' } },
							{ value: respirationrate.respirationrate1, name: '＞24次/分钟:'+respirationrate.respirationrate1+'人', itemStyle: { color: '#d87a80' } }
						].sort(function (a, b) { return a.value - b.value; }),
						roseType: 'radius',
						animationType: 'scale',
						animationEasing: 'elasticOut',
						animationDelay: function (idx) {
							return Math.random() * 200;
						}
					}
				]
			};
			this.lastChart.setOption(option)
		}
	}

})