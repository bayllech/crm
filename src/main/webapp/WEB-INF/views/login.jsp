<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>系统登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/css/font-awesome.min.css">
    <!-- Ionicons -->
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="/static/plugins/iCheck/square/blue.css">

    <link rel="stylesheet" href="/static/css/style.css">


</head>
<body class="hold-transition login-page" style="background-image: url(/static/img/blue-gradients.jpg);">
<div class="login-box">
    <div class="login-logo">
        <a href="#"><h2 style="color: rgba(17,17,34,0.98);">HEROIC CRM</h2></a>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body" style="background-color: #ffe;">
        <p class="login-box-msg">请输入帐号密码</p>

        <form method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" name="userName" placeholder="帐号">
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" name="password" placeholder="密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label style="margin-left:20px"><input type="checkbox">记住我</label>
                    </div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                </div>
                <!-- /.col -->
            </div>
        </form>


        <!-- /.social-auth-links -->

        <a href="#">忘记密码</a><br>

    </div>
    <!-- /.login-box-body -->



</div>
<!-- /.login-box -->

<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="/static/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script>
    $(function () {

    });
</script>
</body>
</html>
