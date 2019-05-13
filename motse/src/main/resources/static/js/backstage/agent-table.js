 var tableList2 = new Array();
  	var pageIndex1=1;
    var pageSize1=10;
    var imei='';
var agents = {
    template: "#replace",
    data() {
        return {
            //接收点击查看获取打的代理商id
            id: this.$route.params.id,
            show: true,
            //数据
            tablelist:[],
            total:'',
            //分页
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
            imei:""
        }

    },
    methods: {
        //更换代理商确定按钮
        ascertain(){
            alert(this.form.name+this.form.region);
        },
        //查询按钮
        polling(){
        	imei=this.imei;
        	this.goback();
        },
        //更换代理商
        replace(event) {
            alert("vvvvv");
            this.form.name = event
        },
        //返回按钮
        ret() {
            //alert(1212)
            this.$router.go(0)
            //this.$router.go(-1)
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
        goback: function (id) {
            console.log("接收到的ID:" + id);
            var params = new URLSearchParams();
	       	 params.append('id',id);
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
        console.log("拿到ID赋值到ajax:"+this.id);
        this.goback(this.id);
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
                    { field: 'id', width: 100, columnAlign: 'center'},
                    { field: 'agentName', width: 200, columnAlign: 'center' },
                    { field: 'master', width: 100, columnAlign: 'center'},
                    { field: 'address', width: 200, columnAlign: 'center' },
                    { field: 'landline', width: 150, columnAlign: 'center' },
                    { field: 'phone', width: 150, columnAlign: 'center'},
                    { field: 'count', width: 80, columnAlign: 'center'},
                    { field: 'endDate', width: 200, columnAlign: 'center' },
                    { field: 'startDate', width: 200, columnAlign: 'center' },
                    { field: 'equipment', width: 150, columnAlign: 'center', componentName: 'table-equipment' },
                    { field: 'fuck', width: 100, columnAlign: 'center', componentName: 'table-operation' }

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
            //input查询对象
            full:"",
            //修改input
            alterlist:{
                name:"",
                age:"",
                phone:"",
                site:""
            },
            altersex: 0,//默认选中第一项
            optionalter: [
                {
                    name: '男',
                    value: 0
                },
                {
                    name: '女',
                    value: 1
                },
            ],
        }
    },
    // 监听,当路由发生变化的时候执行
   /* watch: {
        $route(to, from) {
            console.log("监听,当路由发生变化的时候执行"+to.path);
            if (to.path == "/") {
                this.show = true
            } else {
                this.show = false
            }

        }
    },*/
    methods: {
        //修改按钮
        define(){
            alert(this.alterlist.name+this.alterlist.age+this.alterlist.phone+this.alterlist.site+this.altersex);

        },
        //查询按钮
        query(){
        	agentName=$("#agentName").val();
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
        fix(){
        	if(this.addlist.agentName=="" || this.addlist.master=="" || this.addlist.landline=="" || this.addlist.mailbox=="" || this.addlist.startDate==""	){
        		alert("不能为空");return;
        	}
        	$.ajax({
        	    type : "POST",
        	    url :GetURLInfo()+"agent/addAgentInfo",
        	    datatype : "json",
        	    data:{"type":this.type,"agentName":this.addlist.agentName,"phone":this.addlist.phone,"address":this.addlist.address,"master":this.addlist.master,"landline":this.addlist.landline,"mailbox":this.addlist.mailbox,"startDate":this.addlist.startDate},
        	    success : function(result) {
        	    	if(result.code==-1){
        	    		alert(result.message);
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
        //router点击跳转
        // goHome: function () {
        //     // router.push({name:"首页"});
        //     router.push("/home");
        // },
        login: function (msg) {
            console.log(msg)
            router.push({ name: "更换", params: { id: msg } });
            this.show = !this.show;
        },

        //分页
        getTableData() {

            this.tableConfig.tableData = tableDate;//.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize)
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
            console.log(params);
            if (params.type === 'edit') { // do delete operation
                //alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
                this.alterlist.name = `${params.rowData['name']}`
            } else if (params.type === 'editt') { // do edit operation
            	console.log(`行号：${params.rowData}`);
                this.login(`${params.rowData}`)
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
    // created() {
    //     this.getTableData();
    // },
    mounted() {
        this.goback();
        // this.item = setInterval(this.goback,3000)
    },
    beforeDestroy() {
        clearInterval(this.item);
    }
})

// 自定义列组件<a href="" @click.stop.prevent="deleteRow(rowData,index)"></a>
Vue.component('table-operation', {
    template: '<span class="a">'+
        '<a href="" id="handle" @click.stop.prevent="update(rowData,index)"><img src="../img/agent01.png" alt="" /></a>&nbsp;'+ 
        '</span>',
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
        	alert("跳转了修改按钮");
            // 参数根据业务场景随意构造
            let params = { type: 'edit', index: this.index, rowData: this.rowData };
            this.$emit('on-custom-comp', params);
        },

        // deleteRow(){

        //     // 参数根据业务场景随意构造
        //     let params = {type:'delete',index:this.index};
        //     this.$emit('on-custom-comp',params);

        // }
    }
})
// 自定义列组件
Vue.component('table-equipment', {
    template: "<span>"+
        "<a href='#' v-on:click.stop.prevent='update(rowData,index)'>查看</a>&nbsp;"+
        "<a href='#' >录入</a>"+
       "</span>",
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
        	console.log("点击查看跳转页面>>>>>>>"+this.rowData.id);
            let params = { type: 'editt', rowData: this.rowData.id};
            this.$emit('on-custom-comp', params);
        },

        // deleteRow(){

        //     // 参数根据业务场景随意构造
        //     let params = {type:'delete',index:this.index};
        //     this.$emit('on-custom-comp',params);

        // }
    }
})