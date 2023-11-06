<%@ page import="com.swp_project_g4.Model.CourseProgress" %><%--
    Document   : allCourses
    Created on : Oct 16, 2023, 1:28:52 AM
    Author     : TTNhan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    var courseProgresses = (ArrayList<CourseProgress>) request.getAttribute("courseProgresses");
    var courses = (ArrayList<Course>) request.getAttribute("purchasedCourses");
%>

<div class="purchasedCourseAndCreatedCourse">
    <c:if test="${learner != null}">
        <div class="purchasedCourse">
            <h3><%
                if (guest) {
                    out.print("All taken courses");
                } else {
                    out.print("All purchased courses");
                }
            %></h3>

            <%
                int index = -1;
                for (Course course : courses) {
                    request.getSession().setAttribute("courseID", course.getID());
                    index++;
            %>

            <!-- a course -->
            <div class="courseTaken">
                <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getPicture());%>" alt=""
                     class="courseImg">
                <div class="courseInfor">
                    <a href="/course/<%out.print(course.getID());%>">
                        <p class="courseName"><%out.print(course.getName());%></p>
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
                            int percent = courseProgresses.get(index).getProgressPercent();
                            request.getSession().setAttribute("courseProgress", courseProgresses.get(index));
                    %>
                    <div class="ProgressviewMode">

                        <progress class="courseProgress" value="<%out.print(percent);%>" max="100"></progress>
                        <c:if test="${!courseProgress.completed}">
                        <div class="notCompleted ">
                            <p>In progress <span><%out.print(percent);%></span>%</p>
                        </div>
                        </c:if>

                        <c:if test="${courseProgress.completed}">
                            <div class="completed">
                                <p>Completed</p>
                                <%
                                    if (learner != null && courseProgresses.get(index).isCompleted()) {
                                %>
                                <a href="/certificate/${learner.ID}/${courseID}" target="_blank">View
                                    certificate</a>
                                <%
                                    }
                                %>
                            </div>
                        </c:if>


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
    </c:if>

    <c:if test="${instructor != null}">
        <div class="purchasedCourse">
            <h3>All created courses</h3>


            <%
                if (instructor != null) {
                    courses = (ArrayList<Course>) request.getAttribute("createdCourses");
                    for (Course course : courses) {
            %>

            <!-- a course -->
            <div class="courseTaken">
                <img src="/public/media/course/<%out.print(course.getID() + "/" + course.getPicture());%>" alt=""
                     class="courseImg">
                <div class="courseInfor">
                    <a href="/course/<%out.print(course.getID());%>">
                        <p class="courseName"><%out.print(course.getName());%></p>
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
    </c:if>
</div>
