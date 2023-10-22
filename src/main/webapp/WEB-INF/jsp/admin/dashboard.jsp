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

<%@include file="sidebar.jsp" %>

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

                            <table id="example" class="table align-items-center mb-0">
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
                                                    <img src="../assets/img/team-2.jpg"
                                                         class="avatar avatar-sm me-3 border-radius-lg" alt="user1">
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
                                            <a href="./deleteUser?id=${user.ID}"
                                               class="text-secondary font-weight-bold text-xs">
                                                <i class="fa-solid fa-trash" style="color: #ff0000;"></i>
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
    new DataTable('#example');
</script>


</body>

</html>

<%@ include file="popUpMessage.jsp" %>