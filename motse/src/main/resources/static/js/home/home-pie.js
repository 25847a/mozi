//Vue实例有一个完整的生命周期，也就是从开始创建、初始化数据、编译模板、挂载Dom、渲染→更新→渲染、卸载等一系列过程，我们称这是Vue的生命周期。
var pie =new Vue({
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
	//实例创建完成后执行的钩子
	created() {
		this.goback();
	},
	//编译好的HTML挂载到页面完成后执行的事件钩子，此钩子函数中一般会做一些ajax请求数据进行数据初始化
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.myChart = echarts.init(document.getElementById('echartPie'));// 第一个饼状图
		this.pieChart = echarts.init(document.getElementById('echartPieContainer'));//第二个饼状图
	////	console.log('this.yAxis', this.yAxis)
	/////	console.log('this.xAxis', this.xAxis)
	},
	//在该方法声明方法：
	methods: {
		goback: function () {
			axios.get(GetURLInfo()+"/admin/verifyRegister");
		},
		// 第一个饼状图
		initPieEchart(a,b) {
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
								{ value: a, name: '在线', itemStyle: { color: '#2ec7c9' } },
								{ value: b, name: '离线', itemStyle: { color: '#8d98b3' } }
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