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
                    { field: 'love', width: 50, columnAlign: 'center', isFrozen: true },
                    {
                        field: 'id', width: 50, titleAlign: 'center', columnAlign: 'center',isFrozen: true/*
                        formatter: function (rowData, index, pagingIndex) {
                        	index=14;
                        	pagingIndex=2;
                            var currentIndex = index + pagingIndex;
                            return currentIndex < 3 ? '<span style="color:red;font-weight: bold;">' + (currentIndex + 1) + '</span>' : currentIndex + 1*/
                        // }, isFrozen: true
                    },
                    { field: 'name', width: 147, columnAlign: 'center', isResize: true  },
                    { field: 'Heartrate', width: 137, columnAlign: 'center', isResize: true },
                    { field: 'blood', width: 147, columnAlign: 'center',  isResize: true },
                    { field: 'HRV', width: 137, columnAlign: 'center', isResize: true },
                    { field: 'microcirculation', width: 137, columnAlign: 'center', isResize: true },
                    { field: 'Bloodoxygen', width: 137, columnAlign: 'center', isResize: true },
                    { field: 'respirationrate', width: 137, columnAlign: 'center', isResize: true },
                    { field: 'createtime', width: 205, columnAlign: 'center', isResize: true }
                ],
                titleRows: [

                    [
                        { fields: ['love'], title: '', titleAlign: 'center' },
                        { fields: ['id'], title: 'id', titleAlign: 'center' },
                        { fields: ['name'], title: '姓名', titleAlign: 'center' },
                        { fields: ['Heartrate'], title: '心率', titleAlign: 'center', },//orderBy: ''
                        { fields: ['blood'], title: '血压', titleAlign: 'center' },
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
        	if(parseInt(rowData.Heartrate)>0 && columnName=='Heartrate'){
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
            // console.log('haha');
          //  axios.post(GetURLInfo()+"health/queryBeadhouseList").then(this.getnew)

        },
        getnew(res){
        
        //	var tableData=JSON.stringify(res.data.data);
         //    console.log(res.data.data[0]);
          //   for(var i=0;i<res.data.data.length;i++){
         //   	 this.tableConfig.tableData.push(res.data.data[i]);
         //    }
         	 
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