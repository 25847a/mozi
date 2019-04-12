new Vue({
	el: "#funnel",
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
		this.funnelChart = echarts.init(document.getElementById('echartfunnel'));
	/////	console.log('this.yAxis', this.yAxis)
	/////	console.log('this.xAxis', this.xAxis)
	},
	methods: {
		goback: function () {
		/////	console.log("11")
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
			this.initfunnel()
			// console.log('this.xAxis', this.xAxis)
			// console.log('this.yAxis', this.yAxis)

		},
		initfunnel() {
			let option = {
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c}%"
				},
				legend: {
					data: ['＜90/60', '90-120/60-80', '120-139/80-89', '140-159/90-99', '160-179/100-109','＞180/110']
				},
				calculable: true,
				series: [
					{
						name: '漏斗图',
						type: 'funnel',
						left: '10%',
						top: 60,
						//x2: 80,
						bottom: 60,
						width: '80%',
						// height: {totalHeight} - y - y2,
						min: 0,
						max: 100,
						minSize: '0%',
						maxSize: '100%',
						sort: 'descending',
						gap: 2,
						label: {
							show: true,
							position: 'inside'
						},
						labelLine: {
							length: 10,
							lineStyle: {
								width: 1,
								type: 'solid'
							}
						},
						itemStyle: {
							borderColor: '#fff',
							borderWidth: 1
						},
						emphasis: {
							label: {
								fontSize: 20
							}
						},
						data: [
							{ value: 20, name: '＞180/110' },
							{ value: 40, name: '160-179/100-109' },
							{ value: 60, name: '＜90/60' },
							{ value: 80, name: '140-159/90-99' },
							{ value: 100, name: '90-120/60-80' },
							{ value: 120, name: '120-139/80-89' }
						]
					}
				]
			};
			this.funnelChart.setOption(option)
		}
	}

})