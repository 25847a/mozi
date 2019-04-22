var tableDate = new Array();
new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                multipleSort: false,
                tableData: [

                ],
                columns: [
                    
                    { field: 'imei', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'lordpower', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'signalxhao', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'bluetooth_type', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'eq_status', width: 100, columnAlign: 'center', isResize: true},
                    { field: 'phone1', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'phone2', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'version', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'updatetime', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 350, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [                        
                        { fields: ['imei'], title: 'imei', titleAlign: 'center' },
                        { fields: ['lordpower'], title: '主机电量', titleAlign: 'center' },
                        { fields: ['signalxhao'], title: '主机信号', titleAlign: 'center' },
                        { fields: ['bluetooth_type'], title: '蓝牙类型', titleAlign: 'center', },
                        { fields: ['eq_status'], title: '设备在线状态', titleAlign: 'center' },
                        { fields: ['phone1'], title: '紧急联系人1', titleAlign: 'center' },
                        { fields: ['phone2'], title: '紧急联系人2', titleAlign: 'center' },
                        { fields: ['version'], title: '版本', titleAlign: 'center' },
                        { fields: ['updatetime'], title: '更新时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            }
        }
    },
    created() {
    	
    },
    mounted(){
        this.goback();
        this.getTableData();
    },
    methods: {
        getTableData() {

            this.tableConfig.tableData = tableDate.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize);
            this.total = tableDate.length;
        },
        pageChange(pageIndex) {
            this.pageIndex = pageIndex;
            this.getTableData();
            console.log(pageIndex);
        },
        pageSizeChange(pageSize) {
            this.pageIndex = 1;
            this.pageSize = pageSize;
            this.getTableData();
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
            // console.log('haha');
            axios.post(GetURLInfo()+"equipment/queryEquipmentList").then(this.getnew)

        },
        getnew(res){
        	if(res.data.code==-1){
        		for(var i=0;i<res.data.data.length;i++){
        			tableDate.push(res.data.data[i]);
        			this.getTableData();
                        }
        	}
        }
    }
})

// 自定义列组件
    Vue.component('table-operation',{
        template:`<span>
        <a href="#">清除蓝牙</a>&nbsp;
        <a href="#" >关机</a>
        <a href="#" >升级</a>&nbsp;
        <a href="#" >关联用户信息</a>
        </span>`,
        // props:{
        //     rowData:{
        //         type:Object
        //     },
        //     field:{
        //         type:String
        //     },
        //     index:{
        //         type:Number
        //     }
        // },
        // methods:{
        //     update(){

        //        // 参数根据业务场景随意构造
        //        let params = {type:'edit',index:this.index,rowData:this.rowData};
        //        this.$emit('on-custom-comp',params);
        //     },

        //     deleteRow(){

        //         // 参数根据业务场景随意构造
        //         let params = {type:'delete',index:this.index};
        //         this.$emit('on-custom-comp',params);

        //     }
        // }
    })