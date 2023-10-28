<%-- 
    Document   : rightMenu
    Created on : Oct 5, 2023, 9:09:52 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.LessonDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="rightSide">
    <h4><%out.print(course.getName());%></h4>

    <%
        ArrayList<Chapter> chapters = ChapterDAO.getChaptersByCourseID(course.getCourseID());
        for (Chapter chapter1 : chapters) {

            ArrayList<Lesson> lessons = LessonDAO.getLessonsByChapterID(chapter1.getChapterID());
    %>

    <!-- part -->
    <div class="part">
        <div class="partHeader">
            <div>
                <h5>Part <%out.print(chapter1.getIndex() + ": " + chapter1.getName());%></h5>
                <p class="progressLesson"><%out.print(LessonDAO.getNumberLessonsCompleted(learner.getID(), chapter1.getChapterID()) + "/" + lessons.size());%> Complete</p>
            </div>
            <i class="fa-solid fa-chevron-down"></i>

        </div>

        <div class="partBody">
            <!-- each lesson is a div -->

            <%
                for (Lesson lesson1 : lessons) {
            %>

            <!-- Start lesson -->
            <a href="<%out.print("/learn/" + course.getCourseID() + "/" + lesson1.getLessonID());%>">
                <div class="lesson<%if (lesson1.getLessonID() == lesson.getLessonID()) {
                        out.print(" active");
                    }%>">
                    <span class="lesson-status">
                        <!-- checked -->
                        <i class="<%if (LessonDAO.checkLessonCompleted(learner.getID(), lesson1.getLessonID(), request)) {
                                out.print("fa-solid fa-square-check");
                            } else {
                                out.print("fa-regular fa-square");
                            }%>">
                            <!-- unchecked -->
                            <!-- <i class="fa-regular fa-square"></i> -->
                        </i></span>
                    <div class="lesson-content">
                        <p class="title"><%out.print(lesson1.getIndex() + ". " + lesson1.getName());%></p>
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