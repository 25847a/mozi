<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/ by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:16:41 GMT -->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>卫计委审核系统</title>
     <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="../css/animate.min.css" rel="stylesheet">
    <link href="../css/style.min.css" rel="stylesheet">
    <link href="../css/login.min.css" rel="stylesheet">
</head>
<style>
.sidebar-collapse{    background: #2f4050;}
</style>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden; ">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" src="../img/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">123</strong></span>
                                <span class="text-muted text-xs block">超级管理员<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a class="J_tabExit">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">H+
                        </div>
                    </li>
                    <!-- <li>
                        <a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">账户管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/userList">用户管理</a>
                            </li>
                            <li>
                            	<a class="J_menuItem" href="../admin/menu">菜单管理</a>
                            </li>
                            <li>
                            	<a class="J_menuItem" href="../admin/role">角色管理</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">浆员管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/providerBaseinfoList">浆员管理</a>
                            </li>
                            <li>
                            	<a class="J_menuItem" href="../admin/cancelCardList">信息变更</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                    	<a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">配置管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/company">公司管理</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="../admin/plasmaSiteList">浆站管理</a>
                            </li>
                            <li>
                                <a class="J_menuItem" href="../admin/lockList">写入加密狗</a>
                            </li>
                        </ul>
                    </li>
               		<li>
               			<a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">体检管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/check">体检管理</a>
                            </li>
                        </ul>
               		</li>
               		<li>
               			<a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">化验管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/newCard">化验管理</a>
                            </li>
                        </ul>
               		</li>
               		<li>
               			<a href="#">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">采浆管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="../admin/collection">采浆管理</a>
                            </li>
                        </ul>
               		</li> -->
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                        <form role="search" class="navbar-form-custom" method="post" action="http://www.zi-han.net/theme/hplus/search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form>
                    </div> 
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="../admin/providerBaseinfoList">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
                <a href="javascript:void(0)" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="../admin/providerBaseinfoList" frameborder="0" data-id="../admin/providerBaseinfoList" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">&copy; 2014-2018 <a href="#" target="_blank">shux</a>
                </div>
            </div>
        </div>
        </div> 
    <script src="../js/jquery.min.js?v=2.1.4"></script>
    <script src="../js/bootstrap.min.js?v=3.3.6"></script>
    <script src="../js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="../js/plugins/layer/layer.min.js"></script>
    <script src="../js/hplus.min.js?v=4.1.0"></script>
    <script type="text/javascript" src="../js/contabs.min.js"></script>
    <script src="../js/plugins/pace/pace.min.js"></script>
    <script src="../js/left.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$(".J_tabExit").click(function(){
    			$.post('../exit',function(res){
    				if(res.error==-1){
    					window.location.href="../login";
    				}
    			});
    		});
    	})
    </script>
</body>
</html>
