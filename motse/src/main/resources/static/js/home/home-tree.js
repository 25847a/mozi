var tree = new Vue({
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
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.treChart = echarts.init(document.getElementById('treechart'));
	},
	methods: {
		inittree(bloodoxygen) {
			let option = {
				xAxis: {
					name: '(%)',
					type: 'category',
					data: ['', '<94男性','<94女性','','94-99女性','94-99男性', '']
				},
				yAxis: {
					name: '(人)',
					type: 'value'
				},
				series: [{
					data: bloodoxygen,
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