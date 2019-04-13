new Vue({
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
	created() {
		this.goback()
	},
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.lastChart = echarts.init(document.getElementById('breathechart'));
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
			this.initlast()
			// console.log('this.xAxis', this.xAxis)
			// console.log('this.yAxis', this.yAxis)

		},
		initlast() {
			let option = {				
				title: {
					text: '呼吸频率',
					left: 'center',
					top: 20,
					textStyle: {
						
					}
				},				
				series: [
					{
						name: '访问来源',
						type: 'pie',
						radius: '55%',
						center: ['50%', '50%'],
						data: [
							{ value: 274, name: '＜12次/分钟', itemStyle: { color: '#ffb980' } },
							{ value: 350, name: '16-20次/分钟', itemStyle: { color: '#2ec7c9' } },
							{ value: 274, name: '＞24次/分钟', itemStyle: { color: '#d87a80' } }
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