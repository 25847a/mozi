 var tableList2 = new Array();
  	var pageIndex1=1;
    var pageSize1=10;
    var imei='';
var agents = {
    template: "#replace",
    data() {
        return {
            //接收id
            uid: this.$route.params.id,
            show: true,
            //数据
            tablelist: [],
            //分页
            total:'',
            dialogFormVisible: false,
            //更换代理商
            form: {
                name: '',
                region: 0,
                option: [
                    {
                        name: 'add',
                        value: 0
                    },
                    {
                        name: 'banana',
                        value: 1
                    },
                ],
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            formLabelWidth: '120px',
            //imput查找
            imei: "",
        }

    },
    methods: {
        //更换代理商确定按钮
        ascertain() {
            alert(this.form.name + this.form.region)
        },
        //查询按钮
        polling() {
        	imei=this.imei;
        	this.goback();
        },
        //更换代理商
        replace(event) {
            this.form.name = event
        },
        //返回按钮
        ret() {
            this.$router.go(0);
        },
        //分页
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
        	pageIndex1=val;
        	this.goback();
            console.log(`当前页: ${val}`);
        },
        //数据请求
        goback: function (uid) {
        	console.log("接收到的ID:" + uid);
            var params = new URLSearchParams();
	       	 params.append('id',uid);
	       	 params.append('pageNum',pageIndex1);
          	 params.append('pageSize',pageSize1);
          	 params.append('imei',imei);
            axios.post(GetURLInfo()+"equipment/queryEquipmentImeiInfo",params).then(this.getnew);
        },
        getnew(res) {
        	if(res.data.code==-1){
    		tableList2=[];
    		for(var i=0;i<res.data.data.length;i++){
    			tableList2.push(res.data.data[i]);
                    }
    		this.tablelist = tableList2;
    		this.total=res.data.total;
    	}
        	}
    },
    mounted: function () {
        console.log(this.uid);
        this.goback(this.uid);
    },
}
//路由
var routes = [
    {
        path: "agents/:id",
        name: "更换",
        component: agents
    }
]
var router = new VueRouter({
    routes: routes
})

var tableDate = new Array();
var agentName ="";
var option=[{id:0,agentName:"最高级别"}];
$.ajax({
    type : "POST",
    url :GetURLInfo()+"agent/queryAgentOption",
    datatype : "json",
    success : function(result) {
    	if(result.code==-1){
    		for(var i=0;i<result.data.length;i++){
    			option.push(result.data[i]);
                    }
    	}else{
    		//tips(result.message);
    	}
    },
    error : function() {
    	//tips("操作异常,请更新页面");
    }
});
var tableList = new Vue({
    el: '#app',
    router: router,
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                multipleSort: false,
                tableData: [],
                columns: [
                    { field: 'id', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'agentName', width: 120, columnAlign: 'center', isResize: true },
                    { field: 'master', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'address', width: 160, columnAlign: 'center', isResize: true },
                    { field: 'landline', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'phone', width: 60, columnAlign: 'center', isResize: true },
                    { field: 'count', width: 50, columnAlign: 'center', isResize: true },
                    { field: 'endDate', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'startDate', width: 100, columnAlign: 'center', isResize: true },
                    { field: 'equipment', width: 100, columnAlign: 'center', isResize: true, componentName: 'table-equipment' },
                    { field: 'fuck', width: 50, columnAlign: 'center', isResize: true, componentName: 'table-operation' }

                ],
                titleRows: [
                    [
                    	{ fields: ['id'], title: '编号', titleAlign: 'center' },
                        { fields: ['agentName'], title: '代理商姓名', titleAlign: 'center' },
                        { fields: ['master'], title: '负责人', titleAlign: 'center' },
                        { fields: ['address'], title: '地址', titleAlign: 'center' },
                        { fields: ['landline'], title: '座机', titleAlign: 'center', },//orderBy: ''
                        { fields: ['phone'], title: '手机', titleAlign: 'center' },
                        { fields: ['count'], title: '设备数', titleAlign: 'center' },
                        { fields: ['endDate'], title: '注册时间', titleAlign: 'center' },
                        { fields: ['startDate'], title: '到期时间', titleAlign: 'center' },
                        { fields: ['equipment'], title: '设备', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            },
            show: true,
            //添加代理商
            type: 0,//默认选中第一项
            options: option,
            //添加列表对象
            addlist:{
            	agentName:"",
            	address:"",
            	master:"",
            	landline:"",
            	phone:"",
            	mailbox:"",
            	startDate:""
            },
            //input查询
            full: "",
            //修改input
            alterlist: {
            	updateAgentName:"",
            	updateMaster:"",
            	updateAddress:"",
            	updateLandline:"",
            	updatePhone:"",
            	updateMailbox:"",
            },
            //录入添加输入框
            input: '',
            //自动添加勾选
   //         radio: '',
            //录入添加
            editableTabsValue: '2',
            editableTabs: [],
            tabIndex: 2
        }
    },
    // 监听,当路由发生变化的时候执行
    watch: {
        $route(to, from) {
            console.log(to.path);
            if (to.path == "/") {
                this.show = true
            } else {
                this.show = false
            }
        }
    },
    methods: {
        //录入添加
        addTab(targetName) {
       //     console.log(this.radio)
            if(this.input === ""){
                jqueryAlert({
                    'content': '不能为空',
                    'closeTime': 2000
                })
            }else if(this.input.length != 15){
                jqueryAlert({
                    'content': '请输入15位数字',
                    'closeTime': 2000
                })
            }else{
                let newTabName = ++this.tabIndex + '';
                this.editableTabs.push({
                    title: this.input,
                    name: newTabName,
                });
                this.editableTabsValue = newTabName;
            }
        },
        removeTab(targetName) {
            let tabs = this.editableTabs;
            let activeName = this.editableTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }

            this.editableTabsValue = activeName;
            this.editableTabs = tabs.filter(tab => tab.name !== targetName);
        },
        //录入确定键
        enter(){
        	var imeis = new Array();
        	
        			
        	for( var o in this.editableTabs){
        		imeis.push(this.editableTabs[o].title);
        	}
        	$.ajax({
        	    type : "POST",
        	    url :GetURLInfo()+"equipment/inuptEquipmentImeiInfo",
        	    datatype : "json",
        	    traditional:true,
        	    data:{"id":$("#agentId").val(),"imeis":imeis},
        	    success : function(result) {
        	    	if(result.code==-1){
        	    		tips(result.message);
        	    		location.reload();
        	    	}else{
        	    		tips(result.message);
        	    	}
        	    },
        	    error : function() {
        	    	//tips("操作异常,请更新页面");
        	    }
        	});
        	
        },
        //修改按钮
        define() {
        	if(this.alterlist.updateAgentName=="" || this.alterlist.updateMaster=="" || this.alterlist.updateAddress=="" || this.alterlist.updateLandline=="" || this.alterlist.updatePhone=="" || this.alterlist.updateMailbox==""){
        		tips("文本框不能为空");
        	}else{
        		$.ajax({
            	    type : "POST",
            	    url :GetURLInfo()+"agent/updateAgentInfo",
            	    datatype : "json",
            	    data:{"id":$("#updateId").val(),"agentName":this.alterlist.updateAgentName,"address":this.alterlist.updateAddress,"master":this.alterlist.updateMaster,"landline":this.alterlist.updateLandline,"phone":this.alterlist.updatePhone,"mailbox":this.alterlist.updateMailbox},
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
        //查询按钮
        query(){
        	agentName=this.full;
        	tableList.goback();
        },
        //重置按钮
        reset(){
            this.full = ""
        },
        //刷新按钮
        refresh(){
	        location.reload();
        },

        //添加确定按钮
        fix() {
        	if(this.addlist.agentName=="" || this.addlist.master=="" || this.addlist.landline=="" || this.addlist.mailbox=="" || this.addlist.startDate==""	){
        		tips("不能为空");return;
        	}
        	$.ajax({
        	    type : "POST",
        	    url :GetURLInfo()+"agent/addAgentInfo",
        	    datatype : "json",
        	    data:{"type":this.type,"agentName":this.addlist.agentName,"phone":this.addlist.phone,"address":this.addlist.address,"master":this.addlist.master,"landline":this.addlist.landline,"mailbox":this.addlist.mailbox,"startDate":this.addlist.startDate},
        	    success : function(result) {
        	    	if(result.code==-1){
        	    		tips(result.message);
        	    		location.reload();
        	    	}else{
        	    		//tips(result.message);
        	    	}
        	    },
        	    error : function() {
        	    	//tips("操作异常,请更新页面");
        	    }
        	});
        },
        login: function (msg) {
            console.log(msg)
            router.push({ name: "更换", params: { id: msg } });
            this.show = !this.show;
        },

        //分页
        getTableData() {
        	 this.tableConfig.tableData = tableDate;
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
        //接收子组件的params
        customCompFunc(params) {
            if (params.type === 'edit') {
            	$("#updateId").val(params.rowData['id']);
            	this.alterlist.updateAgentName=params.rowData['agentName'];
            	this.alterlist.updateMaster=params.rowData['master'];
            	this.alterlist.updateAddress=params.rowData['address'];
            	this.alterlist.updateLandline=params.rowData['landline'];
            	this.alterlist.updatePhone=params.rowData['phone'];
            	this.alterlist.updateMailbox=params.rowData['mailbox'];
            } else if (params.type === 'editt') { //列表查看按钮
                this.login(`${params.rowData}`)
            } else if (params.type === 'edittt') {
            	$("#agentId").val(params.rowData['id']);
            	document.getElementById("inputAgentName").innerHTML=params.rowData['agentName'];
            	document.getElementById("inputMaster").innerHTML=params.rowData['master'];
            	document.getElementById("inputPhone").innerHTML=params.rowData['phone'];
            	document.getElementById("inputAddress").innerHTML=params.rowData['address'];
            	document.getElementById("inputCount").innerHTML=params.rowData['count'];
            }

        },
        //数据请求
        goback: function () {
             var params = new URLSearchParams();
	       	 params.append('agentName',agentName);
	       	 params.append('pageNum',this.pageIndex);
	       	 params.append('pageSize',this.pageSize);
             axios.post(GetURLInfo()+"agent/queryAgentList",params).then(this.getnew);

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
});

// 自定义列组件<a href="" @click.stop.prevent="deleteRow(rowData,index)"></a>
Vue.component('table-operation', {
    template: `<span class="a">
        <a href="" id="handle" @click.stop.prevent="update(rowData,index)"><img src="../img/agent01.png" alt="" /></a>&nbsp;        
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
            let params = { type: 'edit',rowData: this.rowData };
            this.$emit('on-custom-comp', params);
            $('.operation').click();
        },
    }
})
// 自定义列组件
Vue.component('table-equipment', {
    template: `<span>
        <a href="#" @click.stop.prevent="update(rowData,index)">查看</a>&nbsp;
        <a href="#" @click.stop.prevent="Row(rowData,index)">录入</a>
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
        	console.log("点击查看跳转页面>>>>>>>"+this.rowData.id);
            let params = { type: 'editt', rowData: this.rowData.id};
            this.$emit('on-custom-comp', params);
        },
        Row() {
            $('.enter').click();
            // 参数根据业务场景随意构造
            let params = { type: 'edittt',rowData: this.rowData };
            this.$emit('on-custom-comp', params);

        }
    }
})