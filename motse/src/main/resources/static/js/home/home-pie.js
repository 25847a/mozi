new Vue({
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
	created() {
		this.goback()
	},
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.myChart = echarts.init(document.getElementById('echartPie'));
		this.pieChart = echarts.init(document.getElementById('echartPieContainer'));
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
			this.initPieEchart()
			this.initPie()
			// console.log('this.xAxis', this.xAxis)
			// console.log('this.yAxis', this.yAxis)

		},
		// 第一个饼状图
		initPieEchart() {
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
						name: '访问来源',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data:/*this.pieDatArray, */
							[
								{ value: 255, name: '在线', itemStyle: { color: '#2ec7c9' } },
								{ value: 25, name: '离线', itemStyle: { color: '#8d98b3' } }
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
		initPie() {
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
						name: '访问来源',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data:/*this.pieDatArray,*/
							[
								{ value: 255, name: '男性', itemStyle: { color: '#5ab1ef' } },
								{ value: 25, name: '女性', itemStyle: { color: '#ffb980' } }
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