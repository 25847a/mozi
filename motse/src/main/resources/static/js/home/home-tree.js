var tree = new Vue({
	el: "#echartree",
	data() {
		return {
			xAxis: [],
			yAxis: [],
			myChart: null,
			pieChart: null
		}
	},
	created() {
		//this.goback();
	},
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.treChart = echarts.init(document.getElementById('treechart'));
	},
	methods: {
		inittree(bloodoxygen) {
			let option = {
				tooltip: {
					trigger: 'axis',
					axisPointer: {            // 坐标轴指示器，坐标轴触发有效
						type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					}
				},
				legend: {
					data:['男性', '女性']
				},
				xAxis: [
					{
						name: '(%)',
						type: 'category',
						data: ['', '<94', '', '94-99', ''],
						axisTick: {
							alignWithLabel: true
						}
					}
				],
				yAxis: [
					{
						name: '(人)',
						type: 'value'
					}
				],
				series: [
					{
						name: '男性',
						type: 'bar',
						barWidth: '60%',
						data: [ ,bloodoxygen[0], ,bloodoxygen[1], ],
						itemStyle: {
							normal: {
								//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
								color: function (params) {
									// build a color map as your need.
									var colorList = [
										'#5ab1ef', '#5ab1ef', '#5ab1ef', '#5ab1ef', '#5ab1ef', '#5ab1ef',
										'#5ab1ef'
									];
									return colorList[params.dataIndex]
								},
							}
						},
						label: {
							show: true,
							position: 'top',
							//      formatter: '{c}'
							//formatter: '{b}\n{c}'
						}
					},
					{
						name: '女性',
						type: 'bar',
						barWidth: '60%',
						data: [ ,bloodoxygen[2], ,bloodoxygen[3], ],
						itemStyle: {
							normal: {
								//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
								color: function (params) {
									// build a color map as your need.
									var colorList = [
										'#ffb980', '#ffb980', '#ffb980', '#ffb980', '#ffb980', '#ffb980',
										'#ffb980'
									];
									return colorList[params.dataIndex]
								},
							}
						},
						label: {
							show: true,
							position: 'top',
							//      formatter: '{c}'
							//formatter: '{b}\n{c}'
						}
					}
				]
			};
			this.treChart.setOption(option)
		}
	}

})