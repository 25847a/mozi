new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                     { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                      { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                       { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                        { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                         { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                          { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                           { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                            { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                            { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                           { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                            { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                            { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                           { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},
                            { 'imei': '867186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03'},

                ],
                columns: [
                    
                    { field: 'imei', width: 150, columnAlign: 'center', isFrozen: true },
                    { field: 'main', width: 80, columnAlign: 'center', isFrozen: true },
                    { field: 'signal', width: 80, columnAlign: 'center', isFrozen: true },
                    { field: 'type', width: 80, columnAlign: 'center', isFrozen: true },
                    { field: 'state', width: 100, columnAlign: 'center', isFrozen: true},
                    { field: 'phone1', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'phone2', width: 100, columnAlign: 'center', isFrozen: true },
                    { field: 'version', width: 150, columnAlign: 'center', isFrozen: true },
                    { field: 'time', width: 30, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 100, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [                        
                        { fields: ['imei'], title: 'imei', titleAlign: 'center' },
                        { fields: ['main'], title: '主机电量', titleAlign: 'center' },
                        { fields: ['signal'], title: '主机信号', titleAlign: 'center' },
                        { fields: ['type'], title: '蓝牙类型', titleAlign: 'center', },//orderBy: ''
                        { fields: ['state'], title: '设备在线状态', titleAlign: 'center' },
                        { fields: ['phone1'], title: '紧急联系人1', titleAlign: 'center' },
                        { fields: ['phone2'], title: '紧急联系人2', titleAlign: 'center' },
                        { fields: ['version'], title: '版本', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
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
        customCompFunc(params){

                console.log(params);

                if (params.type === 'delete'){ // do delete operation
                    console.log("delete")
                    console.log(params)
                    this.$delete(this.tableConfig.tableData,params.index);

                }else if (params.type === 'edit'){ // do edit operation

                    alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
                }

            },
            //数据请求
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

// 自定义列组件
    Vue.component('table-operation',{
        template:`<span>
        <a href="" @click.stop.prevent="update(rowData,index)">清除蓝牙</a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">关机</a>
        <a href="" @click.stop.prevent="update(rowData,index)">升级</a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">关联用户信息</a>
        </span>`,
        props:{
            rowData:{
                type:Object
            },
            field:{
                type:String
            },
            index:{
                type:Number
            }
        },
        methods:{
            update(){

               // 参数根据业务场景随意构造
               let params = {type:'edit',index:this.index,rowData:this.rowData};
               this.$emit('on-custom-comp',params);
            },

            deleteRow(){

                // 参数根据业务场景随意构造
                let params = {type:'delete',index:this.index};
                this.$emit('on-custom-comp',params);

            }
        }
    })