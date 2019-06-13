var tableDate = new Array();
var roleName ="";
var tableList = new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 20,
            total:'',
            tableConfig: {
                multipleSort: false,
                tableData: [],
                columns: [

                    { field: 'id', width: 100, columnAlign: 'center'},
                    { field: 'name', width: 300, columnAlign: 'center' },
                    { field: 'descn', width: 300, columnAlign: 'center'},
                    { field: 'isDisable', width: 300, columnAlign: 'center',formatter: function (rowData) {
                    	if(rowData.isDisable==0){
                    		return "可用";
                    	}else{
                    		return "禁用";
                    	}
                    }},
                    { field: 'createDate', width: 300, columnAlign: 'center' },
                    { field: 'fuck', width: 300, columnAlign: 'center', componentName: 'table-operation' }

                ],
                titleRows: [
                    [
                    	{ fields: ['id'], title: '编号', titleAlign: 'center' },
                        { fields: ['name'], title: '角色名称', titleAlign: 'center' },
                        { fields: ['descn'], title: '角色描述', titleAlign: 'center' },
                        { fields: ['isDisable'], title: '是否禁用', titleAlign: 'center' },
                        { fields: ['createDate'], title: '创建时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            //查询输入框
            queryInput: "",
            //修改按钮
            roleId:"",
            name:"",
            descn:"",
        }
    },
    methods: {
        //点击确定新增
        fix(){
        	if(this.name=="" || this.descn==""){
        		tips("不能为空");return;
        	}else{
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"role/addRoleInfo",
            	    datatype : "json",
            	    data:{"name":this.name,"descn":this.descn,"isDisable":0},
            	    success : function(result) {
            	    	if(result.code==-1){
            	    		tips(result.message);
            	    		location.reload();
            	    	}else{
            	    		tips(result.message);
            	    	}
            	    },
            	    error : function() {
            	    	tips("操作异常,请更新页面");
            	    }
            	});	
        	}
        },
        //点击确定修改
        modify(){
        	if(this.name=="" || this.descn=="" ){
        		tips("不能为空");
        	}else{
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"role/updateRoleInfo",
            	    datatype : "json",
            	    data:{"id":this.roleId,"name":this.name,"descn":this.descn},
            	    success : function(result) {
            	    	if(result.code==-1){
            	    		tips(result.message);
            	    		location.reload();
            	    	}else{
            	    		tips(result.message);
            	    	}
            	    },
            	    error : function() {
            	    	tips("操作异常,请更新页面");
            	    }
            	});
        	}
        },
        // 点击查询获取input 
        query(event) {
        	roleName=this.queryInput;
        	tableList.goback();
        },
        //数据
        getTableData() {
        	this.tableConfig.tableData = tableDate;
        },
        //分页
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
            if (params.type === 'delete') {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                  }).then(() => {
                	  deleteRole(params.id);
                  }).catch(() => {
                	  tips("已取消删除");         
                  });
                                
            } else if (params.type === 'edit') {
                console.log(`行号：${params.id} 姓名：${params.name}`);
                this.roleId = params.id;
                this.name=params.name;
                this.descn=params.descn;
            }

        },
        //数据请求
        goback: function () {
        	var params = new URLSearchParams();
	       	 params.append('name',roleName);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
          axios.post(GetURLInfo()+"role/queryRoleList",params).then(this.getnew);
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
    mounted() {
        this.goback();
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
           let params = {type:'edit',id:this.rowData.id,name:this.rowData.name,descn:this.rowData.descn};
           this.$emit('on-custom-comp',params);
           $('.btn-modify').click();
        },

        deleteRow(){
            // 参数根据业务场景随意构造
            let params = {type:'delete',id:this.rowData.id};
            this.$emit('on-custom-comp',params);

        }
    }
});
function deleteRole(id){
	$.ajax({
	    type : "POST",
	    url :GetURLInfo()+"role/deleteRoleInfo",
	    data:{"id":id,isDisable:1},
	    datatype : "json",
	    success : function(result) {
	    	if(result.code==-1){
	    		tips(result.message);
	    		location.reload();
	    	}else{
	    		tips(result.message);
	    	}
	    },
	    error : function() {
	    	tips("操作异常,请更新页面");
	    }
	});
};