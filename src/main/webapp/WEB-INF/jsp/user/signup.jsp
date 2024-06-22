<%-- 
    Document   : signup
    Created on : Oct 5, 2023, 7:55:04 PM
    Author     : TTNhan
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.swp_project_g4.Database.CountryDAO"%>
<%@page import="com.swp_project_g4.Model.Country"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.swp_project_g4.Model.Learner"%>
<%@page import="com.swp_project_g4.Service.CookieServices"%>
<%@ page import="com.swp_project_g4.Model.Learner" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        response.sendRedirect("./");
        return;
    }

    Learner learner = (Learner) request.getAttribute("userSignUp");
    if (learner == null) {
        learner = new Learner();
    }
    
    request.getSession().setAttribute("learner", learner);
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <jsp:include page="head.jsp">
            <jsp:param name="title" value="Login page"/>
        </jsp:include>
        <link rel="stylesheet" href="/public/assets/css/signup.css">
    </head>
    
    <body>
        <div id="main">
            <div class="box">
                <form action="/signup" method="post" id="signUpForm" class="needs-validation">
                    <input type="text" name="avatar" value="${learner.picture}" style="display: none">

                    <h2>Sign up</h2>
                    <div class="inputBox">
                        <label class="form-label">Username</label>
                        <input class="form-control" id="username" type="text" placeholder="Enter your username" required="required" name="username">
                        <i></i>
                    </div>
                    <div class="inputBox">
                        <label class="form-label">Email</label>
                        <input class="form-control <c:if test="${learner.email != null}">is-valid</c:if>" id="email" type="text" placeholder="Enter your email" required="required" name="email" value="${learner.email}" <c:if test="${learner.email != null}">readonly</c:if>>
                            <i></i>
                        </div>
                        <div class="inputBox-name">
                            <div class="inputBox">
                                <label class="form-label">First Name</label>
                                <input class="form-control" type="text" placeholder="First name" required="required" name="firstName" value="${learner.firstName}">
                            <i></i>
                        </div>
                        <div class="inputBox">
                            <label class="form-label">Last Name</label>
                            <input class="form-control" type="text" placeholder="Last name" required="required" name="lastName" value="${learner.lastName}">
                            <i></i>
                        </div>
                    </div>

                    <div class="inputBox date">
                        <label for="">Birthday</label>
                        <input class="form-control" type="date" value="2023-01-01" required="required" name="birthday">
                        <i></i>
                    </div>

                    <%
                        ArrayList<Country> countries = CountryDAO.getAllCountry();
                        request.getSession().setAttribute("countries", countries);
                    %>

                    <div class="inputBox country">
                        <label class="form-label" for="country">Country</label>
                        <select class="form-control" name="countryId" id="country">
                            <c:forEach items="${countries}" var="country">
                                <option value="${country.ID}" <c:if test="${country.ID == 16}">selected</c:if>>${country.name}</option>
                            </c:forEach>
                        </select>
                        <i></i>
                    </div>

                    <div class="inputBox">
                        <label class="form-label">Password</label>
                        <input class="form-control" type="password" placeholder="Enter your password" required="required" name="password">
                        <i></i>
                    </div>

                    <input type="submit" value="Register">

                </form>

            </div>
        </div>

        <%@include file="foot.jsp" %>

        <script src="/public/assets/js/signup.js"></script>

        <%@include file="popUpMessage.jsp" %>
    </body>

</html>