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
	<script type="text/javascript" src="../js/plasma/plasma_list.js"></script>
</head>

<style type="text/css">
 
		 .form-horizontal .control-label{width:auto}
	</style>
<body style="width: 97% !important;  margin: 0 auto !important;">
<div>
<div class="row form-horizontal" style="    padding-top: 1em;">
				<div class="col-md-3  ">
					<div class="form-group">
						<label class="control-label col-xs-5">发卡时间:</label>
							<div class="input-group col-xs-7">
								<input id="startDate" placeholder="" class="form-control" onfocus="WdatePicker()" style="width: 48%; float: inherit;">
							<label style="width: 4%;text-align: center;">-</label>
							<input id="endDate" placeholder="" class="form-control" onfocus="WdatePicker()" style="width: 48%; float: inherit;">
						</div>
					</div>
				</div>
				<div class="col-md-3  ">
					<div class="form-group">
						<label class="control-label col-xs-5">浆员姓名:</label>
						<div class="input-group col-xs-7">
					<input id="name" placeholder="" class="form-control" style="width: 100%">
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="form-group">
						<label class="control-label col-xs-5">浆员编号:</label>
						<div class="input-group col-xs-7">
				<input id="providerNo" placeholder="" class="form-control" style="width: 100%">
						</div>
					</div>
				</div>
				<div class="col-md-3">
                    <div class="form-group">
                        <label class="control-label col-xs-3">状态</label>
                        <div class="input-group col-xs-8">
                            <select name="type" class="form-control" id="plasma_status">
                                <option value="">请选择</option>
                                <option value="0" selected="selected">未审核</option>
                                <option value="1">已审核</option>
                            </select>
                        </div>
                    </div>
                </div>
				<div class="col-md-3" style="margin-left: 45%;">
					<div class="form-group">
						<div class="pull-left" style="margin-left: 1em;">
							<button type="button" class="btn  btn-reset" onclick="resetInfo()">重置</button>	
							<button id="query" type="button" class="btn  btn-info" style="margin-right:0.5em">查询</button>
							<button id="status" type="button" class="btn  btn-info">审核</button>
						</div>
					</div>
				</div>
			</div>

</div>
<div>
	<table id="table" class="table table-bordered table-hover" style="text-align: center;">
		<thead>
			<tr>
				<th style='text-align: center;'><input type="checkbox" name="rTitle" id="all" /></th>
				<th style='text-align: center;'>浆员卡号</th>
				<th style='text-align: center;'>姓名</th>
				<th style='text-align: center;'>性别</th>
				<th style='text-align: center;'>身份证号码</th>
				<th style='text-align: center;'>血型</th>
				<th style='text-align: center;'>生日</th>
				<th style='text-align: center;'>地址</th>
				<th style='text-align: center;'>类型</th>
				<th style='text-align: center;'>是否审核</th>
				<th style='text-align: center;'>提交时间</th>
				<th style='text-align: center;'>审核时间</th>
				<th style='text-align: center;'>操作</th>
			</tr>
		</thead>
	</table>
</div>
</body>
</html>