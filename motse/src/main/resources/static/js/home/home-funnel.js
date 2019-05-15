var funnel = new Vue({
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
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.funnelChart = echarts.init(document.getElementById('echartfunnel'));
		// console.log('this.yAxis', this.yAxis)
		// console.log('this.xAxis', this.xAxis)
	},
	methods: {
		initfunnel(blood) {
			console.log(blood);
			let option = {
				title : {
			        subtext: '收缩压    舒张压',
			        x: '55%',
			        align: 'right'
			    },
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c}"
				},
				color: ['#8d98b3','#d87a80','#2ec7c9','#ffb980', '#b6a2de', '#5ab1ef', ],
				legend: {
					type: 'scroll',
					orient: 'vertical',
					left: 10,
					top: 20,
					bottom: 20,
//'＞180/＞110:'+blood.blood6+'人','160-179/100-109:'+blood.blood5+'人','140-159/90-99:'+blood.blood4+'人','120-139/80-89:'+blood.blood3+'人','90-120/60-80:'+blood.blood2+'人','＜90/＜60:'+blood.blood1+'人']
					data: ['＜90/＜60(mmHg):'+blood.blood1+'人','90-120/60-80(mmHg):'+blood.blood2+'人','120-139/80-89(mmHg):'+blood.blood3+'人','140-159/90-99(mmHg):'+blood.blood4+'人','160-179/100-109(mmHg):'+blood.blood5+'人','＞180/＞110(mmHg):'+blood.blood6+'人']
				},
				calculable: true,
				series: [
					{
						name: '漏斗图',
						type: 'funnel',
						left: '25%',
						top: 30,
						//x2: 80,
						bottom: 60,
						width: '70%',
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
							{ value: blood.blood1, name: '＜90/＜60(mmHg):'+blood.blood1+'人'},
							{ value: blood.blood2, name: '90-120/60-80(mmHg):'+blood.blood2+'人'},
							{ value: blood.blood3, name: '120-139/80-89(mmHg):'+blood.blood3+'人'},
							{ value: blood.blood4, name: '140-159/90-99(mmHg):'+blood.blood4+'人'},
							{ value: blood.blood5, name: '160-179/100-109(mmHg):'+blood.blood5+'人' },
							{ value: blood.blood6, name: '＞180/＞110(mmHg):'+blood.blood6+'人' }
						]
					}
				]
			};
			this.funnelChart.setOption(option)
		}
	}

})