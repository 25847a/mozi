$(function() {
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
            {field : 'providerNo',tile:"献浆员编号",align:'center'},
            {field : 'idNo',title : '身份证',align : 'center'},
            {field : 'bloodType',title : '血型',align : 'center',templet: function (d){ // 单元格格式化函数
                var text = '-';
                if (d.bloodType ==0) {
                    text = "A";
                } else if(d.bloodType==1){
                    text = "B";
                }else if(d.bloodType==2){
                    text="O";
                }else{
                    text="AB";
                }
                return text;
            }},

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
            {field : 'day',title : '未献浆天',align : 'center'},
            {field : 'collectionDate',title : '最后献浆时间',align : 'center',templet : function(d) { // 单元格格式化函数
                if(null!=d.collectionDate){
                    return dateFtt('yyyy-MM-dd',new Date(d.collectionDate));
                }
                return '';
            }},
            {fixed : 'right',title : '操作',width : 165,align : 'center',toolbar : '#barDemo'}
        ] ]
        dataAll("newtestS",cols,{"startDay":$("#startDay").val(),"endDay":$("#endDay").val()},GetURLInfo()+"/queryQuarantine",'','',function(){
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
                 $.ajax({
                     type : "post",
                     url : GetURLInfo() + "/quarantineAdd",
                     data : {"code":data.providerNo,"name":data.name},
                     success : function(result) {
                         if(result.code==-1) {
                             layer.alert(result.message,function(){
                                 layer.closeAll();
                             });
                         }else{
                             layer.alert(result.message);
                         }
                     }
                 });
            }
        })
    })
    /*
     * initDate("#startDate"); initDate("#endDate");
     */
    function doSearch() {
        var name = $("#name").val()
        var startDay = $("#startDay").val();
        var endDay = $("#endDay").val()
        var r = /^\+?[1-9][0-9]*$/;　　//正整数
        var flag=r.test(startDay);
        var flag1=r.test(endDay);
        if(!flag){
            layer.alert("开始天数必须为正整数");
            return false;
        }else if(!flag1){
            layer.alert("结束天数必须为正整数");
            return false;
        }
        layui.table.reload('newtestS', {
            where : {
                "name" : name,
                "startDay" : startDay,
                "endDay" : endDay
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