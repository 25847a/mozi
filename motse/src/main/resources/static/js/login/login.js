$(function () {
    //点击登陆按钮时进行的判断
    $(".langing").on("click", function () {
        console.log("111")
        if ($("#usertxt").val() == "" || $("#pwdtxt").val() == "") {
            console.log($("#usertxt").val())
            $.MsgBox.Alert("消息","用户名或密码不能为空！");
            return false;
        } else {
            $(".Error_prompt").fadeOut(400);
            //判断数据类型成功以后像后台发出请求来判断登陆是否成功
            $.ajax({
                type: "post",
                url:  GetURLInfo()+"log/queryAdminLogin",//  url放的是当前页面请求的后台地址。
                dataType: "json",
                data: {"account":$("#account").val(),"passWord":$("#passWord").val()},
                success: function (data) {
                    //用户名和密码校验错误
                	if (data.code == 1) {
                   	 alert(data.message);//失败
                   }else{
                   	window.location.href='/admin/index';             
                   }
                },
                error: function () {
                     $.MsgBox.Alert("消息","登录失败");
                }
            });
        }
    });
    //用户键盘按下enter键判断的事件
    document.onkeypress = function (event) {
        e = event ? event : (window.event ? window.event : null);
        var currKey = 0;
        currKey = e.keyCode || e.which || e.charCode;
        if (currKey == 13) {
            if ($("#usertxt").val() == "" || $("#pwdtxt").val() == "") {
                 $.MsgBox.Alert("消息","用户名或密码不能为空！");
                return false;
            } else {
                $(".Error_prompt").fadeOut(400);
                //判断数据类型成功以后向后台发出请求来判断登陆是否成功
                $.ajax({
                    type: "post",
                    url: "",  //url放的是当前页面请求的后台地址。
                    dataType: "json",
                    data: {"username":$("#usertxt").val(),"password":$("#pwdtxt").val()},
                    success: function (data) {
                        //请求成功之后要做的事情
                        var result = data.result;
                        //用户名和密码校验错误
                        if (result == "1") {
                            window.location.href='index.html';                       
                        }else{
                             $.MsgBox.Alert("消息","该用户不存在或密码错误");
                        }
                    },
                    error: function () {
                         $.MsgBox.Alert("消息","登录失败");
                    }
                });
            }
        }
    };
});

(function() {
    $.MsgBox = {
        Alert: function(title, msg) {
            GenerateHtml("alert", title, msg);
            btnOk(); //alert只是弹出消息，因此没必要用到回调函数callback
            btnNo();
        },
        Confirm: function(title, msg, callback) {
            GenerateHtml("confirm", title, msg);
            btnOk(callback);
            btnNo();
        }
    }
    //生成Html
    var GenerateHtml = function(type, title, msg) {
        var _html = "";
        _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';
        _html += '<a id="mb_ico">x</a><div id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
        if (type == "alert") {
            _html += '<input id="mb_btn_ok" type="button" value="确定" />';
        }
        if (type == "confirm") {
            _html += '<input id="mb_btn_ok" type="button" value="确定" />';
            _html += '<input id="mb_btn_no" type="button" value="取消" />';
        }
        _html += '</div></div>';
        //必须先将_html添加到body，再设置Css样式
        $("body").append(_html);
        //生成Css
        GenerateCss();
    }

    //生成Css
    var GenerateCss = function() {
        $("#mb_box").css({
            width: '100%',
            height: '100%',
            zIndex: '99999',
            position: 'fixed',
            filter: 'Alpha(opacity=60)',
            backgroundColor: 'black',
            top: '0',
            left: '0',
            opacity: '0.6'
        });
        $("#mb_con").css({
            zIndex: '999999',
            width: '400px',
            position: 'fixed',
            backgroundColor: 'White',
            borderRadius: '15px'
        });
        $("#mb_tit").css({
            display: 'block',
            fontSize: '14px',
            color: '#ffffff',
            padding: '10px 15px',
            backgroundColor: '#337ac7',
            borderRadius: '15px 15px 0 0',
            borderBottom: '3px solid #009BFE',
            fontWeight: 'bold'
        });
        $("#mb_msg").css({
            padding: '20px',
            lineHeight: '20px',
            borderBottom: '1px dashed #DDD',
            fontSize: '13px'
        });
        $("#mb_ico").css({
            display: 'block',
            position: 'absolute',
            right: '10px',
            top: '9px',
            border: '1px solid Gray',
            width: '18px',
            height: '18px',
            textAlign: 'center',
            lineHeight: '15px',
            cursor: 'pointer',
            borderRadius: '12px',
            fontFamily: '微软雅黑'
        });
        $("#mb_btnbox").css({
            margin: '15px 0 10px 0',
            textAlign: 'center'
        });
        $("#mb_btn_ok,#mb_btn_no").css({
            width: '85px',
            height: '30px',
            color: 'white',
            border: 'none'
        });
        $("#mb_btn_ok").css({
            backgroundColor: '#168bbb'
        });
        $("#mb_btn_no").css({
            backgroundColor: 'gray',
            marginLeft: '20px'
        });
        //右上角关闭按钮hover样式
        $("#mb_ico").hover(function() {
            $(this).css({
                backgroundColor: 'Red',
                color: 'White'
            });
        }, function() {
            $(this).css({
                backgroundColor: '#DDD',
                color: 'black'
            });
        });
        var _widht = document.documentElement.clientWidth; //屏幕宽
        var _height = document.documentElement.clientHeight; //屏幕高
        var boxWidth = $("#mb_con").width();
        var boxHeight = $("#mb_con").height();
        //让提示框居中
        $("#mb_con").css({
            top: (_height - boxHeight) / 2 + "px",
            left: (_widht - boxWidth) / 2 + "px"
        });
    }
    //确定按钮事件
    var btnOk = function(callback) {
        $("#mb_btn_ok").click(function() {
            $("#mb_box,#mb_con").remove();
            if (typeof(callback) == 'function') {
                callback();
            }
        });
    }
    //取消按钮事件
    var btnNo = function() {
        $("#mb_btn_no,#mb_ico").click(function() {
            $("#mb_box,#mb_con").remove();
        });
    }
})();