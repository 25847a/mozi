		/**
		 * 获取基础信息
		 */
		$.ajax({
		    type : "POST",
		    url :GetURLInfo()+"agent/queryAgentInfo",
		    datatype : "json",
		    success : function(result) {
		    	if(result.code==-1){
		    		var data = result.data;
		    		$("#agentId").val(data.id);
		    		document.getElementById("agentName").innerHTML=data.agentName;
		    		document.getElementById("address").innerHTML=data.address;
		    		document.getElementById("master").innerHTML=data.master;
		    		document.getElementById("landline").innerHTML=data.landline;
		    		document.getElementById("phone").innerHTML=data.phone;
		    		document.getElementById("mailbox").innerHTML=data.mailbox;
		    		document.getElementById("endDate").innerHTML=data.endDate;
		    		document.getElementById("startDate").innerHTML=data.startDate;
		    		$("#agentNameA").val(data.agentName);
		    		$("#addressA").val(data.address);
		    		$("#masterA").val(data.master);
		    		$("#landlineA").val(data.landline);
		    		$("#phoneA").val(data.phone);
		    		$("#mailboxA").val(data.mailbox);
		    	}else{
		    		tips(data.message);
		    	}
		    },
		    error : function() {
		    	tips("操作异常,请更新页面");
		    }
		});
		function updateAgentInfo(){
			$.ajax({
			    type : "POST",
			    url :GetURLInfo()+"agent/queryAgentInfo",
			    datatype : "json",
			    success : function(result) {
			    	if(result.code==-1){
			    		var data = result.data;
			    		tableList.update.agentName=data.agentName;
			    		tableList.update.address=data.address;
			    		tableList.update.master=data.master;
			    		tableList.update.landline=data.landline;
			    		tableList.update.phone=data.phone;
			    		tableList.update.mailbox=data.mailbox;
			    	}else{
			    		tips(data.message);
			    	}
			    },
			    error : function() {
			    	tips("操作异常,请更新页面");
			    }
			});
		}
var pageIndex=1;
var pageSize=10;
 var imeiNum='';
var tableList = new Vue({
        el: '#app',
        data() {
            return {
            	slist: [],
            	total:'',
            	 //新增弹框
                dialogFormVisible: false,
                dialogFormModify:false,
                updateFormVisible: false,
                pickerOptions:{
                	disabledDate(time){
                		return time.getTime()>Date.now();
                	}
                },
                options: [{
                    value: '0',
                    label: '初高中'
                    }, {
                    value: '1',
                    label: '大专'
                    }, {
                    value: '2',
                    label: '本科'
                    }, {
                    value: '3',
                    label: '硕士'
                    }, {
                    value: '4',
                    label: '博士'
                    }],
                   education: '',
                   optionsex: [{
                       value: '0',
                       label: '女'
                       }, {
                       value: '1',
                       label: '男'
                       }],
                   gender:'',
                   optionisMarry: [{
                       value: '0',
                       label: '未婚'
                       }, {
                       value: '1',
                       label: '已婚'
                       }],
                    isMarry:'',
                form: {
                	name: '',
                	gender: '',
                	post: '',
                	birth: '',
                	education: '',
                	register: '',
                	isMarry: '',
                	entryDate: '',
                	phone: ''
                },
                update: {
                	agentName: '',
                	address: '',
                	master: '',
                	landline: '',
                	phone: '',
                	mailbox: ''
                },
                modify: {
                    name: '',
                    gender: '',
                    post: '',
                    birth: '',
                    education: '',
                    register: '',
                    isMarry: '',
                	entryDate: '',
                	phone: ''
                },
                formLabelWidth: '120px'
                
            }

        },
        methods: {
        	//新增弹窗确定
            determine() {
            	if(this.form.name=="" || this.form.gender=="" || this.form.post=="" || this.form.birth=="" || this.form.education=="" || this.form.register=="" || this.form.isMarry=="" || this.form.entryDate=="" || this.form.phone==""){
            		tips("不能为空");return;
            	}
            	if(!isPoneAvailable(this.form.phone)){
            		tips("请输入正确的手机号码");return;
            	}
            	$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"hr/addHrInfo",
            	    datatype : "json",
            	    data:{"agentId":$("#agentId").val(),"name":this.form.name,"gender":this.form.gender,"post":this.form.post,"birth":this.form.birth,"education":this.form.education,"register":this.form.register,"isMarry":this.form.isMarry,"entryDate":this.form.entryDate,"phone":this.form.phone},
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
            },
            updateAgen() {
            	if(this.update.agentName=="" || this.update.address=="" || this.update.master=="" || this.update.landline=="" || this.update.phone=="" || this.update.mailbox==""){
            		tips("不能为空");return;
            	}
            	$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"agent/updateAgentInfo",
            	    datatype : "json",
            	    data:{"id":$("#agentId").val(),"agentName":this.update.agentName,"address":this.update.address,"master":this.update.master,"landline":this.update.landline,"phone":this.update.phone,"mailbox":this.update.mailbox},
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
            },
            updateHrInfo() {
            	if(this.modify.name=="" || this.gender=="" || this.modify.post=="" || this.modify.birth=="" || this.education=="" || this.modify.register=="" || this.isMarry=="" || this.modify.entryDate=="" || this.modify.phone==""){
            		tips("不能为空");return;
            	}
            	if(!isPoneAvailable(this.modify.phone)){
            		tips("请输入正确的手机号码");return;
            	}
            	$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"hr/updateHrInfo",
            	    datatype : "json",
            	    data:{"id":$("#hrId").val(),"name":this.modify.name,"gender":this.gender,"post":this.modify.post,"birth":this.modify.birth,"education":this.education,"register":this.modify.register,"isMarry":this.isMarry,"entryDate":this.modify.entryDate,"phone":this.modify.phone},
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
            },
        	goback: function () {
            	var params = new URLSearchParams();
            	console.log("pageNum:"+pageIndex+"<<<<<pageSize"+pageSize);
           	 params.append('pageNum',pageIndex);
           	 params.append('pageSize',pageSize);
           	 params.append('imeiNum',imeiNum);
                axios.post(GetURLInfo()+"hr/queryHrList",params).then(this.getnew);
            },
            getnew(res) {
            	if(res.data.code==-1){
            		tableDate=[];
            		for(var i=0;i<res.data.data.length;i++){
            			tableDate.push(res.data.data[i]);
                            }
            		this.slist = tableDate;
            		this.total=res.data.total;
            	}
            },
            change: function (id, index) {
            	$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"hr/queryHrInfo",
            	    data:{"id":id},
            	    datatype : "json",
            	    success : function(result) {
            	    	if(result.code==-1){
            	    		var data= result.data;
            	    		$("#hrId").val(data.id);
            	    		tableList.modify.name=data.name;
            	    		tableList.gender=data.gender.toString();
            	    		tableList.modify.post=data.post;
            	    		tableList.modify.birth=data.birth;
            	    		tableList.education=data.education.toString();
            	    		tableList.modify.register=data.register;
            	    		tableList.isMarry=data.isMarry.toString();
            	    		tableList.modify.entryDate=data.entryDate;
            	    		tableList.modify.phone=data.phone;
            	    	}else{
            	    		tips(result.message);
            	    	}
            	    },
            	    error : function() {
            	    	tips("操作异常,请更新页面");
            	    }
            	});
            },
          //点击删除按钮
            deleteRow: function (id, index) {
                this.$confirm('此操作将删除该人员所有信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                 //   type: 'warning'
                }).then(() => {
                	
                	deleteAdmin(id);
                    /*this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });*/
            //        this.slist.splice(index, 1);
                }).catch(() => {
                	tips("已取消删除");
//                    this.$message({
//                        type: 'info',
//                        message: '已取消删除'
//                    });
                });
                

            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
              },
              handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
              }
        },
        mounted() {
        	 this.goback();
        }
    });

function deleteAdmin(id){
	$.ajax({
	    type : "POST",
	    url :GetURLInfo()+"hr/deleteHrInfo",
	    data:{"id":id},
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
