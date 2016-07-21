<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录-好心情移动医疗APP后台管理系统</title>
    <meta name="keywords" content="好心情移动医疗">
    <meta name="description" content="好心情移动医疗">

    <link rel="shortcut icon" href="assets/img/favicon.ico">
    <link href="assets/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="assets/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="assets/css/animate.min.css" rel="stylesheet">
    <link href="assets/css/style.min.css?v=4.0.0" rel="stylesheet">
    <!--[if lt IE 8]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>

</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <img src="assets/img/cloud.png"/>
            </div>
            <h3>好心情移动医疗APP后台管理系统</h3>
            <span style="color: red">${errMsg}</span>
            <form class="m-t" role="form" id="loginForm" action="login.do" method="post">
                <div class="form-group">
                    <input type="email" class="form-control" name="loginName" value="${loginName }" placeholder="用户名" required="" />
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password"  placeholder="密码" required=""/>
                </div>
                 <div class="form-group">
                    <input type="text" name="verify" class="form-control" style="width: 70%;float:left;margin-bottom:10px;" placeholder="验证码" maxlength="4" />
					<img id="verifycode" style="width:25%;height:35px;  backgroud-color:white;float:right;cursor: pointer;" onclick="loadCode()"/>
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>
                <p class="text-muted text-center"> <a href="toBackPwd.htm"><small>忘记密码了？</small></a> | <a href="register.htm">注册一个新账号</a>
                </p>
            </form>
        </div>
    </div>
    <script src="assets/js/jquery.min.js?v=2.1.4"></script>
    <script src="assets/js/bootstrap.min.js?v=3.3.5"></script>
	<script type="text/javascript" src="assets/js/login.js"></script>
</body>

</html>