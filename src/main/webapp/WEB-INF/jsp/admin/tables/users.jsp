<%--
  Created by IntelliJ IDEA.
  User: potasium
  Date: 10/23/2023
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
<div class="card my-4">
    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
            <h6 class="text-white text-capitalize ps-3">Users table</h6>
        </div>
    </div>
    <div class="card-body px-0 pb-2">
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
                <tr><td>
                        <div class="d-flex px-2 py-1">
                            <div>
                                <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                     src=
                                     <c:choose>
                                     <c:when test='${user.picture == "null"}'>
                                             "/public/assets/imgs/logo.png"
                                </c:when>
                                <c:otherwise>
                                    "/public/media/user/${user.ID}/${user.picture}"
                                </c:otherwise>
                                </c:choose>
                                >
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                                <a href="./editUser?id=${user.ID}"
                                   class="text-secondary font-weight-bold text-xs">
                                    <h6 class="mb-0 text-sm">${user.username}</h6>
                                </a>
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
</html>
