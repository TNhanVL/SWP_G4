<%-- 
    Document   : profile
    Created on : Jul 7, 2023, 11:21:33 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Country"%>
<%@page import="com.swp_project_g4.Model.Lecturer"%>
<%@page import="com.swp_project_g4.Model.Course"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.swp_project_g4.Model.Organization"%>
<%@ page import="com.swp_project_g4.Database.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    //get user that want to show profile
    String profileUsername = (String) request.getAttribute("username");
    User user = UserDAO.getUserByUsername(profileUsername);

    if (user == null) {
        request.getSession().setAttribute("error", "Not exist this username!");
        response.sendRedirect("/");
        return;
    }

    boolean guest = true;
    //Get user loggedIn
    if (CookieServices.checkUserLoggedIn(request.getCookies())) {
        User userLoggedin = UserDAO.getUserByUsername(CookieServices.getUserName(request.getCookies()));
        guest = (userLoggedin == null || user.getID() != userLoggedin.getID());
    }

    request.getSession().setAttribute("guest", guest);
    request.getSession().setAttribute("user", user);

    Lecturer lecturer = LecturerDAO.getLecturer(user.getID());
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
                                    if (loggedInHeader) {

                                        boolean isUrl = false;
                                        try {
                                            new URL(user.getAvatar()).toURI();
                                            isUrl = true;
                                        } catch (Exception e) {
                                        }

                                        if (isUrl) {
                                            out.print(user.getAvatar());
                                        } else {
                                            out.print("/public/media/user/" + user.getID() + "/" + user.getAvatar());
                                        }
                                    } else {
                                        out.print("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png");
                                    }
                                     %>" alt="">
                            </div>
                            <div class="name">
                                <h4>${user.firstName} ${user.lastName}</h4>
                            </div>

                            <div class="orgranization">
                                <p><%out.print(CountryDAO.getCountry(user.getCountryID()).getName());%></p>
                                <%
                                    if (lecturer != null) {
                                        Organization organization = OrganizationDAO.getOrganization(lecturer.getOrganizationID());
                                %>
                                <p>Lecturer of <%out.print(organization.getName());%></p>
                                <img class="org" src="/public/media/organization/<%out.print(organization.getID() + "/" + organization.getLogo());%>" alt="">
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
                                    int sumTimeCompletedInMinute = CourseDAO.getSumTimeCompletedOfAllCourses(user.getID());
                                    out.print(Math.round(sumTimeCompletedInMinute / 6.0) / 10.0);
                                    %></span>
                            </p>
                            <p class="element"><i class="fa-solid fa-cart-shopping"></i>Courses purchased<span><%out.print(CourseDAO.getNumberPurchasedCourse(user.getID()));%></span></p>
                            <p class="element"><i class="fa-regular fa-circle-check"></i>Courses completed <span><%out.print(CourseDAO.getNumberCompletedCourse(user.getID()));%></span>
                            </p>
                            <p class="element"><i class="fa-sharp fa-solid fa-certificate"></i>Courses created
                                <span><%out.print(CourseDAO.getNumberCreatedCourse(user.getID()));%></span>
                            </p>
                            <p class="element">Learn since 2020</p>
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
                            <button onclick="window.location.href = '#changePassword'">Change password</button>
                        </div>

                        <p>Let the Yojihan community of other learners and instructors know more about you!</p>

                        <form action="/updateUser?userID=${user.ID}" method="post">
                            <div>
                                <label for="firstName">First name:</label>
                                <input value="${user.firstName}" type="text" id="firstName" name="firstName" placeholder="Enter your first name" required>
                            </div>

                            <div>
                                <label for="lastName">Last name:</label>
                                <input value="${user.lastName}" type="text" id="lastName" name="lastName" placeholder="Enter your last name" required>
                            </div>
                            <div>
                                <label for="birthday">Birthday:</label>
                                <input value="${user.birthday}" type="date" id="birthday" name="birthday" placeholder="dd/mm/yyyy" required>
                            </div>

                            <%                                ArrayList<Country> countries = CountryDAO.getAllCountry();
                                request.getSession().setAttribute("countries", countries);
                                %>

                            <div>
                                <label for="country">Country:</label>
                                <select name="countryID" id="country">
                                    <c:forEach items="${countries}" var="country">
                                        <option value="${country.ID}" class="form-control" placeholder="Enter your country" required <c:if test="${country.ID == user.countryID}">selected</c:if>>${country.name}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div>
                                <label for="email">Email address:</label>
                                <input value="${user.email}" type="email" id="email" name="email" placeholder="Enter your email address" required>
                            </div>

                            <hr>

                            <div class="saveInfor">
                                <button type="submit">Update</button>
                            </div>
                        </form>

                    </div>

                </div> 
            </c:if>



        </div>

        <!-- END BODY -->

        <%@include file="../footer.jsp" %>

        <%@include file="../foot.jsp" %>

        <script src="/public/assets/js/lesson.js"></script>
        <script src="/public/assets/js/option.js"></script>

        <%@include file="../popUpMessage.jsp" %>

    </body>

</html>