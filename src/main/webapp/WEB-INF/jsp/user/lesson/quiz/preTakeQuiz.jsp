<%-- 
    Document   : preTakeQuiz
    Created on : Oct 6, 2023, 7:16:02 PM
    Author     : TTNhan
--%>

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
                    long timeSecond = lesson.getTime() * 60L;
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
                <h5>${numberOfQuestion} questions</h5>
            </div>

            <div class="finishBtn"><a href="/learn/startAQuiz/${lessonId}/${lessonProgressID}"><p>Take Quiz</p></a></div>
        </div>
    </form>
</div>
