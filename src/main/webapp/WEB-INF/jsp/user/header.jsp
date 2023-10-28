<%-- 
    Document   : header
    Created on : Oct 3, 2023, 12:52:05 AM
    Author     : TTNhan
--%>

<%@page import="java.net.URL"%>
<%@page import="com.swp_project_g4.Database.CourseDAO"%>
<%@page import="com.swp_project_g4.Database.LearnerDAO"%>
<%@page import="com.swp_project_g4.Model.Learner"%>
<%@page import="com.swp_project_g4.Service.CookieServices"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    boolean loggedInHeader = false;
    Learner learnerHeader = null;
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        loggedInHeader = true;
        learnerHeader = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
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
        <a href="/cart" class="cart">
            <i class="fa-solid fa-cart-shopping"></i>
            <%
                int numberOfOrderHeader = 0;
                if (learnerHeader != null) {
                    numberOfOrderHeader = CourseDAO.countCartProduct(learnerHeader.getID());
                }
                if (numberOfOrderHeader > 0) {
            %>
            <div class="quantity"><%out.print(numberOfOrderHeader);%></div>
            <%}%>
        </a>

        <a href="" class="notification">
            <i class="fa-sharp fa-solid fa-bell"></i>
            <div class="quantity">3</div>
        </a>

        <div onclick="openMenu()" id="user" class="user">
            <a href="<%
                if (loggedInHeader) {
                    out.print("#");
                } else {
                    out.print("/login");
                }
               %>">
                <img src="<%
                    if (loggedInHeader) {

                        boolean isUrl = false;
                        try {
                            new URL(learnerHeader.getPicture()).toURI();
                            isUrl = true;
                        } catch (Exception e) {
                        }

                        if (isUrl) {
                            out.print(learnerHeader.getPicture());
                        } else {
                            out.print("/public/media/user/" + learnerHeader.getID() + "/" + learnerHeader.getPicture());
                        }
                    } else {
                        out.print("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png");
                    }
                     %>"
                     alt="avatar">
                <span class="userInfor"><%
                    if (loggedInHeader) {
                        out.print(learnerHeader.getUsername());
                    } else {
                        out.print("Guest!");
                    }
                    %></span>
            </a>

            <%
                if (loggedInHeader) {
            %>
            <div id="userMenu" class="userMenu close">
                <a href="/profile/<%out.print(learnerHeader.getUsername());%>">
                    <i class="fa-solid fa-user"></i>
                    <span>Profile</span>
                </a>
                <a href="#">
                    <i class="fa-solid fa-gear"></i>
                    <span>Setting</span>
                </a>
                <a href="/logout">
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

<%if (loggedInHeader) {%>
<script src="/public/assets/js/option.js"></script>                    
<%}%>
