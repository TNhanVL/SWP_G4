<%-- 
    Document   : main
    Created on : Oct 3, 2023, 12:06:34 AM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.CourseDAO" %>
<%@page import="com.swp_project_g4.Model.Course" %>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Yojihan"/>
    </jsp:include>
    <link rel="stylesheet" href="/public/assets/css/main.css">
</head>

<body>
<!-- HEADER -->
<%@include file="header.jsp" %>
<!--END HEADER -->

<!-- BODY -->
<div id="body">
    <!-- ????? -->
    <div class="advertisement row">
        <div class="contentBox col-md-6">
            <p>
                <b>Sales end. Dreams go on.</b><br>
                Get a second chance at the best prices
                of the season. Courses start at just
                ₫199,000. Today only.</p>
        </div>
        <div class="teamWorkImg col-md-6">
            <img src="/public/assets/imgs/teamwork.png" alt="">
        </div>
    </div>

    <!-- ????? -->
    <div class="instructorAds row">
        <div class="boxContent col-md-6">
            <div class="content">
                <h4>Become an instructor</h4>
                <p>Instructors from around the world teach millions of students on Udemy. We provide the tools and
                    skills to teach what you love.
                </p>
            </div>
        </div>
        <div class="AdsImg col-md-6">
            <img src="/public/assets/imgs/instructor.png" alt="">
        </div>
    </div>

    <!-- Popular Course -->
    <div class="popularCourse">
        <h2>Some popular courses</h2>
        <!-- Bootstrap Carousel -->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0"
                        class="bg-success active"
                        aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                        class="bg-success"
                        aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                        class="bg-success"
                        aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">

                <%
                    ArrayList<Course> courses = CourseDAO.getPopularCourses(12);
                    int index = -1;
                    for (Course course : courses) {
                        index++;
                        if (index % 4 == 0) {
                %>

                <div class="carousel-item
                    <%if (index / 4 == 0) {
                                out.print(" active");
                            }%>">
                    <ul class="courseList">

                        <%}%>

                        <!-- course -->
                        <li class="listItem">
                            <a href="/course/<%out.print(course.getID());%>">
                                <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getPicture());%>"
                                     alt="" class="courseImg">
                                <h4 class="courseName"><%out.print(course.getName());%></h4>
                                <div class="courseDescription">
                                            <span class="type">
                                                <span><%out.print(course.getDescription());%></span>
                                                <span><%
                                                    int sumTimeInMinute = CourseDAO.getSumTimeOfCourse(course.getID());
                                                    out.print(Math.round(sumTimeInMinute / 6.0) / 10.0);
                                                %>h</span>
                                            </span>
                                    <div class="rateAndPrice">
                                        <div class="rate">
                                            <span><%out.print(course.getRate());%></span>
                                            <i class="fa-solid fa-star"></i>
                                            <span>(0 reviewer)</span>
                                        </div>
                                        <span class="price"><%out.print(course.getPrice());%>$</span>
                                    </div>
                                </div>
                            </a>

                        </li>
                        <!-- end course -->

                        <%if ((index + 1) % 4 == 0) {%>
                    </ul>
                </div>

                <%
                        }
                    }%>

            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-bs-slide="prev">
                <span class="carousel-control-prev-icon bg-dark" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-bs-slide="next">
                <span class="carousel-control-next-icon bg-dark" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </a>
        </div>
        <!-- End  Bootstrap Carousel-->
        <div class="viewCourseBtn">
            <button class="viewCourseBtn">
                <a href="course/all">View All Courses</a>
            </button>
        </div>

    </div>

    <div class="advantageAds row">
        <img src="/public/assets/imgs/banner%20advantages%20-%20homepage.png" alt="" class="ads col-md-4">
        <div class="content col-md-8">
            <h4>Outstanding advantages</h4>
            <ul>
                <li><b> Expert Instruction:</b> Learn from industry professionals who have real-world experience and can
                    provide valuable insights to help you succeed.
                </li>
                <li><b> Flexible, On-Demand Learning:</b> Take courses whenever and wherever it's convenient for you.
                    Our platform allows you to learn at your own pace, on your own schedule.
                </li>
                <li><b> Affordable Prices:</b> Get high-quality education at an affordable price. We believe everyone
                    should have access to quality learning opportunities without breaking the bank.
                </li>
                <li><b> Certification and Credentials:</b> Earn certificates and credentials that demonstrate your
                    mastery of the course material. Our programs are designed to help you get ahead in your career or
                    start a new one.
                </li>
            </ul>
        </div>
    </div>

    <div class="feedBack">
        <h2>What our learners say...</h2>
        <ul class="feedbackListItem">

            <li class="feedbackItem">
                <div class="headerItem">
                    <i class="fa-solid fa-quote-left"></i>
                </div>
                <div class="bodyItem">
                    Education is the key to unlocking your full potential. With knowledge, you can illuminate the path to your dreams and shape a brighter future. Join us on a journey of lifelong learning and discover the endless possibilities that await you.
                </div>
                <div class="footerItem">
                    <img src="/public/assets/imgs/logo.png" alt="" class="userImg">
                    <span class="userInfor">Thanh Nhan</span>
                </div>
            </li>

            <li class="feedbackItem">
                <div class="headerItem">
                    <i class="fa-solid fa-quote-left"></i>
                </div>
                <div class="bodyItem">
                    Learning is the bridge between who you are and who you want to become. Our courses are the stepping stones on that path, guiding you towards a future of infinite opportunities and personal growth.
                </div>
                <div class="footerItem">
                    <img src="/public/assets/imgs/logo.png" alt="" class="userImg">
                    <span class="userInfor">Nhan Tran</span>
                </div>
            </li>

            <li class="feedbackItem">
                <div class="headerItem">
                    <i class="fa-solid fa-quote-left"></i>
                </div>
                <div class="bodyItem">
                    Education is the compass that leads you on a journey of self-discovery and transformation. Enroll in our courses and let knowledge be your guiding star as you navigate the vast landscape of your potential.
                </div>
                <div class="footerItem">
                    <img src="/public/assets/imgs/logo.png" alt="" class="userImg">
                    <span class="userInfor">Tran Thanh Nhan</span>
                </div>
            </li>

            <li class="feedbackItem">
                <div class="headerItem">
                    <i class="fa-solid fa-quote-left"></i>
                </div>
                <div class="bodyItem">
                    Thanks u guys for the website which delivered me an opportunity to enhance my
                    English in a
                    quite short period of time, because of you I succeeded in getting overall band
                    8, with L-9
                    and R-8
                </div>
                <div class="footerItem">
                    <img src="/public/assets/imgs/logo.png" alt="" class="userImg">
                    <span class="userInfor">Thanh Nhan Tran</span>
                </div>
            </li>
        </ul>

    </div>

    <div class="collaborate">
        <p>We collaborate with <span>100+ leading universities and companies</span></p>
        <div class="collaborateLogos">
            <img src="/public/assets/imgs/gg.png" alt="">
            <img src="/public/assets/imgs/michigan.png" alt="">
            <img src="/public/assets/imgs/fu.png" alt="">
            <img src="/public/assets/imgs/standford.png" alt="">
            <img src="/public/assets/imgs/hw.jpg" alt="">
        </div>
    </div>

    <div class="advertisement ads2 row">
        <div class="teamWorkImg col-md-6">
            <img src="/public/assets/imgs/teamwork.png" alt="">
        </div>
        <div class="contentBox ads2 col-md-6">
            <p>
                <b>Expand Your Horizons with Online Learning!</b><br>
                Embark on a learning adventure today and unleash your potential with our diverse online courses.
                From coding to creative writing, we provide the tools and resources to help you thrive in the
                digital age. Don't let distance or time constraints hinder you - ignite your curiosity now!
            </p>
        </div>

    </div>


</div>
<!-- END BODY -->

<%@include file="footer.jsp" %>

<%@include file="foot.jsp" %>

<%@include file="popUpMessage.jsp" %>

</body>

</html>