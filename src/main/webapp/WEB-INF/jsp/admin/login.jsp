<%-- 
    Document   : index
    Created on : Oct 1, 2023, 1:32:33 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    if (CookieServices.checkAdminLoggedIn(request.getCookies())) {
        response.sendRedirect("./dashboard");
    }
%>

<%--<jsp:include page="head.jsp">--%>
<%--    <jsp:param name="title" value="Login"/>--%>
<%--</jsp:include>--%>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>${param.title}</title>

    <!-- Google Font: Source Sans Pro -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">

    <script src="https://kit.fontawesome.com/4e885c2505.js" crossorigin="anonymous"></script>
    <!-- Theme style -->
    <link rel="stylesheet" href="/public/admin/css/adminlte.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="/public/admin/css/styles.css">

    <script src="/public/admin/js/myscript.js"></script>

</head>
<body class="hold-transition login-page">

<div class="login-box">

    <!-- /.login-logo -->
    <div class="card">
        <div class="card-body login-card-body">
            <div class="login-logo">
                <b>Users Management</b>
            </div>
            <%if (request.getSession().getAttribute("error") != null) {%>
            <div class="alert alert-danger">
                <%out.print(request.getSession().getAttribute("error"));%>
            </div>
            <%
                    request.getSession().setAttribute("error", null);
                }%>

            <!-- Create Post Form -->

            <form action="/admin/login" method="post" id="loginForm">
                <div class="form-group">
                    <label for="username">Username</label>
                    <div class="input-group mb-3">
                        <input type="text" id="username" name="username" class="form-control" placeholder="Username"
                               required>
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <i class="fa-solid fa-user"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Username</label>
                    <div class="input-group mb-3">
                        <input type="password" id="password" name="password" class="form-control" placeholder="Password"
                               required>
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <span class="fas fa-lock"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">

                    <div class="col-4">
                        <button type="submit" class="btn btn-primary btn-block">Login</button>
                    </div>
                    <!-- /.col -->
                    <div class="col-4">
                    </div><!-- /.col -->
                    <!-- /.col -->
                    <div class="col-4">
                        <button id="cancelButton" class="btn btn-danger btn-block">Cancel</button>
                    </div>

                </div>
            </form>
        </div>
        <!-- /.login-card-body -->
    </div>
</div>
<!-- /.login-box -->

</body>

<%@ include file="foot.jsp" %>

<div id="toast"></div>

<script>
    document.getElementById("cancelButton").onclick = function () {
        var a = document.getElementsByClassName("alert");
        if (a.length)
            a[0].remove();
        document.getElementsByName("username")[0].value = "";
        document.getElementsByName("password")[0].value = "";
    };


    $(function () {
        $('#loginForm').validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true,
                    minlength: 5
                }
            },
            messages: {
                username: {
                    required: "Please enter a username"
                },
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                }
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });
    });

</script>

<%@ include file="popUpMessage.jsp" %>