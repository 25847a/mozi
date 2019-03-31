var flag=true;
$(function() {
    $("#loadidNo").click(function () {
        $("#loadidNo").text("读取中...");
        if(!flag){
            return;
        }
        flag=false;
        $.ajax({
            type: "post",
            url: "/common/idCard",
            dataType: "json",
            success: function (data) { //返回值
                if (data.code == -1) {
                    func(data.data);
                } else {
                    parent.layer.alert(data.message);
                }
                flag=true;
                $("#loadidNo").text("请提取身份信息");
            },
            error: function () {
                layer.alert('读取身份证错误，请稍后重试');
            }
        });
    })
    $("#loadJust").click(function () {//正面读取
        $("#loadJust").text("读取中...");
        if(!flag){
            return;
        }
        flag=false;
        $.ajax({
            type: "post",
            url: "/common/LoadIdCardJust",
            dataType: "json",
            success: function (data) { //返回值
                if (data.code == -1) {
                    $("#demo1").attr("src","data:image/jpeg;base64,"+data.data.picture);
                    $("#demo3").attr("src","data:image/jpeg;base64,"+data.data.photo);
                    func(data.data);
                } else {
                    $("#demo1").attr("src","/img/new_pa1.png");
                    parent.layer.alert(data.message);
                }
                flag=true;
                $("#loadJust").text("请提取正面身份信息");
            },
            error: function () {
                layer.alert('读取身份证错误，请稍后重试');
            }
        });
    })

    $("#loadBack").click(function () {//反面读取
        $("#loadBack").text("读取中...");
        if(!flag){
            return;
        }
        flag=false;
        $.ajax({
            type: "post",
            url: "/common/LoadIdCardBack",
            dataType: "json",
            success: function (data) { //返回值
                if (data.code == -1) {
                    $("#demo2").attr("src","data:image/jpeg;base64,"+data.data.picture);
                    funcf(data.data);
                } else {
                    parent.layer.alert(data.message);
                }
                flag=true;
                $("#loadBack").text("请提反面取身份信息");
            },
            error: function () {
                layer.alert('读取身份证错误，请稍后重试');
            }
        });
    })
});


