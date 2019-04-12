new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'name': '用户查询', 'type': 'button', 'path': 'admin/queryAdminList', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户添加', 'type': 'button', 'path': 'admin/insertAdmin', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户查询', 'type': 'button', 'path': 'admin/queryAdminList', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户添加', 'type': 'button', 'path': 'admin/deleteAdmin', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户查询', 'type': 'button', 'path': 'admin/insertAdmin', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户添加', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户查询', 'type': 'button', 'path': 'admin/insertAdmin', 'tag': 'admin.view','forbidden': '启用'},
                    { 'name': '用户添加', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用' },
                    { 'name': '用户查询', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用' },
                    { 'name': '角色查询', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '用户查询', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用'},
                    { 'name': '角色查询', 'type': 'button', 'path': '空版本', 'tag': 'admin.view', 'forbidden': '启用' },

                ],
                columns: [
                    
                    { field: 'type', width: 200, columnAlign: 'center', isFrozen: true },
                    { field: 'name', width: 200, columnAlign: 'center', isFrozen: true },
                    { field: 'path', width: 200, columnAlign: 'center', isFrozen: true },
                    { field: 'tag', width: 200, columnAlign: 'center', isFrozen: true },
                    { field: 'forbidden', width: 50, columnAlign: 'center', isFrozen: false, isResize: true },
                    { field: 'time', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 50, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [

                    [
                        
                        { fields: ['type'], title: '类型', titleAlign: 'center' },
                        { fields: ['name'], title: '名称', titleAlign: 'center' },
                        { fields: ['path'], title: '路径', titleAlign: 'center' },
                        { fields: ['tag'], title: '权限标记', titleAlign: 'center', },//orderBy: ''
                        { fields: ['forbidden'], title: '是否禁用', titleAlign: 'center' },
                        { fields: ['time'], title: '创建时间', titleAlign: 'center' },
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