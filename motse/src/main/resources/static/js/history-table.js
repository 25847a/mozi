new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小红', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小蓝', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小芳', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小春', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小明', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小芬', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小艾', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小燕', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小丽', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小华', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小聪', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小飞', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小环', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小李', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '小白', 'age': '77', 'rate': '125', 'gender': '67', 'low': '65', 'hobby': '65', 'breathe': '12', 'blood': '96', 'address': '56', 'time': '2019/3/22 17:33' },
                ],
                columns: [
                    { field: 'imei', width: 150, columnAlign: 'center', isFrozen: true },
                    { field: 'custome', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'name', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'rate', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'gender', width: 100, columnAlign: 'center', isFrozen: false, isResize: true },
                    { field: 'low', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'hobby', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'breathe', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'address', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 200, columnAlign: 'center', isResize: true }

                ],
                titleRows: [

                    [
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['rate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '高压', titleAlign: 'center' },
                        { fields: ['low'], title: '低压', titleAlign: 'center' },
                        { fields: ['hobby'], title: '微循环', titleAlign: 'center' },
                        { fields: ['breathe'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['blood'], title: '血氧', titleAlign: 'center' },
                        { fields: ['address'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' }
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
            this.getTableData();
            console.log(pageIndex)
        },
        pageSizeChange(pageSize) {

            this.pageIndex = 1;
            this.pageSize = pageSize;
            this.getTableData();
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
            console.log('haha');
            axios.post("https://www.apiopen.top/satinApi?type=1&page=1").then(this.getnew)

        },
        getnew(res){
            let data = res.data.data
            console.log(data)
            this.item = data
            // console.log(this.item)
        }
    },
    created() {
        this.getTableData();
    },
    mounted(){
        this.goback();
        // this.item = setInterval(this.goback,3000)
    },
    beforeDestroy(){
        clearInterval(this.item);
    }
})