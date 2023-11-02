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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
    <link rel="stylesheet" href="/public/assets/css/login.css">
    <link rel="stylesheet" href="/public/assets/css/toast.css">
    <title>Login page</title>


</head>

<body>
<div class="box">

    <c:choose>
        <c:when test="${sessionScope.sentPasswordRecoveryEmail == 0}">
            <form action="" method="post" class="login_form">
                <h1>Forgot password</h1>
                <div class="input_div">
                    <div class="input_box">
                        <input type="text" required="required" name="username">
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
            <form action="/resetPassword" method="post" class="login_form passwordForm" id="passwordForm"
                  name="passwordForm">

                <h1>Reset Password</h1>
                <div class="input_div">
                    <div class="input_box">
                        <input type="password"
                               id="password"
                               name="password">
                        <span>New password</span>
                        <i></i>
                    </div>

                    <div class="input_box">
                        <input type="password" id="confirmPassword" name="confirmPassword">
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
            Oppise woossy this should not happens
        </c:otherwise>
    </c:choose>
</div>


<%@include file="foot.jsp" %>

<%@include file="popUpMessage.jsp" %>

<script>
    $("#passwordForm").on("submit", function (event) {
        var newPassword = $("#password").val();
        var confirmPassword = $("#confirmPassword").val();

        if (newPassword.trim() === "" || confirmPassword.trim() === "") {
            $("#passwordError").show();
            event.preventDefault(); // Prevent form submission
        } else if (newPassword !== confirmPassword) {
            $("#passwordMatchError").show();
            event.preventDefault(); // Prevent form submission
        } else {
            $("#passwordError").hide();
            $("#passwordMatchError").hide();
            return true
        }
        return false
    })
</script>

</body>

</html>