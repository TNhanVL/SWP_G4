<%-- Document : login Created on : Oct 25, 2023, 12:24:50 AM Author : TTNhan --%>

<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        response.sendRedirect("./");
        return;
    }

%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link rel="stylesheet" href="/public/assets/css/login.css">
    <link rel="stylesheet" href="/public/assets/css/toast.css">
    <title>Login</title>
</head>

<body>
<div class="box">
    <form action="" method="post" class="login_form">

        <h1>Sign in</h1>
        <div class="usertype">
            <select name="account_type" id="account_type">
                <option value="learner">Learner</option>
                <option value="admin">Admin</option>
                <option value="instructor">Instructor</option>
                <option value="organization">Organization</option>
            </select>
        </div>
        <div class="input_div">
            <div class="input_box">
                <input type="text" required="required" name="username">
                <span>Username</span>
                <i></i>
            </div>
            <div class="input_box">
                <input type="password" required="required" name="password">
                <span>Password</span>
                <i></i>
            </div>
        </div>
        <div class=" links">
            <a href="./forgotPassword">Forgot Password</a>
            <a href="./signup">Signup</a>
        </div>
        <input type="submit" value="Login">

    </form>

    <div class="accountLogin">
        <div class="loginWithGG">
            <%
                String host = request.getServerName() + ":" + request.getServerPort();
            %>
            <a
                    href="https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.profile%20https://www.googleapis.com/auth/userinfo.email&redirect_uri=<%=host%>/loginWithGG&response_type=code
                               &client_id=246255507082-vpebidclj199n0sgg035cos2ijabjrmg.apps.googleusercontent.com&approval_prompt=force">
                <i class="fa-brands fa-google"></i>
                <p>Login with Google</p>
            </a>
        </div>
    </div>
</div>
<%@include file="foot.jsp" %>

<%@include file="popUpMessage.jsp" %>
</body>

</html>