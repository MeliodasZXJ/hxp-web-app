var isSendMail = false;
	var countdown;
	function settime(obj) {

		if (countdown == 0) {
			$(obj).attr("disabled", false);
			$(obj).attr("class", "btn btn-primary btn-sm");
			$(obj).html("重新发送");
			return;
		} else {
			$(obj).attr("disabled", true);
			$(obj).attr("class", "btn btn-default btn-sm");
			$(obj).html("重新发送(" + countdown + ")");
			countdown--;
		}
		setTimeout(function() {
			settime(obj)
		}, 1000)
	}
	
	function sendMail(){
		var loginName = $("#loginName").val();
		$.post("sentVerify.do", {
			loginName : loginName
		}, function(data) {
			if (data.success) {
				isSendMail = true;
				var mailUrl = "http://mail."+loginName.substring(loginName.indexOf("@")+1,loginName.length);
				$("#sendEmailAddress").html(loginName);
				$("#sendEmailUrl").attr("href",mailUrl);
			}
		}, "json");
		countdown = 60;
		settime($("#reSendMail"));
	}
		$(function() {
			$("#form").steps({
				bodyTag : "fieldset",
				onStepChanging : function(event, currentIndex, newIndex) {
					
					var form = $(this);
					form.validate().settings.ignore = ":disabled,:hidden";
					return form.valid()
				},
				onStepChanged : function(event, currentIndex, priorIndex) {
					if(currentIndex==1 && !isSendMail){
						sendMail();
					}
				},
				onFinishing : function(event, currentIndex) {
					var form = $(this);
					form.validate().settings.ignore = ":disabled";
					return form.valid()
				},
				onFinished : function(event, currentIndex) {
					var form = $(this);
					var options = {
							url : "register.do",
							type : "post",
							dataType : "json",
							success : function(data) {
								if (data.success) {
									$.success("操作成功",function(){
										window.location.href = 'login.htm';
									});
								} else {
									$.error(data.msg);
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.warn('服务忙,请稍后再试.');
							}
						};
						$(form).ajaxSubmit(options);
					
					
				}
			}).validate({
				errorPlacement : function(error, element) {
					element.before(error)
				},
				rules : {
					'userInfo.loginName':{
						required:true,
						email:true,
						remote:{
						    url: 'checkLoginName.do',     //后台处理程序
						    type: 'POST',               //数据发送方式
						    data: {                     //要传递的数据
						        loginName: function() {
						            return $("#loginName").val();
						        }
						    }
						}	
					},
					'userInfo.loginPwd':{
						rangelength : [ 6, 10 ]
					},
					'confirm' : {
						equalTo : "#loginPwd",
						rangelength : [ 6, 10 ]
					},
					'userInfo.recommendCode':{
						email:true,
						remote:{
						    url: 'checkLoginNameForPwd.do',     //后台处理程序
						    type: 'POST',               //数据发送方式
						    data: {                     //要传递的数据
						        loginName: function() {
						            return $("#recommendCode").val();
						        }
						    }
						}	
					},
					'verifyCode' : {
						required : true,
						remote:{
						    url: 'checkVerifyCode.do',     //后台处理程序
						    type: 'POST',               //数据发送方式
						    data: {                     //要传递的数据
						    	verifyCode: function() {
						            return $("#verifyCode").val();
						        }
						    }
						}
					},
					'shopInfo.provinceId':{
						required : true
					},
					'shopInfo.cityId':{
						required : true
					},
					'shopInfo.districtId':{
						required : true
					}
				},
				messages:{
					'userInfo.loginName':{
						remote:"用户名已被注册"
					},
					'verifyCode':{
						remote:"验证码错误"
					},
					'userInfo.recommendCode':{
						email:"格式不正确",
						remote:"请输入正确的推荐码"
					}
				}
			})
			$("select[name='shopInfo.provinceId']").change(
					function() {
						if ($("select[name='shopInfo.provinceId']").val() != '') {
							$.getJSON("region/getRegion.do?pid="
									+ $("select[name='shopInfo.provinceId']").val(), function(
									data) {
								var s = "<option value=''>选择市</option>";
								$.each(data, function(i, item) {
									s += "<option value='" + item.id + "'>" + item.name
											+ "</option>";
								});
								$("select[name='shopInfo.cityId']").html(s);
								$("input[name='shopInfo.provinceName']").val(
										$("select[name='shopInfo.provinceId'] option:selected")
												.text());
								$("input[name='shopInfo.cityName']").val('');

								$("select[name='shopInfo.districtId']").html(
										"<option value=''>选择区</option>");
								$("input[name='shopInfo.districtName']").val('');

							});
						} else {
							$("input[name='shopInfo.provinceName']").val('');

							$("select[name='shopInfo.cityId']").html(
									"<option value=''>选择市</option>");
							$("input[name='shopInfo.cityName']").val('');

							$("select[name='shopInfo.districtId']").html(
									"<option value=''>选择区</option>");
							$("input[name='shopInfo.districtName']").val('');

						}

					});

			$("select[name='shopInfo.cityId']").change(
					function() {
						if ($("select[name='shopInfo.cityId']").val() != '') {
							$.getJSON("region/getRegion.do?pid="
									+ $("select[name='shopInfo.cityId']").val(), function(data) {
								var s = "<option value=''>选择区</option>";
								$.each(data, function(i, item) {
									s += "<option value='" + item.id + "'>" + item.name
											+ "</option>";
								});
								$("select[name='shopInfo.districtId']").html(s);
								$("input[name='shopInfo.cityName']").val(
										$("select[name='shopInfo.cityId'] option:selected")
												.text());
								$("input[name='shopInfo.districtName']").val('');
							});
						} else {
							$("input[name='shopInfo.cityName']").val('');
							$("select[name='shopInfo.districtId']").html(
									"<option value=''>选择区</option>");
							$("input[name='shopInfo.districtName']").val('');

						}
					});

			$("select[name='shopInfo.districtId']").change(
					function() {
						if ($("select[name='shopInfo.districtId']").val() != '') {
							$("input[name='shopInfo.districtName']").val(
									$("select[name='shopInfo.districtId'] option:selected")
											.text());
						} else {
							$("input[name='shopInfo.districtName']").val('');
						}
			});
		});