
var tableDate = new Array();
var versionName ="";
var tableList = new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                multipleSort: false,
                tableData: [],
                columns: [
                    { field: 'id', width: 100, columnAlign: 'center'},
                    { field: 'name', width: 250, columnAlign: 'center'},
                    { field: 'imei', width: 170, columnAlign: 'center'},
                    { field: 'currentversion', width: 80, columnAlign: 'center'},
                    { field: 'ziversion', width: 80, columnAlign: 'center'},
                    { field: 'zhuversion', width: 100, columnAlign: 'center'},
                    { field: 'compilation', width: 160, columnAlign: 'center'},
                    { field: 'versiontype', width: 160, columnAlign: 'center'},
                    { field: 'description', width: 250, columnAlign: 'center'},
                    { field: 'createtime', width: 150, columnAlign: 'center'},
                    { field: 'fuck', width: 130, columnAlign: 'center', componentName: 'table-operation' }
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
            },
            //查询
            keyword: "",
            //上传版本弹框
            dialogFormVisible: false,
            upload: {
                edition: '',
                host: '',
                son: '',
                describe: '',
                type: '',
                model: '',
                desc: ''
            },
            formLabelWidth: '120px',
            //上传
            fileList: [
                {
                    name: 'food.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                },
                {
                    name: 'food2.jpeg',
                    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                }
            ],
            //单个升级弹窗
            single: false,
            versiontype: {
                imei: '',
                region: '',
            },
            formLabelWidth: '120px',
            batch: false,
            editiontype: {
                types: '',
            },
        }
    },
    methods: {
        //批量升级确定
        ascertain(){
            alert(this.editiontype.types);
        },
        //单个升级确定
        define(){
            alert(this.versiontype.imei+this.versiontype.region)
        },
        //上传版本确定
        fix() {
            alert(this.upload.edition + this.upload.host + this.upload.son + this.upload.describe + this.upload.type + this.upload.model + this.upload.desc)
        },
        //上传
        submitUpload() {
            this.$refs.upload.submit();
        },
        //上传删除
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        //查询点击
        polling() {
        	versionName=this.keyword;
        	tableList.goback();
        },
        //分页
        getTableData() {
            this.tableConfig.tableData = tableDate;//.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize);
        },
        pageChange(pageIndex) {
            this.pageIndex = pageIndex;
            this.goback();
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
        customCompFunc(params) {
            console.log(params);
            if (params.type === 'delete') { // do delete operation
                console.log("delete")
                console.log(params)
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.$delete(this.tableConfig.tableData, params.index);
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            } else if (params.type === 'edit') { // do edit operation

                alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
            }

        },
        //数据请求
        goback: function () {
        	 var params = new URLSearchParams();
	       	 params.append('name',versionName);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
             axios.post(GetURLInfo()+"uploaddownload/queryUploaddownloadList",params).then(this.getnew);
        },
        getnew(res) {
        	if(res.data.code==-1){
        		tableDate=[];
        		for(var i=0;i<res.data.data.length;i++){
        			tableDate.push(res.data.data[i]);
                        }
        		this.total= res.data.total;
        		this.getTableData();
        }
        }
    },
    // created() {
    //     this.getTableData();
    // },
	    mounted() {
	        this.goback();
	    }/*,
    beforeDestroy() {
        clearInterval(this.item);
    }*/
});

// 自定义列组件<a href="" @click.stop.prevent="update(rowData,index)">编辑</a>&nbsp;
Vue.component('table-operation', {
    template: `<span>
        
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">x</a>
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
        update() {

            // 参数根据业务场景随意构造
            let params = { type: 'edit', index: this.index, rowData: this.rowData };
            this.$emit('on-custom-comp', params);
        },

        deleteRow() {

            // 参数根据业务场景随意构造
            let params = { type: 'delete', index: this.index };
            this.$emit('on-custom-comp', params);

        }
    }
})