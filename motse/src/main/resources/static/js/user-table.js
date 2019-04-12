new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},
                    { 'role': 'admin','name': '黄才健', 'user': '25847a', 'state': '启用', 'login': '2987', 'ip': '192.168.1.122','time': '2018-09-30 12:03'},

                ],
                columns: [
                    { field: 'all', width: 60,  columnAlign: 'center', isFrozen: true ,type: 'selection',},
                    { field: 'role', width: 120, columnAlign: 'center', isFrozen: true },
                    { field: 'name', width: 120, columnAlign: 'center', isFrozen: true },
                    { field: 'user', width: 120, columnAlign: 'center', isFrozen: true },
                    { field: 'state', width: 120, columnAlign: 'center', isFrozen: true },
                    { field: 'login', width: 50, columnAlign: 'center', isFrozen: false, isResize: true },
                    { field: 'ip', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 50, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [
                        { fields: ['all'],  titleAlign: 'center' },
                        { fields: ['role'], title: '角色', titleAlign: 'center' },
                        { fields: ['name'], title: '真实姓名', titleAlign: 'center' },
                        { fields: ['user'], title: '用户名', titleAlign: 'center' },
                        { fields: ['state'], title: '状态', titleAlign: 'center', },//orderBy: ''
                        { fields: ['login'], title: '登录次数', titleAlign: 'center' },
                        { fields: ['ip'], title: '登录IP', titleAlign: 'center' },
                        { fields: ['time'], title: '锁定时间', titleAlign: 'center' },
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
            selectALL(selection) {

                console.log('select-aLL', selection);
            },

            selectChange(selection, rowData) {
                console.log('select-change', selection, rowData);
            },

            selectGroupChange(selection) {
                console.log('select-group-change', selection);
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
        <a href="" @click.stop.prevent="update(rowData,index)">修改</a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
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