<%-- 
    Document   : dashboard
    Created on : Oct 29, 2023, 12:54:53 AM
    Author     : TTNhan
--%>
<%@page import="com.swp_project_g4.Database.CountryDAO" %>
<%@page import="com.swp_project_g4.Model.Country" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.swp_project_g4.Database.UserDAO" %>
<%@page import="com.swp_project_g4.Database.AdminDAO" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.swp_project_g4.Model.User" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@ page import="com.swp_project_g4.Database.UserDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (!CookieServices.checkAdminLoggedIn(request.getCookies())) {
        response.sendRedirect("/login");
    }

    //Get ID
    int ID;
    try {
        ID = Integer.parseInt(request.getParameter("id"));
        if (UserDAO.getUser(ID) == null) {
            throw new Exception();
        }
    } catch (Exception e) {
        response.sendRedirect("./dashboard");
        return;
    }
%>

<jsp:include page="head.jsp">
    <jsp:param name="title" value="Edit user"/>
</jsp:include>

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
<c:set var="org" scope="request" value="${requestScope.currentOrg}"/>
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
                                <img class="card-img-top" src=
                                <c:choose>
                                <c:when test='${org.picture == "null"}'>
                                        "/public/assets/imgs/logo.png"
                                </c:when>
                                <c:otherwise>
                                    "/public/media/organization/${org.organizationID}/${org.picture}"
                                </c:otherwise>
                                </c:choose>>
                                <div class="card-body">
                                    <div class="card-title">
                                        <h5 class="">
                                            ${org.name}
                                        </h5>
                                        ID
                                        <h6 class="text-dark font-weight-bold ">
                                            ${org.organizationID}
                                        </h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 col-xl-9">

                            <div class="col-md-12 content">

                                <div class="card card-primary">
                                    <div class="card-header">
                                        <h3 class="card-title">Organization Information</h3>
                                    </div>

                                    <form modelAttribute="driver" action="./editOrganization?id=${org.organizationID}"
                                          method="post"
                                          id="updateUserForm">
                                        <div class="card-body">

                                            <div class="form-group" hidden="hidden">
                                                <label>ID</label>
                                                <input type="text" name="ID" value="${org.organizationID}">
                                                <input type="text" name="picture"
                                                       value="${org.picture}">

                                            </div>
                                            <div class="form-group">
                                                <label for="username">Account name</label>
                                                <input type="text" class="form-control" id="username"
                                                       name="username" placeholder="Username"
                                                       value="${org.username}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="password">Password</label>
                                                <input type="password" class="form-control" id="password"
                                                       name="password" placeholder="Password"
                                                       value="${org.password}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="email">Email</label>
                                                <input type="email" class="form-control" id="email"
                                                       name="email" placeholder="Email"
                                                       value="${org.email}"
                                                       required>
                                            </div>
                                            <div class="form-group">
                                                <label for="country">country</label>
                                                <select id="country" class="form-control" name="countryID" required>
                                                    <%--                                                    <%--%>
                                                    <%--                                                        ArrayList<Country> countries = CountryDAO.getAllCountry();--%>
                                                    <%--                                                        for (Country country : countries) {--%>
                                                    <%--                                                    %>--%>
                                                    <%--                                                    <option value=--%>
                                                    <%--                                                                <%out.print(country.getID());--%>
                                                    <%--                                                                                                                if (country.getID() == ${org.organizationID}) {%>--%>
                                                    <%--                                                                    selected="selected"--%>
                                                    <%--                                                            <%}%>>--%>
                                                    <%--                                                        <%out.print(country.getName());%>--%>
                                                    <%--                                                    </option>--%>
                                                    <%--                                                    <%}%>--%>
                                                    <c:forEach var="country" items="${requestScope.countryList}">
                                                        <option>
                                                            <c:if test="${country.name = org.}"></c:if>
                                                                ${country.name}

                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div></div>
                                        </div>
                                        <!-- /.card-body -->

                                        <div class="card-footer">
                                            <button type="submit" class="btn btn-primary">Update</button>
                                            <a href="./dashboard">
                                                <div class="btn btn-primary">Back</div>
                                            </a>
                                        </div>
                                    </form>


                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<%@ include file="popUpMessage.jsp" %>