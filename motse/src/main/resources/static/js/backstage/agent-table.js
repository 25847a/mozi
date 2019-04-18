new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},
                    { 'number': '0000001', 'name': '广州墨子星科技集团有限公司', 'age': '10', 'sex': '男', 'phone': '400-165-0388','address':'广东省广州市','eqnumber':'908','time':'2018-09-30 12:03'},

                ],
                columns: [                   
                    { field: 'number', width: 150, columnAlign: 'center', isResize: true  },
                    { field: 'name', width: 250, columnAlign: 'center', isResize: true  },
                    { field: 'age', width: 100, columnAlign: 'center', isResize: true  },
                    { field: 'sex', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'phone', width: 200, columnAlign: 'center',  isResize: true },
                    { field: 'address', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'eqnumber', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'time', width: 250, columnAlign: 'center', isResize: true },
                    { field: 'equipment', width: 100, columnAlign: 'center', isResize: true ,componentName:'table-equipment'},
                    { field: 'fuck', width: 100, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [                       
                        { fields: ['number'], title: '编号', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['age'], title: '年龄', titleAlign: 'center' },
                        { fields: ['sex'], title: '性别', titleAlign: 'center' },
                        { fields: ['phone'], title: '手机号码', titleAlign: 'center', },//orderBy: ''
                        { fields: ['address'], title: '地址', titleAlign: 'center' },
                        { fields: ['eqnumber'], title: '设备数', titleAlign: 'center' },
                        { fields: ['time'], title: '注册时间', titleAlign: 'center' },
                        { fields: ['equipment'], title: '设备', titleAlign: 'center' },
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
            axios.post("https://www.apiopen.top/satinApi?type=1&page=1").then(this.getnew)

        },
        getnew(res){
            let data = res.data.data
            // console.log(data)
            this.item = data
            // console.log(this.item)
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
        template:`<span>
        <a href="#" >查看</a>&nbsp;
        <a href="#" >录入</a>
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