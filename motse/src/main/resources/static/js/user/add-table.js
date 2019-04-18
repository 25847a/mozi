new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'number': '1','imei': '862237048336833',  'name': '张三', 'born': '1960-04-20', 'gender': '女', 'height': '160', 'weight': '40', 'bed': '410-3', 'care': '李小平', 'address': '广州市番禺区海伦堡创意园1座C区','phone': '18826222222'},
                    { 'number': '1','imei': '862237048336833',  'name': '张三', 'born': '1960-04-20', 'gender': '女', 'height': '160', 'weight': '40', 'bed': '410-3', 'care': '李小平', 'address': '广州市番禺区海伦堡创意园1座C区','phone': '18826222222'},
                    { 'number': '1','imei': '862237048336833',  'name': '张三', 'born': '1960-04-20', 'gender': '女', 'height': '160', 'weight': '40', 'bed': '410-3', 'care': '李小平', 'address': '广州市番禺区海伦堡创意园1座C区','phone': '18826222222'},

                ],
                columns: [                   
                    { field: 'number', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'imei', width: 150, columnAlign: 'center',  isResize: true },
                    { field: 'name', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'born', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'gender', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'height', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'weight', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'bed', width: 150, columnAlign: 'center',  isResize: true },
                    { field: 'care', width: 150, columnAlign: 'center',  isResize: true },
                    { field: 'address', width: 300, columnAlign: 'center',  isResize: true },
                    { field: 'phone', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 100, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [                        
                        { fields: ['number'], title: '序号', titleAlign: 'center' },
                        { fields: ['imei'], title: 'IMEI号', titleAlign: 'center' },
                        { fields: ['name'], title: '用户昵称', titleAlign: 'center' },
                        { fields: ['born'], title: '出生日期', titleAlign: 'center', },//orderBy: ''
                        { fields: ['gender'], title: '性别', titleAlign: 'center' },
                        { fields: ['height'], title: '身高CM', titleAlign: 'center' },
                        { fields: ['weight'], title: '体重KG', titleAlign: 'center' },
                        { fields: ['bed'], title: '床号', titleAlign: 'center' },
                        { fields: ['care'], title: '分属护工', titleAlign: 'center' },
                        { fields: ['address'], title: '地址', titleAlign: 'center' },
                        { fields: ['phone'], title: '监测器电话', titleAlign: 'center' },
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

// 自定义列组件<a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
    Vue.component('table-operation',{
        template:`<span>
        <a href="" @click.stop.prevent="update(rowData,index)">修改</a>&nbsp;
        
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