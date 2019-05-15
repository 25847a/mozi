new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            tableConfig: {
                multipleSort: false,
                tableData: [
                    { 'imei': '567186039369706', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '367543254325555', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '165465131661616', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '498794613461166', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '96516130161.616', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '651613206430635', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '874164610.06431', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '321613065130656', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '123465498419846', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '654321354613181', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '456321798413163', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '368431657451366', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '963852741654123', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '258789456321456', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },
                    { 'imei': '741323165478932', 'main': '0', 'signal': '13', 'type': '0', 'state': '离线', 'phone1': '13679628008', 'phone2': '13679628008', 'version': '2_13_18072202_2', 'time': '2018-09-30 12:03' },

                ],
                columns: [

                    { field: 'imei', width: 190, columnAlign: 'center'},
                    { field: 'main', width: 100, columnAlign: 'center'},
                    { field: 'signal', width: 100, columnAlign: 'center'},
                    { field: 'type', width: 100, columnAlign: 'center'},
                    { field: 'state', width: 130, columnAlign: 'center'},
                    { field: 'phone1', width: 160, columnAlign: 'center'},
                    { field: 'phone2', width: 160, columnAlign: 'center'},
                    { field: 'version', width: 200, columnAlign: 'center'},
                    { field: 'time', width: 200, columnAlign: 'center'},
                    { field: 'fuck', width: 250, columnAlign: 'center', componentName: 'table-operation' }

                ],
                titleRows: [
                    [
                        { fields: ['imei'], title: 'imei', titleAlign: 'center' },
                        { fields: ['main'], title: '主机电量', titleAlign: 'center' },
                        { fields: ['signal'], title: '主机信号', titleAlign: 'center' },
                        { fields: ['type'], title: '蓝牙类型', titleAlign: 'center', },//orderBy: ''
                        { fields: ['state'], title: '设备在线状态', titleAlign: 'center' },
                        { fields: ['phone1'], title: '紧急联系人1', titleAlign: 'center' },
                        { fields: ['phone2'], title: '紧急联系人2', titleAlign: 'center' },
                        { fields: ['version'], title: '版本', titleAlign: 'center' },
                        { fields: ['time'], title: '更新时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            //设备管理弹窗
            rolelist: [
                { 
                    number: '13719160407',
                    id:'1596',
                    pet:'13719160407',
                    role:'监护者',
                },
                { 
                    number: '账号',
                    id:'1646',
                    pet:'00',
                    role:'使用者', 
                }
            ],
            //查询框
            myVal:0, //默认选中第一项
            options:[
                {
                    name:'apple',
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
            //input框的值
            imei:"",
            time:""
        }
    },
    methods: {
        //点击删除
        deleteRow:function(index) {
            alert(index)
            this.rolelist.splice(index, 1);
            
          },


        //查询点击事件
        query(){
            console.log(this.imei+this.time+this.myVal)
            
        },

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
        customCompFunc(params) {

            console.log(params);

            //if (params.type === 'delete') { // do delete operation
                // console.log("delete")
                // console.log(params)
                // this.$delete(this.tableConfig.tableData, params.index);

            //} else if (params.type === 'edit') { // do edit operation

                console.log(`行号：${params.index} 姓名：${params.rowData['imei']}`)
            //}

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
});
// 自定义列组件
Vue.component('table-operation', {
    template: `<span>
        <button href="#" id="bluetooth">清除蓝牙</button>&nbsp;
        <button href="#" >关机</button>
        <button href="#" >升级</button>&nbsp;
        <button href="#" id="msg" @click.stop.prevent="update(rowData,index)">关联用户信息</button>
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

        // deleteRow(){

        //     // 参数根据业务场景随意构造
        //     let params = {type:'delete',index:this.index};
        //     this.$emit('on-custom-comp',params);

        // },
    }
});
//清除蓝牙

$(".v-table-body-cell #bluetooth").click(function () {
	//alert(23);
	$('.btn-lggg').click();
});
// 关联用户信息

$(".v-table-body-cell #msg").click(function () {
	//console.log(222);
	$('.btn-lgg').click();
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


})


