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
        response.sendRedirect("/admin/login");
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
            <%@include file="tables/learner.jsp" %>
            <%@include file="tables/instructor.jsp" %>
            <%@include file="tables/organization.jsp" %>
            <%@include file="tables/courses.jsp" %>
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
</body>
