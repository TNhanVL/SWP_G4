<%-- 
    Document   : header
    Created on : Oct 3, 2023, 12:52:05 AM
    Author     : TTNhan
--%>

<%@page import="java.net.URL" %>
<%@page import="com.swp_project_g4.Database.CourseDAO" %>
<%@page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page import="com.swp_project_g4.Database.InstructorDAO" %>
<%@page import="com.swp_project_g4.Model.Learner" %>
<%@page import="com.swp_project_g4.Model.Instructor" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Learner learnerHeader = null;
    Instructor instructorHeader = null;
    int numberOfOrderHeader = 0;
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        learnerHeader = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
        numberOfOrderHeader = CourseDAO.countCartProduct(learnerHeader.getID());
    } else if (CookieServices.checkInstructorLoggedIn(request.getCookies())) {
        instructorHeader = InstructorDAO.getInstructorByUsername(CookieServices.getUserNameOfInstructor(request.getCookies()));
        numberOfOrderHeader = 0;
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
    </div>

    <div class="right-side">
        <%if (learnerHeader != null) {%>
        <a href="/cart" class="cart">
            <i class="fa-solid fa-cart-shopping"></i>
            <div class="quantity"><%=numberOfOrderHeader%>
            </div>
        </a>
        <a onclick="toggle_notification()" class="notification">
            <i class="fa-sharp fa-solid fa-bell"></i>
            <p class="quantity" id="notification_quantity" style="display: none;position: absolute"></p>
        </a>
        <div style="position: relative;right: 40px;top: 30px ;">
            <ul style="position: absolute;list-style-type: none;display: none" id="notification_list">
            </ul>
        </div>

        <%}%>

        <div onclick="openMenu()" id="user" class="user">
            <a href=<%=(learnerHeader != null || instructorHeader != null) ? "#" : "/login"%>>
                <img src="<%
                    if (learnerHeader != null || instructorHeader != null) {
                        try {
                            if(learnerHeader != null){
                                new URL(learnerHeader.getPicture()).toURI();
                                out.print(learnerHeader.getPicture());
                            }
                            else {
                                new URL(instructorHeader.getPicture()).toURI();
                                out.print(instructorHeader.getPicture());
                            }

                        } catch (Exception e) {
                            if(learnerHeader != null)
                            out.print("/public/media/user/" + learnerHeader.getID() + "/" + learnerHeader.getPicture());
                            else
                            out.print("/public/media/instructor/" + instructorHeader.getID() + "/" + instructorHeader.getPicture());
                        }

                    } else {
                        out.print("https://upload.wikimedia./org/wikipedia/commons/9/99/Sample_User_Icon.png");
                    }
%>"
                     alt="avatar">
                <span class="userInfor"><%=learnerHeader != null ? learnerHeader.getUsername() : ""%>
                    <%=instructorHeader != null ? instructorHeader.getUsername() + "(instructor)" : ""%>
                    <%=(learnerHeader == null && instructorHeader == null) ? "Guest!": ""%></span>
            </a>

            <% if (learnerHeader != null || instructorHeader != null) {%>
            <div id="userMenu" class="userMenu close">
                <%if (learnerHeader != null)
                {%>
                <a href="/profile/<%=learnerHeader.getUsername()%>">
                    <i class="fa-solid fa-user"></i>
                    <span>Profile</span>
                </a>
                <%}%>
                <%if (instructorHeader != null)
                {%>
                <a href="/profile/instructor/<%=instructorHeader.getUsername()%>">
                    <i class="fa-solid fa-user"></i>
                    <span>Profile</span>
                </a>
                <%}%>
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
<% if (learnerHeader != null) {%>
<input type="hidden" name="id_string" class="id_string" id="id_string" value="<%=learnerHeader.getID()%>"/>
<%}%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/public/assets/js/option.js"></script>
