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
        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3 ">
            <div class="col">
                <h6 class="text-white text-capitalize ps-3">Learner table</h6>
                <a href="./addLearner">
                    <button class="btn-warning" style="border: none; border-radius: 7px">
                        Add learner
                    </button>
                </a>

            </div>

        </div>
    </div>
    <div class="card-body px-0 pb-2">
        <table id="user" class="table align-items-center mb-0">
            <thead>
            <tr>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                    ID
                </th>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                    Account
                </th>
                <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                    Status
                </th>
                <th class="text-secondary opacity-7"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="instructor" items="${sessionScope.learnerList}">
                <tr>
                    <td>
                        <p class="text-xs font-weight-bold mb-0">
                                ${instructor.ID}
                        </p>
                    </td>
                    <td>
                        <div class="d-flex px-2 py-1">
                            <div>
                                <img class="avatar avatar-sm me-3 border-radius-lg" alt="user1"
                                     src=
                                     <c:choose>
                                     <c:when test='${instructor.picture == "null" || empty instructor.picture}'>
                                             "/public/assets/imgs/logo.png"
                                </c:when>
                                <c:otherwise>
                                    "/public/media/user/${instructor.ID}/${instructor.picture}"
                                </c:otherwise>
                                </c:choose>
                                >
                            </div>
                            <div class="d-flex flex-column justify-content-center">
                                <a href="./editUser?id=${instructor.ID}"
                                   class="text-secondary font-weight-bold text-xs">
                                    <h6 class="mb-0 text-sm">${instructor.username}</h6>
                                </a>
                                <p class="text-xs text-secondary mb-0">${instructor.email}</</p>
                            </div>
                        </div>
                    </td>
                    <td class="align-middle text-center text-sm">
                        <c:choose>
                            <c:when test="${instructor.status == 1}">
                                <span class="badge badge-sm bg-gradient-danger">Locked</span>
                            </c:when>
                            <c:when test="${instructor.status == 0}">
                                <span class="badge badge-sm bg-gradient-success">Active</span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td class="align-middle">
                        <a style="margin: 0 10px" href="./editUser?id=${instructor.ID}"
                           class="text-secondary font-weight-bold text-xs">
                            <i class="fas fa-solid fa-pen"></i>
                        </a>
                        <a style="margin: 0 10px" href="./deleteUser?id=${instructor.ID}"
                           onclick="return confirm('Do you want to delete this learner?')"
                           class="text-secondary font-weight-bold text-xs">
                            <i class="fas fa-solid fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</html>
