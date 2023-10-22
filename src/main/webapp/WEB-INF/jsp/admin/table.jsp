<%--
  Created by IntelliJ IDEA.
  User: potasium
  Date: 10/22/2023
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<table class="table align-items-center mb-0">
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
                <a href="./editUser?id=${user.ID}" class="text-secondary font-weight-bold text-xs">
                    <i class="fas fa-solid fa-pen"></i>
                </a>
                <a href="./deleteUser?id=${user.ID}" class="text-secondary font-weight-bold text-xs">
                    <i class="fa-solid fa-trash" style="color: #ff0000;"></i>
                </a>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>

</html>
