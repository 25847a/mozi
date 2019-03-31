<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
	<link href="../css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
	<link href="../css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	<link href="../css/plugins/datapicker/datepicker3.css" rel="stylesheet">
	<link href="../css/font-awesome.min93e3.css" rel="stylesheet">
	<link href="../css/plugins/dataTables/DT_bootstrap.css" rel="stylesheet">
	<link href="../css/style.min862f.css?v=4.1.0" rel="stylesheet">
	<link href="../css/employeeManagement.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../js/My97DatePicker/skin/WdatePicker.css" >
	<link href="../css/zTreeStyle.css" rel="stylesheet">
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
	<script src="../js/plugins/validate/jquery.validate.min.js"></script>
	<script src="../js/zTree/jquery.ztree.core.min.js"></script>
    <script src="../js/zTree/jquery.ztree.excheck.min.js"></script>
	<script type="text/javascript" src="../js/config/role_list.js"></script>
	<style type="text/css">
		table{
			text-align: center;
		} .form-horizontal .control-label{width:auto}
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
                                    <label class="control-label col-xs-4">关键字</label>
                                    <div class="input-group col-xs-8">
                                        <input placeholder="请输入角色名称" class="form-control" id="roleNameKey" style="width: 100%">
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
                            <div class="col-md-4 col-xs-12">
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
                <table class="table table-bordered table-condensed table-hover dataTables-example" id="roleGrid">
                    <thead>
                        <tr>
                            <th>角色名称</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    
                </table>
            </div>
        </div>
    </div>
    <!--新增模态弹窗-->
    <div class="modal inmodal" id="modalRole" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" >
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="roleForm">
                    <div class="modal-header modal-header-new">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title" id="roleTitle">新增角色</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="ibox">
                                <div class="ibox-content">
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">
                                                    角色名称<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <input title="请输入角色名称" type="text" id="roleName" name="roleName" class="required form-control" placeholder="请输入角色名称"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer fixed">
                    	<input type="hidden" name="id" value="" id="id"/>
                        <button type="button" class="btn btn-reset" data-dismiss="modal">取消</button>
                        <button type="submit" class="btn btn-info">确定</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- 权限分配树形菜单 -->
    <!--新增模态弹窗-->
    <div class="modal inmodal" id="modalSet" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" >
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="SetForm">
                    <div class="modal-header modal-header-new">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title">新增角色</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="ibox">
                                <div class="ibox-content">
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3"> 角色名称<font color="red">*</font>
                                                </label>
                                                <div class="col-xs-8 input-group">
                                                    <input type="text" id="roleSetName" name="roleSetName" class="required form-control" readonly value="角色1"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-horizontal">
                                        <div class="col-sm-12 col-xs-12">
                                            <div class="form-group">
                                                <label class="control-label col-xs-3">   权限分配 </label>
                                                <div class="zTreeDemoBackground col-xs-8">
                                                    <ul id="treeDemo" class="ztree"></ul>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer fixed">
                    	<input type="hidden" id="roleId" name="roleId">
                        <button type="button" class="btn btn-reset" data-dismiss="modal">取消</button>
                        <button type="submit" class="btn btn-info">确定</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>