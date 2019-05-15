var ring = new Vue({
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
	mounted() {
		// 基于准备好的dom，初始化echarts实例
		this.pineChart = echarts.init(document.getElementById('echartring'));
	},
	methods: {
		
		initPineEchart(microcirculation) {
			let option = {
					tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    color:["#2ec7c9","#ffb980","#5ab1ef"],
				    legend: {
				        x: 'center',
				        data: ['50-70(%):'+microcirculation.microcirculation3+'人','70-80(%):'+microcirculation.microcirculation2+'人','>80(%):'+microcirculation.microcirculation1+'人'		] 
				    },
				    series: [

				        {
				            name: '墨子星',
				            type: 'pie',
				            radius: ['30%', '55%'],
				            labelLine: {
				                normal: {
				                    length: 20,
				                    length2: 70,
				                    lineStyle: {
				                        color: '#333'
				                    }
				                }
				            },
				      /*   label: {
				                normal: {
				                    formatter: '{b|{b}}{a|{d}%}\n\n',
				                    borderWidth: 20,
				                    borderRadius: 4,
				                    padding: [0, -70],
				                    rich: {
				                        a: {
				                            color: '#333',
				                            fontSize: 12,
				                            lineHeight: 20
				                        },
				                        b: {
				                            fontSize: 12,
				                            lineHeight: 20,
				                            color: '#333'
				                        }
				                    }
				                }
				            },*/
				          /*  data: [{
				                value: 10,
				                name: '50-70'
				            }, {
				                value: 30,
				                name: '70-80'
				            }, {
				                value: 70,
				                name: '>80'
				            }]*/
				            data: [{
				                value: microcirculation.microcirculation3,
				                name: '50-70(%):'+microcirculation.microcirculation3+'人'
				            }, {
				                value: microcirculation.microcirculation2,
				                name: '70-80(%):'+microcirculation.microcirculation2+'人'
				            }, {
				                value: microcirculation.microcirculation1,
				                name: '>80(%):'+microcirculation.microcirculation1+'人'
				            }]
				        }
				    ]
			};

			this.pineChart.setOption(option)
		},
	}

})