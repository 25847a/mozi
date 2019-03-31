// user
$('#useID').blur(function() {
	if((/^[a-z0-9_-]{4,15}$/).test($(".reg_user").val())) {
		$('.user_hint').html("✔").css("color", "green");
	} else {
		$('.user_hint').html("×").css("color", "red");
		$('#myModal').show()
	}
});
// password
$('#pwdID').blur(function() {
	if((/^[a-z0-9_-]{6,16}$/).test($(".reg_password").val())) {
		$('.password_hint').html("✔").css("color", "green");
	} else {
		$('.password_hint').html("×").css("color", "red");
	}
});
// 登录请求判断 
$(document).ready(function(){
    document.onkeydown = function (event){
        if (event.keyCode==13) //回车键的键值为13
            submitLogin();
    };
    var userName = localStorage.getItem('keyName');  
    if(userName){ 
        $('#useID').val(userName); 
    } 
});
function submitLogin(){
    var userName = $("#useID").val();
    var passWord = $("#pwdID").val();
    localStorage.setItem('keyName',userName);
    if(userName==''){
        $("#p1").html("请输入用户名").fadeIn(100).delay(600).fadeOut(200);
    }else if(passWord==''){
        $("#p1").html("请输入密码").fadeIn(100).delay(600).fadeOut(600);
    } else{
        $.ajax({
            type: "post",
            url: "/log/queryAdminLogin",
            data: {
                "mobile": userName,
                "passWord": passWord
            },
            dataType: "json",
            success: function(data) { //返回值
                //登录成功，存入 cookie
                localStorage.setItem('keyName',userName);
                $("#p1").html(data.message).fadeIn(100).delay(600).fadeOut(200);
                if(data.code==-1) { //url跳转网页中传回的值。
                    window.location.href = "/admin/index";
                }
            },
            error:function(){
                $("#p1").html("请稍后重试").fadeIn(1000).delay(1000).fadeOut(500);

            }
        });
    }
}
$('.login_btn').click(function() {
    submitLogin();
	});
 
$(function() {
	$('body').particleground({
		dotColor: '#E8DFE8',
		lineColor: '#cae3ce'
	});
	
	var userName = localStorage.getItem('keyName');
	var passWord = $("#pwdID").val();
	if(null!=userName && userName.length>0){
		$("#pwdID").focus();
	}else{
		$("#useID").focus();
	}
	
	//监听键盘回车事件
	/*$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$('.login_btn').click();
		}
	});*/
})