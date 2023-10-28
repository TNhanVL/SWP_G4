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

<jsp:include page="head.jsp">
    <jsp:param name="title" value="Dashboard"/>
</jsp:include>

<body>
</aside><!-- End Left sidebar -->
<table>
    <h2>List of Users</h2>

    <tr class="tableHeader">
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Role</th>
        <th>Status</th>
        <th>Modify</th>
    </tr>

    <c:forEach var="instructor" items="${sessionScope.userList}">
        <tr>
            <td>${instructor.ID}</td>
            <td>${instructor.username}</td>
            <td>${instructor.email}</td>
            <td>${instructor.firstName}</td>
            <td>${instructor.lastName}</td>
            <td>
                <c:choose>
                    <c:when test="${instructor.role == 0}">
                        Student
                    </c:when>
                    <c:when test="${instructor.role == 1}">
                        Instructor
                    </c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${instructor.status == 0}">
                        Locked
                    </c:when>
                    <c:when test="${instructor.status == 1}">
                        Active
                    </c:when>
                </c:choose>
            </td>
            <td>
                <a href="./editUser?id=${instructor.ID}">
                    <i class="fas fa-solid fa-pen"></i>
                </a>
                <a href="./deleteUser?id=${instructor.ID}">
                    <i class="fa-solid fa-trash" style="color: #ff0000;"></i>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<%@ include file="foot.jsp" %>


<%@ include file="popUpMessage.jsp" %>