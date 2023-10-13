<%-- 
    Document   : rightMenu
    Created on : Jul 5, 2023, 9:09:52 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.LessonDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="rightSide">
    <h4><%out.print(course.getTitle());%></h4>

    <%
        ArrayList<Mooc> moocs = MoocDAO.getMoocsByCourseID(course.getID());
        for (Mooc mooc1 : moocs) {

            ArrayList<Lesson> lessons = LessonDAO.getLessonsByMoocID(mooc1.getID());
    %>

    <!-- part -->
    <div class="part">
        <div class="partHeader">
            <div>
                <h5>Part <%out.print(mooc1.getIndex() + ": " + mooc1.getTitle());%></h5>
                <p class="progressLesson"><%out.print(LessonDAO.getNumberLessonsCompleted(user.getID(), mooc1.getID()) + "/" + lessons.size());%> Complete</p>
            </div>
            <i class="fa-solid fa-chevron-down"></i>

        </div>

        <div class="partBody">
            <!-- each lesson is a div -->

            <%
                for (Lesson lesson1 : lessons) {
            %>

            <!-- Start lesson -->
            <a href="<%out.print("/learn/" + course.getID() + "/" + lesson1.getID());%>">
                <div class="lesson<%if (lesson1.getID() == lesson.getID()) {
                        out.print(" active");
                    }%>">
                    <span class="lesson-status">
                        <!-- checked -->
                        <i class="<%if (LessonDAO.checkLessonCompleted(user.getID(), lesson1.getID(), request)) {
                                out.print("fa-solid fa-square-check");
                            } else {
                                out.print("fa-regular fa-square");
                            }%>">
                            <!-- unchecked -->
                            <!-- <i class="fa-regular fa-square"></i> -->
                        </i></span>
                    <div class="lesson-content">
                        <p class="title"><%out.print(lesson1.getIndex() + ". " + lesson1.getTitle());%></p>
                        <span class="description">
                            <span class="type">
                                <%
                                    //print out icon
                                    switch (lesson1.getType()) {
                                        case 0:
                                        case 3: {
                                            out.print("<i class=\"fa-brands fa-youtube\"></i>");
                                            break;
                                        }
                                        case 1: {
                                            out.print("<i class=\"fa-solid fa-file-lines\"></i>");
                                            break;
                                        }
                                        case 2: {
                                            out.print("<i class=\"fa-solid fa-pen\"></i>");
                                            break;
                                        }
                                    }
                                %>
                            </span>
                            <span class="time"><%out.print(lesson1.getTime());%> minutes</span>
                        </span>
                    </div>
                </div>
            </a>
            <!-- End lesson -->

            <%
                }
            %>

        </div>

    </div>
    <!-- end part -->

    <%
        }
    %>


</div>