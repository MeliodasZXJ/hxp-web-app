/**
 * 找回密码
 */
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
function loadCode() {
	$("#verifycode").attr("src", "verify.htm?common=back&_=" + Math.random());
}
function sendMail() {
	$.post("sendMail.do", {
		mail : $("input[name='loginName']").val()
	}, function(data) {
		if (data.success) {
			isSendMail = true;
		}
	}, "json");
	countdown = 60;
	settime($("#reSendMail"));

}

$(function() {

	loadCode();
	$("#form").steps({
		bodyTag : "fieldset",
		onStepChanging : function(event, currentIndex, newIndex) {
			var form = $(this);
			form.validate().settings.ignore = ":disabled,:hidden";
			return form.valid()
		},
		onStepChanged : function(event, currentIndex, priorIndex) {
			if (currentIndex == 1 && !isSendMail) {
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
					url : "backPwd.do",
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.success) {
							$.success("操作成功,即将为您跳转到登录页面...",function(){
								window.location.href = 'login.htm';
							});
						} else {
							$("#finishedTitle").html($.error(data.msg));
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
			'loginName' : {
				email : true,
				remote:{
				    url: 'checkLoginNameForPwd.do',     //后台处理程序
				    type: 'POST',               //数据发送方式
				    data: {                     //要传递的数据
				        loginName: function() {
				            return $("#loginName").val();
				        }
				    }
				}
			},
			'verifyCode' : {
				remote : {
					type : "POST",
					url : "checkBackPwdverifyCode.do",
					data : {
						pwdCode : function() {
							return $("#verifyCode").val();
						}
					}
				}
			},
			'mailCode' : {
				remote : {
					type : "POST",
					url : "checkBackPwdMailCode.do",
					data : {
						mailCode : function() {
							return $("#mailCode").val();
						}
					}
				}
			},
			'newPwd' : {
				required : true,
				rangelength : [ 6, 10 ]
			},
			'confirmNewPwd' : {
				required : true,
				equalTo : "#newPwd",
				rangelength : [ 6, 10 ]
			}
		},
		messages:{
			'loginName':{
				remote:"该用户不存在"
			},
			'verifyCode':{
				remote:"验证码错误"
			},
			'mailCode':{
				remote:"邮箱验证码错误"
			}
		}
	})
});