var option=[{id:0,name:'最高级别'}];
$.ajax({
	    type : "POST",
	    url :GetURLInfo()+"auth/queryAuthInfo",
	    datatype : "json",
	    success : function(result) {
	    	if(result.code==-1){
	    		for(var i=0;i<result.data.length;i++){
	    			option.push(result.data[i]);
	    		}
	    	}else{
	    		tips(result.message);
	    	}
	    },
	    error : function() {
	    	tips("操作异常,请更新页面");
	    }
	});



var tableDate = new Array();
var authName ="";

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

                    { field: 'id', width: 80, columnAlign: 'center', isResize: true },
                    { field: 'name', width: 200, columnAlign: 'center', isResize: true  },
                    { field: 'masterType', width: 100, columnAlign: 'center, isResize: true '},
                    { field: 'url', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'permission', width: 300, columnAlign: 'center', isResize: true },
                    { field: 'parentId', width: 200, columnAlign: 'center', isResize: true },
                    { field: 'createDate', width: 220, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 300, columnAlign: 'center', componentName: 'table-operation' , isResize: true }

                ],
                titleRows: [
                    [
                    	{ fields: ['id'], title: '编号', titleAlign: 'center' },
                        { fields: ['name'], title: '名称', titleAlign: 'center' },
                        { fields: ['masterType'], title: '权限类型', titleAlign: 'center' },
                        { fields: ['url'], title: '接口地址', titleAlign: 'center' },
                        { fields: ['permission'], title: '权限标记', titleAlign: 'center' },
                        { fields: ['parentId'], title: '父级ID', titleAlign: 'center' },
                        { fields: ['createDate'], title: '创建时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            //查询输入框
            queryInput: "",
            //修改按钮
            authId:"",
            name:"",
            url:"",
            permission:"",
            parentIds:0,//默认选中第一项
            updateParentIds:option,
            masterType:"menu",
            updateMasterType:[
            	{key:'menu',value:"页面级别"},
                {key:'button',value:"按钮级别"},
            ],
          //新增
            addName:"",
            addParentIds:0,//默认选中第一项
            optionParentIds:option,
            addMasterType:'menu' ,//默认选中第一项
            optionsMasterType:[
            	{key:'menu',value:"页面级别"},
                {key:'button',value:"按钮级别"},
            ],
            addUrl:"",
            addPermission:"",
        }
    },
    methods: {
        //点击确定新增
        fix(){
        	if(this.addName=="" || this.addUrl=="" || this.addPermission==""){
        		tips("文本栏不能为空");return;
        	}else{
        		var data={
        			"name":this.addName,
        			"masterType":this.addMasterType,
        			"url":this.addUrl,
        			"permission":this.addPermission,
        			"parentId":this.addParentIds
        		};
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"auth/addAuthInfo",
            	    datatype : "json",
            	    data:data,
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
        	if(this.name=="" || this.url=="" || this.permission==""){
        		tips("文本栏不能为空");
        	}else{
        		var data={
        	            "id":this.authId,
            			"name":this.name,
            			"permission":this.permission,
            			"url":this.url,
            			"parentId":this.parentIds,
            			"masterType":this.masterType
            		};
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"auth/updateAuthInfo",
            	    datatype : "json",
            	    data:data,
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
        	authName=this.queryInput;
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
            if (params.type === 'delete') { // do delete operation
                //alert("你确定删除吗？")
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                  }).then(() => {
                	  deleteRole(params.id);
                  }).catch(() => {
                	  tips("已取消禁用");         
                  });
                                
            } else if (params.type === 'edit') {
                console.log(`行号：${params.id} 姓名：${params.parentId}`);
                
                this.authId = params.id;
                this.name = params.name;
                this.url = params.url;
                this.permission = params.permission;
                this.parentIds = params.parentId;
                this.masterType = params.masterType;
            }

        },
        //数据请求
        goback: function () {
        	var params = new URLSearchParams();
	       	 params.append('name',authName);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
          axios.post(GetURLInfo()+"auth/queryAuthInfoList",params).then(this.getnew);
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
           let params = {type:'edit',id:this.rowData.id,name:this.rowData.name,masterType:this.rowData.masterType,url:this.rowData.url,permission:this.rowData.permission,parentId:this.rowData.parentId};
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
	    url :GetURLInfo()+"auth/deleteAuthInfo",
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
