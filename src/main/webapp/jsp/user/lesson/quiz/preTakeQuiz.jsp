<%-- 
    Document   : preTakeQuiz
    Created on : Jul 6, 2023, 7:16:02 PM
    Author     : TTNhan
--%>

<%@page import="com.swp_project_g4.Database.QuestionDAO"%>
<%@ page import="com.swp_project_g4.Database.QuestionDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="quiz-type1">
    <form action="" method="post">
        <div class="leftSide">
            <div class="leftSideContent">
                <h1>Press Take Quiz to continue!</h1>
            </div>
        </div>
        <div class="rightSide">
            <div class="time">
                <h5>Overview</h5>
                <i class="fa-regular fa-clock"></i>
                <span><%
                    //calculate remain time then format and print out
                    long timeSecond = lesson.getTime() * 60;
                    if (timeSecond < 0) {
                        timeSecond = 0;
                    }
                    long seconds = timeSecond % 60;
                    timeSecond /= 60;
                    long minutes = timeSecond % 60;
                    timeSecond /= 60;
                    long hours = timeSecond;

                    String timeString = "";
                    if (hours > 0) {
                        timeString += hours + ":";
                    }
                    if (minutes < 10) {
                        timeString += "0";
                    }
                    timeString += minutes + ":";
                    if (seconds < 10) {
                        timeString += "0";
                    }
                    timeString += seconds;
                    out.print(timeString);
                    %></span>
            </div>
            <div class="listQuestion">
                <h5><%out.print(QuestionDAO.getNumberQuestionByLessonID(lesson.getID()));%> questions</h5>
            </div>

            <div class="finishBtn"><a href="/startAQuiz/<%out.print(lesson.getID());%>"><p>Take Quiz</p></a></div>
        </div>
    </form>
</div>
