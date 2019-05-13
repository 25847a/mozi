var tableDate = new Array();
var id ="";
var name ="";
var tableList=new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                tableData: [],
                columns: [
                    { field: 'imei', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'id', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'name', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'Heartrate', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'sbp_ave', width: 100, columnAlign: 'center',  isResize: true },
                    { field: 'dbp_ave', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'microcirculation', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'respirationrate', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'Bloodoxygen', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'HRV', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'createtime', width: 200, columnAlign: 'center', isResize: true }

                ],
                titleRows: [

                    [
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['id'], title: 'ID', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['Heartrate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['sbp_ave'], title: '收缩压(高压)', titleAlign: 'center' },
                        { fields: ['dbp_ave'], title: '舒张压(低压)', titleAlign: 'center' },
                        { fields: ['microcirculation'], title: '微循环', titleAlign: 'center' },
                        { fields: ['respirationrate'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['Bloodoxygen'], title: '血氧', titleAlign: 'center' },
                        { fields: ['HRV'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['createtime'], title: '更新时间', titleAlign: 'center' }
                    ],
                ],
            }
        }
    },
    methods: {
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
        goback:function(){
        	var params = new URLSearchParams();
        	 params.append('id',id);
        	 params.append('name',name);
        	 params.append('pageNum',this.pageIndex);
        	 params.append('pageSize',this.pageSize);
            axios.post(GetURLInfo()+"health/queryHistoryList",params).then(this.getnew);

        },
        getnew(res){
        	if(res.data.code==-1){
        		tableDate=[];
        		for(var i=0;i<res.data.data.length;i++){
        			tableDate.push(res.data.data[i]);
                        }
        		this.total= res.data.total;
        		this.getTableData();
        	}
        }
    }
   ,
    created() {
        console.log("进入历史查询页面");
    },
    mounted(){
        this.goback();
        this.getTableData();
    }
   
});
function query(){
	id = $("#id").val();
	name = $("#name").val();
	tableList.goback();
}
//刷新按钮
function refresh(){
	location.reload();
}
//重置按钮
function empty(){
	$("#search_par input").val("");
}

