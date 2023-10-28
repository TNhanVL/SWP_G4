<%-- 
    Document   : lesson
    Created on : Oct 3, 2023, 9:21:18 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Model.Post" %>
<%@page import="com.swp_project_g4.Model.Chapter" %>
<%@page import="com.swp_project_g4.Model.Lesson" %>
<%@page import="com.swp_project_g4.Service.CookieServices" %>
<%@page import="com.swp_project_g4.Model.Learner" %>
<%@page import="com.swp_project_g4.Model.Course" %>
<%@ page import="com.swp_project_g4.Database.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%
    Learner learner = (Learner) request.getAttribute("learner");
    Course course = (Course) request.getAttribute("course");
    Chapter chapter = (Chapter) request.getAttribute("chapter");
    Lesson lesson = (Lesson) request.getAttribute("lesson");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="Yojihan Study"/>
    </jsp:include>
    <link rel="stylesheet" href="/public/assets/css/lesson.css">
    <script src="https://www.youtube.com/iframe_api"></script>
</head>

<body>
<!-- HEADER -->
<%@include file="header.jsp" %>
<!--END HEADER -->

<div class="main">
    <!-- Left Side -->
    <div class="leftSide">

        <div class="lesson-main">
            <%
                switch (lesson.getType()) {
                    //type 0 -> video
                    //type 3 -> Youtube ID
                    case 0:
                    case 3: {
                        Post post = PostDAO.getPostByLessonID(lesson.getID());
            %>
            <%@include file="lesson/video.jsp" %>
            <%
                    break;
                }
                case 1: {
                    Post post = PostDAO.getPostByLessonID(lesson.getID());
            %>
            <%@include file="lesson/post.jsp" %>
            <%
                    break;
                }
                //type 2 -> quiz
                case 2: {
            %>
            <%@include file="lesson/quiz.jsp" %>
            <% break;
            }
            }
            %>

        </div>

        <!--Info under lesson-->
        <%@include file="lesson/lessonInfo.jsp" %>

    </div>
    <!-- Right side -->

    <%@include file="lesson/rightMenu.jsp" %>

</div>

<%@include file="footer.jsp" %>

<%@include file="foot.jsp" %>

<script src="/public/assets/js/lesson.js"></script>
<script src="<%out.print(request.getContextPath());
        %>/public/assets/js/option.js"></script>

<%@include file="popUpMessage.jsp" %>

</body>

</html>