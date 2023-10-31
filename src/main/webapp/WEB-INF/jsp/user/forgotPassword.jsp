<%-- Document : login Created on : Oct 25, 2023, 12:24:50 AM Author : TTNhan --%>

<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
          integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="/public/assets/css/login.css">
    <link rel="stylesheet" href="/public/assets/css/toast.css">
    <title>Login page</title>


</head>

<body>
<div id="main">
    <div class="box">

        <c:choose>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 0}">
                <form action="" method="post" class="login_form">

                    <h1>Forgot password</h1>
                    <div class="input_div">
                        <div class="input_box">
                            <input type="email" required="required" name="email">
                            <span>Email</span>
                            <i></i>
                        </div>
                    </div>

                    <input type="submit" value="Reset password">
                </form>
            </c:when>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 1}">
                Recover email has been sent to your email
            </c:when>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 2}">
                Email doesn't associate with any account
            </c:when>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 3}">
                <form action="/resetPassword" method="post" class="login_form" id="passwordForm">

                    <h1>Reset Password</h1>
                    <div class="input_div">
                        <div class="input_box">
                            <input type="password"
                                   id="password"
                                   name="password"
                                   required>
                            <span>New password</span>
                            <i></i>
                        </div>

                        <div class="input_box">
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                            <span>Confirm new password</span>
                            <i></i>
                        </div>
                    </div>
                    <div id="passwordMatchError" style="display: none; color: red">
                        Passwords do not match.
                    </div>

                    <input type="submit" value="Reset password">
                </form>
            </c:when>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 4}">
                Request invalided
            </c:when>
            <c:when test="${sessionScope.sentPasswordRecoveryEmail == 5}">
                Password changed
            </c:when>
            <c:otherwise>
                Oppise woossy this should not happend
            </c:otherwise>
        </c:choose>
    </div>


</div>
<%@include file="foot.jsp" %>

<%@include file="popUpMessage.jsp" %>

<script src="/public/assets/js/change_password.js"></script>

<script>
    // Đợi 10 giây trước khi xóa div
    setTimeout(function () {
        let messageDiv = document.getElementById('message');
        messageDiv.parentNode.removeChild(messageDiv);
    }, 5000);
</script>
</body>

</html>