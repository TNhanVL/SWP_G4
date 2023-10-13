<%-- Document : login Created on : Jun 25, 2023, 12:24:50 AM Author : TTNhan --%>

<%@page import="com.swp_project_g4.Service.CookieServices"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    if (CookieServices.checkUserLoggedIn(request.getCookies())) {
        response.sendRedirect("./");
        return;
    }
    
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
              integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="/public/assets/css/login.css">
        <link rel="stylesheet" href="/public/assets/css/toast.css">
        <title>Login page</title>
    </head>

    <body>
        <div id="main">
            <div class="box">
                <form action="" method="post">

                    <h2>Sign in</h2>
                    <div class="inputBox">
                        <input type="text" required="required" name="username">
                        <span>Userame</span>
                        <i></i>
                    </div>
                    <div class="inputBox">
                        <input type="password" required="required" name="password">
                        <span>Password</span>
                        <i></i>
                    </div>
                    <div class="links">
                        <a href="#">Forgot Password</a>
                        <a href="./signup">Signup</a>
                    </div>
                    <input type="submit" value="Login">

                    <p id="message"></p>

                </form>

                <div class="accountLogin">
                    <h6> or </h6>
                    <div class="loginWithGG">
                        <a href="https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.profile%20https://www.googleapis.com/auth/userinfo.email&redirect_uri=http://localhost:8080/loginWithGG&response_type=code
                           &client_id=246255507082-vpebidclj199n0sgg035cos2ijabjrmg.apps.googleusercontent.com&approval_prompt=force">
                            <img src="/public/assets/imgs/logogoogle.png" alt="">
                            <p>Login with Google</p>
                        </a>
                    </div>
                </div>

                <script>
                    // Đợi 10 giây trước khi xóa div
                    setTimeout(function () {
                        let messageDiv = document.getElementById('message');
                        messageDiv.parentNode.removeChild(messageDiv);
                    }, 5000);
                </script>
            </div>
        </div>
        <%@include file="foot.jsp" %>

        <%@include file="popUpMessage.jsp" %>
    </body>

</html>