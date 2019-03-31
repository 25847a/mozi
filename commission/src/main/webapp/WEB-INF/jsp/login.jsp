<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title>登录</title>
	<!-- CSS -->
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/supersized.css">
    <link rel="stylesheet" href="css/style.css">

    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script src="js/layer/layer.js"></script>
    <script type="text/javascript">  </script>

    
    <script type="text/javascript">
    	$(function(){
    		$("#btn").click(function(){
    			var userName = $("#username").val();
    			var password = $("#password").val();
    			$.ajax({
    				url:"userLogin",
    				type:"post",
    				dataType:"json",
    				data:{"userName":userName,"passWord":password},
    				success:function(result){
    					if(result.error == -1){
    						window.location.href="admin/left";
    					}else{
    						alert("账号或密码错误");
    					}
    				}
    			});
    		});
    	});
    </script>
</head>
    
      <body style="background:url('img/1.jpg');">

        <div class="page-container">
            <h1>登录</h1>
                <input type="text" id="username" name="username" class="username" value="admin" placeholder="请输入您的用户名！">
                <input type="password" id="password" name="password" class="password" value="123456" placeholder="请输入您的用户密码！">
                <button id="btn">登录</button>
                <div class="error"><span id="tip"></span></div>
        </div>	
    </body>


</html>

