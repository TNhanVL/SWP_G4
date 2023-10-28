<%--
  Created by IntelliJ IDEA.
  User: potasium
  Date: 10/23/2023
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<html>
<div class="card my-4">
    <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
        <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3">
            <h6 class="text-white text-capitalize ps-3">Courses table</h6>
        </div>
    </div>
    <div class="card-body px-0 pb-2">
        <table id="courses" class="table align-items-center mb-0">
            <thead>
            <tr>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                    Course
                </th>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                    Description
                </th>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                    Price
                </th>
                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">
                    Rate
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="course" items="${sessionScope.courseList}">
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
                                <h6 class="mb-0 text-sm">${course.name}</h6>
                                <p class="text-xs text-secondary mb-0">
                                    <c:out value="${sessionScope.orgList[course.organizationID-1].name}"/>
                                </p>

                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="text-xs font-weight-bold mb-0">
                                ${course.description}
                        </p>
                    </td>
                    <td>
                        <p class="text-xs font-weight-bold mb-0">
                                ${course.price}
                        </p>
                    </td>
                    <td>
                        <p class="text-xs font-weight-bold mb-0">
                                ${course.rate}
                        </p>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</html>
