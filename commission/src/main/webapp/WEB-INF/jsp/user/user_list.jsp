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
<title>取消发卡</title>
	<link href="../css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
	<link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	<link href="../css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="../css/font-awesome.min93e3.css" rel="stylesheet">
	<link href="../css/plugins/dataTables/DT_bootstrap.css" rel="stylesheet">
	<link href="../css/style.min862f.css?v=4.1.0" rel="stylesheet">
	<link href="../css/employeeManagement.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../js/My97DatePicker/skin/WdatePicker.css" >
	<!--jQuery包-->
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<!--bootstarp表格插件-->
	<script type="text/javascript" src="../js/bootstrap.min.js"></script>
	<!-- 时间控件js -->
	<script src="../js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="../js/plugins/dataTables/dataTables.bootstrap.js"></script>
	<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script src="../js/layer/layer.js"></script>
	<script type="text/javascript" src="../js/user/user_list.js"></script>
	<style type="text/css">
		table{
			text-align: center;
		}
		 .form-horizontal .control-label{width:auto}
	</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-xs-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row form-horizontal">
                            <div class="col-md-3 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-4">关键字</label>
                                    <div class="input-group col-xs-8">
                                        <input placeholder="请输入用户名、手机号" id="userNameKey" class="form-control" style="width: 100%">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 col-xs-12">
                                <div class="form-group">
                                    <label class="control-label col-xs-4">用户角色</label>
                                    <div class="input-group col-xs-8">
                                        <select name="type" class="form-control" id="roleName">
                                        	<option value="">请选择</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2 col-xs-2">
                                <div class="form-group"> 
                                        <button type="button" class="btn  btn-reset" onclick="resetInfo()">重置</button>
                                        <button type="button" class="btn  btn-info" id="search">查询</button>
                                   
                                </div>
                            </div>
                        </div>
                        <div class="row form-horizontal">
                            <div class="col-md-6 col-xs-6">
                                <div class="form-group">
                                    <div class="pull-left">
                                        <button type="button" class="btn btn-outline btn-default" id="newAsset" onclick="add()"><i class="fa fa-plus"></i>新增</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-bordered table-condensed table-hover dataTables-example" id="accountGrid">
                    <thead>
                        <tr>
                            <th style="text-align: center;">用户名</th>
                            <th style="text-align: center;">手机号码</th>
                            <th style="text-align: center;">创建时间</th>
                            <th style="text-align: center;">是否禁用</th>
                            <th style="text-align: center;">角色</th>
                            <th style="text-align: center;">操作</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    <!--新增模态弹窗-->
    <div class="modal inmodal" id="modalAccount" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" >
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="accountNewForm">
                    <div class="modal-header modal-header-new">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title" id="modal-title">新增用户</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="ibox">
                                <div class="ibox-content">
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3"> 用户角色<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <select title="请选择用户角色" id="roleId" name="roleId" class="form-control required">
                                                    	<option value="">请选择</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">请选择公司
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <select title="请选择公司" id="companyName" name="companyName" class="form-control required">
                                                    	<option value="">请选择</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-horizontal" id="plasmaId_div">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">请选择浆站
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <select title="请选择用户角色" id="plasmaId" name="plasmaId" class="form-control required">
                                                    	<option value="">请选择</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3"> 用户名<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <input title="请输入用户名" id="userName" name="userName" class="form-control required" placeholder="请输入用户名"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">密码<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <input title="请输入密码" id="passWord" name="passWord" class="form-control required" placeholder="请输入密码"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">
                                                    手机号<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <input title="请输入手机号" id="phone" name="phone" class="form-control required" placeholder="请输入手机号" maxlength="11"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer fixed">
                    	<input type="hidden" name="id" id="update_id">
                        <button type="button" class="btn btn-reset" data-dismiss="modal">取消</button>
                        <button type="button" id="sub" class="btn btn-info">确定</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>