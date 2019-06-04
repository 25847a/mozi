var option= new Array();
$.ajax({
	    type : "POST",
	    url :GetURLInfo()+"role/queryRoleInfo",
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
var optionsAgent= new Array();
$.ajax({
	    type : "POST",
	    url :GetURLInfo()+"agent/queryAgent",
	    datatype : "json",
	    success : function(result) {
	    	if(result.code==-1){
	    		for(var i=0;i<result.data.length;i++){
	    			optionsAgent.push(result.data[i]);
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
var adminName ="";

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

                    { field: 'id', width: 80, columnAlign: 'center'},
                    { field: 'role', width: 100, columnAlign: 'center' },
                    { field: 'name', width: 100, columnAlign: 'center'},
                    { field: 'agentName', width: 200, columnAlign: 'center'},
                    { field: 'account', width: 200, columnAlign: 'center'},
                    
                    { field: 'isDisable', width: 80, columnAlign: 'center',formatter: function (rowData) {
                    	if(rowData.isDisable==0){
                    		return "可用";
                    	}else{
                    		return "禁用";
                    	}
                    }},
                    { field: 'isLoginCount', width: 100, columnAlign: 'center'},
                    { field: 'loginErrorCount', width: 100, columnAlign: 'center'},
                    { field: 'lockDate', width: 214, columnAlign: 'center' },
                    { field: 'lastDate', width: 214, columnAlign: 'center' },
                    { field: 'fuck', width: 200, columnAlign: 'center', componentName: 'table-operation' }

                ],
                titleRows: [
                    [
                    	{ fields: ['id'], title: '编号', titleAlign: 'center' },
                        { fields: ['role'], title: '角色名称', titleAlign: 'center' },
                        { fields: ['name'], title: '用户名', titleAlign: 'center' },
                        { fields: ['agentName'], title: '隶属代理商', titleAlign: 'center' },
                        { fields: ['account'], title: '账号', titleAlign: 'center' },
                        { fields: ['isDisable'], title: '是否禁用', titleAlign: 'center' },
                        { fields: ['isLoginCount'], title: '登录次数', titleAlign: 'center' },
                        { fields: ['loginErrorCount'], title: '登录错误次数', titleAlign: 'center' },
                        { fields: ['lockDate'], title: '锁定时间', titleAlign: 'center' },
                        { fields: ['lastDate'], title: '最后登陆时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            //查询输入框
            queryInput: "",
            //修改按钮
            adminId:"",
            account:"",
            name:"",
            age:"",
            phone:"",
            address:"",
            remarks:"",
            role:1 ,//默认选中第一项
            updateRole:option,
            agent:1 ,//默认选中第一项
            updateAgent:optionsAgent,
            aender:0 ,//默认选中第一项
            updateAender:[
            	{name:'女',value:0},
                {name:'男',value:1},
            ],
          //新增
            addAccount:"",
            addPassword:"",
            addName:"",
            addAge:"",
            addPhone:"",
            addAddress:"",
            addRemarks:"",
            addRole:1 ,//默认选中第一项
            optionsRole:option,
            addAgent:1 ,//默认选中第一项
            optionsAgent:optionsAgent,
            addAender:0 ,//默认选中第一项
            optionsAender:[
            	{name:'女',value:0},
                {name:'男',value:1},
            ],
        }
    },
    methods: {
        //点击确定新增
        fix(){
        	if(this.addAccount=="" || this.addPassword=="" || this.addName=="" || this.addAge=="" || this.addPhone=="" || this.addAddress=="" || this.addRemarks==""){
        		tips("文本栏不能为空");return;
        	}else{
        		var data={
        			"roleId":this.addRole,
        			"agentId":this.addAgent,
        			"account":this.addAccount,
        			"passWord":this.addPassword,
        			"name":this.addName,
        			"age":this.addAge,
        			"gender":this.addAender,
        			"phone":this.addPhone,
        			"address":this.addAddress,
        			"remarks":this.addRemarks
        		};
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"admin/addAdminInfo",
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
        	if(this.name=="" || this.descn=="" || this.descn=="" || this.descn=="" || this.descn=="" || this.descn=="" || this.descn=="" || this.descn=="" ){
        		tips("文本栏不能为空");
        	}else{
        		var data={
        	            "manId":this.adminId,
            			"roleId":this.role,
            			
            			"id":this.adminId,
            			"agentId":this.agent,
            			"account":this.account,
            			"name":this.name,
            			"age":this.age,
            			"phone":this.phone,
            			"address":this.address,
            			"remarks":this.remarks,
            			"gender":this.aender
            		};
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"admin/updateAdminInfo",
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
        	adminName=this.queryInput;
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
                this.$confirm('此操作将永久禁用该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                  }).then(() => {
                	  deleteRole(params.id);
                  }).catch(() => {
                	  tips("已取消禁用");         
                  });
                                
            } else if (params.type === 'edit') {
                console.log(`行号：${params.id} 姓名：${params.name}`);
                this.adminId = params.adminId;
                this.role = params.role;
                this.agent = params.agent;
                this.account = params.account;
                this.name = params.name;
                this.age = params.age;
                this.phone = params.phone;
                this.address = params.address;
                this.remarks=params.remarks;
                this.aender=params.aender;
            }

        },
        //数据请求
        goback: function () {
        	var params = new URLSearchParams();
	       	 params.append('name',adminName);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
          axios.post(GetURLInfo()+"admin/queryAdminInfoList",params).then(this.getnew);
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
        <el-button type="text" @click.stop.prevent="deleteRow(rowData,index)">禁用</el-button>
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
        	let  params ={};
        	$.ajax({
        	    type : "POST",
        	    url :GetURLInfo()+"admin/queryupdateAdminInfo",
        	    async:false,
        	    data:{"id":this.rowData.id},
        	    datatype : "json",
        	    success : function(result) {
        	    	if(result.code==-1){
        	    		var data = result.data;
        	    	// 参数根据业务场景随意构造
        	    		params = {type:'edit',adminId:data.id,role:result.role,agent:data.agentId,aender:data.gender,id:data.id,account:data.account,name:data.name,age:data.age,phone:data.phone,address:data.address,remarks:data.remarks};
        	    	}else{
        	    		tips(result.message);
        	    	}
        	    },
        	    error : function() {
        	    	tips("操作异常,请更新页面");
        	    }
        	});
        	console.log(params);
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
	    url :GetURLInfo()+"admin/deleteAdminInfo",
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
