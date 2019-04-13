new Vue({
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

	created() {
		this.goback()
	},
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.zstChart = echarts.init(document.getElementById('echartzst'));
		// console.log('this.yAxis', this.yAxis)
		// console.log('this.xAxis', this.xAxis)
	},
	methods: {
		goback: function () {
			// console.log("11")
			axios.get("https://www.apiopen.top/findStatistics?appKey=00d91e8e0cca2b76f515926a36db68f5").then(this.getnew)
		},
		getnew(res) {
			let data = res.data.data
			for (let i = 0; i < data.length; i++) {
				if (this.xAxis.indexOf(data[i].type) == -1) {
					this.xAxis.push(data[i].type)
					if (data[i].count < 10000) {
						this.yAxis.push(data[i].count * 100000)
					} else {
						this.yAxis.push(data[i].count)
					}
					this.pieDatArray.push({ value: data[i].count, name: data[i].type })

				}
			}
			this.initzst()
			// console.log('this.xAxis', this.xAxis)
			// console.log('this.yAxis', this.yAxis)

		},
		initzst() {
			let option = {
				title: {
					text: '心率统计图',
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
					data: ['>49次分', '50-59次分', '60-69次分', '70-79次分', '80-89次分', '90-100次分', '>100次分']
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
						data: [40, 80, 130, 150, 50, 60, 85],
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
						data: [65, 50, 100, 20, 10, 75, 80],
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