<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<%@ include file="../../include/taglib.jsp"%>
<script src="${ctx}/assets/js/feedBack/patient_feed_back_list.js"
	type="text/javascript"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeIn">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<ol class="breadcrumb">
					<li class="active">医生反馈信息列表</li>
				</ol>
			</div>
			<div class="ibox-content">
				<div class="row">
					<div class="col-md-12">
						<div class="btn-group hidden-xs" id="patientFbListTableToolbar"
							role="group">
							<div class="input-group">
								<div class="col-md-8">
									<div class="form-group">
										<label class="col-sm-5 control-label">状态：</label>
										<div class="col-sm-7">
										<select class="form-control" name="status">
												<option value="">全部</option>
												<option value="0">未处理</option>
												<option value="1">已处理</option>
									    </select>
									    </div>
									</div>
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-success"
										onclick="search()">
										<i class="fa fa-road"></i>&nbsp;搜索
									</button>
								</div>
							</div>
						</div>

						<table id="patientFbListTable" data-mobile-responsive="true">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 反馈处理 -->
	<div id="handleFb" style="display: none; overflow: hidden;">
		<form class="form-horizontal m-t" id="feedBackForm">
			<input type="hidden" name="id" id="handleId"/>
			<input type="hidden" name="status" value="1"/>
			<input type="hidden" name="replyUserId" value="${hxp_login_user.id}"/>
			<div class="form-group">
				<label class="col-sm-3 control-label">联系方式：</label>
				<div class="col-sm-8" id="handleMobile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">反馈内容：</label>
				<div class="col-sm-8" id="handleContent">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">回复内容：</label>
				<div class="col-sm-8" id="replyContentWrite">
					<textarea type="text" name="replyContent" rows="10" cols="50"></textarea>
				</div>
				<div class="col-sm-8" id="replyContentRead">
					
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

