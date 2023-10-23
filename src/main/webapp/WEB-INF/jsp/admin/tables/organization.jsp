<%--
  Created by IntelliJ IDEA.
  User: potasium
  Date: 10/23/2023
  Time: 11:32 AM
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
        <table id="organization" class="table align-items-center mb-0">
            <thead>
            <tr>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                    Organization
                </th>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                    Description
                </th>
                <th class="text-secondary opacity-7"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="org" items="${sessionScope.orgList}">
                <tr>
                    <td>
                        <div class="d-flex px-2 py-1">
                            <div>
                                <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                     src=
                                     <c:choose>
                                     <c:when test='${empty org.picture}'>
                                             "/public/assets/imgs/logo.png"
                                </c:when>
                                <c:otherwise>
                                    "${org.picture}"
                                </c:otherwise>
                                </c:choose>
                                >
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                                <h6 class="mb-0 text-sm">${org.name}</h6>
                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="text-xs font-weight-bold mb-0">
                                ${org.description}
                        </p>
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
