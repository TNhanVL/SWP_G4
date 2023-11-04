<%-- 
    Document   : header
    Created on : Oct 3, 2023, 12:52:05 AM
    Author     : TTNhan
--%>

<%@page import="java.net.URL" %>
<%@page import="com.swp_project_g4.Database.CourseDAO" %>
<%@page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page import="com.swp_project_g4.Model.Learner" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="repo" class="com.swp_project_g4.Repository.Repo"/>
<%
    boolean loggedInHeader = false;
    Learner learnerHeader = null;
    int numberOfOrderHeader = 0;
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        loggedInHeader = true;
        learnerHeader = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
        numberOfOrderHeader = CourseDAO.countCartProduct(learnerHeader.getID());
    }
%>

<div id="header">
    <div class="left-side">
        <a href="/">
            <img src="/public/assets/imgs/logo.png" alt="logo" class="logo">
        </a>
        <form action="">
            <input type="text" class="search-course" name="headerSearch" placeholder="Searching">
        </form>
        <div class="course-opption">
            <a href="/course/all">Courses</a>
        </div>
        <div class="quesAndAns">
            <a href="#">Expert Q&A</a>
        </div>
    </div>

    <div class="right-side">
        <%if (loggedInHeader) {%>
        <a href="/cart" class="cart">
            <i class="fa-solid fa-cart-shopping"></i>
            <div class="quantity"><%=numberOfOrderHeader%>
            </div>
        </a>
        <a href="" class="notification">
            <i class="fa-sharp fa-solid fa-bell"></i>
            <div class="quantity"></div>
        </a>
        <%}%>

        <div onclick="openMenu()" id="user" class="user">
            <a href=<%=loggedInHeader ? "#" : "/login"%>>
                <img src="<%
                    if (loggedInHeader) {
                        try {
                            new URL(learnerHeader.getPicture()).toURI();
                            out.print(learnerHeader.getPicture());
                        } catch (Exception e) {
                            out.print("/public/media/user/" + learnerHeader.getID() + "/" + learnerHeader.getPicture());
                        }

                    } else {
                        out.print("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png");
                    }
%>"
                     alt="avatar">
                <span class="userInfor"><%=loggedInHeader ? learnerHeader.getUsername() : "Guest!"%></span>
            </a>

            <% if (loggedInHeader) { %>
            <div id="userMenu" class="userMenu close">
                <a href="/profile/<%=learnerHeader.getUsername()%>">
                    <i class="fa-solid fa-user"></i>
                    <span>Profile</span>
                </a>
                <a href="/logout?token=learner">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span>Logout</span>
                </a>
            </div>
            <%
                }
            %>
        </div>

    </div>
</div>
<% if (loggedInHeader) {%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/public/assets/js/option.js"></script>
<%}%>
