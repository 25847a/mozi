<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>加密锁管理</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="../css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="../css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="../css/font-awesome.min93e3.css" rel="stylesheet">
    <!-- Data Tables -->
    <link href="../css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet">
    <link href="../css/animate.min.css" rel="stylesheet">
    <link href="../css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="../css/assetManagement.css" rel="stylesheet">
    <style>
        th,td{
            text-align: center;
        }
    </style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-xs-12">
                <div class="ibox">
                    <div class="ibox-content">
                        <div class="row form-horizontal">
                            <div class="col-md-3 col-xs-3">
                                <div class="form-group">
                                    <label class="control-label col-xs-4" style="width:auto">浆站名称</label>
                                    <div class="input-group col-xs-8">
                                        <select placeholder="浆站名称" class="form-control" id="plasmaId1" name="plasmaId1" style="width: 100%">
                                        	<option value="">请选择</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group"> 
                                        <button type="button" id="query" class="btn  btn-info">查询</button> 
                                </div>
                            </div>
                        </div>
                        <div class="row form-horizontal">
                            <div class="col-md-4 col-xs-12">
                                <div class="form-group">
                                    <div class="pull-left">
                                        <button type="button" class="btn btn-outline btn-default" id="newAsset" onclick="add()"><i class="fa fa-plus"></i>新增绑定加密锁</button>
                                        <button type="button" class="btn btn-outline btn-default" onclick="update()"><i class="fa fa-plus"></i>重置加密锁</button>
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
                <table class="table table-bordered table-condensed table-hover dataTables-example" id="companyGrid">
                    <thead>
                        <tr>
                            <th>锁标识</th>
                            <th>使用浆站</th>
                            <th>创建时间</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
    <!--新增模态弹窗-->
    <div class="modal inmodal" id="modalCompany" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" >
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="companyForm">
                    <div class="modal-header modal-header-new">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title" id="add_cp_title">登记加密锁信息</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="ibox">
                                <div class="ibox-content">
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">
                                                    登记浆站<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <select id="plasmaId" name="plasmaId"></select>
                                                    <input id="type" type="hidden" name="type">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer fixed">
                    	<input type="hidden" id="update_id" name="id">
                        <button type="button" class="btn btn-reset" data-dismiss="modal">取消</button>
                        <button type="submit" class="btn btn-info">确定</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    	<!--jQuery包-->
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<!--bootstarp表格插件-->
	<script type="text/javascript" src="../js/bootstrap.min.js"></script>
	<!-- 时间控件js -->
	<script src="../js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="../js/plugins/dataTables/dataTables.bootstrap.js"></script>
     <script src="../js/plugins/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="../js/utils.js"></script>
	<script src="../js/layer/layer.js"></script>
    <script type="text/javascript" src="../js/lock/lock_list.js"></script>
</body>
</html>