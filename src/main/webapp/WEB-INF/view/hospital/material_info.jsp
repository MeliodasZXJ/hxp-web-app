<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<%@ include file="../../include/taglib.jsp"%>
<script src="${ctx}/assets/js/plugins/suggest/bootstrap-suggest.min.js"
	type="text/javascript"></script>
<script src="${ctx}/assets/js/material/material_info.js"
	type="text/javascript"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li><a href="getMaterialList.htm">材料信息列表</a></li>
					<li class="active"><c:if test="${ empty materialInfo.id}">新增</c:if> <c:if
							test="${!empty materialInfo.id}">编辑</c:if></li>
				</ol>
			</div>

			<div class="ibox-content">
				<div class="row">
					<div class="alert alert-info">请仔细填写材料信息，这将对报表分析结果产生直接影响！</div>
					<form class="form-horizontal m-t" id="materialInfoForm">
						<input type="hidden" name="id" value="${materialInfo.id }" />
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label"><i class="red">*
								</i>材料名称：</label>
								<div class="col-sm-9">
									<input type="text" name="name" class="form-control"
										value="${materialInfo.name}" placeholder="请输入名称">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"><i class="red">*
								</i>进价：</label>
								<div class="col-sm-5">
									<input type="text" name="unitPrice" class="form-control"
										value="<fmt:formatNumber value='${materialInfo.unitPrice}' type='currency' pattern='#.##' />"
										placeholder="元" />
								</div>
								<label class="col-sm-1 control-label">/</label>
								<div class="col-sm-3">
									<select class="form-control" name="unitId">
										<c:forEach items="${unitList }" var="unit">
											<option value="${unit.id }"
												<c:if test="${unit.id==materialInfo.unitId }"> selected="selected" </c:if>>${unit.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">规格：</label>
								<div class="col-sm-9">
									<input name="specifications" class="form-control" type="text" value="${materialInfo.specifications}" /> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">备注：</label>
								<div class="col-sm-9">
									<input name="mark" class="form-control" placeholder="请输入备注"
										type="text" value="${materialInfo.mark }">
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-sm-3 control-label">材料类别：</label>
								<div class="col-sm-9">
									<select class="form-control" name="typeId">
										<c:forEach items="${materialTypeList }" var="materialType">
											<option value="${materialType.id }"
												<c:if test="${materialType.id==materialInfo.typeId }"> selected="selected" </c:if>>${materialType.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"><i class="red">*
								</i>售价：</label>
								<div class="col-sm-9">
									<input type="text" name="salePrice" class="form-control"
										value="<fmt:formatNumber value='${materialInfo.salePrice}' type='currency' pattern='#.##' />"
										placeholder="元" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">品牌：</label>
								<div class="col-sm-9">
									<input name="brand" class="form-control" type="text" value="${materialInfo.brand}" /> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label"><i class="red">*
								</i>供应商：</label>
								<div class="col-sm-9">
									<div class="input-group">
										<input name="supplierId" class="form-control" type="hidden" value="${materialInfo.supplierId}" /> 
										<input class="form-control" type="text" id="supplierName"
											placeholder="点击选择供应商" value="${materialInfo.supplier.name }" />
										<div class="input-group-btn">
											<button type="button" class="btn btn-default dropdown-toggle"
												data-toggle="dropdown">
												<span class="caret"></span>
											</button>
											<ul class="dropdown-menu dropdown-menu-right" role="menu">
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-12">
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-12 col-sm-offset-5">
									<button class="btn btn-primary" type="submit">保存</button>
									<button class="btn btn-white" type="button"
										onclick="history.back(-1);">返回</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>