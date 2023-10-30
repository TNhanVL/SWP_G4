<%-- 
    Document   : profile
    Created on : Oct 7, 2023, 11:21:33 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Country" %>
<%@page import="com.swp_project_g4.Model.Instructor" %>
<%@page import="com.swp_project_g4.Model.Course" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.swp_project_g4.Model.Organization" %>
<%@ page import="com.swp_project_g4.Database.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    Learner learner = (Learner) request.getAttribute("learner");
    boolean guest = (boolean) request.getAttribute("guest");
    Instructor instructor = InstructorDAO.getInstructor(learner.getID());
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="../head.jsp">
        <jsp:param name="title" value="Yojihan"/>
    </jsp:include>
    <link rel="stylesheet" href="/public/assets/css/profile.css">
    <link rel="stylesheet" href="/public/assets/css/responsive.css">
</head>

<body>
<!-- HEADER -->
<%@include file="../header.jsp" %>
<!--END HEADER -->

<!-- BODY -->

<div id="main">

    <c:if test="${!guest}">
    <div class="tab">
        <button class="tablinks" onclick="openTab(event, 'info')">Profile</button>
        <button class="tablinks" onclick="openTab(event, 'personal')">Personal Information</button>
    </div>
    </c:if>

    <div id="info" class="tabcontent active">
        <div class="infor">
            <div class="userTags">
                <div class="inforTag">
                    <div class="avatar">
                        <img src="<%
                                        boolean isUrl = false;
                                        try {
                                            new URL(learner.getPicture()).toURI();
                                            isUrl = true;
                                        } catch (Exception e) {
                                        }

                                        if (isUrl) {
                                            out.print(learner.getPicture());
                                        } else {
                                            out.print("/public/media/user/" + learner.getID() + "/" + learner.getPicture());
                                        }
                                     %>" alt="">
                    </div>
                    <div class="name">
                        <h4>${learner.firstName} ${learner.lastName}</h4>
                    </div>

                    <div class="orgranization">
                        <p><%out.print(CountryDAO.getCountry(learner.getCountryID()).getName());%></p>
                        <%
                            if (instructor != null) {
                                Organization organization = OrganizationDAO.getOrganization(instructor.getOrganizationID());
                        %>
                        <p>Instructor of <%out.print(organization.getName());%></p>
                        <img class="org"
                             src="/public/media/organization/<%out.print(organization.getID() + "/" + organization.getPicture());%>"
                             alt="">
                        <%
                            }
                        %>
                    </div>


                    <!-- <div class="socialMedia" content="Comming Soon">
                    <i class="fa-brands fa-facebook"></i>
                    <i class="fa-brands fa-instagram"></i>
                    <i class="fa-brands fa-github"></i>
                    <i class="fa-solid fa-envelope"></i>
                </div> -->
                </div>
                <div class="expTag">
                    <h4>Experience</h4>
                    <p class="element"><i class="fa-sharp fa-regular fa-clock"></i>Total learning hours
                        <span><%
                            int sumTimeCompletedInMinute = (int) request.getAttribute("totalLearningTime");
                            out.print(Math.round(sumTimeCompletedInMinute / 6.0) / 10.0);
                        %></span>
                    </p>
                    <p class="element"><i class="fa-solid fa-cart-shopping"></i>Courses
                        purchased<span>${numberOfPurchasedCourses}</span></p>
                    <p class="element"><i class="fa-regular fa-circle-check"></i>Courses completed
                        <span>${numberOfCompletedCourse}</span>
                    </p>
                    <p class="element"><i class="fa-sharp fa-solid fa-certificate"></i>Courses created
                        <span><%out.print(CourseDAO.getNumberCreatedCourse(learner.getID()));%></span>
                    </p>
                    <p class="element">Learn since ${firstYearOfLearning}</p>
                </div>
            </div>

            <%@include file="allCourses.jsp" %>

        </div>
    </div>


    <c:if test="${!guest}">
    <div id="personal" class="tabcontent">

        <div class="personal">

            <div class="header">
                <h3>Edit my profile</h3>
                <button type="button"
                        class="btn btn-secondary float-right"
                        id="changePasswordButton">
                    Change Password
                </button>
            </div>

            <p>Let the Yojihan community of other learners and instructors know more about you!</p>

            <form action="/updateUser?userID=${learner.ID}" method="post">
                <div>
                    <label for="firstName">First name:</label>
                    <input value="${learner.firstName}" type="text" id="firstName" name="firstName"
                           placeholder="Enter your first name" required>
                </div>

                <div>
                    <label for="lastName">Last name:</label>
                    <input value="${learner.lastName}" type="text" id="lastName" name="lastName"
                           placeholder="Enter your last name" required>
                </div>
                <div>
                    <label for="birthday">Birthday: </label>
                    <input value=
                                   '<fmt:formatDate pattern="yyyy-MM-dd" value="${learner.birthday}"/>'
                           type=" date" id="birthday" name="birthday" placeholder="dd/mm/yyyy" required>
                </div>

                <% ArrayList<Country> countries = CountryDAO.getAllCountry();
                    request.getSession().setAttribute("countries", countries);
                %>

                <div>
                    <label for="country">Country:</label>
                    <select name="countryID" id="country">
                        <c:forEach items="${countries}" var="country">
                            <option value="${country.ID}" class="form-control" placeholder="Enter your country"
                                    required
                                    <c:if test="${country.ID == learner.countryID}">selected</c:if>>${country.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="email">Email address:</label>
                    <input value="${learner.email}" type="email" id="email" name="email"
                           placeholder="Enter your email address" required>
                </div>

                <hr>

                <div class="saveInfor">
                    <button type="submit">Update</button>
                </div>
            </form>

        </div>

    </div>
    </c:if>
    <div id="overlay"></div>

    <div>
        <form id="passwordForm"
              action="/changePassword"
              method="POST"
              style="display: none">
            <input
                    type="hidden"
                    name="username"
                    value="${learner.username}"
            />

            <h3>Change Password</h3>
            <div class="form-group">
                <label for="oldPassword">Old Password:</label>
                <input
                        type="password"
                        class="form-control"
                        id="oldPassword"
                        name="oldPassword"
                />
            </div>
            <div class="form-group">
                <label for="password">New Password:</label>
                <input
                        type="password"
                        class="form-control"
                        id="password"
                        name="password"
                />
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input
                        type="password"
                        class="form-control"
                        id="confirmPassword"
                        name="confirmPassword"
                />
            </div>
            <div id="passwordMatchError" style="display: none; color: red">
                Passwords do not match.
            </div>
            <div id="passwordError" style="display: none; color: red">
                Password cannot be empty.
            </div>
            <button type="submit"
                    class="btn btn-primary"
                    name="submit"
                    value="password">
                Save
            </button>
            <button type="button"
                    class="btn btn-secondary"
                    id="cancelPasswordChange">
                Cancel
            </button>
        </form>

    </div>

    <!-- END BODY -->

    <%@include file="../footer.jsp" %>

    <%@include file="../foot.jsp" %>

    <script src="/public/assets/js/lesson.js"></script>
    <script src="/public/assets/js/option.js"></script>
    <script src="/public/assets/js/change_password.js"></script>

    <%@include file="../popUpMessage.jsp" %>

</body>

</html>