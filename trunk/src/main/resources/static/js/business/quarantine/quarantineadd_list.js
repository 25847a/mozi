$(function() {
    /**
     * 初始化时间控件
     */
    function initDate(dateId){
        initDateDone(dateId,null);
    }
    initDate("#startDate");
    initDate("#endDate");
    layui.use([ 'table' ], function() {
        var table = layui.table //表格
        var cols = [ [ // 表头
            // {type: 'checkbox', fixed: 'left'},
            {field : 'id',title : 'id',
                display : 'none',
                minWidth : '0',
                width : '0',
                type : "space"},
            {field : 'name',title : '姓名',align : 'center'},
            {field : 'sex',title : '性别',align : 'center',templet: function (d){ // 单元格格式化函数
                var text = '-';
                if (d.sex ==0) {
                    text = "男";
                } else{
                    text = "女";
                }
                return text;
            }},
            {field : 'providerNo',title:'献浆员编号',align:'center'},
            {field : 'idNo',title : '身份证',align : 'center'},
            {field : 'plasmaState',title : '状态',align : 'center',templet: function (d){ // 单元格格式化函数
                var text = '-';
                if (d.plasmaState ==0) {
                    text = "正常";
                }else if(d.plasmaState==1){
                    text = "暂时拒绝";
                } else{
                    text = "永久拒绝";
                }
                return text;
            }},
            {field : 'returnNo',title : '回访编号',align : 'center'},
            {field : 'createDate',title : '登记时间',align : 'center',templet : function(d) { // 单元格格式化函数
                if(null!=d.createDate){
                    return dateFtt('yyyy-MM-dd',new Date(d.createDate));
                }
                return '';
            }},
            {fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'}
        ] ]
        dataAll("newtestS",cols,{"startDate":$("#startDate").val(),"endDate":$("#endDate").val()},GetURLInfo()+"/queryQuarantineReg",'','',function(){
            var tab = $("#newtestS").next().find('.layui-table tbody tr');
            tab.click(function(event) {
                var tr = $(event.target).closest("tr")[0];
                var id = $(tr).find("td").eq(0).find("div").html();
            });
        });

        // 搜索按钮点击事件
        $("#searchBtn").click(function() {
            doSearch();
            currentPageAllAppoint = 1; // 当点击搜索的时候，应该回到第一页
        });

        table.on('tool(demo)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
            // lay-filter="对应的值"
            var data = obj.data // 获得当前行数据
                , layEvent = obj.event; // 获得 lay-event 对应的值
             if(layEvent === 'add'){
                 $.post("/common/printQuarantine",{"code":data.providerNo,"name":data.name,"visitCode":data.returnNo},function(result){
                     layer.alert(result.message);
                 });
            }
        })
    })
    /*
     * initDate("#startDate"); initDate("#endDate");
     */
    function doSearch() {
        var providerNo = $("#providerNo").val()
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        layui.table.reload('newtestS', {
            where : {
                "providerNo" : providerNo,
                "startDate" : startDate,
                "endDate" : endDate
            }
        });
    }

    function getToken() {
        return localStorage.getItem("token");
    }
});

//重置
$("#reset").click(function(){
    $("input[type=text]").val("");
});

!function() {
    // 新增
    // 页面一打开就执行，放入ready是为了layer所需配件（css、扩展模块）加载完毕
    $('#bindAdd').on('click', function() {
        add(GetURLInfo() + 'toBindAdd', true);
    });
}();