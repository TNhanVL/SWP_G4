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
<%@page import="com.swp_project_g4.Model.User" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@ page import="com.swp_project_g4.Database.LearnerDAO" %>
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
        if (LearnerDAO.getUser(ID) == null) {
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
                <%out.print(CookieServices.getUserNameOfAdmin(request.getCookies()));%>!
            </span>
    </div>
    <hr class="horizontal light mt-0 mb-2">
    <div class="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="nav-link text-white active bg-gradient-primary" href="../pages/tables.html">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">table_view</i>
                    </div>
                    <span class="nav-link-text ms-1">Tables</span>
                </a>
            </li>

            <li class="nav-item mt-3">
                <h6 class="ps-4 ms-2 text-uppercase text-xs text-white font-weight-bolder opacity-8">Account pages</h6>
            </li>
            <li class="nav-item">
                <a class="nav-link text-white " onclick="location.href = './logout';">
                    <div class="text-white text-center me-2 d-flex align-items-center justify-content-center">
                        <i class="material-icons opacity-10">login</i>
                    </div>
                    <span class="nav-link-text ms-1">Sign In</span>
                </a>
            </li>
        </ul>
    </div>

</aside>


<main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg ">

    <div class="container-fluid">

        <div class="row">
            <!-- left column -->
            <div class="col-md-12 content">

                <div class="card card-primary">
                    <div class="card-header">
                        <h3 class="card-title">User Information</h3>
                    </div>
                    <!-- /.card-header -->
                    <!-- form start -->

                    <%
                        User user = LearnerDAO.getUser(ID);
                        request.setAttribute("userID", user.getID());
                    %>

                    <form modelAttribute="driver" action="./editUser?id=${userID}" method="post" id="updateUserForm">
                        <div class="card-body">

                            <div class="form-group">
                                <label>ID</label>
                                <input type="text" class="form-control" value="<%out.print(user.getID());%>" disabled>
                            </div>
                            <div class="form-group">
                                <input type="text" name="ID" value="<%out.print(user.getID());%>" style="display: none">
                            </div>
                            <div class="form-group">
                                <label for="avatar">avatar</label>
                                <input type="text" class="form-control" id="avatar"
                                       name="avatar" placeholder="Avatar" value="<%out.print(user.getPicture());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="username">username</label>
                                <input type="text" class="form-control" id="username"
                                       name="username" placeholder="Username" value="<%out.print(user.getUsername());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="password">password</label>
                                <input type="text" class="form-control" id="password"
                                       name="password" placeholder="Password" value="<%out.print(user.getPassword());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="email">email</label>
                                <input type="email" class="form-control" id="email"
                                       name="email" placeholder="Email" value="<%out.print(user.getEmail());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="firstName">firstName</label>
                                <input type="text" class="form-control" id="firstName"
                                       name="firstName" placeholder="First Name"
                                       value="<%out.print(user.getFirstName());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="lastName">lastName</label>
                                <input type="text" class="form-control" id="lastName"
                                       name="lastName" placeholder="Last Name"
                                       value="<%out.print(user.getLastName());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="role">role</label>
                                <select id="role" class="form-control" name="role" required>
                                    <option value="0" <%if (user.getRole() == 0) {%>selected="selected"<%}%>>Student
                                    </option>
                                    <option value="1" <%if (user.getRole() == 1) {%>selected="selected"<%}%>>Instructor
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="birthday">birthday</label>
                                <input type="date" class="form-control" id="birthday"
                                       name="birthday" placeholder="Birthday" value="<%out.print(user.getBirthday());%>"
                                       required>
                            </div>
                            <div class="form-group">
                                <label for="country">country</label>
                                <select id="country" class="form-control" name="countryID" required>
                                    <%
                                        ArrayList<Country> countries = CountryDAO.getAllCountry();
                                        for (Country country : countries) {
                                    %>
                                    <option value=<%out.print(country.getID());
                                        if (country.getID() == user.getCountryID()) {%>
                                                    selected="selected"
                                            <%}%>>
                                        <%out.print(country.getName());%>
                                    </option>
                                    <%}%>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="status">status</label>
                                <select id="status" class="form-control" name="status" required>
                                    <option value="0" <%if (user.getStatus() == 0) {%>selected="selected"<%}%>>Locked
                                    </option>
                                    <option value="1" <%if (user.getStatus() == 1) {%>selected="selected"<%}%>>Legal
                                    </option>
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
            <!--/.col (left) -->
            <!--/.col (right) -->
        </div>

    </div>

</main>
</body>

<%@ include file="popUpMessage.jsp" %>