<%-- 
    Document   : dashboard
    Created on : Oct 29, 2023, 12:54:53 AM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page import="com.swp_project_g4.Database.AdminDAO" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.swp_project_g4.Model.Learner" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@ page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
        response.sendRedirect("/login");
    }
%>

<head>
    <title>
        Admin dashboard
    </title>
    <c:import url="head.jsp"></c:import>
</head>

<body class="g-sidenav-show  bg-gray-200">
<%@ include file="sidebar.jsp" %>
<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">

    <div class="container-fluid py-4">
        <div class="row">
            <div id="default"></div>
            <div id="learner_div">
                <%@include file="tables/learner.jsp" %>
            </div>
            <div id="instructor_div" style="display: none">
                <%@include file="tables/instructor.jsp" %>
            </div>
            <div id="organization_div" style="display: none">
                <%@include file="tables/organization.jsp" %>
            </div>
            <div id="courses_div" style="display: none">
                <%@include file="tables/courses.jsp" %>
            </div>
        </div>
    </div>
</main>
<%@ include file="popUpMessage.jsp" %>

<!--   Core JS Files   -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
    new DataTable('#user');
    new DataTable('#instructor');
    new DataTable('#organization');
    new DataTable('#courses');
</script>
<script>
    function showTable(table) {
        const tables = ["default", "learner_div", "instructor_div", "organization_div", "courses_div"];
        tables.forEach((x) => {
                if (x == table) {
                    $("#" + table).show()
                    $("#sidebar_" + x).addClass("bg-info")
                } else {
                    $("#" + x).hide()
                    $("#sidebar_" + x).removeClass("bg-info")
                }
            }
        )
    }
</script>
</body>
