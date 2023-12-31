<%-- 
    Document   : course
    Created on : Oct 4, 2023, 9:35:46 PM
    Author     : TTNhan
--%>

<%@ page import="com.swp_project_g4.Database.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.swp_project_g4.Model.*" %>
<%-- 
    Document   : allCourse
    Created on : Oct 4, 2023, 9:20:17 PM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    //check course exist
    Course course = null;
    try {
        course = CourseDAO.getCourse((int) request.getAttribute("courseID"));
        if (course == null) {
            throw new Exception("Not exist course!");
        }
    } catch (Exception e) {
        request.getSession().setAttribute("error", "The course not exist!");
        response.sendRedirect("/");
        return;
    }

    Organization organization = OrganizationDAO.getOrganization(course.getOrganizationID());

    Learner learner = null;
    if (CookieServices.checkLearnerLoggedIn(request.getCookies())) {
        learner = LearnerDAO.getUserByUsername(CookieServices.getUserNameOfLearner(request.getCookies()));
    }

    CourseProgress courseProgress = (CourseProgress) request.getAttribute("courseProgress");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Course Infomation"/>
    </jsp:include>
    <link rel="stylesheet" href="/public/assets/css/courseInfo.css">
    <link rel="stylesheet" href="/public/assets/css/responsive.css">
</head>

<body>
<!-- HEADER -->
<%@include file="header.jsp" %>
<!--END HEADER -->

<div id="main">
    <div class="courseMain">
        <div class="courseIntroduction">
            <div class="orgranization">
                <img src="/public/media/organization/<%out.print(organization.getID() + "/" + organization.getPicture());%>"
                     alt="">
            </div>
            <div class="courseName">
                <h1><%out.print(course.getName());%></h1>
            </div>
            <div class="instructorInfor">
                <p class="instructorName">
                    Instructor:
                </p>
                <c:forEach var="instructor" items="${instructors}">

                    <img src="/public/media/instructor/${instructor.ID}/${instructor.picture}" alt=""
                         class="instructorImg">
                    <p class="instructorName">
                        <a
                                href="/profile/instructor/${instructor.username}">${instructor.firstName} ${instructor.lastName}</a>
                    </p>
                </c:forEach>
            </div>
            <div class="price">
                Price: <span><%out.print(course.getPrice());%>$</span>

            </div>
            <div class="addCartBnt">
                <c:choose>
                    <c:when test="${learner != null}">
                        <c:choose>
                            <c:when test="${coursePurchased != null}">
                                <a href="/learn/${course.ID}">${(courseProgress.enrolled) ? "Learn now": "Enroll now"}</a>
                            </c:when>

                            <c:otherwise>
                                <%
                                    if (CourseDAO.checkCartProduct(learner.getID(), course.getID())) {
                                        out.print("<a href=\"/course/deleteOrder/" + course.getID() + "\">Delete from cart</a>");
                                    } else {
                                        out.print("<a href=\"/cart/add/" + course.getID() + "\">Add to cart</a>");
                                    }

                                %>
                            </c:otherwise>
                        </c:choose>
                    </c:when>

                    <c:otherwise>
                        <a href="/login">Add to cart</a>
                    </c:otherwise>
                </c:choose>


                <!--<a href="#">Add to cart</a>-->
            </div>
            <% if (learner != null && courseProgress != null) {
                if (courseProgress.isCompleted()) {
            %>
            <a href="/certificate/${learner.ID}/${courseID}" class="viewCer">View Certificate</a>
            <%
                    }
                }
            %>
            <p class="enrollers">
                <span>${numberOfPurchased}</span> already enrolled
            </p>
        </div>

        <div class="popupSumary">
            <div class="header">
                <h3>Course</h3>
                <p>Gain insight into a topic and learn the fundamentals</p>
            </div>

            <div class="rateAndLike">
                <span><%out.print(course.getRate());%></span>
                <i class="fa-solid fa-star"></i>
                <span>(<%=course.getNumberOfRated()%> reviewer)</span>

                <!-- <span class="like-rate">
                <i class="fa-solid fa-thumbs-up"></i> 96%</span> -->

            </div>

            <p class="time"><%
                int sumTimeInMinute = CourseDAO.getSumTimeOfCourse(course.getID());
                out.print(Math.round(sumTimeInMinute / 6.0) / 10.0);
            %> hours (approximately)</p>

            <p class="desciption">
                Flexible schedule
            <p>Flexible schedule Learn at your own pace</p>
            </p>

            <div class="viewDetal">
                <a href="#modules">View course modules</a>
            </div>

        </div>
    </div>

    <div class="navigationBar">
        <ul>
            <li><a href="#about">About</a></li>
            <li><a href="#outcome">Outcome</a></li>
            <li><a href="#modules">Modules</a></li>
            <li><a href="#review">Review</a></li>
        </ul>
    </div>

    <div class="information">
        <div id="about">
            <h5>Skills you'll gain</h5>
            <p>Overview about the course, it will be a description of course in dadabase, I think so.</p>
        </div>
        <div id="outcome">
            <h5>Build your subject-matter expertise</h5>
            <p>The goal of the course, example as: This course is part of the Ruby on Rails Web Development
                Specialization
                When you enroll in this course, you'll also be enrolled in this Specialization.</p>
        </div>
        <div id="modules">
            <h5>There are 5 module in this course</h5>
            <p>The number of module is the part in the lesson. to simple we will use "ul,li" tag to express:</p>
            <ul>
                <li>Introduction to HTML5</li>
                <li>Introduction to CSS3</li>
                <li>Coding the static Restaurant Site</li>
                <li>Introduction to Javascript</li>
                <li>Using Javascript to build application</li>
            </ul>
        </div>
        <div id="review">
            <div id="Rating" class="tabcontent ">
                <div class="rating-container">
                    <div class="rating-left">
                        <h1>4.5</h1>
                        <i class="fas fa-star star"></i>
                        <i class="fas fa-star star"></i>
                        <i class="fas fa-star star"></i>
                        <i class="fas fa-star star"></i>
                        <i class="fas fa-star star"></i>
                    </div>
                    <div class="rating-right">
                        <div class="rating-detail">
                            <div class="progress-bar">
                                <div class="progress" style="width: 80%;"></div>
                            </div>
                            <span>5<i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i></span>

                            <span>80%</span>
                        </div>
                        <div class="rating-detail">
                            <div class="progress-bar">
                                <div class="progress" style="width: 10%;"></div>
                            </div>
                            <span>4<i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fa-regular fa-star"></i></span>

                            <span>10%</span>
                        </div>
                        <div class="rating-detail">
                            <div class="progress-bar">
                                <div class="progress" style="width: 7%;"></div>
                            </div>
                            <span>3<i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star"></i></span>

                            <span>7%</span>
                        </div>
                        <div class="rating-detail">
                            <div class="progress-bar">
                                <div class="progress" style="width: 2%;"></div>
                            </div>
                            <span>2<i class="fas fa-star star"></i>
                                        <i class="fas fa-star star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star"></i></span>

                            <span>2%</span>
                        </div>
                        <div class="rating-detail">
                            <div class="progress-bar">
                                <div class="progress" style="width: 1%;"></div>
                            </div>
                            <span>1<i class="fas fa-star star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star"></i>
                                        <i class="fa-regular fa-star">

                                        </i></span>

                            <span>1%</span>
                        </div>
                    </div>
                </div>
                <!-- Rating coment of user -->
                <div class="rating-comments">
                    <!-- start a rating -->
                    <div class="rating-content">
                        <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
                        <div class="content">
                            <p class="UserRateName">
                                Google
                            </p>
                            <div class="UserRateComment">
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                            </div>
                            <div class="rateOption">
                                        <span class="like">
                                            <i class="fa-solid fa-thumbs-up"></i>
                                            <i class="fa-solid fa-thumbs-down"></i>
                                        </span>
                                <span class="report"><b>Report</b></span>
                            </div>
                        </div>
                    </div>
                    <!-- end a rating -->
                    <!-- start a rating -->
                    <div class="rating-content">
                        <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
                        <div class="content">
                            <p class="UserRateName">
                                Google
                            </p>
                            <div class="UserRateComment">
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                            </div>
                            <div class="rateOption">
                                        <span class="like">
                                            <i class="fa-solid fa-thumbs-up"></i>
                                            <i class="fa-solid fa-thumbs-down"></i>
                                        </span>
                                <span class="report"><b>Report</b></span>
                            </div>
                        </div>
                    </div>
                    <!-- end a rating -->
                    <!-- start a rating -->
                    <div class="rating-content">
                        <img src="/public/assets/imgs/gg.png" alt="" class="rateUserImg">
                        <div class="content">
                            <p class="UserRateName">
                                Google
                            </p>
                            <div class="UserRateComment">
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                                Phần này thì hiển thị cái đánh giá của nguyên course, rồi hiển thị user nào đã rate
                            </div>
                            <div class="rateOption">
                                        <span class="like">
                                            <i class="fa-solid fa-thumbs-up"></i>
                                            <i class="fa-solid fa-thumbs-down"></i>
                                        </span>
                                <span class="report"><b>Report</b></span>
                            </div>
                        </div>
                    </div>
                    <!-- end a rating -->


                </div>

            </div>
        </div>
    </div>

</div>

<%@include file="footer.jsp" %>

<%@include file="foot.jsp" %>

<%@include file="popUpMessage.jsp" %>

</body>

</html>