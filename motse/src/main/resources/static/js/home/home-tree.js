new Vue({
	el: "#echartree",
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
		this.treChart = echarts.init(document.getElementById('treechart'));
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
			this.inittree()
			// console.log('this.xAxis', this.xAxis)
			// console.log('this.yAxis', this.yAxis)

		},
		inittree() {
			let option = {
				xAxis: {
					name: '(%)',
					type: 'category',
					data: ['', '<94男性', '<94女性', '', '94-99女性', '94-99男性', '']
				},
				yAxis: {
					name: '(人)',
					type: 'value'
				},
				series: [{
					data: [ , 1600, 1700, , 3800, 3400, ],
					type: 'bar',
					itemStyle: {
						normal: {
							//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
							color: function (params) {
								// build a color map as your need.
								var colorList = [
									'#C1232B', '#5ab1ef', '#ffb980', '#E87C25', '#ffb980',
									'#5ab1ef'
								];
								return colorList[params.dataIndex]
							},
							//以下为是否显示，显示位置和显示格式的设置了
							label: {
								show: true,
								position: 'top',
								//      formatter: '{c}'
								formatter: '{b}\n{c}'
							}
						}
					},
				}]
			};
			this.treChart.setOption(option)
		}
	}

})