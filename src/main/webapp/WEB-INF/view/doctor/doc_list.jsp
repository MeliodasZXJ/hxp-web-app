<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<%@ include file="../../include/taglib.jsp"%>
<script src="${ctx}/assets/js/doctor/doctor_list.js"
	type="text/javascript"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li class="active">医生认证信息列表</li>
				</ol>
			</div>
			<div class="ibox-content">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group hidden-xs" id="doctorInfoTableToolbar"
							role="group">
							<div class="input-group">
								<div class="col-md-3">
									<div class="form-group">
										<label class="col-sm-4 control-label">姓名：</label>
										<div class="col-sm-8">
											<input name="name" class="form-control" type="text" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="col-sm-4 control-label">医院：</label>
										<div class="col-sm-7">
											<input name="hospitalName" class="form-control" type="text" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<label class="col-sm-4 control-label">科室：</label>
										<div class="col-sm-7">
											<input name="deptName" class="form-control" type="text" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<button type="button" class="btn btn-success"
										onclick="search()">
										<i class="fa fa-road"></i>&nbsp;搜索
									</button>
								</div>
							</div>
						</div>

						<table id="doctorListTable" data-mobile-responsive="true">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>




	<div id="handleImg" style="display: none; overflow: hidden;">
		<div class="carousel slide" id="carousel2">
			<ol class="carousel-indicators">
				<li data-slide-to="0" data-target="#carousel2" class="active"></li>
				<li data-slide-to="1" data-target="#carousel2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="item active">
					<img alt="image" id="headPath" class="img-responsive" src="${ctx}/assets/img/a1.jpg">
					<div class="carousel-caption">
						<p>头像</p>
					</div>
				</div>
				<div class="item ">
					<img alt="image"  id="pidPath" class="img-responsive" src="${ctx}/assets/img/a2.jpg">
					<div class="carousel-caption">
						<p>胸牌</p>
					</div>
				</div>
			</div>
			<a data-slide="prev" href="#carousel2"
				class="left carousel-control"> <span class="icon-prev"></span>
			</a> 
			<a data-slide="next" href="#carousel2"
				class="right carousel-control"> <span class="icon-next"></span>
			</a>
		</div>
	</div>
</body>
</html>

