new Vue({
	el: "#echring",
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

		this.pineChart = echarts.init(document.getElementById('echartring'));

		////console.log('this.yAxis', this.yAxis)
	/////	console.log('this.xAxis', this.xAxis)

	},
	methods: {
		goback: function () {
		//////	console.log("11")
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
			this.initPineEchart()
		},
		initPineEchart() {
			let option = {
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b}: {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					x: 'left',
					data:['50-70','70-80','>80'] 
					//this.xAxis
				},
				series: [
					{
						name: '访问来源',
						type: 'pie',
						radius: ['50%', '70%'],
						avoidLabelOverlap: false,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: true,
								textStyle: {
									fontSize: '30',
									fontWeight: 'bold'
								}
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data: [
							{ value: 10, name: '50-70' },
							{ value: 30, name: '70-80' },
							{ value: 70, name: '>80' }
						],
						// data: this.pieDatArray
					}
				]
			};

			this.pineChart.setOption(option)
		},
	}

})