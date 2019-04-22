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
                    
                    { field: 'name', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'masterType', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'url', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'permission', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'isDisable', width: 200, columnAlign: 'center',  isResize: true ,formatter: function (rowData) {
                    	if(rowData.isDisable==0){
                    		return '可用';
                    	}else{
                    		return '不可用';
                    	}
                    }},
                    { field: 'createDate', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 200, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [

                    [
                        
                        { fields: ['name'], title: '类型', titleAlign: 'center' },
                        { fields: ['masterType'], title: '名称', titleAlign: 'center' },
                        { fields: ['url'], title: '路径', titleAlign: 'center' },
                        { fields: ['permission'], title: '权限标记', titleAlign: 'center', },//orderBy: ''
                        { fields: ['isDisable'], title: '是否禁用', titleAlign: 'center' },
                        { fields: ['createDate'], title: '创建时间', titleAlign: 'center' },
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
            console.log("A");
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
        	axios.post(GetURLInfo()+"auth/queryAuthList").then(this.getnew);

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