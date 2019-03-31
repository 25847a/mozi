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
<title>浆员信息审核</title>
	<link href="../static/css/bootstrap.min.css" rel="stylesheet"/>
	 <link rel="stylesheet" type="text/css" href="../static/js/My97DatePicker/skin/WdatePicker.css" >
	<!--jQuery包-->
	<script type="text/javascript" src="../static/js/jquery.min.js"></script>
	<!--bootstarp表格插件-->
	<script type="text/javascript" src="../static/js/bootstrap.min.js"></script>
	<!-- 时间控件js -->
	<script type="text/javascript" src="../static/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../static/js/utils.js"></script>
	<script src="../js/layer/layer.js"></script>
	 <script type="text/javascript">
		
		
		function method(result){
			console.log($(this).parent("td").parent("tr").find("td:eq(0)").val());
			openModal(result);
			closeModal();
		};
		//打开模态框
		function openModal(param){
			var fatherBody = $(window.top.document.body);
			var id = 'pages';
			var dialog = $('#' + id);
			if (dialog.length == 0) {
				dialog = $('<div class="modal fade" role="dialog" id="' + id + '"/>');
				dialog.appendTo(fatherBody);
			}
			dialog.load("../baseinfo/model?id="+param, function() {
				dialog.modal({
				  backdrop: false
				});
			});
		}
		
		//关闭模态框
		function closeModal(){
			var fatherBody = $(window.top.document.body);
			fatherBody.find("#pages").on('hidden.bs.modal', function (e) {
				fatherBody.find("#backdropId").remove();
			});
		}
	</script>
	 
</head>
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
						<div class="pull-left" style="margin-left: 1em;">
							<button id="query" type="button" class="btn  btn-info" style="margin-right:0.5em">查询</button>
							<button id="status" type="button" class="btn  btn-info">审核</button>
						</div>
					</div>
				</div>
			</div>
			
			
			
			
			
			
			
			

	<!-- <p style="text-indent:2em;"></p>
	<p style="text-indent:2em;">
		<label class="control-label">浆员编号：</label><input type="text" id="providerNo" style="margin-right: 2em"/>
		<label class="control-label">浆员姓名：</label><input type="text" id="name" style="margin-right: 2em"/>
		<label class="control-label">发卡时间从：</label><input type="text" id="startDate" class="Wdate" onfocus="WdatePicker()" />
		<label class="control-label">到：</label><input type="text" id="endDate" class="Wdate" onfocus="WdatePicker()" style="margin-right: 4em"/>
		<button id="query" class="btn btn-info"  style="margin-right: 1em">查询</button>
    	<button id="status" class="btn btn-primary">审核</button>
    </p> -->
</div>
<div>
	<table id="table" class="table table-bordered table-hover">
		<thead>
			<tr>
				<th style='text-align: center;'><input type="checkbox" name="rTitle" id="rTitle" /></th>
				<th style='text-align: center;'>浆员卡号</th>
				<th style='text-align: center;'>姓名</th>
				<th style='text-align: center;'>性别</th>
				<th style='text-align: center;'>身份证号码</th>
				<th style='text-align: center;'>血型</th>
				<th style='text-align: center;'>生日</th>
				<th style='text-align: center;'>是否审核</th>
				<th style='text-align: center;'>操作</th>
			</tr>
		</thead>
	</table>
</div>
</body>
</html>