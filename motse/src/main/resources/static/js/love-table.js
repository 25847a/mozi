new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },
                    { 'imei': '414243000432', 'custome': '28681', 'name': '吴亦凡', 'born': '1960-4-21', 'sex': '180', 'gender': '男', 'weight': '63kg', 'phone1': '15596968888', 'phone2': '15596968888', 'address': '广州番禺区养老院' },

                ],
                columns: [
                    { field: 'imei', width: 150, columnAlign: 'center', isFrozen: true },
                    { field: 'custome', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'name', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'born', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'gender', width: 100, columnAlign: 'center', isFrozen: false, isResize: true },
                    { field: 'sex', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'weight', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'phone1', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'phone2', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'address', width: 100, columnAlign: 'center', isResize: true }

                ],
                titleRows: [

                    [
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['born'], title: '出生日期', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '性别', titleAlign: 'center' },
                        { fields: ['sex'], title: '身高', titleAlign: 'center' },
                        { fields: ['weight'], title: '体重', titleAlign: 'center' },
                        { fields: ['phone1'], title: '紧急联系人1', titleAlign: 'center' },
                        { fields: ['phone2'], title: '紧急联系人2', titleAlign: 'center' },
                        { fields: ['address'], title: '地址', titleAlign: 'center' }
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