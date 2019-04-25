var tableDate = new Array();
var name ="";
var account ="";
//获取角色选项
$.ajax({
    type : "POST",
    url :GetURLInfo()+"role/queryRoleInfo",
    datatype : "json",
    success : function(result) {
    	if(null!=result.data){
    		for (var o in result.data){
                var str = "<option value=" + result.data[o].id + ">" + result.data[o].name + "</option>";
                $("#role").append(str);
            }
    	}
    },
    error : function() {
    	 $.MsgBox.Alert("消息","获取角色栏失败");
    }
});
var tableList=new Vue({
    el: '#app',
    data() {
        return {
            pageIndex: 1,
            pageSize: 10,
            total:'',
            tableConfig: {
                multipleSort: false,
                tableData: [

                ],
                columns: [
                	{ field: 'id', width: 100, columnAlign: 'center', isResize: true ,type: 'selection'},
                    { field: 'role', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'position', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'name', width: 190, columnAlign: 'center', isResize: true },
                    { field: 'account', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'isDisable', width: 130, columnAlign: 'center', isResize: true ,formatter: function (rowData) {
                    	if(rowData.isDisable==1){
                    		return '禁用';
                    	}else{
                    		return '可用';
                    	}
                    } },
                    { field: 'isLoginCount', width: 130, columnAlign: 'center',  isResize: true },
                    { field: 'loginErrorCount', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'lockDate', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'lastDate', width: 150, columnAlign: 'center', isResize: true },
                    { field: 'fuck', width: 150, columnAlign: 'center', isResize: true ,componentName:'table-operation'}

                ],
                titleRows: [
                    [
                    	{ fields: ['id'],title: 'id' ,titleAlign: 'center' },
                        { fields: ['role'],title: '角色' ,titleAlign: 'center' },
                        { fields: ['position'], title: '职称', titleAlign: 'center' },
                        { fields: ['name'], title: '真实姓名', titleAlign: 'center' },
                        { fields: ['account'], title: '用户名', titleAlign: 'center' },
                        { fields: ['isDisable'], title: '状态', titleAlign: 'center', },//orderBy: ''
                        { fields: ['isLoginCount'], title: '登录次数', titleAlign: 'center' },
                        { fields: ['loginErrorCount'], title: '登陆错误次数', titleAlign: 'center' },
                        { fields: ['lockDate'], title: '锁定时间', titleAlign: 'center' },
                        { fields: ['lastDate'], title: '最后次登陆时间', titleAlign: 'center' },
                        { fields: ['fuck'], title: '操作', titleAlign: 'center' },
                    ],
                ],
            }
        }
    },
    methods: {
        getTableData() {
            this.tableConfig.tableData = tableDate.slice((this.pageIndex - 1) * this.pageSize, (this.pageIndex) * this.pageSize);
            this.total = tableDate.length;
            console.log("A");
        },
        pageChange(pageIndex) {

            this.pageIndex = pageIndex;
            this.getTableData();
            console.log("B");
        },
        pageSizeChange(pageSize) {

            this.pageIndex = 1;
            this.pageSize = pageSize;
             this.getTableData();
        },
        customCompFunc(params){

                console.log(params);

                if (params.type === 'delete'){ // do delete operation
                    console.log("delete")
                    console.log(params)
                    this.$delete(this.tableConfig.tableData,params.index);

                }else if (params.type === 'edit'){ // do edit operation

                    alert(`行号：${params.index} 姓名：${params.rowData['name']}`)
                }

            },
            selectALL(selection) {

                console.log('select-aLL', selection);
            },

            selectChange(selection, rowData) {
                console.log('select-change', selection, rowData);
            },

            selectGroupChange(selection) {
                console.log('select-group-change', selection);
            },
            //数据请求
        goback:function(){
        	var params = new URLSearchParams();
        	 params.append('account',account);
        	 params.append('name',name);
            axios.post(GetURLInfo()+"admin/queryAdminInfoList",params).then(this.getnew);

        },
        getnew(res){
        	if(res.data.code==-1){
        		tableDate=[];
        		for(var i=0;i<res.data.data.length;i++){
        			tableDate.push(res.data.data[i]);
        			this.getTableData();
                        }
        	}
        }
    },
     created() {
        
     },
    mounted(){
        this.goback();
        this.getTableData();
    }
});
/**
 * 查询触发事件
 * @returns
 */
function query(){
	account = $("#account").val();
	name = $("#name").val();
	tableList.goback();
}
/**
 * 刷新按钮触发事件
 * @returns
 */
function refresh(){
	location.reload();
}
/**
 * 重置按钮触发事件
 * @returns
 */
function empty(){
	$("#search_par input").val("");
	$("#search_par select").val("");
}
// 自定义列组件
    Vue.component('table-operation',{
        template:`<span>
        <a href="" @click.stop.prevent="update(rowData,index)">修改</a>&nbsp;
        <a href="" @click.stop.prevent="deleteRow(rowData,index)">删除</a>
        </span>`,
        props:{
            rowData:{
                type:Object
            },
            field:{
                type:String
            },
            index:{
                type:Number
            }
        },
        methods:{
            update(){

               // 参数根据业务场景随意构造
               let params = {type:'edit',index:this.index,rowData:this.rowData};
               this.$emit('on-custom-comp',params);
            },

            deleteRow(){

                // 参数根据业务场景随意构造
                let params = {type:'delete',index:this.index};
                this.$emit('on-custom-comp',params);

            }
        }
    })