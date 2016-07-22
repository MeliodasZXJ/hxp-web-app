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
<script src="${ctx}/assets/js/hospital/hospital_list.js"
	type="text/javascript"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li class="active">医院信息列表</li>
				</ol>
			</div>
			<div class="ibox-content">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group hidden-xs" id="hopitalListTableToolbar"
							role="group">
							<div class="input-group">
								<div class="col-md-4">
									<div class="form-group">
										<label class="col-sm-5 control-label">名称：</label>
										<div class="col-sm-7">
											<input name="name" class="form-control" id="searchName" type="text" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label class="col-sm-2 control-label">地域：</label>
										<div class="col-sm-10">
											<div class="col-sm-4">
												<select class="form-control" name="provinceId" id="searchProvinceId" onchange="selectProvince('search')">
													<option value="">全部</option>
													<c:forEach items="${provinceList}" var="province">
														<option value="${province.id}">${province.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-sm-4">
												<select class="col-sm-4 form-control" name="cityId" id="searchCityId" onchange="selectCity('search')">
													<option value="">请选择</option>
												</select>
											</div>
											<div class="col-sm-4">
												<select class="col-sm-4 form-control" name="regionId" id="searchRegionId">
													<option value="">请选择</option>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-2">
									<button type="button" class="btn btn-success"
										onclick="search()">
										<i class="fa fa-road"></i>&nbsp;搜索
									</button>
									<a type="button" class="btn btn-primary"
										href="javascript:void(0)" onclick="add()">&nbsp;新增</a>
								</div>
							</div>
						</div>

						<table id="hopitalListTable" data-mobile-responsive="true">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 医院信息 -->
	<div id="handleHospital" class="col-sm-10" style="display: none; overflow: hidden;">
		<form class="form-horizontal m-t" id="hospitalForm">
			<input type="hidden" name="id" id="handleId" />
			<div class="form-group">
				<label class="col-sm-3 control-label">名称：</label>
				<div class="col-sm-9">
					<input name="name" id="handleName" class="form-control" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">地域：</label>
				<div class="col-sm-9">
					<div class="col-sm-3">
						<select class="form-control" name="provinceId" id="handleProvinceId"  onchange="selectProvince('handle')">
							<option value="">选择省</option>
							<c:forEach items="${provinceList}" var="province">
								<option value="${province.id}">${province.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-3">
						<select class="col-sm-4 form-control" name="cityId" id="handleCityId" onchange="selectCity('handle')">
							<option value="">请选择</option>
						</select>
					</div>
					<div class="col-sm-3">
						<select class="col-sm-4 form-control" name="regionId" id="handleRegionId">
							<option value="">请选择</option>
						</select>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">详细地址：</label>
				<div class="col-sm-9">
					<input name="address" id="handleAddress" class="form-control" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-9">
					<input name="mark" id="handleMark" class="form-control" type="text" />
				</div>
			</div>
			<div class="form-group" id="btnStatus">
				<div class="col-sm-12 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

