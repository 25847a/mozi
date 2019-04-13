new Vue({
    el: '#app',
    data() {
        return {            
            pageIndex: 1,
            pageSize: 20,
            tab: 1,
            tableConfig1: {
                multipleSort: false,
                tableData: [
                    { 'love': '', 'id': '1', 'name': '小红红','hobby':'15', 'gender': '112', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88','step':'11223', 'time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小明明', 'hobby':'55','gender': '2', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小春春', 'hobby':'85','gender': '52', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小李李', 'hobby':'95','gender': '192', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小丽丽', 'hobby':'605','gender': '52', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小男男', 'hobby':'65','gender': '62', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小亲亲', 'hobby':'685','gender': '82', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小弹单', 'hobby':'165','gender': '22', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小高高', 'hobby':'565','gender': '92', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小阿啊', 'hobby':'365','gender': '72', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小人人','hobby':'765', 'gender': '132', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '1', 'name': '小额额','hobby':'650', 'gender': '121', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                ],
                columns: [
                    { field: 'love', width: 50, columnAlign: 'center', isFrozen: true },
                    {
                        field: 'custome', width: 50, titleAlign: 'center', columnAlign: 'center',
                        formatter: function (rowData, index, pagingIndex) {
                            var currentIndex = index + pagingIndex;
                            return currentIndex < 3 ? '<span style="color:red;font-weight: bold;">' + (currentIndex + 1) + '</span>' : currentIndex + 1
                        }, isFrozen: true
                    },
                    { field: 'name', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'rate', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'gender', width: 80, columnAlign: 'center',  isResize: true },
                    { field: 'address', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'hobby', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'breathe', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'step', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 100, columnAlign: 'center', isResize: true }
                ],
                titleRows: [

                    [
                        { fields: ['love'], title: '', titleAlign: 'center' },
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['rate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '血压', titleAlign: 'center' },
                        { fields: ['address'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['hobby'], title: '微循环', titleAlign: 'center' },
                        { fields: ['blood'], title: '血氧', titleAlign: 'center' },
                        { fields: ['breathe'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['step'], title: '计步', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' }
                    ],
                ],
            },
            tableConfig2: {
                multipleSort: false,
                tableData: [
                    { 'love': '❤', 'id': '1', 'name': '小红','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88','step':'11223', 'time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小明', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小春', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小李', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小丽', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小男', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小亲', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小弹', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小高', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小阿', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小人','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '❤', 'id': '1', 'name': '小额','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                ],
                columns: [
                    { field: 'love', width: 50, columnAlign: 'center', isFrozen: true },
                    {
                        field: 'custome', width: 50, titleAlign: 'center', columnAlign: 'center',
                        formatter: function (rowData, index, pagingIndex) {
                            var currentIndex = index + pagingIndex;
                            return currentIndex < 3 ? '<span style="color:red;font-weight: bold;">' + (currentIndex + 1) + '</span>' : currentIndex + 1
                        }, isFrozen: true
                    },
                    { field: 'name', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'rate', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'gender', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'address', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'hobby', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'breathe', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'step', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 100, columnAlign: 'center', isResize: true }
                ],
                titleRows: [

                    [
                        { fields: ['love'], title: '', titleAlign: 'center' },
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['rate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '血压', titleAlign: 'center' },
                        { fields: ['address'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['hobby'], title: '微循环', titleAlign: 'center' },
                        { fields: ['blood'], title: '血氧', titleAlign: 'center' },
                        { fields: ['breathe'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['step'], title: '计步', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' }
                    ],
                ],
            },
            tableConfig3: {
                multipleSort: false,
                tableData: [
                    { 'love': '', 'id': '1', 'name': '红','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88','step':'11223', 'time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '2', 'name': '明', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '3', 'name': '春', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '4', 'name': '李', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '5', 'name': '丽', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '6', 'name': '男', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '7', 'name': '亲', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '8', 'name': '弹', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '9', 'name': '高', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '10', 'name': '阿', 'hobby':'65','gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '11', 'name': '人','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                    { 'love': '', 'id': '12', 'name': '额','hobby':'65', 'gender': '12', 'rate': '112', 'address': '55', 'blood': '100', 'breathe': '88', 'step':'11223','time': '2019/3/22 17:33' },
                ],
                columns: [
                    { field: 'love', width: 50, columnAlign: 'center', isFrozen: true },
                    {
                        field: 'custome', width: 50, titleAlign: 'center', columnAlign: 'center',
                        formatter: function (rowData, index, pagingIndex) {
                            var currentIndex = index + pagingIndex;
                            return currentIndex < 3 ? '<span style="color:red;font-weight: bold;">' + (currentIndex + 1) + '</span>' : currentIndex + 1
                        }, isFrozen: true
                    },
                    { field: 'name', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'rate', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'gender', width: 80, columnAlign: 'center',  isResize: true },
                    { field: 'address', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'hobby', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'breathe', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'step', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 100, columnAlign: 'center', isResize: true }
                ],
                titleRows: [

                    [
                        { fields: ['love'], title: '', titleAlign: 'center' },
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['rate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '血压', titleAlign: 'center' },
                        { fields: ['address'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['hobby'], title: '微循环', titleAlign: 'center' },
                        { fields: ['blood'], title: '血氧', titleAlign: 'center' },
                        { fields: ['breathe'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['step'], title: '计步', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' }
                    ],
                ],
            }
        }
    },
    methods: {
        tabClick(tabId) {
                this.tab = tabId;
                this.$refs['table' + tabId].resize();
                
            },
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
            // this.tableData.push(data)
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