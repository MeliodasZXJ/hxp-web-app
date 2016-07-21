<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<%@ include file="../../include/taglib.jsp"%>
<link href="${ctx}/assets/css/plugins/treeview/bootstrap-treeview.css"
	rel="stylesheet" />
<script src="${ctx}/assets/js/plugins/treeview/bootstrap-treeview.js"></script>
<script src="${ctx}/assets/js/dicInfo/dic_info.js"
	type="text/javascript"></script>


</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li>系统管理</li>
					<li class="active">字典管理</li>
				</ol>
			</div>
			<div class="ibox-content">
				<div class="row">
					<div class="col-sm-4">
						<div class="panel panel-primary tree-panel">
							<div class="panel-heading">字典类型</div>
							<div class="panel-body">
								<div id="tree_dicType" class="test"></div>
							</div>

						</div>
					</div>
					<div class="col-sm-8">
						<div class="panel panel-primary tree-panel">
							<div class="panel-heading">字典值栈</div>
							<div class="panel-body">

								<div class="col-sm-12">
									<div class="btn-group hidden-xs" id="treeInfoTableToolbar"
										role="group">
										<button type="button" class="btn btn-outline btn-default"
											onclick="addDicInfo()" id="addDicInfoBtn">
											<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
										</button>
									</div>

									<table id="treeInfoTable" data-mobile-responsive="true">
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	




	<div id="addDicInfo" style="display: none; overflow: hidden;">
		<form class="form-horizontal m-t" id="addDicInfoForm">
			<div class="form-group">
				<label class="col-sm-3 control-label">名称：</label>
				<div class="col-sm-8">
					<input type="hidden" name="typeCode"> <input type="text"
						name="name" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">排序：</label>
				<div class="col-sm-8">
					<input type="text" name="sort" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>

