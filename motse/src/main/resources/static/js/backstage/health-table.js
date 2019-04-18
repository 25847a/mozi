new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：80，呼吸频率：21。 1、心率:80，心率正常。 2、血氧饱和度:90，血氧饱和度较低，呼吸系统可能存在异常，长时间低氧会对神经系统造成不可逆的损伤。 3、血压:153/99，血压偏高，可伴有心、脑、肾等器官的功能或器质性损害的临床综合征。 4、微循环:82，血管指数正常。 5、心率变异性:80，心率变异性正常。 6、心律失常检测:0，无心律失常。 建议:增加有氧运动，例如慢跑、打太极等。戒烟、戒酒。吃富含花椰菜和菠菜等绿叶蔬菜的健康饮食，避免由于贫血造成血氧不足。调节饮食，补钾排钠，增加粗粮摄入。要学会定期测血压，注意别再运动后测量血压，这种情况下血压升高是正常现象。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    { 'imei': '862237048336833', 'custome': '28681', 'rate':'81', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'heartbeat': '56', 'presentation':'HRV：0，呼吸频率：12。 数据采集失败，请重新检测！注意，按压均匀，避免漏光。' ,'time': '2019/3/22 17:33' },
                    
                ],
                columns: [
                    { field: 'imei', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'custome', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'rate', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'gender', width: 80, columnAlign: 'center',  isResize: true },
                    { field: 'low', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'hobby', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'breathe', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'heartbeat', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'presentation', width: 500, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 220, columnAlign: 'center', isResize: true }

                ],
                titleRows: [
                    [
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['custome'], title: '用户ID', titleAlign: 'center' },
                        { fields: ['rate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '高压', titleAlign: 'center' },
                        { fields: ['low'], title: '低压', titleAlign: 'center' },
                        { fields: ['hobby'], title: '微循环', titleAlign: 'center' },
                        { fields: ['breathe'], title: '呼吸频率', titleAlign: 'center' },
                        { fields: ['blood'], title: '血氧', titleAlign: 'center' },
                        { fields: ['heartbeat'], title: '心跳异常', titleAlign: 'center' },
                        { fields: ['presentation'], title: '体检报告', titleAlign: 'center' },
                        { fields: ['time'], title: '添加时间', titleAlign: 'center' }
                    ],
                ],
            }
        }
    },
    methods: {
        getTableData() {

            this.tableConfig.tableData = tableDate.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize)
        },
        pageChange(pageIndex) {

            this.pageIndex = pageIndex;
            // this.getTableData();
            console.log(pageIndex)
        },
        pageSizeChange(pageSize) {

            this.pageIndex = 1;
            this.pageSize = pageSize;
            // this.getTableData();
        },
        sortChange(params) {

            if (params.height.length > 0) {

                this.tableConfig.tableData.sort(function (a, b) {

                    if (params.height === 'asc') {

                        return a.height - b.height;
                    } else if (params.height === 'desc') {

                        return b.height - a.height;
                    } else {

                        return 0;
                    }
                });
            }
        },
        goback:function(){
            // console.log('haha');
            axios.post("https://www.apiopen.top/satinApi?type=1&page=1").then(this.getnew)

        },
        getnew(res){
            let data = res.data.data
            // console.log(data)
            this.item = data
            // console.log(this.item)
        }
    },
    // created() {
    //     this.getTableData();
    // },
    mounted(){
        this.goback();
        // this.item = setInterval(this.goback,3000)
    },
    beforeDestroy(){
        clearInterval(this.item);
    }
})