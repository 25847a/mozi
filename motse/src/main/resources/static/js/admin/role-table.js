new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'role': 'admin', 'describe': '超级管理员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': 'vip', 'describe': 'vip会员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '库存管理员', 'describe': '库存管理员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '体检医生', 'describe': '体检医生', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '护士', 'describe': '采浆护士', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '库存管理员', 'describe': '库存管理员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '体检医生', 'describe': '体检医生', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '护士', 'describe': '采浆护士', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': 'admin', 'describe': '超级管理员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': 'vip', 'describe': 'vip会员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },
                    { 'role': '库存管理员', 'describe': '库存管理员', 'time': '2018-09-30 12:03', 'forbidden': '启用' },

                ],
                columns: [

                    { field: 'role', width: 100, columnAlign: 'center'},
                    { field: 'describe', width: 100, columnAlign: 'center' },
                    { field: 'forbidden', width: 50, columnAlign: 'center'},
                    { field: 'time', width: 50, columnAlign: 'center' },
                    { field: 'fuck', width: 50, columnAlign: 'center', componentName: 'table-operation' }

                ],
                titleRows: [
                    [
                        { fields: ['role'], title: '角色名称', titleAlign: 'center' },
                        { fields: ['describe'], title: '角色描述', titleAlign: 'center' },
                        { fields: ['forbidden'], title: '是否禁用', titleAlign: 'center' },
                        { fields: ['time'], title: '创建时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            //查询输入框
            queryInput: "",
            //修改按钮
            username:"",
            admin:"",
            password:"",
            phone:"",
            area:"",
            index:"",
            //新增
            myrole:0 ,//默认选中第一项
            options:[
                {
                    name:'超级管理员',
                    value:0
                },
                {
                    name:'banana',
                    value:1
                },
                {
                    name:'orange',
                    value:2
                },
            ],
            myarea:0 ,//默认选中第一项
            optionsArea:[
                {
                    name:'请选择区域',
                    value:0
                },
                {
                    name:'banana',
                    value:1
                },
                {
                    name:'orange',
                    value:2
                },
            ],

        }
    },
    methods: {
        //点击确定新增
        fix(){
            console.log(this.myrole)
            console.log(this.myarea + this.username +this.password+this.phone)
        },

        //点击确定删除
        defineDel(){
            
        },

        //点击确定修改
        modify(){
            alert('管理员' + this.admin +'选择片区' + this.area +'用户名' + this.username +'密码' + this.password  + '手机号' + this.phone )
            
        },

        // 点击查询获取input 
        query(event) {
            //this.result2 = this.input2 + " 成功!";
            // `this` 在方法里指向当前 Vue 实例
            alert('Hello ' + this.queryInput + " 成功!" + '!')
            // `event` 是原生 DOM 事件
            // if (event) {
            //     alert(event.target.tagName)
            // }
        },

        //赋数据
        getTableData() {

            this.tableConfig.tableData = tableDate.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize)
        },
        //分页
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
        customCompFunc(params) {
            console.log(params);
            
            if (params.type === 'delete') { // do delete operation
                console.log("delete");
                console.log(params);
                //alert("你确定删除吗？")
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(() => {
                    this.$delete(this.tableConfig.tableData,params.index);
                    this.$message({
                      type: 'success',
                      message: '删除成功!'
                    });
                  }).catch(() => {
                    this.$message({
                      type: 'info',
                      message: '已取消删除'
                    });          
                  });
                                
            } else if (params.type === 'edit') { // do edit operation
                console.log(`行号：${params.index} 姓名：${params.rowData['role']}`)
                this.username = params.rowData['role']
            }

        },
        //数据请求
        goback: function () {
            // console.log('haha');
            // axios.post("https://www.apiopen.top/satinApi?type=1&page=1").then(this.getnew)

        },
        getnew(res) {
            let data = res.data.data
            // console.log(data)
            this.item = data
            // console.log(this.item)
        }
    },
    // created() {
    //     this.getTableData();
    // },
    mounted() {
        this.goback();
        // this.item = setInterval(this.goback,3000)
    },
    beforeDestroy() {
        clearInterval(this.item);
    }
})

// 自定义列组件
Vue.component('table-operation', {
    template: `<span>
        <button type="button" class="btn btn-primary" id="modify" @click.stop.prevent="update(rowData,index)">修改</button>&nbsp;
        <el-button type="text" @click.stop.prevent="deleteRow(rowData,index)">删除</el-button>
        </span>`,
    props: {
        rowData: {
            type: Object
        },
        field: {
            type: String
        },
        index: {
            type: Number
        }
    },
    methods: {
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
});
$(".v-table-body-cell #modify").click(function () {
	//console.log(222);
	$('.btn-modify').click();
	var jj = $(this).parent('span').parent('span').parent('div').parent('td').parent('tr').find("td").eq(0).text();
	console.log(jj);
	// $.ajax({
	//     type: "get",//数据发送的方式
	//     url: "https://www.apiopen.top/satinApi?type=1&page=1",//要发送的后台地址
	//     // data: { id: "data" },//要发送的数据
	//     dataType: "json",//后台处理后返回的数据格式
	//     success: function (data) {//ajax请求成功后触发的方法
	//         //alert('请求成功');
	//         //console.log(data.data)
	//         var Data = data.data
	//         for (var i = 0; i < Data.length; i++) {
	//             //console.log(Data[0].theme_name)
	//             
	//         }
	//     },
	//     error: function (msg) {//ajax请求失败后触发的方法
	//         alert(msg);//弹出错误信息
	//     }
	// });
});
$(".v-table-body-cell #dele").click(function () {
	//console.log(222);
	$('.btn-dele').click();
	var jj = $(this).parent('span').parent('span').parent('div').parent('td').parent('tr').find("td").eq(0).text();
	console.log(jj);
	// $.ajax({
	//     type: "get",//数据发送的方式
	//     url: "https://www.apiopen.top/satinApi?type=1&page=1",//要发送的后台地址
	//     // data: { id: "data" },//要发送的数据
	//     dataType: "json",//后台处理后返回的数据格式
	//     success: function (data) {//ajax请求成功后触发的方法
	//         //alert('请求成功');
	//         //console.log(data.data)
	//         var Data = data.data
	//         for (var i = 0; i < Data.length; i++) {
	//             //console.log(Data[0].theme_name)
	//             
	//         }
	//     },
	//     error: function (msg) {//ajax请求失败后触发的方法
	//         alert(msg);//弹出错误信息
	//     }
	// });
});