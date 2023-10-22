<%-- 
    Document   : dashboard
    Created on : Oct 29, 2023, 12:54:53 AM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.UserDAO" %>
<%@page import="com.swp_project_g4.Database.AdminDAO" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.swp_project_g4.Model.User" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@ page import="com.swp_project_g4.Database.UserDAO" %>
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


<aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark"
       id="sidenav-main">
    <div class="sidenav-header">
        <i class="fas fa-times p-3 cursor-pointer text-white opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
           aria-hidden="true" id="iconSidenav"></i>

        <span class="navbar-brand m-0 ms-1 font-weight-bold text-white">
                Hello
                <%out.print(CookieServices.getUserName(request.getCookies()));%>!
            </span>
    </div>
    <hr class="horizontal light mt-0 mb-2">
    <div class="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link text-white " onclick="location.href = './logout';">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">login</i>
                    </div>
                    <span class="nav-link-text ms-1">Logout</span>
                </a>
            </li>
        </ul>
    </div>

</aside>

<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">

    <div class="container-fluid py-4">
        <div class="row">
            <div class="col-12">
                <div class="card my-4">
                    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                            <h6 class="text-white text-capitalize ps-3">Users table</h6>
                        </div>
                    </div>
                    <div class="card-body px-0 pb-2">
                        <div class="table-responsive p-0" style="margin: 10px">

                            <table id="user" class="table align-items-center mb-0">
                                <thead>
                                <tr>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                        Account
                                    </th>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                                        Username
                                    </th>
                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                        Status
                                    </th>
                                    <th class="text-secondary opacity-7"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${sessionScope.userList}">
                                    <tr>
                                        <td>
                                            <div class="d-flex px-2 py-1">
                                                <div>
                                                    <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                                         src=
                                                         <c:choose>
                                                         <c:when test='${empty sessionScope.currentUser.picture}'>
                                                                 "/public/assets/imgs/logo.png"
                                                    </c:when>
                                                    <c:otherwise>
                                                        "${sessionScope.currentUser.picture}"
                                                    </c:otherwise>
                                                    </c:choose>
                                                    >
                                                </div>
                                                <div class="d-flex flex-column justify-content-center">
                                                    <h6 class="mb-0 text-sm">${user.username}</h6>
                                                    <p class="text-xs text-secondary mb-0">${user.email}</</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <p class="text-xs font-weight-bold mb-0">
                                                <c:choose>
                                                    <c:when test="${user.role == 0}">
                                                        Student
                                                    </c:when>
                                                    <c:when test="${user.role == 1}">
                                                        Instructor
                                                    </c:when>
                                                </c:choose>
                                            </p>
                                            <p class="text-xs text-secondary mb-0">Organization</p>
                                        </td>
                                        <td class="align-middle text-center text-sm">
                                            <c:choose>
                                                <c:when test="${user.status == 0}">
                                                    <span class="badge badge-sm bg-gradient-danger">Locked</span>
                                                </c:when>
                                                <c:when test="${user.status == 1}">
                                                    <span class="badge badge-sm bg-gradient-success">Active</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="align-middle">
                                            <a href="./editUser?id=${user.ID}"
                                               class="text-secondary font-weight-bold text-xs">
                                                <i class="fas fa-solid fa-pen"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12">
                <div class="card my-4">
                    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                            <h6 class="text-white text-capitalize ps-3">Users table</h6>
                        </div>
                    </div>
                    <div class="card-body px-0 pb-2">
                        <div class="table-responsive p-0" style="margin: 10px">

                            <table id="organization" class="table align-items-center mb-0">
                                <thead>
                                <tr>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                        Account
                                    </th>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                                        Username
                                    </th>
                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                        Status
                                    </th>
                                    <th class="text-secondary opacity-7"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${sessionScope.userList}">
                                    <tr>
                                        <td>
                                            <div class="d-flex px-2 py-1">
                                                <div>
                                                    <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                                         src=
                                                         <c:choose>
                                                         <c:when test='${empty sessionScope.currentUser.picture}'>
                                                                 "/public/assets/imgs/logo.png"
                                                    </c:when>
                                                    <c:otherwise>
                                                        "${sessionScope.currentUser.picture}"
                                                    </c:otherwise>
                                                    </c:choose>
                                                    >
                                                </div>
                                                <div class="d-flex flex-column justify-content-center">
                                                    <h6 class="mb-0 text-sm">${user.username}</h6>
                                                    <p class="text-xs text-secondary mb-0">${user.email}</</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <p class="text-xs font-weight-bold mb-0">
                                                <c:choose>
                                                    <c:when test="${user.role == 0}">
                                                        Student
                                                    </c:when>
                                                    <c:when test="${user.role == 1}">
                                                        Instructor
                                                    </c:when>
                                                </c:choose>
                                            </p>
                                            <p class="text-xs text-secondary mb-0">Organization</p>
                                        </td>
                                        <td class="align-middle text-center text-sm">
                                            <c:choose>
                                                <c:when test="${user.status == 0}">
                                                    <span class="badge badge-sm bg-gradient-danger">Locked</span>
                                                </c:when>
                                                <c:when test="${user.status == 1}">
                                                    <span class="badge badge-sm bg-gradient-success">Active</span>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td class="align-middle">
                                            <a href="./editUser?id=${user.ID}"
                                               class="text-secondary font-weight-bold text-xs">
                                                <i class="fas fa-solid fa-pen"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</main>
<!--   Core JS Files   -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
    new DataTable('#user');
    new DataTable('#organization');
</script>
</body>
<%@ include file="popUpMessage.jsp" %>