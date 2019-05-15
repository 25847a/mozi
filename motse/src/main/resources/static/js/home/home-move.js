var move = new Vue({
	el: "#move",
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
		this.zstChart = echarts.init(document.getElementById('echartzst'));
	},
	methods: {
		initzst(heartrateBoy,heartrateGirl) {
			let option = {
				title: {
					text: '心率统计图表',
				},
				tooltip: {
					trigger: 'axis'
				},
				color:["#5ab1ef","#ffb980"],
				legend: {
					data: ['男性', '女性']
				},
				
				xAxis: {
					type: 'category',
					boundaryGap: false,
					data: ['<49', '50-59', '60-69', '70-79', '80-89', '90-100', '>100(次分)']
				},
				yAxis: {
					name: '(人)',
					type: 'value',
					axisLabel: {
						formatter: '{value}'
					}
				},
				series: [
					{
						name: '男性',
						type: 'line',
						data: heartrateBoy,
						markPoint: {
							data: [
								{ type: 'max', name: '最大值' },
								{ type: 'min', name: '最小值' }
							]
						},
						lineStyle:{
							normal:{
								color:"#5ab1ef"
							}
						}
					},
					{
						name: '女性',
						type: 'line',
						data: heartrateGirl,
						markPoint: {
							data: [
								{ type: 'max', name: '最大值' },
								{ type: 'min', name: '最小值' }
							]
						},
						lineStyle:{
							normal:{
								color:"#ffb980"
							}
						}
					}
				]
			};
			this.zstChart.setOption(option)
		}
	}

})