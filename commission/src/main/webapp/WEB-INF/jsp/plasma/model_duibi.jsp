<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
				<link rel="stylesheet" href="../css/bootstrap.min.css" /> 
				<link rel="stylesheet" href="../css/mode.css" /> 
		<script type="text/javascript" src="../js/jquery.min.js" ></script>
		<script type="text/javascript" src="../js/bootstrap.min.js" ></script>
	</head>

	<body>
		<div id='backdropId' class='modal-backdrop fade in'></div>
		<div class="modal-dialog" role="document" style="z-index: 99999;height: 100%;margin-top:0;width:100%;">
			<div class="modal-content" style="height: 100%;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">详情</h4>
				</div>
				<div style="overflow-x:  scroll;height: 90%;">
				<div class="modal-body" style="float:left;width:100%;">
				<p><h3 style="    margin-top: -1px;    text-align: center;">${result.plasmaName}</h3></p>
				<div style="float:left;width:49%;">
				
				<div class="row">
					<div class="col-xs-12 goisr">
						<h4 style="    margin-top: -1px;    color: #e0abab;">现有详情</h4>
					</div> 
				</div>
				<div class="row" > 
					<div class="col-xs-10 loralbd">
				 <table class="table table-bordered" align="center"> 
  <tbody>
  	<tr>
      <td width="16%" align="center">献浆员编号</td>
      <td width="28%" align="center">${result.providerNo}</td>
      <td align="center">身份证号 </td>
      <td align="center">${result.idNo}</td>
    </tr>
    <tr>
      <td align="center">电话号码</td>
      <td align="center">${result.phone}</td>
      <td align="center">姓名</td>
      <td align="center">${result.name}</td>
    </tr> 	
    <tr>
      <td align="center">出生日期</td>
      <td align="center">${result.fbirthday}</td>
      <td align="center">婚否</td>
      <td align="center">${result.isMarry==0?"已婚":"未婚"} </td>
    </tr>
	<tr>
      <td align="center">性别</td>
      <td align="center">${result.sex==0?"男":"女"}</td>
      <td align="center">建档日期</td>
      <td align="center">${result.createDate}</td>
    </tr>
	<tr>
      <td align="center">民族</td>
      <td align="center">${result.fname}</td>
      <td align="center">身份证、照片</td>
      <td align="center">有</td>
    </tr>
	<tr>
      <td align="center">血型</td>
      <td align="center">
      	<c:if test="${result.bloodType==0}">
      		A
      	</c:if>
      	<c:if test="${result.bloodType==1}">
      		B
      	</c:if>
      	<c:if test="${result.bloodType==2}">
      		O
      	</c:if>
      	<c:if test="${result.bloodType==3}">
      		AB
      	</c:if>
      </td>
      <td align="center">身份证全省比对</td>
      <td align="center">通过</td>
    </tr>
	<tr>
      <td align="center">籍贯</td>
      <td align="center">${result.place }</td>
      <td align="center">人脸识别比对</td>
      <td align="center">通过</td>
    </tr>
	<tr>
      <td align="center">家庭住址</td>
      <td colspan="3" align="center">${result.addressx}</td>
      </tr>
	 
  </tbody>
</table>
					</div>
					<div class="col-xs-2 actinle">
						<div class="row ioliad">
						 <img src="${result.fimagespot}" >
						</div>
						 
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6 morlad">
						<p>身份证正面</p>
						<img src="${result.imagez==null ? data.imagez : result.imagez}"/>
					</div>
					
					<div class="col-xs-6 morlad">
						<p>身份证反面</p> 
						<img src="${result.imagef==null ? data.imagef : result.imagef}"/>
					</div>
				</div> 
				</div>
				<div style="border-left: 3px solid #717171; float: left; height: 44rem; margin-right: 6px;margin-top: 2.4rem;"></div>
				<div style="float:left;width:49%;">
			<div class="row">
					<div class="col-xs-12 goisr">
						<h4 style="    margin-top: -1px;    color: #e0abab;">此前详情</h4>
					</div> 
				</div>
				<div class="row" > 
					<div class="col-xs-10 loralbd">
				 <table class="table table-bordered" align="center"> 
  <tbody>
  	<tr>
      <td width="16%" align="center">献浆员编号</td>
      <td width="28%" align="center">${data.providerNo}</td>
      <td width="19%" align="center">身份证号</td>
      <td width="37%" align="center">${data.idNo}</td>
    </tr>
    <tr>
      <td align="center">电话号码</td>
      <td align="center">${data.phone }</td>
      <td align="center">姓名</td>
      <td align="center">${data.name }</td>
    </tr>
    <tr>
      <td align="center">出生日期</td>
      <td align="center">${data.birthday}</td>
      <td align="center">婚否</td>
      <td align="center">${data.isMarry==0?"已婚":"未婚"}</td>
    </tr>
	<tr>
      <td align="center">性别</td>
      <td align="center">${data.sex==0?"男":"女"}</td>
      <td align="center">建档日期</td>
      <td align="center">${data.createDate}</td>
    </tr>
	<tr>
      <td align="center">民族</td>
      <td align="center">${data.fname}</td>
      <td align="center">身份证、照片</td>
      <td align="center">有</td>
    </tr>
	<tr>
      <td align="center">血型</td>
      <td align="center">
      	<c:if test="${data.bloodType==0}">
      		A
      	</c:if>
      	<c:if test="${data.bloodType==1}">
      		B
      	</c:if>
      	<c:if test="${data.bloodType==2}">
      		O
      	</c:if>
      	<c:if test="${data.bloodType==3}">
      		AB
      	</c:if>
      </td>
      <td align="center">身份证全省比对</td>
      <td align="center">通过</td>
    </tr>
	<tr>
      <td align="center">籍贯</td>
      <td align="center">${data.place}</td>
      <td align="center">人脸识别比对</td>
      <td align="center">通过</td>
    </tr>
	<tr>
      <td align="center">家庭住址</td>
      <td colspan="3" align="center">${data.addressx}</td>
      </tr>
	 
  </tbody>
</table>
					</div>
					<div class="col-xs-2 actinle">
						<div class="row ioliad">
						 <img src="${data.imagespot==null ? result.imagespot :  data.imagespot}"/>
						</div>
						<!-- <div class="row" id="loagkrd">
						 <div class="col-xs-12">
						 	<div class="col-xs-5">发证状态：</div>
							<div class="col-xs-7"></div>
						 </div>
						 
						 
						  <div class="col-xs-12">
						 	<div class="col-xs-5">未发证原因：</div>
							<div class="col-xs-7"></div>
						 </div>
						 
						  <div class="col-xs-12">
						 	<div class="col-xs-5">发证日期：</div>
							<div class="col-xs-7"></div>
						 </div>
						 
						  <div class="col-xs-12">
						 	<div class="col-xs-5">发证单位：</div>
							<div class="col-xs-7"></div>
						 </div>
						  <div class="col-xs-12">
						 	<div class="col-xs-5">发证人：</div>
							<div class="col-xs-7"></div>
						 </div>
						 
						</div> -->
					</div>
				</div>
				<div class="row">
					<div class="col-xs-6 morlad">
						<p>身份证正面</p>
						<img src="${data.imagez}"/>
					</div>
					
					<div class="col-xs-6 morlad">
						<p>身份证反面</p> 
						<img src="${data.imagef}"/>
					</div>
				</div> 
					</div>
				</div>
				<div class="modal-footer" style="text-align: center;">
					<!-- <button type="button" class="btn btn-primary" style="">保存</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
				</div>
				
			</div>
		</div>
	</body>

</html>