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
                    
                    { field: 'id', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'name', width: 200, columnAlign: 'center',  isResize: true },
                    { field: 'imei', width: 200, columnAlign: 'center',  isResize: true },
                    { field: 'currentversion', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'ziversion', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'zhuversion', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'compilation', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'versiontype', width: 100, columnAlign: 'center', isResize: true ,formatter: function (rowData) {
                    	if(rowData.versiontype==1){
                    		return '公测';
                    	}else if(rowData.versiontype==2){
                    		return '发布';
                    	}else{
                    		return '测试';
                    	}
                    }},
                    { field: 'description', width: 300, columnAlign: 'center', isResize: true },
                    { field: 'createtime', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 100, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [

                    [
                        
                        { fields: ['id'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '版本标题', titleAlign: 'center' },
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['currentversion'], title: '当前版本', titleAlign: 'center', },//orderBy: ''
                        { fields: ['ziversion'], title: '主版本号', titleAlign: 'center' },
                        { fields: ['zhuversion'], title: '子版本号', titleAlign: 'center' },
                        { fields: ['compilation'], title: '编译号', titleAlign: 'center' },
                        { fields: ['versiontype'], title: '版本类型', titleAlign: 'center' },
                        { fields: ['description'], title: '版本描述', titleAlign: 'center' },
                        { fields: ['createtime'], title: '上传时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            }
        }
    },
    methods: {
        getTableData() {

            this.tableConfig.tableData = tableDate.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize);
            this.total = tableDate.length;
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
            axios.post(GetURLInfo()+"versionhistory/queryUploaddownloadList").then(this.getnew);

        },
        getnew(res){
        	if(res.data.code==-1){
        		for(var i=0;i<res.data.data.length;i++){
        			tableDate.push(res.data.data[i]);
        			this.getTableData();
                        }
        	}
        }
    },
     created() {
         this.getTableData();
     },
    mounted(){
        this.goback();
        this.getTableData();
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