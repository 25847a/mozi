var tableDate = new Array();

new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                tableData: [
                ],
                columns: [                   
                    { field: 'id', width: 100, columnAlign: 'center', isResize: true  },
                    { field: 'name', width: 250, columnAlign: 'center', isResize: true  },
                    { field: 'age', width: 100, columnAlign: 'center', isResize: true  },
                    { field: 'gender', width: 150, columnAlign: 'center',  isResize: true,formatter: function (rowData) {
                    	if(rowData.gender==1){
                    		return '男性';
                    	}else{
                    		return '女性';
                    	}
                    } },
                    { field: 'account', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'address', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'count', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'createDate', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'equipment', width: 100, columnAlign: 'center', isResize: true,componentName:'table-equipment'},
                    { field: 'fuck', width: 100, columnAlign: 'center', isResize: true,componentName:'table-operation'}

                ],
                titleRows: [
                    [                       
                        { fields: ['id'], title: '序号', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['age'], title: '年龄', titleAlign: 'center' },
                        { fields: ['gender'], title: '性别', titleAlign: 'center' },
                        { fields: ['account'], title: '手机', titleAlign: 'center' },
                        { fields: ['address'], title: '地址', titleAlign: 'center' },
                        { fields: ['count'], title: '设备数量', titleAlign: 'center' },
                        { fields: ['createDate'], title: '注册时间', titleAlign: 'center' },
                        { fields: ['equipment'], title: '设备', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center'},
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
             console.log("B");
        },
        pageSizeChange(pageSize) {

            this.pageIndex = 1;
            this.pageSize = pageSize;
             this.getTableData();
             console.log("C");
        },
        customCompFunc(params){

            //    console.log(params);

                if (params.type === 'delete'){ // do delete operation
                  //  console.log("delete")
                 //   console.log(params)
                    this.$delete(this.tableConfig.tableData,params.index);

                }else if (params.type === 'edit'){ // do edit operation

                    alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
                }

            },
            //数据请求
        goback:function(){
           axios.post(GetURLInfo()+"admin/queryAdminList").then(this.getnew);

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
    
    mounted(){
        this.goback();
        this.getTableData();
        console.log("E");
    },
    created() {
        console.log("D");
    }
})

// 自定义列组件<a href="" @click.stop.prevent="deleteRow(rowData,index)"></a>
    Vue.component('table-operation',{
        template:`<span class="a">
        <a href="" @click.stop.prevent="update(rowData,index)"><img src="../img/agent01.png" alt="" /></a>&nbsp;
        
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
    // 自定义列组件
    Vue.component('table-equipment',{
    	
    	template:'<span>'+
        '<a href="#" >查看</a>	<a href="#" >录入</a></span>'
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