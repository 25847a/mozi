var pie = new Vue({
	el: "#abb",
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
		this.myChart = echarts.init(document.getElementById('echartPie'));
		this.pieChart = echarts.init(document.getElementById('echartPieContainer'));
	},
	methods: {
		// 第一个饼状图
		initPieEchart(offline,online) {
			let option = {

				title: {
					text: '设备使用状态',
					x: 'center'
				},
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					left: 'left',
					data: ['在线', '离线']
				},
				series: [
					{
						name: '墨子星',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data:/*this.pieDatArray, */
							[
								{ value: offline, name: '在线:'+offline+'人', itemStyle: { color: '#2ec7c9' } },
								{ value: online, name: '离线:'+online+'人', itemStyle: { color: '#8d98b3' } }
							],
						itemStyle: {
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
			this.pieChart.setOption(option)
		},
		//第二个饼状图
		initPie(x,y) {
			let option = {

				title: {
					text: '用户男女比例',
					x: 'center'
				},
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					left: 'left',
					data: ['男性', '女性']
				},
				series: [
					{
						name: '墨子星',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data:/*this.pieDatArray,*/
							[
								{ value: x, name: '男性:'+x+'人', itemStyle: { color: '#5ab1ef' } },
								{ value: y, name: '女性'+y+'人', itemStyle: { color: '#ffb980' } }
							],
						itemStyle: {
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};
			this.myChart.setOption(option)
		}
	},
})