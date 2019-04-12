new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },
                    { 'custome': '98', 'version': '18072202量产版本','imei': '批量升级',  'current': '', 'main': '2', 'son': '13', 'compile': '18072202', 'type': '发布', 'describe': '18072202量产版本', 'time': '2018-09-30 12:03' },

                ],
                columns: [
                    
                    { field: 'custome', width: 50, columnAlign: 'center',  isResize: true },
                    { field: 'version', width: 150, columnAlign: 'center',  isResize: true },
                    { field: 'imei', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'current', width: 60, columnAlign: 'center',  isResize: true },
                    { field: 'main', width: 60, columnAlign: 'center',  isResize: true },
                    { field: 'son', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'compile', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'type', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'describe', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 30, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [

                    [
                        
                        { fields: ['custome'], title: 'ID', titleAlign: 'center' },
                        { fields: ['version'], title: '版本标题', titleAlign: 'center' },
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['current'], title: '当前版本', titleAlign: 'center', },//orderBy: ''
                        { fields: ['main'], title: '主版本号', titleAlign: 'center' },
                        { fields: ['son'], title: '子版本号', titleAlign: 'center' },
                        { fields: ['compile'], title: '编译号', titleAlign: 'center' },
                        { fields: ['type'], title: '版本类型', titleAlign: 'center' },
                        { fields: ['describe'], title: '版本描述', titleAlign: 'center' },
                        { fields: ['time'], title: '上传时间', titleAlign: 'center' },
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

// 自定义列组件<a href="" @click.stop.prevent="update(rowData,index)">编辑</a>&nbsp;
    Vue.component('table-operation',{
        template:`<span>
        
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">x</a>
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