var tableDate = new Array();
var id ="";
var imei ="";
var createDate ="";
var tableList =	new Vue({
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
                	{ field: 'phone', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'imei', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'Heartrate', width: 80, columnAlign: 'center', isResize: true  },
                    { field: 'sbp_ave', width: 80, columnAlign: 'center',  isResize: true },
                    { field: 'dbp_ave', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'microcirculation', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'respirationrate', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'Bloodoxygen', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'HRV', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'Amedicalreport', width: 500, columnAlign: 'center', isResize: true },
                    { field: 'createtime', width: 220, columnAlign: 'center', isResize: true }

                ],
                titleRows: [
                    [
                    	{ fields: ['phone'], title: '用户ID', titleAlign: 'center' },
                        { fields: ['imei'], title: 'IMEI', titleAlign: 'center' },
                        { fields: ['Heartrate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['sbp_ave'], title: '高压', titleAlign: 'center' },
                        { fields: ['dbp_ave'], title: '低压', titleAlign: 'center' },
                        { fields: ['microcirculation'], title: '微循环', titleAlign: 'center' },
                        { fields: ['respirationrate'], title: '呼吸频率', titleAlign: 'center' },
                        { fields: ['Bloodoxygen'], title: '血氧', titleAlign: 'center' },
                        { fields: ['HRV'], title: '心跳异常', titleAlign: 'center' },
                        { fields: ['Amedicalreport'], title: '体检报告', titleAlign: 'center' },
                        { fields: ['createtime'], title: '添加时间', titleAlign: 'center' }
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
	       	 params.append('imei',imei);
	       	 params.append('createDate',createDate);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
             axios.post(GetURLInfo()+"health/queryHealthInfoList",params).then(this.getnew);

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
    },
     created() {
    	console.log("健康数据管理");
     },
    mounted(){
        this.goback();
        this.getTableData();
    }
});

function query(){
	id = $("#id").val();
	imei = $("#imei").val();
	createDate =$("#createDate").val();
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