<%-- 
    Document   : allCourses
    Created on : Jul 16, 2023, 1:28:52 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="purchasedCourseAndCreatedCourse">

    <div class="purchasedCourse">
        <h3><%
            if (guest) {
                out.print("All taken courses");
            } else {
                out.print("All purchased courses");
            }
            %></h3>

        <%
            ArrayList<Course> courses = CourseDAO.getAllPurchasedCourses(user.getID());
            for (Course course : courses) {
        %>

        <!-- a course -->
        <div class="courseTaken">
            <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getImage());%>" alt="" class="courseImg">
            <div class="courseInfor">
                <a href="/course/<%out.print(course.getID());%>">
                    <p class="courseName"><%out.print(course.getTitle());%></p>
                    <%
                        if (guest) {
                    %>
                    <div class="rate">
                        <span><%out.print(course.getRate());%></span>
                        <i class="fa-solid fa-star"></i>
                        <span>(2423 reviewer)</span>
                    </div>
                    <%
                        }
                    %>
                </a>
                <%
                    if (!guest) {
                        int sumTimeOfCourse = CourseDAO.getSumTimeOfCourse(course.getID());
                        int sumOfCompletedTime = CourseDAO.getSumTimeCompletedOfCourse(user.getID(), course.getID());
                        int percent = 100;
                        if (sumTimeOfCourse != 0) {
                            percent = (int) 100f * sumOfCompletedTime / sumTimeOfCourse;
                        }
                %>
                <div class="ProgressviewMode">

                    <progress class="courseProgress" value="<%out.print(percent);%>" max="100"></progress>
                    <div class="notCompleted ">
                        <p>In progress <span><%out.print(percent);%></span>%</p>
                    </div>

                    <div class="completed">
                        <p>Completed</p>
                        <%
                            if (CourseDAO.checkCertificate(user.getID(), course.getID())) {
                                String certificateName = CourseDAO.getCertificateName(user.getID(), course.getID());
                        %>
                        <a href="/public/media/certificate/<%out.print(certificateName);%>" target="_blank">View certificate</a>
                        <%
                            }
                        %>
                    </div>

                </div>

                <%
                    }
                %>

            </div>
        </div>
        <!-- end course -->

        <%
            }
        %>

    </div>

    <div class="purchasedCourse">
        <h3>All created courses</h3>


        <%
            if (lecturer != null) {
                courses = CourseDAO.getAllCreatedCourses(user.getID());
                for (Course course : courses) {
        %>

        <!-- a course -->
        <div class="courseTaken">
            <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getImage());%>" alt="" class="courseImg">
            <div class="courseInfor">
                <a href="/course/<%out.print(course.getID());%>">
                    <p class="courseName"><%out.print(course.getTitle());%></p>
                    <div class="rate">
                        <span><%out.print(course.getRate());%></span>
                        <i class="fa-solid fa-star"></i>
                        <span>(2423 reviewer)</span>
                    </div>
                </a>

            </div>
        </div>
        <!-- end course -->

        <%
                }
            }
        %>

    </div>
</div>
