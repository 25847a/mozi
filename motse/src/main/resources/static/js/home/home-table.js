var tableList=new Vue({
    el: '#app',
    data() {
        return {            
            pageIndex: 1,
            pageSize: 10,
            tab: 1,
            tableConfig: {
                multipleSort: false,
                tableData:
                	[
                ]	,
                columns: [
                    { field: 'love', width: 50, columnAlign: 'center',isFrozen: true,
                        formatter: function (rowData) {
                        	if(rowData.love==1){
                        		return '<img src="'+'../img/love.png'+'"/>';
                        	}
                     }, isResize: true },
                    
                    
                    {
                        field: 'id', width: 50, titleAlign: 'center', columnAlign: 'center',isFrozen: true,
                    },
                    { field: 'name', width: 133, columnAlign: 'center', isResize: true  },
                    { field: 'Heartrate', width: 110, columnAlign: 'center', isResize: true,
                        formatter: function (rowData) {
                        		 return rowData.Heartrate.toString().replace("A","");
                     } },
                    { field: 'sbp_ave', width: 130, columnAlign: 'center',  isResize: true,
                         formatter: function (rowData) {
                    		 return rowData.sbp_ave.toString().replace("A","");
                 }  },
                    { field: 'dbp_ave', width: 130, columnAlign: 'center',  isResize: true,
                     formatter: function (rowData) {
                		 return rowData.dbp_ave.toString().replace("A","");
             }  },
                    { field: 'HRV', width: 120, columnAlign: 'center', isResize: true },
                    { field: 'microcirculation', width: 120, columnAlign: 'center', isResize: true },
                    { field: 'Bloodoxygen', width: 120, columnAlign: 'center', isResize: true },
                    { field: 'respirationrate', width: 120, columnAlign: 'center', isResize: true },
                    { field: 'createtime', width: 200, columnAlign: 'center', isResize: true }
                ],
                titleRows: [

                    [
                        { fields: ['love'], title: '', titleAlign: 'center' },
                        { fields: ['id'], title: '序号', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['Heartrate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['sbp_ave'], title: '收缩压', titleAlign: 'center' },
                        { fields: ['dbp_ave'], title: '舒张压', titleAlign: 'center' },
                        { fields: ['HRV'], title: '心率变异性', titleAlign: 'center' },
                        { fields: ['microcirculation'], title: '微循环', titleAlign: 'center' },
                        { fields: ['Bloodoxygen'], title: '血氧', titleAlign: 'center' },
                        { fields: ['respirationrate'], title: '呼吸', titleAlign: 'center' },
                        { fields: ['createtime'], title: '更新时间', titleAlign: 'center' }
                    ],
                ],
            },
        }
    },
    methods: {
        columnCellClass(rowIndex,columnName,rowData){
        	if(rowData.Heartrate.toString().search("A")!=-1 && columnName=='Heartrate'){
        		return 'column-cell-class-name-test';
        	}
        	if(rowData.sbp_ave.toString().search("A")!=-1 && columnName=='sbp_ave'){
        		return 'column-cell-class-name-test';
        	}
        	if(rowData.dbp_ave.toString().search("A")!=-1 && columnName=='dbp_ave'){
        		return 'column-cell-class-name-test';
        	}
        },
        rowClick(rowIndex,rowData){
        	console.log(rowIndex);
        	console.log(rowData);
        },
    	tabClick(tabId) {
                this.tab = tabId;
                this.$refs['table' + tabId].resize();
                
            },
        getTableData() {

            this.tableConfig.tableData =tableData.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize)
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
        goback:function(){
        	var page = $("#page").val();
           axios.post(GetURLInfo()+"health/queryBeadhouseList?page="+page);
        }
    },
    mounted(){
        this.goback();
        setInterval(this.goback,20000);
    },
    beforeDestroy(){
        clearInterval(this.item);
    }
})