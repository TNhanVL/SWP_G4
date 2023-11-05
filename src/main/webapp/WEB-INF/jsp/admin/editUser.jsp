<%-- 
    Document   : dashboard
    Created on : Oct 29, 2023, 12:54:53 AM
    Author     : TTNhan
--%>
<%@page import="com.swp_project_g4.Database.CountryDAO" %>
<%@page import="com.swp_project_g4.Model.Country" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page import="com.swp_project_g4.Database.AdminDAO" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.swp_project_g4.Model.Learner" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@ page import="com.swp_project_g4.Database.LearnerDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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

<aside class="sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3   bg-gradient-dark"
       id="sidenav-main">
    <div class="sidenav-header">
        <i class="fas fa-times p-3 cursor-pointer text-white opacity-5 position-absolute end-0 top-0 d-none d-xl-none"
           aria-hidden="true" id="iconSidenav"></i>

        <span class="navbar-brand m-0 ms-1 font-weight-bold text-white">
                Hello
                <%out.print(CookieServices.getUserNameOfAdmin(request.getCookies()));%>!
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

            <li class="nav-item mt-3">
                <h6 class="ps-4 ms-2 text-uppercase text-xs text-white font-weight-bolder opacity-8">Pages</h6>
            </li>

            <li class="nav-item">
                <a class="nav-link text-white active bg-gradient-primary" href="/admin/dashboard">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">table_view</i>
                    </div>
                    <span class="nav-link-text ms-1">Dashboard</span>
                </a>
            </li>

        </ul>
    </div>


</aside>

<c:set var="user" scope="session" value="${sessionScope.currentUser}"/>
<c:choose>
    <c:when test='${user.picture == "null" || empty user.picture}'>
        <c:set var="picture" scope="session" value="/public/assets/imgs/logo.png"/>
    </c:when>
    <c:otherwise>
        <c:set var="picture" scope="session" value="/public/media/user/${user.ID}/${user.picture}"/>
    </c:otherwise>
</c:choose>

<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">
    <div class="container-fluid py-4">
        <div class="row">
            <div class="card h-100">
                <div class="card-header pb-0 p-3">
                    <div class="col-6 d-flex align-items-center">
                        <h6 class="mb-0">Profile</h6>
                    </div>
                </div>
                <div class="card-body pt-4 p-3 ">
                    <div class="row container-fluid">
                        <div class="col-12 col-md-4 col-xl-3">
                            <div class="card" style="width: 18rem;">
                                <img class="card-img-top" src=${picture}>
                                <div class="card-body">
                                    <div class="card-title">
                                        <h5 class="">
                                            ${sessionScope.currentUser.username}
                                        </h5>
                                        ID
                                        <h6 class="text-dark font-weight-bold ">
                                            ${sessionScope.currentUser.ID}
                                        </h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 col-xl-9">

                            <div class="col-md-12 content">

                                <div class="card card-primary">
                                    <div class="card-header">
                                        <h3 class="card-title">User Information</h3>
                                    </div>
                                    <form modelAttribute="driver"
                                          action='${sessionScope.addUser ? "./addLearner" : "./editUser?id=" }${sessionScope.addUser ? "" : user.ID }'
                                          method="post"
                                          id="updateUserForm">
                                        <div class="card-body">

                                            <div class="form-group" hidden="hidden">
                                                <label>ID</label>
                                                <input type="text" name="ID" value="${user.ID}">
                                                <input type="text" name="picture"
                                                       value="${user.picture}">

                                            </div>
                                            <div class="form-group">
                                                <label for="username">Username</label>
                                                <input type="text" class="form-control" id="username"
                                                       name="username" placeholder="Username"
                                                       value="${user.username}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="password">Password</label>
                                                <input type="password" class="form-control" id="password"
                                                       name="password" placeholder="Password"
                                                       value="${user.password}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="email" class="form-control" id="email"
                                                       name="email" placeholder="Email"
                                                       value="${user.email}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="firstName">First name</label>
                                                <input type="text" class="form-control" id="firstName"
                                                       name="firstName" placeholder="First Name"
                                                       value="${user.firstName}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="lastName">Last name</label>
                                                <input type="text" class="form-control" id="lastName"
                                                       name="lastName" placeholder="Last Name"
                                                       value="${user.lastName}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="birthday">Birthday</label>
                                                <input type="date" class="form-control" id="birthday"
                                                       name="birthday" placeholder="Birthday"
                                                       value=
                                                       <fmt:formatDate pattern="yyyy-MM-dd"
                                                                       value="${user.birthday}"/>
                                                               required>
                                            </div>
                                            <div class="form-group">
                                                <label for="country">country</label>
                                                <select id="country" class="form-control" name="countryID" required>
                                                    <c:forEach var="country" items="${sessionScope.countryList}">
                                                        <option value=${country.ID} ${country.ID == user.countryID ? "selected" : "" }>
                                                                ${country.name}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="status">status</label>
                                                <select id="status" class="form-control" name="status" required>
                                                    <option value="0"
                                                    ${user.status == 0 ? "selected" : ""}>
                                                        Active
                                                    </option>
                                                    <option value="1"
                                                    ${user.status == 1 ? "selected" : ""}>
                                                        Locked
                                                    </option>
                                                </select>
                                            </div>
                                            <div></div>
                                        </div>
                                        <!-- /.card-body -->

                                        <div class="card-footer">
                                            <button type="submit"
                                                    class="btn btn-primary">${sessionScope.addUser ? "Add" : "Update"}</button>
                                            <a href="./dashboard">
                                                <div class="btn btn-primary">Back</div>
                                            </a>
                                            <a href="./deleteUser?id=${user.ID}"
                                               onclick="return confirm('Do you want to delete this learner?')">
                                                <div class="btn btn-primary">Delete</div>
                                            </a>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${!(sessionScope.addUser)}">
                <div class="card my-4">
                    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
                            <h6 class="text-white text-capitalize ps-3">Bought courses</h6>
                        </div>
                    </div>
                    <div class="card-body px-0 pb-2">
                        <table id="example" class="table align-items-center mb-0">
                            <thead>
                            <tr>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Course
                                </th>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Status
                                </th>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Progress
                                </th>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Rated
                                </th>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Total time
                                </th>
                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                    Start when
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="course_progress" items="${sessionScope.courseProgress}" varStatus="s">
                                <c:set var="course" scope="page" value="${sessionScope.courseList[s.index]}"/>

                                <c:choose>
                                    <c:when test='${course.picture == "null"}'>
                                        <c:set var="picture" scope="page" value="/public/assets/imgs/logo.png"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="picture" scope="page"
                                               value="/public/media/course/${course.ID}/${course.picture}"/>
                                    </c:otherwise>
                                </c:choose>
                                <tr>
                                    <td>
                                        <div class="d-flex px-2 py-1">
                                            <div>
                                                <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                                     src=${picture}>
                                            </div>
                                            <div class="d-flex flex-column justify-content-center">
                                                <a href="./editUser?id=${course.ID}"
                                                   class="text-secondary font-weight-bold text-xs">
                                                    <h6 class="mb-0 text-sm">${course.name}</h6>
                                                </a>
                                                <p class="text-xs text-secondary mb-0">${course.description}</</p>
                                            </div>
                                        </div>
                                    </td>
                                    <td class=" text-sm">
                                        <c:choose>
                                            <c:when test="${course_progress.enrolled}">
                                                <span class="badge badge-sm bg-success">Enrolled</span>
                                            </c:when>
                                            <c:when test="${course_progress.completed}">
                                                <span class="badge badge-sm bg-warning">Complete</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-sm bg-danger">Not Enrolled</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <p class="text-xs text-secondary mb-0">${course_progress.progressPercent} %</p>
                                        <input type="range" min="0" max="100"
                                               value="${course_progress.progressPercent}" disabled>
                                    </td>
                                    <td>
                                        <p class="text-xs text-secondary mb-0">${course_progress.rate} %</p>
                                    </td>
                                    <td>
                                        <p class="text-xs text-secondary mb-0">
                                                ${course_progress.totalTime}
                                        </p>
                                    </td>
                                    <td>
                                        <p class="text-xs text-secondary mb-0">
                                            <fmt:formatDate pattern="yyyy-MM-dd" value="${course_progress.startAt}"/>
                                        </p>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>new DataTable('#example');</script>
</body>

<%@ include file="popUpMessage.jsp" %>