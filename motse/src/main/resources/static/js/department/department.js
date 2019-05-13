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
		    		document.getElementById("agentName").innerHTML=data.agentName;
		    		document.getElementById("address").innerHTML=data.address;
		    		document.getElementById("master").innerHTML=data.master;
		    		document.getElementById("landline").innerHTML=data.landline;
		    		document.getElementById("phone").innerHTML=data.phone;
		    		document.getElementById("mailbox").innerHTML=data.mailbox;
		    		document.getElementById("endDate").innerHTML=data.endDate;
		    		document.getElementById("startDate").innerHTML=data.startDate;
		    	}else{
		    		tips(data.message);
		    	}
		    },
		    error : function() {
		    	tips("操作异常,请更新页面");
		    }
		});

var pageIndex=1;
var pageSize=10;
 var imeiNum='';
var tableList = new Vue({
        el: '#app',
        data() {
            return {
            	slist: [],
                total:''
            }

        },
        methods: {
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
	    	//	location.reload();
	    	}else{
	    		tips(result.message);
	    	}
	    },
	    error : function() {
	    	tips("操作异常,请更新页面");
	    }
	});
}